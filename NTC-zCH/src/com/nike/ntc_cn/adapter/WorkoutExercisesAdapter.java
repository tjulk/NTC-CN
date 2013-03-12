//package com.nike.ntc_cn.adapter;
//
//import java.util.List;
//
//import android.content.Context;
//import android.database.DataSetObserver;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView.ScaleType;
//import android.widget.SpinnerAdapter;
//import android.widget.TextView;
//
//import com.nike.ntc_cn.R;
//import com.nike.ntc_cn.asyncimage.AsyncImageView;
//import com.nike.ntc_cn.asyncimage.ChainImageProcessor;
//import com.nike.ntc_cn.asyncimage.ImageProcessor;
//import com.nike.ntc_cn.asyncimage.MaskImageProcessor;
//import com.nike.ntc_cn.asyncimage.ScaleImageProcessor;
//import com.nike.ntc_cn.db.T_ExerciseControl.M_Exercises;
//import com.nike.ntc_cn.utils.Utils;
//
///**
// * @ClassName: WorkoutExercisesAdapter 
// * @Description: workout-exercises 列表 adapter
// * @author LEIKANG 
// * @date 2013-3-11 下午5:26:38
// */
//public class WorkoutExercisesAdapter implements SpinnerAdapter{
//	protected LayoutInflater mInflater = null;
//	private List<M_Exercises> mList;
//	private Context mContext;
//    private ImageProcessor mImageProcessor;
//	public WorkoutExercisesAdapter (Context context, List<M_Exercises> list) {
//		mList = list;
//		mContext = context;
//		mInflater = LayoutInflater.from(context);
//		prepareImageProcessor(context);
//	}
//	
//    private void prepareImageProcessor(Context context) {
//        
//        final int thumbnailRadius = 1;
//
//            mImageProcessor = new ChainImageProcessor(
//                    new ScaleImageProcessor(200, 200, ScaleType.CENTER),
//                    new MaskImageProcessor(thumbnailRadius));
// 
//    }
//
//	@Override
//	public int getCount() {
//		return mList.size();
//	}
//
//	@Override
//	public Object getItem(int position) {
//		return mList.get(position);
//	}
//
//	@Override
//	public long getItemId(int position) {
//		return position;
//	}
//
//	@Override
//	public int getItemViewType(int position) {
//		return 0;
//	}
//
//	@Override
//	public View getView(int position, View convertView, ViewGroup parent) {
//		   
//			ViewHolder viewHolder = null;
//			
//			if (null == convertView) {
//				viewHolder = new ViewHolder();
//				convertView = mInflater.inflate(R.layout.workout_exercises_list_item, null);
//				viewHolder.image = (AsyncImageView) convertView.findViewById(R.id.exercise_page);
//				
//				viewHolder.image.setImageProcessor(mImageProcessor);
//				
//				convertView.setTag(viewHolder);
//			}
//			else
//				viewHolder = (ViewHolder) convertView.getTag();
//			
//			if (mList.get(position).exercisePages.size()>0)
//				viewHolder.image.setUrl("images/"+mList.get(position).exercisePages.get(0).background_image+".jpg");
//			return convertView;
//	}
//	
//	
//	public final class ViewHolder
//	{
//		public AsyncImageView image = null;
//		public TextView text = null;
//	}
//	
//
//	@Override
//	public int getViewTypeCount() {
//		return 0;
//	}
//
//	@Override
//	public boolean hasStableIds() {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public boolean isEmpty() {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public void registerDataSetObserver(DataSetObserver observer) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void unregisterDataSetObserver(DataSetObserver observer) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public View getDropDownView(int position, View convertView, ViewGroup parent) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//}
