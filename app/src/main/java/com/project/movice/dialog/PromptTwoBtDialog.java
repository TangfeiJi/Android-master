package com.project.movice.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.project.movice.R;
import com.project.movice.utils.StringUtils;

import butterknife.ButterKnife;

/**
 * Created by wy on 2018/11/27.
 */

public class PromptTwoBtDialog extends Dialog {

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

    public PromptTwoBtDialog(@NonNull Context context, String title, CharSequence text, String bt_left, String bt_right) {
        super(context, R.style.dialog);
        View view = LayoutInflater.from(context).inflate(R.layout.prompt_two1_bt, null);
        setContentView(view);

        TextView mTitle = ButterKnife.findById(view, R.id.title);
        TextView mContent = ButterKnife.findById(view, R.id.content);
        TextView mCancel = ButterKnife.findById(view, R.id.cancel);
        TextView mConfirm = ButterKnife.findById(view, R.id.confirm);
        try {
            if (!StringUtils.isEmpty(title)) {
                mTitle.setText(title);
                mTitle.setVisibility(View.VISIBLE);
            }
            mContent.setText(text);
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
                }
                dismiss();
            }
        });
    }
}
