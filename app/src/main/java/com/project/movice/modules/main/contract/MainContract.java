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

package com.project.movice.modules.main.contract;

import com.project.movice.base.presenter.IPresenter;
import com.project.movice.base.view.IView;
import com.project.movice.modules.main.bean.VersionBean;
import com.tbruyelle.rxpermissions2.RxPermissions;

public interface MainContract {
    interface View extends IView {
       void  getVersion(VersionBean versionBean);


       void  getPermissionsUsccess();
    }

    interface Presenter extends IPresenter<View> {
        void logout();
        void requestVersion();

        void requestPermissions(RxPermissions rxPermissions);
    }
}
