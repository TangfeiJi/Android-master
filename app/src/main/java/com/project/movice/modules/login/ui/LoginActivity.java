package com.project.movice.modules.login.ui;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.facebook.FacebookActivity;
import com.project.movice.BuildConfig;
import com.project.movice.R;
import com.project.movice.application.MoviceApp;
import com.project.movice.base.activity.BaseActivity;
import com.project.movice.core.event.EventPersonalInformation;
import com.project.movice.modules.login.bean.BeanSms;
import com.project.movice.modules.login.contract.LoginContract;
import com.project.movice.modules.login.presenter.LoginPresenter;
import com.project.movice.modules.main.bean.BeanUser;
import com.project.movice.utils.Constant;
import com.project.movice.utils.CountDownInterface;
import com.project.movice.utils.CountDownThread;
import com.project.movice.utils.DataUtils;
import com.project.movice.utils.EventBusType;
import com.project.movice.utils.EventFinancing;
import com.project.movice.utils.EventPush;
import com.project.movice.utils.MessageEvent;
import com.project.movice.utils.StringUtils;
import com.project.movice.widget.behavior.VerificationCodeView;
import com.sobot.chat.utils.ToastUtil;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

import static com.project.movice.modules.loan.ui.BaseActivity.showActivityForResult;
import static com.project.movice.utils.LoginUtil.showActivity;

/**
 * Created by wy on 2018/1/8 13:23.
 */


