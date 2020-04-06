package com.project.movice.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.analytics.CampaignTrackingReceiver;
import com.project.movice.utils.Constant;
import com.project.movice.utils.DataUtils;
import com.project.movice.utils.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 渠道统计
 * Created by wy on 2018/5/24 11:33.
 */
public class PlayCampaignReceiver extends BroadcastReceiver {
    public PlayCampaignReceiver() {

    }
    @Override
    public void onReceive(Context context, Intent intent) {
//        new MultipleInstallBroadcastReceiver().onReceive(context, intent);
//        new SingleInstallBroadcastReceiver().onReceive(context, intent);
        new CampaignTrackingReceiver().onReceive(context, intent);//nengyong
        //处理referrer参数
        String referrer = "";
        Bundle extras = intent.getExtras();
        if (extras != null) {
            //utm_source=UangKilat&anid=admob
            try {
                referrer = extras.getString("referrer");
                Map<String, String> map = URLRequest(referrer);
                if(!StringUtils.isEmpty(map.get("utm_source"))) {
                    DataUtils.put(context, Constant.channel, map.get("utm_source"));
                } else if(!StringUtils.isEmpty(map.get("pid"))) {
                    DataUtils.put(context, Constant.channel, map.get("pid"));
                }
                Log.e("wy", "渠道：" + map.get("utm_source"));
                Log.e("wy", "渠道pid：" + map.get("pid"));
                Log.e("wy", "渠道：" + DataUtils.get(context, Constant.channel,"nnull"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public static Map<String, String> URLRequest(String URL) {
        Map<String, String> mapRequest = new HashMap<String, String>();
        String[] arrSplit = null;
        if (URL == null) {
            return mapRequest;
        }
        //每个键值为一组
        try {
            arrSplit = URL.split("[&]");
            for (String strSplit : arrSplit) {
                String[] arrSplitEqual = null;
                arrSplitEqual = strSplit.split("[=]");

                //解析出键值
                if (arrSplitEqual.length > 1) {
                    //正确解析
                    mapRequest.put(arrSplitEqual[0], arrSplitEqual[1]);

                } else {
                    if (arrSplitEqual[0] != "") {
                        //只有参数没有值，不加入
                        mapRequest.put(arrSplitEqual[0], "");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mapRequest;
    }
}
