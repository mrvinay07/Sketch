package helper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.teamvinay.sketch.App;

import com.teamvinay.sketch.Filter;
import com.teamvinay.sketch.FilterAdapter;
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
import java.util.ArrayList;
import java.util.List;
import org.wysaid.nativePort.CGENativeLibrary;
import jp.co.cyberagent.android.gpuimage.GPUImage;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageLookupFilter;
import util.NativeAdUtil;
import util.Utils;

public class FilterButtonFragment extends Fragment {
    /* access modifiers changed from: private */
    public Bitmap bitmap;
    String[] constants = {"@adjust hsv -0.7 -0.7 0.5 -0.7 -0.7 0.5 @pixblend ol 0.243 0.07059 0.59215 1 25", "@adjust hsv 0.10678917 0.14523578 0.71266866 0.009352684 0.34604204 0.8423001 @pixblend ol 0.61192507 0.66061425 0.96537817 1 18", "@adjust hsv 0.08683282 0.8324069 0.21115458 0.96259296 0.3223461 0.33664382 @pixblend ol 0.87732095 0.62007666 0.4117298 1 27", "@curve R(170, 46)(254, 89)(247, 204)(129, 91)(29, 198) @adjust hsv 0.22157013 0.7836555 0.95315063 0.88177353 0.5007757 0.17895842", "@curve R(153, 56)(153, 110)(138, 250)(117, 179)(246, 193) @adjust hsv 0.13393426 0.2909866 0.39522433 0.44626093 0.025464356 0.066244185 @pixblend ol 0.7757735 0.75961334 0.91061693 1 20", "@adjust saturation 0 @curve R(0, 68)(10, 72)(42, 135)(72, 177)(98, 201)(220, 255)G(0, 29)(12, 30)(57, 127)(119, 203)(212, 255)(254, 239)B(0, 36)(54, 118)(66, 141)(119, 197)(155, 215)(255, 254)", "@curve R(0, 64)(16, 13)(58, 128)(108, 109)(162, 223)(255, 255)G(0, 30)(22, 35)(42, 58)(56, 86)(70, 119)(130, 184)(189, 212)B(6, 36)(76, 157)(107, 192)(173, 229)(255, 255)"};
    /* access modifiers changed from: private */
    public Context context;
    private int effectIndex = -1;
    /* access modifiers changed from: private */
    public List<Object> filterList = new ArrayList();
    private boolean freshstart = true;
    private GPUImage gpuImage;
    /* access modifiers changed from: private */
    public FilterButtonListener listener;
    GPUImageLookupFilter lookupFilter;
    GPUImageLookupFilter lookupFilter1;
    GPUImageLookupFilter lookupFilter2;
    /* access modifiers changed from: private */
    public FilterAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    /* access modifiers changed from: private */
    public RecyclerView mRecyclerView;

    /* renamed from: v */
    View v;

    public int getEffectIndex() {
        return this.effectIndex;
    }

    public void setEffectIndex(int i) {
        this.effectIndex = i;
    }

    public List<Object> getFilterList() {
        return this.filterList;
    }

    public void setFilterList(List<Object> list) {
        this.filterList = list;
    }

    public void setmAdapter(FilterAdapter filterAdapter) {
        this.mAdapter = filterAdapter;
    }

    public FilterButtonFragment() {
        Log.d("FilterFragment", "Constructor");
    }

    public void onAttach(Context context2) {
        super.onAttach(context2);
        Log.d("FilterFragment", "Attach");
    }

