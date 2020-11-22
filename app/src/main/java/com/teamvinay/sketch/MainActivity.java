package com.teamvinay.sketch;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.exifinterface.media.ExifInterface;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.SkuDetails;
import com.anjlab.android.iab.v3.TransactionDetails;
import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.ndk.CrashlyticsNdk;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.ads.*;
import com.facebook.ads.AdIconView;

import com.facebook.ads.NativeAd;

import com.facebook.internal.AnalyticsEvents;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;
import com.google.android.gms.ads.formats.MediaView;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAdView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;

import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.mopub.common.MoPub;
import com.mopub.common.SdkConfiguration;
import com.mopub.common.SdkInitializationListener;
import com.mopub.common.privacy.ConsentDialogListener;
import com.mopub.common.privacy.ConsentStatus;
import com.mopub.common.privacy.ConsentStatusChangeListener;
import com.mopub.common.privacy.PersonalInfoManager;

import com.mopub.mobileads.MoPubErrorCode;
import com.mopub.mobileads.MoPubInterstitial;
import com.mopub.nativeads.MoPubNative;
import com.morsebyte.shailesh.twostagerating.FeedbackReceivedListener;
import com.morsebyte.shailesh.twostagerating.FeedbackWithRatingReceivedListener;
import com.morsebyte.shailesh.twostagerating.TwoStageRate;
import com.yalantis.ucrop.UCrop;
import helper.Constants;
import helper.Credits;
import helper.DrawerItemsAdapter;
import helper.ExportDetails;
import helper.ImageResolutions;
import hotchemi.android.rate.AppRate;
import hotchemi.android.rate.OnClickButtonListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import listeners.AdClosedListener;
import listeners.Consumer;
import listeners.CustomScreenShowListener;
import listeners.FragmentUpdateListener;
import listeners.RemoveAdsListener;
import listeners.ReplaceFragmentListener;
import listeners.SplashScreenGoneListener;
import listeners.TaskCompleteListener;
import listeners.WaitScreenGoneListener;
import org.opencv.android.OpenCVLoader;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.services.network.HttpRequest;
import io.fabric.sdk.android.services.settings.SettingsJsonConstants;
import ui.MainFragment;
import util.AdUtil;
import util.EditingFragmentDetails;
import util.Effect;
import util.FireBaseHelper;
import util.ImageFilePath;
import util.ImageSize;
import util.ImageUtils;
import util.ModalClass;
import util.NativeAdUtil;
import util.SharedPreferencesManager;
import util.Utils;

import static android.content.Intent.FILL_IN_ACTION;

public class MainActivity extends AppCompatActivity implements ReplaceFragmentListener, AdClosedListener, BillingProcessor.IBillingHandler, View.OnClickListener, Animation.AnimationListener, Consumer {
    private static final int CAMERA_REQUEST = 1888;
    /* access modifiers changed from: private */
    public static String FACEBOOK_PAGE_ID = "202543950342484";
    private static final int REQUEST_PICK_IMAGE = 1;
    private static final List<String> REQUIRED_DANGEROUS_PERMISSIONS = new ArrayList();
    public static final int RequestPermissionCode = 7;
    private static final String SAMPLE_CROPPED_IMAGE_NAME = "SampleCropImage";
    private static boolean activityVisible = false;
    private RelativeLayout about_app;
    private RelativeLayout adView;
    Bitmap bitmap;

    /* renamed from: bp */
    BillingProcessor f4193bp;
    SkuDetails bronzeDetails = null;
    CallbackManager callbackManager;
    LinearLayout cancelButton;
    private boolean capture = false;
    /* access modifiers changed from: private */
    public RelativeLayout close_ad;
    boolean connected = false;
    private RelativeLayout contact_button;
    private RelativeLayout creditButton;
    private int cropped_image_height;
    private int cropped_image_width;
    CustomScreenShowListener customScreenShowListener;
    private float density = 0.0f;
    EditingFragmentDetails editingFragmentDetails = null;
    private Effect effect;
    ExportDetails export = new ExportDetails();
    private boolean fb_image_share = false;
    private boolean fb_load_called = false;
    NativeAd fbnativeAd;
    File file;
    private FilterHolder filterHolder;
    Bitmap filter_drawable;
    private RelativeLayout floating_ad_background;
    /* access modifiers changed from: private */
    public RelativeLayout floating_native_layout;
    FragmentUpdateListener fragmentUpdateListener;
    SkuDetails goldDetails = null;
    private RelativeLayout guide_button;
    private Handler handler = new Handler();
    private ImageSize imageSize;
    boolean isCropActivityLaunch = false;
    String licenseKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAr07hWs2rpKVsWROYHGW+CVjvvft+UU22cP3UFzwbmnUK0nv1mP/EhFKNtbZAIsuhDQjmzSqg8h6gNiCQS9Cj24yHV0vz3HprJRYqOrlpR05VBX3znIQQI7rqzE4vxup2sVUTHqNhDk9/2fi9+TpIXRnYkVo6HngOnOWUJcl/xuYALDp5iKmTnRj5/prKDOoxMoKTPgO67igKJ44BN9XndfbLGYPUsFsvfaNONSBPlexwwpMJFhDoLgtHYyFuoSCFCtq2myUccNgJKlBXQ0fkV76VctR90i42i+Aqv50fg9LSxhL7PYhRPqnqyr/knWO0L0cVG7aOT44I+UgRC8H+wwIDAQAB";
    /* access modifiers changed from: private */
    public RelativeLayout loadingScreen;
    private TextView loadingText;
    /* access modifiers changed from: private */
    public boolean loggedIn = false;
    /* access modifiers changed from: private */
    public AccessToken mAccessToken;
    private DrawerItemsAdapter mAdapter;
    private ConsentStatusChangeListener mConsentStatusChangeListener;
    DrawerLayout mDrawerLayout;
    PersonalInfoManager mPersonalInfoManager;
    private Uri mainUri;
    boolean marketOpen = false;
    private float memory;
    private ImageView menu;
    private MoPubNative moPubNative;
    private com.mopub.nativeads.NativeAd mopubNativeAd;
    private RelativeLayout mopub_container;
    MoPubInterstitial moreApps;
    NativeAd nativeAd;
    /* access modifiers changed from: private */
    public String newToken = null;
    private RelativeLayout privacy_button;
    int progressStatus = 0;
    private int purchase_credit_quantity = 0;
    private boolean purchase_iap = false;
    private RelativeLayout rating_view;
    boolean rating_view_open = false;
    private boolean readyToPurchase = false;
    BroadcastReceiver receiver;
    /* access modifiers changed from: private */
    public FirebaseRemoteConfig remoteConfig;
    RemoveAdsListener removeAdsListener;
    private Uri resultUri;
    private boolean rewardNotification = false;
    SkuDetails sale_bronze = null;
    SkuDetails sale_gold = null;
    SkuDetails sale_silver = null;
    private float screenDensity = 0.0f;
    SdkConfiguration sdkConfiguration;
    private Uri selected;
    ShareDialog shareDialog;
    SkuDetails silverDetails = null;
    Animation slideAnimation;
    /* access modifiers changed from: private */
    public RelativeLayout splashLayout;
    SplashScreenGoneListener splashScreenGoneListener;
    private boolean start_up_interstitial = true;
    private Button submitButton;
    private TaskCompleteListener taskCompleteListener;
    TextView thanksText;
    /* access modifiers changed from: private */
    public int timeInterval = 0;
    private Uri uri;
    TextView version_name;
    WaitScreenGoneListener waitScreenGoneListener;
    /* access modifiers changed from: private */
    public boolean waitscreen_visible = false;

    private void initializeCredits() {
    }

    public void UpdateLabelCreditAmount() {
    }

    public double getSize(long j) {
        int i = (j > PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID ? 1 : (j == PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID ? 0 : -1));
        return i < 0 ? (double) j : (i < 0 || j >= PlaybackStateCompat.ACTION_SET_CAPTIONING_ENABLED) ? (j < PlaybackStateCompat.ACTION_SET_CAPTIONING_ENABLED || j >= 1073741824) ? (j < 1073741824 || j >= 1099511627776L) ? (j < 1099511627776L || j >= 1125899906842624L) ? (j < 1125899906842624L || j >= 1152921504606846976L) ? j >= 1152921504606846976L ? ((double) j) / ((double) 1152921504606846976L) : FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE : ((double) j) / ((double) 1125899906842624L) : ((double) j) / ((double) 1099511627776L) : ((double) j) / ((double) 1073741824) : ((double) j) / ((double) PlaybackStateCompat.ACTION_SET_CAPTIONING_ENABLED) : ((double) j) / ((double) PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID);
    }

    public void onAnimationRepeat(Animation animation) {
    }

    public void onAnimationStart(Animation animation) {
    }

    public Uri getMainUri() {
        return this.mainUri;
    }

    public void setMainUri(Uri uri2) {
        this.mainUri = uri2;
    }

    public Uri getUri() {
        return this.uri;
    }

    public void setUri(Uri uri2) {
        this.uri = uri2;
    }

    public SkuDetails getBronzeDetails() {
        return this.bronzeDetails;
    }

    public void setBronzeDetails(SkuDetails skuDetails) {
        this.bronzeDetails = skuDetails;
    }

    public SkuDetails getSilverDetails() {
        return this.silverDetails;
    }

    public void setSilverDetails(SkuDetails skuDetails) {
        this.silverDetails = skuDetails;
    }

    public SkuDetails getGoldDetails() {
        return this.goldDetails;
    }

    public void setGoldDetails(SkuDetails skuDetails) {
        this.goldDetails = skuDetails;
    }

    public SkuDetails getSale_bronze() {
        return this.sale_bronze;
    }

    public SkuDetails getSale_silver() {
        return this.sale_silver;
    }

    public SkuDetails getSale_gold() {
        return this.sale_gold;
    }

    public static boolean isActivityVisible() {
        return activityVisible;
    }

    public static void activityResumed() {
        activityVisible = true;
    }

    public static void activityPaused() {
        activityVisible = false;
    }

    public TaskCompleteListener getTaskCompleteListener() {
        return this.taskCompleteListener;
    }

    public void setTaskCompleteListener(TaskCompleteListener taskCompleteListener2) {
        this.taskCompleteListener = taskCompleteListener2;
    }

    public SplashScreenGoneListener getSplashScreenGoneListener() {
        return this.splashScreenGoneListener;
    }

    public void setSplashScreenGoneListener(SplashScreenGoneListener splashScreenGoneListener2) {
        this.splashScreenGoneListener = splashScreenGoneListener2;
    }

    public WaitScreenGoneListener getWaitScreenGoneListener() {
        return this.waitScreenGoneListener;
    }

    public void setWaitScreenGoneListener(WaitScreenGoneListener waitScreenGoneListener2) {
        this.waitScreenGoneListener = waitScreenGoneListener2;
    }

    public FragmentUpdateListener getFragmentUpdateListener() {
        return this.fragmentUpdateListener;
    }

    public void setFragmentUpdateListener(FragmentUpdateListener fragmentUpdateListener2) {
        this.fragmentUpdateListener = fragmentUpdateListener2;
    }

    public Effect getEffect() {
        return this.effect;
    }

    public void setEffect(Effect effect2) {
        this.effect = effect2;
    }

    public RemoveAdsListener getRemoveAdsListener() {
        return this.removeAdsListener;
    }

    public void setRemoveAdsListener(RemoveAdsListener removeAdsListener2) {
        this.removeAdsListener = removeAdsListener2;
    }

    public ExportDetails getExport() {
        return this.export;
    }

    public void setExport(ExportDetails exportDetails) {
        this.export = exportDetails;
    }

    public EditingFragmentDetails getEditingFragmentDetails() {
        return this.editingFragmentDetails;
    }

    public void setEditingFragmentDetails(EditingFragmentDetails editingFragmentDetails2) {
        this.editingFragmentDetails = editingFragmentDetails2;
    }

    static {
        REQUIRED_DANGEROUS_PERMISSIONS.add("android.permission.ACCESS_COARSE_LOCATION");
        REQUIRED_DANGEROUS_PERMISSIONS.add("android.permission.WRITE_EXTERNAL_STORAGE");
        REQUIRED_DANGEROUS_PERMISSIONS.add("android.permission.CAMERA");
    }

    public FilterHolder getFilterHolder() {
        return this.filterHolder;
    }

    public void setFilterHolder(FilterHolder filterHolder2) {
        this.filterHolder = filterHolder2;
    }

    public CustomScreenShowListener getCustomScreenShowListener() {
        return this.customScreenShowListener;
    }

