package com.project.movice.modules.loan.presenter;

import android.Manifest;

import com.project.movice.R;
import com.project.movice.application.MoviceApp;
import com.project.movice.base.presenter.BasePresenter;
import com.project.movice.core.rx.BaseObserver;
import com.project.movice.modules.home.bean.HomeBean;
import com.project.movice.modules.loan.bean.BeanCertificationStatus;
import com.project.movice.modules.loan.bean.BeanJob;
import com.project.movice.modules.loan.bean.InforBean;
import com.project.movice.modules.loan.bean.JokeBean;
import com.project.movice.modules.loan.bean.RequestBean;
import com.project.movice.modules.loan.contract.LoanContract;
import com.project.movice.utils.FastJsonTools;
import com.project.movice.utils.GsonUtil;
import com.project.movice.utils.RequestBeanUtils;
import com.project.movice.utils.RxUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

public class LoanPresenter extends BasePresenter<LoanContract.View> implements LoanContract.Presenter  {
    private Object object;
    @Inject
    LoanPresenter() {
    }


    @Override
    public void reload() {
        request012(new HashMap<>());
    }

    @Override
    public void request012(Map<String, String> hm) {

        addSubscribe(mDataManager.get012(RequestBeanUtils.getRequestBean(hm))
                .compose(RxUtils.SchedulerTransformer())
                .filter(data012 -> mView != null)
                .subscribeWith(new BaseObserver(mView,
                        MoviceApp.getContext().getString(R.string.login_fail),
                        true) {
                    @Override
                    public void onSuccess(String data) {
                        InforBean bean = FastJsonTools.parseObject(data, InforBean.class);


//                        InforBean bean=GsonUtil.GsonToBean(data, InforBean.class);
                        mView.get012(bean);
                    }
                }));

    }

    @Override
    public void requestPermissions(RxPermissions rxPermissions) {
        addSubscribe(rxPermissions
                .request(Manifest.permission.CAMERA)
                .subscribe(granted -> {
                    if (granted) {
                        mView.getPermissionsUsccess();
                    }else{
                        mView.permissionsCancle();
                    }
                }));
    }


}
