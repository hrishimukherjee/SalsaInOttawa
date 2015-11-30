package com.example.hreeels.salsainottawa;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hreeels.salsainottawa.core.Event;
import com.example.hreeels.salsainottawa.dialog.DescriptionDialog;
import com.example.hreeels.salsainottawa.dialog.OrganizerDialog;
import com.example.hreeels.salsainottawa.utils.AppUtils;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.w3c.dom.Text;

import java.util.Date;
import java.util.logging.Handler;


public class EventDetails extends ActionBarActivity {

    private static final int INFO_VIEW_POPUP_DELAY = 2500;
    private static final String DEFAULT_DESCRIPTION_HEADER = "DESCRIPTION";
    private static final String DEFAULT_ORGANIZER_HEADER = "ORGANIZER";

    // GUI Elements
    private TextView iEventTitle;
    private TextView iEventStartTime;
    private TextView iEventEndTime;
    private TextView iEventDateMonth;
    private TextView iEventDateDay;
    private TextView iEventPrice;
    private LinearLayout iDescriptonComponent;
    private TextView iEventDescriptionHeader;
    private LinearLayout iOrganizerComponent;
    private TextView iOrganizerHeader;

    // Data Attributes
    private Event iEventData;
    private GoogleMap iMap;

    private Typeface custom_font;
    private Typeface custom_font_bold;
    private Typeface custom_small_font;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        setupActivity((Event) getIntent().getParcelableExtra("eventPicked"),
                ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_event_details, menu);
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
     * Sets up the activity by initializing all the views,
     * and data fields. It also applies any grooming specified
     * in the decorate function. Finally, it updates the
     * activity with the event object provided. This function
     * is to be called at the beginning, every time this activity
     * is created.
     *
     * @param aEventData the event object containing the data for initialization
     * @param aMap the Google Maps object for initialization
     */
    private void setupActivity(Event aEventData, GoogleMap aMap) {
        initializeData(aEventData, aMap);
        initializeViewComponents();
        activateActionListeners();

        iEventDescriptionHeader.setText(DEFAULT_DESCRIPTION_HEADER);
        iOrganizerHeader.setText(DEFAULT_ORGANIZER_HEADER);

        custom_font = Typeface.createFromAsset(getAssets(), "bebas_neue_regular.ttf");
        custom_font_bold = Typeface.createFromAsset(getAssets(), "bebas_neue_bold.ttf");
        custom_small_font = Typeface.createFromAsset(getAssets(), "Lato-Light.ttf");

        decorateComponents();

        this.updateActivity(aEventData);
    }

    /**
     * Initializes the data fields of this activity with the
     * provided parameters.
     *
     * @param aEventData the event object used for initialization
     * @param aMap the Google map object used to initialization
     */
    private void initializeData(Event aEventData, GoogleMap aMap) {
        this.setEventData(aEventData);
        this.setMap(aMap);
    }

    /**
     * Retrieves the view components from the layout and
     * initializes the view fields.
     */
    private void initializeViewComponents() {
        iEventTitle = (TextView) findViewById(R.id.event_title);
        iEventStartTime = (TextView) findViewById(R.id.event_start_time);
        iEventEndTime = (TextView) findViewById(R.id.event_end_time);
        iEventDateMonth = (TextView) findViewById(R.id.event_date_month);
        iEventDateDay = (TextView) findViewById(R.id.event_date_day);
        iEventPrice = (TextView) findViewById(R.id.event_price);
        iDescriptonComponent = (LinearLayout) findViewById(R.id.description_component);
        iEventDescriptionHeader = (TextView) findViewById(R.id.description_header);
        iOrganizerComponent = (LinearLayout) findViewById(R.id.organizer_component);
        iOrganizerHeader = (TextView) findViewById(R.id.organizer_header);
    }

    /**
     * All grooming of view components done in this function.
     */
    private void decorateComponents() {
        iEventTitle.setTypeface(custom_font);
        iEventStartTime.setTypeface(custom_font_bold);
        iEventEndTime.setTypeface(custom_font_bold);
        iEventDateMonth.setTypeface(custom_font_bold);
        iEventDateDay.setTypeface(custom_font_bold);
        iEventPrice.setTypeface(custom_font_bold);
        iEventDescriptionHeader.setTypeface(custom_font_bold);
        iOrganizerHeader.setTypeface(custom_font_bold);
    }

