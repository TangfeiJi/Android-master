package com.project.movice.modules.area;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.project.movice.R;
import com.project.movice.modules.home.adappter.BaseRecyclerAdapter;


/**
 * 消息
 * Created by wy on 2018/1/27 11:53.
 */


public class AreaAdapter extends BaseRecyclerAdapter<BeanAreaInfo> {

    private Context mContext;

    public AreaAdapter(Context context) {
        super(context);
        this.mContext = context;
    }

    @Override
    protected RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int type) {
        return new AreaAdapter.AreaHolder(mInflater.inflate(R.layout.area_item, parent, false));
    }

    @Override
    protected void onBindDefaultViewHolder(RecyclerView.ViewHolder holder, BeanAreaInfo item, int position) {
        AreaHolder h = (AreaHolder) holder;
        if (null != item) {
            h.mTitle.setText(item.getName());
        }

    }


    private static class AreaHolder extends RecyclerView.ViewHolder {
        TextView mTitle;

        public AreaHolder(View itemView) {
            super(itemView);
            mTitle = (TextView) itemView.findViewById(R.id.title);
        }
    }
}
