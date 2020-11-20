package util;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import androidx.collection.LruCache;
import com.instagram.igdiskcache.EditorOutputStream;
import com.instagram.igdiskcache.IgDiskCache;
import com.instagram.igdiskcache.OptionalStream;
import helper.Constants;
import java.io.File;
import java.util.List;

public class BitmapCache {
    private static final int DEFAULT_DISK_CACHE_SIZE = 104857600;
    private static final int DEFAULT_DISK_CACHE_SIZE_PERCENT = 10;
    private static final int DEFAULT_MEM_CACHE_CAP = 25;
    private static final String DISK_CACHE_DIR = Constants.CACHE_DIRECTORY;
    private static final String TAG = "BitmapCache";
    private Context mContext;
    private IgDiskCache mDiskCache;
    /* access modifiers changed from: private */
    public LruCache<String, Bitmap> mMemoryCache = new LruCache<>(25);

    public BitmapCache(Context context) {
        this.mContext = context;
    }

    public void clearOriginal() {
        new Thread(new Runnable() {
            public void run() {
                BitmapCache.this.getDiskCache().remove(Constants.ORIGINAL_IMAGE);
            }
        }).start();
    }

    public void clearMemory(final List<String> list) {
        new Thread(new Runnable() {
            public void run() {
                for (int i = 0; i < list.size(); i++) {
                    BitmapCache.this.getDiskCache().remove((String) list.get(i));
                }
                BitmapCache.this.mMemoryCache.evictAll();
            }
        }).start();
    }

    public void addBitmapToCache(String str, Bitmap bitmap) {
        Log.d("BitmapCache Size", String.valueOf(getDiskCache().getMaxSizeInBytes()));
        if (str != null && bitmap != null) {
            if (getBitmapFromMemCache(str) == null) {
                this.mMemoryCache.put(str, bitmap);
            }
            if (!getDiskCache().has(str)) {
                Log.d(str, "has !");
                OptionalStream<EditorOutputStream> edit = getDiskCache().edit(str);
                if (edit.isPresent()) {
                    try {
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, edit.get());
                        edit.get().commit();
                    } catch (Exception e) {
                        String str2 = TAG;
                        Log.e(str2, "addBitmapToCache - " + e);
                    } catch (Throwable th) {
                        edit.get().abortUnlessCommitted();
                        throw th;
                    }
                    edit.get().abortUnlessCommitted();
                }
            } else {
                Log.d(str, "has");
                getDiskCache().remove(str);
                OptionalStream<EditorOutputStream> edit2 = getDiskCache().edit(str);
                if (edit2.isPresent()) {
                    Log.d("output", "present");
                    try {
                        bitmap.compress(Bitmap.CompressFormat.PNG, 70, edit2.get());
                        Log.d("writing", "bitmap");
                        edit2.get().commit();
                    } catch (Exception e2) {
                        String str3 = TAG;
                        Log.e(str3, "addBitmapToCache - " + e2);
                    } catch (Throwable th2) {
                        edit2.get().abortUnlessCommitted();
                        throw th2;
                    }
                    edit2.get().abortUnlessCommitted();
                }
            }
            Log.d("Bitmap Added", str);
        }
    }

    public Bitmap getBitmapFromMemCache(String str) {
        return this.mMemoryCache.get(str);
    }

    public Bitmap getCacheBitmap(String str) {
        if (getBitmapFromMemCache(str) != null) {
            Log.d(str, "from ram");
            return this.mMemoryCache.get(str);
        } else if (getBitmapFromDiskCache(str) != null) {
            Log.d(str, "from disk");
            return getBitmapFromDiskCache(str);
        } else {
            Log.d("Else", "null");
            return null;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:15:0x005d  */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x0065  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public android.graphics.Bitmap getBitmapFromDiskCache(java.lang.String r5) {
        /*
            r4 = this;
            com.instagram.igdiskcache.IgDiskCache r0 = r4.getDiskCache()
            com.instagram.igdiskcache.OptionalStream r5 = r0.get(r5)
            boolean r0 = r5.isPresent()
            if (r0 == 0) goto L_0x005a
            java.lang.String r0 = "input"
            java.lang.String r1 = "present"
            android.util.Log.d(r0, r1)
            java.lang.Object r0 = r5.get()     // Catch:{ IOException -> 0x002f }
            com.instagram.igdiskcache.SnapshotInputStream r0 = (com.instagram.igdiskcache.SnapshotInputStream) r0     // Catch:{ IOException -> 0x002f }
            java.io.FileDescriptor r0 = r0.getFD()     // Catch:{ IOException -> 0x002f }
            android.graphics.Bitmap r0 = android.graphics.BitmapFactory.decodeFileDescriptor(r0)     // Catch:{ IOException -> 0x002f }
            java.lang.Object r5 = r5.get()
            java.io.Closeable r5 = (java.io.Closeable) r5
            util.Utils.closeQuietly(r5)
            goto L_0x005b
        L_0x002d:
            r0 = move-exception
            goto L_0x0050
        L_0x002f:
            r0 = move-exception
            java.lang.String r1 = TAG     // Catch:{ all -> 0x002d }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x002d }
            r2.<init>()     // Catch:{ all -> 0x002d }
            java.lang.String r3 = "getBitmapFromDiskCache - "
            r2.append(r3)     // Catch:{ all -> 0x002d }
            r2.append(r0)     // Catch:{ all -> 0x002d }
            java.lang.String r0 = r2.toString()     // Catch:{ all -> 0x002d }
            android.util.Log.e(r1, r0)     // Catch:{ all -> 0x002d }
            java.lang.Object r5 = r5.get()
            java.io.Closeable r5 = (java.io.Closeable) r5
            util.Utils.closeQuietly(r5)
            goto L_0x005a
        L_0x0050:
            java.lang.Object r5 = r5.get()
            java.io.Closeable r5 = (java.io.Closeable) r5
            util.Utils.closeQuietly(r5)
            throw r0
        L_0x005a:
            r0 = 0
        L_0x005b:
            if (r0 == 0) goto L_0x0065
            java.lang.String r5 = "Fetched"
            java.lang.String r1 = "Bitmap"
            android.util.Log.d(r5, r1)
            goto L_0x006c
        L_0x0065:
            java.lang.String r5 = "Fetching"
            java.lang.String r1 = "Failed"
            android.util.Log.d(r5, r1)
        L_0x006c:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: util.BitmapCache.getBitmapFromDiskCache(java.lang.String):android.graphics.Bitmap");
    }

    public void flush() {
        getDiskCache().flush();
    }

    public void close() {
        getDiskCache().close();
    }

    /* access modifiers changed from: private */
    public IgDiskCache getDiskCache() {
        Log.d("enter", "igdiskcache");
        if (this.mDiskCache == null) {
            File cacheDirectory = Utils.getCacheDirectory(this.mContext, DISK_CACHE_DIR);
            Log.d("CacheDir", Utils.getCacheDirectory(this.mContext, DISK_CACHE_DIR).getPath());
            this.mDiskCache = new IgDiskCache(cacheDirectory, Utils.getCacheSizeInBytes(cacheDirectory, 10.0f, 104857600));
        }
        return this.mDiskCache;
    }
}