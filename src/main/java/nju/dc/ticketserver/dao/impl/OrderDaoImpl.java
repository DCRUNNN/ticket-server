package nju.dc.ticketserver.dao.impl;

import nju.dc.ticketserver.dao.OrderDao;
import nju.dc.ticketserver.po.OrderPO;
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
        String sql = "insert into orders(orderID,userID,username,venueID,showID,showName,seats,purchaseMethod,ticketsAmount,"
                + "orderState,orderDate,totalPrice,unitPrice,discount) values "
                + "("
                + '"' + orderPO.getOrderID() + '"' + "," + '"' + orderPO.getUserID() + '"' + "," + '"' + orderPO.getUsername() + '"'
                + "," + '"' + orderPO.getVenueID() + '"' + "," + '"' + orderPO.getShowID() + '"' + "," + '"' + orderPO.getShowName() + '"' + "," + '"' + orderPO.getSeat() + '"'
                + "," + '"' + orderPO.getPurchaseMethod() + '"' + "," + '"' + orderPO.getTicketsAmount() + '"' + "," + '"' + orderPO.getOrderState() + '"'
                + "," + '"' + orderPO.getOrderDate() + '"' + "," + '"' + orderPO.getTotalPrice() + '"' + "," + '"' + orderPO.getUnitPrice() + '"'
                + "," + '"' + orderPO.getDiscount() + '"'
                + ")";


        return jdbcTemplate.update(sql);
    }

    @Override
    public List<OrderPO> getRecentOrders(String userID) {
        String sql = "select * from orders where userID = " + '"' + userID + '"' + " order by orderID DESC limit 6";
        List<OrderPO> recentOrderList = jdbcTemplate.query(sql, getOrderPOMapper());
        return recentOrderList.size() == 0 ? new ArrayList<>() : recentOrderList;
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
            return po;
        };
    }

}
