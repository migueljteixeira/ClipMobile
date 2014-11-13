package com.migueljteixeira.clipmobile.ui;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.migueljteixeira.clipmobile.R;
import com.migueljteixeira.clipmobile.adapters.DrawerAdapter;
import com.migueljteixeira.clipmobile.settings.ClipSettings;
import com.migueljteixeira.clipmobile.util.tasks.UpdateStudentNumbersTask;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class NavDrawerActivity extends ActionBarActivity implements AdapterView.OnItemClickListener {

    public static final int MENU_ITEM_SCHEDULE_POSITION = 2;
    public static final int MENU_ITEM_CALENDAR_POSITION = 3;
    public static final int MENU_ITEM_CLASSES_POSITION = 4;
    public static final int MENU_ITEM_CANTEEN_MENU_POSITION = 7;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private Toolbar mToolbar;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(new CalligraphyContextWrapper(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dualpane);

        setupActionBar();
        setupNavDrawer();

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.content_frame);

        if (fragment == null) {
            fragment = new ScheduleViewPager();
            fm.beginTransaction().replace(R.id.content_frame, fragment).commit();
        }
    }

    private void setupActionBar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitleTextColor(Color.WHITE);
        mToolbar.setTitleTextAppearance(this, R.style.Toolbar);
        //toolbar.setLogo(R.drawable.ic_launcher);

        setSupportActionBar(mToolbar);
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

        // If the device is smaller than 7', hide the drawer
        if(! getResources().getBoolean(R.bool.drawer_opened)) {

            // setup drawer indicator
            mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                    R.string.drawer_open, R.string.drawer_close) {
                public void onDrawerClosed(View view) {
                    invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
                }

                public void onDrawerOpened(View drawerView) {
                    invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
                }
            };
            //mDrawerLayout.setDrawerListener(mDrawerToggle);
            mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, Gravity.START);

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);

            mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {

                    System.out.println("ADF");

                    return false;
                }
            });
/*            mDrawerToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("ASDDADSA");
                }
            });
*/
            //mDrawerLayout.setonclick

            /*gets

            //getSupportActionBar().setDisplayShowTitleEnabled(true);
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mDrawerLayout.isDrawerOpen(Gravity.START))
                        mDrawerLayout.closeDrawer(Gravity.START);
                    else
                        mDrawerLayout.openDrawer(Gravity.START);
                }
            });*/
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
