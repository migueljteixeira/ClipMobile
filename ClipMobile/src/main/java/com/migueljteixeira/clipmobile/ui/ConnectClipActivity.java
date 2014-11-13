package com.migueljteixeira.clipmobile.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;

import com.crashlytics.android.Crashlytics;
import com.migueljteixeira.clipmobile.R;
import com.migueljteixeira.clipmobile.settings.ClipSettings;

import io.fabric.sdk.android.Fabric;

public class ConnectClipActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singlepane);

        setupActionBar();

        Fabric.with(this, new Crashlytics());

        // If the user is already logged in, start the StudentNumbersActivity instead
        if( ClipSettings.isUserLoggedIn(this) ) {
            Intent intent = new Intent(getApplicationContext(), StudentNumbersActivity.class);
            startActivity(intent);

            finish();
        }

        FragmentManager fm = getSupportFragmentManager();
        ConnectClipFragment fragment = (ConnectClipFragment) fm.findFragmentById(R.id.content_frame);

        if (fragment == null) {
            fragment = new ConnectClipFragment();
            fm.beginTransaction().add(R.id.content_frame, fragment).commit();
        }
    }

    private void setupActionBar() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitleTextColor(Color.WHITE);
        mToolbar.setTitleTextAppearance(this, R.style.Toolbar);
        //toolbar.setLogo(R.drawable.ic_launcher);

        setSupportActionBar(mToolbar);
    }

}
