package com.project.movice.modules.home.presenter;

import android.util.Log;

import com.project.movice.R;
import com.project.movice.application.MoviceApp;
import com.project.movice.base.presenter.BasePresenter;
import com.project.movice.core.rx.BaseObserver;
import com.project.movice.modules.home.bean.HomeBean;
import com.project.movice.modules.home.contract.HomeContract;
import com.project.movice.modules.loan.bean.JokeBean;
import com.project.movice.modules.loan.contract.LoanContract;
import com.project.movice.modules.main.bean.VersionBean;
import com.project.movice.utils.FastJsonTools;
import com.project.movice.utils.GsonUtil;
import com.project.movice.utils.RequestBeanUtils;
import com.project.movice.utils.RxUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

public class HomePresenter extends BasePresenter<HomeContract.View> implements HomeContract.Presenter {
    HomeBean homeBean;

    @Inject
    HomePresenter() {
    }


    @Override
    public void reload() {
        requestHome();
    }

    @Override
    public void requestHome() {
        Map<String, String> hm = new HashMap<>();
        addSubscribe(mDataManager.getHome(RequestBeanUtils.getRequestBean(hm))
                .compose(RxUtils.SchedulerTransformer())
                .filter(data -> mView != null)
                .subscribeWith(new BaseObserver(mView,
                        MoviceApp.getContext().getString(R.string.login_fail),
                        true) {
                    @Override
                    public void onSuccess(String data) {
                        homeBean = FastJsonTools.parseObject(data, HomeBean.class);
//                        homeBean = GsonUtil.GsonToBean(data, HomeBean.class);
                        mView.getHome(homeBean.getType());
                    }
                }));
    }


    public HomeBean getLoanProgress() {
        return homeBean;
    }


}
