package com.project.movice.modules.mine.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.project.movice.R;
import com.project.movice.modules.home.adappter.BaseRecyclerAdapter;
import com.project.movice.modules.loan.ui.BaseActivity;
import com.project.movice.modules.mine.adapter.HelperAdapter;
import com.project.movice.modules.mine.bean.BeanHelper;
import com.project.movice.utils.GsonUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by wy on 2018/9/8 16:30.
 */


public class FAQListActivity extends BaseActivity implements BaseRecyclerAdapter.OnItemClickListener {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    HelperAdapter adapter;
    List<BeanHelper.Helper> list = new ArrayList<>();
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_title)
    TextView mTitle;
    @Override
    protected int getContentView() {
        return R.layout.faq_list;
    }

    @Override
    public void initView() {

        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        mTitle.setText(R.string.shushou);
        mToolbar.setNavigationOnClickListener(v -> onBackPressedSupport());



        adapter = new HelperAdapter(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
    }


    @Override
    public void initData() {
        getHelper();
    }

    @Override
    public void initBundle(Bundle bundle, Bundle savedInstanceState) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.left_image:
                exit();
                break;

        }
    }


    String json = "{\n" +
            "\t\"data\": [{\n" +
            "\n" +
            "\t\t\t\"title\": \"Bagaimana cara mendaftar?\",\n" +
            "\t\t\t\"subtitle\": \"1.Bagaimana cara mendaftar?\",\n" +
            "\t\t\t\"id\": \"1\"\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\n" +
            "\t\t\t\"title\": \"Bagaimana cara mengembalikan pinjaman?\",\n" +
            "\t\t\t\"subtitle\": \"2.Bagaimana cara mengembalikan pinjaman?\",\n" +
            "\t\t\t\"id\": \"2\"\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\n" +
            "\t\t\t\"title\": \"Mengapa tidak lolos verifikasi?\",\n" +
            "\t\t\t\"subtitle\": \"3.Mengapa tidak lolos verifikasi?\",\n" +
            "\t\t\t\"id\": \"3\"\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\n" +
            "\t\t\t\"title\": \"Apakah privasi saya terjamin?\",\n" +
            "\t\t\t\"subtitle\": \"4.Apakah privasi saya terjamin?\",\n" +
            "\t\t\t\"id\": \"4\"\n" +
            "\t\t}\n" +
            "\t]\n" +
            "}\n" +
            "\n";

    /**
     * 获取信息
     */
    private void getHelper() {
        try {
            BeanHelper bh = GsonUtil.GsonToBean(json, BeanHelper.class);
            List<BeanHelper.Helper> data = bh.getData();
            list.addAll(data);
            adapter.resetItem(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onItemClick(int position, long itemId) {
        BeanHelper.Helper helper = list.get(position);
        Bundle bundle = new Bundle();
        bundle.putString("title", helper.getTitle());
        bundle.putString("id", helper.getId());
        showActivity(this, HelperDetailsActivity.class, bundle);
    }
}
