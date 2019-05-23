/*
 *     (C) Copyright 2019, ForgetSky.
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 */

package com.project.movice.core.constant;


import android.graphics.Color;


public class Constants {

    public static final String MY_SHARED_PREFERENCE = "my_shared_preference";

    /**
     * url
     */
    public static final String COOKIE = "Cookie";

    /**
     * Tag fragment classify
     */
    public static final int TYPE_NAVIGATION = 2;
    public static final int TYPE_PROJECT = 4;
    public static final int TYPE_JOKEPAGER = 0;



    public static final String CURRENT_FRAGMENT_KEY = "current_fragment";



    /**
     * Tab colors
     */
    public static final int[] TAB_COLORS = new int[]{
            Color.parseColor("#90C5F0"),
            Color.parseColor("#91CED5"),
            Color.parseColor("#F88F55"),
            Color.parseColor("#C0AFD0"),
            Color.parseColor("#E78F8F"),
            Color.parseColor("#67CCB7"),
            Color.parseColor("#F6BC7E")
    };


    public static final String MENU_BUILDER = "MenuBuilder";

    /**
     * Refresh theme color
     */
//    public static final int BLUE_THEME = R.color.colorPrimary;

    /**
     * Avoid double click time area
     */

    public static final long DOUBLE_INTERVAL_TIME = 2000;


    public static final String ARTICLE_LINK = "article_link";

    public static final String ARTICLE_TITLE = "article_title";

    public static final String ARTICLE_ID = "article_id";

    public static final String IS_COLLECTED = "is_collected";

    public static final String IS_SHOW_COLLECT_ICON = "is_show_collect_icon";

    public static final String ARTICLE_ITEM_POSITION = "article_item_position";

    public static final String DB_NAME = "forgetsky_wan_android.db";

    /**
     * Shared Preference key
     */
    public static final String ACCOUNT = "account";


    public static final String LOGIN_STATUS = "login_status";



    /**
     * EventBus Tag
     */
    public static final String EVENT_BUS_TAG = "event_bus_tag";




}
