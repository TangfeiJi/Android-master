package com.project.movice.modules.loan.bean;

public class RequestBean {
    private String page;

    private String count;

    public RequestBean(String page, String count) {
        this.page = page;
        this.count = count;
    }

    @Override
    public String toString() {
        return "RequestBean{" +
                "page='" + page + '\'' +
                ", count='" + count + '\'' +
                '}';
    }
}
