package com.migueljteixeira.clipmobile.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class ClipSettings {

    private final static String COOKIE_NAME = "com.migueljteixeira.clipmobile.cookie";
    private final static String LOGGED_IN_USER_ID = "com.migueljteixeira.clipmobile.loggedInUserId";

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

    public static boolean isUserLoggedIn(Context context) {
        return get(context).getLong(LOGGED_IN_USER_ID, -1) != -1;
    }

    public static long getLoggedInUserId(Context context) {
        return get(context).getLong(LOGGED_IN_USER_ID, -1);
    }

    public static void setLoggedInUserId(Context context, long id) {
        edit(context).putLong(LOGGED_IN_USER_ID, id).commit();
    }


}
