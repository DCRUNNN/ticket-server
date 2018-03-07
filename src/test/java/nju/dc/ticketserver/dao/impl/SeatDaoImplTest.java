package nju.dc.ticketserver.dao.impl;

import nju.dc.ticketserver.dao.SeatDao;
import nju.dc.ticketserver.po.ShowSeatPO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SeatDaoImplTest {

    @Autowired
    private SeatDao seatDao;

    @Test
    public void getShowSeatPOList() throws Exception {
    }

    @Test
    public void getVenueSeatInfo() throws Exception {
    }

    @Test
    public void getShowAreaInfo() throws Exception {
    }

    @Test
    public void isSeatAvailable() throws Exception {
        ShowSeatPO po = new ShowSeatPO();
        po.setShowID("1");
        po.setVenueID("1");
        po.setArea("A");
        po.setRow(1);
        po.setSeat(1);

        System.out.println(seatDao.isSeatAvailable(po));
    }

    @Test
    public void setSeatOccupied() throws Exception {
        ShowSeatPO po = new ShowSeatPO();
        po.setShowID("1");
        po.setVenueID("1");
        po.setArea("A");
        po.setRow(1);
        po.setSeat(1);

        System.out.println(seatDao.setSeatOccupied(po));
    }

}