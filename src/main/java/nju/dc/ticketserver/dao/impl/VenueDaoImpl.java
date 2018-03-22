package nju.dc.ticketserver.dao.impl;

import com.sun.rowset.internal.Row;
import nju.dc.ticketserver.dao.VenueDao;
import nju.dc.ticketserver.po.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Repository
public class VenueDaoImpl implements VenueDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int applyRegVenue(RegApplicationPO regApplicationPO) {

        String sql = "insert into regApplication(venueID,venueName,city,password,venueAddress,area,row,seat,venueInfo,applicationTime,state) values "
                + "("
                + '"' + regApplicationPO.getVenueID() + '"' + "," + '"' + regApplicationPO.getVenueName() + '"' + "," + '"' + regApplicationPO.getCity() + '"' + "," + '"' + regApplicationPO.getPassword() + '"' + "," + '"' + regApplicationPO.getVenueAddress() + '"'
                + "," + '"' + regApplicationPO.getArea() + '"' + "," + '"' + regApplicationPO.getRow() + '"' + "," + '"' + regApplicationPO.getSeat() + '"' + "," + '"' + regApplicationPO.getVenueInfo() + '"' + "," + '"' + regApplicationPO.getApplicationTime() + '"' + "," + '"' + regApplicationPO.getState() + '"'
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
                + "," + '"' + postVenuePO.getAddress() + '"' + "," + '"' + preVenuePO.getVenueInfo() + '"' + "," + '"' + postVenuePO.getVenueInfo() + '"' + "," +'"'+"待审核"+'"'
                + "," + '"' + venuePO.getApplicationTime() + '"'
                + ")";
        return jdbcTemplate.update(sql);
    }

    @Override
    public int releaseShowPlan(ShowPO showPO, List<ShowSeatPO> showSeatPOList) {

        String sql = "insert into shows(showID,category,city,venueID,venueName,performer,showName,photoSrc,showDate,price,description,state,area,allRow,seatInfo) values "
                + "("
                + '"' + showPO.getShowID() + '"' + "," + '"' + showPO.getCategory() + '"' + "," + '"' + showPO.getCity() + '"' + "," + '"' + showPO.getVenueID() + '"'
                + "," + '"' + showPO.getVenueName() + '"' + "," + '"' + showPO.getPerformer() + '"' + "," + '"' + showPO.getShowName() + '"' + "," + '"' + showPO.getPhotoSrc() + '"' + "," + '"' + showPO.getShowDate() + '"'
                + "," + '"' + showPO.getPrice() + '"' + "," + '"' + showPO.getDescription() + '"' + "," + '"' + showPO.getState() + '"' + "," + '"' + showPO.getArea() + '"' + "," + '"' + showPO.getAllRow() + '"' + "," + '"' + showPO.getSeat() + '"'
                + ")";


        //使用stringbuffer来优化，插入数据库时只执行一次插入，而不是多次插入
        StringBuffer sql2 = new StringBuffer("insert into showSeat(showID,venueID,area,row,seat,state) values ");
        for(int i=0;i<showSeatPOList.size()/2;i++) {
            if (i != 0) {
                sql2.append(",");
            }
            ShowSeatPO showSeatPO = showSeatPOList.get(i);
            sql2.append("(" + '"' + showSeatPO.getShowID() + '"' + "," + '"' + showSeatPO.getVenueID() + '"' + "," + '"' + showSeatPO.getArea() + '"' + "," + '"' + showSeatPO.getRow() + '"'
                    + "," + '"' + showSeatPO.getSeat() + '"' + "," + '"' + showSeatPO.getState() + '"'
                    + ")");
        }

        StringBuffer sql3 = new StringBuffer("insert into showSeat(showID,venueID,area,row,seat,state) values ");
        for(int j=showSeatPOList.size()/2;j<showSeatPOList.size();j++) {
            if (j != showSeatPOList.size()/2) {
                sql3.append(",");
            }
            ShowSeatPO showSeatPO = showSeatPOList.get(j);
            sql3.append("(" + '"' + showSeatPO.getShowID() + '"' + "," + '"' + showSeatPO.getVenueID() + '"' + "," + '"' + showSeatPO.getArea() + '"' + "," + '"' + showSeatPO.getRow() + '"'
                    + "," + '"' + showSeatPO.getSeat() + '"' + "," + '"' + showSeatPO.getState() + '"'
                    + ")");
        }

        String[] sqls = new String[3];
        sqls[0] = sql;
        sqls[1] = sql2.toString();
        sqls[2] = sql3.toString();

        int[] check = jdbcTemplate.batchUpdate(sqls);

        //如果包括0则这两条插入语句至少有一句不成功，success为false
        boolean success = !Arrays.asList(check).contains(0);
        //成功的话返回1
        return success ? 1 : 0;
    }

    @Override
    public VenuePO getVenuePO(String venueID) {

        String checkExistSql = "Select count(1) from venue where venueID = " + '"' + venueID + '"';
        int checkExists = jdbcTemplate.queryForObject(checkExistSql, new Object[]{}, Integer.class);
        if (checkExists == 0) {
            return null;
        }

        String sql = "Select * from venue where venueID = " + '"' + venueID + '"';
        VenuePO po = jdbcTemplate.queryForObject(sql, getVenuePOMapper());
        return po;
    }

    @Override
    public List<OrderPO> getVenueRecentOrders(String venueID) {
        String sql = "select * from orders where venueID = " + '"' + venueID + '"' + " order by orderID DESC limit 8";
        List<OrderPO> recentOrderList = jdbcTemplate.query(sql, getOrderPOMapper());
        return recentOrderList.size() == 0 ? new ArrayList<>() : recentOrderList;
    }

    @Override
    public int checkTicket(String orderID) {
        String sql = "update orders set orderState = " + '"' + "进行中" + '"' + " where orderID = " + '"' + orderID + '"';
        return jdbcTemplate.update(sql);
    }

    @Override
    public List<Map<String, Object>> getHotShows(String venueID) {
        String sql = "select count(*) as total,showID from orders where venueID = " + '"' + venueID + '"' + " group by showID order by total limit 4";
        List<Map<String, Object>> map = jdbcTemplate.queryForList(sql);
        return map;
    }

    @Override
    public List<OrderPO> getVenueAllOrders(String venueID) {
        String sql = "select * from orders where venueID = " + '"' + venueID + '"';
        List<OrderPO> orderPOList = jdbcTemplate.query(sql, getOrderPOMapper());
        return orderPOList.size() == 0 ? new ArrayList<>() : orderPOList;
    }

    @Override
    public List<VenuePO> getVenuePOList() {
        String sql = "select * from venue";
        List<VenuePO> venuePOList = jdbcTemplate.query(sql, getVenuePOMapper());
        return venuePOList.size() == 0 ? new ArrayList<>() : venuePOList;
    }

    @Override
    public List<TicketsFinancePO> getVenueFinanceInfo(String venueID) {
        String sql = "select * from ticketsFinance where venueID = " + '"' + venueID + '"';
        List<TicketsFinancePO> ticketsFinancePOList = jdbcTemplate.query(sql, getTicketFinancePOMapper());
        return ticketsFinancePOList.size() == 0 ? new ArrayList<>() : ticketsFinancePOList;
    }

    @Override
    public int setShowGoing(String showID) {
        String sql = "update shows set state = " + '"' + "进行中" + '"' + " where showID = " + '"' + showID + '"';
        return jdbcTemplate.update(sql);
    }

    @Override
    public int setShowDone(String showID) {
        String sql = "update shows set state = " + '"' + "已结束" + '"' + " where showID = " + '"' + showID + '"';
        String sql2 = "update orders set orderState = " + '"' + "已完成" + '"' + " where showID = " + '"' + showID + '"';

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
    public int modifyPassword(String venueID, String newPassword) {
        String sql = "update venue set password = " + '"' + newPassword + '"' + " where venueID = " + '"' + venueID + '"';
        return jdbcTemplate.update(sql);
    }

    private RowMapper<OrderPO> getOrderPOMapper() {
        return (resultSet, i) -> {
            OrderPO po = new OrderPO();
            po.setOrderID(resultSet.getString("orderID"));
            po.setUserID(resultSet.getString("userID"));
            po.setUsername(resultSet.getString("username"));
            po.setVenueID(resultSet.getString("venueID"));
            po.setShowID(resultSet.getString("showID"));
            po.setShowName(resultSet.getString("showName"));
            po.setSeat(resultSet.getString("seats"));
            po.setPurchaseMethod(resultSet.getString("purchaseMethod"));
            po.setTicketsAmount(resultSet.getInt("ticketsAmount"));
            po.setOrderState(resultSet.getString("orderState"));
            po.setOrderDate(resultSet.getString("orderDate"));
            po.setTotalPrice(resultSet.getDouble("totalPrice"));
            po.setUnitPrice(resultSet.getDouble("unitPrice"));
            po.setDiscount(resultSet.getDouble("discount"));
            po.setBackMoney(resultSet.getDouble("backMoney"));

            return po;
        };
    }

    private RowMapper<TicketsFinancePO> getTicketFinancePOMapper() {
        return (resultSet, i) -> {
            TicketsFinancePO tempPO = new TicketsFinancePO();
            tempPO.setFinancialID(resultSet.getString("financialID"));
            tempPO.setDate(resultSet.getString("date"));
            tempPO.setVenueID(resultSet.getString("venueID"));
            tempPO.setVenueName(resultSet.getString("venueName"));
            tempPO.setVenueAddress(resultSet.getString("venueAddress"));
            tempPO.setTotalIncome(resultSet.getDouble("totalIncome"));
            tempPO.setVenueIncome(resultSet.getDouble("venueIncome"));
            tempPO.setTicketsIncome(resultSet.getDouble("ticketsIncome"));
            tempPO.setPaymentRatio(resultSet.getDouble("paymentRatio"));
            tempPO.setShowID(resultSet.getString("showID"));
            return tempPO;
        };
    }

    private RowMapper<VenuePO> getVenuePOMapper(){
        return (resultSet, i) -> {
            VenuePO tempPO = new VenuePO();
            tempPO.setVenueID(resultSet.getString("venueID"));
            tempPO.setVenueName(resultSet.getString("venueName"));
            tempPO.setCity(resultSet.getString("city"));
            tempPO.setPassword(resultSet.getString("password"));
            tempPO.setAddress(resultSet.getString("address"));
            tempPO.setArea(resultSet.getString("area"));
            tempPO.setRow(resultSet.getInt("row"));
            tempPO.setSeat(resultSet.getString("seat"));
            tempPO.setVenueInfo(resultSet.getString("venueInfo"));

            String date = resultSet.getString("regDate");
            tempPO.setRegDate(date.substring(0, date.length() - 2));

            tempPO.setIncome(resultSet.getInt("income"));
            tempPO.setState(resultSet.getString("state"));
            return tempPO;
        };
    }

}
