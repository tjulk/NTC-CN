package com.nike.ntc_cn;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.nike.ntc_cn.adapter.TutorialDetailListAdapter;
import com.nike.ntc_cn.db.T_ExerciseControl.M_Exercises;
import com.nike.ntc_cn.db.T_WorkoutExercisesControl;
import com.nike.ntc_cn.download.DownloadFileAsync;

public class TutorialDetailActivity extends BaseActivity implements OnClickListener{
	
	
	private String workoutName ;
	private List<M_Exercises> exercises ;
	
	private ListView tutorial_detail_list;
	
	private Button music_btn;
	private Button start_btn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tutorial_detail);
		
		workoutName = getIntent().getStringExtra(TutorialListActivity.TAG_WORKOUT_NAME);
		exercises = T_WorkoutExercisesControl.getInstance(this).getExercisesListByWorkoutname(workoutName);
		
		tutorial_detail_list = (ListView) findViewById(R.id.tutorial_detail_list);
		final TutorialDetailListAdapter adapter = new TutorialDetailListAdapter(this, exercises);
		tutorial_detail_list.setAdapter(adapter);
		
		music_btn = (Button)findViewById(R.id.music_btn);
		start_btn = (Button)findViewById(R.id.start_btn);
		music_btn.setOnClickListener(this);
		start_btn.setOnClickListener(this);
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
			new DownloadFileAsync(this).execute(workoutName);
			break;
		case R.id.start_btn:
			Intent intent = new Intent(this,DoWorkoutActivity.class);
			intent.putExtra(TutorialListActivity.TAG_WORKOUT_NAME, workoutName);
			startActivity(intent);
			break;

		default:
			break;
		}
	}
}
