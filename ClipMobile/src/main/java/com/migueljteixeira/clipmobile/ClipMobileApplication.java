package com.migueljteixeira.clipmobile;

import android.app.Application;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class ClipMobileApplication extends Application {

    public static String CONTENT_AUTHORITY;

    @Override
    public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault("Roboto-Regular.ttf", R.attr.fontPath);

        // Set provider authority
        CONTENT_AUTHORITY = getString(R.string.provider_authority);
    }

}
