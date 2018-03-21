package nju.dc.ticketserver.dao;

import nju.dc.ticketserver.po.*;

import java.util.List;
import java.util.Map;

public interface VenueDao {

    int applyRegVenue(RegApplicationPO regApplicationPO);

    int applyModifyVenueInfo(ModifyApplicationPO venuePO);

    int releaseShowPlan(ShowPO showPO, List<ShowSeatPO> showSeatPOList);

    VenuePO getVenuePO(String venueID);

    List<OrderPO> getVenueRecentOrders(String venueID);

    int checkTicket(String orderID);

    List<Map<String, Object>> getHotShows(String venueID);

    List<OrderPO> getVenueAllOrders(String venueID);

}
