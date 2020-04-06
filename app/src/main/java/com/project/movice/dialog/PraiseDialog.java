package com.project.movice.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;


import com.project.movice.R;

import butterknife.ButterKnife;

/**
 * 好评
 * Created by wy on 2018/5/7 15:42.
 */


public class PraiseDialog extends Dialog {


    private OnClickListener listener;

    public interface OnClickListener {
        void onCancel();

        void onConfirm();
    }

    public void setOnClickListener(OnClickListener listener) {
        this.listener = listener;
    }

    public PraiseDialog(@NonNull Context context) {
        super(context, R.style.dialog);
        View view = LayoutInflater.from(context).inflate(R.layout.praise, null);
        setContentView(view);
        TextView mCancel = ButterKnife.findById(view, R.id.cancel);
        TextView mConfirm = ButterKnife.findById(view, R.id.confirm);
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener)
                    listener.onCancel();
                dismiss();
            }
        });
        mConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener)
                    listener.onConfirm();
                dismiss();
            }
        });
    }
}
