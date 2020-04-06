package com.project.movice.modules.main.bean;

import java.io.Serializable;
import java.util.List;

public class VersionBean  implements Serializable {

    /**
     * loginMethod : 1
     * title :
     * type :
     * content :
     * version :
     * iscompulsively :
     * crawlApp : true
     * crawlDeviceFingerprint : true
     * telephone : 082122882905
     * froceAuth : 0
     * mohe_api : https://talosapi.shujumohe.com/octopus/sdk.service.task.query/v3
     * mohe_code : mini_hw_mohe
     * mohe_key : a60c4adef0abb388255e250cada65b9e
     * mohe_url : https://talosopen.shujumohe.com/box/ksl?box_token=AF63C93F294F41C3BE3D157198AD25B6&cb=http://www.minirupiah.com/
     * mohe_url_gjk : https://talosopen.shujumohe.com/box/gjk?box_token=AF63C93F294F41C3BE3D157198AD25B6&cb=http://www.minirupiah.com/
     * face_key : 0YH4k5UOP03Rlb6W_w1vc4QOG9JNSnG-
     * face_secret : puoj9k2-ntytFdwfvE8JXs08Q_5_ZojQ
     * face_pass_value : 70
     * side : a
     * is_loanmarket : 0
     * is_back : 0
     * loanmarket_list : ["aa","bb","cc","dd"]
     */

    private int loginMethod;
    private String title;
    private String type;
    private String content;
    private String version;
    private String iscompulsively;
    private boolean crawlApp;
    private boolean crawlDeviceFingerprint;
    private String telephone;
    private String froceAuth;
    private String mohe_api;
    private String mohe_code;
    private String mohe_key;
    private String mohe_url;
    private String face_key;
    private String face_secret;
    private String face_pass_value;
    private String side;
    private int is_loanmarket;
    private int is_back;
    private List<String> loanmarket_list;

    public int getLoginMethod() {
        return loginMethod;
    }

    public void setLoginMethod(int loginMethod) {
        this.loginMethod = loginMethod;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getIscompulsively() {
        return iscompulsively;
    }

    public void setIscompulsively(String iscompulsively) {
        this.iscompulsively = iscompulsively;
    }

    public boolean isCrawlApp() {
        return crawlApp;
    }

    public void setCrawlApp(boolean crawlApp) {
        this.crawlApp = crawlApp;
    }

    public boolean isCrawlDeviceFingerprint() {
        return crawlDeviceFingerprint;
    }

    public void setCrawlDeviceFingerprint(boolean crawlDeviceFingerprint) {
        this.crawlDeviceFingerprint = crawlDeviceFingerprint;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getFroceAuth() {
        return froceAuth;
    }

    public void setFroceAuth(String froceAuth) {
        this.froceAuth = froceAuth;
    }

    public String getMohe_api() {
        return mohe_api;
    }

    public void setMohe_api(String mohe_api) {
        this.mohe_api = mohe_api;
    }

    public String getMohe_code() {
        return mohe_code;
    }

    public void setMohe_code(String mohe_code) {
        this.mohe_code = mohe_code;
    }

    public String getMohe_key() {
        return mohe_key;
    }

    public void setMohe_key(String mohe_key) {
        this.mohe_key = mohe_key;
    }

    public String getMohe_url() {
        return mohe_url;
    }

    public void setMohe_url(String mohe_url) {
        this.mohe_url = mohe_url;
    }


    public String getFace_key() {
        return face_key;
    }

    public void setFace_key(String face_key) {
        this.face_key = face_key;
    }

    public String getFace_secret() {
        return face_secret;
    }

    public void setFace_secret(String face_secret) {
        this.face_secret = face_secret;
    }

    public String getFace_pass_value() {
        return face_pass_value;
    }

    public void setFace_pass_value(String face_pass_value) {
        this.face_pass_value = face_pass_value;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public int getIs_loanmarket() {
        return is_loanmarket;
    }

    public void setIs_loanmarket(int is_loanmarket) {
        this.is_loanmarket = is_loanmarket;
    }

    public int getIs_back() {
        return is_back;
    }

    public void setIs_back(int is_back) {
        this.is_back = is_back;
    }

    public List<String> getLoanmarket_list() {
        return loanmarket_list;
    }

    public void setLoanmarket_list(List<String> loanmarket_list) {
        this.loanmarket_list = loanmarket_list;
    }
}