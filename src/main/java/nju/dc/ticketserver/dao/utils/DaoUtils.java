package nju.dc.ticketserver.dao.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class DaoUtils {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 创建用户ID
     * @return
     */
    public String createUserID() {

        String sql = "select count(*) as total from user";
        int total = jdbcTemplate.query(sql, resultSet -> resultSet.next() ? resultSet.getInt("total") : 0);
        return "user-" + formatInteger((total + 1), 4);
    }

    /**
     * 创建用户注册日期
     * 创建场馆注册日期
     * @return
     */
    public String setSignUpDate() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    /**
     * 创建订单ID
     * @return
     */
    public String createOrderID() {
        String sql = "select count(*) as total from orders";
        int total = jdbcTemplate.query(sql, resultSet -> resultSet.next() ? resultSet.getInt("total") : 0);
        return new SimpleDateFormat("yyyyMMdd").format(Date.valueOf(LocalDate.now())) + "-" + formatInteger((total + 1), 6);
    }

    /**
     * 创建场馆ID
     * @return
     */
    public String createVenueID(){
        String sql = "select count(*) as total from venue";
        int total = jdbcTemplate.query(sql, resultSet -> resultSet.next() ? resultSet.getInt("total") : 0);

        String sql2 = "select count(*) as total2 from regApplication";
        int total2 = jdbcTemplate.query(sql2, resultSet -> resultSet.next() ? resultSet.getInt("total2") : 0);

        int result = total >= total2 ? total : total2;

        return formatInteger((result + 1), 7);
    }

    public String createTicketFinanceID(){
        String sql = "select count(*) as total from ticketsfinance";
        int total = jdbcTemplate.query(sql, resultSet -> resultSet.next() ? resultSet.getInt("total") : 0);
        return new SimpleDateFormat("yyyyMMdd").format(Date.valueOf(LocalDate.now())) + "-" + formatInteger((total + 1), 6);
    }

    /**
     * 格式化数字
     * @param i
     * @param length
     * @return
     */
    private String formatInteger(int i, int length) {

        return String.format("%0" + length + "d", i);
    }


}
