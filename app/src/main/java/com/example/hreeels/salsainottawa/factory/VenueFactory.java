package com.example.hreeels.salsainottawa.factory;

import com.example.hreeels.salsainottawa.core.Venue;
import com.parse.ParseObject;

/**
 * Created by Hreeels on 2015-11-10.
 */
public class VenueFactory {
    /**
     * Creates a venue object from the given Parse Object.
     * Assumes Parse Object contains Venue data.
     *
     * @param aVenueData The Parse Object containing the venue data
     * @return The Venue object created from the Parse Object
     */
    public static Venue createVenue(ParseObject aVenueData) {
        Venue lVenue = new Venue(aVenueData.getString("venue_name"));

        lVenue.setAddress(aVenueData.getString("venue_address"));
        lVenue.setPhoneNumber(aVenueData.getString("venue_phone_number"));
        lVenue.setWebsite(aVenueData.getString("venue_website"));

        return lVenue;
    }
}
