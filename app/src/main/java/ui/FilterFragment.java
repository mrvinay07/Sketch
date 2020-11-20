package ui;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Registry;
import com.teamvinay.sketch.App;

import com.teamvinay.sketch.EffectFilterTask;
import com.teamvinay.sketch.Filter;
import com.teamvinay.sketch.FilterAdapter;
import com.teamvinay.sketch.MainActivity;
import com.teamvinay.sketch.PhotoActivity;
import com.llollox.androidtoggleswitch.widgets.ToggleSwitch;
import com.teamvinay.sketch.R;

import filters.IF1977Filter;
import filters.IFAmaroFilter;
import filters.IFBrannanFilter;
import filters.IFEarlybirdFilter;
import filters.IFHefeFilter;
import filters.IFHudsonFilter;
import filters.IFInkwellFilter;
import filters.IFLomoFilter;
import filters.IFLordKelvinFilter;
import filters.IFNashvilleFilter;
import filters.IFRiseFilter;
import filters.IFSierraFilter;
import filters.IFSutroFilter;
import filters.IFToasterFilter;
import filters.IFValenciaFilter;
import filters.IFWaldenFilter;
import filters.IFXprollFilter;
import helper.Constants;
import helper.ExportDetails;
import helper.FilterButtonListener;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import listeners.ResetButtonListener;
import listeners.ResetListener;
import listeners.TaskCompleteListener;
import org.opencv.core.Mat;
import org.wysaid.nativePort.CGENativeLibrary;
import jp.co.cyberagent.android.gpuimage.GPUImage;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageLookupFilter;
import util.AdUtil;
import util.BitmapCache;
import util.FireBaseHelper;
import util.NativeAdUtil;
import util.Utils;

/* renamed from: ui.FilterFragment */
public class FilterFragment extends Fragment implements FilterButtonListener, ToggleSwitch.OnChangeListener, View.OnClickListener, ResetListener, TaskCompleteListener {
    public Bitmap bitmap;
    String[] constants = {"@adjust hsv -0.7 -0.7 0.5 -0.7 -0.7 0.5 @pixblend ol 0.243 0.07059 0.59215 1 25", "@adjust hsv 0.10678917 0.14523578 0.71266866 0.009352684 0.34604204 0.8423001 @pixblend ol 0.61192507 0.66061425 0.96537817 1 18", "@adjust hsv 0.08683282 0.8324069 0.21115458 0.96259296 0.3223461 0.33664382 @pixblend ol 0.87732095 0.62007666 0.4117298 1 27", "@curve R(170, 46)(254, 89)(247, 204)(129, 91)(29, 198) @adjust hsv 0.22157013 0.7836555 0.95315063 0.88177353 0.5007757 0.17895842", "@curve R(153, 56)(153, 110)(138, 250)(117, 179)(246, 193) @adjust hsv 0.13393426 0.2909866 0.39522433 0.44626093 0.025464356 0.066244185 @pixblend ol 0.7757735 0.75961334 0.91061693 1 20", "@adjust saturation 0 @curve R(0, 68)(10, 72)(42, 135)(72, 177)(98, 201)(220, 255)G(0, 29)(12, 30)(57, 127)(119, 203)(212, 255)(254, 239)B(0, 36)(54, 118)(66, 141)(119, 197)(155, 215)(255, 254)", "@curve R(0, 64)(16, 13)(58, 128)(108, 109)(162, 223)(255, 255)G(0, 30)(22, 35)(42, 58)(56, 86)(70, 119)(130, 184)(189, 212)B(6, 36)(76, 157)(107, 192)(173, 229)(255, 255)"};
    private Context context;
    private int effectIndex = -1;
    Dialog effectLoadingDialog;
    /* access modifiers changed from: private */
    public Filter filterC;
    private List<Object> filterList = new ArrayList();
    /* access modifiers changed from: private */
    public boolean filterSelected = false;
    /* access modifiers changed from: private */
    public boolean freshStart = true;
    /* access modifiers changed from: private */
    public GPUImage gpuImage;
    /* access modifiers changed from: private */
    public ImageView imageView;
    GPUImageLookupFilter lookupFilter;
    GPUImageLookupFilter lookupFilter1;
    GPUImageLookupFilter lookupFilter2;
    /* access modifiers changed from: private */
    public FilterAdapter mAdapter;
    /* access modifiers changed from: private */
    public RecyclerView.LayoutManager mLayoutManager;
    /* access modifiers changed from: private */
    public RecyclerView recyclerView;
    /* access modifiers changed from: private */
    public ResetButtonListener resetButtonListener;
    /* access modifiers changed from: private */
    public boolean resumeTask = false;
    Bitmap temp = null;
    /* access modifiers changed from: private */
    public int toggleId = 0;
    /* access modifiers changed from: private */
    public ToggleSwitch toggleSwitch;

    public void OnTaskComplete() {
    }

    public void setResetButtonListener(ResetButtonListener resetButtonListener2) {
        this.resetButtonListener = resetButtonListener2;
    }

