package com.zhichanzaixian.trademarkapi.comm.utils;


import java.text.SimpleDateFormat;
import java.util.UUID;

/**
 * @author syl  Date: 2018/7/31 Email:nerosyl@live.com
 */
public class IdUtils {
    public static String uuid() {
        //return   StringUtils.replace(UUID.randomUUID().toString(),"-","");
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static String uuidLong() {
        return UUID.randomUUID().toString();
    }

    /**
     * 精确到微秒
     */
    public static String dateId() {
        return new SimpleDateFormat("yyyyMMddHHmmssSSS").format(System.currentTimeMillis());
    }

//    public static void main(String[] args) {
//        System.out.println(dateIdWithRandom());
//    }

//    public static void main(String[] args) {
//        System.out.println(dateId());
//    }

//    public static void main(String[] args) {
//        System.out.println(IdUtils.dateIdWithRandom());
//    }

    /**
     * 精确到微秒+4位随机数
     */
    public static String dateIdWithRandom() {
        return new SimpleDateFormat("yyyyMMddHHmmssSSS").format(System.currentTimeMillis()) + (int) ((Math.random() + 1) * 1000);
    }







}
