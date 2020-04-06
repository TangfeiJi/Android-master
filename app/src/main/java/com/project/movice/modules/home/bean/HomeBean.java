package com.project.movice.modules.home.bean;

import com.project.movice.modules.home.base.BeanLoan;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class HomeBean implements Serializable {

    private int type;//1:正在贷款中，2:没有正在进行的贷款订单
    private String msg = "";//提示语
    private BeanActivity activity;
    private BeanProduct product;
    private BeanLoanProgress loanProgress;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public BeanActivity getActivity() {
        return activity;
    }

    public void setActivity(BeanActivity activity) {
        this.activity = activity;
    }

    public BeanProduct getProduct() {
        return product;
    }

    public void setProduct(BeanProduct product) {
        this.product = product;
    }


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public BeanLoanProgress getLoanProgress() {
        return loanProgress;
    }

    public void setLoanProgress(BeanLoanProgress loanProgress) {
        this.loanProgress = loanProgress;
    }
}
