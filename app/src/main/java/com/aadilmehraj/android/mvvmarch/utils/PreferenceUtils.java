package com.aadilmehraj.android.mvvmarch.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PreferenceUtils {

    public static final boolean DEFAULT = false;
    public static final String KEY_LAYOUT = "key-layout";

    public static boolean isDrawerLayout(Context context) {
        SharedPreferences preference = PreferenceManager
            .getDefaultSharedPreferences(context);
        return preference.getBoolean(KEY_LAYOUT, DEFAULT);
    }

    public static void setDrawerLayout(Context context, boolean isDrawerLayout) {
        SharedPreferences.Editor editor = PreferenceManager
            .getDefaultSharedPreferences(context).edit();
        editor.putBoolean(KEY_LAYOUT, isDrawerLayout);
        editor.apply();
    }
}
