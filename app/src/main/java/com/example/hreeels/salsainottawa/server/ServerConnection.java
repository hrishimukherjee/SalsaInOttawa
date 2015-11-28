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
import java.util.Calendar;
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
        aLowerBound = DateUtils.applyHourOffsetToDate(aLowerBound, Constants.PST_TO_EST_OFFSET);
        aUpperBound = DateUtils.applyHourOffsetToDate(aUpperBound, Constants.PST_TO_EST_OFFSET);

        // BUG: Offset lower bound by -1 second since greaterThanOrEqualTo behaving as greaterThan
        aLowerBound = DateUtils.applySecondsOffsetToDate(aLowerBound, -1);

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

                    // Pass in the results to the reciewer class
                    aReceiverClass.queryDone(iQueryResults);
                } else {
                    Log.d("getAll" +
                            "BetweenDates()", "Error: " + e.getMessage());
                }
            }
        });
    }

}
