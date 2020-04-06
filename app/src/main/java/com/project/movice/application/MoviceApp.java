/*
 *     (C) Copyright 2019, ForgetSky.
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 */

package com.project.movice.application;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.support.multidex.MultiDex;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.project.movice.R;
import com.project.movice.core.DataManager;
import com.project.movice.core.http.api.ApiService;
import com.project.movice.di.component.DaggerAppComponent;
import com.project.movice.di.module.AppModule;
import com.project.movice.di.module.HttpModule;
import com.sobot.chat.SobotApi;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

public class MoviceApp extends Application implements HasActivityInjector {
    public static boolean crawlApp = true;//是否抓取app列表
    public static String callPhone = "";//拨打的客服电话
    public static int loadShowHind = 0;//借款页面的显示按钮
    @Inject
    DispatchingAndroidInjector<Activity> mAndroidInjector;

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return mAndroidInjector;
    }

    private static Context context;
    private RefWatcher refWatcher;
    @Inject
    public DataManager mDataManager;



    public static boolean loanClick = true;//借款页面是否能点


    @Override
    public void onCreate() {
        super.onCreate();
        // Obtain the FirebaseAnalytics instance.

        context = getApplicationContext();
        DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .httpModule(new HttpModule())
                .build().inject(this);
        refWatcher = setupLeakCanary();

        SobotApi.initSobotSDK(this, ApiService.ZHICHI_KEY, getResources().getString(R.string.app_name));
    }

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    private RefWatcher setupLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return RefWatcher.DISABLED;
        }
        return LeakCanary.install(this);
    }

    public static RefWatcher getRefWatcher(Context context) {
        MoviceApp application = (MoviceApp) context.getApplicationContext();
        return application.refWatcher;
    }

    public static Context getContext() {
        return context;
    }


}
