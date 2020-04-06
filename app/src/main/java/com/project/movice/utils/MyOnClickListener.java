package com.project.movice.utils;

import android.view.View;
import android.view.View.OnClickListener;

/**
 * @Description 自定义点击事件
 * @author wy
 * @date 2015-9-23 上午11:41:42
 */
public class MyOnClickListener implements OnClickListener {

	private long lastClick;

	@Override
	public void onClick(View v) {
		if (canClick()) {
			onMyClick(v);
		}
	}

	private boolean canClick() {

		if (System.currentTimeMillis() - lastClick <= 1000 && (System.currentTimeMillis() - lastClick) > 0) {
			return false;
		}
		lastClick = System.currentTimeMillis();
		return true;
	}

	public void onMyClick(View v) {

	}

}
