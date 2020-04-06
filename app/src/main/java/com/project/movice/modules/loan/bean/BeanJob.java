package com.project.movice.modules.loan.bean;


import com.project.movice.utils.StringUtils;

/**
 * 工作信息
 * Created by wy on 2018/1/22 13:08.
 */


public class BeanJob {

    private String occupation;//职业
    private String workingSeniority;//工作年限
    private String incomeLevel;//收入水平
    private String payDate;//发薪日
    private String companyName;//公司名称
    private String companyPhone;//公司电话
    private String imageDomain;
    private String workingPicture;//工作照(图片路径)
    private String payroll;//工资条照(图片路径)
    private String taxCardPhoto;//税卡照(图片路径)
    private String taxCardNumber;//税卡号
    private String province;//公司地址-省
    private String city;//公司地址-市
    private String area;// 公司地址-区
    private String street;//公司地址-街道
    private String detailedAddress;//详细地址

    public String getPayroll() {
        return payroll;
    }

    public void setPayroll(String payroll) {
        this.payroll = payroll;
    }

    public String getTaxCardPhoto() {
        return taxCardPhoto;
    }

    public void setTaxCardPhoto(String taxCardPhoto) {
        this.taxCardPhoto = taxCardPhoto;
    }

    public String getTaxCardNumber() {
        return taxCardNumber;
    }

    public void setTaxCardNumber(String taxCardNumber) {
        this.taxCardNumber = taxCardNumber;
    }

    public String getImageDomain() {
        return imageDomain;
    }

    public void setImageDomain(String imageDomain) {
        this.imageDomain = imageDomain;
    }

    public String getWorkingSeniority() {
        return workingSeniority;
    }

    public void setWorkingSeniority(String workingSeniority) {
        this.workingSeniority = workingSeniority;
    }

    public String getDetailedAddress() {
        return detailedAddress;
    }

    public void setDetailedAddress(String detailedAddress) {
        this.detailedAddress = detailedAddress;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getIncomeLevel() {
        return incomeLevel;
    }

    public void setIncomeLevel(String incomeLevel) {
        this.incomeLevel = incomeLevel;
    }

    public String getPayDate() {
        return payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyPhone() {
        return companyPhone;
    }

    public void setCompanyPhone(String companyPhone) {
        this.companyPhone = companyPhone;
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

    public String getWorkingPicture() {
        return workingPicture;
    }

    public void setWorkingPicture(String workingPicture) {
        this.workingPicture = workingPicture;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        BeanJob info = (BeanJob) obj;
        if (!isEquals(occupation, info.getOccupation())) {
            return false;
        }
        if (!isEquals(taxCardNumber, info.getTaxCardNumber())) {
            return false;
        }
        if (!isEquals(workingSeniority, info.getWorkingSeniority())) {
            return false;
        }
        if (!isEquals(incomeLevel, info.getIncomeLevel())) {
            return false;
        }
        if (!isEquals(payDate, info.getPayDate())) {
            return false;
        }
        if (!isEquals(companyName, info.getCompanyName())) {
            return false;
        }
        if (!isEquals(companyPhone, info.getCompanyPhone())) {
            return false;
        }
        if (!isEquals(province, info.getProvince())) {
            return false;
        }
        if (!isEquals(city, info.getCity())) {
            return false;
        }
        if (!isEquals(area, info.getArea())) {
            return false;
        }
        if (!isEquals(street, info.getStreet())) {
            return false;
        }
//        if (!StringUtils.isEmpty(info.getWorkingPicture()) && !isEquals(workingPicture, info.getImageDomain() + info.getWorkingPicture())) {
//            return false;
//        }
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
