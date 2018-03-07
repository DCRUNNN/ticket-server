package nju.dc.ticketserver.utils;

public class VIPHelper {

    public static double getVIPDiscount(int vipLevel){
        if(vipLevel==1){
            return 0.95;
        }else if(vipLevel == 2 || vipLevel == 3){
            return 0.9;
        } else if (vipLevel == 4 || vipLevel == 5) {
            return 0.85;
        }else{
            return 0.8;
        }
    }

    public static int setVIPLevel(double totalConsumption) {
        return 0;
    }



}
