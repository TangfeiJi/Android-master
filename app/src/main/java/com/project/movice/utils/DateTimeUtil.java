package com.project.movice.utils;

import android.annotation.SuppressLint;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 时间、日期工具类
 * Created by wy on 2018/1/12 15:44.
 */

public class DateTimeUtil {

    static SimpleDateFormat format;

    /**
     * 日期格式：yyyy-MM-dd HH:mm:ss
     **/
    public static final String DF_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    /**
     * 日期格式：DF_DD_MM_YYYY
     **/
    public static final String DF_DD_MM_YYYY = "dd-MM-yyyy";
    /**
     * 日期格式：MM/dd HH:mm:ss
     **/
    public static final String DF_MM_DD_HH_MM_SS = "MM/dd HH:mm:ss";

    /**
     * 日期格式：yyyy-MM-dd HH:mm
     **/
    public static final String DF_YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";

    /**
     * 日期格式：yyyy-MM-dd
     **/
    public static final String DF_YYYY_MM_DD = "yyyy-MM-dd";
    /**
     * 英文日期格式：yyyy-MM-dd
     **/
    public static final String EN_DF_YYYY_MM_DD = "MMM d, yyyy";
    /**
     * 英文日期格式：HH:mm MMM d, yyyy
     **/
    public static final String EN_DF_HH_MM_MM_D_YYYY = "HH:mm MMM d, yyyy";


    private final static long minute = 60 * 1000;// 1分钟
    private final static long hour = 60 * minute;// 1小时
    private final static long day = 24 * hour;// 1天
    private final static long month = 31 * day;// 月
    private final static long year = 12 * month;// 年


    public DateTimeUtil() {

    }


    /**
     * 将日期以yyyy-MM-dd HH:mm:ss格式化
     *
     * @param dateL 日期
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String formatDateTime(long dateL, String formater) {
        SimpleDateFormat sdf = new SimpleDateFormat(formater);
        return sdf.format(new Date(dateL));
    }

    /**
     * 将日期以yyyy-MM-dd HH:mm:ss英文格式化
     *
     * @param dateL 日期
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String formatEnDateTime(long dateL, String formater) {
        SimpleDateFormat sdf = new SimpleDateFormat(formater, Locale.ENGLISH);
        return sdf.format(new Date(dateL));
    }


    /**
     * 判断当前时间是否在[startTime, endTime]区间，注意时间格式要一致
     *
     * @param curDate 当前时间
     * @param sData   开始时间
     * @param eData   结束时间
     * @return
     * @author wy
     */
    public static boolean isEffectiveDate(long curDate, String sData, String eData) {

        Date startTime = null;
        Date endTime = null;
        Date nowTime = new Date(curDate);//获取当前时间
        DateFormat s = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat d = new SimpleDateFormat("yyyy-MM-dd");
        try {
            startTime = s.parse(sData);
            endTime = d.parse(eData);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        if (nowTime.getTime() == startTime.getTime()
                || nowTime.getTime() == endTime.getTime()) {
            return true;
        }
        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);

        Calendar begin = Calendar.getInstance();
        begin.setTime(startTime);

        Calendar end = Calendar.getInstance();
        end.setTime(endTime);

        if (date.after(begin) && date.before(end)) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * 日期加一天
     *
     * @param date
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static long getTimeDatePlusOnDday(long date) {
        long diff = 0;
        try {
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//            Date sDate = sdf.parse(date);
            Date nowTime = new Date(date);//获取当前时间
            Calendar c = Calendar.getInstance();
            c.setTime(nowTime);
            c.add(Calendar.DAY_OF_MONTH, 1);
            nowTime = c.getTime();
            diff = nowTime.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return diff;
    }


//+++++
   public static Date getDateAfter(Date time,int days){
       Calendar c = Calendar.getInstance();
       c.setTime(time);
       c.add(Calendar.DAY_OF_MONTH, days);// 今天n天



       return  c.getTime();

   }
//-----
    public static Date getDateBefore(Date time,int days){
        Calendar c = Calendar.getInstance();
        c.setTime(time);
        c.add(Calendar.DAY_OF_MONTH, -days);
        return  c.getTime();

    }




    public static String getDateToLong(Date time){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
        try {
            return  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS").parse(sdf.format(time)).getTime() + "";
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }




    public static Date longToDate(long currentTime)
            throws ParseException {
        Date dateOld = new Date(currentTime); // 根据long类型的毫秒数生命一个date类型的时间
        String sDateTime = dateToString(dateOld, "yyyy-MM-dd"); // 把date类型的时间转换为string
        Date date = stringToDate(sDateTime, "yyyy-MM-dd"); // 把String类型转换为Date类型
        return date;
    }
    // formatType格式为yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日 HH时mm分ss秒
    // data Date类型的时间
    public static String dateToString(Date data, String formatType) {
        return new SimpleDateFormat(formatType).format(data);
    }

    // strTime要转换的string类型的时间，formatType要转换的格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日
    // HH时mm分ss秒，
    // strTime的时间格式必须要与formatType的时间格式相同
    public static Date stringToDate(String strTime, String formatType)
            throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(formatType);
        Date date = null;
        date = formatter.parse(strTime);
        return date;
    }
}