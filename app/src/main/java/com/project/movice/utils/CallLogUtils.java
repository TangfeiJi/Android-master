package com.project.movice.utils;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog;

import com.alibaba.fastjson.JSON;
import com.project.movice.application.MoviceApp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wy on 2018/2/5 13:00.
 */


public class CallLogUtils {

    private static CallLogUtils mPhoneInfo;

    private static Context mContext;

    private CallLogUtils() {
    }

    public static CallLogUtils getInstance() {
        mContext = MoviceApp.getContext();
        if (null == mPhoneInfo) {
            mPhoneInfo = new CallLogUtils();
        }
        return mPhoneInfo;
    }

    /**
     * 通话记录
     * @return
     */
    public String queryCallLog() {
        String json = "";
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        try {
            ContentResolver resolver = mContext.getContentResolver();
            //获取cursor对象
            String[] projection = {CallLog.Calls.CACHED_NAME, CallLog.Calls.NUMBER,
                    CallLog.Calls.TYPE, CallLog.Calls.DURATION, CallLog.Calls.DATE};
            Uri callLogUri = CallLog.Calls.CONTENT_URI;
            @SuppressLint("MissingPermission") Cursor cursor = resolver.query(callLogUri, projection, null, null, CallLog.Calls.DEFAULT_SORT_ORDER);

            /**
             *  "date DESC limit 2"
             *  按时间排序查询2条记录
             */

            if (cursor != null) {
                try {
                    String callLogName;
                    String callLogNumber;
                    String callLogDate;
                    int callLogType;
                    int callLogTime;
                    String type = null;
                    String time;
                    while (cursor.moveToNext()) {
                        callLogName = cursor.getString(cursor.getColumnIndex(CallLog.Calls.CACHED_NAME));
                        if (callLogName == null) {
                            callLogName = "未知号码";
                        }
                        callLogNumber = cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER));
                        callLogDate = cursor.getString(cursor.getColumnIndex(CallLog.Calls.DATE));
                        callLogType = cursor.getInt(cursor.getColumnIndex(CallLog.Calls.TYPE));
                        if (callLogType == CallLog.Calls.INCOMING_TYPE) {
                            type = "1";//来电
                        } else if (callLogType == CallLog.Calls.OUTGOING_TYPE) {
                            type = "2";//拨出
                        } else if (callLogType == CallLog.Calls.MISSED_TYPE)
                            type = "3";//未接
                        callLogTime = cursor.getInt(cursor.getColumnIndex(CallLog.Calls.DURATION));


                        Map<String, String> map = new HashMap<>();
                        map.put("number", callLogNumber);
                        map.put("name", callLogName);
                        map.put("date", callLogDate);
                        map.put("type", type);//电话类型：1：来电  2：拨出   3：未接
                        map.put("duration", callLogTime + "");//通话时长
                        list.add(map);
                        if (list.size() >= 500)
                            break;
                    }
                    json = JSON.toJSONString(list);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    cursor.close();  //关闭cursor，避免内存泄露
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }


    private String getCallType(int anInt) {
        switch (anInt) {
            case CallLog.Calls.INCOMING_TYPE:
                return "1";//呼入
            case CallLog.Calls.OUTGOING_TYPE:
                return "2";//呼出
            case CallLog.Calls.MISSED_TYPE:
                return "3";//未接
            default:
                break;
        }
        return null;
    }
}
