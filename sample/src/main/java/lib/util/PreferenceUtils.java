package lib.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.ma.myutil.BuildConfig;

/**
 * SharePreference工具
 */
public class PreferenceUtils {

    private SharedPreferences sp;
    private SharedPreferences.Editor spEditor;
    private static PreferenceUtils preferenceUtils = null;

    /*当前已经存在的字段*/
    public static String KEY_USER_ID = "key_user_id";//用户id
    public static String KEY_ALREADY_ADD_DEFAULT_TYPE = "key_first_add_default_type";//第一次添加

    private PreferenceUtils(Context context) {
        sp = context.getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE);
        spEditor = sp.edit();
    }

    public static PreferenceUtils getInstance(Context context) {
        if (preferenceUtils == null) {
            synchronized (PreferenceUtils.class) {
                if (preferenceUtils == null) {
                    preferenceUtils = new PreferenceUtils(context);
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
