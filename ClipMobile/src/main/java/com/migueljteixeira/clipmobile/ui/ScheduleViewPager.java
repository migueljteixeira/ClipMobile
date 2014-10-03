package com.migueljteixeira.clipmobile.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.astuetz.PagerSlidingTabStrip;
import com.migueljteixeira.clipmobile.R;
import com.migueljteixeira.clipmobile.adapters.ScheduleViewPagerAdapter;
import com.migueljteixeira.clipmobile.util.GetStudentScheduleTask;

public class ScheduleViewPager extends Fragment {

    private GetStudentScheduleTask mTask;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Retain this fragment across configuration changes.
        setRetainInstance(true);

        System.out.println("SCHEDULEVIEWPAGER ONCREATE");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.viewpager_student_numbers, container, false);

        // Initialize the ViewPager and set an adapter
        ViewPager pager = (ViewPager) view.findViewById(R.id.view_pager);
        pager.setAdapter(new ScheduleViewPagerAdapter(getFragmentManager(),
                getResources().getStringArray(R.array.tab_array) ));

        // Bind the tabs to the ViewPager
        PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) view.findViewById(R.id.tabs);
        tabs.setShouldExpand(true);
        tabs.setIndicatorColorResource(R.color.actionbar_color);
        tabs.setTabBackground(R.drawable.clipmobile_list_selector_holo_light);
        tabs.setViewPager(pager);

        return view;
    }

}
