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

import com.project.movice.R;
import com.project.movice.application.MoviceApp;
import com.project.movice.base.presenter.BasePresenter;
import com.project.movice.core.rx.BaseObserver;
import com.project.movice.modules.main.bean.VersionBean;
import com.project.movice.modules.main.contract.MainContract;
import com.project.movice.modules.main.contract.SwichContract;
import com.project.movice.utils.FastJsonTools;
import com.project.movice.utils.RequestBeanUtils;
import com.project.movice.utils.RxUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;


public class SwichPresenter extends BasePresenter<SwichContract.View> implements SwichContract.Presenter {

    @Inject
    SwichPresenter() {
    }

    @Override
    public void reload() {
        super.reload();
        requestVersion();
    }

    @Override
    public void requestVersion() {
        Map<String, String> hm = new HashMap<>();
        addSubscribe(mDataManager.getVersion(RequestBeanUtils.getRequestBean(hm))
                .compose(RxUtils.SchedulerTransformer())
                .filter(versionBean -> mView != null)
                .subscribeWith(new BaseObserver(mView,
                        MoviceApp.getContext().getString(R.string.login_fail),
                        false) {
                    @Override
                    public void onSuccess(String data) {
                        VersionBean versionBean = FastJsonTools.parseObject(data, VersionBean.class);
//                        VersionBean versionBean=GsonUtil.GsonToBean(data,VersionBean.class);
                        mView.getVersion(versionBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                      mView.showErrorData();

                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);
                        mView.showErrorData();
                    }
                }));
    }





}
