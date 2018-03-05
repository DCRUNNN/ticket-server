package nju.dc.ticketserver.service;

import nju.dc.ticketserver.po.UserPO;

public interface UserService {


    int addUser(UserPO userPO);

    int activeUser(String activeCode);

    boolean doRegister(UserPO userPO, String email);

}
