package helper;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import com.facebook.ads.AdError;
import com.facebook.ads.Ad;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;
import com.mopub.mobileads.MoPubErrorCode;
import com.mopub.mobileads.MoPubInterstitial;

import listeners.AdClosedListener;
import util.FireBaseHelper;

public class AdHelper implements MoPubInterstitial.InterstitialAdListener, InterstitialAdListener {
    AdClosedListener adClosedListener;
    private boolean facebookInterstitial_Loaded = false;
    private InterstitialAd interstitialAd;
    private Context mContext;
    private MoPubInterstitial mInterstitial;

    public void onAdClicked(Ad ad) {
    }

    public void onError(Ad ad, AdError adError) {
    }

    public void onInterstitialClicked(MoPubInterstitial moPubInterstitial) {
    }

    public void onLoggingImpression(Ad ad) {
    }

    public AdHelper(Context context) {
        this.mContext = context;
        this.mInterstitial = new MoPubInterstitial((Activity) this.mContext, "2e146683a4354c879a956d6c4d4438eb");
        this.mInterstitial.setInterstitialAdListener(this);
    }

    public void LoadInterstitial() {
        this.mInterstitial.load();
        Bundle bundle = new Bundle();
        bundle.putString("AdNetwork", "Inetrstitial_Load_Rqst");
        FireBaseHelper.getInstance().LogEvent("Ad_Analytics", bundle);
    }

    public void ShowInterstitialAd() {
        if (this.mInterstitial.isReady()) {
            this.mInterstitial.show();
        } else if (this.facebookInterstitial_Loaded) {
            this.interstitialAd.show();
        }
    }

    public void setAdClosedListener(AdClosedListener adClosedListener2) {
        this.adClosedListener = adClosedListener2;
    }

    public boolean isInterstitialAvailable() {
        if (!this.mInterstitial.isReady() && !this.facebookInterstitial_Loaded) {
            return false;
        }
        return true;
    }

    public void onInterstitialLoaded(MoPubInterstitial moPubInterstitial) {
        Bundle bundle = new Bundle();
        bundle.putString("AdNetwork", "Inetrstitial_ad_loaded");
        FireBaseHelper.getInstance().LogEvent("Ad_Analytics", bundle);
    }

    public void onInterstitialFailed(MoPubInterstitial moPubInterstitial, MoPubErrorCode moPubErrorCode) {
        Bundle bundle = new Bundle();
        bundle.putString("AdNetwork", "Inetrstitial_ad_Failed");
        FireBaseHelper.getInstance().LogEvent("Ad_Analytics", bundle);
        this.interstitialAd = new InterstitialAd(this.mContext, "162007114469037_165570124112736");
        this.interstitialAd.setAdListener(this);
        this.interstitialAd.loadAd();
    }

    public void onInterstitialShown(MoPubInterstitial moPubInterstitial) {
        Bundle bundle = new Bundle();
        bundle.putString("AdNetwork", "Inetrstitial_ad_Shown");
        FireBaseHelper.getInstance().LogEvent("Ad_Analytics", bundle);
    }

    public void onInterstitialDismissed(MoPubInterstitial moPubInterstitial) {
        this.adClosedListener.OnAdClosedListener();
    }

    public MoPubInterstitial getmInterstitial() {
        return this.mInterstitial;
    }

    public void onInterstitialDisplayed(Ad ad) {
        this.facebookInterstitial_Loaded = false;
    }

    public void onInterstitialDismissed(Ad ad) {
        this.adClosedListener.OnAdClosedListener();
    }

    public void onAdLoaded(Ad ad) {
        this.facebookInterstitial_Loaded = true;
      //  Log.d(BuildConfig.NETWORK_NAME, "interstitial loaded");
    }
}

