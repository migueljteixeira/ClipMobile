package com.migueljteixeira.clipmobile.provider;

import android.content.ContentValues;
import android.content.Context;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import com.migueljteixeira.clipmobile.provider.ClipMobileContract.UsersColumns;
import com.migueljteixeira.clipmobile.provider.ClipMobileContract.StudentsColumns;
import com.migueljteixeira.clipmobile.provider.ClipMobileContract.StudentsYearsColumns;
import com.migueljteixeira.clipmobile.provider.ClipMobileContract.StudentsSemestersColumns;
import com.migueljteixeira.clipmobile.provider.ClipMobileContract.StudentsScheduleDaysColumns;
import com.migueljteixeira.clipmobile.provider.ClipMobileContract.StudentsScheduleClassesColumns;
import com.migueljteixeira.clipmobile.provider.ClipMobileContract.StudentsCalendarsTypesColumns;
import com.migueljteixeira.clipmobile.provider.ClipMobileContract.StudentsCalendarsColumns;

public class ClipMobileDatabase extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "clip_database";

    public static final int DATABASE_VERSION = 1;

    private DatabaseUtils.InsertHelper mUsersInserter;
    private DatabaseUtils.InsertHelper mStudentsInserter;
    private DatabaseUtils.InsertHelper mStudentsYearsInserter;
    private DatabaseUtils.InsertHelper mStudentsSemestersInserter;
    private DatabaseUtils.InsertHelper mStudentsScheduleDaysInserter;
    private DatabaseUtils.InsertHelper mStudentsScheduleClassesInserter;
    private DatabaseUtils.InsertHelper mStudentsCalendarsTypesInserter;
    private DatabaseUtils.InsertHelper mStudentsCalendarsInserter;


    public interface Tables {

        String USERS = "users";

        String STUDENTS = "students";

        String STUDENTS_YEARS = "students_years";

        String STUDENTS_SEMESTERS = "students_semesters";

        String STUDENTS_SCHEDULE_DAYS = "students_schedules_days";

        String STUDENTS_SCHEDULE_CLASSES = "students_schedules_classes";

        String STUDENTS_CALENDARS_TYPES = "students_calendars_types";

        String STUDENTS_CALENDARS = "students_calendars";
    }

    interface References {

        String USER_ID = "REFERENCES " + Tables.USERS + "(" + BaseColumns._ID + ")";
        String STUDENT_ID = "REFERENCES " + Tables.STUDENTS + "(" + BaseColumns._ID + ")";
        String STUDENT_YEAR_ID = "REFERENCES " + Tables.STUDENTS_YEARS + "(" + BaseColumns._ID + ")";
        String STUDENT_SEMESTER_ID = "REFERENCES " + Tables.STUDENTS_SEMESTERS + "(" + BaseColumns._ID + ")";
        String STUDENT_SCHEDULE_DAY_ID = "REFERENCES " + Tables.STUDENTS_SCHEDULE_DAYS + "(" + BaseColumns._ID + ")";
        String STUDENT_CALENDAR_TYPE_ID = "REFERENCES " + Tables.STUDENTS_CALENDARS_TYPES + "(" + BaseColumns._ID + ")";
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

            + UsersColumns.REF_USER_ID + " TEXT " + References.USER_ID + ","

            + StudentsColumns.NUMBER_ID + " TEXT NOT NULL,"

            + StudentsColumns.NUMBER + " TEXT NOT NULL"

            + ");";

    private static final String CREATE_STUDENTS_YEARS_TABLE = "CREATE TABLE " + Tables.STUDENTS_YEARS
            + " ("

            + BaseColumns._ID + " INTEGER PRIMARY KEY,"

            + StudentsColumns.REF_STUDENT_ID + " TEXT " + References.STUDENT_ID + ","

            + StudentsYearsColumns.YEAR + " TEXT NOT NULL"

            + ");";

    private static final String CREATE_STUDENTS_SEMESTERS_TABLE = "CREATE TABLE " + Tables.STUDENTS_SEMESTERS
            + " ("

            + BaseColumns._ID + " INTEGER PRIMARY KEY,"

            + StudentsYearsColumns.REF_STUDENT_YEAR_ID + " TEXT " + References.STUDENT_YEAR_ID + ","

            + StudentsSemestersColumns.SEMESTER + " TEXT NOT NULL"

            + ");";

    private static final String CREATE_STUDENTS_SCHEDULE_DAYS_TABLE = "CREATE TABLE " + Tables.STUDENTS_SCHEDULE_DAYS
            + " ("

            + BaseColumns._ID + " INTEGER PRIMARY KEY,"

            + StudentsSemestersColumns.REF_STUDENT_SEMESTER_ID + " TEXT " + References.STUDENT_SEMESTER_ID + ","

            + StudentsScheduleDaysColumns.DAY + " TEXT NOT NULL"

            + ");";

    private static final String CREATE_STUDENTS_SCHEDULE_CLASSES_TABLE = "CREATE TABLE " + Tables.STUDENTS_SCHEDULE_CLASSES
            + " ("

            + BaseColumns._ID + " INTEGER PRIMARY KEY,"

            + StudentsScheduleDaysColumns.REF_STUDENT_SCHEDULE_DAY_ID + " TEXT " + References.STUDENT_SCHEDULE_DAY_ID + ","

            + StudentsScheduleClassesColumns.NAME + " TEXT NOT NULL,"

            + StudentsScheduleClassesColumns.NAME_ABBREVIATION + " TEXT NOT NULL,"

            + StudentsScheduleClassesColumns.TYPE + " TEXT NOT NULL,"

            + StudentsScheduleClassesColumns.HOUR_START + " TEXT NOT NULL,"

            + StudentsScheduleClassesColumns.HOUR_END + " TEXT NOT NULL,"

            + StudentsScheduleClassesColumns.ROOM + " TEXT"

            + ");";

    private static final String CREATE_STUDENTS_CALENDARS_TYPES_TABLE = "CREATE TABLE " + Tables.STUDENTS_CALENDARS_TYPES
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

            + ");";








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
        return mStudentsYearsInserter.insert(values);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USERS_TABLE);

        db.execSQL(CREATE_STUDENTS_TABLE);

        db.execSQL(CREATE_STUDENTS_YEARS_TABLE);

        db.execSQL(CREATE_STUDENTS_SEMESTERS_TABLE);

        db.execSQL(CREATE_STUDENTS_SCHEDULE_DAYS_TABLE);

        db.execSQL(CREATE_STUDENTS_SCHEDULE_CLASSES_TABLE);

        db.execSQL(CREATE_STUDENTS_CALENDARS_TYPES_TABLE);

        db.execSQL(CREATE_STUDENTS_CALENDARS_TABLE);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);

        mUsersInserter = new DatabaseUtils.InsertHelper(db, Tables.USERS);
        mStudentsInserter = new DatabaseUtils.InsertHelper(db, Tables.STUDENTS);
        mStudentsYearsInserter = new DatabaseUtils.InsertHelper(db, Tables.STUDENTS_YEARS);
        mStudentsSemestersInserter = new DatabaseUtils.InsertHelper(db, Tables.STUDENTS_SEMESTERS);
        mStudentsScheduleDaysInserter = new DatabaseUtils.InsertHelper(db, Tables.STUDENTS_SCHEDULE_DAYS);
        mStudentsScheduleClassesInserter = new DatabaseUtils.InsertHelper(db, Tables.STUDENTS_SCHEDULE_CLASSES);
        mStudentsCalendarsTypesInserter = new DatabaseUtils.InsertHelper(db, Tables.STUDENTS_CALENDARS_TYPES);
        mStudentsCalendarsInserter = new DatabaseUtils.InsertHelper(db, Tables.STUDENTS_CALENDARS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + Tables.USERS);
        db.execSQL("DROP TABLE IF EXISTS " + Tables.STUDENTS);
        db.execSQL("DROP TABLE IF EXISTS " + Tables.STUDENTS_YEARS);
        db.execSQL("DROP TABLE IF EXISTS " + Tables.STUDENTS_SEMESTERS);
        db.execSQL("DROP TABLE IF EXISTS " + Tables.STUDENTS_SCHEDULE_DAYS);
        db.execSQL("DROP TABLE IF EXISTS " + Tables.STUDENTS_SCHEDULE_CLASSES);
        db.execSQL("DROP TABLE IF EXISTS " + Tables.STUDENTS_CALENDARS_TYPES);
        db.execSQL("DROP TABLE IF EXISTS " + Tables.STUDENTS_CALENDARS);

        onCreate(db);
    }
}
