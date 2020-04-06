package com.project.movice.modules.mine.ui;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.project.movice.R;
import com.project.movice.base.activity.BaseActivity;
import com.project.movice.dialog.LoanAgreementDialog;
import com.project.movice.modules.mine.adapter.LoanDetailsAdapter;
import com.project.movice.modules.mine.bean.BeanLoanDetails;
import com.project.movice.modules.mine.bean.BeanMyLoan;
import com.project.movice.modules.mine.bean.BeanProcessList;
import com.project.movice.modules.mine.contract.LoadDeContract;
import com.project.movice.modules.mine.presenter.LoadDePresenter;
import com.project.movice.utils.Constant;
import com.project.movice.utils.DataUtils;
import com.project.movice.utils.DateTimeUtil;
import com.project.movice.utils.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

import static com.project.movice.core.http.api.ApiService.PROTOCAL;
import static com.project.movice.modules.loan.ui.BaseActivity.showActivity;

/**
 * 借款详情
 * Created by wy on 2018/2/1 14:54.
 */


public class LoanDetailsActivity extends BaseActivity<LoadDePresenter> implements LoadDeContract.View, View.OnClickListener {
    @BindView(R.id.loan_application)
    ViewStub mApplicationStub;
    @BindView(R.id.status_saat_ini)
    TextView mStatusSaatIni;
    @BindView(R.id.loan_successful)
    ViewStub mSuccessfulStub;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;


