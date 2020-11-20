package helper;

import com.teamvinay.sketch.EffectFilterDetails;
import com.teamvinay.sketch.FilterHolder;
import java.util.ArrayList;
import java.util.List;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageFilter;
import util.BitmapCache;
import util.Effect;

public class ExportDetails {
    private static ExportDetails sSoleInstance;
    private int adjustValue = -1;
    private BitmapCache bitmapCache;
    private String cache_name = null;
    private Effect effect;
    private EffectFilterDetails effectFilterDetails;
    private String effect_name = null;
    private GPUImageFilter filter;
    private String filterConstant = null;
    private FilterHolder filterHolder;
    public String fragment_title = null;
    private boolean isEffectSelected = false;
    private boolean isEnhanceFiltersApplied = false;
    private boolean isFilterSelected = false;
    private boolean isNoEffectAndFilterApplied = false;
    private String path = null;
    public List<ImageResolutions> resolutionsList = new ArrayList();
    private List<String> seq = new ArrayList();
    private int targetLength = 0;

    public List<ImageResolutions> getResolutionsList() {
        return this.resolutionsList;
    }

    public void setResolutionsList(List<ImageResolutions> list) {
        this.resolutionsList = list;
    }

    public static ExportDetails getInstance() {
        if (sSoleInstance == null) {
            sSoleInstance = new ExportDetails();
        }
        return sSoleInstance;
    }

    public EffectFilterDetails getEffectFilterDetails() {
        return this.effectFilterDetails;
    }

    public void setEffectFilterDetails(EffectFilterDetails effectFilterDetails2) {
        this.effectFilterDetails = effectFilterDetails2;
    }

    public String getFragment_title() {
        return this.fragment_title;
    }

    public void setFragment_title(String str) {
        this.fragment_title = str;
    }

    public FilterHolder getFilterHolder() {
        return this.filterHolder;
    }

    public void setFilterHolder(FilterHolder filterHolder2) {
        this.filterHolder = filterHolder2;
    }

    public Effect getEffect() {
        return this.effect;
    }

    public void setEffect(Effect effect2) {
        this.effect = effect2;
    }

    public String getEffect_name() {
        return this.effect_name;
    }

    public void setEffect_name(String str) {
        this.effect_name = str;
    }

    public int getAdjustValue() {
        return this.adjustValue;
    }

    public void setAdjustValue(int i) {
        this.adjustValue = i;
    }

    public boolean isEffectSelected() {
        return this.isEffectSelected;
    }

    public void setEffectSelected(boolean z) {
        this.isEffectSelected = z;
    }

    public boolean isFilterSelected() {
        return this.isFilterSelected;
    }

    public void setFilterSelected(boolean z) {
        this.isFilterSelected = z;
    }

    public boolean isEnhanceFiltersApplied() {
        return this.isEnhanceFiltersApplied;
    }

    public void setEnhanceFiltersApplied(boolean z) {
        this.isEnhanceFiltersApplied = z;
    }

    public String getCache_name() {
        return this.cache_name;
    }

    public void setCache_name(String str) {
        this.cache_name = str;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String str) {
        this.path = str;
    }

    public int getTargetLength() {
        return this.targetLength;
    }

    public void setTargetLength(int i) {
        this.targetLength = i;
    }

    public GPUImageFilter getFilter() {
        return this.filter;
    }

    public void setFilter(GPUImageFilter gPUImageFilter) {
        this.filter = gPUImageFilter;
    }

    public String getFilterConstant() {
        return this.filterConstant;
    }

    public void setFilterConstant(String str) {
        this.filterConstant = str;
    }

    public BitmapCache getBitmapCache() {
        return this.bitmapCache;
    }

    public void setBitmapCache(BitmapCache bitmapCache2) {
        this.bitmapCache = bitmapCache2;
    }

    public List<String> getSeq() {
        return this.seq;
    }

    public void setSeq(List<String> list) {
        this.seq = list;
    }

    public boolean isNoEffectAndFilterApplied() {
        return this.isNoEffectAndFilterApplied;
    }

    public void setNoEffectAndFilterApplied(boolean z) {
        this.isNoEffectAndFilterApplied = z;
    }
}