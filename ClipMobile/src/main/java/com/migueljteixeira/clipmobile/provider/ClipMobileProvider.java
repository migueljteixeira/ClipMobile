package com.migueljteixeira.clipmobile.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.migueljteixeira.clipmobile.util.SelectionBuilder;
import com.migueljteixeira.clipmobile.provider.ClipMobileDatabase.Tables;
import com.migueljteixeira.clipmobile.provider.ClipMobileContract.Users;
import com.migueljteixeira.clipmobile.provider.ClipMobileContract.Students;
import com.migueljteixeira.clipmobile.provider.ClipMobileContract.StudentsYears;
import com.migueljteixeira.clipmobile.provider.ClipMobileContract.StudentsSemesters;
import com.migueljteixeira.clipmobile.provider.ClipMobileContract.StudentsScheduleDays;
import com.migueljteixeira.clipmobile.provider.ClipMobileContract.StudentsScheduleClasses;
import com.migueljteixeira.clipmobile.provider.ClipMobileContract.StudentsCalendarsTypes;
import com.migueljteixeira.clipmobile.provider.ClipMobileContract.StudentsCalendars;

public class ClipMobileProvider extends ContentProvider {

    private static UriMatcher sUriMatcher;

    private static final int USERS = 100;

    private static final int STUDENTS = 200;

    private static final int STUDENTS_YEARS = 300;

    private static final int STUDENTS_SEMESTERS = 400;

    private static final int STUDENTS_SCHEDULE_DAYS = 500;

    private static final int STUDENTS_SCHEDULE_CLASSES = 600;

    private static final int STUDENTS_CALENDARS_TYPES = 700;

    private static final int STUDENTS_CALENDARS = 800;


    /**
     * Build and return a {@link UriMatcher} that catches all {@link Uri} variations supported by
     * this {@link ContentProvider}.
     */
    private static UriMatcher buildUriMatcher(Context context) {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = context.getPackageName() + ".provider";

        // Users
        matcher.addURI(authority, ClipMobileContract.PATH_USERS, USERS);

        // Students
        matcher.addURI(authority, ClipMobileContract.PATH_STUDENTS, STUDENTS);

        // Students Years
        matcher.addURI(authority, ClipMobileContract.PATH_STUDENTS_YEARS, STUDENTS_YEARS);

        // Students Semesters
        matcher.addURI(authority, ClipMobileContract.PATH_STUDENTS_SEMESTERS, STUDENTS_SEMESTERS);

        // Students Schedule Days
        matcher.addURI(authority, ClipMobileContract.PATH_STUDENTS_SCHEDULE_DAYS, STUDENTS_SCHEDULE_DAYS);

        // Students Schedule Classes
        matcher.addURI(authority, ClipMobileContract.PATH_STUDENTS_SCHEDULE_CLASSES, STUDENTS_SCHEDULE_CLASSES);

        // Students Calendars Types
        matcher.addURI(authority, ClipMobileContract.PATH_STUDENTS_CALENDARS_TYPES, STUDENTS_CALENDARS_TYPES);

        // Students Calendars
        matcher.addURI(authority, ClipMobileContract.PATH_STUDENTS_CALENDARS, STUDENTS_CALENDARS);

        return matcher;
    }

    private final ThreadLocal<Boolean> mApplyingBatch = new ThreadLocal<Boolean>();

    private ClipMobileDatabase mDbHelper;

    protected SQLiteDatabase mDb;

    @Override
    public boolean onCreate() {
        Context context = getContext();

        sUriMatcher = buildUriMatcher(context);

        mDbHelper = new ClipMobileDatabase(context);

        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
            String sortOrder) {

        final SQLiteDatabase db = mDbHelper.getReadableDatabase();
        final int match = sUriMatcher.match(uri);

        final SelectionBuilder builder = buildSelection(uri, match);

        Cursor query = builder
                .where(selection, selectionArgs)
                .query(db, projection, sortOrder);
        if (query != null) {
            query.setNotificationUri(getContext().getContentResolver(), uri);
        }
        return query;
    }

    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case USERS :
                return Users.CONTENT_TYPE;
            case STUDENTS :
                return Students.CONTENT_TYPE;
            case STUDENTS_YEARS :
                return StudentsYears.CONTENT_TYPE;
            case STUDENTS_SEMESTERS :
                return StudentsSemesters.CONTENT_TYPE;
            case STUDENTS_SCHEDULE_DAYS :
                return StudentsScheduleDays.CONTENT_TYPE;
            case STUDENTS_SCHEDULE_CLASSES :
                return StudentsScheduleClasses.CONTENT_TYPE;
            case STUDENTS_CALENDARS_TYPES :
                return StudentsCalendarsTypes.CONTENT_TYPE;
            case STUDENTS_CALENDARS :
                return StudentsCalendars.CONTENT_TYPE;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Uri newItemUri;

