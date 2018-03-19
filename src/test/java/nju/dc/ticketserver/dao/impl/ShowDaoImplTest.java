package nju.dc.ticketserver.dao.impl;

import nju.dc.ticketserver.dao.ShowDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShowDaoImplTest {

    @Autowired
    private ShowDao showDao;

    @Test
    public void getNeedToPayShows() throws Exception {
        System.out.println(showDao.getNeedToPayShows());

    }

}