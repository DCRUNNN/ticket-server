package nju.dc.ticketserver.dao.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class UserDaoUtils {

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

    public String setSignUpDate() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
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
