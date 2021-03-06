package com.migueljteixeira.clipmobile.ui;

import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.migueljteixeira.clipmobile.R;

public class BaseActivity extends AppCompatActivity {

    protected Toolbar mToolbar;

    protected void setupActionBar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitleTextColor(Color.WHITE);
        mToolbar.setTitleTextAppearance(this, R.style.Toolbar);

        setSupportActionBar(mToolbar);
    }

    protected void setActionBarShadow() {
        findViewById(R.id.toolbar_shadow).setVisibility(View.VISIBLE);
    }

    protected void hideActionBarShadow() {
        findViewById(R.id.toolbar_shadow).setVisibility(View.GONE);
    }

    protected void cancelTasks(AsyncTask mTask) {
        if (mTask != null && mTask.getStatus() != AsyncTask.Status.FINISHED)
            mTask.cancel(true);
    }

}
