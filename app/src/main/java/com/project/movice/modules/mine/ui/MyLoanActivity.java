package com.project.movice.modules.mine.ui;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.project.movice.R;
import com.project.movice.base.activity.BaseActivity;
import com.project.movice.modules.mine.adapter.MyLoadMoneyAdapter;
import com.project.movice.modules.mine.bean.BeanMyLoan;
import com.project.movice.modules.mine.contract.MyLoadContract;
import com.project.movice.modules.mine.presenter.MyLoadPresenter;
import com.project.movice.utils.Constant;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

import static com.project.movice.modules.loan.ui.BaseActivity.showActivity;

/**
 * 我的借款列表
 * Created by wy on 2018/1/31 18:34.
 */


public class MyLoanActivity extends BaseActivity<MyLoadPresenter> implements MyLoadContract.View, SwipeRefreshLayout.OnRefreshListener
       {

    public static final String KEY_STATUS = "status";


    @BindView(R.id.rv_content)
    RecyclerView rv_content;

    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipe_refresh;

    private MyLoadMoneyAdapter adapter;

    private int status = Constant.LOAN_ORDER_ALL;

    private int pager = 0;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_title)
    TextView mTitle;

    @Override
    protected int getLayoutId() {
        return R.layout.my_loan;
    }

    @Override
    public void initView() {
        swipe_refresh.setOnRefreshListener(this);
        rv_content.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyLoadMoneyAdapter(this, null);
//        adapter.setOnLoadMoreListener(this, rv_content);
        View view=View.inflate(MyLoanActivity.this,R.layout.empty_view,null);
        rv_content.setAdapter(adapter);
        adapter.setEmptyView(view);
    }


    @Override
    protected void initToolbar() {
        Bundle bundle = getIntent().getExtras();
        if (null != bundle) {
            status = bundle.getInt(KEY_STATUS);
        }
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        if (status == Constant.LOAN_ORDER_REPAYMENT_COMPLETED) {
            mTitle.setText(R.string.repayment_completed);
        } else {
            mTitle.setText(R.string.i_borrow);
        }

        mToolbar.setNavigationOnClickListener(v -> onBackPressedSupport());
    }

    @Override
    protected void initEventAndData() {
        onRefresh();
    }


    @Override
    public void get030(List<BeanMyLoan> response) {
             if(pager==1){
                 adapter.setNewData(response);
             }else{
                 adapter.addData(response);
             }
        showRefreshView(false);
    }

    @Override
    public void onRefresh() {
        pager=1;
        Map<String, String> params = new HashMap<>();
        params.put("status", status + "");
        params.put("page", pager + "");
        params.put("pageSize", 50 + "");
        mPresenter.request030(params);
    }

//    @Override
//    public void onLoadMoreRequested() {
//        pager++;
//        Map<String, String> params = new HashMap<>();
//        params.put("status", status + "");
//        params.put("page", pager + "");
//        params.put("pageSize", 10 + "");
//        mPresenter.request030(params);
//    }


    public void showRefreshView(final Boolean refresh) {
        swipe_refresh.post(new Runnable() {
            @Override
            public void run() {
                swipe_refresh.setRefreshing(refresh);
            }
        });
    }
}
