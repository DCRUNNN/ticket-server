package nju.dc.ticketserver.dao;

import nju.dc.ticketserver.po.CouponPO;

import java.util.List;

public interface CouponDao {

    List<CouponPO> getAllCouponPOs(String userID);

    CouponPO getCouponPO(String couponID);

    CouponPO getCouponPOByOrderID(String orderID);

    List<CouponPO> getCouponPOByState(String userID, String state);

    int addCouponPO(CouponPO couponPO);

    boolean isUsingCoupon(String orderID);

}
