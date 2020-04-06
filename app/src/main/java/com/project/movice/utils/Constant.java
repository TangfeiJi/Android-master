package com.project.movice.utils;

/**
 * Created by wy on 2018/1/9 14:12.
 */


public class Constant {


    public static final long STANDARD_TIME = 60000;// 验证码60s
    public static final long CANCEL_ORDER_TIME = 10000;// 可取消订单倒计时
    public static final String KEY_URL = "url";// 网页地址
    public static final String KEY_BORROWING = "borrowing";// 借款的数据


    public static final String guide = "guide";// 引导页

    public static final int hari_seven = 1;// 借款7 Hari
    public static final int hari_fourteen = 2;// 借款14 Hari
    public static final int hari_fourteen1 = 3;// 借款14 Hari//新平台用的


    public static final int ORDERSTATUS_CANCEL = 1;// 申请取消
    public static final int ORDERSTATUS_UNDER_REVIEW = 2;// 审核中
    public static final int ORDERSTATUS_AUDIT_FAILURE = 3;// 审核失败
    public static final int ORDERSTATUS_TO_BE_REPAYMENTS = 4;// 待还款
    public static final int ORDERSTATUS_SUSPENDED = 5;// 缓期中
    public static final int ORDERSTATUS_OVERDUE = 6;// 已逾期
    public static final int ORDERSTATUS_REPAYMENT = 7;// 已还款
    public static final int ORDERSTATUS_APPLY_REATE = 8;// 申请创建
    public static final int ORDERSTATUS_REVIEW_SUCCESS = 9;// 审核通过


    public static final String IMAGE_KTP = "1";//KTP照片
    public static final String IMAGE_KTP_HEAD = "14";//KTP照片头像
    public static final String IMAGE_FACE = "15";
    public static final String IMAGE_JOB = "2";//工作卡/工作照
    public static final String IMAGE_3 = "3";//税卡
    public static final String IMAGE_4 = "4";//工资条
    public static final String IMAGE_5 = "5";//信用卡
    public static final String IMAGE_6 = "6";//社保卡
    public static final String IMAGE_10 = "10";//其他贷款还款银行流水
    public static final String IMAGE_13 = "13";//反馈

    public static final String IMAGE_16 = "16";//签名
    public static final String IMAGE_17 = "17";//签名pdf

    public static final int LOGINTYPE_PHONE = 1;//app短信登录
    public static final int LOGINTYPE_FACEBOOK = 2;//Facebook登录


    //还款银行
    public static final String BANK_CODE_PERMATA = "PERMATA";
    public static final String BANK_CODE_MANDIRI = "MANDIRI";
    public static final String BANK_CODE_BCA = "BCA";
    public static final String BANK_CODE_BNI = "BNI";
    public static final String BANK_CODE_OTC = "OTC";
    public static final String BANK_CODE_DUKOPERMATA = "DUKOPERMATA";
    public static final String BANK_CODE_DUKODANAMON = "DUKODANAMON";
    public static final String BANK_CODE_DUKOCIMB = "DUKOCIMB";
    public static final String BANK_CODE_DUKOOTC = "DUKOOTC";


    public static final int UPGRADE = 1;//升级
    public static final int NOTICE = 2;// 公告
    public static final int STOPTAKING = 3;// 停服

    public static final String GOOGLE_PLAY_STORE = "https://play.google.com/store/apps/details?id=com.minirupiah.mr";

    public static final String channel = "channel";// 渠道统计


    public static final String channel_success = "channel_success";// 渠道是否提交成功

    public static final String blackbox = "blackbox";// 设备指纹
    public static final String fingerprint = "fingerprint";// 设备指纹imei


    public static final int apply = 1;//申请中
    public static final int successful_application = 2;//申请成功
    public static final int locked = 3;//锁定中
    public static final int can_withdraw = 4;//可提现
    public static final int cash_withdrawal = 5;//提现中
    public static final int cash_withdrawal_success = 6;//提现成功
    public static final int application_failed = 7;//失败


    public static final int LOAN_ORDER_ALL = 1;//全部
    public static final int LOAN_ORDER_REPAYMENT_COMPLETED = 2;//还款完成

    public static final int NEW_USER = 0;//新用户
    public static final int OLD_USER = 1;//老用户
    public static final int OVERDUE_USER = 2;//逾期用户

    //1:申请中,2:申请失败,3:申请成功4:投资中,5:可提现,6:提现中,7:提现失败,,8:提现成功,9:完成
    public static final int borrowing_order_status1 = 1;//申请中
    public static final int borrowing_order_status2 = 2;//申请失败
    public static final int borrowing_order_status3 = 3;//申请成功
    public static final int borrowing_order_status4 = 4;//投资中
    public static final int borrowing_order_status5 = 5;//可提现
    public static final int borrowing_order_status6 = 6;//提现中
    public static final int borrowing_order_status7 = 7;//提现失败
    public static final int borrowing_order_status8 = 8;//提现成功
    public static final int borrowing_order_status9 = 9;//完成

    //投资理财单个用户到期状态1:申请中,2:未到期,3:已到期,4:提现中,5:完成,6:提现失败
    public static final int borrowing_status1 = 1;//申请中
    public static final int borrowing_status2 = 2;//未到期
    public static final int borrowing_status3 = 3;//已到期
    public static final int borrowing_status4 = 4;//提现中
    public static final int borrowing_status5 = 5;//完成
    public static final int borrowing_status6 = 6;//提现失败

    //2:未完善资料 3:资料审核中 4:资料审核失败 5:资料审核成功 6:可申请投资 7:有订单未汇款
    public static final int INVESTMENT_HOME_STATUS2 = 2;//未完善资料
    public static final int INVESTMENT_HOME_STATUS3 = 3;//资料审核中
    public static final int INVESTMENT_HOME_STATUS4 = 4;//资料审核失败
    public static final int INVESTMENT_HOME_STATUS5 = 5;//资料审核成功
    public static final int INVESTMENT_HOME_STATUS6 = 6;//可申请投资
    public static final int INVESTMENT_HOME_STATUS7 = 7;//有订单未汇款

    //1:有借款进行 2:没有借款进行
    public static final int LOAN_STATUS1 = 1;//有借款进行
    public static final int LOAN_STATUS2 = 2;//没有借款进行

    //保证金状态
    public static final int EARNESTMONEY_UNPAID = 1;//未缴
    public static final int EARNESTMONEY_PAID = 2;//已缴
    public static final int EARNESTMONEY_WITHDRAWAL = 3;//提现中

    public static final int VIP0 = 1;
    public static final int VIP1 = 2;
    public static final int VIP2 = 3;
    public static final int VIP3 = 4;
    public static final int VIP4 = 5;


    //优惠券
    public static final int COUPON_UNUSED = 1;//未使用
    public static final int COUPON_USED = 2;//已使用
    public static final int COUPON_EXPIRED = 3;//已过期

    //优惠券是否可用
    public static final int COUPON_AVAILABLE = 1;//可用
    public static final int COUPON_UNAVAILABLE = 2;//不可用
    //


}
