package nju.dc.ticketserver.service.impl;

import nju.dc.ticketserver.dao.CouponDao;
import nju.dc.ticketserver.dao.utils.DaoUtils;
import nju.dc.ticketserver.po.CouponPO;
import nju.dc.ticketserver.service.CouponService;
import nju.dc.ticketserver.utils.VIPHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
    public List<CouponPO> getCouponPOByState(String userID, String state) {
        return couponDao.getCouponPOByState(userID, state);
    }

    @Override
    public int addCouponPO(CouponPO couponPO) {
        couponPO.setCouponID(daoUtils.createCouponID());
        couponPO.setValue(VIPHelper.memberPointsToCoupon(couponPO.getUsedMemberPoint()));
        couponPO.setState("待使用");
        couponPO.setDescription("使用积分兑换的优惠券");
        //设置截止日期
        couponPO.setLastTerm(getLastTerm());
        couponPO.setExchangeTime(daoUtils.setSignUpDate());

        return couponDao.addCouponPO(couponPO);
    }

    private String getLastTerm() {
        Calendar c = Calendar.getInstance();//获得一个日历的实例
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date date = new Date();
        try{
            date = sdf.parse(sdf.format(date));//当前日期
        }catch(Exception e){
            e.printStackTrace();
        }
        c.setTime(date);//设置日历时间
        c.add(Calendar.MONTH,2);//在日历的月份上增加2个月
        return sdf.format(c.getTime());//得到2个月后的日期
    }

}
