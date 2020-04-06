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

package com.project.movice.base.fragment;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;

import com.classic.common.MultipleStatusView;
import com.project.movice.R;
import com.project.movice.base.presenter.IPresenter;
import com.project.movice.base.view.IView;
import com.project.movice.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class BaseFragment<T extends IPresenter> extends AbstractSimpleFragment implements IView {

    @Inject
    protected T mPresenter;
    private CompositeDisposable compositeDisposable;
    private MultipleStatusView mMultipleStatusView;

    @Override
    public void onAttach(Activity activity) {
        AndroidSupportInjection.inject(this);
        super.onAttach(activity);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        ViewGroup mNormalView = view.findViewById(R.id.normal_view);
//        if (mNormalView != null) {
//            mNormalView.setVisibility(View.GONE);
//        }

        mMultipleStatusView = view.findViewById(R.id.custom_multiple_status_view);
        if (mMultipleStatusView != null) {
            mMultipleStatusView.setOnClickListener(v -> mPresenter.reload());
        }
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }


    }

    @Override
    public void onDestroyView() {
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        hideLoading();
        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (mPresenter != null) {
            mPresenter = null;
        }
    }

    @Override
    public void showErrorMsg(String errorMsg) {
        ToastUtils.showToast(getActivity(), errorMsg);
    }

    @Override
    public void showLoading() {
        if (mMultipleStatusView == null) return;
        mMultipleStatusView.showLoading();

    }

    @Override
    public void hideLoading() {
    }

    @Override
    public void showError() {
        if (mMultipleStatusView == null) return;
        mMultipleStatusView.showError();
    }

    @Override
    public void showNoNetwork() {
        if (mMultipleStatusView == null) return;
        mMultipleStatusView.showNoNetwork();
    }

    @Override
    public void showEmpty() {
        if (mMultipleStatusView == null) return;
        mMultipleStatusView.showEmpty();
    }

    @Override
    public void showContent() {
        if (mMultipleStatusView == null) return;
        mMultipleStatusView.showContent();
    }

    @Override
    public void handleLoginSuccess() {
    }

    @Override
    public void handleLogoutSuccess() {
    }
    protected void addSubscribe(Disposable disposable) {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(disposable);
    }
}
