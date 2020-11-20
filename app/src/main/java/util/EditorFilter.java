package util;

import jp.co.cyberagent.android.gpuimage.filter.GPUImageFilter;

public class EditorFilter {
    private GPUImageFilter filter;
    private float filterValue;

    public EditorFilter(GPUImageFilter gPUImageFilter, float f) {
        this.filter = gPUImageFilter;
        this.filterValue = f;
    }

    public GPUImageFilter getFilter() {
        return this.filter;
    }

    public void setFilter(GPUImageFilter gPUImageFilter) {
        this.filter = gPUImageFilter;
    }

    public float getFilterValue() {
        return this.filterValue;
    }

    public void setFilterValue(float f) {
        this.filterValue = f;
    }
}