package com.project.movice.modules.loan.presenter;

import android.Manifest;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.movice.R;
import com.project.movice.application.MoviceApp;
import com.project.movice.base.presenter.BasePresenter;
import com.project.movice.core.rx.BaseObserver;
import com.project.movice.modules.loan.bean.BeanJob;
import com.project.movice.modules.loan.bean.BeanPersonalInformation;
import com.project.movice.modules.loan.bean.BeanPrivacyInformation;
import com.project.movice.modules.loan.contract.CardContract;
import com.project.movice.modules.loan.contract.JobInforContract;
import com.project.movice.utils.Constant;
import com.project.movice.utils.DataUtils;
import com.project.movice.utils.FastJsonTools;
import com.project.movice.utils.FileUtil;
import com.project.movice.utils.GsonUtil;
import com.project.movice.utils.LubanUtil;
import com.project.movice.utils.PhoneUtils;
import com.project.movice.utils.RequestBeanUtils;
import com.project.movice.utils.RxUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class JobInforPresenter extends BasePresenter<JobInforContract.View> implements JobInforContract.Presenter  {
    private Object object;
    @Inject
    JobInforPresenter() {
    }


    @Override
    public void reload() {
        request007(new HashMap<>());
    }

    @Override
    public void request027(String path, Map<String, String> hm) {
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
//
                                mView.get027( (hm.get("type")));
                            }
                        }));
            }

            @Override
            public void error(Throwable e) {
            }
        });


    }

    @Override
    public void request007(Map<String, String> hm) {

        addSubscribe(mDataManager.get007(RequestBeanUtils.getRequestBean(hm))
                .compose(RxUtils.SchedulerTransformer())
                .filter(data -> mView != null)
                .subscribeWith(new BaseObserver(mView,
                        MoviceApp.getContext().getString(R.string.login_fail),
                        true) {
                    @Override
                    public void onSuccess(String data) {

                        BeanJob bean = FastJsonTools.parseObject(data, BeanJob.class);

//                        BeanJob bean=GsonUtil.GsonToBean(data,BeanJob.class);
                        jobInfo=bean;
                        mView.get007(bean);
                    }
                }));

    }

    @Override
    public void request013(Map<String, String> hm) {
        addSubscribe(mDataManager.get013(RequestBeanUtils.getRequestBean(hm))
                .compose(RxUtils.SchedulerTransformer())
                .filter(data -> mView != null)
                .subscribeWith(new BaseObserver(mView,
                        MoviceApp.getContext().getString(R.string.login_fail),
                        true) {
                    @Override
                    public void onSuccess(String data) {
                        mView.get013();
                    }
                }));
    }

    @Override
    public void requestPermissions(RxPermissions rxPermissions,int type) {
        addSubscribe(rxPermissions
                .request(Manifest.permission.CAMERA )
                .subscribe(granted -> {
                    if (granted) {
                        mView.permissionsSuccess(type);
                    } else {
                        mView.permissionsCancle();
                    }
                }));
    }

    BeanJob jobInfo;
    /**
     * 判断是否修改信息
     *
     * @return true 没有修改  false有修改
     */
    public boolean exitVerification(BeanJob info) {
        return info.equals(jobInfo);
    }

}
