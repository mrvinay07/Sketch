package ui;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdIconView;


import com.facebook.ads.NativeAd;

import com.facebook.ads.NativeAdListener;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.formats.MediaView;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAdView;

import com.teamvinay.sketch.MainActivity;
import com.mopub.common.MoPub;
import com.mopub.common.logging.MoPubLog;

import com.mopub.nativeads.MoPubNative;
import com.mopub.nativeads.MoPubStaticNativeAdRenderer;
import com.mopub.nativeads.NativeErrorCode;
import com.mopub.nativeads.ViewBinder;
import com.teamvinay.sketch.R;

import helper.Constants;
import helper.DrawerItemsAdapter;
import helper.ViewElements;
import java.util.ArrayList;
import java.util.List;
import listeners.CustomScreenShowListener;
import listeners.RemoveAdsListener;
import listeners.SplashScreenGoneListener;
import org.opencv.android.OpenCVLoader;
import util.FireBaseHelper;
import util.SharedPreferencesManager;

/* renamed from: ui.MainFragment */
public class MainFragment extends Fragment implements SplashScreenGoneListener, View.OnClickListener, RemoveAdsListener, CustomScreenShowListener {
    private static final String ADMOB_AD_UNIT_ID = "ca-app-pub-4195495625122728/4049138684";
    AdLoader adLoader;
    RelativeLayout adView;
    private Button ad_button;
    /* access modifiers changed from: private */
    public UnifiedNativeAd admob_nativeAd = null;
    private boolean adnative = false;
    AdLoader.Builder builder;
    private Button cameraButton;
    /* access modifiers changed from: private */
    public boolean fbLoaded = false;
    private Button fbShareButton;
    /* access modifiers changed from: private */
    public boolean fb_load_called = false;
    @Nullable
    NativeAd fbnativeAd;
    private boolean freshStart = true;
    private Button galleryButton;
    private Button likeUsButton;
    private Context mcontext;
    private List<ViewElements> menuItems = new ArrayList();
    private DrawerItemsAdapter menuItemsAdapter;
    private RecyclerView.LayoutManager menuLayoutManager;
    private RecyclerView menuRecyclerView;
    private MoPubNative moPubNative;
    private MoPubNative.MoPubNativeNetworkListener moPubNativeNetworkListener;
    /* access modifiers changed from: private */
    public com.mopub.nativeads.NativeAd mopubNativeAd = null;
    private Button moreAppsButton;
    private boolean mpnative = false;
    @Nullable
    NativeAd nativeAd;
    RelativeLayout nativeAdContainer;

    private Button purchaseButton;
    private TextView purchaseButtonText;
    private Button rateButton;
    private TextView removeAdsText;
    /* access modifiers changed from: private */
    public ImageView screen_image;
    View view;

