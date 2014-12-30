package com.migueljteixeira.clipmobile.ui;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.migueljteixeira.clipmobile.entities.StudentCalendar;

@SuppressLint("ValidFragment")
public class CalendarDialogFragment extends DialogFragment {

    private final StudentCalendar appointment;

    /*public static CalendarDialogFragment newInstance(StudentCalendar appointment) {
        CalendarDialogFragment f = new CalendarDialogFragment();

        ArrayList<String> list = new ArrayList<String>();
        for(StudentYearSemester s : studentYears)
            list.add(s.getYear());

        Bundle args = new Bundle();
        args.putStringArrayList("itemArray", list);
        f.setArguments(args);

        return f;
    }*/

    public CalendarDialogFragment(StudentCalendar appointment) {
        this.appointment = appointment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Construct Dialog
        ScrollView scrollView = new ScrollView(getActivity());

        LinearLayout layout = new LinearLayout(getActivity());
        layout.setOrientation(LinearLayout.VERTICAL);

        // Add Appointment Name
        TextView textView = new TextView(getActivity());
        textView.setTypeface(textView.getTypeface(), Typeface.BOLD);
        textView.setPadding(16, 16, 16, 16);
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        textView.setText(appointment.getName() /*+ "\n(" + appointment.getNumber() + ")"*/);
        layout.addView(textView);

        // Add Appointment Date
        textView = new TextView(getActivity());
        textView.setPadding(16, 16, 16, 16);
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        textView.setText("Data: " + appointment.getDate());
        layout.addView(textView);

        // Add Appointment Hour
        textView = new TextView(getActivity());
        textView.setPadding(16, 16, 16, 16);
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        textView.setText("Hora: " + appointment.getHour());
        layout.addView(textView);

        // Add Appointment Room
        /*textView = new TextView(getActivity());
        textView.setPadding(16, 16, 16, 16);
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        textView.setText("Salas: " + appointment.getRooms());
        layout.addView(textView);*/

        scrollView.addView(layout);

        return new AlertDialog.Builder(getActivity())
                .setView(scrollView)
                .create();
    }

}
