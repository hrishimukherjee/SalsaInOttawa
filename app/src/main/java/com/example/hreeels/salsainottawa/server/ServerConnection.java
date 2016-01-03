package com.example.hreeels.salsainottawa.server;

import android.util.Log;

import com.example.hreeels.salsainottawa.core.Event;
import com.example.hreeels.salsainottawa.factory.EventFactory;
import com.example.hreeels.salsainottawa.utils.AppUtils;
import com.example.hreeels.salsainottawa.utils.Constants;
import com.example.hreeels.salsainottawa.utils.DateUtils;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Hreeels on 2015-11-27.
 *
 * This class directly talks to the Parse server,
 * executes queries and retrieves results.
 */
public class ServerConnection {

    private static ServerConnection iServer;

    private ServerConnection() {};

    public synchronized static ServerConnection getDefault() {
        if(iServer == null) {
            iServer = new ServerConnection();
        }

        return iServer;
    }

    /**
     * Connects to Parse and retrieves all the events
     * from the database including their venue and organizer
     * information.
     *
     * @param aReceiverClass the class which called this function
     */
    public void getAllEventsBetweenDates(final QueryClient aReceiverClass,
                                         Date aLowerBound,
                                         Date aUpperBound) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Event");

        /*
        Offset the date filters from PST to EST. This is done because PARSE stores
        dates in PST whereas this app uses EST as it's time-zone.
         */
        aLowerBound = DateUtils.applyHourOffsetToDate(aLowerBound, Constants.UTC_TO_EST_OFFSET);
        aUpperBound = DateUtils.applyHourOffsetToDate(aUpperBound, Constants.UTC_TO_EST_OFFSET);

        // BUG: Offset lower bound by -1 second since greaterThanOrEqualTo behaving as greaterThan
        aLowerBound = DateUtils.applySecondsOffsetToDate(aLowerBound, -1);

        System.out.println(aLowerBound);
        System.out.println(aUpperBound);

        // Apply date filters
        query.whereGreaterThanOrEqualTo("event_date", aLowerBound);
        query.whereLessThanOrEqualTo("event_date", aUpperBound);

        // Include event related venue and organizer objects
        query.include("venue_id");
        query.include("organizer_id");

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> aEventList, ParseException e) {
                if (e == null) {
                    ArrayList<Event> iQueryResults = new ArrayList<Event>();

                    // Transform the event data to event objects and store in a list
                    for (ParseObject lEventData : aEventList) {
                        Event lNewEvent = EventFactory.createEvent(lEventData);

                        iQueryResults.add(lNewEvent);
                    }

                    System.out.println(iQueryResults);

                    // Pass in the results to the reciewer class
                    aReceiverClass.queryDone(iQueryResults);
                } else {
                    Log.d("getAll" +
                            "BetweenDates()", "Error: " + e.getMessage());
                }
            }
        });
    }

    public void addEventToServer(Event aEvent, String aOrgID, String aVenID) {
        ParseObject newEvent = AppUtils.getParseEventObject(aEvent, aOrgID, aVenID);

        newEvent.saveInBackground();
    }

    /**
     * Adds the given event object to the Parse database
     * for the number of times it recurs. The date of the
     * event is incremented by the 'recursEverXDays' parameter,
     * and then added to the database as a new row.
     *
     * @param aEvent
     * @param aOrganizerID
     * @param aVenueID
     * @param aRecursEveryXDays
     * @param aNumRecurs
     */
    public void addRecurringEventToServer(Event aEvent, String aOrganizerID,
                                          String aVenueID, int aRecursEveryXDays,
                                          int aNumRecurs) {
        System.out.println("Preparing recurring event insertion...");
        ArrayList<ParseObject> lEventList = new ArrayList<ParseObject>();

        System.out.println("Size of event list: " + lEventList.size());
        System.out.println("Entering Loop...");
        for(int i = 0; i < aNumRecurs; i++) {
            System.out.println("Converting to Parse Event...");
            ParseObject lParseEvent = AppUtils.getParseEventObject(aEvent, aOrganizerID,
                    aVenueID);

            System.out.println("Adding Parse Event...");
            lEventList.add(lParseEvent);

            System.out.println("Incrementing date (" + i + ")...");
            System.out.println("Old Date: " + aEvent.getDate());
            // Increment the date by the recur amount
            Date lIncrementedDate = DateUtils.applyDayOffsetToDate(aEvent.getDate(),
                    aRecursEveryXDays);

            aEvent.setDate(lIncrementedDate);

            System.out.println("New Date: " + aEvent.getDate());
        }

        ParseObject.saveAllInBackground(lEventList);
    }
}
