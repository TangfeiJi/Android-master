package com.project.movice.modules.home.ui;

import android.view.View;


import com.project.movice.R;
import com.project.movice.base.fragment.BaseFragment;
import com.project.movice.modules.home.bean.BeanLoanProgress;
import com.project.movice.modules.home.contract.StatusFailuerContract;
import com.project.movice.modules.home.presenter.StatusFailuerPresenter;
import com.project.movice.utils.EventBusType;
import com.project.movice.utils.MessageEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import butterknife.OnClick;

/**
 * 还款成功
 * Created by wy on 2018/4/19 13:16.
 */


public class StatusRepaymentSuccessfulFragment extends BaseFragment<StatusFailuerPresenter> implements StatusFailuerContract.View {

    private BeanLoanProgress loadProgress;

    @Override
    protected void initView() {

        if (null != getArguments()) {
            try {
                loadProgress= (BeanLoanProgress) getArguments().getSerializable("data");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        ((BorrowingStatusFragment) (StatusRepaymentSuccessfulFragment.this.getParentFragment())).setBehvior(true, false, null);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_repayment_successful;
    }

    @Override
    protected void initEventAndData() {

    }





    @OnClick({R.id.i_see})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.i_see:
                    Map<String, String> params = new HashMap<>();
                    params.put("orderId", loadProgress.getOrderId());
                    mPresenter.requestStatusCancel(params);
                break;
        }
    }


    /**
     * 确认成功订单
     */

    @Override
    public void getStatusCancel() {
        EventBus.getDefault().post(new MessageEvent(EventBusType.BORROWING));
    }
}
