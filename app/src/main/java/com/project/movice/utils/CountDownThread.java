package com.project.movice.utils;


import java.util.HashMap;
import java.util.Map;

/**
 * @Description 倒计时管理类
 * @author wy
 * @date 2016-3-23 下午7:47:58
 */
public class CountDownThread implements CountDownInterface {
	Map<String, CountTimer> map = new HashMap<String, CountTimer>();
	public static CountDownThread listFragment;


	CountDownInterface mOnCountDownListening = null;

	public void setOnCountDownListening(CountDownInterface e) {
		mOnCountDownListening = e;
	}

	public static CountDownThread newInstance() {
		if (null == listFragment)
			listFragment = new CountDownThread();
		return listFragment;
	}

	public void startTimer(String key, long millisInFuture, long countDownInterval) {
		CountTimer ct = map.get(key);
		if (null != ct) {// 如果map里面有当初倒计时
			ct.setOnCountDownListening(this);
		} else {
			CountTimer ctimer = new CountTimer(key, millisInFuture, countDownInterval);
			map.put(key, ctimer);
			ctimer.setOnCountDownListening(this);
			ctimer.start();
		}
	}

	/**
	 * @Description 清除倒计时
	 * @author wy
	 * @date 2016-3-24 上午9:59:53
	 * @param key
	 */
	public void cancel(final String key) {
		CountTimer ct = map.get(key);
		if (null != ct) {//
			ct.onCancel();
			map.remove(key);
		}
	}

	@Override
	public void onTick(String key, long millisUntilFinished) {
		mOnCountDownListening.onTick(key, millisUntilFinished);
	}

	@Override
	public void onFinish(String key) {
		map.remove(key);
		mOnCountDownListening.onFinish(key);
	}
}
