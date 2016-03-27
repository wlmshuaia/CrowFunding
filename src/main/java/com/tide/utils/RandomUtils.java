package com.tide.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by wengliemiao on 15/12/24.
 */
public class RandomUtils {

    /**
     * 生成订单编号: 时间戳+4位随机数+用户id
     * @param uid
     * @return
     */
    public static String createOrderNo(Integer uid) {
        long time = new Date().getTime();
        String random = getRandom(4, 10).toString();

        return  time + random + uid ;
    }

    /**
     * 生成项目编号: 时间+4位随机数+用户id
     * @param uid
     * @return
     */
    public static String createProjectNo(Integer uid) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String time = sdf.format(new Date());
        String random = getRandom(4, 10).toString();
        return  time + random + uid ;
    }

    /**
     * 生成随机数
     * @param num   随机数的位数
     * @param range 随机数的范围: 0~range
     * @return
     */
    public static String getRandom(Integer num, Integer range) {
        String random = "";
        for(int i = 0; i < num; i ++){
            random += (int)(Math.random()*range);
        }
        return random;
    }
}
