package com.azucarottawa.app.salsainottawa;

import com.azucarottawa.app.salsainottawa.core.Organizer;
import com.azucarottawa.app.salsainottawa.factory.OrganizerFactory;
import com.parse.ParseObject;

import junit.framework.Assert;

/**
 * Created by Hreeels on 2015-11-30.
 */
public class OrganizerFactoryTest extends ApplicationTest {

    public void testCreateFromParseObject() {
        ParseObject lParseObject = ParseObject.create("organizer");

        lParseObject.put("organizer_name", "Test Organizer");
        lParseObject.put("organizer_phone_number", "123-456-7890");
        lParseObject.put("organizer_email", "test@email.com");
        lParseObject.put("organizer_website", "www.test.com");

        Organizer lOrganizer = OrganizerFactory.createOrganizer(lParseObject);

        Assert.assertEquals(lOrganizer.getTitle(), "Test Organizer");
        Assert.assertEquals(lOrganizer.getPhoneNumber(), "123-456-7890");
        Assert.assertEquals(lOrganizer.getEmail(), "test@email.com");
        Assert.assertEquals(lOrganizer.getWebsite(), "www.test.com");
    }
}
