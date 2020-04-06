package com.project.movice.modules.home.presenter;

import com.project.movice.R;
import com.project.movice.application.MoviceApp;
import com.project.movice.base.presenter.BasePresenter;
import com.project.movice.core.rx.BaseObserver;
import com.project.movice.modules.home.bean.BeanBorrowingStatus;
import com.project.movice.modules.home.bean.BeanRefundBank;
import com.project.movice.modules.home.contract.StatusCancelContract;
import com.project.movice.modules.home.contract.StatusRepayContract;
import com.project.movice.utils.FastJsonTools;
import com.project.movice.utils.GsonUtil;
import com.project.movice.utils.RequestBeanUtils;
import com.project.movice.utils.RxUtils;

import java.util.Map;

import javax.inject.Inject;

public class StatusRepayPresenter extends BasePresenter<StatusRepayContract.View> implements StatusRepayContract.Presenter {

    @Inject
    StatusRepayPresenter() {
    }
    @Override
    public void reload() {

    }


    @Override
    public void requestStatusRepay(Map<String, String> params) {
        addSubscribe(mDataManager.get019(RequestBeanUtils.getRequestBean(params))
                .compose(RxUtils.SchedulerTransformer())
                .filter(data -> mView != null)
                .subscribeWith(new BaseObserver(mView,
                        MoviceApp.getContext().getString(R.string.login_fail),
                        true) {
                    @Override
                    public void onSuccess(String data) {

                        BeanRefundBank beanRefundBank= FastJsonTools.parseObject(data, BeanRefundBank.class);

//                        BeanRefundBank beanRefundBank=GsonUtil.GsonToBean(data,BeanRefundBank.class);

                        mView.getStatusRepay(beanRefundBank);
                    }
                }));
    }
}
