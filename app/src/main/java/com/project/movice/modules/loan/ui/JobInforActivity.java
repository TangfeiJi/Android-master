package com.project.movice.modules.loan.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
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
import com.project.movice.modules.loan.bean.BeanJob;
import com.project.movice.modules.loan.contract.JobInforContract;
import com.project.movice.modules.loan.presenter.JobInforPresenter;
import com.project.movice.utils.ComUtils;
import com.project.movice.utils.Constant;
import com.project.movice.utils.ConstantParams;
import com.project.movice.utils.GlideImageLoader;
import com.project.movice.utils.StringUtils;
import com.project.movice.utils.ToastUtils;
import com.project.movice.widget.behavior.PackPayDayDialog;
import com.project.movice.widget.behavior.SelectOptions;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.greenrobot.eventbus.EventBus;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

import static com.project.movice.modules.loan.ui.BaseActivity.showActivityForResult;

/**
 * 工作信息
 * Created by wy on 2018/1/16 19:27.
 */
public class JobInforActivity extends BaseActivity<JobInforPresenter> implements View.OnTouchListener, View.OnFocusChangeListener, TextWatcher, JobInforContract.View {

    @BindView(R.id.scrollview)
    ScrollView mScrollView;

    @BindView(R.id.card_number_error)
    TextView mCardNumberError;
    @BindView(R.id.steuerkarte_card_number)
    EditText mSteuerkarteCardNumber;//税卡号
    //职业
    @BindView(R.id.job_layout)
    RelativeLayout mJobLayout;
    @BindView(R.id.job)
    TextView mJob;
    //工作年限
    @BindView(R.id.working_seniority_layout)
    RelativeLayout mWorkingSeniorityLayout;
    @BindView(R.id.working_seniority)
    TextView mWorkingSeniority;
    //收入水平
    @BindView(R.id.level_range_layout)
    RelativeLayout mLevelRangeLayout;
    @BindView(R.id.level_range)
    TextView mLevelRange;
    //发薪日
    @BindView(R.id.pay_day_layout)
    RelativeLayout mPayDayLayout;
    @BindView(R.id.pay_day)
    TextView mPayDay;
    //公司名称
    @BindView(R.id.company_name_error)
    TextView mCompanyNameError;
    @BindView(R.id.company_name)
    EditText mCompanyName;
    //公司电话
    @BindView(R.id.company_tel_error)
    TextView mCompanyTelError;
    @BindView(R.id.company_tel)
    EditText mCompanyTel;
    //公司地址
    @BindView(R.id.company_address_layout)
    RelativeLayout mCompanyAddressLayout;
    @BindView(R.id.company_address)
    TextView mCompanyAddress;
    //详细公司地址
    @BindView(R.id.address_error)
    TextView mAddressError;
    @BindView(R.id.detailed_address)
    EditText mDetailedAddress;
    //工作照
    @BindView(R.id.job_card_layout)
    RelativeLayout mJobCardLayout;
    @BindView(R.id.job_card_image)
    ImageView mJobCardImage;
    @BindView(R.id.job_card)
    TextView mJobCard;
    @BindView(R.id.job_card_status)
    TextView mJobCardStatus;
    //工资条
    @BindView(R.id.salary_sheet_layout)
    RelativeLayout mSalarySheetLayout;
    @BindView(R.id.salary_sheet_image)
    ImageView mSalarySheetImage;
    @BindView(R.id.salary_sheet_text)
    TextView mSalarySheetText;
    @BindView(R.id.salary_sheet_status)
    TextView mSalarySheetStatus;
    //税卡照
    @BindView(R.id.tax_card_photo_layout)
    RelativeLayout mTaxCardPhotoLayout;
    @BindView(R.id.tax_card_photo_image)
    ImageView mTaxCardImage;
    @BindView(R.id.tax_card_photo_text)
    TextView mTaxCardPhotoText;
    @BindView(R.id.tax_card_photo_status)
    TextView mTaxCardPhotoStatus;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_title)
    TextView mTitle;

    private boolean isNextPage = false; //是否下一页   true 保存后进入下一项，false 保存后返回上一级

    private String province = "";//省
    private String city = "";//市
    private String area = "";//区
    private String street = "";//街道
    private String imagePath;
    private int whoHasFocus = 0;//记录是那个EditText
    private Map<String, String> mapImg = new HashMap<>();


    @Override
    public void initView() {
        mScrollView.setOnTouchListener(this);

        mCompanyName.setOnFocusChangeListener(this);
        mCompanyTel.setOnFocusChangeListener(this);
        mDetailedAddress.setOnFocusChangeListener(this);
        mSteuerkarteCardNumber.setOnFocusChangeListener(this);
        mCompanyName.addTextChangedListener(this);
        mCompanyTel.addTextChangedListener(this);
        mDetailedAddress.addTextChangedListener(this);
        mSteuerkarteCardNumber.addTextChangedListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.job_information;
    }

    @Override
    protected void initToolbar() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        mTitle.setText(R.string.job_information);
        mToolbar.setNavigationOnClickListener(v -> onBackPressedSupport());
    }

    @Override
    protected void initEventAndData() {
        mPresenter.request007(new HashMap<>());
    }


    @SuppressLint("StringFormatMatches")
    @OnClick({R.id.job_layout, R.id.level_range_layout, R.id.pay_day_layout, R.id.company_address_layout,
            R.id.working_seniority_layout, R.id.job_card_layout, R.id.salary_sheet_layout, R.id.tax_card_photo_layout,
            R.id.job_card_image, R.id.salary_sheet_image, R.id.tax_card_photo_image, R.id.next_step})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.job_layout://职业
                final String[] job_array = getResources().getStringArray(R.array.job);
                ListDialog jobDialog = new ListDialog(this, job_array);
                jobDialog.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        isShow(mJob, job_array[position]);
                    }
                });
                jobDialog.show();
                break;
            case R.id.working_seniority_layout://工作年限
                final String[] ws_array = getResources().getStringArray(R.array.working_seniority);
                ListDialog wsDialog = new ListDialog(this, ws_array);
                wsDialog.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        isShow(mWorkingSeniority, ws_array[position]);
                    }
                });
                wsDialog.show();
                break;
            case R.id.level_range_layout://收入水平
                final String[] incomeLevelArray = getResources().getStringArray(R.array.income_level);
                ListDialog incomeLevelDialog = new ListDialog(this, incomeLevelArray);
                incomeLevelDialog.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        isShow(mLevelRange, incomeLevelArray[position]);
                    }
                });
                incomeLevelDialog.show();
                break;
            case R.id.pay_day_layout://发薪日
                PackPayDayDialog pp = new PackPayDayDialog(this);
                pp.setOnSelectedChangeListener(new PackPayDayDialog.onSelectedChangeListener() {
                    @Override
                    public void onSelected(Object leftValue, Object rightValue) {
                        isShow(mPayDay, (String) leftValue + "  " + rightValue);
                    }
                });
                pp.show();
                break;
            case R.id.company_address_layout://公司地址
                showActivityForResult(this, AreaSelectActivity.class, 100);
                break;
            case R.id.job_card_layout://工作照

                mPresenter.requestPermissions(new RxPermissions(JobInforActivity.this), 0);


                break;
            case R.id.salary_sheet_layout://工资条

                mPresenter.requestPermissions(new RxPermissions(JobInforActivity.this), 1);


                break;
            case R.id.tax_card_photo_layout://税卡

                mPresenter.requestPermissions(new RxPermissions(JobInforActivity.this), 2);

                break;
            case R.id.salary_sheet_image:
            case R.id.tax_card_photo_image:
            case R.id.job_card_image:
                ImageGalleryActivity.show(this, new String[]{mapImg.get(v.getId() + "")}, 0, false);
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
        String occupation = mJob.getText().toString();
        String workingSeniority = mWorkingSeniority.getText().toString();
        String incomeLevel = mLevelRange.getText().toString();
        String payDate = mPayDay.getText().toString();
        String companyName = mCompanyName.getText().toString();
        String companyPhone = mCompanyTel.getText().toString();
        String companyAddress = mCompanyAddress.getText().toString();
        String detailedAddress = mDetailedAddress.getText().toString();
        String cardNumber = mSteuerkarteCardNumber.getText().toString();
        if (StringUtils.isEmpty((String) mJobCardStatus.getTag())) {
            ToastUtils.showToast(JobInforActivity.this, getResources().getString(R.string.please_take_a_picture_of_your_work_card));
            return;
        }
        if (StringUtils.isEmpty((String) mSalarySheetStatus.getTag())) {

            ToastUtils.showToast(JobInforActivity.this, getResources().getString(R.string.please_take_a_picture_of_your_salary));
            return;
        }

        //        税卡照片 和税卡卡号选填
