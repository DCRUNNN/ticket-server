package nju.dc.ticketserver.dao;

import nju.dc.ticketserver.po.OrderPO;

import java.util.List;

public interface OrderDao {

    OrderPO getOrderPO(String orderID);

    int createOrder(OrderPO orderPO);

    List<OrderPO> getRecentOrders(String userID);

}
