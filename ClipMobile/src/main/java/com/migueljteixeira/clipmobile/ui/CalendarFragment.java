package com.migueljteixeira.clipmobile.ui;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.migueljteixeira.clipmobile.R;
import com.migueljteixeira.clipmobile.adapters.CalendarViewPagerAdapter;
import com.migueljteixeira.clipmobile.entities.StudentCalendar;
import com.migueljteixeira.clipmobile.settings.ClipSettings;
import com.migueljteixeira.clipmobile.ui.dialogs.CalendarDialogFragment;
import com.squareup.timessquare.CalendarPickerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class CalendarFragment extends Fragment implements CalendarPickerView.OnDateSelectedListener {

    private final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");;
    private List<StudentCalendar> calendar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        calendar = getArguments().getParcelableArrayList(CalendarViewPagerAdapter.CALENDAR_TAG);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);

        Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR, 1);

        CalendarPickerView calendar = (CalendarPickerView) view.findViewById(R.id.calendar_view);

        // set calendar background color
        Resources resources = getActivity().getResources();
        calendar.setBackgroundColor(resources.getColor(R.color.main_background_color));

        List<Date> dates = new LinkedList<Date>();

        if(this.calendar != null) {
            for (StudentCalendar appointment : this.calendar) {
                try {
                    Date date = format.parse(appointment.getDate());
                    dates.add(date);
                } catch (ParseException e) {
                    System.out.println("AHHH ParseException!");
                }
            }
        }

        calendar.init(ClipSettings.getSemesterStartDate(getActivity()),
                ClipSettings.getSemesterEndDate(getActivity()))
                .withHighlightedDates(dates);

        calendar.setOnDateSelectedListener(this);

        return view;
    }

    @Override
    public void onDateSelected(Date date) {
        if(this.calendar != null) {

            Date cellDate;
            for (StudentCalendar appointment : this.calendar) {
                try {
                    cellDate = format.parse(appointment.getDate());

                    if(cellDate.equals(date)) {
                        // Create an instance of the dialog fragment and show it
                        DialogFragment dialog = new CalendarDialogFragment(appointment);
                        dialog.show(getFragmentManager(), "CalendarDialogFragment");
                    }

                } catch (ParseException e) {
                    System.out.println("AHHH ParseException!");
                }
            }
        }
    }

    @Override
    public void onDateUnselected(Date date) {
        //
    }
}
