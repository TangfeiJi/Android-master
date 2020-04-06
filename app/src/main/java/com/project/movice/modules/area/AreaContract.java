package com.project.movice.modules.area;

import com.project.movice.base.presenter.IPresenter;
import com.project.movice.base.view.IView;

import java.util.List;
import java.util.Map;

public interface AreaContract {
    interface View extends IView {

        void getData(List<BeanAreaInfo> list);
    }

    interface Presenter extends IPresenter<AreaContract.View> {

        void requestData(Map<String,String> hm);
    }
}
