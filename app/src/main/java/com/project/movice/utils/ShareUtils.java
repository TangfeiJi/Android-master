package com.project.movice.utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;

/**
 * Created by wy on 2018/2/9 17:51.
 */


public class ShareUtils {
    private static CallbackManager callbackManager;

    public static void shareFacebook(Fragment fragment, String uri) {
        com.facebook.share.widget.ShareDialog mFBShareDialog = new com.facebook.share.widget.ShareDialog(fragment);
        facebook(mFBShareDialog, uri);
    }

    public static void shareFacebook(Activity activity, String uri) {
        com.facebook.share.widget.ShareDialog mFBShareDialog = new com.facebook.share.widget.ShareDialog(activity);
        facebook(mFBShareDialog, uri);
    }

    private static void facebook(com.facebook.share.widget.ShareDialog mFBShareDialog, String uri) {
        callbackManager = CallbackManager.Factory.create();
        ShareLinkContent.Builder mShareLinkBuilder = new ShareLinkContent.Builder();
        mShareLinkBuilder.setContentUrl(Uri.parse(uri));
        ShareLinkContent mShareLink = mShareLinkBuilder.build();
        mFBShareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
            @Override
            public void onSuccess(Sharer.Result result) {
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        }, 1000);
        mFBShareDialog.show(mShareLink);
    }

    public static void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (null != callbackManager)
            callbackManager.onActivityResult(requestCode, resultCode, data);
    }

}
