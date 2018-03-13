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
}
