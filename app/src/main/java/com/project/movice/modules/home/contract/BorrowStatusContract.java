package com.project.movice.modules.home.contract;

import com.project.movice.base.presenter.IPresenter;
import com.project.movice.base.view.IView;
import com.tbruyelle.rxpermissions2.RxPermissions;

public interface BorrowStatusContract {
    interface View extends IView {
        void  getPermissionsUsccess();
    }

    interface Presenter extends IPresenter<BorrowStatusContract.View> {
        void requestPermissions(RxPermissions rxPermissions);
    }
}
