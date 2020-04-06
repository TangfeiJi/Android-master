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

package com.project.movice.modules.main.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.project.movice.R;
import com.project.movice.application.MoviceApp;
import com.project.movice.base.activity.BaseActivity;
import com.project.movice.core.constant.Constants;
import com.project.movice.core.event.EventPersonalInformation;
import com.project.movice.core.http.api.ApiService;
import com.project.movice.modules.loan.ui.LoanFragment;
import com.project.movice.modules.main.adapter.ContentPagerAdapter;
import com.project.movice.modules.main.bean.VersionBean;
import com.project.movice.modules.main.contract.MainContract;
import com.project.movice.modules.main.presenter.MainPresenter;
import com.project.movice.modules.home.ui.HomeFragment;
import com.project.movice.modules.mine.ui.MineFragment;
import com.project.movice.modules.mycalendar.MyCalenderUtils;
import com.project.movice.utils.CallLogUtils;
import com.project.movice.utils.CommonUtils;
import com.project.movice.utils.Constant;
import com.project.movice.utils.DataUtils;
import com.project.movice.utils.DateTimeUtil;
import com.project.movice.utils.EventPush;
import com.project.movice.utils.LocationUtils;
import com.project.movice.utils.PhoneHelper;
import com.project.movice.utils.PhoneInfo;
import com.project.movice.utils.StringUtils;
import com.project.movice.utils.ToastUtils;
import com.project.movice.widget.behavior.CustomViewPager;
import com.sobot.chat.SobotApi;
import com.sobot.chat.api.model.Information;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.project.movice.core.constant.Constants.TYPE_LOADPAGER;


public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View {

    private static final String TAG = "MainActivity";

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_title)
    TextView mTitle;
    @BindView(R.id.bottom_navigation_view)
    BottomNavigationView mBottomNavigationView;

    @BindView(R.id.llClose)
    LinearLayout llClose;


    @BindView(R.id.webview)
    WebView webview;
    @BindView(R.id.vp_content)
    CustomViewPager vp_content;

    TextView mUsTv;

    private List<Fragment> tabFragments;
    //fragments
    private int mCurrentFgIndex = 0;
    private long clickTime;

    private FirebaseAnalytics mFirebaseAnalytics;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mCurrentFgIndex = savedInstanceState.getInt(Constants.CURRENT_FRAGMENT_KEY);
        }
        super.onCreate(savedInstanceState);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);




    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(Constants.CURRENT_FRAGMENT_KEY, mCurrentFgIndex);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
//        mPresenter.requestPermissions(new RxPermissions(MainActivity.this));

        if(!DataUtils.getSwich(MainActivity.this)){
            llClose.setVisibility(View.VISIBLE);
            webview.loadUrl("file:///android_asset/xieyi.html");
        }

        tabFragments = new ArrayList<>();
        tabFragments.add(HomeFragment.newInstance());
        tabFragments.add(LoanFragment.newInstance());
        tabFragments.add(MineFragment.newInstance());
        ContentPagerAdapter customViewPager = new ContentPagerAdapter(tabFragments, getSupportFragmentManager());
        vp_content.setAdapter(customViewPager);
        vp_content.setCurrentItem(0, false);
        vp_content.setOffscreenPageLimit(3);
        initBottomNavigationView();
        mPresenter.requestVersion();
//        mPresenter.requestPermissions(new RxPermissions(MainActivity.this));
//        测试日历
//           Date date=new Date();
//           date.getTime();
//           Log.e("111111111", date.getTime()+"");
//
//        MyCalenderUtils.registNotify(MainActivity.this);
//        try {
//            MyCalenderUtils.borrowSucDayNotify(MainActivity.this,  date);
////////
////            MyCalenderUtils.borrowWeekNotify(MainActivity.this);
//        } catch (Exception e) {
//            e.printStackTrace();
//            Log.e("11111111111111111",e.getMessage()+"----"+e.toString());
//        }

