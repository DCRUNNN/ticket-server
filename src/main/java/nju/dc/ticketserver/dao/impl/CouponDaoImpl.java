package nju.dc.ticketserver.dao.impl;

import nju.dc.ticketserver.dao.CouponDao;
import nju.dc.ticketserver.po.CouponPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class CouponDaoImpl implements CouponDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;



    @Override
    public List<CouponPO> getAllCouponPOs(String userID) {
        String sql = "select * from coupon where userID = " + '"' + userID + '"';
        List<CouponPO> couponPOList = jdbcTemplate.query(sql, getCouponPOMapper());
        return couponPOList.size() == 0 ? new ArrayList<>() : couponPOList;
    }

    @Override
    public CouponPO getCouponPO(String couponID) {
        String sql = "select * from coupon where couponID = " + '"' + couponID + '"';
        return jdbcTemplate.queryForObject(sql, getCouponPOMapper());
    }


    @Override
    public CouponPO getCouponPOByState(String userID, String state) {
        String sql = "select * from coupon where userID = " + '"' + userID + '"' + " and state = " + '"' + state + '"';
        return jdbcTemplate.queryForObject(sql, getCouponPOMapper());
    }

    @Override
    public int addCouponPO(CouponPO couponPO) {
        String sql = "insert into coupon(couponID,userID,couponName,description,lastTerm,state,orderID,usedTime,usedMemberPoint,value) values "
                + "("
                + '"' + couponPO.getCouponID() + '"' + "," + '"' + couponPO.getUserID() + '"' + "," + '"' + couponPO.getCouponName() + '"' + "," + '"' + couponPO.getDescription() + '"'
                + "," + '"' + couponPO.getLastTerm() + '"' + "," + '"' + couponPO.getState() + '"' + "," + '"' + couponPO.getOrderID() + '"' + "," + '"' + couponPO.getUsedTime() + '"'
                + "," + '"' + couponPO.getUsedMemberPoint() + '"' + "," + '"' + couponPO.getValue() + '"'
                + ")";

        String sql2 = "update user set memberPoints = memberPoints - " + '"' + couponPO.getUsedMemberPoint() + '"' + " where userID = " + '"' + couponPO.getUserID() + '"';

        String[] sqls = new String[2];
        sqls[0] = sql;
        sqls[1] = sql2;

        int[] check = jdbcTemplate.batchUpdate(sqls);

        //如果包括0则这两条插入语句至少有一句不成功，success为false
        boolean success = !Arrays.asList(check).contains(0);
        //成功的话返回1
        return success ? 1 : 0;
    }


    private RowMapper<CouponPO> getCouponPOMapper() {
        return (resultSet, i) -> {
            CouponPO po = new CouponPO();
            po.setCouponID(resultSet.getString("couponID"));
            po.setUserID(resultSet.getString("userID"));
            po.setCouponName(resultSet.getString("couponName"));
            po.setDescription(resultSet.getString("description"));
            po.setLastTerm(resultSet.getString("lastTerm"));
            po.setState(resultSet.getString("state"));
            po.setOrderID(resultSet.getString("orderID"));

            String date = resultSet.getString("usedTime");
            po.setUsedTime(date.substring(0, date.length() - 2));

            po.setUsedMemberPoint(resultSet.getInt("usedMemberPoint"));
            po.setValue(resultSet.getDouble("value"));
            return po;
        };
    }
}
