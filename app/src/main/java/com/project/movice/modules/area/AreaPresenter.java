package com.project.movice.modules.area;

import com.project.movice.R;
import com.project.movice.application.MoviceApp;
import com.project.movice.base.presenter.BasePresenter;
import com.project.movice.core.rx.BaseObserver;
import com.project.movice.modules.home.bean.HomeBean;
import com.project.movice.modules.home.contract.HomeContract;
import com.project.movice.utils.FastJsonTools;
import com.project.movice.utils.RequestBeanUtils;
import com.project.movice.utils.RxUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

public class AreaPresenter extends BasePresenter<AreaContract.View> implements AreaContract.Presenter {
    HomeBean homeBean;
    private Map<String, String> hm = new HashMap<>();

    @Inject
    AreaPresenter() {
    }


    @Override
    public void reload() {
        requestData(hm);
    }

    @Override
    public void requestData(Map<String, String> hm) {
        this.hm = hm;
        addSubscribe(mDataManager.get053(RequestBeanUtils.getRequestBean(hm))
                .compose(RxUtils.SchedulerTransformer())
                .filter(data -> mView != null)
                .subscribeWith(new BaseObserver(mView,
                        MoviceApp.getContext().getString(R.string.login_fail),
                        true) {
                    @Override
                    public void onSuccess(String data) {
                        List<BeanAreaInfo> list = FastJsonTools.parseArray(data, BeanAreaInfo.class);
                        for (int i=0;i<list.size();i++){
                            list.get(i).setLevel(Integer.parseInt(hm.get("level")));
                        }
                        mView.getData(list);


                    }
                }));
    }


}
