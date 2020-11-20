package util;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;

import com.facebook.ads.InterstitialAdListener;
import com.facebook.ads.NativeAd;
import com.facebook.ads.NativeAdListener;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.formats.UnifiedNativeAd;

import com.mopub.mobileads.MoPubErrorCode;
import com.mopub.mobileads.MoPubInterstitial;
import com.mopub.nativeads.MoPubNative;
import com.mopub.nativeads.MoPubStaticNativeAdRenderer;
import com.mopub.nativeads.NativeErrorCode;
import com.mopub.nativeads.ViewBinder;
import com.teamvinay.sketch.R;

import helper.Constants;
import listeners.AdClosedListener;
import listeners.AdLoadListener;
import listeners.RewardAwardedListener;

public class AdUtil implements MoPubInterstitial.InterstitialAdListener, InterstitialAdListener {
    private static Context mContext;
    private static AdUtil sSoleInstance;
    Activity activity;
    private AdClosedListener adClosedListener;
    private AdLoadListener adLoadListener;
    AdLoader adLoader;
    /* access modifiers changed from: private */
    public boolean admob_ed_native_loaded = false;
    UnifiedNativeAd admob_nativeAd;
    private boolean allowAd = true;
    AdLoader.Builder builder;
    NativeAd ed_fb_ad;
    MoPubNative ed_mp_native;
    NativeAd editing_fb_native;
    com.mopub.nativeads.NativeAd editing_mopub_native;
    MoPubNative effectMopubNative;
    private boolean effect_admob_interstitial_loaded = false;
    UnifiedNativeAd effect_admob_native;
    NativeAd effect_fb_native;
    /* access modifiers changed from: private */
    public boolean effect_fb_native_loaded = false;
    private MoPubInterstitial effect_interstitial;
    private boolean effect_loaded = false;
    private boolean effect_mopub_interstitial_loaded = false;
    NativeAd ending_fb_native;
    /* access modifiers changed from: private */
    public boolean ending_fb_native_loaded = false;
    private MoPubInterstitial ending_interstitial;
    private boolean ending_interstitial_Loaded = false;
    private boolean export_ad_loaded;
    private MoPubInterstitial export_screen_interstitial;
    private MoPubInterstitial extra_interstitial;
    private boolean extra_interstitial_loaded = false;
    private boolean facebookInterstitial_Loade = false;
    /* access modifiers changed from: private */
    public boolean fb_ed_native_loaded = false;
    Handler handler = new Handler();
    /* access modifiers changed from: private */
    public boolean mopub_ed_native_loaded = false;
    com.mopub.nativeads.NativeAd mopub_native;
    int repeatTimes;
    private RewardAwardedListener rewardAwardedListener;
    private MoPubInterstitial save_interstitial;
    private boolean save_loaded = false;
    private boolean startUp_interstitial_Loaded = false;
    private MoPubInterstitial start_up_interstitial;

    public void onAdClicked(Ad ad) {
    }

    public void onAdLoaded(Ad ad) {
    }

    public void onError(Ad ad, AdError adError) {
    }

    public void onInterstitialClicked(MoPubInterstitial moPubInterstitial) {
    }

    public void onInterstitialDisplayed(Ad ad) {
    }

    public void onLoggingImpression(Ad ad) {
    }

    public void setRewardAwardedListener(RewardAwardedListener rewardAwardedListener2) {
        this.rewardAwardedListener = rewardAwardedListener2;
    }

    public NativeAd getEffect_fb_native() {
        return this.effect_fb_native;
    }

    public void setEffect_fb_native(NativeAd nativeAd) {
        this.effect_fb_native = nativeAd;
    }

    public NativeAd getEnding_fb_native() {
        return this.ending_fb_native;
    }

    public void setEnding_fb_native(NativeAd nativeAd) {
        this.ending_fb_native = nativeAd;
    }

    public void setAdLoadListener(AdLoadListener adLoadListener2) {
        this.adLoadListener = adLoadListener2;
    }

