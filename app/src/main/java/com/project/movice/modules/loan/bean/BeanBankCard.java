package com.project.movice.modules.loan.bean;


import com.project.movice.utils.StringUtils;

/**
 * 银行信息
 * Created by wy on 2018/1/22 18:53.
 */


public class BeanBankCard {

    private String name;//收款人
    private String electronicAccountName;//电子账户名
    private String bankPhone;//手机号
    private String bankCode;//收款银行类型
    private String bankName;//银行名
    private String bankCardNumber;//"银行卡号

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankPhone() {
        return bankPhone;
    }

    public void setBankPhone(String bankPhone) {
        this.bankPhone = bankPhone;
    }

    public String getElectronicAccountName() {
        return electronicAccountName;
    }

    public void setElectronicAccountName(String electronicAccountName) {
        this.electronicAccountName = electronicAccountName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        BeanBankCard info = (BeanBankCard) obj;
        if (!isEquals(bankCode, info.getBankCode())) {
            return false;
        }
        if (!isEquals(bankPhone, info.getBankPhone())) {
            return false;
        }
        if (!isEquals(bankCardNumber, info.getBankCardNumber())) {
            return false;
        }
        return true;
    }

    private boolean isEquals(String obj1, String obj2) {
        if (StringUtils.isEmpty(obj1) && StringUtils.isEmpty(obj2)) {
            return true;
        }
        if (!StringUtils.isEmpty(obj1) && obj1.equals(obj2)) {
            return true;
        }
        if (!StringUtils.isEmpty(obj2) && obj2.equals(obj1)) {
            return true;
        }
        return false;
    }
}
