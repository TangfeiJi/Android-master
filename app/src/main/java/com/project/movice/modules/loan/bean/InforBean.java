package com.project.movice.modules.loan.bean;

public class InforBean {


    /**
     * isOldUser : 0
     * content :
     * personal : 0
     * job : 0
     * contact : 0
     * other : 0
     * livingCertification :
     * face_pass : 0
     */

    private int isOldUser;
    private String content;
    private int personal;
    private int job;
    private int contact;
    private int id;
    private int other;
    private String livingCertification;
    private String face_pass;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIsOldUser() {
        return isOldUser;
    }

    public void setIsOldUser(int isOldUser) {
        this.isOldUser = isOldUser;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getPersonal() {
        return personal;
    }

    public void setPersonal(int personal) {
        this.personal = personal;
    }

    public int getJob() {
        return job;
    }

    public void setJob(int job) {
        this.job = job;
    }

    public int getContact() {
        return contact;
    }

    public void setContact(int contact) {
        this.contact = contact;
    }

    public int getOther() {
        return other;
    }

    public void setOther(int other) {
        this.other = other;
    }

    public String getLivingCertification() {
        return livingCertification;
    }

    public void setLivingCertification(String livingCertification) {
        this.livingCertification = livingCertification;
    }

    public String getFace_pass() {
        return face_pass;
    }

    public void setFace_pass(String face_pass) {
        this.face_pass = face_pass;
    }
}
