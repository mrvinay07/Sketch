package util;

import android.content.Context;
import android.graphics.Bitmap;
import java.io.File;
import java.io.IOException;
import java.util.Locale;

public class Resizer {
    private Bitmap.CompressFormat compressFormat = Bitmap.CompressFormat.JPEG;
    private String outputDirPath;
    private String outputFilename;
    private int quality = 80;
    private File sourceImage;
    private int targetLength = 1080;

    public Resizer(Context context) {
    }

    public Resizer setTargetLength(int i) {
        if (i < 0) {
            i = 0;
        }
        this.targetLength = i;
        return this;
    }

    public Resizer setQuality(int i) {
        if (i < 0) {
            this.quality = 0;
        } else if (i > 100) {
            this.quality = 100;
        } else {
            this.quality = i;
        }
        return this;
    }

    /* JADX WARNING: Removed duplicated region for block: B:17:0x0037  */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x003c  */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x0041  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public util.Resizer setOutputFormat(java.lang.String r3) {
        /*
            r2 = this;
            int r0 = r3.hashCode()
            r1 = 79369(0x13609, float:1.1122E-40)
            if (r0 == r1) goto L_0x0028
            r1 = 2283624(0x22d868, float:3.200039E-39)
            if (r0 == r1) goto L_0x001e
            r1 = 2660252(0x28979c, float:3.727807E-39)
            if (r0 == r1) goto L_0x0014
            goto L_0x0032
        L_0x0014:
            java.lang.String r0 = "WEBP"
            boolean r3 = r3.equals(r0)
            if (r3 == 0) goto L_0x0032
            r3 = 2
            goto L_0x0033
        L_0x001e:
            java.lang.String r0 = "JPEG"
            boolean r3 = r3.equals(r0)
            if (r3 == 0) goto L_0x0032
            r3 = 0
            goto L_0x0033
        L_0x0028:
            java.lang.String r0 = "PNG"
            boolean r3 = r3.equals(r0)
            if (r3 == 0) goto L_0x0032
            r3 = 1
            goto L_0x0033
        L_0x0032:
            r3 = -1
        L_0x0033:
            switch(r3) {
                case 0: goto L_0x0041;
                case 1: goto L_0x003c;
                case 2: goto L_0x0037;
                default: goto L_0x0036;
            }
        L_0x0036:
            goto L_0x0045
        L_0x0037:
            android.graphics.Bitmap$CompressFormat r3 = android.graphics.Bitmap.CompressFormat.WEBP
            r2.compressFormat = r3
            goto L_0x0045
        L_0x003c:
            android.graphics.Bitmap$CompressFormat r3 = android.graphics.Bitmap.CompressFormat.PNG
            r2.compressFormat = r3
            goto L_0x0045
        L_0x0041:
            android.graphics.Bitmap$CompressFormat r3 = android.graphics.Bitmap.CompressFormat.JPEG
            r2.compressFormat = r3
        L_0x0045:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: util.Resizer.setOutputFormat(java.lang.String):util.Resizer");
    }

    public Resizer setOutputFormat(Bitmap.CompressFormat compressFormat2) {
        if (compressFormat2 != null) {
            this.compressFormat = compressFormat2;
            return this;
        }
        throw new NullPointerException("compressFormat null");
    }

    public Resizer setOutputFilename(String str) {
        if (str == null) {
            throw new NullPointerException("filename null");
        } else if (str.toLowerCase(Locale.US).endsWith(".jpg") || str.toLowerCase(Locale.US).endsWith(".jpeg") || str.toLowerCase(Locale.US).endsWith(".png") || str.toLowerCase(Locale.US).endsWith(".webp")) {
            throw new IllegalStateException("Filename should be provided without extension. See setOutputFormat(String).");
        } else {
            this.outputFilename = str;
            return this;
        }
    }

    public Resizer setOutputDirPath(String str) {
        this.outputDirPath = str;
        return this;
    }

    public Resizer setSourceImage(File file) {
        this.sourceImage = file;
        return this;
    }

    public File getResizedFile() throws IOException {
        return ImageUtils.getScaledImage(this.targetLength, this.quality, this.compressFormat, this.outputDirPath, this.outputFilename, this.sourceImage);
    }

    public Bitmap getResizedBitmap() throws IOException {
        return ImageUtils.getScaledBitmap(this.targetLength, this.sourceImage);
    }
}