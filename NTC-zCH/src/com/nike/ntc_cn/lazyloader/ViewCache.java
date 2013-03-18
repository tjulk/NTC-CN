package com.nike.ntc_cn.lazyloader;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nike.ntc_cn.R;

public class ViewCache {

	    private View baseView;
	    private TextView textView;
	    private ImageView imageView;

	    public ViewCache(View baseView) {
	        this.baseView = baseView;
	    }

	    public TextView getTextView() {
	        if (textView == null) {
	            textView = (TextView) baseView.findViewById(R.id.itemName);
	        }
	        return textView;
	    }

	    public ImageView getImageView() {
	        if (imageView == null) {
	            imageView = (ImageView) baseView.findViewById(R.id.itemOrder);
	        }
	        return imageView;
	    }

}