package com.project.movice.modules.mine.bean;

import com.project.movice.modules.main.bean.BeanBase;

import java.util.ArrayList;
import java.util.List;

/**
 * 订单详情
 * Created by wy on 2018/2/1 15:46.
 */


public class BeanLoanDetails extends BeanBase {
    private int orderStatus;//状态
    private String loanNumber;//借款编号
    private String bankCardNumber;//收款：卡号
    private String bankName;//收款：银行名
    private Long repaymentAmount;//还款金额
    private Long loanAmount;//借款总金额
    private Long getTheAmount;//到手金额
    private Long earnestMoney;//保证金金额
    private int loanPeriod;//借款期限
    private int numberLoanDays;//借款天数，直接显示代表天数的数字
    private Long repaymentDeadline;//还款日
    private Long interest;//利息
    private Long penaltyAmount;//罚息总额
    private String name;//姓名
    private String repaymentBankName;//还款：中国农业银行
    private String repaymentBankCode;//还款：ABC
    private String repaymentBankCardNumber;//还款：卡号
    private List<BeanProcessList> processList = new ArrayList<>();

    public Long getEarnestMoney() {
        return earnestMoney;
    }

    public void setEarnestMoney(Long earnestMoney) {
        this.earnestMoney = earnestMoney;
    }

    public int getNumberLoanDays() {
        return numberLoanDays;
    }

    public void setNumberLoanDays(int numberLoanDays) {
        this.numberLoanDays = numberLoanDays;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getLoanNumber() {
        return loanNumber;
    }

    public void setLoanNumber(String loanNumber) {
        this.loanNumber = loanNumber;
    }

    public String getBankCardNumber() {
        return bankCardNumber;
    }

    public void setBankCardNumber(String bankCardNumber) {
        this.bankCardNumber = bankCardNumber;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public Long getRepaymentAmount() {
        return repaymentAmount;
    }

    public void setRepaymentAmount(Long repaymentAmount) {
        this.repaymentAmount = repaymentAmount;
    }

    public Long getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(Long loanAmount) {
        this.loanAmount = loanAmount;
    }

    public Long getGetTheAmount() {
        return getTheAmount;
    }

    public void setGetTheAmount(Long getTheAmount) {
        this.getTheAmount = getTheAmount;
    }

    public int getLoanPeriod() {
        return loanPeriod;
    }

    public void setLoanPeriod(int loanPeriod) {
        this.loanPeriod = loanPeriod;
    }

    public Long getRepaymentDeadline() {
        return repaymentDeadline;
    }

    public void setRepaymentDeadline(Long repaymentDeadline) {
        this.repaymentDeadline = repaymentDeadline;
    }
    public Long getInterest() {
        return interest;
    }

    public void setInterest(Long interest) {
        this.interest = interest;
    }

    public Long getPenaltyAmount() {
        return penaltyAmount;
    }

    public void setPenaltyAmount(Long penaltyAmount) {
        this.penaltyAmount = penaltyAmount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRepaymentBankName() {
        return repaymentBankName;
    }

    public void setRepaymentBankName(String repaymentBankName) {
        this.repaymentBankName = repaymentBankName;
    }

    public String getRepaymentBankCode() {
        return repaymentBankCode;
    }

    public void setRepaymentBankCode(String repaymentBankCode) {
        this.repaymentBankCode = repaymentBankCode;
    }

    public String getRepaymentBankCardNumber() {
        return repaymentBankCardNumber;
    }

    public void setRepaymentBankCardNumber(String repaymentBankCardNumber) {
        this.repaymentBankCardNumber = repaymentBankCardNumber;
    }

    public List<BeanProcessList> getProcessList() {
        return processList;
    }

    public void setProcessList(List<BeanProcessList> processList) {
        this.processList = processList;
    }

}
