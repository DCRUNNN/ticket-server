package nju.dc.ticketserver.dao;

import nju.dc.ticketserver.po.CouponPO;

import java.util.List;

public interface CouponDao {

    List<CouponPO> getAllCouponPOs(String userID);

    CouponPO getCouponPO(String couponID);

    List<CouponPO> getCouponPOByState(String userID, String state);

    int addCouponPO(CouponPO couponPO);

}