    public void onAttach(Context context) {
        super.onAttach(context);
        this.mcontext = context;
    }

    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
    }

    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        this.view = layoutInflater.inflate(R.layout.main_activity, viewGroup, false);
       // this.nativeAdLayout = (NativeAdLayout) this.view.findViewById(C3674R.C3676id.native_ad_container);
        this.nativeAdContainer = (RelativeLayout) this.view.findViewById(R.id.container);
        this.screen_image = (ImageView) this.view.findViewById(R.id.main_screen_image);
        this.galleryButton = (Button) this.view.findViewById(R.id.gallery_button);
        this.cameraButton = (Button) this.view.findViewById(R.id.camera_button);
        this.moreAppsButton = (Button) this.view.findViewById(R.id.more_apps_button);
        this.rateButton = (Button) this.view.findViewById(R.id.rate_us_button);
        this.likeUsButton = (Button) this.view.findViewById(R.id.like_us_button);
        this.fbShareButton = (Button) this.view.findViewById(R.id.fb_share_button);
        this.purchaseButton = (Button) this.view.findViewById(R.id.purchase_button);
        this.ad_button = (Button) this.view.findViewById(R.id.remove_ads_button);
        this.removeAdsText = (TextView) this.view.findViewById(R.id.remove_ads_text);
        this.purchaseButtonText = (TextView) this.view.findViewById(R.id.purchase_button_text);
        TextView textView = this.purchaseButtonText;
        textView.setText(getString(R.string.credits_purchase_text) + " Hd Pack");
        ((MainActivity) getActivity()).setSplashScreenGoneListener(this);
        ((MainActivity) getActivity()).setRemoveAdsListener(this);
        ((MainActivity) getActivity()).setCustomScreenShowListener(this);
        this.screen_image.setVisibility(View.INVISIBLE);
        Log.d("NativeADD bool", String.valueOf(Constants.main_activity_admob_first));
        if (!Constants.REMOVE_ADS && Constants.CUSTOM_REMOVE_ADS_BUTTON_ENABLED && Constants.CUSTOM_REMOVE_ADS_BUTTON_TEXT_COLOR != null && Constants.CUSTOM_REMOVE_ADS_BUTTON_BACKGROUND_COLOR != null) {
            this.removeAdsText.setBackgroundColor(Color.parseColor(Constants.CUSTOM_REMOVE_ADS_BUTTON_BACKGROUND_COLOR));
            this.removeAdsText.setTextColor(Color.parseColor(Constants.CUSTOM_REMOVE_ADS_BUTTON_TEXT_COLOR));
        }
        if (!(!Constants.CUSTOM_PURCHASE_BUTTON_ENABLED || Constants.CUSTOM_PURCHASE_BUTTON_TEXT_COLOR == null || Constants.CUSTOM_PURCHASE_BUTTON_BACKGROUND_COLOR == null || Constants.CUSTOM_PURCHASE_BUTTON_TEXT == null)) {
            this.purchaseButtonText.setBackgroundColor(Color.parseColor(Constants.CUSTOM_PURCHASE_BUTTON_BACKGROUND_COLOR));
            this.purchaseButtonText.setTextColor(Color.parseColor(Constants.CUSTOM_PURCHASE_BUTTON_TEXT_COLOR));
            this.purchaseButtonText.setText(Constants.CUSTOM_PURCHASE_BUTTON_TEXT);
            Log.d("remoteconfig", "purchase button enabled");
        }
        if (!this.freshStart) {
            this.fbShareButton.setOnClickListener(this);
            this.likeUsButton.setOnClickListener(this);
            this.galleryButton.setOnClickListener(this);
            this.moreAppsButton.setOnClickListener(this);
            this.rateButton.setOnClickListener(this);
            this.cameraButton.setOnClickListener(this);
            this.purchaseButton.setOnClickListener(this);
            this.ad_button.setOnClickListener(this);
        }
        if (Constants.REMOVE_ADS) {
            if (this.screen_image.getVisibility() == View.INVISIBLE || this.screen_image.getVisibility() == View.GONE) {
                this.screen_image.setVisibility(View.VISIBLE);
            }
            this.removeAdsText.setBackgroundColor(Color.parseColor("#3A3A38"));
            this.ad_button.setEnabled(false);
        }/* else if (this.fbnativeAd != null) {
            inflateFBNative(this.fbnativeAd);
            Log.d("AdCustom", "inflate fb call");
        } else if (this.mopubNativeAd != null) {
            inflateMopubNative(this.mopubNativeAd);
            Log.d("AdCustom", "inflate mopub call");
        } else if (this.admob_nativeAd != null) {
            infilateAdmobNativeAd(this.admob_nativeAd);
        } else if (getActivity() != null) {
            initializeAd();
        } else {
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    if (MainFragment.this.getActivity() != null) {
                        MainFragment.this.initializeAd();
                        handler.removeCallbacks(this);
                        return;
                    }
                    handler.postDelayed(this, 100);
                }
            }, 100);
        }*/
        if (Constants.HD_PACK_PURCHASED) {
            this.purchaseButtonText.setBackgroundColor(Color.parseColor("#3A3A38"));
            this.purchaseButton.setEnabled(false);
        }
        return this.view;
    }

    public void infilateAdmobNativeAd(final UnifiedNativeAd unifiedNativeAd) {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                if (MainFragment.this.getActivity() != null && !Constants.REMOVE_ADS) {
                    handler.removeCallbacks(this);
                    if (MainFragment.this.screen_image.getVisibility() == View.VISIBLE) {
                        MainFragment.this.screen_image.setVisibility(View.GONE);
                    }
                    View inflate = LayoutInflater.from(MainFragment.this.getActivity()).inflate(R.layout.admob_native_ad_layout, (ViewGroup) null);
                    MainFragment.this.populateUnifiedNativeAdView(unifiedNativeAd, (UnifiedNativeAdView) inflate.findViewById(R.id.admob_native));
                    MainFragment.this.nativeAdContainer.addView(inflate);
                }
            }
        }, 1500);
    }

    /* access modifiers changed from: private */
    public void populateUnifiedNativeAdView(UnifiedNativeAd unifiedNativeAd, UnifiedNativeAdView unifiedNativeAdView) {
        this.screen_image.setVisibility(View.GONE);
        unifiedNativeAdView.setMediaView((MediaView) unifiedNativeAdView.findViewById(R.id.ad_media));
        unifiedNativeAdView.setHeadlineView(unifiedNativeAdView.findViewById(R.id.ad_headline));
        unifiedNativeAdView.setBodyView(unifiedNativeAdView.findViewById(R.id.ad_body));
        unifiedNativeAdView.setCallToActionView(unifiedNativeAdView.findViewById(R.id.ad_call_to_action));
        unifiedNativeAdView.setIconView(unifiedNativeAdView.findViewById(R.id.ad_app_icon));
        ((TextView) unifiedNativeAdView.getHeadlineView()).setText(unifiedNativeAd.getHeadline());
        if (unifiedNativeAd.getBody() == null) {
            unifiedNativeAdView.getBodyView().setVisibility(View.INVISIBLE);
        } else {
            unifiedNativeAdView.getBodyView().setVisibility(View.VISIBLE);
            ((TextView) unifiedNativeAdView.getBodyView()).setText(unifiedNativeAd.getBody());
        }
        if (unifiedNativeAd.getCallToAction() == null) {
            unifiedNativeAdView.getCallToActionView().setVisibility(View.INVISIBLE);
        } else {
            unifiedNativeAdView.getCallToActionView().setVisibility(View.VISIBLE);
            ((Button) unifiedNativeAdView.getCallToActionView()).setText(unifiedNativeAd.getCallToAction());
        }
        if (unifiedNativeAd.getIcon() == null) {
            unifiedNativeAdView.getIconView().setVisibility(View.GONE);
        } else {
            ((ImageView) unifiedNativeAdView.getIconView()).setImageDrawable(unifiedNativeAd.getIcon().getDrawable());
            unifiedNativeAdView.getIconView().setVisibility(View.VISIBLE);
        }
        unifiedNativeAdView.setNativeAd(unifiedNativeAd);
    }

    public void LoadMopubNative() {
        if (MoPub.isSdkInitialized()) {
            this.moPubNative.makeRequest();
        }
    }

    public void onStart() {
        super.onStart();
    }

    public void onResume() {
        super.onResume();
        Constants.FRAGMENT_VISIBLE = true;
        Log.d("customrating", "show");
    }

    public void onStop() {
        super.onStop();
        Constants.FRAGMENT_VISIBLE = false;
    }

    public void onDestroyView() {
        super.onDestroyView();
        if (this.freshStart) {
            this.freshStart = false;
        }
        this.view = null;
    }

    public void onDestroy() {
        super.onDestroy();
    }

    public void onDetach() {
        super.onDetach();
    }

  /*  public void initializeAd() {
        MoPubStaticNativeAdRenderer moPubStaticNativeAdRenderer = new MoPubStaticNativeAdRenderer(new ViewBinder.Builder(R.layout.main_screen_mopub_native_ad_layout).titleId(C3674R.C3676id.native_ad_title).mainImageId(C3674R.C3676id.native_ad_main_image).iconImageId(C3674R.C3676id.native_ad_icon_image).callToActionId(C3674R.C3676id.native_ad_call_to_action).privacyInformationIconImageId(C3674R.C3676id.native_ad_privacy_information_icon_image).build());
        this.builder = new AdLoader.Builder((Context) getActivity(), ADMOB_AD_UNIT_ID);
        this.builder.forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
            public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
                UnifiedNativeAd unused = MainFragment.this.admob_nativeAd = unifiedNativeAd;
                MainFragment.this.infilateAdmobNativeAd(MainFragment.this.admob_nativeAd);
            }
        });
        this.adLoader = this.builder.withAdListener(new AdListener() {
            public void onAdFailedToLoad(int i) {
                Log.d("NativeADD ", "admob Native ad has not loaded.");
                if (Constants.main_activity_admob_first) {
                    MainFragment.this.LoadMopubNative();
                    MainFragment.this.screen_image.setVisibility(View.VISIBLE);
                }
            }
        }).build();
        this.moPubNativeNetworkListener = new MoPubNative.MoPubNativeNetworkListener() {
            public void onNativeLoad(com.mopub.nativeads.NativeAd nativeAd) {
                Log.d(MoPubLog.LOGTAG, "Native ad has loaded.");
                com.mopub.nativeads.NativeAd unused = MainFragment.this.mopubNativeAd = nativeAd;
                MainFragment.this.inflateMopubNative(nativeAd);
            }

            public void onNativeFail(NativeErrorCode nativeErrorCode) {
                Log.d("NativeADD ", "mopub Native ad has not loaded.");
                if (!Constants.REMOVE_ADS && !Constants.main_activity_admob_first) {
                    MainFragment.this.adLoader.loadAd(new AdRequest.Builder().build());
                    MainFragment.this.screen_image.setVisibility(View.VISIBLE);
                }
            }
        };
        this.nativeAd = new NativeAd((Context) getActivity(), "162007114469037_269723790364035");
        this.moPubNative = new MoPubNative(getActivity(), "6f9b69b5a92a482cbf5e854d4bcd5961", this.moPubNativeNetworkListener);
        this.moPubNative.registerAdRenderer(moPubStaticNativeAdRenderer);
        this.nativeAd.setAdListener(new NativeAdListener() {
            public void onAdClicked(Ad ad) {
            }

            public void onLoggingImpression(Ad ad) {
            }

            public void onMediaDownloaded(Ad ad) {
                Log.d("NativeADD ", "fb Native ad media downloaded.");
            }

            public void onError(Ad ad, AdError adError) {
                Log.d("NativeADD ", "fb Native ad has not loaded. " + String.valueOf(adError.getErrorCode()));
                if (adError.getErrorCode() != 1001) {
                    MainFragment.this.checkNativeAdInitialized();
                } else if (Constants.REMOVE_ADS) {
                } else {
                    if (Constants.main_activity_admob_first) {
                        MainFragment.this.adLoader.loadAd(new AdRequest.Builder().build());
                    } else {
                        MainFragment.this.LoadMopubNative();
                    }
                }
            }*/

          /*  public void onAdLoaded(Ad ad) {
                if (MainFragment.this.nativeAd != null && MainFragment.this.nativeAd == ad) {
                    boolean unused = MainFragment.this.fbLoaded = true;
                    MainFragment.this.fbnativeAd = MainFragment.this.nativeAd;
                    if (!Constants.REMOVE_ADS) {
                        MainFragment.this.inflateFBNative(MainFragment.this.nativeAd);
                        MainFragment.this.screen_image.setVisibility(View.GONE);
                    }
                    Log.d("NativeADD ", "fb Native ad has  loaded.");
                   // Log.d(BuildConfig.NETWORK_NAME, "Native ad has not loaded.");
                }
            }
        });
        if (!this.fb_load_called) {
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    MainFragment.this.nativeAd.loadAd();
                    boolean unused = MainFragment.this.fb_load_called = true;
                }
            }, 3500);
        }
    }*/

  /*  public void checkNativeAdInitialized() {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                if (MainFragment.this.nativeAdLayout.getChildCount() > 0 && MainFragment.this.fbnativeAd != null) {
                    MainFragment.this.screen_image.setVisibility(View.GONE);
                    MainFragment.this.nativeAdContainer.setVisibility(View.GONE);
                } else if (Constants.REMOVE_ADS) {
                } else {
                    if (Constants.main_activity_admob_first) {
                        MainFragment.this.adLoader.loadAd(new AdRequest.Builder().build());
                    } else {
                        MainFragment.this.LoadMopubNative();
                    }
                }
            }
        }, 3000);
    }*/

  /*  public void inflateMopubNative(final com.mopub.nativeads.NativeAd nativeAd2) {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                if (MainFragment.this.getActivity() != null && !Constants.REMOVE_ADS) {
                    handler.removeCallbacks(this);
                    LayoutInflater from = LayoutInflater.from(MainFragment.this.getActivity());
                    MainFragment.this.adView = (RelativeLayout) from.inflate(R.layout.main_screen_mopub_native_ad_layout, MainFragment.this.nativeAdContainer, false);
                    nativeAd2.renderAdView(MainFragment.this.adView);
                    nativeAd2.prepare(MainFragment.this.adView);
                    MainFragment.this.nativeAdContainer.addView(MainFragment.this.adView);
                    MainFragment.this.screen_image.setVisibility(View.GONE);
                }
                if (MainFragment.this.mopubNativeAd != null) {
                    Log.d("NativeADD", "mopubnativeAd not null");
                }
                if (MainFragment.this.nativeAdContainer.getChildCount() > 0) {
                    Log.d("NativeADD", "View Visible");
                }
            }
        }, 1500);
    }*/

   /* public void inflateFBNative(final NativeAd nativeAd2) {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                if (MainFragment.this.getActivity() != null && !Constants.REMOVE_ADS) {
                    handler.removeCallbacks(this);
                    MainFragment.this.inflateAd(nativeAd2);
                }
                Log.d("AdCustom", "inflate fb");
            }
        }, 1500);
    }
*/
    /* access modifiers changed from: private */
 /*   public void inflateAd(NativeAd nativeAd2) {
        nativeAd2.unregisterView();
        int i = 0;
        this.adView = (RelativeLayout) LayoutInflater.from(getActivity()).inflate(R.layout.main_screen_fb_native_ad_layout, this.nativeAdLayout, false);
        this.nativeAdLayout.addView(this.adView);
        LinearLayout linearLayout = (LinearLayout) this.adView.findViewById(R.id.ad_choices_container);
        AdOptionsView adOptionsView = new AdOptionsView(getActivity(), nativeAd2, this.nativeAdLayout);
        linearLayout.removeAllViews();
        linearLayout.addView(adOptionsView, 0);
        AdIconView adIconView = (AdIconView) this.adView.findViewById(R.id.native_ad_icon);
        TextView textView = (TextView) this.adView.findViewById(R.id.native_ad_title);
        com.facebook.ads.MediaView mediaView = (com.facebook.ads.MediaView) this.adView.findViewById(R.id.native_ad_media);
        TextView textView2 = (TextView) this.adView.findViewById(R.id.native_ad_sponsored_label);
        Button button = (Button) this.adView.findViewById(R.id.native_ad_call_to_action);
        textView.setText(nativeAd2.getAdvertiserName());
        ((TextView) this.adView.findViewById(R.id.native_ad_body)).setText(nativeAd2.getAdBodyText());
        ((TextView) this.adView.findViewById(R.id.native_ad_social_context)).setText(nativeAd2.getAdSocialContext());
        if (!nativeAd2.hasCallToAction()) {
            i = 4;
        }
        button.setVisibility(i);
        button.setText(nativeAd2.getAdCallToAction());
        textView2.setText(nativeAd2.getSponsoredTranslation());
        ArrayList arrayList = new ArrayList();
        arrayList.add(textView);
        arrayList.add(button);
       // nativeAd2.registerViewForInteraction((View) this.adView, mediaView, (com.facebook.ads.MediaView) adIconView, (List<View>) arrayList);
        this.screen_image.setVisibility(View.GONE);
    }*/

    public void OnSplashScreenGone() {
        this.fbShareButton.setOnClickListener(this);
        this.likeUsButton.setOnClickListener(this);
        this.galleryButton.setOnClickListener(this);
        this.moreAppsButton.setOnClickListener(this);
        this.rateButton.setOnClickListener(this);
        this.cameraButton.setOnClickListener(this);
        this.purchaseButton.setOnClickListener(this);
        this.ad_button.setOnClickListener(this);
        if (!Constants.REMOVE_ADS && Constants.CUSTOM_REMOVE_ADS_BUTTON_ENABLED && Constants.CUSTOM_REMOVE_ADS_BUTTON_TEXT_COLOR != null && Constants.CUSTOM_REMOVE_ADS_BUTTON_BACKGROUND_COLOR != null) {
            this.removeAdsText.setBackgroundColor(Color.parseColor(Constants.CUSTOM_REMOVE_ADS_BUTTON_BACKGROUND_COLOR));
            this.removeAdsText.setTextColor(Color.parseColor(Constants.CUSTOM_REMOVE_ADS_BUTTON_TEXT_COLOR));
        }
        if (Constants.CUSTOM_PURCHASE_BUTTON_ENABLED && Constants.CUSTOM_PURCHASE_BUTTON_TEXT_COLOR != null && Constants.CUSTOM_PURCHASE_BUTTON_BACKGROUND_COLOR != null && Constants.CUSTOM_PURCHASE_BUTTON_TEXT != null) {
            this.purchaseButtonText.setBackgroundColor(Color.parseColor(Constants.CUSTOM_PURCHASE_BUTTON_BACKGROUND_COLOR));
            this.purchaseButtonText.setTextColor(Color.parseColor(Constants.CUSTOM_PURCHASE_BUTTON_TEXT_COLOR));
            this.purchaseButtonText.setText(Constants.CUSTOM_PURCHASE_BUTTON_TEXT);
            Log.d("remoteconfig", "purchase button enabled");
        }
    }

    public void onClick(View view2) {
        switch (view2.getId()) {
            case R.id.camera_button:
                ((MainActivity) getActivity()).CapturePhoto();
                return;
            case R.id.fb_share_button:
                ((MainActivity) getActivity()).fbShare();
                return;
            case R.id.gallery_button:
                ((MainActivity) getActivity()).SelectImage();
                if (!OpenCVLoader.initDebug()) {
                    System.loadLibrary("opencv_java3");
                    return;
                }
                return;
            case R.id.like_us_button:
                ((MainActivity) getActivity()).fbLike();
                return;
            case R.id.more_apps_button:
                ((MainActivity) getActivity()).moreApps();
                Bundle bundle = new Bundle();
                bundle.putString("AdNetwork", "More_Apps_btn");
                FireBaseHelper.getInstance().LogEvent("Ad_Analytics", bundle);
                return;
            case R.id.purchase_button:
                ((MainActivity) getActivity()).PurchaseProduct(Constants.HD_PACK, 0);
                Bundle bundle2 = new Bundle();
                bundle2.putString("iap_click", "hd_pack");
                FireBaseHelper.getInstance().LogEvent("IAP_Analytics", bundle2);
                return;
            case R.id.rate_us_button:
                ((MainActivity) getActivity()).rateUs();
                return;
            case R.id.remove_ads_button:
                ((MainActivity) getActivity()).PurchaseProduct(Constants.REMOVE_ADS_ID, 0);
                Bundle bundle3 = new Bundle();
                bundle3.putString("iap_click", "remove_ads_button");
                FireBaseHelper.getInstance().LogEvent("IAP_Analytics", bundle3);
                return;
            default:
                return;
        }
    }

    public void OnRemoveAds() {
        if (this.fbnativeAd != null) {
            //this.nativeAdLayout.setVisibility(8);
            this.screen_image.setVisibility(View.VISIBLE);
        } else if (!(this.mopubNativeAd == null && this.admob_nativeAd == null)) {
            this.adView.setVisibility(View.GONE);
            this.nativeAdContainer.setVisibility(View.GONE);
            this.screen_image.setVisibility(View.VISIBLE);
        }
        this.removeAdsText.setBackgroundColor(Color.parseColor("#3A3A38"));
        this.ad_button.setEnabled(false);
        Log.d("RemoveAds", "removeAds");
        if (SharedPreferencesManager.HasKey(getActivity(), Constants.HD_PACK_PURCHASED_KEY)) {
            this.purchaseButtonText.setBackgroundColor(Color.parseColor("#3A3A38"));
            this.purchaseButtonText.setEnabled(false);
            Log.d("RemoveAds", "hdPack");
        }
    }

    public void OnCustomScreenShow() {
        if (this.fbnativeAd != null) {
           // this.nativeAdLayout.setVisibility(8);
            this.screen_image.setVisibility(View.VISIBLE);
        } else if (!(this.mopubNativeAd == null && this.admob_nativeAd == null)) {
            this.adView.setVisibility(View.GONE);
            this.screen_image.setVisibility(View.VISIBLE);
        }
        this.removeAdsText.setBackgroundColor(Color.parseColor("#3A3A38"));
        this.ad_button.setEnabled(false);
        if (SharedPreferencesManager.HasKey(getActivity(), Constants.HD_PACK_PURCHASED_KEY)) {
            this.purchaseButtonText.setBackgroundColor(Color.parseColor("#3A3A38"));
            this.purchaseButtonText.setEnabled(false);
        }
        this.fbShareButton.setOnClickListener((View.OnClickListener) null);
        this.likeUsButton.setOnClickListener((View.OnClickListener) null);
        this.galleryButton.setOnClickListener((View.OnClickListener) null);
        this.moreAppsButton.setOnClickListener((View.OnClickListener) null);
        this.rateButton.setOnClickListener((View.OnClickListener) null);
        this.cameraButton.setOnClickListener((View.OnClickListener) null);
        this.purchaseButton.setOnClickListener((View.OnClickListener) null);
        this.ad_button.setOnClickListener((View.OnClickListener) null);
        Log.d("onback", "custom screen show");
    }
}
