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

package com.project.movice.modules.login.contract;

import com.project.movice.base.presenter.IPresenter;
import com.project.movice.base.view.IView;

/**
 * @author: ForgetSky
 * @date: 2019/3/4
 */
public interface RegisterFragmentContract {
    interface View extends IView {
        void registerSuccess();
    }

    interface Presenter extends IPresenter<View> {
        void register(String username, String password, String password2);
    }
}
