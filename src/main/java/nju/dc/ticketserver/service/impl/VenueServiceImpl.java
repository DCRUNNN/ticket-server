package nju.dc.ticketserver.service.impl;

import nju.dc.ticketserver.dao.OrderDao;
import nju.dc.ticketserver.dao.VenueDao;
import nju.dc.ticketserver.dao.utils.DaoUtils;
import nju.dc.ticketserver.po.*;
import nju.dc.ticketserver.service.OrderService;
import nju.dc.ticketserver.service.SeatService;
import nju.dc.ticketserver.service.ShowService;
import nju.dc.ticketserver.service.VenueService;
import nju.dc.ticketserver.utils.EncryptHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

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


    @Override
    public String arrangeSeat(String showID) {

        List<OrderPO> orders = orderDao.getNeedToArrangeSeatOrders(showID);

        List<ShowSeatPO> availableSeats = null;

        Random rand = new Random();

        StringBuffer result = new StringBuffer();

        for (OrderPO orderPO : orders) {

            String area = showService.getAreaByPrice(showID, String.valueOf((int)orderPO.getUnitPrice()));

            boolean haveEnoughSeat = seatService.haveEnoughSeat(showID, area, orderPO.getTicketsAmount());
            if (haveEnoughSeat) {
                availableSeats = seatService.getAvailableSeat(showID, area);

                Set<ShowSeatPO> set = new HashSet<>();

                boolean[] bool = new boolean[availableSeats.size()];
                int target = 0;
                for (int i = 0; i < orderPO.getTicketsAmount(); i++) {
                    do {
                        target = rand.nextInt(availableSeats.size());
                    }while(bool[target]);
                    bool[target] = true;
                    set.add(availableSeats.get(target));
                }

//                for (int i = 0; i < orderPO.getTicketsAmount(); i++) {
//                    set.add(availableSeats.get(rand.nextInt(availableSeats.size())));
//                }

                int check = 0;
                Iterator it = set.iterator();
                while (it.hasNext()) {

                    ShowSeatPO showSeatPO = (ShowSeatPO) it.next();
                    check = seatService.setSeatOccupied(showSeatPO);
                    result.append((int) orderPO.getUnitPrice()).append("-").append(showSeatPO.getRow()).append("排").append(showSeatPO.getSeat()).append("座").append(",");
                }

                if (check == 0) {
                    while(it.hasNext()){
                        check = seatService.setSeatAvailable((ShowSeatPO) it.next());
                    }
                    return orderPO.getOrderID() + "分配座位失败，修改座位信息失败！";
                }else{
                    //修改order上的座位信息
                    result.deleteCharAt(result.length() - 1);
                    orderDao.updateOrderSeat(orderPO.getOrderID(), result.toString());
                    System.out.println(result.toString());
                    result.setLength(0);
                }
            } else{
                return orderPO.getOrderID() + "分配座位失败，剩余座位不足！";
            }
        }

        return "分配座位成功！";
    }

    @Override
    public String[][] getHotShows(String venueID) {
        String[][] result = new String[2][];

        List<Map<String, Object>> showPOList = venueDao.getHotShows(venueID);

        String[] nums = new String[showPOList.size()];
        String[] showIDs = new String[showPOList.size()];

        for(int i=0;i<showPOList.size();i++) {
            Map<String, Object> temp = showPOList.get(i);
            nums[i] = temp.get("total")+"";
            showIDs[i] = (String) temp.get("showID");
        }

        String[] showNames = new String[showPOList.size()];

        for(int i=0;i<showPOList.size();i++) {
            showNames[i] = showService.getShowPOByID(showIDs[i]).getShowName();
        }

        result[0] = showNames;
        result[1] = nums;
        return result;
    }

    @Override
    public int[] getUserPurchaseMethod(String venueID) {

        List<OrderPO> orderPOList = venueDao.getVenueAllOrders(venueID);

        int chooseSeatPurchase = 0;
        int directPurchase = 0;
        int spotPurchase = 0;

        for (OrderPO orderPO : orderPOList) {
            switch(orderPO.getPurchaseMethod()){
                case "选座购买":
                    chooseSeatPurchase++;
                    break;
                case "立即购买":
                    directPurchase++;
                    break;
                case "现场购票":
                    spotPurchase++;
                    break;
            }
        }

        int[] result = new int[3];
        result[0] = chooseSeatPurchase;
        result[1] = directPurchase;
        result[2] = spotPurchase;

        return result;
    }
}
