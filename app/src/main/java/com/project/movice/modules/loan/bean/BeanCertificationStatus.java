package com.project.movice.modules.loan.bean;

import com.project.movice.modules.main.bean.BeanBase;

import java.io.Serializable;

/**
 * Created by wy on 2018/3/23 16:07.
 */


public class BeanCertificationStatus extends BeanBase implements Serializable {
    private int lazada;////1未完善，2完善
    private int tokopedia;////1未完善，2完善
    private int facebook;////1未完善，2完善
    private int instagram;////1未完善，2完善
    private int gojek;////1未完善，2完善
    private int telkomsel;////1未完善，2完善
    private int npwp;//税卡 1未完善，2完善
    private int bpjs;//社保 1未完善，2完善

    public int getBpjs() {
        return bpjs;
    }

    public void setBpjs(int bpjs) {
        this.bpjs = bpjs;
    }

    public int getNpwp() {
        return npwp;
    }

    public void setNpwp(int npwp) {
        this.npwp = npwp;
    }

    public int getLazada() {
        return lazada;
    }

    public void setLazada(int lazada) {
        this.lazada = lazada;
    }

    public int getTokopedia() {
        return tokopedia;
    }

    public void setTokopedia(int tokopedia) {
        this.tokopedia = tokopedia;
    }

    public int getFacebook() {
        return facebook;
    }

    public void setFacebook(int facebook) {
        this.facebook = facebook;
    }

    public int getInstagram() {
        return instagram;
    }

    public void setInstagram(int instagram) {
        this.instagram = instagram;
    }

    public int getGojek() {
        return gojek;
    }

    public void setGojek(int gojek) {
        this.gojek = gojek;
    }

    public int getTelkomsel() {
        return telkomsel;
    }

    public void setTelkomsel(int telkomsel) {
        this.telkomsel = telkomsel;
    }
}
