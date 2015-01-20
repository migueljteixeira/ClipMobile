package com.migueljteixeira.clipmobile.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.crashlytics.android.Crashlytics;
import com.migueljteixeira.clipmobile.R;

public class StudentNumbersActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_singlepane);
        super.onCreate(savedInstanceState);

        Crashlytics.log("StudentNumbersActivity - onCreate");

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.content_frame);
        if (fragment == null) {
            fragment = new StudentNumbersFragment();
            fm.beginTransaction().add(R.id.content_frame, fragment).commit();
        }
    }

}
