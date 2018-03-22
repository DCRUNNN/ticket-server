package nju.dc.ticketserver.dao;

import nju.dc.ticketserver.po.*;

import java.util.List;

public interface ManagerDao {

    int confirmRegVenue(String venueID);

    int confirmModifyVenueInfo(String venueID);

    int giveMoneyToVenue(ShowPO showPO, double paymentRatio);

    ManagerPO getManagerPO(String email);

    List<RegApplicationPO> getVenueRegApplicationPOs();

    List<ModifyApplicationPO> getVenueModifyApplicationPOs();

    TicketsFinancePO createTicketsFinancePO(ShowPO showPO, double paymentRatio);

    List<ModifyApplicationPO> getVenueModifyApps(String venueID);

    List<TicketsFinancePO> getTicketFinancePOs();

}
