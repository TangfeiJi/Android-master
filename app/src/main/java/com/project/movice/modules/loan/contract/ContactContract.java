package com.project.movice.modules.loan.contract;

import com.project.movice.base.presenter.IPresenter;
import com.project.movice.base.view.IView;
import com.project.movice.modules.loan.bean.BeanMyContact;
import com.project.movice.modules.loan.bean.BeanPersonalInformation;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.util.List;
import java.util.Map;

public interface ContactContract {
    interface View extends IView {
        void get014();
        void get008(List<BeanMyContact> contacts);


        void permissionsSuccess(int type);

        void permissionsCancle();
    }

    interface Presenter extends IPresenter<ContactContract.View> {
        void request008(Map<String, String> hm);
        void request014(Map<String, String> hm);
        void requestPermissions(RxPermissions rxPermissions,int type);
    }
}
