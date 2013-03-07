package com.nike.ntc_cn;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;


public class TutorialLevelActivity extends BaseActivity implements OnClickListener {

	  private LinearLayout mPrimaryBtn;
	  private LinearLayout mMiddleBtn;
	  private LinearLayout mHighBtn;
	
	  @Override
	  protected void onCreate(Bundle savedInstanceState) {
		overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.tutorial_level);
	    setTitle("瘦身");
	    
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
		switch (v.getId()) {
		case R.id.primary_btn:
			startActivity(new Intent(this, TutorialListActivity.class));
			break;
		case R.id.middle_btn:
			startActivity(new Intent(this, TutorialListActivity.class));
			break;
		case R.id.high_btn:
			startActivity(new Intent(this, TutorialListActivity.class));
			break;
			
		default:
			break;
		}		
	}	
	

}
