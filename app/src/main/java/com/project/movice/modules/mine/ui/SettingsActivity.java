package com.project.movice.modules.mine.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.project.movice.R;
import com.project.movice.core.event.EventPersonalInformation;
import com.project.movice.dialog.ConfirmPromptDialog;
import com.project.movice.modules.loan.ui.BaseActivity;
import com.project.movice.modules.main.ui.activity.MainActivity;
import com.project.movice.utils.DataUtils;
import com.project.movice.utils.EventBusType;
import com.project.movice.utils.EventFinancing;
import com.project.movice.utils.EventOutLogin;
import com.project.movice.utils.MessageEvent;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 设置
 * Created by wy on 2018/1/25 13:55.
 */


public class SettingsActivity extends BaseActivity {

    @BindView(R.id.out_login)
    LinearLayout mOutLogin;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_title)
    TextView mTitle;
    @Override
    protected int getContentView() {
        return R.layout.settings;
    }

    @Override
    public void initView() {


        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        mTitle.setText(R.string.settings);
        mToolbar.setNavigationOnClickListener(v -> onBackPressedSupport());


        if (!TextUtils.isEmpty(DataUtils.getPhone(SettingsActivity.this))) {
            mOutLogin.setVisibility(View.VISIBLE);
        }else{
            mOutLogin.setVisibility(View.GONE);
        }
    }

    @Override
    public void initData() {

    }

    @Override
    public void initBundle(Bundle bundle, Bundle savedInstanceState) {

    }

    @OnClick({R.id.out_login})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.left_image:
            case R.id.left_text:
                exit();
                break;
            case R.id.out_login:
                ConfirmPromptDialog confirm = new ConfirmPromptDialog(this,
                        R.mipmap.uangpro110,
                        getResources().getString(R.string.make_sure_to_log_out),
                        getResources().getString(R.string.cancel),
                        getResources().getString(R.string.confirm));
                confirm.setOnClickListener(new ConfirmPromptDialog.OnClickListener() {
                    @Override
                    public void onClick() {
                        DataUtils.logout(SettingsActivity.this);
                        EventBus.getDefault().post(new EventOutLogin(EventOutLogin.OUTLOGIN));
                        EventBus.getDefault().post(new MessageEvent(EventBusType.UPDATELOAN));
                        EventBus.getDefault().post(new EventFinancing(EventFinancing.REFRESH));
                        EventBus.getDefault().post(new EventPersonalInformation(EventPersonalInformation.OTHER, true));
                        showActivity(SettingsActivity.this, MainActivity.class);
                        exit();
                        overridePendingTransition(R.anim.to_static, R.anim.out_to_buttom);
                    }
                });
                confirm.show();
                break;
        }
    }


}
