package ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.exifinterface.media.ExifInterface;
import androidx.fragment.app.Fragment;
import androidx.media2.exoplayer.external.trackselection.AdaptiveTrackSelection;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.facebook.ads.AdIconView;

import com.facebook.ads.MediaView;

import com.facebook.ads.NativeBannerAd;
import com.facebook.internal.AnalyticsEvents;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAdView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.teamvinay.sketch.App;

import com.teamvinay.sketch.Filter;
import com.teamvinay.sketch.FilterHolder;
import com.teamvinay.sketch.FilterType;
import com.teamvinay.sketch.MainActivity;
import com.mopub.common.logging.MoPubLog;
import com.mopub.mobileads.MoPubErrorCode;
import com.mopub.mobileads.MoPubView;
import com.mopub.nativeads.MoPubNative;
import com.mopub.nativeads.MoPubStaticNativeAdRenderer;
import com.mopub.nativeads.NativeAd;
import com.mopub.nativeads.NativeErrorCode;
import com.mopub.nativeads.ViewBinder;
import com.teamvinay.sketch.R;
import com.warkiz.widget.IndicatorSeekBar;
import com.warkiz.widget.OnSeekChangeListener;
import com.warkiz.widget.SeekParams;
import com.willy.ratingbar.BaseRatingBar;
import com.willy.ratingbar.ScaleRatingBar;
import helper.Constants;
import helper.CustomViewPager;
import helper.EditorButtonListener;
import helper.EditorOptionsAdapter;
import helper.EffectButtonFragment;
import helper.EffectButtonListener;
import helper.EnhanceFilters;
import helper.ExportDetails;
import helper.FilterButtonFragment;
import helper.FilterButtonListener;
import helper.FilterNames;
import helper.ViewPagerAdapter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import listeners.ReplaceFragmentListener;
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
import util.EditingFragmentDetails;
import util.EditorFilter;
import util.Effect;
import util.FireBaseHelper;
import util.ImageFilePath;
import util.SharedPreferencesManager;
import util.Utils;

/* renamed from: ui.EditingFragment */
public class EditingFragment extends Fragment implements WaitScreenGoneListener, View.OnClickListener, OnSeekChangeListener, EffectButtonListener, EditorButtonListener, FilterButtonListener, RadioGroup.OnCheckedChangeListener, MoPubView.BannerAdListener {
    private static final String ADMOB_NATIVE_BANNER_ADUNIT_ID = "ca-app-pub-4195495625122728/2301275229";
    private static final String MOPUB_NATIVE_BANNER_ADUNIT_ID = "d86b5a12d15944b9b7202fd0c80c9167";
    private boolean adAvailable = false;
    AdLoader adLoader;
    private RelativeLayout adView;
    /* access modifiers changed from: private */
    public int adjustValue = -1;
    /* access modifiers changed from: private */
    public UnifiedNativeAd admob_nativeAd;
    private boolean all_filter = false;
    Button artButton;
    TextView art_text;
    /* access modifiers changed from: private */
    public boolean bannerLoaded = false;
    /* access modifiers changed from: private */
    public Bitmap bitmap;
    /* access modifiers changed from: private */
    public BitmapCache bitmapCache;
    /* access modifiers changed from: private */
    public Bitmap blendBitmap = null;
    /* access modifiers changed from: private */
    public RelativeLayout bottomLayoutContainer;
    private boolean bottomViewClose = false;
    AdLoader.Builder builder;
    private Button check;
    private Button check_two;
    /* access modifiers changed from: private */
    public final CharSequence[] colorchalk = {"Thin Edge", "Medium Edge", "Thick Edge"};
    private float density = 0.0f;
    EditingFragmentDetails details = null;
    AppCompatRadioButton editorButton;
    private List<EditorFilter> editorFilters = new ArrayList();
    boolean editor_button_activated = false;
    /* access modifiers changed from: private */
    public List<EnhanceFilters> editor_items = new ArrayList();
    TextView editor_text;
    /* access modifiers changed from: private */
    public Effect effect;
    /* access modifiers changed from: private */
    public Bitmap effectBitmap = null;
    AppCompatRadioButton effectButton;
    private EffectButtonFragment effectButtonFragment;
    Dialog effectLoadingDialog;
    boolean effect_button_activated = false;
    /* access modifiers changed from: private */
    public boolean effect_button_checked = false;
    /* access modifiers changed from: private */
    public String effect_name = null;
    /* access modifiers changed from: private */
    public boolean effect_selected = false;
    TextView effect_text;
    /* access modifiers changed from: private */
    public String enhance_filter_name = null;
    /* access modifiers changed from: private */
    public int enhance_position = -1;
    /* access modifiers changed from: private */
    public boolean enhance_selected = false;
    private boolean enjoyPencilPhotoSketch = false;
    private boolean enjoyPencilPhotoSketch_2 = false;
    private boolean fb_load_called = false;
    /* access modifiers changed from: private */
    public boolean feedback = false;
    private boolean feedback_2 = false;
    /* access modifiers changed from: private */
    public Uri fileUri;
    AppCompatRadioButton filterButton;
    private FilterButtonFragment filterButtonFragment;
    /* access modifiers changed from: private */
    public String filterConstant = null;
    FilterHolder filterHolder;
    private float filter_Value = 0.0f;
    boolean filter_button_activated = false;
    /* access modifiers changed from: private */
    public TextView filter_name;
    /* access modifiers changed from: private */
    public int filter_position = -1;
    /* access modifiers changed from: private */
    public boolean filter_selected = false;
    TextView filter_text;
    FilterType filter_type = null;
    /* access modifiers changed from: private */
    public TextView filter_value;
    Mat filtered;
    Mat finalmat;
    /* access modifiers changed from: private */
    public boolean freshStart = true;
    /* access modifiers changed from: private */
    public Bitmap gpuBitmap;
    /* access modifiers changed from: private */
    public GPUImage gpuImage;
    GPUImageFilter gpuImageFilter;
    Handler handler;
    /* access modifiers changed from: private */
    public HashMap<String, Boolean> hashMap;
    private RelativeLayout image;
    /* access modifiers changed from: private */
    public GPUImageFilterGroup imageFilterGroup;
    /* access modifiers changed from: private */
    public RelativeLayout imageLayout;
    /* access modifiers changed from: private */
    public ImageView imageView;
    IndicatorSeekBar indicatorSeekBar;
    LayoutInflater layout_inflater = null;
    Dialog loadingDialog;
    TextView loadingText;
    /* access modifiers changed from: private */
    public EditorOptionsAdapter mAdapter;
    Context mContext;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.LayoutManager menuLayoutManager;
    /* access modifiers changed from: private */
    public MoPubNative moPubNative;
    private MoPubNative.MoPubNativeNetworkListener moPubNativeNetworkListener;
    /* access modifiers changed from: private */
    public MoPubView moPubView;
    NativeAd mopubNativeAd;
    private RelativeLayout nativeAdContainer;

    private NativeBannerAd nativeBannerAd;
    private boolean native_ad_loaded = false;
    View native_rateView;
    private NativeBannerAd nativebanner;
    private Button no_rate;
    /* access modifiers changed from: private */
    public Bitmap optionBitmap;
    /* access modifiers changed from: private */
    public RelativeLayout optionLayout;
    private TextView optionText;
    Mat original;
    private boolean originalBitmap = false;
    /* access modifiers changed from: private */
    public CustomViewPager pager;
    /* access modifiers changed from: private */
    public ViewPagerAdapter pagerAdapter;
    TextView percentage;
    private RelativeLayout radio_buttonLayout;
    TextView rateDialogText;
    private Button rateIt;
    private boolean rateViewShowing = false;
    RecyclerView recyclerView;
    /* access modifiers changed from: private */
    public boolean rerun = true;
    /* access modifiers changed from: private */
    public Bitmap resize = null;
    FloatingActionButton restart;
    boolean resume_temp = false;
    private Button save;
    private RelativeLayout screen;
    /* access modifiers changed from: private */
    public RelativeLayout seekBar_view;
    IndicatorSeekBar seekbar;
    List<String> sequence = new ArrayList();
    private RelativeLayout tablayout;
    private boolean undo_button_activate = false;
    View view;

    public void ApplyingEffectsMsg(String str) {
    }

    public void onBannerClicked(MoPubView moPubView2) {
    }

    public void onBannerCollapsed(MoPubView moPubView2) {
    }

