package nju.dc.ticketserver.service.impl;

import nju.dc.ticketserver.dao.OrderDao;
import nju.dc.ticketserver.po.OrderPO;
import nju.dc.ticketserver.po.SeatPO;
import nju.dc.ticketserver.service.OrderService;
import nju.dc.ticketserver.service.SeatService;
import nju.dc.ticketserver.utils.VIPHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private SeatService seatService;

    @Override
    public OrderPO getOrderPO(String orderID) {
        return orderDao.getOrderPO(orderID);
    }


    @Override
    public int createOrder(OrderPO orderPO) {
        return 0;
    }

    @Override
    public double getUserDiscount(String userID, int vipLevel) {
        return VIPHelper.getVIPDiscount(vipLevel);
    }

}
