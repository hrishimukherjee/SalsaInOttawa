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
        initialzeViewComponents();

        // Retrieve the query results from the intent
        iEventList = getIntent().getParcelableArrayListExtra("queryResult");

        for(Event lEvent: iEventList) {
            Log.d("activity2", lEvent.toString());
        }

        // GROOMING of GUI
        Typeface customFont = Typeface.createFromAsset(getAssets(), "bebas_neue_regular.ttf");
        Typeface customFontBold = Typeface.createFromAsset(getAssets(), "bebas_neue_bold.ttf");

        iSearchResultTitle.setTypeface(customFont);

        // Initialize the Array Adapter with the event list
        ArrayAdapter theAdapter = new EventAdapter(this, iEventList);

        iSearchResultList.setAdapter(theAdapter);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_event_list, menu);
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

    public void initialzeViewComponents() {
        iSearchResultTitle = (TextView) findViewById(R.id.search_result_title);
        iSearchResultList = (ListView) findViewById(R.id.theListView);
    }
}
