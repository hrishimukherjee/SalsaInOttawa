package com.example.hreeels.salsainottawa.utils;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import com.google.android.gms.maps.model.LatLng;
import com.parse.ParseObject;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by Hreeels on 2015-11-10.
 */
public class AppUtils {
    /**
     * Returns a printable String for a Parse Object containing
     * event data.
     *
     * @param object Parse Object containing event data
     * @return Formatted String displaying event details
     */
    public static String printParseEventObject(ParseObject object) {
        String lResult = "";

        lResult += "\n" + object.getString("event_title") + "\n=============";
        lResult += "\n" + object.getString("event_desc");
        lResult += "\nDate: " + object.getDate("event_date");
        lResult += "\nStart Time: " + object.getString("event_start_time");
        lResult += "\nEnd Time: " + object.getString("event_end_time");
        lResult += "\nAdmission Cost: " + object.getString("event_cost");
        lResult += "\nEvent Website: " + object.getString("event_website");
        lResult += "\n-----------------------------------";

        return lResult;
    }

    /**
     * Returns a printable String for a Parse Object containing
     * venue data.
     *
     * @param object Parse Object containing venue data
     * @return Formatted String displaying venue details
     */
    public static String printParseVenueObject(ParseObject object) {
        String result = "";

        result += "Venue Name: " + object.getString("venue_name");
        result += "\nAddress: " + object.getString("venue_address");
        result += "\nPhone Number: " + object.getString("venue_phone_number");
        result += "\nWebsite: " + object.getString("venue_website");

        return result;
    }

    /**
     * Returns the name of the month for the provided
     * month number.
     *
     * @param aMonthNumber the month number ranging from 0 - 11
     * @return the name of the corresponding month
     */
    public static String getMonthString(int aMonthNumber) {
        switch(aMonthNumber) {
            case 0: return "January";
            case 1: return "February";
            case 2: return "March";
            case 3: return "April";
            case 4: return "May";
            case 5: return "June";
            case 6: return "July";
            case 7: return "August";
            case 8: return "September";
            case 9: return "October";
            case 10: return "November";
            case 11: return "December";
            default: return "N/A";
        }
    }

    /**
     * Returns a Google Maps LatLng Object from the provided Address.
     *
     * @param context the context this function is called from
     * @param strAddress the address for which to return a LatLng object
     * @return the LatLng object representing the address
     */
    public static LatLng getLocationFromAddress(Context context, String strAddress) {

        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng p1 = null;

        try {
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }
            Address location = address.get(0);
            location.getLatitude();
            location.getLongitude();

            p1 = new LatLng(location.getLatitude(), location.getLongitude() );

        } catch (Exception ex) {

            ex.printStackTrace();
        }

        return p1;
    }

    /**
     * Returns the absolute first minute of today's date.
     * For example: Nov 28, 2015 00:00:00 EST
     *
     * @return the Date set to the first minute of today
     */
    public static Date getFirstMinuteOfToday() {
        GregorianCalendar lCalendar = new GregorianCalendar();

        lCalendar.set(Calendar.HOUR_OF_DAY, 0);
        lCalendar.set(Calendar.MINUTE, 0);
        lCalendar.set(Calendar.SECOND, 0);

        return lCalendar.getTime();
    }

    /**
     * Returns the absolute last minute of today's date.
     * For example: Nov 28, 2015 23:59:59 EST
     *
     * @return the Date set to the last minute of today
     */
    public static Date getLastMinuteOfToday() {
        GregorianCalendar lCalendar = new GregorianCalendar();

        lCalendar.set(Calendar.HOUR_OF_DAY, 23);
        lCalendar.set(Calendar.MINUTE, 59);
        lCalendar.set(Calendar.SECOND, 59);

        return lCalendar.getTime();
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
        GregorianCalendar lCalendar = AppUtils.dateToCalendar(aDate);

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
        GregorianCalendar lCalendar = AppUtils.dateToCalendar(aDate);

        lCalendar.add(Calendar.SECOND, aOffsetAmount);

        return lCalendar.getTime();
    }
}
