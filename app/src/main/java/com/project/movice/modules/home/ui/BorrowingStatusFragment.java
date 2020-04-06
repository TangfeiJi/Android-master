package com.project.movice.modules.home.ui;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.google.firebase.analytics.FirebaseAnalytics;
import com.project.movice.R;
import com.project.movice.base.fragment.BaseFragment;
import com.project.movice.dialog.PraiseDialog;
import com.project.movice.modules.home.bean.BeanLoanProgress;
import com.project.movice.modules.home.bean.HomeBean;
import com.project.movice.modules.home.contract.BorrowStatusContract;
import com.project.movice.modules.home.presenter.BorrowStatusPresenter;
import com.project.movice.modules.mine.ui.LoanDetailsActivity;
import com.project.movice.modules.mycalendar.MyCalenderUtils;
import com.project.movice.utils.DataUtils;
import com.project.movice.utils.DateTimeUtil;
import com.project.movice.utils.StringUtils;
import com.project.movice.utils.TDevice;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.text.ParseException;

import butterknife.BindView;
import butterknife.OnClick;

import static com.project.movice.modules.loan.ui.BaseActivity.showActivity;

/**
 * 借款进行中
 * Created by wy on 2018/1/9 20:40.
 */


public class BorrowingStatusFragment extends BaseFragment<BorrowStatusPresenter> implements BorrowStatusContract.View {

    public static final int STATUS_CANCEL = 1;//可取消
    public static final int STATUS_UNDERREVIEW = 2;//审核中
    public static final int STATUS_AUDITFAILURE = 3;//审核失败
    public static final int STATUS_SELECTBANK = 4;//选还款银行
    public static final int STATUS_WAITINGFORREPAYMENT = 5;//等待还款
    public static final int STATUS_APPROVED = 6;//审核成功
    public static final int STATUS_REPAYMENT_SUCCESSFUL = 7;//还款成功
    public static final int STATUS_LENDING_FAILUREL = 8;//放款失败

    @BindView(R.id.layout)
    LinearLayout mLayout;
    @BindView(R.id.layout_b)
    View mLayoutB;
    @BindView(R.id.status_layout)
    FrameLayout mStatusLayout;
    @BindView(R.id.loan_details)
    TextView mLoanDetails;//贷款详情
    @BindView(R.id.borrowing_number)
    TextView mBorrowingNumber;//贷款编号
    @BindView(R.id.name)
    TextView mName;//姓名
    @BindView(R.id.ktp_number)
    TextView mKtpNumber;//KTP号码
    @BindView(R.id.application_date)
    TextView mApplicationDate;//申请时间
    @BindView(R.id.borrowing_balance)
    TextView mBorrowingBalance;//借款金额
    @BindView(R.id.life_of_loan)
    TextView mLifeOfLoan;//借款期限
    @BindView(R.id.repayment_amount)
    TextView mRepaymentAmount;//还款金额
    @BindView(R.id.due_bank)
    TextView mDueBank;//收款银行
    @BindView(R.id.collection_bank_card_number)
    TextView mCollectionBankCardNumber;//收款银行卡号
    @BindView(R.id.bottom_sheet)
    View bottomSheet;

    Fragment fragment = null;

    private BeanLoanProgress loadProgress;
    private String msg;

    private BottomSheetBehavior behavior;
    boolean isMeasured = false;
    boolean isExpanded = false;
    boolean isDragging = true;
    View view;

    private FirebaseAnalytics mFirebaseAnalytics;
    @Override
    protected int getLayoutId() {
        return R.layout.borrowing_ongoing;
    }

    @Override
    protected void initEventAndData() {

    }