public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.View, CountDownInterface {
    public static int APP_REQUEST_CODE = 99;
    private static final int RC_CONTACTS = 0x05;
    @BindView(R.id.icv)
    VerificationCodeView mIcv;
    @BindView(R.id.content)
    LinearLayout mContent;
    @BindView(R.id.bt_verification_code)
    Button mBtVerificationCode;
    @BindView(R.id.login)
    Button mLoginl;
    @BindView(R.id.mobile)
    EditText mMobile;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_title)
    TextView mTitle;

    //    private LoginPresenter loginPresenter;
    private String vid;
    public static long validTime = Constant.STANDARD_TIME;
    private CountDownThread countDown;

    @Override
    protected int getLayoutId() {
        return R.layout.login;
    }

    @Override
    protected void initToolbar() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        mTitle.setText(R.string.login);
        mToolbar.setNavigationOnClickListener(v -> onBackPressedSupport());
    }


    @Override
    protected void initEventAndData() {

    }

    @Override
    protected void initView() {
        initData();
        if (validTime != Constant.STANDARD_TIME) {
            countDown = CountDownThread.newInstance();
            countDown.setOnCountDownListening(this);
            countDown.startTimer("1111111111",
                    Constant.STANDARD_TIME, 1000);
        }
    }

    public void initData() {
        mIcv.setInputCompleteListener(new VerificationCodeView.InputCompleteListener() {
            @Override
            public void inputComplete() {
                mIcv.getInputContent();
            }

            @Override
            public void deleteContent() {
                mIcv.getInputContent();
                //清除所有验证方法
                //icv.clearInputContent();
            }
        });
    }


    @OnClick({R.id.facebook, R.id.bt_verification_code, R.id.login})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_verification_code:
                if (canClick()) {
                    String mobile = mMobile.getText().toString();
                    if (TextUtils.isEmpty(mobile.trim())) {
                        return;
                    }
                    mPresenter.request001(checkSMS(mMobile.getText().toString()));
                }
                break;
            case R.id.login:
                String mobile = mMobile.getText().toString();
                String verificationCode = mIcv.getInputContent();
                if (StringUtils.isEmpty(mobile)) {
                    ToastUtil.showToast(LoginActivity.this, getResources().getString(R.string.input_phone));
                    return;
                }
                if (StringUtils.isEmpty(verificationCode)) {
                    ToastUtil.showToast(LoginActivity.this, getResources().getString(R.string.input_verification_code));
                    return;
                }
                if (canClick()) {
//                    tipss();
                    mPresenter.request002(check(mMobile.getText().toString()));
                }
                break;
            case R.id.facebook:
                showActivity(LoginActivity.this, FaceBookLoginActivity.class);
                finish();
                break;
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            overridePendingTransition(R.anim.to_static, R.anim.out_to_buttom);
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == APP_REQUEST_CODE) { // confirm that this response matches your request
            if (resultCode == RESULT_OK) {
                finish();
                overridePendingTransition(R.anim.to_static, R.anim.out_to_buttom);
            }
        }
    }


    public void showVerificationCode(int status, String str) {
        mBtVerificationCode.setText(str);
        if (status == 1) {
            mBtVerificationCode.setEnabled(false);
        } else {
            mBtVerificationCode.setEnabled(true);
        }
    }



    @Override
    public void get002(BeanUser Bean) {
        mPresenter.requestPermissions(new RxPermissions(LoginActivity.this), LoginActivity.this);
        DataUtils.setPhone(LoginActivity.this, mMobile.getText().toString());
        DataUtils.setLoginType(LoginActivity.this, Constant.LOGINTYPE_PHONE + "");
        DataUtils.setToken(LoginActivity.this, Bean.getToken());
        DataUtils.setUserName(LoginActivity.this, Bean.getUserName());
        EventBus.getDefault().post(new MessageEvent(EventBusType.UPDATELOAN));
        EventBus.getDefault().post(new EventPersonalInformation(EventPersonalInformation.OTHER, true));
        EventBus.getDefault().post(new EventFinancing(EventFinancing.REFRESH));
        EventBus.getDefault().post(new EventPush(EventPush.LOGIN, ""));
        DataUtils.put(LoginActivity.this, Constant.channel, BuildConfig.CHANNEL_ID);
        cancel();
        finish();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        cancel();
    }

    @Override
    public void get001(BeanSms Bean) {
        this.vid = Bean.getVid() + "";
        countDown = CountDownThread.newInstance();
        countDown.setOnCountDownListening(this);
        countDown.startTimer("1111111111", Constant.STANDARD_TIME, 1000);
    }


    private Map<String, String> checkSMS(String phone) {
        String mobile = phone;
        if (StringUtils.isEmpty(mobile)) {
            ToastUtil.showToast(LoginActivity.this, getResources().getString(R.string.input_phone));
            return null;
        }
        try {
            if (mobile.substring(0, 1).equals("0")) {
                mobile = mobile.substring(1, mobile.length());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map<String, String> params = new HashMap<>();
        params.put("phone", mobile);
        params.put("type", "2");
        return params;
    }


    private Map<String, String> check(String phone) {
        String mobile = phone;
        String verificationCode = mIcv.getInputContent();
        if (StringUtils.isEmpty(phone)) {
            ToastUtil.showToast(LoginActivity.this, getResources().getString(R.string.input_phone));
            return null;
        }
        if (StringUtils.isEmpty(verificationCode)) {

            ToastUtil.showToast(LoginActivity.this, getResources().getString(R.string.input_verification_code));
            return null;
        }
        try {
            if (mobile.substring(0, 1).equals("0")) {
                mobile = mobile.substring(1, mobile.length());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        String channel = (String) DataUtils.get(LoginActivity.this, Constant.channel, BuildConfig.CHANNEL_ID);
        Map<String, String> params = new HashMap<>();
        params.put("phone", mobile);
        params.put("vcode", verificationCode);
        params.put("loginType", Constant.LOGINTYPE_PHONE + "");
        params.put("vid", vid);
        params.put("channel", channel);
        return params;
    }

    @Override
    public void onTick(String key, long millisUntilFinished) {
        validTime = millisUntilFinished;
        showVerificationCode(1, String.format(LoginActivity.this.getResources().getString(R.string.once_again_to_get), millisUntilFinished / 1000));
    }

    @Override
    public void onFinish(String key) {
        validTime = Constant.STANDARD_TIME;
        countDown.cancel("1111111111");
        showVerificationCode(0, getResources().getString(R.string.get_verification_code));
    }


    public void cancel() {
        if (null != countDown) {
            try {
                validTime = Constant.STANDARD_TIME;
                countDown.cancel("1111111111");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
