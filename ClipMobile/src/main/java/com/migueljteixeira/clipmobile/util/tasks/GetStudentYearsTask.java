package com.migueljteixeira.clipmobile.util.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.migueljteixeira.clipmobile.R;
import com.migueljteixeira.clipmobile.entities.Student;
import com.migueljteixeira.clipmobile.exceptions.ServerUnavailableException;
import com.migueljteixeira.clipmobile.settings.ClipSettings;
import com.migueljteixeira.clipmobile.util.StudentTools;

public class GetStudentYearsTask extends AsyncTask<Object, Void, Student> {

    public interface OnTaskFinishedListener {

        /**
         * Returns one of {@link com.migueljteixeira.clipmobile.enums.NetworkResult}.
         */
        public void onStudentYearsTaskFinished(Student resultCode, int groupPosition);

    }

    private Context mContext;
    private OnTaskFinishedListener mListener;
    private Integer groupPosition;

    public GetStudentYearsTask(Context context, OnTaskFinishedListener listener) {
        mContext = context;
        mListener = listener;
    }

    @Override
    protected Student doInBackground(Object... params) {
        Student student = (Student) params[0];
        groupPosition = (Integer) params[1];

        try {
            // Get students years
            return StudentTools.getStudentsYears(mContext, student.getId(), student.getNumberId());
        } catch (ServerUnavailableException e) {
            return null;
        }
    }

    @Override
    protected void onPostExecute(Student result) {
        super.onPostExecute(result);

        Toast.makeText(mContext, "" + ClipSettings.TimeCookie(mContext),
                Toast.LENGTH_SHORT).show();

        if(result == null) {
            // Server is unavailable right now
            Toast.makeText(mContext, mContext.getString(R.string.connection_failed),
                    Toast.LENGTH_SHORT).show();
        }

        if (mListener != null)
            mListener.onStudentYearsTaskFinished(result, groupPosition);
    }
}
