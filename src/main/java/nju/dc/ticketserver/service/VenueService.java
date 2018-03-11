package nju.dc.ticketserver.service;

import nju.dc.ticketserver.po.*;

import java.util.List;

public interface VenueService {

    String applyRegVenue(RegApplicationPO regApplicationPO);

    int applyModifyVenueInfo(ModifyApplicationPO modifyApplicationPO);

    int releaseShowPlan(ShowPO showPO, List<ShowSeatPO> showSeatPOList);

}
