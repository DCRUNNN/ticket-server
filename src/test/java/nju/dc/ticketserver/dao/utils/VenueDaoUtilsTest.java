package nju.dc.ticketserver.dao.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class VenueDaoUtilsTest {

    @Autowired
    private VenueDaoUtils venueDaoUtils;

    @Test
    public void createVenueID() throws Exception {
        System.out.println(venueDaoUtils.createVenueID());
    }

}