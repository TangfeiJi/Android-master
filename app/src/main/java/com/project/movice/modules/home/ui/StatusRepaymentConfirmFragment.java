package com.project.movice.modules.home.ui;

import android.animation.ObjectAnimator;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.project.movice.R;
import com.project.movice.dialog.BcaPromptDialog;
import com.project.movice.modules.home.base.BaseFragment;
import com.project.movice.modules.home.bean.BeanLoanProgress;
import com.project.movice.modules.mine.ui.LoanDetailsActivity;
import com.project.movice.utils.Constant;
import com.project.movice.utils.DataUtils;
import com.project.movice.utils.DateTimeUtil;
import com.project.movice.utils.EventBusType;
import com.project.movice.utils.GlideImageLoader;
import com.project.movice.utils.MessageEvent;
import com.project.movice.utils.StringUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.Locale;

import anim.OrderStatusAnimator;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * 还款信息显示
 * Created by wy on 2018/4/19 14:44.
 */


public class StatusRepaymentConfirmFragment extends BaseFragment {

    @BindView(R.id.check_cancel)
    ImageView mCheckCancel;
    @BindView(R.id.back)
    ImageView mBack;
    @BindView(R.id.bank_logo)
    ImageView mBankLogo;
    @BindView(R.id.bank_card_number)
    TextView mBankCardNumber;
    @BindView(R.id.virtual_account_valid_until)
    TextView mVirtualAccountValidUntil;
    @BindView(R.id.repayment_amount)
    TextView mAmount;
    @BindView(R.id.doubt)
    TextView mDoubt;
    @BindView(R.id.repayment_deadline)
    TextView mRepaymentDeadline;
    @BindView(R.id.the_bank)
    TextView mTheBank;//本行还款说明
    @BindView(R.id.repayment_assistance)
    TextView mRepaymentAssistance;//本行还款按钮文字
    @BindView(R.id.the_bank_layout)
    RelativeLayout mTheBankLayout;
    @BindView(R.id.other_bank)
    TextView mOtherBank;//他行还款说明
    @BindView(R.id.other_bank_assistance)
    TextView mOtherBankAssistance;//其他还款按钮文字
    @BindView(R.id.other_bank_layout)
    RelativeLayout mOtherBankLayout;
    @BindView(R.id.other_bank_ll)
    LinearLayout mOtherBankLL;

    @BindView(R.id.repayment_switch)//ATM还款切换按钮
            TextView mRepaymentSwitch;
    @BindView(R.id.repayment_switch_layout)
    LinearLayout mRepaymentSwitchLayout;
    @BindView(R.id.network_repayment)//网上还款切换按钮
            TextView mNetworkRepayment;
    @BindView(R.id.network_repayment_layout)
    LinearLayout mNetworkRepaymentLayout;
    @BindView(R.id.the_online_banking)
    RelativeLayout mTheOnlineBanking;
    @BindView(R.id.other_online_banking)
    RelativeLayout mOtherOnlineBanking;
    @BindView(R.id.the_online_banking_text)
    TextView mTheOnlineBankingText;
    @BindView(R.id.the_online_banking_v)
    TextView mTheOnlineBankingV;
    @BindView(R.id.other_online_banking_text)
    TextView mOtherOnlineBankingText;

    FrameLayout fl;
    private BeanLoanProgress loadProgress;

    boolean isAnimatorRun = false;//是否正在动画
    boolean isShow = false;//是否显示了  true显示了   false未显示
    boolean isMeasured = false;
    private OrderStatusAnimator osa;

    @Override
    protected int getLayoutId() {
        return R.layout.layout_waiting1_for_repayment;
    }

