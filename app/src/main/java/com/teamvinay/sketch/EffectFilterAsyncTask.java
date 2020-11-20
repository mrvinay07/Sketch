package com.teamvinay.sketch;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import helper.ExportDetails;
import java.util.ArrayList;
import java.util.List;
import listeners.TaskCompleteListener;
import org.opencv.core.Mat;
import org.wysaid.nativePort.CGENativeLibrary;
import jp.co.cyberagent.android.gpuimage.GPUImage;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageFilter;
import util.BitmapCache;
import util.Effect;
import util.Utils;

public class EffectFilterAsyncTask extends AsyncTask<Void, Void, Bitmap> {
    private String _cache_name;

    /* renamed from: b */
    Bitmap f4190b = null;
    public Bitmap bitmap;
    Dialog dialog;
    public Effect effect;
    EffectFilterDetails effectFilterDetails;
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
    TaskCompleteListener taskCompleteListener;

    public EffectFilterAsyncTask(Bitmap bitmap2, Effect effect2, String str, EffectFilterDetails effectFilterDetails2, GPUImage gPUImage, Dialog dialog2, TaskCompleteListener taskCompleteListener2) {
        this.bitmap = bitmap2;
        this.effect = effect2;
        this.effectName = str;
        this.taskCompleteListener = taskCompleteListener2;
        if (gPUImage != null) {
            this.gpuImage = gPUImage;
        }
        if (dialog2 != null) {
            this.dialog = dialog2;
        }
        this._cache_name = ExportDetails.getInstance().getCache_name();
        Log.d("cachename", this._cache_name);
        this.original = new Mat();
        this.filtered = new Mat();
        this.finalmat = new Mat();
        if (this.effect != null) {
            this.effect.setOriginal_image(true);
            this.effect.setResolution(this._cache_name);
        }
        if (effectFilterDetails2 != null) {
            this.effectFilterDetails = effectFilterDetails2;
            if (this.effectFilterDetails.getSequence().size() >= 0) {
                this.sequence = this.effectFilterDetails.getSequence();
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onPreExecute() {
        super.onPreExecute();
    }

    /* access modifiers changed from: protected */
    public Bitmap doInBackground(Void... voidArr) {
        Log.d("ChechTask effectfilter", String.valueOf(this.effectFilterDetails.isEffecttofilter()));
        if (ExportDetails.getInstance().getFragment_title() != null) {
            int i = 0;
            if (ExportDetails.getInstance().getFragment_title().equals("EffectFragment")) {
                Log.d("ChechTask", "EffectFragment");
                if (this.effectFilterDetails.isEffectSelected()) {
                    while (i < this.sequence.size()) {
                        if (this.sequence.get(i).equals("effect")) {
                            if (this.effect != null && this.effect.isOriginal_image()) {
                                this.effect.setHd_image_height(this.bitmap.getHeight());
                                this.effect.setHd_image_width(this.bitmap.getWidth());
                            }
                            this.f4190b = this.effect.getEffect(this.effectName, this.bitmap);
                            this.f4190b = Utils.convert(this.f4190b, Bitmap.Config.ARGB_8888);
                            Log.d("ChechTask", "EffectTask effect seq");
                        }
                        if (this.sequence.get(i).equals("adjust") && this.f4190b != null) {
                            org.opencv.android.Utils.bitmapToMat(this.bitmap.copy(this.bitmap.getConfig(), true), this.original);
                            org.opencv.android.Utils.bitmapToMat(this.f4190b.copy(this.f4190b.getConfig(), true), this.filtered);
                            this.f4190b = this.bitmap.copy(this.bitmap.getConfig(), true);
                            this.f4190b = Utils.blendBitmaps(this.original, this.filtered, this.finalmat, this.f4190b, ExportDetails.getInstance().getAdjustValue());
                            Log.d("ChechTask", "EffectTask adjust seq");
                        }
                        i++;
                    }
                } else {
                    this.f4190b = this.bitmap;
                    Log.d("ChechTask", "EffectTask orig");
                }
            } else if (ExportDetails.getInstance().getFragment_title().equals("FilterFragment")) {
                if (!this.effectFilterDetails.isEffecttofilter() || !this.effectFilterDetails.isEffectSelected()) {
                    Log.d("ChechTask", "bitmap filter null 2");
                    if (this.effectFilterDetails.isFilterSelected()) {
                        if (this.f4190b == null) {
                            this.f4190b = this.bitmap;
                            Log.d("ChechTask", "bitmap filter null 2");
                        }
                        if (this.effectFilterDetails.getFilterConstant() != null) {
                            this.f4190b = CGENativeLibrary.filterImage_MultipleEffects(Utils.convert(this.f4190b.copy(this.f4190b.getConfig(), true), Bitmap.Config.ARGB_8888), this.effectFilterDetails.getFilterConstant(), 1.0f);
                            this.f4190b = Utils.convert(this.f4190b, Bitmap.Config.RGB_565);
                        } else if (this.effectFilterDetails.getFilter() != null) {
                            this.gpuImage.setImage(this.f4190b);
                            this.gpuImage.setFilter(this.effectFilterDetails.getFilter().getFilter());
                            this.f4190b = this.gpuImage.getBitmapWithFilterApplied();
                        }
                    } else {
                        this.f4190b = this.bitmap;
                    }
                    Log.d("ChechTask", "Fragment filter");
                } else {
                    while (i < this.sequence.size()) {
                        if (this.sequence.get(i).equals("effect")) {
                            if (this.effect != null && this.effect.isOriginal_image()) {
                                this.effect.setHd_image_height(this.bitmap.getHeight());
                                this.effect.setHd_image_width(this.bitmap.getWidth());
                            }
                            this.f4190b = this.effect.getEffect(this.effectName, this.bitmap);
                            Log.d("ChechTask", "EffectTask effect seq");
                        }
                        if (this.sequence.get(i).equals("adjust") && this.f4190b != null) {
                            org.opencv.android.Utils.bitmapToMat(this.bitmap.copy(this.bitmap.getConfig(), true), this.original);
                            org.opencv.android.Utils.bitmapToMat(this.f4190b.copy(this.f4190b.getConfig(), true), this.filtered);
                            this.f4190b = this.bitmap.copy(this.bitmap.getConfig(), true);
                            this.f4190b = Utils.blendBitmaps(this.original, this.filtered, this.finalmat, this.f4190b, ExportDetails.getInstance().getAdjustValue());
                            Log.d("ChechTask", "EffectTask adjust seq");
                        }
                        if (this.sequence.get(i).equals("filter") && this.effectFilterDetails.isFilterSelected()) {
                            if (this.f4190b == null) {
                                this.f4190b = this.bitmap;
                                Log.d("ChechTask", "bitmap filter null");
                            }
                            if (this.effectFilterDetails.getFilterConstant() != null) {
                                this.f4190b = CGENativeLibrary.filterImage_MultipleEffects(Utils.convert(this.f4190b.copy(this.f4190b.getConfig(), true), Bitmap.Config.ARGB_8888), this.effectFilterDetails.getFilterConstant(), 1.0f);
                                this.f4190b = Utils.convert(this.f4190b, Bitmap.Config.RGB_565);
                            } else if (this.effectFilterDetails.getFilter() != null) {
                                this.gpuImage.setImage(this.f4190b);
                                this.gpuImage.setFilter(this.effectFilterDetails.getFilter().getFilter());
                                this.f4190b = this.gpuImage.getBitmapWithFilterApplied();
                            }
                        }
                        i++;
                    }
                }
            } else if (ExportDetails.getInstance().getFragment_title().equals("EditorFragment")) {
                Log.d("ChechTask", "EditorFragment");
                if (this.effectFilterDetails.isEditorFilterSelected().booleanValue()) {
                    Log.d("ChechTask", "EditorFilterSelected");
                    if (!this.effectFilterDetails.isEffecttofilter() || !this.effectFilterDetails.isEffectSelected()) {
                        Log.d("ChechTask", "bitmap filter null 2");
                        if (this.effectFilterDetails.isFilterSelected()) {
                            if (this.f4190b == null) {
                                this.f4190b = this.bitmap;
                                Log.d("ChechTask", "bitmap filter null 2");
                            }
                            if (this.effectFilterDetails.getFilterConstant() != null) {
                                this.f4190b = CGENativeLibrary.filterImage_MultipleEffects(Utils.convert(this.f4190b.copy(this.f4190b.getConfig(), true), Bitmap.Config.ARGB_8888), this.effectFilterDetails.getFilterConstant(), 1.0f);
                                this.f4190b = Utils.convert(this.f4190b, Bitmap.Config.RGB_565);
                            } else if (this.effectFilterDetails.getFilter() != null) {
                                this.gpuImage.setImage(this.f4190b);
                                this.gpuImage.setFilter(this.effectFilterDetails.getFilter().getFilter());
                                this.f4190b = this.gpuImage.getBitmapWithFilterApplied();
                            }
                        } else {
                            this.f4190b = this.bitmap;
                        }
                        Log.d("ChechTask", "Fragment filter");
                    } else {
                        while (i < this.sequence.size()) {
                            if (this.sequence.get(i).equals("effect")) {
                                if (this.effect != null && this.effect.isOriginal_image()) {
                                    this.effect.setHd_image_height(this.bitmap.getHeight());
                                    this.effect.setHd_image_width(this.bitmap.getWidth());
                                }
                                this.f4190b = this.effect.getEffect(this.effectName, this.bitmap);
                                Log.d("ChechTask", "EffectTask effect seq");
                            }
                            if (this.sequence.get(i).equals("adjust") && this.f4190b != null) {
                                org.opencv.android.Utils.bitmapToMat(this.bitmap.copy(this.bitmap.getConfig(), true), this.original);
                                org.opencv.android.Utils.bitmapToMat(this.f4190b.copy(this.f4190b.getConfig(), true), this.filtered);
                                this.f4190b = this.bitmap.copy(this.bitmap.getConfig(), true);
                                this.f4190b = Utils.blendBitmaps(this.original, this.filtered, this.finalmat, this.f4190b, ExportDetails.getInstance().getAdjustValue());
                                Log.d("ChechTask", "EffectTask adjust seq");
                            }
                            if (this.sequence.get(i).equals("filter") && this.effectFilterDetails.isFilterSelected()) {
                                if (this.f4190b == null) {
                                    this.f4190b = this.bitmap;
                                    Log.d("ChechTask", "bitmap filter null");
                                }
                                if (this.effectFilterDetails.getFilterConstant() != null) {
                                    this.f4190b = CGENativeLibrary.filterImage_MultipleEffects(Utils.convert(this.f4190b.copy(this.f4190b.getConfig(), true), Bitmap.Config.ARGB_8888), this.effectFilterDetails.getFilterConstant(), 1.0f);
                                    this.f4190b = Utils.convert(this.f4190b, Bitmap.Config.RGB_565);
                                } else if (this.effectFilterDetails.getFilter() != null) {
                                    this.gpuImage.setImage(this.f4190b);
                                    this.gpuImage.setFilter(this.effectFilterDetails.getFilter().getFilter());
                                    this.f4190b = this.gpuImage.getBitmapWithFilterApplied();
                                    Log.d("ChechTask", "EffectTask filter seq");
                                }
                            }
                            i++;
                        }
                    }
                } else if (this.effectFilterDetails.isEditorEffectSelected().booleanValue()) {
                    Log.d("ChechTask", "EditorEffectSelected");
                    while (i < this.sequence.size()) {
                        if (this.sequence.get(i).equals("effect")) {
                            if (this.effect != null && this.effect.isOriginal_image()) {
                                this.effect.setHd_image_height(this.bitmap.getHeight());
                                this.effect.setHd_image_width(this.bitmap.getWidth());
                            }
                            this.f4190b = this.effect.getEffect(this.effectName, this.bitmap);
                            this.f4190b = Utils.convert(this.f4190b, Bitmap.Config.ARGB_8888);
                            Log.d("ChechTask", "EffectTask effect seq");
                        }
                        if (this.sequence.get(i).equals("adjust") && this.f4190b != null) {
                            org.opencv.android.Utils.bitmapToMat(this.bitmap.copy(this.bitmap.getConfig(), true), this.original);
                            org.opencv.android.Utils.bitmapToMat(this.f4190b.copy(this.f4190b.getConfig(), true), this.filtered);
                            this.f4190b = this.bitmap.copy(this.bitmap.getConfig(), true);
                            this.f4190b = Utils.blendBitmaps(this.original, this.filtered, this.finalmat, this.f4190b, ExportDetails.getInstance().getAdjustValue());
                            this.f4190b = Utils.convert(this.f4190b, Bitmap.Config.ARGB_8888);
                            Log.d("ChechTask", "EffectTask adjust seq");
                        }
                        i++;
                    }
                }
                if (this.effectFilterDetails.getFilterString() != null) {
                    if (this.f4190b == null) {
                        this.f4190b = this.bitmap;
                    }
                    this.f4190b = CGENativeLibrary.filterImage_MultipleEffects(this.f4190b, this.effectFilterDetails.getFilterString(), 1.0f);
                    Log.d("ChechTask", "EffectTask editor seq");
                }
                if (this.f4190b == null) {
                    this.f4190b = this.bitmap;
                    Log.d("ChechTask", "bitmap filter null 2");
                }
            }
        }
        return this.f4190b;
    }

    /* access modifiers changed from: protected */
    public void onPostExecute(Bitmap bitmap2) {
        super.onPostExecute(bitmap2);
        if (this.taskCompleteListener != null) {
            this.taskCompleteListener.OnTaskComplete();
        }
        Log.d("ChechTask", "Complete");
        if (this.effect != null) {
            this.effect.setOriginal_image(false);
        }
    }
}
