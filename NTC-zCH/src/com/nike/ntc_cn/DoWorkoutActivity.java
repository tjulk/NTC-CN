package com.nike.ntc_cn;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.nike.ntc_cn.db.T_ExerciseAudioClipsControl;
import com.nike.ntc_cn.db.T_ExerciseAudioClipsControl.M_ExerciseAudioClips;
import com.nike.ntc_cn.db.T_ExerciseControl.M_Exercises;
import com.nike.ntc_cn.db.T_WorkoutAudioClipsControl;
import com.nike.ntc_cn.db.T_WorkoutAudioClipsControl.M_WorkoutAudioClips;
import com.nike.ntc_cn.db.T_WorkoutControl;
import com.nike.ntc_cn.db.T_WorkoutControl.M_Workouts;
import com.nike.ntc_cn.db.T_WorkoutExercisesControl;
import com.nike.ntc_cn.utils.Utils;


public class DoWorkoutActivity extends BaseActivity implements OnClickListener, OnCompletionListener, OnErrorListener{
	
	private String workoutName ;
	private M_Workouts workout ;
	
	private List<M_Exercises> exercises ;
	
	private TextView tutorial_detail_title;
	private TextView tutorial_total_time;
	private TextView exercise_title;
	private TextView exercise_timer;
	private Button stop_btn;
	private Button pause_btn;
	private ImageView video_player_btn;
	
	private VideoView surfaceView;
	private Timer timer;
	
	private boolean isPause = false;
	private boolean isPlayingNow = false;
	
	private int exerciseIndex = 0;
	private int totalTime = 0;
	private int exerciseTime = 0;
	
	private String exerciseVideoUrl ;
	
	private Drawable exerciseDrawable;
	
	private List<M_WorkoutAudioClips> mWorkoutAudioClips;
	private List<M_ExerciseAudioClips> mExerciseAudioClips;
	
	private int workoutAudioIndex = 0;
	private int exerciseAudioIndex =0;
	private MediaPlayer workoutAudio;
	private MediaPlayer exerciseAudio;
	
