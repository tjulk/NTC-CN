 
package com.nike.ntc_cn.lazyloader;

import java.lang.ref.SoftReference;
import java.util.HashMap;

import android.content.Context;
import android.graphics.Bitmap;

import com.nike.ntc_cn.NtcApplication.OnLowMemoryListener;

public class ImageCache implements OnLowMemoryListener {

    private final HashMap<String, SoftReference<Bitmap>> mSoftCache;

    public ImageCache(Context context) {
        mSoftCache = new HashMap<String, SoftReference<Bitmap>>();
        GDUtils.getApplication(context).registerOnLowMemoryListener(this);
    }

    public static ImageCache from(Context context) {
        return GDUtils.getImageCache(context);
    }

    public Bitmap get(String url) {
        final SoftReference<Bitmap> ref = mSoftCache.get(url);
        if (ref == null) {
            return null;
        }

        final Bitmap bitmap = ref.get();
        if (bitmap == null) {
            mSoftCache.remove(url);
        }

        return bitmap;
    }

    public void put(String url, Bitmap bitmap) {
        mSoftCache.put(url, new SoftReference<Bitmap>(bitmap));
    }

    public void flush() {
        mSoftCache.clear();
    }

    public void onLowMemoryReceived() {
        flush();
    }
}
