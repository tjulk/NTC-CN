package com.nike.ntc_cn;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.nike.ntc_cn.adapter.TutorialDetailListAdapter;
import com.nike.ntc_cn.db.T_ExerciseControl.M_Exercises;
import com.nike.ntc_cn.db.T_WorkoutExercisesControl;
import com.nike.ntc_cn.download.DownloadFileAsync;

public class TutorialDetailActivity extends BaseActivity implements OnClickListener{
	
	
	private List<M_Exercises> exercises ;
	
	private ListView tutorial_detail_list;
	
	private Button music_btn;
	private Button start_btn;
	
	private String workoutName ;
	private boolean isTheWorkoutDownloaded = false;
	private boolean isDownLoading = false;
	private DownloadFileAsync async;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tutorial_detail);
		
		workoutName = getIntent().getStringExtra(TutorialListActivity.TAG_WORKOUT_NAME);
		exercises = T_WorkoutExercisesControl.getInstance(this).getExercisesListByWorkoutname(workoutName);
		
		isTheWorkoutDownloaded = getIntent().getBooleanExtra(TutorialListActivity.TAG_WORKOUT_IS_DOWNLOAD,false);
		
		tutorial_detail_list = (ListView) findViewById(R.id.tutorial_detail_list);
		final TutorialDetailListAdapter adapter = new TutorialDetailListAdapter(this, exercises);
		tutorial_detail_list.setAdapter(adapter);
		
		music_btn = (Button)findViewById(R.id.music_btn);
		start_btn = (Button)findViewById(R.id.start_btn);
		music_btn.setOnClickListener(this);
		start_btn.setOnClickListener(this);
		
		if (isTheWorkoutDownloaded)
			start_btn.setText("开始健身");
		else
			start_btn.setText("下载教程");
		async =	new DownloadFileAsync(this,start_btn,workoutName);
	}
	
	@Override
	protected void onPause() {
		overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
		super.onPause();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.music_btn:
			
			break;
		case R.id.start_btn:
			if (!isTheWorkoutDownloaded) {
				start_btn.setClickable(false);
				async.execute(workoutName);
				isDownLoading = true;
				isTheWorkoutDownloaded = true;
			} else {
				Intent intent = new Intent(this,DoWorkoutActivity.class);
				intent.putExtra(TutorialListActivity.TAG_WORKOUT_NAME, workoutName);
				startActivity(intent);
			}
			break;

		default:
			break;
		}
	}

	@Override
	public void onBackPressed() {
		
		if (isDownLoading && start_btn.isClickable()) {
			async.cancel(true);
			Toast.makeText(this, "下载已停止，下次进入请点击继续下载", Toast.LENGTH_SHORT).show();
		}  
		
		super.onBackPressed();
		
	}
	
	
	
}
