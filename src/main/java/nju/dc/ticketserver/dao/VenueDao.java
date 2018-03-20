package nju.dc.ticketserver.dao;

import nju.dc.ticketserver.po.*;

import java.util.List;

public interface VenueDao {

    int applyRegVenue(RegApplicationPO regApplicationPO);

    int applyModifyVenueInfo(ModifyApplicationPO venuePO);

    int releaseShowPlan(ShowPO showPO, List<ShowSeatPO> showSeatPOList);

    VenuePO getVenuePO(String venueID);

    List<OrderPO> getVenueRecentOrders(String venueID);

    int checkTicket(String orderID);


}
