package com.project.movice.modules.mine.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.movice.R;
import com.project.movice.modules.home.adappter.BaseRecyclerAdapter;
import com.project.movice.modules.mine.bean.BeanHelperDetails;
import com.project.movice.modules.mine.ui.HelperDetailsActivity;
import com.project.movice.utils.MyOnClickListener;


/**
 * 助手详情
 * Created by wy on 2018/1/27 11:53.
 */


public class HelperDetailsAdapter extends BaseRecyclerAdapter<BeanHelperDetails.HelperDetails> {

    public OnClickListener listener;

    public void setOnClickListener(OnClickListener listener) {
        this.listener = listener;
    }

    public interface OnClickListener {
        void onClick(int position, int type);
    }


    public HelperDetailsAdapter(Context context) {
        super(context);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int type) {
        return new HelperDetailsAdapter.HelperDetailsHolder(mInflater.inflate(R.layout.helper_details_item, parent, false));
    }

    @Override
    protected void onBindDefaultViewHolder(RecyclerView.ViewHolder holder, BeanHelperDetails.HelperDetails item, int position) {
        HelperDetailsHolder h = (HelperDetailsHolder) holder;
        if (null != item) {
            h.mTitle.setText((position+1)+"."+item.getTitle());
            h.mContent.setText(item.getContent());
            h.mAgreeNumber.setText(item.getAgree() + "");
            h.mDisagreeNumber.setText(item.getDisagree() + "");
            h.mAgree.setBackgroundResource(R.mipmap.uangpro123);
            h.mDisagree.setBackgroundResource(R.mipmap.lingyi27);
            if (item.isShowDetails()) {
                h.line.setVisibility(View.VISIBLE);
                h.line1.setVisibility(View.GONE);
                h.mDetailsLayout.setVisibility(View.VISIBLE);
                h.mIcon.setRotation(180);
            } else {
                h.line.setVisibility(View.GONE);
                h.line1.setVisibility(View.VISIBLE);
                h.mDetailsLayout.setVisibility(View.GONE);
                h.mIcon.setRotation(0);
            }
            if (item.isAgree() || item.isDisagree()) {
                h.mAgreeLayout.setOnClickListener(null);
                h.mDisagreeLayout.setOnClickListener(null);
                if (item.isAgree()) {
                    h.mAgree.setBackgroundResource(R.mipmap.sadadv142);
                    h.mDisagree.setBackgroundResource(R.mipmap.lingyi27);
                } else {
                    h.mAgree.setBackgroundResource(R.mipmap.uangpro123);
                    h.mDisagree.setBackgroundResource(R.mipmap.lingyi42);
                }
            } else {
                h.mAgreeLayout.setTag(position);
                h.mAgreeLayout.setOnClickListener(onAgree);
                h.mDisagreeLayout.setTag(position);
                h.mDisagreeLayout.setOnClickListener(onDisagree);
            }
        }
    }

    MyOnClickListener onAgree = new MyOnClickListener() {
        @Override
        public void onClick(View v) {
            listener.onClick((Integer) v.getTag(), HelperDetailsActivity.TYPE_AGREE);
        }
    };
    MyOnClickListener onDisagree = new MyOnClickListener() {
        @Override
        public void onClick(View v) {
            listener.onClick((Integer) v.getTag(), HelperDetailsActivity.TYPE_DISAGREE);
        }
    };

    private static class HelperDetailsHolder extends RecyclerView.ViewHolder {
        TextView mTitle;
        TextView mContent;
        TextView mAgreeNumber;
        TextView mDisagreeNumber;
        LinearLayout mDetailsLayout;
        LinearLayout mAgreeLayout;
        LinearLayout mDisagreeLayout;
        ImageView mIcon;
        ImageView mAgree;
        ImageView mDisagree;
        View line;
        View line1;

        public HelperDetailsHolder(View itemView) {
            super(itemView);
            mTitle = (TextView) itemView.findViewById(R.id.title);
            mContent = (TextView) itemView.findViewById(R.id.content);
            mDetailsLayout = (LinearLayout) itemView.findViewById(R.id.details_layout);
            mAgreeLayout = (LinearLayout) itemView.findViewById(R.id.agree_layout);
            mDisagreeLayout = (LinearLayout) itemView.findViewById(R.id.disagree_layout);
            mIcon = (ImageView) itemView.findViewById(R.id.icon);
            mAgree = (ImageView) itemView.findViewById(R.id.agree);
            mDisagree = (ImageView) itemView.findViewById(R.id.disagree);
            mAgreeNumber = (TextView) itemView.findViewById(R.id.agree_number);
            mDisagreeNumber = (TextView) itemView.findViewById(R.id.disagree_number);
            line = (View) itemView.findViewById(R.id.divider_line);
            line1 = (View) itemView.findViewById(R.id.divider_line1);
        }
    }
}
