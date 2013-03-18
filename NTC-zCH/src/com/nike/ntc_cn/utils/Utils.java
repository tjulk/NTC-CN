package com.nike.ntc_cn.utils;

import java.io.IOException;
import java.io.InputStream;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Environment;

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
	
	public static final  String imagePathDir =   android.os.Environment
			   .getExternalStorageDirectory()
			   .getAbsolutePath() +"/ntc/images/";
	
	public static String fitUrlFromImageName(String imageName) {
		
		if (imageName != null)
			return  "https://github.com/tjulk/NTC-CN/blob/master/Resourse/images/" + imageName + ".jpg?raw=true";
		else 
			return null;
	}
	
	public static String getImageNameFromUrl(String url) {
		return url.substring(url.lastIndexOf("/")+1, url.lastIndexOf("?raw=true"));
	}
	
	public static String getImageSDCardPathFromName(String name) {
		return imagePathDir + name;
	}
	
	public static String getImageSDCardPathFromUrl(String url) {
		return imagePathDir + getImageNameFromUrl(url);
	}
	
}
