package com.project.movice.modules.mine.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextPaint;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.movice.R;
import com.project.movice.modules.home.adappter.BaseRecyclerAdapter;
import com.project.movice.modules.mine.bean.BeanProcessList;
import com.project.movice.utils.DateTimeUtil;


/**
 * 订单详情中状态
 * Created by wy on 2018/1/27 11:53.
 */


public class LoanDetailsAdapter extends BaseRecyclerAdapter<BeanProcessList> {

    private Context mContext;

    public LoanDetailsAdapter(Context context) {
        super(context);
        this.mContext = context;
    }

    @Override
    protected RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int type) {
        return new LoanDetailsAdapter.LoanDetailsHolder(mInflater.inflate(R.layout.order_process_item, parent, false));
    }

    @Override
    protected void onBindDefaultViewHolder(RecyclerView.ViewHolder holder, BeanProcessList item, int position) {
        LoanDetailsHolder h = (LoanDetailsHolder) holder;
        if (null != item) {
            h.mDescription.setText(item.getDescription());
            h.mDate.setText(DateTimeUtil.formatDateTime(item.getDate(), DateTimeUtil.DF_YYYY_MM_DD_HH_MM));
            if (position == 0) {
                h.mIcon.setBackgroundResource(R.mipmap.lingyi52);
                h.mDescription.setTextColor(ContextCompat.getColor(mContext, R.color.character_and_title));
                h.mDate.setTextColor(ContextCompat.getColor(mContext, R.color.character_and_title));
                h.mDescription.setTextSize(TypedValue.COMPLEX_UNIT_PX, mContext.getResources().getDimensionPixelSize(R.dimen.text_size_14));
                h.mDate.setTextSize(TypedValue.COMPLEX_UNIT_PX, mContext.getResources().getDimensionPixelSize(R.dimen.text_size_14));
                TextPaint tp = h.mDescription.getPaint();
                tp.setFakeBoldText(true);
                TextPaint tp1 = h.mDate.getPaint();
                tp1.setFakeBoldText(true);
            } else {
                h.mIcon.setBackgroundResource(R.mipmap.sdfvb143);
                h.mDescription.setTextColor(ContextCompat.getColor(mContext, R.color.auxiliary));
                h.mDate.setTextColor(ContextCompat.getColor(mContext, R.color.auxiliary));
                h.mDescription.setTextSize(TypedValue.COMPLEX_UNIT_PX, mContext.getResources().getDimensionPixelSize(R.dimen.text_size_12));
                h.mDate.setTextSize(TypedValue.COMPLEX_UNIT_PX, mContext.getResources().getDimensionPixelSize(R.dimen.text_size_12));
                TextPaint tp = h.mDescription.getPaint();
                tp.setFakeBoldText(false);
                TextPaint tp1 = h.mDate.getPaint();
                tp1.setFakeBoldText(false);
            }
            if (position == getItemCount() - 1) {
                h.mLine.setVisibility(View.GONE);
            } else {
                h.mLine.setVisibility(View.VISIBLE);
            }
        }

    }


    private static class LoanDetailsHolder extends RecyclerView.ViewHolder {
        ImageView mIcon;
        TextView mDescription;
        TextView mDate;
        View mLine;

        public LoanDetailsHolder(View itemView) {
            super(itemView);
            mIcon = (ImageView) itemView.findViewById(R.id.icon_status);
            mDescription = (TextView) itemView.findViewById(R.id.description);
            mDate = (TextView) itemView.findViewById(R.id.date);
            mLine = (View) itemView.findViewById(R.id.line);
        }
    }
}
