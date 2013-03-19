package com.nike.ntc_cn.download;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.nike.ntc_cn.TutorialDetailActivity;
import com.nike.ntc_cn.db.T_ExerciseAudioClipsControl;
import com.nike.ntc_cn.db.T_ExerciseAudioClipsControl.M_ExerciseAudioClips;
import com.nike.ntc_cn.db.T_ExerciseControl.M_Exercises;
import com.nike.ntc_cn.db.T_ExercisePagesControl;
import com.nike.ntc_cn.db.T_ExercisePagesControl.M_ExercisePages;
import com.nike.ntc_cn.db.T_WorkoutAudioClipsControl;
import com.nike.ntc_cn.db.T_WorkoutAudioClipsControl.M_WorkoutAudioClips;
import com.nike.ntc_cn.db.T_WorkoutControl;
import com.nike.ntc_cn.db.T_WorkoutControl.M_Workouts;
import com.nike.ntc_cn.db.T_WorkoutExercisesControl;
import com.nike.ntc_cn.receiver.DownloadBroadcastReceiver;
import com.nike.ntc_cn.utils.Utils;

public class DownloadFileAsync extends AsyncTask<String, String, String> {
	
	private Context mContext;
	
	private String workoutName ;
	
	private int maxFiles = 0;
	
	enum DownloadFileType {
		jpg,ogg,m4v
	}
	
	public DownloadFileAsync(Context context, String name) {
		mContext = context;
		workoutName = name;
	}
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		T_WorkoutControl.getInstance(mContext).changeWorkoutStatus(M_Workouts.ARCHIVE_DOWNLOADING, workoutName);
	}

	@Override
	protected String doInBackground(String... workout) {
		initDownloadFiles(workout[0]);
		return null;
	}

	protected void onProgressUpdate(String... progress) {
		Log.d("ANDRO_ASYNC", progress[0]);
		
		if (progress[0].equals("100")){
			T_WorkoutControl.getInstance(mContext).changeWorkoutStatus(M_Workouts.ARCHIVE_DOWNLOADED, workoutName);
			Intent intent = new Intent(DownloadBroadcastReceiver.DOWNLOAD_ACTION);
			intent.putExtra(TutorialDetailActivity.TAG_WORKOUT_NAME, workoutName);
			mContext.sendBroadcast(intent);
			Toast.makeText(mContext, "教程" + workoutName + "下载完成", Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	protected void onPostExecute(String unused) {
		
	}
	
	
	private void initDownloadFiles(String workoutName) {
		
		final List<DownloadInfo> downloadInfos = new ArrayList<DownloadInfo>();
		
		List<M_WorkoutAudioClips> workoutAudioClips = T_WorkoutAudioClipsControl.getInstance(mContext).getWorkoutAudioClipsListByWorkoutname(workoutName);
		
		maxFiles+= workoutAudioClips.size();
		DownloadInfo info;
		// 获取每个workout对应所有音频
		for (int i=0;i<workoutAudioClips.size();i++) {
			info = new DownloadInfo();
			info.type = DownloadFileType.ogg;
			info.name = workoutAudioClips.get(i).audio_clip_name;
			downloadInfos.add(info);
		}
		
		List<M_Exercises> exercises = T_WorkoutExercisesControl.getInstance(mContext).getExercisesListByWorkoutname(workoutName);
		
		for (int i = 0;i<exercises.size();i++) {
			
			final M_Exercises  exercise= exercises.get(i);
			//获取每个exercise特有图片
			List<M_ExercisePages> exercisePages = T_ExercisePagesControl.getInstance(mContext).getExercisePagesByExerciseName(exercise.name);
			//获取每个exercise包含的所有音频
			List<M_ExerciseAudioClips> exerciseAudioClips = T_ExerciseAudioClipsControl.getInstance(mContext).getM_ExerciseAudioClipsListByExercisename(exercise.name);
			
			info = new DownloadInfo();
			info.type = DownloadFileType.jpg;
			info.name = exercise.thumbnail_medium;
			downloadInfos.add(info);
			
			info = new DownloadInfo();
			info.type = DownloadFileType.m4v;
			info.name = exercise.video_name;
			downloadInfos.add(info);
			
			for (int k=0;k < exercisePages.size();k++) {
				info = new DownloadInfo();
				info.type = DownloadFileType.jpg;
				info.name = exercisePages.get(k).background_image;
				downloadInfos.add(info);
			}
			
			for (int j=0;j < exerciseAudioClips.size();j++) {
				info = new DownloadInfo();
				info.type = DownloadFileType.ogg;
				info.name = exerciseAudioClips.get(j).audio_clip_name;
				downloadInfos.add(info);
			}
		}
		
		maxFiles = downloadInfos.size();
		for (int z = 0;z<downloadInfos.size();z++){
			//开始下载
			downloadFile(downloadInfos.get(z).type,downloadInfos.get(z).name);
			publishProgress("" + ((z+1)* 100/maxFiles));
		}
		
	}
	
	private void downloadFile(DownloadFileType downloadFileType, String filename) {
		int count;
		try {
			
			String urlPath ;
			String sdcardFilePath ;
			
			switch (downloadFileType) {
			case jpg:
				urlPath = Utils.fitUrlFromImageName(filename);
				sdcardFilePath = Utils.getImageSDCardPathFromUrl(urlPath);
				break;
			case ogg:
				urlPath = Utils.fitUrlFromAudioName(filename);
				sdcardFilePath = Utils.getAudioSDCardPathFromUrl(urlPath);
				break;
			case m4v:
				urlPath = Utils.fitUrlFromVideoName(filename);
				sdcardFilePath = Utils.getVideoSDCardPathFromUrl(urlPath);
				break;
			default:
				urlPath = Utils.fitUrlFromImageName(filename);
				sdcardFilePath = Utils.getImageSDCardPathFromUrl(urlPath);
				break;
			}
			//如果文件已存在，则不进行下载
			File file = new File(sdcardFilePath);
			if (file.exists())
				return ;
			
			URL url = new URL(urlPath);
			URLConnection conexion = url.openConnection();
			conexion.connect();
			int lenghtOfFile = conexion.getContentLength();
			Log.d("ANDRO_ASYNC", "Lenght of file: " + lenghtOfFile);
			
			InputStream input = new BufferedInputStream(url.openStream());
			OutputStream output = new FileOutputStream(sdcardFilePath);
			
			byte data[] = new byte[1024];
			while ((count = input.read(data)) != -1) {
				//publishProgress("" + (int) ((total * 100) / lenghtOfFile));
				output.write(data, 0, count);
			}
			output.flush();
			output.close();
			input.close();
			
		} catch (Exception e) {
			Log.e("error", e.getMessage().toString());
			System.out.println(e.getMessage().toString());
		}
	}
	
	
}
