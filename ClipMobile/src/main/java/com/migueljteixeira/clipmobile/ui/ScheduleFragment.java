package com.migueljteixeira.clipmobile.ui;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.migueljteixeira.clipmobile.R;
import com.migueljteixeira.clipmobile.adapters.ScheduleListViewAdapter;
import com.migueljteixeira.clipmobile.entities.StudentScheduleClass;

import java.util.List;

public class ScheduleFragment extends Fragment {

    private List<StudentScheduleClass> classes;

    public ScheduleFragment() {}

    @SuppressLint("ValidFragment")
    public ScheduleFragment(List<StudentScheduleClass> classes) {
        this.classes = classes;
    }

    /*public static ScheduleFragment newInstance(int position) {
        ScheduleFragment f = new ScheduleFragment();
        Bundle b = new Bundle();
        b.putSparseParcelableArray("1", new SparseArray<List<StudentScheduleClass>>(5));
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schedule, container, false);
        ListView listView = (ListView) view.findViewById(R.id.list_view);

        ScheduleListViewAdapter adapter = new ScheduleListViewAdapter(getActivity());

        if (classes != null) {
            for (StudentScheduleClass c : classes) {
                adapter.add(new ListViewItem(c.getName(), c.getType(), c.getHourStart(),
                        c.getHourEnd(), c.getRoom()));
            }
        }

        listView.setAdapter(adapter);

        return view;
    }

    public class ListViewItem {

        public String name, type, hour_start, hour_end, room;

        public ListViewItem(String name, String type, String hour_start, String hour_end, String room) {
            this.name = name;
            this.type = type;
            this.hour_start = hour_start;
            this.hour_end = hour_end;
            this.room = room;
        }
    }
}
