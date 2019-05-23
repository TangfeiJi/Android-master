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

package com.project.movice.modules.mine.ui;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.project.movice.R;
import com.project.movice.base.fragment.BaseFragment;
import com.project.movice.modules.mine.bean.ProjectTreeData;
import com.project.movice.modules.mine.contract.MineContract;
import com.project.movice.modules.mine.presenter.MinePresenter;

import java.util.List;

import butterknife.BindView;

public class MineFragment extends BaseFragment<MinePresenter> implements MineContract.View {

    private static final String TAG = "MineFragment";
    @BindView(R.id.project_tablayout)
    TabLayout mTabLayout;
    @BindView(R.id.project_viewpager)
    ViewPager mViewPager;

    private List<ProjectTreeData> mProjectTreeData;

    public static MineFragment newInstance() {
        return new MineFragment();
    }

    @Override
    protected void initView() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initEventAndData() {

    }





}
