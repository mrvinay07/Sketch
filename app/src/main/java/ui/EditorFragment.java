package ui;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.exifinterface.media.ExifInterface;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Registry;
import com.facebook.appevents.AppEventsConstants;
import com.google.common.net.HttpHeaders;
import com.teamvinay.sketch.EffectFilterTask;
import com.teamvinay.sketch.FilterHolder;
import com.teamvinay.sketch.FilterType;
import com.teamvinay.sketch.MainActivity;
import com.teamvinay.sketch.PhotoActivity;
import com.llollox.androidtoggleswitch.widgets.ToggleSwitch;
import com.teamvinay.sketch.R;
import com.warkiz.widget.IndicatorSeekBar;
import com.warkiz.widget.OnSeekChangeListener;
import com.warkiz.widget.SeekParams;
import helper.Constants;
import helper.EditorButtonListener;
import helper.EditorOptionsAdapter;
import helper.EnhanceFilters;
import helper.ExportDetails;
import helper.FilterNames;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;
import listeners.ResetButtonListener;
import listeners.ResetListener;
import listeners.TaskCompleteListener;
import org.wysaid.nativePort.CGENativeLibrary;
import jp.co.cyberagent.android.gpuimage.GPUImage;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageBrightnessFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageContrastFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageCrosshatchFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageFilterGroup;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageGaussianBlurFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageHueFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImagePixelationFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageSaturationFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageSharpenFilter;
import util.AdUtil;
import util.FilterValuesHolder;
import util.FireBaseHelper;
import util.NativeAdUtil;

/* renamed from: ui.EditorFragment */
public class EditorFragment extends Fragment implements EditorButtonListener, OnSeekChangeListener, ToggleSwitch.OnChangeListener, TaskCompleteListener, ResetListener, View.OnClickListener {
    private int adjustValue = -1;
    private Bitmap bitmap;
    private Bitmap blendBitmap;
    private boolean editorClicked = false;
    private List<Object> editor_items = new ArrayList();
    private Dialog effectLoadingDialog;
    private String effectName = null;
    private boolean effectSelected = false;
    private String enhance_filter_name = null;
    private FilterHolder filterHolder;
    LinkedHashMap<String, String> filterMap = new LinkedHashMap<>();
    String filterName = null;
    FilterType filterType;
    List<TextView> filterValueList = new ArrayList();
    FilterValuesHolder filterValuesHolder;
    List<FilterValuesHolder> filterValuesHolderList = new ArrayList();
    private float filter_Value;
    private TextView filter_name;
    private TextView filter_value;

    /* renamed from: filters  reason: collision with root package name */
    List<String> f6092filters = new ArrayList();
    private boolean freshStart = true;
    private GPUImage gpuImage;
    private GPUImageFilterGroup imageFilterGroup;
    private ImageView imageView;
    RelativeLayout layout;
    private TextView left_side_value;
    private Dialog loadingDialog;
    private EditorOptionsAdapter mAdapter = null;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView recyclerView;
    private ResetButtonListener resetButtonListener;
    private TextView right_side_value;
    private RelativeLayout screenLayout;
    private IndicatorSeekBar seekBar;
    List<IndicatorSeekBar> seekBarList = new ArrayList();
    private RelativeLayout seekBarView;
    Bitmap temp;
    String template;
    private int toggleId = 0;
    private RelativeLayout toggleLayout;
    private ToggleSwitch toggleSwitch;

    public void onStartTrackingTouch(IndicatorSeekBar indicatorSeekBar) {
    }

