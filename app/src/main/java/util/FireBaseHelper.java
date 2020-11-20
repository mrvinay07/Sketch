package util;

import android.content.Context;
import android.os.Bundle;
import com.google.firebase.analytics.FirebaseAnalytics;

public class FireBaseHelper {
    private static FireBaseHelper INSTANCE;
    private static FirebaseAnalytics mFirebaseAnalytics;
    private Context context;

    private FireBaseHelper() {
    }

    public static FireBaseHelper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new FireBaseHelper();
        }
        return INSTANCE;
    }

    public void initialize(Context context2) {
        this.context = context2;
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(context2);
        mFirebaseAnalytics.setMinimumSessionDuration(20000);
        mFirebaseAnalytics.setSessionTimeoutDuration(500);
        mFirebaseAnalytics.setAnalyticsCollectionEnabled(true);
    }

    public void LogEvent(String str, Bundle bundle) {
        if (mFirebaseAnalytics != null) {
            mFirebaseAnalytics.logEvent(str, bundle);
        }
    }

    public FirebaseAnalytics getmFirebaseAnalytics() {
        return mFirebaseAnalytics;
    }
}