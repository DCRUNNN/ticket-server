package nju.dc.ticketserver.dao;

import nju.dc.ticketserver.po.ManagerPO;
import nju.dc.ticketserver.po.ModifyApplicationPO;
import nju.dc.ticketserver.po.OrderPO;
import nju.dc.ticketserver.po.RegApplicationPO;

import java.util.List;

public interface ManagerDao {

    int confirmRegVenue(String venueID);

    int confirmModifyVenueInfo(String venueID);

    int giveMoneyToVenue(OrderPO orderPO);

    ManagerPO getManagerPO(String email);

    List<RegApplicationPO> getVenueRegApplicationPOs();

    List<ModifyApplicationPO> getVenueModifyApplicationPOs();

}
