package com.migueljteixeira.clipmobile;

import android.app.Application;

public class ClipMobileApplication extends Application {

    public static String CONTENT_AUTHORITY;

    @Override
    public void onCreate() {
        super.onCreate();

        // Set provider authority
        CONTENT_AUTHORITY = getString(R.string.provider_authority);
    }
}
