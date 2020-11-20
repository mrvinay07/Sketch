package com.teamvinay.sketch;

import android.content.Context;
import android.util.Log;
import androidx.multidex.MultiDexApplication;
import listeners.AdSdkInitializeListener;

public class App extends MultiDexApplication {
    public static Context context;
    public static App instance;
    private AdSdkInitializeListener sdkInitializeListener;

    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static App getInstance() {
        return instance;
    }

    /* access modifiers changed from: protected */
    public void attachBaseContext(Context context2) {
        super.attachBaseContext(context2);
        Log.d("Mult", "context");
    }
}