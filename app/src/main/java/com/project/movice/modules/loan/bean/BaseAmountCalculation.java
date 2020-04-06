package com.project.movice.modules.loan.bean;

import com.project.movice.modules.main.bean.BeanBase;

/**
 * Created by wy on 2019/1/25.
 */

public class BaseAmountCalculation extends BeanBase {

    private long loanAmount;//借款金额
    private int loanPeriod;//借款期限
    private long getTheAmount;//到手金额
    private long repaymentAmount;//还款金额
    private long interest;//利息
    private long managementFee;//管理费
    private long earnestMoney;//缴纳保证金金额
    private long discountedPrice;//优惠金额
    private String period;//产品id
    private String couponId;//优惠券id

    public long getEarnestMoney() {
        return earnestMoney;
    }

    public void setEarnestMoney(long earnestMoney) {
        this.earnestMoney = earnestMoney;
    }

    public long getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(long loanAmount) {
        this.loanAmount = loanAmount;
    }

    public int getLoanPeriod() {
        return loanPeriod;
    }

    public void setLoanPeriod(int loanPeriod) {
        this.loanPeriod = loanPeriod;
    }

    public long getGetTheAmount() {
        return getTheAmount;
    }

    public void setGetTheAmount(long getTheAmount) {
        this.getTheAmount = getTheAmount;
    }

    public long getRepaymentAmount() {
        return repaymentAmount;
    }

    public void setRepaymentAmount(long repaymentAmount) {
        this.repaymentAmount = repaymentAmount;
    }

    public long getInterest() {
        return interest;
    }

    public void setInterest(long interest) {
        this.interest = interest;
    }

    public long getManagementFee() {
        return managementFee;
    }

    public void setManagementFee(long managementFee) {
        this.managementFee = managementFee;
    }

    public long getDiscountedPrice() {
        return discountedPrice;
    }

    public void setDiscountedPrice(long discountedPrice) {
        this.discountedPrice = discountedPrice;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getCouponId() {
        return couponId;
    }

    public void setCouponId(String couponId) {
        this.couponId = couponId;
    }
}