    @Override
    protected void initBundle(Bundle bundle) {
        super.initBundle(bundle);
        if (null != bundle) {
            try {
                loadProgress= (BeanLoanProgress) getArguments().getSerializable("data");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        fl = ((BorrowingStatusFragment) (StatusRepaymentConfirmFragment.this.getParentFragment())).getView();
    }

    @Override
    protected void initData() {
        ((BorrowingStatusFragment) (StatusRepaymentConfirmFragment.this.getParentFragment())).setBehvior(true, true, mCheckCancel);
        mRepaymentSwitch.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        mRepaymentSwitch.getPaint().setAntiAlias(true);//抗锯齿
        mNetworkRepayment.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        mNetworkRepayment.getPaint().setAntiAlias(true);//抗锯齿
        try {
            GlideImageLoader.load(getContext(),loadProgress.getImageDomain() + loadProgress.getRefundBankLogo(),mBankLogo);
//            Glide.with(getActivity()).load(loadProgress.getImageDomain() + loadProgress.getRefundBankLogo()).crossFade().placeholder(R.mipmap.lingyi32).into(mBankLogo);
            mBankCardNumber.setText(StringUtils.getblank(loadProgress.getRefundBankCardNumber()));
            mAmount.setText(String.format(getResources().getString(R.string.borrowing_money), StringUtils.getAmount(loadProgress.getRepaymentAmount(), 3)));
            if (loadProgress.getRefundBankCode().equalsIgnoreCase(Constant.BANK_CODE_BNI) ||
                    loadProgress.getRefundBankCode().equalsIgnoreCase(Constant.BANK_CODE_PERMATA)) {
                mOtherBankLL.setVisibility(View.VISIBLE);
                String s = String.format(getResources().getString(R.string.repayment_assistance1), loadProgress.getRefundBankName()) +
                        getResources().getString(R.string.rekomendasi);
                SpannableStringBuilder style = new SpannableStringBuilder(s);
                style.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getActivity(), R.color.content_blue)),
                        s.length() - getResources().getString(R.string.rekomendasi).length(),
                        s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                mTheBank.setText(style);
                String otherBank = String.format(getResources().getString(R.string.repayment_assistance3), loadProgress.getRefundBankName());
                mOtherBank.setText(otherBank);
                String ra = String.format(getResources().getString(R.string.repayment_assistance2), loadProgress.getRefundBankName());
                mRepaymentAssistance.setText(ra);
                String raaa = String.format(getResources().getString(R.string.repayment_assistance9), loadProgress.getRefundBankName());
                mTheOnlineBankingV.setText(raaa);
                String raa = String.format(getResources().getString(R.string.repayment_assistance10), loadProgress.getRefundBankName());
                mTheOnlineBankingText.setText(raa);
            } else if (loadProgress.getRefundBankCode().equalsIgnoreCase(Constant.BANK_CODE_OTC) ||
                    loadProgress.getRefundBankCode().equalsIgnoreCase(Constant.BANK_CODE_DUKOOTC)) {
                mTheBank.setText(getResources().getString(R.string.repayment_assistance7));
                mRepaymentAssistance.setText(getResources().getString(R.string.repayment_assistance8));
                mRepaymentSwitch.setVisibility(View.GONE);
            } else if (loadProgress.getRefundBankCode().equalsIgnoreCase(Constant.BANK_CODE_MANDIRI)) {
                mOtherOnlineBanking.setVisibility(View.GONE);// 不支持其他网络还款方式
                mOtherOnlineBankingText.setVisibility(View.GONE);// 不支持其他网络还款方式
                String s = String.format(getResources().getString(R.string.repayment_assistance1), loadProgress.getRefundBankName());
                mTheBank.setText(s);
                String ra = String.format(getResources().getString(R.string.repayment_assistance2), loadProgress.getRefundBankName());
                mRepaymentAssistance.setText(ra);
                String raaa = String.format(getResources().getString(R.string.repayment_assistance9), loadProgress.getRefundBankName());
                mTheOnlineBankingV.setText(raaa);
                String raa = String.format(getResources().getString(R.string.repayment_assistance10), loadProgress.getRefundBankName());
                mTheOnlineBankingText.setText(raa);
            } else if (loadProgress.getRefundBankCode().equalsIgnoreCase(Constant.BANK_CODE_DUKOPERMATA)) {
                mOtherOnlineBanking.setVisibility(View.GONE);// 不支持其他网络还款方式
                mOtherOnlineBankingText.setVisibility(View.GONE);// 不支持其他网络还款方式
                String s = String.format(getResources().getString(R.string.repayment_assistance1), loadProgress.getRefundBankName());
                mTheBank.setText(s);
                String ra = String.format(getResources().getString(R.string.repayment_assistance2), loadProgress.getRefundBankName());
                mRepaymentAssistance.setText(ra);
                String raaa = String.format(getResources().getString(R.string.repayment_assistance9), loadProgress.getRefundBankName());
                mTheOnlineBankingV.setText(raaa);
                String raa = String.format(getResources().getString(R.string.repayment_assistance10), loadProgress.getRefundBankName());
                mTheOnlineBankingText.setText(raa);
            } else if(loadProgress.getRefundBankCode().equalsIgnoreCase(Constant.BANK_CODE_DUKODANAMON)) {
                mRepaymentSwitch.setVisibility(View.GONE);
                mOtherBankLL.setVisibility(View.VISIBLE);
                String s = String.format(getResources().getString(R.string.repayment_assistance1), loadProgress.getRefundBankName()) +
                        getResources().getString(R.string.rekomendasi);
                SpannableStringBuilder style = new SpannableStringBuilder(s);
                style.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getActivity(), R.color.content_blue)),
                        s.length() - getResources().getString(R.string.rekomendasi).length(),
                        s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                mTheBank.setText(style);
                String otherBank = String.format(getResources().getString(R.string.repayment_assistance3), loadProgress.getRefundBankName());
                mOtherBank.setText(otherBank);
                String ra = String.format(getResources().getString(R.string.repayment_assistance2), loadProgress.getRefundBankName());
                mRepaymentAssistance.setText(ra);
                String raaa = String.format(getResources().getString(R.string.repayment_assistance9), loadProgress.getRefundBankName());
                mTheOnlineBankingV.setText(raaa);
                String raa = String.format(getResources().getString(R.string.repayment_assistance10), loadProgress.getRefundBankName());
                mTheOnlineBankingText.setText(raa);
            }else if(loadProgress.getRefundBankCode().equalsIgnoreCase(Constant.BANK_CODE_DUKOCIMB)) {
                mOtherBankLL.setVisibility(View.VISIBLE);
                mOtherOnlineBanking.setVisibility(View.GONE);// 不支持其他网络还款方式
                mOtherOnlineBankingText.setVisibility(View.GONE);// 不支持其他网络还款方式
                String s = String.format(getResources().getString(R.string.repayment_assistance1), loadProgress.getRefundBankName()) +
                        getResources().getString(R.string.rekomendasi);
                SpannableStringBuilder style = new SpannableStringBuilder(s);
                style.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getActivity(), R.color.content_blue)),
                        s.length() - getResources().getString(R.string.rekomendasi).length(),
                        s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                mTheBank.setText(style);
                String otherBank = String.format(getResources().getString(R.string.repayment_assistance3), loadProgress.getRefundBankName());
                mOtherBank.setText(otherBank);
                String ra = String.format(getResources().getString(R.string.repayment_assistance2), loadProgress.getRefundBankName());
                mRepaymentAssistance.setText(ra);
                String raaa = String.format(getResources().getString(R.string.repayment_assistance9), loadProgress.getRefundBankName());
                mTheOnlineBankingV.setText(raaa);
                String raa = String.format(getResources().getString(R.string.repayment_assistance10), loadProgress.getRefundBankName());
                mTheOnlineBankingText.setText(raa);
            } else {
                String s = String.format(getResources().getString(R.string.repayment_assistance1), loadProgress.getRefundBankName());
                mTheBank.setText(s);
                String ra = String.format(getResources().getString(R.string.repayment_assistance2), loadProgress.getRefundBankName());
                mRepaymentAssistance.setText(ra);
            }

            if (null != loadProgress.getRefundBankCode() && loadProgress.getRefundBankCode().equalsIgnoreCase(Constant.BANK_CODE_OTC)) {//支付码
                mVirtualAccountValidUntil.setText(String.format(getResources().getString(R.string.payment_code_is_valid_until),
                        DateTimeUtil.formatEnDateTime(loadProgress.getRefundBankCardNumberValidityPeriod(), DateTimeUtil.EN_DF_HH_MM_MM_D_YYYY)));
            } else {
                mVirtualAccountValidUntil.setText(String.format(getResources().getString(R.string.virtual_account_valid_until),
                        DateTimeUtil.formatEnDateTime(loadProgress.getRefundBankCardNumberValidityPeriod(), DateTimeUtil.EN_DF_HH_MM_MM_D_YYYY)));
            }
            String agreement = String.format(getResources().getString(R.string.reimbursement_deadline),
                    DateTimeUtil.formatEnDateTime(loadProgress.getRepaymentDeadline(), DateTimeUtil.EN_DF_YYYY_MM_DD));
            Locale locale = Locale.getDefault();
            int start = 0;
            int end = 0;
            if (null != locale && !locale.getCountry().equalsIgnoreCase("CN")) {
                start = 35;
                end = agreement.length();
            } else {
                start = 2;
                end = 14;
            }
            SpannableStringBuilder style = new SpannableStringBuilder(agreement);
            style.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getActivity(), R.color.emphasize)),
                    start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            mRepaymentDeadline.setText(style);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        getViewTreeObserver(true, true);
