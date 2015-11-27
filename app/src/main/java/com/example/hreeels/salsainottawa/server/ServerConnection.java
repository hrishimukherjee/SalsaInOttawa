package com.example.hreeels.salsainottawa.server;

import android.util.Log;

import com.example.hreeels.salsainottawa.core.Event;
import com.example.hreeels.salsainottawa.factory.EventFactory;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
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
    public void getAllEvents(final QueryClient aReceiverClass) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Event");

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
                    Log.d("getAllEvents()", "Error: " + e.getMessage());
                }
            }
        });
    }

}
