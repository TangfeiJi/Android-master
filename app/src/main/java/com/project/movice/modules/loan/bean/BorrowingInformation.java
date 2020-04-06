package com.project.movice.modules.loan.bean;

/**
 * 贷款的金额信息
 * Created by wy on 2018/1/22 19:30.
 */


public class BorrowingInformation {

    private long borrowingMoney;//贷款金额
    private long managementFee;//管理费
    private String borrowingPurpose;//用途
    private int borrowingTimeLimit;//产品id
    private int numberLoanDays;//期限
    private float rate;
    private String payBackMoney;//到期还款金额
    private String getMoney;//实际到手金额

    public long getManagementFee() {
        return managementFee;
    }

    public void setManagementFee(long managementFee) {
        this.managementFee = managementFee;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public int getNumberLoanDays() {
        return numberLoanDays;
    }

    public void setNumberLoanDays(int numberLoanDays) {
        this.numberLoanDays = numberLoanDays;
    }

    public long getBorrowingMoney() {
        return borrowingMoney;
    }

    public void setBorrowingMoney(long borrowingMoney) {
        this.borrowingMoney = borrowingMoney;
    }

    public String getBorrowingPurpose() {
        return borrowingPurpose;
    }

    public void setBorrowingPurpose(String borrowingPurpose) {
        this.borrowingPurpose = borrowingPurpose;
    }

    public int getBorrowingTimeLimit() {
        return borrowingTimeLimit;
    }

    public void setBorrowingTimeLimit(int borrowingTimeLimit) {
        this.borrowingTimeLimit = borrowingTimeLimit;
    }

    public String getPayBackMoney() {
        return payBackMoney;
    }

    public void setPayBackMoney(String payBackMoney) {
        this.payBackMoney = payBackMoney;
    }

    public String getGetMoney() {
        return getMoney;
    }

    public void setGetMoney(String getMoney) {
        this.getMoney = getMoney;
    }
}
