package nju.dc.ticketserver.dao.impl;

import nju.dc.ticketserver.dao.ManagerDao;
import nju.dc.ticketserver.dao.UserDao;
import nju.dc.ticketserver.dao.utils.DaoUtils;
import nju.dc.ticketserver.po.CouponPO;
import nju.dc.ticketserver.po.OrderPO;
import nju.dc.ticketserver.po.UserPO;
import nju.dc.ticketserver.utils.VIPHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ManagerDao managerDao;

    @Autowired
    private DaoUtils daoUtils;

    @Override
    public int addUser(UserPO userPO) {
        String sql = "insert into user(username,userID,password,email,isVIP,vipLevel,phoneNumber,balance,totalConsumption,state,activeCode,memberPoints) values "
                + "("
                + '"' + userPO.getUsername() + '"'+ "," + '"' + userPO.getUserID() + '"' + "," + '"' + userPO.getPassword() + '"' + "," + '"' + userPO.getEmail() + '"'
                + "," + userPO.isVIP() + "," + '"' + userPO.getVipLevel() + '"' + "," + '"' + userPO.getPhoneNumber() + '"' + "," + '"' +userPO.getBalance()+ '"'
                + "," + '"'+ userPO.getTotalConsumption()+ '"' + "," + '"' +userPO.getState()+ '"' + "," + '"' +userPO.getActiveCode()+'"'+","+'"'+userPO.getMemberPoints()+'"'
                + ")";
        return jdbcTemplate.update(sql);
    }

    @Override
    public int deleteUser(UserPO userPO) {
        String sql = "delete from user where userID = " + '"' + userPO.getUserID() + '"';
        return jdbcTemplate.update(sql);
    }

    @Override
    public int activeUser(String activeCode) {
        String sql = "update user set state = " + '"' + "已激活" + '"' + " where activeCode = " + '"' + activeCode + '"';
        return jdbcTemplate.update(sql);
    }

    @Override
    public int cancelVIP(String userID) {
        String sql = "update user set isVIP = 0 , vipLevel = -1 where userID = " + '"' + userID + '"';
        return jdbcTemplate.update(sql);
    }

    @Override
    public String getUserID(String username) {
        String sql = "select userID from user where username = ?" ;
        return jdbcTemplate.queryForObject(sql, new Object[] { username }, String.class);
    }

    @Override
    public String getUsername(String userID) {
        String sql = "select username from user where userID = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{ userID }, String.class);
    }

    @Override
    public UserPO getUserPO(String username) {
        String checkExistSql = "Select count(1) from user where userName = " + '"' + username + '"';
        int checkExists = jdbcTemplate.queryForObject(checkExistSql, new Object[]{}, Integer.class);
        if (checkExists == 0) {
            return null;
        }

        String sql = "Select * from user where username = " + '"' + username + '"';
        UserPO po = jdbcTemplate.queryForObject(sql, getUserPOMapper());
        return po;
    }

    @Override
    public UserPO getUserPOByUserID(String userID) {
        String checkExistSql = "Select count(1) from user where userID = " + '"' + userID + '"';
        int checkExists = jdbcTemplate.queryForObject(checkExistSql, new Object[]{}, Integer.class);
        if (checkExists == 0) {
            return null;
        }

        String sql = "Select * from user where userID = " + '"' + userID + '"';
        UserPO po = jdbcTemplate.queryForObject(sql, getUserPOMapper());
        return po;
    }

    @Override
    public UserPO getUserPOByEmail(String email) {
        String checkExistSql = "Select count(1) from user where email = " + '"' + email + '"';
        int checkExists = jdbcTemplate.queryForObject(checkExistSql, new Object[]{}, Integer.class);
        if (checkExists == 0) {
            return null;
        }

        String sql = "Select * from user where email = " + '"' + email + '"';
        UserPO po = jdbcTemplate.queryForObject(sql, getUserPOMapper());
        return po;
    }
    @Override
    public double getUserTotalConsumption(String userID) {
        String sql = "select totalConsumption from user where userID = ? ";
        return jdbcTemplate.queryForObject(sql, new Object[]{ userID }, Double.class);
    }

    @Override
    public int setUserVIP(String userID, int vipLevel) {
        String sql = "update user set vipLevel= " + vipLevel + " where userID = " + '"' + userID + '"';
        return jdbcTemplate.update(sql);
    }

    @Override
    public UserPO modifyUserPO(String userID, String username, String phoneNumber) {
        String sql = "update user set username = " + '"' + username + '"' + "," + " phoneNumber = " + '"' + phoneNumber + '"' + " where userID=" + '"' + userID + '"';
        int success = jdbcTemplate.update(sql);
        return success == 1 ? getUserPOByUserID(userID) : null;
    }

    @Override
    public int modifyPassword(String userID, String newPassword) {
        String sql = "update user set password = " + '"' + newPassword + '"' + " where userID = " + '"' + userID + '"';
        return jdbcTemplate.update(sql);
    }

    @Override
    public double getUserBalance(String userID) {
        return getUserPOByUserID(userID).getBalance();
    }

    @Override
    public double getUserCouponMaxValue(String userID) {
        String sql = "select max(value) from coupon where userID = " + '"' + userID + '"';
        Object obj = jdbcTemplate.queryForObject(sql, Double.class);
        if (obj == null) {
            return 0;
        }else{
            return (double) obj;
        }
    }

    @Override
    public int payOrder(String userID, OrderPO orderPO, CouponPO couponPO) {

        double totalConsumption = getUserTotalConsumption(orderPO.getUserID());
        int vipLevel = VIPHelper.getVIPLevel(totalConsumption + orderPO.getTotalPrice());
        int memberPoints = VIPHelper.getVIPMemberPoints(vipLevel, orderPO.getTotalPrice());

        boolean isUsingCoupon = false;
        if (couponPO.getCouponID() != null) {
            isUsingCoupon = true;
        }

        double nowPrice = 0;
        if (isUsingCoupon) {
            nowPrice = orderPO.getTotalPrice() - couponPO.getValue();
        }else{
            nowPrice = orderPO.getTotalPrice();
        }

        //设置用户余额 总消费金额 VIP
        String sql = "update user set balance = balance - " + '"' + nowPrice + '"' + " , totalConsumption = totalConsumption + " + '"' + nowPrice + '"'
                + ", vipLevel = " + vipLevel + " , memberPoints = memberPoints + " + '"' + memberPoints + '"' + " where userID = " + '"' + userID + '"';

        //设置订单状态
        String sql2 = "update orders set orderState = " + '"' + "已支付" + '"' + " , totalPrice = " + '"' + nowPrice + '"' + " where orderID = " + '"' + orderPO.getOrderID() + '"';

        //设置优惠券状态
        String sql3 = "";

        if (isUsingCoupon == true) {
            sql3 = "update coupon set usedTime = " + '"' + daoUtils.setSignUpDate() + '"' + ",state = " + '"' + "已使用" + '"'
                    + " , orderID = " + '"' + orderPO.getOrderID() + '"'
                    + " where couponID=" + '"' + couponPO.getCouponID() + '"';
        }

        //设置演出总收入
        String sql4 = "update shows set totalIncome = totalIncome +" + '"' + nowPrice + '"' + "where showID=" + '"' + orderPO.getShowID() + '"';

        // ticketFinance

        orderPO.setTotalPrice(nowPrice);


        String[] sqls = null;
        if (isUsingCoupon) {
            sqls = new String[4];
            sqls[0] = sql;
            sqls[1] = sql2;
            sqls[2] = sql3;
            sqls[3] = sql4;
        }else{
            sqls = new String[3];
            sqls[0] = sql;
            sqls[1] = sql2;
            sqls[2] = sql4;
        }

        int[] check = jdbcTemplate.batchUpdate(sqls);

        //如果包括0则这两条插入语句至少有一句不成功，success为false
        boolean success = !Arrays.asList(check).contains(0);
//        成功的话返回1
        return success ? 1 : 0;
    }

    @Override
    public int spotPurchase(String userID, OrderPO orderPO) {

        double nowPrice = orderPO.getTotalPrice();

        //设置订单状态
        String sql = "update orders set orderState = " + '"' + "已支付" + '"' + " , totalPrice = " + '"' + nowPrice + '"' + " where orderID = " + '"' + orderPO.getOrderID() + '"';

        //设置演出总收入
        String sql2 = "update shows set totalIncome = totalIncome +" + '"' + nowPrice + '"' + "where showID=" + '"' + orderPO.getShowID() + '"';

        String[] sqls = new String[2];
        sqls[0] = sql;
        sqls[1] = sql2;

        int[] check = jdbcTemplate.batchUpdate(sqls);

        //如果包括0则这两条插入语句至少有一句不成功，success为false
        boolean success = !Arrays.asList(check).contains(0);
//        成功的话返回1
        return success ? 1 : 0;
    }

    @Override
    public List<UserPO> getUserPOList() {
        String sql = "select * from user";
        List<UserPO> userPOList = jdbcTemplate.query(sql, getUserPOMapper());
        return userPOList.size() == 0 ? new ArrayList<>() : userPOList;
    }

    private RowMapper<UserPO> getUserPOMapper() {
        return (resultSet, i) -> {
            UserPO po = new UserPO();
            po.setUsername(resultSet.getString("username"));
            po.setUserID(resultSet.getString("userID"));
            po.setPassword(resultSet.getString("password"));
            po.setPhoneNumber(resultSet.getString("phoneNumber"));
            po.setEmail(resultSet.getString("email"));
            po.setVIP(resultSet.getBoolean("isVIP"));
            po.setVipLevel(resultSet.getInt("vipLevel"));
            po.setState(resultSet.getString("state"));
            po.setActiveCode(resultSet.getString("activeCode"));
            po.setBalance(resultSet.getDouble("balance"));
            po.setTotalConsumption(resultSet.getDouble("totalConsumption"));
            po.setMemberPoints(resultSet.getInt("memberPoints"));
            return po;
        };
    }
}
