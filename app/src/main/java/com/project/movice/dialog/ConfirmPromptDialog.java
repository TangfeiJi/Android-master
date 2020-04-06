package com.project.movice.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.project.movice.R;

import butterknife.ButterKnife;

/**
 * 两个按钮，确认操作提醒
 * Created by wy on 2018/1/23 20:38.
 */


public class ConfirmPromptDialog extends Dialog {

    private OnClickListener listener;
    private OnClickListener leftListener;

    public interface OnClickListener {
        void onClick();
    }

    public void setOnClickListener(OnClickListener listener) {
        this.listener = listener;
    }

    public void setOnLeftClickListener(OnClickListener listener) {
        this.leftListener = listener;
    }

    public ConfirmPromptDialog(@NonNull Context context, int icon, String content, String bt_left, String bt_right) {
        super(context, R.style.dialog);
        View view = LayoutInflater.from(context).inflate(R.layout.layout_confi1rm_prompt, null);
        setContentView(view);
        ImageView mImage = ButterKnife.findById(view, R.id.image);
        TextView mContent = ButterKnife.findById(view, R.id.content);
        TextView mCancel = ButterKnife.findById(view, R.id.cancel);
        TextView mConfirm = ButterKnife.findById(view, R.id.confirm);
        try {
            mImage.setBackgroundResource(icon);
            mContent.setText(content);
            mCancel.setText(bt_left);
            mConfirm.setText(bt_right);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != leftListener) {
                    leftListener.onClick();
                }
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
