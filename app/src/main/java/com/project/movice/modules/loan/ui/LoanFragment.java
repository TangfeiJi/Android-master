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

package com.project.movice.modules.loan.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.project.movice.R;
import com.project.movice.application.MoviceApp;
import com.project.movice.base.fragment.BaseFragment;
import com.project.movice.core.event.EventPersonalInformation;
import com.project.movice.dialog.NoRepaymentDialog;
import com.project.movice.modules.loan.bean.InforBean;
import com.project.movice.modules.loan.contract.LoanContract;
import com.project.movice.modules.loan.presenter.LoanPresenter;
import com.project.movice.utils.ComUtils;
import com.project.movice.utils.DataUtils;
import com.project.movice.utils.LoginUtil;
import com.project.movice.utils.ToastUtils;
import com.project.movice.widget.behavior.SelectOptions;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.project.movice.modules.home.base.BaseFragment.showActivity;


public class LoanFragment extends BaseFragment<LoanPresenter> implements LoanContract.View {

    private static final String TAG = "LoanFragment";
    @BindView(R.id.ll_one)
    LinearLayout llOne;
    @BindView(R.id.ll_two)
    LinearLayout llTwo;
    @BindView(R.id.ll_three)
    LinearLayout llThree;
    @BindView(R.id.ll_four)
    LinearLayout llFour;
    Unbinder unbinder;
    @BindView(R.id.imgOne)
    ImageView imgOne;
    @BindView(R.id.imgTwo)
    ImageView imgTwo;
    @BindView(R.id.imgThree)
    ImageView imgThree;
    @BindView(R.id.imgFour)
    ImageView imgFour;

    @BindView(R.id.next_step)
    Button next_step;

    @BindView(R.id.scrollView)
    ScrollView scrollView;

    public static LoanFragment newInstance() {
        LoanFragment fragment = new LoanFragment();
        //在此处传递参数，可在fragment恢复时使用；避免在构造函数中传参，fragment恢复时不调用非默认构造函数
//        Bundle args = new Bundle();
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.information_fragment;
    }

    @Override
    protected void initEventAndData() {
        inforBean=new InforBean();
        Map<String,String> hm=new HashMap<>();
        if(TextUtils.isEmpty(DataUtils.getPhone(getActivity()))){
        }else{
            mPresenter.request012(hm);
        }

    }


