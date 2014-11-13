package com.migueljteixeira.clipmobile.provider;

import android.content.ContentValues;
import android.content.Context;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import com.migueljteixeira.clipmobile.provider.ClipMobileContract.ScheduleClassesColumns;
import com.migueljteixeira.clipmobile.provider.ClipMobileContract.ScheduleDaysColumns;
import com.migueljteixeira.clipmobile.provider.ClipMobileContract.StudentClassesColumns;
import com.migueljteixeira.clipmobile.provider.ClipMobileContract.StudentsColumns;
import com.migueljteixeira.clipmobile.provider.ClipMobileContract.StudentsYearSemesterColumns;
import com.migueljteixeira.clipmobile.provider.ClipMobileContract.UsersColumns;

public class ClipMobileDatabase extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "clip_mobile_database";

    public static final int DATABASE_VERSION = 1;

    private DatabaseUtils.InsertHelper mUsersInserter;
    private DatabaseUtils.InsertHelper mStudentsInserter;
    private DatabaseUtils.InsertHelper mStudentsYearSemesterInserter;
    private DatabaseUtils.InsertHelper mScheduleDaysInserter;
    private DatabaseUtils.InsertHelper mScheduleClassesInserter;
    private DatabaseUtils.InsertHelper mStudentClassesInserter;

    //private DatabaseUtils.InsertHelper mStudentsCalendarsTypesInserter;
    //private DatabaseUtils.InsertHelper mStudentsCalendarsInserter;


    public interface Tables {

        String USERS = "users";

        String STUDENTS = "students";

        String STUDENTS_YEAR_SEMESTER = "students_year_semester";

        String SCHEDULE_DAYS = "schedule_days";

        String SCHEDULE_CLASSES = "schedule_classes";

        String STUDENT_CLASSES = "student_classes";

        /*String STUDENTS_CALENDARS_TYPES = "students_calendars_types";

        String STUDENTS_CALENDARS = "students_calendars";*/
    }

    interface References {

        String USER_ID = "REFERENCES " + Tables.USERS + "(" + BaseColumns._ID + ")";
        String STUDENT_ID = "REFERENCES " + Tables.STUDENTS + "(" + BaseColumns._ID + ")";
        String STUDENTS_YEAR_SEMESTER_ID = "REFERENCES " + Tables.STUDENTS_YEAR_SEMESTER + "(" + BaseColumns._ID + ")";

        String STUDENT_SCHEDULE_DAY_ID = "REFERENCES " + Tables.SCHEDULE_DAYS + "(" + BaseColumns._ID + ")";
        //String STUDENT_CALENDAR_TYPE_ID = "REFERENCES " + Tables.STUDENTS_CALENDARS_TYPES + "(" + BaseColumns._ID + ")";*/
    }

    private static final String CREATE_USERS_TABLE = "CREATE TABLE " + Tables.USERS
            + " ("

            + BaseColumns._ID + " INTEGER PRIMARY KEY,"

            + UsersColumns.USERNAME + " TEXT NOT NULL,"

            + "UNIQUE (" + UsersColumns.USERNAME + ")"

            + ");";

    private static final String CREATE_STUDENTS_TABLE = "CREATE TABLE " + Tables.STUDENTS
            + " ("

            + BaseColumns._ID + " INTEGER PRIMARY KEY,"

            + UsersColumns.REF_USERS_ID + " TEXT " + References.USER_ID + " ON DELETE CASCADE,"

            + StudentsColumns.NUMBER_ID + " TEXT NOT NULL,"

            + StudentsColumns.NUMBER + " TEXT NOT NULL"

            + ");";

    private static final String CREATE_STUDENTS_YEAR_SEMESTER_TABLE = "CREATE TABLE " + Tables.STUDENTS_YEAR_SEMESTER
            + " ("

            + BaseColumns._ID + " INTEGER PRIMARY KEY,"

            + StudentsColumns.REF_STUDENTS_ID + " TEXT " + References.STUDENT_ID + " ON DELETE CASCADE,"

            + StudentsYearSemesterColumns.YEAR + " TEXT NOT NULL,"

            + StudentsYearSemesterColumns.SEMESTER + " TEXT NOT NULL"

            + ");";

    private static final String CREATE_SCHEDULE_DAYS_TABLE = "CREATE TABLE " + Tables.SCHEDULE_DAYS
            + " ("

            + BaseColumns._ID + " INTEGER PRIMARY KEY,"

            + StudentsYearSemesterColumns.REF_STUDENTS_YEAR_SEMESTER_ID + " TEXT " +
                References.STUDENTS_YEAR_SEMESTER_ID + " ON DELETE CASCADE,"

            + ScheduleDaysColumns.DAY + " TEXT NOT NULL"

            + ");";

    private static final String CREATE_SCHEDULE_CLASSES_TABLE = "CREATE TABLE " + Tables.SCHEDULE_CLASSES
            + " ("

            + BaseColumns._ID + " INTEGER PRIMARY KEY,"

            + ScheduleDaysColumns.REF_SCHEDULE_DAYS_ID + " TEXT " +
                References.STUDENT_SCHEDULE_DAY_ID + " ON DELETE CASCADE,"

            + ScheduleClassesColumns.NAME + " TEXT NOT NULL,"

            + ScheduleClassesColumns.NAME_ABBREVIATION + " TEXT NOT NULL,"

            + ScheduleClassesColumns.TYPE + " TEXT NOT NULL,"

            + ScheduleClassesColumns.HOUR_START + " TEXT NOT NULL,"

            + ScheduleClassesColumns.HOUR_END + " TEXT NOT NULL,"

            + ScheduleClassesColumns.ROOM + " TEXT"

            + ");";

    private static final String CREATE_STUDENT_CLASSES_TABLE = "CREATE TABLE " + Tables.STUDENT_CLASSES
            + " ("

            + BaseColumns._ID + " INTEGER PRIMARY KEY,"

            + StudentsYearSemesterColumns.REF_STUDENTS_YEAR_SEMESTER_ID + " TEXT " +
                References.STUDENTS_YEAR_SEMESTER_ID + " ON DELETE CASCADE,"

            + StudentClassesColumns.NAME + " TEXT NOT NULL,"

            + StudentClassesColumns.NUMBER + " TEXT NOT NULL,"

            + StudentClassesColumns.SEMESTER + " TEXT NOT NULL"

            + ");";

    /*private static final String CREATE_STUDENTS_CALENDARS_TYPES_TABLE = "CREATE TABLE " + Tables.STUDENTS_CALENDARS_TYPES
            + " ("

            + BaseColumns._ID + " INTEGER PRIMARY KEY,"

            + StudentsSemestersColumns.REF_STUDENT_SEMESTER_ID + " TEXT " + References.STUDENT_SEMESTER_ID + ","

            + StudentsCalendarsTypesColumns.NAME + " TEXT NOT NULL"

            + ");";

    private static final String CREATE_STUDENTS_CALENDARS_TABLE = "CREATE TABLE " + Tables.STUDENTS_CALENDARS
            + " ("

            + BaseColumns._ID + " INTEGER PRIMARY KEY,"

            + StudentsCalendarsTypesColumns.REF_STUDENT_CALENDAR_TYPE_ID + " TEXT " + References.STUDENT_CALENDAR_TYPE_ID + ","

            + StudentsCalendarsColumns.NAME + " TEXT NOT NULL,"

            + StudentsCalendarsColumns.DATE + " TEXT NOT NULL,"

            + StudentsCalendarsColumns.HOUR + " TEXT NOT NULL,"

            + StudentsCalendarsColumns.ROOMS + " TEXT,"

            + StudentsCalendarsColumns.NUMBER + " TEXT NOT NULL"

            + ");";*/








    public ClipMobileDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public long insertUsers(ContentValues values) {
        return mUsersInserter.insert(values);
    }

    public long insertStudents(ContentValues values) {
        return mStudentsInserter.insert(values);
    }

    public long insertStudentYears(ContentValues values) {
        return mStudentsYearSemesterInserter.insert(values);
    }

    public long insertScheduleDays(ContentValues values) {
        return mScheduleDaysInserter.insert(values);
    }

    public long insertScheduleClasses(ContentValues values) {
        return mScheduleClassesInserter.insert(values);
    }

    public long insertStudentClasses(ContentValues values) {
        return mStudentClassesInserter.insert(values);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USERS_TABLE);

        db.execSQL(CREATE_STUDENTS_TABLE);

        db.execSQL(CREATE_STUDENTS_YEAR_SEMESTER_TABLE);

        db.execSQL(CREATE_SCHEDULE_DAYS_TABLE);

        db.execSQL(CREATE_SCHEDULE_CLASSES_TABLE);

        db.execSQL(CREATE_STUDENT_CLASSES_TABLE);

        /*db.execSQL(CREATE_STUDENTS_SCHEDULE_CLASSES_TABLE);

        db.execSQL(CREATE_STUDENTS_CALENDARS_TYPES_TABLE);

        db.execSQL(CREATE_STUDENTS_CALENDARS_TABLE);*/
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);

        if (!db.isReadOnly()) {
            // Enable foreign key constraints
            db.execSQL("PRAGMA foreign_keys=ON;");
        }

        mUsersInserter = new DatabaseUtils.InsertHelper(db, Tables.USERS);
        mStudentsInserter = new DatabaseUtils.InsertHelper(db, Tables.STUDENTS);
        mStudentsYearSemesterInserter = new DatabaseUtils.InsertHelper(db, Tables.STUDENTS_YEAR_SEMESTER);
        mScheduleDaysInserter = new DatabaseUtils.InsertHelper(db, Tables.SCHEDULE_DAYS);
        mScheduleClassesInserter = new DatabaseUtils.InsertHelper(db, Tables.SCHEDULE_CLASSES);
        mStudentClassesInserter = new DatabaseUtils.InsertHelper(db, Tables.STUDENT_CLASSES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + Tables.USERS);
        db.execSQL("DROP TABLE IF EXISTS " + Tables.STUDENTS);
        db.execSQL("DROP TABLE IF EXISTS " + Tables.STUDENTS_YEAR_SEMESTER);
        db.execSQL("DROP TABLE IF EXISTS " + Tables.SCHEDULE_DAYS);
        db.execSQL("DROP TABLE IF EXISTS " + Tables.SCHEDULE_CLASSES);
        db.execSQL("DROP TABLE IF EXISTS " + Tables.STUDENT_CLASSES);

        onCreate(db);
    }
}
