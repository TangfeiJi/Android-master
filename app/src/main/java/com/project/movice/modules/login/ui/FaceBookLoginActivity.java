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

package com.project.movice.modules.login.ui;

import android.content.Intent;

import com.facebook.FacebookActivity;
import com.facebook.accountkit.Account;
import com.facebook.accountkit.AccountKit;
import com.facebook.accountkit.AccountKitCallback;
import com.facebook.accountkit.AccountKitError;
import com.facebook.accountkit.AccountKitLoginResult;
import com.facebook.accountkit.PhoneNumber;
import com.facebook.accountkit.ui.AccountKitActivity;
import com.facebook.accountkit.ui.AccountKitConfiguration;
import com.facebook.accountkit.ui.LoginType;
import com.project.movice.BuildConfig;
import com.project.movice.R;
import com.project.movice.base.activity.BaseActivity;
import com.project.movice.core.event.EventPersonalInformation;
import com.project.movice.modules.login.bean.BeanSms;
import com.project.movice.modules.login.contract.LoginContract;
import com.project.movice.modules.login.presenter.LoginPresenter;
import com.project.movice.modules.main.bean.BeanUser;
import com.project.movice.utils.Constant;
import com.project.movice.utils.DataUtils;
import com.project.movice.utils.EventBusType;
import com.project.movice.utils.EventPush;
import com.project.movice.utils.MessageEvent;
import com.project.movice.utils.StringUtils;
import com.project.movice.utils.ToastUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: ForgetSky
 * @date: 2019/3/4
 */
public class FaceBookLoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.View {

    @Override
    protected void initView() {
        facebookLogin();
    }

    String phoneNumberString;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initToolbar() {

    }

    @Override
    protected void initEventAndData() {

    }


    public static int APP_REQUEST_CODE = 99;
    Map<String, String> params = new HashMap<>();




    public void facebookLogin() {

        final Intent intent = new Intent(this, AccountKitActivity.class);
        final AccountKitConfiguration.AccountKitConfigurationBuilder configurationBuilder =
                new AccountKitConfiguration.AccountKitConfigurationBuilder(
                        LoginType.PHONE,
                        AccountKitActivity.ResponseType.TOKEN);
        configurationBuilder.setDefaultCountryCode("ID");
        configurationBuilder.setSMSWhitelist(new String[]{"ID"});
        final AccountKitConfiguration configuration = configurationBuilder.build();
        intent.putExtra(AccountKitActivity.ACCOUNT_KIT_ACTIVITY_CONFIGURATION, configuration);
        startActivityForResult(intent, APP_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == APP_REQUEST_CODE) { // confirm that this response matches your request
            AccountKitLoginResult loginResult = data.getParcelableExtra(AccountKitLoginResult.RESULT_KEY);
            String toastMessage = null;
            if (loginResult.getError() != null) {//登录出错
                toastMessage = loginResult.getError().getErrorType().getMessage();
                ToastUtils.showToast(FaceBookLoginActivity.this, getResources().getString(R.string.facebook_login_erroor));
//                showActivity(FacebookLoginActivity.this, FaceBookLoginActivity.class);
                finish();
            } else if (loginResult.wasCancelled()) {//登录取消
                toastMessage = "Login Cancelled";
                FaceBookLoginActivity.this.setResult(RESULT_OK);
                finish();
                overridePendingTransition(R.anim.to_static, R.anim.out_to_buttom);
            } else {
                if (loginResult.getAccessToken() != null) {
                    toastMessage = "Success:" + loginResult.getAccessToken().getAccountId();
                    AccountKit.getCurrentAccount(new AccountKitCallback<Account>() {
                        @Override
                        public void onSuccess(final Account account) {
                            // Get Account Kit ID
                            String accountKitId = account.getId();
                            // Get email
                            String email = account.getEmail();
                             phoneNumberString = null;
                            // Get phone number
                            PhoneNumber phoneNumber = account.getPhoneNumber();
                            if (phoneNumber != null) {
                                phoneNumberString = phoneNumber.toString();
                            }
                            if (!StringUtils.isEmpty(phoneNumberString)) {

                                String channel = (String) DataUtils.get(FaceBookLoginActivity.this, Constant.channel, BuildConfig.CHANNEL_ID);
                                if (channel.contains("not%20set")) {
                                    channel = BuildConfig.CHANNEL_ID;
                                }
                                params.put("phone", phoneNumberString.replace("+62", ""));
                                params.put("facebookId", accountKitId);
                                params.put("loginType", Constant.LOGINTYPE_FACEBOOK + "");
                                params.put("channel", channel);
                                params.put("cc", "62");//所有接口统一62，国家手机
                                params.put("systemTime", System.currentTimeMillis() + "");
                                mPresenter.request002(params);
                            } else {
                                ToastUtils.showToast(FaceBookLoginActivity.this, getResources().getString(R.string.facebook_login_erroor));
//                                showActivity(FacebookLoginActivity.this, FaceBookLoginActivity.class);
                                finish();
                            }
                        }

                        @Override
                        public void onError(final AccountKitError error) {
                            ToastUtils.showToast(FaceBookLoginActivity.this, getResources().getString(R.string.facebook_login_erroor));
//                            showActivity(FacebookLoginActivity.this, FaceBookLoginActivity.class);
                            finish();
                        }
                    });
                }
            }
        }
    }


    @Override
    public void get002(BeanUser response) {
        mPresenter.requestPermissions(new RxPermissions(FaceBookLoginActivity.this),FaceBookLoginActivity.this);
        DataUtils.setPhone(FaceBookLoginActivity.this, phoneNumberString.replace("+62", ""));
        DataUtils.setLoginType(FaceBookLoginActivity.this, response.getLoginType() + "");
        DataUtils.setToken(FaceBookLoginActivity.this, response.getToken());
        DataUtils.setUserName(FaceBookLoginActivity.this, response.getUserName());
        EventBus.getDefault().post(new MessageEvent(EventBusType.UPDATELOAN));
        EventBus.getDefault().post(new EventPush(EventPush.LOGIN, ""));
        EventBus.getDefault().post(new EventPersonalInformation(EventPersonalInformation.OTHER, true));
        DataUtils.put(FaceBookLoginActivity.this, Constant.channel, BuildConfig.CHANNEL_ID);
        finish();
    }

    @Override
    public void get001(BeanSms Bean) {

    }
}
