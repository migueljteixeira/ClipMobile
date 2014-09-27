package com.migueljteixeira.clipmobile.ui;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.migueljteixeira.clipmobile.R;
import com.migueljteixeira.clipmobile.adapters.StudentNumbersAdapter;
import com.migueljteixeira.clipmobile.entities.Student;
import com.migueljteixeira.clipmobile.entities.StudentYear;
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
    @InjectView(R.id.list_view) ListView mListView;

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
            mListView.setOnItemClickListener(onItemClickListener);
            return;
        }

        // show Progress Bar
        showProgressSpinner(true);

        // Get logged in user id
        long user_id = ClipSettings.getLoggedInUserId(getActivity());

        // start AsyncTask
        mTask = new GetStudentNumbersTask(getActivity().getApplicationContext(),
                StudentNumbersFragment.this);
        mTask.execute(user_id);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        ButterKnife.reset(this);
    }

    AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            // show Progress Bar
            showProgressSpinnerOnly(true);

            Student student = students.get(position);
            System.out.println("Shit son! " + student.getId() + " , " + student.getNumberId());

            mmTask = new GetStudentYearsTask(getActivity().getApplicationContext(),
                    StudentNumbersFragment.this);
            mmTask.execute(student.getId(), student.getNumberId());

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

        mListAdapter = new StudentNumbersAdapter(getActivity(),
                R.layout.adapter_student_numbers, result);
        mListView.setAdapter(mListAdapter);
        mListView.setOnItemClickListener(onItemClickListener);
    }

    @Override
    public void onStudentYearsTaskFinished(Student result) {
        showProgressSpinnerOnly(false);

        System.out.println("finished! " + result.getYears().size());

        for(StudentYear year :result.getYears()) {
            System.out.println("year: " + year.getId() + " , " + year.getYear());
        }

    }
}