        if (!applyingBatch()) {
            final SQLiteDatabase db = mDbHelper.getWritableDatabase();
            db.beginTransaction();
            try {
                newItemUri = insertInTransaction(uri, values);
                db.setTransactionSuccessful();
            } finally {
                db.endTransaction();
            }
        } else {
            newItemUri = insertInTransaction(uri, values);
        }

        /*if (newItemUri != null) {
            getContext().getContentResolver().notifyChange(uri, null);
        }*/

        return newItemUri;
    }

    private Uri insertInTransaction(Uri uri, ContentValues values) {
        Uri newItemUri = null;

        final int match = sUriMatcher.match(uri);
        switch (match) {
            case USERS: {
                long id = mDbHelper.insertUsers(values);
                if (id < 0) {
                    break;
                }
                newItemUri = Users.buildUserUri(String.valueOf(id));

                break;
            }
            case STUDENTS: {
                long id = mDbHelper.insertStudents(values);
                if (id < 0) {
                    break;
                }
                newItemUri = Students.buildStudentUri(String.valueOf(id));
                break;
            }
            default :
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        return newItemUri;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int count = 0;

        if (!applyingBatch()) {
            final SQLiteDatabase db = mDbHelper.getWritableDatabase();
            db.beginTransaction();
            try {
                count = buildSelection(uri, sUriMatcher.match(uri))
                        .where(selection, selectionArgs)
                        .update(db, values);
                db.setTransactionSuccessful();
            } finally {
                db.endTransaction();
            }
        } else {
            mDb = mDbHelper.getWritableDatabase();
            count = buildSelection(uri, sUriMatcher.match(uri))
                    .where(selection, selectionArgs)
                    .update(mDb, values);
        }

        /*if (count > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }*/

        return count;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int count = 0;

        if (!applyingBatch()) {
            final SQLiteDatabase db = mDbHelper.getWritableDatabase();
            db.beginTransaction();
            try {
                count = buildSelection(uri, sUriMatcher.match(uri))
                        .where(selection, selectionArgs)
                        .delete(db);
                db.setTransactionSuccessful();
            } finally {
                db.endTransaction();
            }
        } else {
            mDb = mDbHelper.getWritableDatabase();
            count = buildSelection(uri, sUriMatcher.match(uri))
                    .where(selection, selectionArgs)
                    .delete(mDb);
        }

        /*if (count > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }*/

        return count;
    }

    /**
     * Allows users to do multiple inserts into a table using the same statement
     */
    private boolean applyingBatch() {
        return mApplyingBatch.get() != null && mApplyingBatch.get();
    }

    /**
     * Builds selection using a {@link SelectionBuilder} to match the requested {@link Uri}.
     */
    private static SelectionBuilder buildSelection(Uri uri, int match) {
        final SelectionBuilder builder = new SelectionBuilder();

        switch (match) {
            case USERS :
                return builder.table(Tables.USERS);
            case STUDENTS :
                return builder.table(Tables.STUDENTS);
            case STUDENTS_YEARS :
                return builder.table(Tables.STUDENTS_YEARS);
            case STUDENTS_SEMESTERS :
                return builder.table(Tables.STUDENTS_SEMESTERS);
            case STUDENTS_SCHEDULE_DAYS :
                return builder.table(Tables.STUDENTS_SCHEDULE_DAYS);
            case STUDENTS_SCHEDULE_CLASSES :
                return builder.table(Tables.STUDENTS_SCHEDULE_CLASSES);
            case STUDENTS_CALENDARS_TYPES :
                return builder.table(Tables.STUDENTS_CALENDARS_TYPES);
            case STUDENTS_CALENDARS :
                return builder.table(Tables.STUDENTS_CALENDARS);

            default :
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Override
    public void shutdown() {
        super.shutdown();

        if (mDbHelper != null) {
            mDbHelper.close();
            mDbHelper = null;
            mDb = null;
        }
    }
}
