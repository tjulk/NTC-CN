package com.nike.ntc_cn;

import java.util.List;

import android.os.Bundle;
import android.widget.ListView;

import com.nike.ntc_cn.adapter.TutorialDetailListAdapter;
import com.nike.ntc_cn.db.T_ExerciseControl.M_Exercises;
import com.nike.ntc_cn.db.T_WorkoutExercisesControl;

public class TutorialDetailActivity extends BaseActivity{
	
	private ListView tutorial_detail_list;
	private List<M_Exercises> exercises ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tutorial_detail);
		
		String workoutName = getIntent().getStringExtra(TutorialListActivity.TAG_WORKOUT_NAME);
		
		tutorial_detail_list = (ListView) findViewById(R.id.tutorial_detail_list);
		exercises = T_WorkoutExercisesControl.getInstance(this).getExercisesListByWorkoutname(workoutName);
		final TutorialDetailListAdapter adapter = new TutorialDetailListAdapter(this, exercises);
		tutorial_detail_list.setAdapter(adapter);
	}
	
	@Override
	protected void onPause() {
		overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
		super.onPause();
	}
}
