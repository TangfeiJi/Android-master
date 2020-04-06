package com.project.movice.modules.home.bean;

/**
 * 查询用户是否可借款
 * Created by wy on 2018/1/27 19:16.
 */


public class BeanBorrowingStatus {

    /**
     * status : 1
     * date :
     */

    private int status;
    private String date;
    private String content;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}