    public static AdUtil getInstance() {
        if (sSoleInstance == null) {
            sSoleInstance = new AdUtil();
        }
        return sSoleInstance;
    }

    private AdUtil() {
    }

    public MoPubInterstitial createInterstitialAd(String str) {
        this.activity = (Activity) mContext;
        MoPubInterstitial moPubInterstitial = new MoPubInterstitial(this.activity, str);
        moPubInterstitial.setInterstitialAdListener(this);
        return moPubInterstitial;
    }

    public void initializeAd(Context context) {
        mContext = context;
        this.start_up_interstitial = createInterstitialAd("2e146683a4354c879a956d6c4d4438eb");
        this.ending_interstitial = createInterstitialAd("635f507ed4404380889e4b3816ecccdc");
        this.export_screen_interstitial = createInterstitialAd("d515af314e0f4ed8b6419613b42394d1");
        this.effect_interstitial = createInterstitialAd("5d9ae288ade74f7b9c7e8e5d76b5f47d");
        if (!Constants.REMOVE_ADS) {
           // LoadStartUpInterstitial();
            LoadEndingInterstitial(context);
          //  LoadExportInterstitial();
          //  LoadSaveInterstitial();
        }
    }

   /* public void initializeEditingNativeAd(Context context) {
        MoPubStaticNativeAdRenderer moPubStaticNativeAdRenderer = new MoPubStaticNativeAdRenderer(new ViewBinder.Builder(R.layout.main_screen_mopub_native_ad_layout).titleId(R.id.native_ad_title).mainImageId(C3674R.C3676id.native_ad_main_image).iconImageId(C3674R.id.native_ad_icon_image).callToActionId(R.id.native_ad_call_to_action).privacyInformationIconImageId(R.id.native_ad_privacy_information_icon_image).build());
        C51611 r1 = new MoPubNative.MoPubNativeNetworkListener() {
            public void onNativeLoad(com.mopub.nativeads.NativeAd nativeAd) {
                Log.d("EditingNativeAd", "Full Screen mopub Native ad has loaded.");
                AdUtil.this.editing_mopub_native = nativeAd;
                boolean unused = AdUtil.this.mopub_ed_native_loaded = true;
            }

            public void onNativeFail(NativeErrorCode nativeErrorCode) {
                Log.d("EditingNativeAd", "Full Screen mopub Native ad has not loaded.");
                AdUtil.this.adLoader.loadAd(new AdRequest.Builder().build());
            }
        };
        this.builder = new AdLoader.Builder(context, "ca-app-pub-4195495625122728/8441418062");
        this.builder.forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
            public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
                AdUtil.this.admob_nativeAd = unifiedNativeAd;
            }
        });
        this.adLoader = this.builder.withAdListener(new AdListener() {
            public void onAdFailedToLoad(int i) {
            }

            public void onAdLoaded() {
                super.onAdLoaded();
                boolean unused = AdUtil.this.admob_ed_native_loaded = true;
                Log.d("EditingNativeAd", "Full Screen admob Native ad has  loaded.");
            }

            public void onAdOpened() {
                super.onAdOpened();
                Log.d("AdmobNative", "Open");
            }

            public void onAdImpression() {
                super.onAdImpression();
                Log.d("AdmobNative", "Impression");
            }
        }).build();
        this.editing_fb_native = new NativeAd(context, "162007114469037_303150763688004");
        this.ed_mp_native = new MoPubNative(context, "7a4622f489194177a916796a3dd8fd3e", r1);
        this.ed_mp_native.registerAdRenderer(moPubStaticNativeAdRenderer);
        this.editing_fb_native.setAdListener(new NativeAdListener() {
            public void onAdClicked(Ad ad) {
            }

            public void onError(Ad ad, AdError adError) {
            }

            public void onLoggingImpression(Ad ad) {
            }

            public void onMediaDownloaded(Ad ad) {
            }

            public void onAdLoaded(Ad ad) {
                if (AdUtil.this.editing_fb_native != null && AdUtil.this.editing_fb_native == ad) {
                    AdUtil.this.ed_fb_ad = AdUtil.this.editing_fb_native;
                    boolean unused = AdUtil.this.fb_ed_native_loaded = true;
                    Log.d("facebooknative", "editing Native ad loaded.");
                    Bundle bundle = new Bundle();
                    bundle.putString("AdNetwork", "Editing_FB_Native_Loaded");
                    FireBaseHelper.getInstance().LogEvent("Ad_Analytics", bundle);
                }
            }
        });
        this.editing_fb_native.loadAd();
    }

    public void initializeEffectNativeInterstitial(Context context) {
        this.effect_fb_native = new NativeAd(context, "162007114469037_303150763688004");
        this.effect_fb_native.setAdListener(new NativeAdListener() {
            public void onAdClicked(Ad ad) {
            }

            public void onError(Ad ad, AdError adError) {
            }

            public void onMediaDownloaded(Ad ad) {
            }

            public void onAdLoaded(Ad ad) {
                if (AdUtil.this.effect_fb_native != null && AdUtil.this.effect_fb_native == ad) {
                    Log.d("facebooknative", "effect Native ad loaded.");
                    boolean unused = AdUtil.this.effect_fb_native_loaded = true;
                }
            }

            public void onLoggingImpression(Ad ad) {
                Log.d(BuildConfig.NETWORK_NAME, "Native ad shown.");
            }
        });
        this.effect_fb_native.loadAd();
    }

    public boolean isEffectNativeLoaded() {
        return this.effect_fb_native_loaded;
    }

    public boolean isEditingNativeLoaded() {
        if (!this.fb_ed_native_loaded && !this.mopub_ed_native_loaded && !this.admob_ed_native_loaded) {
            return false;
        }
        return true;
    }

    public boolean isFBEditingNativeLoaded() {
        return this.fb_ed_native_loaded && this.ed_fb_ad != null;
    }

    public NativeAd getEditing_fb_native() {
        return this.ed_fb_ad;
    }

    public boolean isMopubEditingNativeLoaded() {
        return this.mopub_ed_native_loaded && this.editing_mopub_native != null;
    }

    public com.mopub.nativeads.NativeAd getEditing_mopub_native() {
        return this.editing_mopub_native;
    }

    public boolean isAdmobEditingNativeLoaded() {
        return this.admob_ed_native_loaded && this.admob_nativeAd != null;
    }

    public UnifiedNativeAd getEditing_admob_native() {
        return this.admob_nativeAd;
    }

    public MoPubInterstitial getStartUpAd() {
        if (this.start_up_interstitial != null) {
            return this.start_up_interstitial;
        }
        this.start_up_interstitial = createInterstitialAd("2e146683a4354c879a956d6c4d4438eb");
        return this.start_up_interstitial;
    }

    public MoPubInterstitial getExportSCreenAd() {
        if (this.export_screen_interstitial != null) {
            return this.export_screen_interstitial;
        }
        this.export_screen_interstitial = createInterstitialAd("d515af314e0f4ed8b6419613b42394d1");
        return this.export_screen_interstitial;
    }

    public MoPubInterstitial getEndingAd() {
        if (this.ending_interstitial != null) {
            return this.ending_interstitial;
        }
        this.ending_interstitial = createInterstitialAd("635f507ed4404380889e4b3816ecccdc");
        return this.ending_interstitial;
    }

    public MoPubInterstitial getSaveInterstitial() {
        if (this.save_interstitial != null) {
            return this.save_interstitial;
        }
        this.save_interstitial = createInterstitialAd("145c8fa5f0044ec69db5c6229b7467e9");
        return this.save_interstitial;
    }

    public MoPubInterstitial getExtraInterstitial() {
        if (this.extra_interstitial != null) {
            return this.extra_interstitial;
        }
        this.extra_interstitial = createInterstitialAd("dab2e8314848437ebe1b85aead8a3ff1");
        return this.extra_interstitial;
    }

    public MoPubInterstitial getEffectInterstitial() {
        if (this.effect_interstitial != null) {
            return this.effect_interstitial;
        }
        this.effect_interstitial = createInterstitialAd("5d9ae288ade74f7b9c7e8e5d76b5f47d");
        return this.effect_interstitial;
    }

    public void LoadStartUpInterstitial() {
        if (!Constants.REMOVE_ADS) {
            getStartUpAd().load();
            Bundle bundle = new Bundle();
            bundle.putString("AdNetwork", "Inetrstitial_Load_Rqst");
            FireBaseHelper.getInstance().LogEvent("Ad_Analytics", bundle);
            Log.d("face", "loading");
        }
    }

    public void LoadExportInterstitial() {
        if (!Constants.REMOVE_ADS) {
            getExportSCreenAd().load();
        }
    }

    public void LoadSaveInterstitial() {
        if (!Constants.REMOVE_ADS) {
            getSaveInterstitial().load();
        }
    }

    public void LoadExtraInterstitial() {
        if (!Constants.REMOVE_ADS) {
            getExtraInterstitial().load();
        }
    }

    public void LoadEffectInterstitial() {
        if (!Constants.REMOVE_ADS) {
            getEffectInterstitial().load();
        }
    }*/

