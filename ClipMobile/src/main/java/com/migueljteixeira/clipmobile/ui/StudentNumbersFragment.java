package com.migueljteixeira.clipmobile.ui;

import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.migueljteixeira.clipmobile.R;
import com.migueljteixeira.clipmobile.adapters.StudentNumbersAdapter;
import com.migueljteixeira.clipmobile.entities.Student;
import com.migueljteixeira.clipmobile.entities.User;
import com.migueljteixeira.clipmobile.settings.ClipSettings;
import com.migueljteixeira.clipmobile.util.GetStudentNumbersTask;
import com.migueljteixeira.clipmobile.util.GetStudentYearsTask;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class StudentNumbersFragment extends Fragment implements GetStudentNumbersTask.OnTaskFinishedListener,
        GetStudentYearsTask.OnTaskFinishedListener {

    private GetStudentNumbersTask mTask;
    private GetStudentYearsTask mmTask;
    private StudentNumbersAdapter mListAdapter;
    private List<Student> students;
    @InjectView(R.id.progress_spinner) FrameLayout mProgressSpinner;
    @InjectView(R.id.main_view) LinearLayout mMainView;
    @InjectView(R.id.list_view) ExpandableListView mListView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Retain this fragment across configuration changes.
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_student_numbers, container, false);
        ButterKnife.inject(this, view);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // unfinished task around?
        if (mmTask != null && mmTask.getStatus() != AsyncTask.Status.FINISHED)
            showProgressSpinnerOnly(true);

        // The view has been loaded already
        if(mListAdapter != null) {
            mListView.setAdapter(mListAdapter);
            mListView.setOnGroupClickListener(onGroupClickListener);
            mListView.setOnChildClickListener(onChildClickListener);
            return;
        }

        // Show progress spinner
        showProgressSpinner(true);

        // Get logged in user id
        long user_id = ClipSettings.getLoggedInUserId(getActivity());

        // Start AsyncTask
        mTask = new GetStudentNumbersTask(getActivity().getApplicationContext(),
                StudentNumbersFragment.this);
        mTask.execute(user_id);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        ButterKnife.reset(this);
    }

    ExpandableListView.OnGroupClickListener onGroupClickListener = new ExpandableListView.OnGroupClickListener() {
        @Override
        public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

            if(mListView.isGroupExpanded(groupPosition))
                mListView.collapseGroup(groupPosition);

            else {
                // show Progress Bar
                showProgressSpinnerOnly(true);

                mmTask = new GetStudentYearsTask(getActivity().getApplicationContext(),
                        StudentNumbersFragment.this);
                mmTask.execute(students.get(groupPosition), groupPosition);
            }

            return true;
        }
    };

    ExpandableListView.OnChildClickListener onChildClickListener = new ExpandableListView.OnChildClickListener() {

        @Override
        public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

            Intent intent = new Intent(getActivity(), NavDrawerActivity.class);
            startActivity(intent);

            return true;
        }
    };

    /**
     * Shows the progress spinner and hides the login form.
     */
    private void showProgressSpinner(final boolean show) {
        mProgressSpinner.setVisibility(show ? View.VISIBLE : View.GONE);
        mMainView.setVisibility(show ? View.GONE : View.VISIBLE);
    }

    /**
     * Shows the progress spinner
     */
    private void showProgressSpinnerOnly(final boolean show) {
        mProgressSpinner.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onStudentNumbersTaskFinished(User result) {
        students = result.getStudents();
        showProgressSpinner(false);

        mListAdapter = new StudentNumbersAdapter(getActivity(), this.students);
        mListView.setAdapter(mListAdapter);
        mListView.setOnGroupClickListener(onGroupClickListener);
        mListView.setOnChildClickListener(onChildClickListener);
    }

    @Override
    public void onStudentYearsTaskFinished(Student result, int groupPosition) {
        showProgressSpinnerOnly(false);

        // Server is unvailable right now
        if(result == null) return;

        // Set new user data and notifyDataSetChanged
        students.get(groupPosition).setYears(result.getYears());
        mListAdapter.notifyDataSetChanged();

        // Expand group position
        mListView.expandGroup(groupPosition, true);
    }

    /*private void showStudentNumbersDialog(Student student) {
        FragmentManager fm = getFragmentManager();
        StudentYearsDialogFragment dialog = StudentYearsDialogFragment.newInstance(student.getYears());
        dialog.show(fm, "student_numbers_dialog");
    }*/
}
