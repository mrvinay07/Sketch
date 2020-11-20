package util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.os.StatFs;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import jp.co.cyberagent.android.gpuimage.GPUImage;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageSaturationFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageTwoInputFilter;

public class Utils {
    public static String bytesToMemory(long j) {
        int i = (j > PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID ? 1 : (j == PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID ? 0 : -1));
        if (i < 0) {
            return floatForm((double) j);
        }
        if (i >= 0 && j < PlaybackStateCompat.ACTION_SET_CAPTIONING_ENABLED) {
            return floatForm(((double) j) / ((double) PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID));
        }
        if (j >= PlaybackStateCompat.ACTION_SET_CAPTIONING_ENABLED && j < 1073741824) {
            return floatForm(((double) j) / ((double) PlaybackStateCompat.ACTION_SET_CAPTIONING_ENABLED));
        }
        if (j >= 1073741824 && j < 1099511627776L) {
            return floatForm(((double) j) / ((double) 1073741824));
        }
        if (j >= 1099511627776L && j < 1125899906842624L) {
            return floatForm(((double) j) / ((double) 1099511627776L));
        }
        if (j < 1125899906842624L || j >= 1152921504606846976L) {
            return j >= 1152921504606846976L ? floatForm(((double) j) / ((double) 1152921504606846976L)) : "???";
        }
        return floatForm(((double) j) / ((double) 1125899906842624L));
    }

