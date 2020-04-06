package com.project.movice.modules.mycalendar;

import android.content.Context;

import com.project.movice.utils.DataUtils;
import com.project.movice.utils.DateTimeUtil;
import com.project.movice.utils.PhoneUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.project.movice.utils.DateTimeUtil.getDateToLong;

public class MyCalenderUtils {
    //1.注册成功2天内没有申请贷款  提醒可以申请贷款
//【淘多多】尊敬的淘多多用户{$name}，您的资料已经审核通过，请打开APP申请提现，1分钟到账，续贷降息提额
//
//2.借款成功后，还款日期提前2天提醒
//【淘多多】亲爱的{$name}，您的账单已出，最后还款日{$day}，请您按时还款
//
//3.借款成功后，还款日期提前1天提醒
//【淘多多】亲爱的{$name}，您的账单已出，最后还款日{$day}，请您按时还款
//
//4.借款成功后，还款日期当天提醒
//【淘多多】{$name}您好，今天是您的还款日，请尽快app主动还款，以免对您以后的续贷提额和信用记录产生影响，如已还款，请忽略
//
//5.借款成功后，逾期不还款的提醒
//【淘多多】{$name}您好，您在我平台借款已逾期，为避免对您信用记录产生影响，请尽快处理
//
//6.还款成功后一周提醒，可以借款
//【淘多多】尊敬的淘多多用户{$name}，您的资料已经审核通过，请打开APP申请提现，1分钟到账，续贷降息提额

    //    private static String REGIST_AFTER_STR = "【%s】尊敬的%s用户，您的资料已经审核通过，请打开APP申请提现，1分钟到账，续贷降息提额";
    private static String REGIST_AFTER_STR = "【%s】Nasabah%s,data anda telah lolos verifikasi, silahkan buka aplikasi untuk mencairkan dana, transfer dana dalam waktu 1 menit, pinjaman berikutnya bunga berkurang limit meningkat";

    //    private static String BORROW_AFTER_2D_STR = "【%s】亲爱的%s，您的账单已出，最后还款日%s，请您按时还款";
    private static String BORROW_AFTER_2D_STR = "【%s】 Halo%s, tagihan anda telah tiba, lunasi pinjaman anda selambat-lambatnya pada %s";

    //    private static String BORROW_AFTER_1D_STR = "【%s】亲爱的%s，您的账单已出，最后还款日%s，请您按时还款";
    private static String BORROW_AFTER_1D_STR = "【%s】 Halo%s, tagihan anda telah tiba, lunasi pinjaman anda selambat-lambatnya pada %s";

    //    private static String BORROW_AFTER_0D_STR = "【%s】%s您好，今天是您的还款日，请尽快app主动还款，以免对您以后的续贷提额和信用记录产生影响，如已还款，请忽略";
    private static String BORROW_AFTER_0D_STR = "【%s】Halo%s,hari ini adalah hari jatuh tempo pembayaran anda, silahkan segera melalui aplikasi  lunasi pinjaman anda untuk menghindari pengaruh pada nilai limit dan catatan pinjaman anda berikutnya. Jika anda sudah melunasi pembayaran abaikan pesan ini.";

    //    private static String BORROW_AFTER_EXPECT_STR = "【%s】%s您好，您在我平台借款已逾期，为避免对您信用记录产生影响，请尽快处理";//逾期
    private static String BORROW_AFTER_EXPECT_STR = "【%s】%sHalo, anda telah masuk jatuh tempo di aplikasi kami, untuk menghindari dampak pada catatan pinjaman anda silahkan segera lunasi pinjaman anda.";//逾期

    //    private static String BORROW_AFTER_SUC_STR = "【%s】尊敬的%s用户%s，您的资料已经审核通过，请打开APP申请提现，1分钟到账，续贷降息提额";//还款成功
    private static String BORROW_AFTER_SUC_STR = "【%s】Nasabah%s,data anda telah lolos verifikasi, silahkan bukaaplikasi untuk mencairkan dana, transfer dana dalam waktu 1 menit, pinjaman berikutnya bunga berkurang limit meningkat";//还款成功
    //对外暴露
    public static String REGIST_AFTER_ID = "101";//
    public static String BORROW_AFTER_2D_ID = "102";
    public static String BORROW_AFTER_1D_ID = "103";
    public static String BORROW_AFTER_0D_ID = "104";
    public static String BORROW_AFTER_EXPECT_ID = "105";//逾期
    public static String BORROW_AFTER_SUC_ID = "106";//还款成功
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private static SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");


//    format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        return format.parse(param).getTime();


    public static void registNotify(final Context context) {//注册成功两天
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
        try {
            delCalendar(context, REGIST_AFTER_ID);
            Date date = DateTimeUtil.getDateAfter(new Date(), 2);
            String time = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(sdf.format(date)).getTime() + "";
            EventModel eventModel = new EventModel();
            eventModel.setContent(String.format(REGIST_AFTER_STR, PhoneUtils.getAppName(), PhoneUtils.getAppName()));
            eventModel.setId(REGIST_AFTER_ID);
            eventModel.setTime(time);
            addCalendar(context, eventModel);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        }
    }




