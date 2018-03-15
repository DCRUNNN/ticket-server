package nju.dc.ticketserver.dao;

import nju.dc.ticketserver.po.ShowPO;

import java.util.List;

public interface ShowDao {

    ShowPO getShowPOByID(String showID);

    List<ShowPO> getShowPOByCategory(String category);

    List<ShowPO> getShowPOByCity(String city);

    List<ShowPO> getShowPOByVenue(String venue);

    List<ShowPO> getShowPOByPerformer(String performer);

    List<ShowPO> getShowPOByCityAndCategory(String city, String category);

    List<ShowPO> getAllShowsPO();

    List<String> getAllCities();

}
