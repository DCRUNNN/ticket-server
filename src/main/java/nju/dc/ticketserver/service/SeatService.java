package nju.dc.ticketserver.service;

import nju.dc.ticketserver.po.SeatPO;
import nju.dc.ticketserver.po.ShowPO;
import nju.dc.ticketserver.po.ShowSeatPO;

import java.util.List;

public interface SeatService {

    List<SeatPO> getVenueSeatPOs(String venueID);

    List<ShowSeatPO> getShowSeatPOs(String showID);

    List<ShowSeatPO> convertShowSeatInfo(ShowPO showPO, String area, int row, String seat);

    String convertSeatInfo(String area, int row, String seat);

    String getShowAreaInfo(String showID);

    boolean isSeatAvailable(ShowSeatPO showSeatPO);

    int setSeatOccupied(ShowSeatPO showSeatPO);

    int setSeatAvailable(ShowSeatPO showSeatPO);

    List<ShowSeatPO> getSoldSeat(String showID, String price);

    boolean haveEnoughSeat(String showID, String area, int ticketAmount);

}