    public static void borrowSucDayNotify(final Context context, final Date repayTime) throws ParseException {//还款前
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            delSucCalendar2(context);
            String datebefore = sdf2.format(repayTime) + " 08:00";
            Date repayDate = null;
            try {
                repayDate = sdf.parse(datebefore);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            EventModel eventModel2 = new EventModel();
            eventModel2.setContent(String.format(BORROW_AFTER_2D_STR, PhoneUtils.getAppName(), DataUtils.getPhone(context), sdf2.format(repayDate)));
            eventModel2.setId(BORROW_AFTER_2D_ID);
            eventModel2.setTime(getDateToLong(DateTimeUtil.getDateBefore(repayDate, 2)));
            addCalendar(context, eventModel2);
            EventModel eventModel1 = new EventModel();
            eventModel1.setContent(String.format(BORROW_AFTER_1D_STR, PhoneUtils.getAppName(), DataUtils.getPhone(context), sdf2.format(repayDate)));
            eventModel1.setId(BORROW_AFTER_1D_ID);
            eventModel1.setTime(getDateToLong(DateTimeUtil.getDateBefore(repayDate, 1)));
            addCalendar(context, eventModel1);
            EventModel eventModel0 = new EventModel();
            eventModel0.setContent(String.format(BORROW_AFTER_0D_STR, PhoneUtils.getAppName(), DataUtils.getPhone(context)));
            eventModel0.setId(BORROW_AFTER_0D_ID);
            eventModel0.setTime(getDateToLong((repayDate)));
            addCalendar(context, eventModel0);
            CalendarEvent.deleteEvent(REGIST_AFTER_ID);
            borrowExpectNotify(context, repayDate);
        }
    }


    private static void borrowExpectNotify(Context context, Date date1) throws ParseException {//逾期

        Date date = DateTimeUtil.getDateAfter(date1, 1);
        EventModel eventModel = new EventModel();
        eventModel.setContent(String.format(BORROW_AFTER_EXPECT_STR, PhoneUtils.getAppName(), DataUtils.getPhone(context)));
        eventModel.setId(BORROW_AFTER_EXPECT_ID);
        eventModel.setTime(getDateToLong(date));
        addCalendar(context, eventModel);
    }

    public static void borrowWeekNotify(final Context context) throws ParseException {//还款成功一周
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
        delSucCalendar2(context);
        delCalendar(context, BORROW_AFTER_SUC_ID);
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = DateTimeUtil.getDateAfter(new Date(), 7);
        EventModel eventModel = new EventModel();
        eventModel.setContent(String.format(BORROW_AFTER_SUC_STR, PhoneUtils.getAppName(), PhoneUtils.getAppName(), DataUtils.getPhone(context)));
        eventModel.setId(BORROW_AFTER_SUC_ID);
        eventModel.setTime(getDateToLong(date));

        addCalendar(context, eventModel);
        //删除逾期
//          delCalendar(BORROW_AFTER_EXPECT_ID);
    }}


    private static void addCalendar(Context context, final EventModel eventModel) {
        eventModel.setContent(eventModel.getContent());
        eventModel.setId(eventModel.getId());
        eventModel.setTime(eventModel.getTime());
        CalendarEvent.insertEvent(eventModel);


    }


    private void upDateCalendar(EventModel eventModel) {
        eventModel.setContent(eventModel.getContent());
        eventModel.setId(eventModel.getId());
        eventModel.setTime(eventModel.getTime());
        CalendarEvent.updateEvent(eventModel);
    }

    public static void queryCalendar() {
        List<EventModel> list = CalendarEvent.queryEvents();
    }


    //删除逾期外的
    public static void delSucCalendar(Context context) {
        CalendarEvent.deleteEvent(REGIST_AFTER_ID);
        CalendarEvent.deleteEvent(BORROW_AFTER_2D_ID);
        CalendarEvent.deleteEvent(BORROW_AFTER_1D_ID);
        CalendarEvent.deleteEvent(BORROW_AFTER_0D_ID);
        CalendarEvent.deleteEvent(BORROW_AFTER_SUC_ID);
    }


    //删除借款提醒外的
    private static void delSucCalendar2(Context context) {
        CalendarEvent.deleteEvent(REGIST_AFTER_ID);
        CalendarEvent.deleteEvent(BORROW_AFTER_2D_ID);
        CalendarEvent.deleteEvent(BORROW_AFTER_1D_ID);
        CalendarEvent.deleteEvent(BORROW_AFTER_0D_ID);
        CalendarEvent.deleteEvent(BORROW_AFTER_EXPECT_ID);
        CalendarEvent.deleteEvent(BORROW_AFTER_SUC_ID);
    }

    public static void delAllCalendar() {
        CalendarEvent.deleteAllEvent();
    }


    public static void delCalendar(Context context, final String id) {

            CalendarEvent.deleteEvent(id);

    }


}
