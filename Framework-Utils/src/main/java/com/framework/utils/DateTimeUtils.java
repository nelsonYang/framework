package com.framework.utils;

import com.frameworkLog.factory.LogFactory;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.slf4j.Logger;

/**
 *
 * @author Administrator
 */
public class DateTimeUtils {

    private DateTimeUtils() {
    }
    private static final Logger logger = LogFactory.getInstance().getLogger(DateTimeUtils.class);
    private static final String weekDays[] = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五",
        "星期六"};

    public static long getTimesMorning() {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTimeInMillis();
    }

    public static String getFormatTime(Timestamp datetime) {
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String st = sdf.format(datetime);//
        return st.substring(st.length() - 8);
    }

    public static long getTimes(int hour, int second, int minute, int millsecond) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.SECOND, second);
        cal.set(Calendar.MINUTE, minute);
        cal.set(Calendar.MILLISECOND, millsecond);
        return cal.getTimeInMillis();
    }
    //获得当天24点时间

    public static long getTimesNight() {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());
        cal.set(Calendar.HOUR_OF_DAY, 24);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTimeInMillis();
    }

    public static Timestamp str2Timestamp(String yyyymmddhhmmss) {
        Timestamp ts = Timestamp.valueOf(yyyymmddhhmmss);
        return ts;
    }

    public static String timestamp2Str(Timestamp datetime) {
        String st = "";
        if (datetime != null) {
            SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            st = sdf.format(datetime);//
        }
        return st;
    }

    public static Timestamp long2Timestamp(long time) {
        return new Timestamp(time);
    }

    public static long getTimeStampSeconds(Timestamp datetime) {
        long millionSeconds = datetime.getTime();
        return millionSeconds / 1000l;
    }

    /**
     * 获取指定时间对应的毫秒数
     *
     * @param time "HH:mm:ss"
     * @return
     */
    public static long getTimeMillis(String time) {
        try {
            DateFormat dateFormat = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
            DateFormat dayFormat = new SimpleDateFormat("yy-MM-dd");
            Date curDate = dateFormat.parse(dayFormat.format(new Date()) + " " + time);
            return curDate.getTime();
        } catch (ParseException e) {
            logger.error("解析日期出现问题", e);
        }
        return 0;
    }

    public static int getDateSeconds(String dateStr) {
        int dateSeconds = 0;
        try {
            DateFormat dayFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = dayFormat.parse(dateStr);
            dateSeconds = (int) (date.getTime() / 1000l);
        } catch (ParseException ex) {
            logger.error("解析日期出现问题", ex);
        }
        return dateSeconds;
    }

    public static long getDateMillionSeconds(String dateStr) {
        long dateSeconds = 0;
        try {
            DateFormat dayFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = dayFormat.parse(dateStr);
            dateSeconds = date.getTime();
        } catch (ParseException ex) {
            logger.error("解析日期出现问题", ex);
        }
        return dateSeconds;
    }

    public static String getWeekName(String dateStr) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(getDateMillionSeconds(dateStr));
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (dayOfWeek < 0) {
            dayOfWeek = 0;
        }
        return weekDays[dayOfWeek];
    }

    public static int getWeekIndex(String dateStr) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(getDateMillionSeconds(dateStr));
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (dayOfWeek < 0) {
            dayOfWeek = 0;
        }
        return dayOfWeek;

    }

    public static boolean isToday(Timestamp timestamp) {
        boolean isToday = false;
        if (timestamp != null) {
            long times = getTimes(23, 59, 59, 0);
            long now = timestamp.getTime();
            long diff = times - now;
            if (diff > 0) {
                long days = diff / (1000l * 60 * 60 * 24);
                if (days == 0) {
                    isToday = true;
                }
            }
        }
        return isToday;
    }

    public static int getDay(Timestamp timestamp) {
        String st = "";
        if (timestamp != null) {
            SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyyMMdd");
            st = sdf.format(timestamp);
        }
        return Integer.parseInt(st);
    }

    public static int getLastDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        Date date = calendar.getTime();
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyyMMdd");
        String st = sdf.format(date);
        return Integer.parseInt(st);
    }

    public static Timestamp getLastDayInTimestamp() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        long time = calendar.getTimeInMillis();
        Timestamp timestamp = new Timestamp(time);
        return timestamp;
    }

    public static long getTimestampDiff(Timestamp firstTimestatmp, Timestamp secondTimestamp) {
        long firstTime = firstTimestatmp.getTime();
        long secondTime = secondTimestamp.getTime();
        return firstTime - secondTime;
    }

//    public static int getAge(long birthday) {
//        Date birthdayDate = new Date();
//        birthdayDate.setTime(birthday);
//        Date today = new Date();
//        return today.getYear() - birthdayDate.getYear();
//
//    }
    public static int getAge(Integer birthday) {
        DateFormat dayFormat = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date();
        try {
            date = dayFormat.parse(String.valueOf(birthday));
        } catch (ParseException ex) {
            logger.error("解析日期出现问题", ex);
        }
        Date today = new Date();
        return today.getYear() - date.getYear();

    }

    public static int getDateToInteger(String dateStr) {
        int result = 0;
        if (dateStr != null && !dateStr.isEmpty()) {
            result = Integer.parseInt(dateStr.replace("-", "").trim());
        }
        return result;
    }

    public static String convertDateFormat(Integer dateInteger, String format) {
        String result = "";
        if (dateInteger != null && dateInteger != 0) {
            if (format != null && !format.isEmpty()) {
                try {
                    DateFormat dayFormat = new SimpleDateFormat("yyyyMMdd");
                    Date date = dayFormat.parse(String.valueOf(dateInteger));
                    SimpleDateFormat sdf = new java.text.SimpleDateFormat(format);
                    result = sdf.format(date);
                } catch (ParseException ex) {
                    logger.error("解析日期出现问题", ex);
                }
            }
        }
        return result;

    }

    public static int getDateInSecondes(Integer birthday) {
        int dateSeconds = 0;
        DateFormat dayFormat = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date();
        try {
            date = dayFormat.parse(String.valueOf(birthday));
            dateSeconds = (int) (date.getTime() / 1000l);
        } catch (ParseException ex) {
            logger.error("解析日期出现问题", ex);
        }

        return dateSeconds;

    }

    public static String getWeek(long currentTime, int daysOfWeek) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());
        cal.add(Calendar.DATE, daysOfWeek);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Timestamp timestamp = new Timestamp(cal.getTimeInMillis());
        return dateFormat.format(timestamp);
    }

    public static String getMonth(long currentTime, int month) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());
        cal.add(Calendar.MONTH, month);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Timestamp timestamp = new Timestamp(cal.getTimeInMillis());
        return dateFormat.format(timestamp);
    }

    public static String getHour(long currentTime, int hours) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());
        cal.add(Calendar.HOUR_OF_DAY, hours);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Timestamp timestamp = new Timestamp(cal.getTimeInMillis());
        return dateFormat.format(timestamp);
    }

    public static Timestamp getHourTimestamp(long currentTime, int hours) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());
        cal.add(Calendar.HOUR_OF_DAY, hours);
        Timestamp timestamp = new Timestamp(cal.getTimeInMillis());
        return timestamp;
    }

    public static String currentDay() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(timestamp);
    }

    public static String currentYYYYMMDDDay() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(timestamp);
    }

    public static String currentYYYYMMDay() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
        return dateFormat.format(timestamp);
    }
}
