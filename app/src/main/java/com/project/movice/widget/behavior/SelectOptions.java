package com.project.movice.widget.behavior;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by haibin
 * on 2016/12/5.
 */

public final class SelectOptions {

    /**
     * 拍摄类型-身份证正面
     */
    public final static int TYPE_IDCARD_FRONT = 1;
    /**
     * 拍摄类型-工作照
     */
    public final static int TYPE_WORK_PHOTO = 2;
    /**
     * 拍摄类型-工位照
     */
    public final static int TYPE_FOTO_STASIUN = 3;
    /**
     * 拍摄类型-竖屏拍照
     */
    public final static int TYPE_PORTRAIT = 4;


    private boolean isCrop;
    private int mCropWidth, mCropHeight;
    private Callback mCallback;
    private boolean hasCam;
    private boolean openCamera = false;//是否直接打开相机拍照
    private int mSelectCount;
    private int mCameraType;
    private int mPhotoType;
    private List<String> mSelectedImages;

    private SelectOptions() {

    }

    public boolean isCrop() {
        return isCrop;
    }

    public int getCropWidth() {
        return mCropWidth;
    }

    public int getCropHeight() {
        return mCropHeight;
    }

    public int getCameraType() {
        return mCameraType;
    }

    public int getPhotoType() {
        return mPhotoType;
    }

    public int getSelectCount() {
        return mSelectCount;
    }

    public Callback getCallback() {
        return mCallback;
    }

    public boolean isHasCam() {
        return hasCam;
    }

    public boolean isOpenCamera() {
        return openCamera;
    }

    public List<String> getSelectedImages() {
        return mSelectedImages;
    }

    public static class Builder {
        private boolean isCrop;
        private int cropWidth, cropHeight;
        private Callback callback;
        private boolean hasCam;//是否显示相机
        private boolean openCamera;//是否直接打开相机拍照
        private int selectCount;//可选图片数量
        private int cameraType;//相机类型 1 系统相机  2 自定义ktp相机
        private int photoType;// 1 ktp  2 证件照
        private List<String> selectedImages;

        public Builder() {
            selectCount = 1;
            hasCam = true;
            selectedImages = new ArrayList<>();
        }

        public Builder setCrop(int cropWidth, int cropHeight) {
            if (cropWidth <= 0 || cropHeight <= 0)
                throw new IllegalArgumentException("cropWidth or cropHeight mast be greater than 0 ");
            this.isCrop = true;
            this.cropWidth = cropWidth;
            this.cropHeight = cropHeight;
            return this;
        }

        public Builder setCallback(Callback callback) {
            this.callback = callback;
            return this;
        }

        public Builder setOpenCamera(boolean openCamera) {
            this.openCamera = openCamera;
            return this;
        }

        public Builder setHasCam(boolean hasCam) {
            this.hasCam = hasCam;
            return this;
        }

        public Builder setSelectCount(int selectCount) {
            if (selectCount <= 0)
                throw new IllegalArgumentException("selectCount mast be greater than 0 ");
            this.selectCount = selectCount;
            return this;
        }

        public Builder setCameraType(int cameraType) {
            this.cameraType = cameraType;
            return this;
        }

        public Builder setPhotoType(int photoType) {
            this.photoType = photoType;
            return this;
        }

        public Builder setSelectedImages(List<String> selectedImages) {
            if (selectedImages == null || selectedImages.size() == 0) return this;
            this.selectedImages.addAll(selectedImages);
            return this;
        }

        public Builder setSelectedImages(String[] selectedImages) {
            if (selectedImages == null || selectedImages.length == 0) return this;
            if (this.selectedImages == null) this.selectedImages = new ArrayList<>();
            this.selectedImages.addAll(Arrays.asList(selectedImages));
            return this;
        }

        public SelectOptions build() {
            SelectOptions options = new SelectOptions();
            options.hasCam = hasCam;
            options.openCamera = openCamera;
            options.isCrop = isCrop;
            options.mCropHeight = cropHeight;
            options.mCropWidth = cropWidth;
            options.mCallback = callback;
            options.mSelectCount = selectCount;
            options.mSelectedImages = selectedImages;
            options.mCameraType = cameraType;
            options.mPhotoType = photoType;
            return options;
        }
    }

    public interface Callback {
        void doSelected(String[] images);
    }
}
