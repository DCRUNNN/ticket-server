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
        System.out.println(seatService.convertSeatInfo("A-3/B-5/C-4", 12, "10/10/10/20/20/20/20/20/30/30/30/30"));
        System.out.println(seatService.convertSeatInfo("A-3/B-4/C-5/D-2", 14, "10/10/10/20/20/20/20/30/30/30/30/30/40/40"));
        System.out.println(seatService.convertSeatInfo("A-6/B-4/C-8", 18, "10/10/10/10/10/10/20/20/20/20/30/30/30/30/30/30/40/40"));
        System.out.println(seatService.convertSeatInfo("A-3/B-4/C-2", 9, "10/10/10/20/20/20/20/30/30"));
        System.out.println(seatService.convertSeatInfo("A-3/B-2/C-5/D-4", 14, "10/10/10/20/20/30/30/30/30/30/40/40/40/40"));
        System.out.println(seatService.convertSeatInfo("A-4/B-3/C-5", 12, "10/10/10/10/20/20/20/30/30/30/30/30"));
        System.out.println(seatService.convertSeatInfo("A-6/B-5/C-4", 15, "10/10/10/10/10/10/20/20/20/20/20/30/30/30/30"));
    }

    @Test
    public void havaEnoughSeat() throws Exception {
        System.out.println(seatService.haveEnoughSeat("show-000004", "A", 4));
    }

    @Test
    public void getAvailableSeat() throws Exception {
        System.out.println(seatService.getAvailableSeat("show-000004", "A"));
    }

}