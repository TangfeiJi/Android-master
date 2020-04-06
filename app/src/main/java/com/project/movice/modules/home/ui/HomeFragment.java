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

package com.project.movice.modules.home.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.FrameLayout;

import com.project.movice.R;
import com.project.movice.application.MoviceApp;
import com.project.movice.base.fragment.BaseFragment;
import com.project.movice.modules.home.base.BeanLoan;
import com.project.movice.modules.home.contract.HomeContract;
import com.project.movice.modules.home.presenter.HomePresenter;
import com.project.movice.utils.EventBusType;
import com.project.movice.utils.MessageEvent;


import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;


public class HomeFragment extends BaseFragment<HomePresenter> implements HomeContract.View {
    //1.一个产品固定金额 2.一个产品可选金额 3.两个产品固定金额 4.两个产品可选金额 5.双产品，可选固定两个金额 6.单产品，可选固定两个金额
    public static final int PRODUCT_TWO = 2;

    @BindView(R.id.flContent)
    FrameLayout flContent;

    Fragment fragment = null;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    protected void initView() {


    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initEventAndData() {
        mPresenter.requestHome();
    }


    @Override
    public void getHome(int type) {
        if (null != fragment) {
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .remove(fragment)
                    .commitNowAllowingStateLoss();
        }
        if (type == EventBusType.BORROWINGONGOING) {//借款中
            fragment = new BorrowingStatusFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("data", mPresenter.getLoanProgress());
            bundle.putString("ewqddweq43", mPresenter.getLoanProgress().getMsg() + "");
            fragment.setArguments(bundle);
            MoviceApp.loanClick = false;
        } else if (type == EventBusType.BORROWING) {//去借款
            MoviceApp.loanClick = true;
            int productType = mPresenter.getLoanProgress().getProduct().getProductType();
            ArrayList<BeanLoan> loan = mPresenter.getLoanProgress().getProduct().getLoan();
            if (productType == PRODUCT_TWO) {
                fragment = new HomeLoanFragment();//一个产品可选金额
            } else {
                fragment = new HomeLoanFragment();//一个产品可选金额
            }

            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("data", loan);
            fragment.setArguments(bundle);

        }

        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.flContent, fragment)
                .commitNowAllowingStateLoss();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(MessageEvent messageEvent) {
        if (messageEvent.getMessage() == EventBusType.UPDATELOAN) {//更新数据
            mPresenter.requestHome();
        } else if (messageEvent.getMessage() == EventBusType.BORROWING) {
            getHome(EventBusType.BORROWING);
        } else if (messageEvent.getMessage() == EventBusType.REFRESH) {
            getHome(EventBusType.BORROWINGONGOING);
        }
    }
}
