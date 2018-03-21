package nju.dc.ticketserver.service.impl;

import io.swagger.models.auth.In;
import nju.dc.ticketserver.dao.CouponDao;
import nju.dc.ticketserver.dao.OrderDao;
import nju.dc.ticketserver.dao.UserDao;
import nju.dc.ticketserver.dao.utils.DaoUtils;
import nju.dc.ticketserver.po.*;
import nju.dc.ticketserver.service.OrderService;
import nju.dc.ticketserver.service.SeatService;
import nju.dc.ticketserver.service.ShowService;
import nju.dc.ticketserver.service.UserService;
import nju.dc.ticketserver.utils.VIPHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

    @Autowired
    private CouponDao couponDao;

    @Override
    public OrderPO getOrderPO(String orderID) {
        return orderDao.getOrderPO(orderID);
    }


    @Override
    public String createOrder(OrderPO orderPO) {

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
                return "座位已售出！";
            }
            showSeatPOList.add(showSeatPO);
        }

//        boolean isSpotPurchase = false;
//        if (orderPO.getUserID().equals("现场购票用户")) {
//            isSpotPurchase = true;
//        }

        //检查账户余额
        UserPO userPO = userService.getUserPOByUserID(orderPO.getUserID());
        double balance = userService.getUserBalance(userPO.getUserID());
        double couponMaxValue = userService.getUserCouponMaxValue(userPO.getUserID());
        double discount = VIPHelper.getVIPDiscount(userPO.getVipLevel());

        if ((balance + couponMaxValue) < orderPO.getTotalPrice() * discount) {
            return "余额不足！";
        }

        orderPO.setOrderState("待支付");
        orderPO.setDiscount(discount);
        orderPO.setOrderDate(daoUtils.setSignUpDate());
        orderPO.setSeat(orderPO.getSeat());
        orderPO.setOrderID(daoUtils.createOrderID());
        //优惠
