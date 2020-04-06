package com.project.movice.modules.loan.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wy on 2018/3/7 17:52.
 */


public class PayrollType {

    private PayrollDetails payKey;
    private List<PayrollDetails> payValue = new ArrayList<>();

    public PayrollDetails getPayKey() {
        return payKey;
    }

    public void setPayKey(PayrollDetails payKey) {
        this.payKey = payKey;
    }

    public List<PayrollDetails> getPayValue() {
        return payValue;
    }

    public void setPayValue(List<PayrollDetails> payValue) {
        this.payValue = payValue;
    }
}
