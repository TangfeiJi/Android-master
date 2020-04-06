package com.project.movice.modules.loan.ui;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;


import com.project.movice.utils.CommonUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * Created by wy on 2018/1/5 11:24.
 */


public abstract class BaseActivity extends SupportActivity implements View.OnClickListener {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        initBundle(getIntent().getExtras(), savedInstanceState);
        initView();
        initData();
//        StatusBarUtil.StatusBarLightMode(this, StatusBarUtil.StatusBarLightMode(this));
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(String str) {
        switch (str) {
            case "EVENT_REFRESH_LANGUAGE":
                recreate();//刷新界面
                break;
        }
    }
    @Override
    public void onBackPressedSupport() {
        CommonUtils.hideKeyBoard(this, this.getWindow().getDecorView().getRootView());
        super.onBackPressedSupport();
    }
    /**
     * [绑定视图]
     *
     * @return
     */
    protected abstract int getContentView();

    /**
     * [初始化控件]
     */
    public abstract void initView();

    /**
     * [初始化数据]
     */
    public abstract void initData();

    public abstract void initBundle(Bundle bundle, Bundle savedInstanceState);

    private long lastClick;

    @Override
    public void onClick(View v) {
    }

    /**
     * 解决点击事件双击
     *
     * @return
     */
    public boolean canClick() {
        if (System.currentTimeMillis() - lastClick <= 1000 && (System.currentTimeMillis() - lastClick) > 0) {
            return false;
        }
        lastClick = System.currentTimeMillis();
        return true;
    }


    public static void showActivity(Context context, Class<?> clazz) {
        showActivity(context, clazz, null);
    }


    public static void showActivity(Context context, Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(context, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
    }

    public static void showActivityForResult(Context context, Class<?> clazz, int requestCode) {
        showActivityForResult(context, clazz, null, requestCode);
    }

    public static void showActivityForResult(Context context, Class<?> clazz, Bundle bundle, int requestCode) {
        Intent intent = new Intent(context, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        Activity a = (Activity) context;
        a.startActivityForResult(intent, requestCode);
    }

    public void LoginOut() {
//        LoginOutDialog loginout = new LoginOutDialog(this);
//        loginout.setOnClickListener(new LoginOutDialog.OnClickListener() {
//            @Override
//            public void onClick() {
//                showLogin();
//                exit();
//            }
//        });
//        loginout.show();
    }








    /**
     * @方法描述 键盘监听，关闭当前activity
     * @创建者 wy
     * @创建时间 2013-10-24 下午5:35:18
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 按下键盘上返回按钮
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
        }
        return false;
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);


    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
    }


    /**
     * @方法描述 关闭当前Activity
     * @创建者 wy
     * @创建时间 2013-10-23 下午5:36:07
     */
    public void exit() {
        finish();
    }
}




