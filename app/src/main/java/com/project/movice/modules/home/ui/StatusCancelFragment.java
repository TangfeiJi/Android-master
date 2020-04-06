package com.project.movice.modules.home.ui;

import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;


import com.project.movice.R;
import com.project.movice.base.fragment.BaseFragment;
import com.project.movice.modules.home.bean.BeanLoanProgress;
import com.project.movice.modules.home.contract.StatusCancelContract;
import com.project.movice.modules.home.presenter.StatusCancelPresenter;
import com.project.movice.modules.mycalendar.MyCalenderUtils;
import com.project.movice.utils.Constant;
import com.project.movice.utils.CountDownInterface;
import com.project.movice.utils.CountDownThread;
import com.project.movice.utils.DateTimeUtil;
import com.project.movice.utils.EventBusType;
import com.project.movice.utils.MessageEvent;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.greenrobot.eventbus.EventBus;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import anim.OrderStatusAnimator;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * 2分钟内可取消状态
 * Created by wy on 2018/4/19 13:12.
 */


public class StatusCancelFragment extends BaseFragment<StatusCancelPresenter> implements CountDownInterface, StatusCancelContract.View {

    @BindView(R.id.check_cancel)
    ImageView mCheckCancel;
    @BindView(R.id.loan_status)
    TextView mLoanStatus;//状态
    @BindView(R.id.left_image)
    ImageView mLeftImage;//左边图标
    @BindView(R.id.left_text)
    TextView mLeftText;//左边状态文字
    @BindView(R.id.right_image)
    ImageView mRightImage;//右边图标
    @BindView(R.id.right_text)
    TextView mRightText;//右边状态文字
    @BindView(R.id.processing)
    TextView mProcessing;//倒计时
    @BindView(R.id.time)
    TextView mTime;//时间
    @BindView(R.id.apply_for_cancellation)
    Button mApplyForCancellation;//取消

    private BeanLoanProgress loadProgress;
    FrameLayout fl;
    private CountDownThread countDown;
    public static long validTime = Constant.CANCEL_ORDER_TIME;
    OrderStatusAnimator osa;

    @Override
    protected int getLayoutId() {
        return R.layout.layout_under_review;
    }

    @Override
    protected void initEventAndData() {

    }

    @Override
    protected void initView() {
        fl = ((BorrowingStatusFragment) (StatusCancelFragment.this.getParentFragment())).getView();
        if (null != getArguments()) {
            try {
                 loadProgress= (BeanLoanProgress) getArguments().getSerializable("data");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        initData();

    }


    protected void initData() {
        int type = loadProgress.getOrderStatus();
        if (type == BorrowingStatusFragment.STATUS_APPROVED) {
            //审核成功
            mCheckCancel.setVisibility(View.INVISIBLE);
            mApplyForCancellation.setVisibility(View.GONE);
            mTime.setVisibility(View.INVISIBLE);
            mLeftImage.setBackgroundResource(R.mipmap.lingyi28);
            mRightImage.setBackgroundResource(R.mipmap.lingyi23);
            mLeftText.setText(getResources().getString(R.string.review_success));
            mRightText.setText(getResources().getString(R.string.borrowing_to_account));
            mProcessing.setText(getResources().getString(R.string.account_expected_date));
            mLoanStatus.setText(getResources().getString(R.string.wait_for_a_loan));
            ((BorrowingStatusFragment) (StatusCancelFragment.this.getParentFragment())).setBehvior(false, false, null);
        } else if (type == BorrowingStatusFragment.STATUS_UNDERREVIEW || loadProgress.getCancelCountdown() <= 0) {//不可取消状态
            //审核中
            mCheckCancel.setVisibility(View.VISIBLE);
            mApplyForCancellation.setVisibility(View.GONE);
            mTime.setVisibility(View.INVISIBLE);
            mLeftImage.setBackgroundResource(R.mipmap.lingyi20);
            mRightImage.setBackgroundResource(R.mipmap.lingyi21);
            mLeftText.setText(getResources().getString(R.string.loan_audit));
            mRightText.setText(getResources().getString(R.string.review_success));
            mProcessing.setText(getResources().getString(R.string.arrival_time));
            mLoanStatus.setText(getResources().getString(R.string.loan_audit1));
            ((BorrowingStatusFragment) (StatusCancelFragment.this.getParentFragment())).setBehvior(true, true, mCheckCancel);
        } else {//可取消状态
            //2分钟倒计时
            mCheckCancel.setVisibility(View.VISIBLE);
            mApplyForCancellation.setVisibility(View.GONE);
            mLeftImage.setBackgroundResource(R.mipmap.lingyi34);
            mRightImage.setBackgroundResource(R.mipmap.lingyi50);
            mLeftText.setText(getResources().getString(R.string.waiting));
            mRightText.setText(getResources().getString(R.string.submit_audit));
            mProcessing.setText(getResources().getString(R.string.count_down));
            mLoanStatus.setText(getResources().getString(R.string.pending_application));
            countDown = CountDownThread.newInstance();
            countDown.setOnCountDownListening(this);
            countDown.startTimer(mTime.getId() + getClass().toString(),
                    loadProgress.getCancelCountdown(), 1000);
            ((BorrowingStatusFragment) (StatusCancelFragment.this.getParentFragment())).setBehvior(false, true, mCheckCancel);
        }
    }


    @OnClick({R.id.check_cancel, R.id.apply_for_cancellation})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.check_cancel:

                ((BorrowingStatusFragment) (StatusCancelFragment.this.getParentFragment())).setBehaviorState();
                break;
            case R.id.apply_for_cancellation://取消订单
                    Map<String, String> params = new HashMap<>();
                    params.put("orderId", loadProgress.getOrderId());
                    showLoading();
                    mPresenter.requestStatusCancel(params);
                break;
        }
    }






    @Override
    public void onTick(String key, long millisUntilFinished) {
        try {
            validTime = millisUntilFinished;
            long mm = millisUntilFinished / 1000;
            String m = String.valueOf(mm / 60);
            String s = String.valueOf(mm % 60);
            if (Integer.parseInt(m) < 10) {
                m = "0" + m;
            }
            if (Integer.parseInt(s) < 10) {
                s = "0" + s;
            }
            mTime.setText(m + ":" + s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFinish(String key) {
        try {
            long mm = validTime / 1000;
            String s = String.valueOf(mm % 60);
            if (Integer.parseInt(s) <= 1) {
                mTime.setText("00:00");
                loadProgress.setCancelCountdown(0);
                initData();
                cancel();
                //            EventBus.getDefault().post(new MessageEvent(EventBusType.UPDATELOAN));//
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }



    private void cancel() {
        if (null != countDown) {
            try {
                countDown.cancel(mTime.getId() + getClass().toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void getStatusCancel() {
        cancel();
        EventBus.getDefault().post(new MessageEvent(EventBusType.BORROWING));
    }

}
