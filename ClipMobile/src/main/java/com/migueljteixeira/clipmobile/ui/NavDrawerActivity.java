package com.migueljteixeira.clipmobile.ui;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.migueljteixeira.clipmobile.R;
import com.migueljteixeira.clipmobile.adapters.DrawerAdapter;
import com.migueljteixeira.clipmobile.settings.ClipSettings;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class NavDrawerActivity extends FragmentActivity implements AdapterView.OnItemClickListener {

    public static final int MENU_ITEM_SCHEDULE_POSITION = 2;
    public static final int MENU_ITEM_CALENDAR_POSITION = 3;
    public static final int MENU_ITEM_CLASSES_POSITION = 4;
    public static final int MENU_ITEM_CANTEEN_MENU_POSITION = 7;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(new CalligraphyContextWrapper(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_drawer);

        setupNavDrawer();

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.content_frame);

        if (fragment == null) {
            fragment = new ScheduleViewPager();
            fm.beginTransaction().replace(R.id.content_frame, fragment).commit();
        }
    }

    public void setupNavDrawer() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.setFocusableInTouchMode(false);

        // Setup menu adapter
        DrawerAdapter drawerAdapter = new DrawerAdapter(this);
        drawerAdapter.add(new DrawerTitle( ClipSettings.getYearSelected(this) ));
        drawerAdapter.add(new DrawerDivider());
        drawerAdapter.add(new DrawerItem(getString(R.string.drawer_schedule), 1));
        drawerAdapter.add(new DrawerItem(getString(R.string.drawer_calendar), 1));
        drawerAdapter.add(new DrawerItem(getString(R.string.drawer_classes), 1));
        drawerAdapter.add(new DrawerTitle(getString(R.string.drawer_title_college)));
        drawerAdapter.add(new DrawerDivider());
        drawerAdapter.add(new DrawerItem(getString(R.string.drawer_canteen_menu), 1));

        ListView mDrawerList = (ListView) findViewById(R.id.left_drawer);
        mDrawerList.setAdapter(drawerAdapter);
        mDrawerList.setItemChecked(MENU_ITEM_SCHEDULE_POSITION, true);
        mDrawerList.setOnItemClickListener(this);

        // If the device is bigger than 7', don't open the drawer
        if(! getResources().getBoolean(R.bool.drawer_opened)) {

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
            mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, Gravity.START);

            getActionBar().setDisplayHomeAsUpEnabled(true);
            getActionBar().setHomeButtonEnabled(true);
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // If the device is bigger than 7', lock the drawer
        if(getResources().getBoolean(R.bool.drawer_opened)) {
            mDrawerLayout.openDrawer(GravityCompat.START);
            mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_OPEN);
        }
        else {
            mDrawerLayout.closeDrawer(GravityCompat.START);
            mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);

            // Sync the toggle state after onRestoreInstanceState has occurred.
            mDrawerToggle.syncState();
        }

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();

        System.out.println("--> " + getResources().getBoolean(R.bool.drawer_opened));

        if(getResources().getBoolean(R.bool.drawer_opened) ||
                !mDrawerLayout.isDrawerOpen(GravityCompat.START))
            finish();
        else
            mDrawerLayout.closeDrawer(GravityCompat.START);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.content_frame);

        switch(position) {
            case MENU_ITEM_SCHEDULE_POSITION:
                fragment = new ScheduleViewPager();
                break;

            case MENU_ITEM_CALENDAR_POSITION:
                fragment = new CalendarFragment();
                break;

            case MENU_ITEM_CLASSES_POSITION:
                fragment = new ClassesViewPager();
                break;

            case MENU_ITEM_CANTEEN_MENU_POSITION:
                fragment = new GradesFragment();
                break;
        }

        // Replace fragment and close drawer
        fm.beginTransaction().replace(R.id.content_frame, fragment).commit();

        // If the device is bigger than 7', don't close the drawer
        if(! getResources().getBoolean(R.bool.drawer_opened))
            mDrawerLayout.closeDrawer(Gravity.START);
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

    public class DrawerItem {

        public String mTitle;
        public int mIconRes;

        public DrawerItem(String title, int iconRes) {
            mTitle = title;
            mIconRes = iconRes;
        }
    }

    public class DrawerTitle extends DrawerItem {

        public DrawerTitle(String title) {
            super(title, 0);
        }
    }

    public class DrawerDivider extends DrawerItem {

        public DrawerDivider() {
            super(null, 0);
        }
    }

}
