package com.gateway.domain;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;

/**
 * @author 信息化管理部-方波
 * @site http://www.cr121.com/
 * @company 中铁十二局集团第一工程有限公司
 * @create 2021-03-02 9:09
 */
public class DateUtils {
    /**
     * LocalDateTime 1970-01-01 00:00:00 = 0
     * Excel Double 1900-1-1 00:00:00 = 0
     * Excel 1900-1-1 00:00:00 转 LocalDateTime 1970-01-01 00:00:00
     */
    private static final double EXCEL_DATETIME_TIMESPAN = 25569f;
    private static final double SINGLE_DAY_TIMESTAMP = 86400000f;

    public enum DATETIME_FORMAT {
        /**
         * 年-月-天 时:分:秒 格式
         */
        DEFAULT("yyyy-MM-dd HH:mm:ss"),
        /**
         * 年-月-天 格式
         */
        YEAR_DAYS("yyyy-MM-dd"),
        /**
         * 年-月-天 时 格式
         */
        YEAR_HOURS("yyyy-MM-dd HH"),
        /**
         * 年-月-天 时 格式
         */
        YEAR_MINUTES("yyyy-MM-dd HH:mm"),
        /**
         * 年-月-天 时:分:秒 格式
         */
        YEAR_SECONDS("yyyy-MM-dd HH:mm:ss"),
        /**
         * 年-月-天 时:分:秒:毫秒 格式
         */
        YEAR_MILLIS("yyyy-MM-dd HH:mm:ss:SSS"),
        /**
         * 月-天 格式
         */
        MONTH_DAYS("MM-dd"),
        /**
         * 时:分 格式
         */
        HOUR_MINUTES("HH:mm"),
        /**
         * 时:分:秒 格式
         */
        HOUR_SECONDS("HH:mm:ss"),
        /**
         * 时:分:秒:毫秒 格式
         */
        HOUR_MILLIS("HH:mm:ss:SSS"),
        /**
         * 月-天 时 格式
         */
        MONTH_HOUR("MM-dd HH"),
        /**
         * 月-天 时:分 格式
         */
        MONTH_MINUTES("MM-dd HH:mm"),
        /**
         * 月-天 时:分:秒 格式
         */
        MONTH_SECONDS("MM-dd HH:mm:ss"),
        /**
         * 月-天 时:分:秒:毫秒 格式
         */
        MONTH_MILLIS("MM-dd HH:mm:ss:SSS"),
        /**
         * 天 时 格式
         */
        DAY_HOUR("dd HH"),
        /**
         * 天 时:分 格式
         */
        DAY_MINUTES("dd HH:mm"),
        /**
         * 天 时:分:秒 格式
         */
        DAY_SECONDS("dd HH:mm:ss"),
        /**
         * 天 时:分:秒:毫秒 格式
         */
        DAY_MILLIS("dd HH:mm:ss:SSS");


        private String format;

        DATETIME_FORMAT(String format) {
            this.format = format;
        }

        public String getFormat() {
            return format;
        }
    }

    public enum DATETIME_TIMEZONE {
        /**
         * Asia/Shanghai
         * 北京时区
         */
        DEFAULT("GMT"),
        /**
         * Greenwich Mean Time
         * 格林威治标准间
         */
        GMT("GMT+8"),
        /**
         * Coordinated Universal Time
         * 世界协调时间
         */
        UTC("UTC+8"),
        /**
         * Central Standard Time
         * (USA) UT-6:00
         * (Australia) UT+9:30
         * (Asia/Shanghai)UT+8:00
         * (Cuba)UT-4:00
         * 同时表示美国，澳大利亚，中国，古巴四个国家的标准时间
         */
        CST("CST"),
        /**
         * Daylight Saving Time
         * 夏日节约时间
         */
        DST("DST"),
        /**
         * Pacific Daylight Time
         * 太平洋夏令时间
         */
        PDT("PDT-15"),
        /**
         * Pacific Standard Time
         * 太平洋标准时间
         */
        PST("PST-16"),
        /**
         * Asia/Shanghai
         * 北京时区
         */
        ASIA_SHANGHAI("Asia/Shanghai");


        private String timezone;

        DATETIME_TIMEZONE(String timezone) {
            this.timezone = timezone;
        }

        public String getTimezone() {
            return timezone;
        }
    }

    /**
     * LocalDateTime 时区设置
     *
     * @param timezone
     * @return
     */
    public static ZoneId getLocalDateTimeZone(DATETIME_TIMEZONE timezone) {
        return ZoneId.of(timezone.getTimezone());
    }

