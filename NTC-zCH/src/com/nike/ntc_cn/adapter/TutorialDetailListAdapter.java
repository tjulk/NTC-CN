package com.nike.ntc_cn.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nike.ntc_cn.R;
import com.nike.ntc_cn.db.T_ExerciseControl.M_Exercises;
import com.nike.ntc_cn.lazyloader.AsyncImageView;
import com.nike.ntc_cn.lazyloader.ChainImageProcessor;
import com.nike.ntc_cn.lazyloader.ImageProcessor;
import com.nike.ntc_cn.lazyloader.MaskImageProcessor;
import com.nike.ntc_cn.lazyloader.ScaleImageProcessor;

public class TutorialDetailListAdapter extends BaseAdapter{
	protected LayoutInflater mInflater = null;
	private List<M_Exercises> list = null;
	private Context mContext;
	
    private ImageProcessor mImageProcessor;
	
	public TutorialDetailListAdapter(Context context, List<M_Exercises> exercises) {
		this.list = exercises;
		mInflater = LayoutInflater.from(context);
		mContext = context;
		
		prepareImageProcessor(context);
	}
	
    private void prepareImageProcessor(Context context) {
        
        final int thumbnailW =  context.getResources().getDimensionPixelSize(R.dimen.home_navigation_image_default_width);
        final int thumbnailH = context.getResources().getDimensionPixelSize(R.dimen.home_navigation_image_default_height);
        final int thumbnailRadius = 1;
        mImageProcessor = new ChainImageProcessor(
                    new ScaleImageProcessor(thumbnailW, thumbnailH, ScaleType.FIT_CENTER),
                    new MaskImageProcessor(thumbnailRadius));
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
            holder.itemPage = (AsyncImageView) convertView.findViewById(R.id.itemOrder);
            holder.itemName = (TextView) convertView.findViewById(R.id.itemName);
            holder.itemNumber = (TextView) convertView.findViewById(R.id.itemNumber);
            holder.mSearchRankingItemLayout = (RelativeLayout) convertView.findViewById(R.id.searchranking_item_layout);
            
            holder.itemPage.setImageProcessor(mImageProcessor);
            holder.itemPage.setDefaultImageResource(R.drawable.recover_ex_thumb);
            convertView.setTag(holder);
		}
		else
			holder = (ViewHolder) convertView.getTag();
  
        holder.itemName.setText(exercise.title);
        holder.itemNumber.setText(exercise.duration+" s");
        int oneOrTwo = ((position%2)==0)?R.drawable.searchranking_selector_1:R.drawable.searchranking_selector_2;
        holder.mSearchRankingItemLayout.setBackgroundResource(oneOrTwo);
        
        holder.itemPage.setUrl(exercise.thumbnail_medium);
        
		return convertView;
	}

	public final class ViewHolder
	{
        public AsyncImageView itemPage;
        public TextView itemName;
        public TextView itemNumber;
        public RelativeLayout mSearchRankingItemLayout;
	}
	
 
}
