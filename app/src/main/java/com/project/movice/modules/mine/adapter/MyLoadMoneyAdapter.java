package com.project.movice.modules.mine.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.project.movice.R;
import com.project.movice.modules.mine.bean.BeanMyLoan;
import com.project.movice.modules.mine.ui.LoanDetailsActivity;
import com.project.movice.utils.Constant;
import com.project.movice.utils.DateTimeUtil;
import com.project.movice.utils.StringUtils;

import java.util.List;

public class MyLoadMoneyAdapter extends BaseQuickAdapter<BeanMyLoan, BaseViewHolder> {
    private Context mContext;
    private Resources r;
    public MyLoadMoneyAdapter(Context context, @Nullable List<BeanMyLoan> data) {
        super(R.layout.my_loan_item, data);
        mContext = context;
        r = context.getResources();
    }


    @Override
    protected void convert(BaseViewHolder helper, BeanMyLoan item) {

        TextView mLoanNumber=helper.getView(R.id.loan_number);
        TextView mDate=helper.getView(R.id.date);
        TextView mName=helper.getView(R.id.name);
        TextView mLoanAmount=helper.getView(R.id.loan_amount);
        TextView mOrderStatus=helper.getView(R.id.order_status);
        if (null != item) {
            mLoanNumber.setText(String.format(r.getString(R.string.borrowing_number_s), item.getLoanNumber()));
            mName.setText(item.getName());
            mLoanAmount.setText(String.format(r.getString(R.string.borrowing_money), StringUtils.getAmount(item.getLoanAmount(), 3)));
            mDate.setText(DateTimeUtil.formatDateTime(item.getDate(), DateTimeUtil.DF_MM_DD_HH_MM_SS));
            if (item.getOrderStatus() == Constant.ORDERSTATUS_CANCEL) {//申请取消
                mOrderStatus.setText(r.getString(R.string.status_cancel));
                mOrderStatus.setTextColor(ContextCompat.getColor(mContext, R.color.content_orange));
            } else if (item.getOrderStatus() == Constant.ORDERSTATUS_UNDER_REVIEW) {//审核中
                mOrderStatus.setText(r.getString(R.string.status_audit_failure));
                mOrderStatus.setTextColor(ContextCompat.getColor(mContext, R.color.content_orange));
            } else if (item.getOrderStatus() == Constant.ORDERSTATUS_REVIEW_SUCCESS) {//审核通过
                mOrderStatus.setText(r.getString(R.string.review_success));
                mOrderStatus.setTextColor(ContextCompat.getColor(mContext, R.color.content_blue));
            } else if (item.getOrderStatus() == Constant.ORDERSTATUS_AUDIT_FAILURE) {//审核失败
                mOrderStatus.setText(r.getString(R.string.status_under_review));
                mOrderStatus.setTextColor(ContextCompat.getColor(mContext, R.color.content_orange));
            } else if (item.getOrderStatus() == Constant.ORDERSTATUS_TO_BE_REPAYMENTS) {//待还款
                mOrderStatus.setText(r.getString(R.string.status_to_be_repayments));
                mOrderStatus.setTextColor(ContextCompat.getColor(mContext, R.color.content_prompt_text));
            } else if (item.getOrderStatus() == Constant.ORDERSTATUS_SUSPENDED) {//缓期中
                mOrderStatus.setText(r.getString(R.string.status_suspended));
                mOrderStatus.setTextColor(ContextCompat.getColor(mContext, R.color.content_prompt_text));
            } else if (item.getOrderStatus() == Constant.ORDERSTATUS_OVERDUE) {//已逾期
                mOrderStatus.setText(r.getString(R.string.status_overdue));
                mOrderStatus.setTextColor(ContextCompat.getColor(mContext, R.color.content_prompt_text));
            } else if (item.getOrderStatus() == Constant.ORDERSTATUS_REPAYMENT) {//已还款
                mOrderStatus.setText(r.getString(R.string.status_repayment));
                mOrderStatus.setTextColor(ContextCompat.getColor(mContext, R.color.content_blue));
            } else if (item.getOrderStatus() == Constant.ORDERSTATUS_APPLY_REATE) {//申请已创建
                mOrderStatus.setText(r.getString(R.string.status_apply_reate));
                mOrderStatus.setTextColor(ContextCompat.getColor(mContext, R.color.content_orange));
            } else {
                mOrderStatus.setText("");
            }


        }


              helper.getView(R.id.llContent).setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                      Bundle bundle = new Bundle();
                      bundle.putString("orderId", item.getOrderId());
                      Intent intent=new Intent(mContext, LoanDetailsActivity.class);
                      intent.putExtras(bundle);
                      mContext.startActivity(intent);
                  }
              });






    }
}
