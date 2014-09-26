package com.migueljteixeira.clipmobile.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.migueljteixeira.clipmobile.entities.Student;
import com.migueljteixeira.clipmobile.enums.Result;
import com.migueljteixeira.clipmobile.network.StudentRequest;
import com.migueljteixeira.clipmobile.provider.ClipMobileContract;
import com.migueljteixeira.clipmobile.provider.ClipMobileContract.Users;
import com.migueljteixeira.clipmobile.settings.ClipSettings;

import java.util.List;

public class StudentTools {

    public static int signIn(Context mContext, String username, String password) {

        // Sign in the user, and returns Students available
        List<Student> students = StudentRequest.signIn(mContext, username, password);

        // Invalid credentials
        if(students == null)
            return Result.ERROR;

        // Create new User
        if(! DBUtils.userExists(mContext, username)) {
            long newUserId = DBUtils.createUser(mContext, username);

            // User is now logged in
            ClipSettings.setLoggedInUserId(mContext, newUserId);

            // Insert Students
            DBUtils.insertStudents(mContext, newUserId, students);
        }

        return Result.SUCCESS;
    }


    public void getStudentNumbers() {

        //query database

        // if not in database
        //loadFromClip(...);

        //Student student = new Student();
    }

}
