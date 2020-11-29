package ui;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.media2.exoplayer.external.trackselection.AdaptiveTrackSelection;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.SkuDetails;
import com.facebook.internal.AnalyticsEvents;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAdView;
import com.teamvinay.sketch.AppRater;
import com.teamvinay.sketch.CustomSpinner;
import com.teamvinay.sketch.EffectTask;
import com.teamvinay.sketch.FilterHolder;
import com.teamvinay.sketch.FilterType;
import com.teamvinay.sketch.MainActivity;
import com.teamvinay.sketch.R;
import com.teamvinay.sketch.ResolutionsAdapter;
import com.mopub.common.logging.MoPubLog;
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
import helper.DialogIAPItemsAdapter;
import helper.ExportDetails;
import helper.IAPView;
import helper.ImageResolutions;
import helper.Resolutions;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import listeners.ExportOptionListener;
import listeners.FragmentUpdateListener;
import listeners.PurchaseButtonListener;
import listeners.RewardAwardedListener;
import listeners.WaitScreenGoneListener;
import org.opencv.core.Mat;
import org.wysaid.nativePort.CGENativeLibrary;
import jp.co.cyberagent.android.gpuimage.GPUImage;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageBrightnessFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageContrastFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageFilterGroup;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageGaussianBlurFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageHueFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImagePixelationFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageSaturationFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageSharpenFilter;
import util.AdUtil;
import util.BitmapCache;
import util.Effect;
import util.FireBaseHelper;
import util.ImageFilePath;
import util.Resizer;
import util.SharedPreferencesManager;
import util.Utils;

/* renamed from: ui.ExportFragment */
public class ExportFragment extends Fragment implements WaitScreenGoneListener, DialogInterface.OnDismissListener, CustomSpinner.OnSpinnerEventsListener, ExportOptionListener, RewardAwardedListener, Runnable, PurchaseButtonListener, FragmentUpdateListener, MoPubView.BannerAdListener, View.OnClickListener {
    private static final String ADMOB_NATIVE_BANNER_ADUNIT_ID = "ca-app-pub-4195495625122728/8056657021";
    private static final String MOPUB_NATIVE_BANNER_ADUNIT_ID = "fdf54076878049f9a96a7f77166d143f";
    String C_Name = null;
    int LowerPrice;
    int UpperPrice;
    AdLoader adLoader;
    RelativeLayout adView;
    boolean ad_available = false;
    /* access modifiers changed from: private */
    public UnifiedNativeAd admob_nativeAd;
    boolean bannerLoaded = false;
    BitmapCache bitmapCache;
    RelativeLayout bottomLayoutContainer;
    SkuDetails bronzeDetails;
    AdLoader.Builder builder;
    String cache_file_name = null;
    List<String> cache_name = new ArrayList();
    String closeButtonText;
    String closeButtonText2;
    int credits = 35;
    private float density = 0.0f;
    Dialog dialog;
    boolean dialog_show;
    String dialog_text = null;
    String dialog_title = null;
    Bitmap effectBitmap;
    String effectName = null;
    boolean enjoyPencilPhotoSketch_2 = false;
    ExportDetails export;
    boolean feedback_2 = false;
    Uri fileUri;
    private FilterHolder filterHolder;
    RelativeLayout four_k_effect;
    boolean fresh_start = true;
    RelativeLayout full_hd_effect;
    SkuDetails goldDetails;
    GPUImageFilter gpuImageFilter;
    GPUImage gpuImg;
    Handler handler;
    boolean handler_onetime_repeat = true;
    RelativeLayout hd_effect;
    RelativeLayout hdv_effect;
    DialogIAPItemsAdapter iapItemsAdapter;
    RecyclerView iap_recycler_view;
    private ImageView imageView;
    boolean image_share = false;
    ExportOptionsItemsAdapter itemsAdapter;
    boolean loadingDialoggone = false;
    /* access modifiers changed from: private */
    public ResolutionsAdapter mAdapter;
    Context mContext;
    /* access modifiers changed from: private */
    public MoPubNative moPubNative;
    private MoPubNative.MoPubNativeNetworkListener moPubNativeNetworkListener;
    /* access modifiers changed from: private */
    public MoPubView moPubView;
    NativeAd mopubNativeAd;
    String msg_text = null;
    String msg_title = null;
    RelativeLayout nativeAdContainer;
    String okButtonText;
    String okButtonText2;
    RecyclerView recyclerView;
    RelativeLayout regular_effect;
    int requiredCredits;
    boolean rerun = true;
    String res;
    String resolution;
    /* access modifiers changed from: private */
    public ArrayList<Resolutions> resolutionArray;
    List<ImageResolutions> resolutions;
    boolean reward_awarded = false;
    List<String> sequence = new ArrayList();
    boolean share_dialog_launch = false;
    boolean sharing_done = false;
    boolean show_null_path_dialog = false;
    SkuDetails silverDetails;
    int targetLength = 0;
    RelativeLayout two_k_effect;
    HashMap<String, String> unlockFeatureList;
    List<ExportOptionsView> viewList;

    private void UnlockFeature(String str, String str2, int i) {
    }

    public void OnRewardAwarded(int i) {
    }

    public void OnRewardedVideoClosed() {
    }

    public void OnRewardedVideoLoaded() {
    }

    public void onBannerClicked(MoPubView moPubView2) {
    }

    public void onBannerCollapsed(MoPubView moPubView2) {
    }

