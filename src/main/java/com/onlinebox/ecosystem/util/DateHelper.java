package com.onlinebox.ecosystem.util;

import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author cedric
 */
public class DateHelper {

    /**
     * This method returns the specified date at 00h00min00sec
     *
     * @param date
     * @return
     */
    public static Date getMinHour(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * This method returns the specified date at 23h59min59sec
     *
     * @param date
     * @return
     */
    public static Date getMaxHour(Date date) {
        Date d = getMinHour(date);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        calendar.add(Calendar.DATE, 1);
        calendar.add(Calendar.MILLISECOND, -1);

        return calendar.getTime();
    }

    /**
     * This method returns the first day of the month of the specified date
     *
     * @param date
     * @return
     */
    public static Date getFirstDayOfMonth(Date date) {
        Date d = getMinHour(date);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        return calendar.getTime();
    }

    /**
     * This method returns the last day of the month of the specified date
     *
     * @param date
     * @return
     */
    public static Date getLastDayOfMonth(Date date) {
        Date d = getMinHour(date);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DATE, 1);
        calendar.add(Calendar.MILLISECOND, -1);

        return calendar.getTime();
    }

    /**
     * This method returns the first day of the week of the specified date.
     * Note that the first day of the week is Monday.
     * @param date
     * @return 
     */
    public static Date getFirstDayOfWeek(Date date) {
        Date d = getMinHour(date);

        Calendar calendar = Calendar.getInstance();
        
        calendar.setTime(d);
        while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
            calendar.add(Calendar.DATE, -1);
        }
        return calendar.getTime();
    }
    
    /**
     * This method returns the last day of the week of the specified date.
     * Note that the last day of the week is Sunday.
     * @param date
     * @return 
     */
    public static Date getLastDayOfWeek(Date date) {
        Date d = getMaxHour(date);

        Calendar calendar = Calendar.getInstance();
        
        calendar.setTime(d);
        while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
            calendar.add(Calendar.DATE, 1);
        }
        return calendar.getTime();
    }
}
