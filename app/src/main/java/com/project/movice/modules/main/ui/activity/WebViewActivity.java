package com.project.movice.modules.main.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.CookieSyncManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.project.movice.R;
import com.project.movice.base.activity.BaseActivity;
import com.project.movice.modules.main.contract.WebViewContract;
import com.project.movice.modules.main.presenter.WebViewPresenter;
import com.project.movice.widget.behavior.MyWebView;

import java.net.URISyntaxException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WebViewActivity extends BaseActivity<WebViewPresenter> implements WebViewContract.View {


    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_title)
    TextView mTitle;
    @BindView(R.id.web_view)
    MyWebView webView;
    private String url = "http://g1.malcolmburung.tech/index_1.html";

    @Override
    protected void initView() {
        showLoading();

        int version = android.os.Build.VERSION.SDK_INT;
//        if (version >= 28) {
//            Intent  intent = null;
//            try {
//                intent = Intent.parseUri("http://g1.malcolmburung.tech/index_1.html", Intent.URI_INTENT_SCHEME);
//                intent.addCategory("android.intent.category.BROWSABLE");
//                intent.setComponent(null);
//                intent.setSelector(null);
//                startActivityIfNeeded(intent, -1);
//            } catch (URISyntaxException e) {
//                e.printStackTrace();
//            }
//        }else{
//            initEventAndData();
//        }






        webView = (MyWebView) findViewById(R.id.web_view);
        WebSettings webSettings = webView.getSettings();
        webSettings.setAppCacheEnabled(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setDomStorageEnabled(true);//开启DOM缓存，关闭的话H5自身的一些操作是无效的
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccess(true);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        webSettings.setBlockNetworkImage(false);
        if (url != null) {
            webView.loadUrl(url);
        }
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                showContent();
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                if (!url.contains("index_1")) {
                    Intent  intent = null;
                    try {
                        intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
                        intent.addCategory("android.intent.category.BROWSABLE");
                        intent.setComponent(null);
                        intent.setSelector(null);
                        startActivityIfNeeded(intent, -1);
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }


                }

            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_web_view;
    }

    @Override
    protected void initToolbar() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {

            actionBar.setDisplayShowTitleEnabled(false);
        }
        mTitle.setText(R.string.fill_in_personal_information);
        Intent intent = getIntent();
//0是不可以返回  1是可以返回
        if (intent.getIntExtra("type", 0) == 1) {
            actionBar.setDisplayHomeAsUpEnabled(true);//给左上角图标的左边加上一个返回的图
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(WebViewActivity.this, MainActivity.class));
                    finish();
                }
            });
        }

    }

    @Override
    protected void initEventAndData() {

    }

    // 销毁Webview
    @Override
    protected void onDestroy() {

        if (webView != null) {
            webView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            webView.clearHistory();

            ViewParent parent = webView.getParent();
            if (parent != null) {
                ((ViewGroup) parent).removeView(webView);
            }
            webView.destroy();
            webView = null;
        }
        super.onDestroy();
    }

    private long mExitTime = 0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            long mCurrentTime = System.currentTimeMillis();
            if ((mCurrentTime - mExitTime) > 2000) {// 如果两次按键时间间隔大于2000毫秒，则不退出
                Toast.makeText(WebViewActivity.this,getResources().getString(R.string.exit_system),Toast.LENGTH_SHORT).show();
                mExitTime = mCurrentTime;// 更新mExitTime
            } else {
                finish();;
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
