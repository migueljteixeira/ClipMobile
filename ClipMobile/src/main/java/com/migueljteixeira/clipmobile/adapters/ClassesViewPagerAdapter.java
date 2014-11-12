package com.migueljteixeira.clipmobile.adapters;


import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentStatePagerAdapter;

import com.migueljteixeira.clipmobile.entities.Student;
import com.migueljteixeira.clipmobile.entities.StudentClass;
import com.migueljteixeira.clipmobile.entities.StudentScheduleClass;
import com.migueljteixeira.clipmobile.ui.ClassesFragment;
import com.migueljteixeira.clipmobile.ui.ScheduleFragment;

import java.util.List;

public class ClassesViewPagerAdapter extends FragmentStatePagerAdapter {

    private final String[] tabNames;
    private Student student;

    public ClassesViewPagerAdapter(FragmentManager fm, String[] tabNames, Student student) {
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
        int semester = position + 1;
        List<StudentClass> classes = student.getClasses().get(semester);

        System.out.println("SEMESTER " + " , " + semester + " c " + classes);

        return new ClassesFragment(classes);
    }

    @Override
    public int getCount() {
        return tabNames.length;
    }

}
