package com.project.movice.modules.home.bean;

import java.io.Serializable;

/**
 * 首页活动弹窗
 * Created by wy on 2019/3/18.
 */

public class BeanActivity  implements Serializable {

    private String activityId;//活动id
    private int status;//0：未逾期  1：已逾期  2：活动提示 3：优惠券
    private String activityContent;//活动要显示的内容|status为2，3时必填
    private String activityUrl;//活动地址|如果有跳转，给出Url地址

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getActivityContent() {
        return activityContent;
    }

    public void setActivityContent(String activityContent) {
        this.activityContent = activityContent;
    }

    public String getActivityUrl() {
        return activityUrl;
    }

    public void setActivityUrl(String activityUrl) {
        this.activityUrl = activityUrl;
    }
}
