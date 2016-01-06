package com.example.hreeels.salsainottawa.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.hreeels.salsainottawa.R;
import com.example.hreeels.salsainottawa.core.Event;

import java.util.ArrayList;

/**
 * Created by Hreeels on 2015-08-04.
 */
public class EventAdapter extends ArrayAdapter<Event> {

    /*
    Keeps a list of background drawables
    for each unique position in the list view.
     */
    private ArrayList<Integer> iViewBackgroundDrawables;

    public EventAdapter(Context context, ArrayList<Event> values) {
        // Used for row_layout_2
        //super(context, R.layout.row_layout_2, values);

        // Used for row_layout_3
        super(context, R.layout.event_list_row_layout, values);

        // Initialize background drawables for view
        iViewBackgroundDrawables = new ArrayList<Integer>();
        this.initializeBackgroundDrawables();
    }

    /*
    Returns the background drawable for the view at the position provided.
     */
    private int getBackgroundDrawableAtPosition(int position) {
        return iViewBackgroundDrawables.get(position);
    }

    /*
    Sets a unique drawable for each position in the background color list.
    The drawables cycle between 3 different ones.
     */
    private void initializeBackgroundDrawables() {
        int currentBackgroundID = 0;

        for(int i = 0; i < getCount(); i++) {
            // Set 1 of 3 unique colors to each position in ArrayList
            if(currentBackgroundID == 0) {
                iViewBackgroundDrawables.add(R.drawable.rounded_button_orange_shade_1);
            } else if(currentBackgroundID == 1) {
                iViewBackgroundDrawables.add(R.drawable.rounded_button_orange_shade_2);
            }

            /* else {
                iViewBackgroundDrawables.add(R.drawable.rounded_button_orange_shade_3);
            } */

            // Cycle color ID between 0 and 2
            if(currentBackgroundID < 1) {
                currentBackgroundID++;
            } else {
                currentBackgroundID = 0;
            }
        }
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        // Get Fonts
        Typeface customFont = Typeface.createFromAsset(getContext().getAssets(), "bebas_neue_regular.ttf");
        Typeface customFontBold = Typeface.createFromAsset(getContext().getAssets(), "bebas_neue_bold.ttf");

        LayoutInflater theInflater = LayoutInflater.from(getContext());

        View theView = theInflater.inflate(R.layout.event_list_row_layout, parent, false);
        theView.setBackgroundResource(this.getBackgroundDrawableAtPosition(position));

        Event myEvent = getItem(position);

        TextView theTextView = (TextView) theView.findViewById(R.id.textView1);
        theTextView.setText(myEvent.getTitle());
        theTextView.setTypeface(customFontBold);

        TextView theSecondTextView = (TextView) theView.findViewById(R.id.textView2);

        if(myEvent.getCost().equals("0")) {
            theSecondTextView.setText("$FREE");
        } else if(myEvent.getCost().equals("")) {
            theSecondTextView.setText("$TBA");
        } else {
            theSecondTextView.setText("$" + myEvent.getCost());
        }

        theSecondTextView.setTypeface(customFont);

        TextView theThirdTextView = (TextView) theView.findViewById(R.id.textView3);
        theThirdTextView.setText(myEvent.getStartTime());
        theThirdTextView.setTypeface(customFont);

        return theView;
    }
}
