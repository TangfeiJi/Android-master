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

package com.project.movice.modules.main.presenter;

import android.Manifest;
import android.util.Log;

import com.project.movice.R;
import com.project.movice.application.MoviceApp;
import com.project.movice.base.presenter.BasePresenter;
import com.project.movice.core.event.LoginEvent;
import com.project.movice.core.rx.BaseObserver;
import com.project.movice.modules.main.bean.BeanUser;
import com.project.movice.modules.main.bean.VersionBean;
import com.project.movice.modules.main.contract.MainContract;
import com.project.movice.utils.FastJsonTools;
import com.project.movice.utils.GsonUtil;
import com.project.movice.utils.RequestBeanUtils;
import com.project.movice.utils.RxUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;


import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;


public class MainPresenter extends BasePresenter<MainContract.View> implements MainContract.Presenter {

    @Inject
    MainPresenter() {
    }



    @Override
    public void logout() {

    }

    @Override
    public void requestVersion() {
        Map<String, String> hm = new HashMap<>();
        addSubscribe(mDataManager.getVersion(RequestBeanUtils.getRequestBean(hm))
                .compose(RxUtils.SchedulerTransformer())
                .filter(versionBean -> mView != null)
                .subscribeWith(new BaseObserver(mView,
                        MoviceApp.getContext().getString(R.string.login_fail),
                        true) {
                    @Override
                    public void onSuccess(String data) {

                        VersionBean versionBean = FastJsonTools.parseObject(data, VersionBean.class);

//                        VersionBean versionBean=GsonUtil.GsonToBean(data,VersionBean.class);
                        mView.getVersion(versionBean);
                    }
                }));
    }
        public void requestPermissions(RxPermissions rxPermissions) {
            addSubscribe(rxPermissions
                    .request(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.WRITE_CALENDAR,
                            Manifest.permission.READ_CONTACTS, Manifest.permission.CALL_PHONE, Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CAMERA,Manifest.permission.READ_CALENDAR,Manifest.permission.READ_SMS, Manifest.permission.READ_CALL_LOG)
                    .subscribe(granted -> {
                        if (granted) {
                            mView.getPermissionsUsccess();
                        }
                    }));

        }





}
