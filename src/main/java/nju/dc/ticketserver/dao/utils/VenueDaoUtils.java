package nju.dc.ticketserver.dao.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class VenueDaoUtils {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public String createVenueID(){
        String sql = "select count(*) as total from venue";
        int total = jdbcTemplate.query(sql, resultSet -> resultSet.next() ? resultSet.getInt("total") : 0);
        return formatInteger((total + 1), 7);
    }

    public String setCreateDate() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    private String formatInteger(int i, int length) {
        return String.format("%0" + length + "d", i);
    }
}
