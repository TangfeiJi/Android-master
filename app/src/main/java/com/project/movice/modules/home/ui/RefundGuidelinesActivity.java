package com.project.movice.modules.home.ui;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.project.movice.R;
import com.project.movice.modules.loan.ui.BaseActivity;
import com.project.movice.utils.Constant;
import com.project.movice.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 还款指引
 * Created by wy on 2018/3/7 11:51.
 */


public class RefundGuidelinesActivity extends BaseActivity {

    @BindView(R.id.content_layout)
    LinearLayout mContentLayout;
    @BindView(R.id.title)
    TextView mBankTitle;
    @BindView(R.id.description)
    TextView mDescription;
    @BindView(R.id.content)
    TextView mContent;
    @BindView(R.id.title1)
    TextView mBankTitle1;
    @BindView(R.id.content1)
    TextView mContent1;

    private String bankCode = "";//银行类型
    private String cardNumber = "";//银行卡号
    private String cardName = "";//银行名
    private int bankType;//本行还是他行（1 本行  2 他行）
    private int repaymentMethod;//还款方式(1ATM  2 本行网银/手机  3他行网银/手机)

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_title)
    TextView mTitle;
    @Override
    protected int getContentView() {
        return R.layout.guidelines;
    }

    @Override
    public void initView() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        mTitle.setText(R.string.refund_guidelines);
        mToolbar.setNavigationOnClickListener(v -> onBackPressedSupport());
    }

    @Override
    public void initBundle(Bundle bundle, Bundle savedInstanceState) {
        if (null != bundle) {
            bankCode = bundle.getString("bankCode", "");
            cardNumber = bundle.getString("cardNumber", "");
            cardName = bundle.getString("cardName", "");
            bankType = bundle.getInt("bankType", 1);
            repaymentMethod = bundle.getInt("repaymentMethod", 1);
            cardNumber = StringUtils.getblank(cardNumber);//卡号加空格
        }
    }

    @Override
    public void initData() {
        Resources r = getResources();
        if (bankCode.equalsIgnoreCase(Constant.BANK_CODE_BNI)) {//BNI银行
            if (repaymentMethod == 1) {//ATM
                if (bankType == 1) {
                    mBankTitle.setText(r.getString(R.string.bni_atm_title));
                    mDescription.setText(String.format(r.getString(R.string.repayment_assistance5), cardName, cardName));
                    mContent.setText(String.format(r.getString(R.string.bni_atm), cardNumber));
                } else {
                    mBankTitle.setText(getStyle(r.getString(R.string.other_bank_atm_title)));
                    mDescription.setText(String.format(r.getString(R.string.repayment_assistance6), cardName));
                    mContent.setText(String.format(r.getString(R.string.bni_other_bank_atm), cardNumber));
                }
            } else if (repaymentMethod == 2) {//本行网银/手机
                mDescription.setVisibility(View.GONE);
                mBankTitle.setText(r.getString(R.string.bni_internet_banking_title));
                mContent.setText(String.format(r.getString(R.string.bni_internet_banking), cardNumber));
                mBankTitle1.setText(r.getString(R.string.bni_mobile_banking_title));
                mContent1.setText(String.format(r.getString(R.string.bni_mobile_banking), cardNumber));
            } else if (repaymentMethod == 3) {//他行网银/手机
                mDescription.setVisibility(View.GONE);
                mBankTitle.setText(getStyle(r.getString(R.string.bni_others_internet_banking_title)));
                mContent.setText(String.format(r.getString(R.string.bni_other_internet_banking), cardNumber));
                mBankTitle1.setText(getStyle(r.getString(R.string.bni_others_mobile_banking_title)));
                mContent1.setText(String.format(r.getString(R.string.bni_other_mobile_banking), cardNumber));
            }
        } else if (bankCode.equalsIgnoreCase(Constant.BANK_CODE_PERMATA)) {//permata银行
            if (repaymentMethod == 1) {//ATM
                if (bankType == 1) {
                    mBankTitle.setText(r.getString(R.string.permata_atm_title));
                    mDescription.setText(String.format(r.getString(R.string.repayment_assistance5), cardName, cardName));
                    mContent.setText(String.format(r.getString(R.string.permata_atm), cardNumber));
                } else {
                    mBankTitle.setText(getStyle(r.getString(R.string.other_bank_atm_title)));
                    mDescription.setText(String.format(r.getString(R.string.repayment_assistance6), cardName));
                    mContent.setText(String.format(r.getString(R.string.permata_other_bank_atm), cardNumber));
                }
            } else if (repaymentMethod == 2) {//本行网银/手机
                mDescription.setVisibility(View.GONE);
                mBankTitle.setText(r.getString(R.string.permata_internet_banking_title));
                mContent.setText(String.format(r.getString(R.string.permata_internet_banking), cardNumber));
                mBankTitle1.setText(r.getString(R.string.permata_mobile_banking_title));
                mContent1.setText(String.format(r.getString(R.string.permata_mobile_banking), cardNumber));
            } else if (repaymentMethod == 3) {//他行网银/手机
                mDescription.setVisibility(View.GONE);
                mBankTitle.setText(getStyle(r.getString(R.string.permata_others_internet_banking_title)));
                mContent.setText(String.format(r.getString(R.string.permata_other_internet_banking), cardNumber));
                mBankTitle1.setText(getStyle(r.getString(R.string.permata_others_mobile_banking_title)));
                mContent1.setText(String.format(r.getString(R.string.permata_other_mobile_banking), cardNumber));

            }
        } else if (bankCode.equalsIgnoreCase(Constant.BANK_CODE_OTC)) {//otc便利店
            mBankTitle.setText(r.getString(R.string.alfa_store_title));
            mDescription.setVisibility(View.GONE);
            mContent.setText(String.format(r.getString(R.string.alfa_store), cardNumber));
        } else if (bankCode.equalsIgnoreCase(Constant.BANK_CODE_MANDIRI)) {//mandiri银行
            if (repaymentMethod == 1) {//ATM
                mBankTitle.setText(r.getString(R.string.mandiri_atm_title));
                mDescription.setText(String.format(r.getString(R.string.repayment_assistance5), cardName, cardName));
                mContent.setText(String.format(r.getString(R.string.mandiri_atm), cardNumber));
            } else if (repaymentMethod == 2) {//网银
                mDescription.setVisibility(View.GONE);
                mBankTitle.setText(r.getString(R.string.mandiri_internet_banking_title));
                mContent.setText(String.format(r.getString(R.string.mandiri_internet_banking), cardNumber));
                mBankTitle1.setText(r.getString(R.string.mandiri_mobile_banking_title));
                mContent1.setText(String.format(r.getString(R.string.mandiri_mobile_banking), cardNumber));
            }
        } else if(bankCode.equalsIgnoreCase(Constant.BANK_CODE_DUKOPERMATA)) {
            if (repaymentMethod == 1) {//ATM
                if (bankType == 1) {
                    mBankTitle.setText(r.getString(R.string.permata_atm_title));
                    mDescription.setText(String.format(r.getString(R.string.repayment_assistance5), cardName, cardName));
                    mContent.setText(String.format(r.getString(R.string.doku_permata_atm), cardNumber));
                }
            } else if (repaymentMethod == 2) {//本行网银/手机
                mDescription.setVisibility(View.GONE);
                mBankTitle.setText(r.getString(R.string.permata_internet_banking_title));
                mContent.setText(String.format(r.getString(R.string.doku_permata_internet_banking), cardNumber));
            }
        } else if(bankCode.equalsIgnoreCase(Constant.BANK_CODE_DUKODANAMON)) {
            if (repaymentMethod == 1) {//ATM
                if (bankType == 1) {
                    mBankTitle.setText(r.getString(R.string.danamon_atm_title));
                    mDescription.setText(String.format(r.getString(R.string.repayment_assistance5), cardName, cardName));
                    mContent.setText(String.format(r.getString(R.string.doku_danamon_atm), cardNumber));
                } else {
                    mBankTitle.setText(getStyle(r.getString(R.string.other_bank_atm_title)));
                    mDescription.setText(String.format(r.getString(R.string.repayment_assistance6), cardName));
                    mContent.setText(String.format(r.getString(R.string.doku_danamon_other_bank_atm), cardNumber));
                }
            }
        } else if(bankCode.equalsIgnoreCase(Constant.BANK_CODE_DUKOCIMB)) {
            if (repaymentMethod == 1) {//ATM
                if (bankType == 1) {
                    mBankTitle.setText(r.getString(R.string.cimb_atm_title));
                    mDescription.setText(String.format(r.getString(R.string.repayment_assistance5), cardName, cardName));
                    mContent.setText(String.format(r.getString(R.string.doku_cimb_atm), cardNumber));
                } else {
                    mBankTitle.setText(getStyle(r.getString(R.string.other_bank_atm_title)));
                    mDescription.setText(String.format(r.getString(R.string.repayment_assistance6), cardName));
                    mContent.setText(String.format(r.getString(R.string.doku_cimb_other_bank_atm), cardNumber));
                }
            } else if (repaymentMethod == 2) {//本行网银/手机
                mDescription.setVisibility(View.GONE);
                mBankTitle.setText(r.getString(R.string.cimb_internet_banking_title));
                mContent.setText(String.format(r.getString(R.string.doku_cimb_internet_banking), cardNumber));
                mBankTitle1.setText(r.getString(R.string.cimb_mobile_banking_title));
                mContent1.setText(String.format(r.getString(R.string.doku_cimb_mobile_banking), cardNumber));
            }
        } else if(bankCode.equalsIgnoreCase(Constant.BANK_CODE_DUKOOTC)) {
            mBankTitle.setText(r.getString(R.string.alfa_store_title));
            mDescription.setVisibility(View.GONE);
            mContent.setText(String.format(r.getString(R.string.alfa_store), cardNumber));
        }


    }

    /**
     * 2018-06-08至2018-06-20之间不能用BCA还款
     *
     * @param title
     * @return
     */
    private SpannableStringBuilder getStyle(String title) {
        SpannableStringBuilder style = new SpannableStringBuilder(title);
        return style;
    }

    ;



}
