package com.migueljteixeira.clipmobile.util;

import android.content.Context;
import android.os.AsyncTask;

import com.migueljteixeira.clipmobile.entities.Student;
import com.migueljteixeira.clipmobile.enums.NetworkResult;
import com.uwetrottmann.androidutils.AndroidUtils;

import java.util.List;

public class GetStudentNumbersTask extends AsyncTask<Long, Void, List<Student>> {

    public interface OnTaskFinishedListener {

        /**
         * Returns one of {@link com.migueljteixeira.clipmobile.enums.NetworkResult}.
         */
        public void onTaskFinished(List<Student> resultCode);

    }

    private Context mContext;
    private OnTaskFinishedListener mListener;

    public GetStudentNumbersTask(Context context, OnTaskFinishedListener listener) {
        mContext = context;
        mListener = listener;
    }

    @Override
    protected List<Student> doInBackground(Long... params) {
        String user_id = String.valueOf(params[0]);

        // Get students numbers from the database
        List<Student> students = StudentTools.getStudentsNumbers(mContext, user_id);

        return students;
    }

    @Override
    protected void onPostExecute(List<Student> result) {
        super.onPostExecute(result);

        if (mListener != null) {
            mListener.onTaskFinished(result);
        }
    }
}
