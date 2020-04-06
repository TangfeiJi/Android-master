package com.project.movice.utils;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串操作工具包
 *
 * @author liux (http://my.oschina.net/liux)
 * @author thanatosx
 * @version 2.0
 *          Updated 2016-08-11
 */
public class StringUtils {

    private final static Pattern emailer = Pattern
            .compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");

    private final static Pattern IMG_URL = Pattern
            .compile(".*?(gif|jpeg|png|jpg|bmp)");

    private final static Pattern URL = Pattern
            .compile("^(https|http)://.*?$(net|com|.com.cn|org|me|)");

    private final static ThreadLocal<SimpleDateFormat> YYYYMMDDHHMMSS = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        }
    };

    private final static ThreadLocal<SimpleDateFormat> YYYYMMDD = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        }
    };

    private final static ThreadLocal<SimpleDateFormat> YYYYMMDDHHMM = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        }
    };

    private static final Pattern UniversalDatePattern = Pattern.compile(
            "([0-9]{4})-([0-9]{2})-([0-9]{2})[\\s]+([0-9]{2}):([0-9]{2}):([0-9]{2})"
    );

    /**
     * 将字符串转位日期类型
     *
     * @param sdate string date that's type loss1ve YYYY-MM-DD HH:mm:ss
     * @return {@link Date}
     */
    public static Date toDate(String sdate) {
        return toDate(sdate, YYYYMMDDHHMMSS.get());
    }

    public static Date toDate(String sdate, SimpleDateFormat formatter) {
        try {
            return formatter.parse(sdate);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * YYYY-MM-DD HH:mm:ss格式的时间字符串转换为{@link Calendar}类型
     *
     * @param str YYYY-MM-DD HH:mm:ss格式字符串
     * @return {@link Calendar}
     */
    public static Calendar parseCalendar(String str) {
        Matcher matcher = UniversalDatePattern.matcher(str);
        Calendar calendar = Calendar.getInstance();
        if (!matcher.find()) return null;
        calendar.set(
                matcher.group(1) == null ? 0 : toInt(matcher.group(1)),
                matcher.group(2) == null ? 0 : toInt(matcher.group(2)) - 1,
                matcher.group(3) == null ? 0 : toInt(matcher.group(3)),
                matcher.group(4) == null ? 0 : toInt(matcher.group(4)),
                matcher.group(5) == null ? 0 : toInt(matcher.group(5)),
                matcher.group(6) == null ? 0 : toInt(matcher.group(6))
        );
        return calendar;
    }

    /**
     * transform date to string that's type loss1ve YYYY-MM-DD HH:mm:ss
     *
     * @param date {@link Date}
     * @return
     */
    public static String getDateString(Date date) {
        return YYYYMMDDHHMMSS.get().format(date);
    }

    /**
     * transform date to string that's type loss1ve YYYY-MM-DD HH:mm
     *
     * @param sdate
     * @return
     */
    public static String getDateString(String sdate) {
        if (TextUtils.isEmpty(sdate)) return "";
        try {
            return YYYYMMDDHHMM.get().format(toDate(sdate));
        } catch (Exception e) {
            return sdate;
        }
    }


    public static String formatYearMonthDay(String st) {
        if (TextUtils.isEmpty(st)) return "";
        Matcher matcher = UniversalDatePattern.matcher(st);
        if (!matcher.find()) return st;
        return String.format("%s年%s月%s日",
                matcher.group(1) == null ? 0 : matcher.group(1),
                matcher.group(2) == null ? 0 : matcher.group(2),
                matcher.group(3) == null ? 0 : matcher.group(3));
    }

    public static String formatYearMonthDayNew(String st) {
        if (TextUtils.isEmpty(st)) return "";
        Matcher matcher = UniversalDatePattern.matcher(st);
        if (!matcher.find()) return st;
        return String.format("%s/%s/%s",
                matcher.group(1) == null ? 0 : matcher.group(1),
                matcher.group(2) == null ? 0 : matcher.group(2),
                matcher.group(3) == null ? 0 : matcher.group(3));
    }

    /**
     * format time friendly
     *
     * @param sdate YYYY-MM-DD HH:mm:ss
     * @return n分钟前, n小时前, 昨天, 前天, n天前, n个月前
     */
    public static String formatSomeAgo(String sdate) {
        if (sdate == null) return "";
        Calendar calendar = parseCalendar(sdate);
        if (calendar == null) return sdate;

        Calendar mCurrentDate = Calendar.getInstance();
        long crim = mCurrentDate.getTimeInMillis(); // current
        long trim = calendar.getTimeInMillis(); // target
        long diff = crim - trim;

        int year = mCurrentDate.get(Calendar.YEAR);
        int month = mCurrentDate.get(Calendar.MONTH);
        int day = mCurrentDate.get(Calendar.DATE);

        if (diff < 60 * 1000) {
            return "刚刚";
        }
        if (diff >= 60 * 1000 && diff < AlarmManager.INTERVAL_HOUR) {
            return String.format("%s分钟前", diff / 60 / 1000);
        }
        mCurrentDate.set(year, month, day, 0, 0, 0);
        if (trim >= mCurrentDate.getTimeInMillis()) {
            return String.format("%s小时前", diff / AlarmManager.INTERVAL_HOUR);
        }
        mCurrentDate.set(year, month, day - 1, 0, 0, 0);
        if (trim >= mCurrentDate.getTimeInMillis()) {
            return "昨天";
        }
        mCurrentDate.set(year, month, day - 2, 0, 0, 0);
        if (trim >= mCurrentDate.getTimeInMillis()) {
            return "前天";
        }
        if (diff < AlarmManager.INTERVAL_DAY * 30) {
            return String.format("%s天前", diff / AlarmManager.INTERVAL_DAY);
        }
        if (diff < AlarmManager.INTERVAL_DAY * 30 * 12) {
            return String.format("%s月前", diff / (AlarmManager.INTERVAL_DAY * 30));
        }
        return String.format("%s年前", mCurrentDate.get(Calendar.YEAR) - calendar.get(Calendar.YEAR));
    }

    /**
     * @param str YYYY-MM-DD HH:mm:ss string
     * @return 今天, 昨天, 前天, n天前
     */
    public static String formatSomeDay(String str) {
        return formatSomeDay(parseCalendar(str));
    }

    /**
     * @param calendar {@link Calendar}
     * @return 今天, 昨天, 前天, n天前
     */
    public static String formatSomeDay(Calendar calendar) {
        if (calendar == null) return "?天前";
        Calendar mCurrentDate = Calendar.getInstance();
        long crim = mCurrentDate.getTimeInMillis(); // current
        long trim = calendar.getTimeInMillis(); // target
        long diff = crim - trim;

        int year = mCurrentDate.get(Calendar.YEAR);
        int month = mCurrentDate.get(Calendar.MONTH);
        int day = mCurrentDate.get(Calendar.DATE);

        mCurrentDate.set(year, month, day, 0, 0, 0);
        if (trim >= mCurrentDate.getTimeInMillis()) {
            return "今天";
        }
        mCurrentDate.set(year, month, day - 1, 0, 0, 0);
        if (trim >= mCurrentDate.getTimeInMillis()) {
            return "昨天";
        }
        mCurrentDate.set(year, month, day - 2, 0, 0, 0);
        if (trim >= mCurrentDate.getTimeInMillis()) {
            return "前天";
        }
        return String.format("%s天前", diff / AlarmManager.INTERVAL_DAY);
    }

    /**
     * @param calendar {@link Calendar}
     * @return 星期n
     */
    public static String formatWeek(Calendar calendar) {
        if (calendar == null) return "星期?";
        return new String[]{"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"}[calendar.get(Calendar.DAY_OF_WEEK) - 1];
    }

    /**
     * @param str YYYY-MM-DD HH:mm:ss string
     * @return 星期n
     */
    public static String formatWeek(String str) {
        return formatWeek(parseCalendar(str));
    }

    /**
     * @param sdate YYYY-MM-DD HH:mm:ss string
     * @return
     */
    public static String formatDayWeek(String sdate) {
        Calendar calendar = parseCalendar(sdate);
        if (calendar == null) return "??/?? 星期?";
        Calendar mCurrentDate = Calendar.getInstance();
        String ws = formatWeek(calendar);
        int diff = mCurrentDate.get(Calendar.DATE) - calendar.get(Calendar.DATE);
        if (diff == 0) {
            return "今天 / " + ws;
        }
        if (diff == 1) {
            return "昨天 / " + ws;
        }
        int m = calendar.get(Calendar.MONTH);
        int d = calendar.get(Calendar.DATE);
        return String.format("%s/%s / %s", formatInt(m), formatInt(d), ws);
    }

    /**
     * format to HH
     *
     * @param i integer
     * @return HH
     */
    public static String formatInt(int i) {
        return (i < 10 ? "0" : "") + i;
    }

    public static String formatDayTime(String time) {
        if (TextUtils.isEmpty(time))
            return "";
        try {
            SimpleDateFormat format = YYYYMMDD.get();
            Calendar calendar = parseCalendar(time);
            return format.format(calendar.getTime());
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 智能格式化
     */
    public static String friendly_time3(String sdate) {
        Calendar calendar = parseCalendar(sdate);
        if (calendar == null) return sdate;

        Calendar mCurrentDate = Calendar.getInstance();
        SimpleDateFormat formatter = YYYYMMDDHHMMSS.get();

        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        String s = hour >= 0 && hour < 12 ? "上午" : "下午";
        s += " HH:mm";

        if (mCurrentDate.get(Calendar.DATE) == calendar.get(Calendar.DATE)) {
            formatter.applyPattern(s);
        } else if (mCurrentDate.get(Calendar.DATE) - calendar.get(Calendar.DATE) == 1) {
            formatter.applyPattern("昨天 " + s);
        } else if (mCurrentDate.get(Calendar.YEAR) == calendar.get(Calendar.YEAR)) {
            formatter.applyPattern("MM-dd " + s);
        } else {
            formatter.applyPattern("yyyy-MM-dd " + s);
        }
        return formatter.format(calendar.getTime());
    }


    /**
     * 判断给定字符串时间是否为今日
     *
     * @param sdate
     * @return boolean
     */
    public static boolean isToday(String sdate) {
        Date time = toDate(sdate);
        Date today = new Date();
        if (time != null) {
            String nowDate = YYYYMMDD.get().format(today);
            String timeDate = YYYYMMDD.get().format(time);
            if (nowDate.equals(timeDate)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 是否是相同的一天
     *
     * @param sdate1 sdate1
     * @param sdate2 sdate2
     * @return
     */
    public static boolean isSameDay(String sdate1, String sdate2) {
        if (TextUtils.isEmpty(sdate1) || TextUtils.isEmpty(sdate2)) {
            return false;
        }
        Date date1 = toDate(sdate1);
        Date date2 = toDate(sdate2);
        if (date1 != null && date2 != null) {
            String d1 = YYYYMMDD.get().format(date1);
            String d2 = YYYYMMDD.get().format(date2);
            if (d1.equals(d2)) {
                return true;
            }
        }
        return false;
    }

    public static String getCurrentTimeStr() {
        return YYYYMMDDHHMMSS.get().format(new Date());
    }

    /***
     * 计算两个时间差，返回的是的秒s
     *
     * @param date1
     * @param date2
     * @return
     * @author 火蚁 2015-2-9 下午4:50:06
     */
    public static long calDateDifferent(String date1, String date2) {
        try {
            Date d1 = YYYYMMDDHHMMSS.get().parse(date1);
            Date d2 = YYYYMMDDHHMMSS.get().parse(date2);
            // 毫秒ms
            long diff = d2.getTime() - d1.getTime();
            return diff / 1000;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 判断给定字符串是否空白串。 空白串是指由空格、制表符、回车符、换行符组成的字符串 若输入字符串为null或空字符串，返回true
     *
     * @param input
     * @return boolean   true 空   false 不为空
     */
    public static boolean isEmpty(String input) {
        if (input == null || "".equals(input))
            return true;

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断是不是一个合法的电子邮件地址
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        if (email == null || email.trim().length() == 0)
            return false;
        email = email.replaceAll(" ", "");
        return emailer.matcher(email).matches();
    }

    /**
     * 判断一个url是否为图片url
     *
     * @param url
     * @return
     */
    public static boolean isImgUrl(String url) {
        if (url == null || url.trim().length() == 0)
            return false;
        return IMG_URL.matcher(url).matches();
    }

    /**
     * 判断是否为一个合法的url地址
     *
     * @param str
     * @return
     */
    public static boolean isUrl(String str) {
        if (str == null || str.trim().length() == 0)
            return false;
        return URL.matcher(str).matches();
    }

    /**
     * 字符串转整数
     *
     * @param str
     * @param defValue
     * @return
     */
    public static int toInt(String str, int defValue) {
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
            Log.d("oschina", e.getMessage());
        }
        return defValue;
    }

    /**
     * 对象转整数
     *
     * @param obj
     * @return 转换异常返回 0
     */
    public static int toInt(Object obj) {
        if (obj == null)
            return 0;
        return toInt(obj.toString(), 0);
    }

    /**
     * 对象转整数
     *
     * @param obj
     * @return 转换异常返回 0
     */
    public static long toLong(String obj) {
        try {
            return Long.parseLong(obj);
        } catch (Exception e) {
        }
        return 0;
    }

    /**
     * 字符串转布尔值
     *
     * @param b
     * @return 转换异常返回 false
     */
    public static boolean toBool(String b) {
        try {
            return Boolean.parseBoolean(b);
        } catch (Exception e) {
        }
        return false;
    }

    public static String getString(String s) {
        return s == null ? "" : s;
    }

    /**
     * 将一个InputStream流转换成字符串
     *
     * @param is
     * @return
     */
    public static String toConvertString(InputStream is) {
        StringBuilder res = new StringBuilder();
        BufferedReader read = new BufferedReader(new InputStreamReader(is));
        try {
            String line;
            while ((line = read.readLine()) != null) {
                res.append(line).append("<br>");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                read.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return res.toString();
    }

    /***
     * 截取字符串
     *
     * @param start 从那里开始，0算起
     * @param num   截取多少个
     * @param str   截取的字符串
     * @return
     */
    public static String getSubString(int start, int num, String str) {
        if (str == null) {
            return "";
        }
        int length = str.length();
        if (start < 0) {
            start = 0;
        }
        if (start > length) {
            start = length;
        }
        if (num < 0) {
            num = 1;
        }
        int end = start + num;
        if (end > length) {
            end = length;
        }
        return str.substring(start, end);
    }

    /**
     * 获取当前时间为每年第几周
     *
     * @return
     */
    public static int getWeekOfYear() {
        return getWeekOfYear(new Date());
    }

    /**
     * 获取当前时间为每年第几周
     *
     * @param date
     * @return
     */
    public static int getWeekOfYear(Date date) {
        Calendar c = Calendar.getInstance();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setTime(date);
        int week = c.get(Calendar.WEEK_OF_YEAR) - 1;
        week = week == 0 ? 52 : week;
        return week > 0 ? week : 1;
    }

    public static int[] getCurrentDate() {
        int[] dateBundle = new int[3];
        String[] temp = getDataTime("yyyy-MM-dd").split("-");

        for (int i = 0; i < 3; i++) {
            try {
                dateBundle[i] = Integer.parseInt(temp[i]);
            } catch (Exception e) {
                dateBundle[i] = 0;
            }
        }
        return dateBundle;
    }

    /**
     * 返回当前系统时间
     */
    public static String getDataTime(String format) {
        return new SimpleDateFormat(format, Locale.getDefault()).format(new Date());
    }

    @SuppressLint("DefaultLocale")
    public static String formatDistance(int distance) {
        return distance < 1000 ?
                String.format("%s00m 以内", distance > 100 ? distance / 100 : 1)
                : String.format("%skm 以内", distance / 1000);
    }

    /**
     * 金额3位加点
     *
     * @param str
     * @param num
     * @return
     */
    public static String getAmount(String str, int num) {
        StringBuilder sb = new StringBuilder(str.replace(" ", ""));
        int i = sb.length() / num;
        int j = sb.length() % num;
//        if(j==0)
//            j = 1;
//        int c = num;
//        for (int d = j; d < i; d++) {
//            sb = sb.insert(str.length() - c, ".");
//            c += num;
//        }
        for (int x = (j == 0 ? i - 1 : i); x > 0; x--) {
            sb = sb.insert(sb.length() - (x * num), ".");
        }
        return sb.toString();
    }


    /***
     * 给银行卡加空格
     * @param string
     * @return
     */
    public static String getblank(String string) {
        if (isEmpty(string))
            return "";
        StringBuilder str = new StringBuilder(string.replace(" ", ""));
        int i = str.length() / 4;
        int j = str.length() % 4;
        for (int x = (j == 0 ? i - 1 : i); x > 0; x--) {
            str = str.insert(x * 4, " ");
        }
        return str.toString();
    }


    public static String sHA1(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), PackageManager.GET_SIGNATURES);
            byte[] cert = info.signatures[0].toByteArray();
            MessageDigest md = MessageDigest.getInstance("SHA1");
            byte[] publicKey = md.digest(cert);
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < publicKey.length; i++) {
                String appendString = Integer.toHexString(0xFF & publicKey[i])
                        .toUpperCase(Locale.US);
                if (appendString.length() == 1)
                    hexString.append("0");
                hexString.append(appendString);
                hexString.append(":");
            }
            String result = hexString.toString();
            return result.substring(0, result.length() - 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 保存错误信息到文件中
     *
     * @param ex
     * @return 返回文件名称, 便于将文件传送到服务器
     */
    public static String saveCrashInfo2File(Throwable ex) {
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        String result = writer.toString();
        return result;
    }

    public static String getJson(String fileName, Context context) {
        //将json数据变成字符串
        StringBuilder stringBuilder = new StringBuilder();
        try {
            //获取assets资源管理器
            AssetManager assetManager = context.getAssets();
            //通过管理器打开文件并读取
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();

    }

}