package nju.dc.ticketserver.service.impl;

import io.swagger.models.auth.In;
import nju.dc.ticketserver.dao.SeatDao;
import nju.dc.ticketserver.po.SeatPO;
import nju.dc.ticketserver.po.ShowSeatPO;
import nju.dc.ticketserver.service.SeatService;
import nju.dc.ticketserver.service.ShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class SeatServiceImpl implements SeatService {

    @Autowired
    private SeatDao seatDao;

    @Override
    public List<SeatPO> getVenueSeatPOs(String venueID) {

        List<SeatPO> seatPOList = new ArrayList<>();

        String seat = seatDao.getVenueSeatInfo(venueID);

        String[] seatByArea = seat.split("/");
        for(int i=0;i<seatByArea.length;i++) {
            String[] seatByRow = seatByArea[i].split(";");
            for (int j = 0; j < seatByRow.length; j++) {
                int maxDiff = Integer.parseInt(seatByRow[j].split(",")[1]);

                String firstSeat = seatByRow[j].split(",")[0];
                String[] concreteSeat = firstSeat.split("-");

                for (int k = 0; k <= maxDiff; k++) {
                    SeatPO po = new SeatPO();
                    po.setSeat(Integer.parseInt(concreteSeat[2]) + k + "");
                    po.setArea(concreteSeat[0]);
                    po.setRow(Integer.parseInt(concreteSeat[1]) + "");
                    po.setVenueID(venueID);
                    seatPOList.add(po);
                }
            }
        }

        return seatPOList;
    }


    @Override
    public List<ShowSeatPO> getShowSeatPOs(String showID) {

        return seatDao.getShowSeatPOList(showID);

    }

    @Override
    public String getShowAreaInfo(String showID) {
        return seatDao.getShowAreaInfo(showID);
    }

    @Override
    public boolean isSeatAvailable(SeatPO seatPO) {
        return false;
    }
}
