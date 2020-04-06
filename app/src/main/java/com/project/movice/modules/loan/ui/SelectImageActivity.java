package com.project.movice.modules.loan.ui;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.widget.Toast;


import com.project.movice.R;
import com.project.movice.modules.loan.contract.SelectImageContract;
import com.project.movice.utils.Util;
import com.project.movice.widget.behavior.SelectOptions;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by wy on 2018/1/13 19:48.
 */


@SuppressWarnings("All")
public class SelectImageActivity extends BaseActivity implements  SelectImageContract.Operator {

    String[] permission = new String[]{Manifest.permission.CAMERA};

    private static final int RC_CAMERA = 0x02;
    private static final int RC_CAMERA_PERM = 0x03;
    private static final int RC_EXTERNAL_STORAGE = 0x04;
    private String TAG = "wy";

    private static SelectOptions mOption;
    private List<String> path = new ArrayList<>();
    private SelectImageContract.View mView;
    private String mCamImageName;


    public static void show(Context context, SelectOptions options) {
        mOption = options;
        context.startActivity(new Intent(context, SelectImageActivity.class));
    }

    @Override
    protected int getContentView() {
        return R.layout.activi_select_img;
    }

    @Override
    public void initView() {
//        mOption = new SelectOptions.Builder()
//                .setSelectCount(5)
//                .setHasCam(true)
////                .setCrop(700, 700)
//                .setCallback(new SelectOptions.Callback() {
//                    @Override
//                    public void doSelected(String[] images) {
////                            String path = images[0];
//                    }
//                }).build();
    }

    @Override
    public void initData() {
        if (null != mOption && mOption.isOpenCamera()) {//直接打开相机
            requestCamera();
        } else {
        }
    }

    @Override
    public void initBundle(Bundle bundle, Bundle savedInstanceState) {

    }


    /**
     * 访问相机权限
     */
    @Override
    public void requestCamera() {
            if (mOption.isOpenCamera() && null == mView) {//直接打开相机
                handleView();
            } else if (mView != null) {
                mView.onOpenCameraSuccess();
            }
    }

    @Override
    public void requestExternalStorage() {

    }


    @Override
    public void onBack() {
//        onSupportNavigateUp();
        finish();
    }

    @Override
    public void setDataView(SelectImageContract.View view) {
        mView = view;
    }

    @Override
    protected void onDestroy() {
        mOption = null;
        super.onDestroy();
    }





    /**
     * 打开相机
     */
    private void toOpenCamera() {
        // 判断是否挂载了SD卡
        mCamImageName = null;
        String savePath = "";
        if (Util.hasSDCard()) {
            savePath = Util.getCameraPath();
            File saveDir = new File(savePath);
            if (!saveDir.exists()) {
                saveDir.mkdirs();
            }
        }

        // 没有挂载SD卡，无法保存文件
        if (TextUtils.isEmpty(savePath)) {
            Toast.makeText(this, R.string.unable_to_save_photos, Toast.LENGTH_LONG).show();
            return;
        }

        mCamImageName = Util.getSaveImageFullName();
        File out = new File(savePath, mCamImageName);

        /**
         * android N 系统适配
         */
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri uri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            uri = FileProvider.getUriForFile(this, "com.minirupiah.mr.fileprovider", out);
        } else {
            uri = Uri.fromFile(out);
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(intent,
                0x03);
    }

    /*@Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == AppCompatActivity.RESULT_OK) {
            switch (requestCode) {
                case 0x03:
                    if (mCamImageName == null) return;
                    Uri localUri = Uri.fromFile(new File(Util.getCameraPath() + mCamImageName));
                    Intent localIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, localUri);
                    sendBroadcast(localIntent);
                    mOption.getCallback().doSelected(new String[]{Util.getCameraPath() + mCamImageName});
                    break;
            }
        }
        if (requestCode == 0x03)
            exit();
    }*/

    public static String getRealFilePath(final Context context, final Uri uri) {
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }


    private void removeView() {
        SelectImageContract.View view = mView;
        if (view == null)
            return;
        try {
            getSupportFragmentManager()
                    .beginTransaction()
                    .remove((Fragment) view)
                    .commitNowAllowingStateLoss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void handleView() {
        try {
            //Fragment fragment = Fragment.instantiate(this, SelectFragment.class.getName());
            if (mOption.getCameraType() == 2) {
                KtpCameraActivity.openCertificateCamera(this, mOption);
                exit();
            } else if(mOption.getCameraType() == 5){
                FaceCameraActivity.openCertificateCamera(this, mOption);
                exit();
            }else {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fl_content, SelectFragment.newInstance(mOption))
                        .commitNowAllowingStateLoss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == KtpCameraActivity.RESULT_CODE && resultCode == RESULT_OK && data != null) {
//            String path = data.getStringExtra("result");
//            if (mOption.isOpenCamera()) {
//                mOption.getCallback().doSelected(new String[]{path});
//                exit();
//            }
//        } else {
//            exit();
//        }
//    }
}
