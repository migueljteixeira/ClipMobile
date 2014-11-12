package com.migueljteixeira.clipmobile.provider;

import android.net.Uri;
import android.provider.BaseColumns;

import com.migueljteixeira.clipmobile.ClipMobileApplication;

public class ClipMobileContract {

    interface UsersColumns {

        /**
         * This column is NOT in this table, it is for reference purposes only.
         */
        String REF_USERS_ID = "users_id";

        String USERNAME = "users_username";
    }

    interface StudentsColumns {

        /**
         * This column is NOT in this table, it is for reference purposes only.
         */
        String REF_STUDENTS_ID = "students_id";

        String NUMBER_ID = "students_number_id";

        String NUMBER = "students_number";
    }

    interface StudentsYearSemesterColumns {

        /**
         * This column is NOT in this table, it is for reference purposes only.
         */
        String REF_STUDENTS_YEAR_SEMESTER_ID = "students_year_semester_id";

        String YEAR = "students_year_semester_year";

        String SEMESTER = "students_year_semester_semester";
    }


    interface ScheduleDaysColumns {

        /**
         * This column is NOT in this table, it is for reference purposes only.
         */
        String REF_SCHEDULE_DAYS_ID = "schedule_days_id";

        String DAY = "schedule_days_day";
    }

    interface ScheduleClassesColumns {

        /**
         * This column is NOT in this table, it is for reference purposes only.
         */
        String REF_SCHEDULE_CLASSES_ID = "schedule_classes_id";

        String NAME = "schedule_classes_name";

        String NAME_ABBREVIATION = "schedule_classes_name_abbreviation";

        String TYPE = "schedule_classes_type";

        String HOUR_START = "schedule_classes_hour_start";

        String HOUR_END = "schedule_classes_hour_end";

        String ROOM = "schedule_classes_room";
    }

    interface StudentClassesColumns {

        /**
         * This column is NOT in this table, it is for reference purposes only.
         */
        String REF_STUDENT_CLASSES_ID = "student_classes_id";

        String NAME = "student_classes_name";

        String NUMBER = "student_classes_number";

        String SEMESTER = "student_classes_semester";
    }

//
//    interface StudentsCalendarsTypesColumns {
//
//        /**
//         * This column is NOT in this table, it is for reference purposes only.
//         */
//        String REF_STUDENT_CALENDAR_TYPE_ID = "students_calendars_type_id";
//
//        String NAME = "students_calendars_type_name";
//    }
//
//    interface StudentsCalendarsColumns {
//
//        /**
//         * This column is NOT in this table, it is for reference purposes only.
//         */
//        String REF_STUDENT_CALENDAR_ID = "students_calendars_id";
//
//        String NAME = "students_calendars_name";
//
//        String DATE = "students_calendars_date";
//
//        String HOUR = "students_calendars_hour";
//
//        String ROOMS = "students_calendars_rooms";
//
//        String NUMBER = "students_calendars_number";
//    }







    private static final Uri BASE_CONTENT_URI = Uri.parse("content://"
            + ClipMobileApplication.CONTENT_AUTHORITY);

    public static final String PATH_USERS = "users";

    public static final String PATH_STUDENTS = "students";

    public static final String PATH_STUDENTS_YEAR_SEMESTER = "students_year_semester";

    public static final String PATH_SCHEDULE_DAYS = "schedule_days";

    public static final String PATH_SCHEDULE_CLASSES = "schedule_classes";

    public static final String PATH_STUDENT_CLASSES = "student_classes";

    /*public static final String PATH_STUDENTS_CALENDARS_TYPES = "students_calendars_types";

    public static final String PATH_STUDENTS_CALENDARS = "students_calendars";*/


    public static class Users implements UsersColumns, BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_USERS)
                .build();

        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.clipmobile.users";

        public static Uri buildUri(String userId) {
            return CONTENT_URI.buildUpon().appendPath(userId).build();
        }

    }

    public static class Students implements StudentsColumns, BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_STUDENTS)
                .build();

        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.clipmobile.students";

        public static Uri buildUri(String studentId) {
            return CONTENT_URI.buildUpon().appendPath(studentId).build();
        }

    }

    public static class StudentsYearSemester implements StudentsYearSemesterColumns, BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_STUDENTS_YEAR_SEMESTER)
                .build();

        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.clipmobile.students_year_semester";

        public static Uri buildUri(String studentYearId) {
            return CONTENT_URI.buildUpon().appendPath(studentYearId).build();
        }

    }

    public static class ScheduleDays implements ScheduleDaysColumns, BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_SCHEDULE_DAYS)
                .build();

        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.clipmobile.schedule_days";

        public static Uri buildUri(String studentScheduleDayId) {
            return CONTENT_URI.buildUpon().appendPath(studentScheduleDayId).build();
        }

    }

    public static class ScheduleClasses implements ScheduleClassesColumns, BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_SCHEDULE_CLASSES)
                .build();

        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.clipmobile.schedule_classes";

        public static Uri buildUri(String studentScheduleClassId) {
            return CONTENT_URI.buildUpon().appendPath(studentScheduleClassId).build();
        }

    }

    public static class StudentClasses implements StudentClassesColumns, BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_STUDENT_CLASSES)
                .build();

        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.clipmobile.student_classes";

        public static Uri buildUri(String studentClassId) {
            return CONTENT_URI.buildUpon().appendPath(studentClassId).build();
        }

    }

    /*public static class StudentsCalendarsTypes implements StudentsCalendarsTypesColumns, BaseColumns {

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

    }*/

}
