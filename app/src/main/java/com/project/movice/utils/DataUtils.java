package com.project.movice.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.content.SharedPreferencesCompat;

import com.facebook.accountkit.AccountKit;
import com.project.movice.modules.mycalendar.MyCalenderUtils;

/**
 * Created by Administrator on 2016/8/10.
 */
public class DataUtils {


    /**
     * 保存在手机里面的文件名
     */
    public static final String FILE_NAME = "MiniRupiah";


    //============================================================================
    //===============================Set==========================================
    //============================================================================


    public static void setSwich(Context context, boolean arg) {
        SharedPreferencesUtil.getInstance(context).putBoolean("swich", arg);
    }



    public static void setToken(Context context, String arg) {
        SharedPreferencesUtil.getInstance(context).putString("token", arg);
    }

    public static void setPhone(Context context, String arg) {
        SharedPreferencesUtil.getInstance(context).putString("phone", arg);
    }


    public static void setUserName(Context context, String arg) {
        SharedPreferencesUtil.getInstance(context).putString("setUserName", arg);
    }



    public static void setLoginType(Context context, String arg) {
        SharedPreferencesUtil.getInstance(context).putString("loginType", arg);
    }




    public static void logout(Context context){
        setPhone(context,"");
        setToken(context,"");
        AccountKit.logOut();
    }







    //============================================================================
    //===============================Get==========================================
    //============================================================================
    public static Boolean getSwich(Context context) {
        return SharedPreferencesUtil.getInstance(context).getBoolean("swich");
    }


    public static String getLoginType(Context context) {
        return SharedPreferencesUtil.getInstance(context).getString("loginType");
    }
    public static String getUserName(Context context) {
        return SharedPreferencesUtil.getInstance(context).getString("setUserName");
    }



    public static String getPhone(Context context) {
        return SharedPreferencesUtil.getInstance(context).getString("phone");
    }



    public static String getToken(Context context) {
        return SharedPreferencesUtil.getInstance(context).getString("token");
    }





    /**
     * 保存数据
     * @param context
     * @param key
     * @param value
     */
    public static void put(Context context, String key, Object value) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        if (value instanceof String) {
            editor.putString(key, (String) value);
        } else if (value instanceof Integer) {
            editor.putInt(key, (Integer) value);
        } else if (value instanceof Boolean) {
            editor.putBoolean(key, (Boolean) value);
        } else if (value instanceof Float) {
            editor.putFloat(key, (Float) value);
        } else if (value instanceof Long) {
            editor.putLong(key, (Long) value);
        } else {
            editor.putString(key, value.toString());
        }
        SharedPreferencesCompat.EditorCompat.getInstance().apply(editor);
    }

    /**
     * 获取数据
     * @param context
     * @param key
     * @param defValue
     * @return
     */
    public static Object get(Context context, String key, Object defValue) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        if (defValue instanceof String) {
            return sp.getString(key, (String) defValue);
        } else if (defValue instanceof Integer) {
            return sp.getInt(key, (Integer) defValue);
        } else if (defValue instanceof Boolean) {
            return sp.getBoolean(key, (Boolean) defValue);
        } else if (defValue instanceof Float) {
            return sp.getFloat(key, (Float) defValue);
        } else if (defValue instanceof Long) {
            return sp.getLong(key, (Long) defValue);
        }
        return null;
    }



}
