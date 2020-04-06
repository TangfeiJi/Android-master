package com.project.movice.modules.mine.bean;

/**
 * 订单详情中各状态变更的记录
 * Created by wy on 2018/2/1 15:52.
 */


public class BeanProcessList {
    private Long date;
    private int orderStatus;
    private String description;

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
