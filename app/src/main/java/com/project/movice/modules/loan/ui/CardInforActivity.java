package com.project.movice.modules.loan.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.project.movice.R;
import com.project.movice.application.MoviceApp;
import com.project.movice.base.activity.BaseActivity;
import com.project.movice.core.event.EventPersonalInformation;
import com.project.movice.dialog.NoRepaymentDialog;
import com.project.movice.modules.loan.bean.BeanPersonalInformation;
import com.project.movice.modules.loan.contract.CardContract;
import com.project.movice.modules.loan.presenter.CardPresenter;
import com.project.movice.utils.ComUtils;
import com.project.movice.utils.Constant;
import com.project.movice.utils.ConstantParams;
import com.project.movice.utils.GlideImageLoader;
import com.project.movice.utils.IDcard;
import com.project.movice.utils.StringUtils;
import com.project.movice.utils.ToastUtils;
import com.project.movice.widget.behavior.GenderDialog;
import com.project.movice.widget.behavior.SelectOptions;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.PatternSyntaxException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CardInforActivity extends BaseActivity<CardPresenter> implements CardContract.View {


    @BindView(R.id.img_img)
    ImageView imgImg;
    @BindView(R.id.username_error)
    TextView usernameError;
    @BindView(R.id.username)
    EditText username;
    @BindView(R.id.ktp_error)
    TextView ktpError;
    @BindView(R.id.select_ktp_number)
    EditText selectKtpNumber;
    @BindView(R.id.sex)
    TextView sex;
    @BindView(R.id.select_sex_status)
    TextView selectSexStatus;
    @BindView(R.id.icon2)
    ImageView icon2;
    @BindView(R.id.select_sex)
    RelativeLayout selectSex;
    @BindView(R.id.next_step)
    Button nextStep;
    private String imagePath;
    @BindView(R.id.llContent)
    LinearLayout llContent;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_title)
    TextView mTitle;

    @Override
    public void initView() {
        initData();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_card;
    }

    @Override
    protected void initToolbar() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        mTitle.setText(R.string.fill_in_personal_information);
        mToolbar.setNavigationOnClickListener(v -> onBackPressedSupport());
    }

    @Override
    protected void initEventAndData() {
        mPresenter.request006(new HashMap<>());
    }

    public void initData() {
        username.addTextChangedListener(new TextWatcher() {
            String str = "";

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String strs = username.getText().toString();
                if (!stringFilter(strs.toString()) && !"".equals(strs)) {
                    username.setText(str);
                    username.setSelection(str.length());
                } else {
                    str = strs;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }


    private void isShow(View view, TextView text, String str) {
        if (!StringUtils.isEmpty(str)) {
            text.setText(str);
            view.setVisibility(View.INVISIBLE);
        } else {
            text.setText("");
            view.setVisibility(View.GONE);
        }
    }

    public static boolean stringFilter(String str) throws PatternSyntaxException {
        if (null == str)
            return true;
        // 只允许字母、和空格
        String regEx = "[a-zA-Z .]+";
        return str.matches(regEx);
    }

    @OnClick({R.id.img_img, R.id.select_sex, R.id.next_step, R.id.imgClose, R.id.imgDraw, R.id.llContent})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_img:
                NoRepaymentDialog d = new NoRepaymentDialog(this, R.mipmap.lingyi37, getResources().getString(R.string.are_recognized_title),
                        getResources().getString(R.string.ktp_photos_condition), getResources().getString(R.string.start_taking_photos), true);
                d.setOnClickListener(new NoRepaymentDialog.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mPresenter.requestPermissions(new RxPermissions(CardInforActivity.this));
                    }
                });
                d.show();

                break;
            case R.id.select_sex:
                final String[] sex_array = getResources().getStringArray(R.array.sex);
                GenderDialog genderDialog = new GenderDialog(this);
                genderDialog.setOnClickListener(new GenderDialog.OnClickListener() {
                    @Override
                    public void onClick(int gender) {
                        if (gender == GenderDialog.GENDER_MAN) {
                            isShow(selectSexStatus, sex, sex_array[0]);
                        } else if (gender == GenderDialog.GENDER_FEMALE) {
                            isShow(selectSexStatus, sex, sex_array[1]);
                        }
                    }
                });
                genderDialog.show();
                break;
            case R.id.next_step:
                String name = username.getText().toString();
                String ktpNumber = selectKtpNumber.getText().toString();
                String gender = sex.getText().toString();
                if (StringUtils.isEmpty(name)) {
                    ToastUtils.showToast(CardInforActivity.this, getResources().getString(R.string.select_name));
                    return;
                }
                if (StringUtils.isEmpty(ktpNumber)) {
                    ToastUtils.showToast(CardInforActivity.this, getResources().getString(R.string.select_ktp_number));
                    return;
                } else {
                    IDcard card = new IDcard();
                    if (!card.isIdcard(ktpNumber)) {
                        ktpError.setVisibility(View.VISIBLE);
                        ktpError.setText(getResources().getString(R.string.ktp_inconformity));
                        ToastUtils.showToast(CardInforActivity.this, getResources().getString(R.string.ktp_inconformity));
                        return;
                    }
                }
                if (StringUtils.isEmpty(gender)) {
                    ToastUtils.showToast(CardInforActivity.this, getResources().getString(R.string.select_sex));
                    return;
                }

                if (!isOk) {
                    ToastUtils.showToast(CardInforActivity.this, getResources().getString(R.string.select_ktp_photo));
                    return;
                }
                Map<String, String> hm = new HashMap<>();
                hm.put("name", name);
                hm.put("ktpNumber", ktpNumber);
                hm.put("gender", ConstantParams.gender(true, gender));
                mPresenter.request054(hm);
                break;
            case R.id.imgClose:
            case R.id.llContent:
            case R.id.imgDraw:
                llContent.setVisibility(View.GONE);
                break;
        }
    }


    boolean isOk;


    @Override
    public void get006(BeanPersonalInformation info) {

        if(TextUtils.isEmpty(info.getName())){
            llContent.setVisibility(View.VISIBLE);
        }else{
            llContent.setVisibility(View.GONE);
        }
        username.setText(info.getName());
        selectKtpNumber.setText(info.getKtpNumber());
        GlideImageLoader.load(CardInforActivity.this, info.getImageDomain() + info.getKtpPhoto(), imgImg);
        isShow(selectSexStatus, sex, ConstantParams.gender(false, info.getGender()));
        if(TextUtils.isEmpty(info.getKtpPhoto())){
            isOk = false;
        }else{
            isOk = true;
        }

    }

    @Override
    public void get054() {
        EventBus.getDefault().post(new EventPersonalInformation(EventPersonalInformation.ID, true));
        finish();
    }

    @Override
    public void get027() {
        GlideImageLoader.load(CardInforActivity.this, imagePath, imgImg);
        isOk = true;
    }




    @Override
    public void permissionsSuccess() {
        SelectOptions mOption = new SelectOptions.Builder()
                .setSelectCount(1)
                .setOpenCamera(true)
                .setHasCam(true)
                .setCameraType(2)
                .setPhotoType(SelectOptions.TYPE_IDCARD_FRONT)
                .setCallback(new SelectOptions.Callback() {
                    @Override
                    public void doSelected(String[] images) {
                        imagePath = images[0];


                        Map<String, String> hm = new HashMap<>();
                        hm.put("type", Constant.IMAGE_KTP);
                        mPresenter.request027((imagePath), hm);
                    }
                }).build();
        SelectImageActivity.show(CardInforActivity.this, mOption);
    }

    @Override
    public void permissionsCancle() {
        ComUtils.showPermissions(getResources().getString(R.string.permission_camera), CardInforActivity.this);
    }
}
