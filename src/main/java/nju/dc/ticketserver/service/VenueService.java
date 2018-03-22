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

    String[][] getShowsIncomeInfo(String venueID);

    int[] getVenueOrdersStateInfo(String venueID);

    List<TicketsFinancePO> getVenueFinanceInfo(String venueID);

    int setShowGoing(String showID);

    int setShowDone(String showID);

    int modifyUserPassword(String venueID, String previousPassword, String newPassword);
}
