package com.example.hreeels.salsainottawa.factory;

import com.example.hreeels.salsainottawa.core.Event;
import com.example.hreeels.salsainottawa.utils.Constants;
import com.example.hreeels.salsainottawa.utils.DateUtils;
import com.parse.ParseObject;

import java.util.Date;

/**
 * Created by Hreeels on 2015-06-30.
 */
public class EventFactory {
    /**
     * Creates an Event Object from the details of the Parse Object.
     * Assumes that the Parse Object contains event details including
     * pointers to the Venue and Organizers.
     *
     * @param aEventData The Parse Object containing the event data
     * @return Returns an Event object
     */
    public static Event createEvent(ParseObject aEventData) {
        Event lEvent = new Event(aEventData.getString("event_title"));

        lEvent.setDescription(aEventData.getString("event_desc"));

        // Apply EST to UTC timezone offset
        Date lDate = DateUtils.applyHourOffsetToDate(aEventData.getDate("event_date"),
                -(Constants.UTC_TO_EST_OFFSET));
        lEvent.setDate(lDate);

        lEvent.setStartTime(aEventData.getString("event_start_time"));
        lEvent.setEndTime(aEventData.getString("event_end_time"));
        lEvent.setCost(aEventData.getString("event_cost"));
        lEvent.setWebsite(aEventData.getString("event_website"));

        // Retrieve the Venue Object related to the event
        ParseObject lVenueData = aEventData.getParseObject("venue_id");
        lEvent.setVenue(VenueFactory.createVenue(lVenueData));

        // Retrieve the Organizer object related to the event
        ParseObject lOrganizerData = aEventData.getParseObject("organizer_id");
        lEvent.setOrganizer(OrganizerFactory.createOrganizer(lOrganizerData));

        return lEvent;
    }
}
