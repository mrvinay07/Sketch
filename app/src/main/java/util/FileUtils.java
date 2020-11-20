package util;

import android.graphics.Bitmap;
import java.io.File;
import java.util.Locale;

public class FileUtils {
    public static String getOutputFilePath(Bitmap.CompressFormat compressFormat, String str, String str2, File file) {
        String str3;
        String name = file.getName();
        String str4 = "." + compressFormat.name().toLowerCase(Locale.US).replace("jpeg", "jpg");
        if (str2 == null) {
            int lastIndexOf = name.lastIndexOf(46);
            if (lastIndexOf == -1) {
                str3 = name + str4;
            } else {
                str3 = name.substring(0, lastIndexOf) + str4;
            }
        } else {
            str3 = str2 + str4;
        }
        return str + File.separator + str3;
    }

    /* JADX WARNING: Removed duplicated region for block: B:11:0x0016  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void writeBitmapToFile(android.graphics.Bitmap r2, android.graphics.Bitmap.CompressFormat r3, int r4, java.lang.String r5) throws java.io.IOException {
        /*
            r0 = 0
            java.io.FileOutputStream r1 = new java.io.FileOutputStream     // Catch:{ all -> 0x0013 }
            r1.<init>(r5)     // Catch:{ all -> 0x0013 }
            r2.compress(r3, r4, r1)     // Catch:{ all -> 0x0010 }
            r1.flush()
            r1.close()
            return
        L_0x0010:
            r2 = move-exception
            r0 = r1
            goto L_0x0014
        L_0x0013:
            r2 = move-exception
        L_0x0014:
            if (r0 == 0) goto L_0x001c
            r0.flush()
            r0.close()
        L_0x001c:
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: util.FileUtils.writeBitmapToFile(android.graphics.Bitmap, android.graphics.Bitmap$CompressFormat, int, java.lang.String):void");
    }
}
