package com.migueljteixeira.clipmobile.adapters;


import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentStatePagerAdapter;

import com.migueljteixeira.clipmobile.ui.ScheduleFragment;

public class ScheduleViewPagerAdapter extends FragmentStatePagerAdapter {

    private final String[] tabNames;

    public ScheduleViewPagerAdapter(FragmentManager fm, String[] tabNames) {
        super(fm);
        this.tabNames = tabNames;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabNames[position];
    }

    @Override
    public Fragment getItem(int position) {
        return ScheduleFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return tabNames.length;
    }


}
