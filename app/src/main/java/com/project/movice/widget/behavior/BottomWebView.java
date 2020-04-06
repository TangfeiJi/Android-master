package com.project.movice.widget.behavior;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class BottomWebView extends WebView {


    public BottomWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //设置WebViewClient
        initWebViewClient();
    }



    private void initWebViewClient() {
        setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                post(new Runnable() {
                    @Override
                    public void run() {
                        scrollToBottom();
                    }
                });
            }
        });
    }


    //调用此方法可滚动到底部
    public void scrollToBottom() {
        scrollTo(0, (int) (getContentHeight() *getScale()));
    }


}
