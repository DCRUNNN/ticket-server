package nju.dc.ticketserver.dao.impl;

import nju.dc.ticketserver.dao.UserDao;
import nju.dc.ticketserver.po.UserPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements UserDao{

    @Autowired
    private JdbcTemplate jdbcTemplate;

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
