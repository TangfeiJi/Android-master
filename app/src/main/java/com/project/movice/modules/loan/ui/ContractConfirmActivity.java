package com.project.movice.modules.loan.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;


import com.project.movice.R;
import com.project.movice.application.MoviceApp;
import com.project.movice.core.event.EventPersonalInformation;
import com.project.movice.dialog.ListDialog;
import com.project.movice.dialog.LoanAgreementDialog;
import com.project.movice.dialog.OnItemClickListener;
import com.project.movice.modules.drawname.DrawNameActivity;
import com.project.movice.modules.loan.bean.BaseAmountCalculation;
import com.project.movice.modules.loan.bean.BeanBankCard;
import com.project.movice.modules.loan.bean.BeanPersonalInformation;
import com.project.movice.modules.loan.bean.BorrowingInformation;
import com.project.movice.modules.loan.contract.ContractContract;
import com.project.movice.modules.loan.presenter.ContractPresenter;
import com.project.movice.modules.main.ui.activity.MainActivity;
import com.project.movice.utils.Arith;
import com.project.movice.utils.ComUtils;
import com.project.movice.utils.EventBusType;
import com.project.movice.utils.MessageEvent;
import com.project.movice.utils.StringUtils;
import com.project.movice.widget.behavior.AmountCompositionDialog;
import com.project.movice.widget.behavior.BottomWebView;
import com.project.movice.widget.behavior.EventCoupon;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.webkit.WebView.enableSlowWholeDocumentDraw;
import static com.project.movice.core.http.api.ApiService.PROTOCAL;
import static com.project.movice.modules.loan.ui.BaseActivity.showActivity;

/**
 * 借款合同确认
 * Created by wy on 2018/1/17 15:47.
 */


public class ContractConfirmActivity extends com.project.movice.base.activity.BaseActivity<ContractPresenter> implements ContractContract.View {
    @BindView(R.id.scrollview)
    ScrollView mScrollView;

