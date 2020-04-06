package com.project.movice.modules.main.ui.activity;

import android.content.Intent;
import android.graphics.LinearGradient;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.movice.R;
import com.project.movice.application.MoviceApp;
import com.project.movice.base.activity.BaseActivity;
import com.project.movice.modules.main.bean.VersionBean;
import com.project.movice.modules.main.contract.SwichContract;
import com.project.movice.modules.main.presenter.SwichPresenter;
import com.project.movice.modules.mine.ui.LoanDetailsActivity;
import com.project.movice.utils.DataUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class SwichActivity extends BaseActivity<SwichPresenter> implements SwichContract.View {
    @BindView(R.id.llClose)
    LinearLayout llClose;
    private VersionBean versionBean;


    @BindView(R.id.webview)
    WebView webview;


    @Override
    protected void initView() {

        webview.loadUrl("file:///android_asset/xieyi.html");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_swich;
    }

    @Override
    protected void initToolbar() {

    }

    @Override
    protected void initEventAndData() {
        mPresenter.requestVersion();
    }

    @Override
    public void getVersion(VersionBean response) {
        versionBean=response;
        MoviceApp.crawlApp = (response.isCrawlApp());
        DataUtils.put(SwichActivity.this, "logintype",  response.getLoginMethod());
        DataUtils.put(SwichActivity.this, "phone", response.getTelephone());
        MoviceApp.callPhone = response.getTelephone();
        DataUtils.put(SwichActivity.this, "telphone", response.getFroceAuth());
        DataUtils.put(SwichActivity.this, "shujumohe", response.getFroceAuth());
        DataUtils.put(SwichActivity.this, "getMohe_code", response.getMohe_code());
        DataUtils.put(SwichActivity.this, "getMohe_key", response.getMohe_key());
        DataUtils.put(SwichActivity.this, "apikey", response.getFace_key());
        DataUtils.put(SwichActivity.this, "apisecret", response.getFace_secret());
        DataUtils.put(SwichActivity.this, "apivalue", response.getFace_pass_value());

        if(!DataUtils.getSwich(SwichActivity.this)){
            llClose.setVisibility(View.VISIBLE);
            return;
        }else{
            jumpActivity(response);
        }



    }

    @Override
    public void showErrorData() {

    }


    private void jumpActivity(VersionBean response){
        if (response.getIs_loanmarket() == 1) {
            Intent intent = new Intent(SwichActivity.this, WebViewActivity.class);
            intent.putExtra("type", response.getIs_back());
//            intent.putExtra("type",1);
            startActivity(intent);
            finish();
        } else {
            startActivity(new Intent(SwichActivity.this, MainActivity.class));
            finish();
        }

    }


    @OnClick({R.id.llClose})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llClose://
            DataUtils.setSwich(SwichActivity.this,true);
                jumpActivity(versionBean);
                break;
        }
    }


}
