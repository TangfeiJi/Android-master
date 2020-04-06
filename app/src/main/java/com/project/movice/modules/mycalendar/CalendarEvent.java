package com.project.movice.modules.mycalendar;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Attendees;
import android.provider.CalendarContract.Calendars;
import android.provider.CalendarContract.Events;
import android.provider.CalendarContract.Reminders;
import android.text.TextUtils;
import android.util.Log;

import com.project.movice.application.MoviceApp;
import com.project.movice.utils.PhoneUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

/**
 * @Author: SNXJ
 * @Date : 2018/5/5 0005.
 * @Describe :日历添加删除提醒
 **/
@SuppressLint("MissingPermission")
public class CalendarEvent {
    private static final String ACCOUNT_NAME = PhoneUtils.getAppName() + " Ingatkan / Berita";//自定义

    /**
     * 使用以下Uri时，Android版本>=14; 注意引用包路径：android.provider.CalendarContract下的；
     **/
    private static Uri calendarsUri = Calendars.CONTENT_URI;
    private static Uri eventsUri = Events.CONTENT_URI;
    private static Uri remindersUri = Reminders.CONTENT_URI;
    private static Uri attendeesUri = Attendees.CONTENT_URI;

    /**
     * Events table columns
     */
    public static final String[] EVENTS_COLUMNS = new String[]{Events._ID,
            Events.CALENDAR_ID, Events.TITLE, Events.DESCRIPTION,
            Events.EVENT_LOCATION, Events.DTSTART, Events.DTEND,
            Events.EVENT_TIMEZONE, Events.HAS_ALARM, Events.ALL_DAY,
            Events.AVAILABILITY, Events.ACCESS_LEVEL, Events.STATUS,};

    /**
     * Reminders table columns
     */
    public static final String[] REMINDERS_COLUMNS = new String[]{
            Reminders._ID, Reminders.EVENT_ID, Reminders.MINUTES,
            Reminders.METHOD,};
    /**
     * Attendees table columns
     */
    public static final String[] ATTENDEES_COLUMNS = new String[]{
            Attendees._ID, Attendees.ATTENDEE_NAME, Attendees.ATTENDEE_EMAIL,
            Attendees.ATTENDEE_STATUS};

    /**
     * 插入事件
     */
    @SuppressLint("MissingPermission")
    public static void insertEvent(EventModel model) {
        String calId = queryCalId();
        if (TextUtils.isEmpty(calId)) {
            addAccount();
            insertEvent(model);
            return;
        }


        // 插入事件
        ContentValues event = new ContentValues();
        event.put(Events.TITLE, ACCOUNT_NAME);//标题
        event.put(Events.DESCRIPTION, model.getContent());//备注
        event.put(Events.EVENT_LOCATION, "");//地点用需要可以添加)
        event.put(Events.CALENDAR_ID, calId);
        event.put(Events._ID, model.getId());//事件ID
        event.put(Events.DTSTART, model.getTime());//开始时间
        Log.i("+++++++++++++++++++++", "_++++++++++++ model.getTime()++++++++++++++++++" + model.getTime());
//        event.put(Events.DTEND, model.getTime()+10*1000);//结束时间（+10*1000）对于非重复发生的事件，必须包含DTEND字段； 与DURATION不同时存在
        event.put(Events.STATUS, Events.STATUS_CONFIRMED);
        event.put(Events.HAS_ATTENDEE_DATA, 1);
        event.put(Events.HAS_ALARM, 1);//是否生效?
//
//        对重复发生的事件，必须包含一个附加了RRULE或RDATE字段的DURATIION字段。注意，如果通过INSERT类型的Intent对象来插入一个事件，这个规则不适用。因为在这个Intent对象的应用场景中，你能够把RRULE、DTSTART和DTEND字段联合在一起使用，并且Calendar应用程序能够自动的把它转换成一个持续的时间。

//        event.put(Events.RRULE, "FREQ=WEEKLY;COUNT=5;WKST=SU");//格式化的事件复发规则（RFC5545）。如“FREQ=WEEKLY;COUNT=10;WKST=SU”。//"FREQ=WEEKLY;INTERVAL=2" 两周
//        event.put(Events.RDATE, 1);//事件的复发日期。通常RDATE要联合RRULE一起使用来定义一个重复发生的事件的合集。
        event.put(Events.DURATION, "PT1H");//持续一小时 "P2W"两周
        event.put(Events.GUESTS_CAN_MODIFY, false);//参与者是否能够修改事件

        event.put(Events.EVENT_TIMEZONE, TimeZone.getDefault().getID());//时区，必须有
        Uri newEvent = MoviceApp.getContext().getContentResolver().insert(eventsUri, event);

        // 事件提醒的设定
        long id = Long.parseLong(newEvent != null ? newEvent.getLastPathSegment() : "1");
        ContentValues values = new ContentValues();
        values.put(Reminders.EVENT_ID, id);
        values.put(Reminders.MINUTES, "10");//提前提醒时间 min
        values.put(Reminders.METHOD, Reminders.METHOD_ALERT);//提醒方式method
        MoviceApp.getContext().getContentResolver().insert(remindersUri, values);
    }

