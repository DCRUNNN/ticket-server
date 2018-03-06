package nju.dc.ticketserver.dao.impl;

import nju.dc.ticketserver.dao.OrderDao;
import nju.dc.ticketserver.po.OrderPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

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
            tempPO.setOrderDate(resultSet.getString("orderDate"));
            tempPO.setTotalPrice(resultSet.getDouble("totalPrice"));
            tempPO.setUnitPrice(resultSet.getDouble("unitPrice"));

            //处理seat

            return tempPO;
        });
        return po;
    }
}
