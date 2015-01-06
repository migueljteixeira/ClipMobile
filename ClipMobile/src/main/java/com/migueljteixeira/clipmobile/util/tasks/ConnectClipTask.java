package com.migueljteixeira.clipmobile.util.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.migueljteixeira.clipmobile.R;
import com.migueljteixeira.clipmobile.enums.NetworkResult;
import com.migueljteixeira.clipmobile.enums.Result;
import com.migueljteixeira.clipmobile.exceptions.ServerUnavailableException;
import com.migueljteixeira.clipmobile.util.StudentTools;

public class ConnectClipTask extends AsyncTask<String, Void, Integer> {

    public interface OnTaskFinishedListener {

        /**
         * Returns one of {@link com.migueljteixeira.clipmobile.enums.NetworkResult}.
         */
        public void onTaskFinished(int result);

    }

    private Context mContext;
    private OnTaskFinishedListener mListener;

    public ConnectClipTask(Context context, OnTaskFinishedListener listener) {
        mContext = context;
        mListener = listener;
    }

    @Override
    protected Integer doInBackground(String... params) {
        // Get user data
        String username = params[0];
        String password = params[1];

        try {
            return StudentTools.signIn(mContext, username, password);
        } catch (ServerUnavailableException e) {
            return NetworkResult.OFFLINE;
        }
    }

    @Override
    protected void onPostExecute(Integer result) {
        super.onPostExecute(result);

        switch(result) {
            case NetworkResult.OFFLINE:
                Toast.makeText(mContext,
                        mContext.getString(R.string.connection_failed), Toast.LENGTH_SHORT).show();
                break;

            case Result.ERROR:
                Toast.makeText(mContext,
                        mContext.getString(R.string.error_fields_incorrect), Toast.LENGTH_SHORT).show();
                break;
        }

        if (mListener != null)
            mListener.onTaskFinished(result);
    }
}
