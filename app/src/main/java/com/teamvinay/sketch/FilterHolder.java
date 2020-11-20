package com.teamvinay.sketch;

import android.util.Log;
import helper.Constants;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageBrightnessFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageContrastFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageFilterGroup;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageGaussianBlurFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageHueFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageMonochromeFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImagePixelationFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageSaturationFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageSharpenFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageVignetteFilter;
import util.EditorFilter;

public class FilterHolder implements Serializable {
    private List<TwoHolder> twoHolders = new ArrayList();

    public List<TwoHolder> getTwoHolders() {
        return this.twoHolders;
    }

    public void setTwoHolders(List<TwoHolder> list) {
        this.twoHolders = list;
    }

    public void addFilter(FilterType filterType, float f) {
        if (!this.twoHolders.isEmpty()) {
            boolean z = false;
            for (int i = 0; i < this.twoHolders.size(); i++) {
                if (this.twoHolders.get(i).getFilterType() == filterType) {
                    if (this.twoHolders.get(i).getFilter().getFilterValue() != f) {
                        this.twoHolders.get(i).getFilter().setFilterValue(f);
                    }
                    z = true;
                }
            }
            if (!z) {
                addFiltertoList(filterType, f);
                return;
            }
            return;
        }
        addFiltertoList(filterType, f);
    }

    public float getValue(FilterType filterType) {
        float f;
        if (!this.twoHolders.isEmpty()) {
            f = 0.0f;
            for (int i = 0; i < this.twoHolders.size(); i++) {
                if (this.twoHolders.get(i).getFilterType() == filterType) {
                    f = this.twoHolders.get(i).getFilter().getFilterValue();
                }
            }
        } else {
            f = 0.0f;
        }
        if (filterType == FilterType.CONTRAST && f == 0.0f) {
            f = 0.12f;
        } else if (filterType == FilterType.SATURATION && f == 0.0f && !Constants.IS_SATURATION_ZERO) {
            f = 0.1f;
        }
        Log.d("Filter Value filter", String.valueOf(f));
        return f;
    }

    public void addFiltertoList(FilterType filterType, float f) {
        switch (filterType) {
            case BRIGHTNESS:
                this.twoHolders.add(new TwoHolder(new EditorFilter(new GPUImageFilter(), f), filterType));
                return;
            case SHARPNESS:
                this.twoHolders.add(new TwoHolder(new EditorFilter(new GPUImageSharpenFilter(), f), filterType));
                return;
            case SATURATION:
                this.twoHolders.add(new TwoHolder(new EditorFilter(new GPUImageSaturationFilter(), f), filterType));
                return;
            case HUE:
                this.twoHolders.add(new TwoHolder(new EditorFilter(new GPUImageHueFilter(), f), filterType));
                return;
            case CONTRAST:
                this.twoHolders.add(new TwoHolder(new EditorFilter(new GPUImageContrastFilter(), f), filterType));
                return;
            case PIXEL:
                this.twoHolders.add(new TwoHolder(new EditorFilter(new GPUImagePixelationFilter(), f), filterType));
                return;
            case BLUR:
                this.twoHolders.add(new TwoHolder(new EditorFilter(new GPUImageGaussianBlurFilter(), f), filterType));
                return;
            case EXPOSE:
                this.twoHolders.add(new TwoHolder(new EditorFilter(new GPUImageGaussianBlurFilter(), f), filterType));
                return;
            case VIGNETTE:
                this.twoHolders.add(new TwoHolder(new EditorFilter(new GPUImageVignetteFilter(), f), filterType));
                return;
            case MONO:
                GPUImageMonochromeFilter gPUImageMonochromeFilter = new GPUImageMonochromeFilter();
                gPUImageMonochromeFilter.setColor(new float[]{0.6f, 0.45f, 0.3f, 1.0f});
                this.twoHolders.add(new TwoHolder(new EditorFilter(gPUImageMonochromeFilter, f), filterType));
                return;
            default:
                return;
        }
    }

    public int getFilterValue(FilterType filterType) {
        int value = (int) (getValue(filterType) * 100.0f);
        Log.d("Filter", "Less than 1");
        return value;
    }

    public int getProgress(FilterType filterType) {
        float value = getValue(filterType);
        Log.d("Filter Progress", String.valueOf(value));
        float f = value * 100.0f;
        int i = (int) f;
        Log.d("Filter Progress final", String.valueOf(f));
        return i;
    }

    public GPUImageFilterGroup readFilters() {
        GPUImageFilterGroup gPUImageFilterGroup = new GPUImageFilterGroup();
        for (int i = 0; i < getTwoHolders().size(); i++) {
            gPUImageFilterGroup.addFilter(getFilter(getTwoHolders().get(i).getFilterType()));
        }
        return gPUImageFilterGroup;
    }

    public GPUImageFilter getFilter(FilterType filterType) {
        if (filterType == FilterType.BRIGHTNESS) {
            return new GPUImageBrightnessFilter(getValue(filterType));
        }
        if (filterType == FilterType.CONTRAST) {
            return new GPUImageContrastFilter(getValue(filterType) * 10.0f);
        }
        if (filterType == FilterType.SATURATION) {
            return new GPUImageSaturationFilter(getValue(filterType) * 10.0f);
        }
        if (filterType == FilterType.HUE) {
            return new GPUImageHueFilter(getValue(filterType) * 100.0f);
        }
        if (filterType == FilterType.SHARPNESS) {
            return new GPUImageSharpenFilter(getValue(filterType));
        }
        if (filterType == FilterType.BLUR) {
            return new GPUImageGaussianBlurFilter(getValue(filterType));
        }
        if (filterType != FilterType.PIXEL) {
            return null;
        }
        GPUImagePixelationFilter gPUImagePixelationFilter = new GPUImagePixelationFilter();
        gPUImagePixelationFilter.setPixel(getValue(filterType) * 15.0f);
        return gPUImagePixelationFilter;
    }
}