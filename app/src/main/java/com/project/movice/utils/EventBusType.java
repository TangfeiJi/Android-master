package com.project.movice.utils;

/**
 * Created by wy on 2018/1/18 14:24.
 */


public class EventBusType {

    public static final int BORROWINGONGOING = 1;//显示借款中的信息
    public static final int BORROWING = 2;//没有借款
    public static final int UPDATELOAN = 3;//更新首页贷款信息(网络请求最新)
    public static final int OUTLOGIN = 4;//退出登录
    public static final int REFRESH = 5;//本地刷新借款中数据，用于选择还款银行和还款页面切换
}
