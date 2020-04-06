package com.project.movice.modules.home.contract;

import com.project.movice.base.presenter.IPresenter;
import com.project.movice.base.view.IView;

import java.util.Map;

public interface StatusFailuerContract {
    interface View extends IView {

        void getStatusCancel();
    }

    interface Presenter extends IPresenter<StatusFailuerContract.View> {

        void requestStatusCancel(Map<String, String> params);
    }
}