    public void setBitmap(Bitmap bitmap2, Context context2) {
        this.bitmap = bitmap2;
        this.context = context2;
        if (this.context != null) {
            if (this.bitmap == null) {
                this.bitmap = BitmapFactory.decodeResource(context2.getResources(), R.drawable.filter_drawable);
            }
            if (this.lookupFilter == null) {
                this.lookupFilter = new GPUImageLookupFilter();
                this.lookupFilter.setBitmap(BitmapFactory.decodeResource(context2.getResources(), R.drawable.filmstock));
            }
            if (this.lookupFilter1 == null) {
                this.lookupFilter1 = new GPUImageLookupFilter();
                this.lookupFilter1.setBitmap(BitmapFactory.decodeResource(context2.getResources(), R.drawable.wildbird));
            }
        } else {
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
        Log.d("FilterFragment", "BitmapSet");
    }

    public void changeBitmap(Bitmap bitmap2) {
        if (this.bitmap != null && bitmap2 != null) {
            this.bitmap = null;
            this.bitmap = bitmap2;
        }
    }

    public Bitmap getFilteredBitmap(GPUImageFilter gPUImageFilter) {
        this.gpuImage.setImage(this.bitmap);
        this.gpuImage.setFilter(gPUImageFilter);
        return this.gpuImage.getBitmapWithFilterApplied();
    }

    public Bitmap getFilteredBitmap(String str) {
        return CGENativeLibrary.filterImage_MultipleEffects(Utils.convert(this.bitmap.copy(this.bitmap.getConfig(), true), Bitmap.Config.ARGB_8888), str, 1.0f);
    }

    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        Log.d("FilterFragment", "OnCreate");
    }

    public View onCreateView(LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, Bundle bundle) {
        this.v = layoutInflater.inflate(R.layout.effect_fragment_layout, viewGroup, false);
        Log.d("FilterFragment", "CreateView");
        this.gpuImage = new GPUImage(getActivity());
        this.mRecyclerView = (RecyclerView) this.v.findViewById(R.id.my_recycler_view);
        this.mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        this.mRecyclerView.setLayoutManager(this.mLayoutManager);
        return this.v;
    }

    public void setEffectListener(FilterButtonListener filterButtonListener) {
        this.listener = filterButtonListener;
    }

    public FilterAdapter getmAdapter() {
        return this.mAdapter;
    }

    public void onStart() {
        super.onStart();
        Log.d("FilterFragment", "Start");
    }

    public void onResume() {
        super.onResume();
        Log.d("FilterFragment", "Resume");
    }

