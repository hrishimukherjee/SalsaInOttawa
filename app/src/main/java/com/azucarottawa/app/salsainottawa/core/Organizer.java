package com.azucarottawa.app.salsainottawa.core;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Hreeels on 2015-06-30.
 */

public class Organizer implements Parcelable {
    private String iTitle;
    private String iPhoneNumber;
    private String iEmail;
    private String iWebsite;

    public Organizer() {
        this.setTitle("Default Organizer");
        this.setPhoneNumber("123-456-7890");
        this.setEmail("defaultorganizer@email.com");
        this.setWebsite("www.defaultorganizer.com");
    }

    public Organizer(String aTitle) {
        this.setTitle(aTitle);
    }

    /**
     * Constructor to use when re-constructing object
     * from a parcel.
     *
     * @param in a parcel from which to read this object
     */
    public Organizer (Parcel in) {
        readFromParcel(in);
    }

    public String getTitle() { return this.iTitle; }
    public String getPhoneNumber() { return this.iPhoneNumber; }
    public String getEmail() { return this.iEmail; }
    public String getWebsite() { return this.iWebsite; }

    public void setTitle(String aTitle) {
        this.iTitle = aTitle;
    }

    public void setPhoneNumber(String aPhoneNumber) {
        this.iPhoneNumber = aPhoneNumber;
    }

    public void setEmail(String aEmail) {
        this.iEmail = aEmail;
    }

    public void setWebsite(String aWebsite) {
        this.iWebsite = aWebsite;
    }

    public String toString() {
        String lResult = "";

        lResult += "Organizer Name: " + this.getTitle();
        lResult += "\nPhone Number: " + this.getPhoneNumber();
        lResult += "\nEmail: " + this.getEmail();
        lResult += "\nWebsite: " + this.getWebsite();

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
        dest.writeString(this.iTitle);
        dest.writeString(this.iPhoneNumber);
        dest.writeString(this.iEmail);
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
        this.setTitle(in.readString());
        this.setPhoneNumber(in.readString());
        this.setEmail(in.readString());
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
                public Organizer createFromParcel(Parcel in) {
                    return new Organizer(in);
                }

                public Organizer[] newArray(int size) {
                    return new Organizer[size];
                }
            };
}
