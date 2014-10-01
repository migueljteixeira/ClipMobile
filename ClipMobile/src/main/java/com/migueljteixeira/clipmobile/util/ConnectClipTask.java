package com.migueljteixeira.clipmobile.util;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.migueljteixeira.clipmobile.R;
import com.migueljteixeira.clipmobile.enums.NetworkResult;
import com.migueljteixeira.clipmobile.exceptions.ServerUnavailableException;
import com.uwetrottmann.androidutils.AndroidUtils;

public class ConnectClipTask extends AsyncTask<String, Void, Integer> {

    public interface OnTaskFinishedListener {

        /**
         * Returns one of {@link com.migueljteixeira.clipmobile.enums.NetworkResult}.
         */
        public void onTaskFinished(int resultCode);

    }

    private Context mContext;
    private OnTaskFinishedListener mListener;

    public ConnectClipTask(Context context, OnTaskFinishedListener listener) {
        mContext = context;
        mListener = listener;
    }

    @Override
    protected Integer doInBackground(String... params) {
        // check for connectivity
        if (!AndroidUtils.isNetworkConnected(mContext)) {
            return NetworkResult.OFFLINE;
        }

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
    protected void onPostExecute(Integer resultCode) {
        super.onPostExecute(resultCode);

        if(resultCode == NetworkResult.OFFLINE) {
            // Server is unavailable right now
            Toast.makeText(mContext, mContext.getString(R.string.connection_failed),
                    Toast.LENGTH_SHORT).show();
        }

        if (mListener != null) {
            mListener.onTaskFinished(resultCode);
        }
    }
}
