package helper;

import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import androidx.media2.exoplayer.external.util.MimeTypes;

public class FileUtils {
    static final String TAG = "FileUtils";

    private FileUtils() {
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x002d, code lost:
        if (r7 != null) goto L_0x002f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x002f, code lost:
        r7.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0051, code lost:
        if (r7 != null) goto L_0x002f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0054, code lost:
        return null;
     */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x0058  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String getDataColumn(android.content.Context r7, android.net.Uri r8, java.lang.String r9, java.lang.String[] r10) {
        /*
            java.lang.String r0 = "_data"
            java.lang.String[] r3 = new java.lang.String[]{r0}
            r0 = 0
            android.content.ContentResolver r1 = r7.getContentResolver()     // Catch:{ IllegalArgumentException -> 0x0036, all -> 0x0033 }
            r6 = 0
            r2 = r8
            r4 = r9
            r5 = r10
            android.database.Cursor r7 = r1.query(r2, r3, r4, r5, r6)     // Catch:{ IllegalArgumentException -> 0x0036, all -> 0x0033 }
            if (r7 == 0) goto L_0x002d
            boolean r8 = r7.moveToFirst()     // Catch:{ IllegalArgumentException -> 0x002b }
            if (r8 == 0) goto L_0x002d
            java.lang.String r8 = "_data"
            int r8 = r7.getColumnIndexOrThrow(r8)     // Catch:{ IllegalArgumentException -> 0x002b }
            java.lang.String r8 = r7.getString(r8)     // Catch:{ IllegalArgumentException -> 0x002b }
            if (r7 == 0) goto L_0x002a
            r7.close()
        L_0x002a:
            return r8
        L_0x002b:
            r8 = move-exception
            goto L_0x0038
        L_0x002d:
            if (r7 == 0) goto L_0x0054
        L_0x002f:
            r7.close()
            goto L_0x0054
        L_0x0033:
            r8 = move-exception
            r7 = r0
            goto L_0x0056
        L_0x0036:
            r8 = move-exception
            r7 = r0
        L_0x0038:
            java.lang.String r9 = "FileUtils"
            java.util.Locale r10 = java.util.Locale.getDefault()     // Catch:{ all -> 0x0055 }
            java.lang.String r1 = "getDataColumn: _data - [%s]"
            r2 = 1
            java.lang.Object[] r2 = new java.lang.Object[r2]     // Catch:{ all -> 0x0055 }
            r3 = 0
            java.lang.String r8 = r8.getMessage()     // Catch:{ all -> 0x0055 }
            r2[r3] = r8     // Catch:{ all -> 0x0055 }
            java.lang.String r8 = java.lang.String.format(r10, r1, r2)     // Catch:{ all -> 0x0055 }
            android.util.Log.i(r9, r8)     // Catch:{ all -> 0x0055 }
            if (r7 == 0) goto L_0x0054
            goto L_0x002f
        L_0x0054:
            return r0
        L_0x0055:
            r8 = move-exception
        L_0x0056:
            if (r7 == 0) goto L_0x005b
            r7.close()
        L_0x005b:
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: helper.FileUtils.getDataColumn(android.content.Context, android.net.Uri, java.lang.String, java.lang.String[]):java.lang.String");
    }

    @SuppressLint({"NewApi"})
    public static String getPath(Context context, Uri uri) {
        Uri uri2 = null;
        if (!(Build.VERSION.SDK_INT >= 19) || !DocumentsContract.isDocumentUri(context, uri)) {
            if ("content".equalsIgnoreCase(uri.getScheme())) {
                if (isGooglePhotosUri(uri)) {
                    return uri.getLastPathSegment();
                }
                return getDataColumn(context, uri, (String) null, (String[]) null);
            } else if ("file".equalsIgnoreCase(uri.getScheme())) {
                return uri.getPath();
            }
        } else if (isExternalStorageDocument(uri)) {
            String[] split = DocumentsContract.getDocumentId(uri).split(":");
            if ("primary".equalsIgnoreCase(split[0])) {
                return Environment.getExternalStorageDirectory() + "/" + split[1];
            }
        } else if (isDownloadsDocument(uri)) {
            try {
                return getDataColumn(context, ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(DocumentsContract.getDocumentId(uri)).longValue()), (String) null, (String[]) null);
            } catch (NumberFormatException unused) {
                return null;
            }
        } else if (isMediaDocument(uri)) {
            String[] split2 = DocumentsContract.getDocumentId(uri).split(":");
            String str = split2[0];
            if ("image".equals(str)) {
                uri2 = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            } else if ("video".equals(str)) {
                uri2 = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
            } else if (MimeTypes.BASE_TYPE_AUDIO.equals(str)) {
                uri2 = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
            }
            return getDataColumn(context, uri2, "_id=?", new String[]{split2[1]});
        }
        return null;
    }

    /* JADX WARNING: Removed duplicated region for block: B:22:0x004c  */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x0051  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void copyFile(@androidx.annotation.NonNull java.lang.String r10, @androidx.annotation.NonNull java.lang.String r11) throws java.io.IOException {
        /*
            boolean r0 = r10.equalsIgnoreCase(r11)
            if (r0 == 0) goto L_0x0007
            return
        L_0x0007:
            r0 = 0
            java.io.FileInputStream r1 = new java.io.FileInputStream     // Catch:{ all -> 0x0048 }
            java.io.File r2 = new java.io.File     // Catch:{ all -> 0x0048 }
            r2.<init>(r10)     // Catch:{ all -> 0x0048 }
            r1.<init>(r2)     // Catch:{ all -> 0x0048 }
            java.nio.channels.FileChannel r10 = r1.getChannel()     // Catch:{ all -> 0x0048 }
            java.io.FileOutputStream r1 = new java.io.FileOutputStream     // Catch:{ all -> 0x0042 }
            java.io.File r2 = new java.io.File     // Catch:{ all -> 0x0042 }
            r2.<init>(r11)     // Catch:{ all -> 0x0042 }
            r1.<init>(r2)     // Catch:{ all -> 0x0042 }
            java.nio.channels.FileChannel r11 = r1.getChannel()     // Catch:{ all -> 0x0042 }
            r4 = 0
            long r6 = r10.size()     // Catch:{ all -> 0x003d }
            r3 = r10
            r8 = r11
            r3.transferTo(r4, r6, r8)     // Catch:{ all -> 0x003d }
            r10.close()     // Catch:{ all -> 0x003d }
            if (r10 == 0) goto L_0x0037
            r10.close()
        L_0x0037:
            if (r11 == 0) goto L_0x003c
            r11.close()
        L_0x003c:
            return
        L_0x003d:
            r0 = move-exception
            r9 = r0
            r0 = r10
            r10 = r9
            goto L_0x004a
        L_0x0042:
            r11 = move-exception
            r9 = r0
            r0 = r10
            r10 = r11
            r11 = r9
            goto L_0x004a
        L_0x0048:
            r10 = move-exception
            r11 = r0
        L_0x004a:
            if (r0 == 0) goto L_0x004f
            r0.close()
        L_0x004f:
            if (r11 == 0) goto L_0x0054
            r11.close()
        L_0x0054:
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: helper.FileUtils.copyFile(java.lang.String, java.lang.String):void");
    }
}
