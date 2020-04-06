package com.project.movice.modules.mine.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.project.movice.R;
import com.project.movice.modules.home.adappter.BaseRecyclerAdapter;
import com.project.movice.modules.mine.bean.BeanHelper;


/**
 * 助手
 * Created by wy on 2018/1/27 11:53.
 */


public class HelperAdapter extends BaseRecyclerAdapter<BeanHelper.Helper> {

    private Context mContext;

    public HelperAdapter(Context context) {
        super(context);
        this.mContext = context;
    }

    @Override
    protected RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int type) {
        return new HelperAdapter.HelperHolder(mInflater.inflate(R.layout.helper_item, parent, false));
    }

    @Override
    protected void onBindDefaultViewHolder(RecyclerView.ViewHolder holder, BeanHelper.Helper item, int position) {
        HelperHolder h = (HelperHolder) holder;
        if (null != item) {
            h.mTitle.setText(item.getSubtitle());
        }

    }


    private static class HelperHolder extends RecyclerView.ViewHolder {
        TextView mTitle;

        public HelperHolder(View itemView) {
            super(itemView);
            mTitle = (TextView) itemView.findViewById(R.id.title);
        }
    }
}
