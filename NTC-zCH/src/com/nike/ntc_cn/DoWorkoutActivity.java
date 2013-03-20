package com.nike.ntc_cn;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.nike.ntc_cn.db.T_ExerciseControl.M_Exercises;
import com.nike.ntc_cn.db.T_WorkoutControl;
import com.nike.ntc_cn.db.T_WorkoutControl.M_Workouts;
import com.nike.ntc_cn.db.T_WorkoutExercisesControl;
import com.nike.ntc_cn.player.Player;


public class DoWorkoutActivity extends BaseActivity implements OnClickListener{
	
	private String workoutName ;
	private M_Workouts workout ;
	private List<M_Exercises> exercises ;
	
	private TextView tutorial_detail_title;
	private TextView tutorial_total_time;
	private TextView exercise_timer;
	private Button stop_btn;
	private Button pause_btn;
	
	private SurfaceView surfaceView;
	private Timer timer;
	private Player player;
	
	private int totalTime = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.do_workout);
		
		workoutName = getIntent().getStringExtra(TutorialDetailActivity.TAG_WORKOUT_NAME);
		
		workout = T_WorkoutControl.getInstance(this).getWorkoutByName(workoutName);
		
		exercises = T_WorkoutExercisesControl.getInstance(this).getExercisesListByWorkoutname(workoutName);
		
		surfaceView = (SurfaceView)findViewById(R.id.video_view);
		
		player = new Player(surfaceView);

		totalTime = workout.duration * 60;
		
		tutorial_total_time = (TextView)findViewById(R.id.tutorial_total_time);
		tutorial_total_time.setText(getClockTimeFromMinutesNum(totalTime));
		tutorial_detail_title = (TextView)findViewById(R.id.tutorial_detail_title);
		tutorial_detail_title.setText(workout.title);
		exercise_timer = (TextView)findViewById(R.id.exercise_timer);
		stop_btn = (Button)findViewById(R.id.stop_btn);
		pause_btn = (Button)findViewById(R.id.pause_btn);
		stop_btn.setOnClickListener(this);
		pause_btn.setOnClickListener(this);
		timer = new Timer(true);  
		timer.schedule(task,1000, 1000); 
		
		playerVideoDely();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
	}

	private void playerVideoDely() {
		Message message = new Message();
		message.what = 2;
		handler.sendMessageDelayed(message,2000);
	}


	@Override
	protected void onPause() {
		overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
		player.pause();
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
				
			case 2:
				
				String url = "mnt/sdcard/.ntc/videos/toe-touch.m4v";
				player.playUrl(url);
				player.play();
				
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
			String url = "mnt/sdcard/.ntc/videos/toe-touch.m4v";
			player.playUrl(url);
			player.play();
			break;
		case R.id.stop_btn:
			
			break;
		default:
			break;
		}
	}
	
}
