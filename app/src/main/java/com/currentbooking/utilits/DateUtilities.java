package com.currentbooking.utilits;

import android.text.TextUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Period;
import java.time.temporal.Temporal;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Satya Seshu on 21/06/20.
 */
public class DateUtilities {

    public static String getTimeFromDate(String dateValue) {
        Locale locale = Locale.getDefault();
        if (TextUtils.isEmpty(dateValue)) {
            return getTimeFromCalendar(Calendar.getInstance(locale));
        }
        try {
            DateFormat formatter = new SimpleDateFormat(CALENDAR_DATE_FORMAT_TWO, locale); //MM/dd/yyyy
            Date date = formatter.parse(dateValue);
            Calendar cal = Calendar.getInstance(locale);
            if (date != null) {
                cal.setTime(date);
            }
            return getTimeFromCalendar(cal);
        } catch (ParseException ex) {
            LoggerInfo.errorLog("", ex.getMessage());
        }

        return getTimeFromCalendar(Calendar.getInstance(locale));
    }

    public static Calendar getCalendarFromDate(String dateValue) {
        Locale locale = Locale.getDefault();
        Calendar calendar = Calendar.getInstance();
        if (TextUtils.isEmpty(dateValue)) {
            return calendar;
        }
        try {
            DateFormat formatter = new SimpleDateFormat(CALENDAR_DATE_TIME_FORMAT_ONE, locale); //yyyy-MM-dd HH:mm:ss
            Date date = formatter.parse(dateValue);
            if (date != null) {
                calendar.setTime(date);
            }
            return calendar;
        } catch (ParseException ex) {
            LoggerInfo.errorLog("", ex.getMessage());
        }

        return calendar;
    }

    public static String getTimeFromCalendar(Calendar calendar) {
        Locale locale = Locale.getDefault();
        try {
            DateFormat formatter = new SimpleDateFormat(CALENDAR_TIME_FORMAT_TWO, locale);
            return formatter.format(calendar.getTime());
        } catch (Exception ex) {
            LoggerInfo.errorLog("getDateStringFromCalendar1", ex.getMessage());
        }

        return getTimeFromCalendar(Calendar.getInstance(locale));
    }

    public static String getJourneyHours(Calendar startJourneyCalendar, Calendar endJourneyCalendar) {
        long difference = endJourneyCalendar.getTime().getTime() - startJourneyCalendar.getTime().getTime();

        //long diffSeconds = difference / 1000 % 60;
        long diffMinutes = difference / (60 * 1000) % 60;
        long diffDays = difference / (24 * 60 * 60 * 1000);
        long diffHours = difference / (60 * 60 * 1000) % 24;

        if(diffMinutes <= 15) {
            diffMinutes = 15;
        } else if(diffMinutes <= 30) {
            diffMinutes = 30;
        } else if(diffMinutes <= 45) {
            diffMinutes = 45;
        } else {
            diffMinutes = 0;
            diffHours += 1;
        }

        if(diffDays == 0) {
            return String.format(Locale.getDefault(), "%02d:%02d", diffHours, diffMinutes);
        } else {
            return String.format(Locale.getDefault(), "%02d:%02d:%02d", diffDays, diffHours, diffMinutes);
        }
    }

    private static String CALENDAR_DATE_FORMAT_ONE = "dd/MM/yyyy";
    private static String CALENDAR_DATE_FORMAT_TWO = "MM/dd/yyyy";
    private static String CALENDAR_TIME_FORMAT_ONE = "hh:mm";
    private static String CALENDAR_TIME_FORMAT_TWO = "HH:mm";
    private static String CALENDAR_DATE_TIME_FORMAT_ONE = "yyyy-MM-dd HH:mm:ss"; // 2020-06-21 15:00:00
    private static String CALENDAR_DATE_TIME_FORMAT_TWO = "MM/dd/yyyy hh:mm:ss a";
    private static String CALENDAR_DATE_TIME_FORMAT_THREE = "dd/MM/yyyy HH:mm:ss";
    private static String CALENDAR_DATE_TIME_FORMAT_FOUR = "dd/MM/yyyy hh:mm:ss a";
    private static String CALENDAR_TIME_IN_MILLI_SECS_REG_EXP = "[^\\d.]";
}
