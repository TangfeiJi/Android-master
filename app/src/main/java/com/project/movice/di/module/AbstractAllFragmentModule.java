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

package com.project.movice.di.module;

import com.project.movice.di.component.BaseFragmentComponent;
import com.project.movice.modules.home.ui.HomeFragment;
import com.project.movice.modules.loan.ui.LoanFragment;

import com.project.movice.modules.login.ui.LoginFragment;
import com.project.movice.modules.login.ui.RegisterFragment;
import com.project.movice.modules.mine.ui.MineFragment;


import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module(subcomponents = BaseFragmentComponent.class)
public abstract class AbstractAllFragmentModule {


    @ContributesAndroidInjector(modules = LoanFragmentModule.class)
    abstract LoanFragment contributesLoanFragmentInject();

    @ContributesAndroidInjector(modules = LoginFragmentModule.class)
    abstract LoginFragment contributesLoginFragmentInject();

    @ContributesAndroidInjector(modules = RegisterFragmentModule.class)
    abstract RegisterFragment contributesRegisterFragmentInject();

    @ContributesAndroidInjector(modules = HomeFragmentModule.class)
    abstract HomeFragment contributesHomeFragmentInject();

    @ContributesAndroidInjector(modules = MineFragmentModule.class)
    abstract MineFragment contributesMineFragmentInject();

}
