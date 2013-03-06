package com.nike.ntc_cn.googleplus;

import java.lang.ref.WeakReference;
import java.util.LinkedList;
import java.util.Queue;

import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;

public class AnimationHelper implements AnimationListener {
	private Queue<QueueItem> mQueue = new LinkedList<QueueItem>();
	private Handler mHanlder = new Handler();
	private boolean isAnimationRunning = false;

	public AnimationHelper() {

	}

	@Override
	public void onAnimationEnd(Animation animation) {

		mHanlder.post(new Runnable() {

			@Override
			public void run() {
				isAnimationRunning = false;
				startAnimationIfApplicable();

			}
		});

	}

	@Override
	public void onAnimationRepeat(Animation animation) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onAnimationStart(Animation animation) {
		mHanlder.post(new Runnable() {

			@Override
			public void run() {
				isAnimationRunning = true;

			}
		});

	}

	public void playAnimation(View v, int animation_id, int animator_id, boolean isQuick) {
		if (v == null)
			throw new IllegalArgumentException("view cant be null");
		mQueue.add(new QueueItem(new WeakReference<View>(v), animation_id,
				animator_id, isQuick));
		startAnimationIfApplicable();
	}

	private void startAnimationIfApplicable() {

		mHanlder.post(new Runnable() {

			@Override
			public void run() {
				if (!isAnimationRunning && mQueue != null && !mQueue.isEmpty()) {
					QueueItem item = mQueue.poll();
					if (item != null && item.mView.get() != null) {
						try {
							Animation mAnimation = AnimationUtils
									.loadAnimation(item.mView.get()
											.getContext(), item.animationId);
							mAnimation
									.setAnimationListener(AnimationHelper.this);
							mAnimation.setRepeatCount(0);
							
							if (item.isQuick)
								mAnimation.setDuration(500);
							else
								mAnimation.setDuration(0);
							
							
							item.mView.get().startAnimation(mAnimation);
							if (Util.isHoneyComb()) {
								ObjectAnimator animator = (ObjectAnimator) AnimatorInflater
										.loadAnimator(item.mView.get()
												.getContext(), item.animatorId);
								animator.setTarget(item.mView.get());
								
								if (item.isQuick)
									animator.setDuration(500);
								else
									animator.setDuration(0);
								
								animator.start();
							}
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					} else {
						startAnimationIfApplicable();
					}

				}

			}
		});
	}

	private static class QueueItem {

		public QueueItem(WeakReference<View> mView, int animationId,
				int animatorId, boolean isQuick) {
			super();
			this.mView = mView;
			this.animationId = animationId;
			this.animatorId = animatorId;
			this.isQuick = isQuick;
		}

		WeakReference<View> mView;
		int animationId;
		int animatorId;
		boolean isQuick;
	}

}
