package com.project.movice.modules.loan.contract;

import android.widget.ImageView;
import android.widget.TextView;

import com.project.movice.base.presenter.IPresenter;
import com.project.movice.base.view.IView;
import com.project.movice.modules.loan.bean.BeanJob;
import com.project.movice.modules.loan.bean.BeanPersonalInformation;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.util.Map;

public interface JobInforContract {
    interface View extends IView {
        void get027(String type);
        void get007(BeanJob data);
        void get013();
        void permissionsSuccess(int type);
        void permissionsCancle();
    }

    interface Presenter extends IPresenter<JobInforContract.View> {

        void request027(String type,Map<String, String> hm);

        void request007(Map<String, String> hm);
        void request013(Map<String, String> hm);
        void requestPermissions(RxPermissions rxPermissions,int type);
    }
}
