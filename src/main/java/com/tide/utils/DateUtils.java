package com.tide.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by wengliemiao on 16/1/25.
 */
public class DateUtils {

    public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 计算日期间隔
     * @param startDate
     * @param endDate
     * @return
     */
    public static int getDaysBetween(String startDate, String endDate) {
        if(endDate == null || "".equals(endDate)) {
            return 0;
        }
        try {
            Date sDate = sdf.parse(startDate);
            Date eDate = sdf.parse(endDate);

            long between = eDate.getTime() - sDate.getTime();
            double leftdays = 1.0 * between / (24 * 60 * 60 * 1000);

            return (int) Math.ceil(leftdays);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 计算日期间隔: 天为单位
     * @param sDate
     * @param endDate
     * @return
     */
    public static int getDaysBetween(Date sDate, String endDate) {
        if(endDate == null || "".equals(endDate)) {
            return 0;
        }
        return DateUtils.getDaysBetween(sdf.format(sDate), endDate);
    }

    /**
     * 计算日期间隔: 小时为单位
     * @param sDate
     * @param oEDate
     * @return
     */
    public static int getMinutesBetween(String sDate, Date oEDate) {
        try {
            Date oSDate = sdf.parse(sDate) ;

            long between = oEDate.getTime() - oSDate.getTime();
            double lefthours = 1.0 * between / (60 * 1000);
            return (int) Math.ceil(lefthours);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取当前日期,并格式化返回
     * @return
     */
    public static String getCurrentDate1() {
        return getCurrentDate1(new Date());
    }

    /**
     * 获取当前日期,并格式化返回
     * @return
     */
    public static String getCurrentDate1(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }

    /**
     * 获取当前日期,并格式化返回
     * @return
     */
    public static String getCurrentDate2() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        return sdf.format(new Date());
    }
}