//        if (StringUtils.isEmpty((String) mTaxCardPhotoStatus.getTag())) {
//            ToastUtil.showShort(R.string.please_take_a_picture_of_your_tax_card);
//            return;
//        }
//        if (StringUtils.isEmpty(cardNumber)) {
//            ToastUtil.showShort(R.string.fill_in_your_tax_card_number);
//            return;
//        }
        if (StringUtils.isEmpty(incomeLevel)) {


            ToastUtils.showToast(JobInforActivity.this, getResources().getString(R.string.please_select_your_income_level_range));
            return;
        }
        if (StringUtils.isEmpty(payDate)) {

            ToastUtils.showToast(JobInforActivity.this, getResources().getString(R.string.please_select_your_discovery_cycle));
            return;
        }
        if (StringUtils.isEmpty(occupation)) {

            ToastUtils.showToast(JobInforActivity.this, getResources().getString(R.string.please_choose_your_career));
            return;
        }
        if (StringUtils.isEmpty(workingSeniority)) {

            ToastUtils.showToast(JobInforActivity.this, getResources().getString(R.string.please_select_working_seniority));
            return;
        }
        if (StringUtils.isEmpty(companyPhone)) {

            ToastUtils.showToast(JobInforActivity.this, getResources().getString(R.string.please_fill_in_your_office_telephone_number));
            return;
        }
        if (StringUtils.isEmpty(companyName)) {

            ToastUtils.showToast(JobInforActivity.this, getResources().getString(R.string.please_fill_in_the_full_name_of_your_company));
            return;
        }
        if (StringUtils.isEmpty(companyAddress)) {


            ToastUtils.showToast(JobInforActivity.this, getResources().getString(R.string.please_select_the_area_of_your_company));
            return;
        }
        if (StringUtils.isEmpty(detailedAddress)) {

            ToastUtils.showToast(JobInforActivity.this, getResources().getString(R.string.please_enter_your_company_detailed_office_address));
            return;
        }
        Map<String, String> params = new HashMap<>();
        params.put("taxCardNumber", cardNumber);
        params.put("occupation", ConstantParams.getOccupation(true, occupation));
        params.put("workingSeniority", ConstantParams.getWorkingSeniority(true, workingSeniority));
        params.put("incomeLevel", ConstantParams.getIncomeLevel(true, incomeLevel));
        params.put("payDate", payDate);
        params.put("companyName", companyName);
        params.put("companyPhone", companyPhone);
        params.put("province", province);
        params.put("city", city);
        params.put("area", area);
        params.put("street", street);
        params.put("detailedAddress", detailedAddress);
        params.put("workingPicture", (String) mJobCardStatus.getTag());
