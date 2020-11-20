package com.teamvinay.sketch;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import com.bumptech.glide.Registry;
import helper.Constants;
import helper.ExportDetails;
import java.util.ArrayList;
import java.util.List;
import listeners.TaskCompleteListener;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Mat;
import org.wysaid.nativePort.CGENativeLibrary;
import jp.co.cyberagent.android.gpuimage.GPUImage;
import util.BitmapCache;
import util.Effect;
import util.Utils;

public class EffectFilterTask extends AsyncTask<Void, Void, Bitmap> {

    /* renamed from: b */
    Bitmap f4191b = null;
    Bitmap bitmap;
    BitmapCache bitmapCache;
    Effect effect = null;
    EffectFilterDetails effectFilterDetails;
    String effectName = null;
    Filter filter = null;
    Mat filtered;
    Mat finalmat;
    GPUImage gpuImage = null;
    Dialog loading_dialog;
    Context mContext;
    Mat original;
    Bitmap result = null;
    private List<String> sequence = new ArrayList();
    TaskCompleteListener taskCompleteListener = null;
    String type = null;

    public EffectFilterTask(String str, EffectFilterDetails effectFilterDetails2, Bitmap bitmap2, Context context, TaskCompleteListener taskCompleteListener2, Dialog dialog) {
        this.type = str;
        this.mContext = context;
        this.effectFilterDetails = effectFilterDetails2;
        this.bitmap = bitmap2;
        this.taskCompleteListener = taskCompleteListener2;
        if (dialog != null) {
            this.loading_dialog = dialog;
        }
        if (ExportDetails.getInstance().getBitmapCache() != null) {
            this.bitmapCache = ExportDetails.getInstance().getBitmapCache();
        }
        if (this.type.equals("Effect")) {
            if (this.effectFilterDetails.getEffect() != null) {
                this.effect = this.effectFilterDetails.getEffect();
            }
            if (this.effectFilterDetails.getEffectName() != null) {
                this.effectName = this.effectFilterDetails.getEffectName();
            }
        } else if (this.type.equals(Registry.BUCKET_BITMAP)) {
            if (ExportDetails.getInstance().getBitmapCache() != null) {
                this.bitmapCache = ExportDetails.getInstance().getBitmapCache();
            }
        } else if (this.type.equals("EffectToFilter")) {
            if (this.effectFilterDetails.getEffect() != null) {
                this.effect = this.effectFilterDetails.getEffect();
            }
            if (this.effectFilterDetails.getEffectName() != null) {
                this.effectName = this.effectFilterDetails.getEffectName();
            }
            if (this.effectFilterDetails.getFilter() != null) {
                this.filter = this.effectFilterDetails.getFilter();
            }
            this.gpuImage = new GPUImage(this.mContext);
        } else if (this.type.equals("Filter")) {
            if (this.effectFilterDetails.getFilter() != null) {
                this.filter = this.effectFilterDetails.getFilter();
            }
            if (this.effectFilterDetails.getEffect() != null) {
                this.effect = this.effectFilterDetails.getEffect();
            }
            if (this.effectFilterDetails.getEffectName() != null) {
                this.effectName = this.effectFilterDetails.getEffectName();
            }
            this.gpuImage = new GPUImage(this.mContext);
        }
        if (this.effectFilterDetails.getSequence().size() >= 0) {
            this.sequence = this.effectFilterDetails.getSequence();
        }
        if (OpenCVLoader.initDebug()) {
            this.original = new Mat();
            this.filtered = new Mat();
            this.finalmat = new Mat();
        }
        if (this.bitmap == null) {
            Log.d("EditorFragmentBit", "null");
        }
    }

