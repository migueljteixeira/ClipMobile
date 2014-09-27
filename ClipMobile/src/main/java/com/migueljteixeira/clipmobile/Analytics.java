package com.migueljteixeira.clipmobile;

import android.content.Context;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

public class Analytics {

    private static Tracker sTracker;

    /**
     * Get the global Tracker instance.
     */
    public static synchronized Tracker getTracker(Context context) {
        if (sTracker == null) {
            sTracker = GoogleAnalytics.getInstance(context.getApplicationContext())
                    .newTracker(R.xml.analytics);
        }
        return sTracker;
    }

}
