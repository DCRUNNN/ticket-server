package nju.dc.ticketserver.service;

import nju.dc.ticketserver.po.*;

import java.util.List;

public interface ManagerService {

    int confirmRegVenue(String venueID);

    int confirmModifyVenueInfo(String venueID);

    ManagerPO getManagerPO(String email);

    List<RegApplicationPO> getVenueRegApplicationPOs();

    List<ModifyApplicationPO> getVenueModifyApplicationPOs();

    int giveMoneyToVenue(String showID, double paymentRatio);

    List<ShowPO> getNeedToPayShows();

    List<ModifyApplicationPO> getVenueModifyApps(String venueID);

    String[][] getVenuesIncomeInfo();

    String[][] getVIPTypeInfo();

    int[] getVIPLevelInfo();

    List<TicketsFinancePO> getTicketFinancePOs();

}
