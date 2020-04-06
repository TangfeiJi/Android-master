package com.project.movice.modules.loan.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.project.movice.R;
import com.project.movice.base.activity.BaseActivity;
import com.project.movice.core.event.EventPersonalInformation;
import com.project.movice.dialog.ConfirmPromptDialog;
import com.project.movice.dialog.ListDialog;
import com.project.movice.dialog.NoRepaymentDialog;
import com.project.movice.dialog.OnItemClickListener;
import com.project.movice.modules.area.AreaSelectActivity;
import com.project.movice.modules.loan.bean.BeanPersonalInformation;
import com.project.movice.modules.loan.contract.PersonalContract;
import com.project.movice.modules.loan.presenter.PersonalPresenter;
import com.project.movice.utils.Constant;
import com.project.movice.utils.ConstantParams;
import com.project.movice.utils.DataUtils;
import com.project.movice.utils.DateTimeUtil;
import com.project.movice.utils.GlideImageLoader;
import com.project.movice.utils.IDcard;
import com.project.movice.utils.StringUtils;
import com.project.movice.utils.ToastUtils;
import com.project.movice.widget.behavior.GenderDialog;
import com.project.movice.widget.behavior.PackDateDialog;
import com.project.movice.widget.behavior.SelectOptions;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.PatternSyntaxException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.project.movice.modules.loan.ui.BaseActivity.showActivityForResult;


/**
 * 个人基本信息
 * Created by wy on 2018/1/11 18:49.
 */


public class PersonalInforActivity extends BaseActivity<PersonalPresenter> implements View.OnTouchListener, View.OnFocusChangeListener, TextWatcher, PersonalContract.View {

    @BindView(R.id.scrollview)
    ScrollView mScrollView;

    @BindView(R.id.username_error)
    TextView mUserNameError;
    @BindView(R.id.username)
    TextView mUserName;//姓名

    @BindView(R.id.ktp_error)
    TextView mKtpError;//KTP证件错误提示
    @BindView(R.id.select_ktp_number)
    TextView mKtpNumber;//KTP证件号



    @BindView(R.id.select_sex)
    RelativeLayout mSelectSex;//选择性别
    @BindView(R.id.sex)
    TextView mSex;//性别
    @BindView(R.id.select_sex_status)
    TextView mSelectSexStatus;//选择性别状态

    @BindView(R.id.date_of_birth)
    RelativeLayout mDateOfBirth;//选择出生日期
    @BindView(R.id.birth)
    TextView mBirth;//出生日期
    @BindView(R.id.date_of_birth_status)
    TextView mDateOfBirthStatus;//选择出生日期状态

    @BindView(R.id.marital_status)
    RelativeLayout mMaritalStatus;//选择婚姻状况
    @BindView(R.id.marital)
    TextView mMarital;//婚姻
    @BindView(R.id.select_marital_status_status)
    TextView mSelectMaritalStatus;//婚姻状态

    @BindView(R.id.home_address)
    RelativeLayout mHomeAddress;//选择住址
    @BindView(R.id.address)
    TextView mAddress;//住址
    @BindView(R.id.select_home_address_status)
    TextView mSelectHomeAddressStatus;//选择住址状态

    //详细公司地址
    @BindView(R.id.address_error)
    TextView mAddressError;
    //详细公司地址
    @BindView(R.id.detailed_address)
    EditText mDetailedAddress;

    //手机号
    @BindView(R.id.mobile_error)
    TextView mMobileError;
    @BindView(R.id.mobile)
    EditText mMobile;
    //邮箱
    @BindView(R.id.email)
    EditText mEmail;
    //facebook账号
    @BindView(R.id.whatsapp)
    EditText mWhatsApp;

    @BindView(R.id.educt)
    TextView educt;
    @BindView(R.id.phoneTime)
    TextView phoneTime;


