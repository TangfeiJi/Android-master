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

package com.project.movice.modules.loan.ui;

import android.util.Log;

import com.project.movice.R;
import com.project.movice.base.fragment.BaseFragment;
import com.project.movice.modules.loan.bean.JokeBean;
import com.project.movice.modules.loan.bean.RequestBean;
import com.project.movice.modules.loan.contract.LoanContract;
import com.project.movice.modules.loan.presenter.LoanPresenter;
import com.project.movice.utils.ToastUtils;

import java.util.List;


public class LoanFragment extends BaseFragment<LoanPresenter> implements LoanContract.View {

    private static final String TAG = "LoanFragment";
    public static LoanFragment newInstance() {
        LoanFragment fragment = new LoanFragment();
        //在此处传递参数，可在fragment恢复时使用；避免在构造函数中传参，fragment恢复时不调用非默认构造函数
//        Bundle args = new Bundle();
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_loan;
    }

    @Override
    protected void initEventAndData() {
//        mPresenter.getData("1","10");


        mPresenter.BaseResponse(new RequestBean("1","10"));
    }


    @Override
    protected void initView() {

    }



    @Override
    public void getDataSuccess(List<JokeBean> list, String data) {
        Log.e("111111111",list.size()+"-----"+data);
        ToastUtils.showToast(getActivity(),list.size()+"-----");
    }
}
