package com.teamvinay.sketch;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAdView;
import com.google.android.material.tabs.TabLayout;
import com.mopub.mobileads.MoPubErrorCode;
import com.mopub.mobileads.MoPubView;
import com.mopub.nativeads.MoPubNative;
import com.mopub.nativeads.MoPubStaticNativeAdRenderer;
import com.mopub.nativeads.NativeAd;
import com.mopub.nativeads.NativeErrorCode;
import com.mopub.nativeads.ViewBinder;
import com.willy.ratingbar.BaseRatingBar;
import com.willy.ratingbar.ScaleRatingBar;
import helper.Constants;
import helper.ExportDetails;
import listeners.ResetButtonListener;
import listeners.ResetListener;
import ui.EditorFragment;
import ui.EffectFragment;
import ui.FilterFragment;
import util.AdUtil;
import util.FireBaseHelper;
import util.ImageFilePath;
import util.NativeAdUtil;
import util.SharedPreferencesManager;

public class PhotoActivity extends AppCompatActivity implements View.OnClickListener, MoPubView.BannerAdListener, ResetButtonListener {
    private static final String ADMOB_NATIVE_BANNER_ADUNIT_ID = "ca-app-pub-4195495625122728/2301275229";
    private static final String MOPUB_NATIVE_BANNER_ADUNIT_ID = "d86b5a12d15944b9b7202fd0c80c9167";
    public static Bitmap bitmap;
    public static Bitmap effectFilterbitmap;
    RelativeLayout adContainer;
    AdLoader adLoader;
    /* access modifiers changed from: private */
    public UnifiedNativeAd admob_nativeAd;
    AdLoader.Builder builder;
    DemoClass demoClass;
    private float density = 0.0f;
    /* access modifiers changed from: private */
    public boolean editorFragmentVisible = true;
    private EffectFilterDetails effectFilterDetails;
    /* access modifiers changed from: private */
    public boolean feedback = false;
    /* access modifiers changed from: private */
    public boolean filterFragmentVisible = true;
    String image_path;
    /* access modifiers changed from: private */
    public MoPubNative moPubNative;
    private MoPubNative.MoPubNativeNetworkListener moPubNativeNetworkListener;
    /* access modifiers changed from: private */
    public MoPubView moPubView;
    /* access modifiers changed from: private */
    public boolean nativeAdLoaded = false;
    /* access modifiers changed from: private */
    public int pageCount = 0;
    private PhotoPagerAdapter photoPagerAdapter;
    private boolean resetActivated = false;
    private ImageView resetButton;
    ResetListener resetListener;
    private ImageView saveButton;
    private ViewPager viewPager;

    public void onBannerClicked(MoPubView moPubView2) {
    }

    public void onBannerCollapsed(MoPubView moPubView2) {
    }

    public void onBannerExpanded(MoPubView moPubView2) {
    }

    public void onBannerLoaded(MoPubView moPubView2) {
    }

    static /* synthetic */ int access$408(PhotoActivity photoActivity) {
        int i = photoActivity.pageCount;
        photoActivity.pageCount = i + 1;
        return i;
    }

    public EffectFilterDetails getEffectFilterDetails() {
        return this.effectFilterDetails;
    }

    public ResetListener getResetListener() {
        return this.resetListener;
    }

