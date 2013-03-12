package com.nike.ntc_cn;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;


public class TutorialLevelActivity extends BaseActivity implements OnClickListener {
	
	
	 public static final String TAG_TUTORIAL_GOAL = "TAG_TUTORIAL_GOAL";
	 public static final String TAG_TUTORIAL_LEVEL = "TAG_TUTORIAL_LEVEL";
	
	 
	  public static final String GOAL_NONE = "none";
	  public static final String GOAL_GETLEAN = "lean";
	  public static final String GOAL_GETTONED = "tone";
	  public static final String GOAL_GETSTRONG = "strong";
	  public static final String GOAL_FOCOUS = "focused";
	 
	  public static final String LEVEL_NONE = "none";
	  public static final String LEVEL_PRIMARY = "beginner";
	  public static final String LEVEL_MIDDLE = "intermediate";
	  public static final String LEVEL_HIGH = "advanced";

	  private String mTutorialGoal = GOAL_GETLEAN;
	  private String mTutorialLevel = LEVEL_NONE;

	  private LinearLayout mPrimaryBtn;
	  private LinearLayout mMiddleBtn;
	  private LinearLayout mHighBtn;
	
	  @Override
	  protected void onCreate(Bundle savedInstanceState) {
		overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.tutorial_level);
	    
	    mTutorialGoal = getIntent().getStringExtra(TAG_TUTORIAL_GOAL);
	    
	    mPrimaryBtn = (LinearLayout)findViewById(R.id.primary_btn);
	    mMiddleBtn = (LinearLayout)findViewById(R.id.middle_btn);
	    mHighBtn = (LinearLayout)findViewById(R.id.high_btn);
	    
	    mPrimaryBtn.setOnClickListener(this);
	    mMiddleBtn.setOnClickListener(this);
	    mHighBtn.setOnClickListener(this);
	  }

	@Override
	protected void onPause() {
		overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
		super.onPause();
	}

	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
		case R.id.primary_btn:
			mTutorialLevel = LEVEL_PRIMARY;
			break;
		case R.id.middle_btn:
			mTutorialLevel = LEVEL_MIDDLE;
			break;
		case R.id.high_btn:
			mTutorialLevel = LEVEL_HIGH;			
			break;
		}		
		intent = new Intent(this, TutorialListActivity.class);
		intent.putExtra(TAG_TUTORIAL_GOAL, mTutorialGoal);
		intent.putExtra(TAG_TUTORIAL_LEVEL, mTutorialLevel);
		startActivity(intent);
	}	
	

}
