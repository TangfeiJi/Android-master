package com.project.movice.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.usage.StorageStatsManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.BatteryManager;
import android.os.Build;
import android.os.StatFs;
import android.os.storage.StorageManager;
import android.os.storage.StorageVolume;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.text.format.Formatter;
import android.util.Log;

import com.project.movice.modules.main.bean.Param;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.UUID;


import fairy.easy.httpmodel.HttpModelHelper;
import fairy.easy.httpmodel.resource.HttpListener;
import fairy.easy.httpmodel.resource.HttpType;

import static android.content.Context.BATTERY_SERVICE;
import static android.content.Context.WIFI_SERVICE;
import static com.project.movice.application.MoviceApp.getContext;

/**
 * Email: 2185134304@qq.com
 * Created by JackChen 2018/3/8 13:20
 * Version 1.0
 * Params:
 * Description:  获取手机相关信息
 */
public class PhoneHelper {

    private static PhoneHelper mPhoneHelper;
    private static TelephonyManager tm;
    private Context mContext;
    String device_model = Build.MODEL; // 设备型号 。
    private static TelephonyManager telephonyManager;


    private PhoneHelper(Context context) {
        mContext = context;
        tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
    }

    public static PhoneHelper getInstance(Context context) {
        if (mPhoneHelper == null) {
            synchronized (PhoneHelper.class) {
                if (mPhoneHelper == null) {
                    mPhoneHelper = new PhoneHelper(context);
                }
            }
        }
        return mPhoneHelper;
    }


    /**
     * 判断是否模拟器。如果返回TRUE， 则当前是模拟器,模拟器IMEI是：00000000000000 运营商不让支付
     *
     * @param context
     * @return
     */
    public static boolean  isEmulator(Context context) {
        try {

            @SuppressLint("MissingPermission") String imei = tm.getDeviceId();
            if (imei != null && imei.equals("000000000000000")) {
                return true;
            }
            return (Build.MODEL.equals("sdk"))
                    || (Build.MODEL.equals("google_sdk"));
        } catch (Exception ioe) {
            Log.w("PhoneHelper", "009:" + ioe.toString());
        }
        return false;
    }


