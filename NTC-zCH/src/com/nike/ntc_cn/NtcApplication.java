package com.nike.ntc_cn;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import android.app.Application;

import com.nike.ntc_cn.db.InitDataControl;
import com.nike.ntc_cn.lazyloader.ImageCache;
import com.nike.ntc_cn.utils.Utils;

public class NtcApplication extends Application {
	
	private static final String TAG = "NtcApplication";
	
	public static final String zipPath = "mnt/sdcard/Android/data/com.nike.ntc/files/archives/standard.main.zip";
	
	public static final String PREFS_NAME = "ntc-zch.pref";
	
	public static final String TAG_IS_INIT_DATABASES = "is_init_databases";
	
	public static NtcApplication _instance;
	
	//zip压缩文件流
	public ZipFile zipFile = null;
	//压缩文件夹内所有文件
	public List<ZipEntry> zipfileList;

	@Override
	public void onCreate() {
		super.onCreate();
		_instance = this;
		
        mLowMemoryListeners = new ArrayList<WeakReference<OnLowMemoryListener>>();
        
		//getZipFileFromSDcard();
		
        InitDataControl.getInstance(this).init();
        
        Utils.createAPPFolder();
	}

	public static NtcApplication getInstance() {
		return _instance;
	}
	
	//获取压缩包
	//private void getZipFileFromSDcard() {
	//        try {
	//        	zipFile = new ZipFile(zipPath);
	//			Enumeration<ZipEntry> en=(Enumeration<ZipEntry>) zipFile.entries();
	//			
	//		      ZipEntry entry = null;
	//		      zipfileList = new ArrayList<ZipEntry>();
	//		        while (en.hasMoreElements()) {
	//		            entry = en.nextElement();
	//		            if (!entry.isDirectory()) {
	//		                // 如果文件不是目录，则添加到列表中
	//		                zipfileList.add(entry);
	//		            }
	//		        }
	//		        Log.d(TAG,"total files "+ zipfileList.size());
	//			
	//		} catch (IOException e) {
	//			e.printStackTrace();
	//		}
	//}
	
    /******************************** 缓存相关 ****************************************/
	
    private ImageCache mImageCache;
    
    private ExecutorService mExecutorService;
    
    private ArrayList<WeakReference<OnLowMemoryListener>> mLowMemoryListeners;
    
    private static final int CORE_POOL_SIZE = 5;

    private static final ThreadFactory sThreadFactory = new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger(1);

        public Thread newThread(Runnable r) {
            return new Thread(r, "GreenDroid thread #" + mCount.getAndIncrement());
        }
    };
    
    public ImageCache getImageCache() {
        if (mImageCache == null) {
            mImageCache = new ImageCache(this);
        }
        return mImageCache;
    }
    
    public ExecutorService getExecutor() {
        if (mExecutorService == null) {
            mExecutorService = Executors.newFixedThreadPool(CORE_POOL_SIZE, sThreadFactory);
        }
        return mExecutorService;
    }

    public static interface OnLowMemoryListener {
        
        /**
         * Callback to be invoked when the system needs memory.
         */
        public void onLowMemoryReceived();
    }
    
    
    /**
     * Add a new listener to registered {@link OnLowMemoryListener}.
     * 
     * @param listener The listener to unregister
     * @see OnLowMemoryListener
     */
    public void registerOnLowMemoryListener(OnLowMemoryListener listener) {
        if (listener != null) {
            mLowMemoryListeners.add(new WeakReference<OnLowMemoryListener>(listener));
        }
    }

    /**
     * Remove a previously registered listener
     * 
     * @param listener The listener to unregister
     * @see OnLowMemoryListener
     */
    public void unregisterOnLowMemoryListener(OnLowMemoryListener listener) {
        if (listener != null) {
            int i = 0;
            while (i < mLowMemoryListeners.size()) {
                final OnLowMemoryListener l = mLowMemoryListeners.get(i).get();
                if (l == null || l == listener) {
                    mLowMemoryListeners.remove(i);
                } else {
                    i++;
                }
            }
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        int i = 0;
        while (i < mLowMemoryListeners.size()) {
            final OnLowMemoryListener listener = mLowMemoryListeners.get(i).get();
            if (listener == null) {
                mLowMemoryListeners.remove(i);
            } else {
                listener.onLowMemoryReceived();
                i++;
            }
        }
    }
}
