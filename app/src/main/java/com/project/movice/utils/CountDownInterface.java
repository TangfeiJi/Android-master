package com.project.movice.utils;

/**
 * 倒计时回调
 * Created by wy on 2018/1/9 14:04.
 */


public interface CountDownInterface {
    void onTick(String key, long millisUntilFinished);
    void onFinish(String key);
}
