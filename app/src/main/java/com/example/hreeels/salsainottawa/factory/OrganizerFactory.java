package com.example.hreeels.salsainottawa.factory;

import com.example.hreeels.salsainottawa.core.Organizer;
import com.parse.ParseObject;

/**
 * Created by Hreeels on 2015-11-10.
 */
public class OrganizerFactory {
    /**
     * Creates an organizer object from the given Parse Object.
     * Assumes Parse Object contains Organizer data.
     *
     * @param aOrganizerData The Parse Object containing the organizer data
     * @return The Organizer object created from the Parse Object
     */
    public static Organizer createOrganizer(ParseObject aOrganizerData) {
        Organizer lOrganizer = new Organizer(aOrganizerData.getString("organizer_name"));

        lOrganizer.setPhoneNumber(aOrganizerData.getString("organizer_phone_number"));
        lOrganizer.setEmail(aOrganizerData.getString("organizer_email"));
        lOrganizer.setWebsite(aOrganizerData.getString("organizer_website"));

        return lOrganizer;
    }
}
