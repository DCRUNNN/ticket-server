package nju.dc.ticketserver.service;

import nju.dc.ticketserver.po.*;

public interface VenueService {

    int applyRegVenue(RegApplicationPO regApplicationPO);

    int applyModifyVenueInfo(ModifyApplicationPO modifyApplicationPO);

    int releaseShowPlan(ShowPO showPO, ShowSeatPO showSeatPO);

}
