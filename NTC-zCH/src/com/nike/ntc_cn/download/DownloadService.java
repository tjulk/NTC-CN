package com.nike.ntc_cn.download;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

public class DownloadService extends Service {
	
	public static final String TAG_DOWNLOAD_TYPE = "tag_download_type";
	public static final String T_DOWNLOAD_TYPE_WHOLE = "t_download_type_whole";
	public static final String T_DOWNLOAD_TYPE_WORKOUT = "t_download_type_workout";
	public static final String TAG_WORKOUT_NAME = "tag_workout_name";
	
	private String mType = T_DOWNLOAD_TYPE_WHOLE;
	private String mWorkoutName ;
	
	private boolean isDownloadingAll = false;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	private Context mContext;

	@Override
	public void onCreate() {
		super.onCreate();
		mContext = this;
	}



	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		
		mType = intent.getStringExtra(TAG_DOWNLOAD_TYPE);
		
		if (mType.equals(T_DOWNLOAD_TYPE_WHOLE))
			downloadAllWorkouts();
		else if (mType.equals(T_DOWNLOAD_TYPE_WORKOUT)){
			mWorkoutName = intent.getStringExtra(TAG_WORKOUT_NAME);
			downloadWorkoutByName();
		}

		return super.onStartCommand(intent, flags, startId);
		
	}
	
	
	private void downloadAllWorkouts() {
		
		
		isDownloadingAll = true;
	}
	
	private void downloadWorkoutByName() {
		new DownloadFileAsync(mContext,mWorkoutName).execute(mWorkoutName);
	}
	
	

}
