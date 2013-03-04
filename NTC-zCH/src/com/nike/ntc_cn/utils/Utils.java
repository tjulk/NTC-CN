package com.nike.ntc_cn.utils;

import java.io.IOException;

import com.nike.ntc_cn.NtcApplication;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;

//各种工具
public class Utils {
	
	//获取压缩包中的图片
    public Bitmap createBitmap() {
        Bitmap result = null;
        try {
            result = new BitmapDrawable(NtcApplication.getInstance().zipFile.getInputStream(NtcApplication.getInstance().zipfileList
                    .get(770))).getBitmap();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
