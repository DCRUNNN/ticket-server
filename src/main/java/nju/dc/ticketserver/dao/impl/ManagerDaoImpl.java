package nju.dc.ticketserver.dao.impl;

import nju.dc.ticketserver.dao.ManagerDao;
import nju.dc.ticketserver.dao.VenueDao;
import nju.dc.ticketserver.po.ModifyApplicationPO;
import nju.dc.ticketserver.po.RegApplicationPO;
import nju.dc.ticketserver.po.VenuePO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Arrays;

@Repository
public class ManagerDaoImpl implements ManagerDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int confirmRegVenue(String venueID) {
        RegApplicationPO regApplicationPO = getRegApplicationPO(venueID);

        String sql = "insert into venue(venueID,venueName,city,address,area,row,seat,venueInfo,regDate,income,state) values "
                + "("
                + venueID + "," + '"' + regApplicationPO.getVenueName() + '"' + "," + '"' + regApplicationPO.getCity() + '"' + "," + '"' + regApplicationPO.getVenueAddress() + '"'
                + "," + '"' + regApplicationPO.getArea() + '"' + "," + '"' + regApplicationPO.getRow() + '"' + "," + '"' + regApplicationPO.getSeat() + '"'
                + "," + '"' + regApplicationPO.getVenueInfo() + '"' + "," + '"' + regApplicationPO.getApplicationTime() + '"' + "," + 0
                + "," + '"' + "注册成功" + '"'
                + ")";

        String sql2 = "insert into seat(venueID,area,row,seat) values "
                +"("
                + venueID + "," + '"' + regApplicationPO.getArea() + '"' + "," + '"' + regApplicationPO.getRow() + '"' + "," + '"' + regApplicationPO.getSeat() + '"'
                + ")";

        String sql3 = "update regApplication set state = " + '"' + "审核通过" + '"' + " where venueID = " + '"' + venueID + '"';

        String[] sqls = new String[3];
        sqls[0] = sql;
        sqls[1] = sql2;
        sqls[2] = sql3;

        int[] check = jdbcTemplate.batchUpdate(sqls);

        //如果包括0则这两条插入语句至少有一句不成功，success为false
        boolean success = !Arrays.asList(check).contains(0);
        //成功的话返回1
        return success ? 1 : 0;
    }

    @Override
    public int confirmModifyVenueInfo(String venueID) {

        VenuePO venuePO = getPostVenuePO(venueID);

        String sql = "update venue set venueName = " + '"' + venuePO.getVenueName() + '"' + "," + "address = " + '"' + venuePO.getAddress() + '"'
                + "," + "area = " + '"' + venuePO.getArea() + '"' + "," + "venueInfo = " + '"' + venuePO.getVenueInfo() + '"';

        String sql2 = "update modifyApplication set state = " + '"' + "审核通过" + '"' + " where venueID = " + '"' + venueID + '"';

        String[] sqls = new String[2];
        sqls[0] = sql;
        sqls[1] = sql2;

        int[] check = jdbcTemplate.batchUpdate(sqls);

        //如果包括0则这两条插入语句至少有一句不成功，success为false
        boolean success = !Arrays.asList(check).contains(0);
        //成功的话返回1
        return success ? 1 : 0;
    }

    private RegApplicationPO getRegApplicationPO(String venueID) {
        String sql = "Select * from regApplication where venueID = " + '"' + venueID + '"';
        RegApplicationPO po = jdbcTemplate.queryForObject(sql, (resultSet, i) -> {
            RegApplicationPO tempPO = new RegApplicationPO();
            tempPO.setVenueID(venueID);
            tempPO.setVenueName(resultSet.getString("venueName"));
            tempPO.setCity(resultSet.getString("city"));
            tempPO.setVenueAddress(resultSet.getString("venueAddress"));
            tempPO.setArea(resultSet.getString("area"));
            tempPO.setRow(resultSet.getInt("row"));
            tempPO.setSeat(resultSet.getString("seat"));
            tempPO.setVenueInfo(resultSet.getString("venueInfo"));

            String date = resultSet.getString("applicationTime");
            tempPO.setApplicationTime(date.substring(0, date.length() - 2));

            tempPO.setState(resultSet.getString("state"));
            return tempPO;
        });
        return po;
    }

    private VenuePO getPostVenuePO(String venueID) {
        String sql = "Select * from modifyApplication where venueID = " + '"' + venueID + '"';
        VenuePO po = jdbcTemplate.queryForObject(sql, (resultSet, i) -> {
            VenuePO tempPO = new VenuePO();
            tempPO.setVenueID(venueID);
            tempPO.setVenueName(resultSet.getString("postVenueName"));
            tempPO.setAddress(resultSet.getString("postAddress"));
            tempPO.setVenueInfo(resultSet.getString("postVenueInfo"));
            tempPO.setState(resultSet.getString("state"));
            return tempPO;
        });
        return po;
    }


}
