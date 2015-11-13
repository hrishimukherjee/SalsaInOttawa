package com.example.hreeels.salsainottawa;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hreeels.salsainottawa.core.Event;
import com.example.hreeels.salsainottawa.factory.EventFactory;
import com.example.hreeels.salsainottawa.utils.AppUtils;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    // GUI Elements
    private TextView iTitleCaption;
    private Button iSearchTonightButton;
    private Button iSearchWeekendButton;
    private Button iSearchCustomButton;

    // Data Attributes
    private ArrayList<Event> iQueryResults; /* The query results retrieved from the database */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize and prepare the view's components
        initializeViewComponents();
        decorateComponents();
        initializeActionListeners();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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

    /*
    Initializes the action listeners for all components in the view.
     */
    public void initializeActionListeners() {
        // Initialize the click listener for the search tonight button
        iSearchTonightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pop up a toast notifying the user that the events are being retrieved
                Toast.makeText(MainActivity.this, "Searching for events tonight...",
                        Toast.LENGTH_SHORT).show();

                // Make the progress bar visible
                findViewById(R.id.retrieving_events_bar).setVisibility(View.VISIBLE);

                // Search for the events on a specific date
                searchForEventsTonight();
            }
        });

        // Initialize the click listener for the search weekend button
        iSearchWeekendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Searching for events this weekend...",
                        Toast.LENGTH_SHORT).show();

                // Make the progress bar visible
                findViewById(R.id.retrieving_events_bar).setVisibility(View.VISIBLE);

                // Search for the events on a specific date
                searchForEventsTonight();
            }
        });

        // Initialize the click listener for the search custom date button
        iSearchCustomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Searching for events on a date...",
                        Toast.LENGTH_SHORT).show();

                // Make the progress bar visible
                findViewById(R.id.retrieving_events_bar).setVisibility(View.VISIBLE);

                // Search for the events on a specific date
                searchForEventsTonight();
            }
        });
    }

    /**
     * Queries PARSE for all the events TONIGHT.
     * To be more precise, events ranging between the system's current date
     * to midnight. It also passes in the results to the EventListActivity
     * and starts it up.
     */
    public void searchForEventsTonight() {
        iQueryResults = new ArrayList<Event>();

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Event");

        query.include("venue_id");
        query.include("organizer_id");

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> aEventList, ParseException e) {
                if (e == null) {
                    // Transform the event data to event objects and store in a list
                    for (ParseObject lEventData : aEventList) {
                        Event lNewEvent = EventFactory.createEvent(lEventData);

                        iQueryResults.add(lNewEvent);
                    }

                    // Make the progress bar invisible
                    findViewById(R.id.retrieving_events_bar).setVisibility(View.GONE);

                    // Set up an intent, pass in the query results, and start a new activity
                    Intent myIntent = new Intent(MainActivity.this, EventListActivity.class);
                    myIntent.putParcelableArrayListExtra("queryResult", iQueryResults);
                    MainActivity.this.startActivity(myIntent);
                } else {
                    Log.d("QUERY", "Error: " + e.getMessage());
                }
            }
        });
    }
}
