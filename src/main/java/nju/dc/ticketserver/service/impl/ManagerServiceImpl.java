package nju.dc.ticketserver.service.impl;

import nju.dc.ticketserver.dao.ManagerDao;
import nju.dc.ticketserver.dao.ShowDao;
import nju.dc.ticketserver.po.ManagerPO;
import nju.dc.ticketserver.po.ModifyApplicationPO;
import nju.dc.ticketserver.po.RegApplicationPO;
import nju.dc.ticketserver.po.ShowPO;
import nju.dc.ticketserver.service.ManagerService;
import nju.dc.ticketserver.service.ShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class ManagerServiceImpl implements ManagerService {

    @Autowired
    private ManagerDao managerDao;

    @Autowired
    private ShowDao showDao;

    @Autowired
    private ShowService showService;

    @Override
    public int confirmRegVenue(String venueID) {
        return managerDao.confirmRegVenue(venueID);
    }

    @Override
    public int confirmModifyVenueInfo(String venueID) {
        return managerDao.confirmModifyVenueInfo(venueID);
    }

    @Override
    public ManagerPO getManagerPO(String email) {
        return managerDao.getManagerPO(email);
    }

    @Override
    public List<RegApplicationPO> getVenueRegApplicationPOs() {
        return managerDao.getVenueRegApplicationPOs();
    }

    @Override
    public List<ModifyApplicationPO> getVenueModifyApplicationPOs() {
        return managerDao.getVenueModifyApplicationPOs();
    }

    @Override
    public int giveMoneyToVenue(String showID, double paymentRatio) {
        //判断演出是否结束

        boolean canPay = showService.isShowEnd(showID);

        if (canPay) {
            ShowPO showPO = showService.getShowPOByID(showID);
            return managerDao.giveMoneyToVenue(showPO, paymentRatio);
        }else{
            return -1;
        }
    }

    @Override
    public List<ShowPO> getNeedToPayShows() {
        return showDao.getNeedToPayShows();
    }
}
