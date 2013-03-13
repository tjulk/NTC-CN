package com.nike.ntc_cn;

import java.util.List;

import com.nike.ntc_cn.db.T_WorkoutExercisesControl;
import com.nike.ntc_cn.db.T_ExerciseControl.M_Exercises;

import android.os.Bundle;


public class DoWorkoutActivity extends BaseActivity{
	
	private String workoutName ;
	private List<M_Exercises> exercises ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.do_workout);
		
		workoutName = getIntent().getStringExtra(TutorialListActivity.TAG_WORKOUT_NAME);
		exercises = T_WorkoutExercisesControl.getInstance(this).getExercisesListByWorkoutname(workoutName);
		
		
	}
	
	@Override
	protected void onPause() {
		overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
		super.onPause();
	}
	
}