    TextView mTotalLoanAmount;//总金额
    TextView mLifeOfLoan;//借款期限
    TextView mActualEarnedAmount;//实际到手金额
    TextView mRepaymentAmount;//需还款金额
    TextView mBankCardNumber;//银行卡号
    //完成订单layout
    TextView mRepaymentAmount1;//需还款总金额
    TextView mPrincipal;//本金，贷款的金额
    TextView mRepaymentDeadline;//还款日
    TextView mInterest;//利息
    LinearLayout mPenaltyNameLayout;//逾期罚款layout
    TextView mPenalty;//逾期罚款
    ImageView mPertanyaanHukuman;//罚息疑问
    LinearLayout mRepaymentBankLayout;
    TextView mRepaymentBankName;//还款银行名
    TextView mRepaymentBankCardNumber;//还款卡号
    LinearLayout mEarnestMoneyLayout;//保证金layout
    TextView mEarnestMoney;//保证金
    TextView mViewAgreement;//查看协议

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_title)
    TextView mTitle;
    private List<BeanProcessList> processList = new ArrayList<>();
    private BeanLoanDetails details;
    private LoanDetailsAdapter adapter;
    private String orderId;

    @Override
    protected int getLayoutId() {
        return R.layout.loan_details;
    }

    @Override
    protected void initToolbar() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        mTitle.setText(R.string.loan_details);
        mToolbar.setNavigationOnClickListener(v -> onBackPressedSupport());
    }

    @Override
    public void initView() {
        Bundle bundle = getIntent().getExtras();
        if (null != bundle) {
            orderId = bundle.getString("orderId");
        }

        adapter = new LoanDetailsAdapter(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(adapter);


    }


    @Override
    protected void initEventAndData() {
        Map<String, String> hm = new HashMap<>();
        hm.put("orderId", orderId);
        mPresenter.request031(hm);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.view_agreement:
                if (canClick()) {
                    Bundle bundle = new Bundle();

                    String params = "?token=" + DataUtils.getToken(LoanDetailsActivity.this)
                            + "&orderId=" + orderId;
                    bundle.putString("url", PROTOCAL);
//                    bundle.putString("orderId", orderId);
                    bundle.putString("params", params);
                    LoanAgreementDialog dialog = new LoanAgreementDialog(this, bundle);
                    dialog.show();
                }
                break;
            case R.id.pertanyaan_hukuman:
                Bundle bundle1 = new Bundle();
                bundle1.putString("url", "file:///android_asset/penalty_rule.html");
                showActivity(this, BrowserActivity.class, bundle1);
                break;

        }
    }

    private void showView() {
        if (null == details)
            return;
        try {
            int orderStatus = details.getOrderStatus();
            try {
                List<BeanProcessList> list = details.getProcessList();
                mStatusSaatIni.setText(list.get(0).getDescription());
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (orderStatus == Constant.ORDERSTATUS_CANCEL || orderStatus == Constant.ORDERSTATUS_UNDER_REVIEW ||
                    orderStatus == Constant.ORDERSTATUS_AUDIT_FAILURE || orderStatus == Constant.ORDERSTATUS_APPLY_REATE ||
                    orderStatus == Constant.ORDERSTATUS_REVIEW_SUCCESS) {
                mApplicationStub.inflate();
                mTotalLoanAmount = findViewById(R.id.total_loan_amount);
                mLifeOfLoan = findViewById(R.id.life_of_loan);
                mActualEarnedAmount = findViewById(R.id.actual_earned_amount);
                mRepaymentAmount = findViewById(R.id.repayment_amount);
                mBankCardNumber = findViewById(R.id.bank_card_number);
                mViewAgreement = findViewById(R.id.view_agreement);
                mEarnestMoneyLayout = findViewById(R.id.earnest_money_layout);
                mEarnestMoney = findViewById(R.id.earnest_money);
                mViewAgreement.setOnClickListener(this);

                mViewAgreement.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
                mViewAgreement.getPaint().setAntiAlias(true);//抗锯齿
                try {
                    mTotalLoanAmount.setText(String.format(getResources().getString(R.string.borrowing_money), StringUtils.getAmount(details.getLoanAmount() + "", 3)));
                    mLifeOfLoan.setText(details.getNumberLoanDays() + " Hari");
                    mActualEarnedAmount.setText(String.format(getResources().getString(R.string.borrowing_money), StringUtils.getAmount(details.getGetTheAmount() + "", 3)));
                    mRepaymentAmount.setText(String.format(getResources().getString(R.string.borrowing_money), StringUtils.getAmount(details.getRepaymentAmount() + "", 3)));
                    mBankCardNumber.setText(StringUtils.getblank(details.getBankCardNumber()));
                    if (null != details.getEarnestMoney() && details.getEarnestMoney() > 0) {
                        mEarnestMoney.setText(String.format(getResources().getString(R.string.borrowing_money),
                                StringUtils.getAmount(details.getEarnestMoney() + "", 3)));
                    } else {
                        mEarnestMoneyLayout.setVisibility(View.GONE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                mSuccessfulStub.inflate();
                mRepaymentAmount1 = findViewById(R.id.repayment_amount);//需还款总金额
                mPrincipal = findViewById(R.id.principal);//本金，贷款的金额
                mRepaymentDeadline = findViewById(R.id.repayment_deadline);//还款日
                mInterest = findViewById(R.id.interest);//利息
                mPertanyaanHukuman = findViewById(R.id.pertanyaan_hukuman);//逾期疑问
                mPenaltyNameLayout = findViewById(R.id.penalty_name_layout);//逾期layout
                mPenalty = findViewById(R.id.penalty);//逾期罚款
                mEarnestMoneyLayout = findViewById(R.id.earnest_money_layout);
                mEarnestMoney = findViewById(R.id.earnest_money);
                mLifeOfLoan = findViewById(R.id.life_of_loan);
                mRepaymentBankLayout = findViewById(R.id.repayment_bank_layout);//还款银行layout
                mRepaymentBankName = findViewById(R.id.repayment_bank_name);//还款银行名
                mRepaymentBankCardNumber = findViewById(R.id.repayment_bank_card_number);//还款卡号
                mViewAgreement = findViewById(R.id.view_agreement);
                mPertanyaanHukuman.setOnClickListener(this);
                mViewAgreement.setOnClickListener(this);

                mViewAgreement.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
                mViewAgreement.getPaint().setAntiAlias(true);//抗锯齿
                try {
                    mRepaymentBankName.setText(details.getRepaymentBankName());
                    mLifeOfLoan.setText(details.getNumberLoanDays() + " Hari");
                    mInterest.setText(String.format(getResources().getString(R.string.borrowing_money), StringUtils.getAmount(details.getInterest() + "", 3)));
                    mRepaymentBankCardNumber.setText(StringUtils.getblank(details.getRepaymentBankCardNumber()));
                    mRepaymentAmount1.setText(String.format(getResources().getString(R.string.borrowing_money), StringUtils.getAmount(details.getRepaymentAmount() + "", 3)));
                    mPrincipal.setText(String.format(getResources().getString(R.string.principal), StringUtils.getAmount(details.getLoanAmount() + "", 3)));
                    if (null != details.getPenaltyAmount() && details.getPenaltyAmount() > 0) {
                        mPenaltyNameLayout.setVisibility(View.VISIBLE);
                        mPenalty.setText(String.format(getResources().getString(R.string.borrowing_money), StringUtils.getAmount(details.getPenaltyAmount() + "", 3)));
                    }
                    if (!StringUtils.isEmpty(details.getRepaymentBankCardNumber())) {
                        mRepaymentBankLayout.setVisibility(View.VISIBLE);
                    }
                    if (null != details.getEarnestMoney() && details.getEarnestMoney() > 0) {
                        mEarnestMoney.setText(String.format(getResources().getString(R.string.borrowing_money),
                                StringUtils.getAmount(details.getEarnestMoney() + "", 3)));
                    } else {
                        mEarnestMoneyLayout.setVisibility(View.GONE);
                    }
                    mRepaymentDeadline.setText(DateTimeUtil.formatEnDateTime(details.getRepaymentDeadline(), DateTimeUtil.EN_DF_YYYY_MM_DD));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }






    @Override
    public void get031(BeanLoanDetails response) {
        details = response;
        processList.addAll(details.getProcessList());
        adapter.resetItem(processList);
        showView();
    }
}
