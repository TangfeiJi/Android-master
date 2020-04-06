package com.project.movice.utils;

import android.content.Context;

import com.project.movice.BuildConfig;
import com.project.movice.application.MoviceApp;
import com.project.movice.modules.login.ui.LoginActivity;
import com.project.movice.modules.main.bean.BeanUser;

import java.util.HashMap;
import java.util.Map;

public class RequestBeanUtils {




    public static Map<String,String>  getRequestBean(Map<String,String > hm){
        String channel = (String) DataUtils.get(MoviceApp.getContext(), Constant.channel, BuildConfig.CHANNEL_ID);
        Map<String,String> params=new HashMap<>();
        params.put("v", PhoneUtils.getVersionName());
        params.put("o", "1");
        params.put("cc", "62");
        params.put("i", PhoneUtils.getAppImei());
        params.put("systemVersion", android.os.Build.VERSION.RELEASE);
        params.put("model", android.os.Build.MODEL);
        params.put("systemTime", System.currentTimeMillis() + "");
        params.put("platform", PhoneUtils.getPlatform());
        params.put("phone", DataUtils.getPhone(MoviceApp.getContext()));
        params.put("token",  DataUtils.getToken(MoviceApp.getContext()));
        params.put("channel", channel);
        params.putAll(hm);
        return  params;
    }



}
