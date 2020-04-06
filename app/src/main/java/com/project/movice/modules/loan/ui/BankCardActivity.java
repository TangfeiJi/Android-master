package com.project.movice.modules.loan.ui;

import android.content.Context;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;


import com.project.movice.R;
import com.project.movice.base.activity.BaseActivity;
import com.project.movice.dialog.ConfirmPromptDialog;
import com.project.movice.modules.loan.contract.BankContract;
import com.project.movice.modules.loan.presenter.BankPresenter;
import com.project.movice.utils.StringUtils;

import java.util.HashMap;
import java.util.regex.PatternSyntaxException;

import butterknife.BindView;
import butterknife.OnClick;

import static com.project.movice.modules.loan.ui.BaseActivity.showActivity;

/**
 * 银行卡信息
 * Created by wy on 2018/1/17 10:03.
 */


public class BankCardActivity extends BaseActivity<BankPresenter> implements View.OnTouchListener, View.OnFocusChangeListener, BankContract.View,
        TextWatcher {
    @BindView(R.id.layout)
    LinearLayout mLayout;
    @BindView(R.id.scrollview)
    ScrollView mScrollView;
    //收款人
    @BindView(R.id.name_error)
    TextView mNameError;
    @BindView(R.id.name_of_payee)
    EditText mNameOfPayee;
    //电子账户名
    @BindView(R.id.electronic_account_error)
    TextView mElectronicAccountError;
    @BindView(R.id.electronic_account_name)
    EditText mElectronicAccountName;
    //银行手机号
    @BindView(R.id.bank_card_phone_error)
    TextView mBankCardPhoneError;
    @BindView(R.id.bank_card_phone)
    EditText mBankCardPhone;
    //收款银行
    @BindView(R.id.due_bank_layout)
    RelativeLayout mDueBankLayout;
    @BindView(R.id.due_bank)
    TextView mDueBank;
    //银行卡号
    @BindView(R.id.card_number_error)
    TextView mCardNumberError;
    @BindView(R.id.bank_card_number)
    EditText mBankCardNumber;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_title)
    TextView mTitle;

    private boolean isNextPage = false; //是否下一页   true 保存后进入下一项，false 保存后返回上一级
    private int whoHasFocus = 0;


    @Override
    protected int getLayoutId() {
        return R.layout.bank_cad;
    }

    @Override
    protected void initToolbar() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        mTitle.setText(R.string.bank_card_information);
        mToolbar.setNavigationOnClickListener(v -> onBackPressedSupport());
    }


    @Override
    public void initView() {
        mScrollView.setOnTouchListener(this);
        mNameOfPayee.setOnFocusChangeListener(this);
        mElectronicAccountName.setOnFocusChangeListener(this);
        mBankCardNumber.setOnFocusChangeListener(this);
        mBankCardPhone.setOnFocusChangeListener(this);
        mElectronicAccountName.addTextChangedListener(this);
        mNameOfPayee.addTextChangedListener(this);
        mBankCardNumber.addTextChangedListener(this);
        mBankCardPhone.addTextChangedListener(this);
        mNameOfPayee.addTextChangedListener(new TextWatcher() {
            String str = "";

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String strs = mNameOfPayee.getText().toString();
                if (!stringFilter(strs.toString()) && !"".equals(strs)) {
                    mNameOfPayee.setText(str);
                    mNameOfPayee.setSelection(str.length());
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
    protected void initEventAndData() {
        mPresenter.request006(new HashMap<>());
    }

    public static boolean stringFilter(String str) throws PatternSyntaxException {
        if (null == str)
            return true;
        // 只允许字母、和空格
        String regEx = "[a-zA-Z ]+";
        return str.matches(regEx);
    }


    @OnClick({R.id.due_bank_layout, R.id.next_step})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.left_image:
                InputMethodManager imm = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                if (exitVerification()) {
                    finish();
                } else {
                    showSave();
                }
                break;
            case R.id.due_bank_layout://收款银行
                mPresenter.chooseBank(BankCardActivity.this);
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
        String name = mNameOfPayee.getText().toString();
//                String electronicAccountName = mElectronicAccountName.getText().toString();
        String bankCardPhone = mBankCardPhone.getText().toString();
        String bankName = mDueBank.getText().toString();
        String bankCardNumber = mBankCardNumber.getText().toString();
        mPresenter.submit(name, bankCardNumber, bankCardPhone, bankName, BankCardActivity.this);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        InputMethodManager imm = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        return false;
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
                    mNameError.setVisibility(View.GONE);
                }
                break;
            case 2:
                if (length > 0) {
                    mElectronicAccountError.setVisibility(View.GONE);
                }
                break;
            case 3:
                if (length > 0) {
                    mCardNumberError.setVisibility(View.GONE);
                }
            case 4:
                if (length > 0) {
                    mBankCardPhoneError.setVisibility(View.GONE);
                }
                break;
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
            case R.id.name_of_payee:
                whoHasFocus = 1;
                if (StringUtils.isEmpty(e.getText().toString())) {
                    mNameError.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.electronic_account_name:
                whoHasFocus = 2;
                if (StringUtils.isEmpty(e.getText().toString())) {
                    mElectronicAccountError.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.bank_card_number:
                whoHasFocus = 3;
                if (StringUtils.isEmpty(e.getText().toString())) {
                    mCardNumberError.setVisibility(View.VISIBLE);
                }
            case R.id.bank_card_phone:
                whoHasFocus = 4;
                if (StringUtils.isEmpty(e.getText().toString())) {
                    mBankCardPhoneError.setVisibility(View.VISIBLE);
                }
                break;
        }
    }


    private boolean exitVerification() {
        String bankName = mDueBank.getText().toString();
        String bankCardNumber = mBankCardNumber.getText().toString();
        String bankPhone = mBankCardPhone.getText().toString();
        return mPresenter.exitVerification(bankCardNumber, bankPhone, bankName);
    }

    /***
     * 提示保存
     */
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

    private void showLiving() {

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
    public void showBankName(String bankName) {
        mDueBank.setText(bankName);
    }

    @Override
    public void showBank(String userName, String bankCardNumber, String bankCardPhone, String bankName) {


        try {
            mNameOfPayee.setText(userName);//姓名
            mElectronicAccountName.setText(userName);//电子账户名
            mBankCardNumber.setText(bankCardNumber);//银行卡号
            mBankCardPhone.setText(bankCardPhone);//银行手机
            mDueBank.setText(bankName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void get016() {
        showActivity(BankCardActivity.this, ContractConfirmActivity.class);
////        EventBus.getDefault().post(new EventPersonalInformation(EventPersonalInformation.BANK, true));
//        if (isNextPage) {
////            EventBus.getDefault().post(new EventPersonalInformation(EventPersonalInformation.LIVING_BODY, false));
//            showLiving();
//        } else {
//            showLiving();
//        }

    }

}