    public void LoadEndingInterstitial(Context context) {
        this.ending_fb_native = new NativeAd(context, "162007114469037_300983013904779");
        this.ending_fb_native.setAdListener(new NativeAdListener() {
            public void onAdClicked(Ad ad) {
            }

            public void onError(Ad ad, AdError adError) {
            }

            public void onMediaDownloaded(Ad ad) {
            }

            public void onAdLoaded(Ad ad) {
                if (AdUtil.this.ending_fb_native != null && AdUtil.this.ending_fb_native == ad) {
                    Bundle bundle = new Bundle();
                    bundle.putString("AdNetwork", "Ending_FB_Native_Loaded");
                    FireBaseHelper.getInstance().LogEvent("Ad_Analytics", bundle);
                    Log.d("facebooknative", "ending Native ad loaded.");
                    boolean unused = AdUtil.this.ending_fb_native_loaded = true;
                }
            }

            public void onLoggingImpression(Ad ad) {
               // Log.d(BuildConfig.NETWORK_NAME, "Native ad shown.");
            }
        });
        this.ending_fb_native.loadAd();
      //  getEndingAd().load();
        Bundle bundle = new Bundle();
        bundle.putString("AdNetwork", "Inetrstitial_Load_Rqst");
        FireBaseHelper.getInstance().LogEvent("Ad_Analytics", bundle);
        Log.d("face", "loading");
    }

