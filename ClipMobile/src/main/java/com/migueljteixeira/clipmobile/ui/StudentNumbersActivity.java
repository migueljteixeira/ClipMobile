package com.migueljteixeira.clipmobile.ui;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;

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

}
