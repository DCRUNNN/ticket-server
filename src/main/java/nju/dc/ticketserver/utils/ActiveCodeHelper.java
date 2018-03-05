package nju.dc.ticketserver.utils;

import java.util.UUID;

public class ActiveCodeHelper {

    //生成唯一的激活码
    public static String generateUniqueCode(){
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
