package com.project.movice.modules.home.presenter;

import android.Manifest;
import android.content.Context;
import android.view.View;

import com.google.gson.Gson;
import com.project.movice.R;
import com.project.movice.application.MoviceApp;
import com.project.movice.base.presenter.BasePresenter;
import com.project.movice.core.rx.BaseObserver;
import com.project.movice.dialog.NoRepaymentDialog;
import com.project.movice.modules.home.base.BeanLoan;
import com.project.movice.modules.home.bean.BeanBorrowingStatus;
import com.project.movice.modules.home.bean.BeanLoanProgress;
import com.project.movice.modules.home.contract.ProductContract;
import com.project.movice.modules.home.contract.StatusCancelContract;
import com.project.movice.modules.main.bean.BeanBase;
import com.project.movice.modules.mycalendar.MyCalenderUtils;
import com.project.movice.utils.Constant;
import com.project.movice.utils.ConstantParams;
import com.project.movice.utils.DataUtils;
import com.project.movice.utils.DateTimeUtil;
import com.project.movice.utils.GsonUtil;
import com.project.movice.utils.RequestBeanUtils;
import com.project.movice.utils.RxUtils;
import com.project.movice.utils.StringUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

public class StatusCancelPresenter extends BasePresenter<StatusCancelContract.View> implements StatusCancelContract.Presenter {

    @Inject
    StatusCancelPresenter() {
    }
    @Override
    public void reload() {

    }



    @Override
    public void requestStatusCancel(Map<String, String> params) {
        addSubscribe(mDataManager.get018(RequestBeanUtils.getRequestBean(params))
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
