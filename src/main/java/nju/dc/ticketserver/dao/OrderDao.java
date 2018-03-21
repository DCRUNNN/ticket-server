package nju.dc.ticketserver.dao;

import nju.dc.ticketserver.po.CouponPO;
import nju.dc.ticketserver.po.OrderPO;

import java.util.List;

public interface OrderDao {

    OrderPO getOrderPO(String orderID);

    int createOrder(OrderPO orderPO);

    List<OrderPO> getRecentOrders(String userID);

    List<OrderPO> getUserUnpayOrders(String userID, String showID);

    List<OrderPO> getAllOrders(String userID);

    List<OrderPO> getOrdersByState(String userID, String state);

    OrderPO getUnpayOrder(String orderID);

    int cancelOrder(String orderID);

    double refundOrder(String userID, OrderPO orderPO, CouponPO couponPO);

    int directPurchaseTicket(OrderPO orderPO);

    List<OrderPO> getNeedToArrangeSeatOrders(String showID);

    int updateOrderSeat(String orderID, String seat);

}