    public void setCustomScreenShowListener(CustomScreenShowListener customScreenShowListener2) {
        this.customScreenShowListener = customScreenShowListener2;
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_main);
        if (!OpenCVLoader.initDebug()) {
            System.loadLibrary("opencv_java3");
            Log.d("MainLibrary", "not loaded");
        } else {
            Log.d("MainLibrary", "  loaded");
        }
        initializedAdSdk(this);
        this.imageSize = new ImageSize();
        this.rating_view = (RelativeLayout) findViewById(R.id.rating_view);
        this.submitButton = (Button) findViewById(R.id.submit_button);
        this.submitButton.setEnabled(false);
        this.thanksText = (TextView) findViewById(R.id.thanks_text);
        this.cancelButton = (LinearLayout) findViewById(R.id.cancel_button_rateView);
        this.submitButton.setBackgroundColor(Color.parseColor("#45484a"));
        this.submitButton.setOnClickListener(this);
        this.cancelButton.setOnClickListener(this);
        this.menu = (ImageView) findViewById(R.id.menu_icon);
        this.splashLayout = (RelativeLayout) findViewById(R.id.splash_layout);
        this.mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        this.version_name = (TextView) findViewById(R.id.version_name);
        this.loadingScreen = (RelativeLayout) findViewById(R.id.loading_screen);
        this.loadingText = (TextView) findViewById(R.id.loading_text);
        this.mopub_container = (RelativeLayout) findViewById(R.id.container);
        //this.nativeAdLayout = (NativeAdLayout) findViewById(R.id.native_ad_container);
        if (!FirebaseApp.getApps(this).isEmpty()) {
            this.remoteConfig = FirebaseRemoteConfig.getInstance();
        }
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        FirebaseRemoteConfigSettings build = new FirebaseRemoteConfigSettings.Builder().setDeveloperModeEnabled(false).build();
        this.density = getResources().getDisplayMetrics().density;
        this.remoteConfig.setConfigSettings(build);
        this.remoteConfig.setDefaults((int) R.xml.remote_config_defaults);
        if (SharedPreferencesManager.HasKey(this, "export_button_ads_enable_key")) {
            Constants.EXPORT_BUTTON_ADS = SharedPreferencesManager.getSomeBoolValue(this, "export_button_ads_enable_key");
        }
        if (SharedPreferencesManager.HasKey(this, "export_button_ads_count")) {
            Constants.EXPORT_BUTTON_ADS_COUNT = SharedPreferencesManager.getSomeIntValue(this, "export_button_ads_count");
        }
        if (SharedPreferencesManager.HasKey(this, "export_bottom_ads_key")) {
            Constants.EXPORT_SCREEN_BOTTOM_ADS = SharedPreferencesManager.getSomeBoolValue(this, "export_bottom_ads_key");
        }
        if (SharedPreferencesManager.HasKey(this, "save_ad_key")) {
            Constants.SAVE_AD_COUNT = SharedPreferencesManager.getSomeIntValue(this, "save_ad_key");
        }
        if (SharedPreferencesManager.HasKey(this, "button_ads_key")) {
            Constants.BUTTON_ADS = SharedPreferencesManager.getSomeBoolValue(this, "button_ads_key");
        }
        if (SharedPreferencesManager.HasKey(this, Constants.EFFECT_BUTTON_ADS_KEY)) {
            Constants.EFFECT_AD_BUTTON_SPACE = SharedPreferencesManager.getSomeIntValue(this, Constants.EFFECT_BUTTON_ADS_KEY);
        }
        if (SharedPreferencesManager.HasKey(this, Constants.FILTER_BUTTON_ADS_KEY)) {
            Constants.FILTER_AD_BUTTON_SPACE = SharedPreferencesManager.getSomeIntValue(this, Constants.FILTER_BUTTON_ADS_KEY);
        }
        if (SharedPreferencesManager.HasKey(this, Constants.EDITOR_BUTTON_ADS_KEY)) {
            Constants.EDITOR_AD_BUTTON_SPACE = SharedPreferencesManager.getSomeIntValue(this, Constants.EDITOR_BUTTON_ADS_KEY);
        }
        if (SharedPreferencesManager.HasKey(this, "show_rating_bar_key")) {
            Constants.RATING_BAR = SharedPreferencesManager.getSomeBoolValue(this, "show_rating_bar_key");
        }
        if (SharedPreferencesManager.HasKey(this, "export_rating_bar_key")) {
            Constants.EXPORT_RATING_BAR = SharedPreferencesManager.getSomeBoolValue(this, "export_rating_bar_key");
        }
        if (SharedPreferencesManager.HasKey(this, Constants.HD_PACK_PURCHASED_KEY)) {
            Constants.HD_PACK_PURCHASED = SharedPreferencesManager.getSomeBoolValue(this, Constants.HD_PACK_PURCHASED_KEY);
        } else {
            Bundle bundle2 = new Bundle();
            bundle2.putString("not_purchased", "hd_pack");
            FireBaseHelper.getInstance().LogEvent("Hd_Pack_Not_Purchased", bundle2);
        }
        if (SharedPreferencesManager.HasKey(this, "bottom_rate_view")) {
            Constants.SHOW_BOTTOM_RATE_VIEW = SharedPreferencesManager.getSomeBoolValue(this, "bottom_rate_view");
        } else {
            SharedPreferencesManager.setSomeBoolValue(this, "bottom_rate_view", false);
        }
        if (SharedPreferencesManager.HasKey(this, "show_rate_key")) {
            Constants.SHOW_RATE_DIALOG = SharedPreferencesManager.getSomeBoolValue(this, "show_rate_key");
        }
        if (SharedPreferencesManager.HasKey(this, Constants.REMOVE_ADS_KEY)) {
            Constants.REMOVE_ADS = SharedPreferencesManager.getSomeBoolValue(this, Constants.REMOVE_ADS_KEY);
        }
        if (SharedPreferencesManager.HasKey(this, Constants.ADMOB_NATIVE_FIRST)) {
            Constants.admob_first = SharedPreferencesManager.getSomeBoolValue(this, Constants.ADMOB_NATIVE_FIRST);
        }
        if (SharedPreferencesManager.HasKey(this, Constants.MAIN_ADMOB_NATIVE_FIRST)) {
            Constants.main_activity_admob_first = SharedPreferencesManager.getSomeBoolValue(this, Constants.MAIN_ADMOB_NATIVE_FIRST);
        }
        if (SharedPreferencesManager.HasKey(this, Constants.FIREBASE_TOKEN_KEY)) {
            Constants.FIREBASE_TOKEN = SharedPreferencesManager.getSomeStringValue(this, Constants.FIREBASE_TOKEN_KEY);
        }
        if (SharedPreferencesManager.HasKey(this, Constants.CUSTOM_AD_BUTTON_SPACE_KEY)) {
            Constants.EFFECT_AD_BUTTON_SPACE = SharedPreferencesManager.getSomeIntValue(this, Constants.CUSTOM_AD_BUTTON_SPACE_KEY);
        }
        if (SharedPreferencesManager.HasKey(this, Constants.CUSTOM_AD_BUTTON_KEY)) {
            Constants.CUSTOM_AD_BUTTON = SharedPreferencesManager.getSomeBoolValue(this, Constants.CUSTOM_AD_BUTTON_KEY);
        }
        if (SharedPreferencesManager.HasKey(this, Constants.SAVE_INTERSTITIAL_REPEAT_KEY)) {
            Constants.SAVE_INTERSTITIAL_REPEAT = SharedPreferencesManager.getSomeIntValue(this, Constants.SAVE_INTERSTITIAL_REPEAT_KEY);
        }
        if (SharedPreferencesManager.HasKey(this, Constants.SAVE_INTERSTITIAL_KEY)) {
            Constants.SAVE_INTERSTITIAL = SharedPreferencesManager.getSomeBoolValue(this, Constants.SAVE_INTERSTITIAL_KEY);
        }
        if (SharedPreferencesManager.HasKey(this, "custom_rate_show")) {
            CustomAppRater.initialize(this);
        }
        if (SharedPreferencesManager.HasKey(this, Constants.EFFECT_INTERSTITIAL_REPEAT_KEY)) {
            Constants.EFFECT_INTERSTITIAL_REPEAT = SharedPreferencesManager.getSomeIntValue(this, Constants.EFFECT_INTERSTITIAL_REPEAT_KEY);
        }
        if (SharedPreferencesManager.HasKey(this, Constants.EFFECT_INTERSTITIAL_KEY)) {
            Constants.EFFECT_INTERSTITIAL = SharedPreferencesManager.getSomeBoolValue(this, Constants.EFFECT_INTERSTITIAL_KEY);
        }
        if (SharedPreferencesManager.HasKey(this, Constants.EFFECT_AD_COUNT_KEY)) {
            Constants.EFFECT_AD_COUNT_THRESHOLD = SharedPreferencesManager.getSomeIntValue(this, Constants.EFFECT_AD_COUNT_KEY);
        }
        if (SharedPreferencesManager.HasKey(this, Constants.EFFECT_NATIVE_AD_KEY)) {
            Constants.EFFECT_NATIVE_AD_ENABLE = SharedPreferencesManager.getSomeBoolValue(this, Constants.EFFECT_NATIVE_AD_KEY);
        }
        this.filter_drawable = BitmapFactory.decodeResource(getResources(), R.drawable.filter_drawable);
        this.privacy_button = (RelativeLayout) findViewById(R.id.privacy_button);
        this.contact_button = (RelativeLayout) findViewById(R.id.contact_button);
        this.slideAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_left_to_right_fast);
        this.slideAnimation.setAnimationListener(this);
        initializeCredits();
        TextView textView = this.version_name;
        textView.setText("ver : " + "1.4.7");
        this.f4193bp = new BillingProcessor(this, this.licenseKey, this);
        this.f4193bp.initialize();
        if (getIntent().getExtras() != null) {
            for (String str : getIntent().getExtras().keySet()) {
                Log.d("Credits key maina", str);
                if (str.equals("TimeInterval")) {
                    String string = getIntent().getExtras().getString(str);
                    String string2 = getIntent().getExtras().getString("Message");
                    String string3 = getIntent().getExtras().getString("Title");
                    int parseInt = Integer.parseInt(string);
                    this.timeInterval = parseInt;
                    initializeRewardDialog(parseInt, string2, string3);
                } else {
                    str.equals("Rate");
                }
            }
        }
        this.callbackManager = CallbackManager.Factory.create();
        this.shareDialog = new ShareDialog((Activity) this);
        this.shareDialog.registerCallback(this.callbackManager, new FacebookCallback<Sharer.Result>() {
            public void onCancel() {
            }

            public void onError(FacebookException facebookException) {
            }

            public void onSuccess(Sharer.Result result) {
            }
        });
        AdUtil.getInstance().setAdClosedListener(this);
        FireBaseHelper.getInstance().initialize(this);
        Fabric.with(this, new Crashlytics(), new CrashlyticsNdk());
        this.receiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                try {
                    int intExtra = intent.getIntExtra("TimeInterval", 0);
                    String stringExtra = intent.getStringExtra("Message");
                    String stringExtra2 = intent.getStringExtra("Title");
                    int unused = MainActivity.this.timeInterval = intExtra;
                    MainActivity.this.initializeRewardDialog(intExtra, stringExtra, stringExtra2);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        checkRam();
        //GetToken();
        fetch();
        AppRater.initialize(this);
        LoginManager.getInstance().registerCallback(this.callbackManager, new FacebookCallback<LoginResult>() {
            public void onSuccess(LoginResult loginResult) {
                Log.d("FacebookLogin", "Success");
                AccessToken unused = MainActivity.this.mAccessToken = loginResult.getAccessToken();
                boolean unused2 = MainActivity.this.loggedIn = true;
                try {
                    MainActivity.this.getPackageManager().getApplicationInfo("com.facebook.katana", 0);
                    MainActivity.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("fb://page/" + MainActivity.FACEBOOK_PAGE_ID)));
                    Log.d("page", SettingsJsonConstants.APP_KEY);
                } catch (PackageManager.NameNotFoundException unused3) {
                    MainActivity.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("http://facebook.com/" + MainActivity.FACEBOOK_PAGE_ID)));
                    Log.d("page", "appNot");
                }
            }

            public void onCancel() {
                Log.d("FacebookLogin", "Cancel");
            }

            public void onError(FacebookException facebookException) {
                Log.d("FacebookLogin", AnalyticsEvents.PARAMETER_DIALOG_OUTCOME_VALUE_FAILED);
            }
        });
        this.shareDialog.registerCallback(this.callbackManager, new FacebookCallback<Sharer.Result>() {
            public void onCancel() {
            }

            public void onError(FacebookException facebookException) {
            }

            public void onSuccess(Sharer.Result result) {
            }
        });
        loadFragment(new MainFragment());
        SplashScreen();
        if (SharedPreferencesManager.HasKey(this, "credits_usage_dialog")) {
            Constants.IS_CREDITS_USAGE_DIALOG_SHOWN = true;
        }
        if (SharedPreferencesManager.HasKey(this, Constants.NATIVE_RATE_TITLE)) {
            Constants.RATING_TITLE = SharedPreferencesManager.getSomeStringValue(this, Constants.NATIVE_RATE_TITLE);
        }
        if (SharedPreferencesManager.HasKey(this, Constants.NATIVE_RATE_DIALOG_TEXT)) {
            Constants.RATING_DIALOG_TEXT = SharedPreferencesManager.getSomeStringValue(this, Constants.NATIVE_RATE_DIALOG_TEXT);
        }
        if (SharedPreferencesManager.HasKey(this, Constants.NATIVE_RATE_RATING_TEXT)) {
            Constants.RATING_MSG_TEXT = SharedPreferencesManager.getSomeStringValue(this, Constants.NATIVE_RATE_RATING_TEXT);
        }
    }

    private void getScreenDensity(float f) {
        double d = (double) f;
        if (d == 0.75d) {
            Log.d("ScreenDensity", "LDPI");
        } else if (f == 1.0f) {
            Log.d("ScreenDensity", "MDPI");
        } else if (d == 1.5d) {
            Log.d("ScreenDensity", "HDPI");
        } else if (f == 2.0f) {
            Log.d("ScreenDensity", "XHDPI");
        } else if (f == 3.0f) {
            Log.d("ScreenDensity", "XXHDPI");
        } else if (f == 4.0f) {
            Log.d("ScreenDensity", "XXXHDPI");
        }
    }

    private void loadingStyles() {
        new Thread(new Runnable() {
            public void run() {
            }
        }).start();
    }

    public void checkRateDialog() {
        AppRate.with(this).setInstallDays(Constants.INSTALL_DAYS).setLaunchTimes(Constants.LAUNCH_TIMES).setRemindInterval(Constants.REMIND_INTERVAL).setShowLaterButton(true).setDebug(false).setOnClickButtonListener(new OnClickButtonListener() {
            public void onClickButton(int i) {
                switch (i) {
                    case -3:
                        Bundle bundle = new Bundle();
                        bundle.putString("User_Experience", "AppRate-Remind Later");
                        FireBaseHelper.getInstance().LogEvent("Rate_Analytics", bundle);
                        return;
                    case -2:
                        Bundle bundle2 = new Bundle();
                        bundle2.putString("User_Experience", "AppRate-No Thanks");
                        FireBaseHelper.getInstance().LogEvent("Rate_Analytics", bundle2);
                        Bundle bundle3 = new Bundle();
                        bundle3.putString("not_rate", "Nothing done");
                        FireBaseHelper.getInstance().LogEvent("Not_Rate", bundle3);
                        return;
                    case -1:
                        Bundle bundle4 = new Bundle();
                        bundle4.putString("User_Experience", "AppRate-Rate it");
                        FireBaseHelper.getInstance().LogEvent("Rate_Analytics", bundle4);
                        return;
                    default:
                        return;
                }
            }
        }).monitor();
        if (Constants.SHOW_RATE_DIALOG) {
            AppRate.showRateDialogIfMeetsConditions(this);
        }
    }

    private void fetch() {
        this.remoteConfig.fetch(3600).addOnCompleteListener((Activity) this, new OnCompleteListener<Void>() {
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    MainActivity.this.remoteConfig.activateFetched();
                }
                if (MainActivity.this.remoteConfig.getBoolean("full_screen_ed_native_first")) {
                    Constants.FULL_SCREEN_Editing_NATIVE_FIRST = MainActivity.this.remoteConfig.getBoolean("full_screen_ed_native_first");
                }
                if (MainActivity.this.remoteConfig.getBoolean("full_screen_en_native_first")) {
                    Constants.FULL_SCREEN_Ending_NATIVE_FIRST = MainActivity.this.remoteConfig.getBoolean("full_screen_en_native_first");
                }
                if (MainActivity.this.remoteConfig.getBoolean("full_screen_ef_native_first")) {
                    Constants.FULL_SCREEN_Effect_NATIVE_FIRST = MainActivity.this.remoteConfig.getBoolean("full_screen_ef_native_first");
                }
                if (MainActivity.this.remoteConfig.getBoolean("export_screen_bottom_ads")) {
                    Constants.EXPORT_SCREEN_BOTTOM_ADS = MainActivity.this.remoteConfig.getBoolean("export_screen_bottom_ads");
                }
                if (!MainActivity.this.remoteConfig.getString("intertstitial_ad").equals("null")) {
                    Constants.AD_SHOWN_SPACE = MainActivity.this.remoteConfig.getString("interstitial_ad");
                }
                if (MainActivity.this.remoteConfig.getBoolean("show_rate")) {
                    Constants.SHOW_RATE_DIALOG = MainActivity.this.remoteConfig.getBoolean("show_rate");
                    SharedPreferencesManager.setSomeBoolValue(MainActivity.this, "show_rate_key", true);
                } else {
                    Constants.SHOW_RATE_DIALOG = false;
                    SharedPreferencesManager.setSomeBoolValue(MainActivity.this, "show_rate_key", false);
                }
                if (MainActivity.this.remoteConfig.getBoolean(ModalClass.NATIVE_RATE_VIEW)) {
                    Constants.NATIVE_RATE_VIEW = true;
                    Constants.NATIVE_RATE_TEXT_COLOR = MainActivity.this.remoteConfig.getString(ModalClass.NATIVE_RATE_TEXT_COLOR);
                    Constants.NATIVE_RATE_BG = MainActivity.this.remoteConfig.getString(ModalClass.NATIVE_RATE_BG);
                    Constants.NATIVE_RATE_BUTTON_COLOR = MainActivity.this.remoteConfig.getString(ModalClass.NATIVE_RATE_BUTTON_COLOR);
                    Constants.NATIVE_RATE_BUTTON_TEXT_COLOR = MainActivity.this.remoteConfig.getString(ModalClass.NATIVE_RATE_BUTTON_TEXT_COLOR);
                }
                if (MainActivity.this.remoteConfig.getBoolean(ModalClass.BOTTOM_NATIVE_AD_ENABLE)) {
                    Constants.BOTTOM_NATIVE_AD_SHOW = true;
                }
                if (MainActivity.this.remoteConfig.getBoolean("export_init_image_hd")) {
                    Constants.EXPORT_INIT_HD_ENABLE = true;
                }
                if (MainActivity.this.remoteConfig.getBoolean(ModalClass.EFFECT_NATIVE_ENABLE)) {
                    Constants.EFFECT_NATIVE_AD_ENABLE = true;
                    SharedPreferencesManager.setSomeBoolValue(MainActivity.this, Constants.EFFECT_NATIVE_AD_KEY, true);
                } else {
                    Constants.EFFECT_NATIVE_AD_ENABLE = false;
                    SharedPreferencesManager.setSomeBoolValue(MainActivity.this, Constants.EFFECT_NATIVE_AD_KEY, false);
                }
                if (MainActivity.this.remoteConfig.getBoolean("custom_rate_view_settings")) {
                    int intValue = new Long(MainActivity.this.remoteConfig.getLong("install_days")).intValue();
                    int intValue2 = new Long(MainActivity.this.remoteConfig.getLong("launch_times")).intValue();
                    int intValue3 = new Long(MainActivity.this.remoteConfig.getLong("remind_interval")).intValue();
                    Constants.INSTALL_DAYS = intValue;
                    Constants.LAUNCH_TIMES = intValue2;
                    Constants.REMIND_INTERVAL = intValue3;
                }
                if (MainActivity.this.remoteConfig.getBoolean(ModalClass.SAVE_INTERSTITIAL)) {
                    Constants.SAVE_INTERSTITIAL = true;
                    SharedPreferencesManager.setSomeBoolValue(MainActivity.this, Constants.SAVE_INTERSTITIAL_KEY, true);
                    int intValue4 = new Long(MainActivity.this.remoteConfig.getLong(ModalClass.SAVE_INTERSTITIAL_REPEAT)).intValue();
                    Constants.SAVE_INTERSTITIAL_REPEAT = intValue4;
                    Constants.SAVE_AD_COUNT = new Long(MainActivity.this.remoteConfig.getLong("save_ad_count")).intValue();
                    SharedPreferencesManager.setSomeIntValue(MainActivity.this, "save_ad_key", intValue4);
                    SharedPreferencesManager.setSomeIntValue(MainActivity.this, Constants.SAVE_INTERSTITIAL_REPEAT_KEY, Constants.SAVE_AD_COUNT);
                } else {
                    Constants.SAVE_INTERSTITIAL = false;
                    SharedPreferencesManager.setSomeBoolValue(MainActivity.this, Constants.SAVE_INTERSTITIAL_KEY, false);
                    SharedPreferencesManager.setSomeIntValue(MainActivity.this, Constants.SAVE_INTERSTITIAL_REPEAT_KEY, 0);
                }
                if (MainActivity.this.remoteConfig.getBoolean(ModalClass.EFFECT_INTERSTITIAL)) {
                    Constants.EFFECT_INTERSTITIAL = true;
                    SharedPreferencesManager.setSomeBoolValue(MainActivity.this, Constants.EFFECT_INTERSTITIAL_KEY, true);
                    Constants.EFFECT_INTERSTITIAL_REPEAT = new Long(MainActivity.this.remoteConfig.getLong(ModalClass.EFFECT_INTERSTITIAL_REPEAT)).intValue();
                    int intValue5 = new Long(MainActivity.this.remoteConfig.getLong(ModalClass.EFFECT_AD_REPEAT)).intValue();
                    Constants.EFFECT_AD_COUNT_THRESHOLD = intValue5;
                    SharedPreferencesManager.setSomeIntValue(MainActivity.this, Constants.EFFECT_AD_COUNT_KEY, intValue5);
                } else {
                    Constants.EFFECT_INTERSTITIAL = false;
                    SharedPreferencesManager.setSomeBoolValue(MainActivity.this, Constants.EFFECT_INTERSTITIAL_KEY, false);
                    SharedPreferencesManager.setSomeIntValue(MainActivity.this, Constants.EFFECT_INTERSTITIAL_REPEAT_KEY, 0);
                }
                if (MainActivity.this.remoteConfig.getBoolean(ModalClass.ADMOB_NATIVE_FIRST)) {
                    Constants.admob_first = true;
                    SharedPreferencesManager.setSomeBoolValue(MainActivity.this, Constants.ADMOB_NATIVE_FIRST, true);
                } else {
                    Constants.admob_first = false;
                    SharedPreferencesManager.setSomeBoolValue(MainActivity.this, Constants.ADMOB_NATIVE_FIRST, false);
                }
                if (MainActivity.this.remoteConfig.getBoolean(ModalClass.MAIN_ACTIVITY_ADMOB_NATIVE_FIRST)) {
                    Constants.main_activity_admob_first = true;
                    SharedPreferencesManager.setSomeBoolValue(MainActivity.this, Constants.MAIN_ADMOB_NATIVE_FIRST, true);
                } else {
                    Constants.main_activity_admob_first = false;
                    SharedPreferencesManager.setSomeBoolValue(MainActivity.this, Constants.MAIN_ADMOB_NATIVE_FIRST, false);
                }
                if (MainActivity.this.remoteConfig.getBoolean(ModalClass.CUSTOM_EXPORT_RATE_VIEW)) {
                    Constants.CUSTOM_EXPORT_RATE_VIEW = true;
                    Constants.EXPORT_RATE_VIEW_TEXT_COLOR = MainActivity.this.remoteConfig.getString(ModalClass.EXPORT_RATE_VIEW_TEXT_COLOR);
                    Constants.EXPORT_RATE_VIEW_BG_COLOR = MainActivity.this.remoteConfig.getString(ModalClass.EXPORT_RATE_VIEW_BG_COLOR);
                    Constants.EXPORT_RATE_VIEW_DIVIDER_COLOR = MainActivity.this.remoteConfig.getString(ModalClass.EXPORT_RATE_VIEW_DIVIDER_COLOR);
                    Constants.EXPORT_RATE_VIEW_OK_BUTTON_COLOR = MainActivity.this.remoteConfig.getString(ModalClass.EXPORT_RATE_VIEW_OK_BUTTON_COLOR);
                    Constants.EXPORT_RATE_VIEW_OK_BUTTON_TEXT_COLOR = MainActivity.this.remoteConfig.getString(ModalClass.EXPORT_RATE_VIEW_OK_BUTTON_TEXT_COLOR);
                    Constants.EXPORT_RATE_VIEW_NO_BUTTON_COLOR = MainActivity.this.remoteConfig.getString(ModalClass.EXPORT_RATE_VIEW_NO_BUTTON_COLOR);
                    Constants.EXPORT_RATE_VIEW_NO_BUTTON_TEXT_COLOR = MainActivity.this.remoteConfig.getString(ModalClass.EXPORT_RATE_VIEW_NO_BUTTON_TEXT_COLOR);
                    Constants.EXPORT_RATING_BAR_COLOR = MainActivity.this.remoteConfig.getString(ModalClass.EXPORT_RATING_BAR_COLOR);
                    Constants.EXPORT_RATE_VIEW_NO_BUTTON_STROKE = MainActivity.this.remoteConfig.getString(ModalClass.EXPORT_RATE_VIEW_NO_BUTTON_STROKE);
                    Constants.EXPORT_RATE_VIEW_OK_BUTTON_STROKE = MainActivity.this.remoteConfig.getString(ModalClass.EXPORT_RATE_VIEW_OK_BUTTON_STROKE);
                    Constants.CUSTOM_EXPORT_RATE_BUTTON_OK_TEXT = MainActivity.this.remoteConfig.getString(ModalClass.CUSTOM_EXPORT_RATE_BUTTON_OK_TEXT);
                    Constants.CUSTOM_EXPORT_RATE_BUTTON_CLOSE_TEXT = MainActivity.this.remoteConfig.getString(ModalClass.CUSTOM_EXPORT_RATE_BUTTON_CLOSE_TEXT);
                    Constants.CUSTOM_EXPORT_RATE_DIALOG_TITLE_TEXT = MainActivity.this.remoteConfig.getString(ModalClass.CUSTOM_EXPORT_RATE_DIALOG_TITLE_TEXT);
                    Constants.CUSTOM_EXPORT_RATE_DIALOG_TEXT = MainActivity.this.remoteConfig.getString(ModalClass.CUSTOM_EXPORT_RATE_DIALOG_TEXT);
                    Constants.CUSTOM_EXPORT_RATE_BUTTON_OK_TEXT_2 = MainActivity.this.remoteConfig.getString(ModalClass.CUSTOM_EXPORT_RATE_BUTTON_OK_TEXT_2);
                    Constants.CUSTOM_EXPORT_RATE_BUTTON_CLOSE_TEXT_2 = MainActivity.this.remoteConfig.getString(ModalClass.CUSTOM_EXPORT_RATE_BUTTON_CLOSE_TEXT_2);
                    Constants.CUSTOM_EXPORT_RATE_DIALOG_TEXT_2 = MainActivity.this.remoteConfig.getString(ModalClass.CUSTOM_EXPORT_RATE_DIALOG_TEXT_2);
                    Constants.CUSTOM_EXPORT_RATE_DIALOG_STROKE_COLOR = MainActivity.this.remoteConfig.getString(ModalClass.CUSTOM_EXPORT_RATE_DIALOG_STROKE_COLOR);
                }
                if (MainActivity.this.remoteConfig.getBoolean(ModalClass.SHOW_CUSTOM_REMOVE_ADS_BUTTON)) {
                    Constants.CUSTOM_REMOVE_ADS_BUTTON_ENABLED = true;
                    Constants.CUSTOM_REMOVE_ADS_BUTTON_BACKGROUND_COLOR = MainActivity.this.remoteConfig.getString(ModalClass.REMOVE_ADS_BG);
                    Constants.CUSTOM_REMOVE_ADS_BUTTON_TEXT_COLOR = MainActivity.this.remoteConfig.getString(ModalClass.REMOVE_ADS_TEXT_COLOR);
                }
                if (MainActivity.this.remoteConfig.getBoolean(ModalClass.SHOW_CUSTOM_PURCHASE_BUTTON)) {
                    Constants.CUSTOM_PURCHASE_BUTTON_ENABLED = true;
                    Constants.CUSTOM_PURCHASE_BUTTON_BACKGROUND_COLOR = MainActivity.this.remoteConfig.getString(ModalClass.PURCHASE_BG);
                    Constants.CUSTOM_PURCHASE_BUTTON_TEXT_COLOR = MainActivity.this.remoteConfig.getString(ModalClass.PURCHASE_BUTTON_TEXT_COLOR);
                    Constants.CUSTOM_PURCHASE_BUTTON_TEXT = MainActivity.this.remoteConfig.getString(ModalClass.PURCHASE_BUTTON_TEXT);
                }
                if (MainActivity.this.remoteConfig.getBoolean(ModalClass.SHOW_CUSTOM_RATE)) {
                    Constants.SHOW_CUSTOM_RATE = true;
                    CustomAppRater.initialize(MainActivity.this);
                }
                if (MainActivity.this.remoteConfig.getBoolean("custom_editing_rate_view")) {
                    Constants.RATE_DIALOG_TEXT = MainActivity.this.remoteConfig.getString(ModalClass.RATE_DIALOG_TEXT);
                    Constants.RATE_BUTTON_CLOSE_TEXT = MainActivity.this.remoteConfig.getString(ModalClass.RATE_BUTTON_CLOSE_TEXT);
                    Constants.RATE_BUTTON_OK_TEXT = MainActivity.this.remoteConfig.getString(ModalClass.RATE_BUTTON_OK_TEXT);
                    Constants.RATE_DIALOG_TITLE_TEXT = MainActivity.this.remoteConfig.getString(ModalClass.RATE_DIALOG_TITLE_TEXT);
                    Constants.DIALOG_BG_COLOR = MainActivity.this.remoteConfig.getString(ModalClass.DIALOG_BG_COLOR);
                    Constants.RATE_DIALOG_STROKE_COLOR = MainActivity.this.remoteConfig.getString(ModalClass.RATE_DIALOG_STROKE_COLOR);
                    Constants.RATE_YES_BUTTON_COLOR = MainActivity.this.remoteConfig.getString(ModalClass.RATE_YES_BUTTON_COLOR);
                    Constants.RATE_YES_BUTTON_STROKE_COLOR = MainActivity.this.remoteConfig.getString(ModalClass.RATE_YES_BUTTON_STROKE_COLOR);
                    Constants.RATE_YES_BUTTON_TEXT_COLOR = MainActivity.this.remoteConfig.getString(ModalClass.RATE_YES_BUTTON_TEXT_COLOR);
                    Constants.RATING_BAR_COLOR = MainActivity.this.remoteConfig.getString(ModalClass.RATING_BAR_COLOR);
                    Constants.RATE_NO_BUTTON_COLOR = MainActivity.this.remoteConfig.getString(ModalClass.RATE_NO_BUTTON_COLOR);
                    Constants.RATE_NO_BUTTON_STROKE_COLOR = MainActivity.this.remoteConfig.getString(ModalClass.RATE_NO_BUTTON_STROKE_COLOR);
                    Constants.RATE_NO_BUTTON_TEXT_COLOR = MainActivity.this.remoteConfig.getString(ModalClass.RATE_NO_BUTTON_TEXT_COLOR);
                    Constants.RATE_BUTTON_STROKE_WIDTH = MainActivity.this.remoteConfig.getString(ModalClass.RATE_BUTTON_STROKE_WIDTH);
                    Constants.RATE_BUTTON_CORNER_RADIUS = MainActivity.this.remoteConfig.getString(ModalClass.RATE_BUTTON_CORNER_RADIUS);
                    Constants.RATE_TITLE_COLOR = MainActivity.this.remoteConfig.getString(ModalClass.RATE_TITLE_COLOR);
                    Constants.RATE_TITLE_TEXT_SIZE = MainActivity.this.remoteConfig.getString(ModalClass.RATE_TITLE_TEXT_SIZE);
                    Constants.RATE_TEXT_COLOR = MainActivity.this.remoteConfig.getString(ModalClass.RATE_TEXT_COLOR);
                    Constants.RATE_TEXT_SIZE = MainActivity.this.remoteConfig.getString(ModalClass.RATE_TEXT_SIZE);
                    Constants.RATE_DIVIDER_COLOR = MainActivity.this.remoteConfig.getString(ModalClass.RATE_DIVIDER_COLOR);
                }
                if (MainActivity.this.remoteConfig.getBoolean(ModalClass.CUSTOM_EXPORT_FUNTION_BUTTON_ENABLED)) {
                    Constants.CUSTOM_EXPORT_FUNTION_BUTTON_ENABLED = true;
                    Constants.EXPORT_FUNCTION_BUTTON_COLOR = MainActivity.this.remoteConfig.getString(ModalClass.EXPORT_FUNCTION_BUTTON_COLOR);
                }
                if (MainActivity.this.remoteConfig.getBoolean("rate_event_count_enable")) {
                    SharedPreferencesManager.setSomeIntValue(MainActivity.this, Constants.EVENT_COUNT, new Long(MainActivity.this.remoteConfig.getLong("rate_event_count")).intValue());
                } else {
                    SharedPreferencesManager.setSomeIntValue(MainActivity.this, Constants.EVENT_COUNT, 5);
                }
                if (MainActivity.this.remoteConfig.getBoolean("show_bottom_rate_view")) {
                    Constants.SHOW_BOTTOM_RATE_VIEW = true;
                    SharedPreferencesManager.setSomeBoolValue(MainActivity.this, "bottom_rate_view", true);
                } else {
                    SharedPreferencesManager.setSomeBoolValue(MainActivity.this, "bottom_rate_view", false);
                }
                if (MainActivity.this.remoteConfig.getBoolean("show_rating_bar")) {
                    Constants.RATING_BAR = true;
                    SharedPreferencesManager.setSomeBoolValue(MainActivity.this, "show_rating_bar_key", true);
                } else {
                    Constants.RATING_BAR = false;
                    SharedPreferencesManager.setSomeBoolValue(MainActivity.this, "show_rating_bar_key", false);
                }
                if (MainActivity.this.remoteConfig.getBoolean("export_rating_bar")) {
                    Constants.EXPORT_RATING_BAR = true;
                    SharedPreferencesManager.setSomeBoolValue(MainActivity.this, "export_rating_bar_key", true);
                } else {
                    Constants.EXPORT_RATING_BAR = false;
                    SharedPreferencesManager.setSomeBoolValue(MainActivity.this, "export_rating_bar_key", false);
                }
                if (MainActivity.this.remoteConfig.getBoolean("export_button_ads_enable")) {
                    Constants.EXPORT_BUTTON_ADS = true;
                    Constants.EXPORT_BUTTON_ADS_COUNT = new Long(MainActivity.this.remoteConfig.getLong("export_button_ads")).intValue();
                    SharedPreferencesManager.setSomeBoolValue(MainActivity.this, "export_button_ads_enable_key", true);
                    SharedPreferencesManager.setSomeIntValue(MainActivity.this, "export_button_ads_count", Constants.EXPORT_BUTTON_ADS_COUNT);
                } else {
                    Constants.EXPORT_BUTTON_ADS = false;
                    SharedPreferencesManager.setSomeBoolValue(MainActivity.this, "export_button_ads_enable_key", false);
                }
                if (MainActivity.this.remoteConfig.getBoolean(ModalClass.BUTTON_ADS)) {
                    Constants.BUTTON_ADS = true;
                    Constants.EFFECT_AD_BUTTON_SPACE = new Long(MainActivity.this.remoteConfig.getLong(ModalClass.EFFECT_BUTTON_ADS_COUNT)).intValue();
                    Constants.FILTER_AD_BUTTON_SPACE = new Long(MainActivity.this.remoteConfig.getLong(ModalClass.FILTER_BUTTON_ADS_COUNT)).intValue();
                    Constants.EDITOR_AD_BUTTON_SPACE = new Long(MainActivity.this.remoteConfig.getLong(ModalClass.EDITOR_BUTTON_ADS_COUNT)).intValue();
                    SharedPreferencesManager.setSomeIntValue(MainActivity.this, Constants.EFFECT_BUTTON_ADS_KEY, Constants.EFFECT_AD_BUTTON_SPACE);
                    SharedPreferencesManager.setSomeIntValue(MainActivity.this, Constants.FILTER_BUTTON_ADS_KEY, Constants.FILTER_AD_BUTTON_SPACE);
                    SharedPreferencesManager.setSomeIntValue(MainActivity.this, Constants.EDITOR_BUTTON_ADS_KEY, Constants.EDITOR_AD_BUTTON_SPACE);
                    SharedPreferencesManager.setSomeBoolValue(MainActivity.this, "button_ads_key", true);
                } else {
                    if (SharedPreferencesManager.HasKey(MainActivity.this, Constants.EFFECT_BUTTON_ADS_KEY)) {
                        Constants.EFFECT_AD_BUTTON_SPACE = SharedPreferencesManager.getSomeIntValue(MainActivity.this, Constants.EFFECT_BUTTON_ADS_KEY);
                    }
                    if (SharedPreferencesManager.HasKey(MainActivity.this, Constants.FILTER_BUTTON_ADS_KEY)) {
                        Constants.FILTER_AD_BUTTON_SPACE = SharedPreferencesManager.getSomeIntValue(MainActivity.this, Constants.FILTER_BUTTON_ADS_KEY);
                    }
                    if (SharedPreferencesManager.HasKey(MainActivity.this, Constants.EDITOR_BUTTON_ADS_KEY)) {
                        Constants.EDITOR_AD_BUTTON_SPACE = SharedPreferencesManager.getSomeIntValue(MainActivity.this, Constants.EDITOR_BUTTON_ADS_KEY);
                    }
                    if (SharedPreferencesManager.HasKey(MainActivity.this, "button_ads_key")) {
                        Constants.BUTTON_ADS = false;
                        SharedPreferencesManager.setSomeBoolValue(MainActivity.this, "button_ads_key", false);
                    }
                }
                if (MainActivity.this.remoteConfig.getBoolean("page_scroll_ad")) {
                    Constants.PAGE_COUNT = new Long(MainActivity.this.remoteConfig.getLong("page_count")).intValue();
                }
                if (MainActivity.this.remoteConfig.getBoolean("export_screen_bottom_ads")) {
                    Constants.EXPORT_SCREEN_BOTTOM_ADS = true;
                    SharedPreferencesManager.setSomeBoolValue(MainActivity.this, "export_bottom_ads_key", true);
                    return;
                }
                Constants.EXPORT_SCREEN_BOTTOM_ADS = false;
                SharedPreferencesManager.setSomeBoolValue(MainActivity.this, "export_bottom_ads_key", false);
            }
        });
    }

    public void initializedAdSdk(Context context) {
        MoPub.setLocationAwareness(MoPub.LocationAwareness.TRUNCATED);
        MoPub.setLocationPrecision(4);
        new Bundle().putString("npa", "1");
        HashMap hashMap = new HashMap();
        hashMap.put("npa", "1");
       // this.sdkConfiguration = new SdkConfiguration.Builder("2e146683a4354c879a956d6c4d4438eb").withMediatedNetworkConfiguration(GooglePlayServicesAdapterConfiguration.class.getName(), hashMap).build();
        MoPub.initializeSdk(context, this.sdkConfiguration, initSdkListener());
        this.mConsentStatusChangeListener = initConsentChangeListener();
        this.mPersonalInfoManager = MoPub.getPersonalInformationManager();
        if (this.mPersonalInfoManager != null) {
            this.mPersonalInfoManager.subscribeConsentStatusChangeListener(this.mConsentStatusChangeListener);
        }
    }

    private SdkInitializationListener initSdkListener() {
        return new SdkInitializationListener() {
            public void onInitializationFinished() {
                if (MainActivity.this.mPersonalInfoManager != null && MainActivity.this.mPersonalInfoManager.shouldShowConsentDialog()) {
                    MainActivity.this.mPersonalInfoManager.loadConsentDialog(MainActivity.this.initDialogLoadListener());
                }
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        if (!MoPub.isSdkInitialized()) {
                            handler.postDelayed(this, 100);
                        } else if (!Constants.REMOVE_ADS) {
                            AdUtil.getInstance().initializeAd(MainActivity.this);
                            NativeAdUtil.getInstance().initializeAds(MainActivity.this);
                            Log.d("CheckTask", "initializeAd");
                            MainActivity.this.moreApps = new MoPubInterstitial(MainActivity.this, "5ded99ea4f224e5c9663b6a2a8c246af");
                            MainActivity.this.moreApps.setInterstitialAdListener(new MoPubInterstitial.InterstitialAdListener() {
                                public void onInterstitialClicked(MoPubInterstitial moPubInterstitial) {
                                }

                                public void onInterstitialFailed(MoPubInterstitial moPubInterstitial, MoPubErrorCode moPubErrorCode) {
                                }

                                public void onInterstitialLoaded(MoPubInterstitial moPubInterstitial) {
                                }

                                public void onInterstitialShown(MoPubInterstitial moPubInterstitial) {
                                }

                                public void onInterstitialDismissed(MoPubInterstitial moPubInterstitial) {
                                    MainActivity.this.moreApps.load();
                                }
                            });
                            MainActivity.this.moreApps.load();
                            handler.removeCallbacks(this);
                        }
                        Log.d("Continous", "SDKInitialize");
                    }
                }, 100);
            }
        };
    }

    private ConsentStatusChangeListener initConsentChangeListener() {
        return new ConsentStatusChangeListener() {
            public void onConsentStateChange(@NonNull ConsentStatus consentStatus, @NonNull ConsentStatus consentStatus2, boolean z) {
                if (MainActivity.this.mPersonalInfoManager != null && MainActivity.this.mPersonalInfoManager.shouldShowConsentDialog()) {
                    MainActivity.this.mPersonalInfoManager.loadConsentDialog(MainActivity.this.initDialogLoadListener());
                }
            }
        };
    }

    /* access modifiers changed from: private */
    public ConsentDialogListener initDialogLoadListener() {
        return new ConsentDialogListener() {
            public void onConsentDialogLoadFailed(@NonNull MoPubErrorCode moPubErrorCode) {
            }

            public void onConsentDialogLoaded() {
                if (MainActivity.this.mPersonalInfoManager != null) {
                    MainActivity.this.mPersonalInfoManager.showConsentDialog();
                }
            }
        };
    }

    /* access modifiers changed from: package-private */
    public void SplashScreen() {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                MainActivity.this.splashLayout.setVisibility(View.GONE);
                MainActivity.this.SplashScreenGone();
                Log.d("Continous", "SplashScreen");
            }
        }, (long) Constants.SPLASH_TIME_OUT);
    }

    /* access modifiers changed from: package-private */
    public void SplashScreenGone() {
        this.menu.setOnClickListener(this);
        this.privacy_button.setOnClickListener(this);
        this.contact_button.setOnClickListener(this);
        if (this.splashScreenGoneListener != null) {
            this.splashScreenGoneListener.OnSplashScreenGone();
        }
    }

    private void getIMGSize(Uri uri2) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        if (this.capture) {
            BitmapFactory.decodeFile(this.file.getPath(), options);
            Constants.IMAGE_PATH = this.file.getPath();
            Log.d("IMGSIZE", "Camera");
        } else {
            String path = ImageFilePath.getPath(this, uri2);
            Constants.IMAGE_PATH = path;
            BitmapFactory.decodeFile(path, options);
            Log.d("IMGSIZE", "Gallery");
        }
        int i = options.outHeight;
        int i2 = options.outWidth;
        this.imageSize.setMaximumTextureSize(ImageUtils.getMaximumTextureSize());
        this.imageSize.setBitmapMaximumSize(i2, i);
        ExportDetails.getInstance().setResolutionsList(this.imageSize.getResolutions());
    }

    public void loadFragment(Fragment fragment) {
        FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
        beginTransaction.replace(R.id.frame_layout, fragment);
        beginTransaction.commit();
    }

    public static float round(float f, int i) {
        return new BigDecimal(Float.toString(f)).setScale(i, 4).floatValue();
    }

    public long getTotalMemory() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("/proc/meminfo"), 8192);
            String readLine = bufferedReader.readLine();
            String[] split = readLine.split("\\s+");
            for (String str : split) {
                Log.i(readLine, str + "\t");
            }
            long intValue = (long) (Integer.valueOf(split[1]).intValue() * 1024);
            bufferedReader.close();
            return intValue;
        } catch (IOException unused) {
            return -1;
        }
    }

    public Bitmap getFilter_drawable() {
        return this.filter_drawable;
    }

    /* access modifiers changed from: package-private */
    public void checkRam() {
        if (Build.VERSION.SDK_INT >= 16) {
            ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
            //((ActivityManager) getSystemService("activity")).getMemoryInfo(memoryInfo);
            long j = memoryInfo.totalMem;
            Log.d("Memory ", String.valueOf(j));
            this.memory = round((float) getSize(j), 2);
            if (this.memory > 10.0f) {
                this.memory /= 1000.0f;
            }
            Log.d("Memory Round", String.valueOf(this.memory));
        } else {
            this.memory = round((float) getSize(getTotalMemory()), 2);
        }
        CalculateCropSize(this.memory);
    }

    public void LogDetail(String str, String str2) {
        Log.d(str, str2);
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        Uri uri2;
        this.callbackManager.onActivityResult(i, i2, intent);
        if (!this.f4193bp.handleActivityResult(i, i2, intent)) {
            super.onActivityResult(i, i2, intent);
        }
        if (i2 == -1) {
            if (i == 1) {
                startCropActivity(intent.getData());
                this.uri = intent.getData();
                getIMGSize(this.uri);
            } else if (i == CAMERA_REQUEST) {
                if (!(this.selected == null || (uri2 = this.selected) == null)) {
                    startCropActivity(uri2);
                    getIMGSize(uri2);
                }
            } else if (i == 69) {
                handleCropData(intent);
            }
        }
        if (i2 == 96) {
            Throwable error = UCrop.getError(intent);
            if (error != null) {
                Log.e("CropError", "handleCropError: ", error);
                Toast.makeText(this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
            Log.d("face", "image crop not");
        }
    }

    private void RequestMultiplePermission() {
        ActivityCompat.requestPermissions(this, new String[]{"android.permission.CAMERA", "android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE"}, 7);
    }

    public void ReplaceFragment(Fragment fragment, String str) {
        FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
        beginTransaction.replace(R.id.frame_layout, fragment);
        if (str != null) {
            beginTransaction.addToBackStack(str);
        } else {
            beginTransaction.addToBackStack((String) null);
        }
        beginTransaction.commitAllowingStateLoss();
        this.mDrawerLayout.setDrawerLockMode(mDrawerLayout.getDrawerLockMode(null));
        this.menu.setEnabled(false);
    }

    public void UnlockMenu() {
        this.menu.setEnabled(true);
        this.mDrawerLayout.setDrawerLockMode(mDrawerLayout.getDrawerLockMode(null));
        UpdateLabelCreditAmount();
    }

    private void startCropActivity(@NonNull Uri uri2) {
        if (this.mainUri != null) {
            this.mainUri = null;
            this.mainUri = uri2;
        } else {
            this.mainUri = uri2;
        }
        UCrop of = UCrop.of(uri2, Uri.fromFile(new File(getCacheDir(), SAMPLE_CROPPED_IMAGE_NAME)));
        UCrop.Options options = new UCrop.Options();
        options.setCompressionFormat(Bitmap.CompressFormat.JPEG);
        options.setCompressionQuality(100);
        of.withOptions(options);
        of.withMaxResultSize(this.imageSize.getImageCrop_Width(), this.imageSize.getImageCrop_Height());
        of.start(this);
    }

    private void handleCropData(Intent intent) {
        this.resultUri = UCrop.getOutput(intent);
        Intent intent2 = new Intent(this, PhotoActivity.class);
        intent2.putExtra("imageUri", this.resultUri.toString());
        startActivity(intent2);
    }

    public void SelectImage() {
        this.capture = false;
        if (Build.VERSION.SDK_INT < 23) {
            Intent intent = new Intent("android.intent.action.GET_CONTENT");
            intent.setType("image/*");
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
        } else if (CheckingPermissionIsEnabledOrNot()) {
            Intent intent2 = new Intent("android.intent.action.GET_CONTENT");
            intent2.setType("image/*");
            startActivityForResult(Intent.createChooser(intent2, "Select Picture"), 1);
        } else {
            RequestMultiplePermission();
        }
    }

    public void CapturePhoto() {
        this.capture = true;
        if (Build.VERSION.SDK_INT >= 23) {
            checkCameraPermission();
        } else {
            takePicture();
        }
    }

    /* access modifiers changed from: package-private */
    public void checkCameraPermission() {
        if (CheckingPermissionIsEnabledOrNot()) {
            takePicture();
        } else {
            RequestMultiplePermission();
        }
    }

    /* access modifiers changed from: package-private */
    public void takePicture() {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        File file2 = new File(Environment.getExternalStorageDirectory(), "POST_IMAGE.jpg");
        if (Build.VERSION.SDK_INT >= 24) {
            this.selected = FileProvider.getUriForFile(this, "com.hqgames.pencil.sketch.photo.provider", file2);
        } else {
            this.selected = Uri.fromFile(file2);
        }
        intent.putExtra("output", this.selected);
        intent.addFlags(FILL_IN_ACTION);
        startActivityForResult(intent, CAMERA_REQUEST);
        this.file = new File(file2.getPath());
    }

    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        if (i == 7 && iArr.length > 0) {
            boolean z = false;
            boolean z2 = iArr[0] == 0;
            boolean z3 = iArr[1] == 0;
            if (iArr[2] == 0) {
                z = true;
            }
            if (!z2 || !z || !z3) {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_LONG).show();
            } else if (this.capture) {
                takePicture();
            } else {
                Intent intent = new Intent("android.intent.action.GET_CONTENT");
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
            }
        }
    }

    public boolean CheckingPermissionIsEnabledOrNot() {
        return ContextCompat.checkSelfPermission(getApplicationContext(), "android.permission.CAMERA") == 0 && ContextCompat.checkSelfPermission(getApplicationContext(), "android.permission.WRITE_EXTERNAL_STORAGE") == 0 && ContextCompat.checkSelfPermission(getApplicationContext(), "android.permission.READ_EXTERNAL_STORAGE") == 0;
    }

    /* access modifiers changed from: package-private */
    public void CalculateCropSize(float f) {
        if (f <= 1.0f || (f >= 1.0f && ((double) f) <= 1.5d)) {
            this.imageSize.calculateCropSize(ImageSize.Memory.MEDIUMLOW);
            Log.d("Memory", "1");
            return;
        }
        double d = (double) f;
        if (d >= 1.5d && f <= 2.0f) {
            this.imageSize.calculateCropSize(ImageSize.Memory.MEDIUM);
            Log.d("Memory", "2");
        } else if (f >= 2.0f && d <= 3.5d) {
            this.imageSize.calculateCropSize(ImageSize.Memory.HIGHLOW);
            Log.d("Memory", ExifInterface.GPS_MEASUREMENT_3D);
        } else if (d >= 3.5d) {
            this.imageSize.calculateCropSize(ImageSize.Memory.HIGH);
            Log.d("Memory", "4");
        } else {
            this.imageSize.calculateCropSize(ImageSize.Memory.MEDIUM);
            Log.d("Memory", "else");
        }
    }

    public boolean isInternetAvailable() {
        try {
            return !InetAddress.getByName("google.com").equals("");
        } catch (Exception unused) {
            return false;
        }
    }

   /* public int getAllowAllocatedMemory() {
        return ((ActivityManager) getSystemService("activity")).getMemoryClass();
    }
*/
    /* access modifiers changed from: protected */
    public void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(this).registerReceiver(this.receiver, new IntentFilter("REQUEST_ACCEPT"));
        clearCache();
        MoPub.onStart(this);
        AdUtil.getInstance().setAdClosedListener(this);
        Log.d("MainActivityMethod", "Start");
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        activityPaused();
        super.onPause();
        if (this.loggedIn) {
            disconnectFromFacebook();
        }
        MoPub.onPause(this);
        Log.d("MainActivityMethod", "Pause");
    }

    /* access modifiers changed from: protected */
    public void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(this.receiver);
        MoPub.onStop(this);
        Log.d("MainActivityMethod", "Stop");
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        activityResumed();
        AdUtil.getInstance().setAdClosedListener(this);
        super.onResume();
        if (!Constants.REMOVE_ADS && AdUtil.getInstance().isEndingAdAvailable() && Constants.PHOTOACTIVITY_OPENED) {
            Log.d("MainActivityMethod", " show ending ad");
            ShowEndingAd();
            Constants.PHOTOACTIVITY_OPENED = false;
        }
        MoPub.onResume(this);
        if (this.purchase_iap) {
            UpdateLabelCreditAmount();
            if (this.fragmentUpdateListener != null) {
                this.fragmentUpdateListener.OnFragmentUpdate();
            }
            this.purchase_iap = false;
        }
        if (this.isCropActivityLaunch) {
            this.isCropActivityLaunch = false;
        }
        if (this.rating_view_open && !this.marketOpen) {
            SharedPreferencesManager.setSomeBoolValue(this, Constants.APP_RATED, true);
        }
        Log.d("MainActivityMethod", "Resume");
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        if (this.f4193bp != null) {
            this.f4193bp.release();
        }
        if (this.mPersonalInfoManager != null) {
            this.mPersonalInfoManager.unsubscribeConsentStatusChangeListener(this.mConsentStatusChangeListener);
        }
        this.mConsentStatusChangeListener = null;
        if (this.loggedIn) {
            disconnectFromFacebook();
        }
        if (this.moreApps != null) {
            this.moreApps.destroy();
        }
        AdUtil.getInstance().reset();
        MoPub.onDestroy(this);
        Constants.START_UP_AD = true;
        this.removeAdsListener = null;
        Constants.SHOW_DIALOG_RATE_VIEW = true;
        if (Constants.NATIVE_FULL_SCREEN_SHOWN) {
            Constants.NATIVE_FULL_SCREEN_SHOWN = false;
        }
        if (Constants.ED_NATIVE_FULL_SCREEN_SHOWN) {
            Constants.ED_NATIVE_FULL_SCREEN_SHOWN = false;
        }
        Constants.SALE_IS_ON = false;
        Constants.CUSTOM_REMOVE_ADS_BUTTON_ENABLED = false;
        Constants.CUSTOM_PURCHASE_BUTTON_ENABLED = false;
        Constants.SHOW_CUSTOM_RATE = false;
        Constants.CUSTOM_EXPORT_FUNTION_BUTTON_ENABLED = false;
        Constants.CUSTOM_EDITING_SCREEN_ENABLED = false;
        Constants.IMAGE_SAVE_AD = true;
        Constants.EFFECT_AD_COUNT = 0;
        Constants.ENDING_AD_SHOWN = false;
        Constants.BOTTOM_NATIVE_AD_SHOW = false;
        Constants.SAVE_INCREMENT = 0;
        super.onDestroy();
        Log.d("MainActivity", "Destroy");
    }

    public void disconnectFromFacebook() {
        if (AccessToken.getCurrentAccessToken() != null) {
            new GraphRequest(AccessToken.getCurrentAccessToken(), "/me/permissions/", (Bundle) null, HttpMethod.DELETE, new GraphRequest.Callback() {
                public void onCompleted(GraphResponse graphResponse) {
                    LoginManager.getInstance().logOut();
                }
            }).executeAsync();
            this.loggedIn = false;
        }
    }

    /* access modifiers changed from: package-private */
    public void clearCache() {
        File file2 = new File(Utils.getCacheDirectory(this, Constants.CACHE_DIRECTORY).getPath());
        if (file2.exists()) {
            File[] listFiles = file2.listFiles();
            for (File delete : listFiles) {
                delete.delete();
            }
        }
    }

    public List<ImageResolutions> getResolutions() {
        return this.imageSize.getResolutions();
    }

    public void Clear() {
        this.export = null;
        this.editingFragmentDetails = null;
        if (this.bitmap != null) {
            this.bitmap = null;
        }
    }

    public void OnAdClosedListener() {
        final Handler handler2 = new Handler();
        handler2.postDelayed(new Runnable() {
            public void run() {
                if (MainActivity.this.loadingScreen.getVisibility() == View.VISIBLE) {
                    handler2.postDelayed(this, 50);
                    MainActivity.this.loadingScreen.setVisibility(View.GONE);
                    handler2.removeCallbacks(this);
                } else {
                    handler2.removeCallbacks(this);
                    MainActivity.this.loadingScreen.setVisibility(View.GONE);
                }
                Log.d("Continous", "AdCloseListener");
            }
        }, 50);
    }

    private void RemoveWaiteScreen() {
        RemoveLoadingScreen("Please Wait...");
        final Handler handler2 = new Handler();
        handler2.postDelayed(new Runnable() {
            public void run() {
                if (MainActivity.this.loadingScreen.getVisibility() == View.VISIBLE) {
                    handler2.postDelayed(this, 50);
                } else {
                    if (MainActivity.this.waitScreenGoneListener != null) {
                        MainActivity.this.waitScreenGoneListener.OnWaitScreenGone();
                    }
                    handler2.removeCallbacks(this);
                }
                Log.d("Continous", "AdCloseListener");
            }
        }, 50);
    }

    public void ShowFullScreenAd(String str, boolean z, boolean z2) {
        if (this.loadingScreen == null) {
            this.loadingScreen = (RelativeLayout) findViewById(R.id.loading_screen);
        }
        if (!Constants.REMOVE_ADS) {
            if (str != null) {
                this.loadingScreen.setVisibility(View.VISIBLE);
            } else {
                this.loadingScreen.setVisibility(View.VISIBLE);
            }
            ShowStartUpAd(z);
        }
    }

    public void ShowEndingAd() {
        if (this.loadingScreen == null) {
            this.loadingScreen = (RelativeLayout) findViewById(R.id.loading_screen);
        }
        this.loadingScreen.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                AdUtil.getInstance().ShowEndingInterstitial();
            }
        }, 500);
    }

    public void AdWaitScreen() {
        if (this.loadingScreen == null) {
            this.loadingScreen = (RelativeLayout) findViewById(R.id.loading_screen);
        }
        if (!Constants.REMOVE_ADS) {
            this.loadingScreen.setVisibility(View.VISIBLE);
            showExportAd();
        }
    }

    public void showExportAd() {
        if (this.loadingScreen == null) {
            this.loadingScreen = (RelativeLayout) findViewById(R.id.loading_screen);
        }
        new Handler().postDelayed(new Runnable() {
            public void run() {
                if (!AdUtil.getInstance().isExportAdAvailable()) {
                    return;
                }
                if (MainActivity.isActivityVisible()) {
                    AdUtil.getInstance().ShowExportInterstitial();
                } else {
                    boolean unused = MainActivity.this.waitscreen_visible = true;
                }
            }
        }, 1000);
    }

    public void showFloatingFBNativeAd(NativeAd nativeAd2, boolean z) {
        if (this.adView != null) {
            this.adView = null;
        }
        if (!Constants.REMOVE_ADS) {
            final Dialog dialog = new Dialog(this, R.style.VideAdDialogTheme);
            dialog.getWindow().setDimAmount(0.0f);
            dialog.setContentView(R.layout.activity_dialog_full);
            this.floating_native_layout = (RelativeLayout) dialog.findViewById(R.id.floating_native_ad);
            this.floating_ad_background = (RelativeLayout) dialog.findViewById(R.id.floating_native_ad_background);
            this.close_ad = (RelativeLayout) dialog.findViewById(R.id.ad_close_button);
            //this.nativeAdLayout = (NativeAdLayout) dialog.findViewById(R.id.native_ad_container);
            if (z) {
                this.floating_ad_background.setVisibility(View.VISIBLE);
            } else {
                this.floating_ad_background.setVisibility(View.GONE);
            }
            //inflateAd(nativeAd2, this.nativeAdLayout);
            if (this.adView != null) {
                this.adView.startAnimation(this.slideAnimation);
            }
            dialog.show();
            this.close_ad.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    if (dialog.isShowing()) {
                        dialog.dismiss();
                    }
                    if (MainActivity.this.waitScreenGoneListener != null) {
                        MainActivity.this.waitScreenGoneListener.OnWaitScreenGone();
                    }
                }
            });
            Constants.ED_NATIVE_FULL_SCREEN_SHOWN = true;
        }
    }

    public void showFloatingMopubNativeAd(com.mopub.nativeads.NativeAd nativeAd2, boolean z) {
        Log.d("Native", "Full Screen Show");
        if (!Constants.REMOVE_ADS) {
            final Dialog dialog = new Dialog(this, R.style.VideAdDialogTheme);
            dialog.getWindow().setDimAmount(0.0f);
            dialog.setContentView(R.layout.activity_dialog_full);
            this.floating_native_layout = (RelativeLayout) dialog.findViewById(R.id.floating_native_ad);
            this.floating_ad_background = (RelativeLayout) dialog.findViewById(R.id.floating_native_ad_background);
            this.close_ad = (RelativeLayout) dialog.findViewById(R.id.ad_close_button);
            RelativeLayout relativeLayout = (RelativeLayout) dialog.findViewById(R.id.container);
            if (z) {
                this.floating_ad_background.setVisibility(View.VISIBLE);
            } else {
                this.floating_ad_background.setVisibility(View.GONE);
            }
           // this.adView = (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.main_screen_mopub_native_ad_layout, relativeLayout, false);
            nativeAd2.renderAdView(this.adView);
            nativeAd2.prepare(this.adView);
            relativeLayout.addView(this.adView);
            this.adView.startAnimation(this.slideAnimation);
            dialog.show();
            this.close_ad.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    if (dialog.isShowing()) {
                        dialog.dismiss();
                    }
                }
            });
        }
    }

    public void showFloatingAdmobNativeAd(UnifiedNativeAd unifiedNativeAd, boolean z) {
        Log.d("Native", "Full Screen Show");
        if (!Constants.REMOVE_ADS) {
            final Dialog dialog = new Dialog(this, R.style.VideAdDialogTheme);
            dialog.getWindow().setDimAmount(0.0f);
            dialog.setContentView(R.layout.activity_dialog_full);
            this.floating_native_layout = (RelativeLayout) dialog.findViewById(R.id.floating_native_ad);
            this.floating_ad_background = (RelativeLayout) dialog.findViewById(R.id.floating_native_ad_background);
            this.close_ad = (RelativeLayout) dialog.findViewById(R.id.ad_close_button);
            this.mopub_container = (RelativeLayout) dialog.findViewById(R.id.container);
            if (z) {
                this.floating_ad_background.setVisibility(View.VISIBLE);
            } else {
                this.floating_ad_background.setVisibility(View.GONE);
            }
            View inflate = LayoutInflater.from(this).inflate(R.layout.admob_native_ad_layout, (ViewGroup) null);
            UnifiedNativeAdView unifiedNativeAdView = (UnifiedNativeAdView) inflate.findViewById(R.id.admob_native);
            populateUnifiedNativeAdView(unifiedNativeAd, unifiedNativeAdView);
            this.mopub_container.addView(inflate);
            unifiedNativeAdView.startAnimation(this.slideAnimation);
            dialog.show();
            this.close_ad.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    if (dialog.isShowing()) {
                        dialog.dismiss();
                    }
                }
            });
        }
    }

    public void infilateAdmobNativeAd(UnifiedNativeAd unifiedNativeAd) {
        if (!Constants.REMOVE_ADS) {
            View inflate = LayoutInflater.from(this).inflate(R.layout.admob_native_ad_layout, (ViewGroup) null);
            UnifiedNativeAdView unifiedNativeAdView = (UnifiedNativeAdView) inflate.findViewById(R.id.admob_native);
            populateUnifiedNativeAdView(unifiedNativeAd, unifiedNativeAdView);
            this.mopub_container.addView(inflate);
            unifiedNativeAdView.startAnimation(this.slideAnimation);
        }
    }

    public void LoadMopubNative() {
        if (MoPub.isSdkInitialized()) {
            this.moPubNative.makeRequest();
        }
    }

    private void populateUnifiedNativeAdView(UnifiedNativeAd unifiedNativeAd, UnifiedNativeAdView unifiedNativeAdView) {
        if (this.mopub_container.getChildCount() >= 0) {
            this.mopub_container.removeAllViews();
        }
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

    public void RemoveLoadingScreen(String str) {
        if (this.loadingScreen == null) {
            this.loadingScreen = (RelativeLayout) findViewById(R.id.loading_screen);
        }
        this.loadingScreen = (RelativeLayout) findViewById(R.id.loading_screen);
        this.loadingScreen.setVisibility(View.GONE);
    }

    public void ShowStartUpAd(final boolean z) {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                if (z) {
                    if (AdUtil.getInstance().isStartUpAdAvailable() && Constants.START_UP_AD) {
                        if (MainActivity.isActivityVisible()) {
                            AdUtil.getInstance().ShowStartUpInterstitial();
                        } else {
                            boolean unused = MainActivity.this.waitscreen_visible = true;
                        }
                        Constants.START_UP_AD = false;
                    }
                } else if (AdUtil.getInstance().isEndingAdAvailable()) {
                    if (MainActivity.isActivityVisible()) {
                        AdUtil.getInstance().ShowEndingInterstitial();
                    } else {
                        boolean unused2 = MainActivity.this.waitscreen_visible = true;
                    }
                }
                Constants.CLOSE_LOADING_SCREEN = true;
            }
        }, 2500);
    }

    public void ShareFacebook(Bitmap bitmap2) {
        SharePhotoContent build = new SharePhotoContent.Builder().addPhoto(new SharePhoto.Builder().setBitmap(bitmap2).build()).build();
        this.fb_image_share = true;
        if (ShareDialog.canShow(ShareLinkContent.class)) {
            this.shareDialog.show(build);
        }
    }

    public void onProductPurchased(@NonNull String str, @Nullable TransactionDetails transactionDetails) {
        if (str.equals(Constants.BRONZE_CREDIT_PACK)) {
            if (!SharedPreferencesManager.HasKey(this, Constants.REMOVE_ADS_KEY)) {
                SharedPreferencesManager.setSomeBoolValue(this, Constants.REMOVE_ADS_KEY, true);
            }
            Credits.addCredits(this, SharedPreferencesManager.getSomeIntValue(this, Constants.BRONZE_PACK));
            Constants.REMOVE_ADS = true;
            this.f4193bp.consumePurchase(str);
        } else if (str.equals(Constants.SILVER_CREDIT_PACK)) {
            if (!SharedPreferencesManager.HasKey(this, Constants.REMOVE_ADS_KEY)) {
                SharedPreferencesManager.setSomeBoolValue(this, Constants.REMOVE_ADS_KEY, true);
            }
            Credits.addCredits(this, SharedPreferencesManager.getSomeIntValue(this, Constants.SILVER_PACK));
            Constants.REMOVE_ADS = true;
            this.f4193bp.consumePurchase(str);
        } else if (str.equals(Constants.GOLD_CREDIT_PACK)) {
            if (!SharedPreferencesManager.HasKey(this, Constants.REMOVE_ADS_KEY)) {
                SharedPreferencesManager.setSomeBoolValue(this, Constants.REMOVE_ADS_KEY, true);
            }
            Credits.addCredits(this, SharedPreferencesManager.getSomeIntValue(this, Constants.GOLD_PACK));
            Constants.REMOVE_ADS = true;
            this.f4193bp.consumePurchase(str);
        } else if (str.equals(Constants.SALE_GOLD_CREDIT_PACK)) {
            if (!SharedPreferencesManager.HasKey(this, Constants.REMOVE_ADS_KEY)) {
                SharedPreferencesManager.setSomeBoolValue(this, Constants.REMOVE_ADS_KEY, true);
            }
            Credits.addCredits(this, Constants.SALE_GOLD_PACK);
            Constants.REMOVE_ADS = true;
            this.f4193bp.consumePurchase(str);
        } else if (str.equals(Constants.SALE_SILVER_CREDIT_PACK)) {
            if (!SharedPreferencesManager.HasKey(this, Constants.REMOVE_ADS_KEY)) {
                SharedPreferencesManager.setSomeBoolValue(this, Constants.REMOVE_ADS_KEY, true);
            }
            int i = Constants.SALE_SILVER_PACK;
            Credits.addCredits(this, i);
            Log.d("CreditsPack", String.valueOf(i));
            Constants.REMOVE_ADS = true;
            this.f4193bp.consumePurchase(str);
        } else if (str.equals(Constants.SALE_BRONZE_CREDIT_PACK)) {
            if (!SharedPreferencesManager.HasKey(this, Constants.REMOVE_ADS_KEY)) {
                SharedPreferencesManager.setSomeBoolValue(this, Constants.REMOVE_ADS_KEY, true);
            }
            Credits.addCredits(this, Constants.SALE_BRONZE_PACK);
            Constants.REMOVE_ADS = true;
            this.f4193bp.consumePurchase(str);
        } else if (str.equals(Constants.REMOVE_ADS_ID)) {
            if (!SharedPreferencesManager.HasKey(this, Constants.REMOVE_ADS_KEY)) {
                SharedPreferencesManager.setSomeBoolValue(this, Constants.REMOVE_ADS_KEY, true);
            }
            Constants.REMOVE_ADS = true;
            this.removeAdsListener.OnRemoveAds();
        } else if (str.equals(Constants.HD_PACK)) {
            Constants.REMOVE_ADS = true;
            Constants.HD_PACK_PURCHASED = true;
            SharedPreferencesManager.setSomeBoolValue(this, Constants.HD_PACK_PURCHASED_KEY, true);
            SharedPreferencesManager.setSomeBoolValue(this, Constants.REMOVE_ADS_KEY, true);
            this.removeAdsListener.OnRemoveAds();
            Bundle bundle = new Bundle();
            bundle.putString("hd_pack_purchased", str);
            FireBaseHelper.getInstance().LogEvent("Hd_Pack_Purchased", bundle);
        }
        Bundle bundle2 = new Bundle();
        bundle2.putString("iap_purchased", str);
        FireBaseHelper.getInstance().LogEvent("IAP_Analytics", bundle2);
        this.purchase_iap = true;
    }

    public void onPurchaseHistoryRestored() {
        Log.d("RemoveAds", "restored");
        for (String next : this.f4193bp.listOwnedProducts()) {
            if (next.equals(Constants.BRONZE_CREDIT_PACK) || next.equals(Constants.SILVER_CREDIT_PACK) || next.equals(Constants.GOLD_CREDIT_PACK) || next.equals(Constants.REMOVE_ADS_ID) || next.equals(Constants.SALE_GOLD_CREDIT_PACK) || next.equals(Constants.SALE_BRONZE_CREDIT_PACK) || next.equals(Constants.SALE_SILVER_CREDIT_PACK) || next.equals(Constants.HD_PACK)) {
                if (next.equals(Constants.HD_PACK)) {
                    Constants.REMOVE_ADS = true;
                    Constants.HD_PACK_PURCHASED = true;
                    SharedPreferencesManager.setSomeBoolValue(this, Constants.HD_PACK_PURCHASED_KEY, true);
                    SharedPreferencesManager.setSomeBoolValue(this, Constants.REMOVE_ADS_KEY, true);
                    this.removeAdsListener.OnRemoveAds();
                    Log.d("RemoveAds", "hdPack");
                } else {
                    Constants.REMOVE_ADS = true;
                    SharedPreferencesManager.setSomeBoolValue(this, Constants.REMOVE_ADS_KEY, true);
                    this.removeAdsListener.OnRemoveAds();
                }
                Log.d("RemoveAds", "hdPack");
            }
        }
    }

    public void onBillingError(int i, @Nullable Throwable th) {
        Log.d("RemoveAds", "error");
    }

    public void onBillingInitialized() {
        this.readyToPurchase = true;
        Log.d("RemoveAds", "initialized");
    }

    public void ShowLoadingScreen(String str) {
        if (str != null) {
            ((TextView) this.loadingScreen.findViewById(R.id.loading_text)).setText(str);
            this.loadingScreen.setVisibility(View.VISIBLE);
            return;
        }
        this.loadingScreen.setVisibility(View.VISIBLE);
    }

    public void fbLike() {
        LoginManager.getInstance().logInWithReadPermissions((Activity) this, (Collection<String>) Arrays.asList(new String[]{"email"}));
    }

    public void fbShare() {
        this.shareDialog.show(((ShareLinkContent.Builder) new ShareLinkContent.Builder().setContentUrl(Uri.parse("https://play.google.com/store/apps/details?id=com.hqgames.pencil.sketch.photo"))).build());
    }

    public void moreApps() {
        String str = Constants.MORE_APP_PROMOTION_PACKAGE_ID;
        Bundle bundle = new Bundle();
        bundle.putString("AdNetwork", "More_Apps_btn");
        FireBaseHelper.getInstance().LogEvent("Ad_Analytics", bundle);
        if (Constants.CROSS_PROMOTION && str != null) {
            Log.d("moreapp", "notnull");
            try {
                startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=" + str)));
            } catch (ActivityNotFoundException unused) {
                startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=" + str)));
            }
        } else if (this.moreApps == null || !this.moreApps.isReady() || Constants.REMOVE_ADS) {
            if (str != null) {
                Log.d("moreapp", "notnulll");
                try {
                    startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=" + str)));
                } catch (ActivityNotFoundException unused2) {
                    startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=" + str)));
                }
            } else {
                try {
                    startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=hqgames.cartoon.photo.picture.converter.editor.app")));
                } catch (ActivityNotFoundException unused3) {
                    startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=hqgames.cartoon.photo.picture.converter.editor.app")));
                }
            }
        } else if (isActivityVisible()) {
            this.moreApps.show();
        }
    }

    public void rateUs() {
        TwoStageRate with = TwoStageRate.with(this);
        with.setInstallDays(0).setEventsTimes(0).setLaunchTimes(0);
        with.resetOnDismiss(true).resetOnFeedBackDeclined(true).resetOnRatingDeclined(true);
        with.setShowAppIcon(true);
        if (!with.isDialogPrompted()) {
            with.showIfMeetsConditions();
        } else {
            String packageName = getPackageName();
            try {
                startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=" + packageName)));
            } catch (ActivityNotFoundException unused) {
                startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=" + packageName)));
            }
        }
        with.setFeedbackReceivedListener(new FeedbackReceivedListener() {
            public void onFeedbackReceived(String str) {
            }
        });
        with.setFeedbackWithRatingReceivedListener(new FeedbackWithRatingReceivedListener() {
            public void onFeedbackReceived(float f, String str) {
                Intent intent = new Intent("android.intent.action.SEND");
                intent.putExtra("android.intent.extra.EMAIL", new String[]{"hqgames1080@gmail.com"});
                intent.putExtra("android.intent.extra.SUBJECT", "Pencil Photo Sketch:FeedBack");
                intent.putExtra("android.intent.extra.TEXT", "Rating : " + f + "  Feedback :" + str);
                intent.setType("message/rfc822");
                MainActivity.this.startActivity(Intent.createChooser(intent, "Select Email Sending App :"));
            }
        });
    }

    public void onBackPressed() {
        if (this.loadingScreen == null || this.loadingScreen.getVisibility() != View.VISIBLE || !Constants.CLOSE_LOADING_SCREEN) {
            Log.d("onback", "enter");
            if (Constants.FRAGMENT_VISIBLE) {
                Log.d("onback", "frag visible");
                if (Constants.SHOW_CUSTOM_RATE && !SharedPreferencesManager.HasKey(this, "custom_rate_show") && !this.rating_view_open && !SharedPreferencesManager.HasKey(this, Constants.APP_RATED) && SharedPreferencesManager.HasKey(this, Constants.INAPP_RATING_VIEW_LIMIT_REACHED)) {
                    this.rating_view.setVisibility(View.VISIBLE);
                    Bundle bundle = new Bundle();
                    bundle.putString("Rater_View_Show", "Exit_App");
                    FireBaseHelper.getInstance().LogEvent("App_Rater", bundle);
                    if (this.customScreenShowListener != null) {
                        this.customScreenShowListener.OnCustomScreenShow();
                    }
                    this.rating_view_open = true;
                    Log.d("onback", "custom rate");
                } else if (Constants.SHOW_CUSTOM_RATE && SharedPreferencesManager.HasKey(this, "custom_rate_show") && !SharedPreferencesManager.HasKey(this, Constants.APP_RATED) && SharedPreferencesManager.HasKey(this, Constants.INAPP_RATING_VIEW_LIMIT_REACHED)) {
                    Log.d("onback", "custom rate show");
                    if (!CustomAppRater.initialize(this).isOverRemindDate() || this.rating_view_open) {
                        finish();
                        return;
                    }
                    Bundle bundle2 = new Bundle();
                    bundle2.putString("Rater_View_Show", "Exit_App");
                    FireBaseHelper.getInstance().LogEvent("App_Rater", bundle2);
                    this.rating_view.setVisibility(View.VISIBLE);
                    if (this.customScreenShowListener != null) {
                        this.customScreenShowListener.OnCustomScreenShow();
                    }
                    this.rating_view_open = true;
                    Log.d("onback", "is over");
                } else if (this.rating_view_open) {
                    finish();
                    CustomAppRater.initialize(this).setRemindInterval((Context) this);
                } else {
                    finish();
                }
            } else {
                super.onBackPressed();
            }
        } else {
            this.loadingScreen.setVisibility(View.GONE);
        }
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ad_close_button:
                this.floating_native_layout.setVisibility(View.GONE);
                final Handler handler2 = new Handler();
                handler2.postDelayed(new Runnable() {
                    public void run() {
                        if (MainActivity.this.floating_native_layout.getVisibility() == View.VISIBLE) {
                            handler2.postDelayed(this, 50);
                        } else {
                            if (MainActivity.this.waitScreenGoneListener != null) {
                                MainActivity.this.waitScreenGoneListener.OnWaitScreenGone();
                            }
                            handler2.removeCallbacks(this);
                        }
                        Log.d("Continous", "AdCloseListener");
                    }
                }, 50);
                return;
            case R.id.cancel_button_rateView:
                SharedPreferencesManager.setSomeBoolValue(this, "custom_rate_show", true);
                CustomAppRater.initialize(this).setRemindInterval((Context) this);
                finish();
                return;
            case R.id.contact_button:
                Intent intent = new Intent("android.intent.action.SEND");
                intent.putExtra("android.intent.extra.EMAIL", new String[]{"hqgames1080@gmail.com"});
                intent.putExtra("android.intent.extra.SUBJECT", "Pencil Photo Sketch:Support");
                intent.setType("message/rfc822");
                startActivity(Intent.createChooser(intent, "Select Email Sending App :"));
                return;
            case R.id.menu_icon:
                this.mDrawerLayout.openDrawer((int) GravityCompat.START);
                Log.d("ButtonClick", "Menu");
                return;
            case R.id.privacy_button:
                Log.d("ButtonClick", "Privacy");
                startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://www.iubenda.com/privacy-policy/99919093/full-legal")));
                return;
            case R.id.submit_button:
                SharedPreferencesManager.setSomeBoolValue(this, Constants.APP_RATED, true);
                this.thanksText.setVisibility(View.GONE);
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        MainActivity.this.finish();
                    }
                }, 800);
                return;
            default:
                return;
        }
    }

    public BillingProcessor getBp() {
        return this.f4193bp;
    }

    public void PurchaseProduct(String str, int i) {
        if (this.readyToPurchase) {
            this.f4193bp.purchase(this, str);
            return;
        }
        this.f4193bp = new BillingProcessor(this, this.licenseKey, this);
        this.f4193bp.initialize();
        Toast.makeText(this, "Billing not initialized. Please wait while initializing the billing.", Toast.LENGTH_SHORT).show();
    }

    public void initializeRewardDialog(int i, String str, String str2) {
        Constants.HD_PACK_PURCHASED = true;
        final Dialog dialog = new Dialog(this, R.style.VideAdDialogTheme);
        dialog.getWindow().setDimAmount(0.0f);
        dialog.setContentView(R.layout.weekly_reward_layout);
        ((TextView) dialog.findViewById(R.id.title_text)).setText(str2);
        ((TextView) dialog.findViewById(R.id.reward_text)).setText(str);
        ((Button) dialog.findViewById(R.id.yes)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                MainActivity.this.StartTimer();
                dialog.dismiss();
            }
        });
        dialog.setCancelable(true);
        dialog.show();
    }

    public void StartTimer() {
        if (this.timeInterval == 0) {
            this.timeInterval = 420;
        }
        Log.d("hdpack", "started");
        if (!SharedPreferencesManager.HasKey(this, Constants.HD_PACK_PURCHASED_KEY)) {
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    Constants.HD_PACK_PURCHASED = false;
                    Log.d("hdpack", "stopped");
                }
            }, (long) (this.timeInterval * 1000));
        }
    }

   /* public String GetToken() {
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener((Activity) this, new OnSuccessListener<InstanceIdResult>() {
            public void onSuccess(InstanceIdResult instanceIdResult) {
                String unused = MainActivity.this.newToken = instanceIdResult.getToken();
                Log.e("newToken", MainActivity.this.newToken);
            }
        });
        return this.newToken;
    }*/

    public void ShowErrorToast(String str) {
        Toast.makeText(this, str, 0).show();
    }

    public void onAnimationEnd(Animation animation) {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                MainActivity.this.close_ad.setVisibility(View.VISIBLE);
            }
        }, 500);
    }

    public void accept(Boolean bool) {
        Log.d("Internet Available   ", String.valueOf(bool));
    }

    public class GetPurchaseDetails extends AsyncTask<Void, Void, Void> {
        public GetPurchaseDetails() {
        }

        /* access modifiers changed from: protected */
        public Void doInBackground(Void... voidArr) {
            if (!MainActivity.this.f4193bp.isInitialized()) {
                return null;
            }
            MainActivity.this.bronzeDetails = MainActivity.this.f4193bp.getPurchaseListingDetails(Constants.BRONZE_CREDIT_PACK);
            MainActivity.this.silverDetails = MainActivity.this.f4193bp.getPurchaseListingDetails(Constants.SILVER_CREDIT_PACK);
            MainActivity.this.goldDetails = MainActivity.this.f4193bp.getPurchaseListingDetails(Constants.GOLD_CREDIT_PACK);
            MainActivity.this.sale_bronze = MainActivity.this.f4193bp.getPurchaseListingDetails(Constants.SALE_BRONZE_CREDIT_PACK);
            MainActivity.this.sale_silver = MainActivity.this.f4193bp.getPurchaseListingDetails(Constants.SALE_SILVER_CREDIT_PACK);
            MainActivity.this.sale_gold = MainActivity.this.f4193bp.getPurchaseListingDetails(Constants.SALE_GOLD_CREDIT_PACK);
            return null;
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Void voidR) {
            super.onPostExecute(voidR);
            if (MainActivity.this.bronzeDetails != null) {
                Constants.BRONZE_PRICE_VALUE = MainActivity.this.bronzeDetails.priceValue.doubleValue();
            }
            if (MainActivity.this.silverDetails != null) {
                Constants.SILVER_PRICE_VALUE = MainActivity.this.silverDetails.priceValue.doubleValue();
            }
            if (MainActivity.this.goldDetails != null) {
                Constants.GOLD_PRICE_VALUE = MainActivity.this.goldDetails.priceValue.doubleValue();
            }
        }
    }

   /* private void inflateAd(NativeAd nativeAd2, NativeAdLayout nativeAdLayout2) {
        nativeAd2.unregisterView();
        if (nativeAdLayout2.getChildCount() >= 0) {
            nativeAdLayout2.removeAllViews();
        }
        int i = 0;
        this.adView = (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.in_activity_fb_native_ad_layout, nativeAdLayout2, false);
        nativeAdLayout2.addView(this.adView);
        LinearLayout linearLayout = (LinearLayout) this.adView.findViewById(C3674R.C3676id.ad_choices_container);
        AdOptionsView adOptionsView = new AdOptionsView(this, nativeAd2, nativeAdLayout2);
        linearLayout.removeAllViews();
        linearLayout.addView(adOptionsView, 0);
        AdIconView adIconView = (AdIconView) this.adView.findViewById(C3674R.C3676id.native_ad_icon);
        TextView textView = (TextView) this.adView.findViewById(C3674R.C3676id.native_ad_title);
        com.facebook.ads.MediaView mediaView = (com.facebook.ads.MediaView) this.adView.findViewById(C3674R.C3676id.native_ad_media);
        TextView textView2 = (TextView) this.adView.findViewById(C3674R.C3676id.native_ad_sponsored_label);
        Button button = (Button) this.adView.findViewById(C3674R.C3676id.native_ad_call_to_action);
        textView.setText(nativeAd2.getAdvertiserName());
        ((TextView) this.adView.findViewById(C3674R.C3676id.native_ad_body)).setText(nativeAd2.getAdBodyText());
        ((TextView) this.adView.findViewById(C3674R.C3676id.native_ad_social_context)).setText(nativeAd2.getAdSocialContext());
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
    }

    public void inflateMopubNative(com.mopub.nativeads.NativeAd nativeAd2) {
        if (this.mopub_container.getChildCount() >= 0) {
            this.mopub_container.removeAllViews();
        }
     //   this.adView = (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.in_activity_mopub_native_ad_layout, this.mopub_container, false);
        nativeAd2.renderAdView(this.adView);
        nativeAd2.prepare(this.adView);
        this.mopub_container.addView(this.adView);
    }
*/
    public int dpToPx(int i) {
        return Math.round(((float) i) * this.density);
    }

    public void ShowA(String str) {
        Intent intent = new Intent(this, AdActivity.class);
        intent.putExtra("adtype", str);
        startActivity(intent);
        overridePendingTransition(R.anim.enter, R.anim.exit);
    }

    public void internetIsConnected() {
        new Thread(new Runnable() {
            public void run() {
                try {
                    HttpURLConnection httpURLConnection = (HttpURLConnection) new URL("http://web.mit.edu/").openConnection();
                    httpURLConnection.setRequestMethod(HttpRequest.METHOD_GET);
                    httpURLConnection.setConnectTimeout(5000);
                    MainActivity.this.connected = httpURLConnection.getResponseCode() == 200;
                    if (MainActivity.this.connected) {
                        Log.d("Internet", "Connected");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    class InternetCheck extends AsyncTask<Void, Void, Boolean> {
        Consumer mConsumer;

        public InternetCheck(Consumer consumer) {
            this.mConsumer = consumer;
        }

        /* access modifiers changed from: protected */
        public Boolean doInBackground(Void... voidArr) {
            try {
                Socket socket = new Socket();
                socket.connect(new InetSocketAddress("8.8.8.8", 53), 1500);
                socket.close();
                return true;
            } catch (IOException unused) {
                return false;
            }
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Boolean bool) {
            this.mConsumer.accept(bool);
        }
    }
}