//        repaymentReminder();
    }

    @OnClick({R.id.check_cancel, R.id.doubt, R.id.the_bank_layout, R.id.other_bank_layout, R.id.back,
            R.id.repayment_switch, R.id.network_repayment, R.id.the_online_banking, R.id.other_online_banking})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.check_cancel:
                ((BorrowingStatusFragment) (StatusRepaymentConfirmFragment.this.getParentFragment())).setBehaviorState();
//                int h = ((BorrowingStatusFragment) (StatusRepaymentConfirmFragment.this.getParentFragment())).getViewHeight();
//                if (null == osa)
//                    osa = new OrderStatusAnimator(OrderStatusAnimator.Config.fromTypeArray(mCheckCancel, fl, h, isShow));
//                osa.start();
                break;
            case R.id.doubt:
                Bundle bundleDetails = new Bundle();
                bundleDetails.putString("orderId", loadProgress.getOrderId());
                showActivity(getActivity(), LoanDetailsActivity.class, bundleDetails);
                break;
            case R.id.the_bank_layout://还款指引
                if (null != loadProgress) {
                    String bankCode = loadProgress.getRefundBankCode();
                    Bundle bundle = new Bundle();
                    bundle.putString("bankCode", bankCode);//银行code
                    bundle.putString("cardName", loadProgress.getRefundBankName());//银行名称
                    bundle.putString("cardNumber", loadProgress.getRefundBankCardNumber());//银行卡号
                    bundle.putInt("bankType", 1);//类型，1：本行  2：他行
                    bundle.putInt("repaymentMethod", 1);//还款方式(1ATM  2 本行网银/手机  3他行网银/手机)
                    showActivity(getActivity(), RefundGuidelinesActivity.class, bundle);

                }
                break;
            case R.id.other_bank_layout://其他银行还款指引
                if (null != loadProgress) {
                    String bankCode = loadProgress.getRefundBankCode();
                    Bundle bundle = new Bundle();
                    bundle.putString("bankCode", bankCode);//银行code
                    bundle.putString("cardName", loadProgress.getRefundBankName());//银行名称
                    bundle.putString("cardNumber", loadProgress.getRefundBankCardNumber());//银行卡号
                    bundle.putInt("bankType", 2);//类型，1：本行  2：他行
                    bundle.putInt("repaymentMethod", 1);//还款方式(1ATM  2 本行网银/手机  3他行网银/手机)
                    showActivity(getActivity(), RefundGuidelinesActivity.class, bundle);
                }
                break;
            case R.id.back:
                loadProgress.setOrderStatus(BorrowingStatusFragment.STATUS_SELECTBANK);
                EventBus.getDefault().post(new MessageEvent(EventBusType.REFRESH));
