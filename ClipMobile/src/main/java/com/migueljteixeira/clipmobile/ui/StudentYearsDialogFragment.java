package com.migueljteixeira.clipmobile.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.migueljteixeira.clipmobile.R;
import com.migueljteixeira.clipmobile.entities.StudentYear;

import java.util.ArrayList;
import java.util.List;

public class StudentYearsDialogFragment extends DialogFragment {

    public static StudentYearsDialogFragment newInstance(List<StudentYear> studentYears) {
        StudentYearsDialogFragment f = new StudentYearsDialogFragment();

        ArrayList<String> list = new ArrayList<String>();
        for(StudentYear s : studentYears)
            list.add(s.getYear());

        Bundle args = new Bundle();
        args.putStringArrayList("itemArray", list);
        f.setArguments(args);

        return f;
    }

    @Override
    public void onStart() {
        super.onStart();
        //Utils.trackView(getActivity(), "Sort Dialog");
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Construct Dialog
        TextView textView = new TextView(getActivity());
        textView.setPadding(16, 16, 16, 16);
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        textView.setText(getActivity().getResources().getString(R.string.years_available));

        ListView listView = new ListView(getActivity());
        listView.setAdapter(new ArrayAdapter<String>(getActivity(), R.layout.drawer_item,
                getArguments().getStringArrayList("itemArray")));
        listView.setOnItemClickListener(onItemClickListener);

        LinearLayout layout = new LinearLayout(getActivity());
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(textView);
        layout.addView(listView);

        return new AlertDialog.Builder(getActivity())
                .setView(layout)
                .create();
    }

    AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            System.out.println("yeah dude! :D");

            Intent intent = new Intent(getActivity(), NavDrawerActivity.class);
            startActivity(intent);
        }
    };

}