//        orderPO.setTotalPrice(orderPO.getTotalPrice() * discount);
        //优惠在dao层写

        //设置座位状态

        //可以优化 一次修改多个 而不是多次修改，每次修改一个
        for (ShowSeatPO showSeatPO : showSeatPOList) {
            int check = seatService.setSeatOccupied(showSeatPO);
            if (check == 0) {
                return "修改座位状态失败！";
            }
        }

        int result = orderDao.createOrder(orderPO);

        if (result == 1) {
            return orderPO.getOrderID();
        }else{

            return "创建订单失败！";
        }
    }


    @Override
    public String createSpotPurchaseOrder(OrderPO orderPO) {
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
                return "座位已售出！";
            }
            showSeatPOList.add(showSeatPO);
        }

        orderPO.setOrderState("待支付");
        orderPO.setDiscount(1);
        orderPO.setOrderDate(daoUtils.setSignUpDate());
        orderPO.setSeat(orderPO.getSeat());
        orderPO.setOrderID(daoUtils.createOrderID());

        orderPO.setPurchaseMethod("现场购票");

        //设置座位状态

        //可以优化 一次修改多个 而不是多次修改，每次修改一个
        for (ShowSeatPO showSeatPO : showSeatPOList) {
            int check = seatService.setSeatOccupied(showSeatPO);
            if (check == 0) {
                return "修改座位状态失败！";
            }
        }

        int result = orderDao.createOrder(orderPO);

        if (result == 1) {
            return orderPO.getOrderID();
        }else{

            return "创建订单失败！";
        }
    }

    @Override
    public int cancelOrder(String orderID) {

        OrderPO orderPO = orderDao.getOrderPO(orderID);

        List<ShowSeatPO> showSeatPOList = getShowSeatPOList(orderPO);

        //修改座位状态

        //可以优化 一次修改多个 而不是多次修改，每次修改一个
        int check = 0;
        for (ShowSeatPO showSeatPO : showSeatPOList) {
            check = seatService.setSeatAvailable(showSeatPO);
            if (check == 0) {
                break;
            }
        }

        int result = orderDao.cancelOrder(orderID);

        return result == 1 && check != 0 ? 1 : 0;
    }

    @Override
    public double refundOrder(String userID, String orderID) {
        OrderPO orderPO = getOrderPO(orderID);

        //判断是否使用优惠券
        boolean isUsingCoupon = couponDao.isUsingCoupon(orderID);
        CouponPO couponPO = null;
        if (isUsingCoupon) {
            couponPO = couponDao.getCouponPOByOrderID(orderID);
        }else{
            couponPO = new CouponPO();
        }

        //修改座位状态
        List<ShowSeatPO> showSeatPOList = getShowSeatPOList(orderPO);

        //可以优化 一次修改多个 而不是多次修改，每次修改一个
        int check = 0;
        for (ShowSeatPO showSeatPO : showSeatPOList) {
            check = seatService.setSeatAvailable(showSeatPO);
            if (check == 0) {
                break;
            }
        }

        double backMoney = orderDao.refundOrder(userID, orderPO, couponPO);

        return backMoney != -1 && check != 0 ? backMoney : -1;
    }

    private List<ShowSeatPO> getShowSeatPOList(OrderPO orderPO) {

        String[] seatInfo = orderPO.getSeat().split(",");   // 如355-3排9座,688-4排9座

        List<ShowSeatPO> showSeatPOList = new ArrayList<>();

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
            showSeatPOList.add(showSeatPO);
        }

        return showSeatPOList;
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

    @Override
    public List<OrderPO> getUserUnpayOrders(String userID, String showID) {
        return orderDao.getUserUnpayOrders(userID, showID);
    }

    @Override
    public OrderPO getUnpayOrder(String orderID) {
        return orderDao.getUnpayOrder(orderID);
    }

    @Override
    public long getPayLeftTime(String orderID) {
        OrderPO orderPO = getOrderPO(orderID);
        String createTime = orderPO.getOrderDate();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date createDate = new Date();
        Date nowDate = new Date();

        try{
            createDate = sdf.parse(createTime);//订单创建日期
            nowDate = sdf.parse(sdf.format(nowDate));  //当前日期
        }catch(Exception e){
            e.printStackTrace();
        }

        long diff = nowDate.getTime() - createDate.getTime();//这样得到的差值是毫秒级别

        return 15 * 60 - (diff / 1000);
    }

    @Override
    public List<OrderPO> getAllOrders(String userID) {
        return orderDao.getAllOrders(userID);
    }

    @Override
    public List<OrderPO> getOrdersByState(String userID, String state) {
        return orderDao.getOrdersByState(userID, state);
    }

    @Override
    public String directPurchaseTicket(String userID, String showID, String unitPrice, int ticketAmount) {

        String area = showService.getAreaByPrice(showID, unitPrice + "");

        boolean haveEnoughSeat = seatService.haveEnoughSeat(showID, area, ticketAmount);

        if (haveEnoughSeat) {
            OrderPO orderPO = new OrderPO();

            orderPO.setTotalPrice(Double.parseDouble(unitPrice) * ticketAmount);

            //检查账户余额
            UserPO userPO = userService.getUserPOByUserID(userID);
            double balance = userService.getUserBalance(userID);
            double couponMaxValue = userService.getUserCouponMaxValue(userID);
            double discount = VIPHelper.getVIPDiscount(userPO.getVipLevel());

            if ((balance + couponMaxValue) < orderPO.getTotalPrice() * discount) {
                return "余额不足！";
            }
            orderPO.setOrderState("待支付");
            orderPO.setDiscount(discount);
            orderPO.setOrderDate(daoUtils.setSignUpDate());
            orderPO.setOrderID(daoUtils.createOrderID());
            //优惠在dao层写
            orderPO.setTotalPrice(orderPO.getTotalPrice());

            ShowPO showPO = showService.getShowPOByID(showID);

            orderPO.setShowName(showPO.getShowName());
            orderPO.setUnitPrice(Double.parseDouble(unitPrice));
            orderPO.setTicketsAmount(ticketAmount);
            orderPO.setPurchaseMethod("立即购买");
            orderPO.setSeat("待分配");

            orderPO.setShowID(showID);
            orderPO.setVenueID(showPO.getVenueID());
            orderPO.setUserID(userID);
            orderPO.setUsername(userPO.getUsername());

            int result = orderDao.directPurchaseTicket(orderPO);

            if (result == 1) {
                return orderPO.getOrderID();
            }else{
                return "创建订单失败！";
            }
        } else {
            return "座位数量不足！";
        }
    }

    @Override
    public List<OrderPO> getNeedToArrangeSeatOrders(String showID) {
        return orderDao.getNeedToArrangeSeatOrders(showID);
    }

}
