package com.migueljteixeira.clipmobile.ui;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.migueljteixeira.clipmobile.R;
import com.migueljteixeira.clipmobile.adapters.DrawerAdapter;
import com.migueljteixeira.clipmobile.util.Utils;

public class NavDrawerActivity extends Activity implements AdapterView.OnItemClickListener {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private ListView mDrawerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_drawer);

        setupNavDrawer();

        FragmentManager fm = getFragmentManager();
        ScheduleViewPager fragment = (ScheduleViewPager) fm.findFragmentById(R.id.content_frame);

        if (fragment == null) {
            // Report the start of the Activity
            /*GoogleAnalytics.getInstance(this).reportActivityStart(this);
            System.out.println("Track activity start: NavDrawerActivity");*/

            fragment = new ScheduleViewPager();
            fm.beginTransaction().replace(R.id.content_frame, fragment).commit();
        }

    }

    public void setupNavDrawer() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        //mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        mDrawerList.setAdapter(new DrawerAdapter(this, R.layout.adapter_list_item_drawer, new String[]{"1","2"}));
        mDrawerList.setOnItemClickListener(this);

        // setup drawer indicator
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.drawable.clipmobile_ic_navigation_drawer,
                R.string.drawer_open, R.string.drawer_close) {
            public void onDrawerClosed(View view) {
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle your other action bar items...

        return super.onOptionsItemSelected(item);
    }

    /*@Override
    protected void onStop() {
        super.onStop();

        // Report the end of the Activity
        GoogleAnalytics.getInstance(this).reportActivityStop(this);
        System.out.println("Track activity stop: NavDrawerActivity");
    }*/

}
