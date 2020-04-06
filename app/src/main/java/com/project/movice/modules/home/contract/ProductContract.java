package com.project.movice.modules.home.contract;

import android.content.Context;

import com.project.movice.base.presenter.IPresenter;
import com.project.movice.base.view.IView;
import com.project.movice.modules.home.base.BeanLoan;

public interface ProductContract {
    interface View <T> extends IView {
        void setData(T data);
        long getMoney();//借款金额
        int getLoanDays();//借款天数
        int getNumberLoanDays();//天数
        String getBorrowingPurpose();//借款用途
        float getRate();
        float getShowRate();
        float getHeadRest();

        void setMoney(long money);
        void setBorrowingMoney(String money);
        Context getContext();
        /**
         * 还款金额
         * @param payBackMoney
         */
        void setRepaymentMoney(String payBackMoney);
    }

    interface Presenter extends IPresenter<ProductContract.View> {

        void requestProduct();

        void progressChanged(BeanLoan bl, int progress);
    }
}