    public void setResetListener(ResetListener resetListener2) {
        this.resetListener = resetListener2;
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.main_fragment_activity);
        this.image_path = getIntent().getStringExtra("imageUri");
        bitmap = BitmapFactory.decodeFile(ImageFilePath.getPath(this, Uri.parse(this.image_path)));
        this.photoPagerAdapter = new PhotoPagerAdapter(getSupportFragmentManager());
        this.viewPager = (ViewPager) findViewById(R.id.pager);
        this.adContainer = (RelativeLayout) findViewById(R.id.ad_container);
        this.moPubView = (MoPubView) findViewById(R.id.adview);
        this.effectFilterDetails = new EffectFilterDetails();
        setPhotoPagerAdapter(this.viewPager);
        this.saveButton = (ImageView) findViewById(R.id.save);
        this.resetButton = (ImageView) findViewById(R.id.reset);
        this.saveButton.setOnClickListener(this);
        this.resetButton.setOnClickListener(this);
        ((TabLayout) findViewById(R.id.tabLayout)).setupWithViewPager(this.viewPager);
        this.moPubView.setAdUnitId("191821deb8ab4ef69bb9682048de3821");
        this.density = getResources().getDisplayMetrics().density;
        if (!Constants.REMOVE_ADS && AdUtil.getInstance().isStartUpAdAvailable()) {
            AdUtil.getInstance().ShowStartUpInterstitial();
        }
        this.pageCount = 1;
        Constants.PHOTOACTIVITY_OPENED = true;
        if (Constants.SHOW_BOTTOM_RATE_VIEW) {
            initializeBottomRateView();
        } else if (!Constants.REMOVE_ADS && Constants.BOTTOM_NATIVE_AD_SHOW) {
            initializeBottomAds();
        }
        Bundle bundle2 = new Bundle();
        bundle2.putString("Screen", "Effect Screen");
        FireBaseHelper.getInstance().LogEvent("Screen_Analytics", bundle2);
        if (!Constants.REMOVE_ADS && Constants.EXPORT_BUTTON_ADS) {
            NativeAdUtil.getInstance().initializeExportNativeAds(this);
        }
        if (!Constants.REMOVE_ADS && Constants.EXPORT_SCREEN_BOTTOM_ADS) {
            NativeAdUtil.getInstance().iniitializeExportBottomAds(this);
        }
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
    }

    /* access modifiers changed from: protected */
    public void onStop() {
        super.onStop();
    }

    /* access modifiers changed from: package-private */
    public void initializeBottomAds() {
        Log.d("bottomads", "Banner Loaded");
        this.builder = new AdLoader.Builder((Context) this, ADMOB_NATIVE_BANNER_ADUNIT_ID);
        this.builder.forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
            public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
                UnifiedNativeAd unused = PhotoActivity.this.admob_nativeAd = unifiedNativeAd;
                PhotoActivity.this.infilateAdmobNativeAd(unifiedNativeAd);
            }
        });
        this.adLoader = this.builder.withAdListener(new AdListener() {
            public void onAdFailedToLoad(int i) {
                Log.d("PhotoActivity native", "admob Banner not Loaded");
                if (Constants.REMOVE_ADS) {
                    return;
                }
                if (Constants.admob_first) {
                    PhotoActivity.this.moPubNative.makeRequest();
                } else {
                    PhotoActivity.this.moPubView.loadAd();
                }
            }

            public void onAdLoaded() {
                super.onAdLoaded();
                boolean unused = PhotoActivity.this.nativeAdLoaded = true;
                Log.d("PhotoActivity native ", " admob Banner Loaded");
            }
        }).build();
        this.moPubView.setBannerAdListener(this);
        MoPubStaticNativeAdRenderer moPubStaticNativeAdRenderer = new MoPubStaticNativeAdRenderer(new ViewBinder.Builder(R.layout.native_ad_banner).titleId(R.id.native_ad_title).iconImageId(R.id.native_ad_icon).callToActionId(R.id.native_ad_call_to_action).textId(R.id.native_ad_body).privacyInformationIconImageId(R.id.native_ad_privacy_information_icon_image).build());
        this.moPubNativeNetworkListener = new MoPubNative.MoPubNativeNetworkListener() {
            public void onNativeLoad(NativeAd nativeAd) {
                Log.d("PhotoActivity native", "Banner mopub Native ad has loaded.");
                boolean unused = PhotoActivity.this.nativeAdLoaded = true;
                PhotoActivity.this.inflateMopubNativeAd(nativeAd);
            }

            public void onNativeFail(NativeErrorCode nativeErrorCode) {
                Log.d("PhotoActivity native", "Banner mopu Native ad has not loaded.");
                if (Constants.REMOVE_ADS) {
                    return;
                }
                if (!Constants.admob_first) {
                    PhotoActivity.this.adLoader.loadAd(new AdRequest.Builder().build());
                } else {
                    PhotoActivity.this.moPubView.loadAd();
                }
            }
        };
        this.moPubNative = new MoPubNative(this, MOPUB_NATIVE_BANNER_ADUNIT_ID, this.moPubNativeNetworkListener);
        this.moPubNative.registerAdRenderer(moPubStaticNativeAdRenderer);
        if (Constants.REMOVE_ADS || !Constants.BOTTOM_NATIVE_AD_SHOW) {
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.adContainer.getLayoutParams();
            layoutParams.height = dpToPx(0, false);
            layoutParams.width = -2;
            this.adContainer.setLayoutParams(layoutParams);
        } else if (Constants.admob_first) {
            this.adLoader.loadAd(new AdRequest.Builder().build());
        } else {
            this.moPubNative.makeRequest();
        }
        this.adLoader.loadAd(new AdRequest.Builder().build());
    }

    public void infilateAdmobNativeAd(UnifiedNativeAd unifiedNativeAd) {
        View inflate = LayoutInflater.from(this).inflate(R.layout.admob_native_banner, (ViewGroup) null);
        populateUnifiedNativeAdView(unifiedNativeAd, (UnifiedNativeAdView) inflate.findViewById(R.id.admob_native));
        this.adContainer.addView(inflate);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.adContainer.getLayoutParams();
        layoutParams.height = -2;
        layoutParams.width = -2;
        this.adContainer.setLayoutParams(layoutParams);
    }

    public void inflateMopubNativeAd(NativeAd nativeAd) {
        RelativeLayout relativeLayout = (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.native_ad_banner, this.adContainer, false);
        nativeAd.renderAdView(relativeLayout);
        nativeAd.prepare(relativeLayout);
        this.adContainer.addView(relativeLayout);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.adContainer.getLayoutParams();
        layoutParams.height = -2;
        layoutParams.width = -2;
        this.adContainer.setLayoutParams(layoutParams);
    }

    private void populateUnifiedNativeAdView(UnifiedNativeAd unifiedNativeAd, UnifiedNativeAdView unifiedNativeAdView) {
        unifiedNativeAdView.setHeadlineView(unifiedNativeAdView.findViewById(R.id.ad_headline));
        unifiedNativeAdView.setBodyView(unifiedNativeAdView.findViewById(R.id.ad_body));
        unifiedNativeAdView.setCallToActionView(unifiedNativeAdView.findViewById(R.id.native_ad_call_to_action));
        unifiedNativeAdView.setIconView(unifiedNativeAdView.findViewById(R.id.ad_app_icon));
        ((TextView) unifiedNativeAdView.getHeadlineView()).setText(unifiedNativeAd.getHeadline());
        ((TextView) unifiedNativeAdView.getBodyView()).setText(unifiedNativeAd.getBody());
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

    public static void ShowMessage(String str, String str2) {
        Log.d(str, str2);
    }

    private void setPhotoPagerAdapter(ViewPager viewPager2) {
        PhotoPagerAdapter photoPagerAdapter2 = new PhotoPagerAdapter(getSupportFragmentManager());
        EffectFragment effectFragment = new EffectFragment();
        effectFragment.setResetButtonListener(this);
        photoPagerAdapter2.addFragment(effectFragment, "Effect");
        FilterFragment filterFragment = new FilterFragment();
        filterFragment.setResetButtonListener(this);
        EditorFragment editorFragment = new EditorFragment();
        editorFragment.setResetButtonListener(this);
        photoPagerAdapter2.addFragment(filterFragment, "Filter");
        photoPagerAdapter2.addFragment(editorFragment, "Editor");
        viewPager2.setAdapter(photoPagerAdapter2);
        viewPager2.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrollStateChanged(int i) {
            }

            public void onPageScrolled(int i, float f, int i2) {
            }

            public void onPageSelected(int i) {
                PhotoActivity.access$408(PhotoActivity.this);
                if (PhotoActivity.this.pageCount == Constants.PAGE_COUNT) {
                    int unused = PhotoActivity.this.pageCount = 1;
                    if (AdUtil.getInstance().isExportAdAvailable()) {
                        AdUtil.getInstance().ShowExportInterstitial();
                    }
                    Bundle bundle = new Bundle();
                    bundle.putString("AdNetwork", "PageScrollAd");
                    FireBaseHelper.getInstance().LogEvent("Ad_Analytics", bundle);
                }
                if (i == 1 && PhotoActivity.this.filterFragmentVisible) {
                    Bundle bundle2 = new Bundle();
                    bundle2.putString("Screen", "Filter Screen");
                    FireBaseHelper.getInstance().LogEvent("Screen_Analytics", bundle2);
                    boolean unused2 = PhotoActivity.this.filterFragmentVisible = false;
                }
                if (i == 2 && PhotoActivity.this.editorFragmentVisible) {
                    Bundle bundle3 = new Bundle();
                    bundle3.putString("Screen", "Editor Screen");
                    FireBaseHelper.getInstance().LogEvent("Screen_Analytics", bundle3);
                    boolean unused3 = PhotoActivity.this.editorFragmentVisible = false;
                }
            }
        });
    }

    public void onClick(View view) {
        if (this.effectFilterDetails != null) {
            ExportDetails.getInstance().setEffectFilterDetails(this.effectFilterDetails);
        }
        int id = view.getId();
        if (id != R.id.reset) {
            if (id == R.id.save) {
                Intent intent = new Intent(this, ExportActivity.class);
                intent.putExtra("imageUri", this.image_path);
                startActivity(intent);
            }
        } else if (this.resetActivated) {
            if (this.resetListener != null) {
                this.resetListener.OnReset();
            }
            Log.d("ColorFilter", "Active");
            this.resetActivated = false;
        }
    }

    public int dpToPx(int i, boolean z) {
        if (z) {
            return 0;
        }
        return Math.round(((float) i) * this.density);
    }

    public int dpToPx(int i) {
        return Math.round(((float) i) * this.density);
    }

    public void onBannerFailed(MoPubView moPubView2, MoPubErrorCode moPubErrorCode) {
        if (!this.nativeAdLoaded) {
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.adContainer.getLayoutParams();
            layoutParams.height = dpToPx(0, false);
            layoutParams.width = -2;
            this.adContainer.setLayoutParams(layoutParams);
        }
        Log.d("PhotoActivity native", "banner failed");
    }

    public void initializeBottomRateView() {
        RelativeLayout relativeLayout = (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.rating_view_layout, (ViewGroup) null, false);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.adContainer.getLayoutParams();
        layoutParams.height = dpToPx(90, false);
        layoutParams.width = -1;
        this.adContainer.setLayoutParams(layoutParams);
        this.adContainer.addView(relativeLayout);
        final Button button = (Button) relativeLayout.findViewById(R.id.yes_button);
        final Button button2 = (Button) relativeLayout.findViewById(R.id.no_button);
        final TextView textView = (TextView) relativeLayout.findViewById(R.id.rate_dialog_text);
        LinearLayout linearLayout = (LinearLayout) relativeLayout.findViewById(R.id.layout);
        ScaleRatingBar scaleRatingBar = (ScaleRatingBar) relativeLayout.findViewById(R.id.simpleRatingBar);
        final LinearLayout linearLayout2 = linearLayout;
        final TextView textView2 = textView;
        final Button button3 = button;
        final Button button4 = button2;
        scaleRatingBar.setOnRatingChangeListener(new BaseRatingBar.OnRatingChangeListener() {
            public void onRatingChange(BaseRatingBar baseRatingBar, float f, boolean z) {
                if (f >= 4.0f) {
                    try {
                        PhotoActivity photoActivity = PhotoActivity.this;
                        photoActivity.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=" + PhotoActivity.this.getPackageName())));
                    } catch (ActivityNotFoundException unused) {
                        PhotoActivity photoActivity2 = PhotoActivity.this;
                        photoActivity2.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=" + PhotoActivity.this.getPackageName())));
                    }
                    baseRatingBar.setVisibility(View.GONE);
                    linearLayout2.setVisibility(View.VISIBLE);
                    textView2.setText(PhotoActivity.this.getString(R.string.thanks_text));
                    button3.setText(PhotoActivity.this.getString(R.string.close));
                    button4.setEnabled(false);
                    Bundle bundle = new Bundle();
                    bundle.putString("User_Experience", "Native_Rate_4_5");
                    FireBaseHelper.getInstance().LogEvent("Rate_Analytics", bundle);
                    return;
                }
                baseRatingBar.setVisibility(View.GONE);
                linearLayout2.setVisibility(View.VISIBLE);
                textView2.setText(PhotoActivity.this.getString(R.string.thanks_text));
                button3.setText(PhotoActivity.this.getString(R.string.close));
                button4.setEnabled(false);
                Bundle bundle2 = new Bundle();
                bundle2.putString("User_Experience", "Native_Rate_3");
                FireBaseHelper.getInstance().LogEvent("Rate_Analytics", bundle2);
            }
        });
        if (SharedPreferencesManager.HasKey(this, Constants.APP_ENJOYED)) {
            textView.setText(getString(R.string.review_text));
            button.setText(getString(R.string.ok_sure));
        } else {
            textView.setText(getString(R.string.custom_rate_dialog_title));
        }
        Bundle bundle = new Bundle();
        bundle.putString("User_Experience", "Native-RateView_Show");
        FireBaseHelper.getInstance().LogEvent("Rate_Analytics", bundle);
        final Button button5 = button;
        final TextView textView3 = textView;
        final ScaleRatingBar scaleRatingBar2 = scaleRatingBar;
        final LinearLayout linearLayout3 = linearLayout;
        final Button button6 = button2;
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (button5.getText().equals("Yes!")) {
                    textView3.setText(PhotoActivity.this.getString(R.string.rate_text));
                    if (Constants.RATING_BAR) {
                        scaleRatingBar2.setVisibility(View.VISIBLE);
                        linearLayout3.setVisibility(View.GONE);
                    }
                    button5.setText(PhotoActivity.this.getString(R.string.ok_sure));
                    button6.setText(PhotoActivity.this.getString(R.string.no_thanks));
                    Bundle bundle = new Bundle();
                    bundle.putString("User_Experience", "Native-RateView_Enjoy");
                    FireBaseHelper.getInstance().LogEvent("Rate_Analytics", bundle);
                } else if (button5.getText().equals(PhotoActivity.this.getString(R.string.ok_sure))) {
                    if (PhotoActivity.this.feedback) {
                        Intent intent = new Intent("android.intent.action.SEND");
                        intent.putExtra("android.intent.extra.EMAIL", new String[]{"hqgames1080@gmail.com"});
                        intent.putExtra("android.intent.extra.SUBJECT", "Pencil Photo Sketch:FeedBack");
                        intent.setType("message/rfc822");
                        PhotoActivity.this.startActivity(Intent.createChooser(intent, "Select Email Sending App :"));
                        textView3.setText(PhotoActivity.this.getString(R.string.thanks_text));
                        button5.setText(PhotoActivity.this.getString(R.string.close));
                        button6.setEnabled(false);
                    }
                    if (Constants.RATING_BAR) {
                        scaleRatingBar2.setVisibility(View.VISIBLE);
                        linearLayout3.setVisibility(View.GONE);
                        textView3.setText(PhotoActivity.this.getString(R.string.rate_dialog_title));
                        return;
                    }
                    try {
                        PhotoActivity photoActivity = PhotoActivity.this;
                        photoActivity.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=" + PhotoActivity.this.getPackageName())));
                    } catch (ActivityNotFoundException unused) {
                        PhotoActivity photoActivity2 = PhotoActivity.this;
                        photoActivity2.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=" + PhotoActivity.this.getPackageName())));
                    }
                    textView3.setText(PhotoActivity.this.getString(R.string.thanks_text));
                    button5.setText(PhotoActivity.this.getString(R.string.close));
                    button6.setEnabled(false);
                } else if (button5.getText().equals(PhotoActivity.this.getString(R.string.close))) {
                    PhotoActivity.this.adContainer.removeAllViews();
                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) PhotoActivity.this.adContainer.getLayoutParams();
                    layoutParams.height = PhotoActivity.this.dpToPx(0, false);
                    layoutParams.width = -1;
                    PhotoActivity.this.adContainer.setLayoutParams(layoutParams);
                }
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (button2.getText().equals(PhotoActivity.this.getString(R.string.not))) {
                    textView.setText(PhotoActivity.this.getString(R.string.feedback_text));
                    button.setText(PhotoActivity.this.getString(R.string.ok_sure));
                    button2.setText(PhotoActivity.this.getString(R.string.no_thanks));
                    boolean unused = PhotoActivity.this.feedback = true;
                } else if (button2.getText().equals(PhotoActivity.this.getString(R.string.no_thanks))) {
                    PhotoActivity.this.adContainer.removeAllViews();
                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) PhotoActivity.this.adContainer.getLayoutParams();
                    layoutParams.height = PhotoActivity.this.dpToPx(0, false);
                    layoutParams.width = -1;
                    PhotoActivity.this.adContainer.setLayoutParams(layoutParams);
                }
            }
        });
    }

    public void OnResetButtonActivate() {
        this.resetActivated = true;
        this.resetButton.setColorFilter(Color.parseColor("#FF3B3F"), PorterDuff.Mode.SRC_IN);
        Log.d("ResetButton", "active");
    }

    public void OnResetButtonDeActivate() {
        this.resetActivated = false;
        this.resetButton.setColorFilter(Color.parseColor("#23272c"), PorterDuff.Mode.SRC_IN);
        Log.d("ResetButton", "deactive");
    }
}
