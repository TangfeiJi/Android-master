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

package com.project.movice.modules.login.presenter;

import android.Manifest;
import android.content.Context;

import com.project.movice.R;
import com.project.movice.application.MoviceApp;
import com.project.movice.base.presenter.BasePresenter;
import com.project.movice.core.rx.BaseObserver;
import com.project.movice.modules.home.bean.HomeBean;
import com.project.movice.modules.loan.bean.BeanPersonalInformation;
import com.project.movice.modules.login.bean.BeanSms;
import com.project.movice.modules.login.contract.LoginContract;
import com.project.movice.modules.main.bean.BeanUser;
import com.project.movice.modules.main.ui.activity.MainActivity;
import com.project.movice.modules.mycalendar.MyCalenderUtils;
import com.project.movice.utils.DataUtils;
import com.project.movice.utils.FastJsonTools;
import com.project.movice.utils.GsonUtil;
import com.project.movice.utils.RequestBeanUtils;
import com.project.movice.utils.RxUtils;
import com.project.movice.utils.StringUtils;
import com.sobot.chat.utils.ToastUtil;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

/**
 * @author: ForgetSky
 * @date: 2019/3/4
 */
public class LoginPresenter extends BasePresenter<LoginContract.View> implements LoginContract.Presenter{
    @Inject
    LoginPresenter() {
    }

    @Override
    public void request002(Map<String, String> hm) {
        addSubscribe(mDataManager.get002(RequestBeanUtils.getRequestBean(hm))
                .compose(RxUtils.SchedulerTransformer())
                .filter(data -> mView != null)
                .subscribeWith(new BaseObserver(mView,
                        MoviceApp.getContext().getString(R.string.login_fail),
                        true) {
                    @Override
                    public void onSuccess(String data) {
                        BeanUser bean = FastJsonTools.parseObject(data, BeanUser.class);
//                        BeanUser Bean = GsonUtil.GsonToBean(data, BeanUser.class);

                        mView.get002(bean);
                    }
                }));
    }

    @Override
    public void request001(Map<String, String> hm) {
        addSubscribe(mDataManager.get001(RequestBeanUtils.getRequestBean(hm))
                .compose(RxUtils.SchedulerTransformer())
                .filter(data -> mView != null)
                .subscribeWith(new BaseObserver(mView,
                        MoviceApp.getContext().getString(R.string.login_fail),
                        true) {
                    @Override
                    public void onSuccess(String data) {
                        BeanSms bean = FastJsonTools.parseObject(data, BeanSms.class);
                        mView.get001(bean);
                    }
                }));
    }

    @Override
    public void requestPermissions(RxPermissions rxPermissions, Context context) {
        addSubscribe(rxPermissions
                .request(Manifest.permission.READ_CALENDAR,Manifest.permission.WRITE_CALENDAR )
                .subscribe(granted -> {
                    if (granted) {
                        MyCalenderUtils.registNotify(context);
                    } else {
                    }
                }));
    }


}
