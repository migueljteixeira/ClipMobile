package com.migueljteixeira.clipmobile.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class ClipSettings {

    private final static String COOKIE_NAME = "com.migueljteixeira.clipmobile.cookie";
    private final static String LOGIN_TIME = "com.migueljteixeira.clipmobile.loggedInTime";

    private final static String LOGGED_IN_USER_ID = "com.migueljteixeira.clipmobile.loggedInUserId";
    private final static String LOGGED_IN_USER_NAME = "com.migueljteixeira.clipmobile.loggedInUserName";
    private final static String LOGGED_IN_USER_PW = "com.migueljteixeira.clipmobile.loggedInUserPw";

    private static SharedPreferences get(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    private static SharedPreferences.Editor edit(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).edit();
    }


    public static String getCookie(Context context) {
        return get(context).getString(COOKIE_NAME, null);
    }

    public static void saveCookie(Context context, String cookie) {
        edit(context).putString(COOKIE_NAME, cookie).commit();
    }

    public static void saveLoginTime(Context context) {
        edit(context).putLong(LOGIN_TIME, new Date().getTime()).commit();
    }

    public static boolean isTimeForANewCookie(Context context) {
        long currentTime = new Date().getTime();
        long loginTime = get(context).getLong(LOGIN_TIME, -1);

        long elapsedTime = currentTime - loginTime;

        System.out.println("!!! login: " + loginTime);
        System.out.println("!!! currentTime: " + currentTime);

        int elapsedTimeInMinutes = (int) TimeUnit.MILLISECONDS.toMinutes(elapsedTime);
        System.out.println("!!! elapsed time: " + elapsedTimeInMinutes);

        // If the elapsedTime > 50min, we need to request a new cookie from the server
        if(elapsedTimeInMinutes > 50)
            return true;

        return false;
    }

    public static int TimeCookie(Context context) {
        long currentTime = new Date().getTime();
        long loginTime = get(context).getLong(LOGIN_TIME, -1);

        long elapsedTime = currentTime - loginTime;

        System.out.println("login: " + loginTime);
        System.out.println("currentTime: " + currentTime);

        int elapsedTimeInMinutes = (int) TimeUnit.MILLISECONDS.toMinutes(elapsedTime);
        System.out.println("elapsed time: " + elapsedTimeInMinutes);

        return elapsedTimeInMinutes;
    }


    public static boolean isUserLoggedIn(Context context) {
        return get(context).getLong(LOGGED_IN_USER_ID, -1) != -1;
    }

    public static long getLoggedInUserId(Context context) {
        return get(context).getLong(LOGGED_IN_USER_ID, -1);
    }

    public static String getLoggedInUserName(Context context) {
        return get(context).getString(LOGGED_IN_USER_NAME, null);
    }

    public static String getLoggedInUserPw(Context context) {
        return get(context).getString(LOGGED_IN_USER_PW, null);
    }

    public static void setLoggedInUser(Context context, long id, String username, String password) {
        edit(context).putLong(LOGGED_IN_USER_ID, id).commit();

        // Save credentials
        edit(context).putString(LOGGED_IN_USER_NAME, username).commit();
        edit(context).putString(LOGGED_IN_USER_PW, password).commit();
    }

    public static void logoutUser(Context context) {

        // Clear user personal data
        edit(context).clear().commit();
    }

}
