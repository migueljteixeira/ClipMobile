package com.migueljteixeira.clipmobile.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.astuetz.PagerSlidingTabStrip;
import com.migueljteixeira.clipmobile.R;
import com.migueljteixeira.clipmobile.adapters.ClassesViewPagerAdapter;
import com.migueljteixeira.clipmobile.entities.Student;
import com.migueljteixeira.clipmobile.util.tasks.GetStudentClassesTask;

public class ClassesViewPager extends BaseViewPager implements GetStudentClassesTask.OnTaskFinishedListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = super.onCreateView(inflater, container, savedInstanceState);

        // Start AsyncTask
        GetStudentClassesTask mTask = new GetStudentClassesTask(getActivity(), ClassesViewPager.this);
        mTask.execute();

        return view;
    }

    @Override
    public void onTaskFinished(Student result) {
        showProgressSpinnerOnly(false);

        // Server is unavailable right now
        if(result == null) return;

        // Initialize the ViewPager and set an adapter
        mViewPager.setAdapter(new ClassesViewPagerAdapter(getChildFragmentManager(),
                getResources().getStringArray(R.array.classes_tab_array), result));
        mViewPager.setPageTransformer(true, new DepthPageTransformer());

        // Bind the tabs to the ViewPager
        PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) view.findViewById(R.id.tabs);
        tabs.setShouldExpand(true);
        tabs.setIndicatorColorResource(R.color.actionbar_color);
        tabs.setTabBackground(R.drawable.clipmobile_list_selector_holo_light);
        tabs.setViewPager(mViewPager);
    }
}