    public void setUserVisibleHint(boolean z) {
        super.setUserVisibleHint(z);
        if (z) {
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    if (!(FilterButtonFragment.this.bitmap == null || FilterButtonFragment.this.filterList == null || !FilterButtonFragment.this.filterList.isEmpty())) {
                        FilterButtonFragment.this.filterList.add(new Filter("Hello", FilterButtonFragment.this.getFilteredBitmap((GPUImageFilter) new IF1977Filter(FilterButtonFragment.this.context)), (GPUImageFilter) new IF1977Filter(FilterButtonFragment.this.context)));
                        FilterButtonFragment.this.filterList.add(new Filter("Hello", FilterButtonFragment.this.getFilteredBitmap((GPUImageFilter) new IFAmaroFilter(FilterButtonFragment.this.context)), (GPUImageFilter) new IFAmaroFilter(FilterButtonFragment.this.context)));
                        FilterButtonFragment.this.filterList.add(new Filter("Hello", FilterButtonFragment.this.getFilteredBitmap((GPUImageFilter) new IFBrannanFilter(FilterButtonFragment.this.context)), (GPUImageFilter) new IFBrannanFilter(FilterButtonFragment.this.context)));
                        FilterButtonFragment.this.filterList.add(new Filter("Hello", FilterButtonFragment.this.getFilteredBitmap((GPUImageFilter) new IFEarlybirdFilter(FilterButtonFragment.this.context)), (GPUImageFilter) new IFEarlybirdFilter(FilterButtonFragment.this.context)));
                        FilterButtonFragment.this.filterList.add(new Filter("Hello", FilterButtonFragment.this.getFilteredBitmap((GPUImageFilter) new IFHefeFilter(FilterButtonFragment.this.context)), (GPUImageFilter) new IFHefeFilter(FilterButtonFragment.this.context)));
                        FilterButtonFragment.this.filterList.add(new Filter("Hello", FilterButtonFragment.this.getFilteredBitmap((GPUImageFilter) new IFHudsonFilter(FilterButtonFragment.this.context)), (GPUImageFilter) new IFHudsonFilter(FilterButtonFragment.this.context)));
                        FilterButtonFragment.this.filterList.add(new Filter("Hello", FilterButtonFragment.this.getFilteredBitmap((GPUImageFilter) new IFInkwellFilter(FilterButtonFragment.this.context)), (GPUImageFilter) new IFInkwellFilter(FilterButtonFragment.this.context)));
                        FilterButtonFragment.this.filterList.add(new Filter("Hello", FilterButtonFragment.this.getFilteredBitmap((GPUImageFilter) new IFLomoFilter(FilterButtonFragment.this.context)), (GPUImageFilter) new IFLomoFilter(FilterButtonFragment.this.context)));
                        FilterButtonFragment.this.filterList.add(new Filter("Hello", FilterButtonFragment.this.getFilteredBitmap((GPUImageFilter) new IFLordKelvinFilter(FilterButtonFragment.this.context)), (GPUImageFilter) new IFLordKelvinFilter(FilterButtonFragment.this.context)));
                        FilterButtonFragment.this.filterList.add(new Filter("Hello", FilterButtonFragment.this.getFilteredBitmap((GPUImageFilter) new IFNashvilleFilter(FilterButtonFragment.this.context)), (GPUImageFilter) new IFNashvilleFilter(FilterButtonFragment.this.context)));
                        FilterButtonFragment.this.filterList.add(new Filter("Hello", FilterButtonFragment.this.getFilteredBitmap((GPUImageFilter) new IFRiseFilter(FilterButtonFragment.this.context)), (GPUImageFilter) new IFRiseFilter(FilterButtonFragment.this.context)));
                        FilterButtonFragment.this.filterList.add(new Filter("Hello", FilterButtonFragment.this.getFilteredBitmap((GPUImageFilter) new IFSierraFilter(FilterButtonFragment.this.context)), (GPUImageFilter) new IFSierraFilter(FilterButtonFragment.this.context)));
                        FilterButtonFragment.this.filterList.add(new Filter("Hello", FilterButtonFragment.this.getFilteredBitmap((GPUImageFilter) new IFSutroFilter(FilterButtonFragment.this.context)), (GPUImageFilter) new IFSutroFilter(FilterButtonFragment.this.context)));
                        FilterButtonFragment.this.filterList.add(new Filter("Hello", FilterButtonFragment.this.getFilteredBitmap((GPUImageFilter) new IFToasterFilter(FilterButtonFragment.this.context)), (GPUImageFilter) new IFToasterFilter(FilterButtonFragment.this.context)));
                        FilterButtonFragment.this.filterList.add(new Filter("Hello", FilterButtonFragment.this.getFilteredBitmap((GPUImageFilter) new IFValenciaFilter(FilterButtonFragment.this.context)), (GPUImageFilter) new IFValenciaFilter(FilterButtonFragment.this.context)));
                        FilterButtonFragment.this.filterList.add(new Filter("Hello", FilterButtonFragment.this.getFilteredBitmap((GPUImageFilter) new IFWaldenFilter(FilterButtonFragment.this.context)), (GPUImageFilter) new IFWaldenFilter(FilterButtonFragment.this.context)));
                        FilterButtonFragment.this.filterList.add(new Filter("Hello", FilterButtonFragment.this.getFilteredBitmap((GPUImageFilter) new IFEarlybirdFilter(FilterButtonFragment.this.context)), (GPUImageFilter) new IFEarlybirdFilter(FilterButtonFragment.this.context)));
                        FilterButtonFragment.this.filterList.add(new Filter("Hello", FilterButtonFragment.this.getFilteredBitmap((GPUImageFilter) new IFXprollFilter(FilterButtonFragment.this.context)), (GPUImageFilter) new IFXprollFilter(FilterButtonFragment.this.context)));
                        FilterButtonFragment.this.filterList.add(new Filter("Hello", FilterButtonFragment.this.getFilteredBitmap((GPUImageFilter) FilterButtonFragment.this.lookupFilter), (GPUImageFilter) FilterButtonFragment.this.lookupFilter));
                        FilterButtonFragment.this.filterList.add(new Filter("Hello", FilterButtonFragment.this.getFilteredBitmap((GPUImageFilter) FilterButtonFragment.this.lookupFilter1), (GPUImageFilter) FilterButtonFragment.this.lookupFilter1));
                        Log.d("FilterList Filter Size", String.valueOf(FilterButtonFragment.this.filterList.size()));
                        int i = 0;
                        for (int i2 = 0; i2 < FilterButtonFragment.this.constants.length; i2++) {
                            List access$100 = FilterButtonFragment.this.filterList;
                            access$100.add(new Filter("Constant " + String.valueOf(i2), FilterButtonFragment.this.getFilteredBitmap(FilterButtonFragment.this.constants[i2]), FilterButtonFragment.this.constants[i2]));
                        }
                        Log.d("FilterList Cons Size", String.valueOf(FilterButtonFragment.this.filterList.size()));
                        for (int i3 = Constants.FILTER_AD_BUTTON_SPACE; i3 <= FilterButtonFragment.this.filterList.size(); i3 += Constants.FILTER_AD_BUTTON_SPACE + 1) {
                            if (i < NativeAdUtil.getInstance().getFilter_native_ads().size()) {
                                FilterButtonFragment.this.filterList.add(i3, NativeAdUtil.getInstance().getFilter_native_ads().get(i));
                                i++;
                            }
                        }
                        FilterButtonFragment.this.mAdapter.setFilterButtonListener(FilterButtonFragment.this.listener);
                    }
                    Log.d("FilterFragment", "Visible");
                    Log.d("FilterList Final Size", String.valueOf(FilterButtonFragment.this.filterList.size()));
                    if (FilterButtonFragment.this.mAdapter != null) {
                        FilterButtonFragment.this.mRecyclerView.setAdapter(FilterButtonFragment.this.mAdapter);
                        FilterButtonFragment.this.mAdapter.notifyDataSetChanged();
                        return;
                    }
                    FilterButtonFragment.this.mAdapter.setFilterButtonListener(FilterButtonFragment.this.listener);
                    FilterButtonFragment.this.mRecyclerView.setAdapter(FilterButtonFragment.this.mAdapter);
                    FilterButtonFragment.this.mAdapter.notifyDataSetChanged();
                }
            }, 250);
        }
    }

    public void removeChecked() {
        if (this.mAdapter != null && this.mAdapter.getItemCount() > 0) {
            this.mAdapter.UncheckAll();
            this.mAdapter.notifyDataSetChanged();
        }
    }

    public void onPause() {
        super.onPause();
        Log.d("FilterFragment", "Pause");
    }

    public void onStop() {
        super.onStop();
        Log.d("FilterFragment", "Stop");
    }

    public void onDestroyView() {
        super.onDestroyView();
        Log.d("FilterFragment", "DestroyView");
        this.v = null;
    }

    public void onDestroy() {
        super.onDestroy();
        if (this.bitmap != null) {
            this.bitmap = null;
        }
        this.gpuImage = null;
        Log.d("FilterFragment", "Destroy");
    }

    public void resetList() {
        this.filterList.size();
        Log.d("FilterList Extra Size", String.valueOf(this.filterList.size()));
        if (this.gpuImage != null) {
            for (int i = 0; i < this.filterList.size(); i++) {
                if (this.filterList.get(i) instanceof Filter) {
                    Filter filter = (Filter) this.filterList.get(i);
                    if (filter.getConstant() == null) {
                        Log.d("filtername " + String.valueOf(i), filter.getEffectName());
                        filter.setBitmap(getFilteredBitmap(filter.getFilter()));
                    } else {
                        filter.setBitmap(getFilteredBitmap(filter.getConstant()));
                    }
                }
            }
        }
    }

    public void onDetach() {
        super.onDetach();
        Log.d("FilterFragment", "Detach");
        unbindDrawables(this.mRecyclerView);
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

    public void refreshBitmaps() {
        if (this.gpuImage != null) {
            refresh();
            Log.d("FilterFragment", "gpuImage not null");
            return;
        }
        this.gpuImage = new GPUImage(this.context);
        Log.d("FilterFragment", "gpuImage  null");
        refresh();
    }

    private void refresh() {
        if (this.filterList != null && this.filterList.size() > 0) {
            this.filterList.clear();
        }
    }
}