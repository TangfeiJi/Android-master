package com.project.movice.modules.loan.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.ImageViewState;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.project.movice.R;
import com.project.movice.utils.GlideImageLoader;

import java.io.File;

import butterknife.BindView;

/**
 * 大图预览
 * Created by huanghaibin on 2017/9/27.
 */

public class LargeImageActivity extends BaseActivity {

    @BindView(R.id.imageView)
    SubsamplingScaleImageView mImageView;

    private String mPath;
    protected RequestManager mImageLoader;

    public static void show(Context context, String image) {
        Intent intent = new Intent(context, LargeImageActivity.class);
        intent.putExtra("image", image);
        context.startActivity(intent);
    }

    public synchronized RequestManager getImageLoader() {
        if (mImageLoader == null)
            mImageLoader = Glide.with(this);
        return mImageLoader;
    }

    @Override
    protected int getContentView() {
        return R.layout.activi_larger_img;
    }

    @Override
    public void initView() {
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
//        mImageView.setMinScale(1.0F);//最小显示比例
//        mImageView.setMaxScale(1.0F);//最大显示比例（太大了图片显示会失真，因为一般微博长图的宽度不会太宽）
//        mImageView.setZoomEnabled(true);
//        mImageView.setMinimumScaleType(SubsamplingScaleImageView.SCALE_TYPE_CUSTOM);
    }

    @Override
    public void initData() {
        mPath = getIntent().getStringExtra("image");


        //使用Glide下载图片,保存到本地
        Glide.with(this)
                .download(mPath)
                .into(new SimpleTarget<File>() {
                    @Override
                    public void onLoadFailed(@Nullable Drawable errorDrawable) {
                        super.onLoadFailed(errorDrawable);
                        Log.d("load failed", "nothing");
                    }

                    @Override
                    public void onResourceReady(File resource, Transition<? super File> transition) {
                        mImageView.setImage(ImageSource.uri(resource.getAbsolutePath()));
                        mImageView.setMaxScale(10f);
                    }
                });


        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exit();
            }
        });
    }

    @Override
    public void initBundle(Bundle bundle, Bundle savedInstanceState) {

    }

}
