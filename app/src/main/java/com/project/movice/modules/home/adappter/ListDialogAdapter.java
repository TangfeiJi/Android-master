package com.project.movice.modules.home.adappter;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.project.movice.R;
import com.project.movice.dialog.OnItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wy on 2018/1/10 15:15.
 */


public class ListDialogAdapter extends RecyclerView.Adapter<ListDialogAdapter.MyViewHolder> implements View.OnClickListener {

    private List<String> mList;
    private Context mContext;
    private int noClick;

    private OnItemClickListener mOnItemClickListener = null;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public ListDialogAdapter(Context context, List<String> list) {
        this.mContext = context;
        this.mList = list;
    }

    public ListDialogAdapter(Context context, List<String> list, int noClick) {
        this.mContext = context;
        this.mList = list;
        this.noClick = noClick;
    }

    @Override
    public ListDialogAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_dialog_ite1m, parent,
                false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ListDialogAdapter.MyViewHolder holder, int position) {
        holder.tv.setText(mList.get(position));
        holder.tv.requestFocus();
        holder.tv.setOnClickListener(this);
        holder.tv.setTag(position);
        if (noClick == 1&&position!=0) {
            holder.tv.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG); // 设置中划线并加清晰

        }
        if (position == 0) {
            if (position == getItemCount() - 1) {
//                holder.tv.setBackgroundResource(R.drawable.bt_selector_white_round);
                holder.mDividerLine.setVisibility(View.GONE);
            } else {
//                holder.tv.setBackgroundResource(R.drawable.bt_selector_white_top);
                holder.mDividerLine.setVisibility(View.VISIBLE);
            }
        } else if (position == getItemCount() - 1) {
//            holder.tv.setBackgroundResource(R.drawable.bt_selector_white_bottom);
            holder.mDividerLine.setVisibility(View.GONE);
        } else {
            holder.mDividerLine.setVisibility(View.VISIBLE);
        }
        holder.tv.setBackgroundResource(R.drawable.bt_selector_white_shape);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public void onClick(View view) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取position
            mOnItemClickListener.onItemClick(view, (Integer) view.getTag());
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv)
        TextView tv;
        @BindView(R.id.divider_line)
        View mDividerLine;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
