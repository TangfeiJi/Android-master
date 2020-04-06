package com.project.movice.modules.home.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 贷款进行中
 * Created by wy on 2018/1/18 15:28.
 */


public class BeanLoanProgress implements Serializable {

    private String orderId;//订单id
    private String loanNumber;//借款编号
    private String name;//姓名
    private String ktpNumber;//KTP号码
    private long applicationTime;//申请时间
    private String loanAmount;//借款金额
    private int loanPeriod;//借款期限
    private int numberLoanDays;//借款天数
    private String repaymentAmount;//还款金额
    private String beneficiaryBank;//收款银行
    private String bankCardNumber;//收款银行卡号
    private int orderStatus;//订单类型
    private long cancelCountdown;//取消订单倒计时


    private String refundBankName;//还款银行名
    private String refundBankCode;//银行编号
    private String refundBankCardNumber;//卡号
    private long refundBankCardNumberValidityPeriod;//还款银行卡号有效期
    private String refundBankLogo;//银行icon
    private String imageDomain;
    private long repaymentDeadline;//还款截止日期
    private BaseMembers members;//会员信息
    private List<BeanBank> bankList = new ArrayList<>();

    public BaseMembers getMembers() {
        return members;
    }

    public void setMembers(BaseMembers members) {
        this.members = members;
    }

    public int getNumberLoanDays() {
        return numberLoanDays;
    }

    public void setNumberLoanDays(int numberLoanDays) {
        this.numberLoanDays = numberLoanDays;
    }

    public String getImageDomain() {
        return imageDomain;
    }

    public void setImageDomain(String imageDomain) {
        this.imageDomain = imageDomain;
    }

    public long getRefundBankCardNumberValidityPeriod() {
        return refundBankCardNumberValidityPeriod;
    }

    public void setRefundBankCardNumberValidityPeriod(long refundBankCardNumberValidityPeriod) {
        this.refundBankCardNumberValidityPeriod = refundBankCardNumberValidityPeriod;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getLoanNumber() {
        return loanNumber;
    }

    public void setLoanNumber(String loanNumber) {
        this.loanNumber = loanNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKtpNumber() {
        return ktpNumber;
    }

    public void setKtpNumber(String ktpNumber) {
        this.ktpNumber = ktpNumber;
    }

    public long getApplicationTime() {
        return applicationTime;
    }

    public void setApplicationTime(long applicationTime) {
        this.applicationTime = applicationTime;
    }

    public String getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(String loanAmount) {
        this.loanAmount = loanAmount;
    }

    public int getLoanPeriod() {
        return loanPeriod;
    }

    public void setLoanPeriod(int loanPeriod) {
        this.loanPeriod = loanPeriod;
    }

    public String getRepaymentAmount() {
        return repaymentAmount;
    }

    public void setRepaymentAmount(String repaymentAmount) {
        this.repaymentAmount = repaymentAmount;
    }

    public String getBeneficiaryBank() {
        return beneficiaryBank;
    }

    public void setBeneficiaryBank(String beneficiaryBank) {
        this.beneficiaryBank = beneficiaryBank;
    }

    public String getBankCardNumber() {
        return bankCardNumber;
    }

    public void setBankCardNumber(String bankCardNumber) {
        this.bankCardNumber = bankCardNumber;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public long getCancelCountdown() {
        return cancelCountdown;
    }

    public void setCancelCountdown(long cancelCountdown) {
        this.cancelCountdown = cancelCountdown;
    }

    public String getRefundBankName() {
        return refundBankName;
    }

    public void setRefundBankName(String refundBankName) {
        this.refundBankName = refundBankName;
    }

    public String getRefundBankCode() {
        return refundBankCode;
    }

    public void setRefundBankCode(String refundBankCode) {
        this.refundBankCode = refundBankCode;
    }

    public String getRefundBankCardNumber() {
        return refundBankCardNumber;
    }

    public void setRefundBankCardNumber(String refundBankCardNumber) {
        this.refundBankCardNumber = refundBankCardNumber;
    }

    public String getRefundBankLogo() {
        return refundBankLogo;
    }

    public void setRefundBankLogo(String refundBankLogo) {
        this.refundBankLogo = refundBankLogo;
    }

    public long getRepaymentDeadline() {
        return repaymentDeadline;
    }

    public void setRepaymentDeadline(long repaymentDeadline) {
        this.repaymentDeadline = repaymentDeadline;
    }

    public List<BeanBank> getBankList() {
        return bankList;
    }

    public void setBankList(List<BeanBank> bankList) {
        this.bankList = bankList;
    }

}
