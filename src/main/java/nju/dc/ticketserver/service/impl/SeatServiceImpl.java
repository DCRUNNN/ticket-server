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

    @Autowired
    private ShowService showService;

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
    public boolean isSeatAvailable(ShowSeatPO showSeatPO) {
        return seatDao.isSeatAvailable(showSeatPO);
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
//         输入： A-2/B-2/C-3/D-5 ,12 , 50/50/60/60/10/10/10/5/5/5/5/5
//         输出：A-01-01,50;A-02-01,50/B-01-01,60;B-02-01,60/C-01-01,10;C-02-01,10;C-03-01,10/D-01-01,5;D-02-01,5;D-03-01,5;D-04-01,5;D-05-01,5
        String[] areas = area.split("/");
        String[] seats = seat.split("/");
        StringBuffer result = new StringBuffer();

        int k = 0;
        for(int i=0;i<areas.length;i++) {
            String areaName = areas[i].split("-")[0];
            int areaRow = Integer.parseInt(areas[i].split("-")[1]);
            for(int j=0;j<areaRow;j++) {
                ++k;
                result.append(areaName).append("-").append(formatInteger(j+1, 2)).append("-01").append(",").append(seats[k-1]);
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


    @Override
    public int setSeatOccupied(ShowSeatPO showSeatPO) {
        return seatDao.setSeatOccupied(showSeatPO);
    }

    @Override
    public int setSeatAvailable(ShowSeatPO showSeatPO) {
        return seatDao.setSeatAvailable(showSeatPO);
    }

    @Override
    public List<ShowSeatPO> getSoldSeat(String showID, String price) {
        return seatDao.getSoldSeat(showID, showService.getAreaByPrice(showID, price));
    }

    private String formatInteger(int i, int length) {
        return String.format("%0" + length + "d", i);
    }

}
