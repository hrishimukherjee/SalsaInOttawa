package com.example.hreeels.salsainottawa.utils;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import com.example.hreeels.salsainottawa.core.Event;
import com.google.android.gms.maps.model.LatLng;
import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
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
     * Returns a ParseObject for the Event table with the Organizer
     * and Venue pointers. Assumes that the organizer and venue ID being
     * provided already exist in the database.
     *
     * @param aEvent the event to be converted to a parse object
     * @param aOrganizerID the ID of the Organizer
     * @param aVenueID the ID of the Venue
     * @return the converted ParseObject for the "Event" tale
     */
    public static ParseObject getParseEventObject(Event aEvent, String aOrganizerID,
                                           String aVenueID) {
        ParseObject newEvent = new ParseObject("Event");

        ParseObject organizerPointer = ParseObject.createWithoutData("Organizer", aOrganizerID);
        newEvent.put("organizer_id", organizerPointer);

        ParseObject venuePointer = ParseObject.createWithoutData("Venue", aVenueID);
        newEvent.put("venue_id", venuePointer);

        newEvent.put("event_title", aEvent.getTitle());
        newEvent.put("event_date", aEvent.getDate());
        newEvent.put("event_desc", aEvent.getDescription());
        newEvent.put("event_start_time", aEvent.getStartTime());
        newEvent.put("event_end_time", aEvent.getEndTime());
        newEvent.put("event_cost", aEvent.getCost());
        newEvent.put("event_website", aEvent.getWebsite());

        return newEvent;
    }

    /**
     * Sorts the list of events passed into the function by date
     * in ascending order.
     *
     * @param aEventList the list of events to be sorted
     */
    public static void sortEventListByDate(ArrayList<Event> aEventList) {
        Collections.sort(aEventList, new Comparator<Event>() {
            @Override
            public int compare(Event lhs, Event rhs) {
                // If either date is null return 0
                if(lhs.getDate() == null || rhs.getDate() == null) {
                    return 0;
                }

                return lhs.getDate().compareTo(rhs.getDate());
            }
        });
    }
}
