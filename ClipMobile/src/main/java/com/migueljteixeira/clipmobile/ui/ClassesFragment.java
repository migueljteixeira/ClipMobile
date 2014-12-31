package com.migueljteixeira.clipmobile.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.migueljteixeira.clipmobile.R;
import com.migueljteixeira.clipmobile.adapters.ClassListViewAdapter;
import com.migueljteixeira.clipmobile.entities.Student;
import com.migueljteixeira.clipmobile.entities.StudentClass;
import com.migueljteixeira.clipmobile.settings.ClipSettings;
import com.migueljteixeira.clipmobile.util.tasks.GetStudentClassesTask;
import com.uwetrottmann.androidutils.AndroidUtils;

import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;

@SuppressLint("ValidFragment")
public class ClassesFragment extends BaseFragment implements GetStudentClassesTask.OnTaskFinishedListener {

    private ListView listView;
    private GetStudentClassesTask mTask;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_classes, container, false);
        ButterKnife.inject(this, view);

        listView = (ListView) view.findViewById(R.id.list_view);

        // Show progress spinner
        showProgressSpinner(true);

        // Start AsyncTask
        mTask = new GetStudentClassesTask(getActivity(), ClassesFragment.this);
        AndroidUtils.executeOnPool(mTask);

        return view;
    }

    @Override
    public void onTaskFinished(Student result) {
        showProgressSpinner(false);

        int semester = Integer.parseInt(ClipSettings.getSemesterSelected(getActivity()));
        final ClassListViewAdapter adapter = new ClassListViewAdapter(getActivity());

        if (result.getClasses() != null && result.getClasses().get(semester) != null) {
            List<StudentClass> classes = result.getClasses().get(semester);

            for (StudentClass c : classes)
                adapter.add(new ListViewItem(c.getId(), c.getName(), c.getNumber()));
        }

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListViewItem item = (ListViewItem) adapter.getItem(position);

                // Save class selected and internal classId
                ClipSettings.saveStudentClassSelected(getActivity(), item.number);
                ClipSettings.saveStudentClassIdSelected(getActivity(), item.id);

                Intent intent = new Intent(getActivity(), ClassesDocsActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        cancelTasks(mTask);
    }

    public class ListViewItem {

        public String id, name, number;

        public ListViewItem(String id, String name, String number) {
            this.id = id;
            this.name = name;
            this.number = number;
        }
    }

}
