package com.project.movice.modules.loan.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.hardware.Camera;
import android.os.CountDownTimer;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.project.movice.R;
import com.project.movice.base.activity.BaseActivity;
import com.project.movice.modules.loan.contract.FaceCameraContract;
import com.project.movice.modules.loan.presenter.FaceCameraPresenter;
import com.project.movice.utils.Constant;
import com.project.movice.utils.FileUtil;
import com.project.movice.widget.behavior.CameraPreview;
import com.project.movice.widget.behavior.SelectOptions;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

import static com.project.movice.utils.LoginUtil.showActivity;

/**
 * Created by wy on 2018/7/3 11:05.
 */


public class FaceCameraActivity   extends BaseActivity <FaceCameraPresenter> implements FaceCameraContract.View {

    public final static int RESULT_CODE = 0X14;

    @BindView(R.id.camera_surface)
    CameraPreview mCameraPreview;
    @BindView(R.id.camera_crop_container)
    View mContainerView;
    @BindView(R.id.camera_crop)
    ImageView mCropView;
    @BindView(R.id.camera_flash)
    ImageView mCameraFlash;//闪光
    @BindView(R.id.change_camera)
    ImageView mChangeCamera;//切换摄像头
    @BindView(R.id.camera_option)
    View mCameraOption;
    @BindView(R.id.camera_result)
    View mCameraResult;
    @BindView(R.id.photo_reminder)
    TextView mPhotoReminder;//拍照提示信息

    String pictureCrop;
    private int type;


    @BindView(R.id.imgHead)
    ImageView imgHead;//
    @BindView(R.id.imgEyes)
    ImageView imgEyes;//


    @BindView(R.id.ll_eyes)
    LinearLayout ll_eyes;//
    @BindView(R.id.ll_Head)
    LinearLayout ll_Head;//
    @BindView(R.id.ll_dui)
    LinearLayout ll_dui;//


    CountDownTimer timer;
    @Override
    protected int getLayoutId() {
        return R.layout.face_camera;
    }

