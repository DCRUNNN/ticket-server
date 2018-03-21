package nju.dc.ticketserver.service.impl;

import nju.dc.ticketserver.dao.ShowDao;
import nju.dc.ticketserver.po.ShowPO;
import nju.dc.ticketserver.service.ShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.annotation.Target;
import java.text.SimpleDateFormat;
import java.util.*;

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

    @Override
    public List<ShowPO> guessYouLike() {
        List<ShowPO> showPOList = showDao.getAllShowsPO();
        List<ShowPO> resultList = new ArrayList<>();
        Random random = new Random();
        boolean[] bool = new boolean[showPOList.size()];
        int target = 0;
        for (int i = 0; i < 6; i++) {
            do {
                target = random.nextInt(showPOList.size());
            }while(bool[target]);
            bool[target] = true;
            resultList.add(showPOList.get(target));
        }
        return resultList;
    }

    @Override
    public List<ShowPO> todayRecommend() {
        return guessYouLike();
    }


    @Override
    public List<String> getAllCities() {
        return showDao.getAllCities();
    }

    @Override
    public List<ShowPO> getShowPOByCityAndCategory(String city, String category) {
        return showDao.getShowPOByCityAndCategory(city, category);
    }

    @Override
    public String getAreaByPrice(String showID, String price) {
        return showDao.getAreaByPrice(showID, price);
    }

    @Override
    public boolean isShowEnd(String showID) {
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

        boolean result = false;
        if (c.getTime().before(nowDate)) {
            result = true;
        }
        return result;
    }

    @Override
    public boolean isShowStartCheckTicket(String showID) {
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

        c.add(Calendar.HOUR, -2);//在日历的小时上减去2个小时

        boolean result = false;
        if (c.getTime().before(nowDate)) {
            result = true;
        }
        return result;
    }

    @Override
    public List<ShowPO> getShowByVenueID(String venueID) {
        return showDao.getShowPOByVenueID(venueID);
    }

    @Override
    public List<ShowPO> getVenueOnSaleShow(String venueID) {

        return showDao.getVenueOnSaleShow(venueID);
    }

    @Override
    public List<ShowPO> getVenueNeedToArrangeShow(String venueID) {
        List<ShowPO> allShowPOList = showDao.getVenueNeedToArrangeShow(venueID);

        List<ShowPO> resultList = new ArrayList<>();

        Calendar c = Calendar.getInstance();//获得一个日历的实例
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        Date nowDate = new Date();
        Date showBegin = new Date();

        for (ShowPO showPO : allShowPOList) {
            String beginDate = showPO.getShowDate();
            beginDate = beginDate.substring(0, 11) + beginDate.substring(beginDate.length() - 5); //演出开始时间

            try{
                nowDate = sdf.parse(sdf.format(nowDate));//当前日期
                showBegin = sdf.parse(beginDate);
            }catch(Exception e){
                e.printStackTrace();
            }

            c.setTime(showBegin);//设置日历时间

//            c.add(Calendar.WEEK_OF_YEAR, -2);//在日历的小时上减去2周

            c.set(Calendar.DATE, c.get(Calendar.DATE) - 14);

            if (c.getTime().before(nowDate)) {
                resultList.add(showPO);
            }
        }

        return resultList;
    }
}
