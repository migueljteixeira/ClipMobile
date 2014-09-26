package com.migueljteixeira.clipmobile.util;

import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;

import com.migueljteixeira.clipmobile.enums.NetworkResult;
import com.migueljteixeira.clipmobile.enums.Result;
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

        // get user data
        String username = params[0];
        String password = params[1];

        return StudentTools.signIn(mContext, username, password);
    }

    @Override
    protected void onPostExecute(Integer resultCode) {
        super.onPostExecute(resultCode);

        if (mListener != null) {
            mListener.onTaskFinished(resultCode);
        }
    }
}
