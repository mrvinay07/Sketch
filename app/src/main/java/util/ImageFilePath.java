package util;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.util.IOUtils;
import helper.Constants;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ImageFilePath {
    public static final String DOCUMENTS_DIR = "documents";
    static final String TAG = "FileUtils";

    private static void logDir(File file) {
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v2, resolved type: android.net.Uri} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v6, resolved type: java.lang.String} */
    /* JADX WARNING: type inference failed for: r3v1 */
    /* JADX WARNING: type inference failed for: r3v8 */
    /* JADX WARNING: type inference failed for: r3v9 */
    /* JADX WARNING: type inference failed for: r3v10 */
    /* JADX WARNING: type inference failed for: r3v11 */
    /* JADX WARNING: Multi-variable type inference failed */
    @android.annotation.TargetApi(19)
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String getPath(android.content.Context r8, android.net.Uri r9) {
        /*
            int r0 = android.os.Build.VERSION.SDK_INT
            r1 = 0
            r2 = 1
            r3 = 19
            if (r0 < r3) goto L_0x000a
            r0 = 1
            goto L_0x000b
        L_0x000a:
            r0 = 0
        L_0x000b:
            r3 = 0
            if (r0 == 0) goto L_0x00e8
            boolean r0 = android.provider.DocumentsContract.isDocumentUri(r8, r9)
            if (r0 == 0) goto L_0x00e8
            boolean r0 = isExternalStorageDocument(r9)
            if (r0 == 0) goto L_0x0049
            java.lang.String r8 = android.provider.DocumentsContract.getDocumentId(r9)
            java.lang.String r9 = ":"
            java.lang.String[] r8 = r8.split(r9)
            r9 = r8[r1]
            java.lang.String r0 = "primary"
            boolean r9 = r0.equalsIgnoreCase(r9)
            if (r9 == 0) goto L_0x0120
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            java.io.File r0 = android.os.Environment.getExternalStorageDirectory()
            r9.append(r0)
            java.lang.String r0 = "/"
            r9.append(r0)
            r8 = r8[r2]
            r9.append(r8)
            java.lang.String r8 = r9.toString()
            return r8
        L_0x0049:
            boolean r0 = isDownloadsDocument(r9)
            if (r0 == 0) goto L_0x00a9
            java.lang.String r0 = android.provider.DocumentsContract.getDocumentId(r9)
            if (r0 == 0) goto L_0x0063
            java.lang.String r2 = "raw:"
            boolean r2 = r0.startsWith(r2)
            if (r2 == 0) goto L_0x0063
            r8 = 4
            java.lang.String r8 = r0.substring(r8)
            return r8
        L_0x0063:
            java.lang.String r2 = "content://downloads/public_downloads"
            java.lang.String r4 = "content://downloads/my_downloads"
            java.lang.String r5 = "content://downloads/all_downloads"
            java.lang.String[] r2 = new java.lang.String[]{r2, r4, r5}
            int r4 = r2.length
        L_0x006e:
            if (r1 >= r4) goto L_0x008c
            r5 = r2[r1]
            android.net.Uri r5 = android.net.Uri.parse(r5)
            java.lang.Long r6 = java.lang.Long.valueOf(r0)
            long r6 = r6.longValue()
            android.net.Uri r5 = android.content.ContentUris.withAppendedId(r5, r6)
            java.lang.String r5 = getDataColumn(r8, r5, r3, r3)     // Catch:{ Exception -> 0x0089 }
            if (r5 == 0) goto L_0x0089
            return r5
        L_0x0089:
            int r1 = r1 + 1
            goto L_0x006e
        L_0x008c:
            java.lang.String r0 = getFileName(r8, r9)
            java.io.File r1 = getDocumentCacheDir(r8)
            java.io.File r1 = generateFileName(r0, r1)
            if (r1 == 0) goto L_0x00a1
            java.lang.String r3 = r1.getAbsolutePath()
            saveFileFromUri(r8, r9, r3)
        L_0x00a1:
            if (r0 == 0) goto L_0x00a8
            java.lang.String r8 = "FileName"
            android.util.Log.d(r8, r0)
        L_0x00a8:
            return r3
        L_0x00a9:
            boolean r0 = isMediaDocument(r9)
            if (r0 == 0) goto L_0x0120
            java.lang.String r9 = android.provider.DocumentsContract.getDocumentId(r9)
            java.lang.String r0 = ":"
            java.lang.String[] r9 = r9.split(r0)
            r0 = r9[r1]
            java.lang.String r4 = "image"
            boolean r4 = r4.equals(r0)
            if (r4 == 0) goto L_0x00c6
            android.net.Uri r3 = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            goto L_0x00db
        L_0x00c6:
            java.lang.String r4 = "video"
            boolean r4 = r4.equals(r0)
            if (r4 == 0) goto L_0x00d1
            android.net.Uri r3 = android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI
            goto L_0x00db
        L_0x00d1:
            java.lang.String r4 = "audio"
            boolean r0 = r4.equals(r0)
            if (r0 == 0) goto L_0x00db
            android.net.Uri r3 = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        L_0x00db:
            java.lang.String[] r0 = new java.lang.String[r2]
            r9 = r9[r2]
            r0[r1] = r9
            java.lang.String r9 = "_id=?"
            java.lang.String r8 = getDataColumn(r8, r3, r9, r0)
            return r8
        L_0x00e8:
            java.lang.String r0 = "content"
            java.lang.String r1 = r9.getScheme()
            boolean r0 = r0.equalsIgnoreCase(r1)
            if (r0 == 0) goto L_0x010f
            boolean r0 = isGooglePhotosUri(r9)
            if (r0 == 0) goto L_0x00ff
            java.lang.String r8 = r9.getLastPathSegment()
            return r8
        L_0x00ff:
            int r0 = android.os.Build.VERSION.SDK_INT
            r1 = 21
            if (r0 < r1) goto L_0x010a
            java.lang.String r8 = getFilePathForN(r9, r8)
            return r8
        L_0x010a:
            java.lang.String r8 = getDataColumn(r8, r9, r3, r3)
            return r8
        L_0x010f:
            java.lang.String r8 = "file"
            java.lang.String r0 = r9.getScheme()
            boolean r8 = r8.equalsIgnoreCase(r0)
            if (r8 == 0) goto L_0x0120
            java.lang.String r8 = r9.getPath()
            return r8
        L_0x0120:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: util.ImageFilePath.getPath(android.content.Context, android.net.Uri):java.lang.String");
    }

    private static String getFilePathForN(Uri uri, Context context) {
        Cursor query = context.getContentResolver().query(uri, (String[]) null, (String) null, (String[]) null, (String) null);
        int columnIndex = query.getColumnIndex("_display_name");
        int columnIndex2 = query.getColumnIndex("_size");
        query.moveToFirst();
        String string = query.getString(columnIndex);
        Long.toString(query.getLong(columnIndex2));
        File file = new File(context.getFilesDir(), string);
        try {
            InputStream openInputStream = context.getContentResolver().openInputStream(uri);
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            byte[] bArr = new byte[Math.min(openInputStream.available(), 1048576)];
            while (true) {
                int read = openInputStream.read(bArr);
                if (read == -1) {
                    break;
                }
                fileOutputStream.write(bArr, 0, read);
            }
            Log.e("ImagePath File Size", "Size " + file.length());
            openInputStream.close();
            fileOutputStream.close();
            Log.e("ImagePath File Path", "Path " + file.getPath());
            Log.e("ImagePath File Size", "Size " + file.length());
        } catch (Exception e) {
            Log.e("Exception", e.getMessage());
        }
        return file.getPath();
    }

    /* JADX WARNING: type inference failed for: r0v0 */
    /* JADX WARNING: type inference failed for: r0v1, types: [java.io.BufferedOutputStream] */
    /* JADX WARNING: type inference failed for: r0v2 */
    /* JADX WARNING: type inference failed for: r0v3, types: [java.io.InputStream] */
    /* JADX WARNING: type inference failed for: r0v4 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x0042 A[SYNTHETIC, Splitter:B:28:0x0042] */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x004a A[Catch:{ IOException -> 0x0046 }] */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x0057 A[SYNTHETIC, Splitter:B:39:0x0057] */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x005f A[Catch:{ IOException -> 0x005b }] */
    /* JADX WARNING: Removed duplicated region for block: B:49:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void saveFileFromUri(android.content.Context r3, android.net.Uri r4, java.lang.String r5) {
        /*
            r0 = 0
            android.content.ContentResolver r3 = r3.getContentResolver()     // Catch:{ IOException -> 0x003b, all -> 0x0038 }
            java.io.InputStream r3 = r3.openInputStream(r4)     // Catch:{ IOException -> 0x003b, all -> 0x0038 }
            java.io.BufferedOutputStream r4 = new java.io.BufferedOutputStream     // Catch:{ IOException -> 0x0034, all -> 0x0032 }
            java.io.FileOutputStream r1 = new java.io.FileOutputStream     // Catch:{ IOException -> 0x0034, all -> 0x0032 }
            r2 = 0
            r1.<init>(r5, r2)     // Catch:{ IOException -> 0x0034, all -> 0x0032 }
            r4.<init>(r1)     // Catch:{ IOException -> 0x0034, all -> 0x0032 }
            r5 = 1024(0x400, float:1.435E-42)
            byte[] r5 = new byte[r5]     // Catch:{ IOException -> 0x0030, all -> 0x002e }
            r3.read(r5)     // Catch:{ IOException -> 0x0030, all -> 0x002e }
        L_0x001b:
            r4.write(r5)     // Catch:{ IOException -> 0x0030, all -> 0x002e }
            int r0 = r3.read(r5)     // Catch:{ IOException -> 0x0030, all -> 0x002e }
            r1 = -1
            if (r0 != r1) goto L_0x001b
            if (r3 == 0) goto L_0x002a
            r3.close()     // Catch:{ IOException -> 0x0046 }
        L_0x002a:
            r4.close()     // Catch:{ IOException -> 0x0046 }
            goto L_0x0051
        L_0x002e:
            r5 = move-exception
            goto L_0x0054
        L_0x0030:
            r5 = move-exception
            goto L_0x0036
        L_0x0032:
            r5 = move-exception
            goto L_0x0055
        L_0x0034:
            r5 = move-exception
            r4 = r0
        L_0x0036:
            r0 = r3
            goto L_0x003d
        L_0x0038:
            r5 = move-exception
            r3 = r0
            goto L_0x0055
        L_0x003b:
            r5 = move-exception
            r4 = r0
        L_0x003d:
            r5.printStackTrace()     // Catch:{ all -> 0x0052 }
            if (r0 == 0) goto L_0x0048
            r0.close()     // Catch:{ IOException -> 0x0046 }
            goto L_0x0048
        L_0x0046:
            r3 = move-exception
            goto L_0x004e
        L_0x0048:
            if (r4 == 0) goto L_0x0051
            r4.close()     // Catch:{ IOException -> 0x0046 }
            goto L_0x0051
        L_0x004e:
            r3.printStackTrace()
        L_0x0051:
            return
        L_0x0052:
            r5 = move-exception
            r3 = r0
        L_0x0054:
            r0 = r4
        L_0x0055:
            if (r3 == 0) goto L_0x005d
            r3.close()     // Catch:{ IOException -> 0x005b }
            goto L_0x005d
        L_0x005b:
            r3 = move-exception
            goto L_0x0063
        L_0x005d:
            if (r0 == 0) goto L_0x0066
            r0.close()     // Catch:{ IOException -> 0x005b }
            goto L_0x0066
        L_0x0063:
            r3.printStackTrace()
        L_0x0066:
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: util.ImageFilePath.saveFileFromUri(android.content.Context, android.net.Uri, java.lang.String):void");
    }

    public static File getDocumentCacheDir(@NonNull Context context) {
        File file = new File(context.getCacheDir(), DOCUMENTS_DIR);
        if (!file.exists()) {
            file.mkdirs();
        }
        logDir(context.getCacheDir());
        logDir(file);
        return file;
    }

    @Nullable
    public static File generateFileName(@Nullable String str, File file) {
        if (str == null) {
            return null;
        }
        File file2 = new File(file, str);
        if (file2.exists()) {
            String str2 = "";
            int lastIndexOf = str.lastIndexOf(46);
            int i = 0;
            if (lastIndexOf > 0) {
                String substring = str.substring(0, lastIndexOf);
                str2 = str.substring(lastIndexOf);
                str = substring;
            }
            while (file2.exists()) {
                i++;
                file2 = new File(file, str + '(' + i + ')' + str2);
            }
        }
        try {
            if (!file2.createNewFile()) {
                return null;
            }
            logDir(file);
            return file2;
        } catch (IOException e) {
            Log.w(TAG, e);
            return null;
        }
    }

    public static String getFileName(@NonNull Context context, Uri uri) {
        if (context.getContentResolver().getType(uri) != null || context == null) {
            Cursor query = context.getContentResolver().query(uri, (String[]) null, (String) null, (String[]) null, (String) null);
            if (query == null) {
                return null;
            }
            int columnIndex = query.getColumnIndex("_display_name");
            query.moveToFirst();
            String string = query.getString(columnIndex);
            query.close();
            return string;
        }
        String path = getPath(context, uri);
        if (path == null) {
            return getName(uri.toString());
        }
        return new File(path).getName();
    }

    public static String getName(String str) {
        if (str == null) {
            return null;
        }
        return str.substring(str.lastIndexOf(47) + 1);
    }

    /* JADX WARNING: Removed duplicated region for block: B:18:0x0037  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String getDataColumn(android.content.Context r7, android.net.Uri r8, java.lang.String r9, java.lang.String[] r10) {
        /*
            java.lang.String r0 = "_data"
            java.lang.String[] r3 = new java.lang.String[]{r0}
            r0 = 0
            android.content.ContentResolver r1 = r7.getContentResolver()     // Catch:{ all -> 0x0033 }
            r6 = 0
            r2 = r8
            r4 = r9
            r5 = r10
            android.database.Cursor r7 = r1.query(r2, r3, r4, r5, r6)     // Catch:{ all -> 0x0033 }
            if (r7 == 0) goto L_0x002d
            boolean r8 = r7.moveToFirst()     // Catch:{ all -> 0x002b }
            if (r8 == 0) goto L_0x002d
            java.lang.String r8 = "_data"
            int r8 = r7.getColumnIndexOrThrow(r8)     // Catch:{ all -> 0x002b }
            java.lang.String r8 = r7.getString(r8)     // Catch:{ all -> 0x002b }
            if (r7 == 0) goto L_0x002a
            r7.close()
        L_0x002a:
            return r8
        L_0x002b:
            r8 = move-exception
            goto L_0x0035
        L_0x002d:
            if (r7 == 0) goto L_0x0032
            r7.close()
        L_0x0032:
            return r0
        L_0x0033:
            r8 = move-exception
            r7 = r0
        L_0x0035:
            if (r7 == 0) goto L_0x003a
            r7.close()
        L_0x003a:
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: util.ImageFilePath.getDataColumn(android.content.Context, android.net.Uri, java.lang.String, java.lang.String[]):java.lang.String");
    }

    public static String getFilePathFromURI(Context context, Uri uri) {
        String fileName = getFileName(uri);
        if (TextUtils.isEmpty(fileName)) {
            return null;
        }
        File file = new File(Utils.getCacheDirectory(context, Constants.CACHE_DIRECTORY).getPath() + File.separator + fileName);
        copy(context, uri, file);
        return file.getAbsolutePath();
    }

    public static String getFileName(Uri uri) {
        if (uri == null) {
            return null;
        }
        String substring = uri.getPath().substring(uri.getPath().lastIndexOf("/") + 1);
        return substring.indexOf(".") > 0 ? substring.substring(0, substring.lastIndexOf(".")) : substring;
    }

    public static void copy(Context context, Uri uri, File file) {
        try {
            InputStream openInputStream = context.getContentResolver().openInputStream(uri);
            if (openInputStream != null) {
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                IOUtils.copyStream(openInputStream, fileOutputStream);
                openInputStream.close();
                fileOutputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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
}