    /**
     * Date 转换成 LocalDateTime 对象
     *
     * @param date
     * @return
     */
    public static LocalDateTime convertDateToLocalDateTime(Date date) {
        ZoneId zoneId = ZoneId.systemDefault();
        Instant instant = Instant.ofEpochMilli(date.getTime());
        ZonedDateTime zonedDateTime = instant.atZone(zoneId);

        return zonedDateTime.toLocalDateTime();
    }

    /**
     * localDateTime 转换成 Date 对象
     *
     * @param localDateTime
     * @return
     */
    public static Date convertLocalDateTimeToDate(LocalDateTime localDateTime) {
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zonedDateTime = localDateTime.atZone(zoneId);
        return Date.from(zonedDateTime.toInstant());
    }

    /**
     * LocalDateTime 转换成 Timestamp(long) 对象
     *
     * @param localDateTime
     * @return
     */
    public static long convertLocalDateTimeToTimestamp(LocalDateTime localDateTime) {
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDateTime.atZone(zone).toInstant();
        return instant.toEpochMilli();
    }

    /**
     * Timestamp(long) 转换成 LocalDateTime 对象
     *
     * @param timestamp
     * @return
     */
    public static LocalDateTime convertTimestampToLocalDateTime(long timestamp) {
        Instant instant = Instant.ofEpochMilli(timestamp);
        ZoneId zone = ZoneId.systemDefault();
        return LocalDateTime.ofInstant(instant, zone);
    }

    /**
     * ExcelTimestamp(long) 转换成 LocalTimestamp(long) 对象
     *
     * @param timestamp
     * @return
     */
    public static long convertExcelTimestampToLocalTimestamp(long timestamp) {
        Instant instant = Instant.ofEpochMilli(timestamp);
        ZoneId zone = getLocalDateTimeZone(DATETIME_TIMEZONE.DEFAULT);
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
        return convertLocalDateTimeToTimestamp(localDateTime);
    }

    /**
     * LocalTimestamp(long) 转换成 ExcelTimestamp(long) 对象
     *
     * @param timestamp
     * @return
     */
    public static long convertLocalTimestampToExcelTimestamp(long timestamp) {
        LocalDateTime localDateTime = convertTimestampToLocalDateTime(timestamp);
        ZoneId zone = getLocalDateTimeZone(DATETIME_TIMEZONE.DEFAULT);
        Instant instant = localDateTime.atZone(zone).toInstant();
        return instant.toEpochMilli();
    }

    /**
     * ExcelDateTime(double) 转换成 Timestamp(long) 对象
     *
     * @param excelDateTime
     * @return
     */
    public static long convertExcelDateTimeToTimestamp(double excelDateTime) {
        double timestamp = Math.round((excelDateTime-EXCEL_DATETIME_TIMESPAN)*SINGLE_DAY_TIMESTAMP);
        long localTimestamp = new Double(timestamp).longValue();
        return convertExcelTimestampToLocalTimestamp(localTimestamp);
    }

    /**
     * Timestamp(long) 转换成 ExcelDateTime(double) 对象
     *
     * @param timestamp
     * @return
     */
    public static double convertTimestampToExcelDateTime(long timestamp) {
        long excelTimestamp = convertLocalTimestampToExcelTimestamp(timestamp);
        return excelTimestamp/SINGLE_DAY_TIMESTAMP+EXCEL_DATETIME_TIMESPAN;
    }

    /**
     * ExcelDateTime(double) 转换成 LocalDateTime 对象
     *
     * @param excelDateTime
     * @return
     */
    public static LocalDateTime convertExcelDateTimeToLocalDateTime(double excelDateTime) {
        return convertTimestampToLocalDateTime(convertExcelDateTimeToTimestamp(excelDateTime));
    }

    /**
     * LocalDateTime 转换成 ExcelDateTime(double) 对象
     *
     * @param localDateTime
     * @return
     */
    public static double convertLocalDateTimeToExcelDateTime(LocalDateTime localDateTime) {
        return convertTimestampToExcelDateTime(convertLocalDateTimeToTimestamp(localDateTime));
    }

