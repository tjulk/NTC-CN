package com.nike.ntc_cn.adapter;

import java.util.List;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.SpinnerAdapter;

import com.nike.ntc_cn.R;
import com.nike.ntc_cn.db.T_ExerciseControl.M_Exercises;
import com.nike.ntc_cn.utils.Utils;

/**
 * @ClassName: WorkoutExercisesAdapter 
 * @Description: workout-exercises 列表 adapter
 * @author LEIKANG 
 * @date 2013-3-11 下午5:26:38
 */
public class WorkoutExercisesAdapter implements SpinnerAdapter{
	
	private List<M_Exercises> mList;
	private Context mContext;
	
	public WorkoutExercisesAdapter (Context context, List<M_Exercises> list) {
		mList = list;
		mContext = context;
	}

	@Override
	public int getCount() {
		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public int getItemViewType(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		   ImageView i = new ImageView(mContext);
		   i.setLayoutParams(new Gallery.LayoutParams(300, 400));
		   
		   //i.setImageBitmap(Utils.createBitmapByName(mList.get(position).exercisePages.get(0).background_image));
		   i.setImageResource(R.drawable.aaa);
		   
		   return i;
	}

	@Override
	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void registerDataSetObserver(DataSetObserver observer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unregisterDataSetObserver(DataSetObserver observer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		return null;
	}

}
