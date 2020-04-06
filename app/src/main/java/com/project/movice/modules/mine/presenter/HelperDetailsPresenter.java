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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.project.movice.R;
import com.project.movice.application.MoviceApp;
import com.project.movice.base.presenter.BasePresenter;
import com.project.movice.core.rx.BaseObserver;
import com.project.movice.modules.main.bean.VersionBean;
import com.project.movice.modules.mine.bean.BeanHelperDetails;
import com.project.movice.modules.mine.bean.BeanMyLoan;
import com.project.movice.modules.mine.contract.HelperDetailsContract;
import com.project.movice.modules.mine.contract.MyLoadContract;
import com.project.movice.utils.FastJsonTools;
import com.project.movice.utils.GsonUtil;
import com.project.movice.utils.RequestBeanUtils;
import com.project.movice.utils.RxUtils;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

public class HelperDetailsPresenter extends BasePresenter<HelperDetailsContract.View>
        implements HelperDetailsContract.Presenter {

    @Inject
    HelperDetailsPresenter() {
    }



    @Override
    public void reload() {

    }

    @Override
    public void request024(Map<String, String> hm) {
        addSubscribe(mDataManager.get024(RequestBeanUtils.getRequestBean(hm))
                .compose(RxUtils.SchedulerTransformer())
                .filter(list -> mView != null)
                .subscribeWith(new BaseObserver(mView,
                        MoviceApp.getContext().getString(R.string.login_fail),
                        true) {
                    @Override
                    public void onSuccess(String data) {

                        BeanHelperDetails bean = FastJsonTools.parseObject(data, BeanHelperDetails.class);

//                        BeanHelperDetails bean = GsonUtil.GsonToBean(data,BeanHelperDetails.class);
                        mView.get024(bean);
                    }
                }));


    }

    @Override
    public void request025(Map<String, String> hm) {
        addSubscribe(mDataManager.get025(RequestBeanUtils.getRequestBean(hm))
                .compose(RxUtils.SchedulerTransformer())
                .filter(list -> mView != null)
                .subscribeWith(new BaseObserver(mView,
                        MoviceApp.getContext().getString(R.string.login_fail),
                        true) {
                    @Override
                    public void onSuccess(String data) {
                        mView.get025();
                    }
                }));
    }
}
