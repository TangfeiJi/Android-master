package com.project.movice.modules.loan.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.project.movice.R;
import com.project.movice.modules.home.adappter.BaseRecyclerAdapter;
import com.project.movice.modules.loan.bean.Image;
import com.project.movice.widget.behavior.ImageLoaderListener;


/**
 * 图片列表界面适配器
 */
public class ImageAdapter extends BaseRecyclerAdapter<Image> {
    private ImageLoaderListener loader;
    private boolean isSingleSelect;
    public ImageAdapter(Context context, ImageLoaderListener loader) {
        super(context);
        this.loader = loader;
    }

    //true只显示一张，false多张
    public void setSingleSelect(boolean singleSelect) {
        isSingleSelect = singleSelect;
    }

    @Override
    public int getItemViewType(int position) {
        Image image = getItem(position);
        if (image.getId() == 0)
            return 0;
        return 1;
    }

    @Override
    public void onViewRecycled(RecyclerView.ViewHolder holder) {
        if (holder instanceof ImageViewHolder) {
            ImageViewHolder h = (ImageViewHolder) holder;
//            Glide.clear(h.mImageView);

        }
    }

    @Override
    protected RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int type) {
        if (type == 0)
            return new CamViewHolder(mInflater.inflate(R.layout.item_list_cam, parent, false));
        return new ImageViewHolder(mInflater.inflate(R.layout.item_list_im1age, parent, false));
    }

    @Override
    protected void onBindDefaultViewHolder(RecyclerView.ViewHolder holder, Image item, int position) {
        if (item.getId() != 0) {
            ImageViewHolder h = (ImageViewHolder) holder;
            h.mCheckView.setSelected(item.isSelect());
            h.mMaskView.setVisibility(item.isSelect() ? View.VISIBLE : View.GONE);

            // Show gif mask
            h.mGifMask.setVisibility(item.getPath().toLowerCase().endsWith("gif") ?
                    View.VISIBLE : View.GONE);

            loader.displayImage(h.mImageView, item.getPath());
            h.mCheckView.setVisibility(isSingleSelect ? View.GONE : View.VISIBLE);
        }
    }

    private static class CamViewHolder extends RecyclerView.ViewHolder {
        CamViewHolder(View itemView) {
            super(itemView);
        }
    }

    private static class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView mImageView;
        ImageView mCheckView;
        ImageView mGifMask;
        View mMaskView;

        ImageViewHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.iv_image);
            mCheckView = (ImageView) itemView.findViewById(R.id.cb_selected);
            mMaskView = itemView.findViewById(R.id.lay_mask);
            mGifMask = (ImageView) itemView.findViewById(R.id.iv_is_gif);
        }
    }
}
