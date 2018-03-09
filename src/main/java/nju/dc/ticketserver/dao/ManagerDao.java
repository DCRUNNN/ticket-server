package nju.dc.ticketserver.dao;

import nju.dc.ticketserver.po.OrderPO;

public interface ManagerDao {

    int confirmRegVenue(String venueID);

    int confirmModifyVenueInfo(String venueID);

    int giveMoneyToVenue(OrderPO orderPO);

}
