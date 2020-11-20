package com.teamvinay.sketch;

import android.content.Context;
import android.util.Log;
import helper.Constants;
import java.util.Date;
import util.SharedPreferencesManager;

public class AppRater {
    private static final String PREF_KEY_APP_RATED = "android_app_rated";
    private static final String PREF_KEY_INSTALL_DATE = "android_rate_install_date";
    private static final String PREF_KEY_IS_AGREE_SHOW_DIALOG = "android_rate_is_agree_show_dialog";
    private static final String PREF_KEY_LAUNCH_TIMES = "android_rate_launch_times";
    private static final String PREF_KEY_REMIND_INTERVAL = "android_rate_remind_interval";
    private static int event;
    private static AppRater singleton;
    private int installDate = 5;
    private int launchTimes = 8;
    private Context mContext;
    private int remindInterval = 2;

    private AppRater(Context context) {
        this.mContext = context.getApplicationContext();
        monitor();
    }

    public static AppRater initialize(Context context) {
        if (singleton == null) {
            synchronized (AppRater.class) {
                if (singleton == null) {
                    singleton = new AppRater(context);
                }
            }
        }
        return singleton;
    }

    public AppRater setLaunchTimes(int i) {
        this.launchTimes = i;
        return this;
    }

    public AppRater setInstallDays(int i) {
        this.installDate = i;
        return this;
    }

    public AppRater setRemindInterval(int i) {
        this.remindInterval = i;
        return this;
    }

    private boolean isOverLaunchTimes() {
        return getLaunchTimes(this.mContext) >= this.launchTimes;
    }

    private boolean isOverInstallDate() {
        return isOverDate(getInstallDate(this.mContext), this.installDate);
    }

    public static boolean isOverDate(long j, int i) {
        return new Date().getTime() - j >= ((long) ((((i * 24) * 60) * 60) * 1000));
    }

    private boolean isOverRemindDate() {
        return isOverDate(getRemindInterval(this.mContext), this.remindInterval);
    }

    private void setInstallDate(Context context) {
        SharedPreferencesManager.setSomeLongValue(context, PREF_KEY_INSTALL_DATE, Long.valueOf(new Date().getTime()));
    }

    private void setLaunchTimes(Context context, int i) {
        SharedPreferencesManager.setSomeIntValue(context, PREF_KEY_LAUNCH_TIMES, i);
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

    public void clear() {
        this.mContext = null;
    }

    /* access modifiers changed from: package-private */
    public void check() {
        if (isOverLaunchTimes() || isOverInstallDate()) {
            if (!SharedPreferencesManager.HasKey(this.mContext, Constants.APP_RATER_SHOWED)) {
                Constants.NATIVE_APP_RATER = true;
                Log.d("AppRater", "Over Launch and Date");
            } else if (!SharedPreferencesManager.getSomeBoolValue(this.mContext, Constants.APP_RATED)) {
                Log.d("AppRater", "App Rated " + String.valueOf(SharedPreferencesManager.getSomeBoolValue(this.mContext, Constants.APP_RATED)));
                if (!SharedPreferencesManager.getSomeBoolValue(this.mContext, Constants.FEEDBACK_GIVEN)) {
                    Log.d("AppRater", "FeedBack Given " + String.valueOf(SharedPreferencesManager.getSomeBoolValue(this.mContext, Constants.APP_RATED)));
                    if (!SharedPreferencesManager.HasKey(this.mContext, PREF_KEY_REMIND_INTERVAL)) {
                        Constants.NATIVE_APP_RATER = true;
                        Log.d("AppRater", "Has Key");
                    } else if (isOverRemindDate()) {
                        Log.d("AppRater", "Over Remind Date");
                        Constants.NATIVE_APP_RATER = true;
                    }
                } else if (!SharedPreferencesManager.HasKey(this.mContext, PREF_KEY_REMIND_INTERVAL)) {
                    Constants.NATIVE_APP_RATER = true;
                    Log.d("AppRater", "Has Key");
                } else if (isOverRemindDate()) {
                    Log.d("AppRater", "Over Remind Date");
                    Constants.NATIVE_APP_RATER = true;
                }
            }
            Constants.NATIVE_APP_RATER = true;
            Log.d("AppRater", "Over Launch and Date");
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
        return SharedPreferencesManager.getSomeIntValue(this.mContext, Constants.EVENT) > SharedPreferencesManager.getSomeIntValue(this.mContext, Constants.EVENT_COUNT);
    }

    public void ResetIncrementEvent() {
        SharedPreferencesManager.Remove(this.mContext, Constants.EVENT);
        SharedPreferencesManager.setSomeIntValue(this.mContext, Constants.NEW_EVENT, 5);
        if (event > 0) {
            event = 0;
        }
    }
}
