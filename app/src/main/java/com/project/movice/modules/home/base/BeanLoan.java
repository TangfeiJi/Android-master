package com.project.movice.modules.home.base;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wy on 2018/1/18 15:31.
 */


public class BeanLoan implements Parcelable, Serializable {
    private Long minMoney;//最小金额
    private Long maxMoney;//最大金额
    private int unit;//每次加减金额的钱数
    private int loanPeriod;//产品id
    private int numberLoanDays;//借款天数
    private float rate;//用于计算利率
    private float showRate;//用于显示利率
    private float oldRate;//原利率
    private float headRest;//管理费
    private String rateDiscount;//利率优惠说明
    private int withholdingRate;//是否预收利息   1预收   2  后收
    private int withholdingManagementFee;//是否预收管理费   1预收   2 后收

    private String minScrollAmout;//轮播最小金额
    private String maxScrollAmout;//轮播最大金额
    private String maxRollAmout; //滑动虚拟最大金额
    private float rate14;
    private List<String> LoanDays;
    private List<LoanDayObject> LoanDaysInfo;

    public List<LoanDayObject> getLoanDaysInfo() {
        return LoanDaysInfo;
    }

    public void setLoanDaysInfo(List<LoanDayObject> loanDaysInfo) {
        LoanDaysInfo = loanDaysInfo;
    }

    public float getRate14() {
        return rate14;
    }

    public void setRate14(float rate14) {
        this.rate14 = rate14;
    }

    public List<String> getLoanDays() {
        return LoanDays;
    }

    public void setLoanDays(List<String> loanDays) {
        LoanDays = loanDays;
    }

    public String getMinScrollAmout() {
        return minScrollAmout;
    }

    public void setMinScrollAmout(String minScrollAmout) {
        this.minScrollAmout = minScrollAmout;
    }

    public String getMaxScrollAmout() {
        return maxScrollAmout;
    }

    public void setMaxScrollAmout(String maxScrollAmout) {
        this.maxScrollAmout = maxScrollAmout;
    }

    public String getMaxRollAmout() {
        return maxRollAmout;
    }

    public void setMaxRollAmout(String maxRollAmout) {
        this.maxRollAmout = maxRollAmout;
    }

    public int getWithholdingRate() {
        return withholdingRate;
    }

    public void setWithholdingRate(int withholdingRate) {
        this.withholdingRate = withholdingRate;
    }

    public int getWithholdingManagementFee() {
        return withholdingManagementFee;
    }

    public void setWithholdingManagementFee(int withholdingManagementFee) {
        this.withholdingManagementFee = withholdingManagementFee;
    }

    public float getShowRate() {
        return showRate;
    }

    public void setShowRate(float showRate) {
        this.showRate = showRate;
    }

    public String getRateDiscount() {
        return rateDiscount;
    }

    public void setRateDiscount(String rateDiscount) {
        this.rateDiscount = rateDiscount;
    }

    public float getOldRate() {
        return oldRate;
    }

    public void setOldRate(float oldRate) {
        this.oldRate = oldRate;
    }

    public int getNumberLoanDays() {
        return numberLoanDays;
    }

    public void setNumberLoanDays(int numberLoanDays) {
        this.numberLoanDays = numberLoanDays;
    }

    public Long getMinMoney() {
        return minMoney;
    }

    public void setMinMoney(Long minMoney) {
        this.minMoney = minMoney;
    }

    public Long getMaxMoney() {
        return maxMoney;
    }

    public void setMaxMoney(Long maxMoney) {
        this.maxMoney = maxMoney;
    }

    public int getUnit() {
        return unit;
    }

    public void setUnit(int unit) {
        this.unit = unit;
    }

    public int getLoanPeriod() {
        return loanPeriod;
    }

    public void setLoanPeriod(int loanPeriod) {
        this.loanPeriod = loanPeriod;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public float getHeadRest() {
        return headRest;
    }

    public void setHeadRest(float headRest) {
        this.headRest = headRest;
    }

    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(minMoney);
        parcel.writeLong(maxMoney);
        parcel.writeInt(unit);
        parcel.writeInt(loanPeriod);
        parcel.writeInt(numberLoanDays);
        parcel.writeFloat(rate);
        parcel.writeFloat(headRest);
    }

    public static final Creator<BeanLoan> CREATOR = new Creator<BeanLoan>() {
        @Override
        public BeanLoan createFromParcel(Parcel in) {
            BeanLoan bl = new BeanLoan();
            bl.minMoney = in.readLong();
            bl.maxMoney = in.readLong();
            bl.unit = in.readInt();
            bl.loanPeriod = in.readInt();
            bl.numberLoanDays = in.readInt();
            bl.rate = in.readFloat();
            bl.headRest = in.readFloat();
            return bl;
        }

        @Override
        public BeanLoan[] newArray(int size) {
            return new BeanLoan[size];
        }
    };


    public class LoanDayObject implements Serializable{
        private int day;
        private Float rate;
        private String enable;
        private boolean isSelect;

        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
        }

        public int getDay() {
            return day;
        }

        public void setDay(int day) {
            this.day = day;
        }

        public Float getRate() {
            return rate;
        }

        public void setRate(Float rate) {
            this.rate = rate;
        }

        public String getEnable() {
            return enable;
        }

        public void setEnable(String enable) {
            this.enable = enable;
        }
    }

}


