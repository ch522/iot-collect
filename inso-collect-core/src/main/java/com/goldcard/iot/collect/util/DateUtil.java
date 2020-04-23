package com.goldcard.iot.collect.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @Author G002005
 * @Date 2020/4/8 13:27
 */
public class DateUtil {

    private static final String PATTERN_DAY = "yyyy-MM-dd";

    private static final String PATTERN_HOUR = "yyyy-MM-dd HH";

    private static final String PATTERN_MONTH = "yyyy-MM";

    public static SimpleDateFormat getFormat(String pattern) {
        return new SimpleDateFormat(pattern);
    }

    public static String formatHourStr(Date source) {
        return formatStr(source, PATTERN_HOUR);
    }

    public static String formatStr(Date source) {
        return formatStr(source, PATTERN_DAY);
    }

    public static String formatStr(Date source, String pattern) {
        if (null == source) {
            throw new RuntimeException("source is null");
        }
        return getFormat(pattern).format(source);
    }

    public static Date formatDate(Date source, String pattern) {
        try {
            if (null == source) {
                throw new RuntimeException("source is null");
            }
            return getFormat(pattern).parse(getFormat(pattern).format(source));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return source;

    }

    public static Date addDay(Date source, Integer day) {
        if (null == source) {
            throw new RuntimeException("source is null");
        }
        Calendar instance = Calendar.getInstance();
        instance.setTime(source);
        instance.add(Calendar.DAY_OF_MONTH, day);
        return instance.getTime();
    }

    public static Date addHour(Date source, Integer hour) {
        if (null == source) {
            throw new RuntimeException("source is null");
        }
        Calendar instance = Calendar.getInstance();
        instance.setTime(source);
        instance.add(Calendar.HOUR, hour);
        return instance.getTime();
    }

    public static Date addMonth(Date source, Integer month) {
        if (null == source) {
            throw new RuntimeException("source is null");
        }
        Calendar instance = Calendar.getInstance();
        instance.setTime(source);
        instance.add(Calendar.MONTH, month);
        return instance.getTime();

    }


    public static Date firstDayOfMonth(Date source) {
        if (null == source) {
            throw new RuntimeException("source is null");
        }
        Calendar instance = Calendar.getInstance();
        instance.setTime(source);
        instance.set(Calendar.DAY_OF_MONTH, instance.getActualMinimum(Calendar.DAY_OF_MONTH));
        return instance.getTime();
    }

    public static Date lastDayOfMonth(Date source) {
        if (null == source) {
            throw new RuntimeException("source is null");
        }
        Calendar instance = Calendar.getInstance();
        instance.setTime(source);
        instance.set(Calendar.DAY_OF_MONTH, instance.getActualMaximum(Calendar.DAY_OF_MONTH));
        return instance.getTime();
    }
}
