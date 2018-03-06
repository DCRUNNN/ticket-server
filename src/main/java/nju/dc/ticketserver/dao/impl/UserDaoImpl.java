package nju.dc.ticketserver.dao.impl;

import nju.dc.ticketserver.dao.UserDao;
import nju.dc.ticketserver.po.UserPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements UserDao{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int addUser(UserPO userPO) {
        String sql = "insert into user(username,userID,password,email,isVIP,vipLevel,phoneNumber,balance,totalConsumption,state,activeCode) values "
                + "("
                + '"' + userPO.getUsername() + '"'+ "," + '"' + userPO.getUserID() + '"' + "," + '"' + userPO.getPassword() + '"' + "," + '"' + userPO.getEmail() + '"'
                + "," + userPO.isVIP() + "," + '"' + userPO.getVipLevel() + '"' + "," + '"' + userPO.getPhoneNumber() + '"' + "," + '"' +userPO.getBalance()+ '"'
                + "," + '"'+ userPO.getTotalConsumption()+ '"' + "," + '"' +userPO.getState()+ '"' + "," + '"' +userPO.getActiveCode()+'"'
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
        UserPO po = jdbcTemplate.queryForObject(sql, (resultSet, i) -> {
            UserPO tempPO = new UserPO();
            tempPO.setUsername(resultSet.getString("username"));
            tempPO.setUserID(resultSet.getString("userID"));
            tempPO.setPassword(resultSet.getString("password"));
            tempPO.setPhoneNumber(resultSet.getString("phoneNumber"));
            tempPO.setEmail(resultSet.getString("email"));
            tempPO.setVIP(resultSet.getBoolean("isVIP"));
            tempPO.setVipLevel(resultSet.getInt("vipLevel"));
            return tempPO;
        });
        return po;
    }
}
