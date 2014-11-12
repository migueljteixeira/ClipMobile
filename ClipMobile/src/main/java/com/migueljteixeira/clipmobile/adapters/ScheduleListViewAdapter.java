package com.migueljteixeira.clipmobile.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.migueljteixeira.clipmobile.R;
import com.migueljteixeira.clipmobile.ui.NavDrawerActivity;
import com.migueljteixeira.clipmobile.ui.ScheduleFragment;

public class ScheduleListViewAdapter extends ArrayAdapter<Object> {
    private Context mContext;

    public ScheduleListViewAdapter(Context context) {
        super(context, 0);
        this.mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if(convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_schedule, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.name = (TextView) convertView.findViewById(R.id.class_name);
            viewHolder.hour_start = (TextView) convertView.findViewById(R.id.class_hour_start);
            viewHolder.hour_end = (TextView) convertView.findViewById(R.id.class_hour_end);
            viewHolder.room = (TextView) convertView.findViewById(R.id.class_room);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        ScheduleFragment.ListViewItem item = (ScheduleFragment.ListViewItem) getItem(position);
        viewHolder.name.setText(item.name + " (" + item.type + ")");
        viewHolder.hour_start.setText(item.hour_start);
        viewHolder.hour_end.setText(item.hour_end);
        viewHolder.room.setText(item.room);

        return convertView;
    }

    static class ViewHolder {

        TextView name;
        TextView hour_start, hour_end;
        TextView room;
    }

}