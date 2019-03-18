package com.chejdj.wanandroid.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.chejdj.wanandroid.WanAndroidApplication;


public class SharedPreferenceUtil {
    private static final String TAG = "SharedPreferenceUtil";

    public static boolean putData(String filename, String key, String value) {
        SharedPreferences sharedPreferences = WanAndroidApplication.getMyApplication().getSharedPreferences(filename, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        return editor.commit();
    }

    public static boolean putData(String filename, String key, long value) {
        SharedPreferences sharedPreferences = WanAndroidApplication.getMyApplication().getSharedPreferences(filename, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(key, value);
        return editor.commit();
    }

    public static boolean putData(String filename, String key, int value) {
        SharedPreferences sharedPreferences = WanAndroidApplication.getMyApplication().getSharedPreferences(filename, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        return editor.commit();
    }


    public static String LoadStringData(String filename, String key) {
        SharedPreferences sharedPreferences = WanAndroidApplication.getMyApplication().getSharedPreferences(filename, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, null);
    }

    public static long LoadLongData(String filename, String key) {
        SharedPreferences sharedPreferences = WanAndroidApplication.getMyApplication().getSharedPreferences(filename, Context.MODE_PRIVATE);
        return sharedPreferences.getLong(key, 0);
    }

    public static boolean removeData(String filename, String key) {
        SharedPreferences sharedPreferences = WanAndroidApplication.getMyApplication().getSharedPreferences(filename, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(key);
        return editor.commit();
    }

}
