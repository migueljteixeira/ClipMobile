package com.migueljteixeira.clipmobile.ui;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.migueljteixeira.clipmobile.settings.ClipSettings;
import com.migueljteixeira.clipmobile.util.Utils;

public class ConnectClipActivity extends Activity {

    /*@Override
    protected void onStart() {
        super.onStart();

        // Report the start of the Activity
        GoogleAnalytics.getInstance(this).reportActivityStart(this);
        System.out.println("Track activity start: ConnectClipActivity");

        Utils.trackView(this, "App initialized");
        System.out.println("Track View: App initialized");
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Crashlytics.start(this);

        // If the user is already logged in, start the StudentNumbersActivity instead
        if( ClipSettings.isUserLoggedIn(this) ) {
            Intent intent = new Intent(getApplicationContext(), StudentNumbersActivity.class);
            startActivity(intent);

            Toast.makeText(this, "you're already logged in, dude!", Toast.LENGTH_SHORT).show();
            finish();
        }

        FragmentManager fm = getFragmentManager();
        ConnectClipFragment fragment = (ConnectClipFragment) fm.findFragmentById(android.R.id.content);

        if (fragment == null) {
            fragment = new ConnectClipFragment();
            fm.beginTransaction().add(android.R.id.content, fragment).commit();
        }
    }

    /*@Override
    protected void onStop() {
        super.onStop();

        // Report the end of the Activity
        GoogleAnalytics.getInstance(this).reportActivityStop(this);
        System.out.println("Track activity stop: ConnectClipActivity");
    }*/
}
