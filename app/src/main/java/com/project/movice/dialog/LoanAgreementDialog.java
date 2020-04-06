package com.project.movice.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;


import com.project.movice.R;
import com.project.movice.modules.main.bean.BeanUser;
import com.project.movice.utils.DataUtils;
import com.project.movice.utils.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * 借款协议
 * Created by wy on 2018/8/31 18:09.
 */
public class LoanAgreementDialog extends Dialog {

    private Context mContext;
    private WebView mWebView;
//    private EmptyLayout mEmptyLayout;
    private ImageView mLeftIImage;
    private TextView mTitle;

//    private BaseLoanProtocol loanProtocol;

    private String url;
    private String params;
    private String orderId;

    private String creatTime;

    public LoanAgreementDialog(@NonNull Context context, Bundle bundle) {
        super(context, R.style.dialog);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddhhmmss");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        creatTime = formatter.format(curDate);

        this.mContext = context;
        View view = LayoutInflater.from(mContext).inflate(R.layout.loan_agreement, null);
        mWebView = view.findViewById(R.id.webview);
        mLeftIImage = view.findViewById(R.id.left_image);
//        mEmptyLayout = view.findViewById(R.id.emptyLayout);
        mTitle = view.findViewById(R.id.title);



        setContentView(view);
        initData(bundle);
        mTitle.setText(mContext.getResources().getString(R.string.loan_agreement));
        mLeftIImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        Window window = getWindow();
        // 设置显示动画
        Activity activity = (Activity) context;
        window.setWindowAnimations(R.style.main_menu_animstyle);
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.x = 0;
        wl.y = activity.getWindowManager().getDefaultDisplay().getHeight();
        // 以下这两句是为了保证按钮可以水平满屏
        wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
        wl.height = ViewGroup.LayoutParams.MATCH_PARENT;
        // 设置显示位置
        onWindowAttributesChanged(wl);
    }

    private void initData(Bundle bundle) {
        url = bundle.getString("url");
        params = bundle.getString("params");
        orderId = bundle.getString("orderId");
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

        mWebView.loadUrl(url + params);
    }



    WebChromeClient wvcc = new WebChromeClient() {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            if (!StringUtils.isEmpty(title)) {
                mTitle.setText(title);
            }
        }
    };

    class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return false;
        }
    }







}
