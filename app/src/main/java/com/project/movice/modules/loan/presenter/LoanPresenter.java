package com.project.movice.modules.loan.presenter;

import com.project.movice.R;
import com.project.movice.application.MoviceApp;
import com.project.movice.base.presenter.BasePresenter;
import com.project.movice.core.rx.BaseObserver;
import com.project.movice.modules.loan.bean.JokeBean;
import com.project.movice.modules.loan.bean.RequestBean;
import com.project.movice.modules.loan.contract.LoanContract;
import com.project.movice.utils.RxUtils;

import org.simple.eventbus.EventBus;

import java.util.List;

import javax.inject.Inject;

public class LoanPresenter extends BasePresenter<LoanContract.View> implements LoanContract.Presenter  {
    private Object object;
    @Inject
    LoanPresenter() {
    }

    @Override
    public void registerEventBus() {
        EventBus.getDefault().register(this);
    }

    @Override
    public void unregisterEventBus() {
        EventBus.getDefault().unregister(this);
    }
    @Override
    public void getData(String pager, String num) {
        addSubscribe(mDataManager.getData(pager, num)
                .compose(RxUtils.SchedulerTransformer())
                .filter(jokeData -> mView != null)
                .subscribeWith(new BaseObserver<List<JokeBean>>(mView,
                        MoviceApp.getContext().getString(R.string.login_fail),
                        true) {
                    @Override
                    public void onSuccess(List<JokeBean> list) {
                        mView.getDataSuccess(list,"");
                    }
                }));

    }

    @Override
    public void BaseResponse(Object data) {
        object=data;
        addSubscribe(mDataManager.requestTicketsData(data)
                .compose(RxUtils.SchedulerTransformer())
                .filter(jokeData -> mView != null)
                .subscribeWith(new BaseObserver<List<JokeBean>>(mView,
                        MoviceApp.getContext().getString(R.string.login_fail),
                        true) {
                    @Override
                    public void onSuccess(List<JokeBean> list) {
                        mView.getDataSuccess(list,((RequestBean)data).toString());
                    }
                }));
    }
    @Override
    public void reload() {
        BaseResponse( object);
    }
}
