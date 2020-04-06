package com.project.movice.widget.behavior;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;

import com.project.movice.R;


/**
 * 生日选项
 * Created by wy on 2018/9/29 17:18.
 */


public class GenderDialog extends Dialog {

    public static final int GENDER_MAN = 1;//男
    public static final int GENDER_FEMALE = 2;//女

    private LinearLayout mGenderMan, mGenderFemale;


    private OnClickListener listener;

    public interface OnClickListener {
        void onClick(int gender);
    }

    public void setOnClickListener(OnClickListener listener) {
        this.listener = listener;
    }


    public GenderDialog(@NonNull Context context) {
        super(context, R.style.dialog);
        View view = LayoutInflater.from(context).inflate(R.layout.gender_dialog, null);
        setContentView(view);
        mGenderMan = view.findViewById(R.id.gender_man);
        mGenderFemale = view.findViewById(R.id.gender_female);
        mGenderMan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null != listener)
                    listener.onClick(GENDER_MAN);
                dismiss();
            }
        });
        mGenderFemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null != listener)
                    listener.onClick(GENDER_FEMALE);
                dismiss();
            }
        });
    }


    @Override
    public void dismiss() {
        try {
            super.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