    public void setAdClosedListener(AdClosedListener adClosedListener2) {
        if (adClosedListener2 != null) {
            this.adClosedListener = adClosedListener2;
        }
    }

    public boolean isStartUpAdAvailable() {
        return this.startUp_interstitial_Loaded;
    }

    public boolean isEndingAdAvailable() {
        return this.ending_interstitial_Loaded || this.ending_fb_native_loaded;
    }

    public boolean isEndingNativeLoaded() {
        return this.ending_fb_native_loaded;
    }

    public boolean isExportAdAvailable() {
        return this.export_ad_loaded;
    }

    public boolean isExtraInterstitialAvailable() {
        return this.extra_interstitial_loaded;
    }

    public boolean isSaveInterstitialAvailable() {
        return this.save_loaded;
    }

    public void ShowStartUpInterstitial() {
        if (this.startUp_interstitial_Loaded) {
            this.start_up_interstitial.show();
            Log.d("InterstitialAds", "show interstitial");
        }
    }

    public void ShowEffectInterstitial() {
        if (this.effect_loaded) {
            this.effect_interstitial.show();
        }
    }

    public void ShowEndingInterstitial() {
        if (this.ending_interstitial_Loaded) {
            this.ending_interstitial.show();
        }
    }

    public boolean isEffectInterstitialLoaded() {
        return this.effect_loaded;
    }