    public static long getCacheSizeInBytes(File file, float f, long j) {
        long j2;
        long j3;
        if (file == null || (!file.exists() && !file.mkdir())) {
            return 0;
        }
        try {
            StatFs statFs = new StatFs(file.getPath());
            Log.d("enter", "Utils");
            if (isVersionOrGreaterThan(18)) {
                j3 = statFs.getBlockCountLong() * statFs.getBlockSizeLong();
                j2 = statFs.getAvailableBlocksLong() * statFs.getBlockSizeLong();
            } else {
                j3 = (long) (statFs.getBlockCount() * statFs.getBlockSize());
                j2 = (long) (statFs.getAvailableBlocks() * statFs.getBlockSize());
            }
            long j4 = (j / PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID) / PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID;
            Log.d("maxbytes", Long.toString(j3));
            Log.d("total", Long.toString(j4));
            long j5 = (j3 / PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID) / PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID;
            Log.d("total", String.valueOf(j5));
            int i = ((int) (10 * j5)) / 100;
            long min = Math.min(j5, j4) * PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID * PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID;
            return min > j2 ? j2 / 2 : min;
        } catch (IllegalArgumentException unused) {
            return 0;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:7:0x0015  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.io.File getCacheDirectory(android.content.Context r2, java.lang.String r3) {
        /*
            java.lang.String r0 = android.os.Environment.getExternalStorageState()
            java.lang.String r1 = "mounted"
            boolean r0 = r0.equals(r1)
            r1 = 0
            if (r0 == 0) goto L_0x0012
            java.io.File r0 = r2.getExternalCacheDir()     // Catch:{ NullPointerException -> 0x0012 }
            goto L_0x0013
        L_0x0012:
            r0 = r1
        L_0x0013:
            if (r0 != 0) goto L_0x0019
            java.io.File r0 = r2.getCacheDir()
        L_0x0019:
            java.lang.String r2 = r0.getAbsolutePath()
            helper.Constants.CACHE_DIRECTORY_PARENT_FILE = r2
            if (r0 == 0) goto L_0x0028
            if (r3 == 0) goto L_0x0028
            java.io.File r1 = new java.io.File
            r1.<init>(r0, r3)
        L_0x0028:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: util.Utils.getCacheDirectory(android.content.Context, java.lang.String):java.io.File");
    }

    public static void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException unused) {
            }
        }
    }

    public static String floatForm(double d) {
        return new DecimalFormat("#.##").format(d);
    }

    public static Bitmap updateHSV(Bitmap bitmap, float f, float f2, float f3) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int i = width * height;
        int[] iArr = new int[i];
        int[] iArr2 = new int[i];
        float[] fArr = new float[3];
        bitmap.getPixels(iArr, 0, width, 0, 0, width, height);
        int i2 = 0;
        int i3 = 0;
        while (i2 < height) {
            int i4 = i3;
            for (int i5 = 0; i5 < width; i5++) {
                Color.colorToHSV(iArr[i4], fArr);
                fArr[0] = fArr[0] + f;
                if (fArr[0] < 0.0f) {
                    fArr[0] = 0.0f;
                } else if (fArr[0] > 360.0f) {
                    fArr[0] = 360.0f;
                }
                fArr[1] = fArr[1] + f2;
                if (fArr[1] < 0.0f) {
                    fArr[1] = 0.0f;
                } else if (fArr[1] > 1.0f) {
                    fArr[1] = 1.0f;
                }
                fArr[2] = fArr[2] + f3;
                if (fArr[2] < 0.0f) {
                    fArr[2] = 0.0f;
                } else if (fArr[2] > 1.0f) {
                    fArr[2] = 1.0f;
                }
                iArr2[i4] = Color.HSVToColor(fArr);
                i4++;
            }
            i2++;
            i3 = i4;
        }
        return Bitmap.createBitmap(iArr2, width, height, Bitmap.Config.ARGB_8888);
    }

    public static String bytesToHuman(long j) {
        int i = (j > PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID ? 1 : (j == PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID ? 0 : -1));
        if (i < 0) {
            return floatForm((double) j) + " byte";
        } else if (i >= 0 && j < PlaybackStateCompat.ACTION_SET_CAPTIONING_ENABLED) {
            return floatForm(((double) j) / ((double) PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID)) + " Kb";
        } else if (j >= PlaybackStateCompat.ACTION_SET_CAPTIONING_ENABLED && j < 1073741824) {
            return floatForm(((double) j) / ((double) PlaybackStateCompat.ACTION_SET_CAPTIONING_ENABLED)) + " Mb";
        } else if (j >= 1073741824 && j < 1099511627776L) {
            return floatForm(((double) j) / ((double) 1073741824)) + " Gb";
        } else if (j >= 1099511627776L && j < 1125899906842624L) {
            return floatForm(((double) j) / ((double) 1099511627776L)) + " Tb";
        } else if (j >= 1125899906842624L && j < 1152921504606846976L) {
            return floatForm(((double) j) / ((double) 1125899906842624L)) + " Pb";
        } else if (j < 1152921504606846976L) {
            return "???";
        } else {
            return floatForm(((double) j) / ((double) 1152921504606846976L)) + " Eb";
        }
    }

    public static boolean isVersionOrGreaterThan(int i) {
        return Build.VERSION.SDK_INT >= i;
    }

    public static Bitmap convert(Bitmap bitmap, Bitmap.Config config) {
        Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), config);
        Canvas canvas = new Canvas(createBitmap);
        Paint paint = new Paint();
        paint.setColor(-16777216);
        canvas.drawBitmap(bitmap, 0.0f, 0.0f, paint);
        return createBitmap;
    }

    public static String removeExtention(String str) {
        int lastIndexOf = str.lastIndexOf(46);
        if (lastIndexOf <= 0) {
            return str;
        }
        return str.substring(0, lastIndexOf);
    }

    public static Bitmap saturate(GPUImage gPUImage, Bitmap bitmap, float f) {
        gPUImage.setImage(bitmap);
        gPUImage.setFilter(new GPUImageSaturationFilter(f));
        return gPUImage.getBitmapWithFilterApplied();
    }

    public static GPUImageFilter createBlendFilter(Context context, Class<? extends GPUImageTwoInputFilter> cls, Bitmap bitmap) {
        try {
            GPUImageTwoInputFilter gPUImageTwoInputFilter = (GPUImageTwoInputFilter) cls.newInstance();
            gPUImageTwoInputFilter.setBitmap(bitmap);
            return gPUImageTwoInputFilter;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Bitmap blendBitmaps(Mat mat, Mat mat2, Mat mat3, Bitmap bitmap, int i) {
        double d = ((double) (100 - i)) / 100.0d;
        Core.addWeighted(mat, d, mat2, 1.0d - d, FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE, mat3);
        org.opencv.android.Utils.matToBitmap(mat3, bitmap);
        return bitmap;
    }

    public static Mat ChalkEffect(Bitmap bitmap, boolean z, int i) {
        Mat mat;
        Mat mat2;
        int i2 = i;
        Mat mat3 = new Mat();
        Mat mat4 = new Mat();
        org.opencv.android.Utils.bitmapToMat(bitmap.copy(bitmap.getConfig(), true), mat4);
        Imgproc.cvtColor(mat4, mat3, 6);
        Imgproc.GaussianBlur(mat3, mat3, new Size(3.0d, 3.0d), 1.0d);
        Mat mat5 = new Mat();
        Imgproc.Canny(mat3, mat5, 80.0d, 100.0d, 3, false);
        ArrayList arrayList = new ArrayList();
        Imgproc.findContours(mat5, arrayList, new Mat(), 3, 2);
        Random random = new Random();
        Mat mat6 = new Mat(mat5.size(), CvType.CV_8UC3);
        Scalar scalar = new Scalar(255.0d, 255.0d, 255.0d);
        int i3 = 0;
        while (i3 < arrayList.size()) {
            if (z) {
                mat2 = mat3;
                mat = mat4;
                arrayList = arrayList;
                i2 = i;
                Imgproc.drawContours(mat6, arrayList, i3, new Scalar((double) random.nextInt(255), (double) random.nextInt(255), (double) random.nextInt(255)), i2);
            } else {
                mat2 = mat3;
                mat = mat4;
                Imgproc.drawContours(mat6, arrayList, i3, scalar, i2);
            }
            i3++;
            mat3 = mat2;
            mat4 = mat;
        }
        mat5.release();
        mat3.release();
        mat4.release();
        return mat6;
    }
}