package com.project.movice.modules.home.contract;

import com.project.movice.base.presenter.IPresenter;
import com.project.movice.base.view.IView;
import com.project.movice.modules.loan.bean.JokeBean;

import java.util.List;

public interface HomeContract {
    interface View extends IView {

        void getHome(int type);
    }

    interface Presenter extends IPresenter<HomeContract.View> {

        void requestHome();
    }
}
