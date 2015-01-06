package com.migueljteixeira.clipmobile.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.migueljteixeira.clipmobile.R;
import com.migueljteixeira.clipmobile.adapters.DrawerAdapter;
import com.migueljteixeira.clipmobile.entities.Student;
import com.migueljteixeira.clipmobile.settings.ClipSettings;
import com.migueljteixeira.clipmobile.ui.dialogs.AboutDialogFragment;
import com.migueljteixeira.clipmobile.util.tasks.UpdateStudentPageTask;
import com.uwetrottmann.androidutils.AndroidUtils;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class NavDrawerActivity extends BaseActivity implements AdapterView.OnItemClickListener,
        UpdateStudentPageTask.OnTaskFinishedListener {

    private static final String CURRENT_ACTIVITY_TITLE_TAG = "activity_title";
    private static final int MENU_ITEM_SCHEDULE_POSITION = 2;
    private static final int MENU_ITEM_CALENDAR_POSITION = 3;
    private static final int MENU_ITEM_CLASSES_POSITION = 4;
    private static final int MENU_ITEM_INFO_CONTACTS_POSITION = 7;

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private UpdateStudentPageTask mUpdateTask;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(new CalligraphyContextWrapper(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_dualpane);
        super.onCreate(savedInstanceState);

        setupNavDrawer();

        // Set toolbar title
        if(savedInstanceState == null)
            setTitle(R.string.drawer_schedule);
        else {
            String title = savedInstanceState.getString(CURRENT_ACTIVITY_TITLE_TAG);
            if (title != null)
                setTitle(title);
        }

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.content_frame);
        if (fragment == null) {
            fragment = new ScheduleViewPager();
            fm.beginTransaction().add(R.id.content_frame, fragment).commit();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(CURRENT_ACTIVITY_TITLE_TAG, getTitle().toString());
    }

    public void setupNavDrawer() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        // Setup menu adapter
        DrawerAdapter drawerAdapter = new DrawerAdapter(this);
        drawerAdapter.add(new DrawerTitle( ClipSettings.getYearSelected(this) ));
        drawerAdapter.add(new DrawerDivider());
        drawerAdapter.add(new DrawerItem(getString(R.string.drawer_schedule), 1));
        drawerAdapter.add(new DrawerItem(getString(R.string.drawer_calendar), 1));
        drawerAdapter.add(new DrawerItem(getString(R.string.drawer_classes), 1));
        drawerAdapter.add(new DrawerTitle(getString(R.string.drawer_info_title)));
        drawerAdapter.add(new DrawerDivider());
        drawerAdapter.add(new DrawerItem(getString(R.string.drawer_info_contacts), 1));

        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        mDrawerList.setAdapter(drawerAdapter);
        mDrawerList.setItemChecked(MENU_ITEM_SCHEDULE_POSITION, true);
        mDrawerList.setOnItemClickListener(this);

        // If the device is smaller than 7',
        // hide the drawer and set the icon
        if(! getResources().getBoolean(R.bool.drawer_opened)) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            mToolbar.setNavigationIcon(R.drawable.ic_drawer);
            mToolbar.setNavigationContentDescription(R.string.drawer_open);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_student_page, menu);

        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        int semester = ClipSettings.getSemesterSelected(this);

        if(semester == 1)
            menu.findItem(R.id.semester1).setChecked(true);
        else
            menu.findItem(R.id.semester2).setChecked(true);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Check if we should toggle the navigation drawer
        if (item.getItemId() == android.R.id.home) {
            if (mDrawerLayout.isDrawerVisible(GravityCompat.START))
                mDrawerLayout.closeDrawer(GravityCompat.START);
            else
                mDrawerLayout.openDrawer(GravityCompat.START);
        }

        else if(item.getItemId() == R.id.refresh) {
            System.out.println("refresh!");

            // Refreshing
            Toast.makeText(this, getString(R.string.refreshing),
                    Toast.LENGTH_LONG).show();

            // Start AsyncTask
            mUpdateTask = new UpdateStudentPageTask(this, NavDrawerActivity.this);
            AndroidUtils.executeOnPool(mUpdateTask);
        }

        else if(item.getItemId() == R.id.logout) {
            // Clear user personal data
            ClipSettings.logoutUser(this);

            Intent intent = new Intent(this, ConnectClipActivity.class);
            startActivity(intent);

            overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
            finish();
        }

        else if(item.getItemId() == R.id.about) {
            System.out.println("about!");

            // Create an instance of the dialog fragment and show it
            AboutDialogFragment dialog = new AboutDialogFragment();
            dialog.show(getSupportFragmentManager(), "AboutDialogFragment");
        }

        else if(!item.isChecked() && (item.getItemId() == R.id.semester1 || item.getItemId() == R.id.semester2)) {
            // Check item
            item.setChecked(true);

            if(item.getItemId() == R.id.semester1)
                ClipSettings.saveSemesterSelected(this, 1);
            else
                ClipSettings.saveSemesterSelected(this, 2);

            // Refresh current view
            mDrawerList.performItemClick(mDrawerList.getChildAt(mDrawerList.getCheckedItemPosition()),
                    mDrawerList.getCheckedItemPosition(),
                    mDrawerList.getAdapter().getItemId(mDrawerList.getCheckedItemPosition()));
        }

        else
            return super.onOptionsItemSelected(item);

        return true;
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
                !mDrawerLayout.isDrawerOpen(GravityCompat.START)) {

            FragmentManager fm = getSupportFragmentManager();
            Fragment fragment = fm.findFragmentByTag(ClassesDocsFragment.FRAGMENT_TAG);
            if(fragment != null) {
                // Refresh current view
                mDrawerList.performItemClick(mDrawerList.getChildAt(mDrawerList.getCheckedItemPosition()),
                        mDrawerList.getCheckedItemPosition(),
                        mDrawerList.getAdapter().getItemId(mDrawerList.getCheckedItemPosition()));

                return;
            }

            Intent intent = new Intent(this, StudentNumbersActivity.class);
            startActivity(intent);

            overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
            finish();
        } else
            mDrawerLayout.closeDrawer(GravityCompat.START);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.content_frame);

        switch(position) {
            case MENU_ITEM_SCHEDULE_POSITION:
                setTitle(R.string.drawer_schedule);
                fragment = new ScheduleViewPager();
                break;

            case MENU_ITEM_CALENDAR_POSITION:
                setTitle(R.string.drawer_calendar);
                fragment = new CalendarViewPager();
                break;

            case MENU_ITEM_CLASSES_POSITION:
                setTitle(R.string.drawer_classes);
                fragment = new ClassesFragment();
                ((ClassesFragment) fragment).init(this);
                break;

            case MENU_ITEM_INFO_CONTACTS_POSITION:
                setTitle(R.string.drawer_info_contacts);
                fragment = new InfoContactsFragment();
                break;
        }

        // Replace fragment and close drawer
        fm.beginTransaction().replace(R.id.content_frame, fragment).commit();

        // If the device is bigger than 7', don't close the drawer
        if(! getResources().getBoolean(R.bool.drawer_opened))
            mDrawerLayout.closeDrawer(Gravity.START);
    }

    @Override
    public void onUpdateTaskFinished(Student result) {
        if(isFinishing())
            return;

        // Refresh current view
        mDrawerList.performItemClick(mDrawerList.getChildAt(mDrawerList.getCheckedItemPosition()),
                mDrawerList.getCheckedItemPosition(),
                mDrawerList.getAdapter().getItemId(mDrawerList.getCheckedItemPosition()));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        cancelTasks(mUpdateTask);
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