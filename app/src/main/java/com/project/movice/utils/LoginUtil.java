package com.project.movice.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.facebook.FacebookActivity;
import com.project.movice.application.MoviceApp;
import com.project.movice.modules.login.ui.FaceBookLoginActivity;
import com.project.movice.modules.login.ui.LoginActivity;
import com.project.movice.modules.main.ui.activity.SwichActivity;

/**
 * 登录方式切换
 * Created by wy on 2018/12/4.
 */

public class LoginUtil {

    private static final int LOGIN_SMS = 2;//2自己平台短信登录
    private static final int LOGIN_FACEBOOK = 1;//1facebook登录

    public static void login(Context context) {

     int loginType= (int) DataUtils.get(context, "logintype",  2);
        if (loginType == LOGIN_SMS) {
            showActivity(context, LoginActivity.class);
        } else {
            showActivity(context, FaceBookLoginActivity.class);
        }


    }

    public static void showActivity(Context context, Class<?> clazz) {
        showActivity(context, clazz, null);
    }

    public static void showActivity(Context context, Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(context, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
    }



}
