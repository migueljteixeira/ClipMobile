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
import android.widget.Toast;

import com.migueljteixeira.clipmobile.R;
import com.migueljteixeira.clipmobile.adapters.StudentNumbersAdapter;
import com.migueljteixeira.clipmobile.entities.Student;
import com.migueljteixeira.clipmobile.entities.User;
import com.migueljteixeira.clipmobile.settings.ClipSettings;
import com.migueljteixeira.clipmobile.ui.dialogs.AboutDialogFragment;
import com.migueljteixeira.clipmobile.util.tasks.GetStudentNumbersTask;
import com.migueljteixeira.clipmobile.util.tasks.GetStudentYearsTask;
import com.migueljteixeira.clipmobile.util.tasks.UpdateStudentNumbersTask;
import com.uwetrottmann.androidutils.AndroidUtils;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class StudentNumbersFragment extends BaseFragment implements GetStudentNumbersTask.OnTaskFinishedListener,
        GetStudentYearsTask.OnTaskFinishedListener, UpdateStudentNumbersTask.OnTaskFinishedListener {

    private StudentNumbersAdapter mListAdapter;
    private List<Student> students;
    @InjectView(R.id.list_view) ExpandableListView mListView;

    private GetStudentYearsTask mYearsTask;
    private UpdateStudentNumbersTask mUpdateTask;
    private GetStudentNumbersTask mNumbersTask;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_student_numbers, container, false);
        ButterKnife.inject(this, view);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Unfinished task around?
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

        showProgressSpinner(true);

        // Start AsyncTask
        mNumbersTask = new GetStudentNumbersTask(getActivity(), StudentNumbersFragment.this);
        AndroidUtils.executeOnPool(mNumbersTask);
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

                Toast.makeText(getActivity(), getActivity().getString(R.string.refreshing),
                        Toast.LENGTH_LONG).show();

                // Start AsyncTask
                mUpdateTask = new UpdateStudentNumbersTask(getActivity(),
                        StudentNumbersFragment.this);
                AndroidUtils.executeOnPool(mUpdateTask);

               return true;

            case R.id.logout :
                System.out.println("logout!");
                // Clear user personal data
                ClipSettings.logoutUser(getActivity());

                Intent intent = new Intent(getActivity(), ConnectClipActivity.class);
                startActivity(intent);

                getActivity().overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
                getActivity().finish();

                return true;

            case R.id.about :
                System.out.println("about!");

                // Create an instance of the dialog fragment and show it
                AboutDialogFragment dialog = new AboutDialogFragment();
                dialog.show(getActivity().getSupportFragmentManager(), "AboutDialogFragment");

                return true;

            default :
                return super.onOptionsItemSelected(item);
        }

    }

    ExpandableListView.OnGroupClickListener onGroupClickListener = new ExpandableListView.OnGroupClickListener() {
        @Override
        public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

            if(mListView.isGroupExpanded(groupPosition))
                mListView.collapseGroup(groupPosition);

            else {
                showProgressSpinnerOnly(true);

                mYearsTask = new GetStudentYearsTask(getActivity(), StudentNumbersFragment.this);
                AndroidUtils.executeOnPool(mYearsTask, students.get(groupPosition), groupPosition);
            }

            return true;
        }
    };

    ExpandableListView.OnChildClickListener onChildClickListener = new ExpandableListView.OnChildClickListener() {

        @Override
        public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

            // Save year, studentId and studentNumberId selected
            String yearSelected = students.get(groupPosition).getYears().get(childPosition).getYear();
            System.out.println("YEAR SELECTED " + yearSelected);

            ClipSettings.saveYearSelected(getActivity(), yearSelected);
            ClipSettings.saveStudentIdSelected(getActivity(), students.get(groupPosition).getId());
            ClipSettings.saveStudentNumberId(getActivity(), students.get(groupPosition).getNumberId());
            ClipSettings.saveStudentYearSemesterIdSelected(getActivity(), students.get(groupPosition)
                    .getYears().get(childPosition).getId());

            // Lets go to NavDrawerActivity
            Intent intent = new Intent(getActivity(), NavDrawerActivity.class);
            startActivity(intent);

            getActivity().overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
            getActivity().finish();

            return true;
        }
    };



    @Override
    public void onStudentNumbersTaskFinished(User result) {
        if(!isAdded())
            return;

        students = result.getStudents();
        showProgressSpinner(false);

        mListAdapter = new StudentNumbersAdapter(getActivity(), this.students);
        mListView.setAdapter(mListAdapter);
        mListView.setOnGroupClickListener(onGroupClickListener);
        mListView.setOnChildClickListener(onChildClickListener);
    }

    @Override
    public void onStudentYearsTaskFinished(Student result, int groupPosition) {
        if(!isAdded())
            return;

        showProgressSpinnerOnly(false);

        // Server is unavailable right now
        if(result == null)
            return;

        // Set new data and notifyDataSetChanged
        students.get(groupPosition).setYears(result.getYears());
        mListAdapter.notifyDataSetChanged();

        // Expand group position
        mListView.expandGroup(groupPosition, true);
    }

    @Override
    public void onUpdateTaskFinished(User result) {
        if(!isAdded())
            return;

        // Server is unavailable right now
        if(result == null)
            return;

        // Set new data and notifyDataSetChanged
        students.clear();
        students.addAll(result.getStudents());

        System.out.println("updated!");
        mListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        cancelTasks(mYearsTask);
        cancelTasks(mUpdateTask);
        cancelTasks(mNumbersTask);
    }
}
