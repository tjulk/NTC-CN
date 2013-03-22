package com.nike.ntc_cn.download;

import java.util.List;

import com.nike.ntc_cn.db.T_WorkoutControl;
import com.nike.ntc_cn.db.T_WorkoutControl.M_Workouts;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.widget.Toast;

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
		
		if (intent == null) {
			stopSelf();
			return 0;
		}
		
		mType = intent.getStringExtra(TAG_DOWNLOAD_TYPE);
		
		if (mType.equals(T_DOWNLOAD_TYPE_WHOLE)) {
			downloadAllWorkouts();
		}
		else if (mType.equals(T_DOWNLOAD_TYPE_WORKOUT)){
			mWorkoutName = intent.getStringExtra(TAG_WORKOUT_NAME);
			downloadWorkoutByName();
		}

		return super.onStartCommand(intent, flags, startId);
		
	}
	
	
	private void downloadAllWorkouts() {
		List<M_Workouts> workouts = T_WorkoutControl.getInstance(mContext).getAllUnDownloadWorkouts();
		if (workouts.size() == 0)
            Toast.makeText(this, "所有教程已下载/正在下载中", Toast.LENGTH_SHORT).show();
		else
            Toast.makeText(this, "正在下载剩余教程", Toast.LENGTH_SHORT).show();
		int delay = 0;
		for (int i=0 ;i< workouts.size();i++) {
			mWorkoutName = 	workouts.get(i).name;	
			
			handler.sendEmptyMessageDelayed(0, delay);
			delay = delay + 3000;
		}
		
		isDownloadingAll = true;
	}
	
	
	Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			downloadWorkoutByName();
			super.handleMessage(msg);
		}
		
	};
	
	private void downloadWorkoutByName() {
		new DownloadFileAsync(mContext,mWorkoutName).execute(mWorkoutName);
	}
	
	

}
