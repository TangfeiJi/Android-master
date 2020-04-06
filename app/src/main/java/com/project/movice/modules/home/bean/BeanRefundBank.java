package com.project.movice.modules.home.bean;

/**
 * 还款银行确认
 * Created by wy on 2018/1/23 21:04.
 */


public class BeanRefundBank {

    private String bankName;
    private String bankCode;
    private String bankCardNumber;
    private String bankLogo;
    private String imageDomain;
    private long bankCardNumberValidityPeriod;

    public String getImageDomain() {
        return imageDomain;
    }

    public void setImageDomain(String imageDomain) {
        this.imageDomain = imageDomain;
    }

    public long getBankCardNumberValidityPeriod() {
        return bankCardNumberValidityPeriod;
    }

    public void setBankCardNumberValidityPeriod(long bankCardNumberValidityPeriod) {
        this.bankCardNumberValidityPeriod = bankCardNumberValidityPeriod;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getBankCardNumber() {
        return bankCardNumber;
    }

    public void setBankCardNumber(String bankCardNumber) {
        this.bankCardNumber = bankCardNumber;
    }

    public String getBankLogo() {
        return bankLogo;
    }

    public void setBankLogo(String bankLogo) {
        this.bankLogo = bankLogo;
    }
}
