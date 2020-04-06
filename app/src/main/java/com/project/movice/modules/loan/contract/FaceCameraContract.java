package com.project.movice.modules.loan.contract;

import com.project.movice.base.presenter.IPresenter;
import com.project.movice.base.view.IView;

import java.io.File;
import java.util.Map;

public interface FaceCameraContract {
    interface View extends IView {
        void get027();
    }

    interface Presenter extends IPresenter<FaceCameraContract.View> {

        void request027(String file,Map<String, String> hm);
    }
}