    /**
     * Activates all the action listeners for the clickable
     * components of the activity.
     */
    public void activateActionListeners() {
        // Initialize the dialog for the description and pop it up
        iDescriptonComponent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DescriptionDialog lDialog = new DescriptionDialog(EventDetails.this,
                        iEventData.getDescription());
                lDialog.show();
            }
        });

        iOrganizerComponent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrganizerDialog lDialog = new OrganizerDialog(EventDetails.this,
                        iEventData.getOrganizer());
                lDialog.show();
            }
        });
    }

    /**
     * Updates all the view components of this activity's layout
     * using the information from the model Event object provided.
     *
     * @param aUpdatedEvent an event object used to update the view
     */
    public void updateActivity(Event aUpdatedEvent) {
        setEventTitle(aUpdatedEvent.getTitle());

        setEventStartTime(aUpdatedEvent.getStartTime());
        setEventEndTime(aUpdatedEvent.getEndTime());

        String lMonthString = AppUtils.getMonthString(aUpdatedEvent.getDate().getMonth());
        setEventDateMonth(lMonthString.substring(0, 3));
        setEventDateDay(String.valueOf(aUpdatedEvent.getDate().getDate()));

        setEventPrice(aUpdatedEvent.getCost());

       // setEventDescription(aUpdatedEvent.getDescription());

        updateMap(AppUtils.getLocationFromAddress(this, this.getEventData().getVenueAddress()),
                this.getEventData().getVenueTitle());
    }

    /**
     * Updates the map with the provided coordinates and places a marker there.
     * Translates, and zooms the map to the new coordinates.
     *
     * @param aNewCoordinates the LatLng coordinates used to update the map
     * @param aMarkerTitle the title used for the marker at the new coordinates
     */
    public void updateMap(LatLng aNewCoordinates, String aMarkerTitle) {
        CameraUpdate lUpdate = CameraUpdateFactory.newLatLngZoom(aNewCoordinates, 14.0f);

        final Marker lVenueMarker = this.getMap().addMarker(new MarkerOptions()
                .position(aNewCoordinates)
                .title(aMarkerTitle));

        this.getMap().setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                View v = getLayoutInflater().inflate(R.layout.map_info_view, null);

                TextView venue_title = (TextView) v.findViewById(R.id.venue_title);
                TextView venue_address = (TextView) v.findViewById(R.id.venue_address);
                TextView venue_phone_number = (TextView) v.findViewById(R.id.venue_phone_number);

                venue_title.setText(getEventData().getVenueTitle());
                venue_title.setTypeface(custom_font);

                venue_address.setText(getEventData().getVenueAddress());
                venue_address.setTypeface(custom_small_font);

                venue_phone_number.setText(getEventData().getVenuePhoneNumber());
                venue_phone_number.setTypeface(custom_small_font);

                return v;
            }
        });

        this.getMap().animateCamera(lUpdate);

        // Pop up the info view after the map is done zooming in to the marker
        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                lVenueMarker.showInfoWindow();
            }
        }, INFO_VIEW_POPUP_DELAY);
    }

    /* DATA RELATED GETERS */
    public Event getEventData() {
        return this.iEventData;
    }

    public GoogleMap getMap() {
        return this.iMap;
    }

    /* DATA RELATED SETTERS */
    public void setEventData(Event aEvent) {
        this.iEventData = aEvent;
    }

    public void setMap(GoogleMap aMap) {
        this.iMap = aMap;
    }

    /* VIEW RELATED SETTERS */
    public void setEventTitle(String aTitle) {
        iEventTitle.setText(aTitle);
    }

    public void setEventStartTime(String aStartTime) {
        iEventStartTime.setText(aStartTime);
    }

    public void setEventEndTime(String aEndTime) {
        iEventEndTime.setText(aEndTime);
    }

    public void setEventDateMonth(String aMonth) {
        iEventDateMonth.setText(aMonth);
    }

    public void setEventDateDay(String aDay) {
        iEventDateDay.setText(aDay);
    }

    public void setEventPrice(String aPrice) {
        if(aPrice.equals("0")) {
            iEventPrice.setTextSize(18);
            iEventPrice.setText("FREE");
        } else {
            iEventPrice.setText("$" + aPrice);
        }
    }
}
