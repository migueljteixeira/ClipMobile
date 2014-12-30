package com.migueljteixeira.clipmobile.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.migueljteixeira.clipmobile.entities.Student;
import com.migueljteixeira.clipmobile.entities.StudentCalendar;
import com.migueljteixeira.clipmobile.entities.StudentScheduleClass;
import com.migueljteixeira.clipmobile.ui.CalendarFragment;
import com.migueljteixeira.clipmobile.ui.ScheduleFragment;

import java.util.List;
import java.util.Map;

public class CalendarViewPagerAdapter extends FragmentPagerAdapter {

    private final String[] tabNames;
    private Student student;

    public CalendarViewPagerAdapter(FragmentManager fm, String[] tabNames, Student student) {
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
        List<StudentCalendar> calendar = student.getStudentCalendar().get(position == 1);

        return new CalendarFragment(calendar);
    }

    @Override
    public int getCount() {
        return tabNames.length;
    }

}
