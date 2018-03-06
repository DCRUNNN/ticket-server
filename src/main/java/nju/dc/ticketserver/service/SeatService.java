package nju.dc.ticketserver.service;

import nju.dc.ticketserver.po.SeatPO;

import java.util.List;

public interface SeatService {

    List<SeatPO> getVenueSeatPOs(String venueID);

}
