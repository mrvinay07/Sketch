package util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;

public class ImageUtils {
    public static Bitmap bitmap;
    public static int maximumHeight;
    public static int maximumWidth;
    public static List<String> resolutions = new ArrayList();
    public static int textureSize;

    public static File getScaledImage(int i, int i2, Bitmap.CompressFormat compressFormat, String str, String str2, File file) throws IOException {
        File file2 = new File(str);
        if (!file2.exists()) {
            file2.mkdirs();
        }
        String outputFilePath = FileUtils.getOutputFilePath(compressFormat, str, str2, file);
        FileUtils.writeBitmapToFile(getScaledBitmap(i, file), compressFormat, i2, outputFilePath);
        return new File(outputFilePath);
    }

    public static Bitmap getScaledBitmap(int i, File file) {
        int i2;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = false;
        Bitmap decodeFile = BitmapFactory.decodeFile(file.getAbsolutePath(), options);
        int i3 = options.outWidth;
        int i4 = options.outHeight;
        float f = ((float) i3) / ((float) i4);
        if (i3 > i4) {
            i2 = Math.round(((float) i) / f);
        } else {
            i2 = i;
            i = Math.round(((float) i) / (1.0f / f));
        }
        return Bitmap.createScaledBitmap(decodeFile, i, i2, true);
    }

    public static int getMaximumTextureSize() {
        EGL10 egl10 = (EGL10) EGLContext.getEGL();
        EGLDisplay eglGetDisplay = egl10.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY);
        egl10.eglInitialize(eglGetDisplay, new int[2]);
        int[] iArr = new int[1];
        egl10.eglGetConfigs(eglGetDisplay, (EGLConfig[]) null, 0, iArr);
        EGLConfig[] eGLConfigArr = new EGLConfig[iArr[0]];
        egl10.eglGetConfigs(eglGetDisplay, eGLConfigArr, iArr[0], iArr);
        int[] iArr2 = new int[1];
        int i = 0;
        for (int i2 = 0; i2 < iArr[0]; i2++) {
            egl10.eglGetConfigAttrib(eglGetDisplay, eGLConfigArr[i2], 12332, iArr2);
            if (i < iArr2[0]) {
                i = iArr2[0];
            }
            Log.i("GLHelper", Integer.toString(iArr2[0]));
        }
        egl10.eglTerminate(eglGetDisplay);
        Log.i("GLHelper", "Maximum GL texture size: " + Integer.toString(i));
        return i;
    }

    public static void setMaximumTextureSize(int i) {
        textureSize = i;
        calculateResolutions();
    }

    public static void setBitmapMaximumSize(int i, int i2) {
        maximumWidth = i;
        maximumHeight = i2;
    }

    public static void calculateResolutions() {
        int i = textureSize;
        if (i != 2048) {
            if (i != 4096) {
                if (i == 8192) {
                    if (maximumWidth >= 4096) {
                        resolutions.add("1440 HD");
                        resolutions.add("1080 FHD");
                        resolutions.add("2048 2K");
                        resolutions.add("3840 UHD");
                        resolutions.add("4096 4K");
                    } else if (maximumWidth >= 2160) {
                        resolutions.add("1440 HD");
                        resolutions.add("1080 FHD");
                        resolutions.add("2048 2K");
                    } else if (maximumWidth > 1440) {
                        resolutions.add("1440 HD");
                        if (maximumWidth >= 1920) {
                            resolutions.add("1080 FHD");
                        }
                    } else {
                        resolutions.add("Sorry");
                    }
                }
            } else if (maximumWidth >= 4096) {
                resolutions.add("1440");
                resolutions.add("1080 FHD");
                resolutions.add("2048 2K");
                resolutions.add("3840 UHD");
            } else if (maximumWidth >= 2048) {
                resolutions.add("1440 HD");
                resolutions.add("1080 FHD");
                resolutions.add("2048 2K");
            } else if (maximumWidth > 1440) {
                resolutions.add("1440 HD");
                if (maximumWidth >= 1920) {
                    resolutions.add("1080 FHD");
                }
            } else {
                resolutions.add("Sorry");
            }
        } else if (maximumWidth >= 4096) {
            resolutions.add("1440");
            resolutions.add("1080 FHD");
            resolutions.add("2048 2K");
        } else if (maximumWidth >= 1920) {
            resolutions.add("720 HD");
            resolutions.add("1440 HD");
            resolutions.add("1080 FHD");
        } else if (maximumWidth >= 1440) {
            resolutions.add("720 HD");
            resolutions.add("1440 HD");
            resolutions.add("1080 FHD");
        } else if (maximumWidth >= 800) {
            resolutions.add("720 HD");
        } else {
            resolutions.add("Sorry");
        }
    }
}