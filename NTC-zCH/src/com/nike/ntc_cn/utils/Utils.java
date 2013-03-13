package com.nike.ntc_cn.utils;

import java.io.IOException;
import java.io.InputStream;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;

import com.nike.ntc_cn.NtcApplication;

//各种工具
public  class Utils {
	
	//获取压缩包中的图片
    public static Bitmap createBitmap(int i) {
        Bitmap result = null;
        try {
            result = new BitmapDrawable(NtcApplication.getInstance().zipFile.getInputStream(NtcApplication.getInstance().zipfileList
                    .get(i))).getBitmap();


        } catch (IOException e) {
            e.printStackTrace();
        }
        
        
        return result;
    }

	public static Bitmap createBitmapByName(String name) {
		
        for (int i=0;i<NtcApplication.getInstance().zipfileList.size();i++) {
        	if (NtcApplication.getInstance().zipfileList.get(i).getName().equals("images/"+name+".jpg")){
        		return createBitmap(i);
        	}
        }
		
		return null;
	}
	
	public static InputStream getInputStreamByName(String name) {
		
        for (int i=0;i<NtcApplication.getInstance().zipfileList.size();i++) {
        	if (NtcApplication.getInstance().zipfileList.get(i).getName().equals(name)){
        		try {
					return NtcApplication.getInstance().zipFile.getInputStream(NtcApplication.getInstance().zipfileList.get(i));
				} catch (IOException e) {
					e.printStackTrace();
				}
        	}
        }
        return null;
	}
}