    @BindView(R.id.select_educt_status)
    TextView selectEductStatus;
    @BindView(R.id.select_phone_status)
    TextView select_phone_status;


    private Map<String, String> params = new HashMap<>();

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_title)
    TextView mTitle;
    @BindView(R.id.username_mother)
    TextView username_mother;

    @BindView(R.id.mother)
    EditText mother;

    private String province = "";//省
    private String city = "";//市
    private String area = "";//区
    private String street = "";//街道
    private BeanPersonalInformation pi;
    private String imagePath;//KTP照路径
    private int whoHasFocus = 0;

    private boolean isNextPage = false; //是否下一页   true 保存后进入下一项，false 保存后返回上一级


    @Override
    protected int getLayoutId() {
        return R.layout.personal_lnformation;
    }

    @Override
    public void initView() {
        mScrollView.setOnTouchListener(this);
        mUserName.setOnFocusChangeListener(this);
        mUserName.addTextChangedListener(this);

        mKtpNumber.setOnFocusChangeListener(this);
        mKtpNumber.addTextChangedListener(this);
//        //设置检测回调call back
//        IDCardQualityOCRManager.getInstance(this).setIdCardCallBack(this);
        mDetailedAddress.setOnFocusChangeListener(this);
        mDetailedAddress.addTextChangedListener(this);

        mMobile.setOnFocusChangeListener(this);
        mMobile.addTextChangedListener(this);
    }



    @Override
    protected void initToolbar() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        mTitle.setText(R.string.personal_basic_information);
        mToolbar.setNavigationOnClickListener(v -> onBackPressedSupport());
    }

    @Override
    protected void initEventAndData() {
mPresenter.request006(new HashMap<>());
    }

    public static boolean stringFilter(String str) throws PatternSyntaxException {
        if (null == str)
            return true;
        // 只允许字母、和空格
        String regEx = "[a-zA-Z .]+";
        return str.matches(regEx);
    }



    @OnClick({ R.id.select_sex, R.id.home_address, R.id.date_of_birth, R.id.marital_status,
            R.id.next_step, R.id.marital_educt,R.id.jie_phone})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.left_image:
            case R.id.left_text:
                if (exitVerification()) {
                    showSave();
                } else {
                   finish();
                }
                break;
            case R.id.select_sex://性别
                final String[] sex_array = getResources().getStringArray(R.array.sex);

                GenderDialog genderDialog = new GenderDialog(this);
                genderDialog.setOnClickListener(new GenderDialog.OnClickListener() {
                    @Override
                    public void onClick(int gender) {
                        if (gender == GenderDialog.GENDER_MAN) {
                            isShow(mSelectSexStatus, mSex, sex_array[0]);
                        } else if (gender == GenderDialog.GENDER_FEMALE) {
                            isShow(mSelectSexStatus, mSex, sex_array[1]);
                        }
                    }
                });
                genderDialog.show();
                break;
            case R.id.date_of_birth://出生日期
                PackDateDialog packDate = new PackDateDialog(this);
                packDate.setOnSelectedChangeListener(new PackDateDialog.onSelectedChangeListener() {
                    @Override
                    public void onSelected(long timeMillis) {
//                        mBirth.setText(DateTimeUtil.formatEnDateTime(timeMillis, DateTimeUtil.EN_DF_YYYY_MM_DD));
                        isShow(mDateOfBirthStatus, mBirth, DateTimeUtil.formatEnDateTime(timeMillis, DateTimeUtil.EN_DF_YYYY_MM_DD));
                    }
                });
                packDate.show();
                break;
            case R.id.marital_status://婚姻
                final String[] marital_array = getResources().getStringArray(R.array.marital_status);
                ListDialog maritalDialog = new ListDialog(this, marital_array);
                maritalDialog.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        mMarital.setText(marital_array[position]);
                        isShow(mSelectMaritalStatus, mMarital, marital_array[position]);
                    }
                });
                maritalDialog.show();
                break;


            case R.id.jie_phone://可以接电话的审核时间

                final String[] phoneList = getResources().getStringArray(R.array.select_phone_time);
                ListDialog phoneDialog = new ListDialog(this, phoneList);
                phoneDialog.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        phoneTime.setText(phoneList[position]);
                        isShow(select_phone_status, phoneTime, phoneList[position]);
                    }
                });
                phoneDialog.show();

                break;


            case R.id.marital_educt:
                final String[] esuct = getResources().getStringArray(R.array.select_educt);
                ListDialog esuctDialog = new ListDialog(this, esuct);
                esuctDialog.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        educt.setText(esuct[position]);
                        isShow(selectEductStatus, educt, esuct[position]);
                    }
                });
                esuctDialog.show();


                break;

            case R.id.home_address:
                showActivityForResult(this, AreaSelectActivity.class, 100);
                break;
            case R.id.next_step:
                isNextPage = true;
                submit();
                break;
            case R.id.right_text:
                isNextPage = false;
                submit();
                break;
        }
    }

    private void submit() {
        String name = mUserName.getText().toString();
        String motherDa = mother.getText().toString();
        String ktpNumber = mKtpNumber.getText().toString();
//        String gender = mSex.getText().toString();
//        String birth = mBirth.getText().toString();
        String marital = mMarital.getText().toString();
        String address = mAddress.getText().toString();
        String detailedAddress = mDetailedAddress.getText().toString();
        String mobile = mMobile.getText().toString();
//        String email = mEmail.getText().toString();
        String whatsapp = mWhatsApp.getText().toString();
        String eductData = educt.getText().toString();

        String phoneTimeDate=phoneTime.getText().toString();

//        if (!StringUtils.isEmpty(email)) {
//            email = email.replaceAll(" ", "");
//        }


//        if (StringUtils.isEmpty(ktpNumber)) {
//            ToastUtil.showShort(R.string.select_ktp_number);
//            return;
//        } else {
//            IDcard card = new IDcard();
//            if (!card.isIdcard(ktpNumber)) {
//                mKtpError.setVisibility(View.VISIBLE);
//                mKtpError.setText(getResources().getString(R.string.ktp_inconformity));
//                ToastUtil.showShort(R.string.ktp_inconformity);
//                return;
//            }
//        }
//        if (StringUtils.isEmpty(mobile)) {
//            ToastUtil.showShort(R.string.input_phone);
//            return;
//        }
        if (StringUtils.isEmpty(whatsapp)) {
            ToastUtils.showToast(PersonalInforActivity.this,getResources().getString(R.string.please_enter_whatsapp_account));
            return;
        }
//        if (StringUtils.isEmpty(name)) {
//            ToastUtil.showShort(R.string.select_name);
//            return;
//        }


        if (StringUtils.isEmpty(motherDa)) {

            ToastUtils.showToast(PersonalInforActivity.this,getResources().getString(R.string.select_mothername));
            return;
        }
//        if (StringUtils.isEmpty(gender)) {
//            ToastUtil.showShort(R.string.select_sex);
//            return;
//        }

//        if (StringUtils.isEmpty(birth)) {
//            ToastUtil.showShort(R.string.select_date_of_birth);
//            return;
//        }
        if (StringUtils.isEmpty(marital)) {
            ToastUtils.showToast(PersonalInforActivity.this,getResources().getString(R.string.select_marital_status));
            return;
        }
        if (StringUtils.isEmpty(eductData)) {
            ToastUtils.showToast(PersonalInforActivity.this,getResources().getString(R.string.select_educt_status));
            return;
        }
        if (StringUtils.isEmpty(phoneTimeDate)) {
            ToastUtils.showToast(PersonalInforActivity.this,getResources().getString(R.string.select_phone_status));
            return;
        }



        if (StringUtils.isEmpty(province)) {
            ToastUtils.showToast(PersonalInforActivity.this,getResources().getString(R.string.select_home_address));
            return;
        }
        if (StringUtils.isEmpty(detailedAddress)) {
            ToastUtils.showToast(PersonalInforActivity.this,getResources().getString(R.string.please_enter_your_company_detailed_home_address));
            return;
        }
//        if (!StringUtils.isEmpty(email) && !StringUtils.isEmail(email)) {
//            ToastUtil.showShort(R.string.email_error);
//            return;
//        }
        Map<String, String> params = new HashMap<>();
//        params.put("name", name);
//        params.put("ktpNumber", ktpNumber);
//        params.put("gender", ConstantParams.gender(true, gender));
//        params.put("birthDate", birth);
        params.put("maritalStatus", ConstantParams.getMarital(true, marital));
        params.put("province", province);
        params.put("city", city);
        params.put("area", area);
        params.put("street", street);
        params.put("detailedAddress", detailedAddress);
        params.put("mobile", DataUtils.getPhone(PersonalInforActivity.this));
        params.put("familyLandline", "");
        params.put("email", "");
        params.put("whatsapp", whatsapp);
        params.put("education", ConstantParams.getEducation(true, eductData));
        params.put("phoneTime", ConstantParams.getPhoneTime(true, phoneTimeDate));
        params.put("motherName", motherDa);


        mPresenter.request054(params);
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

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        InputMethodManager imm = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        return false;
    }


    @Override
    public void onFocusChange(View view, boolean b) {
//        if (b)
//            return;
        EditText e = (EditText) view;
        switch (view.getId()) {
            case R.id.username:
                whoHasFocus = 1;
                if (!b && StringUtils.isEmpty(e.getText().toString())) {
                    mUserNameError.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.select_ktp_number:
                whoHasFocus = 2;
                if (!b && StringUtils.isEmpty(e.getText().toString())) {
                    mKtpError.setVisibility(View.VISIBLE);
                    mKtpError.setText(getResources().getString(R.string.please_fill_out));
                } else if (!b && !StringUtils.isEmpty(e.getText().toString())) {
                    IDcard card = new IDcard();
                    if (!card.isIdcard(e.getText().toString())) {
                        mKtpError.setVisibility(View.VISIBLE);
                        mKtpError.setText(getResources().getString(R.string.ktp_inconformity));
                    }
                }
                break;
            case R.id.detailed_address:
                whoHasFocus = 3;
                if (!b && StringUtils.isEmpty(e.getText().toString())) {
                    mAddressError.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.mobile:
                whoHasFocus = 4;
                if (!b && StringUtils.isEmpty(e.getText().toString())) {
                    mMobileError.setVisibility(View.VISIBLE);
                }
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        int length = charSequence.length();
        switch (whoHasFocus) {
            case 1:
                if (length > 0) {
                    mUserNameError.setVisibility(View.GONE);
                }
                break;
            case 2:
                if (length > 0) {
                    IDcard card = new IDcard();
                    mKtpError.setVisibility(View.GONE);
                    isShow(mSelectSexStatus, mSex, ConstantParams.gender(false, card.getGender(charSequence.toString())));
                }
                break;
            case 3:
                if (length > 0) {
                    mAddressError.setVisibility(View.GONE);
                }
                break;
            case 4:
                if (length > 0) {
                    mMobileError.setVisibility(View.GONE);
                }
                break;
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    private boolean exitVerification() {
        String name = mUserName.getText().toString();
        String ktpNumber = mKtpNumber.getText().toString();
        String sex = mSex.getText().toString();
        String birth = mBirth.getText().toString();
        String marital = mMarital.getText().toString();
        String address = mDetailedAddress.getText().toString();
        String mobile = mMobile.getText().toString();
        String email = mEmail.getText().toString();
        String whatsapp = mWhatsApp.getText().toString();

        BeanPersonalInformation info = new BeanPersonalInformation();
        info.setName(name);
        info.setKtpNumber(ktpNumber);

        info.setGender(ConstantParams.gender(true, sex));
        info.setBirthDate(birth);
        info.setMaritalStatus(ConstantParams.getMarital(true, marital));
        info.setProvince(province);
        info.setCity(city);
        info.setArea(area);
        info.setStreet(street);
        info.setDetailedAddress(address);
        info.setMobile(mobile);
//        info.setFamilyLandline(familyLandline);
        info.setEmail(email);
        info.setWhatsapp(whatsapp);
        return mPresenter.exitVerification(info);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
            final Map map = (Map) data.getSerializableExtra("addressInfo");
            province = (String) map.get("provName");
            city = (String) map.get("cityName");
            area = (String) map.get("districtName");
            street = (String) map.get("villName");
            isShow(mSelectHomeAddressStatus, mAddress, province + " " + city + " " + area + " " + street);
        }
    }

    private void showSave() {
        ConfirmPromptDialog confirm = new ConfirmPromptDialog(this,
                R.mipmap.lingyi5,
                getResources().getString(R.string.are_you_sure_you_quit_without_saving),
                getResources().getString(R.string.cancel),
                getResources().getString(R.string.confirm));
        confirm.setOnClickListener(new ConfirmPromptDialog.OnClickListener() {
            @Override
            public void onClick() {
                finish();
            }
        });
        confirm.show();
    }

    /**
     * 是否可编辑ktp或用户名
     *
     * @param b false可编辑  true不可编辑
     */
    public void setEdit(boolean b) {
        if (b) {//不可修改
            mUserName.setEnabled(false);
            mUserName.setTextColor(ContextCompat.getColor(this, R.color.auxiliary));
            mKtpNumber.setEnabled(false);
            mKtpNumber.setTextColor(ContextCompat.getColor(this, R.color.auxiliary));
//            mUploadKtp.setOnClickListener(null);
//            mUploadKtpStatus.setVisibility(View.INVISIBLE);
        } else {
            mUserName.setEnabled(true);
            mUserName.setTextColor(ContextCompat.getColor(this, R.color.character_and_title));
            mKtpNumber.setEnabled(true);
            mKtpNumber.setTextColor(ContextCompat.getColor(this, R.color.character_and_title));
//            mUploadKtp.setOnClickListener(this);
//            mUploadKtpStatus.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (exitVerification()) {
                showSave();
            } else {
               finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }











    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    public void get006(BeanPersonalInformation info) {

        info.setFamilyLandline("");//这版去掉家庭电话
        mUserName.setText(info.getName());
        mKtpNumber.setText(info.getKtpNumber());
        mEmail.setText(info.getEmail());
        mWhatsApp.setText(info.getWhatsapp());

//        mMobile.setText(info.getMobile());
        isShow(mSelectSexStatus, mSex, ConstantParams.gender(false, info.getGender()));
        isShow(mDateOfBirthStatus, mBirth, info.getBirthDate());




        isShow(select_phone_status, phoneTime, ConstantParams.getPhoneTime(false, info.getPhoneTime()));


        isShow(mSelectMaritalStatus, mMarital, ConstantParams.getMarital(false, info.getMaritalStatus()));
        isShow(selectEductStatus, educt, ConstantParams.getEducation(false, info.getEducation()));
        mother.setText(info.getMotherName());

        this.province = info.getProvince();
        this.city = info.getCity();
        this.area = info.getArea();
        this.street = info.getStreet();
        isShow(mSelectHomeAddressStatus, mAddress, province + " " + city + " " + area + " " + street);
        mDetailedAddress.setText(info.getDetailedAddress());
        setEdit(info.getModifyInformation() == 2);



    }

    @Override
    public void get054() {
        EventBus.getDefault().post(new EventPersonalInformation(EventPersonalInformation.PERSONAL, true));
        finish();
    }

}
