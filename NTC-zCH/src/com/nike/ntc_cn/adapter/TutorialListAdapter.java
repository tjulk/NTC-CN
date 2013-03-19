package com.nike.ntc_cn.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nike.ntc_cn.R;
import com.nike.ntc_cn.db.T_WorkoutControl.M_Workouts;

public class TutorialListAdapter extends BaseAdapter{
	protected LayoutInflater mInflater = null;
	private List<M_Workouts> list = null;
	private Context mContext;
	
	public TutorialListAdapter(Context context, List<M_Workouts> list) {
		this.list = list;
		mInflater = LayoutInflater.from(context);
		mContext = context;
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
		ViewHolder holder = null;
		final M_Workouts workout = list.get(position);
		if (null == convertView) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.tutorial_list_item, null);
            holder.itemOrder = (TextView) convertView.findViewById(R.id.itemOrder);
            holder.itemName = (TextView) convertView.findViewById(R.id.itemName);
            holder.itemNumber = (TextView) convertView.findViewById(R.id.itemNumber);
            holder.mSearchRankingItemLayout = (RelativeLayout) convertView.findViewById(R.id.searchranking_item_layout);
            convertView.setTag(holder);
		}
		else
			holder = (ViewHolder) convertView.getTag();
  
		String archiveTitle = "";
		
		if(M_Workouts.ARCHIVE_DOWNLOADED.equals(workout.archive))
			archiveTitle = "(已下载)";
		else if (M_Workouts.ARCHIVE_DOWNLOADING.equals(workout.archive))
			archiveTitle = "(下载中)";
		else if (M_Workouts.ARCHIVE_STANDARD.equals(workout.archive))
			archiveTitle = "(未下载)";
        holder.itemOrder.setText(""+(position+1));
        holder.itemName.setText(workout.title + archiveTitle);
        holder.itemNumber.setText(workout.duration+" 分");
        int oneOrTwo = ((position%2)==0)?R.drawable.searchranking_selector_2:R.drawable.searchranking_selector_1;
        holder.mSearchRankingItemLayout.setBackgroundResource(oneOrTwo);
		
		return convertView;
	}

	public final class ViewHolder
	{
        public TextView itemOrder;
        public TextView itemName;
        public TextView itemNumber;
        public RelativeLayout mSearchRankingItemLayout;
	}
	
 
}
