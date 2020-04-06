package com.project.movice.modules.loan.contract;

import android.content.Context;

import com.project.movice.base.presenter.IPresenter;
import com.project.movice.base.view.IView;
import com.project.movice.modules.loan.bean.BeanBankCard;
import com.project.movice.modules.loan.bean.BeanPersonalInformation;
import com.project.movice.modules.loan.bean.BorrowingInformation;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.util.Map;

public interface ContractContract {
    interface View extends IView {
        void get006(BeanPersonalInformation data);
        void permissionsSuccess();
        void permissionsCancle();
        void loanProtocol(BeanPersonalInformation info, BeanBankCard bank, BorrowingInformation loan, boolean isShow);
        void showInfo(String bankCardNumber, String userName, int loanDays, long borrowingMoney, String getMoney, String payBackMoney, float rate, long managementFee);
        String getSelect();
        void finishActivity();
    }

    interface Presenter extends IPresenter<ContractContract.View> {
        void request006(Map<String, String> hm, Context context);
        void request010(Map<String, String> hm);
        void request049(Map<String, String> hm);
        void request0122(Map<String, String> hm);
        void request034(Map<String, String> hm);
        void requestPermissions(RxPermissions rxPermissions);
    }
}
