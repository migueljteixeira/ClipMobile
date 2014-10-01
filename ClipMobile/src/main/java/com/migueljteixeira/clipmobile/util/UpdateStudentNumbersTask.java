package com.migueljteixeira.clipmobile.util;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.migueljteixeira.clipmobile.R;
import com.migueljteixeira.clipmobile.entities.User;
import com.migueljteixeira.clipmobile.exceptions.ServerUnavailableException;
import com.migueljteixeira.clipmobile.settings.ClipSettings;

public class UpdateStudentNumbersTask extends AsyncTask<Void, Void, User> {

    public interface OnTaskFinishedListener {

        /**
         * Returns one of {@link com.migueljteixeira.clipmobile.enums.NetworkResult}.
         */
        public void onUpdateTaskFinished(User result);

    }

    private Context mContext;
    private OnTaskFinishedListener mListener;

    public UpdateStudentNumbersTask(Context context, OnTaskFinishedListener listener) {
        mContext = context;
        mListener = listener;
    }

    @Override
    protected User doInBackground(Void... params) {
        long userId = ClipSettings.getLoggedInUserId(mContext);

        try {
            // Update students numbers and years
            return StudentTools.updateStudentNumbersAndYears(mContext, userId);
        } catch (ServerUnavailableException e) {
            return null;
        }
    }

    @Override
    protected void onPostExecute(User result) {
        super.onPostExecute(result);

        if(result == null) {
            // Server is unavailable right now
            Toast.makeText(mContext, mContext.getString(R.string.connection_failed),
                    Toast.LENGTH_SHORT).show();
        }

        if (mListener != null)
            mListener.onUpdateTaskFinished(result);
    }
}
