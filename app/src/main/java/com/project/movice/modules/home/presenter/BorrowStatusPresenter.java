package com.project.movice.modules.home.presenter;

import android.Manifest;

import com.project.movice.R;
import com.project.movice.application.MoviceApp;
import com.project.movice.base.presenter.BasePresenter;
import com.project.movice.core.rx.BaseObserver;
import com.project.movice.modules.home.bean.HomeBean;
import com.project.movice.modules.home.contract.BorrowStatusContract;
import com.project.movice.modules.home.contract.HomeContract;
import com.project.movice.utils.FastJsonTools;
import com.project.movice.utils.RequestBeanUtils;
import com.project.movice.utils.RxUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

public class BorrowStatusPresenter extends BasePresenter<BorrowStatusContract.View> implements BorrowStatusContract.Presenter {

    @Inject
    BorrowStatusPresenter() {
    }


    @Override
    public void reload() {
    }



    @Override
    public void requestPermissions(RxPermissions rxPermissions) {


        addSubscribe(rxPermissions
                .request(Manifest.permission.READ_CALENDAR,Manifest.permission.WRITE_CALENDAR)
                .subscribe(granted -> {
                    if (granted) {
                        mView.getPermissionsUsccess();
                    }
                }));
    }


}