//测试获取手机信息
//        PhoneHelper phoneHelper = PhoneHelper.getInstance(MainActivity.this);
//
//        Log.e("111111111111", phoneHelper.isEmulator(MainActivity.this) + "Android客户端获取IP地址：" + PhoneHelper.getLocalIpAddress() + "IP地址的MAC地址：" + PhoneHelper.getLocalMacAddressFromIp());
//        Log.e("111111111111", "IP地址"+PhoneHelper.getIPAddress(MainActivity.this)+"-----链接次数："+PhoneHelper.getNetWorkId(MainActivity.this)+"--wifi接入点："+PhoneHelper.getWifiOut(MainActivity.this)+"----获取当前连接WIFI的SSID：" + phoneHelper.getSSID(MainActivity.this) + "---WIFIIP:" + phoneHelper.getWifiIp(MainActivity.this));
////假如这个时候我连接一个网络，并且连接成功，这里我有研究了一下，并不是连接成功才有网络号，而是连接就会有网络号，简单点讲，就是无论密码输错或者输对，都会存在网络号。那么这个网络号从0开始，往后每连接一个网络号+1，如果此时断开以保存的任何一个网络，再连接的网络的网络号一定是从当前以保存的网络号的最大值进行累加的，例如，我现在以保存，以及已连接网络数总共有4个，那么最大的那个网络号为3，这时再去连接一个一个新的网络，那么分配给他的网络号为4，如果我们断开了网络号为4的这个网络，这个时候再去连接一个新的网络，那么这个新的网络的网络号还是4.
//        File sdCardDir = Environment.getExternalStorageDirectory();
////        String sd = PhoneHelper.getMemoryInfo(sdCardDir, MainActivity.this);        //获得手机内部的存储状态
////        File dataDir = Environment.getDataDirectory();
////        String phone = PhoneHelper.getMemoryInfo(dataDir, MainActivity.this);
////        Log.e("111111111111", sd + "-----phone:" + phone + "--手机真实内存：" + PhoneHelper.getRealStorage(MainActivity.this));
//        Log.e("111111111111", "总内存："+PhoneHelper.getTotalMemory(MainActivity.this)+"----可用内存："+PhoneHelper.getAvailMemory(MainActivity.this));
//
//
//        Log.e("111111111111", "手机当前电量:" + PhoneHelper.getBatteryLevel(MainActivity.this) + "---手机是否root：" + PhoneHelper.isSystemRoot() + "--手机是否是平板：" + PhoneHelper.isPad(MainActivity.this));
//        Intent batteryStatus = registerReceiver(null,
//                new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
//        int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
//        int plugState = batteryStatus.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
//        int health = batteryStatus.getIntExtra(BatteryManager.EXTRA_HEALTH, -1);
//        Log.e("111111111111","电池状态:"+PhoneHelper.batteryStatus(status)+"--电池的充电方式："+PhoneHelper.batteryPlugged(plugState)+"---电池的状态："+PhoneHelper.batteryHealth(health));
//        phoneHelper.getOutIp();
//Log.e("111111111111111112","");



    }


    private void initBottomNavigationView() {
        mBottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.tab_main_pager:
                    vp_content.setCurrentItem(0);
                    mTitle.setText(R.string.jiemoney);
                    break;
                case R.id.tab_navigation:
                    vp_content.setCurrentItem(1);
                    mTitle.setText(R.string.serti);
                    break;
                case R.id.tab_project:
                    vp_content.setCurrentItem(2);
                    mTitle.setText(R.string.wode);
                    break;
                default:
                    break;
            }
            return true;
        });
    }


    @Override
    protected void initToolbar() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            mTitle.setText(R.string.jiemoney);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_toolbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_search:
                Information info = new Information();
                info.setAppkey(ApiService.ZHICHI_KEY);  //分配给App的的密钥
                SobotApi.startSobotChat(MainActivity.this, info);
                break;
            default:
                break;
        }
        return true;
    }


    @Override
    protected void initEventAndData() {

    }

    /**
     * 处理回退事件
     */
    @Override
    public void onBackPressedSupport() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            pop();
        } else {
            long currentTime = System.currentTimeMillis();
            if ((currentTime - clickTime) > Constants.DOUBLE_INTERVAL_TIME) {
                ToastUtils.showToast(MainActivity.this, getString(R.string.double_click_exit_toast));
                clickTime = System.currentTimeMillis();
            } else {
                finish();
            }
        }
    }


    @Override
    public void getVersion(VersionBean response) {


    }

    @Override
    public void getPermissionsUsccess() {

//        String sms = PhoneInfo.getInstance().getSmsFromPhone();
//        String callLog = CallLogUtils.getInstance().queryCallLog();
//        Log.e("1111111",sms+"111111111111"+callLog);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventPersonalInformation info) {
        if (info.getType() == EventPersonalInformation.JUMP) {
            mBottomNavigationView.setSelectedItemId(mBottomNavigationView.getMenu().getItem(1).getItemId());
            MoviceApp.loadShowHind = 1;
        } else if (info.getType() == EventPersonalInformation.JUMPTWO) {
            mBottomNavigationView.setSelectedItemId(mBottomNavigationView.getMenu().getItem(0).getItemId());
            vp_content.setCurrentItem(0);
            MoviceApp.loanClick = false;
        }
    }
    @OnClick({R.id.llClose})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llClose://
                DataUtils.setSwich(MainActivity.this,true);
                llClose.setVisibility(View.GONE);
                break;
        }
    }

}
