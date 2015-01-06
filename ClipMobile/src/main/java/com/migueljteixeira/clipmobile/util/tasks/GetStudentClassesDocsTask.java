package com.migueljteixeira.clipmobile.util.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.migueljteixeira.clipmobile.R;
import com.migueljteixeira.clipmobile.entities.Student;
import com.migueljteixeira.clipmobile.exceptions.ServerUnavailableException;
import com.migueljteixeira.clipmobile.settings.ClipSettings;
import com.migueljteixeira.clipmobile.util.StudentTools;

public class GetStudentClassesDocsTask extends AsyncTask<Integer, Void, Student> {

    private Integer groupPosition;

    public interface OnTaskFinishedListener {

        /**
         * Returns one of {@link com.migueljteixeira.clipmobile.enums.NetworkResult}.
         */
        public void onTaskFinished(Student result, int groupPosition);

    }

    private Context mContext;
    private OnTaskFinishedListener mListener;

    public GetStudentClassesDocsTask(Context context, OnTaskFinishedListener listener) {
        mContext = context;
        mListener = listener;
    }

    @Override
    protected Student doInBackground(Integer... params) {
        groupPosition = params[0];

        //String studentId = ClipSettings.getStudentIdSelected(mContext);
        String yearFormatted = ClipSettings.getYearSelectedFormatted(mContext);
        int semester = ClipSettings.getSemesterSelected(mContext);
        String studentNumberId = ClipSettings.getStudentNumberidSelected(mContext);
        String studentClassIdSelected = ClipSettings.getStudentClassIdSelected(mContext);
        String studentClassSelected = ClipSettings.getStudentClassSelected(mContext);
        String docType = mContext.getResources()
                .getStringArray(R.array.classes_docs_type_array)[groupPosition];

        /*System.out.println("DOINBACKGROUND -> studentID" + studentId + ", year:" + year
                + ", semester:" + semester
                + ", studentNumberID:" + studentNumberId);*/

        // Get student class docs
        try {
            return StudentTools.getStudentClassesDocs(mContext, studentClassIdSelected, yearFormatted,
                    semester, studentNumberId, studentClassSelected, docType);
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
            mListener.onTaskFinished(result, groupPosition);
        }
    }
}
