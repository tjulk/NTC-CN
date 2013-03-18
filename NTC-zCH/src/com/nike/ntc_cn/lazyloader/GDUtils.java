package com.nike.ntc_cn.lazyloader;

import java.util.concurrent.ExecutorService;

import android.content.Context;

import com.nike.ntc_cn.NtcApplication;

/**
 * Class that provides several utility methods related to palyer.
 * 
 */
public class GDUtils {

    private GDUtils() {
    }
 
    public static NtcApplication getApplication(Context context) {
        return (NtcApplication) context.getApplicationContext();
    }

 
    public static ImageCache getImageCache(Context context) {
        return getApplication(context).getImageCache();
    }

    public static ExecutorService getExecutor(Context context) {
        return getApplication(context).getExecutor();
    }

}
