package nju.dc.ticketserver.dao;

import nju.dc.ticketserver.po.SeatPO;
import nju.dc.ticketserver.po.ShowSeatPO;

import java.util.List;

public interface SeatDao {

    List<ShowSeatPO> getShowSeatPOList(String showID);

    String getVenueSeatInfo(String venueID);

    String getShowAreaInfo(String showID);

    boolean isSeatAvailable(ShowSeatPO showSeatPO);

    int setSeatOccupied(ShowSeatPO showSeatPO);

}
