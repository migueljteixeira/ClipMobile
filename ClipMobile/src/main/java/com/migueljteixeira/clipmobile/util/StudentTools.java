package com.migueljteixeira.clipmobile.util;

import android.content.Context;

import com.migueljteixeira.clipmobile.entities.Student;
import com.migueljteixeira.clipmobile.entities.User;
import com.migueljteixeira.clipmobile.enums.Result;
import com.migueljteixeira.clipmobile.exceptions.ServerUnavailableException;
import com.migueljteixeira.clipmobile.network.StudentRequest;
import com.migueljteixeira.clipmobile.settings.ClipSettings;
import com.uwetrottmann.androidutils.AndroidUtils;

public class StudentTools {

    public static int signIn(Context mContext, String username, String password)
            throws ServerUnavailableException {

        // Sign in the user, and returns Students available
        User user = StudentRequest.signIn(mContext, username, password);

        // Invalid credentials
        if(! user.hasStudents())
            return Result.ERROR;

        long userId = DBUtils.getUserId(mContext, username);

        // If the user doesn't exist, create a new one
        if(userId == -1) {
            userId = DBUtils.createUser(mContext, username);

            // Insert Students
            DBUtils.insertStudentsNumbers(mContext, userId, user);
        }

        // User is now logged in
        ClipSettings.setLoggedInUser(mContext, userId, user.getName(), username, password);

        return Result.SUCCESS;
    }


    public static User getStudents(Context mContext, long userId) {

        return DBUtils.getStudents(mContext, userId);
    }

    public static Student getStudentsYears(Context mContext, String studentId, String studentNumberId)
            throws ServerUnavailableException {

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

    public static User updateStudentNumbersAndYears(Context mContext, long userId)
            throws ServerUnavailableException {

        System.out.println("request!");

        // Get (new) studentsNumbers from the server
        User user = StudentRequest.getStudentsNumbers(mContext);

        System.out.println("deleting!");

        // Delete studentsNumbers and studentsYears
        DBUtils.deleteStudentsNumbers(mContext, userId);

        System.out.println("inserting!");

        // Insert Students
        DBUtils.insertStudentsNumbers(mContext, userId, user);

        return user;
    }

}
