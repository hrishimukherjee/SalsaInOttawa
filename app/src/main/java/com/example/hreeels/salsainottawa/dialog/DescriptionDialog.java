package com.example.hreeels.salsainottawa.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Window;
import android.widget.TextView;

import com.example.hreeels.salsainottawa.R;

/**
 * Created by Hreeels on 2015-11-29.
 */
public class DescriptionDialog extends Dialog {

    private Activity iActivity;

    private TextView iDescriptionTitle;
    private TextView iEventDescription;

    private Typeface iCustomFont;
    private Typeface iCustomSmallFont;

    private String iDescription;

    public DescriptionDialog(Activity aActivity, String aDescription) {
        super(aActivity);
        this.iActivity = aActivity;

        this.iDescription = aDescription;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.description_dialog);

        iDescriptionTitle = (TextView) findViewById(R.id.description_title);
        iEventDescription = (TextView) findViewById(R.id.event_description);

        iDescriptionTitle.setText("DESCRIPTION");
        iEventDescription.setMovementMethod(new ScrollingMovementMethod());

        iCustomFont = Typeface.createFromAsset(getContext().getAssets(),
                "bebas_neue_regular.ttf");
        iCustomSmallFont = Typeface.createFromAsset(getContext().getAssets(),
                "Lato-Light.ttf");

        iDescriptionTitle.setTypeface(iCustomFont);
        iEventDescription.setTypeface(iCustomSmallFont);

        updateDialog(iDescription);
    }

    /**
     * Updates the dialog fields.
     *
     * @param aDescription description of the event
     */
    public void updateDialog(String aDescription) {
        iEventDescription.setText(aDescription);
    }
}