    /**
     * Android客户端获取IP地址
     *
     * @return
     */
    public static String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException ex) {

        }
        return null;
    }

    public static String batteryStatus(int status) {
        String healthBat = "uKnown";
        switch (status) {
            case BatteryManager.BATTERY_STATUS_CHARGING:
                healthBat = "charging";//充电
                break;
            case BatteryManager.BATTERY_STATUS_DISCHARGING:
                healthBat = "disCharging";//放电
                break;
            case BatteryManager.BATTERY_STATUS_FULL:
                healthBat = "full";//满的
                break;
            case BatteryManager.BATTERY_STATUS_NOT_CHARGING:
                healthBat = "notCharging";//不充电
                break;
            case BatteryManager.BATTERY_STATUS_UNKNOWN:
                healthBat = "unknown";//不知道
                break;
            default:
                break;
        }
        return healthBat;
    }

    /**
     * 根据IP地址获取MAC地址
     *
     * @return
     */
    public static String getLocalMacAddressFromIp() {
        String strMacAddr = null;
        try {
            //获得IpD地址
            InetAddress ip = getLocalInetAddress();
            byte[] b = NetworkInterface.getByInetAddress(ip).getHardwareAddress();
            StringBuffer buffer = new StringBuffer();
            for (int i = 0; i < b.length; i++) {
                if (i != 0) {
                    buffer.append(':');
                }
                String str = Integer.toHexString(b[i] & 0xFF);
                buffer.append(str.length() == 1 ? 0 + str : str);
            }
            strMacAddr = buffer.toString().toUpperCase();
        } catch (Exception e) {

        }

        return strMacAddr;
    }

    /**
     * 获取移动设备本地IP
     *
     * @return
     */
    private static InetAddress getLocalInetAddress() {
        InetAddress ip = null;
        try {
            //列举
            Enumeration<NetworkInterface> en_netInterface = NetworkInterface.getNetworkInterfaces();
            while (en_netInterface.hasMoreElements()) {//是否还有元素
                NetworkInterface ni = (NetworkInterface) en_netInterface.nextElement();//得到下一个元素
                Enumeration<InetAddress> en_ip = ni.getInetAddresses();//得到一个ip地址的列举
                while (en_ip.hasMoreElements()) {
                    ip = en_ip.nextElement();
                    if (!ip.isLoopbackAddress() && ip.getHostAddress().indexOf(":") == -1)
                        break;
                    else
                        ip = null;
                }

                if (ip != null) {
                    break;
                }
            }
        } catch (SocketException e) {

            e.printStackTrace();
        }
        return ip;
    }


    /**
     * 获取当前连接WIFI的SSID
     */
    public  static  String getSSID(Context context) {
        WifiManager wm = (WifiManager) context.getSystemService(WIFI_SERVICE);
        if (wm != null) {
            WifiInfo winfo = wm.getConnectionInfo();
            if (winfo != null) {
                String s = winfo.getSSID();
                if (s.length() > 2 && s.charAt(0) == '"' && s.charAt(s.length() - 1) == '"') {
                    return s.substring(1, s.length() - 1);
                }
            }
        }
        return "";
    }

    //获取wifiIp
    public static String getWifiIp(Context context) {
        if (context == null) {
            throw new NullPointerException("Global context is null");
        }
        WifiManager wifiMgr = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        if (isWifiEnabled(context)) {
            int ipAsInt = wifiMgr.getConnectionInfo().getIpAddress();
            if (ipAsInt == 0) {
                return null;
            } else {
                return integer2Ip(ipAsInt) + "";
            }
        } else {
            return null;
        }
    }

    /**
     * 将int数字转换成ipv4地址
     */
    public static String integer2Ip(int ip) {
        StringBuilder sb = new StringBuilder();
        int num = 0;
        boolean needPoint = false; // 是否需要加入'.'
        for (int i = 0; i < 4; i++) {
            if (needPoint) {
                sb.append('.');
            }
            needPoint = true;
            int offset = 8 * (3 - i);
            num = (ip >> offset) & 0xff;
            sb.append(num);
        }
        return sb.toString();
    }

    //是否是wifi
    public static boolean isWifiEnabled(Context context) {
        if (context == null) {
            throw new NullPointerException("Global context is null");
        }
        WifiManager wifiMgr = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        if (wifiMgr.getWifiState() == WifiManager.WIFI_STATE_ENABLED) {
            ConnectivityManager connManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo wifiInfo = connManager
                    .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            return wifiInfo.isConnected();
        } else {
            return false;
        }
    }


    public static String getMemoryInfo(File path, Context context,int type) {        //获得一个磁盘状态对象
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();   //获得一个扇区的大小
        long totalBlocks = stat.getBlockCount();   //获得扇区的数量
        long availableBlocks = stat.getAvailableBlocks();   //获得可用扇区的数量
        //总空间
        String totalMemory = Formatter.formatFileSize(context, blockSize * totalBlocks);        //可用空间
        String availableMemory = Formatter.formatFileSize(context, blockSize * availableBlocks);

        if(type==1){
            return  totalMemory ;
        }else{
            return  availableMemory;
        }

    }

    //    获取手机当前电量
    public static int getBatteryLevel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            BatteryManager batteryManager = (BatteryManager) context.getSystemService(BATTERY_SERVICE);
            return batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);
        } else {
            Intent intent = new ContextWrapper(context).
                    registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
            return (intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) * 100) /
                    intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
        }
    }


    /**
     * 判断手机是否ROOT
     */
    public static boolean isSystemRoot() {
        boolean isRoot = false;
        try {
            isRoot = (new File("/system/bin/su").exists())
                    || (new File("/system/xbin/su").exists());
        } catch (Exception e) {

        }
        return isRoot;
    }


    /**
     * 判断当前设备是手机还是平板，代码来自 Google I/O App for Android
     *
     * @param context
     * @return 平板返回 True，手机返回 False
     */
    public static boolean isPad(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }


    //获取手机的真实内存
    public static String getRealStorage(Context context) {
        long total = 0L;
        try {
            StorageManager storageManager = (StorageManager) context.getSystemService(Context.STORAGE_SERVICE);
            int version = Build.VERSION.SDK_INT;
            float unit = version >= Build.VERSION_CODES.O ? 1000 : 1024;
            if (version < Build.VERSION_CODES.M) {
                Method getVolumeList = StorageManager.class.getDeclaredMethod("getVolumeList");
                StorageVolume[] volumeList = (StorageVolume[]) getVolumeList.invoke(storageManager);
                if (volumeList != null) {
                    Method getPathFile = null;
                    for (StorageVolume volume : volumeList) {
                        if (getPathFile == null) {
                            getPathFile = volume.getClass().getDeclaredMethod("getPathFile");
                        }
                        File file = (File) getPathFile.invoke(volume);
                        total += file.getTotalSpace();
                    }
                }
            } else {
                @SuppressLint("PrivateApi") Method getVolumes = StorageManager.class.getDeclaredMethod("getVolumes");
                List<Object> getVolumeInfo = (List<Object>) getVolumes.invoke(storageManager);
                for (Object obj : getVolumeInfo) {
                    Field getType = obj.getClass().getField("type");
                    int type = getType.getInt(obj);
                    if (type == 1) {
                        long totalSize = 0L;
                        if (version >= Build.VERSION_CODES.O) {
                            Method getFsUuid = obj.getClass().getDeclaredMethod("getFsUuid");
                            String fsUuid = (String) getFsUuid.invoke(obj);
                            totalSize = getTotalSize(context, fsUuid);
                        } else if (version >= Build.VERSION_CODES.N_MR1) {
                            Method getPrimaryStorageSize = StorageManager.class.getMethod("getPrimaryStorageSize");
                            totalSize = (long) getPrimaryStorageSize.invoke(storageManager);
                        }
                        Method isMountedReadable = obj.getClass().getDeclaredMethod("isMountedReadable");
                        boolean readable = (boolean) isMountedReadable.invoke(obj);
                        if (readable) {
                            Method file = obj.getClass().getDeclaredMethod("getPath");
                            File f = (File) file.invoke(obj);
                            if (totalSize == 0) {
                                totalSize = f.getTotalSpace();
                            }
                            total += totalSize;
                        }
                    } else if (type == 0) {
                        Method isMountedReadable = obj.getClass().getDeclaredMethod("isMountedReadable");
                        boolean readable = (boolean) isMountedReadable.invoke(obj);
                        if (readable) {
                            Method file = obj.getClass().getDeclaredMethod("getPath");
                            File f = (File) file.invoke(obj);
                            total += f.getTotalSpace();
                        }
                    }
                }
            }
            return getUnit(total, unit);
        } catch (Exception ignore) {

        }
        return null;
    }

    private static String[] units = {"B", "KB", "MB", "GB", "TB"};

    /**
     * 进制转换
     */
    public static String getUnit(float size, float base) {
        int index = 0;
        while (size > base && index < 4) {
            size = size / base;
            index++;
        }
        return String.format(Locale.getDefault(), "%.2f %s ", size, units[index]);
    }

    /**
     * API 26 android O
     * 获取总共容量大小，包括系统大小
     */
    @RequiresApi(Build.VERSION_CODES.O)
    public static long getTotalSize(Context context, String fsUuid) {
        try {
            UUID id;
            if (fsUuid == null) {
                id = StorageManager.UUID_DEFAULT;
            } else {
                id = UUID.fromString(fsUuid);
            }
            StorageStatsManager stats = context.getSystemService(StorageStatsManager.class);
            return stats.getTotalBytes(id);
        } catch (NoSuchFieldError | NoClassDefFoundError | NullPointerException | IOException e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * 获取android当前可用内存大小
     *
     * @param context
     * @return
     */
    public static String getAvailMemory(Context context) {

        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        if (am != null) {
            am.getMemoryInfo(mi);
        }
        //mi.availMem; 当前系统的可用内存
        return Formatter.formatFileSize(context, mi.availMem);
    }


    /**
     * total
     * 使用的内存量
     *
     * @param context
     * @return
     */
    public static String getTotalMemory(Context context) {
        String str1 = "/proc/meminfo";
        String str2;
        String[] arrayOfString;
        long initial_memory = 0;
        try {
            FileReader localFileReader;

            localFileReader = new FileReader(str1);

            BufferedReader localBufferedReader = new BufferedReader(localFileReader, 8192);
            str2 = localBufferedReader.readLine();

            arrayOfString = str2.split("\\s+");
            //noinspection StatementWithEmptyBody
            for (String num : arrayOfString) {
            }

            initial_memory = Long.valueOf(arrayOfString[1]) * 1024;
            localBufferedReader.close();

        } catch (Exception e) {
        }
        return Formatter.formatFileSize(context, initial_memory);
    }

    //电池的充电方式
    public static String batteryPlugged(int status) {
        String healthBat = "uKnown";
        switch (status) {
            case BatteryManager.BATTERY_PLUGGED_AC:
                healthBat = "ac";
                break;
            case BatteryManager.BATTERY_PLUGGED_USB:
                healthBat = "usb";
                break;
            case BatteryManager.BATTERY_PLUGGED_WIRELESS:
                healthBat = "wireless";//无线的
                break;
            default:
                break;
        }
        return healthBat;
    }

    /**
     * 获取WifiInfo
     *
     * @param mContext
     * @return
     */
    @SuppressLint("MissingPermission")
    private static WifiInfo getWifiInfo(Context mContext) {
        WifiManager mWifiManager = (WifiManager) mContext.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (mWifiManager != null) {
            return mWifiManager.getConnectionInfo();
        }
        return null;
    }

    public static String getNetWorkId(Context context) {
        WifiInfo mWifiInfo = getWifiInfo(context);
        return mWifiInfo.getNetworkId() + "";
    }


    public static String getWifiOut(Context context) {
        WifiInfo mWifiInfo = getWifiInfo(context);
        return mWifiInfo.getBSSID();
    }

    public static String batteryHealth(int health) {
        String healthBat = "uKnown";
        switch (health) {
            case BatteryManager.BATTERY_HEALTH_COLD:
                healthBat = "cold";
                break;
            case BatteryManager.BATTERY_HEALTH_DEAD:
                healthBat = "dead";
                break;
            case BatteryManager.BATTERY_HEALTH_GOOD:
                healthBat = "good";
                break;
            case BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE:
                healthBat = "overVoltage";
                break;
            case BatteryManager.BATTERY_HEALTH_OVERHEAT:
                healthBat = "overheat";
                break;
            case BatteryManager.BATTERY_HEALTH_UNKNOWN:
                healthBat = "unknown";
                break;
            case BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE:
                healthBat = "unspecified";
                break;
            default:
                break;
        }
        return healthBat;
    }

    //获取局域网ip
    public static String getIPAddress(Context context) {
        NetworkInfo info = ((ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
            if (info.getType() == ConnectivityManager.TYPE_MOBILE) {//当前使用2G/3G/4G网络
                try {
                    //Enumeration<NetworkInterface> en=NetworkInterface.getNetworkInterfaces();
                    for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                        NetworkInterface intf = en.nextElement();
                        for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                            InetAddress inetAddress = enumIpAddr.nextElement();
                            if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                                return inetAddress.getHostAddress();
                            }
                        }
                    }
                } catch (SocketException e) {
                    e.printStackTrace();
                }

            } else if (info.getType() == ConnectivityManager.TYPE_WIFI) {//当前使用无线网络
                WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                String ipAddress = intIP2StringIP(wifiInfo.getIpAddress());//得到IPV4地址
                return ipAddress;
            }
        } else {
            //当前无网络连接,请在设置中打开网络
        }
        return null;
    }

    /**
     * 将得到的int类型的IP转换为String类型
     *
     * @param ip
     * @return
     */
    public static String intIP2StringIP(int ip) {
        return (ip & 0xFF) + "." +
                ((ip >> 8) & 0xFF) + "." +
                ((ip >> 16) & 0xFF) + "." +
                (ip >> 24 & 0xFF);
    }


    public List<Param> getOutIp() {

        HttpModelHelper.getInstance()
                .init(getContext())
                .setChina(false)
                .setModelLoader(new OkHttpUrlLoader())
                .setFactory()
                .addType(HttpType.NET)
                .build()
                .startAsync("https://www.baidu.com", new HttpListener() {
                    @Override
                    public void onFail(String data) {

                    }

                    @Override
                    public void onFinish(JSONObject result) {

                    }

                    @Override
                    public void onSuccess(HttpType httpType, JSONObject result) {


                         Log.e("111",result.toString());
                    }
                });

        return null;

    }




}


