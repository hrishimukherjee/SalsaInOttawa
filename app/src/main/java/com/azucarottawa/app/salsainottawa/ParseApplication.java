package com.azucarottawa.app.salsainottawa;

/**
 * Created by Hreeels on 2015-06-30.
 */

import android.app.Application;

import com.parse.Parse;


public class ParseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "aFBjCaqrSiTcsXexaz1DcPSZslVquOlx00ySOCty", "fujXzSDApylGn73yeQeRCycoleNf8yL9zqDLQkoR");
    }
}
