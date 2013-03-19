package com.nike.ntc_cn;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.nike.ntc_cn.adapter.TutorialDetailListAdapter;
import com.nike.ntc_cn.db.T_ExerciseControl.M_Exercises;
import com.nike.ntc_cn.db.T_WorkoutControl.M_Workouts;
import com.nike.ntc_cn.db.T_WorkoutExercisesControl;
import com.nike.ntc_cn.download.DownloadService;
import com.nike.ntc_cn.receiver.DownloadBroadcastReceiver;

public class TutorialDetailActivity extends BaseActivity implements OnClickListener{
	
	public static final String TAG_WORKOUT_NAME = "tag_workout";
	public static final String TAG_WORKOUT_ARCHIVE = "tag_workout_archive";
	
	private List<M_Exercises> exercises ;
	
	private ListView tutorial_detail_list;
	
	private Button music_btn;
	private Button start_btn;
	
	private String workoutName ;
	private String workoutArchive ;
	
	private DownloadBroadcastReceiver receiver;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tutorial_detail);
		
		workoutName = getIntent().getStringExtra(TAG_WORKOUT_NAME);
		exercises = T_WorkoutExercisesControl.getInstance(this).getExercisesListByWorkoutname(workoutName);
		
		workoutArchive = getIntent().getStringExtra(TAG_WORKOUT_ARCHIVE);
		
		tutorial_detail_list = (ListView) findViewById(R.id.tutorial_detail_list);
		final TutorialDetailListAdapter adapter = new TutorialDetailListAdapter(this, exercises);
		tutorial_detail_list.setAdapter(adapter);
		
		music_btn = (Button)findViewById(R.id.music_btn);
		start_btn = (Button)findViewById(R.id.start_btn);
		music_btn.setOnClickListener(this);
		start_btn.setOnClickListener(this);
		
		if (workoutArchive.equals(M_Workouts.ARCHIVE_DOWNLOADED))
			start_btn.setText("开始健身");
		else if (workoutArchive.equals(M_Workouts.ARCHIVE_DOWNLOADING))
			start_btn.setText("下载中");
		else if (workoutArchive.equals(M_Workouts.ARCHIVE_STANDARD))
			start_btn.setText("下载教程");

	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
	    IntentFilter intentFilter = new IntentFilter();
	    intentFilter.addAction(DownloadBroadcastReceiver.DOWNLOAD_ACTION); //为BroadcastReceiver指定action，即要监听的消息名字。
	    receiver = new DownloadBroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				super.onReceive(context, intent);
				String action = intent.getAction();
				if (action.equals(DownloadBroadcastReceiver.DOWNLOAD_ACTION)) {
					String name = intent.getStringExtra(TAG_WORKOUT_NAME);
					if (name.equals(workoutName))
						start_btn.setText("开始健身");
				}
			}
		};
	    
	    registerReceiver(receiver,intentFilter); 
	}


	@Override
	protected void onPause() {
		overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
		unregisterReceiver(receiver);
		super.onPause();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.music_btn:
			
			break;
		case R.id.start_btn:
			if (workoutArchive.equals(M_Workouts.ARCHIVE_STANDARD)) {
				Intent intent = new Intent(this, DownloadService.class);
				intent.putExtra(DownloadService.TAG_DOWNLOAD_TYPE, DownloadService.T_DOWNLOAD_TYPE_WORKOUT);
				intent.putExtra(DownloadService.TAG_WORKOUT_NAME, workoutName);
				startService(intent);
				start_btn.setText("下载中");
				workoutArchive = M_Workouts.ARCHIVE_DOWNLOADING;
				
			} else if (workoutArchive.equals(M_Workouts.ARCHIVE_DOWNLOADED)){
				
				Intent intent = new Intent(this,DoWorkoutActivity.class);
				intent.putExtra(TAG_WORKOUT_NAME, workoutName);
				startActivity(intent);
			} else if (workoutArchive.equals(M_Workouts.ARCHIVE_DOWNLOADING)) {
				Toast.makeText(this, "正在下载，请稍后...", Toast.LENGTH_SHORT).show();
			}
			break;

		default:
			break;
		}
	}

	@Override
	public void onBackPressed() {
		
		super.onBackPressed();
		
	}
	
	
	
}
