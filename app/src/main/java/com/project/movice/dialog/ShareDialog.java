package com.project.movice.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.project.movice.R;
import com.project.movice.utils.MyOnClickListener;


/**
 * 分享
 * Created by wy on 2018/2/9 14:28.
 */


public class ShareDialog extends Dialog {

    public static final int KEY_FACEBOOK = 0x01;

    private Context mContext;
    private LinearLayout mFaceBook;

    public ShareDialog(@NonNull Context context) {
        super(context, R.style.dialog);
        this.mContext = context;
        View view = LayoutInflater.from(mContext).inflate(R.layout.share_dialog, null);
        setContentView(view);
        mFaceBook = view.findViewById(R.id.share_facebook);
        mFaceBook.setOnClickListener(onClick);

        Window window = getWindow();
        // 设置显示动画
        Activity activity = (Activity) context;
        window.setWindowAnimations(R.style.main_menu_animstyle);
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.x = 0;
        wl.y = activity.getWindowManager().getDefaultDisplay().getHeight();
        // 以下这两句是为了保证按钮可以水平满屏
        wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
        wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        // 设置显示位置
        onWindowAttributesChanged(wl);
    }


    MyOnClickListener onClick = new MyOnClickListener() {
        @Override
        public void onMyClick(View v) {
            switch (v.getId()) {
                case R.id.share_facebook:
                    if (null != listener) {
                        listener.onShareType(KEY_FACEBOOK);
                    }
                    break;
            }
            dismiss();
        }
    };

    @Override
    public void show() {
        super.show();

    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

    private OnShareClickListener listener;

    public interface OnShareClickListener {
        public void onShareType(int type);
    }

    public void setOnShareClickListener(OnShareClickListener listener) {
        this.listener = listener;
    }


}
