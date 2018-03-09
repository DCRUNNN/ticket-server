package nju.dc.ticketserver.dao.impl;

import nju.dc.ticketserver.dao.OrderDao;
import nju.dc.ticketserver.po.OrderPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Arrays;

@Repository
public class OrderDaoImpl implements OrderDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public OrderPO getOrderPO(String orderID) {
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

            String date = resultSet.getString("orderDate");
            tempPO.setOrderDate(date.substring(0, date.length() - 2));

            tempPO.setTotalPrice(resultSet.getDouble("totalPrice"));
            tempPO.setUnitPrice(resultSet.getDouble("unitPrice"));
            tempPO.setDiscount(resultSet.getDouble("discount"));

            //处理seat
            tempPO.setSeat(resultSet.getString("seat"));

            return tempPO;
        });
        return po;
    }


    @Override
    public int createOrder(OrderPO orderPO) {
        String sql = "insert into orders(orderID,userID,username,venueID,showID,seats,purchaseMethod,ticketsAmount,"
                + "orderState,orderDate,totalPrice,unitPrice,discount) values "
                + "("
                + '"' + orderPO.getOrderID() + '"' + "," + '"' + orderPO.getUserID() + '"' + "," + '"' + orderPO.getUsername() + '"'
                + "," + '"' + orderPO.getVenueID() + '"' + "," + '"' + orderPO.getShowID() + '"' + "," + '"' + orderPO.getSeat() + '"'
                + "," + '"' + orderPO.getPurchaseMethod() + '"' + "," + '"' + orderPO.getTicketsAmount() + '"' + "," + '"' + orderPO.getOrderState() + '"'
                + "," + '"' + orderPO.getOrderDate() + '"' + "," + '"' + orderPO.getTotalPrice() + '"' + "," + '"' + orderPO.getUnitPrice() + '"'
                + "," + '"' + orderPO.getDiscount() + '"'
                + ")";

        String sql2 = "update user set totalConsumption = totalConsumption + " + orderPO.getTotalPrice() + " where userID = " + '"' + orderPO.getUserID() + '"';

        //VIPLevel

        String[] sqls = new String[2];
        sqls[0] = sql;
        sqls[1] = sql2;

        int[] check = jdbcTemplate.batchUpdate(sqls);

        //如果包括0则这两条插入语句至少有一句不成功，success为false
        boolean success = !Arrays.asList(check).contains(0);
        //成功的话返回1
        return success ? 1 : 0;

    }


}
