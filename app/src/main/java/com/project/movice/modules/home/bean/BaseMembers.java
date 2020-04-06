package com.project.movice.modules.home.bean;

import java.io.Serializable;

/**
 * Created by wy on 2018/12/6.
 */

public class BaseMembers implements Serializable {

    private int isUpgradeable;//是否可升级 |1:不可升级 2:可以升级|
    private int level;//会员等级 |1:非会员 2:等级V1 3:等级V2 4:等级V3 5:等级V4|
    private String levelName;//等级名称
    private long integral;//会员积分
    private long increaseIntegral;//增加积分
    private long deductionIntegral;//减少积分
    private String membersInterests;//会员权益内容

    public int getIsUpgradeable() {
        return isUpgradeable;
    }

    public void setIsUpgradeable(int isUpgradeable) {
        this.isUpgradeable = isUpgradeable;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public long getIntegral() {
        return integral;
    }

    public void setIntegral(long integral) {
        this.integral = integral;
    }

    public long getIncreaseIntegral() {
        return increaseIntegral;
    }

    public void setIncreaseIntegral(long increaseIntegral) {
        this.increaseIntegral = increaseIntegral;
    }

    public long getDeductionIntegral() {
        return deductionIntegral;
    }

    public void setDeductionIntegral(long deductionIntegral) {
        this.deductionIntegral = deductionIntegral;
    }

    public String getMembersInterests() {
        return membersInterests;
    }

    public void setMembersInterests(String membersInterests) {
        this.membersInterests = membersInterests;
    }

}
