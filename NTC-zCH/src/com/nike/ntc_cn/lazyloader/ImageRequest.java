package com.nike.ntc_cn.lazyloader;

import java.util.concurrent.Future;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.nike.ntc_cn.lazyloader.ImageLoader.ImageLoaderCallback;

public class ImageRequest {

    public static interface ImageRequestCallback {

        void onImageRequestStarted(ImageRequest request);

        void onImageRequestFailed(ImageRequest request, Throwable throwable);

        void onImageRequestEnded(ImageRequest request, Bitmap image);

        void onImageRequestCancelled(ImageRequest request);
    }

    private static ImageLoader sImageLoader;

    private Future<?> mFuture;
    private String mUrl;
    private ImageRequestCallback mCallback;
    private ImageProcessor mBitmapProcessor;
    private BitmapFactory.Options mOptions;

    public ImageRequest(String url, ImageRequestCallback callback) {
        this(url, callback, null);
    }

    public ImageRequest( String url, ImageRequestCallback callback, ImageProcessor bitmapProcessor) {
        this(url, callback, bitmapProcessor, null);
    }

    public ImageRequest( String url, ImageRequestCallback callback, ImageProcessor bitmapProcessor, BitmapFactory.Options options) {
        mUrl = url;
        mCallback = callback;
        mBitmapProcessor = bitmapProcessor;
        mOptions = options;
    }

    public void setImageRequestCallback(ImageRequestCallback callback) {
        mCallback = callback;
    }

    public String getUrl() {
        return mUrl;
    }

    public void load(Context context) {
        if (mFuture == null) {
            if (sImageLoader == null) {
                sImageLoader = new ImageLoader(context);
            }
            mFuture = sImageLoader.loadImage(mUrl, new InnerCallback(), mBitmapProcessor, mOptions);
        }
    }

    public void cancel() {
        if (!isCancelled()) {
            mFuture.cancel(false);
            if (mCallback != null) {
                mCallback.onImageRequestCancelled(this);
            }
        }
    }

    public final boolean isCancelled() {
        return mFuture.isCancelled();
    }

    private class InnerCallback implements ImageLoaderCallback {

        public void onImageLoadingStarted(ImageLoader loader) {
            if (mCallback != null) {
                mCallback.onImageRequestStarted(ImageRequest.this);
            }
        }

        public void onImageLoadingEnded(ImageLoader loader, Bitmap bitmap) {
            if (mCallback != null && !isCancelled()) {
                mCallback.onImageRequestEnded(ImageRequest.this, bitmap);
            }
            mFuture = null;
        }

        public void onImageLoadingFailed(ImageLoader loader, Throwable exception) {
            if (mCallback != null && !isCancelled()) {
                mCallback.onImageRequestFailed(ImageRequest.this, exception);
            }
            mFuture = null;
        }
    }

}
