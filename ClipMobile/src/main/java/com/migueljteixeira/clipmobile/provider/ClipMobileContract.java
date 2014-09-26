package com.migueljteixeira.clipmobile.provider;

import android.net.Uri;
import android.provider.BaseColumns;

import com.migueljteixeira.clipmobile.ClipMobileApplication;

public class ClipMobileContract {

    interface UsersColumns {

        /**
         * This column is NOT in this table, it is for reference purposes only.
         */
        String REF_USER_ID = "user_id";

        String USERNAME = "users_username";
    }

    interface StudentsColumns {

        /**
         * This column is NOT in this table, it is for reference purposes only.
         */
        String REF_STUDENT_ID = "students_id";

        String NUMBER_ID = "students_number_id";

        String NUMBER = "students_number";
    }

    interface StudentsYearsColumns {

        /**
         * This column is NOT in this table, it is for reference purposes only.
         */
        String REF_STUDENT_YEAR_ID = "students_years_id";

        String YEAR = "students_years_year";
    }

    interface StudentsSemestersColumns {

        /**
         * This column is NOT in this table, it is for reference purposes only.
         */
        String REF_STUDENT_SEMESTER_ID = "students_semesters_id";

        String SEMESTER = "students_semesters_semester";
    }

    interface StudentsScheduleDaysColumns {

        /**
         * This column is NOT in this table, it is for reference purposes only.
         */
        String REF_STUDENT_SCHEDULE_DAY_ID = "students_schedule_days_id";

        String DAY = "students_schedule_days_day";
    }

    interface StudentsScheduleClassesColumns {

        /**
         * This column is NOT in this table, it is for reference purposes only.
         */
        String REF_STUDENT_SCHEDULE_CLASS_ID = "students_schedule_classes_id";

        String NAME = "students_schedule_classes_name";

        String NAME_ABBREVIATION = "students_schedule_classes_name_abbreviation";

        String TYPE = "students_schedule_classes_type";

        String HOUR_START = "students_schedule_classes_hour_start";

        String HOUR_END = "students_schedule_classes_hour_end";

        String ROOM = "students_schedule_classes_room";
    }

    interface StudentsCalendarsTypesColumns {

        /**
         * This column is NOT in this table, it is for reference purposes only.
         */
        String REF_STUDENT_CALENDAR_TYPE_ID = "students_calendars_type_id";

        String NAME = "students_calendars_type_name";
    }

    interface StudentsCalendarsColumns {

        /**
         * This column is NOT in this table, it is for reference purposes only.
         */
        String REF_STUDENT_CALENDAR_ID = "students_calendars_id";

        String NAME = "students_calendars_name";

        String DATE = "students_calendars_date";

        String HOUR = "students_calendars_hour";

        String ROOMS = "students_calendars_rooms";

        String NUMBER = "students_calendars_number";
    }







    private static final Uri BASE_CONTENT_URI = Uri.parse("content://"
            + ClipMobileApplication.CONTENT_AUTHORITY);

    public static final String PATH_USERS = "users";

    public static final String PATH_STUDENTS = "students";

    public static final String PATH_STUDENTS_YEARS = "students_years";

    public static final String PATH_STUDENTS_SEMESTERS = "students_semesters";

    public static final String PATH_STUDENTS_SCHEDULE_DAYS = "students_schedule_days";

    public static final String PATH_STUDENTS_SCHEDULE_CLASSES = "students_schedule_classes";

    public static final String PATH_STUDENTS_CALENDARS_TYPES = "students_calendars_types";

    public static final String PATH_STUDENTS_CALENDARS = "students_calendars";


    public static class Users implements UsersColumns, BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_USERS)
                .build();

        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.clipmobile.users";

        public static Uri buildUserUri(String userId) {
            return CONTENT_URI.buildUpon().appendPath(userId).build();
        }

    }

    public static class Students implements StudentsColumns, BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_STUDENTS)
                .build();

        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.clipmobile.students";

        public static Uri buildStudentUri(String studentId) {
            return CONTENT_URI.buildUpon().appendPath(studentId).build();
        }

    }

    public static class StudentsYears implements StudentsYearsColumns, BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_STUDENTS_YEARS)
                .build();

        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.clipmobile.students_years";

        public static Uri buildStudentUri(String studentYearId) {
            return CONTENT_URI.buildUpon().appendPath(studentYearId).build();
        }

    }

    public static class StudentsSemesters implements StudentsSemestersColumns, BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_STUDENTS_SEMESTERS)
                .build();

        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.clipmobile.students_semesters";

        public static Uri buildStudentUri(String studentSemesterId) {
            return CONTENT_URI.buildUpon().appendPath(studentSemesterId).build();
        }

    }

    public static class StudentsScheduleDays implements StudentsScheduleDaysColumns, BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_STUDENTS_SCHEDULE_DAYS)
                .build();

        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.clipmobile.students_schedule_days";

        public static Uri buildStudentUri(String studentScheduleDayId) {
            return CONTENT_URI.buildUpon().appendPath(studentScheduleDayId).build();
        }

    }

    public static class StudentsScheduleClasses implements StudentsScheduleClassesColumns, BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_STUDENTS_SCHEDULE_CLASSES)
                .build();

        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.clipmobile.students_schedule_classes";

        public static Uri buildStudentUri(String studentScheduleClassId) {
            return CONTENT_URI.buildUpon().appendPath(studentScheduleClassId).build();
        }

    }

    public static class StudentsCalendarsTypes implements StudentsCalendarsTypesColumns, BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_STUDENTS_CALENDARS_TYPES)
                .build();

        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.clipmobile.students_calendars_types";

        public static Uri buildStudentUri(String studentCalendarTypeId) {
            return CONTENT_URI.buildUpon().appendPath(studentCalendarTypeId).build();
        }

    }

    public static class StudentsCalendars implements StudentsCalendarsColumns, BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_STUDENTS_CALENDARS)
                .build();

        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.clipmobile.students_calendars";

        public static Uri buildStudentUri(String studentCalendarId) {
            return CONTENT_URI.buildUpon().appendPath(studentCalendarId).build();
        }

    }

}
