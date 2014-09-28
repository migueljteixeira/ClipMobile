package com.migueljteixeira.clipmobile.util;

import android.content.Context;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.migueljteixeira.clipmobile.Analytics;

public class Utils {

    /**
     * Track a screen view. This is commonly called in onStart() method.
     */
    /*public static void trackView(Context context, String screenName) {
        Tracker tracker = Analytics.getTracker(context);
        tracker.setScreenName(screenName);
        tracker.send(new HitBuilders.AppViewBuilder().build());
    }*/

}
