package nju.dc.ticketserver.service.impl;

import nju.dc.ticketserver.dao.ShowDao;
import nju.dc.ticketserver.po.ShowPO;
import nju.dc.ticketserver.service.ShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShowServiceImpl implements ShowService {

    @Autowired
    private ShowDao showDao;

    @Override
    public ShowPO getShowPOByID(String showID) {
        return showDao.getShowPOByID(showID);
    }

    @Override
    public List<ShowPO> getShowPOByCategory(String category) {
        return showDao.getShowPOByCategory(category);
    }

    @Override
    public List<ShowPO> getShowPOByCity(String city) {
        return showDao.getShowPOByCity(city);
    }

    @Override
    public List<ShowPO> getShowPOByVenue(String venue) {
        return showDao.getShowPOByVenue(venue);
    }

    @Override
    public List<ShowPO> getShowPOByPerformer(String performer) {
        return showDao.getShowPOByPerformer(performer);
    }

}