    public void ShowExportInterstitial() {
        if (this.export_ad_loaded) {
            this.export_screen_interstitial.show();
        }
    }

    public void ShowExtraInterstitial() {
        if (this.extra_interstitial_loaded) {
            this.extra_interstitial.show();
        }
    }

    public void ShowSaveInterstitial() {
        if (this.save_loaded) {
            this.save_interstitial.show();
        }
    }

    public void onInterstitialDismissed(Ad ad) {
        if (this.adClosedListener != null) {
            this.adClosedListener.OnAdClosedListener();
        }
    }

    public void onInterstitialLoaded(MoPubInterstitial moPubInterstitial) {
        if (moPubInterstitial == this.start_up_interstitial) {
            this.startUp_interstitial_Loaded = true;
            Bundle bundle = new Bundle();
            bundle.putString("AdNetwork", "StartUp_ad_loaded_");
            FireBaseHelper.getInstance().LogEvent("Ad_Analytics", bundle);
            Log.d("InterstitialAds", " loaded");
        } else if (moPubInterstitial == this.ending_interstitial) {
            this.ending_interstitial_Loaded = true;
            Bundle bundle2 = new Bundle();
            bundle2.putString("AdNetwork", "Ending_ad_loaded_");
            FireBaseHelper.getInstance().LogEvent("Ad_Analytics", bundle2);
        } else if (moPubInterstitial == this.export_screen_interstitial) {
            this.export_ad_loaded = true;
        } else if (moPubInterstitial == this.save_interstitial) {
            this.save_loaded = true;
            Bundle bundle3 = new Bundle();
            bundle3.putString("AdNetwork", "Save_ad_loaded_");
            FireBaseHelper.getInstance().LogEvent("Ad_Analytics", bundle3);
        } else if (moPubInterstitial == this.effect_interstitial) {
            this.effect_loaded = true;
            Bundle bundle4 = new Bundle();
            bundle4.putString("AdNetwork", "Effect_ad_loaded_");
            FireBaseHelper.getInstance().LogEvent("Ad_Analytics", bundle4);
        } else if (moPubInterstitial == this.extra_interstitial) {
            this.extra_interstitial_loaded = true;
            if (this.adLoadListener != null) {
                this.adLoadListener.OnAdLoaded();
            }
        }
        Log.d("InterstitialAds", " loaded");
    }

    public void onInterstitialFailed(MoPubInterstitial moPubInterstitial, MoPubErrorCode moPubErrorCode) {
        if (moPubInterstitial == this.start_up_interstitial) {
            this.startUp_interstitial_Loaded = false;
            Bundle bundle = new Bundle();
            bundle.putString("AdNetwork", "StartUp_ad_Failed");
            FireBaseHelper.getInstance().LogEvent("Ad_Analytics", bundle);
        } else if (moPubInterstitial == this.ending_interstitial) {
            this.ending_interstitial_Loaded = false;
            Bundle bundle2 = new Bundle();
            bundle2.putString("AdNetwork", "Ending_ad_Failed");
            FireBaseHelper.getInstance().LogEvent("Ad_Analytics", bundle2);
        } else if (moPubInterstitial == this.export_screen_interstitial) {
            this.export_ad_loaded = false;
        } else if (moPubInterstitial == this.effect_interstitial) {
            this.effect_loaded = false;
            Bundle bundle3 = new Bundle();
            bundle3.putString("AdNetwork", "Effect_ad_Failed");
            FireBaseHelper.getInstance().LogEvent("Ad_Analytics", bundle3);
        }
        Log.d("InterstitialAds", " failed");
    }

