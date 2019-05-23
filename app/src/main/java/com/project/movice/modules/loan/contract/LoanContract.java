package com.project.movice.modules.loan.contract;

import com.project.movice.base.presenter.IPresenter;
import com.project.movice.base.view.IView;
import com.project.movice.modules.loan.bean.JokeBean;

import java.util.List;

public interface LoanContract {
    interface View extends IView {
        void getDataSuccess(List<JokeBean> list,String data);

    }

    interface Presenter extends IPresenter<LoanContract.View> {

        void getData(String pager, String num);
        void BaseResponse(Object data);
    }
}
