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

package com.project.movice.core.rx;

import android.support.annotation.CallSuper;
import android.text.TextUtils;
import android.util.Log;

import com.project.movice.R;
import com.project.movice.application.MoviceApp;
import com.project.movice.base.view.IView;
import com.project.movice.core.http.BaseResponse;
import com.project.movice.core.http.exception.ServerException;
import com.project.movice.utils.CommonUtils;

import io.reactivex.observers.ResourceObserver;
import retrofit2.HttpException;


public abstract class BaseObserver<T> extends ResourceObserver<BaseResponse<T>> {
    private static final String TAG = "BaseObserver";

    private IView mView;
    private String mErrorMsg;
    private boolean isShowStatusView = true;

    protected BaseObserver(IView view) {
        this.mView = view;
    }

    protected BaseObserver(IView view, String errorMsg) {
        this.mView = view;
        this.mErrorMsg = errorMsg;
    }

    protected BaseObserver(IView view, boolean isShowStatusView) {
        this.mView = view;
        this.isShowStatusView = isShowStatusView;
    }

    protected BaseObserver(IView view, String errorMsg, boolean isShowStatusView) {
        this.mView = view;
        this.mErrorMsg = errorMsg;
        this.isShowStatusView = isShowStatusView;
    }

    public abstract void onSuccess(T t);

    @CallSuper
    public void onFailure(int code, String message) {
        mView.showErrorMsg(message);
    }

    @Override
    protected void onStart() {
        Log.d(TAG, "onStart");
        if (isShowStatusView) {
            mView.showLoading();
        }
    }

    @Override
    public final void onNext(BaseResponse<T> baseResponse) {
        if (baseResponse.getCode() == BaseResponse.SUCCESS) {
            Log.d(TAG, "onSuccess");
            if (isShowStatusView) {
                mView.hideLoading();
                mView.showContent();
            }
            onSuccess(baseResponse.getResult());
        } else {
            Log.d(TAG, "onFailure");
            if (isShowStatusView) {
                mView.hideLoading();
                mView.showContent();
            }
            onFailure(baseResponse.getCode(), baseResponse.getErrorMsg());
        }
    }

    @Override
    public void onComplete() {
        Log.d(TAG, "onComplete");
        if (mView == null) {
            return;
        }
        if (!CommonUtils.isNetworkConnected()) {
            mView.showErrorMsg(MoviceApp.getContext().getString(R.string.http_error));
        }

    }

    @Override
    public void onError(Throwable e) {
        Log.d(TAG, "onError");
        if (mView == null) {
            return;
        }
        if (isShowStatusView) {
            mView.hideLoading();
        }
        if (e instanceof HttpException) {
            mView.showErrorMsg(MoviceApp.getContext().getString(R.string.http_error));
            if (isShowStatusView) {
                mView.showNoNetwork();
            }
        } else if (e instanceof ServerException) {
            mView.showErrorMsg(e.toString());
            if (isShowStatusView) {
                mView.showError();
            }
        } else {
            if (!TextUtils.isEmpty(mErrorMsg)) {
                mView.showErrorMsg(mErrorMsg);
            }
            if (isShowStatusView) {
                mView.showError();
            }
            Log.e(TAG, e.toString());
        }
    }

}
