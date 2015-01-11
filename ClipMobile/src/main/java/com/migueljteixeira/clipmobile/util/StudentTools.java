package com.migueljteixeira.clipmobile.util;

import android.content.Context;

import com.migueljteixeira.clipmobile.entities.Student;
import com.migueljteixeira.clipmobile.entities.User;
import com.migueljteixeira.clipmobile.enums.NetworkResult;
import com.migueljteixeira.clipmobile.enums.Result;
import com.migueljteixeira.clipmobile.exceptions.ServerUnavailableException;
import com.migueljteixeira.clipmobile.network.StudentCalendarRequest;
import com.migueljteixeira.clipmobile.network.StudentClassesDocsRequest;
import com.migueljteixeira.clipmobile.network.StudentClassesRequest;
import com.migueljteixeira.clipmobile.network.StudentRequest;
import com.migueljteixeira.clipmobile.network.StudentScheduleRequest;
import com.migueljteixeira.clipmobile.settings.ClipSettings;
import com.uwetrottmann.androidutils.AndroidUtils;

public class StudentTools {

    public static int signIn(Context mContext, String username, String password)
            throws ServerUnavailableException {

        // Check for connectivity
        if (! AndroidUtils.isNetworkConnected(mContext))
            return NetworkResult.OFFLINE;

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
        ClipSettings.setLoggedInUser(mContext, userId, username, password);

        return Result.SUCCESS;
    }

    /*
     * ////////////////////////////// STUDENT, STUDENTYEARS, STUDENTNUMBERS  //////////////////////////////
     */

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

    public static Student updateStudentPage(Context mContext, String studentId, String studentNumberId,
                                            String studentYearSemesterId)
            throws ServerUnavailableException {

        System.out.println("request!");

        // Get (new) student info from the server
        Student student = StudentRequest.getStudentsYears(mContext, studentNumberId);

        System.out.println("deleting!");

        // Delete students info
        DBUtils.deleteStudentsInfo(mContext, studentYearSemesterId);

        System.out.println("inserting!");

        // Insert students info
        DBUtils.insertStudentYears(mContext, studentId, student);

        return student;
    }

    /*
     * ////////////////////////////// STUDENT SCHEDULE  //////////////////////////////
     */


    public static Student getStudentSchedule(Context mContext, String studentId, String year, String yearFormatted,
                                             int semester, String studentNumberId)
            throws ServerUnavailableException {

        // First, we get the yearSemesterId
        String yearSemesterId = DBUtils.getYearSemesterId(mContext, studentId, year, semester);

        Student student = DBUtils.getStudentSchedule(mContext, yearSemesterId);

        System.out.println("has " + (student != null));

        if(student != null)
            return student;

        System.out.println("net " + !AndroidUtils.isNetworkConnected(mContext));

        // Check for connectivity
        if (! AndroidUtils.isNetworkConnected(mContext))
            return null;

        // Get student schedule from the server
        student = StudentScheduleRequest.getSchedule(mContext, studentNumberId, yearFormatted, semester);

        System.out.println("schedule request done!");

        // Insert schedule on database
        DBUtils.insertStudentSchedule(mContext, yearSemesterId, student);

        System.out.println("schedule inserted!");

        return student;
    }

    /*
     * ////////////////////////////// STUDENT CLASSES  //////////////////////////////
     */


    public static Student getStudentClasses(Context mContext, String studentId, String year, String yearFormatted,
                                             int semester, String studentNumberId)
            throws ServerUnavailableException {

        // First, we get the yearSemesterId
        String yearSemesterId = DBUtils.getYearSemesterId(mContext, studentId, year, semester);

        Student student = DBUtils.getStudentClasses(mContext, yearSemesterId);

        System.out.println("has " + (student != null));

        if(student != null)
            return student;

        System.out.println("net " + !AndroidUtils.isNetworkConnected(mContext));

        // Check for connectivity
        if (! AndroidUtils.isNetworkConnected(mContext))
            return null;

        // Get student classes from the server
        student = StudentClassesRequest.getClasses(mContext, studentNumberId, yearFormatted);

        System.out.println("classes request done!");

        // Insert classes on database
        DBUtils.insertStudentClasses(mContext, yearSemesterId, student);

        System.out.println("classes inserted!");

        return student;
    }

    public static Student getStudentClassesDocs(Context mContext, String studentClassId, String yearFormatted,
                                            int semester, String studentNumberId, String studentClassSelected,
                                            String docType)
            throws ServerUnavailableException {

        Student student = DBUtils.getStudentClassesDocs(mContext, studentClassId, docType);

        System.out.println("has " + (student != null));

        if(student != null)
            return student;

        System.out.println("net " + !AndroidUtils.isNetworkConnected(mContext));

        // Check for connectivity
        if (! AndroidUtils.isNetworkConnected(mContext))
            return null;

        // Get student classes docs from the server
        student = StudentClassesDocsRequest.getClassesDocs(mContext, studentNumberId,
                yearFormatted, semester, studentClassSelected, docType);

        System.out.println("classes docs request done!");

        // Insert classes docs on database
        DBUtils.insertStudentClassesDocs(mContext, studentClassId, student);

        System.out.println("classes docs inserted!");

        return student;
    }

    /*
     * ////////////////////////////// STUDENT CALENDAR  //////////////////////////////
     */

    public static Student getStudentCalendar(Context mContext, String studentId, String year, String yearFormatted,
                                             int semester, String studentNumberId)
            throws ServerUnavailableException {

        // First, we get the yearSemesterId
        String yearSemesterId = DBUtils.getYearSemesterId(mContext, studentId, year, semester);

        Student student = DBUtils.getStudentCalendar(mContext, yearSemesterId);

        System.out.println("has " + (student != null));

        if(student != null)
            return student;

        System.out.println("net " + !AndroidUtils.isNetworkConnected(mContext));

        // Check for connectivity
        if (! AndroidUtils.isNetworkConnected(mContext))
            return null;

        // ---- EXAM CALENDAR ----

        // Get student exam calendar from the server
        student = new Student();
        StudentCalendarRequest.getExamCalendar(mContext, student, studentNumberId, yearFormatted, semester);

        // ---- TEST CALENDAR ----

        // Get student test calendar from the server
        StudentCalendarRequest.getTestCalendar(mContext, student, studentNumberId, yearFormatted, semester);

        System.out.println("calendar request done!");

        if(student == null || student.getStudentCalendar() == null)
            System.out.println("WHAT");

        // Insert calendar on database
        DBUtils.insertStudentCalendar(mContext, yearSemesterId, student);

        System.out.println("calendar inserted!");

        return student;
    }

}
