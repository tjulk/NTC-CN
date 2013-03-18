package com.nike.ntc_cn.download;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.nike.ntc_cn.db.T_ExerciseControl.M_Exercises;
import com.nike.ntc_cn.db.T_WorkoutAudioClipsControl;
import com.nike.ntc_cn.db.T_WorkoutAudioClipsControl.M_WorkoutAudioClips;
import com.nike.ntc_cn.db.T_WorkoutExercisesControl;
import com.nike.ntc_cn.utils.Utils;

public class DownloadFileAsync extends AsyncTask<String, String, String> {
	
	private Context mContext;
	
	enum DownloadFileType {
		jpg,ogg,m4v
	}
	
	public DownloadFileAsync(Context context) {
		mContext = context;
	}
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}

	@Override
	protected String doInBackground(String... workout) {
		initDownloadFiles(workout[0]);
		return null;
	}

	protected void onProgressUpdate(String... progress) {
		Log.d("ANDRO_ASYNC", progress[0]);
	}

	@Override
	protected void onPostExecute(String unused) {
		
	}
	
	
	private void initDownloadFiles(String workoutName) {
		List<M_WorkoutAudioClips> workoutAudioClips = T_WorkoutAudioClipsControl.getInstance(mContext).getWorkoutAudioClipsListByWorkoutname(workoutName);
		//TODO 获取每个workout对应所有音频
		for (int i=0;i<workoutAudioClips.size();i++) {
			final M_WorkoutAudioClips  workoutAudioClip= workoutAudioClips.get(i);
			downloadFile(DownloadFileType.ogg,workoutAudioClip.audio_clip_name);
		}
		
		List<M_Exercises> exercises = T_WorkoutExercisesControl.getInstance(mContext).getExercisesListByWorkoutname(workoutName);
		
		for (int i = 0;i<exercises.size();i++) {
			final M_Exercises  exercise= exercises.get(i);
			downloadFile(DownloadFileType.jpg,exercise.thumbnail_medium);
			downloadFile(DownloadFileType.m4v,exercise.video_name);
			
			//TODO 获取每个exercise特有图片
			
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
			long total = 0;
			while ((count = input.read(data)) != -1) {
				total += count;
				publishProgress("" + (int) ((total * 100) / lenghtOfFile));
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
