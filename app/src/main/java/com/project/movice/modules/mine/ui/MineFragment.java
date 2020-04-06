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

package com.project.movice.modules.mine.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.movice.R;
import com.project.movice.application.MoviceApp;
import com.project.movice.base.fragment.BaseFragment;
import com.project.movice.core.http.api.ApiService;
import com.project.movice.dialog.ShareDialog;
import com.project.movice.modules.mine.contract.MineContract;
import com.project.movice.modules.mine.presenter.MinePresenter;
import com.project.movice.utils.ComUtils;
import com.project.movice.utils.Constant;
import com.project.movice.utils.DataUtils;
import com.project.movice.utils.LoginUtil;
import com.project.movice.utils.ShareUtils;
import com.project.movice.widget.behavior.CallPhoneDialog;
import com.sobot.chat.SobotApi;
import com.sobot.chat.api.model.Information;
import com.tbruyelle.rxpermissions2.RxPermissions;

import butterknife.BindView;
import butterknife.OnClick;

import static com.project.movice.modules.loan.ui.BaseActivity.showActivity;

public class MineFragment extends BaseFragment<MinePresenter> implements MineContract.View {



    public static MineFragment newInstance() {
        return new MineFragment();
    }

    @Override
    protected void initView() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initEventAndData() {

    }

    @BindView(R.id.user_head)
    ImageView mUserHead;
    @BindView(R.id.user_name)
    TextView mUserName;
    @BindView(R.id.i_borrow)
    LinearLayout mIborrow;
    @BindView(R.id.my_repayment)
    LinearLayout mMyRepayment;
    @BindView(R.id.friends_share)
    LinearLayout mFriendsShare;
    @BindView(R.id.security_center)
    LinearLayout mSecurityCenter;
    @BindView(R.id.settings)
    LinearLayout mSettings;
    @BindView(R.id.test)
    TextView mTest;



    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (TextUtils.isEmpty(DataUtils.getPhone(getActivity()))) {
            mUserName.setText(getResources().getString(R.string.click_on_the_avatar_login));
            mUserHead.setImageResource(R.mipmap.sadad1w21c);
            mSettings.setVisibility(View.VISIBLE);
        } else {
            mUserHead.setImageResource(R.mipmap.lingyi2);

            mUserName.setText(DataUtils.getPhone(getActivity()));
            mSettings.setVisibility(View.VISIBLE);
        }
        setUserVisibleHint(true);
    }



    @OnClick({R.id.user_name, R.id.user_head,  R.id.i_borrow, R.id.my_repayment, R.id.friends_share,
            R.id.security_center, R.id.settings, R.id.callPhone,R.id.ll_kefu,R.id.ll_faq})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.i_borrow:
                if (TextUtils.isEmpty(DataUtils.getPhone(getActivity()))) {
                    showLogin();
                } else {
                    showActivity(getActivity(), MyLoanActivity.class);
                }
                break;
            case R.id.user_name:
            case R.id.user_head:
                if (TextUtils.isEmpty(DataUtils.getPhone(getActivity()))) {
                    showLogin();
                }
                break;

            case R.id.my_repayment:
                if (TextUtils.isEmpty(DataUtils.getPhone(getActivity()))) {
                    showLogin();
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putInt(MyLoanActivity.KEY_STATUS, Constant.LOAN_ORDER_REPAYMENT_COMPLETED);//查询还款完成
                    showActivity(getActivity(), MyLoanActivity.class, bundle);
                }
                break;
            case R.id.friends_share:
                ShareDialog share = new ShareDialog(getActivity());
                share.setOnShareClickListener(new ShareDialog.OnShareClickListener() {
                    @Override
                    public void onShareType(int type) {
                        ShareUtils.shareFacebook(MineFragment.this, "https://play.google.com/store/apps/details?id=" + getActivity().getPackageName());
                    }
                });
                share.show();
                break;

            case R.id.security_center:
                break;
            case R.id.settings:
                showActivity(getActivity(), SettingsActivity.class);
                break;
            case R.id.callPhone:
                callPhone();
//startActivity(new Intent(getActivity(),ContactInforActivity.class));
                break;
            case R.id.ll_kefu:
                Information info = new Information();
                info.setAppkey(ApiService.ZHICHI_KEY);  //分配给App的的密钥
                SobotApi.startSobotChat(getActivity(), info);
                break;
            case R.id.ll_faq:
                showActivity(getActivity(), FAQListActivity.class);
//                startActivity(new Intent(getActivity(), WebViewActivity.class));
                break;
        }
    }

    private void callPhone() {

        mPresenter.requestPermissions(new RxPermissions(getActivity()));

    }




    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        ShareUtils.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }



    public void showLogin() {
//        showActivity(getActivity(), FaceBookLoginActivity.class);
//        showActivity(getActivity(), FacebookLoginActivity.class);

        LoginUtil.login(getActivity());
        getActivity().overridePendingTransition(R.anim.in_to_top, R.anim.to_static);
    }

    @Override
    public void permissionsSuccess() {
        CallPhoneDialog callPhoneDialog = new CallPhoneDialog(getActivity());
        callPhoneDialog.setOnClickListener(new CallPhoneDialog.OnClickListener() {
            @Override
            public void onCancel() {

            }
            @Override
            public void onConfirm() {
                Intent intent = new Intent(Intent.ACTION_CALL);
                Uri data = Uri.parse("tel:" +  MoviceApp.callPhone);
                intent.setData(data);
                getActivity().startActivity(intent);
            }
        });
        callPhoneDialog.show();
    }

    @Override
    public void permissionsCancle() {
        ComUtils.showPermissions(getActivity().getResources().getString(R.string.callphone),getActivity());
    }
}

