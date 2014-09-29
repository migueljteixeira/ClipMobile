package com.migueljteixeira.clipmobile.ui;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.migueljteixeira.clipmobile.R;
import com.migueljteixeira.clipmobile.settings.ClipSettings;

public class StudentNumbersActivity extends Activity {

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_student_numbers, menu);

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings :
                System.out.println("settings!");
                break;

            case R.id.logout :
                // Clear user personal data
                ClipSettings.logoutUser(this);

                Intent intent = new Intent(getApplicationContext(), ConnectClipActivity.class);
                startActivity(intent);

                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

}
