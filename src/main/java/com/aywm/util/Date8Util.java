package com.aywm.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 基于Java8的时间工具类
 *
 * @author keji
 * @version $Id: DateUtil.java, v 0.1 2018-12-28 14:11 keji Exp $
 */
public class Date8Util {

    /**
     * 例如:2018-12-28
     */
    public static final String DATE = "yyyy-MM-dd";
    /**
     * 例如:2018-12-28 10:00:00
     */
    public static final String DATE_TIME = "yyyy-MM-dd HH:mm:ss";
    /**
     * 例如:10:00:00
     */
    public static final String TIME = "HH:mm:ss";
    /**
     * 例如:10:00
     */
    public static final String TIME_WITHOUT_SECOND = "HH:mm";

    /**
     * 例如:2018-12-28 10:00
     */
    public static final String DATE_TIME_WITHOUT_SECONDS = "yyyy-MM-dd HH:mm";


    /**
     * 获取年
     *
     * @return 年
     */
    public static int getYear() {
        LocalTime localTime = LocalTime.now();
        return localTime.get(ChronoField.YEAR);
    }

    /**
     * 获取月份
     *
     * @return 月份
     */
    public static int getMonth() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.MONTH) + 1;
    }

    /**
     * 获取某月的第几天
     *
     * @return 几号
     */
    public static int getMonthOfDay() {
        LocalTime localTime = LocalTime.now();
        return localTime.get(ChronoField.DAY_OF_MONTH);
    }

    /**
     * 格式化日期为字符串默认yyyy-MM-dd HH:mm:ss
     *
     * @param date date
     * @return 日期字符串
     */
    public static String format(Date date) {

        Instant instant = date.toInstant();

        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());

        return localDateTime.format(DateTimeFormatter.ofPattern(DATE_TIME));
    }

    /**
     * 格式化日期为字符串
     *
     * @param date    date
     * @param pattern 格式
     * @return 日期字符串
     */
    public static String format(Date date, String pattern) {
        Instant instant = date.toInstant();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        return localDateTime.format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 解析字符串日期为Date
     *
     * @param dateStr 日期字符串
     * @param pattern 格式
     * @return Date
     */
    public static Date parse(String dateStr, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        try {
            return format.parse(dateStr);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 为Date增加分钟,减传负数
     *
     * @param date        日期
     * @param plusMinutes 要增加的分钟数
     * @return 新的日期
     */
    public static Date addMinutes(Date date, Long plusMinutes) {
        LocalDateTime dateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        LocalDateTime newDateTime = dateTime.plusMinutes(plusMinutes);
        return Date.from(newDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 为Date增加分钟,减传负数
     *
     * @param date        日期
     * @param plusMinutes 要增加的分钟数
     * @return 新的日期
     */
    public static Date addMinutes(Date date, int plusMinutes) {
        LocalDateTime dateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        LocalDateTime newDateTime = dateTime.plusMinutes(plusMinutes);
        return Date.from(newDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 增加时间
     *
     * @param date date
     * @param hour 要增加的小时数
     * @return new date
     */
    public static Date addHour(Date date, Long hour) {
        LocalDateTime dateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        LocalDateTime localDateTime = dateTime.plusHours(hour);
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * @return 返回当天的起始时间
     */
    public static Date getStartTime() {
        return getStartTime(new Date());
    }

    /**
     * @return 返回当天的起始时间
     */
    public static Date getStartTime(Date date) {
        LocalDateTime dateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        LocalDateTime now = dateTime.withHour(0).withMinute(0).withSecond(0).withNano(0);
        return localDateTime2Date(now);
    }

    /**
     * @return 返回当天的起始时间
     */
    public static String getStartTimeStr(String format) {
        LocalDateTime now = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
        Date date = localDateTime2Date(now);
        return format(date, format);
    }


    /**
     * @return 返回当天的结束时间
     */
    public static Date getEndTime() {
        return getEndTime(new Date());
    }

    /**
     * @return 返回当天的结束时间
     */
    public static Date getEndTime(Date date) {
        LocalDateTime dateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        LocalDateTime now = dateTime.withHour(23).withMinute(59).withSecond(59).withNano(9999);
        return localDateTime2Date(now);
    }

    /**
     * @return 返回当天的结束时间
     */
    public static String getEndTimeStr(String format) {
        LocalDateTime now = LocalDateTime.now().withHour(23).withMinute(59).withSecond(59).withNano(9999);
        Date date = localDateTime2Date(now);
        return format(date, format);
    }

    /**
     * @return 返回现在的时间
     */
    public static Date getNow() {
        LocalDateTime now = LocalDateTime.now();
        return localDateTime2Date(now);
    }

    public static String getNowStr(String format) {
        LocalDateTime now = LocalDateTime.now();
        Date date = localDateTime2Date(now);
        return format(date, format);
    }

    /**
     * @return 返回现在的时间
     */
    public static long getNowTimeStamp() {
        LocalDateTime now = LocalDateTime.now();
        return localDateTime2Date(now).getTime();
    }

    /**
     * 减月份
     *
     * @param monthsToSubtract 月份
     * @return Date
     */
    public static Date minusMonths(long monthsToSubtract) {
        LocalDate localDate = LocalDate.now().minusMonths(monthsToSubtract);

        return localDate2Date(localDate);
    }

    /**
     * LocalDate类型转为Date
     *
     * @param localDate LocalDate object
     * @return Date object
     */
    public static Date localDate2Date(LocalDate localDate) {

        ZonedDateTime zonedDateTime = localDate.atStartOfDay(ZoneId.systemDefault());

        return Date.from(zonedDateTime.toInstant());
    }

    /**
     * LocalDateTime类型转为Date
     *
     * @param localDateTime LocalDateTime object
     * @return Date object
     */
    public static Date localDateTime2Date(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 查询当前年的第一天
     *
     * @param pattern 格式，默认格式yyyyMMdd
     * @return 20190101
     */
    public static String getFirstDayOfCurrentYear(String pattern) {
        LocalDateTime localDateTime = LocalDateTime.now().withMonth(1).withDayOfMonth(1);

        if (StringUtils.isEmpty(pattern)) {
            pattern = "yyyyMMdd";
        }

        return format(localDateTime2Date(localDateTime), pattern);
    }

    /**
     * 查询当月的第一天0点时间
     */
    public static Date getFirstDayOfMonth() {
        LocalDateTime localDateTime = LocalDateTime.now().withMonth(getMonth()).withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0);
        return (localDateTime2Date(localDateTime));
    }

    /**
     * 查询前一年最后一个月第一天
     *
     * @param pattern 格式，默认格式yyyyMMdd
     * @return 20190101
     */
    public static String getLastMonthFirstDayOfPreviousYear(String pattern) {
        LocalDateTime localDateTime = LocalDateTime.now().minusYears(1L).withMonth(12).withDayOfMonth(1);
        if (StringUtils.isEmpty(pattern)) {
            pattern = "yyyyMMdd";
        }
        return format(localDateTime2Date(localDateTime), pattern);
    }

    /**
     * 查询前一年最后一个月第一天
     *
     * @param pattern 格式，默认格式yyyyMMdd
     * @return 20190101
     */
    public static String getLastMonthLastDayOfPreviousYear(String pattern) {
        LocalDateTime localDateTime = LocalDateTime.now().minusYears(1L).with(TemporalAdjusters.lastDayOfYear());
        if (StringUtils.isEmpty(pattern)) {
            pattern = "yyyyMMdd";
        }
        return format(localDateTime2Date(localDateTime), pattern);
    }

    /**
     * 获取当前日期
     *
     * @param pattern 格式，默认格式yyyyMMdd
     * @return 20190101
     */
    public static String getCurrentDay(String pattern) {
        LocalDateTime localDateTime = LocalDateTime.now();

        if (StringUtils.isEmpty(pattern)) {
            pattern = "yyyyMMdd";
        }

        return format(localDateTime2Date(localDateTime), pattern);
    }

    /**
     * 功能: 获取这周周几 周一到周日 1-7
     * 作者: zjt
     * 日期: 2020/4/17 14:59
     * 版本: 1.0
     */
    public static int getWeek() {
        Date today = new Date();
        return getWeek(today);
    }

    /**
     * 功能: 获取这周周几 周一到周日 1-7
     * 作者: zjt
     * 日期: 2020/4/17 14:59
     * 版本: 1.0
     */
    public static int getWeek(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int week = c.get(Calendar.DAY_OF_WEEK);
        week--;
        if (week == 0) {
            week = 7;
        }
        return week;
    }

    /**
     * @return void
     * @Description 计算两个日期间隔的天数
     * @Author yangsu
     * @Date 9:39 2020/6/3
     * @Param []
     **/
    public static long betweenDays(Date begin, Date end) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(begin);
        long time1 = cal.getTimeInMillis();
        cal.setTime(end);
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);
        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     * 获取过去第几天的日期
     *
     * @param pastInt 天数
     * @return long
     * @author WeiQiangMiao
     * @date 2020/6/9
     */
    public static long getPastDate(int pastInt) {
        long createTime = DateUtils.addDays(new Date(), -pastInt + 1).getTime();
        return createTime - ((createTime + 28800000) % (86400000));
    }


    /**
     * 根据天数 过去时间
     *
     * @param pastInt 天数
     * @return java.util.Date
     * @author WeiQiangMiao
     * @version 1.0.0
     * @since 2020/7/6
     */
    public static Date pastDate(int pastInt) {
        LocalDateTime now = LocalDateTime.now().plusDays(-pastInt);
        return localDateTime2Date(now);
    }

    /**
     * 获取某天数的开始时间
     *
     * @return java.util.Date
     * @author WeiQiangMiao
     * @date 2020/6/19
     */
    public static Date getStartTimeCertainDay(Integer day) {
        LocalDateTime now = LocalDateTime.now().plusDays(day).withHour(0).withMinute(0).withSecond(0).withNano(0);
        return localDateTime2Date(now);
    }

    /**
     * 获取某天数的结束时间
     *
     * @return java.util.Date
     * @author WeiQiangMiao
     * @date 2020/6/19
     */
    public static Date getEndTimeCertainDay(Integer day) {
        LocalDateTime now = LocalDateTime.now().plusDays(day).withHour(23).withMinute(59).withSecond(59).withNano(999);
        return localDateTime2Date(now);
    }


    public static String getYesterday(String format) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        return format(cal.getTime(), format);
    }

    public static int dateCompare(Date date1, Date date2) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String dateFirst = dateFormat.format(date1);
        String dateLast = dateFormat.format(date2);
        int dateFirstIntVal = Integer.parseInt(dateFirst);
        int dateLastIntVal = Integer.parseInt(dateLast);
        if (dateFirstIntVal > dateLastIntVal) {
            return 1;
        } else if (dateFirstIntVal < dateLastIntVal) {
            return -1;
        }
        return 0;
    }


    /**
     * 功能: 获取time 格式HH:mm:ss
     * 作者: zjt
     * 日期: 2020/10/21 11:53
     * 版本: 1.0
     */
    public static Time getTime(String time) {
        final Date date = parse(time, TIME);
        return new Time(date.getTime());
    }

    /**
     * 功能: 年月日加时分秒
     * 作者: zjt
     * 日期: 2020/10/27 16:27
     * 版本: 1.0
     */
    public static Date dateAddTime(Date date, String time) {
        final String data = format(date, DATE);
        final String newDate = data + " " + time;
        return parse(newDate, DATE_TIME);
    }

    /**
     * 功能: 年月日加时分秒
     * 作者: zjt
     * 日期: 2020/10/27 16:27
     * 版本: 1.0
     */
    public static Date dateAddTime(Date date, String time, String pattern) {
        final String data = format(date, DATE);
        final String newDate = data + " " + time;
        return parse(newDate, pattern);
    }

    /**
     * 功能: 判断是否是同一天
     * 作者: zjt
     * 日期: 2020/10/28 17:44
     * 版本: 1.0
     */
    public static boolean isSameDay(Date date1, Date date2) {
        return getEndTime(date1).getTime() == getEndTime(date2).getTime();
    }


    /**
     * 功能: 获取某个时间段内所有的日期
     * 作者: zjt
     * 日期: 2020/11/18 15:52
     * 版本: 1.0
     */
    public static List<String> findDates(Date dBegin, Date dEnd, String pattern) {
        List<String> lDate = new ArrayList<>();
        SimpleDateFormat sd = new SimpleDateFormat(pattern);
        lDate.add(sd.format(dBegin));
        Calendar calBegin = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calBegin.setTime(dBegin);
        Calendar calEnd = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calEnd.setTime(dEnd);
        // 测试此日期是否在指定日期之后
        while (dEnd.after(calBegin.getTime()) && !isSameDay(calBegin.getTime(), dEnd)) {
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
            calBegin.add(Calendar.DAY_OF_MONTH, 1);
            lDate.add(sd.format(calBegin.getTime()));
        }
        return lDate;
    }
}
