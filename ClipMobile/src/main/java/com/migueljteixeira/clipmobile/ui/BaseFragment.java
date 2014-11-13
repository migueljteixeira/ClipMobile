package com.migueljteixeira.clipmobile.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.migueljteixeira.clipmobile.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class BaseFragment extends Fragment {

    @InjectView(R.id.progress_spinner) FrameLayout mProgressSpinner;
    @InjectView(R.id.main_view) LinearLayout mMainView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Retain this fragment across configuration changes.
        setRetainInstance(true);

        setHasOptionsMenu(true);
    }

    /**
     * Shows the progress spinner and hides the login form.
     */
    protected void showProgressSpinner(final boolean show) {
        mProgressSpinner.setVisibility(show ? View.VISIBLE : View.GONE);
        mMainView.setVisibility(show ? View.GONE : View.VISIBLE);
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

        /*private void showStudentNumbersDialog(Student student) {
        FragmentManager fm = getFragmentManager();
        StudentYearsDialogFragment dialog = StudentYearsDialogFragment.newInstance(student.getYears());
        dialog.show(fm, "student_numbers_dialog");
    }*/
}
