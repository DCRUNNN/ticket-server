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
        ShowPO po = jdbcTemplate.queryForObject(sql, getShowPOMapper());
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

    @Override
    public List<String> getAllCities() {
        String sql = "select distinct city from shows";
        List<String> allCities = jdbcTemplate.queryForList(sql, String.class);
        return allCities.size() == 0 ? new ArrayList<>() : allCities;
    }

    @Override
    public List<ShowPO> getShowPOByCityAndCategory(String city, String category) {
        String sql = "select * from shows where city = " + '"' + city + '"' + " and category = " + '"' + category + '"';
        List<ShowPO> showPOList = jdbcTemplate.query(sql, getShowPOMapper());
        return showPOList.size() == 0 ? new ArrayList<>() : showPOList;
    }

    @Override
    public String getAreaByPrice(String showID, String price) {
        ShowPO showPO = getShowPOByID(showID);
        String[] areas = showPO.getArea().split("/");
        String[] prices = showPO.getPrice().split("/");
        String result = "";
        for (int i = 0; i < areas.length; i++) {
            if (prices[i].equals(price)) {
                result = areas[i];
                break;
            }
        }
        return result.split("-")[0];
    }

    @Override
    public double getShowTotalIncome(String showID) {
        String sql = "select totalIncome from shows where showID = " + '"' + showID + '"';
        return jdbcTemplate.queryForObject(sql, Double.class);
    }

    @Override
    public List<ShowPO> getNeedToPayShows() {
        String sql = "select * from shows S where S.totalIncome<>0 and S.showID not in (select T.showID from ticketsfinance T)";
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

//            String date = resultSet.getString("showDate");
//            po.setShowDate(date.substring(0, date.length() - 2));
            po.setShowDate(resultSet.getString("showDate"));

            po.setPrice(resultSet.getString("price"));
            po.setDescription(resultSet.getString("description"));
            po.setState(resultSet.getString("state"));

            po.setArea(resultSet.getString("area"));
            po.setAllRow(resultSet.getInt("allRow"));
            po.setSeat(resultSet.getString("seatInfo"));

            po.setTotalIncome(resultSet.getDouble("totalIncome"));

            return po;
        };
    }


}
