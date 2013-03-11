package com.nike.ntc_cn;

import java.util.List;

import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Gallery;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.SpinnerAdapter;

import com.nike.ntc_cn.adapter.WorkoutExercisesAdapter;
import com.nike.ntc_cn.db.T_WorkoutControl;
import com.nike.ntc_cn.db.T_WorkoutControl.M_Workouts;

public class TutorialListActivity extends BaseActivity{
	
	public static final int TUTORIAL_LIST_BACKGROUND[] = {R.drawable.bg_blue_tile, R.drawable.bg_green_tile, 
		R.drawable.bg_red_tile, R.drawable.bg_yellow_tile};
	
	private LinearLayout tutorial_list;
	
	private String mTutorialGoal ;
	private String mTutorialLevel ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tutorial_list);
		
		init();
		
	}
	
	@Override
	protected void onPause() {
		overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
		super.onPause();
	}
	
	private void init(){
		mTutorialGoal = getIntent().getStringExtra(TutorialLevelActivity.TAG_TUTORIAL_GOAL);
		mTutorialLevel = getIntent().getStringExtra(TutorialLevelActivity.TAG_TUTORIAL_LEVEL);
		tutorial_list = (LinearLayout)findViewById(R.id.tutorial_list);
		fillWorkoutsList(mTutorialGoal, mTutorialLevel);
	}
	
	
	private void fillWorkoutsList(String goal, String level) {
		List<M_Workouts> list = T_WorkoutControl.getInstance(this).getWorkoutsList(goal,level);
		LayoutParams lp = new LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, getResources().getDimensionPixelOffset(R.dimen.tutorial_list_item_height));
		lp.setMargins(10, 10, 10, 10);
		
		for (int i=0;i < list.size(); i++) {
//			TextView tv = new TextView(this);
//			int randomIndex = i%TUTORIAL_LIST_BACKGROUND.length;
//			tv.setBackgroundResource(TUTORIAL_LIST_BACKGROUND[randomIndex]);
//			tv.setLayoutParams(lp);
//			tv.setText(list.get(i).title);
//			tv.setVisibility(View.INVISIBLE);
			
			final WorkoutExercisesAdapter adapter = new WorkoutExercisesAdapter(this, list.get(i).exercisesList);
			Gallery gallery = new Gallery(this);
			gallery.setAdapter(adapter);
			int randomIndex = i%TUTORIAL_LIST_BACKGROUND.length;
			gallery.setBackgroundResource(TUTORIAL_LIST_BACKGROUND[randomIndex]);
			//gallery.setLayoutParams(lp);
			gallery.setVisibility(View.INVISIBLE);
			
			tutorial_list.addView(gallery);
		}
		
	}
	
}
