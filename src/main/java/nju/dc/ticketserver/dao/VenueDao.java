package nju.dc.ticketserver.dao;

import nju.dc.ticketserver.po.*;

public interface VenueDao {

    int applyRegVenue(RegApplicationPO regApplicationPO);

    int applyModifyVenueInfo(ModifyApplicationPO venuePO);

    int releaseShowPlan(ShowPO showPO, ShowSeatPO showSeatPO);

    VenuePO getVenuePO(String venueID);


}
