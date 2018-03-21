package nju.dc.ticketserver.dao.impl;

import nju.dc.ticketserver.dao.OrderDao;
import nju.dc.ticketserver.dao.UserDao;
import nju.dc.ticketserver.po.CouponPO;
import nju.dc.ticketserver.po.OrderPO;
import nju.dc.ticketserver.utils.VIPHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class OrderDaoImpl implements OrderDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private UserDao userDao;

    @Override
    public OrderPO getOrderPO(String orderID) {

        String checkExistSql = "Select count(1) from orders where orderID = " + '"' + orderID + '"';
        int checkExists = jdbcTemplate.queryForObject(checkExistSql, new Object[]{}, Integer.class);
        if (checkExists == 0) {
            return null;
        }

        String sql = "Select * from orders where orderID = " + '"' + orderID + '"';
        OrderPO po = jdbcTemplate.queryForObject(sql, (resultSet, i) -> {
            OrderPO tempPO = new OrderPO();
            tempPO.setOrderID(orderID);
            tempPO.setUserID(resultSet.getString("userID"));
            tempPO.setUsername(resultSet.getString("username"));
            tempPO.setVenueID(resultSet.getString("venueID"));
            tempPO.setShowID(resultSet.getString("showID"));
            tempPO.setUsername(resultSet.getString("username"));
            tempPO.setPurchaseMethod(resultSet.getString("purchaseMethod"));
            tempPO.setTicketsAmount(resultSet.getInt("ticketsAmount"));
            tempPO.setOrderState(resultSet.getString("orderState"));

            tempPO.setOrderDate(resultSet.getString("orderDate"));

            tempPO.setTotalPrice(resultSet.getDouble("totalPrice"));
            tempPO.setUnitPrice(resultSet.getDouble("unitPrice"));
            tempPO.setDiscount(resultSet.getDouble("discount"));

            //处理seat
            tempPO.setSeat(resultSet.getString("seats"));

            tempPO.setBackMoney(resultSet.getDouble("backMoney"));
            return tempPO;
        });
        return po;
    }


    @Override
    public int createOrder(OrderPO orderPO) {

        double nowPrice = orderPO.getTotalPrice() * orderPO.getDiscount();

        String sql = "insert into orders(orderID,userID,username,venueID,showID,showName,seats,purchaseMethod,ticketsAmount,"
                + "orderState,orderDate,totalPrice,unitPrice,discount) values "
                + "("
                + '"' + orderPO.getOrderID() + '"' + "," + '"' + orderPO.getUserID() + '"' + "," + '"' + orderPO.getUsername() + '"'
                + "," + '"' + orderPO.getVenueID() + '"' + "," + '"' + orderPO.getShowID() + '"' + "," + '"' + orderPO.getShowName() + '"' + "," + '"' + orderPO.getSeat() + '"'
                + "," + '"' + orderPO.getPurchaseMethod() + '"' + "," + '"' + orderPO.getTicketsAmount() + '"' + "," + '"' + orderPO.getOrderState() + '"'
                + "," + '"' + orderPO.getOrderDate() + '"' + "," + '"' + nowPrice + '"' + "," + '"' + orderPO.getUnitPrice() + '"'
                + "," + '"' + orderPO.getDiscount() + '"'
                + ")";

        return jdbcTemplate.update(sql);
    }

    @Override
    public List<OrderPO> getRecentOrders(String userID) {
        String sql = "select * from orders where userID = " + '"' + userID + '"' + " order by orderID DESC limit 8";
        List<OrderPO> recentOrderList = jdbcTemplate.query(sql, getOrderPOMapper());
        return recentOrderList.size() == 0 ? new ArrayList<>() : recentOrderList;
    }

    @Override
    public List<OrderPO> getUserUnpayOrders(String userID, String showID) {
        String sql = "select * from orders where userID = " + '"' + userID + '"' + " and showID = " + '"' + showID + '"' + " and orderState=" + '"' + "待支付" + '"';
        List<OrderPO> unpayOrderList = jdbcTemplate.query(sql, getOrderPOMapper());
        return unpayOrderList.size() == 0 ? new ArrayList<>() : unpayOrderList;
    }

    @Override
    public OrderPO getUnpayOrder(String orderID) {
        String sql = "select * from orders where orderID = " + '"' + orderID + '"';
        return jdbcTemplate.queryForObject(sql, getOrderPOMapper());
    }

    @Override
    public int cancelOrder(String orderID) {
        String sql = "update orders set orderState = " + '"' + "已失效" + '"' + " where orderID = " + '"' + orderID + '"';
        return jdbcTemplate.update(sql);
    }

    @Override
    public List<OrderPO> getAllOrders(String userID) {
        String sql = "select * from orders where userID = " + '"' + userID + '"';
        List<OrderPO> allOrderList = jdbcTemplate.query(sql, getOrderPOMapper());
        return allOrderList.size() == 0 ? new ArrayList<>() : allOrderList;
    }

    @Override
    public List<OrderPO> getOrdersByState(String userID, String state) {
        String sql = "select * from orders where userID = " + '"' + userID + '"' + " and orderState = " + '"' + state + '"';
        List<OrderPO> orderPOList = jdbcTemplate.query(sql, getOrderPOMapper());

        return orderPOList.size() == 0 ? new ArrayList<>() : orderPOList;
    }

    @Override
    public double refundOrder(String userID, OrderPO orderPO, CouponPO couponPO) {

        double totalConsumption = userDao.getUserTotalConsumption(userID);
        int vipLevel = VIPHelper.getVIPLevel(totalConsumption - orderPO.getTotalPrice());
        int memberPoints = VIPHelper.getVIPMemberPoints(vipLevel, orderPO.getTotalPrice());

        boolean isUsingCoupon = false;
        if (couponPO.getCouponID() != null) {
            isUsingCoupon = true;
        }

        double backMoney = VIPHelper.refund(vipLevel, orderPO.getOrderDate(), orderPO.getTotalPrice());

        //设置用户余额 总消费 会员积分 VIP
        String sql = "update user set balance = balance + " + '"' + backMoney + '"' + " , totalConsumption = totalConsumption - " + '"' + backMoney + '"'
                + ", vipLevel = " + vipLevel + " , memberPoints = memberPoints - " + '"' + memberPoints + '"' + " where userID = " + '"' + userID + '"';

        //设置订单状态
        String sql2 = "update orders set orderState = " + '"' + "已退款" + '"' + ", backMoney = " + '"' + backMoney + '"' + " where orderID = " + '"' + orderPO.getOrderID() + '"';

        //设置优惠券状态
        String sql3 = "";
        if (isUsingCoupon) {
            sql3 = "update coupon set usedTime = " + '"' + '"' + ",state = " + '"' + "待使用" + '"'
                    + " , orderID = " + '"' + '"'
                    + " where couponID=" + '"' + couponPO.getCouponID() + '"';
        }

        //设置演出总收入
        String sql4 = "update shows set totalIncome = totalIncome -" + '"' + backMoney + '"' + "where showID=" + '"' + orderPO.getShowID() + '"';

        //设置座位状态
        //在service层修改

        String[] sqls = null;
        if (isUsingCoupon) {
            sqls = new String[4];
            sqls[0] = sql;
            sqls[1] = sql2;
            sqls[2] = sql3;
            sqls[3] = sql4;
        }else{
            sqls = new String[3];
            sqls[0] = sql;
            sqls[1] = sql2;
            sqls[2] = sql4;
        }

        int[] check = jdbcTemplate.batchUpdate(sqls);

        //如果包括0则这两条插入语句至少有一句不成功，success为false
        boolean success = !Arrays.asList(check).contains(0);
//        成功的话返回退回的金额
        return success ? backMoney : -1;
    }

    @Override
    public int directPurchaseTicket(OrderPO orderPO) {

        double nowPrice = orderPO.getTotalPrice() * orderPO.getDiscount();

        String sql = "insert into orders(orderID,userID,username,venueID,showID,showName,seats,purchaseMethod,ticketsAmount,"
                + "orderState,orderDate,totalPrice,unitPrice,discount) values "
                + "("
                + '"' + orderPO.getOrderID() + '"' + "," + '"' + orderPO.getUserID() + '"' + "," + '"' + orderPO.getUsername() + '"'
                + "," + '"' + orderPO.getVenueID() + '"' + "," + '"' + orderPO.getShowID() + '"' + "," + '"' + orderPO.getShowName() + '"' + "," + '"' + orderPO.getSeat() + '"'
                + "," + '"' + orderPO.getPurchaseMethod() + '"' + "," + '"' + orderPO.getTicketsAmount() + '"' + "," + '"' + orderPO.getOrderState() + '"'
                + "," + '"' + orderPO.getOrderDate() + '"' + "," + '"' + nowPrice + '"' + "," + '"' + orderPO.getUnitPrice() + '"'
                + "," + '"' + orderPO.getDiscount() + '"'
                + ")";

        return jdbcTemplate.update(sql);
    }

    @Override
    public List<OrderPO> getNeedToArrangeSeatOrders(String showID) {
        String sql = "select * from orders where showID = " + '"' + showID + '"' + " and seats = " + '"' + "待分配" + '"' + " and orderState = " + '"' + "已支付" + '"';
        List<OrderPO> orderPOList = jdbcTemplate.query(sql, getOrderPOMapper());
        return orderPOList.size() == 0 ? new ArrayList<>() : orderPOList;
    }

    @Override
    public int updateOrderSeat(String orderID, String seat) {
        String sql = "update orders set seats = " + '"' + seat + '"' + " where orderID = " + '"' + orderID + '"';
        return jdbcTemplate.update(sql);
    }

    private RowMapper<OrderPO> getOrderPOMapper() {
        return (resultSet, i) -> {
            OrderPO po = new OrderPO();
            po.setOrderID(resultSet.getString("orderID"));
            po.setUserID(resultSet.getString("userID"));
            po.setUsername(resultSet.getString("username"));
            po.setVenueID(resultSet.getString("venueID"));
            po.setShowID(resultSet.getString("showID"));
            po.setShowName(resultSet.getString("showName"));
            po.setSeat(resultSet.getString("seats"));
            po.setPurchaseMethod(resultSet.getString("purchaseMethod"));
            po.setTicketsAmount(resultSet.getInt("ticketsAmount"));
            po.setOrderState(resultSet.getString("orderState"));
            po.setOrderDate(resultSet.getString("orderDate"));
            po.setTotalPrice(resultSet.getDouble("totalPrice"));
            po.setUnitPrice(resultSet.getDouble("unitPrice"));
            po.setDiscount(resultSet.getDouble("discount"));
            po.setBackMoney(resultSet.getDouble("backMoney"));

            return po;
        };
    }

}
