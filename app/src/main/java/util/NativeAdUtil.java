package util;

import android.content.Context;
import android.util.Log;
import com.facebook.ads.AdError;
import com.facebook.ads.AdSettings;
import com.facebook.ads.Ad;
import com.facebook.ads.NativeAd;
import com.facebook.ads.NativeAdListener;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.VideoController;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.mopub.common.logging.MoPubLog;
import com.mopub.nativeads.MoPubNative;
import com.mopub.nativeads.MoPubStaticNativeAdRenderer;
import com.mopub.nativeads.NativeErrorCode;
import com.mopub.nativeads.ViewBinder;
import com.teamvinay.sketch.R;

import helper.Constants;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NativeAdUtil {
    public static int NUM_OF_ADS = 8;
    private static NativeAdUtil sSoleInstance;
    AdLoader adLoader;
    AdLoader adLoader2;
    AdLoader adLoader3;
    AdLoader adLoader4;
    AdLoader adLoader5;
    /* access modifiers changed from: private */
    public UnifiedNativeAd admob_nativeAd;
    Boolean admob_native_loaded = false;
    boolean ads_loaded = true;
    private List<Object> art_native_ads = new ArrayList();
    AdLoader.Builder builder;
    AdLoader.Builder builder2;
    AdLoader.Builder builder3;
    AdLoader.Builder builder4;
    AdLoader.Builder builder5;
    /* access modifiers changed from: private */
    public List<Object> editor_native_ads = new ArrayList();
    /* access modifiers changed from: private */
    public List<Object> effect_native_ads = new ArrayList();
    /* access modifiers changed from: private */
    public List<Object> export_native_ads = new ArrayList();
    Boolean facebook_native_loaded = false;
    NativeAd fb_native_ad;
    /* access modifiers changed from: private */
    public List<Object> filter_native_ads = new ArrayList();
    UnifiedNativeAd loadingAdmobNative;
    public Context mContext;
    /* access modifiers changed from: private */
    public MoPubNative moPubNative;
    private MoPubNative.MoPubNativeNetworkListener moPubNativeNetworkListener;
    com.mopub.nativeads.NativeAd mopubNativeAd;
    /* access modifiers changed from: private */
    public com.mopub.nativeads.NativeAd mopub_nativeAd;
    Boolean mopub_native_loaded = false;
    VideoController videoController;

    public com.mopub.nativeads.NativeAd getMopub_nativeAd() {
        return this.mopub_nativeAd;
    }

    public void setMopub_nativeAd(com.mopub.nativeads.NativeAd nativeAd) {
        this.mopub_nativeAd = nativeAd;
    }

    public UnifiedNativeAd getAdmob_nativeAd() {
        return this.admob_nativeAd;
    }

    public void setAdmob_nativeAd(UnifiedNativeAd unifiedNativeAd) {
        this.admob_nativeAd = unifiedNativeAd;
    }

    public NativeAd getFb_native_ad() {
        return this.fb_native_ad;
    }

    public UnifiedNativeAd getLoadingAdmobNative() {
        return this.loadingAdmobNative;
    }

    public com.mopub.nativeads.NativeAd getMopubNativeAd() {
        return this.mopubNativeAd;
    }

    public Boolean getAdmob_native_loaded() {
        return this.admob_native_loaded;
    }

    public Boolean getMopub_native_loaded() {
        return this.mopub_native_loaded;
    }

    public Boolean getFacebook_native_loaded() {
        return this.facebook_native_loaded;
    }

    public boolean isAds_loaded() {
        return this.ads_loaded;
    }

    public void setAds_loaded(boolean z) {
        this.ads_loaded = z;
    }

    public List<Object> getExport_native_ads() {
        return this.export_native_ads;
    }

    public List<Object> getEditor_native_ads() {
        return this.editor_native_ads;
    }

    public List<Object> getFilter_native_ads() {
        return this.filter_native_ads;
    }

    public List<Object> getArt_native_ads() {
        return this.art_native_ads;
    }

    public static NativeAdUtil getInstance() {
        if (sSoleInstance == null) {
            sSoleInstance = new NativeAdUtil();
        }
        return sSoleInstance;
    }

    public void initializeAds(Context context) {
        this.mContext = context;
        if (!Constants.REMOVE_ADS && Constants.BUTTON_ADS) {
            initializeEffectNativeAds(context);
            initializeFilterNativeAds(context);
            initializeEditorNativeAds(context);
        }
    }

    public void iniitializeExportBottomAds(Context context) {
        this.builder = new AdLoader.Builder(context, "ca-app-pub-4195495625122728/2301275229");
        this.builder.forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
            public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
                UnifiedNativeAd unused = NativeAdUtil.this.admob_nativeAd = unifiedNativeAd;
            }
        });
        this.adLoader = this.builder.withAdListener(new AdListener() {
            public void onAdFailedToLoad(int i) {
                Log.d("PhotoActivity native", "admob Banner not Loaded");
                if (!Constants.REMOVE_ADS && Constants.admob_first) {
                    NativeAdUtil.this.moPubNative.makeRequest();
                }
            }

            public void onAdLoaded() {
                super.onAdLoaded();
                Log.d("PhotoActivity native ", " admob Banner Loaded");
            }
        }).build();
        MoPubStaticNativeAdRenderer moPubStaticNativeAdRenderer = new MoPubStaticNativeAdRenderer(new ViewBinder.Builder(R.layout.native_ad_banner).titleId(R.id.native_ad_title).iconImageId(R.id.native_ad_icon).callToActionId(R.id.native_ad_call_to_action).textId(R.id.native_ad_body).privacyInformationIconImageId(R.id.native_ad_privacy_information_icon_image).build());
        this.moPubNativeNetworkListener = new MoPubNative.MoPubNativeNetworkListener() {
            public void onNativeLoad(com.mopub.nativeads.NativeAd nativeAd) {
                Log.d("PhotoActivity native", "Banner mopub Native ad has loaded.");
                com.mopub.nativeads.NativeAd unused = NativeAdUtil.this.mopub_nativeAd = nativeAd;
            }

            public void onNativeFail(NativeErrorCode nativeErrorCode) {
                Log.d("PhotoActivity native", "Banner mopu Native ad has not loaded.");
                if (!Constants.REMOVE_ADS && !Constants.admob_first) {
                    NativeAdUtil.this.adLoader.loadAd(new AdRequest.Builder().build());
                }
            }
        };
        this.moPubNative = new MoPubNative(context, "d86b5a12d15944b9b7202fd0c80c9167", this.moPubNativeNetworkListener);
        this.moPubNative.registerAdRenderer(moPubStaticNativeAdRenderer);
        if (Constants.REMOVE_ADS) {
            return;
        }
        if (Constants.admob_first) {
            this.adLoader.loadAd(new AdRequest.Builder().build());
        } else {
            this.moPubNative.makeRequest();
        }
    }

    public void initializeEditorNativeAds(Context context) {
        this.builder5 = new AdLoader.Builder(context, "ca-app-pub-4195495625122728/8243973213");
        this.builder5.forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
            public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
                NativeAdUtil.this.editor_native_ads.add(unifiedNativeAd);
                Collections.shuffle(NativeAdUtil.this.editor_native_ads);
            }
        });
        this.adLoader5 = this.builder5.withAdListener(new AdListener() {
            public void onAdFailedToLoad(int i) {
                Log.d("effectLoaded", "Full Screen admob Native ad has not loaded.");
                Log.d("ExportAds", "admob Native ad has not loaded.");
            }

            public void onAdLoaded() {
                super.onAdLoaded();
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
        this.adLoader5.loadAds(new AdRequest.Builder().build(), 5);
    }

    public void initializeExportNativeAds(Context context) {
        this.builder4 = new AdLoader.Builder(context, "ca-app-pub-4195495625122728/8056657021");
        this.builder4.forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
            public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
                NativeAdUtil.this.export_native_ads.add(unifiedNativeAd);
                Collections.shuffle(NativeAdUtil.this.export_native_ads);
            }
        });
        this.adLoader4 = this.builder4.withAdListener(new AdListener() {
            public void onAdFailedToLoad(int i) {
                Log.d("effectLoaded", "Full Screen admob Native ad has not loaded.");
                Log.d("ExportAds", "admob Native ad has not loaded.");
            }

            public void onAdLoaded() {
                super.onAdLoaded();
                Log.d("ExportActivity", "admob Native ad has  loaded.");
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
        if (Constants.EXPORT_BUTTON_ADS_COUNT == 1) {
            Constants.EXPORT_AD_BUTTON_SPACE = 2;
            this.adLoader4.loadAd(new AdRequest.Builder().build());
        } else if (Constants.EXPORT_BUTTON_ADS_COUNT == 2) {
            Constants.EXPORT_AD_BUTTON_SPACE = 2;
            this.adLoader4.loadAds(new AdRequest.Builder().build(), 2);
        } else if (Constants.EXPORT_BUTTON_ADS_COUNT == 3) {
            Constants.EXPORT_AD_BUTTON_SPACE = 1;
            this.adLoader4.loadAds(new AdRequest.Builder().build(), 3);
        }
    }

    private void initializeEffectNativeAds(Context context) {
        this.builder = new AdLoader.Builder(context, "ca-app-pub-4195495625122728/8731232103");
        MoPubStaticNativeAdRenderer moPubStaticNativeAdRenderer = new MoPubStaticNativeAdRenderer(new ViewBinder.Builder(R.layout.native_ad_button_mopub).titleId(R.id.native_text).iconImageId(R.id.native_image).privacyInformationIconImageId(R.id.native_ad_privacy_information_icon_image).build());
        final MoPubNative moPubNative2 = new MoPubNative(context, "7a4622f489194177a916796a3dd8fd3e", new MoPubNative.MoPubNativeNetworkListener() {
            public void onNativeLoad(com.mopub.nativeads.NativeAd nativeAd) {
                Log.d(MoPubLog.LOGTAG, "Banner mopub Native ad has loaded.");
                int size = Constants.EFFECT_BUTTON_ADS - NativeAdUtil.this.effect_native_ads.size();
                for (int i = 0; i < size; i++) {
                    NativeAdUtil.this.effect_native_ads.add(nativeAd);
                    Log.d("aeffectnative", " mopub  loaded.");
                }
                Collections.shuffle(NativeAdUtil.this.effect_native_ads);
                NativeAdUtil.this.ads_loaded = true;
            }

            public void onNativeFail(NativeErrorCode nativeErrorCode) {
                Log.d(MoPubLog.LOGTAG, "Banner mopub Native ad has not loaded.");
            }
        });
        moPubNative2.registerAdRenderer(moPubStaticNativeAdRenderer);
        this.builder.forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
            public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
                NativeAdUtil.this.effect_native_ads.add(unifiedNativeAd);
                Log.d("effectnative count", String.valueOf(NativeAdUtil.this.effect_native_ads.size()));
                Collections.shuffle(NativeAdUtil.this.effect_native_ads);
            }
        });
        this.adLoader = this.builder.withAdListener(new AdListener() {
            public void onAdFailedToLoad(int i) {
                Log.d("effectLoaded", "Full Screen admob Native ad has not loaded.");
            }

            public void onAdLoaded() {
                super.onAdLoaded();
                NativeAdUtil.this.admob_native_loaded = true;
                NativeAdUtil.this.ads_loaded = true;
                if (NativeAdUtil.this.effect_native_ads.size() > 0 && NativeAdUtil.this.effect_native_ads.size() < Constants.EFFECT_BUTTON_ADS) {
                    moPubNative2.makeRequest();
                }
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
        this.adLoader.loadAds(new AdRequest.Builder().build(), Constants.EFFECT_BUTTON_ADS);
    }

    private void initializeFilterNativeAds(Context context) {
        this.builder2 = new AdLoader.Builder(context, "ca-app-pub-4195495625122728/6878920659");
        MoPubStaticNativeAdRenderer moPubStaticNativeAdRenderer = new MoPubStaticNativeAdRenderer(new ViewBinder.Builder(R.layout.native_ad_button_mopub).titleId(R.id.native_text).iconImageId(R.id.native_image).privacyInformationIconImageId(R.id.native_ad_privacy_information_icon_image).build());
        final MoPubNative moPubNative2 = new MoPubNative(context, "8732150d8aac4d7ba6ad6be1a538722e", new MoPubNative.MoPubNativeNetworkListener() {
            public void onNativeLoad(com.mopub.nativeads.NativeAd nativeAd) {
                Log.d(MoPubLog.LOGTAG, "Banner mopub Native ad has loaded.");
                int size = Constants.FILTER_BUTTON_ADS - NativeAdUtil.this.filter_native_ads.size();
                for (int i = 0; i < size; i++) {
                    NativeAdUtil.this.filter_native_ads.add(nativeAd);
                }
                Collections.shuffle(NativeAdUtil.this.filter_native_ads);
                NativeAdUtil.this.ads_loaded = true;
            }

            public void onNativeFail(NativeErrorCode nativeErrorCode) {
                Log.d(MoPubLog.LOGTAG, "Banner mopub Native ad has not loaded.");
            }
        });
        moPubNative2.registerAdRenderer(moPubStaticNativeAdRenderer);
        this.builder2.forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
            public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
                NativeAdUtil.this.filter_native_ads.add(unifiedNativeAd);
                Collections.shuffle(NativeAdUtil.this.filter_native_ads);
            }
        });
        this.adLoader2 = this.builder2.withAdListener(new AdListener() {
            public void onAdFailedToLoad(int i) {
                Log.d("effectnative", "Full Screen admob Native ad has not loaded.");
            }

            public void onAdLoaded() {
                super.onAdLoaded();
                NativeAdUtil.this.admob_native_loaded = true;
                NativeAdUtil.this.ads_loaded = true;
                if (NativeAdUtil.this.filter_native_ads.size() > 0 && NativeAdUtil.this.filter_native_ads.size() < Constants.FILTER_BUTTON_ADS) {
                    moPubNative2.makeRequest();
                }
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
        this.adLoader2.loadAds(new AdRequest.Builder().build(), Constants.FILTER_BUTTON_ADS);
    }

    public List<Object> getEffectNativeAds() {
        return this.effect_native_ads;
    }

    public void initializeEffectLoadingAd() {
        AdSettings.addTestDevice("88daaeef-2567-4e23-9335-045ea380ea2f");
        this.fb_native_ad.setAdListener(new NativeAdListener() {
            public void onAdClicked(Ad ad) {
            }

            public void onError(Ad ad, AdError adError) {
            }

            public void onLoggingImpression(Ad ad) {
            }

            public void onMediaDownloaded(Ad ad) {
            }

            public void onAdLoaded(Ad ad) {
                NativeAdUtil.this.facebook_native_loaded = true;
             //   Log.d("NativeLoad", String.valueOf(NativeAdUtil.this.fb_native_ad.getAdCreativeType()));
            }
        });
        this.fb_native_ad.loadAd();
    }

   /* public NativeAd.AdCreativeType getAdCreativeType() {
        return this.fb_native_ad.getAdCreativeType();
    }*/
}
