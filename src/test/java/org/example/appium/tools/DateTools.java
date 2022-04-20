package org.example.appium.tools;

import org.apache.commons.lang.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public abstract class DateTools {

    private static Map<String, SimpleDateFormat> formatter = new HashMap<>();
    private static GregorianCalendar calendar = new GregorianCalendar();

    private DateTools() {
    }

    protected static SimpleDateFormat getFormatter(String pattern) {
        return formatter.computeIfAbsent(pattern, SimpleDateFormat::new);
    }

    public static Date now() {
        return new Date();
    }

    public static Date now(int dayShift) {
        return plusDays(now(), dayShift);
    }

    public static String format(Date d, String pattern) {
        if (d == null) {
            return null;
        }
        return getFormatter(pattern).format(d);
    }

    public static Date format(String str, String pattern) {
        if (StringUtils.isEmpty(str)) {
            return null;
        }
        try {
            return getFormatter(pattern).parse(str);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static String formatSimple(Date d) {
        return format(d, "dd.MM.yyyy");
    }

    public static Date formatSimple(String str) {
        return format(str, "dd.MM.yyyy");
    }

    public static Date of(int year, int month, int day) {
        if (month < 0 || month > 11) {
            throw new IllegalStateException("Incorrect month number: " + month);
        }

        calendar.set(year, month, day, 0, 0,0);
        return calendar.getTime();
    }

    public static Date of(int year, int month, int day, int hour, int minute, int second) {
        if (month < 0 || month > 11) {
            throw new IllegalStateException("Incorrect month number: " + month);
        }

        calendar.set(year, month, day, hour, minute, second);
        return calendar.getTime();
    }

    public static Date dropTime(Date d) {
        if (d == null) {
            return null;
        }
        calendar.setTime(d);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        return calendar.getTime();
    }

    public static Date plusDays(Date d, int days) {
        calendar.setTime(d);
        calendar.add(Calendar.DAY_OF_MONTH, days);
        return calendar.getTime();
    }

    public static Date plusYears(Date d, int years) {
        calendar.setTime(d);
        calendar.add(Calendar.YEAR, years);
        return calendar.getTime();
    }

    public static int getYear(Date d) {
        calendar.setTime(d);
        return calendar.get(Calendar.YEAR);
    }

    public static int getMonth(Date d) {
        calendar.setTime(d);
        return calendar.get(Calendar.MONTH);
    }

    public static int getDay(Date d) {
        calendar.setTime(d);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }
}
