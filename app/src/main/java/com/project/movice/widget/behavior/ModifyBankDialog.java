package com.project.movice.widget.behavior;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;


import com.project.movice.R;

import butterknife.ButterKnife;

/**
 * 修改银行提示
 */
public class ModifyBankDialog extends Dialog {

    private OnClickListener listener;

    public interface OnClickListener {
        void onClick();
    }

    public void setOnClickListener(OnClickListener listener) {
        this.listener = listener;
    }

    public ModifyBankDialog(@NonNull Context context, String bankCardNumber, String phoneNumber) {
        super(context, R.style.dialog);
        View view = LayoutInflater.from(context).inflate(R.layout.modify_bank_dialog, null);
        setContentView(view);
        TextView mBankCardNumber = ButterKnife.findById(view, R.id.bank_card_number);
        TextView mPhoneNumber = ButterKnife.findById(view, R.id.phone_number);
        TextView mCancel = ButterKnife.findById(view, R.id.cancel);
        TextView mConfirm = ButterKnife.findById(view, R.id.confirm);
        try {
            mBankCardNumber.setText(bankCardNumber);
            mPhoneNumber.setText(phoneNumber);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        mConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != listener) {
                    listener.onClick();
                    dismiss();
                }
            }
        });
    }
}
