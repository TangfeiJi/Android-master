package com.project.movice.modules.loan.presenter;

import com.project.movice.R;
import com.project.movice.application.MoviceApp;
import com.project.movice.base.presenter.BasePresenter;
import com.project.movice.core.rx.BaseObserver;
import com.project.movice.modules.loan.bean.BeanPersonalInformation;
import com.project.movice.modules.loan.bean.InforBean;
import com.project.movice.modules.loan.contract.CardContract;
import com.project.movice.modules.loan.contract.PersonalContract;
import com.project.movice.utils.FastJsonTools;
import com.project.movice.utils.GsonUtil;
import com.project.movice.utils.RequestBeanUtils;
import com.project.movice.utils.RxUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class PersonalPresenter extends BasePresenter<PersonalContract.View> implements PersonalContract.Presenter  {
    private Object object;
    @Inject
    PersonalPresenter() {
    }


    @Override
    public void reload() {
        request006(new HashMap<>());
    }

    @Override
    public void request006(Map<String, String> hm) {

        addSubscribe(mDataManager.get006(RequestBeanUtils.getRequestBean(hm))
                .compose(RxUtils.SchedulerTransformer())
                .filter(data -> mView != null)
                .subscribeWith(new BaseObserver(mView,
                        MoviceApp.getContext().getString(R.string.login_fail),
                        true) {
                    @Override
                    public void onSuccess(String data) {

                        BeanPersonalInformation bean = FastJsonTools.parseObject(data, BeanPersonalInformation.class);

//                        BeanPersonalInformation bean=GsonUtil.GsonToBean(data,BeanPersonalInformation.class);
                        mView.get006(bean);
                    }
                }));

    }

    @Override
    public void request054(Map<String, String> hm) {
        addSubscribe(mDataManager.get054(RequestBeanUtils.getRequestBean(hm))
                .compose(RxUtils.SchedulerTransformer())
                .filter(data -> mView != null)
                .subscribeWith(new BaseObserver(mView,
                        MoviceApp.getContext().getString(R.string.login_fail),
                        true) {
                    @Override
                    public void onSuccess(String data) {
                        mView.get054();
                    }
                }));
    }



    /**
     * 判断是否修改了信息
     * @return true 修改了   false 未修改
     */
    public boolean exitVerification(BeanPersonalInformation info) {
//        return !info.equals(information);
        return false;
    }


}
