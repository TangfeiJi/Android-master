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

import com.project.movice.R;
import com.project.movice.application.MoviceApp;
import com.project.movice.base.presenter.BasePresenter;
import com.project.movice.core.event.RegisterEvent;
import com.project.movice.core.rx.BaseObserver;
import com.project.movice.modules.login.bean.LoginData;
import com.project.movice.modules.login.contract.RegisterFragmentContract;
import com.project.movice.utils.RxUtils;
import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

/**
 * @author: ForgetSky
 * @date: 2019/3/4
 */
public class RegisterFragmentPresenter extends BasePresenter<RegisterFragmentContract.View> implements RegisterFragmentContract.Presenter{
    @Inject
    RegisterFragmentPresenter() {
    }

    @Override
    public void register(String username, String password, String password2) {

    }
}
