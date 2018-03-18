package nju.dc.ticketserver.service;

import nju.dc.ticketserver.po.CouponPO;

import java.util.List;

public interface CouponService {

    List<CouponPO> getAllCouponPOs(String userID);

    CouponPO getCouponPO(String couponID);

    List<CouponPO> getCouponPOByState(String userID, String state);

    int addCouponPO(CouponPO couponPO);

}