    /* access modifiers changed from: protected */
    public Bitmap doInBackground(Void... voidArr) {
        Log.d("EffectFilterTask", this.type);
        int i = 0;
        if (this.type.equals("Effect")) {
            Log.d("EffectFilterTask", "EffectTask effect");
            if (this.effectName != null) {
                while (i < this.effectFilterDetails.getSequence().size()) {
                    if (this.effectFilterDetails.getSequence().get(i).equals("effect")) {
                        this.result = this.effect.getEffect(this.effectName, this.bitmap.copy(this.bitmap.getConfig(), true));
                        this.result = Utils.convert(this.result, Bitmap.Config.ARGB_8888);
                        Log.d("EffectFilterTask", "EffectTask effect seq" + String.valueOf(this.result.getConfig()));
                    }
                    if (this.effectFilterDetails.getSequence().get(i).equals("adjust") && this.result != null) {
                        org.opencv.android.Utils.bitmapToMat(this.bitmap.copy(this.bitmap.getConfig(), true), this.original);
                        org.opencv.android.Utils.bitmapToMat(this.result.copy(this.result.getConfig(), true), this.filtered);
                        this.result = this.bitmap.copy(this.bitmap.getConfig(), true);
                        this.result = Utils.blendBitmaps(this.original, this.filtered, this.finalmat, this.result, ExportDetails.getInstance().getAdjustValue());
                        Log.d("EffectFilterTask", "EffectTask adjust seq");
                    }
                    i++;
                }
            } else if (this.type.equals(Registry.BUCKET_BITMAP)) {
                if (this.bitmapCache != null) {
                    this.result = this.bitmapCache.getBitmapFromDiskCache(Constants.ORIGINAL_IMAGE);
                }
            } else if (this.type.equals("EffectToFilter")) {
                Log.d("EffectFilterTask", "EffectTask effecttofilter");
                if (this.effectName != null) {
                    this.result = Utils.convert(this.effect.getEffect(this.effectName, this.result.copy(this.result.getConfig(), true)), Bitmap.Config.RGB_565);
                }
                if (this.filter != null) {
                    if (this.filter.getConstant() != null) {
                        this.result = CGENativeLibrary.filterImage_MultipleEffects(Utils.convert(this.result.copy(this.result.getConfig(), true), Bitmap.Config.ARGB_8888), this.filter.getConstant(), 1.0f);
                    } else {
                        this.gpuImage.setImage(this.result);
                        this.gpuImage.setFilter(this.filter.getFilter());
                        this.result = this.gpuImage.getBitmapWithFilterApplied();
                    }
                }
            } else if (this.type.equals("Filter")) {
                Log.d("EffectFilterTask", "EffectTask filter");
                if (this.filter != null) {
                    if (this.filter.getConstant() != null) {
                        this.result = CGENativeLibrary.filterImage_MultipleEffects(Utils.convert(this.bitmap.copy(this.bitmap.getConfig(), true), Bitmap.Config.ARGB_8888), this.filter.getConstant(), 1.0f);
                        PhotoActivity.ShowMessage("EffectFilterTask", "  constant");
                    } else {
                        this.gpuImage.setImage(this.bitmap);
                        this.gpuImage.setFilter(this.filter.getFilter());
                        this.result = this.gpuImage.getBitmapWithFilterApplied();
                        PhotoActivity.ShowMessage("EffectFilterTask", "  filter");
                    }
                }
            }
        } else if (this.type.equals("Filter")) {
            Log.d("EffectFilterTask", "Filter run");
            if (this.effectFilterDetails.isEffecttofilter() && this.effectFilterDetails.isEffectSelected()) {
                while (i < this.sequence.size()) {
                    if (this.sequence.get(i).equals("effect")) {
                        this.result = this.effect.getEffect(this.effectName, this.bitmap);
                        Log.d("EffectFilterTask", "EffectTask effect seq");
                    }
                    if (this.sequence.get(i).equals("adjust") && this.result != null) {
                        org.opencv.android.Utils.bitmapToMat(this.bitmap.copy(this.bitmap.getConfig(), true), this.original);
                        org.opencv.android.Utils.bitmapToMat(this.result.copy(this.result.getConfig(), true), this.filtered);
                        this.result = this.bitmap.copy(this.bitmap.getConfig(), true);
                        this.result = Utils.blendBitmaps(this.original, this.filtered, this.finalmat, this.result, ExportDetails.getInstance().getAdjustValue());
                        Log.d("EffectFilterTask", "EffectTask adjust seq");
                    }
                    if (this.sequence.get(i).equals("filter") && this.effectFilterDetails.isFilterSelected()) {
                        if (this.result == null) {
                            this.result = this.bitmap;
                            Log.d("EffectFilterTask", "bitmap filter null");
                        }
                        if (this.effectFilterDetails.getFilterConstant() != null) {
                            this.result = CGENativeLibrary.filterImage_MultipleEffects(Utils.convert(this.result.copy(this.result.getConfig(), true), Bitmap.Config.ARGB_8888), this.effectFilterDetails.getFilterConstant(), 1.0f);
                            this.result = Utils.convert(this.result, Bitmap.Config.RGB_565);
                        } else if (this.effectFilterDetails.getFilter() != null) {
                            this.gpuImage.setImage(this.result);
                            this.gpuImage.setFilter(this.effectFilterDetails.getFilter().getFilter());
                            this.result = this.gpuImage.getBitmapWithFilterApplied();
                        }
                    }
                    Log.d("EffectFilterTask", "EffectFilter");
                    i++;
                }
            } else if (this.effectFilterDetails.isFilterSelected()) {
                if (this.result == null) {
                    this.result = this.bitmap;
                    Log.d("EffectFilterTask", "bitmap filter null");
                }
                if (this.effectFilterDetails.getFilterConstant() != null) {
                    this.result = CGENativeLibrary.filterImage_MultipleEffects(Utils.convert(this.result.copy(this.result.getConfig(), true), Bitmap.Config.ARGB_8888), this.effectFilterDetails.getFilterConstant(), 1.0f);
                    this.result = Utils.convert(this.result, Bitmap.Config.RGB_565);
                } else if (this.effectFilterDetails.getFilter() != null) {
                    this.gpuImage.setImage(this.result);
                    this.gpuImage.setFilter(this.effectFilterDetails.getFilter().getFilter());
                    this.result = this.gpuImage.getBitmapWithFilterApplied();
                }
            }
        } else if (this.type.equals(Registry.BUCKET_BITMAP)) {
            if (this.bitmap != null) {
                this.result = this.bitmap;
            } else if (this.bitmapCache != null) {
                this.result = this.bitmapCache.getCacheBitmap(Constants.ORIGINAL_IMAGE);
            } else if (ExportDetails.getInstance().getBitmapCache() != null) {
                this.bitmapCache = ExportDetails.getInstance().getBitmapCache();
                this.result = this.bitmapCache.getCacheBitmap(Constants.ORIGINAL_IMAGE);
            }
            Log.d("ResetListener", "original");
        }
        return this.result;
    }

    /* access modifiers changed from: protected */
    public void onPreExecute() {
        super.onPreExecute();
        if (this.loading_dialog != null) {
            this.loading_dialog.show();
        }
        if (this.original == null) {
            this.original = new Mat();
        }
        if (this.filtered == null) {
            this.filtered = new Mat();
        }
        if (this.finalmat == null) {
            this.finalmat = new Mat();
        }
    }

    /* access modifiers changed from: protected */
    public void onPostExecute(Bitmap bitmap2) {
        super.onPostExecute(bitmap2);
        if (this.taskCompleteListener != null) {
            this.taskCompleteListener.OnTaskComplete();
        }
        if (this.loading_dialog != null && this.loading_dialog.isShowing()) {
            this.loading_dialog.dismiss();
        }
    }
}