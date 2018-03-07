package nju.dc.ticketserver.service;

import nju.dc.ticketserver.po.OrderPO;
import nju.dc.ticketserver.po.SeatPO;

import java.util.List;

public interface OrderService {

    OrderPO getOrderPO(String orderID);

    int createOrder(OrderPO orderPO);

    double getUserDiscount(String userID, int vipLevel);
}
