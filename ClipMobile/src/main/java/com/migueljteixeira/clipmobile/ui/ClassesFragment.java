package com.migueljteixeira.clipmobile.ui;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.migueljteixeira.clipmobile.R;
import com.migueljteixeira.clipmobile.adapters.ClassListViewAdapter;
import com.migueljteixeira.clipmobile.adapters.ScheduleListViewAdapter;
import com.migueljteixeira.clipmobile.entities.StudentClass;
import com.migueljteixeira.clipmobile.entities.StudentScheduleClass;

import java.util.List;

import butterknife.ButterKnife;

public class ClassesFragment extends Fragment {

    private List<StudentClass> classes;

    public ClassesFragment() {}

    @SuppressLint("ValidFragment")
    public ClassesFragment(List<StudentClass> classes) {
        this.classes = classes;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_classes, container, false);
        ListView listView = (ListView) view.findViewById(R.id.list_view);

        ClassListViewAdapter adapter = new ClassListViewAdapter(getActivity());

        if (classes != null) {
            for (StudentClass c : classes)
                adapter.add(new ListViewItem(c.getName(), c.getNumber()));
        }

        listView.setAdapter(adapter);

        return view;
    }

    public class ListViewItem {

        public String name, number;

        public ListViewItem(String name, String number) {
            this.name = name;
            this.number = number;
        }
    }

}
