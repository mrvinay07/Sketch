package com.teamvinay.sketch;

import android.graphics.Bitmap;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageFilter;

public class Filter {
    private Bitmap bitmap;
    private String constant = null;
    private String effectName;
    private GPUImageFilter filter;
    private boolean isChecked = false;

    public String getConstant() {
        return this.constant;
    }

    public void setConstant(String str) {
        this.constant = str;
    }

    public void setBitmap(Bitmap bitmap2) {
        this.bitmap = bitmap2;
    }

    public Filter(String str, Bitmap bitmap2, GPUImageFilter gPUImageFilter) {
        this.effectName = str;
        this.bitmap = bitmap2;
        this.filter = gPUImageFilter;
    }

    public Filter(String str, Bitmap bitmap2, String str2) {
        this.effectName = str;
        this.bitmap = bitmap2;
        this.constant = str2;
    }

    public String getEffectName() {
        return this.effectName;
    }

    public Bitmap getBitmap() {
        return this.bitmap;
    }

    public GPUImageFilter getFilter() {
        return this.filter;
    }

    public boolean isChecked() {
        return this.isChecked;
    }

    public void setChecked(boolean z) {
        this.isChecked = z;
    }
}
