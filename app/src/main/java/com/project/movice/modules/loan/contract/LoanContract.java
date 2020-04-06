package com.project.movice.modules.loan.contract;

import com.project.movice.base.presenter.IPresenter;
import com.project.movice.base.view.IView;
import com.project.movice.modules.loan.bean.BeanCertificationStatus;
import com.project.movice.modules.loan.bean.InforBean;
import com.project.movice.modules.loan.bean.JokeBean;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.List;
import java.util.Map;

public interface LoanContract {
    interface View extends IView {
        void get012(InforBean bean);
        void  getPermissionsUsccess();
        void permissionsCancle();
    }

    interface Presenter extends IPresenter<LoanContract.View> {

        void request012(Map<String,String> hm);

        void requestPermissions(RxPermissions rxPermissions);
    }
}
