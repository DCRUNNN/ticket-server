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

}