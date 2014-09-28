package com.migueljteixeira.clipmobile.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.migueljteixeira.clipmobile.R;

public class DrawerAdapter extends ArrayAdapter<String> {

    private Context context;
    private int resource;
    private String[] list;

    public DrawerAdapter(Context context, int resource, String[] list) {
        super(context, resource, list);

        this.context = context;
        this.resource = resource;
        this.list = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null)
            convertView = LayoutInflater.from(context).inflate(resource, parent, false);

        TextView textView = (TextView) convertView.findViewById(R.id.text);
        textView.setText(list[position]);

        return convertView;
    }

}