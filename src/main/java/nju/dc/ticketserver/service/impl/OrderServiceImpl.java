package nju.dc.ticketserver.service.impl;

import io.swagger.models.auth.In;
import nju.dc.ticketserver.dao.OrderDao;
import nju.dc.ticketserver.dao.UserDao;
import nju.dc.ticketserver.dao.utils.DaoUtils;
import nju.dc.ticketserver.po.OrderPO;
import nju.dc.ticketserver.po.SeatPO;
import nju.dc.ticketserver.po.ShowSeatPO;
import nju.dc.ticketserver.po.UserPO;
import nju.dc.ticketserver.service.OrderService;
import nju.dc.ticketserver.service.SeatService;
import nju.dc.ticketserver.service.ShowService;
import nju.dc.ticketserver.service.UserService;
import nju.dc.ticketserver.utils.VIPHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private SeatService seatService;

    @Autowired
    private UserService userService;

    @Autowired
    private ShowService showService;

    @Autowired
    private DaoUtils daoUtils;

    @Override
    public OrderPO getOrderPO(String orderID) {
        return orderDao.getOrderPO(orderID);
    }


    @Override
    public int createOrder(OrderPO orderPO) {

        //检查座位状态
        String[] seatInfo = orderPO.getSeat().split(",");   // 如355-3排9座,688-4排9座
        List<ShowSeatPO> showSeatPOList = new ArrayList<>();
        boolean isSeatAvailable = false;
        for (String seat : seatInfo) {
            String area = showService.getAreaByPrice(orderPO.getShowID(), seat.split("-")[0]);
            String temp1 = seat.split("-")[1]; // 3排9座
            int row = Integer.parseInt(temp1.split("排")[0]);
            int theSeat = Integer.parseInt(subString(temp1, "排", "座"));
            ShowSeatPO showSeatPO = new ShowSeatPO();
            showSeatPO.setShowID(orderPO.getShowID());
            showSeatPO.setVenueID(orderPO.getVenueID());
            showSeatPO.setArea(area);
            showSeatPO.setRow(row);
            showSeatPO.setSeat(theSeat);
            isSeatAvailable = seatService.isSeatAvailable(showSeatPO);
            if (isSeatAvailable == false) {
                return -1;
            }
            showSeatPOList.add(showSeatPO);
        }

        //检查账户余额
        UserPO userPO = userService.getUserPOByUserID(orderPO.getUserID());
        double balance = userService.getUserBalance(userPO.getUserID());
        double couponMaxValue = userService.getUserCouponMaxValue(userPO.getUserID());
        double discount = VIPHelper.getVIPDiscount(userPO.getVipLevel());

        if ((balance + couponMaxValue) < orderPO.getTotalPrice() * discount) {
            return -2;
        }

        orderPO.setOrderState("待支付");
        orderPO.setDiscount(discount);
        orderPO.setOrderDate(daoUtils.setSignUpDate());
        orderPO.setSeat(orderPO.getSeat());
        orderPO.setOrderID(daoUtils.createOrderID());
        //优惠
        orderPO.setTotalPrice(orderPO.getTotalPrice() * discount);

        //设置座位状态

        //可以优化 一次修改多个 而不是多次修改，每次修改一个
        for (ShowSeatPO showSeatPO : showSeatPOList) {
            int check = seatService.setSeatOccupied(showSeatPO);
            if (check == 0) {
                return -3;
            }
        }

        int result = orderDao.createOrder(orderPO);

        return result;
    }

    private String subString(String str, String strStart, String strEnd) {

        /* 找出指定的2个字符在 该字符串里面的位置 */
        int strStartIndex = str.indexOf(strStart);
        int strEndIndex = str.indexOf(strEnd);

        /* 开始截取 */
        String result = str.substring(strStartIndex, strEndIndex).substring(strStart.length());
        return result;
    }

    @Override
    public double getUserDiscount(String userID, int vipLevel) {
        return VIPHelper.getVIPDiscount(vipLevel);
    }

    @Override
    public List<OrderPO> getRecentOrders(String userID) {
        return orderDao.getRecentOrders(userID);
    }

}
