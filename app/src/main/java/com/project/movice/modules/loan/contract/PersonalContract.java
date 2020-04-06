package com.project.movice.modules.loan.contract;

import com.project.movice.base.presenter.IPresenter;
import com.project.movice.base.view.IView;
import com.project.movice.modules.loan.bean.BeanPersonalInformation;

import java.io.File;
import java.util.Map;

public interface PersonalContract {
    interface View extends IView {
        void get006(BeanPersonalInformation data);
        void get054();
    }
    interface Presenter extends IPresenter<PersonalContract.View> {

        void request006(Map<String, String> hm);
        void request054(Map<String, String> hm);
    }
}