    public void onInterstitialShown(MoPubInterstitial moPubInterstitial) {
        if (moPubInterstitial == this.start_up_interstitial) {
            this.startUp_interstitial_Loaded = false;
            Bundle bundle = new Bundle();
            bundle.putString("AdNetwork", "StartUp_ad_Shown");
            FireBaseHelper.getInstance().LogEvent("Ad_Analytics", bundle);
        } else if (moPubInterstitial == this.ending_interstitial) {
            this.ending_interstitial_Loaded = false;
            Bundle bundle2 = new Bundle();
            bundle2.putString("AdNetwork", "Ending_ad_Shown");
            FireBaseHelper.getInstance().LogEvent("Ad_Analytics", bundle2);
        } else if (moPubInterstitial == this.export_screen_interstitial) {
            this.export_ad_loaded = false;
        } else if (moPubInterstitial == this.extra_interstitial) {
            this.extra_interstitial_loaded = false;
            Bundle bundle3 = new Bundle();
            bundle3.putString("AdNetwork", "Extra_ad_Shown");
            FireBaseHelper.getInstance().LogEvent("Ad_Analytics", bundle3);
        } else if (moPubInterstitial == this.save_interstitial) {
            this.save_loaded = false;
            Bundle bundle4 = new Bundle();
            bundle4.putString("AdNetwork", "Save_ad_Shown");
            FireBaseHelper.getInstance().LogEvent("Ad_Analytics", bundle4);
        } else if (moPubInterstitial == this.effect_interstitial) {
            this.effect_loaded = false;
            Bundle bundle5 = new Bundle();
            bundle5.putString("AdNetwork", "Effect_ad_Shown");
            FireBaseHelper.getInstance().LogEvent("Ad_Analytics", bundle5);
        }
    }

    public void onInterstitialDismissed(MoPubInterstitial moPubInterstitial) {
        if (!(this.adClosedListener == null || moPubInterstitial == this.extra_interstitial || moPubInterstitial == this.save_interstitial)) {
            this.adClosedListener.OnAdClosedListener();
        }
        if (moPubInterstitial == this.extra_interstitial) {
            loadAgain();
        }
        if (moPubInterstitial == this.save_interstitial) {
            Constants.SAVE_INTERSTITIAL_REPEAT--;
            if (Constants.SAVE_INTERSTITIAL_REPEAT > 0) {
              //  LoadSaveInterstitial();
            }
        }
        if (moPubInterstitial == this.effect_interstitial) {
            Constants.EFFECT_INTERSTITIAL_REPEAT--;
            if (Constants.EFFECT_INTERSTITIAL_REPEAT > 0) {
              //  LoadEffectInterstitial();
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void loadAgain() {
        if (this.handler != null) {
            this.handler.postDelayed(new Runnable() {
                public void run() {
            //        AdUtil.this.LoadExtraInterstitial();
                }
            }, 1000);
        }
    }

    public void reset() {
        mContext = null;
        sSoleInstance = null;
        if (this.start_up_interstitial != null) {
            this.start_up_interstitial.destroy();
            this.start_up_interstitial = null;
        }
        if (this.ending_interstitial != null) {
            this.ending_interstitial.destroy();
            this.ending_interstitial = null;
        }
        if (this.extra_interstitial != null) {
            this.extra_interstitial.destroy();
            this.extra_interstitial = null;
        }
        if (this.save_interstitial != null) {
            this.save_interstitial.destroy();
            this.save_interstitial = null;
        }
        if (this.effect_interstitial != null) {
            this.effect_interstitial.destroy();
            this.effect_interstitial = null;
        }
        if (this.ending_fb_native != null) {
            this.ending_fb_native.destroy();
            this.ending_fb_native = null;
        }
        if (this.editing_fb_native != null) {
            this.editing_fb_native.destroy();
            this.editing_fb_native = null;
        }
        if (this.ed_fb_ad != null) {
            this.ed_fb_ad.destroy();
            this.ed_fb_ad = null;
        }
    }
}
