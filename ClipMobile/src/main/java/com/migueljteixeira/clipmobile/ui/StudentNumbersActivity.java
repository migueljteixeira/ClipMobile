package com.migueljteixeira.clipmobile.ui;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.migueljteixeira.clipmobile.util.Utils;

public class StudentNumbersActivity extends Activity {

    /*@Override
    protected void onStart() {
        super.onStart();

        // Report the start of the Activity
        GoogleAnalytics.getInstance(this).reportActivityStart(this);
        System.out.println("Track activity start: StudentNumbersActivity");

        Utils.trackView(this, "StudentNumbers Page initialized");
        System.out.println("Track View: StudentNumbers Page initialized");
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FragmentManager fm = getFragmentManager();
        StudentNumbersFragment fragment = (StudentNumbersFragment) fm.findFragmentById(android.R.id.content);

        if (fragment == null) {
            fragment = new StudentNumbersFragment();
            fm.beginTransaction().add(android.R.id.content, fragment).commit();
        }
    }

    /*@Override
    protected void onStop() {
        super.onStop();

        // Report the end of the Activity
        GoogleAnalytics.getInstance(this).reportActivityStop(this);
        System.out.println("Track activity stop: StudentNumbersActivity");
    }*/

}
