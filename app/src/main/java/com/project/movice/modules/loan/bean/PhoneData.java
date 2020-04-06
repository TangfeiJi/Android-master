package com.project.movice.modules.loan.bean;

public class PhoneData {
    private boolean isPhone;//是否是模拟器
    private String phoneIp;//客户端网卡的MAC地址 以及IP地址
    private String phoneIpMac;

    private String LanIp;//局域网Ip
    private String WifiTime;//链接次数
    private String WifiAccPoint;//wifi接入点
    private String WifiSsid;//
    private String WifiIp;//

    private String sdCardMemoryTotal;//
    private String sdCardMemoryAvailable;//
    private String romMemoryTotal;//
    private String romMemoryAvailable;//

    private String ramMemoryTotal;//
    private String ramMemoryAvailable;//

    private String sdCardRealMemoryTotal;//

    private String electric;
    private String status;
    private String plugStatus;
    private String health;

    private boolean isRoot;

    public PhoneData(boolean isPhone, String phoneIp, String phoneIpMac, String lanIp, String wifiTime, String wifiAccPoint, String wifiSsid,  String wifiIp, String sdCardMemoryTotal, String sdCardMemoryAvailable, String romMemoryTotal, String romMemoryAvailable, String ramMemoryTotal, String ramMemoryAvailable, String sdCardRealMemoryTotal, String electric, String status, String plugStatus, String health, boolean isRoot) {
        this.isPhone = isPhone;
        this.phoneIp = phoneIp;
        this.phoneIpMac = phoneIpMac;
        LanIp = lanIp;
        WifiTime = wifiTime;
        WifiAccPoint = wifiAccPoint;
        WifiSsid = wifiSsid;
        WifiIp = wifiIp;
        this.sdCardMemoryTotal = sdCardMemoryTotal;
        this.sdCardMemoryAvailable = sdCardMemoryAvailable;
        this.romMemoryTotal = romMemoryTotal;
        this.romMemoryAvailable = romMemoryAvailable;
        this.ramMemoryTotal = ramMemoryTotal;
        this.ramMemoryAvailable = ramMemoryAvailable;
        this.sdCardRealMemoryTotal = sdCardRealMemoryTotal;
        this.electric = electric;
        this.status = status;
        this.plugStatus = plugStatus;
        this.health = health;
        this.isRoot = isRoot;
    }


    public String getPhoneIp() {
        return phoneIp;
    }

    public void setPhoneIp(String phoneIp) {
        this.phoneIp = phoneIp;
    }

    public String getPhoneIpMac() {
        return phoneIpMac;
    }

    public void setPhoneIpMac(String phoneIpMac) {
        this.phoneIpMac = phoneIpMac;
    }

    public String getLanIp() {
        return LanIp;
    }

    public void setLanIp(String lanIp) {
        LanIp = lanIp;
    }

    public String getWifiTime() {
        return WifiTime;
    }

    public void setWifiTime(String wifiTime) {
        WifiTime = wifiTime;
    }

    public String getWifiAccPoint() {
        return WifiAccPoint;
    }

    public void setWifiAccPoint(String wifiAccPoint) {
        WifiAccPoint = wifiAccPoint;
    }

    public String getWifiSsid() {
        return WifiSsid;
    }

    public void setWifiSsid(String wifiSsid) {
        WifiSsid = wifiSsid;
    }


    public String getWifiIp() {
        return WifiIp;
    }

    public void setWifiIp(String wifiIp) {
        WifiIp = wifiIp;
    }

    public String getSdCardMemoryTotal() {
        return sdCardMemoryTotal;
    }

    public void setSdCardMemoryTotal(String sdCardMemoryTotal) {
        this.sdCardMemoryTotal = sdCardMemoryTotal;
    }

    public String getSdCardMemoryAvailable() {
        return sdCardMemoryAvailable;
    }

    public void setSdCardMemoryAvailable(String sdCardMemoryAvailable) {
        this.sdCardMemoryAvailable = sdCardMemoryAvailable;
    }

    public String getRomMemoryTotal() {
        return romMemoryTotal;
    }

    public void setRomMemoryTotal(String romMemoryTotal) {
        this.romMemoryTotal = romMemoryTotal;
    }

    public String getRomMemoryAvailable() {
        return romMemoryAvailable;
    }

    public void setRomMemoryAvailable(String romMemoryAvailable) {
        this.romMemoryAvailable = romMemoryAvailable;
    }

    public String getRamMemoryTotal() {
        return ramMemoryTotal;
    }

    public void setRamMemoryTotal(String ramMemoryTotal) {
        this.ramMemoryTotal = ramMemoryTotal;
    }

    public String getRamMemoryAvailable() {
        return ramMemoryAvailable;
    }

    public void setRamMemoryAvailable(String ramMemoryAvailable) {
        this.ramMemoryAvailable = ramMemoryAvailable;
    }

    public String getSdCardRealMemoryTotal() {
        return sdCardRealMemoryTotal;
    }

    public void setSdCardRealMemoryTotal(String sdCardRealMemoryTotal) {
        this.sdCardRealMemoryTotal = sdCardRealMemoryTotal;
    }

    public String getElectric() {
        return electric;
    }

    public void setElectric(String electric) {
        this.electric = electric;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPlugStatus() {
        return plugStatus;
    }

    public void setPlugStatus(String plugStatus) {
        this.plugStatus = plugStatus;
    }

    public String getHealth() {
        return health;
    }

    public void setHealth(String health) {
        this.health = health;
    }

    public boolean isPhone() {
        return isPhone;
    }

    public void setPhone(boolean phone) {
        isPhone = phone;
    }

    public boolean isRoot() {
        return isRoot;
    }

    public void setRoot(boolean root) {
        isRoot = root;
    }
}
