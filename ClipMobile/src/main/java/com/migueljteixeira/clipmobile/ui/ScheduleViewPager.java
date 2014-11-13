package com.migueljteixeira.clipmobile.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.astuetz.PagerSlidingTabStrip;
import com.migueljteixeira.clipmobile.R;
import com.migueljteixeira.clipmobile.adapters.ScheduleViewPagerAdapter;
import com.migueljteixeira.clipmobile.entities.Student;
import com.migueljteixeira.clipmobile.util.tasks.GetStudentScheduleTask;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ScheduleViewPager extends Fragment implements GetStudentScheduleTask.OnTaskFinishedListener {

    @InjectView(R.id.progress_spinner) FrameLayout mProgressSpinner;
    private GetStudentScheduleTask mTask;
    private View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Retain this fragment across configuration changes.
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_viewpager, container, false);
        ButterKnife.inject(this, view);

        System.out.println("SCHEDULEVIEWPAGER ONCREATEVIEW !!!!!");

        // Show progress spinner
        showProgressSpinnerOnly(true);

        // Start AsyncTask
        GetStudentScheduleTask mTask = new GetStudentScheduleTask(getActivity(),
                ScheduleViewPager.this);
        mTask.execute();

        return view;
    }

    /**
     * Shows the progress spinner
     */
    protected void showProgressSpinnerOnly(final boolean show) {
        mProgressSpinner.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        ButterKnife.reset(this);
    }

    @Override
    public void onTaskFinished(Student result) {
        showProgressSpinnerOnly(false);

        // Server is unavailable right now
        if(result == null) return;

        // Initialize the ViewPager and set an adapter
        ViewPager pager = (ViewPager) view.findViewById(R.id.view_pager);
        pager.setAdapter(new ScheduleViewPagerAdapter(getChildFragmentManager(),
                getResources().getStringArray(R.array.schedule_tab_array), result));
        pager.setPageTransformer(true, new DepthPageTransformer());

        // Bind the tabs to the ViewPager
        PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) view.findViewById(R.id.tabs);
        tabs.setShouldExpand(true);
        tabs.setIndicatorColorResource(R.color.actionbar_color);
        tabs.setTabBackground(R.drawable.clipmobile_list_selector_holo_light);
        tabs.setViewPager(pager);

    }
}
