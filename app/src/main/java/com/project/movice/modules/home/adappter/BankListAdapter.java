package com.project.movice.modules.home.adappter;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.project.movice.R;
import com.project.movice.modules.home.bean.BeanBank;
import com.project.movice.utils.GlideImageLoader;

/**
 * Created by wy on 2018/1/23 15:54.
 */


public class BankListAdapter extends BaseRecyclerAdapter<BeanBank> {
    private Context mContext;


    public BankListAdapter(Context context) {
        super(context);
        this.mContext = context;
    }

    @Override
    protected RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int type) {
        return new SelectBankHolder(mInflater.inflate(R.layout.select_bank_item, parent, false));
    }

    @Override
    protected void onBindDefaultViewHolder(RecyclerView.ViewHolder holder, BeanBank item, int position) {
        SelectBankHolder h = (SelectBankHolder) holder;
        if (null != item) {
            try {
                h.mBankCardNumber.setText(item.getBankName());
                if (position == 0 || position == 1) {
                    h.mRecommended.setVisibility(View.VISIBLE);
                } else {
                    h.mRecommended.setVisibility(View.GONE);
                }
//                Glide.with(mContext).load(item.getImageDomain() + item.getBankLogo()).placeholder(R.mipmap.lingyi32).into(h.mBankLogo);
                GlideImageLoader.load(mContext,item.getImageDomain() + item.getBankLogo(),h.mBankLogo);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }


    private static class SelectBankHolder extends RecyclerView.ViewHolder {
        ImageView mBankLogo;
        TextView mBankCardNumber;
        ImageView mRecommended;

        public SelectBankHolder(View itemView) {
            super(itemView);
            mBankLogo = (ImageView) itemView.findViewById(R.id.bank_logo);
            mBankCardNumber = (TextView) itemView.findViewById(R.id.bank_card_number);
            mRecommended = (ImageView) itemView.findViewById(R.id.recommended);
        }
    }


}
