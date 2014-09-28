package com.migueljteixeira.clipmobile.util;

import android.content.Context;

import com.migueljteixeira.clipmobile.entities.Student;
import com.migueljteixeira.clipmobile.entities.User;
import com.migueljteixeira.clipmobile.enums.Result;
import com.migueljteixeira.clipmobile.network.StudentRequest;
import com.migueljteixeira.clipmobile.settings.ClipSettings;
import com.uwetrottmann.androidutils.AndroidUtils;

public class StudentTools {

    public static int signIn(Context mContext, String username, String password) {

        // Sign in the user, and returns Students available
        User user = StudentRequest.signIn(mContext, username, password);

        // Invalid credentials
        if(! user.hasStudents())
            return Result.ERROR;

        // Create new User
        if(! DBUtils.userExists(mContext, username)) {
            long newUserId = DBUtils.createUser(mContext, username);

            // User is now logged in
            ClipSettings.setLoggedInUserId(mContext, newUserId);

            // Insert Students
            DBUtils.insertStudentsNumbers(mContext, newUserId, user);
        }

        return Result.SUCCESS;
    }


    public static User getStudents(Context mContext, String userId) {

        return DBUtils.getStudents(mContext, userId);
    }

    public static Student getStudentsYears(Context mContext, String studentId, String studentNumberId) {

        Student student = DBUtils.getStudentYears(mContext, studentId);

        System.out.println("has " + student.hasStudentYears());

        if(student.hasStudentYears())
            return student;

        System.out.println("net " + !AndroidUtils.isNetworkConnected(mContext));

        // Check for connectivity
        if (! AndroidUtils.isNetworkConnected(mContext))
            return null;

        // Get student years from the server
        student = StudentRequest.getStudentsYears(mContext, studentNumberId);

        // Insert Students
        DBUtils.insertStudentYears(mContext, studentId, student);

        return student;
    }
}
