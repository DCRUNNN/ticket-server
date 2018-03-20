package nju.dc.ticketserver.service.impl;

import nju.dc.ticketserver.dao.OrderDao;
import nju.dc.ticketserver.dao.VenueDao;
import nju.dc.ticketserver.dao.utils.DaoUtils;
import nju.dc.ticketserver.po.*;
import nju.dc.ticketserver.service.SeatService;
import nju.dc.ticketserver.service.ShowService;
import nju.dc.ticketserver.service.VenueService;
import nju.dc.ticketserver.utils.EncryptHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class VenueServiceImpl implements VenueService {

    @Autowired
    private VenueDao venueDao;

    @Autowired
    private DaoUtils daoUtils;

    @Autowired
    private SeatService seatService;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private ShowService showService;

    @Override
    public String applyRegVenue(RegApplicationPO regApplicationPO) {
        String venueID = daoUtils.createVenueID();
        regApplicationPO.setVenueID(venueID);

        regApplicationPO.setPassword(EncryptHelper.getShaEncryption(regApplicationPO.getPassword()));

        regApplicationPO.setApplicationTime(daoUtils.setSignUpDate());
        regApplicationPO.setState("待审核");

        //处理area
        String seat = seatService.convertSeatInfo(regApplicationPO.getArea(), regApplicationPO.getRow(), regApplicationPO.getSeat());
        regApplicationPO.setSeat(seat);
        int check = venueDao.applyRegVenue(regApplicationPO);

        return check==1? venueID:"fail" ;
    }

    @Override
    public int applyModifyVenueInfo(ModifyApplicationPO modifyApplicationPO) {
        modifyApplicationPO.setApplicationTime(daoUtils.setSignUpDate());
        modifyApplicationPO.setState("待审核");

        return venueDao.applyModifyVenueInfo(modifyApplicationPO);
    }

    @Override
    public int releaseShowPlan(ShowPO showPO, List<ShowSeatPO> showSeatPOList) {
        return venueDao.releaseShowPlan(showPO, showSeatPOList);
    }

    @Override
    public VenuePO getVenuePO(String venueID) {
        return venueDao.getVenuePO(venueID);
    }


    @Override
    public List<OrderPO> getVenueRecentOrders(String venueID) {
        return venueDao.getVenueRecentOrders(venueID);
    }

    @Override
    public int checkTicket(String orderID, String venueID) {

        OrderPO orderPO = orderDao.getOrderPO(orderID);
        if (orderPO == null) {
            return -2;  //订单不存在
        }

        if (!orderPO.getVenueID().equals(venueID)) {
            return -6; //不是这个场馆的订单
        }

        if (orderPO.getOrderState().equals("已完成")) {
            return -3; //演出项目已结束
        }

        if (orderPO.getOrderState().equals("进行中")) {
            return -4; //已检票登记
        }

        if (orderPO.getOrderState().equals("已退款") || orderPO.getOrderState().equals("已失效")) {
            return -7;
        }

        if (orderPO.getOrderState().equals("待支付")) {
            return -8;
        }

        //判断是否到了可以检票的时候
        ShowPO showPO = showService.getShowPOByID(orderPO.getShowID());
        boolean canCheck = showService.isShowStartCheckTicket(showPO.getShowID());

        if (canCheck) {
            int check = venueDao.checkTicket(orderID);
            return check == 1 ? 1 : -1;
        } else {
            return -5;
        }

    }
}
