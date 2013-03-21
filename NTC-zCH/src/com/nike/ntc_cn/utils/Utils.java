package com.nike.ntc_cn.utils;

import java.io.File;

//各种工具
public  class Utils {
	
	private static final String rootPath = android.os.Environment.getExternalStorageDirectory().getAbsolutePath() + "/.ntc/" ;
	
	private static final String rootUrl = "https://github.com/tjulk/NTC-CN/blob/master/Resourse/";
	
	public static final String audioPathDir = rootPath + "audio/";
	
	public static final String videoPathDir = rootPath +"videos/";
	
	public static final  String imagePathDir = rootPath +"images/";
	
	//===================================图片资源位置相关 ===============================================
	public static String fitUrlFromImageName(String imageName) {
		
		if (imageName != null)
			return  rootUrl + "images/" + imageName + ".jpg?raw=true";
		else 
			return null;
	}
	
	public static String getImageNameFromUrl(String url) {
		return url.substring(url.lastIndexOf("/")+1, url.lastIndexOf("?raw=true"));
	}
	
	public static String getImageSDCardPathFromName(String nameWithEnd) {
		return imagePathDir + nameWithEnd;
	}
	
	public static String getImageSDCardPathFromUrl(String url) {
		return imagePathDir + getImageNameFromUrl(url);
	}
	
	//===================================音频资源位置相关 ===============================================
	public static String fitUrlFromAudioName(String name) {
		
		if (name != null)
			return  rootUrl + "audio/" + name + ".ogg?raw=true";
		else 
			return null;
	}
	
	public static String getAudioNameFromUrl(String url) {
		return url.substring(url.lastIndexOf("/")+1, url.lastIndexOf("?raw=true"));
	}
	
	public static String getAudioSDCardPathFromName(String nameWithoutEnd) {
		return audioPathDir + nameWithoutEnd + ".ogg";
	}
	
	public static String getAudioSDCardPathFromUrl(String url) {
		return audioPathDir + getImageNameFromUrl(url);
	}
	
	//===================================音频资源位置相关 ===============================================
	public static String fitUrlFromVideoName(String name) {
		
		if (name != null)
			return  rootUrl + "videos/" + name + ".m4v?raw=true";
		else 
			return null;
	}
	
	public static String getVideoNameFromUrl(String url) {
		return url.substring(url.lastIndexOf("/")+1, url.lastIndexOf("?raw=true"));
	}
	
	public static String getVideoSDCardPathFromName(String nameWithoutEnd) {
		return videoPathDir + nameWithoutEnd + ".m4v";
	}
	
	public static String getVideoSDCardPathFromUrl(String url) {
		return videoPathDir + getImageNameFromUrl(url);
	}

	public static void createAPPFolder() {
		File file = new File(Utils.imagePathDir);
		if (!file.exists()) {
			file.mkdirs();
		}
		
		file = new File(Utils.videoPathDir);
		if (!file.exists()) {
			file.mkdirs();
		}
		
		file = new File(Utils.audioPathDir);
		if (!file.exists()) {
			file.mkdirs();
		}
	}
	
	
}
