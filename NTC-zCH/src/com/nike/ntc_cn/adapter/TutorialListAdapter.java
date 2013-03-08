package com.nike.ntc_cn.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nike.ntc_cn.R;
import com.nike.ntc_cn.db.T_WorkoutControl.M_Workouts;

public class TutorialListAdapter extends BaseAdapter{
	protected LayoutInflater mInflater = null;
	private List<M_Workouts> list = null;
	//private Context mContext;
	
	public TutorialListAdapter(Context context, List<M_Workouts> list) {
		this.list = list;
		mInflater = LayoutInflater.from(context);
		//mContext = context;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		final M_Workouts workout = list.get(position);
		if (null == convertView) {
			viewHolder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.tutorial_list_item, null);
			viewHolder.name = (TextView) convertView.findViewById(R.id.workout_name);
			convertView.setTag(viewHolder);
		}
		else
			viewHolder = (ViewHolder) convertView.getTag();
  
		viewHolder.name.setText(workout.name);
		
		return convertView;
	}

	public final class ViewHolder
	{
		public TextView name = null;
	}
	
 
}
