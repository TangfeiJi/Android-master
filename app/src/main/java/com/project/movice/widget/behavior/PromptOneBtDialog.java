package com.project.movice.widget.behavior;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;


import com.project.movice.R;
import com.project.movice.utils.StringUtils;

import butterknife.ButterKnife;

/**
 * Created by wy on 2018/11/27.
 */

public class PromptOneBtDialog extends Dialog {


    private OnClickListener listener;

    public interface OnClickListener {
        void onClick();
    }

    public void setOnClickListener(OnClickListener listener) {
        this.listener = listener;
    }

    public void setOnTouchOutside(boolean cancel) {
        setCanceledOnTouchOutside(cancel);
        setCancelable(cancel);
    }

    public PromptOneBtDialog(@NonNull Context context, String title, CharSequence text, String bt) {
        super(context, R.style.dialog);
        View view = LayoutInflater.from(context).inflate(R.layout.prompt_one1_bt, null);
        setContentView(view);


        TextView mTitle = ButterKnife.findById(view, R.id.title);
        TextView mContent = ButterKnife.findById(view, R.id.content);
        TextView mConfirm = ButterKnife.findById(view, R.id.confirm);
        try {
            if (!StringUtils.isEmpty(title)) {
                mTitle.setText(title);
                mTitle.setVisibility(View.VISIBLE);
            }
            mContent.setText(text);
            mConfirm.setText(bt);
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    //laji
    public static void hideKeyboard(Context mcontext, ViewGroup view){
        view.requestFocus();
        InputMethodManager im= (InputMethodManager) mcontext.getSystemService(Context.INPUT_METHOD_SERVICE);
        try{
            im.hideSoftInputFromWindow(view.getWindowToken(),0);

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void showKeyboard(Context mcontext, View view){
        InputMethodManager im= (InputMethodManager) mcontext.getSystemService(Context.INPUT_METHOD_SERVICE);
        im.showSoftInput(view,0);
    }
}
