package com.project.movice.modules.area;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.TextView;


import com.project.movice.R;
import com.project.movice.base.activity.BaseActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * 地区选择
 * Created by wy on 2018/2/2 10:46.
 */


public class AreaSelectActivity extends BaseActivity<AreaAcPresenter> implements AreaFragment.OnFragmentInteractionListener {

    private Fragment oneFragment;
    private Fragment twoFragment;
    private Map map = new HashMap();


    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_title)
    TextView mTitle;

    @Override
    protected int getLayoutId() {
        return R.layout.activi_sres_select;
    }

    @Override
    protected void initToolbar() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        mToolbar.setNavigationOnClickListener(v -> onBackPressedSupport());
    }

    @Override
    protected void initEventAndData() {

    }

    @Override
    public void initView() {

        titleList.add(getResources().getString(R.string.select_province));
        showTitle();
        oneFragment = AreaFragment.newInstance("1", "0",1);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content, oneFragment).commit();

    }






    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                FragmentManager fragmentManager = getSupportFragmentManager();
                if (fragmentManager.getBackStackEntryCount() > 0) {
                    fragmentManager.popBackStack();
                } else {
                    finish();
                }
                break;
        }
        return true;
    }




    @Override
    public void onFragmentInteraction(BeanAreaInfo areaInfo) {
        if (areaInfo == null) {
            return;
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        int level = areaInfo.getLevel();
        switch (level) {
            case 1:
                map.put("provId", areaInfo.getId());
                map.put("provName", areaInfo.getName());
                transaction.hide(oneFragment);
                transaction.add(R.id.content, twoFragment = AreaFragment.newInstance("2" , areaInfo.getId(), 2)).addToBackStack(null).commit();
                titleList.add(getResources().getString(R.string.select_city));
                showTitle();

                break;
            case 2:
                map.put("cityId", areaInfo.getId());
                map.put("cityName", areaInfo.getName());
                transaction.hide(oneFragment);
                transaction.add(R.id.content, twoFragment = AreaFragment.newInstance("3" , areaInfo.getId() , 3)).addToBackStack(null).commit();
                titleList.add(getResources().getString(R.string.select_area));
                showTitle();
                break;
            case 3:
                map.put("districtId", areaInfo.getId());
                map.put("districtName", areaInfo.getName());
                transaction.hide(oneFragment);
                transaction.add(R.id.content, twoFragment = AreaFragment.newInstance("4", areaInfo.getId() , 4)).addToBackStack(null).commit();
                titleList.add(getResources().getString(R.string.select_street));
                showTitle();
                break;
            case 4:
                map.put("villId", areaInfo.getId());
                map.put("villName", areaInfo.getName());
                Intent intent = new Intent();
                intent.putExtra("addressInfo", (Serializable) map);
                setResult(RESULT_OK, intent);
                finish();
                break;
        }
    }

    List<String> titleList = new ArrayList<>();

    private void showTitle() {
        mTitle.setText(titleList.get(titleList.size() - 1));


    }

    private void popBackStack() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();
            titleList.remove(titleList.size() - 1);
            showTitle();
        } else {
            finish();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            popBackStack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}