    public void onAttach(@NonNull Context context2) {
        super.onAttach(this.context);
        this.context = context2;
        if (this.context != null) {
            if (this.bitmap == null) {
                this.bitmap = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.filter_drawable);
            }
            if (this.lookupFilter == null) {
                this.lookupFilter = new GPUImageLookupFilter();
                this.lookupFilter.setBitmap(BitmapFactory.decodeResource(this.context.getResources(), R.drawable.filmstock));
            }
            if (this.lookupFilter1 == null) {
                this.lookupFilter1 = new GPUImageLookupFilter();
                this.lookupFilter1.setBitmap(BitmapFactory.decodeResource(this.context.getResources(), R.drawable.wildbird));
                return;
            }
            return;
        }
        if (this.bitmap == null) {
            this.bitmap = BitmapFactory.decodeResource(App.getInstance().getApplicationContext().getResources(), R.drawable.filter_drawable);
        }
        if (this.lookupFilter == null) {
            this.lookupFilter = new GPUImageLookupFilter();
            this.lookupFilter.setBitmap(BitmapFactory.decodeResource(App.getInstance().getApplicationContext().getResources(), R.drawable.filmstock));
        }
        if (this.lookupFilter1 == null) {
            this.lookupFilter1 = new GPUImageLookupFilter();
            this.lookupFilter1.setBitmap(BitmapFactory.decodeResource(App.getInstance().getApplicationContext().getResources(), R.drawable.wildbird));
        }
    }

    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
    }

    @Nullable
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.filter_fragment, viewGroup, false);
        this.recyclerView = (RecyclerView) inflate.findViewById(R.id.my_recycler_view);
        this.imageView = (ImageView) inflate.findViewById(R.id.gpuimage);
        this.imageView.setOnClickListener(this);
        this.toggleSwitch = (ToggleSwitch) inflate.findViewById(R.id.toggle_switch);
        this.toggleSwitch.setOnChangeListener(this);
        this.toggleSwitch.setCheckedPosition(0);
        if (this.freshStart) {
            new StartUpLoadingTask(getActivity()).execute(new Void[0]);
            Log.d("FilterFragment", "fresh start");
        }
        return inflate;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle bundle) {
        super.onViewCreated(view, bundle);
    }

    public void onStart() {
        super.onStart();
        PhotoActivity.ShowMessage("FilterFragment ", "onStart");
    }

    public void onResume() {
        super.onResume();
        ExportDetails.getInstance().setFragment_title("FilterFragment");
        new Handler().postDelayed(new Runnable() {
            public void run() {
                FilterFragment.this.Resume();
            }
        }, 300);
    }

    public void onPause() {
        super.onPause();
        PhotoActivity.ShowMessage("FilterFragment ", "onPause");
        this.imageView.setImageBitmap((Bitmap) null);
        resetSequence();
    }

    public void onStop() {
        super.onStop();
        PhotoActivity.ShowMessage("FilterFragment ", "onStop");
    }

    public void onDestroyView() {
        super.onDestroyView();
        this.imageView.setImageBitmap((Bitmap) null);
        this.temp = null;
    }

    public void onDestroy() {
        super.onDestroy();
    }

    public void onDetach() {
        super.onDetach();
    }

    /* access modifiers changed from: package-private */
    public void Resume() {
        if (this.toggleId == 1) {
            PhotoActivity.ShowMessage("FilterFragment ", "togglee 1");
            this.toggleId = 0;
            if (((PhotoActivity) getActivity()).getEffectFilterDetails() != null) {
                if (!((PhotoActivity) getActivity()).getEffectFilterDetails().isEffectSelected()) {
                    PhotoActivity.ShowMessage("FilterFragment ", "togglee 11 ");
                    this.toggleSwitch.setCheckedPosition(0);
                    setImageViewOriginalBitmap();
                } else {
                    this.toggleId = 1;
                }
            }
        }
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                Log.d("EffectContext", "context");
                if (FilterFragment.this.getActivity() != null) {
                    new ResumeAsycTask(FilterFragment.this.getActivity()).execute(new Void[0]);
                    ((PhotoActivity) FilterFragment.this.getActivity()).setResetListener(FilterFragment.this);
                    return;
                }
                handler.postDelayed(this, 50);
            }
        }, 100);
    }

    public Bitmap getFilteredBitmap(GPUImageFilter gPUImageFilter) {
        Log.d("FilterFragment bit", String.valueOf(this.bitmap.getWidth()));
        this.gpuImage.setImage(this.bitmap);
        this.gpuImage.setFilter(gPUImageFilter);
        return this.gpuImage.getBitmapWithFilterApplied();
    }

    public Bitmap getFilteredBitmap(String str) {
        Log.d("FilterFragment bit", String.valueOf(this.bitmap.getWidth()));
        return CGENativeLibrary.filterImage_MultipleEffects(Utils.convert(this.bitmap.copy(this.bitmap.getConfig(), true), Bitmap.Config.ARGB_8888), str, 1.0f);
    }

    /* access modifiers changed from: private */
    public void initializeContent() {
        this.filterList.add(new Filter("Hello", getFilteredBitmap((GPUImageFilter) new IF1977Filter(this.context)), (GPUImageFilter) new IF1977Filter(this.context)));
        this.filterList.add(new Filter("Hello", getFilteredBitmap((GPUImageFilter) new IFAmaroFilter(this.context)), (GPUImageFilter) new IFAmaroFilter(this.context)));
        this.filterList.add(new Filter("Hello", getFilteredBitmap((GPUImageFilter) new IFBrannanFilter(this.context)), (GPUImageFilter) new IFBrannanFilter(this.context)));
        this.filterList.add(new Filter("Hello", getFilteredBitmap((GPUImageFilter) new IFEarlybirdFilter(this.context)), (GPUImageFilter) new IFEarlybirdFilter(this.context)));
        this.filterList.add(new Filter("Hello", getFilteredBitmap((GPUImageFilter) new IFHefeFilter(this.context)), (GPUImageFilter) new IFHefeFilter(this.context)));
        this.filterList.add(new Filter("Hello", getFilteredBitmap((GPUImageFilter) new IFHudsonFilter(this.context)), (GPUImageFilter) new IFHudsonFilter(this.context)));
        this.filterList.add(new Filter("Hello", getFilteredBitmap((GPUImageFilter) new IFInkwellFilter(this.context)), (GPUImageFilter) new IFInkwellFilter(this.context)));
        this.filterList.add(new Filter("Hello", getFilteredBitmap((GPUImageFilter) new IFLomoFilter(this.context)), (GPUImageFilter) new IFLomoFilter(this.context)));
        this.filterList.add(new Filter("Hello", getFilteredBitmap((GPUImageFilter) new IFLordKelvinFilter(this.context)), (GPUImageFilter) new IFLordKelvinFilter(this.context)));
        this.filterList.add(new Filter("Hello", getFilteredBitmap((GPUImageFilter) new IFNashvilleFilter(this.context)), (GPUImageFilter) new IFNashvilleFilter(this.context)));
        this.filterList.add(new Filter("Hello", getFilteredBitmap((GPUImageFilter) new IFRiseFilter(this.context)), (GPUImageFilter) new IFRiseFilter(this.context)));
        this.filterList.add(new Filter("Hello", getFilteredBitmap((GPUImageFilter) new IFSierraFilter(this.context)), (GPUImageFilter) new IFSierraFilter(this.context)));
        this.filterList.add(new Filter("Hello", getFilteredBitmap((GPUImageFilter) new IFSutroFilter(this.context)), (GPUImageFilter) new IFSutroFilter(this.context)));
        this.filterList.add(new Filter("Hello", getFilteredBitmap((GPUImageFilter) new IFToasterFilter(this.context)), (GPUImageFilter) new IFToasterFilter(this.context)));
        this.filterList.add(new Filter("Hello", getFilteredBitmap((GPUImageFilter) new IFValenciaFilter(this.context)), (GPUImageFilter) new IFValenciaFilter(this.context)));
        this.filterList.add(new Filter("Hello", getFilteredBitmap((GPUImageFilter) new IFWaldenFilter(this.context)), (GPUImageFilter) new IFWaldenFilter(this.context)));
        this.filterList.add(new Filter("Hello", getFilteredBitmap((GPUImageFilter) new IFEarlybirdFilter(this.context)), (GPUImageFilter) new IFEarlybirdFilter(this.context)));
        this.filterList.add(new Filter("Hello", getFilteredBitmap((GPUImageFilter) new IFXprollFilter(this.context)), (GPUImageFilter) new IFXprollFilter(this.context)));
        this.filterList.add(new Filter("Hello", getFilteredBitmap((GPUImageFilter) this.lookupFilter), (GPUImageFilter) this.lookupFilter));
        this.filterList.add(new Filter("Hello", getFilteredBitmap((GPUImageFilter) this.lookupFilter1), (GPUImageFilter) this.lookupFilter1));
        int i = 0;
        for (int i2 = 0; i2 < this.constants.length; i2++) {
            List<Object> list = this.filterList;
            list.add(new Filter("Constant " + String.valueOf(i2), getFilteredBitmap(this.constants[i2]), this.constants[i2]));
        }
        if (!Constants.REMOVE_ADS && Constants.BUTTON_ADS) {
            for (int i3 = Constants.FILTER_AD_BUTTON_SPACE; i3 <= this.filterList.size(); i3 += Constants.FILTER_AD_BUTTON_SPACE + 1) {
                if (i < NativeAdUtil.getInstance().getFilter_native_ads().size() && !Constants.REMOVE_ADS) {
                    this.filterList.add(i3, NativeAdUtil.getInstance().getFilter_native_ads().get(i));
                    i++;
                }
            }
        }
        this.mAdapter = new FilterAdapter(getContext(), this.filterList, this.effectIndex, this);
    }

    public void resetList() {
        this.filterList.size();
        Log.d("FilterList Extra Size", String.valueOf(this.filterList.size()));
        if (this.gpuImage != null) {
            for (int i = 0; i < this.filterList.size(); i++) {
                if (this.filterList.get(i) instanceof Filter) {
                    Filter filter = (Filter) this.filterList.get(i);
                    if (filter.getConstant() == null) {
                        filter.setBitmap(getFilteredBitmap(filter.getFilter()));
                    } else {
                        filter.setBitmap(getFilteredBitmap(filter.getConstant()));
                    }
                }
            }
        }
        ((PhotoActivity) getActivity()).getEffectFilterDetails();
        Log.d("FilterFragment", "reset List");
    }

    public void onClick(View view) {
        if (view.getId() == R.id.gpuimage) {
            this.imageView.setImageBitmap(PhotoActivity.effectFilterbitmap);
        }
    }

    public void OnReset() {
        Log.d("ResetListener", "FilterFragment");
        this.toggleSwitch.setCheckedPosition(0);
        this.toggleId = 0;
        this.filterSelected = false;
        new StartUpLoadingTask(getActivity()).execute(new Void[0]);
        if (((PhotoActivity) getActivity()).getEffectFilterDetails() != null) {
            ((PhotoActivity) getActivity()).getEffectFilterDetails().setFilterSelected(false);
            ((PhotoActivity) getActivity()).getEffectFilterDetails().setFilterConstant((String) null);
            ((PhotoActivity) getActivity()).getEffectFilterDetails().setFilter((Filter) null);
            ((PhotoActivity) getActivity()).getEffectFilterDetails().setEffecttofilter(false);
            this.filterSelected = false;
        }
        resetSequence();
        if (this.resetButtonListener != null) {
            this.resetButtonListener.OnResetButtonDeActivate();
        }
    }

    /* renamed from: ui.FilterFragment$ResetTask */
    private class ResetTask extends AsyncTask<Void, Void, Void> {
        public boolean resetImage = false;

        public ResetTask() {
            Log.d("FilterFragment", "resettask");
        }

        /* access modifiers changed from: protected */
        public Void doInBackground(Void... voidArr) {
            if (FilterFragment.this.toggleId == 1) {
                FilterFragment.this.bitmap = FilterFragment.resize(PhotoActivity.effectFilterbitmap, 96, 96);
                Log.d("FilterFragment", "button image reset");
            } else {
                FilterFragment.this.bitmap = FilterFragment.resize(PhotoActivity.bitmap, 96, 96);
                Log.d("FilterFragment", "button image reset orig");
            }
            FilterFragment.this.resetList();
            return null;
        }

        /* access modifiers changed from: protected */
        public void onPreExecute() {
            super.onPreExecute();
            if (PhotoActivity.bitmap == null) {
                FilterFragment.this.getOriginal();
            }
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Void voidR) {
            super.onPostExecute(voidR);
            RecyclerView.LayoutManager unused = FilterFragment.this.mLayoutManager = new LinearLayoutManager(FilterFragment.this.getActivity(), 0, false);
            FilterFragment.this.recyclerView.setLayoutManager(FilterFragment.this.mLayoutManager);
            FilterFragment.this.mAdapter.initClickEffect();
            FilterFragment.this.recyclerView.setAdapter(FilterFragment.this.mAdapter);
            FilterFragment.this.mAdapter.notifyDataSetChanged();
        }
    }

    public void OnFilterClick(Filter filter, int i) {
        this.filterC = filter;
        if (this.resetButtonListener != null) {
            this.resetButtonListener.OnResetButtonActivate();
        }
        if (((PhotoActivity) getActivity()).getEffectFilterDetails() != null && this.toggleId == 1) {
            ((PhotoActivity) getActivity()).getEffectFilterDetails().setFilter(filter);
            ((PhotoActivity) getActivity()).getEffectFilterDetails().setEffecttofilter(true);
            ((PhotoActivity) getActivity()).getEffectFilterDetails().setFilterSelected(true);
        } else if (((PhotoActivity) getActivity()).getEffectFilterDetails() != null) {
            ((PhotoActivity) getActivity()).getEffectFilterDetails().setFilter(filter);
            ((PhotoActivity) getActivity()).getEffectFilterDetails().setFilterSelected(true);
        }
        if (this.toggleId == 0) {
            if (filter.getConstant() != null) {
                this.temp = CGENativeLibrary.filterImage_MultipleEffects(Utils.convert(PhotoActivity.bitmap.copy(PhotoActivity.bitmap.getConfig(), true), Bitmap.Config.ARGB_8888), filter.getConstant(), 1.0f);
                this.imageView.setImageBitmap(this.temp);
                resetSequence();
                ((PhotoActivity) getActivity()).getEffectFilterDetails().setFilterConstant(filter.getConstant());
            } else {
                this.gpuImage.setImage(PhotoActivity.bitmap);
                this.gpuImage.setFilter(filter.getFilter());
                this.imageView.setImageBitmap(Utils.convert(this.gpuImage.getBitmapWithFilterApplied(), Bitmap.Config.RGB_565));
                resetSequence();
                ((PhotoActivity) getActivity()).getEffectFilterDetails().setFilter(filter);
            }
        } else if (this.toggleId == 1) {
            if (filter.getConstant() != null) {
                this.temp = CGENativeLibrary.filterImage_MultipleEffects(Utils.convert(PhotoActivity.effectFilterbitmap.copy(PhotoActivity.effectFilterbitmap.getConfig(), true), Bitmap.Config.ARGB_8888), filter.getConstant(), 1.0f);
                this.imageView.setImageBitmap(this.temp);
                resetSequence();
                ((PhotoActivity) getActivity()).getEffectFilterDetails().setFilterConstant(filter.getConstant());
            } else {
                this.gpuImage.setImage(PhotoActivity.effectFilterbitmap);
                this.gpuImage.setFilter(filter.getFilter());
                this.imageView.setImageBitmap(Utils.convert(this.gpuImage.getBitmapWithFilterApplied(), Bitmap.Config.RGB_565));
                resetSequence();
                ((PhotoActivity) getActivity()).getEffectFilterDetails().setFilter(filter);
            }
        }
        this.filterSelected = true;
        checkForAd();
    }

    public void OnRemoveEFilter() {
        if (this.resetButtonListener != null) {
            this.resetButtonListener.OnResetButtonDeActivate();
        }
        if (this.toggleId == 0) {
            this.imageView.setImageBitmap(PhotoActivity.bitmap);
        } else if (this.toggleId == 1) {
            this.imageView.setImageBitmap(PhotoActivity.effectFilterbitmap);
            Log.d("FilterFragment", "remove filter");
        }
        if (((PhotoActivity) getActivity()).getEffectFilterDetails() != null) {
            ((PhotoActivity) getActivity()).getEffectFilterDetails().setFilterSelected(false);
            ((PhotoActivity) getActivity()).getEffectFilterDetails().setFilterConstant((String) null);
            ((PhotoActivity) getActivity()).getEffectFilterDetails().setFilter((Filter) null);
        }
        resetSequence();
        this.filterSelected = false;
    }

    public void onToggleSwitchChanged(int i) {
        Log.d("FilterFragment toggle", String.valueOf(i));
        if (i == 0) {
            if (this.toggleId == 1) {
                setImageViewOriginalBitmap();
                new ResetTask().execute(new Void[0]);
            }
            if (((PhotoActivity) getActivity()).getEffectFilterDetails() != null) {
                ((PhotoActivity) getActivity()).getEffectFilterDetails().setEffecttofilter(false);
            }
            this.toggleId = 0;
        } else if (i == 1 && ((PhotoActivity) getActivity()).getEffectFilterDetails() != null) {
            if (((PhotoActivity) getActivity()).getEffectFilterDetails().isEffectSelected()) {
                this.effectLoadingDialog = new Dialog(this.context, R.style.EffectDialogTheme);
                this.effectLoadingDialog.requestWindowFeature(1);
                this.effectLoadingDialog.setContentView(R.layout.effect_loading_dialog);
                this.effectLoadingDialog.setCancelable(false);
                setImageViewEffectBitmap(this.effectLoadingDialog);
                new ResetTask().execute(new Void[0]);
                this.toggleId = 1;
                Log.d("FilterFragment", "toggle reset");
                ((PhotoActivity) getActivity()).getEffectFilterDetails().setEffecttofilter(true);
            } else {
                this.toggleId = 0;
                this.toggleSwitch.setCheckedPosition(0);
                setImageViewOriginalBitmap();
                new ResetTask().execute(new Void[0]);
                Toast.makeText(getActivity(), "No Effect Selected", Toast.LENGTH_LONG).show();
            }
        }
        if (((PhotoActivity) getActivity()).getEffectFilterDetails() != null) {
            ((PhotoActivity) getActivity()).getEffectFilterDetails().setFilterSelected(false);
            ((PhotoActivity) getActivity()).getEffectFilterDetails().setFilterConstant((String) null);
            ((PhotoActivity) getActivity()).getEffectFilterDetails().setFilter((Filter) null);
            this.filterSelected = false;
        }
        resetSequence();
    }

    /* renamed from: ui.FilterFragment$StartUpLoadingTask */
    private class StartUpLoadingTask extends AsyncTask<Void, Void, Void> {

        /* renamed from: re */
        Mat f6045re;

        public StartUpLoadingTask(Context context) {
            FilterFragment.this.effectLoadingDialog = new Dialog(context, R.style.EffectDialogTheme);
            FilterFragment.this.effectLoadingDialog.requestWindowFeature(1);
            FilterFragment.this.effectLoadingDialog.setContentView(R.layout.effect_loading_dialog);
            FilterFragment.this.effectLoadingDialog.setCancelable(false);
            if (FilterFragment.this.toggleId == 0) {
                FilterFragment.this.effectLoadingDialog.show();
            }
        }

        /* access modifiers changed from: protected */
        public void onPreExecute() {
            super.onPreExecute();
            Log.d("FilterFragment", "toggle res");
            if (FilterFragment.this.toggleId == 0) {
                FilterFragment.this.setImageViewOriginalBitmap();
                FilterFragment.this.toggleSwitch.setCheckedPosition(0);
            } else if (FilterFragment.this.toggleId == 1) {
                FilterFragment.this.toggleSwitch.setCheckedPosition(1);
                if (((PhotoActivity) FilterFragment.this.getActivity()).getEffectFilterDetails() != null) {
                    if (((PhotoActivity) FilterFragment.this.getActivity()).getEffectFilterDetails().isEffectSelected()) {
                        Log.d("FilterFragment", "toggle ress");
                        FilterFragment.this.setImageViewEffectBitmap(FilterFragment.this.effectLoadingDialog);
                    }
                    ((PhotoActivity) FilterFragment.this.getActivity()).getEffectFilterDetails().setEffecttofilter(true);
                    return;
                }
                Log.d("FilterFragment", "original ress");
                FilterFragment.this.toggleSwitch.setCheckedPosition(0);
                FilterFragment.this.setImageViewOriginalBitmap();
                FilterFragment.this.bitmap = FilterFragment.resize(PhotoActivity.bitmap, 96, 96);
            }
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Void voidR) {
            super.onPostExecute(voidR);
            if (FilterFragment.this.freshStart) {
                GPUImage unused = FilterFragment.this.gpuImage = new GPUImage(FilterFragment.this.getActivity());
                FilterFragment.this.initializeContent();
                boolean unused2 = FilterFragment.this.freshStart = false;
                RecyclerView.LayoutManager unused3 = FilterFragment.this.mLayoutManager = new LinearLayoutManager(FilterFragment.this.getActivity(), 0, false);
                FilterFragment.this.recyclerView.setLayoutManager(FilterFragment.this.mLayoutManager);
                FilterFragment.this.recyclerView.setAdapter(FilterFragment.this.mAdapter);
                FilterFragment.this.mAdapter.notifyDataSetChanged();
                FilterFragment.this.imageView.setImageBitmap(PhotoActivity.bitmap);
            } else {
                if (FilterFragment.this.filterSelected) {
                    if (FilterFragment.this.resetButtonListener != null) {
                        FilterFragment.this.resetButtonListener.OnResetButtonActivate();
                    }
                    PhotoActivity.ShowMessage("FilterFragment", "filterselected");
                    if (FilterFragment.this.filterC != null) {
                        if (FilterFragment.this.filterC.getConstant() != null) {
                            if (FilterFragment.this.toggleId == 1) {
                                PhotoActivity.ShowMessage("FilterFragment", "filterselected 11");
                                FilterFragment.this.temp = CGENativeLibrary.filterImage_MultipleEffects(Utils.convert(PhotoActivity.effectFilterbitmap.copy(PhotoActivity.effectFilterbitmap.getConfig(), true), Bitmap.Config.ARGB_8888), FilterFragment.this.filterC.getConstant(), 1.0f);
                                FilterFragment.this.imageView.setImageBitmap(FilterFragment.this.temp);
                                if (((PhotoActivity) FilterFragment.this.getActivity()).getEffectFilterDetails() != null) {
                                    ((PhotoActivity) FilterFragment.this.getActivity()).getEffectFilterDetails().setEffecttofilter(true);
                                }
                                FilterFragment.this.resetList();
                            } else {
                                PhotoActivity.ShowMessage("FilterFragment", "filterselected 001");
                                FilterFragment.this.temp = CGENativeLibrary.filterImage_MultipleEffects(Utils.convert(PhotoActivity.bitmap.copy(PhotoActivity.bitmap.getConfig(), true), Bitmap.Config.ARGB_8888), FilterFragment.this.filterC.getConstant(), 1.0f);
                                FilterFragment.this.imageView.setImageBitmap(FilterFragment.this.temp);
                                FilterFragment.this.resetList();
                            }
                        } else if (FilterFragment.this.toggleId == 0) {
                            PhotoActivity.ShowMessage("FilterFragment", "filterselected 01");
                            FilterFragment.this.gpuImage.setImage(PhotoActivity.bitmap);
                            FilterFragment.this.gpuImage.setFilter(FilterFragment.this.filterC.getFilter());
                            FilterFragment.this.imageView.setImageBitmap(Utils.convert(FilterFragment.this.gpuImage.getBitmapWithFilterApplied(), Bitmap.Config.RGB_565));
                            FilterFragment.this.resetList();
                        } else {
                            if (((PhotoActivity) FilterFragment.this.getActivity()).getEffectFilterDetails() != null) {
                                ((PhotoActivity) FilterFragment.this.getActivity()).getEffectFilterDetails().setEffecttofilter(true);
                            }
                            PhotoActivity.ShowMessage("FilterFragment", "filterselected 111");
                            if (PhotoActivity.effectFilterbitmap != null) {
                                FilterFragment.this.gpuImage.setImage(PhotoActivity.effectFilterbitmap);
                                FilterFragment.this.gpuImage.setFilter(FilterFragment.this.filterC.getFilter());
                                FilterFragment.this.imageView.setImageBitmap(Utils.convert(FilterFragment.this.gpuImage.getBitmapWithFilterApplied(), Bitmap.Config.RGB_565));
                                FilterFragment.this.resetList();
                            }
                        }
                    }
                } else {
                    PhotoActivity.ShowMessage("FilterFragment", "filterse");
                    if (FilterFragment.this.resetButtonListener != null) {
                        FilterFragment.this.resetButtonListener.OnResetButtonDeActivate();
                    }
                    if (FilterFragment.this.toggleId == 1) {
                        FilterFragment.this.resetList();
                    } else {
                        FilterFragment.this.resetList();
                    }
                }
                RecyclerView.LayoutManager unused4 = FilterFragment.this.mLayoutManager = new LinearLayoutManager(FilterFragment.this.getActivity(), 0, false);
                FilterFragment.this.recyclerView.setLayoutManager(FilterFragment.this.mLayoutManager);
                FilterFragment.this.recyclerView.setAdapter(FilterFragment.this.mAdapter);
                if (!FilterFragment.this.filterSelected) {
                    FilterFragment.this.mAdapter.initClickEffect();
                }
                FilterFragment.this.mAdapter.notifyDataSetChanged();
            }
            if (FilterFragment.this.effectLoadingDialog.isShowing() && FilterFragment.this.toggleId == 0) {
                FilterFragment.this.effectLoadingDialog.dismiss();
            }
        }

        /* access modifiers changed from: protected */
        public Void doInBackground(Void... voidArr) {
            if (FilterFragment.this.toggleId == 1) {
                if (PhotoActivity.effectFilterbitmap == null) {
                    return null;
                }
                FilterFragment.this.bitmap = FilterFragment.resize(PhotoActivity.effectFilterbitmap, 96, 96);
                return null;
            } else if (PhotoActivity.bitmap != null) {
                FilterFragment.this.bitmap = FilterFragment.resize(PhotoActivity.bitmap, 96, 96);
                return null;
            } else if (ExportDetails.getInstance().getBitmapCache() == null) {
                return null;
            } else {
                BitmapCache bitmapCache = ExportDetails.getInstance().getBitmapCache();
                if (bitmapCache.getCacheBitmap(Constants.ORIGINAL_IMAGE) == null) {
                    return null;
                }
                PhotoActivity.bitmap = bitmapCache.getCacheBitmap(Constants.ORIGINAL_IMAGE);
                return null;
            }
        }
    }

    public void setImageViewOriginalBitmap() {
        if (PhotoActivity.bitmap != null) {
            this.imageView.setImageBitmap(PhotoActivity.bitmap);
            return;
        }
        Log.d("ChechTask", "EffectTask run 2");
        try {
            PhotoActivity.bitmap = (Bitmap) new EffectFilterTask(Registry.BUCKET_BITMAP, ((PhotoActivity) getActivity()).getEffectFilterDetails(), (Bitmap) null, getActivity(), this, (Dialog) null).execute(new Void[0]).get();
            this.imageView.setImageBitmap(PhotoActivity.bitmap);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e2) {
            e2.printStackTrace();
        }
    }

    public void setImageViewEffectBitmap(Dialog dialog) {
        try {
            this.temp = (Bitmap) new EffectFilterTask("Effect", ((PhotoActivity) getActivity()).getEffectFilterDetails(), PhotoActivity.bitmap.copy(PhotoActivity.bitmap.getConfig(), true), getActivity(), this, dialog).execute(new Void[0]).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e2) {
            e2.printStackTrace();
        }
        PhotoActivity.effectFilterbitmap = this.temp;
        Log.d("FilterFragment", "EffectTask run 1");
        this.imageView.setImageBitmap(this.temp);
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

    public void getOriginal() {
        try {
            PhotoActivity.bitmap = (Bitmap) new EffectFilterTask(Registry.BUCKET_BITMAP, ((PhotoActivity) getActivity()).getEffectFilterDetails(), (Bitmap) null, getActivity(), (TaskCompleteListener) null, (Dialog) null).execute(new Void[0]).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e2) {
            e2.printStackTrace();
        }
    }

    /* access modifiers changed from: package-private */
    public void resetSequence() {
        ((PhotoActivity) getActivity()).getEffectFilterDetails().getSequence().clear();
        if (((PhotoActivity) getActivity()).getEffectFilterDetails().isEffectSelected()) {
            ((PhotoActivity) getActivity()).getEffectFilterDetails().getSequence().add("effect");
        }
        if (ExportDetails.getInstance().getAdjustValue() != -1) {
            ExportDetails.getInstance().setAdjustValue(ExportDetails.getInstance().getAdjustValue());
            ((PhotoActivity) getActivity()).getEffectFilterDetails().getSequence().add("adjust");
        }
        if (((PhotoActivity) getActivity()).getEffectFilterDetails().isFilterSelected()) {
            ((PhotoActivity) getActivity()).getEffectFilterDetails().getSequence().add("filter");
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
                    } else if (AdUtil.getInstance().isEffectNativeLoaded() && AdUtil.getInstance().getEffect_fb_native() != null) {
                        ((MainActivity) getActivity()).showFloatingFBNativeAd(AdUtil.getInstance().getEffect_fb_native(), false);
                    }
                } else if (AdUtil.getInstance().isEffectNativeLoaded()) {
                    if (AdUtil.getInstance().getEffect_fb_native() != null) {
                        ((MainActivity) getActivity()).showFloatingFBNativeAd(AdUtil.getInstance().getEffect_fb_native(), false);
                    }
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

    /* renamed from: ui.FilterFragment$ResumeAsycTask */
    private class ResumeAsycTask extends AsyncTask<Void, Void, Void> {
        Mat filtered;
        Mat finalmat;
        Mat original;
        Bitmap result = null;
        Bitmap tempBitmap = null;

        public ResumeAsycTask(Context context) {
            boolean unused = FilterFragment.this.resumeTask = true;
            FilterFragment.this.effectLoadingDialog = new Dialog(context, R.style.EffectDialogTheme);
            FilterFragment.this.effectLoadingDialog.requestWindowFeature(1);
            FilterFragment.this.effectLoadingDialog.setContentView(R.layout.effect_loading_dialog);
            FilterFragment.this.effectLoadingDialog.setCancelable(false);
            FilterFragment.this.effectLoadingDialog.show();
            this.original = new Mat();
            this.filtered = new Mat();
            this.finalmat = new Mat();
        }

        /* access modifiers changed from: protected */
        public Void doInBackground(Void... voidArr) {
            if (FilterFragment.this.toggleId == 0) {
                if (PhotoActivity.bitmap != null) {
                    this.result = PhotoActivity.bitmap;
                } else if (ExportDetails.getInstance().getBitmapCache() != null) {
                    this.result = ExportDetails.getInstance().getBitmapCache().getBitmapFromDiskCache(Constants.ORIGINAL_IMAGE);
                }
                PhotoActivity.bitmap = this.result;
            } else if (FilterFragment.this.toggleId == 1) {
                if (PhotoActivity.bitmap != null) {
                    this.tempBitmap = PhotoActivity.bitmap;
                } else if (ExportDetails.getInstance().getBitmapCache() != null) {
                    this.tempBitmap = ExportDetails.getInstance().getBitmapCache().getBitmapFromDiskCache(Constants.ORIGINAL_IMAGE);
                }
                if (((PhotoActivity) FilterFragment.this.getActivity()).getEffectFilterDetails() != null) {
                    if (((PhotoActivity) FilterFragment.this.getActivity()).getEffectFilterDetails().isEffectSelected()) {
                        Log.d("FilterFragment", "toggle ress");
                        if (((PhotoActivity) FilterFragment.this.getActivity()).getEffectFilterDetails() != null) {
                            for (int i = 0; i < ((PhotoActivity) FilterFragment.this.getActivity()).getEffectFilterDetails().getSequence().size(); i++) {
                                if (((PhotoActivity) FilterFragment.this.getActivity()).getEffectFilterDetails().getSequence().get(i).equals("effect")) {
                                    this.result = ((PhotoActivity) FilterFragment.this.getActivity()).getEffectFilterDetails().getEffect().getEffect(((PhotoActivity) FilterFragment.this.getActivity()).getEffectFilterDetails().getEffectName(), this.tempBitmap.copy(this.tempBitmap.getConfig(), true));
                                    Log.d("EffectFilterTask", "EffectTask effect seq");
                                }
                                if (((PhotoActivity) FilterFragment.this.getActivity()).getEffectFilterDetails().getSequence().get(i).equals("adjust") && this.result != null) {
                                    org.opencv.android.Utils.bitmapToMat(this.tempBitmap.copy(this.tempBitmap.getConfig(), true), this.original);
                                    org.opencv.android.Utils.bitmapToMat(this.result.copy(this.result.getConfig(), true), this.filtered);
                                    this.result = this.tempBitmap.copy(this.tempBitmap.getConfig(), true);
                                    this.result = Utils.blendBitmaps(this.original, this.filtered, this.finalmat, this.result, ExportDetails.getInstance().getAdjustValue());
                                    Log.d("EffectFilterTask", "EffectTask adjust seq");
                                }
                            }
                            PhotoActivity.effectFilterbitmap = this.result;
                        }
                    }
                    ((PhotoActivity) FilterFragment.this.getActivity()).getEffectFilterDetails().setEffecttofilter(true);
                } else if (PhotoActivity.bitmap != null) {
                    this.result = PhotoActivity.bitmap;
                } else if (ExportDetails.getInstance().getBitmapCache() != null) {
                    this.result = ExportDetails.getInstance().getBitmapCache().getBitmapFromDiskCache(Constants.ORIGINAL_IMAGE);
                }
            }
            if (this.result != null) {
                FilterFragment.this.bitmap = FilterFragment.resize(this.result, 96, 96);
                return null;
            } else if (ExportDetails.getInstance().getBitmapCache() == null) {
                return null;
            } else {
                this.result = ExportDetails.getInstance().getBitmapCache().getBitmapFromDiskCache(Constants.ORIGINAL_IMAGE);
                return null;
            }
        }

        /* access modifiers changed from: protected */
        public void onPreExecute() {
            super.onPreExecute();
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Void voidR) {
            super.onPostExecute(voidR);
            if (FilterFragment.this.freshStart) {
                GPUImage unused = FilterFragment.this.gpuImage = new GPUImage(FilterFragment.this.getActivity());
                FilterFragment.this.initializeContent();
                boolean unused2 = FilterFragment.this.freshStart = false;
                RecyclerView.LayoutManager unused3 = FilterFragment.this.mLayoutManager = new LinearLayoutManager(FilterFragment.this.getActivity(), 0, false);
                FilterFragment.this.recyclerView.setLayoutManager(FilterFragment.this.mLayoutManager);
                FilterFragment.this.recyclerView.setAdapter(FilterFragment.this.mAdapter);
                FilterFragment.this.mAdapter.notifyDataSetChanged();
                FilterFragment.this.imageView.setImageBitmap(PhotoActivity.bitmap);
            } else {
                if (FilterFragment.this.filterSelected) {
                    if (FilterFragment.this.resetButtonListener != null) {
                        FilterFragment.this.resetButtonListener.OnResetButtonActivate();
                    }
                    PhotoActivity.ShowMessage("FilterFragment", "filterselected");
                    if (FilterFragment.this.filterC != null) {
                        if (FilterFragment.this.filterC.getConstant() != null) {
                            if (FilterFragment.this.toggleId == 1) {
                                PhotoActivity.ShowMessage("FilterFragment", "filterselected 11");
                                FilterFragment.this.temp = CGENativeLibrary.filterImage_MultipleEffects(Utils.convert(PhotoActivity.effectFilterbitmap.copy(PhotoActivity.effectFilterbitmap.getConfig(), true), Bitmap.Config.ARGB_8888), FilterFragment.this.filterC.getConstant(), 1.0f);
                                FilterFragment.this.imageView.setImageBitmap(FilterFragment.this.temp);
                                if (((PhotoActivity) FilterFragment.this.getActivity()).getEffectFilterDetails() != null) {
                                    ((PhotoActivity) FilterFragment.this.getActivity()).getEffectFilterDetails().setEffecttofilter(true);
                                }
                                FilterFragment.this.resetList();
                            } else {
                                PhotoActivity.ShowMessage("FilterFragment", "filterselected 001");
                                FilterFragment.this.temp = CGENativeLibrary.filterImage_MultipleEffects(Utils.convert(PhotoActivity.bitmap.copy(PhotoActivity.bitmap.getConfig(), true), Bitmap.Config.ARGB_8888), FilterFragment.this.filterC.getConstant(), 1.0f);
                                FilterFragment.this.imageView.setImageBitmap(FilterFragment.this.temp);
                                FilterFragment.this.resetList();
                            }
                        } else if (FilterFragment.this.toggleId == 0) {
                            PhotoActivity.ShowMessage("FilterFragment", "filterselected 01");
                            FilterFragment.this.gpuImage.setImage(PhotoActivity.bitmap);
                            FilterFragment.this.gpuImage.setFilter(FilterFragment.this.filterC.getFilter());
                            FilterFragment.this.imageView.setImageBitmap(Utils.convert(FilterFragment.this.gpuImage.getBitmapWithFilterApplied(), Bitmap.Config.RGB_565));
                            FilterFragment.this.resetList();
                        } else {
                            if (((PhotoActivity) FilterFragment.this.getActivity()).getEffectFilterDetails() != null) {
                                ((PhotoActivity) FilterFragment.this.getActivity()).getEffectFilterDetails().setEffecttofilter(true);
                            }
                            PhotoActivity.ShowMessage("FilterFragment", "filterselected 111");
                            if (PhotoActivity.effectFilterbitmap != null) {
                                FilterFragment.this.gpuImage.setImage(PhotoActivity.effectFilterbitmap);
                                FilterFragment.this.gpuImage.setFilter(FilterFragment.this.filterC.getFilter());
                                FilterFragment.this.imageView.setImageBitmap(Utils.convert(FilterFragment.this.gpuImage.getBitmapWithFilterApplied(), Bitmap.Config.RGB_565));
                                FilterFragment.this.resetList();
                            }
                        }
                    }
                } else {
                    PhotoActivity.ShowMessage("FilterFragment", "filtersel");
                    FilterFragment.this.imageView.setImageBitmap(this.result);
                    if (FilterFragment.this.resetButtonListener != null) {
                        FilterFragment.this.resetButtonListener.OnResetButtonDeActivate();
                    }
                    if (FilterFragment.this.toggleId == 1) {
                        FilterFragment.this.resetList();
                    } else {
                        FilterFragment.this.resetList();
                    }
                }
                RecyclerView.LayoutManager unused4 = FilterFragment.this.mLayoutManager = new LinearLayoutManager(FilterFragment.this.getActivity(), 0, false);
                FilterFragment.this.recyclerView.setLayoutManager(FilterFragment.this.mLayoutManager);
                FilterFragment.this.recyclerView.setAdapter(FilterFragment.this.mAdapter);
                if (!FilterFragment.this.filterSelected) {
                    FilterFragment.this.mAdapter.initClickEffect();
                }
                FilterFragment.this.mAdapter.notifyDataSetChanged();
            }
            if (FilterFragment.this.effectLoadingDialog.isShowing()) {
                FilterFragment.this.effectLoadingDialog.dismiss();
            }
            if (FilterFragment.this.toggleId == 0) {
                FilterFragment.this.toggleSwitch.setCheckedPosition(0);
            } else if (FilterFragment.this.toggleId == 1) {
                FilterFragment.this.toggleSwitch.setCheckedPosition(1);
            } else {
                FilterFragment.this.toggleSwitch.setCheckedPosition(0);
            }
        }

        /* access modifiers changed from: protected */
        public void onProgressUpdate(Void... voidArr) {
            super.onProgressUpdate(voidArr);
        }
    }
}