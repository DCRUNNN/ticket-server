package nju.dc.ticketserver.dao;

import nju.dc.ticketserver.po.CouponPO;
import nju.dc.ticketserver.po.OrderPO;
import nju.dc.ticketserver.po.UserPO;

import java.util.List;

public interface UserDao {

    int addUser(UserPO userPO);

    int deleteUser(UserPO userPO);

    int activeUser(String activeCode);

    int cancelVIP(String userID);

    String getUserID(String username);

    String getUsername(String userID);

    UserPO getUserPO(String username);

    UserPO getUserPOByUserID(String userID);

    UserPO getUserPOByEmail(String email);

    double getUserTotalConsumption(String userID);

    int setUserVIP(String userID, int vipLevel);

    UserPO modifyUserPO(String userID, String username, String phoneNumber);

    int modifyPassword(String userID, String newPassword);

    double getUserBalance(String userID);

    double getUserCouponMaxValue(String userID);

    int payOrder(String userID, OrderPO orderPO, CouponPO couponPO);

    int spotPurchase(String userID, OrderPO orderPO);

    List<UserPO> getUserPOList();

}