    @Override
    protected void initView() {
        if (null != getArguments()) {
            try {
                HomeBean homeBean = (HomeBean) getArguments().getSerializable("data");
                loadProgress = homeBean.getLoanProgress();
                msg = getArguments().getString("ewqddweq43");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        initData();
    }


    public int getViewHeight() {
        return mLayoutB.getHeight();
    }

    public FrameLayout getView() {
        return mStatusLayout;
    }


    protected void initData() {
        behavior = BottomSheetBehavior.from(bottomSheet);
        behavior.setHideable(false);
        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (!isDragging) {
                    if (isExpanded) {
                        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    } else {
                        behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    }
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                if (null != view) {
                    view.setRotation(slideOffset * 180);
                }
            }
        });
        mBorrowingNumber.setText(loadProgress.getLoanNumber());
        mName.setText(loadProgress.getName());
        mKtpNumber.setText(loadProgress.getKtpNumber());
        mApplicationDate.setText(DateTimeUtil.formatDateTime(loadProgress.getApplicationTime(), DateTimeUtil.DF_YYYY_MM_DD_HH_MM_SS));
        mBorrowingBalance.setText(String.format(getResources().getString(R.string.borrowing_money), StringUtils.getAmount(loadProgress.getLoanAmount(), 3)));
        mRepaymentAmount.setText(String.format(getResources().getString(R.string.borrowing_money), StringUtils.getAmount(loadProgress.getRepaymentAmount(), 3)));

        mDueBank.setText(loadProgress.getBeneficiaryBank());
        mCollectionBankCardNumber.setText(StringUtils.getblank(loadProgress.getBankCardNumber()));
        mLifeOfLoan.setText(loadProgress.getNumberLoanDays() + " Hari");
        try {
            if (null != fragment) {
                getChildFragmentManager().beginTransaction().remove(fragment).commitNowAllowingStateLoss();
            }
//            loadProgress.setOrderStatus(7);
            int type = loadProgress.getOrderStatus();
            if (type == STATUS_CANCEL || type == STATUS_UNDERREVIEW || type == STATUS_APPROVED) {//2分钟倒计时  审核中  审核成功
                fragment = new StatusCancelFragment();
            } else if (type == STATUS_AUDITFAILURE || type == STATUS_LENDING_FAILUREL) {//审核失败   放款失败
                fragment = new StatusFailuerFragment();
            } else if (type == STATUS_SELECTBANK) {//选择还款银行

                mFirebaseAnalytics = FirebaseAnalytics.getInstance(getActivity());
                Bundle aaa = new Bundle();
                aaa.putString("image_name", "3");
                aaa.putString("full_text", "3");
                mFirebaseAnalytics.logEvent("fkid", aaa);
                addSubscribe(new RxPermissions(getActivity())
                        .request(Manifest.permission.READ_CALENDAR, Manifest.permission.WRITE_CALENDAR)
                        .subscribe(granted -> {
                            if (granted) {
                                try {
                                    MyCalenderUtils.borrowSucDayNotify(getActivity(), DateTimeUtil.longToDate(loadProgress.getRepaymentDeadline()));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }));
                fragment = new StatusRepaymentSelectBankFragment();
                showPraise();
            } else if (type == STATUS_WAITINGFORREPAYMENT) {//等待还款
                addSubscribe(new RxPermissions(getActivity())
                        .request(Manifest.permission.READ_CALENDAR, Manifest.permission.WRITE_CALENDAR)
                        .subscribe(granted -> {
                            if (granted) {
                                try {
                                    MyCalenderUtils.borrowSucDayNotify(getActivity(), DateTimeUtil.longToDate(loadProgress.getRepaymentDeadline()));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }));

                fragment = new StatusRepaymentConfirmFragment();
                showPraise();
            } else if (type == STATUS_REPAYMENT_SUCCESSFUL) {//还款成功
                fragment = new StatusRepaymentSuccessfulFragment();
                addSubscribe(new RxPermissions(getActivity())
                        .request(Manifest.permission.READ_CALENDAR, Manifest.permission.WRITE_CALENDAR)
                        .subscribe(granted -> {
                            if (granted) {
                                MyCalenderUtils.borrowWeekNotify(getActivity());
                            }
                        }));

                showPraise();
            }
            Bundle bundle = new Bundle();
            bundle.putSerializable("data", loadProgress);
            bundle.putString("ewqddweq43", msg);
            fragment.setArguments(bundle);
            getChildFragmentManager().beginTransaction().replace(R.id.status_layout, fragment).commitNowAllowingStateLoss();
        } catch (Exception e) {
            e.printStackTrace();
        }

        ViewTreeObserver vto = mLayoutB.getViewTreeObserver();
        vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                if (!isMeasured) {
                    isMeasured = true;
                    behavior.setPeekHeight(getViewHeight());
                }
                return true;
            }
        });
    }

    /**
     * @param isExpanded 是否默认展开  true 是   false 否
     * @param isDragging 是否可展开，折叠  true 是   false 否
     */
    public void setBehvior(boolean isExpanded, boolean isDragging, View btView) {
        this.isExpanded = isExpanded;
        this.isDragging = isDragging;
        this.view = btView;
        if (isExpanded) {
            behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            if (null != view)
                view.setRotation(180);
        } else {
            behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            if (null != view)
                view.setRotation(0);
        }
    }

    public boolean setBehaviorState() {
        if (behavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
            behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            return false;
        } else {
            behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            return true;
        }
    }

    @OnClick({R.id.loan_details})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loan_details://订单详情
                Bundle bundleDetails = new Bundle();
                bundleDetails.putString("orderId", loadProgress.getOrderId());
                showActivity(getActivity(), LoanDetailsActivity.class, bundleDetails);
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    /**
     * 通过用户是还款中的状态，提示用户去给我们好评，一个订单只提示一次。
     */
    private void showPraise() {
        try {
            String number = (String) DataUtils.get(getActivity(), "gr2t", "1");
            if (number.equals(loadProgress.getLoanNumber())) {
                return;
            }
            PraiseDialog praiseDialog = new PraiseDialog(getActivity());
            praiseDialog.setOnClickListener(new PraiseDialog.OnClickListener() {
                @Override
                public void onCancel() {

                }

                @Override
                public void onConfirm() {
                    TDevice.openAppInMarket(getActivity());
                }
            });
            praiseDialog.show();
            DataUtils.put(getActivity(), "gr2t", loadProgress.getLoanNumber());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getPermissionsUsccess() {
        try {
            MyCalenderUtils.borrowSucDayNotify(getActivity(), DateTimeUtil.longToDate(loadProgress.getRepaymentDeadline()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
