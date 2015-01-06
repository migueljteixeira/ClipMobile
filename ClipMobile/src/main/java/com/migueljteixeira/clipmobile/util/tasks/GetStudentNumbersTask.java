package com.migueljteixeira.clipmobile.util.tasks;

import android.content.Context;
import android.os.AsyncTask;

import com.migueljteixeira.clipmobile.entities.User;
import com.migueljteixeira.clipmobile.settings.ClipSettings;
import com.migueljteixeira.clipmobile.util.StudentTools;

public class GetStudentNumbersTask extends AsyncTask<Void, Void, User> {

    public interface OnTaskFinishedListener {

        /**
         * Returns a {@link com.migueljteixeira.clipmobile.entities.User}.
         */
        public void onStudentNumbersTaskFinished(User result);

    }

    private Context mContext;
    private OnTaskFinishedListener mListener;

    public GetStudentNumbersTask(Context context, OnTaskFinishedListener listener) {
        mContext = context;
        mListener = listener;
    }

    @Override
    protected User doInBackground(Void... params) {
        long userId = ClipSettings.getLoggedInUserId(mContext);

        // Get students numbers
        return StudentTools.getStudents(mContext, userId);
    }

    @Override
    protected void onPostExecute(User result) {
        super.onPostExecute(result);

        if (mListener != null)
            mListener.onStudentNumbersTaskFinished(result);
    }
}
