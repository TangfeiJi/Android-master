package com.project.movice.modules.area;

/**
 * 地区选择
 * Created by wy on 2018/2/2 10:56.
 */


public class BeanAreaInfo {


    private String id;//自己的id
    private String pid;//上一级id
    private String name;//城市名
    private Integer level;

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
