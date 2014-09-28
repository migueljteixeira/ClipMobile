package com.migueljteixeira.clipmobile;

import android.app.Application;

import com.google.android.gms.analytics.GoogleAnalytics;

public class ClipMobileApplication extends Application {

    public static String CONTENT_AUTHORITY;

    @Override
    public void onCreate() {
        super.onCreate();

        // Set provider authority
        CONTENT_AUTHORITY = getString(R.string.provider_authority);

        // Initialize tracker
        //Analytics.getTracker(this);
    }

}
