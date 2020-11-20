package util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.Toast;
import com.teamvinay.sketch.App;
import com.teamvinay.sketch.R;
import util.*;

import helper.Constants;
import helper.Filters;
import java.util.Random;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;
import jp.co.cyberagent.android.gpuimage.GPUImage;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageColorInvertFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageGaussianBlurFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageGrayscaleFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageLinearBurnBlendFilter;

public class Effect implements Parcelable {
    public static final Parcelable.Creator<Effect> CREATOR = new Parcelable.Creator<Effect>() {
        public Effect createFromParcel(Parcel parcel) {
            return new Effect(parcel);
        }

        public Effect[] newArray(int i) {
            return new Effect[i];
        }
    };
    private BitmapCache bitmapCache;
    private Context context;
    private String effect_Name;
    private int hd_image_height;
    private int hd_image_width;
    private GPUImage image;
    private int imageHeight;
    private int imageWidth;
    private Mat laplace;
    private Mat original;
    private boolean original_image;

    /* renamed from: r */
    Random f6048r;
    private String resolution;

    public int describeContents() {
        return 0;
    }

    public void recycleBitmap(Bitmap bitmap) {
    }

    protected Effect(Parcel parcel) {
        this.effect_Name = null;
        this.resolution = null;
        boolean z = false;
        this.imageWidth = 0;
        this.imageHeight = 0;
        this.original_image = false;
        this.effect_Name = parcel.readString();
        this.resolution = parcel.readString();
        this.hd_image_width = parcel.readInt();
        this.hd_image_height = parcel.readInt();
        this.original_image = parcel.readByte() != 0 ? true : z;
    }

    public String getResolution() {
        return this.resolution;
    }

    public void setResolution(String str) {
        this.resolution = str;
    }

    public int getHd_image_width() {
        return this.hd_image_width;
    }

    public void setHd_image_width(int i) {
        this.hd_image_width = i;
    }

    public int getHd_image_height() {
        return this.hd_image_height;
    }

    public void setHd_image_height(int i) {
        this.hd_image_height = i;
    }

    public boolean isOriginal_image() {
        return this.original_image;
    }

    public void setOriginal_image(boolean z) {
        this.original_image = z;
    }

    public Effect(Context context2, BitmapCache bitmapCache2) {
        this.effect_Name = null;
        this.resolution = null;
        this.imageWidth = 0;
        this.imageHeight = 0;
        this.original_image = false;
        this.context = context2;
        this.bitmapCache = bitmapCache2;
        this.image = new GPUImage(context2);
        if (OpenCVLoader.initDebug()) {
            this.original = new Mat();
            this.laplace = new Mat();
        }
        Filters.init();
        this.f6048r = new Random();
    }

    public Bitmap getBitmap(int i, int i2) {
        return Bitmap.createBitmap(i, i2, Bitmap.Config.RGB_565);
    }

