package nju.dc.ticketserver.service.impl;

import nju.dc.ticketserver.dao.ManagerDao;
import nju.dc.ticketserver.dao.ShowDao;
import nju.dc.ticketserver.po.ManagerPO;
import nju.dc.ticketserver.po.ModifyApplicationPO;
import nju.dc.ticketserver.po.RegApplicationPO;
import nju.dc.ticketserver.po.ShowPO;
import nju.dc.ticketserver.service.ManagerService;
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

        ShowPO showPO = showDao.getShowPOByID(showID);
        String beginDate = showPO.getShowDate();
        beginDate = beginDate.substring(0, 11) + beginDate.substring(beginDate.length() - 5); //演出开始时间

        Calendar c = Calendar.getInstance();//获得一个日历的实例
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        Date nowDate = new Date();
        Date showBegin = new Date();
        try{
            nowDate = sdf.parse(sdf.format(nowDate));//当前日期
            showBegin = sdf.parse(beginDate);
        }catch(Exception e){
            e.printStackTrace();
        }
        c.setTime(showBegin);//设置日历时间
        c.add(Calendar.HOUR, 2);//在日历的小时上增加2个小时

        boolean canPay = false;
        if (c.getTime().before(nowDate)) {
            canPay = true;
        }

        if (canPay) {
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
