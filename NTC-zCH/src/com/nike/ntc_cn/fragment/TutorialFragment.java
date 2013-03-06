
package com.nike.ntc_cn.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.nike.ntc_cn.R;

public class TutorialFragment extends Fragment implements OnClickListener {
  public static final String TAG = TutorialFragment.class.getSimpleName();

  private static final String ABOUT_SCHEME = "settings";
  private static final String ABOUT_AUTHORITY = "about";
  public static final Uri ABOUT_URI = new Uri.Builder()
  .scheme(ABOUT_SCHEME)
  .authority(ABOUT_AUTHORITY)
  .build();
  
  
  private LinearLayout mGetleanBtn;
  private LinearLayout mGettonedBtn;
  private LinearLayout mGetstrongBtn;
  private LinearLayout mGetforcusdBtn;
  
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
    Bundle savedInstanceState) {

    final View v = inflater.inflate(R.layout.tutorial, container, false);
    mGetleanBtn = (LinearLayout)v.findViewById(R.id.getlean_btn);
    mGettonedBtn = (LinearLayout)v.findViewById(R.id.gettoned_btn);
    mGetstrongBtn = (LinearLayout)v.findViewById(R.id.getstrong_btn);
    mGetforcusdBtn = (LinearLayout)v.findViewById(R.id.getfocused_btn);
    
    mGetleanBtn.setOnClickListener(this);
    mGettonedBtn.setOnClickListener(this);
    mGetstrongBtn.setOnClickListener(this);
    mGetforcusdBtn.setOnClickListener(this);
    return v;
  }

@Override
public void onClick(View v) {
	switch (v.getId()) {
	case R.id.getlean_btn:
		Toast.makeText(getActivity(), "test", Toast.LENGTH_SHORT).show();
		break;
	case R.id.gettoned_btn:
		
		break;
	case R.id.getstrong_btn:
		
		break;
	case R.id.getfocused_btn:
		
		break;
		
	default:
		break;
	}
}
}
