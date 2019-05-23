/*
 *     (C) Copyright 2019, ForgetSky.
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 */

package com.project.movice.modules.main.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.project.movice.R;
import com.project.movice.base.activity.BaseActivity;
import com.project.movice.core.constant.Constants;
import com.project.movice.modules.loan.ui.LoanFragment;
import com.project.movice.modules.main.contract.MainContract;
import com.project.movice.modules.main.presenter.MainPresenter;
import com.project.movice.modules.home.ui.HomeFragment;
import com.project.movice.modules.mine.ui.MineFragment;
import com.project.movice.utils.CommonUtils;
import com.project.movice.utils.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View {

    private static final String TAG = "MainActivity";

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_title)
    TextView mTitle;
    @BindView(R.id.nav_view)
    NavigationView mNavigationView;
    @BindView(R.id.main_floating_action_btn)
    FloatingActionButton mFloatingActionButton;
    @BindView(R.id.bottom_navigation_view)
    BottomNavigationView mBottomNavigationView;
    @BindView(R.id.fragment_group)
    FrameLayout mFrameGroup;
    TextView mUsTv;
    private AlertDialog mDialog;
    //fragments
    private LoanFragment loanFragment;
    private HomeFragment mHomeFragment;
    private MineFragment mMineFragment;
    private int mLastFgIndex = -1;
    private int mCurrentFgIndex = 0;
    private long clickTime;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mCurrentFgIndex = savedInstanceState.getInt(Constants.CURRENT_FRAGMENT_KEY);
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(Constants.CURRENT_FRAGMENT_KEY, mCurrentFgIndex);
    }

    @Override
    protected void initView() {
        showFragment(mCurrentFgIndex);
        initNavigationView();
        initBottomNavigationView();
    }

    private void showFragment(int index) {
        mCurrentFgIndex = index;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        hideFragment(transaction);
        mLastFgIndex = index;
        switch (index) {
            case Constants.TYPE_JOKEPAGER:
                mTitle.setText("joke");
                if (loanFragment == null) {
                    loanFragment = LoanFragment.newInstance();
                    transaction.add(R.id.fragment_group, loanFragment);
                }
                transaction.show(loanFragment);
                break;

            case Constants.TYPE_NAVIGATION:
                mTitle.setText(getString(R.string.navigation));
                if (mHomeFragment == null) {
                    mHomeFragment = HomeFragment.newInstance();
                    transaction.add(R.id.fragment_group, mHomeFragment);
                }
                transaction.show(mHomeFragment);
                break;

            case Constants.TYPE_PROJECT:
                mTitle.setText(getString(R.string.project));
                if (mMineFragment == null) {
                    mMineFragment = MineFragment.newInstance();
                    transaction.add(R.id.fragment_group, mMineFragment);
                }
                transaction.show(mMineFragment);
                break;

            default:

                break;
        }
        transaction.commit();
    }

    private void hideFragment(FragmentTransaction transaction) {
        switch (mLastFgIndex) {

            case Constants.TYPE_JOKEPAGER:
                if (loanFragment != null) {
                    transaction.hide(loanFragment);
                }
                break;
            case Constants.TYPE_NAVIGATION:
                if (mHomeFragment != null) {
                    transaction.hide(mHomeFragment);
                }
                break;


            case Constants.TYPE_PROJECT:
                if (mMineFragment != null) {
                    transaction.hide(mMineFragment);
                }
                break;
            default:
                break;
        }
    }

    private void initBottomNavigationView() {
        mBottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.tab_main_pager:
                    showFragment(Constants.TYPE_JOKEPAGER);
                    break;
                case R.id.tab_navigation:
                    showFragment(Constants.TYPE_NAVIGATION);
                    break;
                case R.id.tab_project:
                    showFragment(Constants.TYPE_PROJECT);
                    break;
                default:
                    break;
            }
            return true;
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initToolbar() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            mTitle.setText(R.string.home_pager);
        }
    }


    private void initNavigationView() {
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    //TODO navigation item
                    case R.id.nav_item_my_collect:
                        if (mPresenter.getLoginStatus()) {

                        } else {
                            CommonUtils.startLoginActivity(MainActivity.this);
                            ToastUtils.showToast(MainActivity.this, getString(R.string.login_first));
                        }
                        break;
                    case R.id.nav_item_todo:
                        ToastUtils.showToast(MainActivity.this, getString(R.string.in_the_process));
                        break;
                    case R.id.nav_item_night_mode:
                        recreate();
                        break;
                    case R.id.nav_item_setting:

                        break;
                    case R.id.nav_item_about_us:

                        break;
                    case R.id.nav_item_logout:
                        mPresenter.logout();
                        break;
                    default:
                        break;
                }
                return true;
            }
        });

//        mUsTv.setText(mPresenter.getLoginStatus() ? mPresenter.getLoginAccount() : getString(R.string.login));
//        mUsTv.setOnClickListener(v -> CommonUtils.startLoginActivity(MainActivity.this));
//        mNavigationView.getMenu().findItem(R.id.nav_item_logout).setVisible(mPresenter.getLoginStatus());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_toolbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_usage:

                break;
            case R.id.action_search:

                break;
            default:
                break;
        }
        return true;
    }

    @OnClick({R.id.main_floating_action_btn})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.main_floating_action_btn:
                jumpToTheTop();
                break;
            default:
                break;
        }
    }

    private void jumpToTheTop() {
        switch (mCurrentFgIndex) {
            case Constants.TYPE_JOKEPAGER:
                if (loanFragment != null) {

                }
                break;

//            case Constants.TYPE_HOME_PAGER:
//                if (mHomePagerFragment != null) {
//                    mHomePagerFragment.jumpToTheTop();
//                }
//                break;

            case Constants.TYPE_NAVIGATION:
                if (mHomeFragment != null) {
                    mHomeFragment.jumpToTheTop();
                }
                break;
            case Constants.TYPE_PROJECT:
                if (mMineFragment != null) {
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void initEventAndData() {

    }

    /**
     * 处理回退事件
     */
    @Override
    public void onBackPressedSupport() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            pop();
        } else {
            long currentTime = System.currentTimeMillis();
            if ((currentTime - clickTime) > Constants.DOUBLE_INTERVAL_TIME) {
                ToastUtils.showToast(MainActivity.this, getString(R.string.double_click_exit_toast));
                clickTime = System.currentTimeMillis();
            } else {
                finish();
            }
        }
    }

    @Override
    public void handleLoginSuccess() {
        mUsTv.setText(mPresenter.getLoginAccount());
        mUsTv.setOnClickListener(null);
        mNavigationView.getMenu().findItem(R.id.nav_item_logout).setVisible(true);
    }

    @Override
    public void handleLogoutSuccess() {
        mUsTv.setText(getString(R.string.login));
        mUsTv.setOnClickListener(v -> CommonUtils.startLoginActivity(MainActivity.this));
        mNavigationView.getMenu().findItem(R.id.nav_item_logout).setVisible(false);
    }

    @Override
    public void showLoading() {
        if (mDialog == null) {
            mDialog = CommonUtils.getLoadingDialog(this, getString(R.string.logging_out));
        }
        mDialog.show();
    }

    @Override
    public void hideLoading() {
        if (mDialog != null) {
            mDialog.dismiss();
            mDialog = null;
        }
    }
}
