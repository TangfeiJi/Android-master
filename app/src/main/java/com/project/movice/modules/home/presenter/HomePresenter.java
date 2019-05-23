package com.project.movice.modules.home.presenter;

import com.project.movice.R;
import com.project.movice.application.MoviceApp;
import com.project.movice.base.presenter.BasePresenter;
import com.project.movice.core.rx.BaseObserver;
import com.project.movice.modules.home.contract.HomeContract;
import com.project.movice.modules.loan.bean.JokeBean;
import com.project.movice.modules.loan.contract.LoanContract;
import com.project.movice.utils.RxUtils;

import org.simple.eventbus.EventBus;

import java.util.List;

import javax.inject.Inject;

public class HomePresenter extends BasePresenter<HomeContract.View> implements HomeContract.Presenter {
    private Object object;

    @Inject
    HomePresenter() {
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
    public void reload() {

    }
}
