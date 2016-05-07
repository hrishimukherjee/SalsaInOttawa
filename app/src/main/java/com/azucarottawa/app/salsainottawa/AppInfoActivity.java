package com.azucarottawa.app.salsainottawa;

import android.graphics.Typeface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.TextView;


public class AppInfoActivity extends ActionBarActivity {

    private static final String INFO_WHAT_DESCRIPTION = "A directory of all salsa, merengue, " +
            "bachata, cha-cha and kizomba dance events in the Ottawa-Gatineau area. " +
            "Salsa in Ottawa does not organize any of the events listed on our calendar. " +
            "We do our best to stay up to date, but for details about a specific event, " +
            "please contact the event organizers.";

    private static final String INFO_WHO_DESCRIPTION = "Salsa in Ottawa is volunteer-run by " +
            "members of Azucar! Latin Dance Company (http://www.azucarottawa.com). " +
            "If you know of an event that's not listed here, please contact us at " +
            "info@salsainottawa.com!";


    private static final String INFO_DEV_DESCRIPTION = "Hey there! This is Hrishi Mukherjee. " +
            "I started this app off as a personal project which ended up turning into " +
            "something real! I am pretty passionate about programming, and solving problems. " +
            "I love learning, growing myself, meeting new people and delving in new experiences! " +
            "Oh and travelling is awesome. Did I mention I dance Salsa? " +
            "Sorry, I thought that that was a given!" +
            " Need to contact me? \n\nCheck me out on LinkedIn: " +
            "https://ca.linkedin.com/in/hrishi-mukherjee-3510a383" +
            "\n\nOr shoot me an email " +
            "at hrishi.mukherjee@hotmail.com" + "\n\nI am always open to new ideas " +
            "and I think that everyone has something cool to offer to this world. Cheers!";

    TextView iInfoWhatTitle;
    TextView iInfoWhatDescription;
    TextView iInfoWhoTitle;
    TextView iInfoWhoDescription;
    TextView iInfoDevTitle;
    TextView iInfoDevDescription;

    private Typeface iCustomFont;
    private Typeface iCustomFontBold;
    private Typeface iCustomSmallFont;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_info);

        initializeViewComponents();
        decorateComponents();

        update(INFO_WHAT_DESCRIPTION, INFO_WHO_DESCRIPTION, INFO_DEV_DESCRIPTION);
    }

    private void initializeViewComponents() {
        iInfoWhatTitle = (TextView) findViewById(R.id.info_what_title);
        iInfoWhatDescription = (TextView) findViewById(R.id.info_what_description);
        iInfoWhoTitle = (TextView) findViewById(R.id.info_who_title);
        iInfoWhoDescription = (TextView) findViewById(R.id.info_who_description);
        iInfoDevTitle = (TextView) findViewById(R.id.info_dev_title);
        iInfoDevDescription = (TextView) findViewById(R.id.info_dev_description);
    }

    private void decorateComponents() {
        iCustomFontBold = Typeface.createFromAsset(getAssets(), "bebas_neue_bold.ttf");
        iCustomFont = Typeface.createFromAsset(getAssets(), "bebas_neue_regular.ttf");
        iCustomSmallFont = Typeface.createFromAsset(getAssets(), "Lato-Light.ttf");

        iInfoWhatTitle.setTypeface(iCustomFontBold);
        iInfoWhatDescription.setTypeface(iCustomSmallFont);
        iInfoWhoTitle.setTypeface(iCustomFontBold);
        iInfoWhoDescription.setTypeface(iCustomSmallFont);
        iInfoDevTitle.setTypeface(iCustomFontBold);
        iInfoDevDescription.setTypeface(iCustomSmallFont);
    }

    public void update(String aAboutUsDescription,
                       String aWhoDescription,
                       String aDeveloperDescription) {
        iInfoWhatDescription.setText(aAboutUsDescription);
        iInfoWhoDescription.setText(aWhoDescription);
        iInfoDevDescription.setText(aDeveloperDescription);
    }
}
