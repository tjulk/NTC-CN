package com.nike.ntc_cn.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nike.ntc_cn.R;
import com.nike.ntc_cn.db.T_ExerciseControl.M_Exercises;
import com.nike.ntc_cn.lazyloader.ImageLoader;

public class TutorialDetailListAdapter extends BaseAdapter{
	protected LayoutInflater mInflater = null;
	private List<M_Exercises> list = null;
	private Context mContext;
	
	private ImageLoader mImageLoader;
	
	private boolean mBusy = false;

	public void setFlagBusy(boolean busy) {
		this.mBusy = busy;
	}
	
	public TutorialDetailListAdapter(Context context, List<M_Exercises> exercises) {
		this.list = exercises;
		mInflater = LayoutInflater.from(context);
		mContext = context;
		
		mImageLoader = new ImageLoader(context);
	}
	
	public ImageLoader getImageLoader(){
		return mImageLoader;
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

	ViewHolder holder = null;
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final M_Exercises exercise = list.get(position);
		if (null == convertView) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.tutorial_detail_list_item, null);
            holder.itemPage = (ImageView) convertView.findViewById(R.id.itemOrder);
            holder.itemName = (TextView) convertView.findViewById(R.id.itemName);
            holder.itemNumber = (TextView) convertView.findViewById(R.id.itemNumber);
            holder.mSearchRankingItemLayout = (RelativeLayout) convertView.findViewById(R.id.searchranking_item_layout);
            convertView.setTag(holder);
		}
		else
			holder = (ViewHolder) convertView.getTag();
  
        holder.itemPage.setBackgroundResource(R.drawable.bg_green_tile);
        holder.itemName.setText(exercise.title);
        holder.itemNumber.setText(exercise.duration+" s");
        int oneOrTwo = ((position%2)==0)?R.drawable.searchranking_selector_1:R.drawable.searchranking_selector_2;
        holder.mSearchRankingItemLayout.setBackgroundResource(oneOrTwo);
        
		mImageLoader.DisplayImage(exercise.name, holder.itemPage, false);
        
		return convertView;
	}

	public final class ViewHolder
	{
        public ImageView itemPage;
        public TextView itemName;
        public TextView itemNumber;
        public RelativeLayout mSearchRankingItemLayout;
	}
	
 
}
