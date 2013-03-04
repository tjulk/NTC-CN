package com.nike.ntc_cn;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import android.app.Application;
import android.util.Log;

public class NtcApplication extends Application {
	
	private static final String TAG = "NtcApplication";
	
	public static final String zipPath = "mnt/sdcard/Android/data/com.nike.ntc/files/archives/standard.main.zip";
	
	public static NtcApplication _instance;
	
	//zip压缩文件流
	public ZipFile zipFile = null;
	//压缩文件夹内所有文件
	public List<ZipEntry> zipfileList;

	@Override
	public void onCreate() {
		super.onCreate();
		_instance = this;
		
        try {
        	zipFile = new ZipFile(zipPath);
			Enumeration<ZipEntry> en=(Enumeration<ZipEntry>) zipFile.entries();
		      ZipEntry entry = null;
		      zipfileList = new ArrayList<ZipEntry>();
		        while (en.hasMoreElements()) {
		            entry = en.nextElement();
		            if (!entry.isDirectory()) {
		                // 如果文件不是目录，则添加到列表中
		                zipfileList.add(entry);
		            }
		        }
		        Log.d(TAG,"total files "+ zipfileList.size());
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public static NtcApplication getInstance() {
		return _instance;
	}
	
}
