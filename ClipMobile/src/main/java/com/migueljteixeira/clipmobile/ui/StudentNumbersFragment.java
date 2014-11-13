package com.migueljteixeira.clipmobile.ui;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.migueljteixeira.clipmobile.R;
import com.migueljteixeira.clipmobile.adapters.StudentNumbersAdapter;
import com.migueljteixeira.clipmobile.entities.Student;
import com.migueljteixeira.clipmobile.entities.User;
import com.migueljteixeira.clipmobile.settings.ClipSettings;
import com.migueljteixeira.clipmobile.util.tasks.GetStudentNumbersTask;
import com.migueljteixeira.clipmobile.util.tasks.GetStudentYearsTask;
import com.migueljteixeira.clipmobile.util.tasks.UpdateStudentNumbersTask;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class StudentNumbersFragment extends BaseFragment implements GetStudentNumbersTask.OnTaskFinishedListener,
        GetStudentYearsTask.OnTaskFinishedListener, UpdateStudentNumbersTask.OnTaskFinishedListener {

    private GetStudentYearsTask mYearsTask;
    private UpdateStudentNumbersTask mUpdateTask;
    private StudentNumbersAdapter mListAdapter;
    private List<Student> students;
    @InjectView(R.id.list_view) ExpandableListView mListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_student_numbers, container, false);
        ButterKnife.inject(this, view);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // unfinished task around?
        if ( ( mYearsTask != null && mYearsTask.getStatus() != AsyncTask.Status.FINISHED ) ||
                ( mUpdateTask != null && mUpdateTask.getStatus() != AsyncTask.Status.FINISHED ) )
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

        // Start AsyncTask
        GetStudentNumbersTask mNumbersTask = new GetStudentNumbersTask(getActivity(),
                StudentNumbersFragment.this);
        mNumbersTask.execute();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_student_numbers, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refresh :
                System.out.println("refresh!");
                showProgressSpinnerOnly(true);

                // Start AsyncTask
                mUpdateTask = new UpdateStudentNumbersTask(getActivity().getApplicationContext(),
                        StudentNumbersFragment.this);
                mUpdateTask.execute();
                break;

            case R.id.settings :
                System.out.println("settings!");
                break;

            case R.id.logout :
                // Clear user personal data
                ClipSettings.logoutUser(getActivity());

                Intent intent = new Intent(getActivity(), ConnectClipActivity.class);
                startActivity(intent);

                getActivity().finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    ExpandableListView.OnGroupClickListener onGroupClickListener = new ExpandableListView.OnGroupClickListener() {
        @Override
        public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

            if(mListView.isGroupExpanded(groupPosition))
                mListView.collapseGroup(groupPosition);

            else {
                // show Progress Bar
                showProgressSpinnerOnly(true);

                mYearsTask = new GetStudentYearsTask(getActivity().getApplicationContext(),
                        StudentNumbersFragment.this);
                mYearsTask.execute(students.get(groupPosition), groupPosition);
            }

            return true;
        }
    };

    ExpandableListView.OnChildClickListener onChildClickListener = new ExpandableListView.OnChildClickListener() {

        @Override
        public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

            // Save studentId, year and semester selected
            String yearSelected = students.get(groupPosition).getYears().get(childPosition).getYear();

            ClipSettings.saveStudentIdSelected(getActivity(), students.get(groupPosition).getId());
            ClipSettings.saveYearSelected(getActivity(), yearSelected);
            ClipSettings.saveSemesterSelected(getActivity(), "1");

            // Save student numberID and studentYearSemester ID
            ClipSettings.saveStudentNumberId(getActivity(), students.get(groupPosition).getNumberId());
            /*ClipSettings.saveStudentYearSemesterIdSelected(getActivity(), students.get(groupPosition)
                    .getYears().get(childPosition).getId());*/

            Intent intent = new Intent(getActivity(), NavDrawerActivity.class);
            startActivity(intent);

            return true;
        }
    };



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

        // Server is unavailable right now
        if(result == null) return;

        // Set new data and notifyDataSetChanged
        students.get(groupPosition).setYears(result.getYears());
        mListAdapter.notifyDataSetChanged();

        // Expand group position
        mListView.expandGroup(groupPosition, true);
    }

    @Override
    public void onUpdateTaskFinished(User result) {
        showProgressSpinnerOnly(false);

        if(result != null) {
            // Set new data and notifyDataSetChanged
            students.clear();
            students.addAll(result.getStudents());

            System.out.println("updated!");
            mListAdapter.notifyDataSetChanged();
        }
    }
}
