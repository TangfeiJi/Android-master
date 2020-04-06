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

import com.project.movice.R;
import com.project.movice.application.MoviceApp;
import com.project.movice.base.presenter.BasePresenter;
import com.project.movice.core.rx.BaseObserver;
import com.project.movice.modules.mine.bean.BeanLoanDetails;
import com.project.movice.modules.mine.bean.BeanMyLoan;
import com.project.movice.modules.mine.contract.LoadDeContract;
import com.project.movice.modules.mine.contract.MyLoadContract;
import com.project.movice.utils.FastJsonTools;
import com.project.movice.utils.RequestBeanUtils;
import com.project.movice.utils.RxUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

public class LoadDePresenter extends BasePresenter<LoadDeContract.View>
        implements LoadDeContract.Presenter {
private Map<String,String> hm=new HashMap<>();
    @Inject
    LoadDePresenter() {
    }



    @Override
    public void reload() {
        request031(hm);
    }

    @Override
    public void request031(Map<String, String> hm) {
        this.hm=hm;
        addSubscribe(mDataManager.get031(RequestBeanUtils.getRequestBean(hm))
                .compose(RxUtils.SchedulerTransformer())
                .filter(list -> mView != null)
                .subscribeWith(new BaseObserver(mView,
                        MoviceApp.getContext().getString(R.string.login_fail),
                        true) {
                    @Override
                    public void onSuccess(String data) {
                        BeanLoanDetails bean=FastJsonTools.parseObject(data,BeanLoanDetails.class);
                        mView.get031(bean);
                    }
                }));


    }
}
