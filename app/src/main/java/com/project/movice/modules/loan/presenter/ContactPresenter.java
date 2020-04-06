package com.project.movice.modules.loan.presenter;

import android.Manifest;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.project.movice.R;
import com.project.movice.application.MoviceApp;
import com.project.movice.base.presenter.BasePresenter;
import com.project.movice.core.rx.BaseObserver;
import com.project.movice.modules.loan.bean.BeanMyContact;
import com.project.movice.modules.loan.bean.BeanPersonalInformation;
import com.project.movice.modules.loan.contract.CardContract;
import com.project.movice.modules.loan.contract.ContactContract;
import com.project.movice.utils.GsonUtil;
import com.project.movice.utils.RequestBeanUtils;
import com.project.movice.utils.RxUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class ContactPresenter extends BasePresenter<ContactContract.View> implements ContactContract.Presenter  {
    private Object object;
    @Inject
    ContactPresenter() {
    }


    @Override
    public void reload() {
        request008(new HashMap<>());
    }

    @Override
    public void request008(Map<String, String> hm) {
        addSubscribe(mDataManager.get008(RequestBeanUtils.getRequestBean(hm))
                .compose(RxUtils.SchedulerTransformer())
                .filter(data -> mView != null)
                .subscribeWith(new BaseObserver(mView,
                        MoviceApp.getContext().getString(R.string.login_fail),
                        true) {
                    @Override
                    public void onSuccess(String data) {
                        List<BeanMyContact> list = new Gson().fromJson(data, new TypeToken<List<BeanMyContact>>(){}.getType());
                        mView.get008(list);
                    }
                }));
    }

    @Override
    public void request014(Map<String, String> hm) {
        addSubscribe(mDataManager.get014(RequestBeanUtils.getRequestBean(hm))
                .compose(RxUtils.SchedulerTransformer())
                .filter(data -> mView != null)
                .subscribeWith(new BaseObserver(mView,
                        MoviceApp.getContext().getString(R.string.login_fail),
                        true) {
                    @Override
                    public void onSuccess(String data) {
                        mView.get014();
                    }
                }));
    }

    @Override
    public void requestPermissions(RxPermissions rxPermissions,int type) {
        addSubscribe(rxPermissions
                .request(Manifest.permission.READ_CONTACTS )
                .subscribe(granted -> {
                    if (granted) {
                        mView.permissionsSuccess(type);
                    } else {
                        mView.permissionsCancle();
                    }
                }));
    }
}
