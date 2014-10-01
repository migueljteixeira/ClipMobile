package com.migueljteixeira.clipmobile.util;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.migueljteixeira.clipmobile.entities.Student;
import com.migueljteixeira.clipmobile.entities.StudentYear;
import com.migueljteixeira.clipmobile.entities.User;
import com.migueljteixeira.clipmobile.provider.ClipMobileContract;

public class DBUtils {

    /**
     * ////////////////////////////// USERS //////////////////////////////
     */

    public static long getUserId(Context mContext, String username) {

        final Cursor user_cursor = mContext.getContentResolver().query(
                ClipMobileContract.Users.CONTENT_URI,
                new String[] { ClipMobileContract.Users._ID },
                ClipMobileContract.Users.USERNAME + "=?", new String[] { username }, null);

        if(user_cursor.getCount() == 0) {
            user_cursor.close();

            return -1;
        }

        user_cursor.moveToFirst();
        long userId = user_cursor.getInt(0);
        user_cursor.close();

        return userId;
    }

    public static long createUser(Context mContext, String username) {
        ContentValues values = new ContentValues();
        values.put(ClipMobileContract.Users.USERNAME, username);

        Uri uri = mContext.getContentResolver().insert(ClipMobileContract.Users.CONTENT_URI, values);
        System.out.println("user inserted! " + uri.getPath());

        return ContentUris.parseId(uri);
    }

    /**
     * ////////////////////////////// STUDENTS //////////////////////////////
     */

    public static User getStudents(Context mContext, long userId) {

        final Cursor students_cursor = mContext.getContentResolver().query(
                ClipMobileContract.Students.CONTENT_URI, null,
                ClipMobileContract.Users.REF_USER_ID + "=?", new String[] { String.valueOf(userId) }, null);

        User user = new User();
        while(students_cursor.moveToNext()) {
                String id = students_cursor.getString(0);
                String number_id = students_cursor.getString(2);
                String number = students_cursor.getString(3);

            Student student = new Student();
            student.setId(id);
            student.setNumberId(number_id);
            student.setNumber(number);

            user.addStudent(student);
        }
        students_cursor.close();

        return user;
    }

    public static void insertStudentsNumbers(Context mContext, long userId, User user) {

        for(Student student : user.getStudents()) {
            ContentValues values = new ContentValues();
            values.put(ClipMobileContract.Users.REF_USER_ID, userId);
            values.put(ClipMobileContract.Students.NUMBER_ID, student.getNumberId());
            values.put(ClipMobileContract.Students.NUMBER, student.getNumber());

            Uri uri = mContext.getContentResolver().insert(ClipMobileContract.Students.CONTENT_URI, values);
            System.out.println("student inserted! " + uri.getPath());

            String newId = String.valueOf( ContentUris.parseId(uri) );
            student.setId(newId);
        }

    }

    /**
     * ////////////////////////////// STUDENTS YEARS //////////////////////////////
     */

    public static Student getStudentYears(Context mContext, String student_id) {

        final Cursor studentYears_cursor = mContext.getContentResolver().query(
                ClipMobileContract.StudentsYears.CONTENT_URI, null,
                ClipMobileContract.Students.REF_STUDENT_ID + "=?", new String[] { student_id }, null);

        Student student = new Student();
        while(studentYears_cursor.moveToNext()) {
            String id = studentYears_cursor.getString(0);
            String year = studentYears_cursor.getString(2);

            StudentYear student_year = new StudentYear();
            student_year.setId(id);
            student_year.setYear(year);

            student.addYear(student_year);
        }
        studentYears_cursor.close();

        return student;
    }

    public static void insertStudentYears(Context mContext, String studentId, Student student) {

        for(StudentYear year : student.getYears()) {
            ContentValues values = new ContentValues();
            values.put(ClipMobileContract.Students.REF_STUDENT_ID, studentId);
            values.put(ClipMobileContract.StudentsYears.YEAR, year.getYear());

            Uri uri = mContext.getContentResolver().insert(ClipMobileContract.StudentsYears.CONTENT_URI, values);
            System.out.println("student year inserted! " + uri.getPath());

            String newId = String.valueOf( ContentUris.parseId(uri) );
            year.setId(newId);
        }

    }

    /**
     * ////////////////////////////// UPDATE STUDENT INFO //////////////////////////////
     */

    public static void deleteStudentsNumbers(Context mContext, long userId) {

        // Delete Student Numbers
        mContext.getContentResolver().delete(ClipMobileContract.Students.CONTENT_URI,
                ClipMobileContract.Users.REF_USER_ID + "=?",
                new String[] { String.valueOf(userId) });
    }

}
