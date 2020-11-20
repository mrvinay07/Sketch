package util;

import android.content.Context;
import android.content.SharedPreferences;
import helper.Constants;

public class SharedPreferencesManager {
    private SharedPreferencesManager() {
    }

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(Constants.PREFS_NAME, 0);
    }

    public static String getSomeStringValue(Context context, String str) {
        return getSharedPreferences(context).getString(str, (String) null);
    }

    public static boolean getSomeBoolValue(Context context, String str) {
        return getSharedPreferences(context).getBoolean(str, false);
    }

    public static Long getSomeLongValue(Context context, String str) {
        return Long.valueOf(getSharedPreferences(context).getLong(str, 0));
    }

    public static int getSomeIntValue(Context context, String str) {
        return getSharedPreferences(context).getInt(str, 0);
    }

    public static void setSomeBoolValue(Context context, String str, boolean z) {
        SharedPreferences.Editor edit = getSharedPreferences(context).edit();
        edit.putBoolean(str, z);
        edit.commit();
    }

    public static void setSomeStringValue(Context context, String str, String str2) {
        SharedPreferences.Editor edit = getSharedPreferences(context).edit();
        edit.putString(str, str2);
        edit.commit();
    }

    public static void setSomeLongValue(Context context, String str, Long l) {
        SharedPreferences.Editor edit = getSharedPreferences(context).edit();
        edit.putLong(str, l.longValue());
        edit.commit();
    }

    public static void setSomeIntValue(Context context, String str, int i) {
        SharedPreferences.Editor edit = getSharedPreferences(context).edit();
        edit.putInt(str, i);
        edit.commit();
    }

    public static boolean HasKey(Context context, String str) {
        return getSharedPreferences(context).contains(str);
    }

    public static void Remove(Context context, String str) {
        SharedPreferences.Editor edit = getSharedPreferences(context).edit();
        edit.remove(str);
        edit.commit();
    }
}