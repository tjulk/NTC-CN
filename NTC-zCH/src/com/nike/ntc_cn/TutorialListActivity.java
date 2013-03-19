package com.nike.ntc_cn;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.nike.ntc_cn.adapter.TutorialListAdapter;
import com.nike.ntc_cn.db.T_WorkoutControl;
import com.nike.ntc_cn.db.T_WorkoutControl.M_Workouts;
import com.nike.ntc_cn.receiver.DownloadBroadcastReceiver;

public class TutorialListActivity extends BaseActivity implements OnItemClickListener{
	
	
	public static final int TUTORIAL_LIST_BACKGROUND[] = {R.drawable.bg_blue_tile, R.drawable.bg_green_tile, 
		R.drawable.bg_red_tile, R.drawable.bg_yellow_tile};
	
	//private LinearLayout tutorial_list;
	private List<M_Workouts> list;
	private ListView tutorial_list;
	
	private String mTutorialGoal ;
	private String mTutorialLevel ;

	private DownloadBroadcastReceiver receiver;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tutorial_list);

	}
	
	@Override
	protected void onResume() {
		super.onResume();
		init();
		
	    IntentFilter intentFilter = new IntentFilter();
	    intentFilter.addAction(DownloadBroadcastReceiver.DOWNLOAD_ACTION); 
	    receiver = new DownloadBroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				super.onReceive(context, intent);
				
				fillWorkoutsList(mTutorialGoal, mTutorialLevel);
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
		intent.putExtra(TutorialDetailActivity.TAG_WORKOUT_NAME,list.get(position).name);  
		intent.putExtra(TutorialDetailActivity.TAG_WORKOUT_ARCHIVE,list.get(position).archive);
		startActivity(intent);
	}
	
}