    /* JADX WARNING: type inference failed for: r10v0, types: [util.Effect] */
    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:552:0x1732, code lost:
        r12 = r0;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public android.graphics.Bitmap getEffect(java.lang.String r11, android.graphics.Bitmap r12) {
        /*
            r10 = this;
            jp.co.cyberagent.android.gpuimage.GPUImage r0 = r10.image
            if (r0 != 0) goto L_0x0028
            android.content.Context r0 = r10.context
            if (r0 == 0) goto L_0x0012
            jp.co.cyberagent.android.gpuimage.GPUImage r0 = new jp.co.cyberagent.android.gpuimage.GPUImage
            android.content.Context r1 = r10.context
            r0.<init>(r1)
            r10.image = r0
            goto L_0x0028
        L_0x0012:
            jp.co.cyberagent.android.gpuimage.GPUImage r0 = new jp.co.cyberagent.android.gpuimage.GPUImage
            com.hqgames.pencil.sketch.photo.App r1 = com.hqgames.pencil.sketch.photo.App.getInstance()
            android.content.Context r1 = r1.getApplicationContext()
            r0.<init>(r1)
            r10.image = r0
            java.lang.String r0 = "EffectContext"
            java.lang.String r1 = "appcontext"
            android.util.Log.d(r0, r1)
        L_0x0028:
            org.opencv.core.Mat r0 = r10.original
            if (r0 != 0) goto L_0x0033
            org.opencv.core.Mat r0 = new org.opencv.core.Mat
            r0.<init>()
            r10.original = r0
        L_0x0033:
            org.opencv.core.Mat r0 = r10.laplace
            if (r0 != 0) goto L_0x003e
            org.opencv.core.Mat r0 = new org.opencv.core.Mat
            r0.<init>()
            r10.laplace = r0
        L_0x003e:
            r0 = -1
            int r1 = r11.hashCode()
            r2 = 3
            r3 = 2
            r4 = 7
            r5 = 20
            r6 = 12
            r7 = 8
            r8 = 1
            switch(r1) {
                case -2075887794: goto L_0x0185;
                case -2014025173: goto L_0x017a;
                case -1997757957: goto L_0x016f;
                case -1981790649: goto L_0x0164;
                case -1907984083: goto L_0x015a;
                case -1816807476: goto L_0x014f;
                case -1653589809: goto L_0x0144;
                case -801549910: goto L_0x013a;
                case -717304834: goto L_0x012f;
                case -588644578: goto L_0x0124;
                case -558354122: goto L_0x0118;
                case -496829338: goto L_0x010d;
                case -366484067: goto L_0x0101;
                case -366001032: goto L_0x00f5;
                case -8246298: goto L_0x00ea;
                case 69062747: goto L_0x00de;
                case 69942638: goto L_0x00d2;
                case 106170808: goto L_0x00c6;
                case 323950648: goto L_0x00bb;
                case 493572826: goto L_0x00b0;
                case 514393161: goto L_0x00a4;
                case 546630690: goto L_0x0098;
                case 999744269: goto L_0x008c;
                case 1091114603: goto L_0x0080;
                case 1180969725: goto L_0x0075;
                case 1487110314: goto L_0x0069;
                case 2026444070: goto L_0x005d;
                case 2059935509: goto L_0x0052;
                default: goto L_0x0050;
            }
        L_0x0050:
            goto L_0x018f
        L_0x0052:
            java.lang.String r1 = "LIGHT_SKETCH"
            boolean r11 = r11.equals(r1)
            if (r11 == 0) goto L_0x018f
            r0 = 6
            goto L_0x018f
        L_0x005d:
            java.lang.String r1 = "Crayon"
            boolean r11 = r11.equals(r1)
            if (r11 == 0) goto L_0x018f
            r0 = 20
            goto L_0x018f
        L_0x0069:
            java.lang.String r1 = "Color Pencil"
            boolean r11 = r11.equals(r1)
            if (r11 == 0) goto L_0x018f
            r0 = 23
            goto L_0x018f
        L_0x0075:
            java.lang.String r1 = "ColorSketchTwo"
            boolean r11 = r11.equals(r1)
            if (r11 == 0) goto L_0x018f
            r0 = 4
            goto L_0x018f
        L_0x0080:
            java.lang.String r1 = "Drawing_Two"
            boolean r11 = r11.equals(r1)
            if (r11 == 0) goto L_0x018f
            r0 = 22
            goto L_0x018f
        L_0x008c:
            java.lang.String r1 = "Hard Stroke"
            boolean r11 = r11.equals(r1)
            if (r11 == 0) goto L_0x018f
            r0 = 15
            goto L_0x018f
        L_0x0098:
            java.lang.String r1 = "LIGHTSKETCH"
            boolean r11 = r11.equals(r1)
            if (r11 == 0) goto L_0x018f
            r0 = 9
            goto L_0x018f
        L_0x00a4:
            java.lang.String r1 = "Silhoute"
            boolean r11 = r11.equals(r1)
            if (r11 == 0) goto L_0x018f
            r0 = 18
            goto L_0x018f
        L_0x00b0:
            java.lang.String r1 = "Water Color"
            boolean r11 = r11.equals(r1)
            if (r11 == 0) goto L_0x018f
            r0 = 0
            goto L_0x018f
        L_0x00bb:
            java.lang.String r1 = "PaperSketch"
            boolean r11 = r11.equals(r1)
            if (r11 == 0) goto L_0x018f
            r0 = 3
            goto L_0x018f
        L_0x00c6:
            java.lang.String r1 = "PENCILDARKSTROKES"
            boolean r11 = r11.equals(r1)
            if (r11 == 0) goto L_0x018f
            r0 = 10
            goto L_0x018f
        L_0x00d2:
            java.lang.String r1 = "DarkStroke"
            boolean r11 = r11.equals(r1)
            if (r11 == 0) goto L_0x018f
            r0 = 11
            goto L_0x018f
        L_0x00de:
            java.lang.String r1 = "Grain"
            boolean r11 = r11.equals(r1)
            if (r11 == 0) goto L_0x018f
            r0 = 14
            goto L_0x018f
        L_0x00ea:
            java.lang.String r1 = "Water Color Two"
            boolean r11 = r11.equals(r1)
            if (r11 == 0) goto L_0x018f
            r0 = 1
            goto L_0x018f
        L_0x00f5:
            java.lang.String r1 = "Black_N_White"
            boolean r11 = r11.equals(r1)
            if (r11 == 0) goto L_0x018f
            r0 = 24
            goto L_0x018f
        L_0x0101:
            java.lang.String r1 = "Color Thick Edge"
            boolean r11 = r11.equals(r1)
            if (r11 == 0) goto L_0x018f
            r0 = 27
            goto L_0x018f
        L_0x010d:
            java.lang.String r1 = "CartoonFilter"
            boolean r11 = r11.equals(r1)
            if (r11 == 0) goto L_0x018f
            r0 = 2
            goto L_0x018f
        L_0x0118:
            java.lang.String r1 = "Silhoute_Two"
            boolean r11 = r11.equals(r1)
            if (r11 == 0) goto L_0x018f
            r0 = 19
            goto L_0x018f
        L_0x0124:
            java.lang.String r1 = "Pencil_Sketch"
            boolean r11 = r11.equals(r1)
            if (r11 == 0) goto L_0x018f
            r0 = 13
            goto L_0x018f
        L_0x012f:
            java.lang.String r1 = "Drawing"
            boolean r11 = r11.equals(r1)
            if (r11 == 0) goto L_0x018f
            r0 = 16
            goto L_0x018f
        L_0x013a:
            java.lang.String r1 = "PencilDarkShade"
            boolean r11 = r11.equals(r1)
            if (r11 == 0) goto L_0x018f
            r0 = 7
            goto L_0x018f
        L_0x0144:
            java.lang.String r1 = "ColorSketch"
            boolean r11 = r11.equals(r1)
            if (r11 == 0) goto L_0x018f
            r0 = 12
            goto L_0x018f
        L_0x014f:
            java.lang.String r1 = "Sketch"
            boolean r11 = r11.equals(r1)
            if (r11 == 0) goto L_0x018f
            r0 = 8
            goto L_0x018f
        L_0x015a:
            java.lang.String r1 = "Pencil"
            boolean r11 = r11.equals(r1)
            if (r11 == 0) goto L_0x018f
            r0 = 5
            goto L_0x018f
        L_0x0164:
            java.lang.String r1 = "Color Thin Edge"
            boolean r11 = r11.equals(r1)
            if (r11 == 0) goto L_0x018f
            r0 = 25
            goto L_0x018f
        L_0x016f:
            java.lang.String r1 = "Water_Color"
            boolean r11 = r11.equals(r1)
            if (r11 == 0) goto L_0x018f
            r0 = 17
            goto L_0x018f
        L_0x017a:
            java.lang.String r1 = "Color Medium Edge"
            boolean r11 = r11.equals(r1)
            if (r11 == 0) goto L_0x018f
            r0 = 26
            goto L_0x018f
        L_0x0185:
            java.lang.String r1 = "Cartoon"
            boolean r11 = r11.equals(r1)
            if (r11 == 0) goto L_0x018f
            r0 = 21
        L_0x018f:
            r11 = 2131230946(0x7f0800e2, float:1.807796E38)
            r1 = 1065353216(0x3f800000, float:1.0)
            r9 = 2131231056(0x7f080150, float:1.8078182E38)
            switch(r0) {
                case 0: goto L_0x15e5;
                case 1: goto L_0x14ba;
                case 2: goto L_0x13bf;
                case 3: goto L_0x12b2;
                case 4: goto L_0x11a5;
                case 5: goto L_0x10ca;
                case 6: goto L_0x0fef;
                case 7: goto L_0x0f14;
                case 8: goto L_0x0e35;
                case 9: goto L_0x0d32;
                case 10: goto L_0x0c2f;
                case 11: goto L_0x0b54;
                case 12: goto L_0x0a7b;
                case 13: goto L_0x0a21;
                case 14: goto L_0x093b;
                case 15: goto L_0x0872;
                case 16: goto L_0x078d;
                case 17: goto L_0x0740;
                case 18: goto L_0x064a;
                case 19: goto L_0x0567;
                case 20: goto L_0x04d8;
                case 21: goto L_0x0442;
                case 22: goto L_0x03b6;
                case 23: goto L_0x0327;
                case 24: goto L_0x02ac;
                case 25: goto L_0x0258;
                case 26: goto L_0x0201;
                case 27: goto L_0x019c;
                default: goto L_0x019a;
            }
        L_0x019a:
            goto L_0x1740
        L_0x019c:
            boolean r11 = r10.original_image
            if (r11 == 0) goto L_0x01c1
            android.graphics.Bitmap$Config r11 = r12.getConfig()
            android.graphics.Bitmap r11 = r12.copy(r11, r8)
            org.opencv.core.Mat r12 = util.Utils.ChalkEffect(r12, r8, r3)
            org.opencv.android.Utils.matToBitmap(r12, r11)
            android.graphics.Bitmap$Config r12 = android.graphics.Bitmap.Config.RGB_565
            android.graphics.Bitmap r12 = util.Utils.convert(r11, r12)
            r10.recycleBitmap(r11)
            java.lang.String r11 = "EffectName"
            java.lang.String r0 = "ThickEdge with original"
            android.util.Log.d(r11, r0)
            goto L_0x1740
        L_0x01c1:
            util.BitmapCache r11 = r10.bitmapCache
            java.lang.String r0 = helper.Constants.COLOR_CHALK_THICK
            android.graphics.Bitmap r11 = r11.getCacheBitmap(r0)
            if (r11 != 0) goto L_0x01ef
            java.lang.String r11 = "Color Chalk Thick Edge"
            r10.effect_Name = r11
            android.graphics.Bitmap$Config r11 = r12.getConfig()
            android.graphics.Bitmap r11 = r12.copy(r11, r8)
            org.opencv.core.Mat r12 = util.Utils.ChalkEffect(r12, r8, r2)
            org.opencv.android.Utils.matToBitmap(r12, r11)
            android.graphics.Bitmap$Config r12 = android.graphics.Bitmap.Config.RGB_565
            android.graphics.Bitmap r12 = util.Utils.convert(r11, r12)
            util.BitmapCache r0 = r10.bitmapCache
            java.lang.String r1 = helper.Constants.COLOR_CHALK_THICK
            r0.addBitmapToCache(r1, r12)
            r10.recycleBitmap(r11)
            goto L_0x01f8
        L_0x01ef:
            util.BitmapCache r11 = r10.bitmapCache
            java.lang.String r12 = helper.Constants.COLOR_CHALK_THICK
            android.graphics.Bitmap r11 = r11.getCacheBitmap(r12)
            r12 = r11
        L_0x01f8:
            java.lang.String r11 = "EffectName"
            java.lang.String r0 = "ThickEdge without original"
            android.util.Log.d(r11, r0)
            goto L_0x1740
        L_0x0201:
            boolean r11 = r10.original_image
            if (r11 == 0) goto L_0x021f
            android.graphics.Bitmap$Config r11 = r12.getConfig()
            android.graphics.Bitmap r11 = r12.copy(r11, r8)
            org.opencv.core.Mat r12 = util.Utils.ChalkEffect(r12, r8, r3)
            org.opencv.android.Utils.matToBitmap(r12, r11)
            android.graphics.Bitmap$Config r12 = android.graphics.Bitmap.Config.RGB_565
            android.graphics.Bitmap r12 = util.Utils.convert(r11, r12)
            r10.recycleBitmap(r11)
            goto L_0x1740
        L_0x021f:
            util.BitmapCache r11 = r10.bitmapCache
            java.lang.String r0 = helper.Constants.COLOR_CHALK_MEDIUM
            android.graphics.Bitmap r11 = r11.getCacheBitmap(r0)
            if (r11 != 0) goto L_0x024e
            java.lang.String r11 = "Color Chalk Medium Edge"
            r10.effect_Name = r11
            android.graphics.Bitmap$Config r11 = r12.getConfig()
            android.graphics.Bitmap r11 = r12.copy(r11, r8)
            org.opencv.core.Mat r12 = util.Utils.ChalkEffect(r12, r8, r3)
            org.opencv.android.Utils.matToBitmap(r12, r11)
            android.graphics.Bitmap$Config r12 = android.graphics.Bitmap.Config.RGB_565
            android.graphics.Bitmap r12 = util.Utils.convert(r11, r12)
            util.BitmapCache r0 = r10.bitmapCache
            java.lang.String r1 = helper.Constants.COLOR_CHALK_MEDIUM
            r0.addBitmapToCache(r1, r12)
            r10.recycleBitmap(r11)
            goto L_0x1740
        L_0x024e:
            util.BitmapCache r11 = r10.bitmapCache
            java.lang.String r12 = helper.Constants.COLOR_CHALK_MEDIUM
            android.graphics.Bitmap r12 = r11.getCacheBitmap(r12)
            goto L_0x1740
        L_0x0258:
            boolean r11 = r10.original_image
            if (r11 == 0) goto L_0x0273
            android.graphics.Bitmap$Config r11 = r12.getConfig()
            android.graphics.Bitmap r11 = r12.copy(r11, r8)
            org.opencv.core.Mat r12 = util.Utils.ChalkEffect(r12, r8, r8)
            org.opencv.android.Utils.matToBitmap(r12, r11)
            android.graphics.Bitmap$Config r12 = android.graphics.Bitmap.Config.RGB_565
            android.graphics.Bitmap r12 = util.Utils.convert(r11, r12)
            goto L_0x1740
        L_0x0273:
            util.BitmapCache r11 = r10.bitmapCache
            java.lang.String r0 = helper.Constants.COLOR_CHALK_THIN
            android.graphics.Bitmap r11 = r11.getCacheBitmap(r0)
            if (r11 != 0) goto L_0x02a2
            java.lang.String r11 = "Color Chalk Thin Edge"
            r10.effect_Name = r11
            android.graphics.Bitmap$Config r11 = r12.getConfig()
            android.graphics.Bitmap r11 = r12.copy(r11, r8)
            org.opencv.core.Mat r12 = util.Utils.ChalkEffect(r12, r8, r8)
            org.opencv.android.Utils.matToBitmap(r12, r11)
            android.graphics.Bitmap$Config r12 = android.graphics.Bitmap.Config.RGB_565
            android.graphics.Bitmap r12 = util.Utils.convert(r11, r12)
            util.BitmapCache r0 = r10.bitmapCache
            java.lang.String r1 = helper.Constants.COLOR_CHALK_THIN
            r0.addBitmapToCache(r1, r12)
            r10.recycleBitmap(r11)
            goto L_0x1740
        L_0x02a2:
            util.BitmapCache r11 = r10.bitmapCache
            java.lang.String r12 = helper.Constants.COLOR_CHALK_THIN
            android.graphics.Bitmap r12 = r11.getCacheBitmap(r12)
            goto L_0x1740
        L_0x02ac:
            boolean r11 = r10.original_image
            if (r11 == 0) goto L_0x02da
            org.opencv.core.Mat r11 = r10.original
            org.opencv.android.Utils.bitmapToMat(r12, r11)
            org.opencv.core.Mat r11 = r10.original
            long r0 = r11.getNativeObjAddr()
            helper.Filters.BlackNWhite(r0)
            int r11 = r12.getWidth()
            int r12 = r12.getHeight()
            android.graphics.Bitmap r11 = r10.getBitmap(r11, r12)
            org.opencv.core.Mat r12 = r10.original
            org.opencv.android.Utils.matToBitmap(r12, r11)
            android.graphics.Bitmap$Config r12 = android.graphics.Bitmap.Config.RGB_565
            android.graphics.Bitmap r12 = util.Utils.convert(r11, r12)
            r10.recycleBitmap(r11)
            goto L_0x1740
        L_0x02da:
            util.BitmapCache r11 = r10.bitmapCache
            java.lang.String r0 = helper.Constants.BLACK_N_WHITE
            android.graphics.Bitmap r11 = r11.getCacheBitmap(r0)
            if (r11 != 0) goto L_0x0319
            java.lang.String r11 = "Black N White Sketch"
            r10.effect_Name = r11
            org.opencv.core.Mat r11 = r10.original
            org.opencv.android.Utils.bitmapToMat(r12, r11)
            org.opencv.core.Mat r11 = r10.original
            long r0 = r11.getNativeObjAddr()
            helper.Filters.BlackNWhite(r0)
            int r11 = r12.getWidth()
            int r12 = r12.getHeight()
            android.graphics.Bitmap r11 = r10.getBitmap(r11, r12)
            org.opencv.core.Mat r12 = r10.original
            org.opencv.android.Utils.matToBitmap(r12, r11)
            android.graphics.Bitmap$Config r12 = android.graphics.Bitmap.Config.RGB_565
            android.graphics.Bitmap r12 = util.Utils.convert(r11, r12)
            util.BitmapCache r0 = r10.bitmapCache
            java.lang.String r1 = helper.Constants.BLACK_N_WHITE
            r0.addBitmapToCache(r1, r12)
            r10.recycleBitmap(r11)
            goto L_0x1740
        L_0x0319:
            java.lang.String r11 = "Black N White Sketch"
            r10.effect_Name = r11
            util.BitmapCache r11 = r10.bitmapCache
            java.lang.String r12 = helper.Constants.BLACK_N_WHITE
            android.graphics.Bitmap r12 = r11.getCacheBitmap(r12)
            goto L_0x1740
        L_0x0327:
            boolean r11 = r10.original_image
            if (r11 == 0) goto L_0x0369
            int r11 = r12.getWidth()
            int r0 = r12.getHeight()
            android.graphics.Bitmap r11 = r10.getBitmap(r11, r0)
            org.opencv.core.Mat r0 = r10.original
            org.opencv.android.Utils.bitmapToMat(r12, r0)
            java.lang.String r12 = r10.resolution
            java.lang.String r0 = "fourk"
            boolean r12 = r12.equals(r0)
            if (r12 == 0) goto L_0x0350
            org.opencv.core.Mat r12 = r10.original
            long r0 = r12.getNativeObjAddr()
            helper.Filters.ColorPencil4K(r0)
            goto L_0x0359
        L_0x0350:
            org.opencv.core.Mat r12 = r10.original
            long r0 = r12.getNativeObjAddr()
            helper.Filters.ColorPencil(r0)
        L_0x0359:
            org.opencv.core.Mat r12 = r10.original
            org.opencv.android.Utils.matToBitmap(r12, r11)
            android.graphics.Bitmap$Config r12 = android.graphics.Bitmap.Config.RGB_565
            android.graphics.Bitmap r12 = util.Utils.convert(r11, r12)
            r10.recycleBitmap(r11)
            goto L_0x1740
        L_0x0369:
            util.BitmapCache r11 = r10.bitmapCache
            java.lang.String r0 = helper.Constants.COLOR_PENCIL
            android.graphics.Bitmap r11 = r11.getCacheBitmap(r0)
            if (r11 != 0) goto L_0x03a8
            java.lang.String r11 = "Color Pencil"
            r10.effect_Name = r11
            int r11 = r12.getWidth()
            int r0 = r12.getHeight()
            android.graphics.Bitmap r11 = r10.getBitmap(r11, r0)
            org.opencv.core.Mat r0 = r10.original
            org.opencv.android.Utils.bitmapToMat(r12, r0)
            org.opencv.core.Mat r12 = r10.original
            long r0 = r12.getNativeObjAddr()
            helper.Filters.ColorPencil(r0)
            org.opencv.core.Mat r12 = r10.original
            org.opencv.android.Utils.matToBitmap(r12, r11)
            android.graphics.Bitmap$Config r12 = android.graphics.Bitmap.Config.RGB_565
            android.graphics.Bitmap r12 = util.Utils.convert(r11, r12)
            util.BitmapCache r0 = r10.bitmapCache
            java.lang.String r1 = helper.Constants.COLOR_PENCIL
            r0.addBitmapToCache(r1, r12)
            r10.recycleBitmap(r11)
            goto L_0x1740
        L_0x03a8:
            java.lang.String r11 = "Color Pencil"
            r10.effect_Name = r11
            util.BitmapCache r11 = r10.bitmapCache
            java.lang.String r12 = helper.Constants.COLOR_PENCIL
            android.graphics.Bitmap r12 = r11.getCacheBitmap(r12)
            goto L_0x1740
        L_0x03b6:
            boolean r11 = r10.original_image
            if (r11 == 0) goto L_0x03f5
            org.opencv.core.Mat r11 = r10.original
            org.opencv.android.Utils.bitmapToMat(r12, r11)
            java.lang.String r11 = r10.resolution
            java.lang.String r0 = "fourk"
            boolean r11 = r11.equals(r0)
            if (r11 == 0) goto L_0x03d3
            org.opencv.core.Mat r11 = r10.original
            long r0 = r11.getNativeObjAddr()
            helper.Filters.DrawingTwo4K(r0)
            goto L_0x03dc
        L_0x03d3:
            org.opencv.core.Mat r11 = r10.original
            long r0 = r11.getNativeObjAddr()
            helper.Filters.DrawingTwoHD(r0)
        L_0x03dc:
            int r11 = r12.getWidth()
            int r12 = r12.getHeight()
            android.graphics.Bitmap r11 = r10.getBitmap(r11, r12)
            org.opencv.core.Mat r12 = r10.original
            org.opencv.android.Utils.matToBitmap(r12, r11)
            android.graphics.Bitmap$Config r12 = android.graphics.Bitmap.Config.RGB_565
            android.graphics.Bitmap r12 = util.Utils.convert(r11, r12)
            goto L_0x1740
        L_0x03f5:
            util.BitmapCache r11 = r10.bitmapCache
            java.lang.String r0 = helper.Constants.DRAWING_TWO
            android.graphics.Bitmap r11 = r11.getCacheBitmap(r0)
            if (r11 != 0) goto L_0x0434
            java.lang.String r11 = "Drawing Effect Two"
            r10.effect_Name = r11
            org.opencv.core.Mat r11 = r10.original
            org.opencv.android.Utils.bitmapToMat(r12, r11)
            org.opencv.core.Mat r11 = r10.original
            long r0 = r11.getNativeObjAddr()
            helper.Filters.DrawingTwo(r0)
            int r11 = r12.getWidth()
            int r12 = r12.getHeight()
            android.graphics.Bitmap r11 = r10.getBitmap(r11, r12)
            org.opencv.core.Mat r12 = r10.original
            org.opencv.android.Utils.matToBitmap(r12, r11)
            android.graphics.Bitmap$Config r12 = android.graphics.Bitmap.Config.RGB_565
            android.graphics.Bitmap r12 = util.Utils.convert(r11, r12)
            util.BitmapCache r0 = r10.bitmapCache
            java.lang.String r1 = helper.Constants.DRAWING_TWO
            r0.addBitmapToCache(r1, r12)
            r10.recycleBitmap(r11)
            goto L_0x1740
        L_0x0434:
            java.lang.String r11 = "Drawing Effect Two"
            r10.effect_Name = r11
            util.BitmapCache r11 = r10.bitmapCache
            java.lang.String r12 = helper.Constants.DRAWING_TWO
            android.graphics.Bitmap r12 = r11.getCacheBitmap(r12)
            goto L_0x1740
        L_0x0442:
            boolean r11 = r10.original_image
            if (r11 == 0) goto L_0x048b
            org.opencv.core.Mat r11 = r10.original
            org.opencv.android.Utils.bitmapToMat(r12, r11)
            java.lang.String r11 = r10.resolution
            java.lang.String r0 = "fourk"
            boolean r11 = r11.equals(r0)
            if (r11 == 0) goto L_0x045f
            org.opencv.core.Mat r11 = r10.original
            long r0 = r11.getNativeObjAddr()
            helper.Filters.CartoonArt4K(r0)
            goto L_0x0468
        L_0x045f:
            org.opencv.core.Mat r11 = r10.original
            long r0 = r11.getNativeObjAddr()
            helper.Filters.CartoonArtHD(r0)
        L_0x0468:
            int r11 = r12.getWidth()
            int r12 = r12.getHeight()
            android.graphics.Bitmap r11 = r10.getBitmap(r11, r12)
            org.opencv.core.Mat r12 = r10.original
            org.opencv.android.Utils.matToBitmap(r12, r11)
            android.graphics.Bitmap$Config r12 = android.graphics.Bitmap.Config.RGB_565
            android.graphics.Bitmap r12 = util.Utils.convert(r11, r12)
            r10.recycleBitmap(r11)
            java.lang.String r11 = "Effect Cartoon"
            java.lang.String r0 = "hd"
            android.util.Log.d(r11, r0)
            goto L_0x1740
        L_0x048b:
            util.BitmapCache r11 = r10.bitmapCache
            java.lang.String r0 = helper.Constants.CARTOON
            android.graphics.Bitmap r11 = r11.getCacheBitmap(r0)
            if (r11 != 0) goto L_0x04ca
            java.lang.String r11 = "Cartoon Art Effect"
            r10.effect_Name = r11
            org.opencv.core.Mat r11 = r10.original
            org.opencv.android.Utils.bitmapToMat(r12, r11)
            org.opencv.core.Mat r11 = r10.original
            long r0 = r11.getNativeObjAddr()
            helper.Filters.CartoonArt(r0)
            int r11 = r12.getWidth()
            int r12 = r12.getHeight()
            android.graphics.Bitmap r11 = r10.getBitmap(r11, r12)
            org.opencv.core.Mat r12 = r10.original
            org.opencv.android.Utils.matToBitmap(r12, r11)
            android.graphics.Bitmap$Config r12 = android.graphics.Bitmap.Config.RGB_565
            android.graphics.Bitmap r12 = util.Utils.convert(r11, r12)
            util.BitmapCache r0 = r10.bitmapCache
            java.lang.String r1 = helper.Constants.CARTOON
            r0.addBitmapToCache(r1, r12)
            r10.recycleBitmap(r11)
            goto L_0x1740
        L_0x04ca:
            java.lang.String r11 = "Cartoon Art Effect"
            r10.effect_Name = r11
            util.BitmapCache r11 = r10.bitmapCache
            java.lang.String r12 = helper.Constants.CARTOON
            android.graphics.Bitmap r12 = r11.getCacheBitmap(r12)
            goto L_0x1740
        L_0x04d8:
            boolean r11 = r10.original_image
            if (r11 == 0) goto L_0x051a
            org.opencv.core.Mat r11 = r10.original
            org.opencv.android.Utils.bitmapToMat(r12, r11)
            java.lang.String r11 = r10.resolution
            java.lang.String r0 = "fourk"
            boolean r11 = r11.equals(r0)
            if (r11 == 0) goto L_0x04f5
            org.opencv.core.Mat r11 = r10.original
            long r0 = r11.getNativeObjAddr()
            helper.Filters.Crayon4K(r0)
            goto L_0x04fe
        L_0x04f5:
            org.opencv.core.Mat r11 = r10.original
            long r0 = r11.getNativeObjAddr()
            helper.Filters.Crayon(r0)
        L_0x04fe:
            int r11 = r12.getWidth()
            int r12 = r12.getHeight()
            android.graphics.Bitmap r11 = r10.getBitmap(r11, r12)
            org.opencv.core.Mat r12 = r10.original
            org.opencv.android.Utils.matToBitmap(r12, r11)
            android.graphics.Bitmap$Config r12 = android.graphics.Bitmap.Config.RGB_565
            android.graphics.Bitmap r12 = util.Utils.convert(r11, r12)
            r10.recycleBitmap(r11)
            goto L_0x1740
        L_0x051a:
            util.BitmapCache r11 = r10.bitmapCache
            java.lang.String r0 = helper.Constants.CRAYON_DRAWING
            android.graphics.Bitmap r11 = r11.getCacheBitmap(r0)
            if (r11 != 0) goto L_0x0559
            java.lang.String r11 = "Crayon Effect"
            r10.effect_Name = r11
            org.opencv.core.Mat r11 = r10.original
            org.opencv.android.Utils.bitmapToMat(r12, r11)
            org.opencv.core.Mat r11 = r10.original
            long r0 = r11.getNativeObjAddr()
            helper.Filters.Crayon(r0)
            int r11 = r12.getWidth()
            int r12 = r12.getHeight()
            android.graphics.Bitmap r11 = r10.getBitmap(r11, r12)
            org.opencv.core.Mat r12 = r10.original
            org.opencv.android.Utils.matToBitmap(r12, r11)
            android.graphics.Bitmap$Config r12 = android.graphics.Bitmap.Config.RGB_565
            android.graphics.Bitmap r12 = util.Utils.convert(r11, r12)
            util.BitmapCache r0 = r10.bitmapCache
            java.lang.String r1 = helper.Constants.CRAYON_DRAWING
            r0.addBitmapToCache(r1, r12)
            r10.recycleBitmap(r11)
            goto L_0x1740
        L_0x0559:
            java.lang.String r11 = "Crayon Effect"
            r10.effect_Name = r11
            util.BitmapCache r11 = r10.bitmapCache
            java.lang.String r12 = helper.Constants.CRAYON_DRAWING
            android.graphics.Bitmap r12 = r11.getCacheBitmap(r12)
            goto L_0x1740
        L_0x0567:
            boolean r11 = r10.original_image
            if (r11 == 0) goto L_0x05ed
            org.opencv.core.Mat r11 = new org.opencv.core.Mat
            r11.<init>()
            int r0 = r12.getWidth()
            int r1 = r12.getHeight()
            android.graphics.Bitmap r0 = r10.getBitmap(r0, r1)
            java.lang.String r1 = r10.resolution
            java.lang.String r2 = "hd"
            boolean r1 = r1.equals(r2)
            if (r1 != 0) goto L_0x05c4
            java.lang.String r1 = r10.resolution
            java.lang.String r2 = "hdv"
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L_0x0591
            goto L_0x05c4
        L_0x0591:
            java.lang.String r1 = r10.resolution
            java.lang.String r2 = "full_hd"
            boolean r1 = r1.equals(r2)
            if (r1 != 0) goto L_0x05ba
            java.lang.String r1 = r10.resolution
            java.lang.String r2 = "twok"
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L_0x05a6
            goto L_0x05ba
        L_0x05a6:
            java.lang.String r1 = r10.resolution
            java.lang.String r2 = "fourk"
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L_0x05cd
            android.graphics.Bitmap r1 = r10.Sketch(r12, r5)
            org.opencv.core.Mat r2 = r10.original
            org.opencv.android.Utils.bitmapToMat(r1, r2)
            goto L_0x05cd
        L_0x05ba:
            android.graphics.Bitmap r1 = r10.Sketch(r12, r6)
            org.opencv.core.Mat r2 = r10.original
            org.opencv.android.Utils.bitmapToMat(r1, r2)
            goto L_0x05cd
        L_0x05c4:
            android.graphics.Bitmap r1 = r10.Sketch(r12, r7)
            org.opencv.core.Mat r2 = r10.original
            org.opencv.android.Utils.bitmapToMat(r1, r2)
        L_0x05cd:
            org.opencv.android.Utils.bitmapToMat(r12, r11)
            org.opencv.core.Mat r12 = r10.original
            long r1 = r12.getNativeObjAddr()
            long r11 = r11.getNativeObjAddr()
            helper.Filters.Gothic45HD(r1, r11)
            org.opencv.core.Mat r11 = r10.original
            org.opencv.android.Utils.matToBitmap(r11, r0)
            android.graphics.Bitmap$Config r11 = android.graphics.Bitmap.Config.RGB_565
            android.graphics.Bitmap r12 = util.Utils.convert(r0, r11)
            r10.recycleBitmap(r0)
            goto L_0x1740
        L_0x05ed:
            util.BitmapCache r11 = r10.bitmapCache
            java.lang.String r0 = helper.Constants.SILHOUTE_TWO
            android.graphics.Bitmap r11 = r11.getCacheBitmap(r0)
            if (r11 != 0) goto L_0x063c
            java.lang.String r11 = "Gothic Effect"
            r10.effect_Name = r11
            org.opencv.core.Mat r11 = new org.opencv.core.Mat
            r11.<init>()
            int r0 = r12.getWidth()
            int r1 = r12.getHeight()
            android.graphics.Bitmap r0 = r10.getBitmap(r0, r1)
            android.graphics.Bitmap r1 = r10.Sketch(r12, r7)
            org.opencv.core.Mat r2 = r10.original
            org.opencv.android.Utils.bitmapToMat(r1, r2)
            org.opencv.android.Utils.bitmapToMat(r12, r11)
            org.opencv.core.Mat r12 = r10.original
            long r1 = r12.getNativeObjAddr()
            long r11 = r11.getNativeObjAddr()
            helper.Filters.Gothic(r1, r11)
            org.opencv.core.Mat r11 = r10.original
            org.opencv.android.Utils.matToBitmap(r11, r0)
            android.graphics.Bitmap$Config r11 = android.graphics.Bitmap.Config.RGB_565
            android.graphics.Bitmap r12 = util.Utils.convert(r0, r11)
            util.BitmapCache r11 = r10.bitmapCache
            java.lang.String r1 = helper.Constants.SILHOUTE_TWO
            r11.addBitmapToCache(r1, r12)
            r10.recycleBitmap(r0)
            goto L_0x1740
        L_0x063c:
            java.lang.String r11 = "Gothic Effect"
            r10.effect_Name = r11
            util.BitmapCache r11 = r10.bitmapCache
            java.lang.String r12 = helper.Constants.SILHOUTE_TWO
            android.graphics.Bitmap r12 = r11.getCacheBitmap(r12)
            goto L_0x1740
        L_0x064a:
            boolean r11 = r10.original_image
            if (r11 == 0) goto L_0x06e3
            org.opencv.core.Mat r11 = new org.opencv.core.Mat
            r11.<init>()
            int r0 = r12.getWidth()
            int r1 = r12.getHeight()
            android.graphics.Bitmap r0 = r10.getBitmap(r0, r1)
            android.graphics.Bitmap$Config r1 = r12.getConfig()
            android.graphics.Bitmap r1 = r12.copy(r1, r8)
            android.graphics.Bitmap$Config r2 = android.graphics.Bitmap.Config.RGB_565
            android.graphics.Bitmap r1 = util.Utils.convert(r1, r2)
            org.opencv.core.Mat r2 = r10.original
            org.opencv.android.Utils.bitmapToMat(r1, r2)
            org.opencv.android.Utils.bitmapToMat(r12, r11)
            java.lang.String r1 = r10.resolution
            java.lang.String r2 = "hd"
            boolean r1 = r1.equals(r2)
            if (r1 != 0) goto L_0x06bd
            java.lang.String r1 = r10.resolution
            java.lang.String r2 = "hdv"
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L_0x068a
            goto L_0x06bd
        L_0x068a:
            java.lang.String r1 = r10.resolution
            java.lang.String r2 = "full_hd"
            boolean r1 = r1.equals(r2)
            if (r1 != 0) goto L_0x06b3
            java.lang.String r1 = r10.resolution
            java.lang.String r2 = "twok"
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L_0x069f
            goto L_0x06b3
        L_0x069f:
            java.lang.String r1 = r10.resolution
            java.lang.String r2 = "fourk"
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L_0x06c6
            android.graphics.Bitmap r12 = r10.Sketch(r12, r5)
            org.opencv.core.Mat r1 = r10.original
            org.opencv.android.Utils.bitmapToMat(r12, r1)
            goto L_0x06c6
        L_0x06b3:
            android.graphics.Bitmap r12 = r10.Sketch(r12, r6)
            org.opencv.core.Mat r1 = r10.original
            org.opencv.android.Utils.bitmapToMat(r12, r1)
            goto L_0x06c6
        L_0x06bd:
            android.graphics.Bitmap r12 = r10.Sketch(r12, r7)
            org.opencv.core.Mat r1 = r10.original
            org.opencv.android.Utils.bitmapToMat(r12, r1)
        L_0x06c6:
            org.opencv.core.Mat r12 = r10.original
            long r1 = r12.getNativeObjAddr()
            long r11 = r11.getNativeObjAddr()
            helper.Filters.Silhoute45HD(r1, r11)
            org.opencv.core.Mat r11 = r10.original
            org.opencv.android.Utils.matToBitmap(r11, r0)
            android.graphics.Bitmap$Config r11 = android.graphics.Bitmap.Config.RGB_565
            android.graphics.Bitmap r12 = util.Utils.convert(r0, r11)
            r10.recycleBitmap(r0)
            goto L_0x1740
        L_0x06e3:
            util.BitmapCache r11 = r10.bitmapCache
            java.lang.String r0 = helper.Constants.SILHOUTE
            android.graphics.Bitmap r11 = r11.getCacheBitmap(r0)
            if (r11 != 0) goto L_0x0732
            java.lang.String r11 = "Silhouette Effect"
            r10.effect_Name = r11
            org.opencv.core.Mat r11 = new org.opencv.core.Mat
            r11.<init>()
            int r0 = r12.getWidth()
            int r1 = r12.getHeight()
            android.graphics.Bitmap r0 = r10.getBitmap(r0, r1)
            android.graphics.Bitmap r1 = r10.Sketch(r12, r7)
            org.opencv.core.Mat r2 = r10.original
            org.opencv.android.Utils.bitmapToMat(r1, r2)
            org.opencv.android.Utils.bitmapToMat(r12, r11)
            org.opencv.core.Mat r12 = r10.original
            long r1 = r12.getNativeObjAddr()
            long r11 = r11.getNativeObjAddr()
            helper.Filters.Silhoute(r1, r11)
            org.opencv.core.Mat r11 = r10.original
            org.opencv.android.Utils.matToBitmap(r11, r0)
            android.graphics.Bitmap$Config r11 = android.graphics.Bitmap.Config.RGB_565
            android.graphics.Bitmap r12 = util.Utils.convert(r0, r11)
            util.BitmapCache r11 = r10.bitmapCache
            java.lang.String r1 = helper.Constants.SILHOUTE
            r11.addBitmapToCache(r1, r12)
            r10.recycleBitmap(r0)
            goto L_0x1740
        L_0x0732:
            java.lang.String r11 = "Silhouette Effect"
            r10.effect_Name = r11
            util.BitmapCache r11 = r10.bitmapCache
            java.lang.String r12 = helper.Constants.SILHOUTE
            android.graphics.Bitmap r12 = r11.getCacheBitmap(r12)
            goto L_0x1740
        L_0x0740:
            util.BitmapCache r11 = r10.bitmapCache
            java.lang.String r0 = helper.Constants.WATER_COLOR
            android.graphics.Bitmap r11 = r11.getCacheBitmap(r0)
            if (r11 != 0) goto L_0x077f
            java.lang.String r11 = "Water Painting Effect"
            r10.effect_Name = r11
            int r11 = r12.getWidth()
            int r0 = r12.getHeight()
            android.graphics.Bitmap r11 = r10.getBitmap(r11, r0)
            org.opencv.core.Mat r0 = r10.original
            org.opencv.android.Utils.bitmapToMat(r12, r0)
            org.opencv.core.Mat r12 = r10.original
            long r0 = r12.getNativeObjAddr()
            helper.Filters.WaterPainting(r0)
            org.opencv.core.Mat r12 = r10.original
            org.opencv.android.Utils.matToBitmap(r12, r11)
            android.graphics.Bitmap$Config r12 = android.graphics.Bitmap.Config.RGB_565
            android.graphics.Bitmap r12 = util.Utils.convert(r11, r12)
            util.BitmapCache r0 = r10.bitmapCache
            java.lang.String r1 = helper.Constants.WATER_COLOR
            r0.addBitmapToCache(r1, r12)
            r10.recycleBitmap(r11)
            goto L_0x1740
        L_0x077f:
            java.lang.String r11 = "Water Painting Effect"
            r10.effect_Name = r11
            util.BitmapCache r11 = r10.bitmapCache
            java.lang.String r12 = helper.Constants.WATER_COLOR
            android.graphics.Bitmap r12 = r11.getCacheBitmap(r12)
            goto L_0x1740
        L_0x078d:
            boolean r11 = r10.original_image
            if (r11 == 0) goto L_0x0811
            android.graphics.Bitmap$Config r11 = r12.getConfig()
            android.graphics.Bitmap r11 = r12.copy(r11, r8)
            android.graphics.Bitmap$Config r0 = android.graphics.Bitmap.Config.RGB_565
            android.graphics.Bitmap r11 = util.Utils.convert(r11, r0)
            org.opencv.core.Mat r0 = r10.original
            org.opencv.android.Utils.bitmapToMat(r11, r0)
            java.lang.String r11 = r10.resolution
            java.lang.String r0 = "hd"
            boolean r11 = r11.equals(r0)
            if (r11 != 0) goto L_0x07ec
            java.lang.String r11 = r10.resolution
            java.lang.String r0 = "hdv"
            boolean r11 = r11.equals(r0)
            if (r11 == 0) goto L_0x07b9
            goto L_0x07ec
        L_0x07b9:
            java.lang.String r11 = r10.resolution
            java.lang.String r0 = "full_hd"
            boolean r11 = r11.equals(r0)
            if (r11 != 0) goto L_0x07e2
            java.lang.String r11 = r10.resolution
            java.lang.String r0 = "twok"
            boolean r11 = r11.equals(r0)
            if (r11 == 0) goto L_0x07ce
            goto L_0x07e2
        L_0x07ce:
            java.lang.String r11 = r10.resolution
            java.lang.String r0 = "fourk"
            boolean r11 = r11.equals(r0)
            if (r11 == 0) goto L_0x07f5
            org.opencv.core.Mat r11 = r10.original
            long r0 = r11.getNativeObjAddr()
            helper.Filters.Drawing4K(r0)
            goto L_0x07f5
        L_0x07e2:
            org.opencv.core.Mat r11 = r10.original
            long r0 = r11.getNativeObjAddr()
            helper.Filters.DrawingHD(r0)
            goto L_0x07f5
        L_0x07ec:
            org.opencv.core.Mat r11 = r10.original
            long r0 = r11.getNativeObjAddr()
            helper.Filters.Drawing(r0)
        L_0x07f5:
            int r11 = r12.getWidth()
            int r12 = r12.getHeight()
            android.graphics.Bitmap r11 = r10.getBitmap(r11, r12)
            org.opencv.core.Mat r12 = r10.original
            org.opencv.android.Utils.matToBitmap(r12, r11)
            android.graphics.Bitmap$Config r12 = android.graphics.Bitmap.Config.RGB_565
            android.graphics.Bitmap r12 = util.Utils.convert(r11, r12)
            r10.recycleBitmap(r11)
            goto L_0x1740
        L_0x0811:
            util.BitmapCache r11 = r10.bitmapCache
            java.lang.String r0 = helper.Constants.COLOR_DRAWING
            android.graphics.Bitmap r11 = r11.getCacheBitmap(r0)
            if (r11 != 0) goto L_0x0864
            java.lang.String r11 = "Drawing Effect"
            r10.effect_Name = r11
            android.graphics.Bitmap$Config r11 = r12.getConfig()
            android.graphics.Bitmap r11 = r12.copy(r11, r8)
            android.graphics.Bitmap$Config r0 = android.graphics.Bitmap.Config.RGB_565
            android.graphics.Bitmap r11 = util.Utils.convert(r11, r0)
            org.opencv.core.Mat r0 = r10.original
            org.opencv.android.Utils.bitmapToMat(r11, r0)
            org.opencv.core.Mat r11 = r10.original
            long r0 = r11.getNativeObjAddr()
            helper.Filters.Drawing(r0)
            int r11 = r12.getWidth()
            int r12 = r12.getHeight()
            android.graphics.Bitmap r11 = r10.getBitmap(r11, r12)
            org.opencv.core.Mat r12 = r10.original
            org.opencv.android.Utils.matToBitmap(r12, r11)
            android.graphics.Bitmap$Config r12 = android.graphics.Bitmap.Config.RGB_565
            android.graphics.Bitmap r12 = util.Utils.convert(r11, r12)
            util.BitmapCache r0 = r10.bitmapCache
            java.lang.String r1 = helper.Constants.COLOR_DRAWING
            android.graphics.Bitmap$Config r2 = android.graphics.Bitmap.Config.RGB_565
            android.graphics.Bitmap r2 = util.Utils.convert(r12, r2)
            r0.addBitmapToCache(r1, r2)
            r10.recycleBitmap(r11)
            goto L_0x1740
        L_0x0864:
            java.lang.String r11 = "Drawing Effect"
            r10.effect_Name = r11
            util.BitmapCache r11 = r10.bitmapCache
            java.lang.String r12 = helper.Constants.COLOR_DRAWING
            android.graphics.Bitmap r12 = r11.getCacheBitmap(r12)
            goto L_0x1740
        L_0x0872:
            boolean r11 = r10.original_image
            if (r11 == 0) goto L_0x08f1
            int r11 = r12.getWidth()
            int r0 = r12.getHeight()
            android.graphics.Bitmap r11 = r10.getBitmap(r11, r0)
            java.lang.String r0 = r10.resolution
            java.lang.String r1 = "hd"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x08c0
            java.lang.String r0 = r10.resolution
            java.lang.String r1 = "hdv"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0897
            goto L_0x08c0
        L_0x0897:
            java.lang.String r0 = r10.resolution
            java.lang.String r1 = "full_hd"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x08bb
            java.lang.String r0 = r10.resolution
            java.lang.String r1 = "twok"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x08ac
            goto L_0x08bb
        L_0x08ac:
            java.lang.String r0 = r10.resolution
            java.lang.String r1 = "fourk"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x08c4
            android.graphics.Bitmap r11 = r10.Sketch(r12, r5)
            goto L_0x08c4
        L_0x08bb:
            android.graphics.Bitmap r11 = r10.Sketch(r12, r6)
            goto L_0x08c4
        L_0x08c0:
            android.graphics.Bitmap r11 = r10.Sketch(r12, r7)
        L_0x08c4:
            org.opencv.core.Mat r0 = r10.original
            org.opencv.android.Utils.bitmapToMat(r11, r0)
            org.opencv.core.Mat r0 = r10.original
            long r0 = r0.getNativeObjAddr()
            helper.Filters.HardStroke(r0)
            int r0 = r12.getWidth()
            int r12 = r12.getHeight()
            android.graphics.Bitmap r12 = r10.getBitmap(r0, r12)
            org.opencv.core.Mat r0 = r10.original
            org.opencv.android.Utils.matToBitmap(r0, r12)
            android.graphics.Bitmap$Config r0 = android.graphics.Bitmap.Config.RGB_565
            android.graphics.Bitmap r0 = util.Utils.convert(r12, r0)
            r10.recycleBitmap(r12)
            r10.recycleBitmap(r11)
            goto L_0x1732
        L_0x08f1:
            util.BitmapCache r11 = r10.bitmapCache
            java.lang.String r0 = helper.Constants.DARK_SHADING
            android.graphics.Bitmap r11 = r11.getCacheBitmap(r0)
            if (r11 != 0) goto L_0x092d
            java.lang.String r11 = "Hard Stroke Effect"
            r10.effect_Name = r11
            android.graphics.Bitmap r11 = r10.Sketch(r12, r7)
            org.opencv.core.Mat r0 = r10.original
            org.opencv.android.Utils.bitmapToMat(r11, r0)
            org.opencv.core.Mat r11 = r10.original
            long r0 = r11.getNativeObjAddr()
            helper.Filters.HardStroke(r0)
            int r11 = r12.getWidth()
            int r12 = r12.getHeight()
            android.graphics.Bitmap r11 = r10.getBitmap(r11, r12)
            org.opencv.core.Mat r12 = r10.original
            org.opencv.android.Utils.matToBitmap(r12, r11)
            android.graphics.Bitmap$Config r12 = android.graphics.Bitmap.Config.RGB_565
            android.graphics.Bitmap r12 = util.Utils.convert(r11, r12)
            r10.recycleBitmap(r11)
            goto L_0x1740
        L_0x092d:
            java.lang.String r11 = "Hard Stroke Effect"
            r10.effect_Name = r11
            util.BitmapCache r11 = r10.bitmapCache
            java.lang.String r12 = helper.Constants.DARK_SHADING
            android.graphics.Bitmap r12 = r11.getCacheBitmap(r12)
            goto L_0x1740
        L_0x093b:
            boolean r0 = r10.original_image
            if (r0 == 0) goto L_0x09c5
            android.content.Context r0 = r10.context
            android.content.res.Resources r0 = r0.getResources()
            android.graphics.Bitmap r11 = android.graphics.BitmapFactory.decodeResource(r0, r11)
            int r0 = r10.hd_image_width
            int r1 = r10.hd_image_height
            android.graphics.Bitmap r11 = android.graphics.Bitmap.createScaledBitmap(r11, r0, r1, r8)
            int r0 = r12.getWidth()
            int r1 = r12.getHeight()
            android.graphics.Bitmap r0 = r10.getBitmap(r0, r1)
            java.lang.String r1 = r10.resolution
            java.lang.String r2 = "hd"
            boolean r1 = r1.equals(r2)
            if (r1 != 0) goto L_0x099b
            java.lang.String r1 = r10.resolution
            java.lang.String r2 = "hdv"
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L_0x0972
            goto L_0x099b
        L_0x0972:
            java.lang.String r1 = r10.resolution
            java.lang.String r2 = "full_hd"
            boolean r1 = r1.equals(r2)
            if (r1 != 0) goto L_0x0996
            java.lang.String r1 = r10.resolution
            java.lang.String r2 = "twok"
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L_0x0987
            goto L_0x0996
        L_0x0987:
            java.lang.String r1 = r10.resolution
            java.lang.String r2 = "fourk"
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L_0x099f
            android.graphics.Bitmap r0 = r10.Sketch(r12, r5)
            goto L_0x099f
        L_0x0996:
            android.graphics.Bitmap r0 = r10.Sketch(r12, r6)
            goto L_0x099f
        L_0x099b:
            android.graphics.Bitmap r0 = r10.Sketch(r12, r7)
        L_0x099f:
            jp.co.cyberagent.android.gpuimage.GPUImage r12 = r10.image
            r12.setImage((android.graphics.Bitmap) r11)
            jp.co.cyberagent.android.gpuimage.GPUImage r12 = r10.image
            android.content.Context r1 = r10.context
            java.lang.Class<jp.co.cyberagent.android.gpuimage.GPUImageMultiplyBlendFilter> r2 = p041jp.p042co.cyberagent.android.gpuimage.GPUImageMultiplyBlendFilter.class
            jp.co.cyberagent.android.gpuimage.GPUImageFilter r1 = util.Utils.createBlendFilter(r1, r2, r0)
            r12.setFilter(r1)
            jp.co.cyberagent.android.gpuimage.GPUImage r12 = r10.image
            android.graphics.Bitmap r12 = r12.getBitmapWithFilterApplied()
            android.graphics.Bitmap$Config r1 = android.graphics.Bitmap.Config.RGB_565
            android.graphics.Bitmap r12 = util.Utils.convert(r12, r1)
            r10.recycleBitmap(r0)
            r11.recycle()
            goto L_0x1740
        L_0x09c5:
            util.BitmapCache r11 = r10.bitmapCache
            java.lang.String r0 = helper.Constants.GRAIN
            android.graphics.Bitmap r11 = r11.getCacheBitmap(r0)
            if (r11 != 0) goto L_0x0a0d
            java.lang.String r11 = "Grain Effect"
            r10.effect_Name = r11
            int r11 = r12.getWidth()
            int r0 = r12.getHeight()
            r10.getBitmap(r11, r0)
            android.graphics.Bitmap r11 = r10.Sketch(r12, r7)
            android.graphics.Bitmap r12 = r10.getGrainTexture(r12)
            jp.co.cyberagent.android.gpuimage.GPUImage r0 = r10.image
            r0.setImage((android.graphics.Bitmap) r12)
            jp.co.cyberagent.android.gpuimage.GPUImage r0 = r10.image
            android.content.Context r1 = r10.context
            java.lang.Class<jp.co.cyberagent.android.gpuimage.GPUImageMultiplyBlendFilter> r2 = p041jp.p042co.cyberagent.android.gpuimage.GPUImageMultiplyBlendFilter.class
            jp.co.cyberagent.android.gpuimage.GPUImageFilter r1 = util.Utils.createBlendFilter(r1, r2, r11)
            r0.setFilter(r1)
            jp.co.cyberagent.android.gpuimage.GPUImage r0 = r10.image
            android.graphics.Bitmap r0 = r0.getBitmapWithFilterApplied()
            util.BitmapCache r1 = r10.bitmapCache
            java.lang.String r2 = helper.Constants.GRAIN
            r1.addBitmapToCache(r2, r0)
            r10.recycleBitmap(r11)
            r10.recycleBitmap(r12)
            goto L_0x1732
        L_0x0a0d:
            java.lang.String r11 = "Grain Effect"
            r10.effect_Name = r11
            util.BitmapCache r11 = r10.bitmapCache
            java.lang.String r12 = helper.Constants.GRAIN
            android.graphics.Bitmap r11 = r11.getCacheBitmap(r12)
            android.graphics.Bitmap$Config r12 = android.graphics.Bitmap.Config.RGB_565
            android.graphics.Bitmap r12 = util.Utils.convert(r11, r12)
            goto L_0x1740
        L_0x0a21:
            java.lang.String r11 = "Light Sketch Effect"
            r10.effect_Name = r11
            boolean r11 = r10.original_image
            if (r11 == 0) goto L_0x0a6f
            java.lang.String r11 = r10.resolution
            java.lang.String r0 = "hd"
            boolean r11 = r11.equals(r0)
            if (r11 != 0) goto L_0x0a69
            java.lang.String r11 = r10.resolution
            java.lang.String r0 = "hdv"
            boolean r11 = r11.equals(r0)
            if (r11 == 0) goto L_0x0a3e
            goto L_0x0a69
        L_0x0a3e:
            java.lang.String r11 = r10.resolution
            java.lang.String r0 = "full_hd"
            boolean r11 = r11.equals(r0)
            if (r11 != 0) goto L_0x0a63
            java.lang.String r11 = r10.resolution
            java.lang.String r0 = "twok"
            boolean r11 = r11.equals(r0)
            if (r11 == 0) goto L_0x0a53
            goto L_0x0a63
        L_0x0a53:
            java.lang.String r11 = r10.resolution
            java.lang.String r0 = "fourk"
            boolean r11 = r11.equals(r0)
            if (r11 == 0) goto L_0x1740
            android.graphics.Bitmap r12 = r10.Sketch(r12, r5)
            goto L_0x1740
        L_0x0a63:
            android.graphics.Bitmap r12 = r10.Sketch(r12, r6)
            goto L_0x1740
        L_0x0a69:
            android.graphics.Bitmap r12 = r10.Sketch(r12, r7)
            goto L_0x1740
        L_0x0a6f:
            android.graphics.Bitmap r11 = r10.Sketch(r12, r7)
            android.graphics.Bitmap$Config r12 = android.graphics.Bitmap.Config.RGB_565
            android.graphics.Bitmap r12 = util.Utils.convert(r11, r12)
            goto L_0x1740
        L_0x0a7b:
            boolean r11 = r10.original_image
            if (r11 == 0) goto L_0x0aff
            android.graphics.Bitmap$Config r11 = r12.getConfig()
            android.graphics.Bitmap r11 = r12.copy(r11, r8)
            android.graphics.Bitmap$Config r0 = android.graphics.Bitmap.Config.RGB_565
            android.graphics.Bitmap r11 = util.Utils.convert(r11, r0)
            org.opencv.core.Mat r0 = r10.original
            org.opencv.android.Utils.bitmapToMat(r11, r0)
            java.lang.String r11 = r10.resolution
            java.lang.String r0 = "hd"
            boolean r11 = r11.equals(r0)
            if (r11 != 0) goto L_0x0ada
            java.lang.String r11 = r10.resolution
            java.lang.String r0 = "hdv"
            boolean r11 = r11.equals(r0)
            if (r11 == 0) goto L_0x0aa7
            goto L_0x0ada
        L_0x0aa7:
            java.lang.String r11 = r10.resolution
            java.lang.String r0 = "full_hd"
            boolean r11 = r11.equals(r0)
            if (r11 != 0) goto L_0x0ad0
            java.lang.String r11 = r10.resolution
            java.lang.String r0 = "twok"
            boolean r11 = r11.equals(r0)
            if (r11 == 0) goto L_0x0abc
            goto L_0x0ad0
        L_0x0abc:
            java.lang.String r11 = r10.resolution
            java.lang.String r0 = "fourk"
            boolean r11 = r11.equals(r0)
            if (r11 == 0) goto L_0x0ae3
            org.opencv.core.Mat r11 = r10.original
            long r0 = r11.getNativeObjAddr()
            helper.Filters.ColorSketch4K(r0)
            goto L_0x0ae3
        L_0x0ad0:
            org.opencv.core.Mat r11 = r10.original
            long r0 = r11.getNativeObjAddr()
            helper.Filters.ColorSketchHD(r0)
            goto L_0x0ae3
        L_0x0ada:
            org.opencv.core.Mat r11 = r10.original
            long r0 = r11.getNativeObjAddr()
            helper.Filters.ColorSketch(r0)
        L_0x0ae3:
            int r11 = r12.getWidth()
            int r12 = r12.getHeight()
            android.graphics.Bitmap r11 = r10.getBitmap(r11, r12)
            org.opencv.core.Mat r12 = r10.original
            org.opencv.android.Utils.matToBitmap(r12, r11)
            android.graphics.Bitmap$Config r12 = android.graphics.Bitmap.Config.RGB_565
            android.graphics.Bitmap r12 = util.Utils.convert(r11, r12)
            r10.recycleBitmap(r11)
            goto L_0x1740
        L_0x0aff:
            util.BitmapCache r11 = r10.bitmapCache
            java.lang.String r0 = helper.Constants.COLOR_SKETCH
            android.graphics.Bitmap r11 = r11.getCacheBitmap(r0)
            if (r11 != 0) goto L_0x0b46
            java.lang.String r11 = "Sketch Drawing Effect"
            r10.effect_Name = r11
            android.graphics.Bitmap$Config r11 = r12.getConfig()
            android.graphics.Bitmap r11 = r12.copy(r11, r8)
            org.opencv.core.Mat r0 = r10.original
            org.opencv.android.Utils.bitmapToMat(r11, r0)
            org.opencv.core.Mat r11 = r10.original
            long r0 = r11.getNativeObjAddr()
            helper.Filters.ColorSketch(r0)
            int r11 = r12.getWidth()
            int r12 = r12.getHeight()
            android.graphics.Bitmap r11 = r10.getBitmap(r11, r12)
            org.opencv.core.Mat r12 = r10.original
            org.opencv.android.Utils.matToBitmap(r12, r11)
            android.graphics.Bitmap$Config r12 = android.graphics.Bitmap.Config.RGB_565
            android.graphics.Bitmap r12 = util.Utils.convert(r11, r12)
            util.BitmapCache r0 = r10.bitmapCache
            java.lang.String r1 = helper.Constants.COLOR_SKETCH
            r0.addBitmapToCache(r1, r12)
            r10.recycleBitmap(r11)
            goto L_0x1740
        L_0x0b46:
            java.lang.String r11 = "Sketch Drawing Effect"
            r10.effect_Name = r11
            util.BitmapCache r11 = r10.bitmapCache
            java.lang.String r12 = helper.Constants.COLOR_SKETCH
            android.graphics.Bitmap r12 = r11.getCacheBitmap(r12)
            goto L_0x1740
        L_0x0b54:
            boolean r11 = r10.original_image
            if (r11 == 0) goto L_0x0bd9
            android.graphics.Bitmap$Config r11 = r12.getConfig()
            android.graphics.Bitmap r11 = r12.copy(r11, r8)
            android.graphics.Bitmap$Config r0 = android.graphics.Bitmap.Config.RGB_565
            android.graphics.Bitmap r11 = util.Utils.convert(r11, r0)
            org.opencv.core.Mat r0 = r10.original
            org.opencv.android.Utils.bitmapToMat(r11, r0)
            java.lang.String r11 = r10.resolution
            java.lang.String r0 = "hd"
            boolean r11 = r11.equals(r0)
            if (r11 != 0) goto L_0x0bb3
            java.lang.String r11 = r10.resolution
            java.lang.String r0 = "hdv"
            boolean r11 = r11.equals(r0)
            if (r11 == 0) goto L_0x0b80
            goto L_0x0bb3
        L_0x0b80:
            java.lang.String r11 = r10.resolution
            java.lang.String r0 = "full_hd"
            boolean r11 = r11.equals(r0)
            if (r11 != 0) goto L_0x0ba9
            java.lang.String r11 = r10.resolution
            java.lang.String r0 = "twok"
            boolean r11 = r11.equals(r0)
            if (r11 == 0) goto L_0x0b95
            goto L_0x0ba9
        L_0x0b95:
            java.lang.String r11 = r10.resolution
            java.lang.String r0 = "fourk"
            boolean r11 = r11.equals(r0)
            if (r11 == 0) goto L_0x0bbc
            org.opencv.core.Mat r11 = r10.original
            long r0 = r11.getNativeObjAddr()
            helper.Filters.DarkPencilSketch4K(r0)
            goto L_0x0bbc
        L_0x0ba9:
            org.opencv.core.Mat r11 = r10.original
            long r0 = r11.getNativeObjAddr()
            helper.Filters.DarkPencilSketchHD(r0)
            goto L_0x0bbc
        L_0x0bb3:
            org.opencv.core.Mat r11 = r10.original
            long r0 = r11.getNativeObjAddr()
            helper.Filters.DarkPencilSketch(r0)
        L_0x0bbc:
            android.graphics.Bitmap$Config r11 = r12.getConfig()
            android.graphics.Bitmap r11 = r12.copy(r11, r8)
            org.opencv.core.Mat r12 = r10.original
            org.opencv.android.Utils.matToBitmap(r12, r11)
            org.opencv.core.Mat r12 = r10.original
            r12.release()
            android.graphics.Bitmap$Config r12 = android.graphics.Bitmap.Config.RGB_565
            android.graphics.Bitmap r12 = util.Utils.convert(r11, r12)
            r10.recycleBitmap(r11)
            goto L_0x1740
        L_0x0bd9:
            util.BitmapCache r11 = r10.bitmapCache
            java.lang.String r0 = helper.Constants.HARD_STROKE_SKETCH
            android.graphics.Bitmap r11 = r11.getCacheBitmap(r0)
            if (r11 != 0) goto L_0x0c21
            java.lang.String r11 = "Dark Pencil Sketch"
            r10.effect_Name = r11
            android.graphics.Bitmap$Config r11 = r12.getConfig()
            android.graphics.Bitmap r11 = r12.copy(r11, r8)
            org.opencv.core.Mat r0 = r10.original
            org.opencv.android.Utils.bitmapToMat(r11, r0)
            org.opencv.core.Mat r11 = r10.original
            long r0 = r11.getNativeObjAddr()
            helper.Filters.DarkPencilSketch(r0)
            android.graphics.Bitmap$Config r11 = r12.getConfig()
            android.graphics.Bitmap r11 = r12.copy(r11, r8)
            org.opencv.core.Mat r12 = r10.original
            org.opencv.android.Utils.matToBitmap(r12, r11)
            org.opencv.core.Mat r12 = r10.original
            r12.release()
            android.graphics.Bitmap$Config r12 = android.graphics.Bitmap.Config.RGB_565
            android.graphics.Bitmap r12 = util.Utils.convert(r11, r12)
            util.BitmapCache r0 = r10.bitmapCache
            java.lang.String r1 = helper.Constants.HARD_STROKE_SKETCH
            r0.addBitmapToCache(r1, r12)
            r10.recycleBitmap(r11)
            goto L_0x1740
        L_0x0c21:
            java.lang.String r11 = "Dark Pencil Sketch"
            r10.effect_Name = r11
            util.BitmapCache r11 = r10.bitmapCache
            java.lang.String r12 = helper.Constants.HARD_STROKE_SKETCH
            android.graphics.Bitmap r12 = r11.getCacheBitmap(r12)
            goto L_0x1740
        L_0x0c2f:
            boolean r11 = r10.original_image
            if (r11 == 0) goto L_0x0cca
            android.graphics.Bitmap$Config r11 = r12.getConfig()
            android.graphics.Bitmap r11 = r12.copy(r11, r8)
            android.graphics.Bitmap$Config r0 = android.graphics.Bitmap.Config.ARGB_8888
            android.graphics.Bitmap r11 = util.Utils.convert(r11, r0)
            java.lang.String r0 = "#unpack @style sketch 1.0"
            android.graphics.Bitmap r11 = org.wysaid.nativePort.CGENativeLibrary.filterImage_MultipleEffects(r11, r0, r1)
            org.opencv.core.Mat r0 = new org.opencv.core.Mat
            r0.<init>()
            org.opencv.core.Mat r1 = new org.opencv.core.Mat
            r1.<init>()
            org.opencv.android.Utils.bitmapToMat(r11, r1)
            android.graphics.Bitmap$Config r2 = r12.getConfig()
            android.graphics.Bitmap r12 = r12.copy(r2, r8)
            org.opencv.android.Utils.bitmapToMat(r12, r0)
            java.lang.String r12 = r10.resolution
            java.lang.String r2 = "hd"
            boolean r12 = r12.equals(r2)
            if (r12 != 0) goto L_0x0cab
            java.lang.String r12 = r10.resolution
            java.lang.String r2 = "hdv"
            boolean r12 = r12.equals(r2)
            if (r12 == 0) goto L_0x0c74
            goto L_0x0cab
        L_0x0c74:
            java.lang.String r12 = r10.resolution
            java.lang.String r2 = "full_hd"
            boolean r12 = r12.equals(r2)
            if (r12 != 0) goto L_0x0c9f
            java.lang.String r12 = r10.resolution
            java.lang.String r2 = "twok"
            boolean r12 = r12.equals(r2)
            if (r12 == 0) goto L_0x0c89
            goto L_0x0c9f
        L_0x0c89:
            java.lang.String r12 = r10.resolution
            java.lang.String r2 = "fourk"
            boolean r12 = r12.equals(r2)
            if (r12 == 0) goto L_0x0cb6
            long r2 = r0.getNativeObjAddr()
            long r4 = r1.getNativeObjAddr()
            helper.Filters.PencilDarkStrokes4K(r2, r4)
            goto L_0x0cb6
        L_0x0c9f:
            long r2 = r0.getNativeObjAddr()
            long r4 = r1.getNativeObjAddr()
            helper.Filters.PencilDarkStrokesHD(r2, r4)
            goto L_0x0cb6
        L_0x0cab:
            long r2 = r0.getNativeObjAddr()
            long r4 = r1.getNativeObjAddr()
            helper.Filters.PencilDarkStrokes(r2, r4)
        L_0x0cb6:
            org.opencv.android.Utils.matToBitmap(r1, r11)
            r0.release()
            r1.release()
            android.graphics.Bitmap$Config r12 = android.graphics.Bitmap.Config.RGB_565
            android.graphics.Bitmap r12 = util.Utils.convert(r11, r12)
            r10.recycleBitmap(r11)
            goto L_0x1740
        L_0x0cca:
            util.BitmapCache r11 = r10.bitmapCache
            java.lang.String r0 = helper.Constants.DARK_SHADE_SKETCH
            android.graphics.Bitmap r11 = r11.getCacheBitmap(r0)
            if (r11 != 0) goto L_0x0d24
            java.lang.String r11 = "Pencil Dark Strokes"
            r10.effect_Name = r11
            android.graphics.Bitmap$Config r11 = r12.getConfig()
            android.graphics.Bitmap r11 = r12.copy(r11, r8)
            android.graphics.Bitmap$Config r0 = android.graphics.Bitmap.Config.ARGB_8888
            android.graphics.Bitmap r11 = util.Utils.convert(r11, r0)
            java.lang.String r0 = "#unpack @style sketch 1.0"
            android.graphics.Bitmap r11 = org.wysaid.nativePort.CGENativeLibrary.filterImage_MultipleEffects(r11, r0, r1)
            org.opencv.core.Mat r0 = new org.opencv.core.Mat
            r0.<init>()
            org.opencv.core.Mat r1 = new org.opencv.core.Mat
            r1.<init>()
            org.opencv.android.Utils.bitmapToMat(r11, r1)
            android.graphics.Bitmap$Config r2 = r12.getConfig()
            android.graphics.Bitmap r12 = r12.copy(r2, r8)
            org.opencv.android.Utils.bitmapToMat(r12, r0)
            long r2 = r0.getNativeObjAddr()
            long r4 = r1.getNativeObjAddr()
            helper.Filters.PencilDarkStrokes(r2, r4)
            org.opencv.android.Utils.matToBitmap(r1, r11)
            android.graphics.Bitmap$Config r12 = android.graphics.Bitmap.Config.RGB_565
            android.graphics.Bitmap r12 = util.Utils.convert(r11, r12)
            util.BitmapCache r0 = r10.bitmapCache
            java.lang.String r1 = helper.Constants.DARK_SHADE_SKETCH
            r0.addBitmapToCache(r1, r12)
            r10.recycleBitmap(r11)
            goto L_0x1740
        L_0x0d24:
            java.lang.String r11 = "Pencil Dark Strokes"
            r10.effect_Name = r11
            util.BitmapCache r11 = r10.bitmapCache
            java.lang.String r12 = helper.Constants.DARK_SHADE_SKETCH
            android.graphics.Bitmap r12 = r11.getCacheBitmap(r12)
            goto L_0x1740
        L_0x0d32:
            boolean r11 = r10.original_image
            if (r11 == 0) goto L_0x0dcd
            android.graphics.Bitmap$Config r11 = r12.getConfig()
            android.graphics.Bitmap r11 = r12.copy(r11, r8)
            android.graphics.Bitmap$Config r0 = android.graphics.Bitmap.Config.ARGB_8888
            android.graphics.Bitmap r11 = util.Utils.convert(r11, r0)
            java.lang.String r0 = "#unpack @style sketch 1.0"
            android.graphics.Bitmap r11 = org.wysaid.nativePort.CGENativeLibrary.filterImage_MultipleEffects(r11, r0, r1)
            org.opencv.core.Mat r0 = new org.opencv.core.Mat
            r0.<init>()
            org.opencv.core.Mat r1 = new org.opencv.core.Mat
            r1.<init>()
            org.opencv.android.Utils.bitmapToMat(r11, r1)
            android.graphics.Bitmap$Config r2 = r12.getConfig()
            android.graphics.Bitmap r12 = r12.copy(r2, r8)
            org.opencv.android.Utils.bitmapToMat(r12, r0)
            java.lang.String r12 = r10.resolution
            java.lang.String r2 = "hd"
            boolean r12 = r12.equals(r2)
            if (r12 != 0) goto L_0x0dae
            java.lang.String r12 = r10.resolution
            java.lang.String r2 = "hdv"
            boolean r12 = r12.equals(r2)
            if (r12 == 0) goto L_0x0d77
            goto L_0x0dae
        L_0x0d77:
            java.lang.String r12 = r10.resolution
            java.lang.String r2 = "full_hd"
            boolean r12 = r12.equals(r2)
            if (r12 != 0) goto L_0x0da2
            java.lang.String r12 = r10.resolution
            java.lang.String r2 = "twok"
            boolean r12 = r12.equals(r2)
            if (r12 == 0) goto L_0x0d8c
            goto L_0x0da2
        L_0x0d8c:
            java.lang.String r12 = r10.resolution
            java.lang.String r2 = "fourk"
            boolean r12 = r12.equals(r2)
            if (r12 == 0) goto L_0x0db9
            long r2 = r0.getNativeObjAddr()
            long r4 = r1.getNativeObjAddr()
            helper.Filters.PencilLightStrokesHD(r2, r4)
            goto L_0x0db9
        L_0x0da2:
            long r2 = r0.getNativeObjAddr()
            long r4 = r1.getNativeObjAddr()
            helper.Filters.PencilLightStrokes(r2, r4)
            goto L_0x0db9
        L_0x0dae:
            long r2 = r0.getNativeObjAddr()
            long r4 = r1.getNativeObjAddr()
            helper.Filters.PencilLightStrokes(r2, r4)
        L_0x0db9:
            org.opencv.android.Utils.matToBitmap(r1, r11)
            r0.release()
            r1.release()
            android.graphics.Bitmap$Config r12 = android.graphics.Bitmap.Config.RGB_565
            android.graphics.Bitmap r12 = util.Utils.convert(r11, r12)
            r10.recycleBitmap(r11)
            goto L_0x1740
        L_0x0dcd:
            util.BitmapCache r11 = r10.bitmapCache
            java.lang.String r0 = helper.Constants.SHADE_SKETCH
            android.graphics.Bitmap r11 = r11.getCacheBitmap(r0)
            if (r11 != 0) goto L_0x0e27
            java.lang.String r11 = "Pencil Light Sketch "
            r10.effect_Name = r11
            android.graphics.Bitmap$Config r11 = r12.getConfig()
            android.graphics.Bitmap r11 = r12.copy(r11, r8)
            android.graphics.Bitmap$Config r0 = android.graphics.Bitmap.Config.ARGB_8888
            android.graphics.Bitmap r11 = util.Utils.convert(r11, r0)
            java.lang.String r0 = "#unpack @style sketch 1.0"
            android.graphics.Bitmap r11 = org.wysaid.nativePort.CGENativeLibrary.filterImage_MultipleEffects(r11, r0, r1)
            org.opencv.core.Mat r0 = new org.opencv.core.Mat
            r0.<init>()
            org.opencv.core.Mat r1 = new org.opencv.core.Mat
            r1.<init>()
            org.opencv.android.Utils.bitmapToMat(r11, r1)
            android.graphics.Bitmap$Config r2 = r12.getConfig()
            android.graphics.Bitmap r12 = r12.copy(r2, r8)
            org.opencv.android.Utils.bitmapToMat(r12, r0)
            long r2 = r0.getNativeObjAddr()
            long r4 = r1.getNativeObjAddr()
            helper.Filters.PencilLightStrokes(r2, r4)
            org.opencv.android.Utils.matToBitmap(r1, r11)
            android.graphics.Bitmap$Config r12 = android.graphics.Bitmap.Config.RGB_565
            android.graphics.Bitmap r12 = util.Utils.convert(r11, r12)
            util.BitmapCache r0 = r10.bitmapCache
            java.lang.String r1 = helper.Constants.SHADE_SKETCH
            r0.addBitmapToCache(r1, r12)
            r10.recycleBitmap(r11)
            goto L_0x1740
        L_0x0e27:
            java.lang.String r11 = "Pencil Light Sketch "
            r10.effect_Name = r11
            util.BitmapCache r11 = r10.bitmapCache
            java.lang.String r12 = helper.Constants.SHADE_SKETCH
            android.graphics.Bitmap r12 = r11.getCacheBitmap(r12)
            goto L_0x1740
        L_0x0e35:
            boolean r11 = r10.original_image
            if (r11 == 0) goto L_0x0eb9
            android.graphics.Bitmap$Config r11 = r12.getConfig()
            android.graphics.Bitmap r11 = r12.copy(r11, r8)
            android.graphics.Bitmap$Config r0 = android.graphics.Bitmap.Config.RGB_565
            android.graphics.Bitmap r11 = util.Utils.convert(r11, r0)
            org.opencv.core.Mat r0 = r10.original
            org.opencv.android.Utils.bitmapToMat(r11, r0)
            java.lang.String r11 = r10.resolution
            java.lang.String r0 = "hd"
            boolean r11 = r11.equals(r0)
            if (r11 != 0) goto L_0x0e94
            java.lang.String r11 = r10.resolution
            java.lang.String r0 = "hdv"
            boolean r11 = r11.equals(r0)
            if (r11 == 0) goto L_0x0e61
            goto L_0x0e94
        L_0x0e61:
            java.lang.String r11 = r10.resolution
            java.lang.String r0 = "full_hd"
            boolean r11 = r11.equals(r0)
            if (r11 != 0) goto L_0x0e8a
            java.lang.String r11 = r10.resolution
            java.lang.String r0 = "twok"
            boolean r11 = r11.equals(r0)
            if (r11 == 0) goto L_0x0e76
            goto L_0x0e8a
        L_0x0e76:
            java.lang.String r11 = r10.resolution
            java.lang.String r0 = "fourk"
            boolean r11 = r11.equals(r0)
            if (r11 == 0) goto L_0x0e9d
            org.opencv.core.Mat r11 = r10.original
            long r0 = r11.getNativeObjAddr()
            helper.Filters.PencilSketch4K(r0)
            goto L_0x0e9d
        L_0x0e8a:
            org.opencv.core.Mat r11 = r10.original
            long r0 = r11.getNativeObjAddr()
            helper.Filters.PencilSketchHD(r0)
            goto L_0x0e9d
        L_0x0e94:
            org.opencv.core.Mat r11 = r10.original
            long r0 = r11.getNativeObjAddr()
            helper.Filters.PencilSketch(r0)
        L_0x0e9d:
            int r11 = r12.getWidth()
            int r12 = r12.getHeight()
            android.graphics.Bitmap r11 = r10.getBitmap(r11, r12)
            org.opencv.core.Mat r12 = r10.original
            org.opencv.android.Utils.matToBitmap(r12, r11)
            android.graphics.Bitmap$Config r12 = android.graphics.Bitmap.Config.RGB_565
            android.graphics.Bitmap r12 = util.Utils.convert(r11, r12)
            r10.recycleBitmap(r11)
            goto L_0x1740
        L_0x0eb9:
            util.BitmapCache r11 = r10.bitmapCache
            java.lang.String r0 = helper.Constants.SKETCH_EFFECT
            android.graphics.Bitmap r11 = r11.getCacheBitmap(r0)
            if (r11 != 0) goto L_0x0f06
            java.lang.String r11 = "Pencil Sketch Effect"
            r10.effect_Name = r11
            android.graphics.Bitmap$Config r11 = r12.getConfig()
            android.graphics.Bitmap r11 = r12.copy(r11, r8)
            android.graphics.Bitmap$Config r0 = android.graphics.Bitmap.Config.RGB_565
            android.graphics.Bitmap r11 = util.Utils.convert(r11, r0)
            org.opencv.core.Mat r0 = r10.original
            org.opencv.android.Utils.bitmapToMat(r11, r0)
            org.opencv.core.Mat r11 = r10.original
            long r0 = r11.getNativeObjAddr()
            helper.Filters.PencilSketch(r0)
            int r11 = r12.getWidth()
            int r12 = r12.getHeight()
            android.graphics.Bitmap r11 = r10.getBitmap(r11, r12)
            org.opencv.core.Mat r12 = r10.original
            org.opencv.android.Utils.matToBitmap(r12, r11)
            android.graphics.Bitmap$Config r12 = android.graphics.Bitmap.Config.RGB_565
            android.graphics.Bitmap r12 = util.Utils.convert(r11, r12)
            util.BitmapCache r0 = r10.bitmapCache
            java.lang.String r1 = helper.Constants.SKETCH_EFFECT
            r0.addBitmapToCache(r1, r12)
            r10.recycleBitmap(r11)
            goto L_0x1740
        L_0x0f06:
            java.lang.String r11 = "Pencil Sketch Effect"
            r10.effect_Name = r11
            util.BitmapCache r11 = r10.bitmapCache
            java.lang.String r12 = helper.Constants.SKETCH_EFFECT
            android.graphics.Bitmap r12 = r11.getCacheBitmap(r12)
            goto L_0x1740
        L_0x0f14:
            boolean r11 = r10.original_image
            if (r11 == 0) goto L_0x0f8f
            org.opencv.core.Mat r11 = r10.original
            org.opencv.android.Utils.bitmapToMat(r12, r11)
            java.lang.String r11 = r10.resolution
            java.lang.String r0 = "hd"
            boolean r11 = r11.equals(r0)
            if (r11 != 0) goto L_0x0f65
            java.lang.String r11 = r10.resolution
            java.lang.String r0 = "hdv"
            boolean r11 = r11.equals(r0)
            if (r11 == 0) goto L_0x0f32
            goto L_0x0f65
        L_0x0f32:
            java.lang.String r11 = r10.resolution
            java.lang.String r0 = "full_hd"
            boolean r11 = r11.equals(r0)
            if (r11 != 0) goto L_0x0f5b
            java.lang.String r11 = r10.resolution
            java.lang.String r0 = "twok"
            boolean r11 = r11.equals(r0)
            if (r11 == 0) goto L_0x0f47
            goto L_0x0f5b
        L_0x0f47:
            java.lang.String r11 = r10.resolution
            java.lang.String r0 = "fourk"
            boolean r11 = r11.equals(r0)
            if (r11 == 0) goto L_0x0f6e
            org.opencv.core.Mat r11 = r10.original
            long r0 = r11.getNativeObjAddr()
            helper.Filters.PencilDarkShade(r0)
            goto L_0x0f6e
        L_0x0f5b:
            org.opencv.core.Mat r11 = r10.original
            long r0 = r11.getNativeObjAddr()
            helper.Filters.PencilDarkShade(r0)
            goto L_0x0f6e
        L_0x0f65:
            org.opencv.core.Mat r11 = r10.original
            long r0 = r11.getNativeObjAddr()
            helper.Filters.PencilDarkShade(r0)
        L_0x0f6e:
            int r11 = r12.getWidth()
            int r12 = r12.getHeight()
            android.graphics.Bitmap r11 = r10.getBitmap(r11, r12)
            org.opencv.core.Mat r12 = r10.original
            org.opencv.android.Utils.matToBitmap(r12, r11)
            org.opencv.core.Mat r12 = r10.original
            r12.release()
            android.graphics.Bitmap$Config r12 = android.graphics.Bitmap.Config.RGB_565
            android.graphics.Bitmap r12 = util.Utils.convert(r11, r12)
            r10.recycleBitmap(r11)
            goto L_0x1740
        L_0x0f8f:
            util.BitmapCache r11 = r10.bitmapCache
            java.lang.String r0 = helper.Constants.PENCILDARK_SHADE
            android.graphics.Bitmap r11 = r11.getCacheBitmap(r0)
            if (r11 != 0) goto L_0x0fe1
            java.lang.String r11 = "Pencil Dark Shade"
            r10.effect_Name = r11
            android.graphics.Bitmap$Config r11 = r12.getConfig()
            android.graphics.Bitmap r11 = r12.copy(r11, r8)
            android.graphics.Bitmap$Config r0 = android.graphics.Bitmap.Config.RGB_565
            android.graphics.Bitmap r11 = util.Utils.convert(r11, r0)
            org.opencv.core.Mat r0 = r10.original
            org.opencv.android.Utils.bitmapToMat(r11, r0)
            org.opencv.core.Mat r11 = r10.original
            long r0 = r11.getNativeObjAddr()
            helper.Filters.PencilDarkShade(r0)
            int r11 = r12.getWidth()
            int r12 = r12.getHeight()
            android.graphics.Bitmap r11 = r10.getBitmap(r11, r12)
            org.opencv.core.Mat r12 = r10.original
            org.opencv.android.Utils.matToBitmap(r12, r11)
            org.opencv.core.Mat r12 = r10.original
            r12.release()
            android.graphics.Bitmap$Config r12 = android.graphics.Bitmap.Config.RGB_565
            android.graphics.Bitmap r12 = util.Utils.convert(r11, r12)
            util.BitmapCache r0 = r10.bitmapCache
            java.lang.String r1 = helper.Constants.PENCILDARK_SHADE
            r0.addBitmapToCache(r1, r12)
            r10.recycleBitmap(r11)
            goto L_0x1740
        L_0x0fe1:
            java.lang.String r11 = "Pencil Dark Shade"
            r10.effect_Name = r11
            util.BitmapCache r11 = r10.bitmapCache
            java.lang.String r12 = helper.Constants.PENCILDARK_SHADE
            android.graphics.Bitmap r12 = r11.getCacheBitmap(r12)
            goto L_0x1740
        L_0x0fef:
            boolean r11 = r10.original_image
            if (r11 == 0) goto L_0x106a
            org.opencv.core.Mat r11 = r10.original
            org.opencv.android.Utils.bitmapToMat(r12, r11)
            java.lang.String r11 = r10.resolution
            java.lang.String r0 = "hd"
            boolean r11 = r11.equals(r0)
            if (r11 != 0) goto L_0x1040
            java.lang.String r11 = r10.resolution
            java.lang.String r0 = "hdv"
            boolean r11 = r11.equals(r0)
            if (r11 == 0) goto L_0x100d
            goto L_0x1040
        L_0x100d:
            java.lang.String r11 = r10.resolution
            java.lang.String r0 = "full_hd"
            boolean r11 = r11.equals(r0)
            if (r11 != 0) goto L_0x1036
            java.lang.String r11 = r10.resolution
            java.lang.String r0 = "twok"
            boolean r11 = r11.equals(r0)
            if (r11 == 0) goto L_0x1022
            goto L_0x1036
        L_0x1022:
            java.lang.String r11 = r10.resolution
            java.lang.String r0 = "fourk"
            boolean r11 = r11.equals(r0)
            if (r11 == 0) goto L_0x1049
            org.opencv.core.Mat r11 = r10.original
            long r0 = r11.getNativeObjAddr()
            helper.Filters.LightSketch4K(r0)
            goto L_0x1049
        L_0x1036:
            org.opencv.core.Mat r11 = r10.original
            long r0 = r11.getNativeObjAddr()
            helper.Filters.LightSketchHD(r0)
            goto L_0x1049
        L_0x1040:
            org.opencv.core.Mat r11 = r10.original
            long r0 = r11.getNativeObjAddr()
            helper.Filters.LightSketch(r0)
        L_0x1049:
            int r11 = r12.getWidth()
            int r12 = r12.getHeight()
            android.graphics.Bitmap r11 = r10.getBitmap(r11, r12)
            org.opencv.core.Mat r12 = r10.original
            org.opencv.android.Utils.matToBitmap(r12, r11)
            org.opencv.core.Mat r12 = r10.original
            r12.release()
            android.graphics.Bitmap$Config r12 = android.graphics.Bitmap.Config.RGB_565
            android.graphics.Bitmap r12 = util.Utils.convert(r11, r12)
            r10.recycleBitmap(r11)
            goto L_0x1740
        L_0x106a:
            util.BitmapCache r11 = r10.bitmapCache
            java.lang.String r0 = helper.Constants.LIGHT_SKETCH
            android.graphics.Bitmap r11 = r11.getCacheBitmap(r0)
            if (r11 != 0) goto L_0x10bc
            java.lang.String r11 = "Pencil Light Shade"
            r10.effect_Name = r11
            android.graphics.Bitmap$Config r11 = r12.getConfig()
            android.graphics.Bitmap r11 = r12.copy(r11, r8)
            android.graphics.Bitmap$Config r0 = android.graphics.Bitmap.Config.RGB_565
            android.graphics.Bitmap r11 = util.Utils.convert(r11, r0)
            org.opencv.core.Mat r0 = r10.original
            org.opencv.android.Utils.bitmapToMat(r11, r0)
            org.opencv.core.Mat r11 = r10.original
            long r0 = r11.getNativeObjAddr()
            helper.Filters.LightSketch(r0)
            int r11 = r12.getWidth()
            int r12 = r12.getHeight()
            android.graphics.Bitmap r11 = r10.getBitmap(r11, r12)
            org.opencv.core.Mat r12 = r10.original
            org.opencv.android.Utils.matToBitmap(r12, r11)
            org.opencv.core.Mat r12 = r10.original
            r12.release()
            android.graphics.Bitmap$Config r12 = android.graphics.Bitmap.Config.RGB_565
            android.graphics.Bitmap r12 = util.Utils.convert(r11, r12)
            util.BitmapCache r0 = r10.bitmapCache
            java.lang.String r1 = helper.Constants.LIGHT_SKETCH
            r0.addBitmapToCache(r1, r12)
            r10.recycleBitmap(r11)
            goto L_0x1740
        L_0x10bc:
            java.lang.String r11 = "Pencil Light Shade"
            r10.effect_Name = r11
            util.BitmapCache r11 = r10.bitmapCache
            java.lang.String r12 = helper.Constants.LIGHT_SKETCH
            android.graphics.Bitmap r12 = r11.getCacheBitmap(r12)
            goto L_0x1740
        L_0x10ca:
            boolean r11 = r10.original_image
            if (r11 == 0) goto L_0x1145
            org.opencv.core.Mat r11 = r10.original
            org.opencv.android.Utils.bitmapToMat(r12, r11)
            java.lang.String r11 = r10.resolution
            java.lang.String r0 = "hd"
            boolean r11 = r11.equals(r0)
            if (r11 != 0) goto L_0x111b
            java.lang.String r11 = r10.resolution
            java.lang.String r0 = "hdv"
            boolean r11 = r11.equals(r0)
            if (r11 == 0) goto L_0x10e8
            goto L_0x111b
        L_0x10e8:
            java.lang.String r11 = r10.resolution
            java.lang.String r0 = "full_hd"
            boolean r11 = r11.equals(r0)
            if (r11 != 0) goto L_0x1111
            java.lang.String r11 = r10.resolution
            java.lang.String r0 = "twok"
            boolean r11 = r11.equals(r0)
            if (r11 == 0) goto L_0x10fd
            goto L_0x1111
        L_0x10fd:
            java.lang.String r11 = r10.resolution
            java.lang.String r0 = "fourk"
            boolean r11 = r11.equals(r0)
            if (r11 == 0) goto L_0x1124
            org.opencv.core.Mat r11 = r10.original
            long r0 = r11.getNativeObjAddr()
            helper.Filters.Pencil(r0)
            goto L_0x1124
        L_0x1111:
            org.opencv.core.Mat r11 = r10.original
            long r0 = r11.getNativeObjAddr()
            helper.Filters.Pencil(r0)
            goto L_0x1124
        L_0x111b:
            org.opencv.core.Mat r11 = r10.original
            long r0 = r11.getNativeObjAddr()
            helper.Filters.Pencil(r0)
        L_0x1124:
            int r11 = r12.getWidth()
            int r12 = r12.getHeight()
            android.graphics.Bitmap r11 = r10.getBitmap(r11, r12)
            org.opencv.core.Mat r12 = r10.original
            org.opencv.android.Utils.matToBitmap(r12, r11)
            org.opencv.core.Mat r12 = r10.original
            r12.release()
            android.graphics.Bitmap$Config r12 = android.graphics.Bitmap.Config.RGB_565
            android.graphics.Bitmap r12 = util.Utils.convert(r11, r12)
            r10.recycleBitmap(r11)
            goto L_0x1740
        L_0x1145:
            util.BitmapCache r11 = r10.bitmapCache
            java.lang.String r0 = helper.Constants.PENCIL
            android.graphics.Bitmap r11 = r11.getCacheBitmap(r0)
            if (r11 != 0) goto L_0x1197
            java.lang.String r11 = "Pencil Effect"
            r10.effect_Name = r11
            android.graphics.Bitmap$Config r11 = r12.getConfig()
            android.graphics.Bitmap r11 = r12.copy(r11, r8)
            android.graphics.Bitmap$Config r0 = android.graphics.Bitmap.Config.RGB_565
            android.graphics.Bitmap r11 = util.Utils.convert(r11, r0)
            org.opencv.core.Mat r0 = r10.original
            org.opencv.android.Utils.bitmapToMat(r11, r0)
            org.opencv.core.Mat r11 = r10.original
            long r0 = r11.getNativeObjAddr()
            helper.Filters.Pencil(r0)
            int r11 = r12.getWidth()
            int r12 = r12.getHeight()
            android.graphics.Bitmap r11 = r10.getBitmap(r11, r12)
            org.opencv.core.Mat r12 = r10.original
            org.opencv.android.Utils.matToBitmap(r12, r11)
            org.opencv.core.Mat r12 = r10.original
            r12.release()
            android.graphics.Bitmap$Config r12 = android.graphics.Bitmap.Config.RGB_565
            android.graphics.Bitmap r12 = util.Utils.convert(r11, r12)
            util.BitmapCache r0 = r10.bitmapCache
            java.lang.String r1 = helper.Constants.PENCIL
            r0.addBitmapToCache(r1, r12)
            r10.recycleBitmap(r11)
            goto L_0x1740
        L_0x1197:
            java.lang.String r11 = "Pencil Effect"
            r10.effect_Name = r11
            util.BitmapCache r11 = r10.bitmapCache
            java.lang.String r12 = helper.Constants.PENCIL
            android.graphics.Bitmap r12 = r11.getCacheBitmap(r12)
            goto L_0x1740
        L_0x11a5:
            boolean r11 = r10.original_image
            if (r11 == 0) goto L_0x1244
            android.content.Context r11 = r10.context
            android.content.res.Resources r11 = r11.getResources()
            r10.loadSketchTexture(r11, r9)
            org.opencv.core.Mat r11 = new org.opencv.core.Mat
            r11.<init>()
            android.graphics.Bitmap$Config r0 = r12.getConfig()
            android.graphics.Bitmap r0 = r12.copy(r0, r8)
            android.graphics.Bitmap$Config r1 = android.graphics.Bitmap.Config.RGB_565
            android.graphics.Bitmap r0 = util.Utils.convert(r0, r1)
            org.opencv.core.Mat r1 = r10.original
            org.opencv.android.Utils.bitmapToMat(r0, r1)
            java.lang.String r0 = r10.resolution
            java.lang.String r1 = "hd"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x121a
            java.lang.String r0 = r10.resolution
            java.lang.String r1 = "hdv"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x11df
            goto L_0x121a
        L_0x11df:
            java.lang.String r0 = r10.resolution
            java.lang.String r1 = "full_hd"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x120c
            java.lang.String r0 = r10.resolution
            java.lang.String r1 = "twok"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x11f4
            goto L_0x120c
        L_0x11f4:
            java.lang.String r0 = r10.resolution
            java.lang.String r1 = "fourk"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x1227
            org.opencv.core.Mat r0 = r10.original
            long r0 = r0.getNativeObjAddr()
            long r2 = r11.getNativeObjAddr()
            helper.Filters.ColorSketchFilter4K(r0, r2)
            goto L_0x1227
        L_0x120c:
            org.opencv.core.Mat r0 = r10.original
            long r0 = r0.getNativeObjAddr()
            long r2 = r11.getNativeObjAddr()
            helper.Filters.ColorSketchFilterHD(r0, r2)
            goto L_0x1227
        L_0x121a:
            org.opencv.core.Mat r0 = r10.original
            long r0 = r0.getNativeObjAddr()
            long r2 = r11.getNativeObjAddr()
            helper.Filters.ColorSketchFilter(r0, r2)
        L_0x1227:
            int r0 = r12.getWidth()
            int r12 = r12.getHeight()
            android.graphics.Bitmap r12 = r10.getBitmap(r0, r12)
            org.opencv.android.Utils.matToBitmap(r11, r12)
            android.graphics.Bitmap$Config r0 = android.graphics.Bitmap.Config.RGB_565
            android.graphics.Bitmap r0 = util.Utils.convert(r12, r0)
            r10.recycleBitmap(r12)
            r11.release()
            goto L_0x1732
        L_0x1244:
            util.BitmapCache r11 = r10.bitmapCache
            java.lang.String r0 = helper.Constants.COLOR_SKETCH_TWO
            android.graphics.Bitmap r11 = r11.getCacheBitmap(r0)
            if (r11 != 0) goto L_0x12a4
            java.lang.String r11 = "Color Sketch"
            r10.effect_Name = r11
            android.content.Context r11 = r10.context
            android.content.res.Resources r11 = r11.getResources()
            r10.loadSketchTexture(r11, r9)
            org.opencv.core.Mat r11 = new org.opencv.core.Mat
            r11.<init>()
            android.graphics.Bitmap$Config r0 = r12.getConfig()
            android.graphics.Bitmap r0 = r12.copy(r0, r8)
            android.graphics.Bitmap$Config r1 = android.graphics.Bitmap.Config.RGB_565
            android.graphics.Bitmap r0 = util.Utils.convert(r0, r1)
            org.opencv.core.Mat r1 = r10.original
            org.opencv.android.Utils.bitmapToMat(r0, r1)
            org.opencv.core.Mat r0 = r10.original
            long r0 = r0.getNativeObjAddr()
            long r2 = r11.getNativeObjAddr()
            helper.Filters.ColorSketchFilter(r0, r2)
            int r0 = r12.getWidth()
            int r12 = r12.getHeight()
            android.graphics.Bitmap r12 = r10.getBitmap(r0, r12)
            org.opencv.android.Utils.matToBitmap(r11, r12)
            android.graphics.Bitmap$Config r0 = android.graphics.Bitmap.Config.RGB_565
            android.graphics.Bitmap r0 = util.Utils.convert(r12, r0)
            util.BitmapCache r1 = r10.bitmapCache
            java.lang.String r2 = helper.Constants.COLOR_SKETCH_TWO
            r1.addBitmapToCache(r2, r0)
            r10.recycleBitmap(r12)
            r11.release()
            goto L_0x1732
        L_0x12a4:
            java.lang.String r11 = "Color Sketch"
            r10.effect_Name = r11
            util.BitmapCache r11 = r10.bitmapCache
            java.lang.String r12 = helper.Constants.COLOR_SKETCH_TWO
            android.graphics.Bitmap r12 = r11.getCacheBitmap(r12)
            goto L_0x1740
        L_0x12b2:
            boolean r11 = r10.original_image
            if (r11 == 0) goto L_0x1351
            android.content.Context r11 = r10.context
            android.content.res.Resources r11 = r11.getResources()
            r10.loadSketchTexture(r11, r9)
            org.opencv.core.Mat r11 = new org.opencv.core.Mat
            r11.<init>()
            android.graphics.Bitmap$Config r0 = r12.getConfig()
            android.graphics.Bitmap r0 = r12.copy(r0, r8)
            android.graphics.Bitmap$Config r1 = android.graphics.Bitmap.Config.RGB_565
            android.graphics.Bitmap r0 = util.Utils.convert(r0, r1)
            org.opencv.core.Mat r1 = r10.original
            org.opencv.android.Utils.bitmapToMat(r0, r1)
            java.lang.String r0 = r10.resolution
            java.lang.String r1 = "hd"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x1327
            java.lang.String r0 = r10.resolution
            java.lang.String r1 = "hdv"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x12ec
            goto L_0x1327
        L_0x12ec:
            java.lang.String r0 = r10.resolution
            java.lang.String r1 = "full_hd"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x1319
            java.lang.String r0 = r10.resolution
            java.lang.String r1 = "twok"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x1301
            goto L_0x1319
        L_0x1301:
            java.lang.String r0 = r10.resolution
            java.lang.String r1 = "fourk"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x1334
            org.opencv.core.Mat r0 = r10.original
            long r0 = r0.getNativeObjAddr()
            long r2 = r11.getNativeObjAddr()
            helper.Filters.PencilSketchFilter(r0, r2)
            goto L_0x1334
        L_0x1319:
            org.opencv.core.Mat r0 = r10.original
            long r0 = r0.getNativeObjAddr()
            long r2 = r11.getNativeObjAddr()
            helper.Filters.PencilSketchFilter(r0, r2)
            goto L_0x1334
        L_0x1327:
            org.opencv.core.Mat r0 = r10.original
            long r0 = r0.getNativeObjAddr()
            long r2 = r11.getNativeObjAddr()
            helper.Filters.PencilSketchFilter(r0, r2)
        L_0x1334:
            int r0 = r12.getWidth()
            int r12 = r12.getHeight()
            android.graphics.Bitmap r12 = r10.getBitmap(r0, r12)
            org.opencv.android.Utils.matToBitmap(r11, r12)
            android.graphics.Bitmap$Config r0 = android.graphics.Bitmap.Config.RGB_565
            android.graphics.Bitmap r0 = util.Utils.convert(r12, r0)
            r10.recycleBitmap(r12)
            r11.release()
            goto L_0x1732
        L_0x1351:
            util.BitmapCache r11 = r10.bitmapCache
            java.lang.String r0 = helper.Constants.PAPER_SKETCH
            android.graphics.Bitmap r11 = r11.getCacheBitmap(r0)
            if (r11 != 0) goto L_0x13b1
            java.lang.String r11 = "Pencil Sketch "
            r10.effect_Name = r11
            android.content.Context r11 = r10.context
            android.content.res.Resources r11 = r11.getResources()
            r10.loadSketchTexture(r11, r9)
            org.opencv.core.Mat r11 = new org.opencv.core.Mat
            r11.<init>()
            android.graphics.Bitmap$Config r0 = r12.getConfig()
            android.graphics.Bitmap r0 = r12.copy(r0, r8)
            android.graphics.Bitmap$Config r1 = android.graphics.Bitmap.Config.RGB_565
            android.graphics.Bitmap r0 = util.Utils.convert(r0, r1)
            org.opencv.core.Mat r1 = r10.original
            org.opencv.android.Utils.bitmapToMat(r0, r1)
            org.opencv.core.Mat r0 = r10.original
            long r0 = r0.getNativeObjAddr()
            long r2 = r11.getNativeObjAddr()
            helper.Filters.PencilSketchFilter(r0, r2)
            int r0 = r12.getWidth()
            int r12 = r12.getHeight()
            android.graphics.Bitmap r12 = r10.getBitmap(r0, r12)
            org.opencv.android.Utils.matToBitmap(r11, r12)
            android.graphics.Bitmap$Config r0 = android.graphics.Bitmap.Config.RGB_565
            android.graphics.Bitmap r0 = util.Utils.convert(r12, r0)
            util.BitmapCache r1 = r10.bitmapCache
            java.lang.String r2 = helper.Constants.PAPER_SKETCH
            r1.addBitmapToCache(r2, r0)
            r10.recycleBitmap(r12)
            r11.release()
            goto L_0x1732
        L_0x13b1:
            java.lang.String r11 = "Pencil Sketch "
            r10.effect_Name = r11
            util.BitmapCache r11 = r10.bitmapCache
            java.lang.String r12 = helper.Constants.PAPER_SKETCH
            android.graphics.Bitmap r12 = r11.getCacheBitmap(r12)
            goto L_0x1740
        L_0x13bf:
            boolean r11 = r10.original_image
            if (r11 == 0) goto L_0x1455
            org.opencv.core.Mat r11 = new org.opencv.core.Mat
            r11.<init>()
            android.graphics.Bitmap$Config r0 = r12.getConfig()
            android.graphics.Bitmap r0 = r12.copy(r0, r8)
            android.graphics.Bitmap$Config r1 = android.graphics.Bitmap.Config.RGB_565
            android.graphics.Bitmap r0 = util.Utils.convert(r0, r1)
            org.opencv.core.Mat r1 = r10.original
            org.opencv.android.Utils.bitmapToMat(r0, r1)
            java.lang.String r0 = r10.resolution
            java.lang.String r1 = "hd"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x142b
            java.lang.String r0 = r10.resolution
            java.lang.String r1 = "hdv"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x13f0
            goto L_0x142b
        L_0x13f0:
            java.lang.String r0 = r10.resolution
            java.lang.String r1 = "full_hd"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x141d
            java.lang.String r0 = r10.resolution
            java.lang.String r1 = "twok"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x1405
            goto L_0x141d
        L_0x1405:
            java.lang.String r0 = r10.resolution
            java.lang.String r1 = "fourk"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x1438
            org.opencv.core.Mat r0 = r10.original
            long r0 = r0.getNativeObjAddr()
            long r2 = r11.getNativeObjAddr()
            helper.Filters.CartoonFilter4K(r0, r2)
            goto L_0x1438
        L_0x141d:
            org.opencv.core.Mat r0 = r10.original
            long r0 = r0.getNativeObjAddr()
            long r2 = r11.getNativeObjAddr()
            helper.Filters.CartoonFilterHD(r0, r2)
            goto L_0x1438
        L_0x142b:
            org.opencv.core.Mat r0 = r10.original
            long r0 = r0.getNativeObjAddr()
            long r2 = r11.getNativeObjAddr()
            helper.Filters.CartoonFilter(r0, r2)
        L_0x1438:
            int r0 = r12.getWidth()
            int r12 = r12.getHeight()
            android.graphics.Bitmap r12 = r10.getBitmap(r0, r12)
            org.opencv.android.Utils.matToBitmap(r11, r12)
            android.graphics.Bitmap$Config r0 = android.graphics.Bitmap.Config.RGB_565
            android.graphics.Bitmap r0 = util.Utils.convert(r12, r0)
            r10.recycleBitmap(r12)
            r11.release()
            goto L_0x1732
        L_0x1455:
            util.BitmapCache r11 = r10.bitmapCache
            java.lang.String r0 = helper.Constants.CARTOON_FILTER
            android.graphics.Bitmap r11 = r11.getCacheBitmap(r0)
            if (r11 != 0) goto L_0x14ac
            java.lang.String r11 = "Cartoon Effect"
            r10.effect_Name = r11
            org.opencv.core.Mat r11 = new org.opencv.core.Mat
            r11.<init>()
            android.graphics.Bitmap$Config r0 = r12.getConfig()
            android.graphics.Bitmap r0 = r12.copy(r0, r8)
            android.graphics.Bitmap$Config r1 = android.graphics.Bitmap.Config.RGB_565
            android.graphics.Bitmap r0 = util.Utils.convert(r0, r1)
            org.opencv.core.Mat r1 = r10.original
            org.opencv.android.Utils.bitmapToMat(r0, r1)
            org.opencv.core.Mat r0 = r10.original
            long r0 = r0.getNativeObjAddr()
            long r2 = r11.getNativeObjAddr()
            helper.Filters.CartoonFilter(r0, r2)
            int r0 = r12.getWidth()
            int r12 = r12.getHeight()
            android.graphics.Bitmap r12 = r10.getBitmap(r0, r12)
            org.opencv.android.Utils.matToBitmap(r11, r12)
            android.graphics.Bitmap$Config r0 = android.graphics.Bitmap.Config.RGB_565
            android.graphics.Bitmap r0 = util.Utils.convert(r12, r0)
            util.BitmapCache r1 = r10.bitmapCache
            java.lang.String r2 = helper.Constants.CARTOON_FILTER
            r1.addBitmapToCache(r2, r0)
            r10.recycleBitmap(r12)
            r11.release()
            goto L_0x1732
        L_0x14ac:
            java.lang.String r11 = "Cartoon Effect"
            r10.effect_Name = r11
            util.BitmapCache r11 = r10.bitmapCache
            java.lang.String r12 = helper.Constants.CARTOON_FILTER
            android.graphics.Bitmap r12 = r11.getCacheBitmap(r12)
            goto L_0x1740
        L_0x14ba:
            boolean r0 = r10.original_image
            if (r0 == 0) goto L_0x156d
            org.opencv.core.Mat r0 = new org.opencv.core.Mat
            r0.<init>()
            android.graphics.Bitmap$Config r1 = r12.getConfig()
            android.graphics.Bitmap r1 = r12.copy(r1, r8)
            android.content.Context r2 = r10.context
            android.content.res.Resources r2 = r2.getResources()
            android.graphics.Bitmap r11 = android.graphics.BitmapFactory.decodeResource(r2, r11)
            int r2 = r10.hd_image_width
            int r3 = r10.hd_image_height
            android.graphics.Bitmap r11 = android.graphics.Bitmap.createScaledBitmap(r11, r2, r3, r8)
            jp.co.cyberagent.android.gpuimage.GPUImage r2 = r10.image
            r2.setImage((android.graphics.Bitmap) r12)
            jp.co.cyberagent.android.gpuimage.GPUImage r12 = r10.image
            jp.co.cyberagent.android.gpuimage.GPUImageKuwaharaFilter r2 = new jp.co.cyberagent.android.gpuimage.GPUImageKuwaharaFilter
            r2.<init>(r4)
            r12.setFilter(r2)
            jp.co.cyberagent.android.gpuimage.GPUImage r12 = r10.image
            android.graphics.Bitmap r12 = r12.getBitmapWithFilterApplied()
            org.opencv.core.Mat r2 = r10.original
            org.opencv.android.Utils.bitmapToMat(r12, r2)
            org.opencv.android.Utils.bitmapToMat(r11, r0)
            java.lang.String r12 = r10.resolution
            java.lang.String r2 = "hd"
            boolean r12 = r12.equals(r2)
            if (r12 != 0) goto L_0x154a
            java.lang.String r12 = r10.resolution
            java.lang.String r2 = "hdv"
            boolean r12 = r12.equals(r2)
            if (r12 == 0) goto L_0x150f
            goto L_0x154a
        L_0x150f:
            java.lang.String r12 = r10.resolution
            java.lang.String r2 = "full_hd"
            boolean r12 = r12.equals(r2)
            if (r12 != 0) goto L_0x153c
            java.lang.String r12 = r10.resolution
            java.lang.String r2 = "twok"
            boolean r12 = r12.equals(r2)
            if (r12 == 0) goto L_0x1524
            goto L_0x153c
        L_0x1524:
            java.lang.String r12 = r10.resolution
            java.lang.String r2 = "fourk"
            boolean r12 = r12.equals(r2)
            if (r12 == 0) goto L_0x1557
            org.opencv.core.Mat r12 = r10.original
            long r2 = r12.getNativeObjAddr()
            long r4 = r0.getNativeObjAddr()
            helper.Filters.WaterColorTwo4K(r2, r4)
            goto L_0x1557
        L_0x153c:
            org.opencv.core.Mat r12 = r10.original
            long r2 = r12.getNativeObjAddr()
            long r4 = r0.getNativeObjAddr()
            helper.Filters.WaterColorTwoHD(r2, r4)
            goto L_0x1557
        L_0x154a:
            org.opencv.core.Mat r12 = r10.original
            long r2 = r12.getNativeObjAddr()
            long r4 = r0.getNativeObjAddr()
            helper.Filters.WaterColorTwo(r2, r4)
        L_0x1557:
            org.opencv.core.Mat r12 = r10.original
            org.opencv.android.Utils.matToBitmap(r12, r1)
            android.graphics.Bitmap$Config r12 = android.graphics.Bitmap.Config.RGB_565
            android.graphics.Bitmap r12 = util.Utils.convert(r1, r12)
            r0.release()
            r10.recycleBitmap(r11)
            r10.recycleBitmap(r1)
            goto L_0x1740
        L_0x156d:
            util.BitmapCache r11 = r10.bitmapCache
            java.lang.String r0 = helper.Constants.WATER_PAINTING_TWO
            android.graphics.Bitmap r11 = r11.getCacheBitmap(r0)
            if (r11 != 0) goto L_0x15d7
            java.lang.String r11 = "Water Color Two"
            r10.effect_Name = r11
            org.opencv.core.Mat r11 = new org.opencv.core.Mat
            r11.<init>()
            jp.co.cyberagent.android.gpuimage.GPUImage r0 = r10.image
            r0.setImage((android.graphics.Bitmap) r12)
            jp.co.cyberagent.android.gpuimage.GPUImage r0 = r10.image
            jp.co.cyberagent.android.gpuimage.GPUImageKuwaharaFilter r1 = new jp.co.cyberagent.android.gpuimage.GPUImageKuwaharaFilter
            r1.<init>(r4)
            r0.setFilter(r1)
            android.graphics.Bitmap$Config r0 = r12.getConfig()
            android.graphics.Bitmap r0 = r12.copy(r0, r8)
            android.graphics.Bitmap r12 = r10.getGrainTexture(r12)
            jp.co.cyberagent.android.gpuimage.GPUImage r1 = r10.image
            android.graphics.Bitmap r1 = r1.getBitmapWithFilterApplied()
            org.opencv.core.Mat r2 = r10.original
            org.opencv.android.Utils.bitmapToMat(r1, r2)
            org.opencv.android.Utils.bitmapToMat(r12, r11)
            org.opencv.core.Mat r1 = r10.original
            long r1 = r1.getNativeObjAddr()
            long r3 = r11.getNativeObjAddr()
            helper.Filters.WaterColorTwo(r1, r3)
            org.opencv.core.Mat r1 = r10.original
            org.opencv.android.Utils.matToBitmap(r1, r0)
            android.graphics.Bitmap$Config r1 = android.graphics.Bitmap.Config.RGB_565
            android.graphics.Bitmap r1 = util.Utils.convert(r0, r1)
            r11.release()
            util.BitmapCache r11 = r10.bitmapCache
            java.lang.String r2 = helper.Constants.WATER_PAINTING_TWO
            r11.addBitmapToCache(r2, r1)
            r10.recycleBitmap(r12)
            r10.recycleBitmap(r0)
            r10.recycleBitmap(r12)
            r12 = r1
            goto L_0x1740
        L_0x15d7:
            java.lang.String r11 = "Water Color Two"
            r10.effect_Name = r11
            util.BitmapCache r11 = r10.bitmapCache
            java.lang.String r12 = helper.Constants.WATER_PAINTING_TWO
            android.graphics.Bitmap r12 = r11.getCacheBitmap(r12)
            goto L_0x1740
        L_0x15e5:
            boolean r11 = r10.original_image
            r0 = 4607182418800017408(0x3ff0000000000000, double:1.0)
            if (r11 == 0) goto L_0x16a9
            org.opencv.core.Mat r11 = new org.opencv.core.Mat
            r11.<init>()
            jp.co.cyberagent.android.gpuimage.GPUImage r2 = r10.image
            r2.setImage((android.graphics.Bitmap) r12)
            jp.co.cyberagent.android.gpuimage.GPUImage r12 = r10.image
            jp.co.cyberagent.android.gpuimage.GPUImageKuwaharaFilter r2 = new jp.co.cyberagent.android.gpuimage.GPUImageKuwaharaFilter
            r2.<init>(r4)
            r12.setFilter(r2)
            jp.co.cyberagent.android.gpuimage.GPUImage r12 = r10.image
            android.graphics.Bitmap r12 = r12.getBitmapWithFilterApplied()
            android.graphics.Bitmap$Config r2 = r12.getConfig()
            android.graphics.Bitmap r2 = r12.copy(r2, r8)
            android.graphics.Bitmap$Config r3 = r2.getConfig()
            android.graphics.Bitmap r3 = r2.copy(r3, r8)
            org.opencv.android.Utils.bitmapToMat(r12, r11)
            long r4 = r11.getNativeObjAddr()
            helper.Filters.ColorValue(r4, r0)
            org.opencv.android.Utils.matToBitmap(r11, r2)
            org.opencv.core.Mat r0 = r10.original
            org.opencv.android.Utils.bitmapToMat(r12, r0)
            java.lang.String r0 = r10.resolution
            java.lang.String r1 = "hd"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x166f
            java.lang.String r0 = r10.resolution
            java.lang.String r1 = "hdv"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x163c
            goto L_0x166f
        L_0x163c:
            java.lang.String r0 = r10.resolution
            java.lang.String r1 = "full_hd"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x1665
            java.lang.String r0 = r10.resolution
            java.lang.String r1 = "twok"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x1651
            goto L_0x1665
        L_0x1651:
            java.lang.String r0 = r10.resolution
            java.lang.String r1 = "fourk"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x1678
            org.opencv.core.Mat r0 = r10.original
            long r0 = r0.getNativeObjAddr()
            helper.Filters.WaterColor4K(r0)
            goto L_0x1678
        L_0x1665:
            org.opencv.core.Mat r0 = r10.original
            long r0 = r0.getNativeObjAddr()
            helper.Filters.WaterColorHD(r0)
            goto L_0x1678
        L_0x166f:
            org.opencv.core.Mat r0 = r10.original
            long r0 = r0.getNativeObjAddr()
            helper.Filters.WaterColor(r0)
        L_0x1678:
            org.opencv.core.Mat r0 = r10.original
            org.opencv.android.Utils.matToBitmap(r0, r3)
            jp.co.cyberagent.android.gpuimage.GPUImage r0 = r10.image
            r0.setImage((android.graphics.Bitmap) r3)
            jp.co.cyberagent.android.gpuimage.GPUImage r0 = r10.image
            android.content.Context r1 = r10.context
            java.lang.Class<jp.co.cyberagent.android.gpuimage.GPUImageLinearBurnBlendFilter> r4 = p041jp.p042co.cyberagent.android.gpuimage.GPUImageLinearBurnBlendFilter.class
            jp.co.cyberagent.android.gpuimage.GPUImageFilter r1 = util.Utils.createBlendFilter(r1, r4, r2)
            r0.setFilter(r1)
            jp.co.cyberagent.android.gpuimage.GPUImage r0 = r10.image
            android.graphics.Bitmap r0 = r0.getBitmapWithFilterApplied()
            android.graphics.Bitmap$Config r1 = android.graphics.Bitmap.Config.RGB_565
            android.graphics.Bitmap r0 = util.Utils.convert(r0, r1)
            r10.recycleBitmap(r2)
            r10.recycleBitmap(r3)
            r10.recycleBitmap(r12)
            r11.release()
            goto L_0x1732
        L_0x16a9:
            util.BitmapCache r11 = r10.bitmapCache
            java.lang.String r2 = helper.Constants.WATER_PAINTING
            android.graphics.Bitmap r11 = r11.getCacheBitmap(r2)
            if (r11 != 0) goto L_0x1734
            java.lang.String r11 = "Water Color"
            r10.effect_Name = r11
            org.opencv.core.Mat r11 = new org.opencv.core.Mat
            r11.<init>()
            jp.co.cyberagent.android.gpuimage.GPUImage r2 = r10.image
            r2.setImage((android.graphics.Bitmap) r12)
            jp.co.cyberagent.android.gpuimage.GPUImage r12 = r10.image
            jp.co.cyberagent.android.gpuimage.GPUImageKuwaharaFilter r2 = new jp.co.cyberagent.android.gpuimage.GPUImageKuwaharaFilter
            r2.<init>(r4)
            r12.setFilter(r2)
            jp.co.cyberagent.android.gpuimage.GPUImage r12 = r10.image
            android.graphics.Bitmap r12 = r12.getBitmapWithFilterApplied()
            android.graphics.Bitmap$Config r2 = r12.getConfig()
            android.graphics.Bitmap r2 = r12.copy(r2, r8)
            android.graphics.Bitmap$Config r3 = r2.getConfig()
            android.graphics.Bitmap r3 = r2.copy(r3, r8)
            org.opencv.android.Utils.bitmapToMat(r12, r11)
            long r4 = r11.getNativeObjAddr()
            helper.Filters.ColorValue(r4, r0)
            org.opencv.android.Utils.matToBitmap(r11, r2)
            org.opencv.core.Mat r0 = r10.original
            org.opencv.android.Utils.bitmapToMat(r12, r0)
            org.opencv.core.Mat r0 = r10.original
            long r0 = r0.getNativeObjAddr()
            helper.Filters.WaterColor(r0)
            org.opencv.core.Mat r0 = r10.original
            org.opencv.android.Utils.matToBitmap(r0, r3)
            jp.co.cyberagent.android.gpuimage.GPUImage r0 = r10.image
            r0.setImage((android.graphics.Bitmap) r3)
            jp.co.cyberagent.android.gpuimage.GPUImage r0 = r10.image
            android.content.Context r1 = r10.context
            java.lang.Class<jp.co.cyberagent.android.gpuimage.GPUImageLinearBurnBlendFilter> r4 = p041jp.p042co.cyberagent.android.gpuimage.GPUImageLinearBurnBlendFilter.class
            jp.co.cyberagent.android.gpuimage.GPUImageFilter r1 = util.Utils.createBlendFilter(r1, r4, r2)
            r0.setFilter(r1)
            jp.co.cyberagent.android.gpuimage.GPUImage r0 = r10.image
            android.graphics.Bitmap r0 = r0.getBitmapWithFilterApplied()
            android.graphics.Bitmap$Config r1 = android.graphics.Bitmap.Config.RGB_565
            android.graphics.Bitmap r0 = util.Utils.convert(r0, r1)
            r10.recycleBitmap(r2)
            r10.recycleBitmap(r3)
            r10.recycleBitmap(r12)
            r11.release()
            util.BitmapCache r11 = r10.bitmapCache
            java.lang.String r12 = helper.Constants.WATER_PAINTING
            r11.addBitmapToCache(r12, r0)
        L_0x1732:
            r12 = r0
            goto L_0x1740
        L_0x1734:
            java.lang.String r11 = "Water Color"
            r10.effect_Name = r11
            util.BitmapCache r11 = r10.bitmapCache
            java.lang.String r12 = helper.Constants.WATER_PAINTING
            android.graphics.Bitmap r12 = r11.getCacheBitmap(r12)
        L_0x1740:
            org.opencv.core.Mat r11 = r10.original
            if (r11 == 0) goto L_0x1749
            org.opencv.core.Mat r11 = r10.original
            r11.release()
        L_0x1749:
            org.opencv.core.Mat r11 = r10.laplace
            if (r11 == 0) goto L_0x1752
            org.opencv.core.Mat r11 = r10.laplace
            r11.release()
        L_0x1752:
            return r12
        */
        throw new UnsupportedOperationException("Method not decompiled: util.Effect.getEffect(java.lang.String, android.graphics.Bitmap):android.graphics.Bitmap");
    }