    public void setResetButtonListener(ResetButtonListener resetButtonListener2) {
        this.resetButtonListener = resetButtonListener2;
    }

    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
    }

    @Nullable
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.editor_fragment, viewGroup, false);
        this.recyclerView = (RecyclerView) inflate.findViewById(R.id.my_recycler_view);
        this.imageView = (ImageView) inflate.findViewById(R.id.gpuimage);
        this.seekBarView = (RelativeLayout) inflate.findViewById(R.id.seekbar_view);
        this.screenLayout = (RelativeLayout) inflate.findViewById(R.id.screen_layout);
        this.toggleLayout = (RelativeLayout) inflate.findViewById(R.id.toggle_layout);
        this.seekBar = (IndicatorSeekBar) inflate.findViewById(R.id.seek);
        this.toggleLayout = (RelativeLayout) inflate.findViewById(R.id.toggle_layout);
        this.filter_name = (TextView) inflate.findViewById(R.id.filterName);
        this.filter_value = (TextView) inflate.findViewById(R.id.seek_progress_value);
        this.left_side_value = (TextView) inflate.findViewById(R.id.leftSide);
        this.right_side_value = (TextView) inflate.findViewById(R.id.rightSide);
        this.toggleSwitch = (ToggleSwitch) inflate.findViewById(R.id.toggle_switch);
        this.filterHolder = new FilterHolder();
        this.toggleSwitch.setOnChangeListener(this);
        this.toggleSwitch.setCheckedPosition(0);
        this.seekBar.setOnSeekChangeListener(this);
        this.imageView.setOnClickListener(this);
        this.filterValuesHolder = new FilterValuesHolder();
        this.gpuImage = new GPUImage(getActivity());
        this.imageFilterGroup = new GPUImageFilterGroup();
        if (this.editor_items.isEmpty()) {
            this.editor_items.add(new EnhanceFilters(R.drawable.baseline_brightness_6_24, FilterNames.BRIGHT, FilterType.BRIGHTNESS));
            this.editor_items.add(new EnhanceFilters(R.drawable.baseline_details_24, FilterNames.SHARP, FilterType.SHARPNESS));
            this.editor_items.add(new EnhanceFilters(R.drawable.ic_saturation_icon, FilterNames.SATURATE, FilterType.SATURATION));
            this.editor_items.add(new EnhanceFilters(R.drawable.contrast_box, FilterNames.CONTRAST, FilterType.CONTRAST));
            this.editor_items.add(new EnhanceFilters(R.drawable.ic_blur_icon, FilterNames.BLUR, FilterType.BLUR));
            this.editor_items.add(new EnhanceFilters(R.drawable.hsl, FilterNames.HSL, FilterType.PIXEL, "HSL", new ArrayList(Arrays.asList(new String[]{"Hue", ExifInterface.TAG_SATURATION, "Luminance"}))));
            this.editor_items.add(new EnhanceFilters(R.drawable.haze_icon, FilterNames.HAZE, FilterType.PIXEL, "Haze", new ArrayList(Arrays.asList(new String[]{"Distance", "Slope", "Red", "Green", "Blue"}))));
            this.editor_items.add(new EnhanceFilters(R.drawable.levels, FilterNames.LEVELS, FilterType.PIXEL, "Levels", new ArrayList(Arrays.asList(new String[]{"Dark", "Light", ExifInterface.TAG_GAMMA}))));
            this.editor_items.add(new EnhanceFilters(R.drawable.exposer, FilterNames.EXPOSE, FilterType.PIXEL));
            this.editor_items.add(new EnhanceFilters(R.drawable.shadow_highlight, FilterNames.SHADOW_HIGHLIGH, FilterType.PIXEL, "Shadow Highlight", new ArrayList(Arrays.asList(new String[]{HttpHeaders.RANGE, "Highlight"}))));
            this.editor_items.add(new EnhanceFilters(R.drawable.color_balance, FilterNames.COLOR_BALANCE, FilterType.PIXEL, "Color Balance", new ArrayList(Arrays.asList(new String[]{"Red", "Green", "Blue"}))));
            this.editor_items.add(new EnhanceFilters(R.drawable.white_balance, FilterNames.WHITE_BALANCE, FilterType.PIXEL, "White Balance", new ArrayList(Arrays.asList(new String[]{"Temperature", "Tint"}))));
            this.editor_items.add(new EnhanceFilters(R.drawable.emboss_icon, FilterNames.EMBOSS, FilterType.PIXEL, "Emboss", new ArrayList(Arrays.asList(new String[]{"Color", "Details", "Shade"}))));
            this.editor_items.add(new EnhanceFilters(R.drawable.halftone_icon, FilterNames.HALFTONE, FilterType.PIXEL));
            this.editor_items.add(new EnhanceFilters(R.drawable.edge_icon, FilterNames.EDGE, FilterType.PIXEL));
            if (!Constants.REMOVE_ADS && Constants.BUTTON_ADS && NativeAdUtil.getInstance().getEditor_native_ads().size() > 0) {
                int i = 0;
                for (int i2 = Constants.EDITOR_AD_BUTTON_SPACE; i2 <= this.editor_items.size(); i2 += Constants.EDITOR_AD_BUTTON_SPACE + 1) {
                    if (i < NativeAdUtil.getInstance().getEditor_native_ads().size() && !Constants.REMOVE_ADS) {
                        this.editor_items.add(i2, NativeAdUtil.getInstance().getEditor_native_ads().get(i));
                        i++;
                    }
                }
            }
        }
        new GPUImageCrosshatchFilter();
        this.mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        this.recyclerView.setLayoutManager(this.mLayoutManager);
        this.mAdapter = new EditorOptionsAdapter(getActivity(), this.editor_items, this);
        this.mAdapter.UncheckAll();
        this.recyclerView.setAdapter(this.mAdapter);
        this.mAdapter.notifyDataSetChanged();
        this.imageView.setImageBitmap(PhotoActivity.bitmap);
        if (this.resetButtonListener != null) {
            this.resetButtonListener.OnResetButtonDeActivate();
        }
        return inflate;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle bundle) {
        super.onViewCreated(view, bundle);
    }

    public void onStart() {
        super.onStart();
    }

    public void onResume() {
        super.onResume();
        ExportDetails.getInstance().setFragment_title("EditorFragment");
        if (!(((PhotoActivity) getActivity()).getEffectFilterDetails() == null || ((PhotoActivity) getActivity()).getEffectFilterDetails().getFilterValuesHolder() == null)) {
            this.filterValuesHolder = ((PhotoActivity) getActivity()).getEffectFilterDetails().getFilterValuesHolder();
        }
        new Handler().postDelayed(new Runnable() {
            public void run() {
                EditorFragment.this.Resume();
            }
        }, 500);
    }

    /* access modifiers changed from: package-private */
    public void Resume() {
        if (this.freshStart) {
            PhotoActivity.ShowMessage("EditorFragment", "  onStart");
            this.freshStart = false;
        } else if (this.toggleId == 0) {
            resetRecyclerView();
            try {
                PhotoActivity.bitmap = (Bitmap) new EffectFilterTask(Registry.BUCKET_BITMAP, ((PhotoActivity) getActivity()).getEffectFilterDetails(), (Bitmap) null, getActivity(), this, (Dialog) null).execute(new Void[0]).get();
                if (this.resetButtonListener != null) {
                    this.resetButtonListener.OnResetButtonDeActivate();
                }
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e2) {
                e2.printStackTrace();
            }
            this.toggleSwitch.setCheckedPosition(0);
            this.imageView.setImageBitmap(PhotoActivity.bitmap);
            if (((PhotoActivity) getActivity()).getEffectFilterDetails().getFilterValuesHolder() != null) {
                if (this.resetButtonListener != null) {
                    this.resetButtonListener.OnResetButtonActivate();
                }
                PhotoActivity.ShowMessage("EditorFragment", "  activate");
            } else if (this.resetButtonListener != null) {
                this.resetButtonListener.OnResetButtonDeActivate();
            }
            Log.d("EditorFragment", "Original");
        } else if (this.toggleId == 1) {
            if (((PhotoActivity) getActivity()).getEffectFilterDetails().isEffectSelected()) {
                try {
                    PhotoActivity.effectFilterbitmap = (Bitmap) new EffectFilterTask("Effect", ((PhotoActivity) getActivity()).getEffectFilterDetails(), PhotoActivity.bitmap, getActivity(), this, (Dialog) null).execute(new Void[0]).get();
                } catch (ExecutionException e3) {
                    e3.printStackTrace();
                } catch (InterruptedException e4) {
                    e4.printStackTrace();
                }
                this.toggleSwitch.setCheckedPosition(1);
                this.imageView.setImageBitmap(PhotoActivity.effectFilterbitmap);
                Log.d("EditorFragment", "Effect");
                if (((PhotoActivity) getActivity()).getEffectFilterDetails().getFilterValuesHolder() != null) {
                    if (this.resetButtonListener != null) {
                        this.resetButtonListener.OnResetButtonActivate();
                    }
                    PhotoActivity.ShowMessage("EditorFragment", "  activate");
                } else if (this.resetButtonListener != null) {
                    this.resetButtonListener.OnResetButtonDeActivate();
                }
            } else {
                resetRecyclerView();
                try {
                    PhotoActivity.bitmap = (Bitmap) new EffectFilterTask(Registry.BUCKET_BITMAP, ((PhotoActivity) getActivity()).getEffectFilterDetails(), (Bitmap) null, getActivity(), this, (Dialog) null).execute(new Void[0]).get();
                } catch (ExecutionException e5) {
                    e5.printStackTrace();
                } catch (InterruptedException e6) {
                    e6.printStackTrace();
                }
                this.toggleSwitch.setCheckedPosition(0);
                this.imageView.setImageBitmap(PhotoActivity.bitmap);
                if (((PhotoActivity) getActivity()).getEffectFilterDetails().getFilterValuesHolder() != null) {
                    if (this.resetButtonListener != null) {
                        this.resetButtonListener.OnResetButtonActivate();
                    }
                    PhotoActivity.ShowMessage("EditorFragment", "  activate");
                } else if (this.resetButtonListener != null) {
                    this.resetButtonListener.OnResetButtonDeActivate();
                }
            }
        } else if (this.toggleId == 2) {
            if (((PhotoActivity) getActivity()).getEffectFilterDetails().isFilterSelected()) {
                try {
                    PhotoActivity.effectFilterbitmap = (Bitmap) new EffectFilterTask("Filter", ((PhotoActivity) getActivity()).getEffectFilterDetails(), PhotoActivity.bitmap, getActivity(), this, (Dialog) null).execute(new Void[0]).get();
                } catch (ExecutionException e7) {
                    e7.printStackTrace();
                } catch (InterruptedException e8) {
                    e8.printStackTrace();
                }
                Log.d("EditorFragment", "Filter");
                this.imageView.setImageBitmap(PhotoActivity.effectFilterbitmap);
                this.toggleSwitch.setCheckedPosition(2);
                if (((PhotoActivity) getActivity()).getEffectFilterDetails().getFilterValuesHolder() != null) {
                    if (this.resetButtonListener != null) {
                        this.resetButtonListener.OnResetButtonActivate();
                    }
                    PhotoActivity.ShowMessage("EditorFragment", "  activate");
                } else if (this.resetButtonListener != null) {
                    this.resetButtonListener.OnResetButtonDeActivate();
                }
            } else {
                resetRecyclerView();
                try {
                    PhotoActivity.bitmap = (Bitmap) new EffectFilterTask(Registry.BUCKET_BITMAP, ((PhotoActivity) getActivity()).getEffectFilterDetails(), (Bitmap) null, getActivity(), this, (Dialog) null).execute(new Void[0]).get();
                } catch (ExecutionException e9) {
                    e9.printStackTrace();
                } catch (InterruptedException e10) {
                    e10.printStackTrace();
                }
                this.toggleSwitch.setCheckedPosition(0);
                this.imageView.setImageBitmap(PhotoActivity.bitmap);
                if (((PhotoActivity) getActivity()).getEffectFilterDetails().getFilterValuesHolder() != null) {
                    PhotoActivity.ShowMessage("EditorFragment", "  activate");
                } else if (this.resetButtonListener != null) {
                    this.resetButtonListener.OnResetButtonDeActivate();
                }
            }
            PhotoActivity.ShowMessage("EditorFragment", "  onStart Resu me");
        }
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                Log.d("EffectContext", "context");
                if (EditorFragment.this.getActivity() != null) {
                    ((PhotoActivity) EditorFragment.this.getActivity()).setResetListener(EditorFragment.this);
                } else {
                    handler.postDelayed(this, 50);
                }
            }
        }, 100);
    }

    public void onPause() {
        super.onPause();
        this.imageView.setImageBitmap((Bitmap) null);
        PhotoActivity.ShowMessage("EditorFragment", "  onPause");
    }

    public void onStop() {
        super.onStop();
        if (((PhotoActivity) getActivity()).getEffectFilterDetails() != null) {
            if (this.editorClicked && this.template != null) {
                ((PhotoActivity) getActivity()).getEffectFilterDetails().setFilterString(this.template);
            }
            ((PhotoActivity) getActivity()).getEffectFilterDetails().setFilterValuesHolder(this.filterValuesHolder);
        }
        PhotoActivity.ShowMessage("EditorFragment", "  onStop");
    }

    public void onDestroyView() {
        super.onDestroyView();
        if (((PhotoActivity) getActivity()).getEffectFilterDetails() != null) {
            if (this.editorClicked && this.template != null) {
                ((PhotoActivity) getActivity()).getEffectFilterDetails().setFilterString(this.template);
            }
            ((PhotoActivity) getActivity()).getEffectFilterDetails().setFilterValuesHolder(this.filterValuesHolder);
        }
        PhotoActivity.ShowMessage("EditorFragment", "  onDestroyView");
    }

    public void onDestroy() {
        super.onDestroy();
        PhotoActivity.ShowMessage("EditorFragment", "  onDestroy");
    }

    public void onDetach() {
        super.onDetach();
    }

    public void OnEnhanceClick(String str, FilterType filterType2, int i, EnhanceFilters enhanceFilters) {
        if (this.layout != null) {
            this.screenLayout.removeView(this.layout);
        }
        if (enhanceFilters.getSeeks() != null && enhanceFilters.getSeeks().size() > 0) {
            initializeView(enhanceFilters);
            Log.d("effectslected", "selectdd");
        }
        this.enhance_filter_name = str;
        this.editorClicked = true;
        if (str != null) {
            this.filter_name.setText(str);
        } else {
            this.filter_name.setText("");
        }
        if (str.equals("Brightness") || str.equals(ExifInterface.TAG_SHARPNESS) || str.equals(ExifInterface.TAG_CONTRAST) || str.equals(ExifInterface.TAG_SATURATION) || str.equals("Edge") || str.equals("Blur") || str.equals("Halftone") || str.equals("Exposure")) {
            if (str.equals("Brightness")) {
                this.seekBar.setMin(-100.0f);
                this.seekBar.setMax(100.0f);
                this.seekBar.setProgress(0.0f);
                this.left_side_value.setText("-1");
                this.right_side_value.setText("1");
            }
            if (str.equals(ExifInterface.TAG_SHARPNESS)) {
                this.seekBar.setMin(0.0f);
                this.seekBar.setMax(1000.0f);
                this.left_side_value.setText(AppEventsConstants.EVENT_PARAM_VALUE_NO);
                this.right_side_value.setText("10");
            }
            if (str.equals(ExifInterface.TAG_CONTRAST)) {
                this.seekBar.setMin(0.0f);
                this.seekBar.setMax(500.0f);
                this.seekBar.setProgress(100.0f);
                this.left_side_value.setText(AppEventsConstants.EVENT_PARAM_VALUE_NO);
                this.right_side_value.setText("5");
            }
            if (str.equals(ExifInterface.TAG_SATURATION)) {
                this.seekBar.setMin(0.0f);
                this.seekBar.setMax(200.0f);
                this.seekBar.setProgress(100.0f);
                this.left_side_value.setText(AppEventsConstants.EVENT_PARAM_VALUE_NO);
                this.right_side_value.setText("2");
            }
            if (str.equals("Blur")) {
                this.seekBar.setMin(0.0f);
                this.seekBar.setMax(1000.0f);
                this.left_side_value.setText(AppEventsConstants.EVENT_PARAM_VALUE_NO);
                this.right_side_value.setText("10");
            }
            if (str.equals("Exposure")) {
                this.seekBar.setMin(-200.0f);
                this.seekBar.setMax(200.0f);
                this.seekBar.setProgress(0.0f);
                this.left_side_value.setText("-2");
                this.right_side_value.setText("2");
            }
            if (str.equals("Edge")) {
                this.seekBar.setMin(0.0f);
                this.seekBar.setMax(200.0f);
                this.seekBar.setProgress(0.0f);
                this.left_side_value.setText(AppEventsConstants.EVENT_PARAM_VALUE_NO);
                this.right_side_value.setText("2");
            }
            if (str.equals("Halftone")) {
                this.seekBar.setMin(0.0f);
                this.seekBar.setMax(200.0f);
                this.seekBar.setProgress(0.0f);
                this.left_side_value.setText(AppEventsConstants.EVENT_PARAM_VALUE_NO);
                this.right_side_value.setText("2");
            }
            Log.d("effectslected ", str);
            if (this.layout != null) {
                this.screenLayout.removeView(this.layout);
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.toggleLayout.getLayoutParams();
                layoutParams.addRule(2, this.seekBarView.getId());
                layoutParams.bottomMargin = ((PhotoActivity) getActivity()).dpToPx(15);
                this.toggleLayout.setLayoutParams(layoutParams);
                Log.d("seeklayout", "null");
            }
            this.seekBarView.setVisibility(View.INVISIBLE);
        } else if (this.seekBarView != null && this.seekBarView.getVisibility() == View.INVISIBLE) {
            this.seekBarView.setVisibility(View.GONE);
        }
        this.filterType = filterType2;
        if (this.filterValuesHolder == null || !this.filterValuesHolder.hasKey(this.enhance_filter_name)) {
            PhotoActivity.ShowMessage("value", "  no value");
        } else if (!(this.filterValuesHolder.getFilterValue(this.filterName) instanceof List)) {
            float floatValue = ((Float) this.filterValuesHolder.getFilterValue(this.enhance_filter_name)).floatValue();
            this.seekBar.setProgress(floatValue * 100.0f);
            this.filter_value.setText(String.valueOf(floatValue));
            Log.d("effectslected", "value");
        }
        if (((PhotoActivity) getActivity()).getEffectFilterDetails() != null) {
            ((PhotoActivity) getActivity()).getEffectFilterDetails().setFilterType(filterType2);
            ((PhotoActivity) getActivity()).getEffectFilterDetails().setFilterName(str);
        }
        if (this.toggleId != 0) {
            if (PhotoActivity.effectFilterbitmap == null) {
                getBitmap();
                PhotoActivity.ShowMessage("EditorBitmap orig", "  null");
            }
        } else if (PhotoActivity.bitmap == null) {
            getBitmap();
            PhotoActivity.ShowMessage("EditorBitmap Effect", "  null");
        }
        if (this.filterValuesHolder != null && this.filterValuesHolder.hasKey(this.filterName) && (this.filterValuesHolder.getFilterValue(this.filterName) instanceof List)) {
            for (int i2 = 0; i2 < ((List) this.filterValuesHolder.getFilterValue(this.filterName)).size(); i2++) {
                float floatValue2 = ((Float) ((List) this.filterValuesHolder.getFilterValue(this.filterName)).get(i2)).floatValue();
                Log.d("Seek", String.valueOf(floatValue2));
                this.seekBarList.get(i2).setProgress(floatValue2 * 100.0f);
                this.filterValueList.get(i2).setText(String.valueOf(floatValue2));
            }
        }
        checkForAd();
    }

    public void OnEnhanceRemove() {
        this.editorClicked = false;
        this.seekBarView.setVisibility(View.GONE);
        if (this.layout != null) {
            this.screenLayout.removeView(this.layout);
        }
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.toggleLayout.getLayoutParams();
        layoutParams.addRule(2, this.seekBarView.getId());
        layoutParams.bottomMargin = ((PhotoActivity) getActivity()).dpToPx(15);
        this.toggleLayout.setLayoutParams(layoutParams);
    }

    public void onSeeking(SeekParams seekParams) {
        //if (seekParams.seekBar.getId() == R.id.seek_1) {
        if (seekParams.seekBar.getId() == Constants.SEEK_BAR.SEEK_BAR_1.getValue()) {
            this.filterValueList.get(0).setText(String.valueOf(((float) seekParams.seekBar.getProgress()) / 100.0f));
        } else if (seekParams.seekBar.getId() == Constants.SEEK_BAR.SEEK_BAR_2.getValue()) {
            this.filterValueList.get(1).setText(String.valueOf(((float) seekParams.seekBar.getProgress()) / 100.0f));
        } else if (seekParams.seekBar.getId() == Constants.SEEK_BAR.SEEK_BAR_3.getValue()) {
            this.filterValueList.get(2).setText(String.valueOf(((float) seekParams.seekBar.getProgress()) / 100.0f));
        } else if (seekParams.seekBar.getId() == Constants.SEEK_BAR.SEEK_BAR_4.getValue()) {
            this.filterValueList.get(3).setText(String.valueOf(((float) seekParams.seekBar.getProgress()) / 100.0f));
        } else if (seekParams.seekBar.getId() == Constants.SEEK_BAR.SEEK_BAR_1.getValue()) {
            this.filterValueList.get(4).setText(String.valueOf(((float) seekParams.seekBar.getProgress()) / 100.0f));
        } else {
            this.filter_value.setText(String.valueOf(((float) seekParams.seekBar.getProgress()) / 100.0f));
        }
        Log.d("effectslected", "value2");
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0068, code lost:
        if (r0.equals("Halftone") != false) goto L_0x009e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:80:0x03d1, code lost:
        if (r0.equals("Emboss") != false) goto L_0x0411;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onStopTrackingTouch(com.warkiz.widget.IndicatorSeekBar r13) {
        /*
            r12 = this;
            listeners.ResetButtonListener r0 = r12.resetButtonListener
            if (r0 == 0) goto L_0x0009
            listeners.ResetButtonListener r0 = r12.resetButtonListener
            r0.OnResetButtonActivate()
        L_0x0009:
            int r0 = r13.getId()
            r1 = 2131296715(0x7f0901cb, float:1.8211355E38)
            r2 = 6
            r3 = 4
            r4 = 3
            r5 = 5
            r6 = 2
            r7 = -1
            r8 = 1
            r9 = 0
            r10 = 1120403456(0x42c80000, float:100.0)
            r11 = 1065353216(0x3f800000, float:1.0)
            if (r0 == r1) goto L_0x03b9
            int r0 = r13.getId()
            r1 = 2131296716(0x7f0901cc, float:1.8211357E38)
            if (r0 == r1) goto L_0x03b9
            int r0 = r13.getId()
            r1 = 2131296717(0x7f0901cd, float:1.8211359E38)
            if (r0 == r1) goto L_0x03b9
            int r0 = r13.getId()
            r1 = 2131296718(0x7f0901ce, float:1.821136E38)
            if (r0 == r1) goto L_0x03b9
            int r0 = r13.getId()
            r1 = 2131296719(0x7f0901cf, float:1.8211363E38)
            if (r0 != r1) goto L_0x0044
            goto L_0x03b9
        L_0x0044:
            java.lang.String r0 = r12.enhance_filter_name
            int r1 = r0.hashCode()
            switch(r1) {
                case -1861361369: goto L_0x0093;
                case -1653340047: goto L_0x0089;
                case -502302942: goto L_0x007f;
                case 2073735: goto L_0x0075;
                case 2154973: goto L_0x006b;
                case 11549765: goto L_0x0062;
                case 432862497: goto L_0x0058;
                case 1762973682: goto L_0x004e;
                default: goto L_0x004d;
            }
        L_0x004d:
            goto L_0x009d
        L_0x004e:
            java.lang.String r1 = "Saturation"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x009d
            r2 = 3
            goto L_0x009e
        L_0x0058:
            java.lang.String r1 = "Sharpness"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x009d
            r2 = 1
            goto L_0x009e
        L_0x0062:
            java.lang.String r1 = "Halftone"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x009d
            goto L_0x009e
        L_0x006b:
            java.lang.String r1 = "Edge"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x009d
            r2 = 7
            goto L_0x009e
        L_0x0075:
            java.lang.String r1 = "Blur"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x009d
            r2 = 4
            goto L_0x009e
        L_0x007f:
            java.lang.String r1 = "Contrast"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x009d
            r2 = 2
            goto L_0x009e
        L_0x0089:
            java.lang.String r1 = "Brightness"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x009d
            r2 = 0
            goto L_0x009e
        L_0x0093:
            java.lang.String r1 = "Exposure"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x009d
            r2 = 5
            goto L_0x009e
        L_0x009d:
            r2 = -1
        L_0x009e:
            switch(r2) {
                case 0: goto L_0x035d;
                case 1: goto L_0x02f6;
                case 2: goto L_0x028d;
                case 3: goto L_0x0224;
                case 4: goto L_0x01c2;
                case 5: goto L_0x0160;
                case 6: goto L_0x0104;
                case 7: goto L_0x00a3;
                default: goto L_0x00a1;
            }
        L_0x00a1:
            goto L_0x07d3
        L_0x00a3:
            int r13 = r13.getProgress()
            float r13 = (float) r13
            float r13 = r13 / r10
            r12.filter_Value = r13
            java.lang.StringBuilder r13 = new java.lang.StringBuilder
            r13.<init>()
            java.lang.String r0 = "@style edge "
            r13.append(r0)
            float r0 = r12.filter_Value
            java.lang.String r0 = java.lang.String.valueOf(r0)
            r13.append(r0)
            java.lang.String r0 = " 2"
            r13.append(r0)
            java.lang.String r13 = r13.toString()
            r12.template = r13
            util.FilterValuesHolder r13 = r12.filterValuesHolder
            java.lang.String r0 = "Edge"
            float r1 = r12.filter_Value
            java.lang.Float r1 = java.lang.Float.valueOf(r1)
            r13.addFilterwithValue(r0, r1)
            java.util.LinkedHashMap<java.lang.String, java.lang.String> r13 = r12.filterMap
            java.lang.String r0 = "Edge"
            java.lang.String r1 = r12.template
            r13.put(r0, r1)
            r12.readMap()
            int r13 = r12.toggleId
            if (r13 != 0) goto L_0x00f5
            android.widget.ImageView r13 = r12.imageView
            android.graphics.Bitmap r0 = com.hqgames.pencil.sketch.photo.PhotoActivity.bitmap
            java.lang.String r1 = r12.template
            android.graphics.Bitmap r0 = org.wysaid.nativePort.CGENativeLibrary.filterImage_MultipleEffects(r0, r1, r11)
            r13.setImageBitmap(r0)
            goto L_0x07d3
        L_0x00f5:
            android.widget.ImageView r13 = r12.imageView
            android.graphics.Bitmap r0 = com.hqgames.pencil.sketch.photo.PhotoActivity.effectFilterbitmap
            java.lang.String r1 = r12.template
            android.graphics.Bitmap r0 = org.wysaid.nativePort.CGENativeLibrary.filterImage_MultipleEffects(r0, r1, r11)
            r13.setImageBitmap(r0)
            goto L_0x07d3
        L_0x0104:
            int r13 = r13.getProgress()
            float r13 = (float) r13
            float r13 = r13 / r10
            r12.filter_Value = r13
            java.lang.StringBuilder r13 = new java.lang.StringBuilder
            r13.<init>()
            java.lang.String r0 = "@style halftone "
            r13.append(r0)
            float r0 = r12.filter_Value
            java.lang.String r0 = java.lang.String.valueOf(r0)
            r13.append(r0)
            java.lang.String r13 = r13.toString()
            r12.template = r13
            util.FilterValuesHolder r13 = r12.filterValuesHolder
            java.lang.String r0 = "Halftone"
            float r1 = r12.filter_Value
            java.lang.Float r1 = java.lang.Float.valueOf(r1)
            r13.addFilterwithValue(r0, r1)
            java.util.LinkedHashMap<java.lang.String, java.lang.String> r13 = r12.filterMap
            java.lang.String r0 = "Halftone"
            java.lang.String r1 = r12.template
            r13.put(r0, r1)
            r12.readMap()
            int r13 = r12.toggleId
            if (r13 != 0) goto L_0x0151
            android.widget.ImageView r13 = r12.imageView
            android.graphics.Bitmap r0 = com.hqgames.pencil.sketch.photo.PhotoActivity.bitmap
            java.lang.String r1 = r12.template
            android.graphics.Bitmap r0 = org.wysaid.nativePort.CGENativeLibrary.filterImage_MultipleEffects(r0, r1, r11)
            r13.setImageBitmap(r0)
            goto L_0x07d3
        L_0x0151:
            android.widget.ImageView r13 = r12.imageView
            android.graphics.Bitmap r0 = com.hqgames.pencil.sketch.photo.PhotoActivity.effectFilterbitmap
            java.lang.String r1 = r12.template
            android.graphics.Bitmap r0 = org.wysaid.nativePort.CGENativeLibrary.filterImage_MultipleEffects(r0, r1, r11)
            r13.setImageBitmap(r0)
            goto L_0x07d3
        L_0x0160:
            int r13 = r13.getProgress()
            float r13 = (float) r13
            float r13 = r13 / r10
            r12.filter_Value = r13
            java.lang.StringBuilder r13 = new java.lang.StringBuilder
            r13.<init>()
            java.lang.String r0 = "@adjust exposure "
            r13.append(r0)
            float r0 = r12.filter_Value
            java.lang.String r0 = java.lang.String.valueOf(r0)
            r13.append(r0)
            java.lang.String r13 = r13.toString()
            r12.template = r13
            util.FilterValuesHolder r13 = r12.filterValuesHolder
            java.lang.String r0 = "Blur"
            float r1 = r12.filter_Value
            java.lang.Float r1 = java.lang.Float.valueOf(r1)
            r13.addFilterwithValue(r0, r1)
            java.util.LinkedHashMap<java.lang.String, java.lang.String> r13 = r12.filterMap
            java.lang.String r0 = "Expose"
            java.lang.String r1 = r12.template
            r13.put(r0, r1)
            r12.readMap()
            int r13 = r12.toggleId
            if (r13 != 0) goto L_0x01ac
            android.widget.ImageView r13 = r12.imageView
            android.graphics.Bitmap r0 = com.hqgames.pencil.sketch.photo.PhotoActivity.bitmap
            java.lang.String r1 = r12.template
            android.graphics.Bitmap r0 = org.wysaid.nativePort.CGENativeLibrary.filterImage_MultipleEffects(r0, r1, r11)
            r13.setImageBitmap(r0)
            goto L_0x01b9
        L_0x01ac:
            android.widget.ImageView r13 = r12.imageView
            android.graphics.Bitmap r0 = com.hqgames.pencil.sketch.photo.PhotoActivity.effectFilterbitmap
            java.lang.String r1 = r12.template
            android.graphics.Bitmap r0 = org.wysaid.nativePort.CGENativeLibrary.filterImage_MultipleEffects(r0, r1, r11)
            r13.setImageBitmap(r0)
        L_0x01b9:
            java.lang.String r13 = "key value"
            java.lang.String r0 = r12.template
            android.util.Log.d(r13, r0)
            goto L_0x07d3
        L_0x01c2:
            int r13 = r13.getProgress()
            float r13 = (float) r13
            float r13 = r13 / r10
            r12.filter_Value = r13
            java.lang.StringBuilder r13 = new java.lang.StringBuilder
            r13.<init>()
            java.lang.String r0 = "@blur lerp "
            r13.append(r0)
            float r0 = r12.filter_Value
            java.lang.String r0 = java.lang.String.valueOf(r0)
            r13.append(r0)
            java.lang.String r13 = r13.toString()
            r12.template = r13
            util.FilterValuesHolder r13 = r12.filterValuesHolder
            java.lang.String r0 = "Blur"
            float r1 = r12.filter_Value
            java.lang.Float r1 = java.lang.Float.valueOf(r1)
            r13.addFilterwithValue(r0, r1)
            java.util.LinkedHashMap<java.lang.String, java.lang.String> r13 = r12.filterMap
            java.lang.String r0 = "Blur"
            java.lang.String r1 = r12.template
            r13.put(r0, r1)
            r12.readMap()
            int r13 = r12.toggleId
            if (r13 != 0) goto L_0x020e
            android.widget.ImageView r13 = r12.imageView
            android.graphics.Bitmap r0 = com.hqgames.pencil.sketch.photo.PhotoActivity.bitmap
            java.lang.String r1 = r12.template
            android.graphics.Bitmap r0 = org.wysaid.nativePort.CGENativeLibrary.filterImage_MultipleEffects(r0, r1, r11)
            r13.setImageBitmap(r0)
            goto L_0x021b
        L_0x020e:
            android.widget.ImageView r13 = r12.imageView
            android.graphics.Bitmap r0 = com.hqgames.pencil.sketch.photo.PhotoActivity.effectFilterbitmap
            java.lang.String r1 = r12.template
            android.graphics.Bitmap r0 = org.wysaid.nativePort.CGENativeLibrary.filterImage_MultipleEffects(r0, r1, r11)
            r13.setImageBitmap(r0)
        L_0x021b:
            java.lang.String r13 = "key value"
            java.lang.String r0 = r12.template
            android.util.Log.d(r13, r0)
            goto L_0x07d3
        L_0x0224:
            int r13 = r13.getProgress()
            float r13 = (float) r13
            float r13 = r13 / r10
            r12.filter_Value = r13
            java.lang.StringBuilder r13 = new java.lang.StringBuilder
            r13.<init>()
            java.lang.String r0 = "@adjust saturation "
            r13.append(r0)
            float r0 = r12.filter_Value
            java.lang.String r0 = java.lang.String.valueOf(r0)
            r13.append(r0)
            java.lang.String r13 = r13.toString()
            r12.template = r13
            util.FilterValuesHolder r13 = r12.filterValuesHolder
            java.lang.String r0 = "Saturation"
            float r1 = r12.filter_Value
            java.lang.Float r1 = java.lang.Float.valueOf(r1)
            r13.addFilterwithValue(r0, r1)
            java.util.List<java.lang.String> r13 = r12.f6092filters
            java.lang.String r0 = "Saturation"
            r13.add(r0)
            java.util.LinkedHashMap<java.lang.String, java.lang.String> r13 = r12.filterMap
            java.lang.String r0 = "Saturation"
            java.lang.String r1 = r12.template
            r13.put(r0, r1)
            r12.readMap()
            int r13 = r12.toggleId
            if (r13 != 0) goto L_0x0277
            android.widget.ImageView r13 = r12.imageView
            android.graphics.Bitmap r0 = com.hqgames.pencil.sketch.photo.PhotoActivity.bitmap
            java.lang.String r1 = r12.template
            android.graphics.Bitmap r0 = org.wysaid.nativePort.CGENativeLibrary.filterImage_MultipleEffects(r0, r1, r11)
            r13.setImageBitmap(r0)
            goto L_0x0284
        L_0x0277:
            android.widget.ImageView r13 = r12.imageView
            android.graphics.Bitmap r0 = com.hqgames.pencil.sketch.photo.PhotoActivity.effectFilterbitmap
            java.lang.String r1 = r12.template
            android.graphics.Bitmap r0 = org.wysaid.nativePort.CGENativeLibrary.filterImage_MultipleEffects(r0, r1, r11)
            r13.setImageBitmap(r0)
        L_0x0284:
            java.lang.String r13 = "key value"
            java.lang.String r0 = r12.template
            android.util.Log.d(r13, r0)
            goto L_0x07d3
        L_0x028d:
            int r13 = r13.getProgress()
            float r13 = (float) r13
            float r13 = r13 / r10
            r12.filter_Value = r13
            java.lang.StringBuilder r13 = new java.lang.StringBuilder
            r13.<init>()
            java.lang.String r0 = "@adjust contrast "
            r13.append(r0)
            float r0 = r12.filter_Value
            java.lang.String r0 = java.lang.String.valueOf(r0)
            r13.append(r0)
            java.lang.String r13 = r13.toString()
            r12.template = r13
            util.FilterValuesHolder r13 = r12.filterValuesHolder
            java.lang.String r0 = "Contrast"
            float r1 = r12.filter_Value
            java.lang.Float r1 = java.lang.Float.valueOf(r1)
            r13.addFilterwithValue(r0, r1)
            java.util.List<java.lang.String> r13 = r12.f6092filters
            java.lang.String r0 = "Contrast"
            r13.add(r0)
            java.util.LinkedHashMap<java.lang.String, java.lang.String> r13 = r12.filterMap
            java.lang.String r0 = "Contrast"
            java.lang.String r1 = r12.template
            r13.put(r0, r1)
            r12.readMap()
            int r13 = r12.toggleId
            if (r13 != 0) goto L_0x02e0
            android.widget.ImageView r13 = r12.imageView
            android.graphics.Bitmap r0 = com.hqgames.pencil.sketch.photo.PhotoActivity.bitmap
            java.lang.String r1 = r12.template
            android.graphics.Bitmap r0 = org.wysaid.nativePort.CGENativeLibrary.filterImage_MultipleEffects(r0, r1, r11)
            r13.setImageBitmap(r0)
            goto L_0x02ed
        L_0x02e0:
            android.widget.ImageView r13 = r12.imageView
            android.graphics.Bitmap r0 = com.hqgames.pencil.sketch.photo.PhotoActivity.effectFilterbitmap
            java.lang.String r1 = r12.template
            android.graphics.Bitmap r0 = org.wysaid.nativePort.CGENativeLibrary.filterImage_MultipleEffects(r0, r1, r11)
            r13.setImageBitmap(r0)
        L_0x02ed:
            java.lang.String r13 = "key value"
            java.lang.String r0 = r12.template
            android.util.Log.d(r13, r0)
            goto L_0x07d3
        L_0x02f6:
            int r13 = r13.getProgress()
            float r13 = (float) r13
            float r13 = r13 / r10
            r12.filter_Value = r13
            java.lang.StringBuilder r13 = new java.lang.StringBuilder
            r13.<init>()
            java.lang.String r0 = "@adjust sharpen "
            r13.append(r0)
            float r0 = r12.filter_Value
            java.lang.String r0 = java.lang.String.valueOf(r0)
            r13.append(r0)
            java.lang.String r0 = " 2"
            r13.append(r0)
            java.lang.String r13 = r13.toString()
            r12.template = r13
            util.FilterValuesHolder r13 = r12.filterValuesHolder
            java.lang.String r0 = "Sharpness"
            float r1 = r12.filter_Value
            java.lang.Float r1 = java.lang.Float.valueOf(r1)
            r13.addFilterwithValue(r0, r1)
            java.util.LinkedHashMap<java.lang.String, java.lang.String> r13 = r12.filterMap
            java.lang.String r0 = "Sharpness"
            java.lang.String r1 = r12.template
            r13.put(r0, r1)
            r12.readMap()
            int r13 = r12.toggleId
            if (r13 != 0) goto L_0x0347
            android.widget.ImageView r13 = r12.imageView
            android.graphics.Bitmap r0 = com.hqgames.pencil.sketch.photo.PhotoActivity.bitmap
            java.lang.String r1 = r12.template
            android.graphics.Bitmap r0 = org.wysaid.nativePort.CGENativeLibrary.filterImage_MultipleEffects(r0, r1, r11)
            r13.setImageBitmap(r0)
            goto L_0x0354
        L_0x0347:
            android.widget.ImageView r13 = r12.imageView
            android.graphics.Bitmap r0 = com.hqgames.pencil.sketch.photo.PhotoActivity.effectFilterbitmap
            java.lang.String r1 = r12.template
            android.graphics.Bitmap r0 = org.wysaid.nativePort.CGENativeLibrary.filterImage_MultipleEffects(r0, r1, r11)
            r13.setImageBitmap(r0)
        L_0x0354:
            java.lang.String r13 = "key value"
            java.lang.String r0 = r12.template
            android.util.Log.d(r13, r0)
            goto L_0x07d3
        L_0x035d:
            int r13 = r13.getProgress()
            float r13 = (float) r13
            float r13 = r13 / r10
            r12.filter_Value = r13
            java.lang.StringBuilder r13 = new java.lang.StringBuilder
            r13.<init>()
            java.lang.String r0 = "@adjust brightness "
            r13.append(r0)
            float r0 = r12.filter_Value
            java.lang.String r0 = java.lang.String.valueOf(r0)
            r13.append(r0)
            java.lang.String r13 = r13.toString()
            r12.template = r13
            util.FilterValuesHolder r13 = r12.filterValuesHolder
            java.lang.String r0 = "Brightness"
            float r1 = r12.filter_Value
            java.lang.Float r1 = java.lang.Float.valueOf(r1)
            r13.addFilterwithValue(r0, r1)
            java.util.LinkedHashMap<java.lang.String, java.lang.String> r13 = r12.filterMap
            java.lang.String r0 = "Brightness"
            java.lang.String r1 = r12.template
            r13.put(r0, r1)
            r12.readMap()
            int r13 = r12.toggleId
            if (r13 != 0) goto L_0x03aa
            android.widget.ImageView r13 = r12.imageView
            android.graphics.Bitmap r0 = com.hqgames.pencil.sketch.photo.PhotoActivity.bitmap
            java.lang.String r1 = r12.template
            android.graphics.Bitmap r0 = org.wysaid.nativePort.CGENativeLibrary.filterImage_MultipleEffects(r0, r1, r11)
            r13.setImageBitmap(r0)
            goto L_0x07d3
        L_0x03aa:
            android.widget.ImageView r13 = r12.imageView
            android.graphics.Bitmap r0 = com.hqgames.pencil.sketch.photo.PhotoActivity.effectFilterbitmap
            java.lang.String r1 = r12.template
            android.graphics.Bitmap r0 = org.wysaid.nativePort.CGENativeLibrary.filterImage_MultipleEffects(r0, r1, r11)
            r13.setImageBitmap(r0)
            goto L_0x07d3
        L_0x03b9:
            java.util.ArrayList r13 = new java.util.ArrayList
            r13.<init>()
            r13.clear()
            java.lang.String r0 = r12.filterName
            int r1 = r0.hashCode()
            switch(r1) {
                case -2022260337: goto L_0x0406;
                case -1142069851: goto L_0x03fc;
                case -939416140: goto L_0x03f2;
                case -800788769: goto L_0x03e8;
                case 71841: goto L_0x03de;
                case 2242052: goto L_0x03d4;
                case 2079105077: goto L_0x03cb;
                default: goto L_0x03ca;
            }
        L_0x03ca:
            goto L_0x0410
        L_0x03cb:
            java.lang.String r1 = "Emboss"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0410
            goto L_0x0411
        L_0x03d4:
            java.lang.String r1 = "Haze"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0410
            r2 = 1
            goto L_0x0411
        L_0x03de:
            java.lang.String r1 = "HSL"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0410
            r2 = 0
            goto L_0x0411
        L_0x03e8:
            java.lang.String r1 = "Color Balance"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0410
            r2 = 4
            goto L_0x0411
        L_0x03f2:
            java.lang.String r1 = "Shadow Highlight"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0410
            r2 = 3
            goto L_0x0411
        L_0x03fc:
            java.lang.String r1 = "White Balance"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0410
            r2 = 5
            goto L_0x0411
        L_0x0406:
            java.lang.String r1 = "Levels"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0410
            r2 = 2
            goto L_0x0411
        L_0x0410:
            r2 = -1
        L_0x0411:
            switch(r2) {
                case 0: goto L_0x074c;
                case 1: goto L_0x06c3;
                case 2: goto L_0x063a;
                case 3: goto L_0x05b1;
                case 4: goto L_0x0528;
                case 5: goto L_0x049f;
                case 6: goto L_0x0416;
                default: goto L_0x0414;
            }
        L_0x0414:
            goto L_0x07d3
        L_0x0416:
            java.lang.String r0 = "#unpack @style emboss"
            r12.template = r0
        L_0x041a:
            java.util.List<com.warkiz.widget.IndicatorSeekBar> r0 = r12.seekBarList
            int r0 = r0.size()
            if (r9 >= r0) goto L_0x0464
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = r12.template
            r0.append(r1)
            java.lang.String r1 = " "
            r0.append(r1)
            java.util.List<com.warkiz.widget.IndicatorSeekBar> r1 = r12.seekBarList
            java.lang.Object r1 = r1.get(r9)
            com.warkiz.widget.IndicatorSeekBar r1 = (com.warkiz.widget.IndicatorSeekBar) r1
            int r1 = r1.getProgress()
            float r1 = (float) r1
            float r1 = r1 / r10
            java.lang.String r1 = java.lang.String.valueOf(r1)
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            r12.template = r0
            java.util.List<com.warkiz.widget.IndicatorSeekBar> r0 = r12.seekBarList
            java.lang.Object r0 = r0.get(r9)
            com.warkiz.widget.IndicatorSeekBar r0 = (com.warkiz.widget.IndicatorSeekBar) r0
            int r0 = r0.getProgress()
            float r0 = (float) r0
            float r0 = r0 / r10
            java.lang.Float r0 = java.lang.Float.valueOf(r0)
            r13.add(r0)
            int r9 = r9 + 1
            goto L_0x041a
        L_0x0464:
            util.FilterValuesHolder r0 = r12.filterValuesHolder
            java.lang.String r1 = r12.filterName
            r0.addFilterwithValue(r1, r13)
            java.util.LinkedHashMap<java.lang.String, java.lang.String> r13 = r12.filterMap
            java.lang.String r0 = "Emboss"
            java.lang.String r1 = r12.template
            r13.put(r0, r1)
            r12.readMap()
            int r13 = r12.toggleId
            if (r13 != 0) goto L_0x0489
            android.widget.ImageView r13 = r12.imageView
            android.graphics.Bitmap r0 = com.hqgames.pencil.sketch.photo.PhotoActivity.bitmap
            java.lang.String r1 = r12.template
            android.graphics.Bitmap r0 = org.wysaid.nativePort.CGENativeLibrary.filterImage_MultipleEffects(r0, r1, r11)
            r13.setImageBitmap(r0)
            goto L_0x0496
        L_0x0489:
            android.widget.ImageView r13 = r12.imageView
            android.graphics.Bitmap r0 = com.hqgames.pencil.sketch.photo.PhotoActivity.effectFilterbitmap
            java.lang.String r1 = r12.template
            android.graphics.Bitmap r0 = org.wysaid.nativePort.CGENativeLibrary.filterImage_MultipleEffects(r0, r1, r11)
            r13.setImageBitmap(r0)
        L_0x0496:
            java.lang.String r13 = "key value"
            java.lang.String r0 = r12.template
            android.util.Log.d(r13, r0)
            goto L_0x07d3
        L_0x049f:
            java.lang.String r0 = "@adjust whitebalance"
            r12.template = r0
        L_0x04a3:
            java.util.List<com.warkiz.widget.IndicatorSeekBar> r0 = r12.seekBarList
            int r0 = r0.size()
            if (r9 >= r0) goto L_0x04ed
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = r12.template
            r0.append(r1)
            java.lang.String r1 = " "
            r0.append(r1)
            java.util.List<com.warkiz.widget.IndicatorSeekBar> r1 = r12.seekBarList
            java.lang.Object r1 = r1.get(r9)
            com.warkiz.widget.IndicatorSeekBar r1 = (com.warkiz.widget.IndicatorSeekBar) r1
            int r1 = r1.getProgress()
            float r1 = (float) r1
            float r1 = r1 / r10
            java.lang.String r1 = java.lang.String.valueOf(r1)
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            r12.template = r0
            java.util.List<com.warkiz.widget.IndicatorSeekBar> r0 = r12.seekBarList
            java.lang.Object r0 = r0.get(r9)
            com.warkiz.widget.IndicatorSeekBar r0 = (com.warkiz.widget.IndicatorSeekBar) r0
            int r0 = r0.getProgress()
            float r0 = (float) r0
            float r0 = r0 / r10
            java.lang.Float r0 = java.lang.Float.valueOf(r0)
            r13.add(r0)
            int r9 = r9 + 1
            goto L_0x04a3
        L_0x04ed:
            util.FilterValuesHolder r0 = r12.filterValuesHolder
            java.lang.String r1 = r12.filterName
            r0.addFilterwithValue(r1, r13)
            java.util.LinkedHashMap<java.lang.String, java.lang.String> r13 = r12.filterMap
            java.lang.String r0 = "White Balance"
            java.lang.String r1 = r12.template
            r13.put(r0, r1)
            r12.readMap()
            int r13 = r12.toggleId
            if (r13 != 0) goto L_0x0512
            android.widget.ImageView r13 = r12.imageView
            android.graphics.Bitmap r0 = com.hqgames.pencil.sketch.photo.PhotoActivity.bitmap
            java.lang.String r1 = r12.template
            android.graphics.Bitmap r0 = org.wysaid.nativePort.CGENativeLibrary.filterImage_MultipleEffects(r0, r1, r11)
            r13.setImageBitmap(r0)
            goto L_0x051f
        L_0x0512:
            android.widget.ImageView r13 = r12.imageView
            android.graphics.Bitmap r0 = com.hqgames.pencil.sketch.photo.PhotoActivity.effectFilterbitmap
            java.lang.String r1 = r12.template
            android.graphics.Bitmap r0 = org.wysaid.nativePort.CGENativeLibrary.filterImage_MultipleEffects(r0, r1, r11)
            r13.setImageBitmap(r0)
        L_0x051f:
            java.lang.String r13 = "key value"
            java.lang.String r0 = r12.template
            android.util.Log.d(r13, r0)
            goto L_0x07d3
        L_0x0528:
            java.lang.String r0 = "@adjust colorbalance"
            r12.template = r0
        L_0x052c:
            java.util.List<com.warkiz.widget.IndicatorSeekBar> r0 = r12.seekBarList
            int r0 = r0.size()
            if (r9 >= r0) goto L_0x0576
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = r12.template
            r0.append(r1)
            java.lang.String r1 = " "
            r0.append(r1)
            java.util.List<com.warkiz.widget.IndicatorSeekBar> r1 = r12.seekBarList
            java.lang.Object r1 = r1.get(r9)
            com.warkiz.widget.IndicatorSeekBar r1 = (com.warkiz.widget.IndicatorSeekBar) r1
            int r1 = r1.getProgress()
            float r1 = (float) r1
            float r1 = r1 / r10
            java.lang.String r1 = java.lang.String.valueOf(r1)
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            r12.template = r0
            java.util.List<com.warkiz.widget.IndicatorSeekBar> r0 = r12.seekBarList
            java.lang.Object r0 = r0.get(r9)
            com.warkiz.widget.IndicatorSeekBar r0 = (com.warkiz.widget.IndicatorSeekBar) r0
            int r0 = r0.getProgress()
            float r0 = (float) r0
            float r0 = r0 / r10
            java.lang.Float r0 = java.lang.Float.valueOf(r0)
            r13.add(r0)
            int r9 = r9 + 1
            goto L_0x052c
        L_0x0576:
            util.FilterValuesHolder r0 = r12.filterValuesHolder
            java.lang.String r1 = r12.filterName
            r0.addFilterwithValue(r1, r13)
            java.util.LinkedHashMap<java.lang.String, java.lang.String> r13 = r12.filterMap
            java.lang.String r0 = "Color Balance"
            java.lang.String r1 = r12.template
            r13.put(r0, r1)
            r12.readMap()
            int r13 = r12.toggleId
            if (r13 != 0) goto L_0x059b
            android.widget.ImageView r13 = r12.imageView
            android.graphics.Bitmap r0 = com.hqgames.pencil.sketch.photo.PhotoActivity.bitmap
            java.lang.String r1 = r12.template
            android.graphics.Bitmap r0 = org.wysaid.nativePort.CGENativeLibrary.filterImage_MultipleEffects(r0, r1, r11)
            r13.setImageBitmap(r0)
            goto L_0x05a8
        L_0x059b:
            android.widget.ImageView r13 = r12.imageView
            android.graphics.Bitmap r0 = com.hqgames.pencil.sketch.photo.PhotoActivity.effectFilterbitmap
            java.lang.String r1 = r12.template
            android.graphics.Bitmap r0 = org.wysaid.nativePort.CGENativeLibrary.filterImage_MultipleEffects(r0, r1, r11)
            r13.setImageBitmap(r0)
        L_0x05a8:
            java.lang.String r13 = "key value"
            java.lang.String r0 = r12.template
            android.util.Log.d(r13, r0)
            goto L_0x07d3
        L_0x05b1:
            java.lang.String r0 = "@adjust shadowhighlight"
            r12.template = r0
        L_0x05b5:
            java.util.List<com.warkiz.widget.IndicatorSeekBar> r0 = r12.seekBarList
            int r0 = r0.size()
            if (r9 >= r0) goto L_0x05ff
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = r12.template
            r0.append(r1)
            java.lang.String r1 = " "
            r0.append(r1)
            java.util.List<com.warkiz.widget.IndicatorSeekBar> r1 = r12.seekBarList
            java.lang.Object r1 = r1.get(r9)
            com.warkiz.widget.IndicatorSeekBar r1 = (com.warkiz.widget.IndicatorSeekBar) r1
            int r1 = r1.getProgress()
            float r1 = (float) r1
            float r1 = r1 / r10
            java.lang.String r1 = java.lang.String.valueOf(r1)
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            r12.template = r0
            java.util.List<com.warkiz.widget.IndicatorSeekBar> r0 = r12.seekBarList
            java.lang.Object r0 = r0.get(r9)
            com.warkiz.widget.IndicatorSeekBar r0 = (com.warkiz.widget.IndicatorSeekBar) r0
            int r0 = r0.getProgress()
            float r0 = (float) r0
            float r0 = r0 / r10
            java.lang.Float r0 = java.lang.Float.valueOf(r0)
            r13.add(r0)
            int r9 = r9 + 1
            goto L_0x05b5
        L_0x05ff:
            util.FilterValuesHolder r0 = r12.filterValuesHolder
            java.lang.String r1 = r12.filterName
            r0.addFilterwithValue(r1, r13)
            java.util.LinkedHashMap<java.lang.String, java.lang.String> r13 = r12.filterMap
            java.lang.String r0 = "Shadow Highlight"
            java.lang.String r1 = r12.template
            r13.put(r0, r1)
            r12.readMap()
            int r13 = r12.toggleId
            if (r13 != 0) goto L_0x0624
            android.widget.ImageView r13 = r12.imageView
            android.graphics.Bitmap r0 = com.hqgames.pencil.sketch.photo.PhotoActivity.bitmap
            java.lang.String r1 = r12.template
            android.graphics.Bitmap r0 = org.wysaid.nativePort.CGENativeLibrary.filterImage_MultipleEffects(r0, r1, r11)
            r13.setImageBitmap(r0)
            goto L_0x0631
        L_0x0624:
            android.widget.ImageView r13 = r12.imageView
            android.graphics.Bitmap r0 = com.hqgames.pencil.sketch.photo.PhotoActivity.effectFilterbitmap
            java.lang.String r1 = r12.template
            android.graphics.Bitmap r0 = org.wysaid.nativePort.CGENativeLibrary.filterImage_MultipleEffects(r0, r1, r11)
            r13.setImageBitmap(r0)
        L_0x0631:
            java.lang.String r13 = "key value"
            java.lang.String r0 = r12.template
            android.util.Log.d(r13, r0)
            goto L_0x07d3
        L_0x063a:
            java.lang.String r0 = "@adjust level"
            r12.template = r0
        L_0x063e:
            java.util.List<com.warkiz.widget.IndicatorSeekBar> r0 = r12.seekBarList
            int r0 = r0.size()
            if (r9 >= r0) goto L_0x0688
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = r12.template
            r0.append(r1)
            java.lang.String r1 = " "
            r0.append(r1)
            java.util.List<com.warkiz.widget.IndicatorSeekBar> r1 = r12.seekBarList
            java.lang.Object r1 = r1.get(r9)
            com.warkiz.widget.IndicatorSeekBar r1 = (com.warkiz.widget.IndicatorSeekBar) r1
            int r1 = r1.getProgress()
            float r1 = (float) r1
            float r1 = r1 / r10
            java.lang.String r1 = java.lang.String.valueOf(r1)
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            r12.template = r0
            java.util.List<com.warkiz.widget.IndicatorSeekBar> r0 = r12.seekBarList
            java.lang.Object r0 = r0.get(r9)
            com.warkiz.widget.IndicatorSeekBar r0 = (com.warkiz.widget.IndicatorSeekBar) r0
            int r0 = r0.getProgress()
            float r0 = (float) r0
            float r0 = r0 / r10
            java.lang.Float r0 = java.lang.Float.valueOf(r0)
            r13.add(r0)
            int r9 = r9 + 1
            goto L_0x063e
        L_0x0688:
            util.FilterValuesHolder r0 = r12.filterValuesHolder
            java.lang.String r1 = r12.filterName
            r0.addFilterwithValue(r1, r13)
            java.util.LinkedHashMap<java.lang.String, java.lang.String> r13 = r12.filterMap
            java.lang.String r0 = "Levels"
            java.lang.String r1 = r12.template
            r13.put(r0, r1)
            r12.readMap()
            int r13 = r12.toggleId
            if (r13 != 0) goto L_0x06ad
            android.widget.ImageView r13 = r12.imageView
            android.graphics.Bitmap r0 = com.hqgames.pencil.sketch.photo.PhotoActivity.bitmap
            java.lang.String r1 = r12.template
            android.graphics.Bitmap r0 = org.wysaid.nativePort.CGENativeLibrary.filterImage_MultipleEffects(r0, r1, r11)
            r13.setImageBitmap(r0)
            goto L_0x06ba
        L_0x06ad:
            android.widget.ImageView r13 = r12.imageView
            android.graphics.Bitmap r0 = com.hqgames.pencil.sketch.photo.PhotoActivity.effectFilterbitmap
            java.lang.String r1 = r12.template
            android.graphics.Bitmap r0 = org.wysaid.nativePort.CGENativeLibrary.filterImage_MultipleEffects(r0, r1, r11)
            r13.setImageBitmap(r0)
        L_0x06ba:
            java.lang.String r13 = "key value"
            java.lang.String r0 = r12.template
            android.util.Log.d(r13, r0)
            goto L_0x07d3
        L_0x06c3:
            java.lang.String r0 = "@style haze"
            r12.template = r0
        L_0x06c7:
            java.util.List<com.warkiz.widget.IndicatorSeekBar> r0 = r12.seekBarList
            int r0 = r0.size()
            if (r9 >= r0) goto L_0x0711
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = r12.template
            r0.append(r1)
            java.lang.String r1 = " "
            r0.append(r1)
            java.util.List<com.warkiz.widget.IndicatorSeekBar> r1 = r12.seekBarList
            java.lang.Object r1 = r1.get(r9)
            com.warkiz.widget.IndicatorSeekBar r1 = (com.warkiz.widget.IndicatorSeekBar) r1
            int r1 = r1.getProgress()
            float r1 = (float) r1
            float r1 = r1 / r10
            java.lang.String r1 = java.lang.String.valueOf(r1)
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            r12.template = r0
            java.util.List<com.warkiz.widget.IndicatorSeekBar> r0 = r12.seekBarList
            java.lang.Object r0 = r0.get(r9)
            com.warkiz.widget.IndicatorSeekBar r0 = (com.warkiz.widget.IndicatorSeekBar) r0
            int r0 = r0.getProgress()
            float r0 = (float) r0
            float r0 = r0 / r10
            java.lang.Float r0 = java.lang.Float.valueOf(r0)
            r13.add(r0)
            int r9 = r9 + 1
            goto L_0x06c7
        L_0x0711:
            util.FilterValuesHolder r0 = r12.filterValuesHolder
            java.lang.String r1 = r12.filterName
            r0.addFilterwithValue(r1, r13)
            java.util.LinkedHashMap<java.lang.String, java.lang.String> r13 = r12.filterMap
            java.lang.String r0 = "Haze"
            java.lang.String r1 = r12.template
            r13.put(r0, r1)
            r12.readMap()
            int r13 = r12.toggleId
            if (r13 != 0) goto L_0x0736
            android.widget.ImageView r13 = r12.imageView
            android.graphics.Bitmap r0 = com.hqgames.pencil.sketch.photo.PhotoActivity.bitmap
            java.lang.String r1 = r12.template
            android.graphics.Bitmap r0 = org.wysaid.nativePort.CGENativeLibrary.filterImage_MultipleEffects(r0, r1, r11)
            r13.setImageBitmap(r0)
            goto L_0x0743
        L_0x0736:
            android.widget.ImageView r13 = r12.imageView
            android.graphics.Bitmap r0 = com.hqgames.pencil.sketch.photo.PhotoActivity.effectFilterbitmap
            java.lang.String r1 = r12.template
            android.graphics.Bitmap r0 = org.wysaid.nativePort.CGENativeLibrary.filterImage_MultipleEffects(r0, r1, r11)
            r13.setImageBitmap(r0)
        L_0x0743:
            java.lang.String r13 = "key value"
            java.lang.String r0 = r12.template
            android.util.Log.d(r13, r0)
            goto L_0x07d3
        L_0x074c:
            java.lang.String r0 = "@adjust hsl"
            r12.template = r0
        L_0x0750:
            java.util.List<com.warkiz.widget.IndicatorSeekBar> r0 = r12.seekBarList
            int r0 = r0.size()
            if (r9 >= r0) goto L_0x079a
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = r12.template
            r0.append(r1)
            java.lang.String r1 = " "
            r0.append(r1)
            java.util.List<com.warkiz.widget.IndicatorSeekBar> r1 = r12.seekBarList
            java.lang.Object r1 = r1.get(r9)
            com.warkiz.widget.IndicatorSeekBar r1 = (com.warkiz.widget.IndicatorSeekBar) r1
            int r1 = r1.getProgress()
            float r1 = (float) r1
            float r1 = r1 / r10
            java.lang.String r1 = java.lang.String.valueOf(r1)
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            r12.template = r0
            java.util.List<com.warkiz.widget.IndicatorSeekBar> r0 = r12.seekBarList
            java.lang.Object r0 = r0.get(r9)
            com.warkiz.widget.IndicatorSeekBar r0 = (com.warkiz.widget.IndicatorSeekBar) r0
            int r0 = r0.getProgress()
            float r0 = (float) r0
            float r0 = r0 / r10
            java.lang.Float r0 = java.lang.Float.valueOf(r0)
            r13.add(r0)
            int r9 = r9 + 1
            goto L_0x0750
        L_0x079a:
            util.FilterValuesHolder r0 = r12.filterValuesHolder
            java.lang.String r1 = r12.filterName
            r0.addFilterwithValue(r1, r13)
            java.util.LinkedHashMap<java.lang.String, java.lang.String> r13 = r12.filterMap
            java.lang.String r0 = "HSL"
            java.lang.String r1 = r12.template
            r13.put(r0, r1)
            r12.readMap()
            int r13 = r12.toggleId
            if (r13 != 0) goto L_0x07bf
            android.widget.ImageView r13 = r12.imageView
            android.graphics.Bitmap r0 = com.hqgames.pencil.sketch.photo.PhotoActivity.bitmap
            java.lang.String r1 = r12.template
            android.graphics.Bitmap r0 = org.wysaid.nativePort.CGENativeLibrary.filterImage_MultipleEffects(r0, r1, r11)
            r13.setImageBitmap(r0)
            goto L_0x07cc
        L_0x07bf:
            android.widget.ImageView r13 = r12.imageView
            android.graphics.Bitmap r0 = com.hqgames.pencil.sketch.photo.PhotoActivity.effectFilterbitmap
            java.lang.String r1 = r12.template
            android.graphics.Bitmap r0 = org.wysaid.nativePort.CGENativeLibrary.filterImage_MultipleEffects(r0, r1, r11)
            r13.setImageBitmap(r0)
        L_0x07cc:
            java.lang.String r13 = "key value"
            java.lang.String r0 = r12.template
            android.util.Log.d(r13, r0)
        L_0x07d3:
            androidx.fragment.app.FragmentActivity r13 = r12.getActivity()
            com.hqgames.pencil.sketch.photo.PhotoActivity r13 = (com.hqgames.pencil.sketch.photo.PhotoActivity) r13
            com.hqgames.pencil.sketch.photo.EffectFilterDetails r13 = r13.getEffectFilterDetails()
            java.lang.String r0 = r12.template
            r13.setFilterString(r0)
            androidx.fragment.app.FragmentActivity r13 = r12.getActivity()
            com.hqgames.pencil.sketch.photo.PhotoActivity r13 = (com.hqgames.pencil.sketch.photo.PhotoActivity) r13
            com.hqgames.pencil.sketch.photo.EffectFilterDetails r13 = r13.getEffectFilterDetails()
            util.FilterValuesHolder r0 = r12.filterValuesHolder
            r13.setFilterValuesHolder(r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: p049ui.EditorFragment.onStopTrackingTouch(com.warkiz.widget.IndicatorSeekBar):void");
    }

    public GPUImageFilter getFilter(FilterType filterType2) {
        if (filterType2 == FilterType.BRIGHTNESS) {
            return new GPUImageBrightnessFilter(this.filterHolder.getValue(filterType2));
        }
        if (filterType2 == FilterType.CONTRAST) {
            return new GPUImageContrastFilter(this.filterHolder.getValue(filterType2) * 10.0f);
        }
        if (filterType2 == FilterType.SATURATION) {
            return new GPUImageSaturationFilter(this.filterHolder.getValue(filterType2) * 10.0f);
        }
        if (filterType2 == FilterType.HUE) {
            return new GPUImageHueFilter(this.filterHolder.getValue(filterType2) * 100.0f);
        }
        if (filterType2 == FilterType.SHARPNESS) {
            return new GPUImageSharpenFilter(this.filterHolder.getValue(filterType2));
        }
        if (filterType2 == FilterType.BLUR) {
            return new GPUImageGaussianBlurFilter(this.filterHolder.getValue(filterType2));
        }
        if (filterType2 != FilterType.PIXEL) {
            return null;
        }
        GPUImagePixelationFilter gPUImagePixelationFilter = new GPUImagePixelationFilter();
        gPUImagePixelationFilter.setPixel(this.filterHolder.getValue(filterType2) * 15.0f);
        return gPUImagePixelationFilter;
    }

    /* access modifiers changed from: package-private */
    public void readMap() {
        this.template = "";
        for (String next : this.filterMap.keySet()) {
            System.out.println("key : " + next);
            System.out.println("value : " + this.filterMap.get(next));
            this.template += " " + this.filterMap.get(next);
        }
    }

    /* access modifiers changed from: package-private */
    public void clearData() {
        if (this.filterMap.size() > 0) {
            this.filterMap.clear();
        }
        this.filterValuesHolder.clear();
        this.filterValuesHolder = null;
        this.filterValuesHolder = new FilterValuesHolder();
    }

    public void onToggleSwitchChanged(int i) {
        if (i == 0) {
            if (this.toggleId != 0) {
                clearData();
                resetRecyclerView();
                try {
                    PhotoActivity.bitmap = (Bitmap) new EffectFilterTask(Registry.BUCKET_BITMAP, ((PhotoActivity) getActivity()).getEffectFilterDetails(), (Bitmap) null, getActivity(), this, (Dialog) null).execute(new Void[0]).get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e2) {
                    e2.printStackTrace();
                }
                this.imageView.setImageBitmap(PhotoActivity.bitmap);
            }
            if (((PhotoActivity) getActivity()).getEffectFilterDetails() != null) {
                ((PhotoActivity) getActivity()).getEffectFilterDetails().setEditorFilterSelected(false);
                ((PhotoActivity) getActivity()).getEffectFilterDetails().setEditorEffectSelected(false);
            }
            this.toggleId = 0;
        } else if (i == 1) {
            resetRecyclerView();
            if (((PhotoActivity) getActivity()).getEffectFilterDetails() != null) {
                if (((PhotoActivity) getActivity()).getEffectFilterDetails().isEffectSelected()) {
                    try {
                        PhotoActivity.effectFilterbitmap = (Bitmap) new EffectFilterTask("Effect", ((PhotoActivity) getActivity()).getEffectFilterDetails(), PhotoActivity.bitmap, getActivity(), this, (Dialog) null).execute(new Void[0]).get();
                    } catch (ExecutionException e3) {
                        e3.printStackTrace();
                    } catch (InterruptedException e4) {
                        e4.printStackTrace();
                    }
                    this.imageView.setImageBitmap(PhotoActivity.effectFilterbitmap);
                    Log.d("EditorFragment effect", "effect selected");
                    this.toggleId = 1;
                    clearData();
                } else {
                    this.toggleId = 0;
                    this.toggleSwitch.setCheckedPosition(0);
                    try {
                        PhotoActivity.bitmap = (Bitmap) new EffectFilterTask(Registry.BUCKET_BITMAP, ((PhotoActivity) getActivity()).getEffectFilterDetails(), PhotoActivity.bitmap, getActivity(), this, (Dialog) null).execute(new Void[0]).get();
                    } catch (ExecutionException e5) {
                        e5.printStackTrace();
                    } catch (InterruptedException e6) {
                        e6.printStackTrace();
                    }
                    this.imageView.setImageBitmap(PhotoActivity.bitmap);
                    Toast.makeText(getActivity(), "No Effect Selected", Toast.LENGTH_SHORT).show();
                    clearData();
                }
            }
            this.toggleId = 1;
            if (((PhotoActivity) getActivity()).getEffectFilterDetails() != null) {
                ((PhotoActivity) getActivity()).getEffectFilterDetails().setEditorEffectSelected(true);
                ((PhotoActivity) getActivity()).getEffectFilterDetails().setEditorFilterSelected(false);
            }
        } else if (i == 2) {
            this.toggleId = 2;
            if (((PhotoActivity) getActivity()).getEffectFilterDetails() != null) {
                if (!((PhotoActivity) getActivity()).getEffectFilterDetails().isEffecttofilter() || !((PhotoActivity) getActivity()).getEffectFilterDetails().isEffectSelected()) {
                    PhotoActivity.ShowMessage("EffecttoFilter", "  false");
                    if (((PhotoActivity) getActivity()).getEffectFilterDetails() != null) {
                        if (((PhotoActivity) getActivity()).getEffectFilterDetails().isFilterSelected()) {
                            try {
                                PhotoActivity.effectFilterbitmap = (Bitmap) new EffectFilterTask("Filter", ((PhotoActivity) getActivity()).getEffectFilterDetails(), PhotoActivity.bitmap, getActivity(), this, (Dialog) null).execute(new Void[0]).get();
                            } catch (ExecutionException e7) {
                                e7.printStackTrace();
                            } catch (InterruptedException e8) {
                                e8.printStackTrace();
                            }
                            this.imageView.setImageBitmap(PhotoActivity.effectFilterbitmap);
                        } else {
                            this.toggleSwitch.setCheckedPosition(0);
                            this.toggleId = 0;
                        }
                    }
                    clearData();
                } else {
                    try {
                        PhotoActivity.effectFilterbitmap = (Bitmap) new EffectFilterTask("Filter", ((PhotoActivity) getActivity()).getEffectFilterDetails(), PhotoActivity.bitmap, getActivity(), this, (Dialog) null).execute(new Void[0]).get();
                    } catch (ExecutionException e9) {
                        e9.printStackTrace();
                    } catch (InterruptedException e10) {
                        e10.printStackTrace();
                    }
                    this.imageView.setImageBitmap(PhotoActivity.effectFilterbitmap);
                    clearData();
                }
                if (((PhotoActivity) getActivity()).getEffectFilterDetails() != null) {
                    ((PhotoActivity) getActivity()).getEffectFilterDetails().setEditorFilterSelected(true);
                    ((PhotoActivity) getActivity()).getEffectFilterDetails().setEditorEffectSelected(false);
                }
            }
        }
        if (((PhotoActivity) getActivity()).getEffectFilterDetails() != null) {
            ((PhotoActivity) getActivity()).getEffectFilterDetails().setFilterString((String) null);
        }
        resetRecyclerView();
        if (this.layout != null) {
            this.screenLayout.removeView(this.layout);
        }
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.toggleLayout.getLayoutParams();
        layoutParams.addRule(2, this.seekBarView.getId());
        layoutParams.bottomMargin = ((PhotoActivity) getActivity()).dpToPx(15);
        this.toggleLayout.setLayoutParams(layoutParams);
        this.filterMap.clear();
        this.filterValuesHolder = null;
        ((PhotoActivity) getActivity()).getEffectFilterDetails().setFilterString((String) null);
        ((PhotoActivity) getActivity()).getEffectFilterDetails().setFilterValuesHolder((FilterValuesHolder) null);
        this.filterValuesHolder = new FilterValuesHolder();
    }

    private void resetRecyclerView() {
        this.editorClicked = false;
        this.seekBarView.setVisibility(View.GONE);
        this.mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        this.recyclerView.setLayoutManager(this.mLayoutManager);
        this.mAdapter = new EditorOptionsAdapter(getActivity(), this.editor_items, this);
        this.recyclerView.setAdapter(this.mAdapter);
        this.mAdapter.UncheckAll();
        this.mAdapter.notifyDataSetChanged();
    }

    public void OnTaskComplete() {
        if (((PhotoActivity) getActivity()).getEffectFilterDetails() != null) {
            if (((PhotoActivity) getActivity()).getEffectFilterDetails().getFilterString() != null) {
                this.template = ((PhotoActivity) getActivity()).getEffectFilterDetails().getFilterString();
                if (PhotoActivity.effectFilterbitmap != null) {
                    this.imageView.setImageBitmap(CGENativeLibrary.filterImage_MultipleEffects(PhotoActivity.effectFilterbitmap, this.template, 1.0f));
                    Log.d("EditorFragment", " task first");
                } else if (PhotoActivity.bitmap != null) {
                    this.imageView.setImageBitmap(CGENativeLibrary.filterImage_MultipleEffects(PhotoActivity.bitmap, this.template, 1.0f));
                    Log.d("EditorFragment", " task 2");
                } else {
                    Log.d("EditorFragment", " task 3");
                }
            } else if (PhotoActivity.effectFilterbitmap != null && this.toggleId == 1) {
                this.imageView.setImageBitmap(PhotoActivity.effectFilterbitmap);
                Log.d("EditorFragment", " task 4");
                this.temp = PhotoActivity.effectFilterbitmap;
            } else if (PhotoActivity.bitmap != null && this.toggleId == 0) {
                this.imageView.setImageBitmap(PhotoActivity.bitmap);
                Log.d("EditorFragment", " task 5");
            }
        }
        if (PhotoActivity.effectFilterbitmap != null) {
            Log.d("toggleparams", "effect bitmap not null");
        } else {
            Log.d("toggleparams", "effect bitmap  null");
        }
    }

    public void OnReset() {
        Log.d("ResetListener", "EditorFragment");
        resetRecyclerView();
        try {
            PhotoActivity.bitmap = (Bitmap) new EffectFilterTask(Registry.BUCKET_BITMAP, ((PhotoActivity) getActivity()).getEffectFilterDetails(), (Bitmap) null, getActivity(), (TaskCompleteListener) null, (Dialog) null).execute(new Void[0]).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e2) {
            e2.printStackTrace();
        }
        this.toggleSwitch.setCheckedPosition(0);
        this.toggleId = 0;
        if (this.layout != null) {
            this.screenLayout.removeView(this.layout);
        }
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.toggleLayout.getLayoutParams();
        layoutParams.addRule(2, this.seekBarView.getId());
        layoutParams.bottomMargin = ((PhotoActivity) getActivity()).dpToPx(15);
        this.toggleLayout.setLayoutParams(layoutParams);
        this.filterMap.clear();
        this.filterValuesHolder.clear();
        this.filterValuesHolder = null;
        this.filterValuesHolder = new FilterValuesHolder();
        this.imageView.setImageBitmap(PhotoActivity.bitmap);
        if (((PhotoActivity) getActivity()).getEffectFilterDetails() != null) {
            ((PhotoActivity) getActivity()).getEffectFilterDetails().setEditorFilterSelected(false);
            ((PhotoActivity) getActivity()).getEffectFilterDetails().setEditorEffectSelected(false);
        }
        if (this.resetButtonListener != null) {
            this.resetButtonListener.OnResetButtonDeActivate();
        }
        ((PhotoActivity) getActivity()).getEffectFilterDetails().setFilterString((String) null);
    }

    public void onClick(View view) {
        view.getId();
    }

    public void getBitmap() {
        switch (this.toggleId) {
            case 0:
                PhotoActivity.ShowMessage("ToggleId", " 0 ");
                try {
                    PhotoActivity.bitmap = (Bitmap) new EffectFilterTask(Registry.BUCKET_BITMAP, ((PhotoActivity) getActivity()).getEffectFilterDetails(), (Bitmap) null, getActivity(), this, (Dialog) null).execute(new Void[0]).get();
                    break;
                } catch (ExecutionException e) {
                    e.printStackTrace();
                    break;
                } catch (InterruptedException e2) {
                    e2.printStackTrace();
                    break;
                }
            case 1:
                PhotoActivity.ShowMessage("ToggleId", " 1 ");
                if (((PhotoActivity) getActivity()).getEffectFilterDetails() != null) {
                    if (!((PhotoActivity) getActivity()).getEffectFilterDetails().isEffectSelected()) {
                        this.toggleId = 0;
                        this.toggleSwitch.setCheckedPosition(0);
                        try {
                            PhotoActivity.bitmap = (Bitmap) new EffectFilterTask(Registry.BUCKET_BITMAP, ((PhotoActivity) getActivity()).getEffectFilterDetails(), (Bitmap) null, getActivity(), this, (Dialog) null).execute(new Void[0]).get();
                        } catch (ExecutionException e3) {
                            e3.printStackTrace();
                        } catch (InterruptedException e4) {
                            e4.printStackTrace();
                        }
                        Toast.makeText(getActivity(), "No Effect Selected", Toast.LENGTH_SHORT).show();
                        break;
                    } else {
                        try {
                            PhotoActivity.effectFilterbitmap = (Bitmap) new EffectFilterTask("Effect", ((PhotoActivity) getActivity()).getEffectFilterDetails(), PhotoActivity.bitmap, getActivity(), this, (Dialog) null).execute(new Void[0]).get();
                        } catch (ExecutionException e5) {
                            e5.printStackTrace();
                        } catch (InterruptedException e6) {
                            e6.printStackTrace();
                        }
                        this.toggleId = 1;
                        break;
                    }
                }
                break;
            case 2:
                PhotoActivity.ShowMessage("ToggleId", " 2 ");
                if (((PhotoActivity) getActivity()).getEffectFilterDetails() != null) {
                    if (((PhotoActivity) getActivity()).getEffectFilterDetails().isEffecttofilter() && ((PhotoActivity) getActivity()).getEffectFilterDetails().isEffectSelected()) {
                        try {
                            PhotoActivity.effectFilterbitmap = (Bitmap) new EffectFilterTask("Filter", ((PhotoActivity) getActivity()).getEffectFilterDetails(), PhotoActivity.bitmap, getActivity(), this, (Dialog) null).execute(new Void[0]).get();
                        } catch (ExecutionException e7) {
                            e7.printStackTrace();
                        } catch (InterruptedException e8) {
                            e8.printStackTrace();
                        }
                        PhotoActivity.ShowMessage("EffecttoFilter", "  true");
                        this.toggleId = 2;
                        break;
                    } else {
                        PhotoActivity.ShowMessage("EffecttoFilter", "  false");
                        if (((PhotoActivity) getActivity()).getEffectFilterDetails() != null) {
                            if (!((PhotoActivity) getActivity()).getEffectFilterDetails().isFilterSelected()) {
                                try {
                                    PhotoActivity.bitmap = (Bitmap) new EffectFilterTask(Registry.BUCKET_BITMAP, ((PhotoActivity) getActivity()).getEffectFilterDetails(), (Bitmap) null, getActivity(), this, (Dialog) null).execute(new Void[0]).get();
                                } catch (ExecutionException e9) {
                                    e9.printStackTrace();
                                } catch (InterruptedException e10) {
                                    e10.printStackTrace();
                                }
                                this.toggleSwitch.setCheckedPosition(0);
                                this.toggleId = 0;
                                break;
                            } else {
                                try {
                                    PhotoActivity.effectFilterbitmap = (Bitmap) new EffectFilterTask("Filter", ((PhotoActivity) getActivity()).getEffectFilterDetails(), PhotoActivity.bitmap, getActivity(), this, (Dialog) null).execute(new Void[0]).get();
                                } catch (ExecutionException e11) {
                                    e11.printStackTrace();
                                } catch (InterruptedException e12) {
                                    e12.printStackTrace();
                                }
                                this.toggleId = 2;
                                break;
                            }
                        }
                    }
                }
                break;
        }
        if (PhotoActivity.effectFilterbitmap != null) {
            PhotoActivity.ShowMessage("Bitmap effect", " not null ");
        } else {
            PhotoActivity.ShowMessage("Bitmap effect", "  null ");
        }
    }

    private void checkForAd() {
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

    /* access modifiers changed from: package-private */
    @SuppressLint({"ResourceType"})
    public void initializeView(EnhanceFilters enhanceFilters) {
        if (this.layout != null) {
            this.screenLayout.removeView(this.layout);
        }
        if (this.filterValueList != null) {
            this.filterValueList.clear();
        }
        if (this.seekBarList != null) {
            this.seekBarList.clear();
        }
        this.layout = new RelativeLayout(getActivity());
        int i = -1;
        int i2 = -2;
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, -2);
        layoutParams.addRule(2, R.id.my_recycler_view);
        layoutParams.bottomMargin = ((PhotoActivity) getActivity()).dpToPx(20);
        this.layout.setLayoutParams(layoutParams);
        this.layout.setId(2);
        this.screenLayout.addView(this.layout);
        RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) this.toggleLayout.getLayoutParams();
        layoutParams2.addRule(2, this.layout.getId());
        int i3 = 15;
        layoutParams2.bottomMargin = ((PhotoActivity) getActivity()).dpToPx(15);
        this.toggleLayout.setLayoutParams(layoutParams2);
        LinearLayout linearLayout = new LinearLayout(getActivity());
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
        int i4 = 1;
        linearLayout.setOrientation(1);
        TextView textView = new TextView(getActivity());
        textView.setGravity(17);
        textView.setText(enhanceFilters.getFilterTitle());
        textView.setTextSize(16.0f);
        textView.setTextColor(Color.parseColor("#FFFFFF"));
        linearLayout.addView(textView);
        linearLayout.setBackgroundColor(Color.parseColor("#80000d17"));
        this.layout.addView(linearLayout);
        this.filterName = enhanceFilters.getFilterTitle();
        int i5 = 0;
        int i6 = 0;
        while (i6 < enhanceFilters.getSeeks().size()) {
            RelativeLayout relativeLayout = new RelativeLayout(getActivity());
            relativeLayout.setLayoutParams(new RelativeLayout.LayoutParams(i, ((PhotoActivity) getActivity()).dpToPx(50)));
            linearLayout.addView(relativeLayout);
            RelativeLayout.LayoutParams layoutParams3 = new RelativeLayout.LayoutParams(i2, i2);
            RelativeLayout.LayoutParams layoutParams4 = new RelativeLayout.LayoutParams(i2, i2);
            RelativeLayout.LayoutParams layoutParams5 = new RelativeLayout.LayoutParams(i2, i2);
            RelativeLayout.LayoutParams layoutParams6 = new RelativeLayout.LayoutParams(i2, i2);
            RelativeLayout.LayoutParams layoutParams7 = new RelativeLayout.LayoutParams(i, i2);
            TextView textView2 = new TextView(getActivity());
            layoutParams3.setMargins(((PhotoActivity) getActivity()).dpToPx(8), ((PhotoActivity) getActivity()).dpToPx(5), i5, i5);
            layoutParams3.addRule(i3);
            textView2.setTextSize(13.0f);
            textView2.setText(enhanceFilters.getSeeks().get(i6));
            textView2.setLayoutParams(layoutParams3);
            textView2.setId(i6 + 201);
            textView2.setTextColor(Color.parseColor("#FFFFFF"));
            TextView textView3 = new TextView(getActivity());
            textView3.setTextSize(13.0f);
            if (i6 == 0) {
                //textView3.setId(R.id.filter_value_1);
                textView3.setId(Constants.FILTER_VALUE.FILTER_VALUE_1.getValue());
            } else if (i6 == i4) {
                textView3.setId(Constants.FILTER_VALUE.FILTER_VALUE_2.getValue());
            } else if (i6 == 2) {
                textView3.setId(Constants.FILTER_VALUE.FILTER_VALUE_3.getValue());
            } else if (i6 == 3) {
                textView3.setId(Constants.FILTER_VALUE.FILTER_VALUE_4.getValue());
            } else if (i6 == 4) {
                textView3.setId(Constants.FILTER_VALUE.FILTER_VALUE_5.getValue());
            }
            textView3.setTextColor(Color.parseColor("#FFFFFF"));
            if (Build.VERSION.SDK_INT < 16) {
                textView3.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.progress_text_layout));
            } else {
                textView3.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.progress_text_layout));
            }
            textView3.setPadding(((PhotoActivity) getActivity()).dpToPx(2), ((PhotoActivity) getActivity()).dpToPx(2), ((PhotoActivity) getActivity()).dpToPx(2), ((PhotoActivity) getActivity()).dpToPx(2));
            textView3.setWidth(((PhotoActivity) getActivity()).dpToPx(35));
            textView3.setGravity(17);
            layoutParams6.rightMargin = ((PhotoActivity) getActivity()).dpToPx(5);
            layoutParams6.addRule(15);
            layoutParams6.addRule(11);
            textView3.setLayoutParams(layoutParams6);
            IndicatorSeekBar build = IndicatorSeekBar.with(getActivity()).min(0.0f).max(100.0f).progress(50.0f).showIndicatorType(0).thumbColor(Color.parseColor("#ffffff")).thumbSize(15).onlyThumbDraggable(false).trackProgressColor(Color.parseColor("#ffffff")).trackProgressSize(4).trackBackgroundColor(Color.parseColor("#333333")).trackBackgroundSize(2).build();
            if (i6 == 0) {
                //build.setId(R.id.seek_1);
                build.setId(Constants.SEEK_BAR.SEEK_BAR_1.getValue());
            } else if (i6 == 1) {
                build.setId(Constants.SEEK_BAR.SEEK_BAR_2.getValue());
            } else if (i6 == 2) {
                build.setId(Constants.SEEK_BAR.SEEK_BAR_3.getValue());
            } else if (i6 == 3) {
                build.setId(Constants.SEEK_BAR.SEEK_BAR_4.getValue());
            } else if (i6 == 4) {
                build.setId(Constants.SEEK_BAR.SEEK_BAR_5.getValue());
            }
            TextView textView4 = new TextView(getActivity());
            layoutParams4.setMargins(((PhotoActivity) getActivity()).dpToPx(10), ((PhotoActivity) getActivity()).dpToPx(5), 0, 0);
            layoutParams4.addRule(15);
            layoutParams4.addRule(1, textView2.getId());
            textView4.setTextSize(13.0f);
            textView4.setText("-1");
            textView4.setLayoutParams(layoutParams4);
            textView4.setId(i6 + 202);
            textView4.setTextColor(Color.parseColor("#FFFFFF"));
            TextView textView5 = new TextView(getActivity());
            layoutParams5.setMargins(0, ((PhotoActivity) getActivity()).dpToPx(5), ((PhotoActivity) getActivity()).dpToPx(10), 0);
            layoutParams5.addRule(15);
            layoutParams5.addRule(0, textView3.getId());
            textView5.setTextSize(13.0f);
            textView5.setText("1");
            textView5.setLayoutParams(layoutParams5);
            textView5.setId(i6 + 205);
            textView5.setTextColor(Color.parseColor("#FFFFFF"));
            if (enhanceFilters.getFilterTitle().equals("HSL")) {
                build.setMax(100.0f);
                build.setMin(-100.0f);
                textView4.setText("-1");
                textView5.setText("1");
                textView3.setText(String.valueOf(0.5f));
                Log.d("Seek", "HSL");
            } else if (enhanceFilters.getFilterTitle().equals("Levels")) {
                build.setMax(100.0f);
                build.setMin(0.0f);
                build.setProgress(50.0f);
                textView4.setText(AppEventsConstants.EVENT_PARAM_VALUE_NO);
                textView5.setText("1");
            } else if (enhanceFilters.getFilterTitle().equals("Shadow Highlight")) {
                if (build.getId() == Constants.SEEK_BAR.SEEK_BAR_1.getValue()) {
                    build.setMax(100.0f);
                    build.setMin(-200.0f);
                    textView4.setText("-2");
                    textView5.setText("1");
                    textView3.setText(String.valueOf(AppEventsConstants.EVENT_PARAM_VALUE_NO));
                } else if (build.getId() == Constants.SEEK_BAR.SEEK_BAR_2.getValue()) {
                    build.setMax(200.0f);
                    build.setMin(-100.0f);
                    textView4.setText("-1");
                    textView5.setText("2");
                    textView3.setText(String.valueOf(AppEventsConstants.EVENT_PARAM_VALUE_NO));
                }
            } else if (enhanceFilters.getFilterTitle().equals("Color Balance")) {
                build.setMax(100.0f);
                build.setMin(-100.0f);
                build.setProgress(15.0f);
            } else if (enhanceFilters.getFilterTitle().equals("White Balance")) {
                if (build.getId() == Constants.SEEK_BAR.SEEK_BAR_1.getValue()) {
                    build.setMax(100.0f);
                    build.setMin(-100.0f);
                    textView3.setText(String.valueOf(AppEventsConstants.EVENT_PARAM_VALUE_NO));
                    textView4.setText("-1");
                    textView5.setText("1");
                } else if (build.getId() == Constants.SEEK_BAR.SEEK_BAR_2.getValue()) {
                    build.setMax(500.0f);
                    build.setMin(0.0f);
                    build.setProgress(100.0f);
                    textView3.setText(String.valueOf("1"));
                    textView4.setText(AppEventsConstants.EVENT_PARAM_VALUE_NO);
                    textView5.setText("5");
                }
            } else if (enhanceFilters.getFilterTitle().equals("Haze")) {
                if (build.getId() == Constants.SEEK_BAR.SEEK_BAR_1.getValue()) {
                    build.setMax(50.0f);
                    build.setMin(-50.0f);
                    build.setProgress(0.0f);
                    textView3.setText(String.valueOf(AppEventsConstants.EVENT_PARAM_VALUE_NO));
                    textView4.setText("-0.5");
                    textView5.setText("0.5");
                } else if (build.getId() == Constants.SEEK_BAR.SEEK_BAR_2.getValue()) {
                    build.setMax(50.0f);
                    build.setMin(-50.0f);
                    build.setProgress(0.0f);
                    textView3.setText(String.valueOf(AppEventsConstants.EVENT_PARAM_VALUE_NO));
                    textView4.setText("-0.5");
                    textView5.setText("0.5");
                } else if (build.getId() == Constants.SEEK_BAR.SEEK_BAR_3.getValue()) {
                    build.setMax(100.0f);
                    build.setMin(0.0f);
                    build.setProgress(100.0f);
                    textView3.setText(String.valueOf("1"));
                    textView4.setText(AppEventsConstants.EVENT_PARAM_VALUE_NO);
                    textView5.setText("1");
                } else if (build.getId() == Constants.SEEK_BAR.SEEK_BAR_4.getValue()) {
                    build.setMax(100.0f);
                    build.setMin(0.0f);
                    build.setProgress(100.0f);
                    textView3.setText(String.valueOf("1"));
                    textView4.setText(AppEventsConstants.EVENT_PARAM_VALUE_NO);
                    textView5.setText("1");
                } else if (build.getId() == Constants.SEEK_BAR.SEEK_BAR_5.getValue()) {
                    build.setMax(100.0f);
                    build.setMin(0.0f);
                    build.setProgress(100.0f);
                    textView3.setText(String.valueOf("1"));
                    textView4.setText(AppEventsConstants.EVENT_PARAM_VALUE_NO);
                    textView5.setText("1");
                }
            } else if (enhanceFilters.getFilterTitle().equals("Emboss")) {
                if (build.getId() == Constants.SEEK_BAR.SEEK_BAR_1.getValue()) {
                    build.setMax(100.0f);
                    build.setMin(0.0f);
                    build.setProgress(100.0f);
                    textView3.setText(String.valueOf("1"));
                    textView4.setText(AppEventsConstants.EVENT_PARAM_VALUE_NO);
                    textView5.setText("1");
                } else if (build.getId() == Constants.SEEK_BAR.SEEK_BAR_2.getValue()) {
                    build.setMax(500.0f);
                    build.setMin(0.0f);
                    build.setProgress(100.0f);
                    textView3.setText(String.valueOf("1"));
                    textView4.setText(AppEventsConstants.EVENT_PARAM_VALUE_NO);
                    textView5.setText("5");
                } else if (build.getId() == Constants.SEEK_BAR.SEEK_BAR_3.getValue()) {
                    build.setMax(500.0f);
                    build.setMin(0.0f);
                    build.setProgress(100.0f);
                    textView3.setText(String.valueOf("1"));
                    textView4.setText(AppEventsConstants.EVENT_PARAM_VALUE_NO);
                    textView5.setText("5");
                }
            }
            this.filterValueList.add(textView3);
            this.seekBarList.add(build);
            build.setOnSeekChangeListener(this);
            i3 = 15;
            layoutParams7.addRule(15);
            layoutParams7.addRule(1, textView4.getId());
            layoutParams7.addRule(0, textView5.getId());
            layoutParams7.leftMargin = ((PhotoActivity) getActivity()).dpToPx(5);
            layoutParams7.rightMargin = ((PhotoActivity) getActivity()).dpToPx(5);
            build.setLayoutParams(layoutParams7);
            relativeLayout.addView(textView4);
            relativeLayout.addView(textView5);
            relativeLayout.addView(textView2);
            relativeLayout.addView(textView3);
            relativeLayout.addView(build);
           // Log.d("seekBar", TtmlNode.TAG_LAYOUT);
            i6++;
            i = -1;
            i2 = -2;
            i4 = 1;
            i5 = 0;
        }
    }
}