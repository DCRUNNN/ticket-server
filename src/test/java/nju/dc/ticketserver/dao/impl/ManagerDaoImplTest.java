package nju.dc.ticketserver.dao.impl;

import nju.dc.ticketserver.dao.ManagerDao;
import nju.dc.ticketserver.dao.ShowDao;
import nju.dc.ticketserver.po.ShowPO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class ManagerDaoImplTest {

    @Autowired
    private ManagerDao managerDao;

    @Autowired
    private ShowDao showDao;

    @Test
    public void giveMoneyToVenue() throws Exception {
        ShowPO showPO = showDao.getShowPOByID("show-000006");

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

        System.out.println(c.getTime());
        System.out.println(nowDate);

        if (c.getTime().before(nowDate)) {
            System.out.println("是之前啊！");
        }

//        managerDao.giveMoneyToVenue(null);
    }

    @Test
    public void getManagerRatio() throws Exception {
        System.out.println(managerDao.getManagerPO("458891338@163.com").getPaymentRatio());
    }

}