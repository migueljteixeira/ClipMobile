package com.migueljteixeira.clipmobile.util.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.migueljteixeira.clipmobile.R;
import com.migueljteixeira.clipmobile.entities.Student;
import com.migueljteixeira.clipmobile.exceptions.ServerUnavailableException;
import com.migueljteixeira.clipmobile.settings.ClipSettings;
import com.migueljteixeira.clipmobile.util.StudentTools;

public class GetStudentClassesTask extends AsyncTask<Void, Void, Student> {


    public interface OnTaskFinishedListener {

        /**
         * Returns one of {@link com.migueljteixeira.clipmobile.enums.NetworkResult}.
         */
        public void onTaskFinished(Student result);

    }

    private Context mContext;
    private OnTaskFinishedListener mListener;

    public GetStudentClassesTask(Context context, OnTaskFinishedListener listener) {
        mContext = context;
        mListener = listener;
    }

    @Override
    protected Student doInBackground(Void... params) {
        String studentId = ClipSettings.getStudentIdSelected(mContext);
        String year = ClipSettings.getYearSelected(mContext);
        String yearFormatted = ClipSettings.getYearSelectedFormatted(mContext);
        String semester = ClipSettings.getSemesterSelected(mContext);
        String studentNumberId = ClipSettings.getStudentNumberidSelected(mContext);

        // Get student classes
        try {
            return StudentTools.getStudentClasses(mContext, studentId, year, yearFormatted, semester, studentNumberId);
        } catch (ServerUnavailableException e) {
            return null;
        }
    }

    @Override
    protected void onPostExecute(Student result) {
        super.onPostExecute(result);

        if(result == null) {
            // Server is unavailable right now
            Toast.makeText(mContext, mContext.getString(R.string.connection_failed),
                    Toast.LENGTH_SHORT).show();
        }

        if (mListener != null) {
            mListener.onTaskFinished(result);
        }
    }
}
