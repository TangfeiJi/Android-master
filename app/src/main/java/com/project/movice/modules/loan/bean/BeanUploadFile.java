package com.project.movice.modules.loan.bean;

import com.project.movice.modules.main.bean.BeanBase;

/**
 * Created by wy on 2018/1/29 16:25.
 */


public class BeanUploadFile extends BeanBase {
    private String imageDomain;
    private String url;

    public String getImageDomain() {
        return imageDomain;
    }

    public void setImageDomain(String imageDomain) {
        this.imageDomain = imageDomain;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
