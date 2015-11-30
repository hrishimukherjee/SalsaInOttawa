package com.example.hreeels.salsainottawa;

import com.example.hreeels.salsainottawa.core.Venue;
import com.example.hreeels.salsainottawa.factory.VenueFactory;
import com.parse.ParseObject;

import junit.framework.Assert;

/**
 * Created by Hreeels on 2015-11-30.
 */
public class VenueFactoryTest extends ApplicationTest {

    public void testCreateFromParseObject() {
        ParseObject lParseObject = ParseObject.create("venue");

        lParseObject.put("venue_name", "Amigos");
        lParseObject.put("venue_address", "123 ABC Street, DEF, GHI A1B 2C3");
        lParseObject.put("venue_phone_number", "123-456-7890");
        lParseObject.put("venue_website", "www.amigos.com");

        Venue lVenue = VenueFactory.createVenue(lParseObject);

        Assert.assertEquals(lVenue.getTitle(), "Amigos");
        Assert.assertEquals(lVenue.getAddress(), "123 ABC Street, DEF, GHI A1B 2C3");
        Assert.assertEquals(lVenue.getPhoneNumber(), "123-456-7890");
        Assert.assertEquals(lVenue.getWebsite(), "www.amigos.com");
    }
}
