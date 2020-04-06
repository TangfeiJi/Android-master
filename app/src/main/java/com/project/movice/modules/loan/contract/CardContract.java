package com.project.movice.modules.loan.contract;

import com.project.movice.base.presenter.IPresenter;
import com.project.movice.base.view.IView;
import com.project.movice.modules.loan.bean.BeanJob;
import com.project.movice.modules.loan.bean.BeanPersonalInformation;
import com.project.movice.modules.loan.bean.InforBean;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.util.Map;

public interface CardContract {
    interface View extends IView {
        void get006(BeanPersonalInformation data);
        void get054();
        void get027();
        void permissionsSuccess();

        void permissionsCancle();
    }

    interface Presenter extends IPresenter<CardContract.View> {
        void request027(String path,Map<String, String> hm);
        void request006(Map<String, String> hm);
        void request054(Map<String, String> hm);

        void requestPermissions(RxPermissions rxPermissions);
    }
}
