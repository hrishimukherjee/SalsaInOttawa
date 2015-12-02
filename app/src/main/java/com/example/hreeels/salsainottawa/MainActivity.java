package com.example.hreeels.salsainottawa;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hreeels.salsainottawa.core.Event;
import com.example.hreeels.salsainottawa.server.QueryClient;
import com.example.hreeels.salsainottawa.server.ServerConnection;
import com.example.hreeels.salsainottawa.utils.AppUtils;
import com.example.hreeels.salsainottawa.utils.Constants;
import com.example.hreeels.salsainottawa.utils.DateUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class MainActivity extends ActionBarActivity implements QueryClient {

    // GUI Elements
    private TextView iTitleCaption;
    private Button iSearchTonightButton;
    private Button iSearchWeekendButton;
    private Button iSearchCustomButton;

    // Data Attributes
    private ServerConnection iServer; /* The class used to communicate with the server */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iServer = ServerConnection.getDefault();

        // Initialize and prepare the view's components
        initializeViewComponents();
        decorateComponents();
        activateActionListeners();
    }

    /**
     * Re-enables the action listeners once the activity
     * comes back into the foreground.
     */
    @Override
    public void onResume() {
        super.onResume();

        activateActionListeners();
    }

    /**
     * Initializes the individual components in the view being used by the activity.
     */
    public void initializeViewComponents() {
        iTitleCaption = (TextView) findViewById(R.id.go_dancing_text);
        iSearchTonightButton = (Button) findViewById(R.id.search_tonight_button);
        iSearchWeekendButton = (Button) findViewById(R.id.search_weekend_button);
        iSearchCustomButton = (Button) findViewById(R.id.search_custom_button);
    }

    /**
     * Any formatting, design, or aesthetic changes should be applied in this function.
     */
    public void decorateComponents() {
        Typeface customFontBold = Typeface.createFromAsset(getAssets(), "bebas_neue_bold.ttf");

        iTitleCaption.setTypeface(customFontBold);
        iSearchTonightButton.setTypeface(customFontBold);
        iSearchWeekendButton.setTypeface(customFontBold);
        iSearchCustomButton.setTypeface(customFontBold);
    }

    /**
     * Initializes the action listeners for all components in the view.
     */

    public void activateActionListeners() {
        // Initialize the click listener for the search tonight button
        iSearchTonightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deactivateActionListeners();

                onSearchTonightPressed();
            }
        });

        // Initialize the click listener for the search weekend button
        iSearchWeekendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deactivateActionListeners();

                onSearchWeekendPressed();
            }
        });

        // Initialize the click listener for the search custom date button
        iSearchCustomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deactivateActionListeners();

                onSearchByDatePressed();
            }
        });
    }

    /**
     * Deactivates all the action listeners.
     */
    public void deactivateActionListeners() {
        iSearchTonightButton.setOnClickListener(null);
        iSearchWeekendButton.setOnClickListener(null);
        iSearchCustomButton.setOnClickListener(null);
    }

    /**
     * On click listener for search events tonight button.
     */
    public void onSearchTonightPressed() {
        // Pop up a toast
        Toast.makeText(MainActivity.this, "Searching for events tonight...",
                Toast.LENGTH_SHORT).show();

        // Make the progress bar visible
        findViewById(R.id.retrieving_events_bar).setVisibility(View.VISIBLE);

        // Execute the query using the server
        iServer.getAllEventsBetweenDates(this,
                DateUtils.getTodayLowerBound(),
                DateUtils.getTodayUpperBound());
    }

    /**
     * On click listener for search events this weekend button.
     */
    public void onSearchWeekendPressed() {
        // Pop up a toast
        Toast.makeText(MainActivity.this, "Searching for events this weekend...",
                Toast.LENGTH_SHORT).show();

        // Make the progress bar visible
        findViewById(R.id.retrieving_events_bar).setVisibility(View.VISIBLE);

        // Execute the query using the server
        iServer.getAllEventsBetweenDates(this,
                DateUtils.getWeekendLowerBound(),
                DateUtils.getWeekendUpperBound());
    }

    /**
     * On click listener for search by date button.
     */
    public void onSearchByDatePressed() {
        // Pop up a date picker dialog
        showDialog(Constants.DATE_PICKER_DIALOG_ID);
    }

    /**
     * Uses the event list to start the event list activity.
     *
     * @param aQueryResult the result of the query
     */
    public void queryDone(ArrayList<Event> aQueryResult) {
        // Make the progress bar invisible
        findViewById(R.id.retrieving_events_bar).setVisibility(View.GONE);

        // Set up an intent, pass in the query results, and start the event list activity
        Intent myIntent = new Intent(MainActivity.this, EventListActivity.class);
        myIntent.putParcelableArrayListExtra("queryResult", aQueryResult);
        MainActivity.this.startActivity(myIntent);
    }

    /**
     * Handles dialog creation.
     *
     * @param aId ID of the dialog which called the function
     * @return Dialog of the specified ID
     */
    @Override
    protected Dialog onCreateDialog(int aId) {
        switch (aId) {
            case Constants.DATE_PICKER_DIALOG_ID:
                // Set the date picker to the current date
                return getDatePickerDialog();
        }
        return null;
    }

    /**
     * Returns a date picker dialog defaulted to the current date.
     * It also assigns the action listeners for the buttons in the dialog.
     *
     * @return the new date picker dialog
     */
    public DatePickerDialog getDatePickerDialog() {
        Calendar lCurrentDate = DateUtils.getCurrentCalendar();

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, iDatePickerListener,
                lCurrentDate.get(Calendar.YEAR),
                lCurrentDate.get(Calendar.MONTH),
                lCurrentDate.get(Calendar.DATE));

        // Set the action for when the cancel button is clicked on the date picker
        datePickerDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(which == DialogInterface.BUTTON_NEGATIVE) {
                            // Re-activate the action listeners for the activity
                            activateActionListeners();
                        }
                    }
                });

        // Set the date picker to the current date
        return datePickerDialog;
    }

    /**
     * On click listener for the OK button of the date picker
     */
    private DatePickerDialog.OnDateSetListener iDatePickerListener
            = new DatePickerDialog.OnDateSetListener() {

        /**
         * Retrieves the date information from the user's selection,
         * and uses this information to send a query request.
         *
         * @param aView the date picker view
         * @param aSelectedYear the user selected year
         * @param aSelectedMonth the user selected month
         * @param aSelectedDay the user selected date
         */
        public void onDateSet(DatePicker aView, int aSelectedYear,
                              int aSelectedMonth, int aSelectedDay) {
            Date lLowerBound = DateUtils.createCustomDate(aSelectedYear,
                    aSelectedMonth, aSelectedDay);

            Date lUpperBound = DateUtils.getDateUpperBound(lLowerBound);

            // Execute the query for events
            iServer.getAllEventsBetweenDates(MainActivity.this,
                    lLowerBound, lUpperBound);

            // Make the progress bar visible
            findViewById(R.id.retrieving_events_bar).setVisibility(View.VISIBLE);

            // Pop up a toast
            Toast.makeText(MainActivity.this, "Searching for events on " +
                            AppUtils.getMonthString(aSelectedMonth) + " " +
                    aSelectedDay + "...",
                    Toast.LENGTH_SHORT).show();
        }
    };
}
