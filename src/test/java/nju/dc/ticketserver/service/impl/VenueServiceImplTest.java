package nju.dc.ticketserver.service.impl;

import nju.dc.ticketserver.service.VenueService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class VenueServiceImplTest {

    @Autowired
    private VenueService venueService;

    @Test
    public void applyRegVenue() throws Exception {
    }

    @Test
    public void applyModifyVenueInfo() throws Exception {
    }

    @Test
    public void releaseShowPlan() throws Exception {
    }

    @Test
    public void getVenuePO() throws Exception {
        System.out.println(venueService.getVenuePO("0000001"));

    }

}