    public void onBannerExpanded(MoPubView moPubView2) {
    }

    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
    }

    public void onStartTrackingTouch(IndicatorSeekBar indicatorSeekBar2) {
    }

    public float range(float f, float f2, float f3) {
        return (((f3 - f2) * f) / 100.0f) + f2;
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        showMessage("FragmentEditing", "Attach");
        this.mContext = context;
        this.density = this.mContext.getResources().getDisplayMetrics().density;
    }

    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
       /* if (!Constants.REMOVE_ADS && !Constants.ED_NATIVE_FULL_SCREEN_SHOWN && AdUtil.getInstance().isFBEditingNativeLoaded() && Constants.START_UP_AD) {
            Log.d("editingnative", "loaded");
            Constants.START_UP_AD = false;
            ((MainActivity) getActivity()).showFloatingFBNativeAd(AdUtil.getInstance().getEditing_fb_native(), true);
            this.adAvailable = true;
        }*/
    }

    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        this.view = layoutInflater.inflate(R.layout.editing_activity, viewGroup, false);
        this.screen = (RelativeLayout) this.view.findViewById(R.id.editing_screen);
        if (Constants.CUSTOM_EDITING_SCREEN_ENABLED && Constants.CUSTOM_EDITING_SCREEN_BG_COLOR != null) {
            this.screen.setBackgroundColor(Color.parseColor(Constants.CUSTOM_EDITING_SCREEN_BG_COLOR));
        }
        this.radio_buttonLayout = (RelativeLayout) this.view.findViewById(R.id.radio_button_layout);
        this.hashMap = new HashMap<>();
        this.image = (RelativeLayout) this.view.findViewById(R.id.export);
        this.bottomLayoutContainer = (RelativeLayout) this.view.findViewById(C3674R.C3676id.bottom_view_container);
        this.imageLayout = (RelativeLayout) this.view.findViewById(C3674R.C3676id.image_layout);
        this.layout_inflater = layoutInflater;
        this.moPubView = (MoPubView) this.view.findViewById(C3674R.C3676id.adview);
        this.nativeAdContainer = (RelativeLayout) this.view.findViewById(C3674R.C3676id.native_ad_container);
        this.moPubView = (MoPubView) this.view.findViewById(C3674R.C3676id.adview);
        this.effectButton = (AppCompatRadioButton) this.view.findViewById(R.id.effect_button);
        this.filterButton = (AppCompatRadioButton) this.view.findViewById(C3674R.C3676id.filter_button);
        this.editorButton = (AppCompatRadioButton) this.view.findViewById(C3674R.C3676id.editor_button);
        this.artButton = (Button) this.view.findViewById(C3674R.C3676id.art_button);
        this.effect_text = (TextView) this.view.findViewById(R.id.effect_text);
        this.filter_text = (TextView) this.view.findViewById(C3674R.C3676id.filter_text);
        this.editor_text = (TextView) this.view.findViewById(C3674R.C3676id.editor_text);
        if (!(!Constants.CUSTOM_EDITING_SCREEN_ENABLED || Constants.RADIO_BUTTON_LAYOUT_BG_COLOR == null || Constants.RADIO_BUTTON_TINT_COLOR == null || Constants.RADIO_BUTTON_TEXT_COLOR == null)) {
            this.radio_buttonLayout.setBackgroundColor(Color.parseColor(Constants.RADIO_BUTTON_LAYOUT_BG_COLOR));
            if (Build.VERSION.SDK_INT >= 21) {
                ColorStateList colorStateList = new ColorStateList(new int[][]{new int[]{-16842910}, new int[]{16842910}}, new int[]{Color.parseColor(Constants.RADIO_BUTTON_TINT_COLOR), Color.parseColor(Constants.RADIO_BUTTON_TINT_COLOR)});
                this.effectButton.setButtonTintList(colorStateList);
                this.filterButton.setButtonTintList(colorStateList);
                this.editorButton.setButtonTintList(colorStateList);
                this.effectButton.invalidate();
                this.effectButton.invalidate();
                this.filterButton.invalidate();
                this.editorButton.invalidate();
            } else {
                this.effectButton.setSupportButtonTintList(new ColorStateList(new int[][]{new int[]{-16842912}, new int[]{16842912}}, new int[]{Color.parseColor(Constants.RADIO_BUTTON_TINT_COLOR), Color.parseColor(Constants.RADIO_BUTTON_TINT_COLOR)}));
            }
            this.effect_text.setTextColor(Color.parseColor(Constants.RADIO_BUTTON_TEXT_COLOR));
            this.filter_text.setTextColor(Color.parseColor(Constants.RADIO_BUTTON_TEXT_COLOR));
            this.editor_text.setTextColor(Color.parseColor(Constants.RADIO_BUTTON_TEXT_COLOR));
        }
        if (Constants.CUSTOM_EDITING_SCREEN_ENABLED && Constants.SAVE_BUTTON_COLOR != null) {
            this.image.setBackgroundColor(Color.parseColor(Constants.SAVE_BUTTON_COLOR));
        }
        this.restart = (FloatingActionButton) this.view.findViewById(R.id.undo_button);
        if (!(!Constants.CUSTOM_EDITING_SCREEN_ENABLED || Constants.RESTART_DESELECT_TINT_COLOR == null || Constants.RESTART_SELECT_TINT_COLOR == null)) {
            this.restart.setColorFilter(Color.parseColor(Constants.RESTART_DESELECT_TINT_COLOR), PorterDuff.Mode.SRC_IN);
        }
        activateButton(this.effectButton);
        this.effect_button_activated = true;
        this.moPubView.setAdUnitId("191821deb8ab4ef69bb9682048de3821");
        ((MainActivity) getActivity()).setWaitScreenGoneListener(this);
        this.bottomViewClose = false;
        this.native_ad_loaded = false;
        this.bannerLoaded = false;
        this.restart.setOnClickListener(this);
        this.image.setOnClickListener(this);
        this.handler = new Handler();
        this.indicatorSeekBar = (IndicatorSeekBar) this.view.findViewById(R.id.seek);
        this.seekbar = (IndicatorSeekBar) this.view.findViewById(R.id.seek_second);
        this.recyclerView = (RecyclerView) this.view.findViewById(R.id.my_recycler_view);
        this.effectButton.setOnClickListener(this);
        this.editorButton.setOnClickListener(this);
        this.filterButton.setOnClickListener(this);
        this.artButton.setOnClickListener(this);
        this.indicatorSeekBar.setOnSeekChangeListener(this);
        this.seekbar.setOnSeekChangeListener(this);
        if (this.editor_items.isEmpty()) {
            this.editor_items.add(new EnhanceFilters(R.drawable.baseline_brightness_6_24, FilterNames.BRIGHT, FilterType.BRIGHTNESS));
            this.editor_items.add(new EnhanceFilters(R.drawable.baseline_details_24, FilterNames.SHARP, FilterType.SHARPNESS));
            this.editor_items.add(new EnhanceFilters(R.drawable.ic_saturation_icon, FilterNames.SATURATE, FilterType.SATURATION));
            this.editor_items.add(new EnhanceFilters(R.drawable.contrast_box, FilterNames.CONTRAST, FilterType.CONTRAST));
            this.editor_items.add(new EnhanceFilters(R.drawable.ic_hue_icon, FilterNames.HUE, FilterType.HUE));
            this.editor_items.add(new EnhanceFilters(R.drawable.ic_blur_icon, FilterNames.BLUR, FilterType.BLUR));
            this.editor_items.add(new EnhanceFilters(R.drawable.ic_pixel_icon, FilterNames.PIXEL, FilterType.PIXEL));
        }
        this.mLayoutManager = new LinearLayoutManager(getActivity(), 0, false);
        this.recyclerView.setLayoutManager(this.mLayoutManager);
        this.mAdapter.setEditorButtonListener(this);
        this.recyclerView.setAdapter(this.mAdapter);
        this.original = new Mat();
        this.filtered = new Mat();
        this.finalmat = new Mat();
        this.imageView = (ImageView) this.view.findViewById(R.id.gpuimage);
        this.imageView.setOnClickListener(this);
        this.imageFilterGroup = new GPUImageFilterGroup();
        this.filter_name = (TextView) this.view.findViewById(R.id.filterName);
        this.pager = (CustomViewPager) this.view.findViewById(R.id.pager);
        this.pagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
        this.pager.measure(-1, -2);
        this.pager.setOffscreenPageLimit(2);
        this.optionLayout = (RelativeLayout) this.view.findViewById(R.id.options);
        this.seekBar_view = (RelativeLayout) this.view.findViewById(R.id.seekbar_view);
        this.bitmapCache = new BitmapCache(getActivity());
        if (this.details == null) {
            this.fileUri = Uri.parse(getArguments().getString("imageUri"));
            if (this.fileUri != null) {
                Constants.FILE_URI = this.fileUri;
            }
            this.bitmap = BitmapFactory.decodeFile(ImageFilePath.getPath(getActivity(), this.fileUri));
            this.imageView.setImageBitmap(this.bitmap);
            if (this.bitmap != null) {
                this.effectBitmap = Bitmap.createBitmap(this.bitmap.getWidth(), this.bitmap.getHeight(), Bitmap.Config.RGB_565);
                Constants.ORIGINAL_BITMAP_WIDTH = this.bitmap.getWidth();
                Constants.ORIGINAL_BITMAP_HEIGHT = this.bitmap.getHeight();
            }
            this.effect = new Effect(getActivity(), this.bitmapCache);
            this.effectButtonFragment = new EffectButtonFragment();
            this.effectButtonFragment.setEffectListener(this);
            this.pagerAdapter.AddFragment(this.effectButtonFragment, "");
            this.pager.setAdapter(this.pagerAdapter);
            this.pager.setPagingEnabled(false);
        }
        this.gpuImage = new GPUImage(getActivity());
        this.pager.setCurrentItem(0);
        if (Constants.SHOW_BOTTOM_RATE_VIEW) {
            final Handler handler2 = new Handler();
            handler2.postDelayed(new Runnable() {
                public void run() {
                    if (EditingFragment.this.getActivity() != null) {
                        EditingFragment.this.initializeBottomRateView();
                        handler2.removeCallbacks(this);
                        return;
                    }
                    handler2.postDelayed(this, 50);
                }
            }, 50);
        } else if (Constants.REMOVE_ADS || !Constants.BOTTOM_NATIVE_AD_SHOW) {
            initializeBottomView();
        } else if (this.admob_nativeAd != null) {
            infilateAdmobNativeAd(this.admob_nativeAd);
        } else if (this.mopubNativeAd != null) {
            inflateMopubNativeAd(this.mopubNativeAd);
        } else if (this.bannerLoaded) {
            this.moPubView.loadAd();
        } else {
            initializeBottomView();
        }
        if (this.details != null) {
            Log.d("FragmentEditing", "StartUpTask");
            initializeContent();
        } else if (!this.adAvailable) {
            final Handler handler3 = new Handler();
            handler3.postDelayed(new Runnable() {
                public void run() {
                    if (EditingFragment.this.bitmap != null) {
                        handler3.removeCallbacks(this);
                        new StartUpLoadingTask(EditingFragment.this.getActivity()).execute(new Void[0]);
                        Log.d("FragmentEditing Con", "StartUpTask");
                    } else {
                        handler3.postDelayed(this, 50);
                    }
                    Log.d("FragmentEditing Con", "StartUpTask");
                }
            }, 50);
        }
        Log.d("FragmentEditing", "CreateView");
        return this.view;
    }

    public void initializeBottomRateView() {
        this.adView = (RelativeLayout) LayoutInflater.from(getActivity()).inflate(R.layout.rating_view_layout, (ViewGroup) null, false);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.bottomLayoutContainer.getLayoutParams();
        layoutParams.height = dpToPx(90, false);
        layoutParams.width = -1;
        this.bottomLayoutContainer.setLayoutParams(layoutParams);
        this.bottomLayoutContainer.addView(this.adView);
        final Button button = (Button) this.adView.findViewById(R.id.yes_button);
        final Button button2 = (Button) this.adView.findViewById(R.id.no_button);
        final TextView textView = (TextView) this.adView.findViewById(R.id.rate_dialog_text);
        LinearLayout linearLayout = (LinearLayout) this.adView.findViewById(R.id.layout);
        ScaleRatingBar scaleRatingBar = (ScaleRatingBar) this.adView.findViewById(R.id.simpleRatingBar);
        final LinearLayout linearLayout2 = linearLayout;
        final TextView textView2 = textView;
        final Button button3 = button;
        final Button button4 = button2;
        scaleRatingBar.setOnRatingChangeListener(new BaseRatingBar.OnRatingChangeListener() {
            public void onRatingChange(BaseRatingBar baseRatingBar, float f, boolean z) {
                if (f >= 4.0f) {
                    try {
                        EditingFragment editingFragment = EditingFragment.this;
                        editingFragment.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=" + EditingFragment.this.getActivity().getPackageName())));
                    } catch (ActivityNotFoundException unused) {
                        EditingFragment editingFragment2 = EditingFragment.this;
                        editingFragment2.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=" + EditingFragment.this.getActivity().getPackageName())));
                    }
                    baseRatingBar.setVisibility(View.GONE);
                    linearLayout2.setVisibility(View.VISIBLE);
                    textView2.setText(EditingFragment.this.getString(R.string.thanks_text));
                    button3.setText(EditingFragment.this.getString(R.string.close));
                    button4.setEnabled(false);
                    Bundle bundle = new Bundle();
                    bundle.putString("User_Experience", "Native_Rate_4_5");
                    FireBaseHelper.getInstance().LogEvent("Rate_Analytics", bundle);
                    return;
                }
                baseRatingBar.setVisibility(View.GONE);
                linearLayout2.setVisibility(View.VISIBLE);
                textView2.setText(EditingFragment.this.getString(R.string.thanks_text));
                button3.setText(EditingFragment.this.getString(R.string.close));
                button4.setEnabled(false);
                Bundle bundle2 = new Bundle();
                bundle2.putString("User_Experience", "Native_Rate_3");
                FireBaseHelper.getInstance().LogEvent("Rate_Analytics", bundle2);
            }
        });
        if (SharedPreferencesManager.HasKey(getActivity(), Constants.APP_ENJOYED)) {
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
                    textView3.setText(EditingFragment.this.getString(R.string.rate_text));
                    if (Constants.RATING_BAR) {
                        scaleRatingBar2.setVisibility(View.VISIBLE);
                        linearLayout3.setVisibility(View.GONE);
                    }
                    button5.setText(EditingFragment.this.getString(R.string.ok_sure));
                    button6.setText(EditingFragment.this.getString(R.string.no_thanks));
                    Bundle bundle = new Bundle();
                    bundle.putString("User_Experience", "Native-RateView_Enjoy");
                    FireBaseHelper.getInstance().LogEvent("Rate_Analytics", bundle);
                } else if (button5.getText().equals(EditingFragment.this.getString(R.string.ok_sure))) {
                    if (EditingFragment.this.feedback) {
                        Intent intent = new Intent("android.intent.action.SEND");
                        intent.putExtra("android.intent.extra.EMAIL", new String[]{"hqgames1080@gmail.com"});
                        intent.putExtra("android.intent.extra.SUBJECT", "Pencil Photo Sketch:FeedBack");
                        intent.setType("message/rfc822");
                        EditingFragment.this.startActivity(Intent.createChooser(intent, "Select Email Sending App :"));
                        textView3.setText(EditingFragment.this.getString(R.string.thanks_text));
                        button5.setText(EditingFragment.this.getString(R.string.close));
                        button6.setEnabled(false);
                    }
                    if (Constants.RATING_BAR) {
                        scaleRatingBar2.setVisibility(View.VISIBLE);
                        linearLayout3.setVisibility(View.GONE);
                        textView3.setText(EditingFragment.this.getString(R.string.rate_dialog_title));
                        return;
                    }
                    try {
                        EditingFragment editingFragment = EditingFragment.this;
                        editingFragment.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=" + EditingFragment.this.getActivity().getPackageName())));
                    } catch (ActivityNotFoundException unused) {
                        EditingFragment editingFragment2 = EditingFragment.this;
                        editingFragment2.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=" + EditingFragment.this.getActivity().getPackageName())));
                    }
                    textView3.setText(EditingFragment.this.getString(R.string.thanks_text));
                    button5.setText(EditingFragment.this.getString(R.string.close));
                    button6.setEnabled(false);
                } else if (button5.getText().equals(EditingFragment.this.getString(R.string.close))) {
                    EditingFragment.this.bottomLayoutContainer.removeAllViews();
                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) EditingFragment.this.bottomLayoutContainer.getLayoutParams();
                    layoutParams.height = EditingFragment.this.dpToPx(0, false);
                    layoutParams.width = -1;
                    EditingFragment.this.bottomLayoutContainer.setLayoutParams(layoutParams);
                }
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (button2.getText().equals(EditingFragment.this.getString(R.string.not))) {
                    textView.setText(EditingFragment.this.getString(R.string.feedback_text));
                    button.setText(EditingFragment.this.getString(R.string.ok_sure));
                    button2.setText(EditingFragment.this.getString(R.string.no_thanks));
                    boolean unused = EditingFragment.this.feedback = true;
                } else if (button2.getText().equals(EditingFragment.this.getString(R.string.no_thanks))) {
                    EditingFragment.this.bottomLayoutContainer.removeAllViews();
                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) EditingFragment.this.bottomLayoutContainer.getLayoutParams();
                    layoutParams.height = EditingFragment.this.dpToPx(0, false);
                    layoutParams.width = -1;
                    EditingFragment.this.bottomLayoutContainer.setLayoutParams(layoutParams);
                }
            }
        });
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
            unifiedNativeAdView.getIconView().setVisibility(View.GONE);
        }
        unifiedNativeAdView.setNativeAd(unifiedNativeAd);
    }

    public void infilateAdmobNativeAd(final UnifiedNativeAd unifiedNativeAd) {
        final Handler handler2 = new Handler();
        handler2.postDelayed(new Runnable() {
            public void run() {
                if (EditingFragment.this.getActivity() != null && !Constants.REMOVE_ADS) {
                    handler2.removeCallbacks(this);
                    View inflate = LayoutInflater.from(EditingFragment.this.getActivity()).inflate(R.layout.admob_native_banner, (ViewGroup) null);
                    EditingFragment.this.populateUnifiedNativeAdView(unifiedNativeAd, (UnifiedNativeAdView) inflate.findViewById(R.id.admob_native));
                    EditingFragment.this.bottomLayoutContainer.addView(inflate);
                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) EditingFragment.this.bottomLayoutContainer.getLayoutParams();
                    layoutParams.height = -2;
                    layoutParams.width = -2;
                    EditingFragment.this.bottomLayoutContainer.setLayoutParams(layoutParams);
                }
            }
        }, 1500);
    }

    public void inflateMopubNativeAd(final NativeAd nativeAd) {
        final Handler handler2 = new Handler();
        handler2.postDelayed(new Runnable() {
            public void run() {
                if (EditingFragment.this.getActivity() != null) {
                    if (!Constants.REMOVE_ADS) {
                        handler2.removeCallbacks(this);
                        //EditingFragment.this.inflateMopubNative(nativeAd);
                    }
                } else if (EditingFragment.this.rerun) {
                    handler2.postDelayed(this, AdaptiveTrackSelection.DEFAULT_MIN_TIME_BETWEEN_BUFFER_REEVALUTATION_MS);
                    boolean unused = EditingFragment.this.rerun = false;
                }
                Log.d("AdCustom", "inflate mopub");
            }
        }, 1500);
    }

    public int dpToPx(int i, boolean z) {
        if (z) {
            return 0;
        }
        return Math.round(((float) i) * this.density);
    }

    private void initializeBottomView() {
        this.builder = new AdLoader.Builder((Context) getActivity(), ADMOB_NATIVE_BANNER_ADUNIT_ID);
        this.builder.forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
            public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
                UnifiedNativeAd unused = EditingFragment.this.admob_nativeAd = unifiedNativeAd;
                EditingFragment.this.infilateAdmobNativeAd(unifiedNativeAd);
            }
        });
        this.adLoader = this.builder.withAdListener(new AdListener() {
            public void onAdFailedToLoad(int i) {
                Log.d("Native", "Banner not Loaded");
                if (Constants.REMOVE_ADS) {
                    return;
                }
                if (Constants.admob_first) {
                    EditingFragment.this.moPubNative.makeRequest();
                } else {
                    EditingFragment.this.moPubView.loadAd();
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
                EditingFragment.this.mopubNativeAd = nativeAd;
                EditingFragment.this.inflateMopubNativeAd(nativeAd);
            }

            public void onNativeFail(NativeErrorCode nativeErrorCode) {
                Log.d(MoPubLog.LOGTAG, "Banner Native ad has not loaded.");
                if (Constants.REMOVE_ADS) {
                    return;
                }
                if (!Constants.admob_first) {
                    EditingFragment.this.adLoader.loadAd(new AdRequest.Builder().build());
                } else {
                    EditingFragment.this.moPubView.loadAd();
                }
            }
        };
        this.moPubNative = new MoPubNative(getActivity(), MOPUB_NATIVE_BANNER_ADUNIT_ID, this.moPubNativeNetworkListener);
        this.moPubNative.registerAdRenderer(moPubStaticNativeAdRenderer);
        if (Constants.REMOVE_ADS || !Constants.BOTTOM_NATIVE_AD_SHOW) {
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

   /* private void inflateAd(NativeBannerAd nativeBannerAd2) {
        nativeBannerAd2.unregisterView();
        int i = 0;
        this.adView = (RelativeLayout) LayoutInflater.from(getActivity()).inflate(C3674R.C3678layout.fb_native_banner, this.nativeAdLayout, false);
        this.nativeAdLayout.addView(this.adView);
        RelativeLayout relativeLayout = (RelativeLayout) this.adView.findViewById(C3674R.C3676id.ad_choices_container);
        AdOptionsView adOptionsView = new AdOptionsView(getActivity(), nativeBannerAd2, this.nativeAdLayout);
        relativeLayout.removeAllViews();
        relativeLayout.addView(adOptionsView, 0);
        TextView textView = (TextView) this.adView.findViewById(C3674R.C3676id.native_ad_title);
        TextView textView2 = (TextView) this.adView.findViewById(C3674R.C3676id.native_ad_social_context);
        TextView textView3 = (TextView) this.adView.findViewById(C3674R.C3676id.native_ad_sponsored_label);
        AdIconView adIconView = (AdIconView) this.adView.findViewById(C3674R.C3676id.native_icon_view);
        Button button = (Button) this.adView.findViewById(C3674R.C3676id.native_ad_call_to_action);
        if (!nativeBannerAd2.hasCallToAction()) {
            i = 4;
        }
        button.setVisibility(i);
        textView.setText(nativeBannerAd2.getAdvertiserName());
        textView2.setText(nativeBannerAd2.getAdSocialContext());
        ArrayList arrayList = new ArrayList();
        arrayList.add(textView);
        arrayList.add(button);
        nativeBannerAd2.registerViewForInteraction((View) this.adView, (MediaView) adIconView, (List<View>) arrayList);
    }

    public void inflateMopubNative(NativeAd nativeAd) {
        this.adView = (RelativeLayout) LayoutInflater.from(getActivity()).inflate(C3674R.C3678layout.native_ad_banner, this.nativeAdContainer, false);
        nativeAd.renderAdView(this.adView);
        nativeAd.prepare(this.adView);
        this.bottomLayoutContainer.addView(this.adView);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.bottomLayoutContainer.getLayoutParams();
        layoutParams.height = -2;
        layoutParams.width = -2;
        this.bottomLayoutContainer.setLayoutParams(layoutParams);
    }*/

    public void initializeContent() {
        if (((MainActivity) getActivity()) == null || ((MainActivity) getActivity()).getEditingFragmentDetails() == null) {
            showMessage("FragmentEditing", "fresh start");
            this.details = new EditingFragmentDetails();
            this.filterHolder = new FilterHolder();
            this.filterButtonFragment = new FilterButtonFragment();
            this.pagerAdapter.AddFragment(this.filterButtonFragment, "");
            this.effectButtonFragment.setEffectListener(this);
            this.filterButtonFragment.setEffectListener(this);
            if (this.resize != null) {
                this.filterButtonFragment.setBitmap(this.resize, getActivity());
            } else if (this.bitmap != null) {
                this.resize = resize(this.bitmap, 96, 96);
                this.filterButtonFragment.setBitmap(this.resize, getActivity());
            }
            this.pagerAdapter.notifyDataSetChanged();
            return;
        }
        this.details = ((MainActivity) getActivity()).getEditingFragmentDetails();
        this.filterHolder = this.details.getFilterHolder();
        if (this.pagerAdapter == null || this.pagerAdapter.getCount() != 0) {
            if (this.pagerAdapter.getLstfragment() != null && !this.pagerAdapter.getLstfragment().isEmpty()) {
                this.pagerAdapter.getLstfragment().clear();
            }
            if (this.effectButtonFragment == null) {
                this.effectButtonFragment = new EffectButtonFragment();
                this.pagerAdapter.AddFragment(this.effectButtonFragment, "");
            } else if (!this.effectButtonFragment.isAdded()) {
                this.effectButtonFragment = new EffectButtonFragment();
                this.pagerAdapter.AddFragment(this.effectButtonFragment, "");
            } else {
                this.pagerAdapter.AddFragment(this.effectButtonFragment, "");
            }
            if (this.filterButtonFragment == null) {
                this.filterButtonFragment = new FilterButtonFragment();
                this.pagerAdapter.AddFragment(this.filterButtonFragment, "");
            } else if (!this.filterButtonFragment.isAdded()) {
                this.filterButtonFragment = new FilterButtonFragment();
                this.pagerAdapter.AddFragment(this.filterButtonFragment, "");
            } else {
                this.pagerAdapter.AddFragment(this.filterButtonFragment, "");
            }
        } else {
            if (this.pagerAdapter.getLstfragment() != null && !this.pagerAdapter.getLstfragment().isEmpty()) {
                this.pagerAdapter.getLstfragment().clear();
            }
            if (this.effectButtonFragment == null) {
                this.effectButtonFragment = new EffectButtonFragment();
                this.pagerAdapter.AddFragment(this.effectButtonFragment, "");
            } else if (!this.effectButtonFragment.isAdded()) {
                this.effectButtonFragment = new EffectButtonFragment();
                this.pagerAdapter.AddFragment(this.effectButtonFragment, "");
            } else {
                this.pagerAdapter.AddFragment(this.effectButtonFragment, "");
            }
            if (this.filterButtonFragment == null) {
                this.filterButtonFragment = new FilterButtonFragment();
                this.pagerAdapter.AddFragment(this.filterButtonFragment, "");
            } else if (!this.filterButtonFragment.isAdded()) {
                this.filterButtonFragment = new FilterButtonFragment();
                this.pagerAdapter.AddFragment(this.filterButtonFragment, "");
            } else {
                this.pagerAdapter.AddFragment(this.filterButtonFragment, "");
            }
        }
        this.effectButtonFragment.setEffectListener(this);
        this.filterButtonFragment.setEffectListener(this);
        if (this.pager != null && this.pager.getAdapter() == null) {
            this.pager.setAdapter(this.pagerAdapter);
            Log.d("pageradapter", "adapter set");
        }
        this.pager.setPagingEnabled(false);
        this.pager.setVisibility(View.GONE);
        if (this.details.getEffect() != null) {
            this.effect = this.details.getEffect();
        }
        new FragmentStateResume(this.bitmap, this.effect, this.details, this.gpuImage, (Dialog) null).execute(new Void[0]);
        showMessage("FragmentEditing", "resume");
    }

    public void onStart() {
        super.onStart();
        showMessage("FragmentEditing", "Start");
    }

    public void onResume() {
        super.onResume();
        showMessage("FragmentEditing", "Resume");
        if (this.resume_temp) {
            this.resume_temp = false;
        }
    }

    public void onPause() {
        super.onPause();
        showMessage("FragmentEditing", "Pause");
        if (this.effectLoadingDialog != null) {
            this.effectLoadingDialog.dismiss();
            this.effectLoadingDialog = null;
        }
    }

    public void onStop() {
        super.onStop();
        showMessage("FragmentEditing", "Stop");
        this.resume_temp = true;
    }

    public void onDestroyView() {
        if (this.moPubView != null) {
            this.moPubView.destroy();
        }
        if (this.moPubNative != null) {
            this.moPubNative.destroy();
        }
        if (this.mopubNativeAd != null) {
            this.mopubNativeAd.destroy();
        }
        if (this.nativebanner != null) {
            this.nativebanner.destroy();
        }
        if (this.nativeBannerAd != null) {
            this.nativeBannerAd.destroy();
        }
        if (this.layout_inflater != null) {
            this.layout_inflater = null;
        }
        if (this.admob_nativeAd != null) {
            this.admob_nativeAd.destroy();
            this.admob_nativeAd = null;
        }
        super.onDestroyView();
        showMessage("FragmentEditing", "DestroyView");
    }

    public void onDestroy() {
        super.onDestroy();
        ((MainActivity) getActivity()).Clear();
        if (this.moPubView != null) {
            this.moPubView.destroy();
        }
        if (this.moPubNative != null) {
            this.moPubNative.destroy();
        }
        if (this.mopubNativeAd != null) {
            this.mopubNativeAd.destroy();
        }
        if (this.nativebanner != null) {
            this.nativebanner.destroy();
        }
        if (this.nativeBannerAd != null) {
            this.nativeBannerAd.destroy();
        }
        unbindDrawables(this.recyclerView);
        unbindDrawables(this.image);
        unbindDrawables(this.imageView);
        if (this.effectLoadingDialog != null) {
            this.effectLoadingDialog.dismiss();
            this.effectLoadingDialog = null;
        }
        if (this.gpuBitmap != null) {
            this.gpuBitmap = null;
        }
        if (this.bitmap != null) {
            this.bitmap = null;
        }
        if (this.effectBitmap != null) {
            this.effectBitmap = null;
        }
        if (this.optionBitmap != null) {
            this.optionBitmap = null;
        }
        if (this.gpuImage != null) {
            this.gpuImage = null;
        }
        if (this.blendBitmap != null) {
            this.blendBitmap = null;
        }
        if (this.effect != null) {
            this.effect.clear();
        }
        if (this.bitmapCache != null) {
            this.bitmapCache.clearOriginal();
        }
        if (this.original != null) {
            this.original.release();
            this.original = null;
        }
        if (this.filtered != null) {
            this.filtered.release();
            this.filtered = null;
        }
        if (this.finalmat != null) {
            this.finalmat.release();
            this.finalmat = null;
        }
    }

    public void onDetach() {
        super.onDetach();
        showMessage("FragmentEditing", "Detach");
        if (!Constants.REMOVE_ADS && !Constants.ENDING_AD_SHOWN) {
            if (!Constants.FULL_SCREEN_Ending_NATIVE_FIRST) {
                if (AdUtil.getInstance().isEndingAdAvailable() && !Constants.ENDING_AD_SHOWN) {
                    Constants.ENDING_AD_SHOWN = true;
                    ((MainActivity) getActivity()).ShowFullScreenAd((String) null, false, false);
                } else if (AdUtil.getInstance().isEndingNativeLoaded() && AdUtil.getInstance().getEnding_fb_native() != null) {
                    ((MainActivity) getActivity()).showFloatingFBNativeAd(AdUtil.getInstance().getEnding_fb_native(), true);
                    Constants.ENDING_AD_SHOWN = true;
                }
            } else if (AdUtil.getInstance().isEndingNativeLoaded() && AdUtil.getInstance().getEnding_fb_native() != null) {
                ((MainActivity) getActivity()).showFloatingFBNativeAd(AdUtil.getInstance().getEnding_fb_native(), true);
                Constants.ENDING_AD_SHOWN = true;
            } else if (AdUtil.getInstance().isEndingAdAvailable() && !Constants.ENDING_AD_SHOWN) {
                Constants.ENDING_AD_SHOWN = true;
                ((MainActivity) getActivity()).ShowFullScreenAd((String) null, false, false);
            }
        }
        ((MainActivity) getActivity()).setWaitScreenGoneListener((WaitScreenGoneListener) null);
        ((MainActivity) getActivity()).UnlockMenu();
        Bundle bundle = new Bundle();
        bundle.putString("Screen", "Editing Screen Close");
        FireBaseHelper.getInstance().LogEvent("Screen_Analytics", bundle);
    }

    private void reinitialize() {
        new ReinitializeTask().execute(new Void[0]);
    }

    /* renamed from: ui.EditingFragment$ReinitializeTask */
    private class ReinitializeTask extends AsyncTask<Void, Void, Void> {
        private ReinitializeTask() {
        }

        /* access modifiers changed from: protected */
        public Void doInBackground(Void... voidArr) {
            if (EditingFragment.this.bitmapCache == null || EditingFragment.this.bitmapCache.getCacheBitmap(Constants.ORIGINAL_IMAGE) != null) {
                return null;
            }
            EditingFragment.this.bitmapCache.addBitmapToCache(Constants.ORIGINAL_IMAGE, EditingFragment.this.bitmap);
            return null;
        }
    }

    public void unbindDrawables(View view2) {
        try {
            if (view2.getBackground() != null) {
                view2.getBackground().setCallback((Drawable.Callback) null);
            }
            if (view2 instanceof ImageView) {
                Log.d("View", "imageView");
                ((ImageView) view2).setImageBitmap((Bitmap) null);
            } else if (view2 instanceof ViewGroup) {
                Log.d("View", "viewGroup");
                ViewGroup viewGroup = (ViewGroup) view2;
                for (int i = 0; i < viewGroup.getChildCount(); i++) {
                    unbindDrawables(viewGroup.getChildAt(i));
                }
                if (!(view2 instanceof AdapterView)) {
                    viewGroup.removeAllViews();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onClick(View view2) {
        if (view2 instanceof RadioButton) {
            boolean isChecked = ((RadioButton) view2).isChecked();
            int id = view2.getId();
            if (id == R.id.editor_button) {
                Bundle bundle = new Bundle();
                bundle.putString("Screen", "Editor Screen");
                FireBaseHelper.getInstance().LogEvent("Screen_Analytics", bundle);
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.restart.getLayoutParams();
                layoutParams.addRule(2, R.id.seekbar_view);
                this.restart.setLayoutParams(layoutParams);
                if (isChecked) {
                    disableButtons();
                    activateButton(this.editorButton);
                    deactivateButton(this.effectButton);
                    deactivateButton(this.filterButton);
                    hideSeekbar();
                    this.pager.setVisibility(View.GONE);
                    this.optionLayout.setVisibility(View.VISIBLE);
                    RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) this.imageLayout.getLayoutParams();
                    layoutParams2.addRule(2, R.id.options);
                    this.imageLayout.setLayoutParams(layoutParams2);
                    if (this.enhance_selected && this.enhance_position >= 0 && this.filter_type != null) {
                        this.editor_items.get(this.enhance_position).setChecked(true);
                        this.mAdapter.setItemIndex(this.enhance_position);
                        this.mAdapter.notifyDataSetChanged();
                        this.indicatorSeekBar.setProgress((float) this.filterHolder.getProgress(this.filter_type));
                        this.filter_name.setText(this.enhance_filter_name);
                        TextView textView = this.filter_value;
                        textView.setText(String.valueOf(this.filterHolder.getFilterValue(this.filter_type)) + " %");
                        this.seekBar_view.setVisibility(View.VISIBLE);
                    }
                    this.optionBitmap = ((BitmapDrawable) this.imageView.getDrawable()).getBitmap();
                    if (this.filterHolder == null || !this.filterHolder.getTwoHolders().isEmpty()) {
                        if (this.filterHolder != null && !this.filterHolder.getTwoHolders().isEmpty()) {
                            ApplyEnhanceFilter();
                        }
                    } else if (this.effect_selected) {
                        if (this.blendBitmap == null || this.adjustValue <= 0) {
                            if (this.filter_selected) {
                                ApplyFilter(true);
                            } else {
                                this.imageView.setImageBitmap(this.effectBitmap);
                                this.optionBitmap = this.effectBitmap;
                            }
                        } else if (this.filter_selected) {
                            ApplyFilter(true);
                        } else {
                            this.imageView.setImageBitmap(this.blendBitmap);
                            this.optionBitmap = this.blendBitmap;
                        }
                    } else if (this.filter_selected) {
                        ApplyFilter(true);
                    } else {
                        this.imageView.setImageBitmap(this.bitmap);
                        this.optionBitmap = this.bitmap;
                    }
                    this.editor_button_activated = true;
                    showMessage("buttonactive", "editor");
                }
            } else if (id == R.id.effect_button) {
                Bundle bundle2 = new Bundle();
                bundle2.putString("Screen", "Effect Screen");
                FireBaseHelper.getInstance().LogEvent("Screen_Analytics", bundle2);
                if (isChecked && !this.effect_button_activated) {
                    disableButtons();
                    activateButton(this.effectButton);
                    deactivateButton(this.filterButton);
                    deactivateButton(this.editorButton);
                    if (this.effectBitmap == null || !this.effect_selected) {
                        this.imageView.setImageBitmap(this.bitmap);
                    } else if (this.effectBitmap == null || this.adjustValue < 0) {
                        showSeekbar(true);
                        this.imageView.setImageBitmap(this.effectBitmap);
                    } else {
                        if (this.blendBitmap != null) {
                            this.imageView.setImageBitmap(this.blendBitmap);
                        } else {
                            this.imageView.setImageBitmap(this.effectBitmap);
                        }
                        showSeekbar(false);
                    }
                    int visibility = this.pager.getVisibility();
                    if (visibility == 0) {
                        this.pager.setCurrentItem(0);
                        showMessage("Filter", "Visible");
                    } else if (visibility == 4 || visibility == 8) {
                        if (this.optionLayout.getVisibility() == View.VISIBLE) {
                            this.optionLayout.setVisibility(View.GONE);
                        }
                        if (this.seekBar_view.getVisibility() == View.VISIBLE) {
                            this.seekBar_view.setVisibility(View.GONE);
                        }
                        this.pager.setVisibility(View.VISIBLE);
                        this.pager.setCurrentItem(0);
                        showMessage("Filter", "INVisible");
                    }
                    this.effect_button_activated = true;
                    showMessage("buttonactive", "effectInstance");
                }
                RelativeLayout.LayoutParams layoutParams3 = (RelativeLayout.LayoutParams) this.imageLayout.getLayoutParams();
                layoutParams3.addRule(2, R.id.tab);
                this.imageLayout.setLayoutParams(layoutParams3);
                RelativeLayout.LayoutParams layoutParams4 = (RelativeLayout.LayoutParams) this.restart.getLayoutParams();
                layoutParams4.addRule(2, R.id.seek_second);
                this.restart.setLayoutParams(layoutParams4);
                if (this.optionBitmap != null) {
                    this.optionBitmap = null;
                }
            } else if (id == R.id.filter_button) {
                Bundle bundle3 = new Bundle();
                bundle3.putString("Screen", "Filter Screen");
                FireBaseHelper.getInstance().LogEvent("Screen_Analytics", bundle3);
                if (isChecked) {
                    disableButtons();
                    activateButton(this.filterButton);
                    deactivateButton(this.effectButton);
                    deactivateButton(this.editorButton);
                    hideSeekbar();
                    if (!(this.blendBitmap == null || this.pagerAdapter == null || this.pagerAdapter.getCount() <= 1)) {
                        FilterButtonFragment filterButtonFragment2 = (FilterButtonFragment) this.pager.getAdapter().instantiateItem((ViewGroup) this.pager, 1);
                        filterButtonFragment2.changeBitmap(resize(this.blendBitmap, 96, 96));
                        if (!this.filter_selected) {
                            filterButtonFragment2.resetList();
                        }
                    }
                    int visibility2 = this.pager.getVisibility();
                    if (visibility2 == 0) {
                        this.pager.setCurrentItem(1);
                        if (this.filter_position >= 0) {
                            ApplyFilter(false);
                        }
                    } else if (visibility2 == 4 || visibility2 == 8) {
                        if (this.optionLayout.getVisibility() == View.VISIBLE) {
                            this.optionLayout.setVisibility(View.GONE);
                        }
                        if (this.seekBar_view.getVisibility() == View.VISIBLE) {
                            this.seekBar_view.setVisibility(View.GONE);
                        }
                        this.pager.setVisibility(View.VISIBLE);
                        this.pager.setCurrentItem(1);
                        showMessage("Filter", "INVisible");
                        if (!this.effect_selected) {
                            this.imageView.setImageBitmap(this.bitmap);
                        } else if (this.blendBitmap == null || this.adjustValue <= 0) {
                            this.imageView.setImageBitmap(this.effectBitmap);
                        } else {
                            this.imageView.setImageBitmap(this.blendBitmap);
                        }
                        if (this.filter_position >= 0) {
                            ApplyFilter(false);
                        }
                    }
                    this.filter_button_activated = true;
                    if (this.optionBitmap != null) {
                        this.optionBitmap = null;
                    }
                    showMessage("buttonactive", "filter");
                }
                RelativeLayout.LayoutParams layoutParams5 = (RelativeLayout.LayoutParams) this.imageLayout.getLayoutParams();
                layoutParams5.addRule(2, R.id.tab);
                this.imageLayout.setLayoutParams(layoutParams5);
                if (this.optionBitmap != null) {
                    this.optionBitmap = null;
                }
            }
        }
        switch (view2.getId()) {
            case R.id.art_button:
                ArtEffectFragment artEffectFragment = new ArtEffectFragment();
                if (this.details != null) {
                    this.details.setEffect(this.effect);
                    this.details.setFilterHolder(this.filterHolder);
                    ((MainActivity) getActivity()).setFilterHolder(this.filterHolder);
                    if (this.effect_selected && this.effect_name != null) {
                        this.details.setEffectName(this.effect_name);
                        this.details.setEffectSelected(this.effect_selected);
                    }
                    if (this.filter_selected && this.gpuImageFilter != null) {
                        this.details.setFilter(this.gpuImageFilter);
                        this.details.setFilterSelected(this.filter_selected);
                        Log.d("putfilter", "filterconstant");
                    }
                    if (this.filter_selected && this.filterConstant != null) {
                        this.details.setFilterConstant(this.filterConstant);
                        this.details.setFilterSelected(this.filter_selected);
                        Log.d("putfilter", "gpufilter");
                    }
                } else {
                    this.details = new EditingFragmentDetails();
                    this.details.setEffect(this.effect);
                    this.details.setFilterHolder(this.filterHolder);
                    ((MainActivity) getActivity()).setFilterHolder(this.filterHolder);
                    if (this.effect_selected && this.effect_name != null) {
                        this.details.setEffectName(this.effect_name);
                        this.details.setEffectSelected(this.effect_selected);
                    }
                    if (this.filter_selected && this.gpuImageFilter != null) {
                        this.details.setFilter(this.gpuImageFilter);
                        this.details.setFilterSelected(this.filter_selected);
                        Log.d("putfilter", "filterconstant");
                    }
                    if (this.filter_selected && this.filterConstant != null) {
                        this.details.setFilterConstant(this.filterConstant);
                        this.details.setFilterSelected(this.filter_selected);
                        Log.d("putfilter", "gpufilter");
                    }
                }
                if (this.filterHolder != null && !this.filterHolder.getTwoHolders().isEmpty()) {
                    if (this.sequence == null || !this.sequence.contains("enhance")) {
                        this.sequence.add("enhance");
                    } else {
                        this.sequence.remove("enhance");
                        this.sequence.add("enhance");
                    }
                    ((MainActivity) getActivity()).setFilterHolder(this.filterHolder);
                }
                if (this.sequence != null && !this.sequence.isEmpty()) {
                    this.details.setSeq(this.sequence);
                }
                ((MainActivity) getActivity()).setEffect(this.effect);
                if (this.effect_button_activated) {
                    this.details.setEffectSelected(true);
                    this.details.setEffectButtonChecked(true);
                }
                if (this.filter_button_activated) {
                    this.details.setFilterSelected(true);
                    this.details.setFilterButtonChecked(true);
                }
                if (this.editor_button_activated) {
                    this.details.setEditorSelected(true);
                    this.details.setEditorButtonChecked(true);
                }
                ((MainActivity) getActivity()).setEditingFragmentDetails(this.details);
                ((ReplaceFragmentListener) getActivity()).ReplaceFragment(artEffectFragment, "Art");
                return;
            case R.id.export:
                if (this.details != null) {
                    this.details.setEffect(this.effect);
                    this.details.setFilterHolder(this.filterHolder);
                    ((MainActivity) getActivity()).setFilterHolder(this.filterHolder);
                    if (this.effect_selected && this.effect_name != null) {
                        this.details.setEffectName(this.effect_name);
                        this.details.setEffectSelected(this.effect_selected);
                    }
                    if (this.filter_selected && this.gpuImageFilter != null) {
                        this.details.setFilter(this.gpuImageFilter);
                        this.details.setFilterSelected(this.filter_selected);
                        Log.d("putfilter", "filterconstant");
                    }
                    if (this.filter_selected && this.filterConstant != null) {
                        this.details.setFilterConstant(this.filterConstant);
                        this.details.setFilterSelected(this.filter_selected);
                        Log.d("putfilter", "gpufilter");
                    }
                } else {
                    this.details = new EditingFragmentDetails();
                    this.details.setEffect(this.effect);
                    this.details.setFilterHolder(this.filterHolder);
                    ((MainActivity) getActivity()).setFilterHolder(this.filterHolder);
                    if (this.effect_selected && this.effect_name != null) {
                        this.details.setEffectName(this.effect_name);
                        this.details.setEffectSelected(this.effect_selected);
                    }
                    if (this.filter_selected && this.gpuImageFilter != null) {
                        this.details.setFilter(this.gpuImageFilter);
                        this.details.setFilterSelected(this.filter_selected);
                        Log.d("putfilter", "filterconstant");
                    }
                    if (this.filter_selected && this.filterConstant != null) {
                        this.details.setFilterConstant(this.filterConstant);
                        this.details.setFilterSelected(this.filter_selected);
                        Log.d("putfilter", "gpufilter");
                    }
                }
                startExportFragment();
                ((MainActivity) getActivity()).setEditingFragmentDetails(this.details);
                return;
            case R.id.no_button:
                if (this.no_rate.getText().equals(getString(R.string.not))) {
                    this.rateDialogText.setText(getString(R.string.feedback_text));
                    this.rateIt.setText(getString(R.string.ok_sure));
                    this.no_rate.setText(getString(R.string.no_thanks));
                    this.feedback = true;
                    Bundle bundle4 = new Bundle();
                    bundle4.putString("LoveApp", "Not Really");
                    FireBaseHelper.getInstance().LogEvent("Native_App_Rate", bundle4);
                } else if (this.no_rate.getText().equals(getString(R.string.no_thanks))) {
                    this.native_rateView.setVisibility(View.GONE);
                    if (this.enjoyPencilPhotoSketch) {
                        SharedPreferencesManager.setSomeBoolValue(getActivity(), Constants.APP_ENJOYED, true);
                        Bundle bundle5 = new Bundle();
                        bundle5.putString("AppRater", "Enjoyed But Not Rated");
                        FireBaseHelper.getInstance().LogEvent("Native_App_Rate", bundle5);
                    } else if (this.feedback) {
                        Bundle bundle6 = new Bundle();
                        bundle6.putString("AppRater", "Not Enjoyed Provide Feedback");
                        FireBaseHelper.getInstance().LogEvent("Native_App_Rate", bundle6);
                    }
                }
                this.rateViewShowing = false;
                if (!SharedPreferencesManager.HasKey(getActivity(), Constants.APP_RATED) && !SharedPreferencesManager.getSomeBoolValue(getActivity(), Constants.APP_RATED)) {
                    Bundle bundle7 = new Bundle();
                    bundle7.putString("not_rate", "Nothing done");
                    FireBaseHelper.getInstance().LogEvent("Not_Rate", bundle7);
                }
                SharedPreferencesManager.setSomeBoolValue(getActivity(), Constants.APP_RATED, true);
                return;
            case R.id.undo_button:
                if (!SharedPreferencesManager.HasKey(getActivity(), "reset_dialog_shown")) {
                    SharedPreferencesManager.setSomeBoolValue(getActivity(), "reset_dialog_shown", true);
                    initializeResetDialogTut();
                    return;
                } else if (this.undo_button_activate) {
                    restart();
                    RelativeLayout.LayoutParams layoutParams6 = (RelativeLayout.LayoutParams) this.imageLayout.getLayoutParams();
                    layoutParams6.addRule(2, R.id.tab);
                    this.imageLayout.setLayoutParams(layoutParams6);
                    RelativeLayout.LayoutParams layoutParams7 = (RelativeLayout.LayoutParams) this.restart.getLayoutParams();
                    layoutParams7.addRule(2, R.id.seek_second);
                    this.restart.setLayoutParams(layoutParams7);
                    return;
                } else {
                    return;
                }
            case R.id.yes_button:
                Log.d("Nativerate", "yes");
                if (this.rateIt.getText().equals("Yes!")) {
                    this.rateDialogText.setText(getString(R.string.rate_text));
                    this.rateIt.setText(getString(R.string.ok_sure));
                    this.no_rate.setText(getString(R.string.no_thanks));
                    Bundle bundle8 = new Bundle();
                    bundle8.putString("LoveApp", "Yes");
                    FireBaseHelper.getInstance().LogEvent("Native_App_Rate", bundle8);
                    this.enjoyPencilPhotoSketch = true;
                } else if (this.rateIt.getText().equals(getString(R.string.ok_sure))) {
                    if (this.feedback) {
                        Intent intent = new Intent("android.intent.action.SEND");
                        intent.putExtra("android.intent.extra.EMAIL", new String[]{"hqgames1080@gmail.com"});
                        intent.putExtra("android.intent.extra.SUBJECT", "Pencil Photo Sketch:FeedBack");
                        intent.setType("message/rfc822");
                        startActivity(Intent.createChooser(intent, "Select Email Sending App :"));
                        Bundle bundle9 = new Bundle();
                        bundle9.putString("AppRater", "Feedback");
                        FireBaseHelper.getInstance().LogEvent("Native_App_Rate", bundle9);
                        this.rateDialogText.setText(getString(R.string.thanks_text));
                        this.rateIt.setText(getString(R.string.close));
                        this.no_rate.setEnabled(false);
                        if (!SharedPreferencesManager.HasKey(getActivity(), Constants.APP_RATED) && !SharedPreferencesManager.getSomeBoolValue(getActivity(), Constants.APP_RATED)) {
                            Bundle bundle10 = new Bundle();
                            bundle10.putString("not_rate", "feedback_done");
                            FireBaseHelper.getInstance().LogEvent("Not_Rate", bundle10);
                        }
                        SharedPreferencesManager.setSomeBoolValue(getActivity(), Constants.APP_RATED, true);
                    } else {
                        try {
                            startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=" + getActivity().getPackageName())));
                        } catch (ActivityNotFoundException unused) {
                            startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=" + getActivity().getPackageName())));
                        }
                        if (SharedPreferencesManager.getSomeBoolValue(getActivity(), Constants.APP_ENJOYED)) {
                            this.enjoyPencilPhotoSketch = true;
                        }
                        this.rateDialogText.setText(getString(R.string.thanks_text));
                        this.rateIt.setText(getString(R.string.close));
                        this.no_rate.setEnabled(false);
                    }
                } else if (this.rateIt.getText().equals(getString(R.string.close))) {
                    this.native_rateView.setVisibility(View.GONE);
                    if (this.enjoyPencilPhotoSketch) {
                        Bundle bundle11 = new Bundle();
                        bundle11.putString("AppRater", "Enjoyed And Rated");
                        FireBaseHelper.getInstance().LogEvent("Native_App_Rate", bundle11);
                    } else if (this.feedback) {
                        Bundle bundle12 = new Bundle();
                        bundle12.putString("AppRater", "Not Enjoyed & No Feedback");
                        FireBaseHelper.getInstance().LogEvent("Native_App_Rate", bundle12);
                    }
                    SharedPreferencesManager.setSomeBoolValue(getActivity(), Constants.APP_RATED, true);
                }
                this.rateViewShowing = false;
                return;
            default:
                return;
        }
    }

    /* access modifiers changed from: package-private */
    public float getNext() {
        return new Random().nextFloat();
    }

    /* access modifiers changed from: package-private */
    public int getNextInt(int i, int i2) {
        return new Random().nextInt((i2 - i) + 1) + i;
    }

    /* access modifiers changed from: private */
    public void disableButtons() {
        this.effect_button_activated = false;
        this.filter_button_activated = false;
        this.editor_button_activated = false;
    }

    /* access modifiers changed from: private */
    public void activateButton(RadioButton radioButton) {
        radioButton.setChecked(true);
    }

    /* access modifiers changed from: private */
    public void deactivateButton(RadioButton radioButton) {
        radioButton.setChecked(false);
    }

    public void activateUndoButton() {
        this.undo_button_activate = true;
        if (!Constants.CUSTOM_EDITING_SCREEN_ENABLED || Constants.RESTART_SELECT_TINT_COLOR == null) {
            this.restart.setColorFilter(Color.parseColor("#FF3B3F"), PorterDuff.Mode.SRC_IN);
        } else {
            this.restart.setColorFilter(Color.parseColor(Constants.RESTART_SELECT_TINT_COLOR), PorterDuff.Mode.SRC_IN);
        }
    }

    public void deactivateUndoButton() {
        this.undo_button_activate = false;
        if (!Constants.CUSTOM_EDITING_SCREEN_ENABLED || Constants.RESTART_SELECT_TINT_COLOR == null || Constants.RESTART_DESELECT_TINT_COLOR == null) {
            this.restart.setColorFilter(Color.parseColor("#23272c"), PorterDuff.Mode.SRC_IN);
        } else {
            this.restart.setColorFilter(Color.parseColor(Constants.RESTART_DESELECT_TINT_COLOR), PorterDuff.Mode.SRC_IN);
        }
    }

    private void startExportFragment() {
        setFunctionsSequence();
        ExportDetails exportDetails = new ExportDetails();
        exportDetails.setEffect(this.effect);
        exportDetails.setBitmapCache(this.bitmapCache);
        if (this.details.isEffectSelected()) {
            exportDetails.setEffect_name(this.effect_name);
        }
        if (this.details.isFilterSelected() && this.details.getFilter() != null) {
            exportDetails.setFilter(this.details.getFilter());
        } else if (this.details.isFilterSelected() && this.details.getFilterConstant() != null) {
            exportDetails.setFilterConstant(this.details.getFilterConstant());
        }
        if (this.adjustValue > 0) {
            exportDetails.setAdjustValue(this.adjustValue);
        }
        if (this.filterHolder != null && !this.filterHolder.getTwoHolders().isEmpty()) {
            if (this.sequence == null || !this.sequence.contains("enhance")) {
                this.sequence.add("enhance");
            } else {
                this.sequence.remove("enhance");
                this.sequence.add("enhance");
            }
            exportDetails.setEnhanceFiltersApplied(true);
            exportDetails.setFilterHolder(this.filterHolder);
            ((MainActivity) getActivity()).setFilterHolder(this.filterHolder);
        }
        if (this.sequence == null || this.sequence.isEmpty()) {
            exportDetails.setNoEffectAndFilterApplied(true);
        } else {
            exportDetails.setSeq(this.sequence);
            this.details.setSeq(this.sequence);
        }
        ((MainActivity) getActivity()).setEffect(this.effect);
        ((MainActivity) getActivity()).setExport(exportDetails);
        if (this.effect_button_activated) {
            this.details.setEffectSelected(true);
            this.details.setEffectButtonChecked(true);
        }
        if (this.filter_button_activated) {
            this.details.setFilterSelected(true);
            this.details.setFilterButtonChecked(true);
        }
        if (this.editor_button_activated) {
            this.details.setEditorSelected(true);
            this.details.setEditorButtonChecked(true);
        }
        ExportFragment exportFragment = new ExportFragment();
        if (this.fileUri != null) {
            Bundle bundle = new Bundle();
            bundle.putString("fileUri", this.fileUri.toString());
            exportFragment.setArguments(bundle);
        }
        ((ReplaceFragmentListener) getActivity()).ReplaceFragment(exportFragment, "EditingFragment");
    }

    private void setFunctionsSequence() {
        if (this.sequence != null && this.sequence.size() >= 0) {
            this.sequence.clear();
        }
        if (this.effect_selected) {
            if (this.sequence == null || !this.sequence.contains("effect")) {
                this.sequence.add("effect");
            } else {
                this.sequence.remove("effect");
                this.sequence.add("effect");
            }
            Log.d("listsequence", " effect called");
        }
        if (this.adjustValue < 100 && this.blendBitmap != null) {
            if (this.sequence == null || !this.sequence.contains("adjust")) {
                this.sequence.add("adjust");
            } else {
                this.sequence.remove("adjust");
                this.sequence.add("adjust");
            }
        }
        if (!this.filter_selected) {
            return;
        }
        if (this.sequence == null || !this.sequence.contains("filter")) {
            this.sequence.add("filter");
            return;
        }
        this.sequence.remove("filter");
        this.sequence.add("filter");
    }

    public void OnEffectClick(String str, int i) {
        Log.d("effectname", str);
        if (str.equals("CHALK")) {
            AlertDialog.Builder builder2 = new AlertDialog.Builder(getActivity());
            builder2.setTitle("Color Chalk-Choose Effect");
            builder2.setItems(this.colorchalk, new DialogInterface.OnClickListener() {
                /* JADX WARNING: Removed duplicated region for block: B:17:0x0046  */
                /* JADX WARNING: Removed duplicated region for block: B:21:0x00d7  */
                /* JADX WARNING: Removed duplicated region for block: B:25:0x0168  */
                /* JADX WARNING: Removed duplicated region for block: B:35:? A[RETURN, SYNTHETIC] */
                /* Code decompiled incorrectly, please refer to instructions dump. */
                public void onClick(android.content.DialogInterface r7, int r8) {
                    /*
                        r6 = this;
                        ui.EditingFragment r7 = p049ui.EditingFragment.this
                        java.lang.CharSequence[] r7 = r7.colorchalk
                        r7 = r7[r8]
                        java.lang.String r7 = r7.toString()
                        int r0 = r7.hashCode()
                        r1 = -1728623736(0xffffffff98f74b88, float:-6.392427E-24)
                        r2 = 0
                        r3 = 1
                        if (r0 == r1) goto L_0x0036
                        r1 = 751101088(0x2cc4e4a0, float:5.5960375E-12)
                        if (r0 == r1) goto L_0x002c
                        r1 = 1240849124(0x49f5dae4, float:2014044.5)
                        if (r0 == r1) goto L_0x0022
                        goto L_0x0040
                    L_0x0022:
                        java.lang.String r0 = "Thin Edge"
                        boolean r7 = r7.equals(r0)
                        if (r7 == 0) goto L_0x0040
                        r7 = 0
                        goto L_0x0041
                    L_0x002c:
                        java.lang.String r0 = "Thick Edge"
                        boolean r7 = r7.equals(r0)
                        if (r7 == 0) goto L_0x0040
                        r7 = 2
                        goto L_0x0041
                    L_0x0036:
                        java.lang.String r0 = "Medium Edge"
                        boolean r7 = r7.equals(r0)
                        if (r7 == 0) goto L_0x0040
                        r7 = 1
                        goto L_0x0041
                    L_0x0040:
                        r7 = -1
                    L_0x0041:
                        switch(r7) {
                            case 0: goto L_0x0168;
                            case 1: goto L_0x00d7;
                            case 2: goto L_0x0046;
                            default: goto L_0x0044;
                        }
                    L_0x0044:
                        goto L_0x01f6
                    L_0x0046:
                        ui.EditingFragment$EffectApplyingTask r7 = new ui.EditingFragment$EffectApplyingTask
                        ui.EditingFragment r0 = p049ui.EditingFragment.this
                        ui.EditingFragment r1 = p049ui.EditingFragment.this
                        androidx.fragment.app.FragmentActivity r1 = r1.getActivity()
                        java.lang.StringBuilder r4 = new java.lang.StringBuilder
                        r4.<init>()
                        java.lang.String r5 = "Color "
                        r4.append(r5)
                        ui.EditingFragment r5 = p049ui.EditingFragment.this
                        java.lang.CharSequence[] r5 = r5.colorchalk
                        r5 = r5[r8]
                        java.lang.String r5 = r5.toString()
                        r4.append(r5)
                        java.lang.String r4 = r4.toString()
                        r7.<init>(r1, r4)
                        java.lang.Void[] r0 = new java.lang.Void[r2]
                        r7.execute(r0)
                        ui.EditingFragment r7 = p049ui.EditingFragment.this
                        boolean unused = r7.effect_selected = r3
                        ui.EditingFragment r7 = p049ui.EditingFragment.this
                        java.lang.StringBuilder r0 = new java.lang.StringBuilder
                        r0.<init>()
                        java.lang.String r1 = "Color "
                        r0.append(r1)
                        ui.EditingFragment r1 = p049ui.EditingFragment.this
                        java.lang.CharSequence[] r1 = r1.colorchalk
                        r8 = r1[r8]
                        java.lang.String r8 = r8.toString()
                        r0.append(r8)
                        java.lang.String r8 = r0.toString()
                        java.lang.String unused = r7.effect_name = r8
                        ui.EditingFragment r7 = p049ui.EditingFragment.this
                        util.EditingFragmentDetails r7 = r7.details
                        if (r7 == 0) goto L_0x00b8
                        ui.EditingFragment r7 = p049ui.EditingFragment.this
                        util.EditingFragmentDetails r7 = r7.details
                        r7.setEffectSelected(r3)
                        ui.EditingFragment r7 = p049ui.EditingFragment.this
                        util.EditingFragmentDetails r7 = r7.details
                        ui.EditingFragment r8 = p049ui.EditingFragment.this
                        java.lang.String r8 = r8.effect_name
                        r7.setEffectName(r8)
                        goto L_0x01f6
                    L_0x00b8:
                        ui.EditingFragment r7 = p049ui.EditingFragment.this
                        util.EditingFragmentDetails r8 = new util.EditingFragmentDetails
                        r8.<init>()
                        r7.details = r8
                        ui.EditingFragment r7 = p049ui.EditingFragment.this
                        util.EditingFragmentDetails r7 = r7.details
                        r7.setEffectSelected(r3)
                        ui.EditingFragment r7 = p049ui.EditingFragment.this
                        util.EditingFragmentDetails r7 = r7.details
                        ui.EditingFragment r8 = p049ui.EditingFragment.this
                        java.lang.String r8 = r8.effect_name
                        r7.setEffectName(r8)
                        goto L_0x01f6
                    L_0x00d7:
                        ui.EditingFragment$EffectApplyingTask r7 = new ui.EditingFragment$EffectApplyingTask
                        ui.EditingFragment r0 = p049ui.EditingFragment.this
                        ui.EditingFragment r1 = p049ui.EditingFragment.this
                        androidx.fragment.app.FragmentActivity r1 = r1.getActivity()
                        java.lang.StringBuilder r4 = new java.lang.StringBuilder
                        r4.<init>()
                        java.lang.String r5 = "Color "
                        r4.append(r5)
                        ui.EditingFragment r5 = p049ui.EditingFragment.this
                        java.lang.CharSequence[] r5 = r5.colorchalk
                        r5 = r5[r8]
                        java.lang.String r5 = r5.toString()
                        r4.append(r5)
                        java.lang.String r4 = r4.toString()
                        r7.<init>(r1, r4)
                        java.lang.Void[] r0 = new java.lang.Void[r2]
                        r7.execute(r0)
                        ui.EditingFragment r7 = p049ui.EditingFragment.this
                        boolean unused = r7.effect_selected = r3
                        ui.EditingFragment r7 = p049ui.EditingFragment.this
                        java.lang.StringBuilder r0 = new java.lang.StringBuilder
                        r0.<init>()
                        java.lang.String r1 = "Color "
                        r0.append(r1)
                        ui.EditingFragment r1 = p049ui.EditingFragment.this
                        java.lang.CharSequence[] r1 = r1.colorchalk
                        r8 = r1[r8]
                        java.lang.String r8 = r8.toString()
                        r0.append(r8)
                        java.lang.String r8 = r0.toString()
                        java.lang.String unused = r7.effect_name = r8
                        ui.EditingFragment r7 = p049ui.EditingFragment.this
                        util.EditingFragmentDetails r7 = r7.details
                        if (r7 == 0) goto L_0x0149
                        ui.EditingFragment r7 = p049ui.EditingFragment.this
                        util.EditingFragmentDetails r7 = r7.details
                        r7.setEffectSelected(r3)
                        ui.EditingFragment r7 = p049ui.EditingFragment.this
                        util.EditingFragmentDetails r7 = r7.details
                        ui.EditingFragment r8 = p049ui.EditingFragment.this
                        java.lang.String r8 = r8.effect_name
                        r7.setEffectName(r8)
                        goto L_0x01f6
                    L_0x0149:
                        ui.EditingFragment r7 = p049ui.EditingFragment.this
                        util.EditingFragmentDetails r8 = new util.EditingFragmentDetails
                        r8.<init>()
                        r7.details = r8
                        ui.EditingFragment r7 = p049ui.EditingFragment.this
                        util.EditingFragmentDetails r7 = r7.details
                        r7.setEffectSelected(r3)
                        ui.EditingFragment r7 = p049ui.EditingFragment.this
                        util.EditingFragmentDetails r7 = r7.details
                        ui.EditingFragment r8 = p049ui.EditingFragment.this
                        java.lang.String r8 = r8.effect_name
                        r7.setEffectName(r8)
                        goto L_0x01f6
                    L_0x0168:
                        ui.EditingFragment$EffectApplyingTask r7 = new ui.EditingFragment$EffectApplyingTask
                        ui.EditingFragment r0 = p049ui.EditingFragment.this
                        ui.EditingFragment r1 = p049ui.EditingFragment.this
                        androidx.fragment.app.FragmentActivity r1 = r1.getActivity()
                        java.lang.StringBuilder r4 = new java.lang.StringBuilder
                        r4.<init>()
                        java.lang.String r5 = "Color "
                        r4.append(r5)
                        ui.EditingFragment r5 = p049ui.EditingFragment.this
                        java.lang.CharSequence[] r5 = r5.colorchalk
                        r5 = r5[r8]
                        java.lang.String r5 = r5.toString()
                        r4.append(r5)
                        java.lang.String r4 = r4.toString()
                        r7.<init>(r1, r4)
                        java.lang.Void[] r0 = new java.lang.Void[r2]
                        r7.execute(r0)
                        ui.EditingFragment r7 = p049ui.EditingFragment.this
                        boolean unused = r7.effect_selected = r3
                        ui.EditingFragment r7 = p049ui.EditingFragment.this
                        java.lang.StringBuilder r0 = new java.lang.StringBuilder
                        r0.<init>()
                        java.lang.String r1 = "Color "
                        r0.append(r1)
                        ui.EditingFragment r1 = p049ui.EditingFragment.this
                        java.lang.CharSequence[] r1 = r1.colorchalk
                        r8 = r1[r8]
                        java.lang.String r8 = r8.toString()
                        r0.append(r8)
                        java.lang.String r8 = r0.toString()
                        java.lang.String unused = r7.effect_name = r8
                        ui.EditingFragment r7 = p049ui.EditingFragment.this
                        util.EditingFragmentDetails r7 = r7.details
                        if (r7 == 0) goto L_0x01d9
                        ui.EditingFragment r7 = p049ui.EditingFragment.this
                        util.EditingFragmentDetails r7 = r7.details
                        r7.setEffectSelected(r3)
                        ui.EditingFragment r7 = p049ui.EditingFragment.this
                        util.EditingFragmentDetails r7 = r7.details
                        ui.EditingFragment r8 = p049ui.EditingFragment.this
                        java.lang.String r8 = r8.effect_name
                        r7.setEffectName(r8)
                        goto L_0x01f6
                    L_0x01d9:
                        ui.EditingFragment r7 = p049ui.EditingFragment.this
                        util.EditingFragmentDetails r8 = new util.EditingFragmentDetails
                        r8.<init>()
                        r7.details = r8
                        ui.EditingFragment r7 = p049ui.EditingFragment.this
                        util.EditingFragmentDetails r7 = r7.details
                        r7.setEffectSelected(r3)
                        ui.EditingFragment r7 = p049ui.EditingFragment.this
                        util.EditingFragmentDetails r7 = r7.details
                        ui.EditingFragment r8 = p049ui.EditingFragment.this
                        java.lang.String r8 = r8.effect_name
                        r7.setEffectName(r8)
                    L_0x01f6:
                        return
                    */
                    throw new UnsupportedOperationException("Method not decompiled: p049ui.EditingFragment.C510511.onClick(android.content.DialogInterface, int):void");
                }
            });
            AlertDialog create = builder2.create();
            create.setOnDismissListener(new DialogInterface.OnDismissListener() {
                public void onDismiss(DialogInterface dialogInterface) {
                }
            });
            create.show();
        } else {
            new EffectApplyingTask(getActivity(), str).execute(new Void[0]);
            this.effect_selected = true;
            this.effect_name = str;
        }
        if (this.details != null) {
            this.details.setEffectSelected(this.effect_selected);
            this.details.setEffectPosition(i);
            this.details.setEffectName(this.effect_name);
        } else {
            this.details = new EditingFragmentDetails();
            this.details.setEffectSelected(this.effect_selected);
            this.details.setEffectPosition(i);
            this.details.setEffectName(this.effect_name);
        }
        if (this.filterHolder != null && !this.filterHolder.getTwoHolders().isEmpty() && this.sequence != null && this.sequence.contains("enhance")) {
            this.sequence.remove("enhance");
        }
        activateUndoButton();
    }

    public void OnRemoveEffect() {
        Log.d("Effect", "Remove");
        this.effect_selected = false;
        this.effect_name = null;
        this.imageView.setImageBitmap(this.bitmap);
        this.details.setEffectSelected(this.effect_selected);
        this.details.setEffectPosition(-1);
        this.details.setEffectName((String) null);
        this.mAdapter.UncheckAll();
        this.mAdapter.notifyDataSetChanged();
        if (this.pager.getAdapter() != null && this.pager.getAdapter().getCount() > 1) {
            FilterButtonFragment filterButtonFragment2 = (FilterButtonFragment) this.pager.getAdapter().instantiateItem((ViewGroup) this.pager, 1);
            filterButtonFragment2.changeBitmap(resize(this.bitmap, 96, 96));
            filterButtonFragment2.resetList();
        }
        if (!this.filter_selected && this.filterHolder != null && this.filterHolder.getTwoHolders().isEmpty()) {
            deactivateUndoButton();
        }
        if (this.effectBitmap != null) {
            this.effectBitmap = null;
        }
        if (this.blendBitmap != null) {
            this.blendBitmap = null;
        }
        if (this.gpuBitmap != null) {
            this.gpuBitmap = null;
        }
        if (this.optionBitmap != null) {
            this.optionBitmap = null;
        }
        hideSeekbar();
    }

    /* access modifiers changed from: package-private */
    public void ApplyFilter(final boolean z) {
        final Handler handler2 = new Handler();
        handler2.postDelayed(new Runnable() {
            public void run() {
                if (EditingFragment.this.filterConstant != null) {
                    if (EditingFragment.this.blendBitmap != null && EditingFragment.this.effect_selected && EditingFragment.this.adjustValue >= 0) {
                        Bitmap unused = EditingFragment.this.gpuBitmap = CGENativeLibrary.filterImage_MultipleEffects(Utils.convert(EditingFragment.this.blendBitmap.copy(EditingFragment.this.blendBitmap.getConfig(), true), Bitmap.Config.ARGB_8888), EditingFragment.this.filterConstant, 1.0f);
                    } else if (!EditingFragment.this.effect_selected || EditingFragment.this.effectBitmap == null) {
                        Bitmap unused2 = EditingFragment.this.gpuBitmap = CGENativeLibrary.filterImage_MultipleEffects(Utils.convert(EditingFragment.this.bitmap.copy(EditingFragment.this.bitmap.getConfig(), true), Bitmap.Config.ARGB_8888), EditingFragment.this.filterConstant, 1.0f);
                    } else {
                        Bitmap unused3 = EditingFragment.this.gpuBitmap = CGENativeLibrary.filterImage_MultipleEffects(Utils.convert(EditingFragment.this.effectBitmap.copy(EditingFragment.this.effectBitmap.getConfig(), true), Bitmap.Config.ARGB_8888), EditingFragment.this.filterConstant, 1.0f);
                    }
                    if (z) {
                        Bitmap unused4 = EditingFragment.this.optionBitmap = EditingFragment.this.gpuBitmap;
                    }
                    EditingFragment.this.imageView.setImageBitmap(EditingFragment.this.gpuBitmap);
                    return;
                }
                if (EditingFragment.this.gpuImage != null) {
                    EditingFragment.this.gpuImage.setImage(EditingFragment.this.bitmap);
                    if (!EditingFragment.this.effect_selected) {
                        EditingFragment.this.gpuImage.setImage(EditingFragment.this.bitmap);
                    } else if (EditingFragment.this.blendBitmap != null && EditingFragment.this.adjustValue > 0) {
                        EditingFragment.this.gpuImage.setImage(EditingFragment.this.blendBitmap);
                    } else if (EditingFragment.this.effectBitmap == null || !EditingFragment.this.effect_selected) {
                        EditingFragment.this.gpuImage.setImage(EditingFragment.this.bitmap);
                    } else {
                        EditingFragment.this.gpuImage.setImage(EditingFragment.this.effectBitmap);
                    }
                    EditingFragment.this.gpuImage.setFilter(EditingFragment.this.gpuImageFilter);
                    Bitmap unused5 = EditingFragment.this.gpuBitmap = Utils.convert(EditingFragment.this.gpuImage.getBitmapWithFilterApplied(), Bitmap.Config.RGB_565);
                    EditingFragment.this.imageView.setImageBitmap(EditingFragment.this.gpuBitmap);
                } else if (EditingFragment.this.getActivity() != null) {
                    GPUImage unused6 = EditingFragment.this.gpuImage = new GPUImage(EditingFragment.this.getActivity());
                    handler2.postDelayed(this, 100);
                } else {
                    handler2.postDelayed(this, 50);
                }
                if (z) {
                    Bitmap unused7 = EditingFragment.this.optionBitmap = EditingFragment.this.gpuBitmap;
                }
            }
        }, 200);
    }

    /* access modifiers changed from: package-private */
    public void ApplyEnhanceFilter() {
        final Handler handler2 = new Handler();
        handler2.postDelayed(new Runnable() {
            public void run() {
                if (EditingFragment.this.gpuImage != null) {
                    if (!EditingFragment.this.effect_selected || EditingFragment.this.effectBitmap == null) {
                        if (EditingFragment.this.filter_selected && EditingFragment.this.gpuImageFilter != null) {
                            EditingFragment.this.gpuImage.setImage(EditingFragment.this.bitmap);
                            EditingFragment.this.gpuImage.setFilter(EditingFragment.this.gpuImageFilter);
                            Bitmap unused = EditingFragment.this.optionBitmap = Utils.convert(EditingFragment.this.gpuImage.getBitmapWithFilterApplied(), Bitmap.Config.RGB_565);
                            EditingFragment.this.ApplyingEffectsMsg("Filter + Editor");
                        } else if (!EditingFragment.this.filter_selected || EditingFragment.this.filterConstant == null) {
                            Bitmap unused2 = EditingFragment.this.optionBitmap = EditingFragment.this.bitmap;
                            EditingFragment.this.ApplyingEffectsMsg("Editor");
                        } else {
                            Bitmap unused3 = EditingFragment.this.optionBitmap = CGENativeLibrary.filterImage_MultipleEffects(Utils.convert(EditingFragment.this.effectBitmap.copy(EditingFragment.this.effectBitmap.getConfig(), true), Bitmap.Config.ARGB_8888), EditingFragment.this.filterConstant, 1.0f);
                            Bitmap unused4 = EditingFragment.this.optionBitmap = Utils.convert(EditingFragment.this.optionBitmap, Bitmap.Config.RGB_565);
                        }
                    } else if (EditingFragment.this.blendBitmap == null || EditingFragment.this.adjustValue <= 0) {
                        if (EditingFragment.this.filter_selected && EditingFragment.this.gpuImageFilter != null) {
                            EditingFragment.this.gpuImage.setImage(EditingFragment.this.effectBitmap);
                            EditingFragment.this.gpuImage.setFilter(EditingFragment.this.gpuImageFilter);
                            Bitmap unused5 = EditingFragment.this.optionBitmap = Utils.convert(EditingFragment.this.gpuImage.getBitmapWithFilterApplied(), Bitmap.Config.RGB_565);
                            EditingFragment.this.ApplyingEffectsMsg("Effect + Filter + Editor");
                        } else if (!EditingFragment.this.filter_selected || EditingFragment.this.filterConstant == null) {
                            if (EditingFragment.this.effectBitmap != null) {
                                Bitmap unused6 = EditingFragment.this.optionBitmap = EditingFragment.this.effectBitmap;
                            }
                            EditingFragment.this.ApplyingEffectsMsg("Effect + Editor");
                        } else {
                            Bitmap unused7 = EditingFragment.this.optionBitmap = CGENativeLibrary.filterImage_MultipleEffects(Utils.convert(EditingFragment.this.effectBitmap.copy(EditingFragment.this.effectBitmap.getConfig(), true), Bitmap.Config.ARGB_8888), EditingFragment.this.filterConstant, 1.0f);
                            Bitmap unused8 = EditingFragment.this.optionBitmap = Utils.convert(EditingFragment.this.optionBitmap, Bitmap.Config.RGB_565);
                        }
                    } else if (EditingFragment.this.filter_selected && EditingFragment.this.gpuImageFilter != null) {
                        EditingFragment.this.gpuImage.setImage(EditingFragment.this.blendBitmap);
                        EditingFragment.this.gpuImage.setFilter(EditingFragment.this.gpuImageFilter);
                        Bitmap unused9 = EditingFragment.this.optionBitmap = Utils.convert(EditingFragment.this.gpuImage.getBitmapWithFilterApplied(), Bitmap.Config.RGB_565);
                        EditingFragment.this.ApplyingEffectsMsg("Effect + Filter + Editor");
                    } else if (!EditingFragment.this.filter_selected || EditingFragment.this.filterConstant == null) {
                        if (EditingFragment.this.blendBitmap != null) {
                            Bitmap unused10 = EditingFragment.this.optionBitmap = EditingFragment.this.blendBitmap;
                        }
                        EditingFragment.this.ApplyingEffectsMsg("Effect + Editor");
                    } else {
                        Bitmap unused11 = EditingFragment.this.optionBitmap = CGENativeLibrary.filterImage_MultipleEffects(Utils.convert(EditingFragment.this.blendBitmap.copy(EditingFragment.this.blendBitmap.getConfig(), true), Bitmap.Config.ARGB_8888), EditingFragment.this.filterConstant, 1.0f);
                        Bitmap unused12 = EditingFragment.this.optionBitmap = Utils.convert(EditingFragment.this.optionBitmap, Bitmap.Config.RGB_565);
                    }
                    if (EditingFragment.this.optionBitmap == null) {
                        Bitmap unused13 = EditingFragment.this.optionBitmap = ((BitmapDrawable) EditingFragment.this.imageView.getDrawable()).getBitmap();
                    }
                    EditingFragment.this.readFilters();
                    EditingFragment.this.gpuImage.setImage(EditingFragment.this.optionBitmap);
                    EditingFragment.this.gpuImage.setFilter(EditingFragment.this.imageFilterGroup);
                    EditingFragment.this.imageView.setImageBitmap(EditingFragment.this.gpuImage.getBitmapWithFilterApplied());
                    return;
                }
                GPUImage unused14 = EditingFragment.this.gpuImage = new GPUImage(EditingFragment.this.getContext());
                handler2.postDelayed(this, 200);
            }
        }, 500);
    }

    public void OnFilterClick(Filter filter, int i) {
        if (this.gpuBitmap != null) {
            this.gpuBitmap = null;
        }
        if (filter.getConstant() != null) {
            if (this.blendBitmap != null && this.effect_selected && this.adjustValue >= 0) {
                this.gpuBitmap = CGENativeLibrary.filterImage_MultipleEffects(Utils.convert(this.blendBitmap.copy(this.blendBitmap.getConfig(), true), Bitmap.Config.ARGB_8888), filter.getConstant(), 1.0f);
            } else if (!this.effect_selected || this.effectBitmap == null) {
                this.gpuBitmap = CGENativeLibrary.filterImage_MultipleEffects(Utils.convert(this.bitmap.copy(this.bitmap.getConfig(), true), Bitmap.Config.ARGB_8888), filter.getConstant(), 1.0f);
            } else {
                this.gpuBitmap = CGENativeLibrary.filterImage_MultipleEffects(Utils.convert(this.effectBitmap.copy(this.effectBitmap.getConfig(), true), Bitmap.Config.ARGB_8888), filter.getConstant(), 1.0f);
            }
            if (!(filter == null || filter.getEffectName() == null)) {
                filterLogEvent(filter.getEffectName());
            }
            this.imageView.setImageBitmap(this.gpuBitmap);
            this.filterConstant = filter.getConstant();
            this.details.setFilterConstant(this.filterConstant);
            this.details.setFilterSelected(this.filter_selected);
            if (this.details.getFilter() != null) {
                this.details.setFilter((GPUImageFilter) null);
            }
            if (this.gpuImageFilter != null) {
                this.gpuImageFilter = null;
            }
            this.filter_selected = true;
            if (this.filterHolder != null && !this.filterHolder.getTwoHolders().isEmpty() && this.sequence != null && this.sequence.contains("enhance")) {
                this.sequence.remove("enhance");
            }
            this.filter_position = i;
            activateUndoButton();
            return;
        }
        if (this.blendBitmap != null && this.effect_selected && this.adjustValue >= 0) {
            this.gpuImage.setImage(this.blendBitmap);
        } else if (!this.effect_selected || this.effectBitmap == null) {
            this.gpuImage.setImage(this.bitmap);
        } else {
            this.gpuImage.setImage(this.effectBitmap);
        }
        this.gpuImage.setFilter(filter.getFilter());
        this.gpuBitmap = Utils.convert(this.gpuImage.getBitmapWithFilterApplied(), Bitmap.Config.RGB_565);
        this.imageView.setImageBitmap(this.gpuBitmap);
        this.filter_selected = true;
        this.gpuImageFilter = filter.getFilter();
        this.details.setFilter(this.gpuImageFilter);
        this.details.setFilterSelected(this.filter_selected);
        if (this.details.getFilterConstant() != null) {
            this.details.setFilterConstant((String) null);
        }
        if (this.filterConstant != null) {
            this.filterConstant = null;
        }
        if (this.filterHolder != null && !this.filterHolder.getTwoHolders().isEmpty() && this.sequence != null && this.sequence.contains("enhance")) {
            this.sequence.remove("enhance");
        }
        this.filter_position = i;
        activateUndoButton();
        Constants.EFFECT_AD_COUNT++;
        checkForAd();
    }

    public void OnRemoveEFilter() {
        if (this.blendBitmap != null && this.effect_selected) {
            this.imageView.setImageBitmap(this.blendBitmap);
        } else if (this.effect_selected) {
            this.imageView.setImageBitmap(this.effectBitmap);
        } else {
            this.imageView.setImageBitmap(this.bitmap);
        }
        this.gpuBitmap = null;
        this.gpuImageFilter = null;
        this.filter_selected = false;
        this.filterConstant = null;
        this.details.setFilter((GPUImageFilter) null);
        this.details.setFilterConstant((String) null);
        this.details.setFilterSelected(this.filter_selected);
        if (!(this.pager == null || this.pagerAdapter == null || this.pagerAdapter.getLstfragment().size() <= 1 || this.pager.getAdapter() == null || this.pager.getAdapter().getCount() <= 1)) {
            ((FilterButtonFragment) this.pager.getAdapter().instantiateItem((ViewGroup) this.pager, 1)).removeChecked();
        }
        showMessage("removefilter", "filter");
        this.filter_position = -1;
        if (!this.effect_selected && this.filterHolder != null && this.filterHolder.getTwoHolders().isEmpty()) {
            deactivateUndoButton();
        }
        if (this.gpuBitmap != null) {
            this.gpuBitmap = null;
        }
        if (this.optionBitmap != null) {
            this.optionBitmap = null;
        }
    }

    public void filterLogEvent(String str) {
        Bundle bundle = new Bundle();
        bundle.putString("filter_func", str);
        FireBaseHelper.getInstance().LogEvent("filter_applied", bundle);
    }

    public boolean isSeekBarVisible() {
        return this.seekBar_view.getVisibility() == View.VISIBLE;
    }

    public void readFilters() {
        if (this.imageFilterGroup.getFilters().size() > 0) {
            this.imageFilterGroup.getFilters().clear();
        }
        for (int i = 0; i < this.filterHolder.getTwoHolders().size(); i++) {
            this.imageFilterGroup.addFilter(getFilter(this.filterHolder.getTwoHolders().get(i).getFilterType()));
        }
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

    public void showMessage(String str, String str2) {
        Log.d(str, str2);
    }

    public void OnEnhanceClick(String str, FilterType filterType, int i, EnhanceFilters enhanceFilters) {
        if (this.filterHolder == null) {
            if (((MainActivity) getActivity()).getFilterHolder() != null) {
                Log.d("filterholder", "mainactivity have");
                this.filterHolder = ((MainActivity) getActivity()).getFilterHolder();
            } else {
                this.filterHolder = new FilterHolder();
            }
        }
        if (!str.equals("Adjust")) {
            this.enhance_filter_name = str;
            this.filter_type = filterType;
            if (this.filterHolder != null) {
                this.indicatorSeekBar.setProgress((float) this.filterHolder.getProgress(filterType));
            } else {
                this.indicatorSeekBar.setProgress(0.0f);
            }
            if (this.filter_name != null) {
                this.filter_name.setText(str);
            } else {
                this.filter_name.setText("");
            }
            if (this.filterHolder != null) {
                this.filter_value.setText(String.valueOf(this.filterHolder.getFilterValue(filterType)) + " %");
            } else {
                this.filter_value.setText("0 %");
            }
            this.seekBar_view.setVisibility(View.VISIBLE);
        } else if (this.adjustValue > 0) {
            this.enhance_filter_name = str;
            this.indicatorSeekBar.setProgress((float) this.adjustValue);
            this.filter_name.setText(str);
            this.filter_value.setText(String.valueOf(this.adjustValue) + " %");
            this.seekBar_view.setVisibility(View.VISIBLE);
            this.imageView.setImageBitmap(this.optionBitmap);
            Log.d("Adjust", "value greater");
            org.opencv.android.Utils.bitmapToMat(this.bitmap.copy(this.bitmap.getConfig(), true), this.original);
            org.opencv.android.Utils.bitmapToMat(this.optionBitmap, this.filtered);
        } else {
            org.opencv.android.Utils.bitmapToMat(this.bitmap.copy(this.bitmap.getConfig(), true), this.original);
            org.opencv.android.Utils.bitmapToMat(this.optionBitmap, this.filtered);
            this.enhance_filter_name = str;
            this.indicatorSeekBar.setProgress(100.0f);
            this.filter_name.setText(str);
            this.filter_value.setText("100 %");
            this.seekBar_view.setVisibility(View.VISIBLE);
            Log.d("Adjust", "value not greater");
        }
        this.enhance_filter_name = str;
        this.filter_type = filterType;
        this.enhance_selected = true;
        this.enhance_position = i;
        activateUndoButton();
        if (this.optionBitmap == null) {
            this.optionBitmap = ((BitmapDrawable) this.imageView.getDrawable()).getBitmap();
        }
        Constants.EFFECT_AD_COUNT++;
        checkForAd();
    }

    public void OnEnhanceRemove() {
        this.seekBar_view.setVisibility(View.GONE);
        this.enhance_selected = false;
        this.filter_type = null;
        this.enhance_filter_name = null;
        if (this.filterHolder != null && this.filterHolder.getTwoHolders().isEmpty() && !this.effect_selected && !this.filter_selected) {
            deactivateUndoButton();
        }
    }

    public void onSeeking(SeekParams seekParams) {
        Log.d("Progress Seek ", String.valueOf(seekParams.progress));
        seekParams.progress = 10;
    }

    public void onStopTrackingTouch(final IndicatorSeekBar indicatorSeekBar2) {
        if (this.gpuImage != null) {
            Enhance(indicatorSeekBar2);
        } else if (this.mContext != null) {
            this.gpuImage = new GPUImage(this.mContext);
            Enhance(indicatorSeekBar2);
        } else if (App.getInstance().getApplicationContext() != null) {
            this.gpuImage = new GPUImage(App.getInstance().getApplicationContext());
            Enhance(indicatorSeekBar2);
        } else {
            final Handler handler2 = new Handler();
            handler2.postDelayed(new Runnable() {
                public void run() {
                    if (EditingFragment.this.getActivity() != null) {
                        GPUImage unused = EditingFragment.this.gpuImage = new GPUImage(EditingFragment.this.getActivity());
                        EditingFragment.this.Enhance(indicatorSeekBar2);
                        handler2.removeCallbacks(this);
                        return;
                    }
                    handler2.postDelayed(this, 50);
                }
            }, 100);
        }
    }

    /* access modifiers changed from: private */
    public void Enhance(IndicatorSeekBar indicatorSeekBar2) {
        char c = 65535;
        if (indicatorSeekBar2 == this.indicatorSeekBar) {
            String str = this.enhance_filter_name;
            switch (str.hashCode()) {
                case -1653340047:
                    if (str.equals("Brightness")) {
                        c = 0;
                        break;
                    }
                    break;
                case -502302942:
                    if (str.equals(ExifInterface.TAG_CONTRAST)) {
                        c = 2;
                        break;
                    }
                    break;
                case 72920:
                    if (str.equals("Hue")) {
                        c = 4;
                        break;
                    }
                    break;
                case 2073735:
                    if (str.equals("Blur")) {
                        c = 6;
                        break;
                    }
                    break;
                case 77128294:
                    if (str.equals("Pixel")) {
                        c = 5;
                        break;
                    }
                    break;
                case 432862497:
                    if (str.equals(ExifInterface.TAG_SHARPNESS)) {
                        c = 1;
                        break;
                    }
                    break;
                case 1762973682:
                    if (str.equals(ExifInterface.TAG_SATURATION)) {
                        c = 3;
                        break;
                    }
                    break;
                case 1956520879:
                    if (str.equals("Adjust")) {
                        c = 7;
                        break;
                    }
                    break;
            }
            switch (c) {
                case 0:
                    if (this.filterHolder == null) {
                        if (((MainActivity) getActivity()).getFilterHolder() != null) {
                            Log.d("filterholder", "mainactivity have");
                            this.filterHolder = ((MainActivity) getActivity()).getFilterHolder();
                        } else {
                            this.filterHolder = new FilterHolder();
                        }
                    }
                    this.filter_Value = ((float) indicatorSeekBar2.getProgress()) / 100.0f;
                    this.filterHolder.addFilter(FilterType.BRIGHTNESS, this.filter_Value);
                    readFilters();
                    this.gpuImage.setImage(this.optionBitmap);
                    this.gpuImage.setFilter(this.imageFilterGroup);
                    this.imageView.setImageBitmap(this.gpuImage.getBitmapWithFilterApplied());
                    EditorLogEvent("Brightness");
                    break;
                case 1:
                    if (this.filterHolder == null) {
                        if (((MainActivity) getActivity()).getFilterHolder() != null) {
                            Log.d("filterholder", "mainactivity have");
                            this.filterHolder = ((MainActivity) getActivity()).getFilterHolder();
                        } else {
                            this.filterHolder = new FilterHolder();
                        }
                    }
                    this.filter_Value = ((float) indicatorSeekBar2.getProgress()) / 100.0f;
                    this.filterHolder.addFilter(FilterType.SHARPNESS, this.filter_Value);
                    readFilters();
                    this.gpuImage.setImage(this.optionBitmap);
                    this.gpuImage.setFilter(this.imageFilterGroup);
                    this.imageView.setImageBitmap(this.gpuImage.getBitmapWithFilterApplied());
                    EditorLogEvent(ExifInterface.TAG_SHARPNESS);
                    break;
                case 2:
                    if (this.filterHolder == null) {
                        if (((MainActivity) getActivity()).getFilterHolder() != null) {
                            Log.d("filterholder", "mainactivity have");
                            this.filterHolder = ((MainActivity) getActivity()).getFilterHolder();
                        } else {
                            this.filterHolder = new FilterHolder();
                        }
                    }
                    this.filter_Value = ((float) indicatorSeekBar2.getProgress()) / 100.0f;
                    this.filterHolder.addFilter(FilterType.CONTRAST, this.filter_Value);
                    readFilters();
                    this.gpuImage.setImage(this.optionBitmap);
                    this.gpuImage.setFilter(this.imageFilterGroup);
                    this.imageView.setImageBitmap(this.gpuImage.getBitmapWithFilterApplied());
                    EditorLogEvent(ExifInterface.TAG_CONTRAST);
                    break;
                case 3:
                    if (this.filterHolder == null) {
                        if (((MainActivity) getActivity()).getFilterHolder() != null) {
                            Log.d("filterholder", "mainactivity have");
                            this.filterHolder = ((MainActivity) getActivity()).getFilterHolder();
                        } else {
                            this.filterHolder = new FilterHolder();
                        }
                    }
                    this.filter_Value = ((float) indicatorSeekBar2.getProgress()) / 100.0f;
                    if (this.filter_Value == 0.0f) {
                        Constants.IS_SATURATION_ZERO = true;
                    } else {
                        Constants.IS_SATURATION_ZERO = false;
                    }
                    this.filterHolder.addFilter(FilterType.SATURATION, this.filter_Value);
                    readFilters();
                    this.gpuImage.setImage(this.optionBitmap);
                    this.gpuImage.setFilter(this.imageFilterGroup);
                    this.imageView.setImageBitmap(this.gpuImage.getBitmapWithFilterApplied());
                    EditorLogEvent(ExifInterface.TAG_SATURATION);
                    break;
                case 4:
                    if (this.filterHolder == null) {
                        if (((MainActivity) getActivity()).getFilterHolder() != null) {
                            Log.d("filterholder", "mainactivity have");
                            this.filterHolder = ((MainActivity) getActivity()).getFilterHolder();
                        } else {
                            this.filterHolder = new FilterHolder();
                        }
                    }
                    this.filter_Value = ((float) indicatorSeekBar2.getProgress()) / 100.0f;
                    this.filterHolder.addFilter(FilterType.HUE, this.filter_Value);
                    readFilters();
                    this.gpuImage.setImage(this.optionBitmap);
                    this.gpuImage.setFilter(this.imageFilterGroup);
                    this.imageView.setImageBitmap(this.gpuImage.getBitmapWithFilterApplied());
                    EditorLogEvent("Hue");
                    break;
                case 5:
                    if (this.filterHolder == null) {
                        if (((MainActivity) getActivity()).getFilterHolder() != null) {
                            Log.d("filterholder", "mainactivity have");
                            this.filterHolder = ((MainActivity) getActivity()).getFilterHolder();
                        } else {
                            this.filterHolder = new FilterHolder();
                        }
                    }
                    this.filter_Value = ((float) indicatorSeekBar2.getProgress()) / 100.0f;
                    this.filterHolder.addFilter(FilterType.PIXEL, this.filter_Value);
                    readFilters();
                    this.gpuImage.setImage(this.optionBitmap);
                    this.gpuImage.setFilter(this.imageFilterGroup);
                    this.imageView.setImageBitmap(this.gpuImage.getBitmapWithFilterApplied());
                    EditorLogEvent("Pixelated");
                    break;
                case 6:
                    this.filter_Value = ((float) indicatorSeekBar2.getProgress()) / 100.0f;
                    this.filterHolder.addFilter(FilterType.BLUR, this.filter_Value);
                    readFilters();
                    this.gpuImage.setImage(this.optionBitmap);
                    this.gpuImage.setFilter(this.imageFilterGroup);
                    this.imageView.setImageBitmap(this.gpuImage.getBitmapWithFilterApplied());
                    EditorLogEvent("Blur");
                    break;
            }
            TextView textView = this.filter_value;
            textView.setText(": " + String.valueOf(indicatorSeekBar2.getProgress()) + "%");
        } else if (indicatorSeekBar2 == this.seekbar && this.effectBitmap != null && this.bitmap != null) {
            org.opencv.android.Utils.bitmapToMat(this.bitmap.copy(this.bitmap.getConfig(), true), this.original);
            org.opencv.android.Utils.bitmapToMat(this.effectBitmap.copy(this.effectBitmap.getConfig(), true), this.filtered);
            this.blendBitmap = this.bitmap.copy(this.bitmap.getConfig(), true);
            this.blendBitmap = Utils.blendBitmaps(this.original, this.filtered, this.finalmat, this.blendBitmap, indicatorSeekBar2.getProgress());
            this.imageView.setImageBitmap(this.blendBitmap);
            this.adjustValue = indicatorSeekBar2.getProgress();
            if (this.gpuBitmap != null) {
                this.gpuBitmap = null;
            }
            if (this.optionBitmap != null) {
                this.optionBitmap = null;
            }
            if (this.filterHolder != null && !this.filterHolder.getTwoHolders().isEmpty()) {
                if (this.sequence != null && this.sequence.contains("enhance")) {
                    this.sequence.remove("enhance");
                }
                this.mAdapter.setItemIndex(-1);
                this.mAdapter.notifyDataSetChanged();
            }
            this.enhance_selected = false;
        }
    }

    public void EditorLogEvent(String str) {
        Bundle bundle = new Bundle();
        bundle.putString("editor_func", str);
        FireBaseHelper.getInstance().LogEvent("edit_func_applied", bundle);
    }

    public GPUImageFilterGroup filterGroup() {
        GPUImageFilterGroup gPUImageFilterGroup = new GPUImageFilterGroup();
        for (int i = 0; i < this.filterHolder.getTwoHolders().size(); i++) {
            gPUImageFilterGroup.addFilter(getFilter(this.filterHolder.getTwoHolders().get(i).getFilterType()));
        }
        return gPUImageFilterGroup;
    }

    public void onBannerLoaded(final MoPubView moPubView2) {
        Log.d("Banner", "Loaded");
        if (!Constants.REMOVE_ADS) {
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.bottomLayoutContainer.getLayoutParams();
            layoutParams.height = -2;
            layoutParams.width = -2;
            this.bottomLayoutContainer.setLayoutParams(layoutParams);
            this.bannerLoaded = true;
            return;
        }
        final Handler handler2 = new Handler();
        handler2.postDelayed(new Runnable() {
            public void run() {
                if (EditingFragment.this.getActivity() != null) {
                    if (!SharedPreferencesManager.HasKey(EditingFragment.this.getActivity(), Constants.APP_RATED)) {
                        moPubView2.destroy();
                        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) EditingFragment.this.bottomLayoutContainer.getLayoutParams();
                        layoutParams.height = EditingFragment.this.dpToPx(90, false);
                        layoutParams.width = -1;
                        EditingFragment.this.bottomLayoutContainer.setLayoutParams(layoutParams);
                        EditingFragment.this.initializeBottomRateView();
                    } else {
                        RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) EditingFragment.this.bottomLayoutContainer.getLayoutParams();
                        layoutParams2.height = EditingFragment.this.dpToPx(0, true);
                        layoutParams2.width = -1;
                        EditingFragment.this.bottomLayoutContainer.setLayoutParams(layoutParams2);
                        boolean unused = EditingFragment.this.bannerLoaded = false;
                    }
                    handler2.removeCallbacks(this);
                    return;
                }
                handler2.postDelayed(this, 50);
            }
        }, 50);
    }

    public void onBannerFailed(MoPubView moPubView2, MoPubErrorCode moPubErrorCode) {
        this.bannerLoaded = false;
        Log.d("Banner", AnalyticsEvents.PARAMETER_DIALOG_OUTCOME_VALUE_FAILED);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.bottomLayoutContainer.getLayoutParams();
        layoutParams.height = dpToPx(0, true);
        layoutParams.width = -1;
        this.bottomLayoutContainer.setLayoutParams(layoutParams);
    }

    public void OnWaitScreenGone() {
        Log.d("FragmentEditing Con", "StartUpTask");
        new StartUpLoadingTask(getActivity()).execute(new Void[0]);
    }

    /* renamed from: ui.EditingFragment$FragmentStateResume */
    public class FragmentStateResume extends AsyncTask<Void, Void, Bitmap> {
        private String _cache_name;

        /* renamed from: b */
        Bitmap f6041b = null;
        public Bitmap bitmap;
        Dialog dialog;
        private EditingFragmentDetails editingFragmentDetails;
        public Effect effect;
        private String effectName;
        private GPUImageFilter filter;
        private FilterHolder filterHolder;
        private GPUImage gpuImage;
        private BitmapCache mbitmapCache;
        private List<String> seq = new ArrayList();

        public FragmentStateResume(Bitmap bitmap2, Effect effect2, EditingFragmentDetails editingFragmentDetails2, GPUImage gPUImage, Dialog dialog2) {
            this.bitmap = bitmap2;
            this.effect = effect2;
            if (editingFragmentDetails2.getEffectName() != null) {
                this.effectName = editingFragmentDetails2.getEffectName();
            }
            if (gPUImage != null) {
                this.gpuImage = gPUImage;
            }
            if (editingFragmentDetails2 != null) {
                this.editingFragmentDetails = editingFragmentDetails2;
                if (this.editingFragmentDetails.getSeq() != null && !this.editingFragmentDetails.getSeq().isEmpty()) {
                    this.seq = this.editingFragmentDetails.getSeq();
                    for (int i = 0; i < this.seq.size(); i++) {
                        Log.d("fragmentseq", this.seq.get(i));
                    }
                }
                if (this.editingFragmentDetails.getFilterHolder() != null) {
                    this.filterHolder = this.editingFragmentDetails.getFilterHolder();
                }
                if (this.editingFragmentDetails.getFilter() != null) {
                    this.filter = this.editingFragmentDetails.getFilter();
                }
                if (dialog2 != null) {
                    this.dialog = dialog2;
                }
            }
            EditingFragment.this.effectLoadingDialog = new Dialog(EditingFragment.this.getActivity(), R.style.EffectDialogTheme);
            EditingFragment.this.effectLoadingDialog.requestWindowFeature(1);
            EditingFragment.this.effectLoadingDialog.setContentView(R.layout.effect_loading_dialog);
            ((TextView) EditingFragment.this.effectLoadingDialog.findViewById(R.id.estimate_text)).setText("Loading Screen");
            EditingFragment.this.effectLoadingDialog.setCancelable(false);
            EditingFragment.this.effectLoadingDialog.show();
            effect2.setOriginal_image(false);
            Log.d("FragmentState", "Constructor");
        }

        /* access modifiers changed from: protected */
        public Bitmap doInBackground(Void... voidArr) {
            Log.d("FragmentState", "doinBackground");
            if (this.seq == null || this.seq.size() <= 0) {
                this.f6041b = this.bitmap;
            } else {
                for (int i = 0; i < this.seq.size(); i++) {
                    if (this.seq.get(i).equals("effect")) {
                        this.f6041b = this.effect.getEffect(this.effectName, this.bitmap);
                        Log.d("FragmentState", "effectInstance");
                        Bitmap unused = EditingFragment.this.effectBitmap = this.f6041b;
                        Bitmap unused2 = EditingFragment.this.optionBitmap = this.f6041b;
                        boolean unused3 = EditingFragment.this.effect_button_checked = true;
                    }
                    if (this.seq.get(i).equals("adjust") && this.f6041b != null) {
                        org.opencv.android.Utils.bitmapToMat(this.bitmap.copy(this.bitmap.getConfig(), true), EditingFragment.this.original);
                        org.opencv.android.Utils.bitmapToMat(this.f6041b.copy(this.f6041b.getConfig(), true), EditingFragment.this.filtered);
                        this.f6041b = this.bitmap.copy(this.bitmap.getConfig(), true);
                        this.f6041b = Utils.blendBitmaps(EditingFragment.this.original, EditingFragment.this.filtered, EditingFragment.this.finalmat, this.f6041b, EditingFragment.this.adjustValue);
                        Bitmap unused4 = EditingFragment.this.blendBitmap = this.f6041b;
                    }
                    if (this.seq.get(i).equals("filter")) {
                        if (this.f6041b == null) {
                            this.f6041b = this.bitmap;
                            Log.d("FragmentState", "enhance_filter_name null bitmap");
                        }
                        if (this.editingFragmentDetails.getFilterConstant() != null) {
                            this.f6041b = CGENativeLibrary.filterImage_MultipleEffects(Utils.convert(this.f6041b.copy(this.f6041b.getConfig(), true), Bitmap.Config.ARGB_8888), this.editingFragmentDetails.getFilterConstant(), 1.0f);
                            this.f6041b = Utils.convert(this.f6041b, Bitmap.Config.RGB_565);
                        } else if (this.editingFragmentDetails.getFilter() != null) {
                            this.gpuImage.setImage(this.f6041b);
                            this.gpuImage.setFilter(this.filter);
                            this.f6041b = this.gpuImage.getBitmapWithFilterApplied();
                            Bitmap unused5 = EditingFragment.this.optionBitmap = this.f6041b;
                        }
                    }
                    if (this.seq.get(i).equals("enhance")) {
                        if (this.f6041b == null) {
                            this.f6041b = this.bitmap;
                        }
                        this.gpuImage.setImage(this.f6041b);
                        this.gpuImage.setFilter(this.filterHolder.readFilters());
                        this.f6041b = this.gpuImage.getBitmapWithFilterApplied();
                        Log.d("FragmentState", "enhance");
                    }
                }
            }
            return this.f6041b;
        }

        /* access modifiers changed from: protected */
        public void onPreExecute() {
            super.onPreExecute();
            Log.d("FragmentState", "PreExecute");
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Bitmap bitmap2) {
            super.onPostExecute(bitmap2);
            if (this.dialog != null && this.dialog.isShowing()) {
                this.dialog.dismiss();
            }
            if (EditingFragment.this.effectLoadingDialog != null && EditingFragment.this.effectLoadingDialog.isShowing()) {
                EditingFragment.this.effectLoadingDialog.dismiss();
            }
            boolean unused = EditingFragment.this.freshStart = false;
            if (this.editingFragmentDetails.isEditorButtonChecked()) {
                EditingFragment.this.details.setEditorButtonChecked(false);
                EditingFragment.this.pager.setVisibility(View.GONE);
                EditingFragment.this.optionLayout.setVisibility(View.VISIBLE);
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) EditingFragment.this.imageLayout.getLayoutParams();
                layoutParams.addRule(2, R.id.options);
                EditingFragment.this.imageLayout.setLayoutParams(layoutParams);
                RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) EditingFragment.this.restart.getLayoutParams();
                layoutParams2.addRule(2, R.id.seekbar_view);
                EditingFragment.this.restart.setLayoutParams(layoutParams2);
                Log.d("FragmentState", "Editor");
                if (EditingFragment.this.enhance_selected && EditingFragment.this.enhance_position >= 0) {
                    ((EnhanceFilters) EditingFragment.this.editor_items.get(EditingFragment.this.enhance_position)).setChecked(true);
                    EditingFragment.this.mAdapter.setItemIndex(EditingFragment.this.enhance_position);
                    EditingFragment.this.mAdapter.notifyDataSetChanged();
                    EditingFragment.this.indicatorSeekBar.setProgress((float) this.filterHolder.getProgress(EditingFragment.this.filter_type));
                    EditingFragment.this.filter_name.setText(EditingFragment.this.enhance_filter_name);
                    TextView access$3600 = EditingFragment.this.filter_value;
                    access$3600.setText(String.valueOf(this.filterHolder.getFilterValue(EditingFragment.this.filter_type)) + " %");
                    EditingFragment.this.seekBar_view.setVisibility(View.VISIBLE);
                }
                EditingFragment.this.disableButtons();
                EditingFragment.this.activateButton(EditingFragment.this.editorButton);
                EditingFragment.this.deactivateButton(EditingFragment.this.filterButton);
                EditingFragment.this.deactivateButton(EditingFragment.this.effectButton);
                EditingFragment.this.editor_button_activated = true;
                EditingFragment.this.imageView.setImageBitmap(this.f6041b);
            } else {
                EditingFragment.this.pager.setVisibility(View.VISIBLE);
                if (this.editingFragmentDetails.isFilterButtonChecked()) {
                    EditingFragment.this.pager.setCurrentItem(1);
                    EditingFragment.this.details.setFilterButtonChecked(false);
                    if (!(EditingFragment.this.pager == null || EditingFragment.this.pagerAdapter == null || EditingFragment.this.pagerAdapter.getLstfragment().size() <= 0 || EditingFragment.this.pager.getAdapter() == null || EditingFragment.this.pager.getAdapter().getCount() <= 0)) {
                        FilterButtonFragment filterButtonFragment = (FilterButtonFragment) EditingFragment.this.pager.getAdapter().instantiateItem((ViewGroup) EditingFragment.this.pager, 1);
                        if (EditingFragment.this.optionBitmap != null) {
                            filterButtonFragment.changeBitmap(EditingFragment.resize(EditingFragment.this.optionBitmap, 96, 96));
                        } else {
                            filterButtonFragment.changeBitmap(EditingFragment.resize(this.f6041b, 96, 96));
                        }
                        if (EditingFragment.this.filter_position >= 0) {
                            filterButtonFragment.setEffectIndex(EditingFragment.this.filter_position);
                            if (!EditingFragment.this.effect_selected || EditingFragment.this.sequence == null || !EditingFragment.this.sequence.contains("effectInstance") || EditingFragment.this.optionBitmap == null) {
                                EditingFragment.this.imageView.setImageBitmap(this.f6041b);
                            } else {
                                EditingFragment.this.imageView.setImageBitmap(EditingFragment.this.effectBitmap);
                                EditingFragment.this.ApplyFilter(false);
                            }
                        } else {
                            EditingFragment.this.imageView.setImageBitmap(this.f6041b);
                        }
                    }
                    EditingFragment.this.disableButtons();
                    EditingFragment.this.activateButton(EditingFragment.this.filterButton);
                    EditingFragment.this.deactivateButton(EditingFragment.this.effectButton);
                    EditingFragment.this.deactivateButton(EditingFragment.this.editorButton);
                    EditingFragment.this.filter_button_activated = true;
                }
                if (this.editingFragmentDetails.isEffectButtonChecked()) {
                    EditingFragment.this.pager.setCurrentItem(0);
                    EditingFragment.this.details.setEffectButtonChecked(false);
                    if (this.seq.contains("effectInstance")) {
                        EditingFragment.this.showSeekbar(false);
                    }
                    EditingFragment.this.disableButtons();
                    EditingFragment.this.activateButton(EditingFragment.this.effectButton);
                    EditingFragment.this.deactivateButton(EditingFragment.this.filterButton);
                    EditingFragment.this.deactivateButton(EditingFragment.this.editorButton);
                    EditingFragment.this.effect_button_activated = true;
                    if (EditingFragment.this.effectBitmap == null || !EditingFragment.this.effect_selected) {
                        EditingFragment.this.imageView.setImageBitmap(this.f6041b);
                    } else if (EditingFragment.this.blendBitmap == null || EditingFragment.this.adjustValue <= 0) {
                        EditingFragment.this.imageView.setImageBitmap(EditingFragment.this.effectBitmap);
                    } else {
                        EditingFragment.this.imageView.setImageBitmap(EditingFragment.this.blendBitmap);
                    }
                }
                RelativeLayout.LayoutParams layoutParams3 = (RelativeLayout.LayoutParams) EditingFragment.this.restart.getLayoutParams();
                layoutParams3.addRule(2, R.id.seek_second);
                EditingFragment.this.restart.setLayoutParams(layoutParams3);
            }
            if (EditingFragment.this.details.getSeq() != null) {
                EditingFragment.this.details.getSeq().clear();
            }
            if (EditingFragment.this.sequence != null && !EditingFragment.this.sequence.isEmpty()) {
                EditingFragment.this.activateUndoButton();
            }
        }

        /* access modifiers changed from: protected */
        public void onProgressUpdate(Void... voidArr) {
            super.onProgressUpdate(voidArr);
            Log.d("FragmentState", "ProgressUpdate");
        }
    }

    /* renamed from: ui.EditingFragment$StartUpLoadingTask */
    private class StartUpLoadingTask extends AsyncTask<Void, Void, Void> {

        /* renamed from: re */
        Mat f6042re;

        public StartUpLoadingTask(Context context) {
            EditingFragment.this.loadingDialog = new Dialog(context);
            EditingFragment.this.loadingDialog.requestWindowFeature(1);
            EditingFragment.this.loadingDialog.setContentView(R.layout.loading_dialog);
            EditingFragment.this.loadingDialog.setCancelable(false);
            EditingFragment.this.loadingText = (TextView) EditingFragment.this.loadingDialog.findViewById(R.id.text);
            EditingFragment.this.percentage = (TextView) EditingFragment.this.loadingDialog.findViewById(R.id.percentage);
        }

        /* access modifiers changed from: protected */
        public void onPreExecute() {
            super.onPreExecute();
            EditingFragment.this.loadingDialog.show();
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Void voidR) {
            super.onPostExecute(voidR);
            EditingFragment.this.loadingDialog.dismiss();
            EditingFragment.this.initializeContent();
            ((MainActivity) EditingFragment.this.getActivity()).checkRateDialog();
        }

        /* access modifiers changed from: protected */
        public Void doInBackground(Void... voidArr) {
            EditingFragment.this.updateLoadingProgress("Loading Image", 25);
            EditingFragment.this.bitmapCache.addBitmapToCache(Constants.ORIGINAL_IMAGE, EditingFragment.this.bitmap);
            EditingFragment.this.updateLoadingProgress("Loading Textures", 50);
            if (EditingFragment.this.bitmap != null) {
                EditingFragment.this.effect.loadImages(EditingFragment.this.bitmap.getWidth(), EditingFragment.this.bitmap.getHeight());
            }
            if (EditingFragment.this.bitmap != null) {
                Bitmap unused = EditingFragment.this.resize = EditingFragment.resize(EditingFragment.this.bitmap, 96, 96);
            }
            EditingFragment.this.updateLoadingProgress("Loading UI", 75);
            EditingFragment.this.updateLoadingProgress("Loading Complete", 100);
            return null;
        }
    }

    /* access modifiers changed from: private */
    public void updateLoadingProgress(final String str, final int i) {
        if (getActivity() != null) {
            getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    EditingFragment.this.loadingText.setText(str);
                    TextView textView = EditingFragment.this.percentage;
                    textView.setText(i + "%");
                }
            });
        }
    }

    /* renamed from: ui.EditingFragment$EffectApplyingTask */
    private class EffectApplyingTask extends AsyncTask<Void, Void, Void> {
        String effectName = null;

        public EffectApplyingTask(Context context, String str) {
            this.effectName = str;
            EditingFragment.this.effectLoadingDialog = new Dialog(context, R.style.EffectDialogTheme);
            EditingFragment.this.effectLoadingDialog.requestWindowFeature(1);
            EditingFragment.this.effectLoadingDialog.setContentView(R.layout.effect_loading_dialog);
            EditingFragment.this.effectLoadingDialog.setCancelable(false);
            EditingFragment.this.effectLoadingDialog.show();
        }

        /* access modifiers changed from: protected */
        public void onPreExecute() {
            super.onPreExecute();
            if (EditingFragment.this.effectBitmap != null) {
                Bitmap unused = EditingFragment.this.effectBitmap = null;
            }
            if (EditingFragment.this.blendBitmap != null) {
                Bitmap unused2 = EditingFragment.this.blendBitmap = null;
            }
            if (EditingFragment.this.gpuBitmap != null) {
                Bitmap unused3 = EditingFragment.this.gpuBitmap = null;
            }
            if (EditingFragment.this.optionBitmap != null) {
                Bitmap unused4 = EditingFragment.this.optionBitmap = null;
            }
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Void voidR) {
            super.onPostExecute(voidR);
            if (EditingFragment.this.effectLoadingDialog != null && EditingFragment.this.effectLoadingDialog.isShowing()) {
                EditingFragment.this.effectLoadingDialog.dismiss();
            }
            EditingFragment.this.imageView.setImageBitmap(EditingFragment.this.effectBitmap);
            if (!(EditingFragment.this.pager == null || EditingFragment.this.pagerAdapter == null || EditingFragment.this.pagerAdapter.getCount() <= 1 || EditingFragment.this.pager.getAdapter() == null || EditingFragment.this.pager.getAdapter().getCount() <= 1 || EditingFragment.this.effectBitmap == null)) {
                FilterButtonFragment filterButtonFragment = (FilterButtonFragment) EditingFragment.this.pager.getAdapter().instantiateItem((ViewGroup) EditingFragment.this.pager, 1);
                filterButtonFragment.changeBitmap(EditingFragment.resize(EditingFragment.this.effectBitmap, 96, 96));
                filterButtonFragment.resetList();
            }
            EditingFragment.this.showSeekbar(true);
            if (EditingFragment.this.hashMap.get(this.effectName) == null) {
                EditingFragment.this.hashMap.put(this.effectName, true);
                EditingFragment.this.effectApplied(EditingFragment.this.effect.getEffectName());
                Bundle bundle = new Bundle();
                bundle.putString("AdNetwork", "Genuine_Effect_AD_Count");
                FireBaseHelper.getInstance().LogEvent("Ad_Analytics", bundle);
            }
            EditingFragment.this.LogEvent(this.effectName);
            Constants.EFFECT_AD_COUNT++;
            EditingFragment.this.checkForAd();
        }

        /* access modifiers changed from: protected */
        public Void doInBackground(Void... voidArr) {
            if (EditingFragment.this.bitmap != null) {
                Bitmap unused = EditingFragment.this.effectBitmap = Utils.convert(EditingFragment.this.effect.getEffect(this.effectName, EditingFragment.this.bitmap.copy(EditingFragment.this.bitmap.getConfig(), true)), Bitmap.Config.RGB_565);
            } else if (EditingFragment.this.bitmapCache != null && EditingFragment.this.bitmapCache.getCacheBitmap(Constants.ORIGINAL_IMAGE) != null) {
                Bitmap unused2 = EditingFragment.this.bitmap = EditingFragment.this.bitmapCache.getCacheBitmap(Constants.ORIGINAL_IMAGE);
                Bitmap unused3 = EditingFragment.this.effectBitmap = Utils.convert(EditingFragment.this.effect.getEffect(this.effectName, EditingFragment.this.bitmap.copy(EditingFragment.this.bitmap.getConfig(), true)), Bitmap.Config.RGB_565);
            } else if (EditingFragment.this.fileUri != null) {
                Bitmap unused4 = EditingFragment.this.bitmap = BitmapFactory.decodeFile(ImageFilePath.getPath(EditingFragment.this.getActivity(), EditingFragment.this.fileUri));
                Bitmap unused5 = EditingFragment.this.effectBitmap = Utils.convert(EditingFragment.this.effect.getEffect(this.effectName, EditingFragment.this.bitmap.copy(EditingFragment.this.bitmap.getConfig(), true)), Bitmap.Config.RGB_565);
            } else if (Constants.FILE_URI != null) {
                Bitmap unused6 = EditingFragment.this.bitmap = BitmapFactory.decodeFile(ImageFilePath.getPath(EditingFragment.this.getActivity(), Constants.FILE_URI));
                Bitmap unused7 = EditingFragment.this.effectBitmap = Utils.convert(EditingFragment.this.effect.getEffect(this.effectName, EditingFragment.this.bitmap.copy(EditingFragment.this.bitmap.getConfig(), true)), Bitmap.Config.RGB_565);
            }
            EditingFragment.this.showMessage("effecttask", "executing");
            return null;
        }
    }

    /* access modifiers changed from: private */
    public static Bitmap resize(Bitmap bitmap2, int i, int i2) {
        if (i2 <= 0 || i <= 0) {
            return bitmap2;
        }
        float width = ((float) bitmap2.getWidth()) / ((float) bitmap2.getHeight());
        float f = (float) i;
        float f2 = (float) i2;
        if (f / f2 > 1.0f) {
            i = (int) (f2 * width);
        } else {
            i2 = (int) (f / width);
        }
        return Bitmap.createScaledBitmap(bitmap2, i, i2, true);
    }

    public void LogEvent(String str) {
        Bundle bundle = new Bundle();
        bundle.putString("effect_name", str);
        FireBaseHelper.getInstance().LogEvent("effect_applied", bundle);
    }

    public void effectApplied(String str) {
        if (this.layout_inflater != null) {
            View inflate = this.layout_inflater.inflate(R.layout.custom_toast, (ViewGroup) null, false);
           // Log.d("Check", TtmlNode.TAG_LAYOUT);
            ((TextView) inflate.findViewById(R.id.text)).setText(str);
            final Toast toast = new Toast(getActivity());
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setGravity(17, 0, 0);
            toast.setView(inflate);
            Log.d("Check", "toast layout");
            toast.show();
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    toast.cancel();
                }
            }, 800);
        }
    }

    public void onLowMemory() {
        super.onLowMemory();
        Log.d("LowMemory", "Editing Low");
    }

    public void showSeekbar(boolean z) {
        this.seekbar.setMax(100.0f);
        if (z) {
            this.seekbar.setProgress(100.0f);
            this.adjustValue = -1;
        } else if (this.adjustValue >= 100 || this.adjustValue <= 0) {
            this.seekbar.setProgress(100.0f);
        } else {
            this.seekbar.setProgress((float) this.adjustValue);
        }
        this.seekbar.setVisibility(View.VISIBLE);
    }

    public void hideSeekbar() {
        this.seekbar.setVisibility(View.INVISIBLE);
    }

    public void restart() {
        deactivateUndoButton();
        ((EffectButtonFragment) this.pager.getAdapter().instantiateItem((ViewGroup) this.pager, 0)).reset();
        FilterButtonFragment filterButtonFragment2 = (FilterButtonFragment) this.pager.getAdapter().instantiateItem((ViewGroup) this.pager, 1);
        filterButtonFragment2.changeBitmap(resize(this.bitmap, 96, 96));
        filterButtonFragment2.removeChecked();
        filterButtonFragment2.resetList();
        this.mAdapter.UncheckAll();
        this.mAdapter.notifyDataSetChanged();
        this.adjustValue = -1;
        this.filter_position = -1;
        this.enhance_position = -1;
        this.blendBitmap = null;
        this.effectBitmap = null;
        this.optionBitmap = null;
        this.gpuBitmap = null;
        disableButtons();
        activateButton(this.effectButton);
        deactivateButton(this.filterButton);
        deactivateButton(this.editorButton);
        this.effect_button_activated = true;
        if (this.details.getSeq() != null) {
            this.details.getSeq().clear();
        }
        this.enhance_selected = false;
        this.filter_type = null;
        this.enhance_filter_name = null;
        if (this.sequence != null) {
            this.sequence.clear();
        }
        if (this.filterHolder != null && !this.filterHolder.getTwoHolders().isEmpty()) {
            this.filterHolder.getTwoHolders().clear();
        }
        this.gpuImageFilter = null;
        this.filter_selected = false;
        this.details.setFilter((GPUImageFilter) null);
        this.details.setFilterSelected(this.filter_selected);
        this.details.setEditorButtonChecked(false);
        this.details.setEffectButtonChecked(false);
        this.details.setFilterButtonChecked(false);
        this.effect_selected = false;
        this.effect_name = null;
        this.details.setEffectSelected(this.effect_selected);
        this.details.setEffectPosition(-1);
        this.details.setEffectName((String) null);
        if (this.optionLayout.getVisibility() == View.VISIBLE) {
            this.optionLayout.setVisibility(View.GONE);
        }
        if (this.seekBar_view.getVisibility() == View.VISIBLE) {
            this.seekBar_view.setVisibility(View.GONE);
        }
        if (this.seekbar.getVisibility() == View.VISIBLE) {
            this.seekbar.setVisibility(View.GONE);
        }
        this.pager.setVisibility(View.VISIBLE);
        this.pager.setCurrentItem(0);
        this.imageView.setImageBitmap(this.bitmap);
    }

    private void initializeResetDialogTut() {
        final Dialog dialog = new Dialog(getActivity(), R.style.VideAdDialogTheme);
        dialog.getWindow().setDimAmount(0.0f);
        dialog.setContentView(R.layout.weekly_reward_layout);
        Button button = (Button) dialog.findViewById(R.id.yes);
        ((TextView) dialog.findViewById(R.id.title_text)).setText("Function");
        ((TextView) dialog.findViewById(R.id.reward_text)).setText(getString(R.string.restart_button_function));
        button.setText(getString(R.string.ok));
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.setCancelable(true);
        dialog.show();
    }

    private Bitmap changeVForAllPixels(Bitmap bitmap2, float f) {
        long currentTimeMillis = System.currentTimeMillis();
        int width = bitmap2.getWidth();
        int height = bitmap2.getHeight();
        int[] iArr = new int[(width * height)];
        float[] fArr = new float[3];
        bitmap2.getPixels(iArr, 0, width, 0, 0, width, height);
        for (int i = 0; i < iArr.length; i++) {
            Color.colorToHSV(iArr[i], fArr);
            fArr[2] = f;
            iArr[i] = Color.HSVToColor(fArr);
        }
        Log.d("timetaken", String.valueOf((System.currentTimeMillis() - currentTimeMillis) / 1000) + " seconds ");
        return Bitmap.createBitmap(iArr, width, height, Bitmap.Config.ARGB_8888);
    }

    /* access modifiers changed from: private */
    public void checkForAd() {
        if (Constants.REMOVE_ADS) {
            return;
        }
        if (Constants.AD_SHOWN_SPACE == null || !Constants.AD_SHOWN_SPACE.equals("EFFECT_EVENT")) {
            if (Constants.EFFECT_INTERSTITIAL && Constants.EFFECT_AD_COUNT >= Constants.EFFECT_AD_COUNT_THRESHOLD) {
                Bundle bundle = new Bundle();
                bundle.putString("AdNetwork", "Effect_AD_Count");
                FireBaseHelper.getInstance().LogEvent("Ad_Analytics", bundle);
                Constants.EFFECT_AD_COUNT = 0;
                if (!Constants.FULL_SCREEN_Effect_NATIVE_FIRST) {
                    if (AdUtil.getInstance().isEffectInterstitialLoaded()) {
                        AdUtil.getInstance().ShowEffectInterstitial();
                    } /*else if (AdUtil.getInstance().isEffectNativeLoaded() && AdUtil.getInstance().getEffect_fb_native() != null) {
                        ((MainActivity) getActivity()).showFloatingFBNativeAd(AdUtil.getInstance().getEffect_fb_native(), false);
                    }
                } else if (AdUtil.getInstance().isEffectNativeLoaded()) {
                    if (AdUtil.getInstance().getEffect_fb_native() != null) {
                        ((MainActivity) getActivity()).showFloatingFBNativeAd(AdUtil.getInstance().getEffect_fb_native(), false);
                    }*/
                } else if (AdUtil.getInstance().isEffectInterstitialLoaded()) {
                    AdUtil.getInstance().ShowEffectInterstitial();
                }
            }
        } else if (Constants.EFFECT_AD_COUNT >= Constants.EFFECT_AD_COUNT_THRESHOLD) {
            Constants.EFFECT_AD_COUNT = 0;
            if (AdUtil.getInstance().isStartUpAdAvailable()) {
                AdUtil.getInstance().ShowStartUpInterstitial();
            }
        }
    }
}