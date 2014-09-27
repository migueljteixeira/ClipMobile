package com.migueljteixeira.clipmobile.util;

import android.content.Context;
import android.os.AsyncTask;

import com.migueljteixeira.clipmobile.entities.Student;

import java.util.List;

public class GetStudentYearsTask extends AsyncTask<String, Void, Student> {

    public interface OnTaskFinishedListener {

        /**
         * Returns one of {@link com.migueljteixeira.clipmobile.enums.NetworkResult}.
         */
        public void onStudentYearsTaskFinished(Student resultCode);

    }

    private Context mContext;
    private OnTaskFinishedListener mListener;

    public GetStudentYearsTask(Context context, OnTaskFinishedListener listener) {
        mContext = context;
        mListener = listener;
    }

    @Override
    protected Student doInBackground(String... params) {
        String studentId = String.valueOf(params[0]);
        String studentNumberId = String.valueOf(params[1]);

        // Get students years from the database
        Student students = StudentTools.getStudentsYears(mContext, studentId, studentNumberId);

        return students;
    }

    @Override
    protected void onPostExecute(Student result) {
        super.onPostExecute(result);

        if (mListener != null) {
            mListener.onStudentYearsTaskFinished(result);
        }
    }
}
