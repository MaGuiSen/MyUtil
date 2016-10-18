package com.ma.easysource.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * SharePreference工具
 */
public class PreferenceUtils {

    private SharedPreferences sp;
    private SharedPreferences.Editor spEditor;
    private static PreferenceUtils preferenceUtils = null;

    private PreferenceUtils(Context context) {
        sp = context.getSharedPreferences("preference_data", Context.MODE_PRIVATE);
        spEditor = sp.edit();
    }

    public static PreferenceUtils getInstance(Context context) {
        if (preferenceUtils == null) {
            synchronized (PreferenceUtils.class) {
                if (preferenceUtils == null) {
                    preferenceUtils = new PreferenceUtils(context.getApplicationContext());
                }
            }
        }
        return preferenceUtils;
    }

    public String getString(String key) {
        return getString(key, "");
    }

    public String getString(String key, String defaultString) {
        return sp.getString(key, defaultString);
    }

    public boolean getBoolean(String key) {
        return getBoolean(key, false);
    }

    public boolean getBoolean(String key, boolean defaultBool) {
        return sp.getBoolean(key,defaultBool);
    }

    public int getInt(String key) {
        return getInt(key, 0);
    }

    public int getInt(String key, int defaultInt) {
        return sp.getInt(key, defaultInt);
    }

    public long getLong(String key) {
        return getLong(key, 0);
    }

    public long getLong(String key, long defaultInt) {
        return sp.getLong(key, defaultInt);
    }

    public void save(String key, String value) {
        spEditor.putString(key, value).commit();
    }

    public void save(String key, boolean value) {
        spEditor.putBoolean(key, value).commit();
    }

    public void save(String key, int value) {
        spEditor.putInt(key, value).commit();
    }

    public void save(String key, long value) {
        spEditor.putLong(key, value).commit();
    }

    public void removeKey(String key) {
        spEditor.remove(key).commit();
    }
}
