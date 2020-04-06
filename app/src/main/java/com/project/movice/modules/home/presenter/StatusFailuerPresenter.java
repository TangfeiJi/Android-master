package com.project.movice.modules.home.presenter;

import com.project.movice.R;
import com.project.movice.application.MoviceApp;
import com.project.movice.base.presenter.BasePresenter;
import com.project.movice.core.rx.BaseObserver;
import com.project.movice.modules.home.contract.StatusCancelContract;
import com.project.movice.modules.home.contract.StatusFailuerContract;
import com.project.movice.utils.RequestBeanUtils;
import com.project.movice.utils.RxUtils;

import java.util.Map;

import javax.inject.Inject;

public class StatusFailuerPresenter extends BasePresenter<StatusFailuerContract.View> implements StatusFailuerContract.Presenter {

    @Inject
    StatusFailuerPresenter() {
    }
    @Override
    public void reload() {

    }



    @Override
    public void requestStatusCancel(Map<String, String> params) {
        addSubscribe(mDataManager.get020(RequestBeanUtils.getRequestBean(params))
                .compose(RxUtils.SchedulerTransformer())
                .filter(data -> mView != null)
                .subscribeWith(new BaseObserver(mView,
                        MoviceApp.getContext().getString(R.string.login_fail),
                        true) {
                    @Override
                    public void onSuccess(String data) {
                        mView.getStatusCancel();
                    }
                }));
    }
}
