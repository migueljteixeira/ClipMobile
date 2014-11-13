package com.migueljteixeira.clipmobile.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;

import com.migueljteixeira.clipmobile.R;

public class StudentNumbersActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singlepane);

        setupActionBar();

        FragmentManager fm = getSupportFragmentManager();
        StudentNumbersFragment fragment = (StudentNumbersFragment) fm.findFragmentById(R.id.content_frame);

        if (fragment == null) {
            fragment = new StudentNumbersFragment();
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