    public void clear() {
        if (this.original != null) {
            this.original.release();
            this.original = null;
        }
        if (this.laplace != null) {
            this.laplace.release();
            this.laplace = null;
        }
        this.image = null;
        this.context = null;
    }

    public void releaseMemory() {
        if (this.original != null) {
            this.original.release();
            this.original = null;
        }
        if (this.laplace != null) {
            this.laplace.release();
            this.laplace = null;
        }
        this.image = null;
    }

    public void loadImages(int i, int i2) {
        this.imageWidth = i;
        this.imageHeight = i2;
    }

    public Bitmap getGrainTexture(Bitmap bitmap) {
        Bitmap bitmap2;
        Bitmap bitmap3;
        if (this.context != null) {
            bitmap2 = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.grain);
        } else {
            bitmap2 = BitmapFactory.decodeResource(App.getInstance().getApplicationContext().getResources(),R.drawable.grain);
        }
        if (this.imageWidth != 0 && this.imageHeight != 0) {
            bitmap3 = Bitmap.createScaledBitmap(bitmap2, this.imageWidth, this.imageHeight, true);
        } else if (Constants.ORIGINAL_BITMAP_WIDTH == 0 || Constants.ORIGINAL_BITMAP_HEIGHT == 0) {
            bitmap3 = Bitmap.createScaledBitmap(bitmap2, bitmap.getWidth(), bitmap.getHeight(), true);
        } else {
            bitmap3 = Bitmap.createScaledBitmap(bitmap2, Constants.ORIGINAL_BITMAP_WIDTH, Constants.ORIGINAL_BITMAP_HEIGHT, true);
        }
        return util.Utils.convert(bitmap3, Bitmap.Config.RGB_565);
    }

    public Bitmap Sketch(Bitmap bitmap, int i) {
        this.image.setImage(bitmap);
        this.image.setFilter(new GPUImageGaussianBlurFilter((float) i));
        Bitmap bitmapWithFilterApplied = this.image.getBitmapWithFilterApplied();
        this.image.deleteImage();
        this.image.setImage(bitmap);
        this.image.setFilter(new GPUImageColorInvertFilter());
        this.image.setImage(this.image.getBitmapWithFilterApplied());
        this.image.setFilter(util.Utils.createBlendFilter(this.context, GPUImageLinearBurnBlendFilter.class, bitmapWithFilterApplied));
        this.image.setImage(this.image.getBitmapWithFilterApplied());
        this.image.setFilter(new GPUImageColorInvertFilter());
        this.image.setImage(this.image.getBitmapWithFilterApplied());
        this.image.setFilter(new GPUImageGrayscaleFilter());
        return this.image.getBitmapWithFilterApplied();
    }

    public Bitmap getFilteredBitmap(Bitmap bitmap, GPUImageFilter gPUImageFilter) {
        this.image.setImage(bitmap);
        this.image.setFilter(gPUImageFilter);
        return this.image.getBitmapWithFilterApplied();
    }

    public void showToast(String str) {
        Toast.makeText(this.context, str, Toast.LENGTH_SHORT).show();
    }

    public String getEffectName() {
        return this.effect_Name;
    }

    private Bitmap MattoBitmap(Mat mat, Bitmap bitmap) {
        Bitmap copy = bitmap.copy(bitmap.getConfig(), true);
        Utils.matToBitmap(mat, copy);
        return copy;
    }

    private void loadSketchTexture(Resources resources, int i) {
        Bitmap decodeResource = BitmapFactory.decodeResource(resources, i);
        Mat mat = new Mat(decodeResource.getHeight(), decodeResource.getWidth(), CvType.CV_8UC4);
        Utils.bitmapToMat(decodeResource, mat);
        Mat mat2 = new Mat(mat.size(), CvType.CV_8UC1);
        Imgproc.cvtColor(mat, mat2, 11);
        Filters.LoadSketchTexture(mat2.getNativeObjAddr());
        decodeResource.recycle();
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.effect_Name);
        parcel.writeString(this.resolution);
        parcel.writeInt(this.imageWidth);
        parcel.writeInt(this.imageHeight);
        parcel.writeInt(this.hd_image_width);
        parcel.writeInt(this.hd_image_height);
        parcel.writeByte(this.original_image ? (byte) 1 : 0);
    }
}