//                initData();
                break;
            case R.id.repayment_switch:
                mRepaymentSwitchLayout.setVisibility(View.GONE);
                mNetworkRepaymentLayout.setVisibility(View.VISIBLE);
                break;
            case R.id.network_repayment:
                mRepaymentSwitchLayout.setVisibility(View.VISIBLE);
                mNetworkRepaymentLayout.setVisibility(View.GONE);
                break;
            case R.id.the_online_banking://本行网银/手机
                if (null != loadProgress) {
                    String bankCode = loadProgress.getRefundBankCode();
                    Bundle bundle = new Bundle();
                    bundle.putString("bankCode", bankCode);//银行code
                    bundle.putString("cardName", loadProgress.getRefundBankName());//银行名称
                    bundle.putString("cardNumber", loadProgress.getRefundBankCardNumber());//银行卡号
                    bundle.putInt("repaymentMethod", 2);//还款方式(1ATM  2 本行网银/手机  3他行网银/手机)
                    showActivity(getActivity(), RefundGuidelinesActivity.class, bundle);
                }
                break;
            case R.id.other_online_banking://他行网银/手机
                if (null != loadProgress) {
                    String bankCode = loadProgress.getRefundBankCode();
                    Bundle bundle = new Bundle();
                    bundle.putString("bankCode", bankCode);//银行code
                    bundle.putString("cardName", loadProgress.getRefundBankName());//银行名称
                    bundle.putString("cardNumber", loadProgress.getRefundBankCardNumber());//银行卡号
                    bundle.putInt("repaymentMethod", 3);//还款方式(1ATM  2 本行网银/手机  3他行网银/手机)
                    showActivity(getActivity(), RefundGuidelinesActivity.class, bundle);
                }
                break;
        }
    }


    /**
     * @param showAll    是否全部展示 true 展示，false隐藏
     * @param isAnimator 是否显示可箭头按钮 true 显示，false隐藏
     */
    private void getViewTreeObserver(final boolean showAll, final boolean isAnimator) {
        isMeasured = false;
        ViewTreeObserver vto = mCheckCancel.getViewTreeObserver();
        vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                if (!isMeasured) {
                    isMeasured = true;
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("showAll", showAll);
                    bundle.putBoolean("isAnimator", isAnimator);
                    Message message = new Message();
                    message.setData(bundle);
                    handler.sendMessage(message);
                }
                return true;
            }
        });
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            try {
                Bundle bundle = msg.getData();
                Boolean showAll = bundle.getBoolean("showAll", true);
                Boolean isAnimator = bundle.getBoolean("isAnimator", true);
                if (!isAnimator) {
                    mCheckCancel.setVisibility(View.INVISIBLE);
                }
                ObjectAnimator animator;
                if (!showAll) {
                    int h = ((BorrowingStatusFragment) (StatusRepaymentConfirmFragment.this.getParentFragment())).getViewHeight();
                    animator = ObjectAnimator.ofFloat(fl, "translationY", 0, fl.getHeight() - h);
                    animator.setDuration(10);
                    animator.start();
                    isShow = false;
                } else {
                    mCheckCancel.setRotation(180);
                    isShow = true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };



}
