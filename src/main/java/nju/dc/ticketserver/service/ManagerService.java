package nju.dc.ticketserver.service;

import nju.dc.ticketserver.po.ManagerPO;
import nju.dc.ticketserver.po.ModifyApplicationPO;
import nju.dc.ticketserver.po.RegApplicationPO;
import nju.dc.ticketserver.po.ShowPO;

import java.util.List;

public interface ManagerService {

    int confirmRegVenue(String venueID);

    int confirmModifyVenueInfo(String venueID);

    ManagerPO getManagerPO(String email);

    List<RegApplicationPO> getVenueRegApplicationPOs();

    List<ModifyApplicationPO> getVenueModifyApplicationPOs();

    int giveMoneyToVenue(String showID, double paymentRatio);

    List<ShowPO> getNeedToPayShows();

}
