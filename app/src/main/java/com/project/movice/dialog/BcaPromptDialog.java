package com.project.movice.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.project.movice.R;

import java.util.Locale;

import butterknife.ButterKnife;

/**
 * 单个按钮提醒
 * Created by wy on 2018/1/20 14:31.
 */


public class BcaPromptDialog extends Dialog {


    private OnClickListener listener;

    public interface OnClickListener {
        void onClick();
    }

    public void setOnTouchOutside(boolean cancel) {
        setCanceledOnTouchOutside(cancel);
        setCancelable(cancel);
    }

    public void setOnClickListener(OnClickListener listener) {
        this.listener = listener;
    }

    public BcaPromptDialog(@NonNull Context context, int icon, String content, String bt) {
        super(context, R.style.dialog);
        View view = LayoutInflater.from(context).inflate(R.layout.layout_prompt, null);
        setContentView(view);
        ImageView mImage = ButterKnife.findById(view, R.id.image);
        TextView mContent = ButterKnife.findById(view, R.id.content);
        TextView mBt = ButterKnife.findById(view, R.id.bt);
        mImage.setBackgroundResource(icon);
        Locale locale = Locale.getDefault();
        if (null != locale && !locale.getCountry().equalsIgnoreCase("CN")) {
            SpannableStringBuilder style = new SpannableStringBuilder(content);
            style.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, R.color.content_prompt_text)),
                    48, 65, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            style.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 89, 92, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //粗体
            mContent.setText(style);
        } else {
            mContent.setText(content);
        }

        mBt.setText(bt);
        mBt.setOnClickListener(new View.OnClickListener() {
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
