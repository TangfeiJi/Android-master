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

package com.project.movice.modules.mine.presenter;

import android.Manifest;

import com.project.movice.R;
import com.project.movice.application.MoviceApp;
import com.project.movice.base.presenter.BasePresenter;
import com.project.movice.core.rx.BaseObserver;
import com.project.movice.modules.mine.bean.ProjectTreeData;
import com.project.movice.modules.mine.contract.MineContract;
import com.project.movice.utils.RxUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.List;

import javax.inject.Inject;

public class MinePresenter extends BasePresenter<MineContract.View>
        implements MineContract.Presenter {

    @Inject
    MinePresenter() {
    }



    @Override
    public void reload() {

    }

    @Override
    public void requestPermissions(RxPermissions rxPermissions) {
        addSubscribe(rxPermissions
                .request(Manifest.permission.CALL_PHONE )
                .subscribe(granted -> {
                    if (granted) {
                        mView.permissionsSuccess();
                    } else {
                        mView.permissionsCancle();
                    }
                }));
    }
}