    /**
     * 根据账户查询账户日历
     *
     * @return List
     */
    @SuppressLint("MissingPermission")
    public static List<EventModel> queryEvents() {
        List<EventModel> calendars = new ArrayList<>();
        Cursor cursor;
        // 本地帐户查询：ACCOUNT_TYPE_LOCAL是一个特殊的日历账号类型，它不跟设备账号关联。这种类型的日历不同步到服务器
        // 如果是谷歌的账户是可以同步到服务器的
        cursor = MoviceApp.getContext().getContentResolver().query(eventsUri, EVENTS_COLUMNS,
                Calendars.ACCOUNT_NAME + " = ? ", new String[]{ACCOUNT_NAME}, null);
        while (cursor != null && cursor.moveToNext()) {
            EventModel eventModel = new EventModel();
            eventModel.setId(cursor.getString(0));
            eventModel.setTime(cursor.getString(5));
            eventModel.setContent(cursor.getString(3));
            calendars.add(eventModel);
        }
        return calendars;
    }

    /**
     * 更新某条Event
     *
     * @param model model
     */
    @SuppressLint("MissingPermission")
    public static void updateEvent(EventModel model) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Events.DTSTART, model.getTime());
        contentValues.put(Events.DESCRIPTION, model.getContent());
        MoviceApp.getContext().getContentResolver().update(eventsUri, contentValues
                , Events._ID + " =? ", new String[]{model.getId()});
    }

    /**
     * 删除某条Event
     *
     * @param id id
     * @return The number of rows deleted.
     */
    public static int deleteEvent(String id) {
        return MoviceApp.getContext().getContentResolver()
                .delete(eventsUri, Events._ID + " =? ", new String[]{id});
    }

    /**
     * 删除所有Event
     *
     * @return The number of rows deleted.
     */

    public static int deleteAllEvent() {
        return MoviceApp.getContext().getContentResolver()
                .delete(eventsUri, Events.CALENDAR_ID + " =? ", new String[]{queryCalId()});
    }

    /**
     * 查询 calendar_id
     *
     * @return calId
     */
    @SuppressLint("MissingPermission")
    private static String queryCalId() {
        Cursor userCursor = null;
        try {
            userCursor = MoviceApp.getContext().getContentResolver().query(calendarsUri, null, "name=?", new String[]{ACCOUNT_NAME}, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (userCursor == null) {
            return null;
        }
        if (userCursor.getCount() > 0) {
            userCursor.moveToLast(); //是向符合条件的最后一个账户添加
            return userCursor.getString(userCursor.getColumnIndex("_id"));
        }
        return "";
    }

    /**
     * 添加账户
     */
    private static void addAccount() {
        ContentValues value = new ContentValues();
        value.put(Calendars.ACCOUNT_NAME, ACCOUNT_NAME);
        value.put(Calendars.ACCOUNT_TYPE, "LOCAL");
        value.put(Calendars._SYNC_ID, "1");
        value.put(Calendars.DIRTY, "1");
        value.put(Calendars.NAME, ACCOUNT_NAME);
        value.put(Calendars.CALENDAR_DISPLAY_NAME, ACCOUNT_NAME);
        value.put(Calendars.CALENDAR_COLOR, Color.BLUE);
        value.put(Calendars.CALENDAR_ACCESS_LEVEL, Calendars.CAL_ACCESS_OWNER);
        value.put(Calendars.CALENDAR_TIME_ZONE, TimeZone.getDefault().getID());
        value.put(Calendars.VISIBLE, "1");
        value.put(Calendars.SYNC_EVENTS, "1");
        value.put(Calendars.OWNER_ACCOUNT, ACCOUNT_NAME);
        value.put(Calendars.CAN_ORGANIZER_RESPOND, "1");
        Uri calendarUri = Calendars.CONTENT_URI;
        calendarUri = calendarUri.buildUpon()
                .appendQueryParameter(CalendarContract.CALLER_IS_SYNCADAPTER, "true")
                .appendQueryParameter(Calendars.ACCOUNT_NAME, ACCOUNT_NAME)
                .appendQueryParameter(Calendars.ACCOUNT_TYPE, "1").build();
        MoviceApp.getContext().getContentResolver().insert(calendarUri, value);
    }


}
