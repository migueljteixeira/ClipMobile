package com.migueljteixeira.clipmobile.ui;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.migueljteixeira.clipmobile.R;
import com.migueljteixeira.clipmobile.enums.NetworkResult;
import com.migueljteixeira.clipmobile.enums.Result;
import com.migueljteixeira.clipmobile.util.ConnectClipTask;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ConnectClipFragment extends Fragment implements ConnectClipTask.OnTaskFinishedListener{

    private ConnectClipTask mTask;
    @InjectView(R.id.progress_spinner) FrameLayout mProgressSpinner;
    @InjectView(R.id.login_form) FrameLayout mLoginForm;
    @InjectView(R.id.username) EditText mUsername;
    @InjectView(R.id.password) EditText mPassword;
    @InjectView(R.id.log_in_button) Button mLogInButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Retain this fragment across configuration changes.
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_login, container, false);
        ButterKnife.inject(this, view);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // unfinished task around?
        if (mTask != null && mTask.getStatus() != AsyncTask.Status.FINISHED)
            showProgressSpinner(true);

        // log in button
        mLogInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            View mFocusView = null;

            // get username and password
            Editable editableUsername = mUsername.getText();
            String username = editableUsername != null ?
                    editableUsername.toString().trim() : null;
            Editable editablePassword = mPassword.getText();
            String password = editablePassword != null ?
                    editablePassword.toString().trim() : null;

            // check if the username field isn't empty
            if (TextUtils.isEmpty(username)) {
                mUsername.setError(getString(R.string.error_fields_required));
                mFocusView = mUsername;
            }

            // check if we the password field isn't empty
            if (TextUtils.isEmpty(password)) {
                mPassword.setError(getString(R.string.error_fields_required));
                mFocusView = mPassword;
            }

            if(mFocusView != null) {
                // Focus the last form field with an error.
                mFocusView.requestFocus();
            }
            else {
                // show Progress Bar
                showProgressSpinner(true);

                // start AsyncTask
                mTask = new ConnectClipTask(getActivity().getApplicationContext(),
                        ConnectClipFragment.this);
                mTask.execute(username, password);
            }

            }
        });
    }

    /**
     * Shows the progress spinner and hides the login form.
     */
    private void showProgressSpinner(final boolean show) {
        mProgressSpinner.setVisibility(show ? View.VISIBLE : View.GONE);
        mLoginForm.setVisibility(show ? View.GONE : View.VISIBLE);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();

        ButterKnife.reset(this);
    }

    @Override
    public void onTaskFinished(int resultCode) {
        showProgressSpinner(false);

        System.out.println("-> " + resultCode);

        switch(resultCode) {
            case NetworkResult.OFFLINE :
                Toast.makeText(getActivity().getApplicationContext(),
                        getString(R.string.connection_failed), Toast.LENGTH_SHORT).show();
                break;

            case Result.ERROR :
                Toast.makeText(getActivity().getApplicationContext(),
                        getString(R.string.error_fields_incorrect), Toast.LENGTH_SHORT).show();
                break;

            case Result.SUCCESS :
                Toast.makeText(getActivity().getApplicationContext(),
                        "nice!", Toast.LENGTH_SHORT).show();
                break;
        }

        System.out.println("done!");
    }
}