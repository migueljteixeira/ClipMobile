package com.migueljteixeira.clipmobile.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.crashlytics.android.Crashlytics;
import com.migueljteixeira.clipmobile.R;
import com.migueljteixeira.clipmobile.settings.ClipSettings;

import io.fabric.sdk.android.Fabric;

public class ConnectClipActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_singlepane);
        super.onCreate(savedInstanceState);

        Fabric.with(this, new Crashlytics());

        // If the user is already logged in, start the StudentNumbersActivity instead
        if( ClipSettings.isUserLoggedIn(this) ) {
            Intent intent = new Intent(getApplicationContext(), StudentNumbersActivity.class);
            startActivity(intent);

            finish();
        }

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.content_frame);
        if (fragment == null) {
            fragment = new ConnectClipFragment();
            fm.beginTransaction().add(R.id.content_frame, fragment).commit();
        }
    }

}
