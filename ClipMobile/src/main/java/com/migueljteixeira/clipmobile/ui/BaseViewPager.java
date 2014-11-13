package com.migueljteixeira.clipmobile.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.migueljteixeira.clipmobile.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class BaseViewPager extends Fragment {

    @InjectView(R.id.progress_spinner) FrameLayout mProgressSpinner;
    @InjectView(R.id.view_pager) ViewPager mViewPager;
    protected View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Retain this fragment across configuration changes.
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_viewpager, container, false);
        ButterKnife.inject(this, view);

        // Show progress spinner
        showProgressSpinnerOnly(true);

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

}
