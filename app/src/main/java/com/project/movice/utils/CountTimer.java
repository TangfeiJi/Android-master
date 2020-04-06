package com.project.movice.utils;

import android.os.CountDownTimer;

/**
 * @类描述 倒计时
 * @创建者 wy
 * @创建时间 2014-6-13 上午11:34:26
 */
public class CountTimer extends CountDownTimer {

	private String key;


	CountDownInterface mOnCountDownListening = null;

	public void setOnCountDownListening(CountDownInterface e) {
		mOnCountDownListening = e;
	}

	public CountTimer(String key, long millisInFuture, long countDownInterval) {
		super(millisInFuture, countDownInterval);
		this.key = key;
	}

	@Override
	public void onTick(long millisUntilFinished) {
		if (null != mOnCountDownListening) {
			mOnCountDownListening.onTick(key ,millisUntilFinished);
		}
		// if (handler != null) {
		// Message ewqddweq43 = new Message();
		// ewqddweq43.what = 1;
		// ewqddweq43.obj = millisUntilFinished;
		// handler.sendMessage(ewqddweq43);
		// }
	}

	@Override
	public void onFinish() {
		// if (handler != null) {
		// Message ewqddweq43 = new Message();
		// ewqddweq43.what = -1;
		// handler.sendMessage(ewqddweq43);
		// }
		if (null != mOnCountDownListening) {
			mOnCountDownListening.onFinish(key);
		}
	}

	public void onCancel() {
		this.cancel();
		this.onFinish();
	}

}
