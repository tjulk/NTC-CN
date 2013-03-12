package com.nike.ntc_cn;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.nike.ntc_cn.adapter.TutorialListAdapter;
import com.nike.ntc_cn.db.T_WorkoutControl;
import com.nike.ntc_cn.db.T_WorkoutControl.M_Workouts;

public class TutorialListActivity extends BaseActivity implements OnItemClickListener{
	
	public static final String TAG_WORKOUT_NAME = "tag_workout_name";
	
	public static final int TUTORIAL_LIST_BACKGROUND[] = {R.drawable.bg_blue_tile, R.drawable.bg_green_tile, 
		R.drawable.bg_red_tile, R.drawable.bg_yellow_tile};
	
	//private LinearLayout tutorial_list;
	private List<M_Workouts> list;
	private ListView tutorial_list;
	
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
		tutorial_list = (ListView)findViewById(R.id.tutorial_list);
		tutorial_list.setOnItemClickListener(this);
		
		fillWorkoutsList(mTutorialGoal, mTutorialLevel);
	}
	
	
	private void fillWorkoutsList(String goal, String level) {
		list = T_WorkoutControl.getInstance(this).getWorkoutsList(goal,level);
		TutorialListAdapter adapter = new TutorialListAdapter(this, list);
		tutorial_list.setAdapter(adapter);
		
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		Intent intent = new Intent(this, TutorialDetailActivity.class);
		intent.putExtra(TAG_WORKOUT_NAME, list.get(position).name);
		startActivity(intent);
	}
	
}