    @BindView(R.id.total_loan_amount)
    TextView mTotalLoanAmount;//总金额
    @BindView(R.id.life_of_loan)
    TextView mLifeOfLoan;//借款期限
    @BindView(R.id.actual_earned_amount)
    TextView mActualEarnedAmount;//实际到手金额
    @BindView(R.id.repayment_amount)
    TextView mRepaymentAmount;//需还款金额
    @BindView(R.id.bank_card_number)
    TextView mBankCardNumber;//银行卡号
    @BindView(R.id.earnest_money_layout)
    LinearLayout mEarnestMoneyLayout;
    @BindView(R.id.earnest_money)
    TextView mEarnestMoney;//保证金
    @BindView(R.id.agree_text)
    TextView mAgreeText;//协议文字
    @BindView(R.id.agree)
    ImageView mAgree;
    @BindView(R.id.i_agree)
    Button mIAgree;//我同意
    @BindView(R.id.img_one)
    ImageView imgOne;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.img_two)
    ImageView imgTwo;


    @BindView(R.id.llContent)
    LinearLayout llContent;

    @BindView(R.id.llNext)
    LinearLayout llNext;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_title)
    TextView mTitle;

    private String period;
    private int loanDays;
    private float rate;
    private long interest;
    private long borrowingMoney;
    private long managementFee;
    private String couponId = "";
    private long earnestMoney;
    @BindView(R.id.borrowing_purpose)
    TextView mBorrowingPurpose;
    @BindView(R.id.webview)
    BottomWebView mWebView;
    private String creatTime;

    @BindView(R.id.llDays)
    LinearLayout llDays;

    @Override
    protected int getLayoutId() {
        return R.layout.contract_confirm;
    }

    @Override
    public void initView() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            mWebView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        initData();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddhhmmss");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        creatTime = formatter.format(curDate);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setAllowFileAccess(true);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            CookieManager.getInstance().setAcceptThirdPartyCookies(mWebView, true);
        }

        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mWebView.scrollToBottom();
            }
        });

    }



    @Override
    protected void initToolbar() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        mTitle.setText(R.string.confirmation_of_loan_contract);
        mToolbar.setNavigationOnClickListener(v -> onBackPressedSupport());
    }

    @Override
    protected void initEventAndData() {

    }

    public void initData() {
        whetherCanRob();
        mPresenter.request006(new HashMap<>(),this);
        mAgree.setSelected(true);
        imgOne.setSelected(true);
        imgTwo.setSelected(true);
        try {
            String agreement = getResources().getString(R.string.consent_minirupiah_loan_agreement);
            Locale locale = Locale.getDefault();
            int start = 0;
            int end = 0;
            if (null != locale && !locale.getCountry().equalsIgnoreCase("CN")) {
                start = 16;
                end = agreement.length();
            } else {
                start = 3;
                end = agreement.length();
            }
            SpannableStringBuilder style = new SpannableStringBuilder(agreement);
            style.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.character_and_title)),
                    start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            style.setSpan(new UnderlineSpan(), start, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            mAgreeText.setText(style);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private boolean isSelect = false;

    @OnClick({R.id.select_purpose,R.id.llDays, R.id.btNext, R.id.i_agree, R.id.agree, R.id.agree_text, R.id.actual_earned_amount_question, R.id.img_one, R.id.img_two, R.id.imgDraw, R.id.imgClose})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.select_purpose:
                final String[] array = getResources().getStringArray(R.array.borrowing_purposes);
                ListDialog ld = new ListDialog(ContractConfirmActivity.this, array);
                ld.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        mBorrowingPurpose.setText(array[position]);
                        isSelect = true;
                        whetherCanRob();
                    }
                });
                ld.show();
                break;
            case R.id.agree://协议
                if (mAgree.isSelected()) {
                    mAgree.setSelected(false);
                } else {
                    mAgree.setSelected(true);
                }
                whetherCanRob();
                break;
            case R.id.agree_text://协议
                if (canClick()) {
                    mPresenter.showLoanAgreement(true);
                }
                break;
            case R.id.actual_earned_amount_question:
                double reat = Arith.round(Arith.mul(rate, 100), 1);
                AmountCompositionDialog ac = new AmountCompositionDialog(ContractConfirmActivity.this,
                        reat + "", interest, borrowingMoney + "", managementFee, earnestMoney);
                ac.show();
                break;
            case R.id.i_agree://确认
                llContent.setVisibility(View.VISIBLE);
                break;
            case R.id.img_one:
                if (imgOne.isSelected()) {
                    imgOne.setSelected(false);
                } else {
                    imgOne.setSelected(true);
                }
                whetherCanRob();
                break;
            case R.id.img_two:
                if (imgTwo.isSelected()) {
                    imgTwo.setSelected(false);
                } else {
                    imgTwo.setSelected(true);
                }
                whetherCanRob();
                break;

            case R.id.imgDraw:
                llContent.setVisibility(View.GONE);
                startActivityForResult(new Intent(ContractConfirmActivity.this, DrawNameActivity.class), 111);
                break;
            case R.id.imgClose:
                llContent.setVisibility(View.GONE);
                break;
            case R.id.btNext:
                mPresenter.requestPermissions(new RxPermissions(this));
                break;
            case R.id.llDays:
                final String[] marital_array = getResources().getStringArray(R.array.loan_days);
                ListDialog maritalDialog = new ListDialog(this, marital_array,1);
                maritalDialog.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                    }
                });
                maritalDialog.show();
                break;

        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 111:
                if (resultCode == Activity.RESULT_OK) {
                    mPresenter.showLoanAgreement(false);
                } else {
                    Log.i("onactivityresultdemo", "i blabalabal");
                }
                break;
            default:
                break;
        }
    }

    /**
     * 判断所有填都有
     */
    private void whetherCanRob() {
        if (mAgree.isSelected() & imgOne.isSelected() & imgTwo.isSelected() & isSelect) {
            mIAgree.setEnabled(true);
        } else {
            mIAgree.setEnabled(false);
        }

    }







    BeanPersonalInformation persion;

    @Override
    public void loanProtocol(BeanPersonalInformation info, BeanBankCard bank, BorrowingInformation loan, boolean isShow) {
        Bundle bundle = new Bundle();
        Calendar calendar = Calendar.getInstance();  //获取当前时间，作为图标的名字
        Calendar calendar_en = Calendar.getInstance(Locale.ENGLISH);  //获取当前时间，作为图标的名字

        persion = info;
        String params = "?applicationTime=" + System.currentTimeMillis()
//                + "&month=" + month
//                + "&day=" + day
                + "&name=" + info.getName()
                + "&uid=" + info.getUid()
//                + "&uuId=" + info.
                + "&ktpNumber=" + info.getKtpNumber()
                + "&loanAmount=" + loan.getBorrowingMoney()
//                + "&token=" + loan.getBorrowingMoney()
//                + "&orderId=" + loan.getBorrowingMoney()
                + "&earnestMoney=" + earnestMoney;
        bundle.putString("url", PROTOCAL);
        bundle.putString("params", params);
        if (isShow) {
            LoanAgreementDialog dialog = new LoanAgreementDialog(this, bundle);
            dialog.show();
        } else {
            llNext.setVisibility(View.VISIBLE);
            mWebView.loadUrl(PROTOCAL + params);
        }

    }



    @Override
    public String getSelect() {
        return mBorrowingPurpose.getText().toString().trim();
    }

    @Override
    public void finishActivity() {
        EventBus.getDefault().post(new MessageEvent(EventBusType.UPDATELOAN));//
        EventBus.getDefault().post(new EventPersonalInformation(EventPersonalInformation.JUMPTWO, true));
        showActivity(ContractConfirmActivity.this, MainActivity.class);
        MoviceApp.loanClick = false;
        finish();
    }


    @Override
    public void showInfo(String bankCardNumber, String userName, int loanDays, long borrowingMoney, String getMoney, String payBackMoney, float rate, long managementFee) {
        try {
            this.rate = rate;
            mBankCardNumber.setText(bankCardNumber);
            showMoney();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showMoney() {

        BaseAmountCalculation amout = mPresenter.getMoney();
        this.loanDays = amout.getLoanPeriod();
        this.interest = amout.getInterest();
        this.borrowingMoney = amout.getLoanAmount();
        this.managementFee = amout.getManagementFee();
        this.earnestMoney = amout.getEarnestMoney();
        this.period = amout.getPeriod();


        mTotalLoanAmount.setText(String.format(getResources().getString(R.string.borrowing_money),
                StringUtils.getAmount(borrowingMoney + "", 3)));//借款总金额

        tvMoney.setText(getResources().getString(R.string.genjunindezizhi) + StringUtils.getAmount(borrowingMoney + "", 3));

        mLifeOfLoan.setText(amout.getLoanPeriod() + " Hari");//借款期限
        mActualEarnedAmount.setText(String.format(getResources().getString(R.string.borrowing_money),
                StringUtils.getAmount(amout.getGetTheAmount() + "", 3)));//实际到手金额
        mRepaymentAmount.setText(String.format(getResources().getString(R.string.borrowing_money),
                StringUtils.getAmount(amout.getRepaymentAmount() + "", 3)));//需还款金额

        if (amout.getEarnestMoney() <= 0) {
            mEarnestMoneyLayout.setVisibility(View.GONE);
        } else {
            mEarnestMoneyLayout.setVisibility(View.VISIBLE);
            mEarnestMoney.setText(String.format(getResources().getString(R.string.borrowing_money),
                    StringUtils.getAmount(amout.getEarnestMoney() + "", 3)));
        }


    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventCoupon info) {
        if (null != info) {
            couponId = info.getCouponId();
//            showLoading();
//            Map<String, String> params = new HashMap<>();
//            params.put("money", borrowingInformation.getBorrowingMoney() + "");
//            params.put("period", borrowingInformation.getBorrowingTimeLimit() + "");
//            params.put("couponId", couponId);
//            mPresenter.calculation(true, couponId);
        }
    }





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }



    @Override
    public void get006(BeanPersonalInformation data) {

    }


    @Override
    public void permissionsSuccess() {
        mPresenter.showAgree();
    }

    @Override
    public void permissionsCancle() {
        ComUtils.showPermissions(getResources().getString(R.string.permission_contacts)+","+getResources().getString(R.string.permission_location), ContractConfirmActivity.this);
    }
}
