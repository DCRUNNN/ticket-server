package nju.dc.ticketserver.dao.impl;

import nju.dc.ticketserver.dao.SeatDao;
import nju.dc.ticketserver.po.SeatPO;
import nju.dc.ticketserver.po.ShowSeatPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

@Repository
public class SeatDaoImpl implements SeatDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<ShowSeatPO> getShowSeatPOList(String showID) {

        String sql = "select * from showSeat where showID = " + '"' + showID + '"' ;

        List<ShowSeatPO> showSeatPOList = jdbcTemplate.query(sql, getShowSeatMapper());

        return showSeatPOList.size() == 0 ? new ArrayList<>() : showSeatPOList;

    }

    @Override
    public String getVenueSeatInfo(String venueID) {
        String sql = "select seat from seat where venueID = ? ";
        return jdbcTemplate.queryForObject(sql, new Object[] { venueID }, String.class);
    }

    @Override
    public String getShowAreaInfo(String showID) {
        String sql = "select area from showSeat where showID = ? ";
        return jdbcTemplate.queryForObject(sql, new Object[] { showID }, String.class);
    }

    @Override
    public boolean isSeatAvailable(ShowSeatPO showSeatPO) {
        String sql = "select state from showSeat where showID =? and venueID=? and area=? and row=? and seat=?";
        String state = jdbcTemplate.queryForObject(sql, new Object[]{showSeatPO.getShowID(), showSeatPO.getVenueID(), showSeatPO.getArea(), showSeatPO.getRow(), showSeatPO.getSeat()}, String.class);
        return !state.equals("可售") ? false : true;
    }

    @Override
    public int setSeatOccupied(ShowSeatPO showSeatPO) {
        String sql = "update showSeat set state= ? where showID =? and venueID=? and area=? and row=? and seat=?";
        int[] types = {Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.INTEGER, Types.INTEGER};
        return jdbcTemplate.update(sql, new Object[]{"已售", showSeatPO.getShowID(), showSeatPO.getVenueID(), showSeatPO.getArea(), showSeatPO.getRow(), showSeatPO.getSeat()}, types);
    }


    @Override
    public List<ShowSeatPO> getSoldSeat(String showID, String area) {
        String sql = "select * from showSeat where showID = " + '"' + showID + '"' + " and state = " + '"' + "已售" + '"' + " and area = " + '"' + area + '"';
        return jdbcTemplate.query(sql, getShowSeatMapper());
    }

    private RowMapper<ShowSeatPO> getShowSeatMapper() {
        return (resultSet, i) -> {
            ShowSeatPO po = new ShowSeatPO();
            po.setShowID(resultSet.getString("showID"));
            po.setVenueID(resultSet.getString("venueID"));
            po.setArea(resultSet.getString("area"));
            po.setRow(resultSet.getInt("row"));
            po.setSeat(resultSet.getInt("seat"));
            po.setState(resultSet.getString("state"));
            return po;
        };
    }
}
