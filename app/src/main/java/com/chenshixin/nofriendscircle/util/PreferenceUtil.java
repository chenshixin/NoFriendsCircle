package com.chenshixin.nofriendscircle.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Preference工具
 * Created by csx.
 */
public class PreferenceUtil {

    /**
     * 是否开启服务
     */
    public static final String KEY_SERVICE_START = "key_service_start";

    /**
     * 屏蔽次数
     */
    public static final String KEY_STOP_COUNT = "key_stop_count";

    private static SharedPreferences sp;

    public static void init(Context context) {
        if (sp == null) {
            try {
                sp = context.getSharedPreferences("no_friends_circle", Context.MODE_PRIVATE);
            } catch (Exception e) {
                Log.i("PreferenceUtil", "error=" + e.getMessage());
            }
        }
    }

    public static void saveLong(String key, long l) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putLong(key, l).apply();
    }

    public static long getLong(String key, long defaultLong) {
        return sp.getLong(key, defaultLong);
    }

    public static void saveBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(key, value).apply();
    }

    public static boolean getBoolean(String key, boolean defaultBoolean) {

        return sp.getBoolean(key, defaultBoolean);
    }

    public static void saveInt(String key, int value) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(key, value).apply();
    }

    public static int getInt(String key, int defaultInt) {
        return sp.getInt(key, defaultInt);
    }

    public static String getString(String key, String defaultInt) {
        return sp.getString(key, defaultInt);
    }

    public static void saveString(String key, String value) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, value).apply();
    }

    public static void remove(String key) {
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key).apply();
    }
}
