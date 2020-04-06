package com.project.movice.modules.loan.bean;


import com.project.movice.modules.main.bean.BeanBase;
import com.project.movice.utils.StringUtils;

/**
 * 个人基本信息
 * Created by wy on 2018/1/20 19:35.
 */


public class BeanPersonalInformation extends BeanBase {
    private String uid = "";
    private String name = ""; //姓名
    private String ktpNumber = ""; //ktp号码
    private String imageDomain = "";//ktp照地址
    private String ktpPhoto = ""; //ktp照地址
    private String gender = ""; //性别
    private String birthDate = ""; //出生年月
    private String maritalStatus = ""; //婚姻状况
    private String province = ""; //住址-省
    private String city = ""; //住址-市
    private String area = ""; //住址-区
    private String street = ""; //住址-街道
    private String detailedAddress = "";//详细住址
    private String mobile = "";//手机号
    private String familyLandline = "";//固定电话
    private String email = "";//邮箱
    private String whatsapp = "";//whatsapp账号
    private int modifyInformation;//是否可修改ktp（1可修改,2不可修改）

    private String phoneTime;

    public String getPhoneTime() {
        return phoneTime;
    }

    public void setPhoneTime(String phoneTime) {
        this.phoneTime = phoneTime;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    private String motherName = "";//whatsapp账号

    private String education = "";//whatsapp账号

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getMotherName() {
        return motherName;
    }

    public void setMotherName(String motherName) {
        this.motherName = motherName;
    }

    public int getModifyInformation() {
        return modifyInformation;
    }

    public void setModifyInformation(int modifyInformation) {
        this.modifyInformation = modifyInformation;
    }

    public String getImageDomain() {
        return imageDomain;
    }

    public void setImageDomain(String imageDomain) {
        this.imageDomain = imageDomain;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getFamilyLandline() {
        return familyLandline;
    }

    public void setFamilyLandline(String familyLandline) {
        this.familyLandline = familyLandline;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWhatsapp() {
        return whatsapp;
    }

    public void setWhatsapp(String whatsapp) {
        this.whatsapp = whatsapp;
    }

    public String getDetailedAddress() {
        return detailedAddress;
    }

    public void setDetailedAddress(String detailedAddress) {
        this.detailedAddress = detailedAddress;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKtpNumber() {
        return ktpNumber;
    }

    public void setKtpNumber(String ktpNumber) {
        this.ktpNumber = ktpNumber;
    }

    public String getKtpPhoto() {
        return ktpPhoto;
    }

    public void setKtpPhoto(String ktpPhoto) {
        this.ktpPhoto = ktpPhoto;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        BeanPersonalInformation info = (BeanPersonalInformation) obj;
        if (!isEquals(name, info.getName()))
            return false;
        if (!isEquals(ktpNumber, info.getKtpNumber()))
            return false;
//        if (!StringUtils.isEmpty(info.getKtpPhoto()) && !isEquals(ktpPhoto, info.getImageDomain() + info.getKtpPhoto()))
//            return false;
        if (!isEquals(gender, info.getGender()))
            return false;
        if (!isEquals(birthDate, info.getBirthDate()))
            return false;
        if (!isEquals(maritalStatus, info.getMaritalStatus()))
            return false;
        if (!isEquals(province, info.getProvince()))
            return false;
        if (!isEquals(city, info.getCity()))
            return false;
        if (!isEquals(area, info.getArea()))
            return false;
        if (!isEquals(street, info.getStreet()))
            return false;
        if (!isEquals(detailedAddress, info.getDetailedAddress()))
            return false;
        if (!isEquals(mobile, info.getMobile()))
            return false;
        if (!isEquals(familyLandline, info.getFamilyLandline()))
            return false;
        if (!isEquals(email, info.getEmail()))
            return false;
        if (!isEquals(whatsapp, info.getWhatsapp()))
            return false;

        return true;
    }

    private boolean isEquals(String obj1, String obj2) {
        if (StringUtils.isEmpty(obj1) && StringUtils.isEmpty(obj2)) {
            return true;
        }
        if (!StringUtils.isEmpty(obj1) && obj1.equals(obj2)) {
            return true;
        }
        if (!StringUtils.isEmpty(obj2) && obj2.equals(obj1)) {
            return true;
        }
        return false;
    }

}