    @Override
    public void initView() {
        if (mOption == null) {
            finish();
            return;
        }
        //获取背景，并将其强转成AnimationDrawable
        AnimationDrawable animationDrawable = (AnimationDrawable) imgHead.getBackground();
        //判断是否在运行
        if (!animationDrawable.isRunning()) {
            //开启帧动画
            animationDrawable.start();
        }

        //获取背景，并将其强转成AnimationDrawable
        AnimationDrawable animationDrawable2 = (AnimationDrawable) imgEyes.getBackground();
        //判断是否在运行
        if (!animationDrawable2.isRunning()) {
            //开启帧动画
            animationDrawable2.start();
        }

        type = mOption.getPhotoType();
        if (type == SelectOptions.TYPE_WORK_PHOTO) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else if (type == SelectOptions.TYPE_FOTO_STASIUN) {
            mCameraPreview.setCameraFacing(Camera.CameraInfo.CAMERA_FACING_FRONT);//设置默认摄像头
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            mChangeCamera.setVisibility(View.VISIBLE);//显示切换摄像头按钮
        } else if (type == SelectOptions.TYPE_PORTRAIT) {
            mCameraPreview.setCameraFacing(Camera.CameraInfo.CAMERA_FACING_FRONT);//设置默认摄像头
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            mChangeCamera.setVisibility(View.VISIBLE);//显示切换摄像头按钮
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        //获取屏幕最小边，设置为cameraPreview较窄的一边
        float screenMinSize = Math.min(getResources().getDisplayMetrics().widthPixels, getResources().getDisplayMetrics().heightPixels);
        //根据screenMinSize，计算出cameraPreview的较宽的一边，长宽比为标准的16:9
        float maxSize = screenMinSize / 9.0f * 16.0f;
        RelativeLayout.LayoutParams layoutParams;
        if (type == SelectOptions.TYPE_FOTO_STASIUN) {
            layoutParams = new RelativeLayout.LayoutParams((int) maxSize, (int) screenMinSize);
            mPhotoReminder.setText(getResources().getString(R.string.photo_reminder));
        } else if (type == SelectOptions.TYPE_WORK_PHOTO) {
            layoutParams = new RelativeLayout.LayoutParams((int) maxSize, (int) screenMinSize);
            mPhotoReminder.setText(getResources().getString(R.string.pay_slip_prompt));
        } else if (type == SelectOptions.TYPE_PORTRAIT) {
            layoutParams = new RelativeLayout.LayoutParams((int) screenMinSize, (int) maxSize);
        } else {
            layoutParams = new RelativeLayout.LayoutParams((int) maxSize, (int) screenMinSize);
        }
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        mCameraPreview.setLayoutParams(layoutParams);
//        if (type == SelectOptions.TYPE_WORK_PHOTO) {
//            float width = (int) (screenMinSize * 0.8);
//            float height = (int) (width * 43.0f / 30.0f);
//            LinearLayout.LayoutParams containerParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) height);
//            LinearLayout.LayoutParams cropParams = new LinearLayout.LayoutParams((int) width, (int) height);
//            mContainerView.setLayoutParams(containerParams);
//            mCropView.setLayoutParams(cropParams);
//        } else {
//            float height = (int) (screenMinSize * 0.75);
//            float width = (int) (height * 75.0f / 47.0f);
//            LinearLayout.LayoutParams containerParams = new LinearLayout.LayoutParams((int) width, ViewGroup.LayoutParams.MATCH_PARENT);
//            LinearLayout.LayoutParams cropParams = new LinearLayout.LayoutParams((int) width, (int) height);
//            mContainerView.setLayoutParams(containerParams);
//            mCropView.setLayoutParams(cropParams);
//        }
        float height = (int) (screenMinSize * 0.75);
        float width = (int) (height * 75.0f / 47.0f);
        LinearLayout.LayoutParams containerParams = new LinearLayout.LayoutParams((int) width, ViewGroup.LayoutParams.MATCH_PARENT);
        LinearLayout.LayoutParams cropParams = new LinearLayout.LayoutParams((int) width, (int) height);
        mContainerView.setLayoutParams(containerParams);
        mCropView.setLayoutParams(cropParams);
        switch (type) {
            case SelectOptions.TYPE_IDCARD_FRONT:
                mCropView.setImageResource(R.mipmap.lingyi15);
                break;
            case SelectOptions.TYPE_WORK_PHOTO:
                mCropView.setImageResource(R.mipmap.lingyi12);
                break;
            case SelectOptions.TYPE_FOTO_STASIUN:
                mCropView.setImageResource(R.mipmap.sfsd132cv);
                break;
        }


        /**
         * CountDownTimer timer = new CountDownTimer(3000, 1000)中，
         * 第一个参数表示总时间，第二个参数表示间隔时间。
         * 意思就是每隔一秒会回调一次方法onTick，然后1秒之后会回调onFinish方法。
         */
         timer = new CountDownTimer(11000, 1000) {
            public void onTick(long millisUntilFinished) {

                if ((millisUntilFinished / 1000) == 6) {
                    ll_eyes.setVisibility(View.GONE);
                    ll_Head.setVisibility(View.GONE);
                    ll_dui.setVisibility(View.VISIBLE);

                }
                if ((millisUntilFinished / 1000) == 5) {
                    ll_eyes.setVisibility(View.VISIBLE);
                    ll_Head.setVisibility(View.GONE);
                    ll_dui.setVisibility(View.GONE);
                }

            }

            public void onFinish() {
                ll_eyes.setVisibility(View.GONE);
                ll_Head.setVisibility(View.GONE);
                ll_dui.setVisibility(View.VISIBLE);
                takePhoto();
            }
        };
        //调用 CountDownTimer 对象的 start() 方法开始倒计时，也不涉及到线程处理
        timer.start();
        FileUtil.mkdir();// 初始化文件夹

    }



    @Override
    protected void initToolbar() {

    }

    @Override
    protected void initEventAndData() {

    }



    private static SelectOptions mOption;

    public static void openCertificateCamera(Activity activity, SelectOptions options) {
        mOption = options;
        showActivity(activity, FaceCameraActivity.class);

//        Intent intent = new Intent(activity, KtpCameraActivity.class);
//        intent.putExtra("type", type);
//        activity.startActivityForResult(intent, RESULT_CODE);
    }

    @OnClick({R.id.camera_surface, R.id.camera_close, R.id.camera_take,
            R.id.camera_flash, R.id.camera_result_ok, R.id.camera_result_cancel, R.id.change_camera, R.id.img_dui})
    public void onClick(View v) {
        switch (v.getId()) {


            case R.id.img_dui:
                goBack();
                break;

            case R.id.camera_surface:
                mCameraPreview.focus();
                break;
            case R.id.camera_close:
                finish();
                break;
            case R.id.change_camera://摄像头切换
                mCameraPreview.changeCameraFacing();
                mCameraPreview.setEnabled(true);
                mCameraPreview.setVisibility(View.INVISIBLE);
                mCameraPreview.setVisibility(View.VISIBLE);
                mCameraPreview.requestLayout();
                break;
            case R.id.camera_take:
                if (canClick()) {
                    takePhoto();
                }
                break;
            case R.id.camera_flash:
                boolean isFlashOn = mCameraPreview.switchFlashLight();
                mCameraFlash.setImageResource(isFlashOn ? R.mipmap.lingyi14 : R.mipmap.lingyi13);
                break;
            case R.id.camera_result_ok:
                goBack();
                break;
            case R.id.camera_result_cancel:
                mCameraOption.setVisibility(View.VISIBLE);
                mCameraPreview.setEnabled(true);
                mCameraResult.setVisibility(View.GONE);
                mCameraPreview.startPreview();
                if (type == SelectOptions.TYPE_FOTO_STASIUN || type == SelectOptions.TYPE_PORTRAIT) {
                    mChangeCamera.setVisibility(View.VISIBLE);
                }
                break;
        }
    }


    private void takePhoto() {
        mCameraOption.setVisibility(View.GONE);
        mCameraPreview.setEnabled(false);
        mCameraPreview.takePhoto(cp);
    }

    Camera.PictureCallback cp = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(final byte[] data, Camera camera) {
            camera.startPreview();
            //子线程处理图片，防止ANR
            new Thread(new Runnable() {
                @Override
                public void run() {
                    FileOutputStream originalFileOutputStream = null;
                    BufferedOutputStream bos = null;
                    Bitmap bitmap = null;
                    try {
                        File originalFile = getOriginalFile();
                        originalFileOutputStream = new FileOutputStream(originalFile);
                        originalFileOutputStream.write(data);
                        originalFileOutputStream.close();

                        bitmap = BitmapFactory.decodeFile(originalFile.getPath());

                        //计算裁剪位置
                        //计算裁剪位置
                        float left, top, right, bottom;
                        if (type == SelectOptions.TYPE_WORK_PHOTO) {//工资条不截图

                        } else if (type == SelectOptions.TYPE_IDCARD_FRONT) {
                            left = ((float) mContainerView.getLeft() - (float) mCameraPreview.getLeft()) / (float) mCameraPreview.getWidth();
                            top = (float) mCropView.getTop() / (float) mCameraPreview.getHeight();
                            right = (float) mContainerView.getRight() / (float) mCameraPreview.getWidth();
                            bottom = (float) mCropView.getBottom() / (float) mCameraPreview.getHeight();
                            //裁剪及保存到文件
                            bitmap = Bitmap.createBitmap(bitmap,
                                    (int) (left * (float) bitmap.getWidth()),
                                    (int) (top * (float) bitmap.getHeight()),
                                    (int) ((right - left) * (float) bitmap.getWidth()),
                                    (int) ((bottom - top) * (float) bitmap.getHeight()));
                        } else if (type == SelectOptions.TYPE_FOTO_STASIUN) {//工位照不裁剪

                        }
                        pictureCrop = getCropFile();
                        bos = new BufferedOutputStream(new FileOutputStream(new File(pictureCrop)));
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mCameraResult.setVisibility(View.VISIBLE);
                                mChangeCamera.setVisibility(View.GONE);
                            }
                        });
                        return;
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            bos.flush();//输出
                            bos.close();//关闭
                            originalFileOutputStream.flush();
                            originalFileOutputStream.close();
                            bitmap.recycle();// 回收bitmap空间
                            mCameraPreview.stopPreview();
                            goBack();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mCameraOption.setVisibility(View.VISIBLE);
                            mCameraPreview.setEnabled(true);
                        }
                    });
                }
            }).start();

        }
    };


    /**
     * @return 拍摄图片原始文件
     */
    private File getOriginalFile() {
        return new File(FileUtil.getImagePath(System.currentTimeMillis() + ".jpg"));
    }

    /**
     * @return 拍摄图片裁剪文件
     */
    private String getCropFile() {
        return FileUtil.getImagePath(System.currentTimeMillis() + ".jpg");
    }

    /**
     * 点击对勾，使用拍照结果，返回对应图片路径
     */
    private void goBack() {

        Map<String, String> params = new HashMap<>();
        params.put("type", Constant.IMAGE_FACE);
        mPresenter.request027((pictureCrop),params);


    }


    @Override
    public void get027() {
        finish();
        startActivity(new Intent(FaceCameraActivity.this, BankCardActivity.class));

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
         timer.cancel();
    }
}
