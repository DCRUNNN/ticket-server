package nju.dc.ticketserver.service.impl;

import nju.dc.ticketserver.dao.UserDao;
import nju.dc.ticketserver.dao.utils.DaoUtils;
import nju.dc.ticketserver.po.CouponPO;
import nju.dc.ticketserver.po.OrderPO;
import nju.dc.ticketserver.po.UserPO;
import nju.dc.ticketserver.service.CouponService;
import nju.dc.ticketserver.service.OrderService;
import nju.dc.ticketserver.service.UserService;
import nju.dc.ticketserver.utils.ActiveCodeHelper;
import nju.dc.ticketserver.utils.EncryptHelper;
import nju.dc.ticketserver.utils.MailHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private OrderService orderService;

    @Autowired
    private CouponService couponService;

    @Autowired
    private DaoUtils daoUtils;

    @Override
    public int addUser(UserPO userPO) {
        String password = userPO.getPassword();
        password = EncryptHelper.getShaEncryption(password);
        userPO.setPassword(password);
        userPO.setUserID(daoUtils.createUserID());
        return userDao.addUser(userPO);
    }

    @Override
    public int activeUser(String activeCode) {
        return userDao.activeUser(activeCode);
    }

    @Override
    public boolean doRegister(UserPO userPO, String email) {
        // 这里可以验证各字段是否为空

        //利用正则表达式（可改进）验证邮箱是否符合邮箱的格式
        if(!email.matches("^\\w+@(\\w+\\.)+\\w+$")){
            return false;
        }
        //生成激活码
        String activeCode= ActiveCodeHelper.generateUniqueCode();

        userPO.setActiveCode(activeCode);
        userPO.setState("未激活");
        userPO.setVIP(true);
        userPO.setMemberPoints(0);

        //保存成功则通过线程的方式给用户发送一封邮件
        if(addUser(userPO)>0){
            new Thread(new MailHelper(email, activeCode)).start();;
            return true;
        }
        return false;
    }

    @Override
    public int cancelVIP(String userID) {
        return userDao.cancelVIP(userID);
    }

    @Override
    public double getUserTotalConsumption(String userID) {
        return userDao.getUserTotalConsumption(userID);
    }

    @Override
    public int setUserVIP(String userID, int vipLevel) {

        return userDao.setUserVIP(userID, vipLevel);
    }

    @Override
    public UserPO getUserPO(String username) {
        return userDao.getUserPO(username);
    }

    @Override
    public UserPO getUserPOByEmail(String email) {
        return userDao.getUserPOByEmail(email);
    }

    @Override
    public UserPO getUserPOByUserID(String userID) {
        return userDao.getUserPOByUserID(userID);
    }

    @Override
    public UserPO modifyUserPO(String userID, String username, String phoneNumber) {
        return userDao.modifyUserPO(userID, username, phoneNumber);
    }

    @Override
    public int modifyUserPassword(String userID, String previousPassword, String newPassword) {

        UserPO checkUserPO = getUserPOByUserID(userID);
        boolean equal = EncryptHelper.checkPassword(previousPassword, checkUserPO.getPassword());
        if(equal){
            newPassword = EncryptHelper.getShaEncryption(newPassword);
            return userDao.modifyPassword(userID, newPassword);
        }else{
            return -1;
        }
    }

    @Override
    public double getUserBalance(String userID) {
        return userDao.getUserBalance(userID);
    }

    @Override
    public double getUserCouponMaxValue(String userID) {
        return userDao.getUserCouponMaxValue(userID);
    }

    @Override
    public int payOrder(String userID, String orderID, String couponID) {
        OrderPO orderPO = orderService.getOrderPO(orderID);
        CouponPO couponPO = couponService.getCouponPO(couponID);
        return userDao.payOrder(userID, orderPO, couponPO);
    }
}
