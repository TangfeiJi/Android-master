package com.project.movice.widget.behavior;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;


import com.project.movice.R;
import com.project.movice.application.MoviceApp;
import com.project.movice.utils.DataUtils;

import butterknife.ButterKnife;

/**
 * 好评/吐槽
 * Created by wy on 2018/5/7 15:44.
 */


public class CallPhoneDialog extends Dialog {


    private OnClickListener listener;

    public interface OnClickListener {
        void onCancel();

        void onConfirm();
    }

    public void setOnClickListener(OnClickListener listener) {
        this.listener = listener;
    }

    public CallPhoneDialog(@NonNull Context context) {
        super(context, R.style.dialog);
        View view = LayoutInflater.from(context).inflate(R.layout.callphone, null);
        setContentView(view);
        TextView content = ButterKnife.findById(view, R.id.content);
        TextView mCancel = ButterKnife.findById(view, R.id.cancel);
        TextView mConfirm = ButterKnife.findById(view, R.id.confirm);
        content.setText(MoviceApp.callPhone);
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
