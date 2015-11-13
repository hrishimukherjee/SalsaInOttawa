package com.example.hreeels.salsainottawa.core;

/**
 * Created by Hreeels on 2015-06-30.
 */

import android.os.Parcel;
import android.os.Parcelable;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Event implements Parcelable {
    private String iTitle; 			/* Name of the event */
    private String iDescription;	/* Description of the event */
    private Date iDate;				/* Date of the event */
    private String iStartTime;		/* Starting time of the event */
    private String iEndTime;		/* Ending time of the event */
    private String iCost;			/* Admission cost of the event */
    private String iWebsite;		/* Web site of the event */
    private Venue iVenue;			/* Venue of the event */
    private Organizer iOrganizer;	/* Organizer of the event */

    /**
     * Standard constructor for non parcel object creation.
     */
    public Event() {
        this.setTitle("Default event");
        this.setDescription("No description");
        this.setDate("2015-01-01");
        this.setStartTime("9:00 AM");
        this.setEndTime("12:00 AM");
        this.setCost("0");
        this.setWebsite("www.defaultevent.com");
        this.setVenue(null);
        this.setOrganizer(null);
    }

    /**
     * Constructor to use when re-constructing object
     * from a parcel.
     *
     * @param in a parcel from which to read this object
     */
    public Event(Parcel in) {
        readFromParcel(in);
    }

    public Event(String aTitle) {
        this.setTitle(aTitle);
        this.setDate("2015-01-01");
    }

	/* Event Related Getters */

    public String getTitle() { return this.iTitle; }
    public String getDescription() { return this.iDescription; }
    public Date getDate() { return this.iDate; }
    public String getStartTime() { return this.iStartTime; }
    public String getEndTime() { return this.iEndTime; }
    public String getCost() { return this.iCost; }
    public String getWebsite() { return this.iWebsite; }
    public Venue getVenue() { return this.iVenue; }
    public Organizer getOrganizer() { return this.iOrganizer; }

	/* Venue Related Getters */

    public String getVenueTitle() { return this.iVenue.getTitle(); }
    public String getVenueAddress() { return this.iVenue.getAddress(); }
    public String getVenuePhoneNumber() { return this.iVenue.getPhoneNumber(); }
    public String getVenueWebsite() { return this.iVenue.getWebsite(); }

	/* Organizer Related Getters */

    public String getOrganizerTitle() { return this.iOrganizer.getTitle(); }
    public String getOrganizerPhoneNumber() { return this.iOrganizer.getPhoneNumber(); }
    public String getOrganizerEmail() { return this.iOrganizer.getEmail(); }
    public String getOrganizerWebsite() { return this.iOrganizer.getWebsite(); }

	/* Event Related Setters */

    public void setTitle(String aTitle) {
        this.iTitle = aTitle;
    }

    public void setDescription(String aDescription) {
        this.iDescription = aDescription;
    }

    public void setDate(Date aDate) {
        this.iDate = aDate;
    }

    public void setDate(String aDate) {
        try {
            DateFormat lFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            this.iDate = lFormat.parse(aDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void setStartTime(String aStartTime) {
        this.iStartTime = aStartTime;
    }

    public void setEndTime(String aEndTime) {
        this.iEndTime = aEndTime;
    }

    public void setCost(String aCost) {
        this.iCost = aCost;
    }

    public void setWebsite(String aWebsite) {
        this.iWebsite = aWebsite;
    }

    public void setVenue(Venue aVenue) {
        this.iVenue = aVenue;
    }

    public void setOrganizer(Organizer aOrganizer) {
        this.iOrganizer = aOrganizer;
    }

    public String toString() {
        String lResult = "";

        lResult += "\n" + this.getTitle() + "\n=============";
        lResult += "\n" + this.getDescription();
        lResult += "\nDate: " + this.getDate();
        lResult += "\nStart Time: " + this.getStartTime();
        lResult += "\nEnd Time: " + this.getEndTime();
        lResult += "\nAdmission Cost: " + this.getCost();
        lResult += "\nEvent Website: " + this.getWebsite();

        if(this.getVenue() != null) {
            lResult += "\n\nVenue Information:\n------------------\n" + this.getVenue();
        } else {
            lResult += "\n-----------------\nNo Venue Information Provided.";
        }

        if(this.getOrganizer() != null) {
            lResult += "\n\nOrganizer Information:\n----------------------\n" + this.getOrganizer();
        } else {
            lResult += "\n-----------------\nNo Organizer Information Provided.\n";
        }

        return lResult;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Writes this object's fields into a parcel.
     * NOTE: When this parcel is read back again,
     * the field's have to be read in the SAME ORDER!
     *
     * @param dest the parcel which will hold the fields
     * @param flags any flags
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.iVenue, flags);
        dest.writeParcelable(this.iOrganizer, flags);
        dest.writeString(this.iTitle);
        dest.writeString(this.iDescription);

        // Write Date as a LONG to avoid using writeSerializable()
        dest.writeLong(this.iDate.getTime());

        dest.writeString(this.iStartTime);
        dest.writeString(this.iEndTime);
        dest.writeString(this.iCost);
        dest.writeString(this.iWebsite);
    }

    /**
     * Reads the data from a parcel and sets this object's
     * fields accordingly.
     * NOTE: When this parcel is read, it has to be read in
     * the SAME ORDER as it was written into.
     *
     * @param in parcel from which to re-create object
     */
    private void readFromParcel(Parcel in) {
        // Read the Venue Object
        Venue lVenue = (Venue) in.readParcelable(Venue.class.getClassLoader());
        this.setVenue(lVenue);

        // Read the Organizer Object
        Organizer lOrganizer = (Organizer) in.readParcelable(Organizer.class.getClassLoader());
        this.setOrganizer(lOrganizer);

        this.setTitle(in.readString());
        this.setDescription(in.readString());
        this.setDate(new Date(in.readLong()));
        this.setStartTime(in.readString());
        this.setEndTime(in.readString());
        this.setCost(in.readString());
        this.setWebsite(in.readString());
    }

    /**
     * This field is needed for Android to be able to
     * create new objects, individually or as arrays.
     *
     * This also means that you can use use the default
     * constructor to create the object and use another
     * method to hyrdate it as necessary.
     *
     */
    public static final Parcelable.Creator CREATOR =
            new Parcelable.Creator() {
                public Event createFromParcel(Parcel in) {
                    return new Event(in);
                }

                public Event[] newArray(int size) {
                    return new Event[size];
                }
            };
}

