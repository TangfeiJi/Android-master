package com.project.movice.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.project.movice.R;
import com.project.movice.modules.home.adappter.ListDialogAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * 列表选择
 * Created by wy on 2018/1/10 15:04.
 */


public class ListDialog extends Dialog {

    private Context mContext;
    private ListDialogAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private List<String> mList = new ArrayList<>();

private int noClick;
    private OnItemClickListener mOnItemClickListener = null;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public ListDialog(@NonNull Context context, String[] list) {
        super(context, R.style.dialog);
        this.mContext = context;
        if(null != list) {
            for (int i=0; i<list.length; i++) {
                mList.add(list[i]);
            }
        }
        initView();
    }

    public ListDialog(@NonNull Context context, String[] list,int noClick) {
        super(context, R.style.dialog);
        this.mContext = context;
        this.noClick=noClick;
        if(null != list) {
            for (int i=0; i<list.length; i++) {
                mList.add(list[i]);
            }
        }
        initView();
    }

    public ListDialog(@NonNull Context context, List<String> list) {
        super(context, R.style.dialog);
        this.mContext = context;
        this.mList = list;
        initView();
    }

    private void initView() {
//        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_dialog, null);
        setContentView(view);
        mRecyclerView = ButterKnife.findById(view, R.id.recycler_view);
        //添加自定义分割线
//        MyDividerItemDecoration divider = new MyDividerItemDecoration(context, MyDividerItemDecoration.VERTICAL);
//        divider.setDrawable(ContextCompat.getDrawable(context,R.drawable.divider));
//        mRecyclerView.addItemDecoration(divider);

        mAdapter = new ListDialogAdapter(mContext, mList,noClick);
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                mOnItemClickListener.onItemClick(view, position);
                dismiss();
            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setAdapter(mAdapter);
    }
}
