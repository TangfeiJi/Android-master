package com.project.movice.modules.home.contract;

import com.project.movice.base.presenter.IPresenter;
import com.project.movice.base.view.IView;
import com.project.movice.modules.home.bean.BeanRefundBank;

import java.util.Map;

public interface StatusRepayContract {
    interface View extends IView {

        void getStatusRepay(BeanRefundBank data);
    }

    interface Presenter extends IPresenter<StatusRepayContract.View> {

        void requestStatusRepay(Map<String, String> params);
    }
}