    /**
     * 根据 LocalDateTime 时间字符串 转换成 LocalDateTime 对象
     *
     * @param localDateTime
     * @param format
     * @return
     */
    public static LocalDateTime parseLocalDateTime(String localDateTime, DATETIME_FORMAT format) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format.getFormat());
        return LocalDateTime.parse(localDateTime, dateTimeFormatter);
    }

    public static LocalDateTime parseLocalDateTime(String localDateTime, String format) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
        return LocalDateTime.parse(localDateTime, dateTimeFormatter);
    }

    /**
     * 根据 ExcelDateTime(double) 时间字符串 转换成 ExcelDateTime(double) 对象
     *
     * @param excelDateTime
     * @param format
     * @return
     */
    public static double parseExcelDateTime(String excelDateTime, DATETIME_FORMAT format) {
        return convertLocalDateTimeToExcelDateTime(parseLocalDateTime(excelDateTime, format));
    }

    public static double parseExcelDateTime(String excelDateTime, String format) {
        return convertLocalDateTimeToExcelDateTime(parseLocalDateTime(excelDateTime, format));
    }

    /**
     * LocalDateTime 格式化成 LocalDateTime 时间字符串
     *
     * @param localDateTime
     * @param format
     * @return
     */
    public static String formatLocalDateTime(LocalDateTime localDateTime, DATETIME_FORMAT format) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format.getFormat());
        return dateTimeFormatter.format(localDateTime);
    }

    public static String formatLocalDateTime(LocalDateTime localDateTime, String format) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
        return dateTimeFormatter.format(localDateTime);
    }

    /**
     * ExcelDateTime(double) 格式化成 ExcelDateTime(double) 时间字符串
     *
     * @param excelDateTime
     * @param format
     * @return
     */
    public static String formatExcelDateTime(double excelDateTime, DATETIME_FORMAT format) {
        return formatLocalDateTime(convertExcelDateTimeToLocalDateTime(excelDateTime), format);
    }

    public static String formatExcelDateTime(double excelDateTime, String format) {
        return formatLocalDateTime(convertExcelDateTimeToLocalDateTime(excelDateTime), format);
    }


    /**
     * LocalDateTime 开始时间，结束时间进行对比
     *
     * @param startLocalDateTime 开始时间
     * @param endLocalDateTime 结束时间
     * @return
     */
    public static int compareLocalDateTime(LocalDateTime startLocalDateTime, LocalDateTime endLocalDateTime) {
        return startLocalDateTime.compareTo(endLocalDateTime);
    }

    /**
     * LocalDateTime 开始时间，结束时间进行对比
     *
     * @param startLocalDateTime 开始时间
     * @param endLocalDateTime 结束时间
     * @return
     */
    public static boolean equalLocalDateTime(LocalDateTime startLocalDateTime, LocalDateTime endLocalDateTime) {
        return startLocalDateTime.isEqual(endLocalDateTime);
    }

    /**
     * LocalDateTime 开始时间，结束时间进行对比
     *
     * @param startLocalDateTime 开始时间
     * @param endLocalDateTime 结束时间
     * @return
     */
    public static boolean beforeLocalDateTime(LocalDateTime startLocalDateTime, LocalDateTime endLocalDateTime) {
        return startLocalDateTime.isBefore(endLocalDateTime);
    }

    /**
     * LocalDateTime 开始时间，结束时间进行对比
     *
     * @param startLocalDateTime 开始时间
     * @param endLocalDateTime 结束时间
     * @return
     */
    public static boolean afterLocalDateTime(LocalDateTime startLocalDateTime, LocalDateTime endLocalDateTime) {
        return startLocalDateTime.isAfter(endLocalDateTime);
    }

    /**
     * ExcelDateTime(double) 开始时间，结束时间进行对比
     *
     * @param startExcelDateTime 开始时间
     * @param endExcelDateTime 结束时间
     * @return
     */
    public static int compareExcelDateTime(double startExcelDateTime, double endExcelDateTime) {
        return Double.compare(startExcelDateTime, endExcelDateTime);
    }

    /**
     * ExcelDateTime(double) 开始时间，结束时间进行对比
     *
     * @param startExcelDateTime 开始时间
     * @param endExcelDateTime 结束时间
     * @return
     */
    public static boolean equalExcelDateTime(double startExcelDateTime, double endExcelDateTime) {
        return Double.compare(startExcelDateTime, endExcelDateTime) == 0;
    }

    /**
     * ExcelDateTime(double) 开始时间，结束时间进行对比
     *
     * @param startExcelDateTime 开始时间
     * @param endExcelDateTime 结束时间
     * @return
     */
    public static boolean beforeExcelDateTime(double startExcelDateTime, double endExcelDateTime) {
        return Double.compare(startExcelDateTime, endExcelDateTime) < 0;
    }

    /**
     * ExcelDateTime(double) 开始时间，结束时间进行对比
     *
     * @param startExcelDateTime 开始时间
     * @param endExcelDateTime 结束时间
     * @return
     */
    public static boolean afterExcelDateTime(double startExcelDateTime, double endExcelDateTime) {
        return Double.compare(startExcelDateTime, endExcelDateTime) > 0;
    }


    /**
     * 获取当前日期时间 LocalDateTime 格式
     *
     * @return
     */
    public static LocalDateTime currentLocalDateTime() {
        return LocalDateTime.now();
    }

    /**
     * 获获取当前日期时间 ExcelDateTime(double) 格式
     * @return
     */
    public static double currentExcelDateTime() {
        return convertLocalDateTimeToExcelDateTime(LocalDateTime.now());
    }

    /**
     * LocalDateTime 格式化成 LocalDateTime 时间字符串
     *
     * @param format
     * @return
     */
    public static String formatCurrentLocalDateTime(DATETIME_FORMAT format) {
        return formatLocalDateTime(LocalDateTime.now(), format);
    }

    /**
     * ExcelDateTime(double) 格式化成 ExcelDateTime(double) 时间字符串
     *
     * @param format
     * @return
     */
    public static String formatCurrentExcelDateTime(DATETIME_FORMAT format) {
        return formatExcelDateTime(currentExcelDateTime(), format);
    }


    /**
     * LocalDateTime 增加为新的 LocalDateTime 对象
     *
     * @param localDateTime
     * @param value
     * @param unit
     * @return
     */
    public static LocalDateTime plusLocalDateTime(LocalDateTime localDateTime, long value, ChronoUnit unit) {
        return localDateTime.plus(value, unit);
    }

    /**
     * LocalDateTime 减少为新的 LocalDateTime 对象
     *
     * @param localDateTime
     * @param value
     * @param unit
     * @return
     */
    public static LocalDateTime minusLocalDateTime(LocalDateTime localDateTime, long value, ChronoUnit unit) {
        return localDateTime.minus(value, unit);
    }

    /**
     * 计算两个 LocalDateTime 时间间隔
     * @param startLocalDateTime
     * @param endLocalDateTime
     * @param unit
     * @return
     */
    public static long getDurationOfLocalDateTime(LocalDateTime startLocalDateTime, LocalDateTime endLocalDateTime, ChronoUnit unit) {
        Duration duration = Duration.between(startLocalDateTime, endLocalDateTime);
        if(unit == ChronoUnit.DAYS) {
            return duration.toDays();
        } else if(unit == ChronoUnit.HOURS) {
            return duration.toHours();
        } else if(unit == ChronoUnit.MINUTES) {
            return duration.toMinutes();
        } else if(unit == ChronoUnit.SECONDS) {
            return duration.toMillis()/1000;
        } else if(unit == ChronoUnit.MILLIS) {
            return duration.toMillis();
        } else if(unit == ChronoUnit.NANOS) {
            return duration.toNanos();
        }

        return duration.toMillis();
    }


    /**
     * 获取 LocalDateTime 本月第一天
     */
    public static LocalDateTime getFirstDayOfMonth() {
        LocalDateTime localDateTime = LocalDateTime.now();
        return localDateTime.with(TemporalAdjusters.firstDayOfMonth());
    }

    /**
     * 获取 LocalDateTime 本月最后一天
     */
    public static LocalDateTime getLastDayOfMonth() {
        LocalDateTime localDateTime = LocalDateTime.now();
        return localDateTime.with(TemporalAdjusters.lastDayOfMonth());
    }

    /**
     * 获得 LocalDateTime 某天最大时间 1970-01-01 23:59:59
     *
     * @param localDateTime
     * @return
     */
    public static LocalDateTime getMaxTimeOfDay(LocalDateTime localDateTime) {
        return localDateTime.with(LocalTime.MAX);
    }

    /**
     * 获得 LocalDateTime 某天最小时间 1970-01-01 23:59:59
     *
     * @param localDateTime
     * @return
     */
    public static LocalDateTime getMinTimeOfDay(LocalDateTime localDateTime) {
        return localDateTime.with(LocalTime.MIN);
    }

    /**
     * 获取与当前时间的日期时间间隔 LocalDateTime
     *
     * @param localDateTime
     * @return
     */
    public static long getTimestampOfNow(LocalDateTime localDateTime) {
        return convertLocalDateTimeToTimestamp(LocalDateTime.now()) - convertLocalDateTimeToTimestamp(localDateTime);
    }

    /**
     * 获取与当前时间的日期时间间隔毫秒数 LocalDateTime
     *
     * @param localDateTime
     * @return
     */
    public static String getTimespanMillisOfNow(LocalDateTime localDateTime) {
        return getTimestampOfNow(localDateTime) + " ms";
    }
}