//        params.put("taxCardPhoto", (String) mTaxCardPhotoStatus.getTag());
        params.put("payroll", (String) mSalarySheetStatus.getTag());
        mPresenter.request013(params);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        InputMethodManager imm = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        return false;
    }

    private void isShow(TextView text, String str) {
        try {
            if (!StringUtils.isEmpty(str)) {
                text.setText(str);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getMonth(int i) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH, i);//当前时间前去一个月，即一个月前的时间
        return calendar.get(Calendar.MONTH) + 1 + "";
    }


    /**
     * 显示KTP图片
     *
     * @param path
     */
    private void showImage(String path, ImageView iv, TextView text, TextView status) {
        if (!StringUtils.isEmpty(path)) {

            GlideImageLoader.load(JobInforActivity.this, path, iv);
            status.setText(getResources().getString(R.string.reselect));
            status.setTextColor(ContextCompat.getColor(this, R.color.auxiliary));
            status.setTag(path);
            text.setVisibility(View.GONE);
            iv.setVisibility(View.VISIBLE);
            mapImg.put(iv.getId() + "", path);
        } else {
            status.setText("");
            text.setVisibility(View.VISIBLE);
            iv.setVisibility(View.GONE);
        }
    }


    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        int length = charSequence.length();
        if (length > 0) {
            switch (whoHasFocus) {
                case 1:
                    mCompanyNameError.setVisibility(View.GONE);
                    break;
                case 2:
                    mCompanyTelError.setVisibility(View.GONE);
                    break;
                case 3:
                    mAddressError.setVisibility(View.GONE);
                    break;
                case 4:
                    mCardNumberError.setVisibility(View.GONE);
                    break;
            }
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    public void onFocusChange(View view, boolean b) {
        if (b)
            return;
        EditText e = (EditText) view;
        switch (view.getId()) {
            case R.id.company_name:
                whoHasFocus = 1;
                if (StringUtils.isEmpty(e.getText().toString())) {
                    mCompanyNameError.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.company_tel:
                whoHasFocus = 2;
                if (StringUtils.isEmpty(e.getText().toString())) {
                    mCompanyTelError.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.detailed_address:
                whoHasFocus = 3;
                if (StringUtils.isEmpty(e.getText().toString())) {
                    mAddressError.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.steuerkarte_card_number:
                whoHasFocus = 4;
                if (StringUtils.isEmpty(e.getText().toString())) {
                    mCardNumberError.setVisibility(View.VISIBLE);
                }
                break;
        }
    }


    private boolean exitVerification() {
        String occupation = mJob.getText().toString();
        String workingSeniority = mWorkingSeniority.getText().toString();
        String incomeLevel = mLevelRange.getText().toString();
        String payDate = mPayDay.getText().toString();
        String companyName = mCompanyName.getText().toString();
        String companyPhone = mCompanyTel.getText().toString();
        String detailedAddress = mDetailedAddress.getText().toString();
        String cardNumber = mSteuerkarteCardNumber.getText().toString();
        BeanJob info = new BeanJob();
        info.setOccupation(ConstantParams.getOccupation(true, occupation));
        info.setWorkingSeniority(ConstantParams.getWorkingSeniority(true, workingSeniority));
        info.setIncomeLevel(ConstantParams.getIncomeLevel(true, incomeLevel));
        info.setPayDate(payDate);
        info.setCompanyName(companyName);
        info.setCompanyPhone(companyPhone);
        info.setWorkingPicture((String) mJobCardStatus.getTag());
        info.setTaxCardPhoto((String) mTaxCardPhotoStatus.getTag());
        info.setPayroll((String) mSalarySheetStatus.getTag());
        info.setProvince(province);
        info.setCity(city);
        info.setArea(area);
        info.setStreet(street);
        info.setTaxCardNumber(cardNumber);
        info.setDetailedAddress(detailedAddress);
        return mPresenter.exitVerification(info);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
            final Map map = (Map) data.getSerializableExtra("addressInfo");
            province = (String) map.get("provName");
            city = (String) map.get("cityName");
            area = (String) map.get("districtName");
            street = (String) map.get("villName");
            isShow(mCompanyAddress, province + " " + city + " " + area + " " + street);
        }
    }


    private void showTips(int image, String title, String content, String bt, boolean isLeft,
                          final ImageView iv, final TextView text, final TextView status) {
        NoRepaymentDialog d = new NoRepaymentDialog(this, image, title,
                content, bt, isLeft);
        d.setOnClickListener(new NoRepaymentDialog.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelectOptions mOption = new SelectOptions.Builder()
                        .setSelectCount(1)
                        .setOpenCamera(true)
                        .setHasCam(true)
                        .setCameraType(2)
                        .setPhotoType(SelectOptions.TYPE_PORTRAIT)
                        .setCallback(new SelectOptions.Callback() {
                            @Override
                            public void doSelected(String[] images) {
                                showImage(images[0], iv, text, status);
                                if (iv.getId() == mTaxCardImage.getId()) {//税卡
                                    Map<String, String> hm = new HashMap<>();
                                    hm.put("type", Constant.IMAGE_3);
                                    mPresenter.request027((images[0]), hm);
                                } else if (iv.getId() == mSalarySheetImage.getId()) {//工资条
                                    Map<String, String> hm = new HashMap<>();
                                    hm.put("type", Constant.IMAGE_4);
                                    mPresenter.request027((images[0]), hm);
                                }
                            }
                        }).build();
                SelectImageActivity.show(JobInforActivity.this, mOption);
            }
        });
        d.show();
    }


    /**
     * 工位照提醒
     */
    private void isStationPrompt() {
        ConfirmPromptDialog confirm = new ConfirmPromptDialog(this,
                R.mipmap.lingyi5,
                getResources().getString(R.string.is_it_at_the_station),
                getResources().getString(R.string.no),
                getResources().getString(R.string.yes));
        confirm.setOnLeftClickListener(new ConfirmPromptDialog.OnClickListener() {
            @Override
            public void onClick() {
                showStationPrompt(2);
            }
        });
        confirm.setOnClickListener(new ConfirmPromptDialog.OnClickListener() {
            @Override
            public void onClick() {
                showStationPrompt(1);
            }
        });
        confirm.show();
    }

    /**
     * 是否在工位
     *
     * @param status 1在  2不在
     */
    private void showStationPrompt(final int status) {
        int image = R.mipmap.lingyi38;
        String title = null;
        String content = null;
        String bt = null;
        if (status == 1) {
            image = R.mipmap.uangprso142;
            title = getResources().getString(R.string.please_wear_a_badge);
            content = getResources().getString(R.string.station_photo_will_speed_up_the_review);
            bt = getResources().getString(R.string.start_taking_photos);
        } else {
            image = R.mipmap.lingyi38;
            title = getResources().getString(R.string.please_photograph_your_work_card);
            content = getResources().getString(R.string.shooting_the_worker_hint_content);
            bt = getResources().getString(R.string.start_taking_photos);
        }
        NoRepaymentDialog d = new NoRepaymentDialog(this, image, title, content, bt, true);
        d.setOnClickListener(new NoRepaymentDialog.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelectOptions.Builder mOption = new SelectOptions.Builder().setSelectCount(1);
                mOption.setSelectCount(1);
                mOption.setOpenCamera(true);
                mOption.setHasCam(true);
                mOption.setCameraType(2);
                if (status != 2) {
                    mOption.setPhotoType(SelectOptions.TYPE_FOTO_STASIUN);
                } else {
                    mOption.setPhotoType(SelectOptions.TYPE_PORTRAIT);
                }
                SelectOptions so = mOption.setCallback(new SelectOptions.Callback() {
                    @Override
                    public void doSelected(String[] images) {
                        showImage(images[0], mJobCardImage, mJobCard, mJobCardStatus);
                        Map<String,String> hm=new HashMap<>();
                        hm.put("type",Constant.IMAGE_JOB);
                        mPresenter.request027(mJobCardStatus.getTag().toString(), hm);
                    }
                }).build();
                SelectImageActivity.show(JobInforActivity.this, so);
            }
        });
        d.show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (exitVerification()) {
                finish();
            } else {
                showSave();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void get027(String type) {
//        税卡
        if (type.equals(Constant.IMAGE_3)) {

        } else if (type.equals(Constant.IMAGE_JOB)) {

        } else if (type.equals(Constant.IMAGE_4)) {

        }
    }

    @Override
    public void get007(BeanJob job) {
        try {
            mCompanyName.setText(job.getCompanyName());
            mCompanyTel.setText(job.getCompanyPhone());
            mSteuerkarteCardNumber.setText(job.getTaxCardNumber());
            isShow(mJob, ConstantParams.getOccupation(false, job.getOccupation()));
            isShow(mWorkingSeniority, ConstantParams.getWorkingSeniority(false, job.getWorkingSeniority()));
            isShow(mLevelRange, ConstantParams.getIncomeLevel(false, job.getIncomeLevel()));
            isShow(mPayDay, job.getPayDate());
            this.province = job.getProvince();
            this.city = job.getCity();
            this.area = job.getArea();
            this.street = job.getStreet();
            isShow(mCompanyAddress, province + " " + city + " " + area + " " + street);
            mDetailedAddress.setText(job.getDetailedAddress());
            if (!StringUtils.isEmpty(job.getWorkingPicture())) {
                showImage(job.getImageDomain() + job.getWorkingPicture(), mJobCardImage, mJobCard, mJobCardStatus);
            }
            if (!StringUtils.isEmpty(job.getPayroll())) {
                showImage(job.getImageDomain() + job.getPayroll(), mSalarySheetImage, mSalarySheetText, mSalarySheetStatus);
            }
            if (!StringUtils.isEmpty(job.getTaxCardPhoto())) {
                showImage(job.getImageDomain() + job.getTaxCardPhoto(), mTaxCardImage, mTaxCardPhotoText, mTaxCardPhotoStatus);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void get013() {
        EventBus.getDefault().post(new EventPersonalInformation(EventPersonalInformation.JOB, true));
        finish();
    }

    @Override
    public void permissionsSuccess(int type) {
        switch (type) {
            case 0:
                isStationPrompt();
                break;
            case 1:
                String content = String.format(getResources().getString(R.string.shooting_the_payroll_card_hint_content),
                        getMonth(0), getMonth(-1));
                showTips(R.mipmap.lingyi43, getResources().getString(R.string.are_recognized_title),
                        content, getResources().getString(R.string.start_taking_photos),
                        true, mSalarySheetImage, mSalarySheetText, mSalarySheetStatus);
                break;
            case 2:
                showTips(R.mipmap.tax_c1ard, getResources().getString(R.string.are_recognized_title),
                        getResources().getString(R.string.shooting_the_lohnsteuerkarte_hint_content),
                        getResources().getString(R.string.start_taking_photos),
                        true, mTaxCardImage, mTaxCardPhotoText, mTaxCardPhotoStatus);
                break;
        }

    }

    @Override
    public void permissionsCancle() {
        ComUtils.showPermissions(getResources().getString(R.string.permission_camera), JobInforActivity.this);
    }
}
