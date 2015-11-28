package com.example.hreeels.salsainottawa.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Hreeels on 2015-11-28.
 */
public class DateUtils {

    /**
     * Returns the current system date and time.
     *
     * @return Calendar object of current system date and time
     */
    public static GregorianCalendar getCurrentCalendar() {
        return new GregorianCalendar();
    }

    /**
     * Creates a custom date with a year, month, and date.
     *
     * @param aYear the year of the date
     * @param aMonth the month of the date
     * @param aDate the day of the date
     * @return the Date object
     */
    public static Date createCustomDate(int aYear, int aMonth, int aDate) {
        GregorianCalendar lCalendar = DateUtils.getCurrentCalendar();

        lCalendar.set(Calendar.YEAR, aYear);
        lCalendar.set(Calendar.MONTH, aMonth);
        lCalendar.set(Calendar.DATE, aDate);

        return DateUtils.getDateLowerBound(lCalendar.getTime());
    }

    /**
     * Returns the lowest time of the given date.
     * For example: For a given date Nov 28, 2015
     * - this function would return Nov 28, 2015 00:00:00.
     *
     * @param aDate the Date to be set to the lower bound
     * @return the update Date object
     */
    public static Date getDateLowerBound(Date aDate) {
        GregorianCalendar lCalendar = new GregorianCalendar();
        lCalendar.setTime(aDate);

        lCalendar.set(Calendar.HOUR_OF_DAY, 0);
        lCalendar.set(Calendar.MINUTE, 0);
        lCalendar.set(Calendar.SECOND, 0);

        return lCalendar.getTime();
    }

    /**
     * Returns the highest time of the given date.
     * For example: For a given date Nov 28, 2015
     * - this function would return Nov 28, 2015 23:59:59.
     *
     * @param aDate the Date to be set to the lower bound
     * @return the update Date object
     */
    public static Date getDateUpperBound(Date aDate) {
        GregorianCalendar lCalendar = new GregorianCalendar();
        lCalendar.setTime(aDate);

        lCalendar.set(Calendar.HOUR_OF_DAY, 23);
        lCalendar.set(Calendar.MINUTE, 59);
        lCalendar.set(Calendar.SECOND, 59);

        return lCalendar.getTime();
    }

    /**
     * Returns the absolute first minute of today's date.
     * For example: Nov 28, 2015 00:00:00 EST
     *
     * @return the Date set to the first minute of today
     */
    public static Date getTodayLowerBound() {
        return DateUtils.getDateLowerBound(
                new GregorianCalendar().getTime());
    }

    /**
     * Returns the absolute last minute of today's date.
     * For example: Nov 28, 2015 23:59:59 EST
     *
     * @return the Date set to the last minute of today
     */
    public static Date getTodayUpperBound() {
        return DateUtils.getDateUpperBound(
                new GregorianCalendar().getTime());
    }

    /**
     * Returns the Date for the start of the weekend (Friday 00:00:00).
     *
     * @return the Date signifying the start of the weekend
     */
    public static Date getWeekendLowerBound() {
        GregorianCalendar lCalendar = new GregorianCalendar();
        lCalendar.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);

        return getDateLowerBound(lCalendar.getTime());
    }

    /**
     * Returns the Date for the end of the weekend (Sunday 00:00:00).
     *
     * @return the Date signifying the start of the weekend
     */
    public static Date getWeekendUpperBound() {
        GregorianCalendar lCalendar = new GregorianCalendar();
        lCalendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);

        return getDateUpperBound(lCalendar.getTime());
    }

    /**
     * Converts a Date object into a Calendar object.
     *
     * @param aDate the date object to be converted
     * @return the converted Calendar Object
     */
    public static GregorianCalendar dateToCalendar(Date aDate) {
        GregorianCalendar lCalendar = new GregorianCalendar();

        lCalendar.setTime(aDate);

        return lCalendar;
    }

    /**
     * Applies the given amount of offset to the hour of the date
     * provided. Offset can be both negative and positive.
     *
     * @param aDate The Date object to be offset for hour of day
     * @param aOffsetAmount the amount to offset by
     * @return the new Date object offset for hour of day
     */
    public static Date applyHourOffsetToDate(Date aDate, int aOffsetAmount) {
        GregorianCalendar lCalendar = DateUtils.dateToCalendar(aDate);

        lCalendar.add(Calendar.HOUR_OF_DAY, aOffsetAmount);

        return lCalendar.getTime();
    }

    /**
     * Applies the given amount of offset to the seconds of the date
     * provided. Offset can be both negative and positive.
     *
     * @param aDate The Date object to be offset for seconds
     * @param aOffsetAmount the amount to offset by
     * @return the new Date object offset for seconds
     */
    public static Date applySecondsOffsetToDate(Date aDate, int aOffsetAmount) {
        GregorianCalendar lCalendar = DateUtils.dateToCalendar(aDate);

        lCalendar.add(Calendar.SECOND, aOffsetAmount);

        return lCalendar.getTime();
    }
}
