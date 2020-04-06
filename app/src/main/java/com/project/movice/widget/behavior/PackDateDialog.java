package com.project.movice.widget.behavior;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.project.movice.R;

import butterknife.ButterKnife;

/**
 * 日期选择
 * Created by wy on 2018/1/12 13:15.
 */


public class PackDateDialog extends Dialog {

    private Context mContext;
    private PickTimeView mPickTime;
    private TextView mConfirm;
    private long time;

    public interface onSelectedChangeListener {
        void onSelected(long timeMillis);
    }

    private onSelectedChangeListener mOnSelectedChangeListener;
    /**
     * 设置选中监听回调
     *
     * @param listener
     */
    public void setOnSelectedChangeListener(onSelectedChangeListener listener) {
        this.mOnSelectedChangeListener = listener;
    }

    public PackDateDialog(@NonNull Context context) {
        super(context, R.style.dialog);
        this.mContext = context;
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = LayoutInflater.from(context).inflate(R.layout.pack_date, null);
        setContentView(view);
        mPickTime = ButterKnife.findById(view, R.id.pickDate);
        mConfirm = ButterKnife.findById(view, R.id.confirm);
        mPickTime.setViewType(PickTimeView.TYPE_PICK_DATE);
        mPickTime.setOnSelectedChangeListener(new PickTimeView.onSelectedChangeListener() {
            @Override
            public void onSelected(PickTimeView view, long timeMillis) {
                time = timeMillis;
            }
        });
        mConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /**
                 * 向外部发送当前选中时间
                 */
                if (mOnSelectedChangeListener != null) {
                    mOnSelectedChangeListener.onSelected(time);
                }
                dismiss();
            }
        });
    }

    @Override
    public void show() {
        super.show();
    }
}
