package com.migueljteixeira.clipmobile.util;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.migueljteixeira.clipmobile.entities.Student;
import com.migueljteixeira.clipmobile.provider.ClipMobileContract;

import java.util.LinkedList;
import java.util.List;

public class DBUtils {

    /**
     * ////////////////////////////// USERS //////////////////////////////
     */

    public static boolean userExists(Context mContext, String username) {

        final Cursor user = mContext.getContentResolver().query(
                ClipMobileContract.Users.CONTENT_URI,
                new String[] { ClipMobileContract.Users.USERNAME },
                ClipMobileContract.Users.USERNAME + "=?", new String[] { username }, null);

        return user.getCount() > 0;
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

    public static List<Student> getStudentsNumbers(Context mContext, String user_id) {

        final Cursor students_cursor = mContext.getContentResolver().query(
                ClipMobileContract.Students.CONTENT_URI, null,
                ClipMobileContract.Users.REF_USER_ID + "=?", new String[] { user_id }, null);

        List<Student> students = new LinkedList<Student>();
        while(students_cursor.moveToNext()) {
            String id = students_cursor.getString(0);
            String number_id = students_cursor.getString(2);
            String number = students_cursor.getString(3);

            Student student = new Student();
            student.setId(id);
            student.setNumberID(number_id);
            student.setNumber(number);

            students.add(student);
        }

        return students;
    }

    public static void insertStudentsNumbers(Context mContext, long userId, List<Student> students) {

        for(Student student : students) {
            ContentValues values = new ContentValues();
            values.put(ClipMobileContract.Users.REF_USER_ID, userId);
            values.put(ClipMobileContract.Students.NUMBER_ID, student.getNumberID());
            values.put(ClipMobileContract.Students.NUMBER, student.getNumber());

            Uri uri = mContext.getContentResolver().insert(ClipMobileContract.Students.CONTENT_URI, values);
            System.out.println("student inserted! " + uri.getPath());
        }

    }
}
