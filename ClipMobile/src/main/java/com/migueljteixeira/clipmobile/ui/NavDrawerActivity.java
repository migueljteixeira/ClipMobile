package com.migueljteixeira.clipmobile.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

public class NavDrawerActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    public static final int MENU_ITEM_SCHEDULE_POSITION = 2;
    public static final int MENU_ITEM_CALENDAR_POSITION = 3;
    public static final int MENU_ITEM_CLASSES_POSITION = 4;
    public static final int MENU_ITEM_CANTEEN_MENU_POSITION = 7;

    private DrawerLayout mDrawerLayout;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(new CalligraphyContextWrapper(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_dualpane);
        super.onCreate(savedInstanceState);

        setupNavDrawer();

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.content_frame);
        if (fragment == null) {
            fragment = new ScheduleViewPager();
            fm.beginTransaction().add(R.id.content_frame, fragment).commit();
        }
    }

    public void setupNavDrawer() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        //mDrawerLayout.setFocusableInTouchMode(false);

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
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            mToolbar.setNavigationIcon(R.drawable.ic_drawer);
            mToolbar.setNavigationContentDescription(R.string.drawer_open);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // check if we should toggle the navigation drawer
        if (item != null && item.getItemId() == android.R.id.home) {
            if (mDrawerLayout.isDrawerVisible(GravityCompat.START))
                mDrawerLayout.closeDrawer(GravityCompat.START);
            else
                mDrawerLayout.openDrawer(GravityCompat.START);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // If the device is bigger than 7', keep the drawer opened
        if(getResources().getBoolean(R.bool.drawer_opened)) {
            mDrawerLayout.openDrawer(GravityCompat.START);
            mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_OPEN);
        }
        else {
            mDrawerLayout.closeDrawer(GravityCompat.START);
            mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        }

    }

    @Override
    public void onBackPressed() {
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