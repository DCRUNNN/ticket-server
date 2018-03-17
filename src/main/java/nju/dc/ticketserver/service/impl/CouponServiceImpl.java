package nju.dc.ticketserver.service.impl;

import nju.dc.ticketserver.dao.CouponDao;
import nju.dc.ticketserver.dao.utils.DaoUtils;
import nju.dc.ticketserver.po.CouponPO;
import nju.dc.ticketserver.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CouponServiceImpl implements CouponService {

    @Autowired
    private CouponDao couponDao;

    @Autowired
    private DaoUtils daoUtils;

    @Override
    public List<CouponPO> getAllCouponPOs(String userID) {
        return couponDao.getAllCouponPOs(userID);
    }

    @Override
    public CouponPO getCouponPO(String couponID) {
        return couponDao.getCouponPO(couponID);
    }

    @Override
    public CouponPO getCouponPOByState(String userID, String state) {
        return couponDao.getCouponPOByState(userID, state);
    }

    @Override
    public int addCouponPO(CouponPO couponPO) {
        couponPO.setCouponID(daoUtils.createCouponID());

        //设置截止日期

        return couponDao.addCouponPO(couponPO);
    }

}
