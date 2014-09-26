package com.migueljteixeira.clipmobile.ui;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.widget.Toast;

import com.migueljteixeira.clipmobile.settings.ClipSettings;

public class ConnectClipActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // If the user is already logged in, start the StudentNumbersActivity instead
        if( ClipSettings.isUserLoggedIn(this) ) {
            //Intent intent = new Intent(getApplicationContext(), StudentNumbersActivity.class);
            //startActivity(intent);
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


}
