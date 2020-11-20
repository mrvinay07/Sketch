package com.teamvinay.sketch;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import listeners.AdClosedListener;
import util.AdUtil;

public class AdActivity extends AppCompatActivity implements AdClosedListener {
    private static boolean activityVisible = false;
    String adType = null;
    boolean backPressEnable = false;
    TextView textView;

    public static boolean isActivityVisible() {
        return activityVisible;
    }

    public static void activityResumed() {
        activityVisible = true;
    }

    public static void activityPaused() {
        activityVisible = false;
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.ad_activity);
        this.textView = (TextView) findViewById(R.id.ad_text);
        this.adType = getIntent().getStringExtra("adtype");
        AdUtil.getInstance().setAdClosedListener(this);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                if (AdActivity.isActivityVisible()) {
                    if (AdActivity.this.adType.equals("STARTUP_AD")) {
                        AdUtil.getInstance().ShowStartUpInterstitial();
                        Log.d(com.google.android.gms.ads.AdActivity.SIMPLE_CLASS_NAME, "start-up");
                    } else if (AdActivity.this.adType.equals("EFFECT_INTERSTITIAL")) {
                        AdUtil.getInstance().ShowEffectInterstitial();
                    }
                    AdActivity.this.textView.setText("Press back button to continue..");
                }
                AdActivity.this.backPressEnable = true;
            }
        }, 3000);
    }

    public void onBackPressed() {
        if (this.backPressEnable) {
            super.onBackPressed();
        }
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        activityPaused();
        super.onPause();
    }

    /* access modifiers changed from: protected */
    public void onStop() {
        super.onStop();
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        AdUtil.getInstance().setAdClosedListener((AdClosedListener) null);
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        activityResumed();
        super.onResume();
    }

    public void OnAdClosedListener() {
        finish();
    }
}
