 
package com.nike.ntc_cn.lazyloader;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.nike.ntc_cn.R;
import com.nike.ntc_cn.lazyloader.ImageRequest.ImageRequestCallback;
import com.nike.ntc_cn.utils.Utils;

/**
 * @ClassName: AsyncImageView 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author LEIKANG 
 * @date 2013-3-18 下午1:13:58
 */
public class AsyncImageView extends ImageView implements ImageRequestCallback {
	
    public static interface OnImageViewLoadListener {

        void onLoadingStarted(AsyncImageView imageView);

        void onLoadingEnded(AsyncImageView imageView, Bitmap image);

        void onLoadingFailed(AsyncImageView imageView, Throwable throwable);
    }

    private static final int IMAGE_SOURCE_UNKNOWN = -1;
    private static final int IMAGE_SOURCE_RESOURCE = 0;
    private static final int IMAGE_SOURCE_DRAWABLE = 1;
    private static final int IMAGE_SOURCE_BITMAP = 2;

    private int mImageSource;
    private Bitmap mDefaultBitmap;
    private Drawable mDefaultDrawable;
    private int mDefaultResId;

    private String mUrl;
    
    private ImageRequest mRequest;
    private boolean mPaused;

    private Bitmap mBitmap;
    private OnImageViewLoadListener mOnImageViewLoadListener;
    private ImageProcessor mImageProcessor;
    private BitmapFactory.Options mOptions;
    
    private Context mContext;
    

    public AsyncImageView(Context context) {
        this(context, null);
        mContext = context;
    }

    public AsyncImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AsyncImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        initializeDefaultValues();

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.AsyncImageView, defStyle, 0);

        Drawable d = a.getDrawable(R.styleable.AsyncImageView_defaultSrc);
        if (d != null) {
            setDefaultImageDrawable(d);
        }

        final int inDensity = a.getInt(R.styleable.AsyncImageView_inDensity, -1);
        if (inDensity != -1) {
            setInDensity(inDensity);
        }

        setUrl(a.getString(R.styleable.AsyncImageView_url));

        a.recycle();
    }

    private void initializeDefaultValues() {
        mImageSource = IMAGE_SOURCE_UNKNOWN;
        mPaused = false;
    }

    public boolean isLoading() {
        return mRequest != null;
    }

    public boolean isLoaded() {
        return mRequest == null && mBitmap != null;
    }

    public void setPaused(boolean paused) {
        if (mPaused != paused) {
            mPaused = paused;
            if (!paused) {
                reload();
            }
        }
    }

    public void setInDensity(int inDensity) {
        if (mOptions == null) {
            mOptions = new BitmapFactory.Options();
            mOptions.inDither = true;
            mOptions.inScaled = true;
            mOptions.inTargetDensity = getContext().getResources().getDisplayMetrics().densityDpi;
        }

        mOptions.inDensity = inDensity;
    }

    public void setOptions(BitmapFactory.Options options) {
        mOptions = options;
    }

    public void reload() {
        reload(false);
    }

    public void reload(boolean force) {
        if (mRequest == null && mUrl != null) {

            mBitmap = null;
            
            if (!force) {
                mBitmap = GDUtils.getImageCache(getContext()).get(Utils.getImageNameFromUrl(mUrl));
            }

            if (mBitmap != null) {
                setImageBitmap(mBitmap);
                return;
            }

            setDefaultImage();
            mRequest = new ImageRequest(mUrl, this, mImageProcessor, mOptions);
            mRequest.load(getContext());
        }
    }

    public void stopLoading() {
        if (mRequest != null) {
            mRequest.cancel();
            mRequest = null;
        }
    }
    
 
    public void setOnImageViewLoadListener(OnImageViewLoadListener listener) {
        mOnImageViewLoadListener = listener;
    }

    public void setUrl(String exerciseUrl) {
    	
    	final String temurl = Utils.fitUrlFromImageName(exerciseUrl);

        if (mBitmap != null && temurl != null && temurl.equals(mUrl)) {
            return;
        }

        mUrl = temurl;
        
        stopLoading();
        

        if (TextUtils.isEmpty(mUrl)) {
            mBitmap = null;
            setDefaultImage();
        } else {
            if (!mPaused) {
                reload();
            } else {
            	
                mBitmap = GDUtils.getImageCache(getContext()).get(Utils.getImageNameFromUrl(mUrl));
                mBitmap = Bitmap.createBitmap(mBitmap, 0, 0, 10, 10);
                if (mBitmap != null) {
                    setImageBitmap(mBitmap);
                    return;
                } else {
                    setDefaultImage();
                }
            }
        }
    }

    public void setDefaultImageBitmap(Bitmap bitmap) {
        mImageSource = IMAGE_SOURCE_BITMAP;
        mDefaultBitmap = bitmap;
        setDefaultImage();
    }

    public void setDefaultImageDrawable(Drawable drawable) {
        mImageSource = IMAGE_SOURCE_DRAWABLE;
        mDefaultDrawable = drawable;
        setDefaultImage();
    }

    public void setDefaultImageResource(int resId) {
        mImageSource = IMAGE_SOURCE_RESOURCE;
        mDefaultResId = resId;
        setDefaultImage();
    }

    public void setImageProcessor(ImageProcessor imageProcessor) {
        mImageProcessor = imageProcessor;
    }

    private void setDefaultImage() {
        if (mBitmap == null) {
            switch (mImageSource) {
                case IMAGE_SOURCE_BITMAP:
                    setImageBitmap(mDefaultBitmap);
                    break;
                case IMAGE_SOURCE_DRAWABLE:
                    setImageDrawable(mDefaultDrawable);
                    break;
                case IMAGE_SOURCE_RESOURCE:
                    setImageResource(mDefaultResId);
                    break;
                default:
                    setImageDrawable(null);
                    break;
            }
        }
    }

    static class SavedState extends BaseSavedState {
        String url;

        SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            super(in);
            url = in.readString();
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeString(url);
        }

        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() {
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState ss = new SavedState(superState);

        ss.url = mUrl;

        return ss;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());

        setUrl(ss.url);
    }

    public void onImageRequestStarted(ImageRequest request) {
        if (mOnImageViewLoadListener != null) {
            mOnImageViewLoadListener.onLoadingStarted(this);
        }
    }

    public void onImageRequestFailed(ImageRequest request, Throwable throwable) {
        mRequest = null;
        if (mOnImageViewLoadListener != null) {
            mOnImageViewLoadListener.onLoadingFailed(this, throwable);
        }
    }

    public void onImageRequestEnded(ImageRequest request, Bitmap image) {
        mBitmap = image;
        setImageBitmap(image);
        if (mOnImageViewLoadListener != null) {
            mOnImageViewLoadListener.onLoadingEnded(this, image);
        }
        mRequest = null;
    }

    public void onImageRequestCancelled(ImageRequest request) {
        mRequest = null;
        if (mOnImageViewLoadListener != null) {
            mOnImageViewLoadListener.onLoadingFailed(this, null);
        }
    }
}
