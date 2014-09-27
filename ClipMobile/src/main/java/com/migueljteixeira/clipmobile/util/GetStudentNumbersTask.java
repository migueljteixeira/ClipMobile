package com.migueljteixeira.clipmobile.util;

import android.content.Context;
import android.os.AsyncTask;

import com.migueljteixeira.clipmobile.entities.Student;
import com.migueljteixeira.clipmobile.entities.User;
import com.migueljteixeira.clipmobile.enums.NetworkResult;
import com.uwetrottmann.androidutils.AndroidUtils;

import java.util.List;

public class GetStudentNumbersTask extends AsyncTask<Long, Void, User> {

    public interface OnTaskFinishedListener {

        /**
         * Returns one of {@link com.migueljteixeira.clipmobile.enums.NetworkResult}.
         */
        public void onStudentNumbersTaskFinished(User resultCode);

    }

    private Context mContext;
    private OnTaskFinishedListener mListener;

    public GetStudentNumbersTask(Context context, OnTaskFinishedListener listener) {
        mContext = context;
        mListener = listener;
    }

    @Override
    protected User doInBackground(Long... params) {
        String user_id = String.valueOf(params[0]);

        // Get students numbers from the database
        User user = StudentTools.getStudents(mContext, user_id);

        return user;
    }

    @Override
    protected void onPostExecute(User result) {
        super.onPostExecute(result);

        if (mListener != null) {
            mListener.onStudentNumbersTaskFinished(result);
        }
    }
}
