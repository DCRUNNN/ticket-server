package nju.dc.ticketserver.service.impl;

import io.swagger.models.auth.In;
import nju.dc.ticketserver.dao.SeatDao;
import nju.dc.ticketserver.po.SeatPO;
import nju.dc.ticketserver.po.ShowPO;
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

    @Override
    public List<ShowSeatPO> convertShowSeatInfo(ShowPO showPO, String area, int row, String seat) {
        List<ShowSeatPO> showSeatPOList = new ArrayList<>();

        String showSeatInfo = convertSeatInfo(area, row, seat);

        String[] seatByArea = showSeatInfo.split("/");

        for(int i=0;i<seatByArea.length;i++) {
            String[] seatByRow = seatByArea[i].split(";");
            for (int j = 0; j < seatByRow.length; j++) {
                int maxDiff = Integer.parseInt(seatByRow[j].split(",")[1]);

                String firstSeat = seatByRow[j].split(",")[0];
                String[] concreteSeat = firstSeat.split("-");

                for (int k = 0; k <= maxDiff; k++) {
                    ShowSeatPO po = new ShowSeatPO();
                    po.setSeat(Integer.parseInt(concreteSeat[2]) + k);
                    po.setArea(concreteSeat[0]);
                    po.setRow(Integer.parseInt(concreteSeat[1]));
                    po.setVenueID(showPO.getVenueID());
                    po.setShowID(showPO.getShowID());
                    po.setState("可售");
                    showSeatPOList.add(po);
                }
            }
        }
        return showSeatPOList;
    }

    @Override
    public String convertSeatInfo(String area, int row, String seat) {
//           输入： A-2/B-2/C-3/D-5 ,12 , 50/50/60/60/10/10/10/5/5/5/5/5
//           输出：A-01-01,50;A-02-01,50/B-03-01,60;B-04-01,60/C-05-01,10;C-06-01,10;C-07-01,10/D-08-01,10;D-09-01,5;D-10-01,5;D-11-01,5;D-12-01,5
        String[] areas = area.split("/");
        String[] seats = seat.split("/");
        StringBuffer result = new StringBuffer();

        int k = 0;
        for(int i=0;i<areas.length;i++) {
            String areaName = areas[i].split("-")[0];
            int areaRow = Integer.parseInt(areas[i].split("-")[1]);
            for(int j=0;j<areaRow;j++) {
                result.append(areaName).append("-").append(formatInteger(++k, 2)).append("-01").append(",").append(seats[k-1]);
                if (j != areaRow - 1) {
                    result.append(";");
                } else {
                    result.append("/");
                }
            }
        }
        result.deleteCharAt(result.length() - 1);
        return result.toString();
    }

    private String formatInteger(int i, int length) {
        return String.format("%0" + length + "d", i);
    }

}
