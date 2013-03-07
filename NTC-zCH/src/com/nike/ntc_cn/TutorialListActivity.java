package com.nike.ntc_cn;

import android.os.Bundle;

public class TutorialListActivity extends BaseActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);
	}
	@Override
	protected void onPause() {
		overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
		super.onPause();
	}
	
}
