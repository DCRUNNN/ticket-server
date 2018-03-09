package nju.dc.ticketserver.utils;

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



}
