package nju.dc.ticketserver.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class VIPHelper {

    public static double getVIPDiscount(int vipLevel){
        if(vipLevel==1){
            return 0.95;
        }else if(vipLevel == 2 ){
            return 0.9;
        } else if (vipLevel == 3) {
            return 0.85;
        } else if (vipLevel == 4) {
            return 0.8;
        } else if (vipLevel == 5) {
            return 0.75;
        } else {
            return 0.7;
        }
    }

    public static int getVIPLevel(double totalConsumption) {
        if (totalConsumption > 0 && totalConsumption < 600) {
            return 1;
        } else if (totalConsumption >= 600 && totalConsumption < 1600) {
            return 2;
        } else if (totalConsumption >= 1600 && totalConsumption < 3000) {
            return 3;
        } else if (totalConsumption >= 3000 && totalConsumption < 5000) {
            return 4;
        } else if (totalConsumption >= 5000 && totalConsumption < 8000) {
            return 5;
        }else {
            return 6;
        }
    }

    public static int getVIPMemberPoints(int vipLevel, double totalPrice) {
        if(vipLevel==1){
            return (int) (totalPrice * 0.2);
        }else if(vipLevel == 2 ){
            return (int) (totalPrice * 0.25);
        } else if (vipLevel == 3) {
            return (int) (totalPrice * 0.3);
        } else if (vipLevel == 4) {
            return (int) (totalPrice * 0.4);
        } else if (vipLevel == 5) {
            return (int) (totalPrice * 0.5);
        } else {
            return (int) (totalPrice * 0.6);
        }
    }


    public static double refund(int vipLevel, String date, double totalPrice) {

        if (vipLevel == -1) {
            return 0;
        }

//        Calendar c = Calendar.getInstance();//获得一个日历的实例
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date nowDate = new Date();
        Date orderDate = new Date();
        try {
            nowDate = sdf.parse(sdf.format(nowDate));//当前日期
            orderDate = sdf.parse(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        c.setTime(orderDate);//设置订单创建时间

        long minDiff = (nowDate.getTime() - orderDate.getTime()) / (1000 * 60);

        //根据时间 返回退回多少钱
        double result = 0;
        if (minDiff > 0 && minDiff < 60) {
            result = setPrecision(totalPrice * 0.8);
        } else if (minDiff >= 60 && minDiff < 120) {
            result = setPrecision(totalPrice * 0.6);
        } else if (minDiff >= 120 && minDiff < 180) {
            result = setPrecision(totalPrice * 0.4);
        }else {
            result = setPrecision(totalPrice * 0.3);
        }

        return result;

    }


    public static double memberPointsToCoupon(int usedMemberPoints) {
        return Double.parseDouble(String.format("%.2f", usedMemberPoints * 0.01));
    }

    private static double setPrecision(double input) {
        return Double.parseDouble(String.format("%.2f", input));
    }

//    public static void main(String[] args){
//        System.out.println(VIPHelper.refund(1, "2018-03-18 23:43:37", 304));
//    }

}
