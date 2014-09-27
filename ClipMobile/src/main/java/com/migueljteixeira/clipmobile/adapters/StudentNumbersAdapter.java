package com.migueljteixeira.clipmobile.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.migueljteixeira.clipmobile.R;
import com.migueljteixeira.clipmobile.entities.Student;
import com.migueljteixeira.clipmobile.entities.User;

import java.util.List;

public class StudentNumbersAdapter extends ArrayAdapter<Student> {

    private final int resource;
    private final List<Student> students;
    private final Context context;

    public StudentNumbersAdapter(Context context, int resource, User list) {
        super(context, resource, list.getStudents());

        this.context = context;
        this.resource = resource;
        this.students = list.getStudents();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null)
            convertView = LayoutInflater.from(context).inflate(resource, parent, false);

        TextView textView = (TextView) convertView.findViewById(R.id.text);
        textView.setText(students.get(position).getNumber());

        return convertView;
    }

}
