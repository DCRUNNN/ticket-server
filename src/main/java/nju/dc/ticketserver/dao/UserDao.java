package nju.dc.ticketserver.dao;

import nju.dc.ticketserver.po.UserPO;

public interface UserDao {

    int addUser(UserPO userPO);

    int deleteUser(UserPO userPO);

    int activeUser(String activeCode);

    int cancelVIP(String userID);

    String getUserID(String username);

    String getUsername(String userID);

    UserPO getUserPO(String username);

    double getUserTotalConsumption(String userID);

    int setUserVIP(String userID, int vipLevel);
}
