package com.project.movice.widget.behavior;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.movice.R;
import com.project.movice.modules.loan.bean.BeanAmountComposition;
import com.project.movice.utils.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;

/**
 * 金额构成
 * Created by wy on 2018/11/27.
 */

public class AmountCompositionDialog extends Dialog {

    Map<String, String> map = new HashMap<>();
    private List<BeanAmountComposition> list = new ArrayList<>();

    /**
     * @param context
     * @param reat
     * @param reatAmount    利息
     * @param loanAmount    借款金额
     * @param managementFee 管理费
     * @param earnestMoney  保证金
     */
    @SuppressLint("StringFormatInvalid")
    public AmountCompositionDialog(@NonNull Context context, String reat, long reatAmount, String loanAmount, long managementFee, long earnestMoney) {
        super(context, R.style.dialog);
        Resources r = context.getResources();
        View view = LayoutInflater.from(context).inflate(R.layout.amount_comp, null);
        setContentView(view);

        LinearLayout mAmountLayout = ButterKnife.findById(view, R.id.amount_layout);
        TextView mRefundOfDeposit = ButterKnife.findById(view, R.id.refund_of_deposit);
        TextView mConfirm = ButterKnife.findById(view, R.id.confirm);
        BeanAmountComposition borrowingBalance = new BeanAmountComposition();
        borrowingBalance.setTitle(r.getString(R.string.borrowing_balance));
        borrowingBalance.setContent("+ " + String.format(context.getResources().getString(R.string.borrowing_money), StringUtils.getAmount(loanAmount, 3)));
        list.add(borrowingBalance);
        if (reatAmount > 0) {
            BeanAmountComposition interestRate = new BeanAmountComposition();
            interestRate.setTitle(String.format(context.getResources().getString(R.string.interest_rate2), reat + "%"));
            interestRate.setContent("- " + String.format(context.getResources().getString(R.string.borrowing_money), StringUtils.getAmount(reatAmount + "", 3)));
            list.add(interestRate);
        }
        if (managementFee > 0) {
            BeanAmountComposition management = new BeanAmountComposition();
            management.setTitle(r.getString(R.string.management_fee));
            management.setContent("- " + String.format(context.getResources().getString(R.string.borrowing_money), StringUtils.getAmount(managementFee + "", 3)));
            list.add(management);
        }
        if (earnestMoney > 0) {
            BeanAmountComposition earnest = new BeanAmountComposition();
            earnest.setTitle(r.getString(R.string.earnest_money));
            earnest.setContent("- " + String.format(context.getResources().getString(R.string.borrowing_money), StringUtils.getAmount(earnestMoney + "", 3)));
            list.add(earnest);
            mRefundOfDeposit.setVisibility(View.VISIBLE);
        }

        for (int i = 0; i < list.size(); i++) {
            View amount_item = LayoutInflater.from(context).inflate(R.layout.amount_compo_item, null);
            TextView mTitle = amount_item.findViewById(R.id.title);
            TextView mContent = amount_item.findViewById(R.id.content);
            BeanAmountComposition ac = list.get(i);
            mTitle.setText((i + 1) + "." + ac.getTitle());
            mContent.setText(ac.getContent());
            mAmountLayout.addView(amount_item);
        }


        mConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }
}
