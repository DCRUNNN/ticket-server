package nju.dc.ticketserver.dao.impl;

import nju.dc.ticketserver.dao.ManagerDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class ManagerDaoImplTest {

    @Autowired
    private ManagerDao managerDao;

    @Test
    public void giveMoneyToVenue() throws Exception {
        managerDao.giveMoneyToVenue(null);
    }

}