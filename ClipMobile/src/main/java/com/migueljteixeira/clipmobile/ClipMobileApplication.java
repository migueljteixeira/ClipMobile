package com.migueljteixeira.clipmobile;

import android.annotation.SuppressLint;
import android.app.Application;
import android.os.StrictMode;

import com.uwetrottmann.androidutils.AndroidUtils;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class ClipMobileApplication extends Application {

    public static String CONTENT_AUTHORITY;

    @Override
    public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault("Roboto-Regular.ttf", R.attr.fontPath);

        // Set provider authority
        CONTENT_AUTHORITY = getString(R.string.provider_authority);

        // Enable StrictMode
        enableStrictMode();
    }

    @SuppressLint("NewApi")
    private void enableStrictMode() {
        if (!BuildConfig.DEBUG || !AndroidUtils.isGingerbreadOrHigher())
            return;

        // Enable StrictMode
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectAll().penaltyLog().build());

        // Policy applied to all threads in the virtual machine's process
        final StrictMode.VmPolicy.Builder vmPolicyBuilder = new StrictMode.VmPolicy.Builder();
        vmPolicyBuilder.detectAll();
        vmPolicyBuilder.penaltyLog();
        if (AndroidUtils.isJellyBeanOrHigher())
            vmPolicyBuilder.detectLeakedRegistrationObjects();

        StrictMode.setVmPolicy(vmPolicyBuilder.build());
    }

}
