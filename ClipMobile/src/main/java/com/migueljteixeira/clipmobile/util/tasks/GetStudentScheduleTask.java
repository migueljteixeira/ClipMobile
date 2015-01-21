package com.migueljteixeira.clipmobile.util.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.migueljteixeira.clipmobile.R;
import com.migueljteixeira.clipmobile.entities.Student;
import com.migueljteixeira.clipmobile.exceptions.ServerUnavailableException;
import com.migueljteixeira.clipmobile.settings.ClipSettings;
import com.migueljteixeira.clipmobile.util.StudentTools;

public class GetStudentScheduleTask extends AsyncTask<Void, Void, Student> {


    public interface OnTaskFinishedListener {

        /**
         * Returns a {@link com.migueljteixeira.clipmobile.entities.Student}.
         */
        public void onTaskFinished(Student result);

    }

    private Context mContext;
    private OnTaskFinishedListener mListener;

    public GetStudentScheduleTask(Context context, OnTaskFinishedListener listener) {
        mContext = context;
        mListener = listener;
    }

    @Override
    protected Student doInBackground(Void... params) {
        String studentId = ClipSettings.getStudentIdSelected(mContext);
        String year = ClipSettings.getYearSelected(mContext);
        String yearFormatted = ClipSettings.getYearSelectedFormatted(mContext);
        int semester = ClipSettings.getSemesterSelected(mContext);

        //String yearSemesterId = ClipSettings.getStudentYearSemesterIdSelected(mContext);
        String studentNumberId = ClipSettings.getStudentNumberidSelected(mContext);

        // Get student schedule
        try {
            return StudentTools.getStudentSchedule(mContext, studentId, year, yearFormatted, semester,
                    studentNumberId);
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

        if (mListener != null)
            mListener.onTaskFinished(result);
    }
}
