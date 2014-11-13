package com.migueljteixeira.clipmobile.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.migueljteixeira.clipmobile.entities.Student;
import com.migueljteixeira.clipmobile.entities.StudentScheduleClass;
import com.migueljteixeira.clipmobile.ui.ScheduleFragment;

import java.util.List;

public class ScheduleViewPagerAdapter extends FragmentPagerAdapter {

    private final String[] tabNames;
    private Student student;

    public ScheduleViewPagerAdapter(FragmentManager fm, String[] tabNames, Student student) {
        super(fm);
        this.tabNames = tabNames;
        this.student = student;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return tabNames[position];
    }

    @Override
    public Fragment getItem(int position) {
        int day = position + 2;
        List<StudentScheduleClass> classes = student.getScheduleClasses().get(day);

        System.out.println("DAY " + " , " + day + " c " + classes);

        return new ScheduleFragment(classes);
    }

    @Override
    public int getCount() {
        return tabNames.length;
    }

}
