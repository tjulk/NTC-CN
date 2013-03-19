package com.nike.ntc_cn.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public abstract class DownloadBroadcastReceiver extends BroadcastReceiver {
	
	public static final String DOWNLOAD_ACTION = "com.nike.ntc_cn.DOWNLOAD_ACTION";
	
	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();
		
	}
}