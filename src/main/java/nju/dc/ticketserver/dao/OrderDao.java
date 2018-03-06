package nju.dc.ticketserver.dao;

import nju.dc.ticketserver.po.OrderPO;

public interface OrderDao {

    OrderPO getOrderPO(String orderID);

}
