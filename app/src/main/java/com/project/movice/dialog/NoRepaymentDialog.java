package com.project.movice.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.project.movice.R;

import butterknife.ButterKnife;

/**
 * 未还款提示
 * Created by wy on 2018/1/10 20:44.
 */


public class NoRepaymentDialog extends Dialog {
    private Context mContext;
    private ImageView mImage;
    private TextView mTitle, mContent;
    private Button mBt;

    private OnClickListener listener;

    public interface OnClickListener {
        void onClick(View view);
    }

    public void setOnClickListener(OnClickListener listener) {
        this.listener = listener;
    }

    public void setOnTouchOutside(boolean cancel) {
        setCanceledOnTouchOutside(cancel);
        setCancelable(cancel);
    }


    /**
     * @param context
     * @param image   显示图片
     * @param title   标题
     * @param content 内容
     * @param bt      按钮文字
     * @param isLeft  内容是否靠左，true靠左显示  false靠右
     */
    public NoRepaymentDialog(@NonNull Context context, int image, String title, CharSequence content, String bt, boolean isLeft) {
        super(context, R.style.dialog);
        this.mContext = context;
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = LayoutInflater.from(context).inflate(R.layout.no_repayment, null);
        setContentView(view);
        mImage = ButterKnife.findById(view, R.id.image);
        mTitle = ButterKnife.findById(view, R.id.title);
        mContent = ButterKnife.findById(view, R.id.content);
        mBt = ButterKnife.findById(view, R.id.bt);
        Window window = getWindow() ;
        WindowManager.LayoutParams p = window.getAttributes(); // 获取对话框当前的参数值
        WindowManager m = window.getWindowManager();
        Display d = m.getDefaultDisplay();  //为获取屏幕宽、高
        p.height = (int) (d.getHeight() * 0.9);   //高度设置为屏幕的0.3
        p.width = (int) (d.getWidth() * 0.85);    //宽度设置为屏幕的0.5
        window.setAttributes(p);

        mImage.setBackgroundResource(image);
        mTitle.setText(title);
        mContent.setText(content);
        if (isLeft)
            mContent.setGravity(Gravity.LEFT);
        mBt.setText(bt);
        mBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                if (null != listener) {
                    listener.onClick(view);
                }
            }
        });
    }

}
