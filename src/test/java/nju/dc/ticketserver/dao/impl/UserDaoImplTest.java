package nju.dc.ticketserver.dao.impl;

import nju.dc.ticketserver.dao.UserDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserDaoImplTest {
    @Autowired
    private UserDao userDao;

    @Test
    public void getUserBalance() throws Exception {
        System.out.println(userDao.getUserBalance("user-0001"));
    }

    @Test
    public void getUserCouponMaxValue() throws Exception {
        System.out.println(userDao.getUserCouponMaxValue("user-0001"));
    }



}