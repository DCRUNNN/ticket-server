package nju.dc.ticketserver.service;

import nju.dc.ticketserver.po.UserPO;

public interface UserService {


    int addUser(UserPO userPO);

    int activeUser(String activeCode);

    boolean doRegister(UserPO userPO, String email);

    int cancelVIP(String userID);

    double getUserTotalConsumption(String userID);

    int setUserVIP(String userID, int vipLevel);

    UserPO getUserPO(String username);

    UserPO getUserPOByEmail(String email);

    UserPO getUserPOByUserID(String userID);

    UserPO modifyUserPO(String userID, String username, String phoneNumber);

    int modifyUserPassword(String userID, String previousPassword, String newPassword);

    double getUserBalance(String userID);

    double getUserCouponMaxValue(String userID);

    int payOrder(String userID, String orderID, String couponID);

    int spotPurchase(String userID, String orderID);

    int[] getUserOrderTypesInfo(String userID);

    String[][] getOrderPriceInfo(String userID);
}
