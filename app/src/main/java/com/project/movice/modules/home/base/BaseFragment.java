package com.project.movice.modules.home.base;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by wy on 2018/1/5 12:06.
 */


public abstract class BaseFragment extends SupportFragment implements View.OnClickListener {

    protected View mRoot;
    protected Bundle mBundle;
    private CompositeDisposable compositeDisposable;
    Unbinder unbinder;

    //封装抽象方法用于在fragment中初始化控件，增加代码的条理性
    protected abstract void initView(View view, Bundle savedInstanceState);

    protected abstract void initData();

    //获取fragment布局文件ID
    protected abstract int getLayoutId();

    protected void initBundle(Bundle bundle) {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBundle = getArguments();
        initBundle(mBundle);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (null != mRoot) {
            ViewGroup parent = (ViewGroup) mRoot.getParent();
            if (null != parent) {
                parent.removeView(mRoot);
            }
        } else {
            //初始化语言
            mRoot = inflater.inflate(getLayoutId(), container, false);
            unbinder = ButterKnife.bind(this, mRoot);
            EventBus.getDefault().register(this);
            initView(mRoot, savedInstanceState);
            initData();
        }
        return mRoot;
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(String str) {
        switch (str) {
            case "EVENT_REFRESH_LANGUAGE":
                getActivity().recreate();//刷新界面
                break;
        }
    }

    public static void showActivity(Context context, Class<?> clazz) {
        showActivity(context, clazz, null);
    }

    public static void showActivity(Context context, Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(context, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
    }

    public void LoginOut() {
//        LoginOutDialog loginout = new LoginOutDialog(getActivity());
//        loginout.setOnClickListener(new LoginOutDialog.OnClickListener() {
//            @Override
//            public void onClick() {
//                showLogin();
//            }
//        });
//        loginout.show();
        showLogin();
    }

    public void showLogin() {
//        showActivity(getActivity(), FaceBookLoginActivity.class);
//        showActivity(getActivity(), FacebookLoginActivity.class);

    }

    public void showActivityForResult(Context context, Class<?> clazz, int requestCode) {
        showActivityForResult(context, clazz, null, requestCode);
    }

    public void showActivityForResult(Context context, Class<?> clazz, Bundle bundle, int requestCode) {
        Intent intent = new Intent(context, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
//		Fragment a = (Fragment) context;
        startActivityForResult(intent, requestCode);
    }


    private long lastClick;

    @Override
    public void onClick(View v) {
    }

    /**
     * 解决点击事件双击
     *
     * @return
     */
    public boolean canClick() {
        if (System.currentTimeMillis() - lastClick <= 1000 && (System.currentTimeMillis() - lastClick) > 0) {
            return false;
        }
        lastClick = System.currentTimeMillis();
        return true;
    }





    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        unbinder.unbind();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


    protected void addSubscribe(Disposable disposable) {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(disposable);
    }
}
