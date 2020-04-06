package com.project.movice.utils;

import android.content.Context;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;

import java.util.concurrent.Executors;

public class GetAndroidIdGAid {



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
}
