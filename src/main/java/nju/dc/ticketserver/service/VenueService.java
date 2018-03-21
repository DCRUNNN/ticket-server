package nju.dc.ticketserver.service;

import nju.dc.ticketserver.po.*;

import java.util.List;

public interface VenueService {

    String applyRegVenue(RegApplicationPO regApplicationPO);

    int applyModifyVenueInfo(ModifyApplicationPO modifyApplicationPO);

    int releaseShowPlan(ShowPO showPO, List<ShowSeatPO> showSeatPOList);

    VenuePO getVenuePO(String venueID);

    List<OrderPO> getVenueRecentOrders(String venueID);

    int checkTicket(String orderID, String venueID);

    String arrangeSeat(String showID);

    String[][] getHotShows(String venueID);

    int[] getUserPurchaseMethod(String venueID);
}
