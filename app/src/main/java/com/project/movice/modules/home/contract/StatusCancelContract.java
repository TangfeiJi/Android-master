package com.project.movice.modules.home.contract;

import android.content.Context;

import com.project.movice.base.presenter.IPresenter;
import com.project.movice.base.view.IView;
import com.project.movice.modules.home.bean.BeanLoanProgress;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.Map;

public interface StatusCancelContract {
    interface View extends IView {

        void getStatusCancel();

    }

    interface Presenter extends IPresenter<StatusCancelContract.View> {

        void requestStatusCancel(Map<String, String> params);

    }
}
