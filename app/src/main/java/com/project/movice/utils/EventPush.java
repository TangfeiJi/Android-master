package com.project.movice.utils;

/**
 * 推送消息
 * Created by wy on 2018/1/18 14:24.
 */
public class EventPush {

    public static final int LOGIN = 1;//登录
    public static final int PUSH = 2;//更新phshtoken

    private String token;
    private int type;

    public EventPush(int type, String token) {
        this.type = type;
        this.token = token;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
