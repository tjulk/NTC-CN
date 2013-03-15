package com.nike.ntc_cn;

import java.util.List;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.widget.MediaController;
import android.widget.VideoView;

import com.nike.ntc_cn.db.T_ExerciseControl.M_Exercises;
import com.nike.ntc_cn.db.T_WorkoutExercisesControl;


public class DoWorkoutActivity extends BaseActivity{
	
	private String workoutName ;
	private List<M_Exercises> exercises ;
	
	private VideoView videoView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.do_workout);
		
		workoutName = getIntent().getStringExtra(TutorialListActivity.TAG_WORKOUT_NAME);
		exercises = T_WorkoutExercisesControl.getInstance(this).getExercisesListByWorkoutname(workoutName);
		
		videoView = (VideoView)findViewById(R.id.video_view);
		Uri uri = Uri.parse(Environment.getExternalStorageDirectory().getPath()+"/DCIM/Camera/VID_20130315_175030.mp4");  
		
		
		
		videoView.setMediaController(new MediaController(this));    
		videoView.setVideoURI(uri);  
		videoView.start();    
		videoView.requestFocus();
		
		
	}
	
	@Override
	protected void onPause() {
		overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
		super.onPause();
	}
	
}
