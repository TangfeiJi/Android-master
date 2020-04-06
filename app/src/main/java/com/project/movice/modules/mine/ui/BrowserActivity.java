package com.project.movice.modules.mine.ui;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;


import com.project.movice.R;
import com.project.movice.modules.loan.ui.BaseActivity;
import com.project.movice.utils.StringUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 协议，文本展示
 * Created by wy on 2018/3/27 14:00.
 */

public class BrowserActivity extends BaseActivity {


    @BindView(R.id.webview)
    WebView mWebView;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_title)
    TextView mTitle;
    private String url;

    @Override
    protected int getContentView() {
        return R.layout.browsr;
    }

    @Override
    public void initBundle(Bundle bundle, Bundle savedInstanceState) {
        if (null != bundle) {
            url = bundle.getString("url");
        }
    }

    @Override
    public void initView() {

        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        mToolbar.setNavigationOnClickListener(v -> onBackPressedSupport());
        mWebView.setWebChromeClient(wvcc);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setAllowFileAccess(true);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            CookieManager.getInstance().setAcceptThirdPartyCookies(mWebView, true);
        }
        mWebView.setWebViewClient(new MyWebViewClient());
    }

    @Override
    public void initData() {
        mTitle.setText((R.string.error_view_loading));
        mWebView.loadUrl(url);
    }

    @OnClick({R.id.left_image})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.left_image:
                exit();
                break;
        }
    }

    WebChromeClient wvcc = new WebChromeClient() {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            if (!StringUtils.isEmpty(title))

            mTitle.setText(title);
        }
    };

    class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return false;
        }
    }
}