    public void onBannerExpanded(MoPubView moPubView2) {
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
        this.density = this.mContext.getResources().getDisplayMetrics().density;
    }

    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        Log.d("ExportFragment", "Create");
        this.gpuImg = new GPUImage(getActivity());
    }

    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.export_fragment, viewGroup, false);
        AdUtil.getInstance().setRewardAwardedListener(this);
        this.unlockFeatureList = new HashMap<>();
        this.imageView = (ImageView) inflate.findViewById(R.id.gpuimage);
        this.recyclerView = (RecyclerView) inflate.findViewById(R.id.my_recycler_view);
        this.regular_effect = (RelativeLayout) inflate.findViewById(R.id.resol_norm);
        this.hd_effect = (RelativeLayout) inflate.findViewById(R.id.resol_hd);
        this.hdv_effect = (RelativeLayout) inflate.findViewById(R.id.resol_hdv);
        this.full_hd_effect = (RelativeLayout) inflate.findViewById(R.id.resol_full_hd);
        this.two_k_effect = (RelativeLayout) inflate.findViewById(R.id.resol_twoK);
        this.four_k_effect = (RelativeLayout) inflate.findViewById(R.id.resol_fourK);
        this.regular_effect.setBackgroundColor(Color.parseColor("#2196F3"));
        this.regular_effect.setOnClickListener(this);
        this.hdv_effect.setOnClickListener(this);
        this.hd_effect.setOnClickListener(this);
        this.full_hd_effect.setOnClickListener(this);
        this.two_k_effect.setOnClickListener(this);
        this.four_k_effect.setOnClickListener(this);
        this.imageView.setOnClickListener(this);
        this.bottomLayoutContainer = (RelativeLayout) inflate.findViewById(R.id.bottom_view_container);
        this.moPubView = (MoPubView) inflate.findViewById(R.id.adview);
        this.moPubView.setAdUnitId("78399486c45d43a4a014869dad876197");
        ((MainActivity) getActivity()).setWaitScreenGoneListener(this);
        if (getArguments() != null) {
            this.fileUri = Uri.parse(getArguments().getString("fileUri"));
        }
        this.fresh_start = true;
        this.handler = new Handler();
        this.viewList = new ArrayList();
        this.viewList.add(new ExportOptionsView("Save", (String) null, R.drawable.flogo/*R.drawable.baseline_save_alt_black_48*/));
        this.viewList.add(new ExportOptionsView("Share", (String) null,R.drawable.flogo /*R.drawable.baseline_share_black_48*/));
        this.viewList.add(new ExportOptionsView("Facebook", (String) null, R.drawable.flogo));
        this.viewList.add(new ExportOptionsView("Instagram", (String) null, R.drawable.insta));
        this.itemsAdapter = new ExportOptionsItemsAdapter(this.viewList, this);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        this.recyclerView.setAdapter(this.itemsAdapter);
        this.resolutions = ((MainActivity) getActivity()).getResolutions();
        ((MainActivity) getActivity()).setFragmentUpdateListener(this);
        this.cache_name = new ArrayList();
        this.export = ((MainActivity) getActivity()).getExport();
        if (this.export != null) {
            if (this.export.isFilterSelected()) {
                this.gpuImageFilter = this.export.getFilter();
            }
            if (!this.export.getSeq().isEmpty()) {
                this.sequence = this.export.getSeq();
            }
            if (this.export.isEnhanceFiltersApplied()) {
                this.filterHolder = this.export.getFilterHolder();
            }
            this.bitmapCache = this.export.getBitmapCache();
            this.export.setPath(Constants.IMAGE_PATH);
            if (this.export.getEffect_name() != null) {
                this.effectName = this.export.getEffect_name();
            }
            if (this.export.getEffect() != null) {
                this.export.getEffect().releaseMemory();
            } else {
                Effect effect = ((MainActivity) getActivity()).getEffect();
                if (effect != null) {
                    effect.releaseMemory();
                    this.export.setEffect(effect);
                }
            }
        }
        initList();
        this.res = "Regular";
        setLength(this.res);
        this.export.setCache_name(this.cache_file_name);
        this.export.setTargetLength(this.targetLength);
        if (!this.ad_available) {
            processImage();
        }
        this.mAdapter = new ResolutionsAdapter(getContext(), this.resolutionArray);
        initializeBottomView();
        Bundle bundle2 = new Bundle();
        bundle2.putString("Screen", "Export Screen");
        FireBaseHelper.getInstance().LogEvent("Screen_Analytics", bundle2);
        return inflate;
    }

    private void initializeBottomView() {
        this.builder = new AdLoader.Builder((Context) getActivity(), ADMOB_NATIVE_BANNER_ADUNIT_ID);
        this.builder.forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
            public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
                UnifiedNativeAd unused = ExportFragment.this.admob_nativeAd = unifiedNativeAd;
                ExportFragment.this.infilateAdmobNativeAd(unifiedNativeAd);
            }
        });
        this.adLoader = this.builder.withAdListener(new AdListener() {
            public void onAdFailedToLoad(int i) {
                Log.d("Native", "Banner not Loaded");
                if (Constants.REMOVE_ADS) {
                    return;
                }
                if (Constants.admob_first) {
                    ExportFragment.this.moPubNative.makeRequest();
                } else {
                    ExportFragment.this.moPubView.loadAd();
                }
            }

            public void onAdLoaded() {
                super.onAdLoaded();
                Log.d("Native", "Banner Loaded");
            }
        }).build();
        this.moPubView.setBannerAdListener(this);
        MoPubStaticNativeAdRenderer moPubStaticNativeAdRenderer = new MoPubStaticNativeAdRenderer(new ViewBinder.Builder(R.layout.native_ad_banner).titleId(R.id.native_ad_title).iconImageId(R.id.native_ad_icon).callToActionId(R.id.native_ad_call_to_action).textId(R.id.native_ad_body).privacyInformationIconImageId(R.id.native_ad_privacy_information_icon_image).build());
        this.moPubNativeNetworkListener = new MoPubNative.MoPubNativeNetworkListener() {
            public void onNativeLoad(NativeAd nativeAd) {
                Log.d(MoPubLog.LOGTAG, "Banner Native ad has loaded.");
                ExportFragment.this.mopubNativeAd = nativeAd;
                ExportFragment.this.inflateMopubNativeAd(nativeAd);
            }

            public void onNativeFail(NativeErrorCode nativeErrorCode) {
                Log.d(MoPubLog.LOGTAG, "Banner Native ad has not loaded.");
                if (Constants.REMOVE_ADS) {
                    return;
                }
                if (!Constants.admob_first) {
                    ExportFragment.this.adLoader.loadAd(new AdRequest.Builder().build());
                } else {
                    ExportFragment.this.moPubView.loadAd();
                }
            }
        };
        this.moPubNative = new MoPubNative(getActivity(), MOPUB_NATIVE_BANNER_ADUNIT_ID, this.moPubNativeNetworkListener);
        this.moPubNative.registerAdRenderer(moPubStaticNativeAdRenderer);
        if (Constants.REMOVE_ADS || !Constants.EXPORT_SCREEN_BOTTOM_ADS) {
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.bottomLayoutContainer.getLayoutParams();
            layoutParams.height = dpToPx(0, false);
            layoutParams.width = -1;
            this.bottomLayoutContainer.setLayoutParams(layoutParams);
        } else if (Constants.admob_first) {
            this.adLoader.loadAd(new AdRequest.Builder().build());
        } else {
            this.moPubNative.makeRequest();
        }
    }

    public void inflateMopubNativeAd(final NativeAd nativeAd) {
        final Handler handler2 = new Handler();
        handler2.postDelayed(new Runnable() {
            public void run() {
                if (ExportFragment.this.getActivity() != null) {
                    if (!Constants.REMOVE_ADS) {
                        handler2.removeCallbacks(this);
                        ExportFragment.this.inflateMopubNative(nativeAd);
                    }
                } else if (ExportFragment.this.rerun) {
                    handler2.postDelayed(this, AdaptiveTrackSelection.DEFAULT_MIN_TIME_BETWEEN_BUFFER_REEVALUTATION_MS);
                    ExportFragment.this.rerun = false;
                }
                Log.d("AdCustom", "inflate mopub");
            }
        }, 1500);
    }

    public void inflateMopubNative(NativeAd nativeAd) {
        this.adView = (RelativeLayout) LayoutInflater.from(getActivity()).inflate(R.layout.native_ad_banner, this.nativeAdContainer, false);
        nativeAd.renderAdView(this.adView);
        nativeAd.prepare(this.adView);
        this.bottomLayoutContainer.addView(this.adView);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.bottomLayoutContainer.getLayoutParams();
        layoutParams.height = -2;
        layoutParams.width = -2;
        this.bottomLayoutContainer.setLayoutParams(layoutParams);
    }

    public void infilateAdmobNativeAd(final UnifiedNativeAd unifiedNativeAd) {
        final Handler handler2 = new Handler();
        handler2.postDelayed(new Runnable() {
            public void run() {
                if (ExportFragment.this.getActivity() != null && !Constants.REMOVE_ADS) {
                    handler2.removeCallbacks(this);
                    View inflate = LayoutInflater.from(ExportFragment.this.getActivity()).inflate(R.layout.admob_native_banner, (ViewGroup) null);
                    ExportFragment.this.populateUnifiedNativeAdView(unifiedNativeAd, (UnifiedNativeAdView) inflate.findViewById(R.id.admob_native));
                    ExportFragment.this.bottomLayoutContainer.addView(inflate);
                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) ExportFragment.this.bottomLayoutContainer.getLayoutParams();
                    layoutParams.height = -2;
                    layoutParams.width = -2;
                    ExportFragment.this.bottomLayoutContainer.setLayoutParams(layoutParams);
                }
            }
        }, 1500);
    }

    /* access modifiers changed from: private */
    public void populateUnifiedNativeAdView(UnifiedNativeAd unifiedNativeAd, UnifiedNativeAdView unifiedNativeAdView) {
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

    /* access modifiers changed from: package-private */
    public void initializeSkudetails() {
        this.bronzeDetails = ((MainActivity) getActivity()).getBronzeDetails();
        this.silverDetails = ((MainActivity) getActivity()).getSilverDetails();
        this.goldDetails = ((MainActivity) getActivity()).getGoldDetails();
    }

    public void unbindDrawables(View view) {
        try {
            if (view.getBackground() != null) {
                view.getBackground().setCallback((Drawable.Callback) null);
            }
            if (view instanceof ImageView) {
                Log.d("View", "imageView");
                ((ImageView) view).setImageBitmap((Bitmap) null);
            } else if (view instanceof ViewGroup) {
                Log.d("View", "viewGroup");
                ViewGroup viewGroup = (ViewGroup) view;
                for (int i = 0; i < viewGroup.getChildCount(); i++) {
                    unbindDrawables(viewGroup.getChildAt(i));
                }
                if (!(view instanceof AdapterView)) {
                    viewGroup.removeAllViews();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onStart() {
        super.onStart();
        Log.d("ExportFragment", "Start");
    }

    public void onResume() {
        super.onResume();
        if (this.sharing_done) {
            Log.d("ExportFragment", "Resume after share");
            this.image_share = false;
            this.share_dialog_launch = false;
            this.sharing_done = false;
            if (AppRater.initialize(getActivity()).isOverIncrementCount() && !SharedPreferencesManager.getSomeBoolValue(getActivity(), Constants.APP_RATED)) {
                Log.d("RateDialog", "Show");
                initializeRateDialog();
                return;
            }
            return;
        }
        if (this.image_share) {
            this.image_share = false;
        }
        if (this.share_dialog_launch) {
            this.share_dialog_launch = false;
        } else {
            Log.d("ExportFragment", "Resume");
        }
    }

    public void onPause() {
        super.onPause();
        if (this.image_share) {
            this.share_dialog_launch = true;
            Log.d("ExportFragment", "Share button press");
            return;
        }
        Log.d("ExportFragment", "Pause");
    }

    public void onStop() {
        super.onStop();
        if (this.share_dialog_launch) {
            AppRater.initialize(getActivity()).IncrementEvent();
            this.sharing_done = true;
            Log.d("ExportFragment", "Share Activity Launch");
        } else if (!(this.bitmapCache == null || this.cache_name == null || this.cache_name.isEmpty())) {
            this.bitmapCache.clearMemory(this.cache_name);
        }
        Log.d("ExportFragment", "Stop");
    }

    public void onDestroyView() {
        super.onDestroyView();
        Log.d("ExportFragment", "DestroyView");
    }

    public void onDestroy() {
        super.onDestroy();
        if (this.effectBitmap != null) {
            this.effectBitmap = null;
        }
        unbindDrawables(this.recyclerView);
        unbindDrawables(getView());
        if (this.gpuImg != null) {
            this.gpuImg.deleteImage();
            this.gpuImg = null;
        }
        if (!(this.bitmapCache == null || this.cache_name == null || this.cache_name.isEmpty())) {
            this.bitmapCache.clearMemory(this.cache_name);
        }
        if (!(this.export == null || this.export.getEffect() == null)) {
            this.export.getEffect().releaseMemory();
        }
        if (this.handler != null) {
            this.handler.removeCallbacks(this);
        }
        Log.d("ExportFragment", "Destroy");
    }

    public void onDetach() {
        super.onDetach();
        ((MainActivity) getActivity()).setWaitScreenGoneListener((WaitScreenGoneListener) null);
    }

    private void initList() {
        this.resolutionArray = new ArrayList<>();
        this.resolutionArray.add(new Resolutions("Regular", false));
        if (this.effectName == null) {
            for (int i = 0; i < this.resolutions.size(); i++) {
                if (this.resolutions.get(i).getImage_width() == 1280) {
                    this.hd_effect.setVisibility(View.VISIBLE);
                }
                if (this.resolutions.get(i).getImage_width() == 1440) {
                    this.hdv_effect.setVisibility(View.VISIBLE);
                }
                if (this.resolutions.get(i).getImage_width() == 1920) {
                    this.full_hd_effect.setVisibility(View.VISIBLE);
                }
                if (this.resolutions.get(i).getImage_width() == 2048) {
                    this.two_k_effect.setVisibility(View.VISIBLE);
                }
                if (this.resolutions.get(i).getImage_width() == 3840) {
                    this.four_k_effect.setVisibility(View.VISIBLE);
                }
            }
        } else if (this.effectName.equals("Water_Color") || this.effectName.equals("Color Thick Edge") || this.effectName.equals("Color Medium Edge") || this.effectName.equals("Color Thin Edge")) {
            this.resolutionArray.add(new Resolutions("Sorry", false));
        } else {
            for (int i2 = 0; i2 < this.resolutions.size(); i2++) {
                if (this.resolutions.get(i2).getImage_width() == 1280) {
                    this.hd_effect.setVisibility(View.VISIBLE);
                }
                if (this.resolutions.get(i2).getImage_width() == 1440) {
                    this.hdv_effect.setVisibility(View.VISIBLE);
                }
                if (this.resolutions.get(i2).getImage_width() == 1920) {
                    this.full_hd_effect.setVisibility(View.VISIBLE);
                }
                if (this.resolutions.get(i2).getImage_width() == 2048) {
                    this.two_k_effect.setVisibility(View.VISIBLE);
                }
                if (this.resolutions.get(i2).getImage_width() == 3840) {
                    this.four_k_effect.setVisibility(View.VISIBLE);
                }
            }
        }
    }

/*   */private void initializeFeatureUnlockDialog() {
        final Dialog dialog2 = new Dialog(getActivity(), R.style.VideAdDialogTheme);
        dialog2.getWindow().setDimAmount(0.0f);
        dialog2.setContentView(R.layout.feature_unlock_dialog_layout);
        Button button = (Button) dialog2.findViewById(R.id.cancel_button);
        ((TextView) dialog2.findViewById(R.id.title_text)).setText("Hd Resolutions Locked");
        button.setText(getString(R.string.cancel));
        ((TextView) dialog2.findViewById(R.id.msg_text)).setText("HD resolutions are locked to unlock them please purchase HD Resolution pack and unlock all Hd & Ultra Hd Resolutions (Full Hd , 2K , 4K) and save or share your images in Hd resolutions");
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                dialog2.dismiss();
            }
        });
        dialog2.setCancelable(true);
        dialog2.show();
    }

    private void initializeIAPDialog() {
        Dialog dialog2 = new Dialog(getActivity(), R.style.VideAdDialogTheme);
        dialog2.getWindow().setDimAmount(0.0f);
        dialog2.setContentView(R.layout.iap_dialog);
        this.iap_recycler_view = (RecyclerView) dialog2.findViewById(R.id.my_recycler_view);
        ArrayList arrayList = new ArrayList();
        BillingProcessor bp = ((MainActivity) getActivity()).getBp();
        if (this.bronzeDetails != null) {
            arrayList.add(new IAPView("Bronze", "25 ", "INR 100.0", Constants.BRONZE_CREDIT_PACK, this.bronzeDetails));
        } else {
            arrayList.add(new IAPView("Bronze", "25 ", "INR 100.0", Constants.BRONZE_CREDIT_PACK, (SkuDetails) null));
        }
        if (this.silverDetails != null) {
            arrayList.add(new IAPView("Silver", "50 ", "INR 150.0", Constants.SILVER_CREDIT_PACK, this.silverDetails));
        } else {
            arrayList.add(new IAPView("Silver", "50 ", "INR 150.0", Constants.SILVER_CREDIT_PACK, (SkuDetails) null));
        }
        if (this.goldDetails != null) {
            arrayList.add(new IAPView("Gold", "100 ", "INR 250.0", Constants.GOLD_CREDIT_PACK, this.goldDetails));
        } else {
            arrayList.add(new IAPView("Gold", "100 ", "INR 250.0", Constants.GOLD_CREDIT_PACK, (SkuDetails) null));
        }
        this.iapItemsAdapter = new DialogIAPItemsAdapter(arrayList);
        this.iapItemsAdapter.setBp(bp);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 1);
        this.iapItemsAdapter.setPurchaseButtonListener(this);
        this.iap_recycler_view.setAdapter(this.iapItemsAdapter);
        this.iap_recycler_view.setLayoutManager(gridLayoutManager);
        dialog2.show();
        dialog2.setOnDismissListener(new DialogInterface.OnDismissListener() {
            public void onDismiss(DialogInterface dialogInterface) {
            }
        });
    }

    /* access modifiers changed from: private */
    public void initializeImageNullDialog() {
        final Handler handler2 = new Handler();
        handler2.postDelayed(new Runnable() {
            public void run() {
                if (ExportFragment.this.getActivity() != null) {
                    final Dialog dialog = new Dialog(ExportFragment.this.getActivity(), R.style.VideAdDialogTheme);
                    dialog.getWindow().setDimAmount(0.0f);
                    dialog.setContentView(R.layout.weekly_reward_layout);
                    Button button = (Button) dialog.findViewById(R.id.yes);
                    ((TextView) dialog.findViewById(R.id.title_text)).setText(ExportFragment.this.getString(R.string.null_image_title));
                    ((TextView) dialog.findViewById(R.id.reward_text)).setText(ExportFragment.this.getString(R.string.null_image_warning));
                    button.setText(ExportFragment.this.getString(R.string.ok));
                    button.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });
                    dialog.setCancelable(true);
                    dialog.show();
                    handler2.removeCallbacks(this);
                    return;
                }
                handler2.postDelayed(this, 50);
            }
        }, 100);
    }

    private void initializePathNullDialog() {
        final Dialog dialog2 = new Dialog(getActivity(), R.style.VideAdDialogTheme);
        dialog2.getWindow().setDimAmount(0.0f);
        dialog2.setContentView(R.layout.weekly_reward_layout);
        Button button = (Button) dialog2.findViewById(R.id.yes);
        ((TextView) dialog2.findViewById(R.id.title_text)).setText(getString(R.string.image_path_null_title));
        ((TextView) dialog2.findViewById(R.id.reward_text)).setText(getString(R.string.image_path_null_warning));
        button.setText(getString(R.string.ok));
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                dialog2.dismiss();
            }
        });
        dialog2.setCancelable(true);
        dialog2.show();
    }

    private void initializeCreditFunctionTellingDialog() {
        final Dialog dialog2 = new Dialog(getActivity(), R.style.VideAdDialogTheme);
        dialog2.getWindow().setDimAmount(0.0f);
        dialog2.setContentView(R.layout.weekly_reward_layout);
        Button button = (Button) dialog2.findViewById(R.id.yes);
        ((TextView) dialog2.findViewById(R.id.title_text)).setText(getString(R.string.credit_usage));
        ((TextView) dialog2.findViewById(R.id.reward_text)).setText(getString(R.string.credit_usage_text));
        button.setText(getString(R.string.ok));
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                dialog2.dismiss();
            }
        });
        dialog2.setCancelable(false);
        dialog2.show();
    }

    private void initializeRewardDialog() {
        final Dialog dialog2 = new Dialog(getActivity(),R.style.VideAdDialogTheme);
        dialog2.getWindow().setDimAmount(0.0f);
        dialog2.setContentView(R.layout.video_ad_dialog);
        ((Button) dialog2.findViewById(R.id.no)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                dialog2.dismiss();
            }
        });
        ((Button) dialog2.findViewById(R.id.yes)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            }
        });
        dialog2.setCancelable(false);
        dialog2.show();
        Bundle bundle = new Bundle();
        bundle.putString("AdNetwork", "RewardDialog_Show");
        FireBaseHelper.getInstance().LogEvent("Ad_Analytics", bundle);
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x0052  */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x005b  */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x0064  */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x006d  */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x0076  */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x008a  */
    /* JADX WARNING: Removed duplicated region for block: B:36:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void setLength(java.lang.String r3) {
        /*
            r2 = this;
            int r0 = r3.hashCode()
            r1 = 1625(0x659, float:2.277E-42)
            if (r0 == r1) goto L_0x0043
            r1 = 1687(0x697, float:2.364E-42)
            if (r0 == r1) goto L_0x0039
            r1 = 2332(0x91c, float:3.268E-42)
            if (r0 == r1) goto L_0x002f
            r1 = 71386(0x116da, float:1.00033E-40)
            if (r0 == r1) goto L_0x0025
            r1 = 1153349677(0x44beb82d, float:1525.7555)
            if (r0 == r1) goto L_0x001b
            goto L_0x004d
        L_0x001b:
            java.lang.String r0 = "Full Hd"
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L_0x004d
            r0 = 2
            goto L_0x004e
        L_0x0025:
            java.lang.String r0 = "HDV"
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L_0x004d
            r0 = 1
            goto L_0x004e
        L_0x002f:
            java.lang.String r0 = "Hd"
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L_0x004d
            r0 = 0
            goto L_0x004e
        L_0x0039:
            java.lang.String r0 = "4K"
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L_0x004d
            r0 = 4
            goto L_0x004e
        L_0x0043:
            java.lang.String r0 = "2K"
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L_0x004d
            r0 = 3
            goto L_0x004e
        L_0x004d:
            r0 = -1
        L_0x004e:
            switch(r0) {
                case 0: goto L_0x0076;
                case 1: goto L_0x006d;
                case 2: goto L_0x0064;
                case 3: goto L_0x005b;
                case 4: goto L_0x0052;
                default: goto L_0x0051;
            }
        L_0x0051:
            goto L_0x007e
        L_0x0052:
            r0 = 3840(0xf00, float:5.381E-42)
            r2.targetLength = r0
            java.lang.String r0 = "fourk"
            r2.cache_file_name = r0
            goto L_0x007e
        L_0x005b:
            r0 = 2048(0x800, float:2.87E-42)
            r2.targetLength = r0
            java.lang.String r0 = "twok"
            r2.cache_file_name = r0
            goto L_0x007e
        L_0x0064:
            r0 = 1920(0x780, float:2.69E-42)
            r2.targetLength = r0
            java.lang.String r0 = "full_hd"
            r2.cache_file_name = r0
            goto L_0x007e
        L_0x006d:
            r0 = 1440(0x5a0, float:2.018E-42)
            r2.targetLength = r0
            java.lang.String r0 = "hdv"
            r2.cache_file_name = r0
            goto L_0x007e
        L_0x0076:
            r0 = 1280(0x500, float:1.794E-42)
            r2.targetLength = r0
            java.lang.String r0 = "hd"
            r2.cache_file_name = r0
        L_0x007e:
            java.lang.String r0 = r2.cache_file_name
            r2.resolution = r0
            java.lang.String r0 = "Regular"
            boolean r3 = r3.equals(r0)
            if (r3 != 0) goto L_0x0091
            java.util.List<java.lang.String> r3 = r2.cache_name
            java.lang.String r0 = r2.cache_file_name
            r3.add(r0)
        L_0x0091:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: p049ui.ExportFragment.setLength(java.lang.String):void");
    }

    public void onSpinnerOpened() {
        Log.d("Spinner", "Open");
    }

    public void onSpinnerClosed() {
        Log.d("Spinner", "Close");
    }

    public void run() {
        if (this.dialog.isShowing()) {
            this.handler.postDelayed(this, 100);
        } else if (this.loadingDialoggone) {
            this.handler.removeCallbacks(this);
            initializePathNullDialog();
        } else {
            this.handler.postDelayed(this, 500);
            this.loadingDialoggone = true;
        }
    }

    public void OnPurchaseButtonClick(IAPView iAPView) {
        ((MainActivity) getActivity()).PurchaseProduct(iAPView.getProductId(), 0);
    }

    public void OnFragmentUpdate() {
        if (this.resolution != null) {
            checkExportAvailable(this.resolution);
        }
    }

    public void onDismiss(DialogInterface dialogInterface) {
        if (!SharedPreferencesManager.HasKey(getActivity(), "credits_usage_dialog")) {
            final Handler handler2 = new Handler();
            handler2.postDelayed(new Runnable() {
                public void run() {
                    if (ExportFragment.this.getActivity() != null) {
                        handler2.removeCallbacks(this);
                    } else if (ExportFragment.this.handler_onetime_repeat) {
                        handler2.postDelayed(this, 1500);
                        ExportFragment.this.handler_onetime_repeat = false;
                    }
                }
            }, 800);
        }
        Log.d("credits_usage_dialog", "run");
    }

    public void OnWaitScreenGone() {
        processImage();
        this.ad_available = false;
    }

    public void onBannerLoaded(MoPubView moPubView2) {
        if (!Constants.REMOVE_ADS) {
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.bottomLayoutContainer.getLayoutParams();
            layoutParams.height = -2;
            layoutParams.width = -2;
            this.bottomLayoutContainer.setLayoutParams(layoutParams);
            this.bannerLoaded = true;
            return;
        }
        RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) this.bottomLayoutContainer.getLayoutParams();
        layoutParams2.height = dpToPx(0, true);
        layoutParams2.width = -1;
        this.bottomLayoutContainer.setLayoutParams(layoutParams2);
        this.bannerLoaded = false;
    }

    public void onBannerFailed(MoPubView moPubView2, MoPubErrorCode moPubErrorCode) {
        this.bannerLoaded = false;
        Log.d("Banner", AnalyticsEvents.PARAMETER_DIALOG_OUTCOME_VALUE_FAILED);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.bottomLayoutContainer.getLayoutParams();
        layoutParams.height = dpToPx(0, true);
        layoutParams.width = -1;
        this.bottomLayoutContainer.setLayoutParams(layoutParams);
    }

    /* access modifiers changed from: package-private */
    public void disableAll() {
        this.regular_effect.setBackgroundColor(0);
        this.hd_effect.setBackgroundColor(0);
        this.hdv_effect.setBackgroundColor(0);
        this.full_hd_effect.setBackgroundColor(0);
        this.two_k_effect.setBackgroundColor(0);
        this.four_k_effect.setBackgroundColor(0);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.resol_fourK:
                disableAll();
                this.four_k_effect.setBackgroundColor(Color.parseColor("#2196F3"));
                this.res = "4K";
                setLength(this.res);
                this.export.setCache_name(this.cache_file_name);
                this.export.setTargetLength(this.targetLength);
                if (!this.ad_available) {
                    processImage();
                    return;
                }
                return;
            case R.id.resol_full_hd:
                disableAll();
                this.full_hd_effect.setBackgroundColor(Color.parseColor("#2196F3"));
                this.res = "Full Hd";
                setLength(this.res);
                this.export.setCache_name(this.cache_file_name);
                this.export.setTargetLength(this.targetLength);
                if (!this.ad_available) {
                    processImage();
                    return;
                }
                return;
            case R.id.resol_hd:
                disableAll();
                this.hd_effect.setBackgroundColor(Color.parseColor("#2196F3"));
                this.res = "Hd";
                setLength(this.res);
                this.export.setCache_name(this.cache_file_name);
                this.export.setTargetLength(this.targetLength);
                if (!this.ad_available) {
                    processImage();
                    return;
                }
                return;
            case R.id.resol_hdv:
                disableAll();
                this.hdv_effect.setBackgroundColor(Color.parseColor("#2196F3"));
                this.res = "HDV";
                setLength(this.res);
                this.export.setCache_name(this.cache_file_name);
                this.export.setTargetLength(this.targetLength);
                if (!this.ad_available) {
                    processImage();
                    return;
                }
                return;
            case R.id.resol_norm:
                disableAll();
                this.regular_effect.setBackgroundColor(Color.parseColor("#2196F3"));
                this.res = "Regular";
                setLength(this.res);
                this.export.setCache_name(this.cache_file_name);
                this.export.setTargetLength(this.targetLength);
                if (!this.ad_available) {
                    processImage();
                    return;
                }
                return;
            case R.id.resol_twoK:
                disableAll();
                this.two_k_effect.setBackgroundColor(Color.parseColor("#2196F3"));
                this.res = "2K";
                setLength(this.res);
                this.export.setCache_name(this.cache_file_name);
                this.export.setTargetLength(this.targetLength);
                if (!this.ad_available) {
                    processImage();
                    return;
                }
                return;
            default:
                return;
        }
    }

    /* renamed from: ui.ExportFragment$HDEffectTask */
    public class HDEffectTask extends AsyncTask<Void, Void, Bitmap> {
        private String _cache_name;

        /* renamed from: b */
        Bitmap f6044b = null;
        Dialog dialog;
        public Effect effect;
        private String effectName;
        private ExportDetails exportDetails;
        Mat filtered;
        Mat finalmat;
        private GPUImage gpuImage;
        private boolean isHighResolution = false;
        private BitmapCache mbitmapCache;
        Mat original;
        Bitmap resize;
        private List<String> sequence = new ArrayList();

        public HDEffectTask(ExportDetails exportDetails2, boolean z, GPUImage gPUImage, Bitmap bitmap, Dialog dialog2) {
            this.resize = bitmap;
            this.exportDetails = exportDetails2;
            this.effect = exportDetails2.getEffect();
            this.mbitmapCache = this.exportDetails.getBitmapCache();
            if (this.exportDetails.getEffect_name() != null) {
                this.effectName = this.exportDetails.getEffect_name();
            }
            this.isHighResolution = z;
            if (this.exportDetails.getCache_name() != null) {
                this._cache_name = this.exportDetails.getCache_name();
            }
            if (!this.exportDetails.getSeq().isEmpty()) {
                this.sequence = this.exportDetails.getSeq();
            }
            this.gpuImage = gPUImage;
            this.dialog = dialog2;
            this.effect.setOriginal_image(z);
            this.effect.setResolution(this._cache_name);
            this.original = new Mat();
            this.filtered = new Mat();
            this.finalmat = new Mat();
            Log.d("ChechTask", "HDTask Cons");
            if (ExportFragment.this.effectBitmap != null) {
                ExportFragment.this.effectBitmap = null;
            }
        }

        /* access modifiers changed from: protected */
        public Bitmap doInBackground(Void... voidArr) {
            Log.d("ChechTask", "HDTask doin");
            if (this.exportDetails.isNoEffectAndFilterApplied()) {
                this.f6044b = this.resize;
                Log.d("ChechTask", "HDTask NoEffectApplied");
            } else if (ExportFragment.this.bitmapCache.getCacheBitmap(this._cache_name) == null) {
                for (int i = 0; i < this.sequence.size(); i++) {
                    if (this.sequence.get(i).equals("effect")) {
                        this.effect.setHd_image_height(this.resize.getHeight());
                        this.effect.setHd_image_width(this.resize.getWidth());
                        this.f6044b = this.effect.getEffect(this.effectName, this.resize);
                        Log.d("ChechTask", "HDTask effect seq");
                    }
                    if (this.sequence.get(i).equals("filter")) {
                        if (this.f6044b == null) {
                            this.f6044b = this.resize;
                        }
                        if (this.exportDetails.getFilterConstant() != null) {
                            this.f6044b = CGENativeLibrary.filterImage_MultipleEffects(Utils.convert(this.f6044b.copy(this.f6044b.getConfig(), true), Bitmap.Config.ARGB_8888), this.exportDetails.getFilterConstant(), 1.0f);
                            this.f6044b = Utils.convert(this.f6044b, Bitmap.Config.RGB_565);
                        } else if (this.exportDetails.getFilter() != null) {
                            this.gpuImage.setImage(this.f6044b);
                            this.gpuImage.setFilter(this.exportDetails.getFilter());
                            this.f6044b = Utils.convert(this.gpuImage.getBitmapWithFilterApplied(), Bitmap.Config.RGB_565);
                        }
                        Log.d("ChechTask", "HDTask filter seq");
                    }
                    if (this.sequence.get(i).equals("enhance")) {
                        if (this.f6044b == null) {
                            this.f6044b = this.resize;
                        }
                        this.gpuImage.setImage(this.f6044b);
                        this.gpuImage.setFilter(ExportFragment.this.readFilters());
                        this.f6044b = Utils.convert(this.gpuImage.getBitmapWithFilterApplied(), Bitmap.Config.RGB_565);
                        Log.d("ChechTask", "HDTask enhance seq");
                    }
                    if (this.sequence.get(i).equals("adjust")) {
                        org.opencv.android.Utils.bitmapToMat(this.resize.copy(this.resize.getConfig(), true), this.original);
                        org.opencv.android.Utils.bitmapToMat(this.f6044b.copy(this.f6044b.getConfig(), true), this.filtered);
                        this.f6044b = this.resize.copy(this.resize.getConfig(), true);
                        this.f6044b = Utils.blendBitmaps(this.original, this.filtered, this.finalmat, this.f6044b, this.exportDetails.getAdjustValue());
                    }
                }
                ExportFragment.this.bitmapCache.addBitmapToCache(this._cache_name, this.f6044b);
                Log.d("Bitmap loaded hdtask", "to memory");
            } else {
                this.f6044b = this.mbitmapCache.getCacheBitmap(this._cache_name);
                Log.d("Bitmap available hdtask", "from memory");
            }
            return this.f6044b;
        }

        /* access modifiers changed from: protected */
        public void onPreExecute() {
            super.onPreExecute();
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if (this.dialog.isShowing()) {
                this.dialog.dismiss();
            }
            ExportFragment.this.C_Name = this._cache_name;
            ExportFragment.this.checkExportAvailable(this._cache_name);
            if (this.original != null) {
                this.original.release();
            }
            if (this.filtered != null) {
                this.filtered.release();
            }
            if (this.finalmat != null) {
                this.finalmat.release();
            }
            this.resize = null;
            if (this.effect != null) {
                this.effect.setOriginal_image(false);
                this.effect.releaseMemory();
            }
            if (this.gpuImage != null) {
                this.gpuImage = null;
            }
        }

        /* access modifiers changed from: protected */
        public void onProgressUpdate(Void... voidArr) {
            super.onProgressUpdate(voidArr);
        }
    }

    public GPUImageFilterGroup readFilters() {
        GPUImageFilterGroup gPUImageFilterGroup = new GPUImageFilterGroup();
        for (int i = 0; i < this.filterHolder.getTwoHolders().size(); i++) {
            gPUImageFilterGroup.addFilter(getFilter(this.filterHolder.getTwoHolders().get(i).getFilterType()));
        }
        return gPUImageFilterGroup;
    }

    public GPUImageFilter getFilter(FilterType filterType) {
        if (filterType == FilterType.BRIGHTNESS) {
            return new GPUImageBrightnessFilter(this.filterHolder.getValue(filterType));
        }
        if (filterType == FilterType.CONTRAST) {
            return new GPUImageContrastFilter(this.filterHolder.getValue(filterType) * 10.0f);
        }
        if (filterType == FilterType.SATURATION) {
            return new GPUImageSaturationFilter(this.filterHolder.getValue(filterType) * 10.0f);
        }
        if (filterType == FilterType.HUE) {
            return new GPUImageHueFilter(this.filterHolder.getValue(filterType) * 100.0f);
        }
        if (filterType == FilterType.SHARPNESS) {
            return new GPUImageSharpenFilter(this.filterHolder.getValue(filterType));
        }
        if (filterType == FilterType.BLUR) {
            return new GPUImageGaussianBlurFilter(this.filterHolder.getValue(filterType));
        }
        if (filterType != FilterType.PIXEL) {
            return null;
        }
        GPUImagePixelationFilter gPUImagePixelationFilter = new GPUImagePixelationFilter();
        gPUImagePixelationFilter.setPixel(this.filterHolder.getValue(filterType) * 15.0f);
        return gPUImagePixelationFilter;
    }

    /* access modifiers changed from: package-private */
    public void processImage() {
        new EffectApplyingTask(this.export).execute(new Void[0]);
    }

    /* access modifiers changed from: package-private */
    public void getImage(String str, Bitmap bitmap) {
        if (str.equals("Regular")) {
            this.export.setCache_name("regular");
            this.cache_name.add("regular");
            try {
                this.effectBitmap = (Bitmap) new EffectTask(bitmap, this.export.getEffect(), this.export.getEffect_name(), this.export, this.gpuImg, this.dialog).execute(new Void[0]).get();
                checkExportAvailable("regular");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e2) {
                e2.printStackTrace();
            }
        } else {
            try {
                this.effectBitmap = (Bitmap) new HDEffectTask(this.export, true, this.gpuImg, bitmap, this.dialog).execute(new Void[0]).get();
            } catch (InterruptedException e3) {
                e3.printStackTrace();
            } catch (ExecutionException e4) {
                e4.printStackTrace();
            }
        }
        this.imageView.setImageBitmap(this.effectBitmap);
    }

    /* renamed from: ui.ExportFragment$EffectApplyingTask */
    private class EffectApplyingTask extends AsyncTask<Void, Void, Void> {
        ExportDetails exportDetails;
        boolean isPathnull = false;
        int length;
        private File originalImag;
        String path;
        Bitmap resize = null;

        public EffectApplyingTask(ExportDetails exportDetails2) {
            this.exportDetails = exportDetails2;
            this.length = this.exportDetails.getTargetLength();
            this.path = this.exportDetails.getPath();
            this.isPathnull = false;
            ExportFragment.this.dialog = new Dialog(ExportFragment.this.getActivity(), R.style.RateUsDialogTheme);
            ExportFragment.this.dialog.requestWindowFeature(1);
            ExportFragment.this.dialog.setContentView(R.layout.effect_loading_dialog);
            ExportFragment.this.dialog.setCancelable(false);
            if (!Constants.IS_CREDITS_USAGE_DIALOG_SHOWN) {
                ExportFragment.this.dialog.setOnDismissListener(ExportFragment.this);
                Log.d("credits_usage_dialog", "not shown");
                return;
            }
            Log.d("credits_usage_dialog", " shown");
        }

        /* access modifiers changed from: protected */
        public void onPreExecute() {
            super.onPreExecute();
            if (this.path != null) {
                this.originalImag = new File(this.path);
            } else {
                if (ExportFragment.this.resolutionArray.size() > 0) {
                    ExportFragment.this.resolutionArray.clear();
                }
                ExportFragment.this.resolutionArray.add(new Resolutions("Regular", false));
                ResolutionsAdapter unused = ExportFragment.this.mAdapter = new ResolutionsAdapter(ExportFragment.this.getActivity(), ExportFragment.this.resolutionArray);
                ExportFragment.this.mAdapter.notifyDataSetChanged();
                this.isPathnull = true;
            }
            if (ExportFragment.this.gpuImg == null) {
                ExportFragment.this.gpuImg = new GPUImage(ExportFragment.this.getActivity());
            }
            ExportFragment.this.dialog.show();
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Void voidR) {
            super.onPostExecute(voidR);
            ExportFragment.this.show_null_path_dialog = this.isPathnull;
            if (this.resize != null && !this.isPathnull) {
                ExportFragment.this.getImage(ExportFragment.this.res, Utils.convert(this.resize, Bitmap.Config.RGB_565));
            } else if (this.resize != null) {
                ExportFragment.this.getImage(ExportFragment.this.res, Utils.convert(this.resize, Bitmap.Config.RGB_565));
                if (this.isPathnull) {
                    ExportFragment.this.handler.postDelayed(ExportFragment.this, 1500);
                }
            } else {
                ExportFragment.this.initializeImageNullDialog();
            }
            if (ExportFragment.this.fresh_start) {
                ExportFragment.this.initializeSkudetails();
                ExportFragment.this.fresh_start = false;
            }
            if (!this.isPathnull) {
                Log.d("ImagePath path", this.path);
            }
            if (Constants.REMOVE_ADS) {
                return;
            }
            if (!Constants.FULL_SCREEN_Editing_NATIVE_FIRST) {
                if (Constants.START_UP_AD && AdUtil.getInstance().isStartUpAdAvailable()) {
                    Constants.START_UP_AD = false;
                    if (AdUtil.getInstance().isStartUpAdAvailable()) {
                        AdUtil.getInstance().ShowStartUpInterstitial();
                    }
                }
            } /*else if (!Constants.ED_NATIVE_FULL_SCREEN_SHOWN && AdUtil.getInstance().isFBEditingNativeLoaded() && Constants.START_UP_AD) {
                Log.d("editingnative", "loaded");
                Constants.START_UP_AD = false;
                ((MainActivity) ExportFragment.this.getActivity()).showFloatingFBNativeAd(AdUtil.getInstance().getEditing_fb_native(), true);
            }*/
        }

        /* access modifiers changed from: protected */
        public Void doInBackground(Void... voidArr) {
            if (!ExportFragment.this.res.equals("Regular") && ExportFragment.this.bitmapCache != null && ExportFragment.this.bitmapCache.getCacheBitmap(this.exportDetails.getCache_name()) == null) {
                try {
                    this.resize = new Resizer(ExportFragment.this.getActivity()).setTargetLength(this.length).setSourceImage(this.originalImag).getResizedBitmap();
                    return null;
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            } else if (ExportFragment.this.bitmapCache != null && ExportFragment.this.bitmapCache.getCacheBitmap(Constants.ORIGINAL_IMAGE) != null) {
                this.resize = ExportFragment.this.bitmapCache.getCacheBitmap(Constants.ORIGINAL_IMAGE);
                Log.d("FragmentEditing", "bitmapcache");
                return null;
            } else if (ExportFragment.this.fileUri != null) {
                this.resize = BitmapFactory.decodeFile(ImageFilePath.getPath(ExportFragment.this.getActivity(), ExportFragment.this.fileUri));
                if (!(ExportFragment.this.bitmapCache == null || this.resize == null)) {
                    ExportFragment.this.bitmapCache.addBitmapToCache(Constants.ORIGINAL_IMAGE, this.resize);
                }
                Log.d("FragmentEditing", "fileUri");
                return null;
            } else if (Constants.FILE_URI == null) {
                return null;
            } else {
                this.resize = BitmapFactory.decodeFile(ImageFilePath.getPath(ExportFragment.this.getActivity(), Constants.FILE_URI));
                if (!(ExportFragment.this.bitmapCache == null || this.resize == null)) {
                    ExportFragment.this.bitmapCache.addBitmapToCache(Constants.ORIGINAL_IMAGE, this.resize);
                }
                Log.d("FragmentEditing", "Constant fileUri");
                return null;
            }
        }
    }

    /* renamed from: ui.ExportFragment$ExportOptionsView */
    public class ExportOptionsView {
        private boolean Locked = true;
        private String credits;
        private int creditsamount = 0;
        private int imgId;
        private int lockImage;
        private String title;

        public int getCreditsamount() {
            return this.creditsamount;
        }

        public void setCreditsamount(int i) {
            this.creditsamount = i;
        }

        public boolean isLocked() {
            return this.Locked;
        }

        public void setLocked(boolean z) {
            this.Locked = z;
        }

        public int getLockImage() {
            return this.lockImage;
        }

        public void setLockImage(int i) {
            this.lockImage = i;
        }

        public String getTitle() {
            return this.title;
        }

        public void setTitle(String str) {
            this.title = str;
        }

        public String getCredits() {
            return this.credits;
        }

        public void setCredits(String str) {
            this.credits = str;
        }

        public int getImgId() {
            return this.imgId;
        }

        public void setImgId(int i) {
            this.imgId = i;
        }

        public ExportOptionsView(String str, String str2, int i) {
            this.title = str;
            this.credits = str2;
            this.imgId = i;
            this.lockImage = R.drawable.ic_lock_black_24dp;
            this.Locked = true;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:103:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x0087  */
    /* JADX WARNING: Removed duplicated region for block: B:56:0x0193  */
    /* JADX WARNING: Removed duplicated region for block: B:65:0x020e  */
    /* JADX WARNING: Removed duplicated region for block: B:89:0x0333  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void OnExportButtonClick(ui.ExportFragment.ExportOptionsView r8) {
        /*
            r7 = this;
            java.util.List<java.lang.String> r0 = r7.sequence
            if (r0 == 0) goto L_0x034a
            java.util.List<java.lang.String> r0 = r7.sequence
            java.lang.String r1 = "effect"
            boolean r0 = r0.contains(r1)
            if (r0 != 0) goto L_0x0022
            java.util.List<java.lang.String> r0 = r7.sequence
            java.lang.String r1 = "filter"
            boolean r0 = r0.contains(r1)
            if (r0 != 0) goto L_0x0022
            java.util.List<java.lang.String> r0 = r7.sequence
            java.lang.String r1 = "enhance"
            boolean r0 = r0.contains(r1)
            if (r0 == 0) goto L_0x034a
        L_0x0022:
            int r0 = r8.getLockImage()
            r1 = 1
            if (r0 != r1) goto L_0x0346
            boolean r0 = r8.isLocked()
            if (r0 != 0) goto L_0x0351
            java.lang.String r0 = "ApplyFeatures"
            java.lang.String r2 = "Not"
            android.util.Log.d(r0, r2)
            java.lang.String r8 = r8.getTitle()
            r0 = -1
            int r2 = r8.hashCode()
            r3 = 2569629(0x27359d, float:3.600817E-39)
            r4 = 0
            if (r2 == r3) goto L_0x0073
            r3 = 79847359(0x4c25fbf, float:4.569711E-36)
            if (r2 == r3) goto L_0x0069
            r3 = 561774310(0x217bfee6, float:8.5379463E-19)
            if (r2 == r3) goto L_0x005f
            r3 = 2032871314(0x792b2792, float:5.5542834E34)
            if (r2 == r3) goto L_0x0055
            goto L_0x007d
        L_0x0055:
            java.lang.String r2 = "Instagram"
            boolean r8 = r8.equals(r2)
            if (r8 == 0) goto L_0x007d
            r8 = 3
            goto L_0x007e
        L_0x005f:
            java.lang.String r2 = "Facebook"
            boolean r8 = r8.equals(r2)
            if (r8 == 0) goto L_0x007d
            r8 = 2
            goto L_0x007e
        L_0x0069:
            java.lang.String r2 = "Share"
            boolean r8 = r8.equals(r2)
            if (r8 == 0) goto L_0x007d
            r8 = 1
            goto L_0x007e
        L_0x0073:
            java.lang.String r2 = "Save"
            boolean r8 = r8.equals(r2)
            if (r8 == 0) goto L_0x007d
            r8 = 0
            goto L_0x007e
        L_0x007d:
            r8 = -1
        L_0x007e:
            r0 = 100
            r2 = 24
            switch(r8) {
                case 0: goto L_0x0333;
                case 1: goto L_0x020e;
                case 2: goto L_0x0193;
                case 3: goto L_0x0087;
                default: goto L_0x0085;
            }
        L_0x0085:
            goto L_0x0351
        L_0x0087:
            android.content.Intent r8 = new android.content.Intent
            java.lang.String r3 = "android.intent.action.SEND"
            r8.<init>(r3)
            java.lang.String r3 = "image/jpeg"
            r8.setType(r3)
            java.io.ByteArrayOutputStream r3 = new java.io.ByteArrayOutputStream
            r3.<init>()
            android.graphics.Bitmap r4 = r7.effectBitmap
            android.graphics.Bitmap$CompressFormat r5 = android.graphics.Bitmap.CompressFormat.JPEG
            r4.compress(r5, r0, r3)
            int r0 = android.os.Build.VERSION.SDK_INT
            if (r0 < r2) goto L_0x00cd
            java.io.File r0 = new java.io.File
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            androidx.fragment.app.FragmentActivity r5 = r7.getActivity()
            java.lang.String r6 = helper.Constants.CACHE_DIRECTORY
            java.io.File r5 = util.Utils.getCacheDirectory(r5, r6)
            java.lang.String r5 = r5.getPath()
            r4.append(r5)
            java.lang.String r5 = java.io.File.separator
            r4.append(r5)
            java.lang.String r5 = "Share1.jpg"
            r4.append(r5)
            java.lang.String r4 = r4.toString()
            r0.<init>(r4)
            goto L_0x00ec
        L_0x00cd:
            java.io.File r0 = new java.io.File
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.io.File r5 = android.os.Environment.getExternalStorageDirectory()
            r4.append(r5)
            java.lang.String r5 = java.io.File.separator
            r4.append(r5)
            java.lang.String r5 = "Share1.jpg"
            r4.append(r5)
            java.lang.String r4 = r4.toString()
            r0.<init>(r4)
        L_0x00ec:
            r0.createNewFile()     // Catch:{ IOException -> 0x00fc }
            java.io.FileOutputStream r4 = new java.io.FileOutputStream     // Catch:{ IOException -> 0x00fc }
            r4.<init>(r0)     // Catch:{ IOException -> 0x00fc }
            byte[] r3 = r3.toByteArray()     // Catch:{ IOException -> 0x00fc }
            r4.write(r3)     // Catch:{ IOException -> 0x00fc }
            goto L_0x0100
        L_0x00fc:
            r3 = move-exception
            r3.printStackTrace()
        L_0x0100:
            int r3 = android.os.Build.VERSION.SDK_INT
            if (r3 < r2) goto L_0x011c
            r8.addFlags(r1)
            java.lang.String r2 = "android.intent.extra.STREAM"
            androidx.fragment.app.FragmentActivity r3 = r7.getActivity()
            java.lang.String r4 = "com.hqgames.pencil.sketch.photo.provider"
            android.net.Uri r0 = androidx.core.content.FileProvider.getUriForFile(r3, r4, r0)
            r8.putExtra(r2, r0)
            java.lang.String r0 = "com.instagram.android"
            r8.setPackage(r0)
            goto L_0x012c
        L_0x011c:
            java.lang.String r0 = "android.intent.extra.STREAM"
            java.lang.String r2 = "file:///sdcard/Share1.jpg"
            android.net.Uri r2 = android.net.Uri.parse(r2)
            r8.putExtra(r0, r2)
            java.lang.String r0 = "com.instagram.android"
            r8.setPackage(r0)
        L_0x012c:
            r7.image_share = r1
            java.lang.String r0 = "Share Image"
            android.content.Intent r8 = android.content.Intent.createChooser(r8, r0)
            r7.startActivity(r8)
            android.widget.ImageView r8 = r7.imageView
            android.graphics.drawable.Drawable r8 = r8.getDrawable()
            android.graphics.drawable.BitmapDrawable r8 = (android.graphics.drawable.BitmapDrawable) r8
            android.graphics.Bitmap r8 = r8.getBitmap()
            if (r8 == 0) goto L_0x0351
            android.widget.ImageView r8 = r7.imageView
            android.graphics.drawable.Drawable r8 = r8.getDrawable()
            android.graphics.drawable.BitmapDrawable r8 = (android.graphics.drawable.BitmapDrawable) r8
            android.graphics.Bitmap r8 = r8.getBitmap()
            android.graphics.Bitmap r0 = r7.effectBitmap
            if (r8 != r0) goto L_0x0351
            android.os.Bundle r8 = new android.os.Bundle
            r8.<init>()
            java.lang.String r0 = r7.effectName
            if (r0 == 0) goto L_0x0181
            java.lang.String r0 = "image_share"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "insta "
            r1.append(r2)
            java.lang.String r2 = r7.effectName
            r1.append(r2)
            java.lang.String r2 = " "
            r1.append(r2)
            java.lang.String r2 = r7.res
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            r8.putString(r0, r1)
            goto L_0x0188
        L_0x0181:
            java.lang.String r0 = "image_share"
            java.lang.String r1 = "original"
            r8.putString(r0, r1)
        L_0x0188:
            util.FireBaseHelper r0 = util.FireBaseHelper.getInstance()
            java.lang.String r1 = "Insta_Share"
            r0.LogEvent(r1, r8)
            goto L_0x0351
        L_0x0193:
            androidx.fragment.app.FragmentActivity r8 = r7.getActivity()
            com.hqgames.pencil.sketch.photo.MainActivity r8 = (com.hqgames.pencil.sketch.photo.MainActivity) r8
            android.graphics.Bitmap r0 = r7.effectBitmap
            r8.ShareFacebook(r0)
            r7.image_share = r1
            androidx.fragment.app.FragmentActivity r8 = r7.getActivity()
            java.lang.String r0 = "Please wait while uploading to facebook"
            android.widget.Toast r8 = android.widget.Toast.makeText(r8, r0, r4)
            r8.show()
            android.widget.ImageView r8 = r7.imageView
            android.graphics.drawable.Drawable r8 = r8.getDrawable()
            android.graphics.drawable.BitmapDrawable r8 = (android.graphics.drawable.BitmapDrawable) r8
            android.graphics.Bitmap r8 = r8.getBitmap()
            if (r8 == 0) goto L_0x01d8
            android.widget.ImageView r8 = r7.imageView
            android.graphics.drawable.Drawable r8 = r8.getDrawable()
            android.graphics.drawable.BitmapDrawable r8 = (android.graphics.drawable.BitmapDrawable) r8
            android.graphics.Bitmap r8 = r8.getBitmap()
            android.graphics.Bitmap r0 = r7.effectBitmap
            if (r8 != r0) goto L_0x01d8
            java.lang.String r8 = r7.effectName
            if (r8 == 0) goto L_0x01d4
            java.lang.String r8 = r7.effectName
            helper.Constants.EFFECT_NAME = r8
            goto L_0x01d8
        L_0x01d4:
            java.lang.String r8 = "original"
            helper.Constants.EFFECT_NAME = r8
        L_0x01d8:
            java.lang.String r8 = r7.res
            helper.Constants.RESOLUTION = r8
            android.os.Bundle r8 = new android.os.Bundle
            r8.<init>()
            java.lang.String r0 = "image_share"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "facebook "
            r1.append(r2)
            java.lang.String r2 = helper.Constants.EFFECT_NAME
            r1.append(r2)
            java.lang.String r2 = " "
            r1.append(r2)
            java.lang.String r2 = helper.Constants.RESOLUTION
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            r8.putString(r0, r1)
            util.FireBaseHelper r0 = util.FireBaseHelper.getInstance()
            java.lang.String r1 = "Facebook_Share"
            r0.LogEvent(r1, r8)
            goto L_0x0351
        L_0x020e:
            java.lang.String r8 = "Share"
            java.lang.String r3 = "Share"
            android.util.Log.d(r8, r3)
            android.content.Intent r8 = new android.content.Intent
            java.lang.String r3 = "android.intent.action.SEND"
            r8.<init>(r3)
            java.lang.String r3 = "image/jpeg"
            r8.setType(r3)
            java.io.ByteArrayOutputStream r3 = new java.io.ByteArrayOutputStream
            r3.<init>()
            android.graphics.Bitmap r4 = r7.effectBitmap
            android.graphics.Bitmap$CompressFormat r5 = android.graphics.Bitmap.CompressFormat.JPEG
            r4.compress(r5, r0, r3)
            java.util.Random r0 = new java.util.Random
            r0.<init>()
            r4 = 10000(0x2710, float:1.4013E-41)
            int r0 = r0.nextInt(r4)
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "image-"
            r4.append(r5)
            r4.append(r0)
            java.lang.String r0 = ".jpg"
            r4.append(r0)
            java.lang.String r0 = r4.toString()
            int r4 = android.os.Build.VERSION.SDK_INT
            if (r4 < r2) goto L_0x0263
            java.io.File r4 = new java.io.File
            java.io.File r5 = android.os.Environment.getExternalStorageDirectory()
            r4.<init>(r5, r0)
            java.lang.String r0 = "SDKINT"
            java.lang.String r5 = "Greater 24"
            android.util.Log.d(r0, r5)
            goto L_0x0280
        L_0x0263:
            java.io.File r4 = new java.io.File
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.io.File r6 = android.os.Environment.getExternalStorageDirectory()
            r5.append(r6)
            java.lang.String r6 = java.io.File.separator
            r5.append(r6)
            r5.append(r0)
            java.lang.String r0 = r5.toString()
            r4.<init>(r0)
        L_0x0280:
            r4.createNewFile()     // Catch:{ IOException -> 0x0290 }
            java.io.FileOutputStream r0 = new java.io.FileOutputStream     // Catch:{ IOException -> 0x0290 }
            r0.<init>(r4)     // Catch:{ IOException -> 0x0290 }
            byte[] r3 = r3.toByteArray()     // Catch:{ IOException -> 0x0290 }
            r0.write(r3)     // Catch:{ IOException -> 0x0290 }
            goto L_0x0294
        L_0x0290:
            r0 = move-exception
            r0.printStackTrace()
        L_0x0294:
            int r0 = android.os.Build.VERSION.SDK_INT
            if (r0 < r2) goto L_0x02c9
            java.lang.String r0 = "SDKINT"
            java.lang.String r2 = "Greater 24 4"
            android.util.Log.d(r0, r2)
            androidx.fragment.app.FragmentActivity r0 = r7.getActivity()
            if (r0 == 0) goto L_0x02b5
            java.lang.String r0 = "android.intent.extra.STREAM"
            androidx.fragment.app.FragmentActivity r2 = r7.getActivity()
            java.lang.String r3 = "com.hqgames.pencil.sketch.photo.provider"
            android.net.Uri r2 = androidx.core.content.FileProvider.getUriForFile(r2, r3, r4)
            r8.putExtra(r0, r2)
            goto L_0x02d2
        L_0x02b5:
            java.lang.String r0 = "android.intent.extra.STREAM"
            com.hqgames.pencil.sketch.photo.App r2 = com.hqgames.pencil.sketch.photo.App.getInstance()
            android.content.Context r2 = r2.getApplicationContext()
            java.lang.String r3 = "com.hqgames.pencil.sketch.photo.provider"
            android.net.Uri r2 = androidx.core.content.FileProvider.getUriForFile(r2, r3, r4)
            r8.putExtra(r0, r2)
            goto L_0x02d2
        L_0x02c9:
            java.lang.String r0 = "android.intent.extra.STREAM"
            android.net.Uri r2 = android.net.Uri.fromFile(r4)
            r8.putExtra(r0, r2)
        L_0x02d2:
            r7.image_share = r1
            java.lang.String r0 = "Share Image"
            android.content.Intent r8 = android.content.Intent.createChooser(r8, r0)
            r7.startActivity(r8)
            android.widget.ImageView r8 = r7.imageView
            android.graphics.drawable.Drawable r8 = r8.getDrawable()
            android.graphics.drawable.BitmapDrawable r8 = (android.graphics.drawable.BitmapDrawable) r8
            android.graphics.Bitmap r8 = r8.getBitmap()
            if (r8 == 0) goto L_0x0351
            android.widget.ImageView r8 = r7.imageView
            android.graphics.drawable.Drawable r8 = r8.getDrawable()
            android.graphics.drawable.BitmapDrawable r8 = (android.graphics.drawable.BitmapDrawable) r8
            android.graphics.Bitmap r8 = r8.getBitmap()
            android.graphics.Bitmap r0 = r7.effectBitmap
            if (r8 != r0) goto L_0x0351
            android.os.Bundle r8 = new android.os.Bundle
            r8.<init>()
            java.lang.String r0 = r7.effectName
            if (r0 == 0) goto L_0x0322
            java.lang.String r0 = "image_share"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = r7.effectName
            r1.append(r2)
            java.lang.String r2 = " "
            r1.append(r2)
            java.lang.String r2 = r7.res
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            r8.putString(r0, r1)
            goto L_0x0329
        L_0x0322:
            java.lang.String r0 = "image_share"
            java.lang.String r1 = "original"
            r8.putString(r0, r1)
        L_0x0329:
            util.FireBaseHelper r0 = util.FireBaseHelper.getInstance()
            java.lang.String r1 = "Image_Share"
            r0.LogEvent(r1, r8)
            goto L_0x0351
        L_0x0333:
            r7.dialog_show = r4
            androidx.fragment.app.FragmentActivity r8 = r7.getActivity()
            com.hqgames.pencil.sketch.photo.AppRater r8 = com.hqgames.pencil.sketch.photo.AppRater.initialize(r8)
            r8.IncrementEvent()
            android.graphics.Bitmap r8 = r7.effectBitmap
            r7.SaveImage(r8)
            goto L_0x0351
        L_0x0346:
            r7.initializeFeatureUnlockDialog()
            goto L_0x0351
        L_0x034a:
            java.lang.String r8 = r8.getTitle()
            r7.ApplyFeatures(r8)
        L_0x0351:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: p049ui.ExportFragment.OnExportButtonClick(ui.ExportFragment$ExportOptionsView):void");
    }

    public Uri getLocalBitmapUri(ImageView imageView2) {
        if (imageView2.getDrawable() instanceof BitmapDrawable) {
            return getBitmapFromDrawable(((BitmapDrawable) imageView2.getDrawable()).getBitmap());
        }
        return null;
    }

    public Uri getBitmapFromDrawable(Bitmap bitmap) {
        try {
            File cacheDirectory = Utils.getCacheDirectory(getActivity(), Constants.CACHE_DIRECTORY);
            File file = new File(cacheDirectory, "share_image_" + System.currentTimeMillis() + ".jpg");
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fileOutputStream);
            fileOutputStream.close();
            if (Build.VERSION.SDK_INT >= 24) {
                return FileProvider.getUriForFile(getActivity(), "com.hqgames.pencil.sketch.photo.provider", file);
            }
            return Uri.fromFile(file);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:23:0x0054  */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x0160  */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x01db  */
    /* JADX WARNING: Removed duplicated region for block: B:78:0x0300  */
    /* JADX WARNING: Removed duplicated region for block: B:85:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void ApplyFeatures(java.lang.String r8) {
        /*
            r7 = this;
            java.lang.String r0 = "ApplyFeatures"
            java.lang.String r1 = "Yes"
            android.util.Log.d(r0, r1)
            int r0 = r8.hashCode()
            r1 = 2569629(0x27359d, float:3.600817E-39)
            r2 = 0
            r3 = 1
            if (r0 == r1) goto L_0x0040
            r1 = 79847359(0x4c25fbf, float:4.569711E-36)
            if (r0 == r1) goto L_0x0036
            r1 = 561774310(0x217bfee6, float:8.5379463E-19)
            if (r0 == r1) goto L_0x002c
            r1 = 2032871314(0x792b2792, float:5.5542834E34)
            if (r0 == r1) goto L_0x0022
            goto L_0x004a
        L_0x0022:
            java.lang.String r0 = "Instagram"
            boolean r8 = r8.equals(r0)
            if (r8 == 0) goto L_0x004a
            r8 = 3
            goto L_0x004b
        L_0x002c:
            java.lang.String r0 = "Facebook"
            boolean r8 = r8.equals(r0)
            if (r8 == 0) goto L_0x004a
            r8 = 2
            goto L_0x004b
        L_0x0036:
            java.lang.String r0 = "Share"
            boolean r8 = r8.equals(r0)
            if (r8 == 0) goto L_0x004a
            r8 = 1
            goto L_0x004b
        L_0x0040:
            java.lang.String r0 = "Save"
            boolean r8 = r8.equals(r0)
            if (r8 == 0) goto L_0x004a
            r8 = 0
            goto L_0x004b
        L_0x004a:
            r8 = -1
        L_0x004b:
            r0 = 100
            r1 = 24
            switch(r8) {
                case 0: goto L_0x0300;
                case 1: goto L_0x01db;
                case 2: goto L_0x0160;
                case 3: goto L_0x0054;
                default: goto L_0x0052;
            }
        L_0x0052:
            goto L_0x0312
        L_0x0054:
            android.content.Intent r8 = new android.content.Intent
            java.lang.String r2 = "android.intent.action.SEND"
            r8.<init>(r2)
            java.lang.String r2 = "image/jpeg"
            r8.setType(r2)
            java.io.ByteArrayOutputStream r2 = new java.io.ByteArrayOutputStream
            r2.<init>()
            android.graphics.Bitmap r4 = r7.effectBitmap
            android.graphics.Bitmap$CompressFormat r5 = android.graphics.Bitmap.CompressFormat.JPEG
            r4.compress(r5, r0, r2)
            int r0 = android.os.Build.VERSION.SDK_INT
            if (r0 < r1) goto L_0x009a
            java.io.File r0 = new java.io.File
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            androidx.fragment.app.FragmentActivity r5 = r7.getActivity()
            java.lang.String r6 = helper.Constants.CACHE_DIRECTORY
            java.io.File r5 = util.Utils.getCacheDirectory(r5, r6)
            java.lang.String r5 = r5.getPath()
            r4.append(r5)
            java.lang.String r5 = java.io.File.separator
            r4.append(r5)
            java.lang.String r5 = "Share1.jpg"
            r4.append(r5)
            java.lang.String r4 = r4.toString()
            r0.<init>(r4)
            goto L_0x00b9
        L_0x009a:
            java.io.File r0 = new java.io.File
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.io.File r5 = android.os.Environment.getExternalStorageDirectory()
            r4.append(r5)
            java.lang.String r5 = java.io.File.separator
            r4.append(r5)
            java.lang.String r5 = "Share1.jpg"
            r4.append(r5)
            java.lang.String r4 = r4.toString()
            r0.<init>(r4)
        L_0x00b9:
            r0.createNewFile()     // Catch:{ IOException -> 0x00c9 }
            java.io.FileOutputStream r4 = new java.io.FileOutputStream     // Catch:{ IOException -> 0x00c9 }
            r4.<init>(r0)     // Catch:{ IOException -> 0x00c9 }
            byte[] r2 = r2.toByteArray()     // Catch:{ IOException -> 0x00c9 }
            r4.write(r2)     // Catch:{ IOException -> 0x00c9 }
            goto L_0x00cd
        L_0x00c9:
            r2 = move-exception
            r2.printStackTrace()
        L_0x00cd:
            int r2 = android.os.Build.VERSION.SDK_INT
            if (r2 < r1) goto L_0x00e9
            r8.addFlags(r3)
            java.lang.String r1 = "android.intent.extra.STREAM"
            androidx.fragment.app.FragmentActivity r2 = r7.getActivity()
            java.lang.String r4 = "com.hqgames.pencil.sketch.photo.provider"
            android.net.Uri r0 = androidx.core.content.FileProvider.getUriForFile(r2, r4, r0)
            r8.putExtra(r1, r0)
            java.lang.String r0 = "com.instagram.android"
            r8.setPackage(r0)
            goto L_0x00f9
        L_0x00e9:
            java.lang.String r0 = "android.intent.extra.STREAM"
            java.lang.String r1 = "file:///sdcard/Share1.jpg"
            android.net.Uri r1 = android.net.Uri.parse(r1)
            r8.putExtra(r0, r1)
            java.lang.String r0 = "com.instagram.android"
            r8.setPackage(r0)
        L_0x00f9:
            r7.image_share = r3
            java.lang.String r0 = "Share Image"
            android.content.Intent r8 = android.content.Intent.createChooser(r8, r0)
            r7.startActivity(r8)
            android.widget.ImageView r8 = r7.imageView
            android.graphics.drawable.Drawable r8 = r8.getDrawable()
            android.graphics.drawable.BitmapDrawable r8 = (android.graphics.drawable.BitmapDrawable) r8
            android.graphics.Bitmap r8 = r8.getBitmap()
            if (r8 == 0) goto L_0x0312
            android.widget.ImageView r8 = r7.imageView
            android.graphics.drawable.Drawable r8 = r8.getDrawable()
            android.graphics.drawable.BitmapDrawable r8 = (android.graphics.drawable.BitmapDrawable) r8
            android.graphics.Bitmap r8 = r8.getBitmap()
            android.graphics.Bitmap r0 = r7.effectBitmap
            if (r8 != r0) goto L_0x0312
            android.os.Bundle r8 = new android.os.Bundle
            r8.<init>()
            java.lang.String r0 = r7.effectName
            if (r0 == 0) goto L_0x014e
            java.lang.String r0 = "image_share"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "insta "
            r1.append(r2)
            java.lang.String r2 = r7.effectName
            r1.append(r2)
            java.lang.String r2 = " "
            r1.append(r2)
            java.lang.String r2 = r7.res
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            r8.putString(r0, r1)
            goto L_0x0155
        L_0x014e:
            java.lang.String r0 = "image_share"
            java.lang.String r1 = "original"
            r8.putString(r0, r1)
        L_0x0155:
            util.FireBaseHelper r0 = util.FireBaseHelper.getInstance()
            java.lang.String r1 = "Insta_Share"
            r0.LogEvent(r1, r8)
            goto L_0x0312
        L_0x0160:
            androidx.fragment.app.FragmentActivity r8 = r7.getActivity()
            com.hqgames.pencil.sketch.photo.MainActivity r8 = (com.hqgames.pencil.sketch.photo.MainActivity) r8
            android.graphics.Bitmap r0 = r7.effectBitmap
            r8.ShareFacebook(r0)
            r7.image_share = r3
            androidx.fragment.app.FragmentActivity r8 = r7.getActivity()
            java.lang.String r0 = "Please wait while uploading to facebook"
            android.widget.Toast r8 = android.widget.Toast.makeText(r8, r0, r2)
            r8.show()
            android.widget.ImageView r8 = r7.imageView
            android.graphics.drawable.Drawable r8 = r8.getDrawable()
            android.graphics.drawable.BitmapDrawable r8 = (android.graphics.drawable.BitmapDrawable) r8
            android.graphics.Bitmap r8 = r8.getBitmap()
            if (r8 == 0) goto L_0x01a5
            android.widget.ImageView r8 = r7.imageView
            android.graphics.drawable.Drawable r8 = r8.getDrawable()
            android.graphics.drawable.BitmapDrawable r8 = (android.graphics.drawable.BitmapDrawable) r8
            android.graphics.Bitmap r8 = r8.getBitmap()
            android.graphics.Bitmap r0 = r7.effectBitmap
            if (r8 != r0) goto L_0x01a5
            java.lang.String r8 = r7.effectName
            if (r8 == 0) goto L_0x01a1
            java.lang.String r8 = r7.effectName
            helper.Constants.EFFECT_NAME = r8
            goto L_0x01a5
        L_0x01a1:
            java.lang.String r8 = "original"
            helper.Constants.EFFECT_NAME = r8
        L_0x01a5:
            java.lang.String r8 = r7.res
            helper.Constants.RESOLUTION = r8
            android.os.Bundle r8 = new android.os.Bundle
            r8.<init>()
            java.lang.String r0 = "image_share"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "facebook "
            r1.append(r2)
            java.lang.String r2 = helper.Constants.EFFECT_NAME
            r1.append(r2)
            java.lang.String r2 = " "
            r1.append(r2)
            java.lang.String r2 = helper.Constants.RESOLUTION
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            r8.putString(r0, r1)
            util.FireBaseHelper r0 = util.FireBaseHelper.getInstance()
            java.lang.String r1 = "Facebook_Share"
            r0.LogEvent(r1, r8)
            goto L_0x0312
        L_0x01db:
            java.lang.String r8 = "Share"
            java.lang.String r2 = "Share"
            android.util.Log.d(r8, r2)
            android.content.Intent r8 = new android.content.Intent
            java.lang.String r2 = "android.intent.action.SEND"
            r8.<init>(r2)
            java.lang.String r2 = "image/jpeg"
            r8.setType(r2)
            java.io.ByteArrayOutputStream r2 = new java.io.ByteArrayOutputStream
            r2.<init>()
            android.graphics.Bitmap r4 = r7.effectBitmap
            android.graphics.Bitmap$CompressFormat r5 = android.graphics.Bitmap.CompressFormat.JPEG
            r4.compress(r5, r0, r2)
            java.util.Random r0 = new java.util.Random
            r0.<init>()
            r4 = 10000(0x2710, float:1.4013E-41)
            int r0 = r0.nextInt(r4)
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "image-"
            r4.append(r5)
            r4.append(r0)
            java.lang.String r0 = ".jpg"
            r4.append(r0)
            java.lang.String r0 = r4.toString()
            int r4 = android.os.Build.VERSION.SDK_INT
            if (r4 < r1) goto L_0x0230
            java.io.File r4 = new java.io.File
            java.io.File r5 = android.os.Environment.getExternalStorageDirectory()
            r4.<init>(r5, r0)
            java.lang.String r0 = "SDKINT"
            java.lang.String r5 = "Greater 24"
            android.util.Log.d(r0, r5)
            goto L_0x024d
        L_0x0230:
            java.io.File r4 = new java.io.File
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.io.File r6 = android.os.Environment.getExternalStorageDirectory()
            r5.append(r6)
            java.lang.String r6 = java.io.File.separator
            r5.append(r6)
            r5.append(r0)
            java.lang.String r0 = r5.toString()
            r4.<init>(r0)
        L_0x024d:
            r4.createNewFile()     // Catch:{ IOException -> 0x025d }
            java.io.FileOutputStream r0 = new java.io.FileOutputStream     // Catch:{ IOException -> 0x025d }
            r0.<init>(r4)     // Catch:{ IOException -> 0x025d }
            byte[] r2 = r2.toByteArray()     // Catch:{ IOException -> 0x025d }
            r0.write(r2)     // Catch:{ IOException -> 0x025d }
            goto L_0x0261
        L_0x025d:
            r0 = move-exception
            r0.printStackTrace()
        L_0x0261:
            int r0 = android.os.Build.VERSION.SDK_INT
            if (r0 < r1) goto L_0x028f
            androidx.fragment.app.FragmentActivity r0 = r7.getActivity()
            if (r0 == 0) goto L_0x027b
            java.lang.String r0 = "android.intent.extra.STREAM"
            androidx.fragment.app.FragmentActivity r1 = r7.getActivity()
            java.lang.String r2 = "com.hqgames.pencil.sketch.photo.provider"
            android.net.Uri r1 = androidx.core.content.FileProvider.getUriForFile(r1, r2, r4)
            r8.putExtra(r0, r1)
            goto L_0x0298
        L_0x027b:
            java.lang.String r0 = "android.intent.extra.STREAM"
            com.hqgames.pencil.sketch.photo.App r1 = com.hqgames.pencil.sketch.photo.App.getInstance()
            android.content.Context r1 = r1.getApplicationContext()
            java.lang.String r2 = "com.hqgames.pencil.sketch.photo.provider"
            android.net.Uri r1 = androidx.core.content.FileProvider.getUriForFile(r1, r2, r4)
            r8.putExtra(r0, r1)
            goto L_0x0298
        L_0x028f:
            java.lang.String r0 = "android.intent.extra.STREAM"
            android.net.Uri r1 = android.net.Uri.fromFile(r4)
            r8.putExtra(r0, r1)
        L_0x0298:
            r7.image_share = r3
            java.lang.String r0 = "Share Image"
            android.content.Intent r8 = android.content.Intent.createChooser(r8, r0)
            r7.startActivity(r8)
            android.widget.ImageView r8 = r7.imageView
            android.graphics.drawable.Drawable r8 = r8.getDrawable()
            android.graphics.drawable.BitmapDrawable r8 = (android.graphics.drawable.BitmapDrawable) r8
            android.graphics.Bitmap r8 = r8.getBitmap()
            if (r8 == 0) goto L_0x02f8
            android.widget.ImageView r8 = r7.imageView
            android.graphics.drawable.Drawable r8 = r8.getDrawable()
            android.graphics.drawable.BitmapDrawable r8 = (android.graphics.drawable.BitmapDrawable) r8
            android.graphics.Bitmap r8 = r8.getBitmap()
            android.graphics.Bitmap r0 = r7.effectBitmap
            if (r8 != r0) goto L_0x02f8
            android.os.Bundle r8 = new android.os.Bundle
            r8.<init>()
            java.lang.String r0 = r7.effectName
            if (r0 == 0) goto L_0x02e8
            java.lang.String r0 = "image_share"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = r7.effectName
            r1.append(r2)
            java.lang.String r2 = " "
            r1.append(r2)
            java.lang.String r2 = r7.res
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            r8.putString(r0, r1)
            goto L_0x02ef
        L_0x02e8:
            java.lang.String r0 = "image_share"
            java.lang.String r1 = "original"
            r8.putString(r0, r1)
        L_0x02ef:
            util.FireBaseHelper r0 = util.FireBaseHelper.getInstance()
            java.lang.String r1 = "Image_Share"
            r0.LogEvent(r1, r8)
        L_0x02f8:
            java.lang.String r8 = "SDKINT"
            java.lang.String r0 = "Greater 24"
            android.util.Log.d(r8, r0)
            goto L_0x0312
        L_0x0300:
            r7.dialog_show = r2
            androidx.fragment.app.FragmentActivity r8 = r7.getActivity()
            com.hqgames.pencil.sketch.photo.AppRater r8 = com.hqgames.pencil.sketch.photo.AppRater.initialize(r8)
            r8.IncrementEvent()
            android.graphics.Bitmap r8 = r7.effectBitmap
            r7.SaveImage(r8)
        L_0x0312:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: p049ui.ExportFragment.ApplyFeatures(java.lang.String):void");
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void checkExportAvailable(java.lang.String r8) {
        /*
            r7 = this;
            androidx.fragment.app.FragmentActivity r0 = r7.getActivity()
            int r0 = helper.Credits.getCredits(r0)
            r7.credits = r0
            int r0 = r8.hashCode()
            r1 = 0
            r2 = 2
            switch(r0) {
                case -511242068: goto L_0x0046;
                case 3324: goto L_0x003c;
                case 103162: goto L_0x0032;
                case 3573663: goto L_0x0028;
                case 97622021: goto L_0x001e;
                case 1086463900: goto L_0x0014;
                default: goto L_0x0013;
            }
        L_0x0013:
            goto L_0x0050
        L_0x0014:
            java.lang.String r0 = "regular"
            boolean r8 = r8.equals(r0)
            if (r8 == 0) goto L_0x0050
            r8 = 0
            goto L_0x0051
        L_0x001e:
            java.lang.String r0 = "fourk"
            boolean r8 = r8.equals(r0)
            if (r8 == 0) goto L_0x0050
            r8 = 5
            goto L_0x0051
        L_0x0028:
            java.lang.String r0 = "twok"
            boolean r8 = r8.equals(r0)
            if (r8 == 0) goto L_0x0050
            r8 = 4
            goto L_0x0051
        L_0x0032:
            java.lang.String r0 = "hdv"
            boolean r8 = r8.equals(r0)
            if (r8 == 0) goto L_0x0050
            r8 = 2
            goto L_0x0051
        L_0x003c:
            java.lang.String r0 = "hd"
            boolean r8 = r8.equals(r0)
            if (r8 == 0) goto L_0x0050
            r8 = 1
            goto L_0x0051
        L_0x0046:
            java.lang.String r0 = "full_hd"
            boolean r8 = r8.equals(r0)
            if (r8 == 0) goto L_0x0050
            r8 = 3
            goto L_0x0051
        L_0x0050:
            r8 = -1
        L_0x0051:
            r0 = 25
            r3 = 50
            r4 = 20
            r5 = 30
            r6 = 40
            switch(r8) {
                case 0: goto L_0x01f9;
                case 1: goto L_0x01cf;
                case 2: goto L_0x01a5;
                case 3: goto L_0x0138;
                case 4: goto L_0x00cd;
                case 5: goto L_0x0060;
                default: goto L_0x005e;
            }
        L_0x005e:
            goto L_0x0222
        L_0x0060:
            java.util.List<java.lang.String> r8 = r7.sequence
            if (r8 == 0) goto L_0x00c6
            java.util.List<java.lang.String> r8 = r7.sequence
            java.lang.String r4 = "effect"
            boolean r8 = r8.contains(r4)
            if (r8 != 0) goto L_0x0082
            java.util.List<java.lang.String> r8 = r7.sequence
            java.lang.String r4 = "filter"
            boolean r8 = r8.contains(r4)
            if (r8 != 0) goto L_0x0082
            java.util.List<java.lang.String> r8 = r7.sequence
            java.lang.String r4 = "enhance"
            boolean r8 = r8.contains(r4)
            if (r8 == 0) goto L_0x00c6
        L_0x0082:
            r8 = 110(0x6e, float:1.54E-43)
            r7.requiredCredits = r8
            r8 = 60
            r7.UpperPrice = r8
            r7.LowerPrice = r3
            r7.setFeaturesCredits(r5, r0)
            boolean r8 = helper.Constants.HD_PACK_PURCHASED
            if (r8 == 0) goto L_0x00b9
            ui.ExportFragment$ExportOptionsItemsAdapter r8 = r7.itemsAdapter
            java.lang.String r0 = "4K"
            java.lang.String r1 = "Save"
            r8.UnLock2(r0, r1)
            ui.ExportFragment$ExportOptionsItemsAdapter r8 = r7.itemsAdapter
            java.lang.String r0 = "4K"
            java.lang.String r1 = "Share"
            r8.UnLock2(r0, r1)
            ui.ExportFragment$ExportOptionsItemsAdapter r8 = r7.itemsAdapter
            java.lang.String r0 = "4K"
            java.lang.String r1 = "Facebook"
            r8.UnLock2(r0, r1)
            ui.ExportFragment$ExportOptionsItemsAdapter r8 = r7.itemsAdapter
            java.lang.String r0 = "4K"
            java.lang.String r1 = "Instagram"
            r8.UnLock2(r0, r1)
            goto L_0x0222
        L_0x00b9:
            ui.ExportFragment$ExportOptionsItemsAdapter r8 = r7.itemsAdapter
            int r0 = r7.UpperPrice
            int r0 = r0 / r2
            int r3 = r7.LowerPrice
            int r3 = r3 / r2
            r8.ApplyBlockage(r0, r3, r1)
            goto L_0x0222
        L_0x00c6:
            ui.ExportFragment$ExportOptionsItemsAdapter r8 = r7.itemsAdapter
            r8.RemoveBlockage()
            goto L_0x0222
        L_0x00cd:
            java.util.List<java.lang.String> r8 = r7.sequence
            if (r8 == 0) goto L_0x0131
            java.util.List<java.lang.String> r8 = r7.sequence
            java.lang.String r5 = "effect"
            boolean r8 = r8.contains(r5)
            if (r8 != 0) goto L_0x00ef
            java.util.List<java.lang.String> r8 = r7.sequence
            java.lang.String r5 = "filter"
            boolean r8 = r8.contains(r5)
            if (r8 != 0) goto L_0x00ef
            java.util.List<java.lang.String> r8 = r7.sequence
            java.lang.String r5 = "enhance"
            boolean r8 = r8.contains(r5)
            if (r8 == 0) goto L_0x0131
        L_0x00ef:
            r8 = 90
            r7.requiredCredits = r8
            r7.UpperPrice = r3
            r7.LowerPrice = r6
            r7.setFeaturesCredits(r0, r4)
            boolean r8 = helper.Constants.HD_PACK_PURCHASED
            if (r8 == 0) goto L_0x0124
            ui.ExportFragment$ExportOptionsItemsAdapter r8 = r7.itemsAdapter
            java.lang.String r0 = "2K"
            java.lang.String r1 = "Save"
            r8.UnLock2(r0, r1)
            ui.ExportFragment$ExportOptionsItemsAdapter r8 = r7.itemsAdapter
            java.lang.String r0 = "2K"
            java.lang.String r1 = "Share"
            r8.UnLock2(r0, r1)
            ui.ExportFragment$ExportOptionsItemsAdapter r8 = r7.itemsAdapter
            java.lang.String r0 = "2K"
            java.lang.String r1 = "Facebook"
            r8.UnLock2(r0, r1)
            ui.ExportFragment$ExportOptionsItemsAdapter r8 = r7.itemsAdapter
            java.lang.String r0 = "2K"
            java.lang.String r1 = "Instagram"
            r8.UnLock2(r0, r1)
            goto L_0x0222
        L_0x0124:
            ui.ExportFragment$ExportOptionsItemsAdapter r8 = r7.itemsAdapter
            int r0 = r7.UpperPrice
            int r0 = r0 / r2
            int r3 = r7.LowerPrice
            int r3 = r3 / r2
            r8.ApplyBlockage(r0, r3, r1)
            goto L_0x0222
        L_0x0131:
            ui.ExportFragment$ExportOptionsItemsAdapter r8 = r7.itemsAdapter
            r8.RemoveBlockage()
            goto L_0x0222
        L_0x0138:
            java.util.List<java.lang.String> r8 = r7.sequence
            if (r8 == 0) goto L_0x019e
            java.util.List<java.lang.String> r8 = r7.sequence
            java.lang.String r0 = "effect"
            boolean r8 = r8.contains(r0)
            if (r8 != 0) goto L_0x015a
            java.util.List<java.lang.String> r8 = r7.sequence
            java.lang.String r0 = "filter"
            boolean r8 = r8.contains(r0)
            if (r8 != 0) goto L_0x015a
            java.util.List<java.lang.String> r8 = r7.sequence
            java.lang.String r0 = "enhance"
            boolean r8 = r8.contains(r0)
            if (r8 == 0) goto L_0x019e
        L_0x015a:
            r8 = 70
            r7.requiredCredits = r8
            r7.UpperPrice = r6
            r7.LowerPrice = r5
            r8 = 15
            r7.setFeaturesCredits(r4, r8)
            boolean r8 = helper.Constants.HD_PACK_PURCHASED
            if (r8 == 0) goto L_0x0191
            ui.ExportFragment$ExportOptionsItemsAdapter r8 = r7.itemsAdapter
            java.lang.String r0 = "Full Hd"
            java.lang.String r1 = "Save"
            r8.UnLock2(r0, r1)
            ui.ExportFragment$ExportOptionsItemsAdapter r8 = r7.itemsAdapter
            java.lang.String r0 = "Full Hd"
            java.lang.String r1 = "Share"
            r8.UnLock2(r0, r1)
            ui.ExportFragment$ExportOptionsItemsAdapter r8 = r7.itemsAdapter
            java.lang.String r0 = "Full Hd"
            java.lang.String r1 = "Facebook"
            r8.UnLock2(r0, r1)
            ui.ExportFragment$ExportOptionsItemsAdapter r8 = r7.itemsAdapter
            java.lang.String r0 = "Full Hd"
            java.lang.String r1 = "Instagram"
            r8.UnLock2(r0, r1)
            goto L_0x0222
        L_0x0191:
            ui.ExportFragment$ExportOptionsItemsAdapter r8 = r7.itemsAdapter
            int r0 = r7.UpperPrice
            int r0 = r0 / r2
            int r3 = r7.LowerPrice
            int r3 = r3 / r2
            r8.ApplyBlockage(r0, r3, r1)
            goto L_0x0222
        L_0x019e:
            ui.ExportFragment$ExportOptionsItemsAdapter r8 = r7.itemsAdapter
            r8.RemoveBlockage()
            goto L_0x0222
        L_0x01a5:
            ui.ExportFragment$ExportOptionsItemsAdapter r8 = r7.itemsAdapter
            java.lang.String r0 = "HDV"
            java.lang.String r1 = "Save"
            r8.UnLock2(r0, r1)
            ui.ExportFragment$ExportOptionsItemsAdapter r8 = r7.itemsAdapter
            java.lang.String r0 = "HDV"
            java.lang.String r1 = "Share"
            r8.UnLock2(r0, r1)
            ui.ExportFragment$ExportOptionsItemsAdapter r8 = r7.itemsAdapter
            java.lang.String r0 = "HDV"
            java.lang.String r1 = "Facebook"
            r8.UnLock2(r0, r1)
            ui.ExportFragment$ExportOptionsItemsAdapter r8 = r7.itemsAdapter
            java.lang.String r0 = "HDV"
            java.lang.String r1 = "Instagram"
            r8.UnLock2(r0, r1)
            ui.ExportFragment$ExportOptionsItemsAdapter r8 = r7.itemsAdapter
            r8.RemoveBlockage()
            goto L_0x0222
        L_0x01cf:
            ui.ExportFragment$ExportOptionsItemsAdapter r8 = r7.itemsAdapter
            java.lang.String r0 = "Hd"
            java.lang.String r1 = "Save"
            r8.UnLock2(r0, r1)
            ui.ExportFragment$ExportOptionsItemsAdapter r8 = r7.itemsAdapter
            java.lang.String r0 = "Hd"
            java.lang.String r1 = "Share"
            r8.UnLock2(r0, r1)
            ui.ExportFragment$ExportOptionsItemsAdapter r8 = r7.itemsAdapter
            java.lang.String r0 = "Hd"
            java.lang.String r1 = "Facebook"
            r8.UnLock2(r0, r1)
            ui.ExportFragment$ExportOptionsItemsAdapter r8 = r7.itemsAdapter
            java.lang.String r0 = "Hd"
            java.lang.String r1 = "Instagram"
            r8.UnLock2(r0, r1)
            ui.ExportFragment$ExportOptionsItemsAdapter r8 = r7.itemsAdapter
            r8.RemoveBlockage()
            goto L_0x0222
        L_0x01f9:
            ui.ExportFragment$ExportOptionsItemsAdapter r8 = r7.itemsAdapter
            java.lang.String r0 = "Regular"
            java.lang.String r1 = "Save"
            r8.UnLock(r0, r1)
            ui.ExportFragment$ExportOptionsItemsAdapter r8 = r7.itemsAdapter
            java.lang.String r0 = "Regular"
            java.lang.String r1 = "Share"
            r8.UnLock(r0, r1)
            ui.ExportFragment$ExportOptionsItemsAdapter r8 = r7.itemsAdapter
            java.lang.String r0 = "Regular"
            java.lang.String r1 = "Facebook"
            r8.UnLock(r0, r1)
            ui.ExportFragment$ExportOptionsItemsAdapter r8 = r7.itemsAdapter
            java.lang.String r0 = "Regular"
            java.lang.String r1 = "Instagram"
            r8.UnLock(r0, r1)
            ui.ExportFragment$ExportOptionsItemsAdapter r8 = r7.itemsAdapter
            r8.RemoveBlockage()
        L_0x0222:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: p049ui.ExportFragment.checkExportAvailable(java.lang.String):void");
    }

    private void setFeaturesCredits(int i, int i2) {
        this.itemsAdapter.SetCredits(0, i);
        this.itemsAdapter.SetCredits(1, i);
        this.itemsAdapter.SetCredits(2, i2);
        this.itemsAdapter.SetCredits(3, i2);
    }

    /* renamed from: ui.ExportFragment$ExportOptionsItemsAdapter */
    public class ExportOptionsItemsAdapter extends RecyclerView.Adapter<ExportOptionsItemsAdapter.MyViewHolder> {
        /* access modifiers changed from: private */
        public ExportOptionListener optionSelected;
        private List<ExportOptionsView> viewElements;

        public ExportOptionsItemsAdapter(List<ExportOptionsView> list, ExportOptionListener exportOptionListener) {
            this.viewElements = list;
            this.optionSelected = exportOptionListener;
        }

        public void RemoveBlockage() {
            for (int i = 0; i < this.viewElements.size(); i++) {
                HashMap<String, String> hashMap = ExportFragment.this.unlockFeatureList;
                if (hashMap.containsKey(ExportFragment.this.res + this.viewElements.get(i).getTitle())) {
                    HashMap<String, String> hashMap2 = ExportFragment.this.unlockFeatureList;
                    if (hashMap2.get(ExportFragment.this.res + this.viewElements.get(i).getTitle()).equals(this.viewElements.get(i).getTitle())) {
                        this.viewElements.get(i).setLocked(false);
                        this.viewElements.get(i).setCredits((String) null);
                        this.viewElements.get(i).setLockImage(1);
                        Log.d("removeblockage 0-3", "featurelist");
                        notifyItemChanged(i);
                        Log.d("CheckExport", "remove");
                    }
                }
                this.viewElements.get(i).setLockImage(1);
                this.viewElements.get(i).setLocked(true);
                notifyItemChanged(i);
                Log.d("CheckExport", "remove");
            }
        }

        public void RemoveBlockage(int i) {
            HashMap<String, String> hashMap = ExportFragment.this.unlockFeatureList;
            if (hashMap.containsKey(ExportFragment.this.res + this.viewElements.get(i).getTitle())) {
                HashMap<String, String> hashMap2 = ExportFragment.this.unlockFeatureList;
                if (hashMap2.get(ExportFragment.this.res + this.viewElements.get(i).getTitle()).equals(this.viewElements.get(i).getTitle())) {
                    this.viewElements.get(i).setLocked(false);
                    this.viewElements.get(i).setCredits((String) null);
                    this.viewElements.get(i).setLockImage(1);
                    Log.d("removeblockage ind", "featurelist");
                    notifyItemChanged(i);
                    Log.d("CheckExport", "Remove Blockage index");
                }
            }
            this.viewElements.get(i).setLockImage(1);
            this.viewElements.get(i).setLocked(true);
            notifyItemChanged(i);
            Log.d("CheckExport", "Remove Blockage index");
        }

        public void UnLock(String str, String str2) {
            for (int i = 0; i < this.viewElements.size(); i++) {
                if (this.viewElements.get(i).getTitle().equals(str2)) {
                    this.viewElements.get(i).setLocked(false);
                    this.viewElements.get(i).setCredits((String) null);
                    HashMap<String, String> hashMap = ExportFragment.this.unlockFeatureList;
                    hashMap.put(str + str2, str2);
                    if (ExportFragment.this.resolution != null) {
                        ExportFragment.this.checkExportAvailable(ExportFragment.this.resolution);
                    }
                    notifyItemChanged(i);
                }
            }
        }

        public void UnLock2(String str, String str2) {
            for (int i = 0; i < this.viewElements.size(); i++) {
                if (this.viewElements.get(i).getTitle().equals(str2)) {
                    this.viewElements.get(i).setLocked(false);
                    this.viewElements.get(i).setCredits((String) null);
                    HashMap<String, String> hashMap = ExportFragment.this.unlockFeatureList;
                    hashMap.put(str + str2, str2);
                    notifyItemChanged(i);
                }
            }
        }

        public void SetCredits(int i, int i2) {
            this.viewElements.get(i).setCreditsamount(i2);
            this.viewElements.get(i).setCredits(String.valueOf(i2 + " Credits"));
            notifyItemChanged(i);
        }

        public void ApplyBlockage(int i, int i2, boolean z) {
            for (int i3 = 0; i3 < this.viewElements.size(); i3++) {
                if (i3 < 2) {
                    this.viewElements.get(i3).setLockImage(0);
                    this.viewElements.get(i3).setCredits(String.valueOf("Locked"));
                    this.viewElements.get(i3).setLocked(true);
                    HashMap<String, String> hashMap = ExportFragment.this.unlockFeatureList;
                    if (hashMap.containsKey(ExportFragment.this.res + this.viewElements.get(i3).getTitle())) {
                        HashMap<String, String> hashMap2 = ExportFragment.this.unlockFeatureList;
                        if (hashMap2.get(ExportFragment.this.res + this.viewElements.get(i3).getTitle()).equals(this.viewElements.get(i3).getTitle())) {
                            this.viewElements.get(i3).setLocked(false);
                            this.viewElements.get(i3).setCredits((String) null);
                            this.viewElements.get(i3).setLockImage(1);
                            Log.d("ApplyBlockage 0-1", "featurelist");
                        }
                    }
                } else {
                    this.viewElements.get(i3).setLockImage(0);
                    this.viewElements.get(i3).setCredits(String.valueOf("Locked"));
                    this.viewElements.get(i3).setLocked(true);
                    Log.d("creditsupdate", "ApplyBlockage 0-1 " + String.valueOf(i));
                    HashMap<String, String> hashMap3 = ExportFragment.this.unlockFeatureList;
                    if (hashMap3.containsKey(ExportFragment.this.res + this.viewElements.get(i3).getTitle())) {
                        HashMap<String, String> hashMap4 = ExportFragment.this.unlockFeatureList;
                        if (hashMap4.get(ExportFragment.this.res + this.viewElements.get(i3).getTitle()).equals(this.viewElements.get(i3).getTitle())) {
                            this.viewElements.get(i3).setLocked(false);
                            this.viewElements.get(i3).setCredits((String) null);
                            this.viewElements.get(i3).setLockImage(1);
                            Log.d("ApplyBlockage 0-1", "featurelist");
                        }
                    }
                }
                notifyItemChanged(i3);
            }
            Log.d("CheckExport", "apply bloc all");
        }

        public void ApplyBlockage(int i, int i2) {
            HashMap<String, String> hashMap = ExportFragment.this.unlockFeatureList;
            if (hashMap.containsKey(ExportFragment.this.res + this.viewElements.get(i).getTitle())) {
                HashMap<String, String> hashMap2 = ExportFragment.this.unlockFeatureList;
                if (hashMap2.get(ExportFragment.this.res + this.viewElements.get(i).getTitle()).equals(this.viewElements.get(i).getTitle())) {
                    this.viewElements.get(i).setLocked(false);
                    this.viewElements.get(i).setCredits((String) null);
                    this.viewElements.get(i).setLockImage(1);
                    Log.d("ApplyBlockage ind", "featurelist");
                    notifyItemChanged(i);
                    Log.d("CheckExport", "Apply bloc ind");
                }
            }
            this.viewElements.get(i).setLockImage(0);
            this.viewElements.get(i).setCredits(String.valueOf(i2 + " Credits"));
            this.viewElements.get(i).setLocked(true);
            notifyItemChanged(i);
            Log.d("CheckExport", "Apply bloc ind");
        }

        @NonNull
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return new MyViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.export_bottom_view, viewGroup, false));
        }

        public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
            final ExportOptionsView exportOptionsView = this.viewElements.get(i);
            if (exportOptionsView.getCredits() != null) {
                if (myViewHolder.textLayout.getVisibility() != View.VISIBLE) {
                    myViewHolder.textLayout.setVisibility(View.VISIBLE);
                }
                myViewHolder.credit.setText(exportOptionsView.getCredits());
            } else {
                if (myViewHolder.textLayout.getVisibility() == View.VISIBLE) {
                    myViewHolder.textLayout.setVisibility(View.GONE);
                }
                myViewHolder.credit.setText("");
            }
            if (exportOptionsView.getImgId() != 0) {
                myViewHolder.icon.setImageResource(exportOptionsView.getImgId());
            }
            if (exportOptionsView.getLockImage() != 0) {
                myViewHolder.lock_image.setVisibility(View.GONE);
            } else {
                myViewHolder.lock_image.setVisibility(View.VISIBLE);
            }
            myViewHolder.layout.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    ExportOptionsItemsAdapter.this.optionSelected.OnExportButtonClick(exportOptionsView);
                }
            });
        }

        public int getItemCount() {
            return this.viewElements.size();
        }

        /* renamed from: ui.ExportFragment$ExportOptionsItemsAdapter$MyViewHolder */
        public class MyViewHolder extends RecyclerView.ViewHolder {
            TextView credit;
            ImageView icon;
            RelativeLayout layout;
            ImageView lock_image;
            RelativeLayout textLayout;

            public MyViewHolder(View view) {
                super(view);
                this.textLayout = (RelativeLayout) view.findViewById(R.id.text_layout);
                this.credit = (TextView) view.findViewById(R.id.credit_amount);
                this.icon = (ImageView) view.findViewById(R.id.feature_icon);
                this.layout = (RelativeLayout) view.findViewById(R.id.outer);
                this.lock_image = (ImageView) view.findViewById(R.id.lock);
                if (Constants.CUSTOM_EXPORT_FUNTION_BUTTON_ENABLED && Constants.EXPORT_FUNCTION_BUTTON_COLOR != null) {
                    this.textLayout.setBackgroundColor(Color.parseColor(Constants.EXPORT_FUNCTION_BUTTON_COLOR));
                }
            }
        }
    }

    private void SaveImage(Bitmap bitmap) {
        Constants.AD_ON_SAVING = true;
        String file = Environment.getExternalStorageDirectory().toString();
        File file2 = new File(file + "/pencil_photo_sketch");
        file2.mkdirs();
        int nextInt = new Random().nextInt(10000);
        File file3 = new File(file2, "Image-" + nextInt + ".jpg");
        if (file3.exists()) {
            file3.delete();
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file3);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            FragmentActivity activity = getActivity();
            activity.sendBroadcast(new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", Uri.parse("file://" + file2.getAbsolutePath())));
            MediaScannerConnection.scanFile(getActivity(), new String[]{file3.toString()}, (String[]) null, new MediaScannerConnection.OnScanCompletedListener() {
                public void onScanCompleted(String str, Uri uri) {
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        FragmentActivity activity2 = getActivity();
        Toast.makeText(activity2, "Image Saved Successfully to" + file2.getAbsolutePath(), Toast.LENGTH_LONG).show();
        if (AppRater.initialize(getActivity()).isOverIncrementCount() && !SharedPreferencesManager.getSomeBoolValue(getActivity(), Constants.APP_RATED) && !SharedPreferencesManager.HasKey(getActivity(), Constants.EXPORT_RATING_VIEW_LIMIT_REACHED)) {
            Log.d("RateDialog", "Show");
            SharedPreferencesManager.setSomeBoolValue(getActivity(), Constants.EVENT_RATE_DIALOG_SHOWED, true);
            this.dialog_show = true;
            initializeRateDialog();
        }
        if (!this.dialog_show) {
            if (!Constants.REMOVE_ADS && AdUtil.getInstance().isSaveInterstitialAvailable() && Constants.SAVE_INTERSTITIAL && MainActivity.isActivityVisible()) {
                AdUtil.getInstance().ShowSaveInterstitial();
            }
            Bundle bundle = new Bundle();
            bundle.putString("AdNetwork", "ImageSaveAd");
            FireBaseHelper.getInstance().LogEvent("Ad_Analytics", bundle);
        }
        if (bitmap == this.effectBitmap) {
            Bundle bundle2 = new Bundle();
            if (this.effectName != null) {
                bundle2.putString("image_saved", this.effectName + " " + this.res);
            } else {
                bundle2.putString("image_saved", "original");
            }
            FireBaseHelper.getInstance().LogEvent("Image_Analytics", bundle2);
        }
    }

    public void initializeRateDialog() {
        this.dialog = new Dialog(getActivity(), R.style.RateUsDialogTheme);
        this.dialog.getWindow().setDimAmount(0.0f);
        this.dialog.setContentView(R.layout.rate_dialog);
        RelativeLayout relativeLayout = (RelativeLayout) this.dialog.findViewById(R.id.background);
        GradientDrawable gradientDrawable = new GradientDrawable();
        this.closeButtonText = getString(R.string.not);
        this.okButtonText = getString(R.string.ok);
        this.closeButtonText2 = getString(R.string.no_thanks);
        this.okButtonText2 = getString(R.string.ok_sure);
        if (Constants.CUSTOM_EXPORT_RATE_BUTTON_OK_TEXT != null && !Constants.CUSTOM_EXPORT_RATE_BUTTON_OK_TEXT.equals("null")) {
            this.okButtonText = Constants.CUSTOM_EXPORT_RATE_BUTTON_OK_TEXT;
        }
        if (Constants.CUSTOM_EXPORT_RATE_BUTTON_CLOSE_TEXT != null && !Constants.CUSTOM_EXPORT_RATE_BUTTON_CLOSE_TEXT.equals("null")) {
            this.closeButtonText = Constants.CUSTOM_EXPORT_RATE_BUTTON_CLOSE_TEXT;
        }
        if (Constants.CUSTOM_EXPORT_RATE_BUTTON_OK_TEXT_2 != null && !Constants.CUSTOM_EXPORT_RATE_BUTTON_OK_TEXT_2.equals("null")) {
            this.okButtonText2 = Constants.CUSTOM_EXPORT_RATE_BUTTON_OK_TEXT_2;
        }
        if (Constants.CUSTOM_EXPORT_RATE_BUTTON_CLOSE_TEXT_2 != null && !Constants.CUSTOM_EXPORT_RATE_BUTTON_CLOSE_TEXT_2.equals("null")) {
            this.closeButtonText2 = Constants.CUSTOM_EXPORT_RATE_BUTTON_CLOSE_TEXT_2;
        }
        Button button = (Button) this.dialog.findViewById(R.id.yes);
        Button button2 = (Button) this.dialog.findViewById(R.id.no);
        TextView textView = (TextView) this.dialog.findViewById(R.id.rating_text);
        TextView textView2 = (TextView) this.dialog.findViewById(R.id.dialog_title);
        View findViewById = this.dialog.findViewById(R.id.divider);
        ScaleRatingBar scaleRatingBar = (ScaleRatingBar) this.dialog.findViewById(R.id.simpleRatingBar);
        LinearLayout linearLayout = (LinearLayout) this.dialog.findViewById(R.id.layout);
        final LinearLayout linearLayout2 = linearLayout;
        final TextView textView3 = textView;
        final Button button3 = button;
        LinearLayout linearLayout3 = linearLayout;
        //C513515 r15 = r0;
        final Button button4 = button2;
        BaseRatingBar.OnRatingChangeListener r0 = new BaseRatingBar.OnRatingChangeListener() {
            public void onRatingChange(BaseRatingBar baseRatingBar, float f, boolean z) {
                if (f >= 4.0f) {
                    try {
                        ExportFragment exportFragment = ExportFragment.this;
                        exportFragment.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=" + ExportFragment.this.getActivity().getPackageName())));
                    } catch (ActivityNotFoundException unused) {
                        ExportFragment exportFragment2 = ExportFragment.this;
                        exportFragment2.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=" + ExportFragment.this.getActivity().getPackageName())));
                    }
                    baseRatingBar.setVisibility(View.GONE);
                    linearLayout2.setVisibility(View.VISIBLE);
                    textView3.setText(ExportFragment.this.getString(R.string.thanks_text));
                    button3.setText(ExportFragment.this.getString(R.string.close));
                    button4.setEnabled(false);
                    Bundle bundle = new Bundle();
                    bundle.putString("User_Experience", "Export_Rate_4_5");
                    FireBaseHelper.getInstance().LogEvent("Rate_Analytics", bundle);
                    return;
                }
                baseRatingBar.setVisibility(View.GONE);
                linearLayout2.setVisibility(View.VISIBLE);
                textView3.setText(ExportFragment.this.getString(R.string.thanks_text));
                button3.setText(ExportFragment.this.getString(R.string.close));
                button4.setEnabled(false);
                Bundle bundle2 = new Bundle();
                bundle2.putString("User_Experience", "Export_Rate_3");
                FireBaseHelper.getInstance().LogEvent("Rate_Analytics", bundle2);
            }
        };
        //scaleRatingBar.setOnRatingChangeListener(r15);
        scaleRatingBar.setOnRatingChangeListener(r0);
        if (Constants.CUSTOM_EXPORT_RATE_DIALOG_TITLE_TEXT == null || Constants.CUSTOM_EXPORT_RATE_DIALOG_TITLE_TEXT.equals("null")) {
            textView2.setText(R.string.app_name);
        } else {
            textView2.setText(Constants.CUSTOM_EXPORT_RATE_DIALOG_TITLE_TEXT);
        }
        if (Constants.CUSTOM_EXPORT_RATE_DIALOG_TEXT == null || Constants.CUSTOM_EXPORT_RATE_DIALOG_TEXT.equals("null")) {
            textView.setText(R.string.custom_rate_dialog_title);
        } else {
            textView.setText(Constants.CUSTOM_EXPORT_RATE_DIALOG_TEXT);
        }
        button2.setText(this.closeButtonText);
        button.setText("Yes!");
        if (Constants.CUSTOM_EXPORT_RATE_VIEW) {
            if (Constants.EXPORT_RATE_VIEW_OK_BUTTON_COLOR != null) {
                GradientDrawable gradientDrawable2 = new GradientDrawable();
                gradientDrawable2.setColor(Color.parseColor(Constants.EXPORT_RATE_VIEW_OK_BUTTON_COLOR));
                gradientDrawable2.setStroke(((MainActivity) getActivity()).dpToPx(2), Color.parseColor(Constants.EXPORT_RATE_VIEW_OK_BUTTON_STROKE));
                gradientDrawable2.setCornerRadius((float) ((MainActivity) getActivity()).dpToPx(2));
                button.setBackground(gradientDrawable2);
                button.setTextColor(Color.parseColor(Constants.EXPORT_RATE_VIEW_OK_BUTTON_TEXT_COLOR));
            }
            if (Constants.EXPORT_RATE_VIEW_NO_BUTTON_COLOR != null) {
                GradientDrawable gradientDrawable3 = new GradientDrawable();
                gradientDrawable3.setColor(Color.parseColor(Constants.EXPORT_RATE_VIEW_NO_BUTTON_COLOR));
                gradientDrawable3.setStroke(((MainActivity) getActivity()).dpToPx(2), Color.parseColor(Constants.EXPORT_RATE_VIEW_NO_BUTTON_STROKE));
                gradientDrawable3.setCornerRadius((float) ((MainActivity) getActivity()).dpToPx(2));
                button2.setBackground(gradientDrawable3);
                button2.setTextColor(Color.parseColor(Constants.EXPORT_RATE_VIEW_NO_BUTTON_TEXT_COLOR));
            }
            if (Constants.EXPORT_RATE_VIEW_TEXT_COLOR != null) {
                textView.setTextColor(Color.parseColor(Constants.EXPORT_RATE_VIEW_TEXT_COLOR));
                textView2.setTextColor(Color.parseColor(Constants.EXPORT_RATE_VIEW_TEXT_COLOR));
            }
            if (Constants.EXPORT_RATE_VIEW_DIVIDER_COLOR != null) {
                findViewById.setBackgroundColor(Color.parseColor(Constants.EXPORT_RATE_VIEW_DIVIDER_COLOR));
            }
            if (!(Constants.EXPORT_RATE_VIEW_BG_COLOR == null || Constants.CUSTOM_EXPORT_RATE_DIALOG_STROKE_COLOR == null)) {
                gradientDrawable.setColor(Color.parseColor(Constants.EXPORT_RATE_VIEW_BG_COLOR));
                gradientDrawable.setStroke(((MainActivity) getActivity()).dpToPx(2), Color.parseColor(Constants.CUSTOM_EXPORT_RATE_DIALOG_STROKE_COLOR));
                relativeLayout.setBackground(gradientDrawable);
            }
        }
        this.dialog.setCancelable(false);
        if (SharedPreferencesManager.HasKey(getActivity(), Constants.NEW_EVENT_CALLED)) {
            Bundle bundle = new Bundle();
            bundle.putString("User_Experience", "Export_Fragment_S_T-RateView_Show");
            FireBaseHelper.getInstance().LogEvent("Rate_Analytics", bundle);
        }
        final Button button5 = button;
        final TextView textView4 = textView;
        final ScaleRatingBar scaleRatingBar2 = scaleRatingBar;
        final LinearLayout linearLayout4 = linearLayout3;
        final Button button6 = button2;
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (button5.getText().equals("Yes!")) {
                    if (Constants.CUSTOM_EXPORT_RATE_DIALOG_TEXT_2 == null || Constants.CUSTOM_EXPORT_RATE_DIALOG_TEXT_2.equals("null")) {
                        textView4.setText(R.string.rate_text);
                    } else {
                        textView4.setText(Constants.CUSTOM_EXPORT_RATE_DIALOG_TEXT_2);
                    }
                    if (Constants.EXPORT_RATING_BAR) {
                        scaleRatingBar2.setVisibility(View.VISIBLE);
                        linearLayout4.setVisibility(View.GONE);
                    }
                    button5.setText(ExportFragment.this.okButtonText2);
                    button6.setText(ExportFragment.this.closeButtonText2);
                    if (!SharedPreferencesManager.HasKey(ExportFragment.this.getActivity(), Constants.NEW_EVENT_CALLED)) {
                        Bundle bundle = new Bundle();
                        bundle.putString("User_Experience", "Export_Fragment-Enjoyed");
                        FireBaseHelper.getInstance().LogEvent("Rate_Analytics", bundle);
                    } else {
                        Bundle bundle2 = new Bundle();
                        bundle2.putString("User_Experience", "Export_Fragment_S_T-Enjoyed");
                        FireBaseHelper.getInstance().LogEvent("Rate_Analytics", bundle2);
                    }
                    ExportFragment.this.enjoyPencilPhotoSketch_2 = true;
                } else if (button5.getText().equals(ExportFragment.this.okButtonText2)) {
                    if (ExportFragment.this.feedback_2) {
                        Intent intent = new Intent("android.intent.action.SEND");
                        intent.putExtra("android.intent.extra.EMAIL", new String[]{"hqgames1080@gmail.com"});
                        intent.putExtra("android.intent.extra.SUBJECT", "Pencil Photo Sketch:FeedBack");
                        intent.setType("message/rfc822");
                        ExportFragment.this.startActivity(Intent.createChooser(intent, "Select Email Sending App :"));
                        textView4.setText(ExportFragment.this.getString(R.string.thanks_text));
                        button5.setText(ExportFragment.this.getString(R.string.close));
                        button6.setEnabled(false);
                        if (SharedPreferencesManager.HasKey(ExportFragment.this.getActivity(), Constants.NEW_EVENT_CALLED) && SharedPreferencesManager.HasKey(ExportFragment.this.getActivity(), Constants.NEW_EVENT_CALLED)) {
                            if (!SharedPreferencesManager.HasKey(ExportFragment.this.getActivity(), Constants.APP_RATED) && !SharedPreferencesManager.getSomeBoolValue(ExportFragment.this.getActivity(), Constants.APP_RATED)) {
                                Bundle bundle3 = new Bundle();
                                bundle3.putString("not_rate", "feedback_done");
                                FireBaseHelper.getInstance().LogEvent("Not_Rate", bundle3);
                            }
                            SharedPreferencesManager.setSomeBoolValue(ExportFragment.this.getActivity(), Constants.EXPORT_RATING_VIEW_LIMIT_REACHED, true);
                            return;
                        }
                        return;
                    }
                    try {
                        ExportFragment exportFragment = ExportFragment.this;
                        exportFragment.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=" + ExportFragment.this.getActivity().getPackageName())));
                    } catch (ActivityNotFoundException unused) {
                        ExportFragment exportFragment2 = ExportFragment.this;
                        exportFragment2.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=" + ExportFragment.this.getActivity().getPackageName())));
                    }
                    if (!SharedPreferencesManager.HasKey(ExportFragment.this.getActivity(), Constants.NEW_EVENT_CALLED)) {
                        Bundle bundle4 = new Bundle();
                        bundle4.putString("User_Experience", "Export_Fragment-MarketOpen");
                        FireBaseHelper.getInstance().LogEvent("Rate_Analytics", bundle4);
                    } else {
                        Bundle bundle5 = new Bundle();
                        bundle5.putString("User_Experience", "Export_Fragment_S_T-MarketOpen");
                        FireBaseHelper.getInstance().LogEvent("Rate_Analytics", bundle5);
                    }
                    textView4.setText(ExportFragment.this.getString(R.string.thanks_text));
                    button5.setText(ExportFragment.this.getString(R.string.close));
                    button6.setEnabled(false);
                } else if (button5.getText().equals(ExportFragment.this.getString(R.string.close))) {
                    if (ExportFragment.this.dialog.isShowing()) {
                        ExportFragment.this.dialog.dismiss();
                        if (!Constants.REMOVE_ADS && AdUtil.getInstance().isSaveInterstitialAvailable() && Constants.SAVE_INTERSTITIAL && MainActivity.isActivityVisible()) {
                            AdUtil.getInstance().ShowSaveInterstitial();
                        }
                    }
                    if (!ExportFragment.this.enjoyPencilPhotoSketch_2) {
                        if (ExportFragment.this.feedback_2) {
                            AppRater.initialize(ExportFragment.this.getActivity()).ResetIncrementEvent();
                        } else {
                            AppRater.initialize(ExportFragment.this.getActivity()).ResetIncrementEvent();
                        }
                    }
                }
            }
        });
        final Button button7 = button2;
        final TextView textView5 = textView2;
        final TextView textView6 = textView;
        final Button button8 = button;
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (button7.getText().equals(ExportFragment.this.closeButtonText)) {
                    textView5.setText(ExportFragment.this.getString(R.string.app_name));
                    textView6.setText(ExportFragment.this.getString(R.string.feedback_text));
                    button8.setText(ExportFragment.this.getString(R.string.ok_sure));
                    button7.setText(ExportFragment.this.getString(R.string.no_thanks));
                    ExportFragment.this.feedback_2 = true;
                } else if (button7.getText().equals(ExportFragment.this.closeButtonText2)) {
                    if (ExportFragment.this.dialog.isShowing()) {
                        ExportFragment.this.dialog.dismiss();
                        if (!Constants.REMOVE_ADS && AdUtil.getInstance().isSaveInterstitialAvailable() && Constants.SAVE_INTERSTITIAL && MainActivity.isActivityVisible()) {
                            AdUtil.getInstance().ShowSaveInterstitial();
                        }
                    }
                    if (ExportFragment.this.enjoyPencilPhotoSketch_2) {
                        SharedPreferencesManager.setSomeBoolValue(ExportFragment.this.getActivity(), Constants.APP_ENJOYED, true);
                    } else if (ExportFragment.this.feedback_2) {
                        AppRater.initialize(ExportFragment.this.getActivity()).ResetIncrementEvent();
                    }
                    AppRater.initialize(ExportFragment.this.getActivity()).ResetIncrementEvent();
                    if (SharedPreferencesManager.HasKey(ExportFragment.this.getActivity(), Constants.NEW_EVENT_CALLED)) {
                        if (!SharedPreferencesManager.HasKey(ExportFragment.this.getActivity(), Constants.APP_RATED) && !SharedPreferencesManager.getSomeBoolValue(ExportFragment.this.getActivity(), Constants.APP_RATED)) {
                            Bundle bundle = new Bundle();
                            bundle.putString("not_rate", "Nothing done");
                            FireBaseHelper.getInstance().LogEvent("Not_Rate", bundle);
                        }
                        SharedPreferencesManager.setSomeBoolValue(ExportFragment.this.getActivity(), Constants.EXPORT_RATING_VIEW_LIMIT_REACHED, true);
                    }
                }
            }
        });
        this.dialog.show();
        Constants.SHOW_DIALOG_RATE_VIEW = false;
        Bundle bundle2 = new Bundle();
        bundle2.putString("User_Experience", "Export_Fragment-RateView_Show");
        FireBaseHelper.getInstance().LogEvent("Rate_Analytics", bundle2);
    }

    public void onLowMemory() {
        super.onLowMemory();
        Log.d("LowMemory", "Low");
    }

    public void initializeRateDialogTexts() {
        if (SharedPreferencesManager.HasKey(getActivity(), Constants.EVENT_DIALOG_TITLE)) {
            this.dialog_title = SharedPreferencesManager.getSomeStringValue(getActivity(), Constants.EVENT_DIALOG_TITLE);
        }
        if (SharedPreferencesManager.HasKey(getActivity(), Constants.EVENT_RATE_TITLE)) {
            this.msg_title = SharedPreferencesManager.getSomeStringValue(getActivity(), Constants.EVENT_RATE_TITLE);
        }
        if (SharedPreferencesManager.HasKey(getActivity(), Constants.EVENT_RATE_RATING_TEXT)) {
            this.msg_text = SharedPreferencesManager.getSomeStringValue(getActivity(), Constants.EVENT_RATE_RATING_TEXT);
        }
    }

    public void initRateViewTexts() {
        final Handler handler2 = new Handler();
        handler2.postDelayed(new Runnable() {
            public void run() {
                if (ExportFragment.this.getActivity() != null) {
                    ExportFragment.this.initializeRateDialogTexts();
                    handler2.removeCallbacks(this);
                    return;
                }
                handler2.postDelayed(this, 500);
            }
        }, 500);
    }

    public int dpToPx(int i, boolean z) {
        if (z) {
            return 0;
        }
        return Math.round(((float) i) * this.density);
    }
}