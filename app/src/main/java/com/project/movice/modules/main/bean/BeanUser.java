package com.project.movice.modules.main.bean;


/**
 * Created by wy on 2018/1/11 14:20.
 */


public class BeanUser extends BeanBase {

    private String mobile;
    private String userName;
    private String token;
    private String headIcon;
    private int loginType;//登录类型，1：app登录，2：Facebook登录
    private boolean login = false; // 登录状态 true登录 false未登录

    public int getLoginType() {
        return loginType;
    }

    public void setLoginType(int loginType) {
        this.loginType = loginType;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public boolean isLogin() {
        return login;
    }

    public void setLogin(boolean login) {
        this.login = login;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getHeadIcon() {
        return headIcon;
    }

    public void setHeadIcon(String headIcon) {
        this.headIcon = headIcon;
    }

}
