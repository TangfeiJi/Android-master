package com.project.movice.modules.home.ui;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;


import com.project.movice.R;
import com.project.movice.base.fragment.BaseFragment;
import com.project.movice.dialog.ConfirmPromptDialog;
import com.project.movice.modules.home.adappter.BankListAdapter;
import com.project.movice.modules.home.adappter.BaseRecyclerAdapter;
import com.project.movice.modules.home.bean.BeanBank;
import com.project.movice.modules.home.bean.BeanLoanProgress;
import com.project.movice.modules.home.bean.BeanRefundBank;
import com.project.movice.modules.home.contract.StatusRepayContract;
import com.project.movice.modules.home.presenter.StatusRepayPresenter;
import com.project.movice.utils.Constant;
import com.project.movice.utils.DateTimeUtil;
import com.project.movice.utils.EventBusType;
import com.project.movice.utils.MessageEvent;
import com.project.movice.utils.StringUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import anim.BtStatusAnimator;
import anim.OrderStatusAnimator;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * 选择银行页面
 * Created by wy on 2018/4/19 14:43.
 */


public class StatusRepaymentSelectBankFragment extends BaseFragment <StatusRepayPresenter> implements StatusRepayContract.View {

    @BindView(R.id.close)
    ImageView mClose;
    @BindView(R.id.check_cancel)
    ImageView mCheckCancel;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.repayment_deadline)
    TextView mRepaymentDeadline;


    FrameLayout fl;
    private BeanLoanProgress loadProgress;

    boolean isAnimatorRun = false;//是否正在动画
    boolean isShow = false;//是否显示了  true显示了   false未显示
    boolean isMeasured = false;
    private OrderStatusAnimator osa;
    private BtStatusAnimator btStatus;


    @Override
    protected void initView() {

        if (null != getArguments()) {
            try {
                loadProgress= (BeanLoanProgress) getArguments().getSerializable("data");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        fl = ((BorrowingStatusFragment) (StatusRepaymentSelectBankFragment.this.getParentFragment())).getView();
        initData();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_select_bank;
    }

    @Override
    protected void initEventAndData() {

    }



    protected void initData() {
        ((BorrowingStatusFragment) (StatusRepaymentSelectBankFragment.this.getParentFragment())).setBehvior(true, true, mCheckCancel);
        if (!StringUtils.isEmpty(loadProgress.getRefundBankCardNumber())) {
            mClose.setVisibility(View.VISIBLE);
        } else {
            mClose.setVisibility(View.GONE);
        }

        final List<BeanBank> list = loadProgress.getBankList();
        BankListAdapter adapter = new BankListAdapter(getActivity());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, long itemId) {
//                    ConfirmPromptDialog
                final BeanBank bank = list.get(position);
                String content = getResources().getString(R.string.virtual_number_validity_period);
                if (null != bank.getBankCode()) {
                    if (bank.getBankCode().equalsIgnoreCase(Constant.BANK_CODE_OTC)) {//支付码
                        content = getResources().getString(R.string.payment_code_validity_period);
                    } else if (bank.getBankCode().equalsIgnoreCase(Constant.BANK_CODE_MANDIRI)) {//mandiri只能在自己银行还款，所以要明确提示
                        content = getResources().getString(R.string.mandiri_only_support_atm) +
                                getResources().getString(R.string.virtual_number_validity_period);
                    } else if (bank.getBankCode().equalsIgnoreCase(Constant.BANK_CODE_PERMATA) || bank.getBankCode().equalsIgnoreCase(Constant.BANK_CODE_BCA) ||
                            bank.getBankCode().equalsIgnoreCase(Constant.BANK_CODE_BNI)) {
                        String s = String.format(getResources().getString(R.string.virtual_number_validity_period_s), bank.getBankName());
                        content = getResources().getString(R.string.virtual_number_validity_period);
                    }
                }
                ConfirmPromptDialog confirm = new ConfirmPromptDialog(getActivity(),
                        R.mipmap.lingyi5, content,
                        getResources().getString(R.string.cancel),
                        getResources().getString(R.string.confirm));
                confirm.setOnClickListener(new ConfirmPromptDialog.OnClickListener() {
                    @Override
                    public void onClick() {

                            Map<String, String> params = new HashMap<>();
                            params.put("orderId", loadProgress.getOrderId());
                            params.put("bankCode", bank.getBankCode());

                            mPresenter.requestStatusRepay(params);

                    }
                });
                confirm.show();
            }
        });
        mRepaymentDeadline.setText(String.format(getResources().getString(R.string.reimbursement_deadline),
                DateTimeUtil.formatEnDateTime(loadProgress.getRepaymentDeadline(), DateTimeUtil.EN_DF_YYYY_MM_DD)));
        adapter.resetItem(list);
//        getViewTreeObserver(true, true);
    }

    @OnClick({R.id.check_cancel, R.id.close})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.close:
                loadProgress.setOrderStatus(BorrowingStatusFragment.STATUS_WAITINGFORREPAYMENT);
                EventBus.getDefault().post(new MessageEvent(EventBusType.REFRESH));
                break;
            case R.id.check_cancel:
                ((BorrowingStatusFragment) (StatusRepaymentSelectBankFragment.this.getParentFragment())).setBehaviorState();
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
                    int h = ((BorrowingStatusFragment) (StatusRepaymentSelectBankFragment.this.getParentFragment())).getViewHeight();
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


    /**
     * 确认还款银行
     */


    @Override
    public void getStatusRepay(BeanRefundBank response) {
        if (null != response) {
            loadProgress.setRefundBankCardNumber(response.getBankCardNumber());
            loadProgress.setRefundBankLogo(response.getBankLogo());
            loadProgress.setImageDomain(response.getImageDomain());
            loadProgress.setRefundBankCode(response.getBankCode());
            loadProgress.setRefundBankName(response.getBankName());
            loadProgress.setRefundBankCardNumberValidityPeriod(response.getBankCardNumberValidityPeriod());
            loadProgress.setOrderStatus(BorrowingStatusFragment.STATUS_WAITINGFORREPAYMENT);
            EventBus.getDefault().post(new MessageEvent(EventBusType.REFRESH));
        }
    }
}
