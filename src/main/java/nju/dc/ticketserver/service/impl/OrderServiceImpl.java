package nju.dc.ticketserver.service.impl;

import nju.dc.ticketserver.dao.OrderDao;
import nju.dc.ticketserver.po.OrderPO;
import nju.dc.ticketserver.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Override
    public OrderPO getOrderPO(String orderID) {
        return orderDao.getOrderPO(orderID);
    }



}
