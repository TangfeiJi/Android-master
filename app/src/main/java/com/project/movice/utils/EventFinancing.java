package com.project.movice.utils;

/**
 * 理财首页刷新
 * Created by wy on 2018/8/21 13:09.
 */
public class EventFinancing {

    public static final int REFRESH = 1;//刷新
    public static final int REFRESH_OPTIONAL = 2;//自选刷新

    private int type;

    public EventFinancing(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
