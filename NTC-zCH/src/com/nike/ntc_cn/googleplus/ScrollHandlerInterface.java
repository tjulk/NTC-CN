package com.nike.ntc_cn.googleplus;

import android.os.Bundle;

public interface ScrollHandlerInterface {
	public void saveState(Bundle args);

	public void restoreState(Bundle args);

	public boolean didAniamtionPlayedOnChild(int position);
}
