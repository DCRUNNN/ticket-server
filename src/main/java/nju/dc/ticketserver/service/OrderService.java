package nju.dc.ticketserver.service;

import nju.dc.ticketserver.po.CouponPO;
import nju.dc.ticketserver.po.OrderPO;
import nju.dc.ticketserver.po.SeatPO;

import java.util.List;

public interface OrderService {

    OrderPO getOrderPO(String orderID);

    String createOrder(OrderPO orderPO); //返回orderID

    double getUserDiscount(String userID, int vipLevel);

    List<OrderPO> getRecentOrders(String userID);

    List<OrderPO> getUserUnpayOrders(String userID, String showID);

    OrderPO getUnpayOrder(String orderID);

    long getPayLeftTime(String orderID);

    int cancelOrder(String orderID);

    List<OrderPO> getAllOrders(String userID);

    List<OrderPO> getOrdersByState(String userID, String state);

    double refundOrder(String userID, String orderID);

    String directPurchaseTicket(String userID, String showID, String unitPrice, int ticketAmount);

}