    @Override
    protected void initView() {
        if(MoviceApp.loadShowHind==1){
            next_step.setVisibility(View.VISIBLE);
        }
        if(MoviceApp.loanClick){

        }else{
            next_step.setVisibility(View.GONE);
        }

    }






    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.ll_one, R.id.ll_two, R.id.ll_three, R.id.ll_four, R.id.next_step})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_one:
                if (!TextUtils.isEmpty(DataUtils.getPhone(getActivity()))) {
                    if (MoviceApp.loanClick) {
                        showActivity(getActivity(), CardInforActivity.class);
                    } else {
                       ToastUtils.showToast(getActivity(), getResources().getString(R.string.you_also_have_outstanding_loans));
                    }

                } else {
                    showLogin();
                }

                break;
            case R.id.ll_two:
                if (!TextUtils.isEmpty(DataUtils.getPhone(getActivity()))) {
                    if (inforBean.getId() == 1) {

                        if (MoviceApp.loanClick) {
                            showActivity(getActivity(), PersonalInforActivity.class);
                        } else {
                            ToastUtils.showToast(getActivity(), getResources().getString(R.string.you_also_have_outstanding_loans));
                        }

                    } else {
                        ToastUtils.showToast(getActivity(), getResources().getString(R.string.qingwanshangshen));
                    }
                } else {
                    showLogin();
                }


                break;
            case R.id.ll_three:
                if (!TextUtils.isEmpty(DataUtils.getPhone(getActivity()))) {
                    if (inforBean.getPersonal() == 1) {
                        if (MoviceApp.loanClick) {
                            showActivity(getActivity(), ContactInforActivity.class);
                        } else {
                            ToastUtils.showToast(getActivity(), getResources().getString(R.string.you_also_have_outstanding_loans));
                        }

                    } else {
                        ToastUtils.showToast(getActivity(), getResources().getString(R.string.qingwanshangger));
                    }
                } else {
                    showLogin();
                }
                break;
            case R.id.ll_four:
                if (!TextUtils.isEmpty(DataUtils.getPhone(getActivity()))) {
                    if (inforBean.getContact() == 1) {
                        if (MoviceApp.loanClick) {
                            showActivity(getActivity(), JobInforActivity.class);
                        } else {
                            ToastUtils.showToast(getActivity(), getResources().getString(R.string.you_also_have_outstanding_loans));
                        }
                    } else {
                        ToastUtils.showToast(getActivity(), getResources().getString(R.string.qingwanshanglian));
                    }
                } else {
                    showLogin();
                }
                break;
            case R.id.next_step:

                if (MoviceApp.loanClick) {
                    if (inforBean.getJob() != 1 || inforBean.getId() != 1 || inforBean.getContact() != 1 || inforBean.getPersonal() != 1) {
                        ToastUtils.showToast(getActivity(), getActivity().getResources().getString(R.string.your_information_is_not_perfect));
                        return;
                    }
                    mPresenter.requestPermissions(new RxPermissions(getActivity()));

                } else {
                    ToastUtils.showToast(getActivity(), getResources().getString(R.string.you_also_have_outstanding_loans));
                }



                break;
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventPersonalInformation info) {
        if (info.getType() == EventPersonalInformation.JUMP) {
            next_step.setVisibility(View.VISIBLE);
        }
        if (info.getType() == EventPersonalInformation.OTHER) {
            Map<String,String> hm=new HashMap<>();
            mPresenter.request012(hm);
        }


        if (info.getType() == EventPersonalInformation.PERSONAL) {//个人
            if (info.isPerfect()) {
                inforBean.setPersonal(1);
                imgTwo.setImageResource(R.mipmap.lingyi1);

            } else {
                imgTwo.setImageResource(R.mipmap.lingyi4);
                inforBean.setPersonal(0);

            }

        } else if (info.getType() == EventPersonalInformation.JOB) {//工作
            if (info.isPerfect()) {
                inforBean.setJob(1);
                imgFour.setImageResource(R.mipmap.lingyi1);
            } else {
                imgFour.setImageResource(R.mipmap.lingyi4);
                inforBean.setJob(0);
            }
        } else if (info.getType() == EventPersonalInformation.CONTACT) {//联系人
            if (info.isPerfect()) {
                inforBean.setContact(1);
                imgThree.setImageResource(R.mipmap.lingyi1);
            } else {
                imgThree.setImageResource(R.mipmap.lingyi4);
                inforBean.setContact(0);
            }
        } else if (info.getType() == EventPersonalInformation.ID) {
            if (info.isPerfect()) {
                inforBean.setId(1);
                imgOne.setImageResource(R.mipmap.lingyi1);
            } else {
                imgOne.setImageResource(R.mipmap.lingyi4);
                inforBean.setId(0);
            }


        }
    }

    InforBean inforBean;


    @Override
    public void get012(InforBean bean) {
        inforBean = bean;
        if (inforBean.getId() == 1) {
            imgOne.setImageResource(R.mipmap.lingyi1);
        } else {
            imgOne.setImageResource(R.mipmap.lingyi4);
        }

        if (inforBean.getPersonal() == 1) {
            imgTwo.setImageResource(R.mipmap.lingyi1);
        } else {
            imgTwo.setImageResource(R.mipmap.lingyi4);
        }

        if (inforBean.getContact() == 1) {
            imgThree.setImageResource(R.mipmap.lingyi1);
        } else {
            imgThree.setImageResource(R.mipmap.lingyi4);
        }

        if (inforBean.getJob() == 1) {
            imgFour.setImageResource(R.mipmap.lingyi1);
        } else {
            imgFour.setImageResource(R.mipmap.lingyi4);
        }
    }

    @Override
    public void getPermissionsUsccess() {
        NoRepaymentDialog d = new NoRepaymentDialog(getActivity(), R.mipmap.lingyi39,
                getResources().getString(R.string.face_authentication_title),
                getResources().getString(R.string.face_authentication_content), getResources().getString(R.string.face_recording), false);
        d.setOnClickListener(new NoRepaymentDialog.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelectOptions mOption = new SelectOptions.Builder()
                        .setSelectCount(1)
                        .setOpenCamera(true)
                        .setHasCam(true)
                        .setCameraType(5)
                        .setPhotoType(SelectOptions.TYPE_PORTRAIT)
                        .setCallback(new SelectOptions.Callback() {
                            @Override
                            public void doSelected(String[] images) {
                            }
                        }).build();
                SelectImageActivity.show(getActivity(), mOption);
            }
        });
        d.show();
    }

    @Override
    public void permissionsCancle() {
        ComUtils.showPermissions(getResources().getString(R.string.permission_camera), getActivity());
    }

    public void showLogin() {
        DataUtils.logout(getActivity());
        LoginUtil.login(getActivity());
        getActivity().overridePendingTransition(R.anim.in_to_top, R.anim.to_static);
    }
}
