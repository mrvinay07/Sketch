package com.teamvinay.sketch;

import android.content.Context;
import android.util.Log;
import helper.Constants;
import java.util.Date;
import util.SharedPreferencesManager;

public class CustomAppRater {
    private static final String PREF_CUSTOM_LAUNCH_TIMES = "launch_times";
    private static final String PREF_KEY_INSTALL_DATE = "custom_android_rate_install_date";
    private static final String PREF_KEY_IS_AGREE_SHOW_DIALOG = "custom_android_rate_is_agree_show_dialog";
    private static final String PREF_KEY_LAUNCH_TIMES = "custom_android_rate_launch_times";
    private static final String PREF_KEY_REMIND_INTERVAL = "custom_android_rate_remind_interval";
    private static int event = 0;
    private static int event_count = 3;
    private static CustomAppRater singleton;
    private int installDate = 5;
    private int launchTimes = 2;
    private Context mContext;
    private int remindInterval = 2;

    private CustomAppRater(Context context) {
        this.mContext = context.getApplicationContext();
        monitor();
    }

    public static CustomAppRater initialize(Context context) {
        if (singleton == null) {
            synchronized (CustomAppRater.class) {
                if (singleton == null) {
                    singleton = new CustomAppRater(context);
                }
            }
        }
        return singleton;
    }

    public CustomAppRater setLaunchTimes(int i) {
        this.launchTimes = i;
        return this;
    }

    public CustomAppRater setInstallDays(int i) {
        this.installDate = i;
        return this;
    }

    public CustomAppRater setRemindInterval(int i) {
        this.remindInterval = i;
        return this;
    }

    public void reset() {
        SharedPreferencesManager.Remove(this.mContext, PREF_KEY_INSTALL_DATE);
        SharedPreferencesManager.Remove(this.mContext, PREF_KEY_LAUNCH_TIMES);
        SharedPreferencesManager.Remove(this.mContext, PREF_KEY_REMIND_INTERVAL);
    }

    public boolean isOverLaunchTimes() {
        if (SharedPreferencesManager.HasKey(this.mContext, PREF_CUSTOM_LAUNCH_TIMES)) {
            if (getLaunchTimes(this.mContext) >= SharedPreferencesManager.getSomeIntValue(this.mContext, PREF_CUSTOM_LAUNCH_TIMES)) {
                return true;
            }
            return false;
        } else if (getLaunchTimes(this.mContext) >= this.launchTimes) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isOverInstallDate() {
        return isOverDate(getInstallDate(this.mContext), this.installDate);
    }

    public static boolean isOverDate(long j, int i) {
        return new Date().getTime() - j >= ((long) ((((i * 24) * 60) * 60) * 1000));
    }

    public boolean isOverRemindDate() {
        return isOverDate(getRemindInterval(this.mContext), this.remindInterval);
    }

    private void setInstallDate(Context context) {
        SharedPreferencesManager.setSomeLongValue(context, PREF_KEY_INSTALL_DATE, Long.valueOf(new Date().getTime()));
    }

    public void setLaunchTimes(Context context, int i) {
        SharedPreferencesManager.setSomeIntValue(context, PREF_KEY_LAUNCH_TIMES, i);
    }

    public void setCustomLaunchTimes(Context context, int i) {
        SharedPreferencesManager.setSomeIntValue(context, PREF_CUSTOM_LAUNCH_TIMES, i);
    }

    public void setRemindInterval(Context context) {
        SharedPreferencesManager.setSomeLongValue(context, PREF_KEY_REMIND_INTERVAL, Long.valueOf(new Date().getTime()));
    }

    private int getLaunchTimes(Context context) {
        return SharedPreferencesManager.getSomeIntValue(context, PREF_KEY_LAUNCH_TIMES);
    }

    private long getInstallDate(Context context) {
        return SharedPreferencesManager.getSomeLongValue(context, PREF_KEY_INSTALL_DATE).longValue();
    }

    private long getRemindInterval(Context context) {
        return SharedPreferencesManager.getSomeLongValue(context, PREF_KEY_REMIND_INTERVAL).longValue();
    }

    public void monitor() {
        if (!SharedPreferencesManager.HasKey(this.mContext, PREF_KEY_LAUNCH_TIMES)) {
            setInstallDate(this.mContext);
            Log.d("launch", "First");
        }
        setLaunchTimes(this.mContext, getLaunchTimes(this.mContext) + 1);
        Log.d("launch", "Not first");
        check();
    }

    /* access modifiers changed from: package-private */
    public void check() {
        if (isOverLaunchTimes() || isOverInstallDate()) {
            Log.d("CustomAppRater", "Over Launch and Date");
        }
    }

    public void IncrementEvent() {
        if (!SharedPreferencesManager.getSomeBoolValue(this.mContext, Constants.APP_RATED)) {
            if (!SharedPreferencesManager.HasKey(this.mContext, Constants.EVENT)) {
                event++;
                SharedPreferencesManager.setSomeIntValue(this.mContext, Constants.EVENT, event);
            } else {
                event = SharedPreferencesManager.getSomeIntValue(this.mContext, Constants.EVENT);
                event++;
                SharedPreferencesManager.setSomeIntValue(this.mContext, Constants.EVENT, event);
            }
            Log.d("IncrementEvent", String.valueOf(SharedPreferencesManager.getSomeIntValue(this.mContext, Constants.EVENT)));
        }
    }

    public boolean isOverIncrementCount() {
        if (SharedPreferencesManager.HasKey(this.mContext, Constants.NEW_EVENT)) {
            Log.d("IncrementEvent", "Has Key Over");
            if (SharedPreferencesManager.getSomeIntValue(this.mContext, Constants.EVENT) <= SharedPreferencesManager.getSomeIntValue(this.mContext, Constants.NEW_EVENT) || SharedPreferencesManager.HasKey(this.mContext, Constants.NEW_EVENT_CALLED)) {
                return false;
            }
            SharedPreferencesManager.setSomeBoolValue(this.mContext, Constants.NEW_EVENT_CALLED, true);
            return true;
        }
        Log.d("IncrementEvent", " Not Has KeyOver");
        return SharedPreferencesManager.getSomeIntValue(this.mContext, Constants.EVENT) > event_count;
    }

    public void ResetIncrementEvent() {
        SharedPreferencesManager.Remove(this.mContext, Constants.EVENT);
        SharedPreferencesManager.setSomeIntValue(this.mContext, Constants.NEW_EVENT, 5);
        if (event > 0) {
            event = 0;
        }
    }
}