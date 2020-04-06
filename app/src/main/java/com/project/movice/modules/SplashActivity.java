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

package com.project.movice.modules;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.RelativeLayout;

import com.project.movice.R;
import com.project.movice.application.MoviceApp;
import com.project.movice.base.activity.BaseActivity;
import com.project.movice.modules.main.bean.VersionBean;
import com.project.movice.modules.main.contract.SwichContract;
import com.project.movice.modules.main.presenter.SwichPresenter;
import com.project.movice.modules.main.ui.activity.MainActivity;
import com.project.movice.modules.main.ui.activity.SwichActivity;
import com.project.movice.modules.main.ui.activity.WebViewActivity;
import com.project.movice.utils.DataUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author ForgetSky
 * @date 19-2-25
 */
public class SplashActivity extends BaseActivity<SwichPresenter> implements SwichContract.View {

    @Override
    protected void initView() {
        AlphaAnimation alphaAnimation = new AlphaAnimation(0F, 0F);
        alphaAnimation.setDuration(1000);

        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mPresenter.requestVersion();
//                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        findViewById(R.id.layout_splash).startAnimation(alphaAnimation);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initToolbar() {

    }

    @Override
    protected void initEventAndData() {

    }

    @Override
    public void getVersion(VersionBean response) {
        MoviceApp.crawlApp = (response.isCrawlApp());
        DataUtils.put(SplashActivity.this, "logintype", response.getLoginMethod());
        MoviceApp.callPhone = response.getTelephone();
       jumpActivity(response);
    }

    @Override
    public void showErrorData() {
        startActivity(new Intent(SplashActivity.this, MainActivity.class));
        finish();
    }

    private void jumpActivity(VersionBean response) {
        if (response.getIs_loanmarket() == 1) {
            Intent intent = new Intent(SplashActivity.this, WebViewActivity.class);
            intent.putExtra("type", response.getIs_back());
            startActivity(intent);
            finish();
        } else {
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            finish();
        }
    }

    @OnClick({R.id.layout_splash})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_splash://
                mPresenter.requestVersion();
                break;
        }
    }


}
