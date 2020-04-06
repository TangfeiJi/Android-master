package com.project.movice.modules.loan.contract;

import com.project.movice.base.presenter.IPresenter;
import com.project.movice.base.view.IView;
import com.project.movice.modules.loan.bean.BeanPersonalInformation;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.util.Map;

public interface BankContract {
    interface View extends IView {
        void get016();
        void showBankName(String bankName);

        /**
         *
         * @param userName 用户名
         * @param bankCardNumber 银行卡号
         * @param bankCardPhone  银行预留手机号
         * @param bankName  银行名称
         */
        void showBank(String userName, String bankCardNumber,
                      String bankCardPhone, String bankName);
    }

    interface Presenter extends IPresenter<BankContract.View> {
        void request006(Map<String, String> hm);
        void request021(Map<String, String> hm);
        void request016(Map<String, String> hm);
        void request010(Map<String, String> hm);

    }
}
