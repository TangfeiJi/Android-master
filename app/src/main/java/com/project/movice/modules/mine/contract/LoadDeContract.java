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

package com.project.movice.modules.mine.contract;

import com.project.movice.base.presenter.IPresenter;
import com.project.movice.base.view.IView;
import com.project.movice.modules.mine.bean.BeanLoanDetails;
import com.project.movice.modules.mine.bean.BeanMyLoan;

import java.util.List;
import java.util.Map;

public interface LoadDeContract {
    interface View extends IView {

        void  get031(BeanLoanDetails response);

    }

    interface Presenter extends IPresenter<View> {
        void  request031(Map<String, String> hm);
    }
}
