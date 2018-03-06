package nju.dc.ticketserver.service;

import nju.dc.ticketserver.po.OrderPO;

public interface OrderService {

    OrderPO getOrderPO(String orderID);

}
