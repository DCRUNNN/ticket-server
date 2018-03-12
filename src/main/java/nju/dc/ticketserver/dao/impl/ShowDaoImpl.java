package nju.dc.ticketserver.dao.impl;

import nju.dc.ticketserver.dao.ShowDao;
import nju.dc.ticketserver.po.ShowPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ShowDaoImpl implements ShowDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public ShowPO getShowPOByID(String showID) {

        String sql = "Select * from shows where showID = " + '"' + showID + '"';
        ShowPO po = jdbcTemplate.queryForObject(sql, (resultSet, i) -> {
            ShowPO tempPO = new ShowPO();
            tempPO.setShowID(showID);
            tempPO.setCategory(resultSet.getString("category"));
            tempPO.setCity(resultSet.getString("city"));
            tempPO.setVenueID(resultSet.getString("venueID"));
            tempPO.setVenueName(resultSet.getString("venueName"));
            tempPO.setPerformer(resultSet.getString("performer"));
            tempPO.setShowName(resultSet.getString("showName"));
            tempPO.setPhotoSrc(resultSet.getString("photoSrc"));
            tempPO.setShowDate(resultSet.getString("showDate"));
            tempPO.setPrice(resultSet.getString("price"));
            tempPO.setDescription(resultSet.getString("description"));
            tempPO.setState(resultSet.getString("state"));
            return tempPO;
        });
        return po;
    }

    @Override
    public List<ShowPO> getShowPOByCategory(String category) {

        String sql = "select * from shows where category = " + '"' + category + '"';

        List<ShowPO> showPOList = jdbcTemplate.query(sql, getShowPOMapper());

        return showPOList.size() == 0 ? new ArrayList<>() : showPOList;
    }

    @Override
    public List<ShowPO> getShowPOByCity(String city) {
        String sql = "select * from shows where city = " + '"' + city + '"';

        List<ShowPO> showPOList = jdbcTemplate.query(sql, getShowPOMapper());

        return showPOList.size() == 0 ? new ArrayList<>() : showPOList;
    }

    @Override
    public List<ShowPO> getShowPOByVenue(String venue) {
        String sql = "select * from shows where venueName like " + "%" +  + '"' + venue + '"' + "%";

        List<ShowPO> showPOList = jdbcTemplate.query(sql, getShowPOMapper());

        return showPOList.size() == 0 ? new ArrayList<>() : showPOList;
    }

    @Override
    public List<ShowPO> getShowPOByPerformer(String performer) {
        String sql = "select * from shows where performer like " + "%" + '"' + performer + '"'+ "%";

        List<ShowPO> showPOList = jdbcTemplate.query(sql, getShowPOMapper());

        return showPOList.size() == 0 ? new ArrayList<>() : showPOList;
    }

    @Override
    public List<ShowPO> getAllShowsPO() {
        String sql = "select * from shows";
        List<ShowPO> showPOList = jdbcTemplate.query(sql, getShowPOMapper());
        return showPOList.size() == 0 ? new ArrayList<>() : showPOList;
    }

    private RowMapper<ShowPO> getShowPOMapper() {
        return (resultSet, i) -> {
            ShowPO po = new ShowPO();
            po.setShowID(resultSet.getString("showID"));
            po.setCategory(resultSet.getString("category"));
            po.setCity(resultSet.getString("city"));
            po.setVenueID(resultSet.getString("venueID"));
            po.setVenueName(resultSet.getString("venueName"));
            po.setPerformer(resultSet.getString("performer"));
            po.setShowName(resultSet.getString("showName"));
            po.setPhotoSrc(resultSet.getString("photoSrc"));

            String date = resultSet.getString("showDate");
            po.setShowDate(date.substring(0, date.length() - 2));

            po.setPrice(resultSet.getString("price"));
            po.setDescription(resultSet.getString("description"));
            po.setState(resultSet.getString("state"));
            return po;
        };
    }

}
