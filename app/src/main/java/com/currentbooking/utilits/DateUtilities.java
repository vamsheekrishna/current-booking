package com.currentbooking.utilits;

import android.text.TextUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
            DateFormat formatter = new SimpleDateFormat(CALENDAR_DATE_FORMAT_TWO, locale); //dd MMM yyyy
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
    public static String getTodayDateString(String dateFormat) {
        Locale locale = Locale.getDefault();
        Calendar cal = Calendar.getInstance(locale);
        DateFormat formatter = new SimpleDateFormat(dateFormat, locale);
        return formatter.format(cal.getTime());
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

    public static Calendar getCalendarFromDate2(String dateValue) {
        Locale locale = Locale.getDefault();
        Calendar calendar = Calendar.getInstance();
        if (TextUtils.isEmpty(dateValue)) {
            return calendar;
        }
        try {
            DateFormat formatter = new SimpleDateFormat(CALENDAR_DATE_TIME_FORMAT_TWO, locale); //dd-MM-yyyy
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

        if (diffMinutes > 0 && diffMinutes <= 15) {
            diffMinutes = 15;
        } else if (diffMinutes > 15 && diffMinutes <= 30) {
            diffMinutes = 30;
        } else if (diffMinutes > 30 && diffMinutes <= 45) {
            diffMinutes = 45;
        } else if (diffMinutes > 45) {
            diffMinutes = 0;
            diffHours += 1;
        }

        if (diffDays == 0) {
            return String.format(Locale.getDefault(), "%02d:%02d", diffHours, diffMinutes);
        } else {
            return String.format(Locale.getDefault(), "%02d:%02d:%02d", diffDays, diffHours, diffMinutes);
        }
    }

    public static int getAgeDifference(Calendar dateOfBirthCalendar) {
        Calendar currentCalendar = Calendar.getInstance();
        return currentCalendar.get(Calendar.YEAR) - dateOfBirthCalendar.get(Calendar.YEAR);
    }

    public static String getDateOfBirthFromCalendar(Calendar calendar) {
        Locale locale = Locale.getDefault();
        try {
            DateFormat formatter = new SimpleDateFormat(CALENDAR_DATE_FORMAT_ONE, locale); //dd-MM-yyy
            return formatter.format(calendar.getTime());
        } catch (Exception ex) {
            LoggerInfo.errorLog("getDateStringFromCalendar1", ex.getMessage());
        }

        return getDateOfBirthFromCalendar(Calendar.getInstance(locale));
    }

    public static String getDateOfBirthFromCalendar1(Calendar calendar) {
        Locale locale = Locale.getDefault();
        try {
            DateFormat formatter = new SimpleDateFormat(CALENDAR_DATE_FORMAT_TWO, locale); //dd MMM yyy
            return formatter.format(calendar.getTime());
        } catch (Exception ex) {
            LoggerInfo.errorLog("getDateStringFromCalendar1", ex.getMessage());
        }

        return getDateOfBirthFromCalendar(Calendar.getInstance(locale));
    }



    public static String getCurrentDate() {
        Locale locale = Locale.getDefault();
        Calendar calendar = Calendar.getInstance();
        try {
            DateFormat formatter = new SimpleDateFormat(CALENDAR_DATE_FORMAT_TWO, locale);
            return formatter.format(calendar.getTime());
        } catch (Exception ex) {
            LoggerInfo.errorLog("getCurrentDate", ex.getMessage());
        }
        return getCurrentDate();
    }

    public static String getCalendarFromMultipleDateFormats1(String pDate) {
        Calendar calendar = Calendar.getInstance();
        if (TextUtils.isEmpty(pDate)) {
            return getDateOfBirthFromCalendar1(calendar);
        }
        Locale locale = Locale.getDefault();
        SimpleDateFormat[] formats =
                new SimpleDateFormat[]{new SimpleDateFormat(CALENDAR_DATE_FORMAT_ONE, locale), new SimpleDateFormat(CALENDAR_DATE_FORMAT_TWO, locale)};
        Date parsedDate;
        for (SimpleDateFormat format : formats) {
            try {
                parsedDate = format.parse(pDate);
                if(parsedDate != null) {
                    calendar.setTime(parsedDate);
                    return getDateOfBirthFromCalendar1(calendar);
                }
            } catch (ParseException e) {
            }
        }
        return getDateOfBirthFromCalendar1(calendar);
    }

    public static Calendar getCalendarFromMultipleDateFormats2(String pDate) {
        Calendar calendar = Calendar.getInstance();
        if (TextUtils.isEmpty(pDate)) {
            return calendar;
        }
        Locale locale = Locale.getDefault();
        SimpleDateFormat[] formats =
                new SimpleDateFormat[]{new SimpleDateFormat(CALENDAR_DATE_FORMAT_ONE, locale), new SimpleDateFormat(CALENDAR_DATE_FORMAT_TWO, locale)};
        Date parsedDate;
        for (SimpleDateFormat format : formats) {
            try {
                parsedDate = format.parse(pDate);
                if(parsedDate != null) {
                    calendar.setTime(parsedDate);
                    return calendar;
                }
            } catch (ParseException e) {
            }
        }
        return calendar;
    }



    private static String CALENDAR_DATE_FORMAT_ONE = "dd-MM-yyyy";
    private static String CALENDAR_DATE_FORMAT_TWO = "dd MMM yyyy";
    public static String CALENDAR_DATE_FORMAT_THREE = "yyyy-MM-dd";
    private static String CALENDAR_TIME_FORMAT_ONE = "hh:mm";
    private static String CALENDAR_TIME_FORMAT_TWO = "HH:mm";
    private static String CALENDAR_DATE_TIME_FORMAT_ONE = "yyyy-MM-dd HH:mm:ss"; // 2020-06-21 15:00:00
    private static String CALENDAR_DATE_TIME_FORMAT_TWO = "dd-MM-yyyy";
    private static String CALENDAR_DATE_TIME_FORMAT_THREE = "dd/MM/yyyy HH:mm:ss";
    private static String CALENDAR_DATE_TIME_FORMAT_FOUR = "dd/MM/yyyy hh:mm:ss a";
    private static String CALENDAR_TIME_IN_MILLI_SECS_REG_EXP = "[^\\d.]";
}
