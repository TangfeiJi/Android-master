package com.project.movice.modules.home.ui;

import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;


import com.project.movice.R;
import com.project.movice.base.fragment.BaseFragment;
import com.project.movice.modules.home.bean.BeanLoanProgress;
import com.project.movice.modules.home.contract.StatusFailuerContract;
import com.project.movice.modules.home.presenter.StatusFailuerPresenter;
import com.project.movice.utils.EventBusType;
import com.project.movice.utils.MessageEvent;
import com.project.movice.utils.StringUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 审核失败
 * Created by wy on 2018/4/19 13:14.
 */


public class StatusFailuerFragment extends BaseFragment<StatusFailuerPresenter> implements StatusFailuerContract.View {

    @BindView(R.id.i_see)
    Button mIsee;//知道了
    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.content)
    TextView mContent;


    FrameLayout fl;
    private BeanLoanProgress loadProgress;
    private String msg;

    @Override
    protected void initView() {
        if (null != getArguments()) {
            try {
                loadProgress= (BeanLoanProgress) getArguments().getSerializable("data");
                msg = getArguments().getString("ewqddweq43");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        fl = ((BorrowingStatusFragment) (StatusFailuerFragment.this.getParentFragment())).getView();

        initData();

    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_audi1t_failure;
    }

    @Override
    protected void initEventAndData() {

    }



    protected void initData() {
        int type = loadProgress.getOrderStatus();
        if (type == BorrowingStatusFragment.STATUS_LENDING_FAILUREL) {//放款失败
            mTitle.setText(getResources().getString(R.string.auditing_feedback));
            if (StringUtils.isEmpty(msg)) {
                mContent.setText(getResources().getString(R.string.lending_failure_believes_content));
            } else {
                mContent.setText(msg);
            }
        } else {//审核失败
            mTitle.setText(getResources().getString(R.string.auditing_feedback));
            if (StringUtils.isEmpty(msg)) {
                mContent.setText(getResources().getString(R.string.the_information_is_not_approved));
            } else {
                mContent.setText(msg);
            }
        }
        ((BorrowingStatusFragment) (StatusFailuerFragment.this.getParentFragment())).setBehvior(true, false, null);
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


    @Override
    public void getStatusCancel() {
        EventBus.getDefault().post(new MessageEvent(EventBusType.BORROWING));
    }
}
