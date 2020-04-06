package com.project.movice.widget.behavior;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ZoomButtonsController;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MyWebView extends WebView {
    private Context mContext;
    private int mProgress = 100;
    private boolean mIsLoading = false;
    private String mLoadedUrl;
    private static boolean mBoMethodsLoaded = false;
    private static Method mOnPauseMethod = null;
    private static Method mOnResumeMethod = null;
    private static Method mSetFindIsUp = null;
    private static Method mNotifyFindDialogDismissed = null;

    public MyWebView(Context context) {
        super(context);
        this.mContext = context;
        this.initializeOptions();
        this.loadMethods();
    }

    public MyWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        this.initializeOptions();
        this.loadMethods();
    }

    @SuppressLint({"NewApi"})
    public void initializeOptions() {
        WebSettings settings = this.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setLoadsImagesAutomatically(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setSaveFormData(true);
        settings.setSavePassword(true);
        settings.setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
        if(Build.VERSION.SDK_INT >= 8) {
            settings.setPluginState(WebSettings.PluginState.ON);
        }

        settings.setSupportZoom(true);
        if(Build.VERSION.SDK_INT >= 11) {
            settings.setBuiltInZoomControls(true);
            settings.setDisplayZoomControls(false);
        } else {
            this.getControlls();
        }

        settings.setSupportMultipleWindows(true);
        this.setLongClickable(true);
        this.setScrollbarFadingEnabled(true);
        //this.setScrollBarStyle(0);
        this.setDrawingCacheEnabled(true);
        settings.setAppCacheEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setDomStorageEnabled(true);
    }

    private void getControlls() {
        try {
            Class e = Class.forName("android.webkit.WebView");
            Method method = e.getMethod("getZoomButtonsController", new Class[0]);
            ZoomButtonsController var3 = (ZoomButtonsController)method.invoke(this, new Object[]{Boolean.valueOf(true)});
        } catch (Exception var4) {
            var4.printStackTrace();
        }

    }


    public boolean onTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        if(action != 0 && action != 5 && action != 5 && action != 261 && action != 517) {
            if(action == 1 || action == 6 || action == 6 || action == 262 || action == 518) {
                this.getSettings().setBuiltInZoomControls(false);
                this.getSettings().setSupportZoom(false);
            }
        } else if(ev.getPointerCount() > 1) {
            this.getSettings().setBuiltInZoomControls(true);
            this.getSettings().setSupportZoom(true);
        } else {
            this.getSettings().setBuiltInZoomControls(false);
            this.getSettings().setSupportZoom(false);
        }

        return super.onTouchEvent(ev);
    }

    public void loadUrl(String url) {
        this.mLoadedUrl = url;
        super.loadUrl(url);
    }

    public void loadAdSweep() {
    }

    public void setProgress(int progress) {
        this.mProgress = progress;
    }

    public int getProgress() {
        return this.mProgress;
    }

    public void notifyPageStarted() {
        this.mIsLoading = true;
    }

    public void notifyPageFinished() {
        this.mProgress = 100;
        this.mIsLoading = false;
    }

    public boolean isLoading() {
        return this.mIsLoading;
    }

    public String getLoadedUrl() {
        return this.mLoadedUrl;
    }

    public void resetLoadedUrl() {
        this.mLoadedUrl = null;
    }

    public boolean isSameUrl(String url) {
        return url != null?url.equalsIgnoreCase(this.getUrl()):false;
    }

    public void doOnPause() {
        if(mOnPauseMethod != null) {
            try {
                mOnPauseMethod.invoke(this, new Object[0]);
            } catch (IllegalArgumentException var2) {
                Log.e("CustomWebView", "doOnPause(): " + var2.getMessage());
            } catch (IllegalAccessException var3) {
                Log.e("CustomWebView", "doOnPause(): " + var3.getMessage());
            } catch (InvocationTargetException var4) {
                Log.e("CustomWebView", "doOnPause(): " + var4.getMessage());
            }
        }

    }

    public void doOnResume() {
        if(mOnResumeMethod != null) {
            try {
                mOnResumeMethod.invoke(this, new Object[0]);
            } catch (IllegalArgumentException var2) {
                Log.e("CustomWebView", "doOnResume(): " + var2.getMessage());
            } catch (IllegalAccessException var3) {
                Log.e("CustomWebView", "doOnResume(): " + var3.getMessage());
            } catch (InvocationTargetException var4) {
                Log.e("CustomWebView", "doOnResume(): " + var4.getMessage());
            }
        }

    }

    public void doSetFindIsUp(boolean value) {
        if(mSetFindIsUp != null) {
            try {
                mSetFindIsUp.invoke(this, new Object[]{Boolean.valueOf(value)});
            } catch (IllegalArgumentException var3) {
                Log.e("CustomWebView", "doSetFindIsUp(): " + var3.getMessage());
            } catch (IllegalAccessException var4) {
                Log.e("CustomWebView", "doSetFindIsUp(): " + var4.getMessage());
            } catch (InvocationTargetException var5) {
                Log.e("CustomWebView", "doSetFindIsUp(): " + var5.getMessage());
            }
        }

    }

    public void doNotifyFindDialogDismissed() {
        if(mNotifyFindDialogDismissed != null) {
            try {
                mNotifyFindDialogDismissed.invoke(this, new Object[0]);
            } catch (IllegalArgumentException var2) {
                Log.e("CustomWebView", "doNotifyFindDialogDismissed(): " + var2.getMessage());
            } catch (IllegalAccessException var3) {
                Log.e("CustomWebView", "doNotifyFindDialogDismissed(): " + var3.getMessage());
            } catch (InvocationTargetException var4) {
                Log.e("CustomWebView", "doNotifyFindDialogDismissed(): " + var4.getMessage());
            }
        }

    }

    private void loadMethods() {
        if(!mBoMethodsLoaded) {
            try {
                mOnPauseMethod = WebView.class.getMethod("onPause", new Class[0]);
                mOnResumeMethod = WebView.class.getMethod("onResume", new Class[0]);
            } catch (SecurityException var4) {
                Log.e("CustomWebView", "loadMethods(): " + var4.getMessage());
                mOnPauseMethod = null;
                mOnResumeMethod = null;
            } catch (NoSuchMethodException var5) {
                Log.e("CustomWebView", "loadMethods(): " + var5.getMessage());
                mOnPauseMethod = null;
                mOnResumeMethod = null;
            }

            try {
                mSetFindIsUp = WebView.class.getMethod("setFindIsUp", new Class[]{Boolean.TYPE});
                mNotifyFindDialogDismissed = WebView.class.getMethod("notifyFindDialogDismissed", new Class[0]);
            } catch (SecurityException var2) {
                Log.e("CustomWebView", "loadMethods(): " + var2.getMessage());
                mSetFindIsUp = null;
                mNotifyFindDialogDismissed = null;
            } catch (NoSuchMethodException var3) {
                Log.e("CustomWebView", "loadMethods(): " + var3.getMessage());
                mSetFindIsUp = null;
                mNotifyFindDialogDismissed = null;
            }

            mBoMethodsLoaded = true;
        }

    }
}
