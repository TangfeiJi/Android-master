package com.project.movice.modules.drawname;

import com.project.movice.base.presenter.IPresenter;
import com.project.movice.base.view.IView;
import com.project.movice.modules.loan.bean.BeanPersonalInformation;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.util.Map;

public interface DrawNameContract {
    interface View extends IView {
        void get027();

    }

    interface Presenter extends IPresenter<DrawNameContract.View> {
        void request027(String path, Map<String, String> hm);

    }
}
