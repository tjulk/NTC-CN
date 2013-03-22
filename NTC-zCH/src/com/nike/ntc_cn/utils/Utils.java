package com.nike.ntc_cn.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import android.util.Log;

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
	
	public static void Unzip(String zipFile, String targetDir) {
		int BUFFER = 4096; // 这里缓冲区我们使用4KB,
		String strEntry; // 保存每个zip的条目名称
		try {
			BufferedOutputStream dest = null; // 缓冲输出流
			FileInputStream fis = new FileInputStream(zipFile);
			ZipInputStream zis = new ZipInputStream(
					new BufferedInputStream(fis));
			ZipEntry entry; // 每个zip条目的实例
			while ((entry = zis.getNextEntry()) != null) {
				try {
					Log.i("Unzip： ", "=" + entry);
					int count;
					byte data[] = new byte[BUFFER];
					strEntry = entry.getName();
					File entryFile = new File(targetDir + strEntry);
					File entryDir = new File(entryFile.getParent());
					if (!entryDir.exists()) {
						entryDir.mkdirs();
					}
					FileOutputStream fos = new FileOutputStream(entryFile);
					dest = new BufferedOutputStream(fos, BUFFER);
					while ((count = zis.read(data, 0, BUFFER)) != -1) {
						dest.write(data, 0, count);
					}
					dest.flush();
					dest.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
			zis.close();
		} catch (Exception cwj) {
			cwj.printStackTrace();
		}
	}
	
}
