package com.nike.ntc_cn;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;
import android.widget.VideoView;

import com.nike.ntc_cn.db.T_ExerciseControl.M_Exercises;
import com.nike.ntc_cn.db.T_WorkoutControl;
import com.nike.ntc_cn.db.T_WorkoutControl.M_Workouts;
import com.nike.ntc_cn.db.T_WorkoutExercisesControl;


public class DoWorkoutActivity extends BaseActivity{
	
	private String workoutName ;
	

	private M_Workouts workout ;
	private List<M_Exercises> exercises ;
	
	private TextView tutorial_detail_title;
	
	private TextView tutorial_total_time;
	
	private VideoView videoView;
	private Timer timer;
	
	private int totalTime = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.do_workout);
		
		workoutName = getIntent().getStringExtra(TutorialDetailActivity.TAG_WORKOUT_NAME);
		
		workout = T_WorkoutControl.getInstance(this).getWorkoutByName(workoutName);
		
		exercises = T_WorkoutExercisesControl.getInstance(this).getExercisesListByWorkoutname(workoutName);
		
		videoView = (VideoView)findViewById(R.id.video_view);
		
		totalTime = workout.duration * 60;
		
		tutorial_total_time = (TextView)findViewById(R.id.tutorial_total_time);
		tutorial_total_time.setText(getClockTimeFromMinutesNum(totalTime));
		tutorial_detail_title = (TextView)findViewById(R.id.tutorial_detail_title);
		tutorial_detail_title.setText(workout.title);
		
		timer = new Timer(true);  
		timer.schedule(task,3000, 1000); 
	}
	
	@Override
	protected void onPause() {
		overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
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
				totalTime --;
				tutorial_total_time.setText(getClockTimeFromMinutesNum(totalTime));
				if (totalTime == 0)
					timer.cancel();
				
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
	
	
}
