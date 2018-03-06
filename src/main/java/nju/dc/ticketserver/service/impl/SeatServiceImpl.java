package nju.dc.ticketserver.service.impl;

import io.swagger.models.auth.In;
import nju.dc.ticketserver.po.SeatPO;
import nju.dc.ticketserver.service.SeatService;
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
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<SeatPO> getVenueSeatPOs(String venueID) {

        List<SeatPO> seatPOList = new ArrayList<>();

        String seat = getVenueSeatInfo(venueID);

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
        System.out.println(seatPOList.size());

        System.out.println(seatPOList);

        return null;
    }

    private String getVenueSeatInfo(String venueID) {
//        String sql = "select row,seat from seat where venueID = " + '"' + venueID + '"';
//        RowMapper mapper = new RowMapper() {
//            public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
//                return rs.getInt("row") + "#" + rs.getString("seat");
//            }
//        };
//        return (String)jdbcTemplate.query(sql, mapper).get(0);

        String sql = "select seat from seat where venueID = ? ";
        return jdbcTemplate.queryForObject(sql, new Object[] { venueID }, String.class);

    }
}
