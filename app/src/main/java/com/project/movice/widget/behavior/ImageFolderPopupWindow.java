package com.project.movice.widget.behavior;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.project.movice.R;
import com.project.movice.modules.home.adappter.BaseRecyclerAdapter;
import com.project.movice.modules.loan.adapter.ImageFolderAdapter;
import com.project.movice.modules.loan.bean.ImageFolder;

/**
 * 图片选择器菜单选择界面
 */
public class ImageFolderPopupWindow extends PopupWindow implements
        View.OnAttachStateChangeListener,
        BaseRecyclerAdapter.OnItemClickListener {
    private ImageFolderAdapter mAdapter;
    private RecyclerView mFolderView;
    private Callback mCallback;

     @SuppressLint("InflateParams")
     public ImageFolderPopupWindow(Context context, Callback callback) {
        super(LayoutInflater.from(context).inflate(R.layout.popup_window_folder, null),
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        mCallback = callback;

        // init
        setAnimationStyle(R.style.popup_anim_style_alpha);
        setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setOutsideTouchable(true);
        setFocusable(true);

        // content
        View content = getContentView();
        content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        content.addOnAttachStateChangeListener(this);

        mFolderView = (RecyclerView) content.findViewById(R.id.rv_popup_folder);
        mFolderView.setLayoutManager(new LinearLayoutManager(context));

    }

    public void setAdapter(ImageFolderAdapter adapter) {
        this.mAdapter = adapter;
        mFolderView.setAdapter(adapter);
        mAdapter.setOnItemClickListener(this);
    }

    @Override
    public void showAsDropDown(View anchor) {
        if(Build.VERSION.SDK_INT >= 24){
            Rect visibleFrame = new Rect();
            anchor.getGlobalVisibleRect(visibleFrame);
            int height = anchor.getResources().getDisplayMetrics().heightPixels - visibleFrame.bottom;
            setHeight(height);
        }

        super.showAsDropDown(anchor);
    }

    @Override
    public void onViewAttachedToWindow(View v) {
        final Callback callback = mCallback;
        if (callback != null)
            callback.onShow();
    }

    @Override
    public void onViewDetachedFromWindow(View v) {
        final Callback callback = mCallback;
        if (callback != null)
            callback.onDismiss();
    }

    @Override
    public void onItemClick(int position, long itemId) {
        final Callback callback = mCallback;
        if (callback != null)
            callback.onSelect(this, mAdapter.getItem(position));
    }

    public interface Callback {
        void onSelect(ImageFolderPopupWindow popupWindow, ImageFolder model);

        void onDismiss();

        void onShow();
    }
}
