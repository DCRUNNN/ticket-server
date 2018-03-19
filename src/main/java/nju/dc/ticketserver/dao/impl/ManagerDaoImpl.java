package nju.dc.ticketserver.dao.impl;

import nju.dc.ticketserver.dao.ManagerDao;
import nju.dc.ticketserver.dao.VenueDao;
import nju.dc.ticketserver.dao.utils.DaoUtils;
import nju.dc.ticketserver.po.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class ManagerDaoImpl implements ManagerDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private DaoUtils daoUtils;

    @Autowired
    private VenueDao venueDao;

    @Override
    public int confirmRegVenue(String venueID) {
        RegApplicationPO regApplicationPO = getRegApplicationPO(venueID);

        String sql = "insert into venue(venueID,venueName,password,city,address,area,row,seat,venueInfo,regDate,income,state) values "
                + "("
                + '"' + venueID + '"' + "," + '"' + regApplicationPO.getVenueName() + '"' + "," + '"' + regApplicationPO.getPassword() + '"' + "," + '"' + regApplicationPO.getCity() + '"' + "," + '"' + regApplicationPO.getVenueAddress() + '"'
                + "," + '"' + regApplicationPO.getArea() + '"' + "," + '"' + regApplicationPO.getRow() + '"' + "," + '"' + regApplicationPO.getSeat() + '"'
                + "," + '"' + regApplicationPO.getVenueInfo() + '"' + "," + '"' + regApplicationPO.getApplicationTime() + '"' + "," + 0
                + "," + '"' + "注册成功" + '"'
                + ")";

        String sql2 = "insert into seat(venueID,area,row,seat) values "
                + "("
                + '"' + venueID + '"' + "," + '"' + regApplicationPO.getArea() + '"' + "," + '"' + regApplicationPO.getRow() + '"' + "," + '"' + regApplicationPO.getSeat() + '"'
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
                + "," + "venueInfo = " + '"' + venuePO.getVenueInfo() + '"' + " where venueID = " + '"' + venueID + '"';

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
            tempPO.setPassword(resultSet.getString("password"));
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
        String sql = "Select * from modifyApplication where venueID = " + '"' + venueID + '"' + " and state = " + '"' + "待审核" + '"';
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

    @Override
    public int giveMoneyToVenue(ShowPO showPO, double paymentRatio) {

        TicketsFinancePO ticketsFinancePO = createTicketsFinancePO(showPO, paymentRatio);
        String sql1 = "insert into ticketsFinance(financialID,date,venueID,venueName,venueAddress,totalIncome,venueIncome,ticketsIncome,paymentRatio,showID) values "
                + "("
                + '"' + ticketsFinancePO.getFinancialID() + '"' + "," + '"' + ticketsFinancePO.getDate() + '"' + "," + '"' + ticketsFinancePO.getVenueID() + '"'
                + "," + '"' + ticketsFinancePO.getVenueName() + '"' + "," + '"' + ticketsFinancePO.getVenueAddress() + '"' + "," + '"' + ticketsFinancePO.getTotalIncome() + '"'
                + "," + '"' + ticketsFinancePO.getVenueIncome() + '"' + "," + '"' + ticketsFinancePO.getTicketsIncome() + '"' + "," + '"' + ticketsFinancePO.getPaymentRatio() + '"'
                + "," + '"' + ticketsFinancePO.getShowID() + '"'
                + ")";

//        支付给场馆
//        手动支付给场馆
        String sql2 = "update venue set income = income +" + '"' + ticketsFinancePO.getVenueIncome() + '"' + " where venueID = " + '"' + ticketsFinancePO.getVenueID() + '"';

        String[] sqls = new String[2];
        sqls[0] = sql1;
        sqls[1] = sql2;

        int[] check = jdbcTemplate.batchUpdate(sqls);

        //如果包括0则这两条插入语句至少有一句不成功，success为false
        boolean success = !Arrays.asList(check).contains(0);
//        成功的话返回1
        return success ? 1 : 0;
    }

    @Override
    public TicketsFinancePO createTicketsFinancePO(ShowPO showPO, double paymentRatio) {

        TicketsFinancePO po = new TicketsFinancePO();

        po.setDate(daoUtils.setSignUpDate());
        po.setFinancialID(daoUtils.createTicketFinanceID());

//        double paymentRatio = getManagerPO("458891338@163.com").getPaymentRatio(); //tickets收入的百分比

        po.setPaymentRatio(paymentRatio);

        po.setTotalIncome(showPO.getTotalIncome());

        po.setTicketsIncome(showPO.getTotalIncome() * paymentRatio);

        po.setVenueIncome(showPO.getTotalIncome() - showPO.getTotalIncome() * paymentRatio);
        po.setVenueID(showPO.getVenueID());

        VenuePO venuePO = venueDao.getVenuePO(showPO.getVenueID());
        po.setVenueAddress(venuePO.getAddress());
        po.setVenueName(venuePO.getVenueName());

        po.setShowID(showPO.getShowID());
        return po;
    }

    @Override
    public ManagerPO getManagerPO(String email) {
        String sql = "Select * from manager where email = " + '"' + email + '"';
        ManagerPO po = jdbcTemplate.queryForObject(sql, getManagerPOMapper());
        return po;
    }

    @Override
    public List<RegApplicationPO> getVenueRegApplicationPOs() {
        String sql = "select * from regApplication";
        List<RegApplicationPO> regApplicationPOList = jdbcTemplate.query(sql, getVenueRegApplicationPOMapper());
        return regApplicationPOList.size() == 0 ? new ArrayList<>() : regApplicationPOList;
    }

    @Override
    public List<ModifyApplicationPO> getVenueModifyApplicationPOs() {
        String sql = "select * from modifyApplication";
        List<ModifyApplicationPO> modifyApplicationPOList = jdbcTemplate.query(sql, getVenueModifyApplicationPOMapper());
        return modifyApplicationPOList.size() == 0 ? new ArrayList<>() : modifyApplicationPOList;
    }

    private RowMapper<ManagerPO> getManagerPOMapper() {
        return (resultSet, i) -> {
            ManagerPO po = new ManagerPO();
            po.setName(resultSet.getString("name"));
            po.setEmail(resultSet.getString("email"));
            po.setPassword(resultSet.getString("password"));
            po.setPaymentRatio(resultSet.getDouble("paymentRatio"));
            return po;
        };
    }

    private RowMapper<RegApplicationPO> getVenueRegApplicationPOMapper(){
        return (resultSet, i) -> {
            RegApplicationPO po = new RegApplicationPO();
            po.setVenueID(resultSet.getString("venueID"));
            po.setVenueName(resultSet.getString("venueName"));
            po.setCity(resultSet.getString("city"));
            po.setPassword(resultSet.getString("password"));

            po.setVenueAddress(resultSet.getString("venueAddress"));
            po.setArea(resultSet.getString("area"));
            po.setRow(resultSet.getInt("row"));
            po.setSeat(resultSet.getString("seat"));
            po.setVenueInfo(resultSet.getString("venueInfo"));

            String date = resultSet.getString("applicationTime");
            po.setApplicationTime(date.substring(0, date.length() - 2));
            po.setState(resultSet.getString("state"));
            return po;
        };
    }

    private RowMapper<ModifyApplicationPO> getVenueModifyApplicationPOMapper() {
        return (resultSet, i) -> {
            ModifyApplicationPO po = new ModifyApplicationPO();
            VenuePO preVenuePO = new VenuePO();
            VenuePO postVenuePO = new VenuePO();

            preVenuePO.setVenueName(resultSet.getString("preVenueName"));
            preVenuePO.setAddress(resultSet.getString("preAddress"));
            preVenuePO.setVenueInfo(resultSet.getString("preVenueInfo"));

            postVenuePO.setVenueName(resultSet.getString("postVenueName"));
            postVenuePO.setAddress(resultSet.getString("postAddress"));
            postVenuePO.setVenueInfo(resultSet.getString("postVenueInfo"));

            po.setVenueID(resultSet.getString("venueID"));
            po.setState(resultSet.getString("state"));

            String date = resultSet.getString("applicationTime");
            po.setApplicationTime(date.substring(0, date.length() - 2));
            po.setPreVenuePO(preVenuePO);
            po.setPostVenuePO(postVenuePO);
            return po;
        };
    }

}
