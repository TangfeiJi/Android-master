package com.project.movice.modules.loan.bean;

public class TrafficBean {
private String download;
    private String upload;

    public TrafficBean(String download, String upload) {
        this.download = download;
        this.upload = upload;
    }

    public String getDownload() {
        return download;
    }

    public void setDownload(String download) {
        this.download = download;
    }

    public String getUpload() {
        return upload;
    }

    public void setUpload(String upload) {
        this.upload = upload;
    }
}
