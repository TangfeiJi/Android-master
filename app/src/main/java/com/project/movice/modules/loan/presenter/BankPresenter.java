package com.project.movice.modules.loan.presenter;

import android.Manifest;
import android.content.Context;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.project.movice.R;
import com.project.movice.application.MoviceApp;
import com.project.movice.base.presenter.BasePresenter;
import com.project.movice.core.rx.BaseObserver;
import com.project.movice.dialog.ListDialog;
import com.project.movice.dialog.OnItemClickListener;
import com.project.movice.modules.home.bean.BeanBank;
import com.project.movice.modules.home.bean.BeanRefundBank;
import com.project.movice.modules.loan.bean.BeanBankCard;
import com.project.movice.modules.loan.bean.BeanMyContact;
import com.project.movice.modules.loan.bean.BeanPersonalInformation;
import com.project.movice.modules.loan.contract.BankContract;
import com.project.movice.modules.loan.contract.CardContract;
import com.project.movice.utils.DataUtils;
import com.project.movice.utils.FastJsonTools;
import com.project.movice.utils.GsonUtil;
import com.project.movice.utils.RequestBeanUtils;
import com.project.movice.utils.RxUtils;
import com.project.movice.utils.StringUtils;
import com.project.movice.widget.behavior.ModifyBankDialog;
import com.sobot.chat.utils.ToastUtil;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class BankPresenter extends BasePresenter<BankContract.View> implements BankContract.Presenter {
    private Object object;
    private List<BeanBank> bankList;
    private BeanBankCard bank;
    private BeanPersonalInformation personalInformation;

    @Inject
    BankPresenter() {
    }


    @Override
    public void reload() {
        request006(new HashMap<>());
    }

    @Override
    public void request006(Map<String, String> hm) {
        addSubscribe(mDataManager.get006(RequestBeanUtils.getRequestBean(hm))
                .compose(RxUtils.SchedulerTransformer())
                .filter(data -> mView != null)
                .subscribeWith(new BaseObserver(mView,
                        MoviceApp.getContext().getString(R.string.login_fail),
                        true) {
                    @Override
                    public void onSuccess(String data) {

                        BeanPersonalInformation bean= FastJsonTools.parseObject(data, BeanPersonalInformation.class);

//                        BeanPersonalInformation bean = GsonUtil.GsonToBean(data, BeanPersonalInformation.class);
                        personalInformation = bean;
                        request021(hm);
                    }
                }));

    }

    @Override
    public void request021(Map<String, String> hm) {

        addSubscribe(mDataManager.get021(RequestBeanUtils.getRequestBean(hm))
                .compose(RxUtils.SchedulerTransformer())
                .filter(data -> mView != null)
                .subscribeWith(new BaseObserver(mView,
                        MoviceApp.getContext().getString(R.string.login_fail),
                        true) {
                    @Override
                    public void onSuccess(String data) {
                        List<BeanBank> list = new Gson().fromJson(data, new TypeToken<List<BeanBank>>() {
                        }.getType());
                        bankList = list;
                        request010(hm);
                    }
                }));

    }

    @Override
    public void request016(Map<String, String> hm) {
        addSubscribe(mDataManager.get016(RequestBeanUtils.getRequestBean(hm))
                .compose(RxUtils.SchedulerTransformer())
                .filter(data -> mView != null)
                .subscribeWith(new BaseObserver(mView,
                        MoviceApp.getContext().getString(R.string.login_fail),
                        true) {
                    @Override
                    public void onSuccess(String data) {
                        mView.get016();
                    }
                }));
    }

    @Override
    public void request010(Map<String, String> hm) {
        addSubscribe(mDataManager.get010(RequestBeanUtils.getRequestBean(hm))
                .compose(RxUtils.SchedulerTransformer())
                .filter(data -> mView != null)
                .subscribeWith(new BaseObserver(mView,
                        MoviceApp.getContext().getString(R.string.login_fail),
                        true) {
                    @Override
                    public void onSuccess(String data) {
                        BeanBankCard bean = GsonUtil.GsonToBean(data, BeanBankCard.class);
                        bank = bean;
                        showBank(bank);

                    }
                }));
    }

    private void showBank(BeanBankCard bank) {
        String bankName = null;
        if (null != bank) {
            for (int i = 0; i < bankList.size(); i++) {//通过银行code显示银行名
                if (bank.getBankCode().equals(bankList.get(i).getBankCode())) {
                    bankName = bankList.get(i).getBankName();
                    break;
                }
            }
        }

        mView.showBank(personalInformation.getName(), bank.getBankCardNumber(),
                bank.getBankPhone(), bankName);
    }

    public boolean exitVerification(String bankCardNumber,
                                    String bankCardPhone, String bankName) {
        BeanBankCard info = new BeanBankCard();
        if (!StringUtils.isEmpty(bankName) && bankList.size() > 0) {
            for (int i = 0; i < bankList.size(); i++) {
                if (bankList.get(i).getBankName().equals(bankName)) {
                    info.setBankCode(bankList.get(i).getBankCode());
                }
            }
        }
        info.setBankPhone(bankCardPhone);
        if (!StringUtils.isEmpty(bankCardNumber))
            info.setBankCardNumber(bankCardNumber.replaceAll(" ", ""));
        return info.equals(bank);
    }

    /**
     * 选择银行
     */
    public void chooseBank(Context context) {
        if (null == bankList || bankList.size() <= 0)
            return;
        final List<String> list = new ArrayList<>();
        for (int i = 0; i < bankList.size(); i++) {
            list.add(bankList.get(i).getBankName());
        }
        ListDialog bankDialog = new ListDialog(context, list);
        bankDialog.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                mView.showBankName(list.get(position));
            }
        });
        bankDialog.show();
    }


    public void submit(String userName, String bankCardNumber,
                       String bankCardPhone, String bankName, Context context) {
        if (StringUtils.isEmpty(bankCardPhone)) {
            ToastUtil.showToast(context, context.getResources().getString(R.string.bank_card_phone));
            return;
        }
        if (StringUtils.isEmpty(bankName)) {

            ToastUtil.showToast(context, context.getResources().getString(R.string.please_select_your_collection_bank));
            return;
        }
        if (StringUtils.isEmpty(bankCardNumber)) {

            ToastUtil.showToast(context, context.getResources().getString(R.string.please_enter_your_credit_card_number));
            return;
        }

        Map<String, String> params = new HashMap<>();
        params.put("name", userName);
        params.put("bankPhone", bankCardPhone);
        params.put("electronicAccountName", userName);//这个字段在1.0.18版本中去掉了，但是接口还未去掉。默认提交用户的姓名。
        params.put("bankCardNumber", bankCardNumber.replaceAll(" ", ""));
        for (int i = 0; i < bankList.size(); i++) {
            if (bankList.get(i).getBankName().equals(bankName)) {
                params.put("bankCode", bankList.get(i).getBankCode());
            }
        }
        if (null != bank && !StringUtils.isEmpty(bank.getBankCardNumber())) {
            confirmUpdate(params, bankCardNumber, bankCardPhone, context);
        } else {
            request016(params);
        }
    }

    /**
     * 提示是否确认修改
     *
     * @param params
     * @param bankCardNumber
     * @param bankCardPhone
     */
    private void confirmUpdate(final Map<String, String> params, String bankCardNumber, String bankCardPhone, Context context) {
        ModifyBankDialog bankDialog = new ModifyBankDialog(context, bankCardNumber, bankCardPhone);
        bankDialog.setOnClickListener(new ModifyBankDialog.OnClickListener() {
            @Override
            public void onClick() {
                request016(params);
            }
        });
        bankDialog.show();
    }

}
