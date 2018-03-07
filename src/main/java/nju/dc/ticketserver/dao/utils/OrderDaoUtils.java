package nju.dc.ticketserver.dao.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

@Component
public class OrderDaoUtils {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public String createOrderID() {
        String sql = "select count(*) as total from orders";
        int total = jdbcTemplate.query(sql, resultSet -> resultSet.next() ? resultSet.getInt("total") : 0);
        return new SimpleDateFormat("yyyyMMdd").format(Date.valueOf(LocalDate.now())) + "-" + formatInteger((total + 1), 6);
    }

    private String formatInteger(int i, int length) {

        return String.format("%0" + length + "d", i);
    }

}
