package com.nandy007.web.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtil {

    /**
     * 字符串转换为Date类型
     * 
     * @param date 待转换日期
     * @param pattern 转化格式 默认格式yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static Date formatDateTime(String date, String pattern) {
        if (pattern == null || "".equals(pattern)) {
            pattern = "yyyy-MM-dd HH:mm:ss";
        }
        if (date == null || "".equals(date)) {
            return null;
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            try {
                Date d = sdf.parse(date);
                return d;
            } catch (Exception e) {
                return null;
            }

        }
    }

    /**
     * 时间转换
     */
    public static Date getDate(String dateTime) {
        return formatDateTime(dateTime, null);
    }

    /**
     * Date类型转换为字符串
     * 
     * @param date 待转换日期
     * @param pattern 转化格式 默认格式yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String formatDateTime(Date date, String pattern) {
        if (pattern == null || "".equals(pattern)) {
            pattern = "yyyy-MM-dd HH:mm:ss";
        }
        if (date == null || "".equals(date)) {
            return null;
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            return sdf.format(date);
        }
    }

    public static String formatDateTime(long time) {
        String pattern = "yyyy-MM-dd HH:mm:ss";
        return formatDateTime(time, pattern);
    }

    public static String formatDateTime(long time, String pattern) {
        if (pattern == null || "".equals(pattern)) {
            pattern = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            Date d = new Date(time);
            return sdf.format(d);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Date类型转换为字符串
     * 
     * @param date 待转换日期
     * @param pattern 转化格式 默认格式yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String formatDateTime(Date date) {
        return formatDateTime(date, null);
    }

    /**
     * 获取当前时间字符串表示
     */
    public static String getNowTime() {
        return formatDateTime(new Date());
    }

    /**
     * 日期添加天数
     * 
     * @param date
     * @param days
     * @return
     */
    public static Date addDays(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days);
        return cal.getTime();
    }

    /**
     * 获取时间差（天）
     * 
     * @param smallTime
     * @param bigTime
     * @return
     */
    public static int getTimeDifference(String smallTime, String bigTime) {
        SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        int days = 0;
        try {
            long from = simpleFormat.parse(smallTime).getTime();
            long to = simpleFormat.parse(bigTime).getTime();
            days = (int)((to - from) / (1000 * 60 * 60 * 24));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return days;
    }

    /**
     * 日期添加分钟
     * 
     * @param date
     * @param minutes
     * @return
     */
    public static Date addMinutes(Date date, int minutes) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MINUTE, minutes);
        return cal.getTime();
    }
    
    /**
     * 获取指定日期月份
     * 
     * @param date
     * @return
     */
    public static int getMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        return cal.get(Calendar.MONTH) + 1;
    }
}
