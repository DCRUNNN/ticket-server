package nju.dc.ticketserver.service.impl;

import nju.dc.ticketserver.service.SeatService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SeatServiceImplTest {

    @Autowired
    private SeatService seatService;
    @Test
    public void getVenueSeatInfo() throws Exception {
        System.out.println(seatService.getVenueSeatPOs("1"));
    }

    @Test
    public void convertSeatInfo() throws Exception {
        System.out.println(seatService.convertSeatInfo("A-3/B-5/C-4", 12, "A-01-01,10;A-02-01,20;A-03-01,30/B-04-01,30;B-05-01,40;B-06-01,50;B-07-01,60;B-08-01,70/C-09-01,50;C-10-01,60;C-11-01,70;C-12-01,80"));
    }

}