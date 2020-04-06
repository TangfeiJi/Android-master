package com.project.movice.utils;

import android.content.Context;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import com.project.movice.R;
import com.project.movice.application.MoviceApp;

import java.util.concurrent.Executors;

public class PhoneUtils {
    /**
     * 获取软件包名,版本名
     */
    public static String getVersionName( ) {
        try {
          Context  mContext=  MoviceApp.getContext();
            String pkName = mContext.getPackageName();
            String versionName = mContext.getPackageManager().getPackageInfo(
                    pkName, 0).versionName;
            return versionName;
        } catch (Exception e) {
        }
        return null;
    }


    /**
     * 获取App唯一标识
     *
     * @return
     */
    public static String getAppImei() {
        Context  mContext=  MoviceApp.getContext();
        TelephonyManager tm;
        String imei = "";// imei号
//        try {
        imei = getAndroidGaid(mContext);
        Log.e("111111122222",imei);

        return imei;
    }

    public static String getAndroidGaid(final Context context){
        final String[] fingerNum = {""};
        String ANDROID_ID = Settings.System.getString(context.getContentResolver(), Settings.System.ANDROID_ID);
        if (TextUtils.isEmpty(ANDROID_ID)) {
            Executors.newSingleThreadExecutor().execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        String adid = AdvertisingIdClient.getGoogleAdId(context.getApplicationContext());
                        Log.e("MainActivity", "adid:  " + adid);
                        fingerNum[0] =adid;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } else {
            fingerNum[0] =ANDROID_ID;
        }
        return  fingerNum[0];
    }
    /**
     * 获取App名
     *
     * @return
     */
    public static String getAppName() {
        Context  mContext=  MoviceApp.getContext();
        return mContext.getResources().getString(R.string.app_name).replaceAll(" ", "");
    }


    /**
     * 获取App名
     *
     * @return
     */
    public static String getPlatform() {
        Context  mContext=  MoviceApp.getContext();
        return mContext.getResources().getString(R.string.platform).replaceAll(" ", "");
    }
}
