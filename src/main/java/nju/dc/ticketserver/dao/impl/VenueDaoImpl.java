package nju.dc.ticketserver.dao.impl;

import com.sun.rowset.internal.Row;
import nju.dc.ticketserver.dao.VenueDao;
import nju.dc.ticketserver.po.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Arrays;

@Repository
public class VenueDaoImpl implements VenueDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int applyRegVenue(RegApplicationPO regApplicationPO) {

        String sql = "insert into regApplication(venueID,venueName,city,venueAddress,area,row,seat,venueInfo,applicationTime,state) values "
                + "("
                + '"' + regApplicationPO.getVenueID() + '"' + "," + '"' + regApplicationPO.getVenueName() + '"' + "," + '"' + regApplicationPO.getCity() + '"' + "," + '"' + regApplicationPO.getVenueAddress() + '"'
                + "," + '"' + regApplicationPO.getArea() + '"' + "," + '"' + regApplicationPO.getRow() + '"' + "," + '"' + regApplicationPO.getSeat() + '"' + "," + '"' + regApplicationPO.getVenueInfo() + '"' + "," + '"' + regApplicationPO.getApplicationTime() + '"' + "," +  '"' + regApplicationPO.getState() + '"'
                + ")";
        return jdbcTemplate.update(sql);
    }

    @Override
    public int applyModifyVenueInfo(ModifyApplicationPO venuePO) {

        VenuePO preVenuePO = venuePO.getPreVenuePO();
        VenuePO postVenuePO = venuePO.getPostVenuePO();

        String sql = "insert into modifyApplication(preVenueName,postVenueName,venueID,preAddress,postAddress,preVenueInfo,postVenueInfo,state,applicationTime) values "
                + "("
                + '"' + preVenuePO.getVenueName() + '"' + "," + '"' + postVenuePO.getVenueName() + '"' + "," + '"' + preVenuePO.getVenueID() + '"' + "," + '"' + preVenuePO.getAddress() + '"'
                + "," + '"' + postVenuePO.getAddress() + '"' + "," + '"' + preVenuePO.getVenueInfo() + '"' + "," + '"' + postVenuePO.getVenueInfo() + '"' + "," + "待审核"
                + "," + '"' + venuePO.getApplicationTime() + '"'
                + ")";
        return jdbcTemplate.update(sql);
    }

    @Override
    public int releaseShowPlan(ShowPO showPO, ShowSeatPO showSeatPO) {
        String sql = "insert into show(showID,category,city,venueID,venueName,performer,showName,showDate,price,description,state) values "
                + "("
                + '"' + showPO.getShowID() + '"' + "," + '"' + showPO.getCategory() + '"' + "," + '"' + showPO.getCity() + '"' + "," + '"' + showPO.getVenueID() + '"'
                + "," + '"' + showPO.getVenueName() + '"' + "," + '"' + showPO.getPerformer() + '"' + "," + '"' + showPO.getShowName() + '"' + "," + '"' + showPO.getShowDate() + '"'
                + "," + '"' + showPO.getPrice() + '"' + "," + '"' + showPO.getDescription() + '"' + "," + '"' + showPO.getState() + '"'
                + ")";

        String sql2 = "insert into showSeat(showID,venueID,area,row,seat) values "
                + "("
                + '"' + showPO.getShowID() + '"' + "," + '"' + showPO.getVenueID() + '"' + "," + '"' + showSeatPO.getArea() + '"' + "," + '"' + showSeatPO.getRow() + '"' + "," + '"' + showSeatPO.getSeat() + '"'
                + ")";

        String[] sqls = new String[2];
        sqls[0] = sql;
        sqls[1] = sql2;

        int[] check = jdbcTemplate.batchUpdate(sqls);

        //如果包括0则这两条插入语句至少有一句不成功，success为false
        boolean success = !Arrays.asList(check).contains(0);
        //成功的话返回1
        return success ? 1 : 0;
    }

    @Override
    public VenuePO getVenuePO(String venueID) {
        String sql = "Select * from venue where venueID = " + '"' + venueID + '"';
        VenuePO po = jdbcTemplate.queryForObject(sql, (resultSet, i) -> {
            VenuePO tempPO = new VenuePO();
            tempPO.setVenueID(venueID);
            tempPO.setVenueName(resultSet.getString("venueName"));
            tempPO.setCity(resultSet.getString("city"));
            tempPO.setAddress(resultSet.getString("address"));
            tempPO.setArea(resultSet.getString("area"));
            tempPO.setRow(resultSet.getInt("row"));
            tempPO.setSeat(resultSet.getString("seat"));
            tempPO.setVenueInfo(resultSet.getString("venueInfo"));
            tempPO.setRegDate(resultSet.getString("regDate"));
            tempPO.setIncome(resultSet.getInt("income"));
            tempPO.setState(resultSet.getString("state"));
            return tempPO;
        });
        return po;
    }
}
