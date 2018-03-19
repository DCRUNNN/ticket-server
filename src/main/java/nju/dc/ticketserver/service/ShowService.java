package nju.dc.ticketserver.service;

import nju.dc.ticketserver.po.ShowPO;

import java.util.List;

public interface ShowService {

    ShowPO getShowPOByID(String showID);

    List<ShowPO> getShowPOByCategory(String category);

    List<ShowPO> getShowPOByCity(String city);

    List<ShowPO> getShowPOByVenue(String venue);

    List<ShowPO> getShowPOByPerformer(String performer);

    List<ShowPO> getShowPOByCityAndCategory(String city, String category);

    List<ShowPO> guessYouLike();

    List<ShowPO> todayRecommend();

    List<String> getAllCities();

    String getAreaByPrice(String showID, String price);

}
