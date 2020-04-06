package com.project.movice.di.module;

import android.util.Log;

import okhttp3.logging.HttpLoggingInterceptor;

public class HttpLogger implements HttpLoggingInterceptor.Logger  {
    @Override
    public void log(String message) {
        Log.e("HttpLogInfo", message);
    }
}
