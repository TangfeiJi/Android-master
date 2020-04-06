package com.project.movice.widget.behavior;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.project.movice.R;
import com.project.movice.modules.loan.bean.PayrollDetails;
import com.project.movice.modules.loan.bean.PayrollType;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * 日期选择
 * Created by wy on 2018/3/7 16:15.
 */


public class PackPayDayDialog extends Dialog {

    private Context mContext;
    private PickPayDayView mPickTime;
    private TextView mConfirm;
    private Object left;
    private Object right;
    List<PayrollType> list = new ArrayList<>();
    private String[] mingguan = new String[]{"Senin", "Selasa", "Rabu", "Kamis", "Jumat", "Sabtu", "Minggu"};

    public interface onSelectedChangeListener {
        void onSelected(Object leftValue, Object rightValue);
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

    public PackPayDayDialog(@NonNull Context context) {
        super(context, R.style.dialog);
        this.mContext = context;
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = LayoutInflater.from(context).inflate(R.layout.pack_pay_day, null);
        setContentView(view);
        mPickTime = ButterKnife.findById(view, R.id.pickPayDay);
        mConfirm = ButterKnife.findById(view, R.id.confirm);

        PayrollType pay = new PayrollType();
        PayrollDetails key = new PayrollDetails();
        List<PayrollDetails> value = new ArrayList<>();
        key.setCode("1");
        key.setName("Mingguan");
        for (int i = 0; i < mingguan.length; i++) {
            PayrollDetails details = new PayrollDetails();
            details.setCode("" + i);
            details.setName(mingguan[i]);
            value.add(details);
        }
        pay.setPayKey(key);
        pay.setPayValue(value);
        PayrollType pay1 = new PayrollType();
        PayrollDetails key1 = new PayrollDetails();
        List<PayrollDetails> value1 = new ArrayList<>();
        key1.setCode("2");
        key1.setName("Bulanan");
        for (int i = 0; i < 31; i++) {
            PayrollDetails details = new PayrollDetails();
            details.setCode("" + i);
            details.setName("" + (i + 1));
            value1.add(details);
        }
        pay1.setPayKey(key1);
        pay1.setPayValue(value1);
        list.add(pay);
        list.add(pay1);


        mPickTime.setValueData(list);
        mPickTime.setOnSelectedChangeListener(new PickPayDayView.onSelectedChangeListener() {
            @Override
            public void onSelected(PickPayDayView view, Object leftValue, Object rightValue) {
                left = leftValue;
                right = rightValue;
            }
        });
        mConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /**
                 * 向外部发送当前选中时间
                 */
                if (mOnSelectedChangeListener != null) {
                    mOnSelectedChangeListener.onSelected(left, right);
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
