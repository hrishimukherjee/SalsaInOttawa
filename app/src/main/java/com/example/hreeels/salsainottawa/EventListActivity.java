package com.example.hreeels.salsainottawa;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hreeels.salsainottawa.core.Event;
import com.example.hreeels.salsainottawa.utils.EventAdapter;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class EventListActivity extends ActionBarActivity {

    // GUI Elements
    private TextView iSearchResultTitle;
    private ListView iSearchResultList;

    // Data Attributes
    private ArrayList<Event> iEventList; /* The list of events to be displayed on the screen */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);

        // Initialize the components of the screen
        initializeViewComponents();

        // Retrieve the query results from the intent
        iEventList = getIntent().getParcelableArrayListExtra("queryResult");

        // Setup the activity
        setupActivity(iEventList);
    }

    /**
     * Sets up the activity with the provided list of events.
     *
     * @param aEventList
     */
    public void setupActivity(ArrayList<Event> aEventList) {
        Typeface customFont = Typeface.createFromAsset(getAssets(), "bebas_neue_regular.ttf");
        iSearchResultTitle.setTypeface(customFont);

        activateActionListeners();

        updateEventList(aEventList);
    }

    /**
     * Initializes the view components of the activity.
     */
    public void initializeViewComponents() {
        iSearchResultTitle = (TextView) findViewById(R.id.search_result_title);
        iSearchResultList = (ListView) findViewById(R.id.theListView);
    }

    /**
     * Activates all the action listeners for the components in the activity.
     */
    public void activateActionListeners() {
        iSearchResultList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Event eventPicked = (Event) parent.getItemAtPosition(position);

                String displayString = "You picked " +
                        eventPicked.getTitle();

                // Set up an intent, pass in the query results, and start a new activity
                Intent myIntent = new Intent(EventListActivity.this, EventDetails.class);
                myIntent.putExtra("eventPicked", eventPicked);
                EventListActivity.this.startActivity(myIntent);

                // Toast.makeText(EventListActivity.this, displayString, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Updates the activity with the latest event list passed in
     * through the parameter.
     *
     * @param aEventList the new event list to display
     */
    public void updateEventList(ArrayList<Event> aEventList) {
        // Initialize the Array Adapter with the event list
        ArrayAdapter theAdapter = new EventAdapter(this, aEventList);

        iSearchResultList.setAdapter(theAdapter);
    }
}
