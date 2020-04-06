package com.project.movice.modules.drawname;

import android.Manifest;

import com.project.movice.R;
import com.project.movice.application.MoviceApp;
import com.project.movice.base.presenter.BasePresenter;
import com.project.movice.core.rx.BaseObserver;
import com.project.movice.modules.loan.bean.BeanPersonalInformation;
import com.project.movice.modules.loan.bean.BeanUploadFile;
import com.project.movice.modules.loan.contract.CardContract;
import com.project.movice.utils.Constant;
import com.project.movice.utils.DataUtils;
import com.project.movice.utils.FileUtil;
import com.project.movice.utils.GsonUtil;
import com.project.movice.utils.LubanUtil;
import com.project.movice.utils.PhoneUtils;
import com.project.movice.utils.RequestBeanUtils;
import com.project.movice.utils.RxUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.util.Map;

import javax.inject.Inject;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class DrawNamePresenter extends BasePresenter<DrawNameContract.View> implements DrawNameContract.Presenter  {
    private Object object;
    @Inject
    DrawNamePresenter() {
    }


    @Override
    public void reload() {

    }




    @Override
    public void request027(String path,Map<String, String> hm) {

        LubanUtil.getFile(MoviceApp.getContext(), path, FileUtil.getImagePath(""), new LubanUtil.OnLubanFinishListener() {
            @Override
            public void finish(File file) {
                MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
                builder.addFormDataPart("phone", DataUtils.getPhone(MoviceApp.getContext()));
                builder.addFormDataPart("token",  DataUtils.getToken(MoviceApp.getContext()));
                builder.addFormDataPart("type", hm.get("type"));
                builder.addFormDataPart("cc", "62");
                builder.addFormDataPart("platform", PhoneUtils.getAppName());
                builder.addFormDataPart("file", "file", RequestBody.create(MediaType.parse("image/jpeg; charset=utf-8"),file));

                addSubscribe(mDataManager.get0277(builder.build())
                        .compose(RxUtils.SchedulerTransformer())
                        .filter(data -> mView != null)
                        .subscribeWith(new BaseObserver(mView,
                                MoviceApp.getContext().getString(R.string.login_fail),
                                true) {
                            @Override
                            public void onSuccess(String data) {
                                mView.get027();
                            }
                        }));
            }

            @Override
            public void error(Throwable e) {
            }
        });


    }
}
