package com.teamvinay.sketch;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import helper.ExportDetails;
import java.util.ArrayList;
import java.util.List;
import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.wysaid.nativePort.CGENativeLibrary;
import jp.co.cyberagent.android.gpuimage.GPUImage;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageFilter;
import util.BitmapCache;
import util.Effect;

public class EffectTask extends AsyncTask<Void, Void, Bitmap> {
    private String _cache_name;

    /* renamed from: b */
    Bitmap f4192b = null;
    public Bitmap bitmap;
    Dialog dialog;
    public Effect effect;
    private String effectName;
    private ExportDetails exportDetails;
    GPUImageFilter filter;
    private FilterHolder filterHolder;
    Mat filtered;
    Mat finalmat;
    private GPUImage gpuImage;
    private BitmapCache mbitmapCache;
    Mat original;
    private List<String> sequence = new ArrayList();

    public EffectTask(Bitmap bitmap2, Effect effect2, String str, ExportDetails exportDetails2, GPUImage gPUImage, Dialog dialog2) {
        this.bitmap = bitmap2;
        this.effect = effect2;
        this.effectName = str;
        if (gPUImage != null) {
            this.gpuImage = gPUImage;
        }
        if (exportDetails2 != null) {
            this.exportDetails = exportDetails2;
            if (!this.exportDetails.getSeq().isEmpty()) {
                this.sequence = this.exportDetails.getSeq();
            }
            this.mbitmapCache = this.exportDetails.getBitmapCache();
            this._cache_name = this.exportDetails.getCache_name();
            this.filterHolder = this.exportDetails.getFilterHolder();
            this.filter = this.exportDetails.getFilter();
            if (dialog2 != null) {
                this.dialog = dialog2;
            }
        }
        if (dialog2 != null) {
            this.dialog = dialog2;
            this.dialog.show();
        }
        this.original = new Mat();
        this.filtered = new Mat();
        this.finalmat = new Mat();
        if (this.effect != null) {
            this.effect.setOriginal_image(false);
        }
    }

    /* access modifiers changed from: protected */
    public Bitmap doInBackground(Void... voidArr) {
        Log.d("ChechTask", "doinBackground");
        if (this.exportDetails != null && this.exportDetails.isNoEffectAndFilterApplied()) {
            this.f4192b = this.bitmap;
            Log.d("ChechTask", "NoEffectApplied");
        } else if (this._cache_name == null || !this._cache_name.equals("regular")) {
            this.f4192b = this.effect.getEffect(this.effectName, this.bitmap);
        } else if (this.mbitmapCache != null) {
            if (this.mbitmapCache.getCacheBitmap(this._cache_name) == null) {
                for (int i = 0; i < this.sequence.size(); i++) {
                    if (this.sequence.get(i).equals("effect")) {
                        this.f4192b = this.effect.getEffect(this.effectName, this.bitmap);
                        Log.d("ChechTask", "EffectTask effect seq");
                    }
                    if (this.sequence.get(i).equals("adjust") && this.f4192b != null) {
                        Utils.bitmapToMat(this.bitmap.copy(this.bitmap.getConfig(), true), this.original);
                        Utils.bitmapToMat(this.f4192b.copy(this.f4192b.getConfig(), true), this.filtered);
                        this.f4192b = this.bitmap.copy(this.bitmap.getConfig(), true);
                        this.f4192b = util.Utils.blendBitmaps(this.original, this.filtered, this.finalmat, this.f4192b, this.exportDetails.getAdjustValue());
                        Log.d("ChechTask", "EffectTask adjust seq");
                    }
                    if (this.sequence.get(i).equals("filter")) {
                        if (this.f4192b == null) {
                            this.f4192b = this.bitmap;
                            Log.d("ChechTask", "bitmap filter null");
                        }
                        if (this.exportDetails.getFilterConstant() != null) {
                            this.f4192b = CGENativeLibrary.filterImage_MultipleEffects(util.Utils.convert(this.f4192b.copy(this.f4192b.getConfig(), true), Bitmap.Config.ARGB_8888), this.exportDetails.getFilterConstant(), 1.0f);
                            this.f4192b = util.Utils.convert(this.f4192b, Bitmap.Config.RGB_565);
                        } else if (this.exportDetails.getFilter() != null) {
                            this.gpuImage.setImage(this.f4192b);
                            this.gpuImage.setFilter(this.exportDetails.getFilter());
                            this.f4192b = this.gpuImage.getBitmapWithFilterApplied();
                        }
                        Log.d("ChechTask", "EffectTask enhance filter");
                    }
                    if (this.sequence.get(i).equals("enhance")) {
                        if (this.f4192b == null) {
                            this.f4192b = this.bitmap;
                            Log.d("ChechTask", "bitmap enhance null");
                        }
                        this.gpuImage.setImage(this.f4192b);
                        this.gpuImage.setFilter(this.filterHolder.readFilters());
                        this.f4192b = this.gpuImage.getBitmapWithFilterApplied();
                        Log.d("ChechTask", "EffectTask enhance seq");
                    }
                }
                this.mbitmapCache.addBitmapToCache(this._cache_name, this.f4192b);
                Log.d("Bitmap loaded", "to memory");
            } else {
                this.f4192b = this.mbitmapCache.getCacheBitmap(this._cache_name);
                Log.d("Bitmap available", "from memory");
            }
        }
        return this.f4192b;
    }

    /* access modifiers changed from: protected */
    public void onPreExecute() {
        super.onPreExecute();
        Log.d("EffectTask", "PreExecute");
    }

    /* access modifiers changed from: protected */
    public void onPostExecute(Bitmap bitmap2) {
        super.onPostExecute(bitmap2);
        if (this.dialog != null && this.dialog.isShowing()) {
            this.dialog.dismiss();
        }
        if (this.original != null) {
            this.original.release();
        }
        if (this.filtered != null) {
            this.filtered.release();
        }
        if (this.finalmat != null) {
            this.finalmat.release();
        }
    }

    /* access modifiers changed from: protected */
    public void onProgressUpdate(Void... voidArr) {
        super.onProgressUpdate(voidArr);
        Log.d("EffectTask", "ProgressUpdate");
    }
}

