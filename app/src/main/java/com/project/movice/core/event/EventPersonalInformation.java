package com.project.movice.core.event;

/**
 * 个人资料保存
 * Created by wy on 2018/9/27 17:45.
 */


public class EventPersonalInformation {
    public static final int ID = 0;//身份信息
    public static final int PERSONAL = 1;//个人信息
    public static final int JOB = 2;//工作信息
    public static final int CONTACT = 3;//联系人信息
    public static final int BANK = 4;//银行信息
    public static final int LIVING_BODY = 5;//活体认证信息
    public static final int JUMP = 11;//
    public static final int JUMPJUMP = 16;//
    public static final int JUMPTWO = 12;//活体认证信息
    public static final int OTHER = 7;//活体认证信息

    public static final int DIMSSNEXT = 13;//隐藏不可借款


    public static final int SHOWNEXT = 133;//可借款
    private int type;
    private boolean isPerfect = false;
    private boolean isNextPage = true; //是否下一页     如果是true会自动进入下一项信息填写

    public EventPersonalInformation(int type, boolean isPerfect) {
        this.type = type;
        this.isPerfect = isPerfect;
    }

    public EventPersonalInformation(int type, boolean isPerfect, boolean isNextPage) {
        this.type = type;
        this.isPerfect = isPerfect;
        this.isNextPage = isNextPage;
    }

    public boolean isNextPage() {
        return isNextPage;
    }

    public void setNextPage(boolean nextPage) {
        isNextPage = nextPage;
    }

    public int getType() {
        return type;
    }

    public boolean isPerfect() {
        return isPerfect;
    }
}
