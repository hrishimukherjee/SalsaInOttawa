package com.example.hreeels.salsainottawa.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

import com.example.hreeels.salsainottawa.R;
import com.example.hreeels.salsainottawa.core.Organizer;

/**
 * Created by Hreeels on 2015-11-29.
 */
public class OrganizerDialog extends Dialog {

    private Activity iActivity;

    private TextView iDialogTitle;
    private TextView iOrganizerName;
    private TextView iOrganizerPhoneNumber;
    private TextView iOrganizerEmail;
    private TextView iOrganizerWebsite;

    private Typeface iCustomFont;
    private Typeface iCustomSmallFont;

    private Organizer iOrganizer;

    public OrganizerDialog(Activity aActivity, Organizer aOrganizer) {
        super(aActivity);
        this.iActivity = aActivity;

        this.iOrganizer = aOrganizer;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.organizer_dialog);

        iCustomFont = Typeface.createFromAsset(getContext().getAssets(),
                "bebas_neue_regular.ttf");
        iCustomSmallFont = Typeface.createFromAsset(getContext().getAssets(),
                "Lato-Light.ttf");

        iDialogTitle = (TextView) findViewById(R.id.organizer_title);
        iOrganizerName = (TextView) findViewById(R.id.organizer_name);
        iOrganizerPhoneNumber = (TextView) findViewById(R.id.organizer_phone_number);
        iOrganizerEmail = (TextView) findViewById(R.id.organizer_email);
        iOrganizerWebsite = (TextView) findViewById(R.id.organizer_website);

        iDialogTitle.setText("ORGANIZER");

        iDialogTitle.setTypeface(iCustomFont);
        iOrganizerName.setTypeface(iCustomSmallFont);
        iOrganizerPhoneNumber.setTypeface(iCustomSmallFont);
        iOrganizerEmail.setTypeface(iCustomSmallFont);
        iOrganizerWebsite.setTypeface(iCustomSmallFont);

        updateDialog(iOrganizer.getTitle(), iOrganizer.getPhoneNumber(),
                iOrganizer.getEmail(), iOrganizer.getWebsite());
    }

    /**
     * Updates the dialog fields.
     *
     * @param aName organizer name
     * @param aPhoneNumber organizer phone number
     * @param aEmail organizer email
     * @param aWebsite organizer website
     */
    public void updateDialog(String aName, String aPhoneNumber,
                             String aEmail, String aWebsite) {
        iOrganizerName.setText(aName);
        iOrganizerPhoneNumber.setText(aPhoneNumber);
        iOrganizerEmail.setText(aEmail);
        iOrganizerWebsite.setText(aWebsite);
    }
}