	private M_Exercises nowExercise;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.do_workout);
		
		workoutName = getIntent().getStringExtra(TutorialDetailActivity.TAG_WORKOUT_NAME);
		workout = T_WorkoutControl.getInstance(this).getWorkoutByName(workoutName);
		exercises = T_WorkoutExercisesControl.getInstance(this).getExercisesListByWorkoutname(workoutName);
		mWorkoutAudioClips = T_WorkoutAudioClipsControl.getInstance(this).getWorkoutAudioClipsListByWorkoutname(workoutName);
		
		tutorial_total_time = (TextView)findViewById(R.id.tutorial_total_time);
		tutorial_detail_title = (TextView)findViewById(R.id.tutorial_detail_title);
		exercise_timer = (TextView)findViewById(R.id.exercise_timer);
		exercise_title = (TextView)findViewById(R.id.exercise_detail_title);
		stop_btn = (Button)findViewById(R.id.stop_btn);
		pause_btn = (Button)findViewById(R.id.pause_btn);
		video_player_btn = (ImageView)findViewById(R.id.video_player_btn);
		stop_btn.setOnClickListener(this);
		pause_btn.setOnClickListener(this);
		video_player_btn.setOnClickListener(this);
		
		tutorial_detail_title.setText(workout.title);
		
		surfaceView = (VideoView)findViewById(R.id.video_view);
		surfaceView.setOnCompletionListener(this);
		surfaceView.setOnErrorListener(this);
		
		workoutAudio = new MediaPlayer();
		exerciseAudio = new MediaPlayer();
		workoutAudio.setOnCompletionListener(this);
		
		initTime();
		
		initWorkout();
		
		initExercise();
		
		handler.sendEmptyMessageDelayed(2, (long)(mWorkoutAudioClips.get(workoutAudioIndex).start_time * 1000));
		
	}
	
	private void initExerciseAudio() {
		nowExercise = exercises.get(exerciseIndex);
		exerciseAudioIndex = 0;
		mExerciseAudioClips = T_ExerciseAudioClipsControl.getInstance(this).getM_ExerciseAudioClipsListByExercisename(nowExercise.name);
		
		handler.sendEmptyMessageDelayed(3, (long)(mExerciseAudioClips.get(exerciseAudioIndex).start_time * 1000));
	}
	
	
	private void PlayWorkoutAudio() {
		final String name = mWorkoutAudioClips.get(workoutAudioIndex).audio_clip_name;
		try {
			
			
			workoutAudio.release();
			workoutAudio = new MediaPlayer();
			workoutAudio.setDataSource(Utils.getAudioSDCardPathFromName(name));
			workoutAudio.prepare();
			workoutAudio.start();
			workoutAudioIndex = workoutAudioIndex +1;
			
			final float workoutNextStart_time = mWorkoutAudioClips.get(workoutAudioIndex).start_time;
			final float workoutNowStartTime = mWorkoutAudioClips.get(workoutAudioIndex-1).start_time;
			System.out.println("PlayWorkoutAudio  " + workoutNextStart_time + "  " + workoutNowStartTime + " " + name);
			
			handler.sendEmptyMessageDelayed(2, (long)((workoutNextStart_time - workoutNowStartTime) * 1000));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void PlayExerciseAudio() {
		
		if (exerciseAudioIndex == mExerciseAudioClips.size()) {
			return;
		}
		
		final String name = mExerciseAudioClips.get(exerciseAudioIndex).audio_clip_name;
		
		try {
			
			exerciseAudio.release();
			exerciseAudio = new MediaPlayer();
			exerciseAudio.setDataSource(Utils.getAudioSDCardPathFromName(name));
			exerciseAudio.prepare();
			exerciseAudio.start();
			exerciseAudioIndex = exerciseAudioIndex+1;
			
			final float exerciseNextStart_time = mExerciseAudioClips.get(exerciseAudioIndex).start_time;
			final float exerciseNowStartTime = mWorkoutAudioClips.get(exerciseAudioIndex-1).start_time;
			
			System.out.println("PlayExerciseAudio " + exerciseNextStart_time + "  " + exerciseNowStartTime + "  " + name);
			
			handler.sendEmptyMessageDelayed(3, (long)((exerciseNextStart_time - exerciseNowStartTime)
					* 1000));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void initTime(){
		timer = new Timer(true);  
		timer.schedule(task,1000, 1000); 
	}
	
	private void initWorkout() {
		totalTime = workout.duration * 60;
		tutorial_total_time.setText(getClockTimeFromMinutesNum(totalTime));
	}
	
	private boolean initExercise() {
		
		if (exerciseIndex == exercises.size())
			return false;
		
		initExerciseAudio();
		
		exerciseTime = exercises.get(exerciseIndex).duration;
		exercise_timer.setText(getClockTimeFromMinutesNum(exerciseTime));
		exercise_title.setText(exercises.get(exerciseIndex).title);
		
		final String videoName = exercises.get(exerciseIndex).video_name;
		if (videoName == null || videoName.equals(""))
			exerciseVideoUrl = null;
		else
			exerciseVideoUrl = Utils.getVideoSDCardPathFromName(videoName);
		
	   final String imagePath =Utils.getImageSDCardPathFromName(exercises.get(exerciseIndex).thumbnail_medium + ".jpg");
 	     	
	   final BitmapFactory.Options options = new BitmapFactory.Options(); 
	   final Bitmap bitmap = BitmapFactory.decodeFile(imagePath, options);
		
	   exerciseDrawable =new BitmapDrawable(bitmap);
		
	   exerciseIndex ++;
		
	   return true;
	}
	
	private void playExerciseVideo() {
		
		if (exerciseVideoUrl == null) {
			surfaceView.setBackgroundDrawable(exerciseDrawable);
			video_player_btn.setVisibility(View.GONE);
		} else {
			surfaceView.setBackgroundResource(0);
			video_player_btn.setVisibility(View.GONE);
			surfaceView.setVideoPath(exerciseVideoUrl);
			surfaceView.requestFocus();
			surfaceView.start();
		}
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		if (isPause)
			pause_btn.setText("继续训练");
		else
			pause_btn.setText("暂停训练");
		
		playExerciseVideo();
	}

	@Override
	protected void onPause() {
		overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
		if (surfaceView.isPlaying())
			surfaceView.pause();
		
		isPause = true;
		
		super.onPause();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		timer.cancel();
	}

	final Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				
				if (isPause)
					break;
				
				totalTime --;
				tutorial_total_time.setText(getClockTimeFromMinutesNum(totalTime));
				if (totalTime == 0)
					timer.cancel();
				
				exerciseTime --;
				exercise_timer.setText(getClockTimeFromMinutesNum(exerciseTime));
				if (exerciseTime == 0) {
					if (initExercise())
						playExerciseVideo();
				}
				break;
				
			case 2:
				PlayWorkoutAudio();
				break;
				
			case 3:
				PlayExerciseAudio();
				break;
			}
			super.handleMessage(msg);
		}
	};
	
	TimerTask task = new TimerTask() {
		public void run() {
			Message message = new Message();
			message.what = 1;
			handler.sendMessage(message);
		}
	};
	
	public static String getClockTimeFromMinutesNum(int num) {
		return formatNumber(num/ 60) + ":" + formatNumber(num % 60);
	}
	
    private static String formatNumber(int num){
        DecimalFormat df=new DecimalFormat("00");
        String str=df.format(num);
       return str;
    }

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.pause_btn:
			if (!isPause) {
				
				if (surfaceView.isPlaying()) {
					video_player_btn.setVisibility(View.VISIBLE);
					surfaceView.pause();
					isPlayingNow = true;	
				}
				
				isPause = true;
				pause_btn.setText("继续训练");
			} else {
				if (isPlayingNow) {
					video_player_btn.setVisibility(View.GONE);
					surfaceView.start();
				} 
				isPause = false;
				pause_btn.setText("暂停训练");
			}
			
			break;
		case R.id.stop_btn:
			if (totalTime > 0)
				Toast.makeText(this, "开始了，就不要停止！", Toast.LENGTH_SHORT).show();
			break;
			
		case R.id.video_player_btn:
			if (isPlayingNow) {
				isPlayingNow = false;
				video_player_btn.setVisibility(View.GONE);
				surfaceView.start();
			} else 
			    playExerciseVideo();
		break;
		default:
			break;
		}
	}

	@Override
	public void onCompletion(MediaPlayer mp) {
		
		if (mp == workoutAudio) {
			
		} else if (mp == exerciseAudio) {
			
		} else {
			isPlayingNow = false;
			surfaceView.setBackgroundDrawable(exerciseDrawable);
			video_player_btn.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public boolean onError(MediaPlayer mp, int what, int extra) {
		surfaceView.setBackgroundDrawable(exerciseDrawable);
		video_player_btn.setVisibility(View.GONE);
		return false;
	}

	@Override
	public void onBackPressed() {
		
		if (totalTime > 0)
			Toast.makeText(this, "退不出去了，胖子！", Toast.LENGTH_SHORT).show();
		else
			super.onBackPressed();
	}
	
}
