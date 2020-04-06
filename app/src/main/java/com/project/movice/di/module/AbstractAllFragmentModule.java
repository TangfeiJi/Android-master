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
import com.project.movice.modules.area.AreaFragment;
import com.project.movice.modules.home.ui.BorrowingStatusFragment;
import com.project.movice.modules.home.ui.HomeFragment;
import com.project.movice.modules.home.ui.HomeLoanFragment;
import com.project.movice.modules.home.ui.StatusCancelFragment;
import com.project.movice.modules.home.ui.StatusFailuerFragment;
import com.project.movice.modules.home.ui.StatusRepaymentConfirmFragment;
import com.project.movice.modules.home.ui.StatusRepaymentSelectBankFragment;
import com.project.movice.modules.home.ui.StatusRepaymentSuccessfulFragment;
import com.project.movice.modules.loan.ui.LoanFragment;

import com.project.movice.modules.mine.ui.MineFragment;


import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module(subcomponents = BaseFragmentComponent.class)
public abstract class AbstractAllFragmentModule {


    @ContributesAndroidInjector(modules = LoanFragmentModule.class)
    abstract LoanFragment contributesLoanFragmentInject();


    @ContributesAndroidInjector(modules = HomeFragmentModule.class)
    abstract HomeFragment contributesHomeFragmentInject();

    @ContributesAndroidInjector(modules = MineFragmentModule.class)
    abstract MineFragment contributesMineFragmentInject();

    @ContributesAndroidInjector(modules = ProductTwoFragmentModule.class)
    abstract HomeLoanFragment contributesProductTwoFragmentInject();


    @ContributesAndroidInjector(modules = StatusCancelFragmentModule.class)
    abstract StatusCancelFragment StatusCancelFragmentInject();
    @ContributesAndroidInjector(modules = StatusFailuerFragmentModule.class)
    abstract StatusFailuerFragment StatusFailuerFragmentInject();

    @ContributesAndroidInjector(modules = StatusRepaymentSelectBankFragmentModule.class)
    abstract StatusRepaymentSelectBankFragment StatusRepaymentSelectBankFragmentInject();

    @ContributesAndroidInjector(modules = StatusRepaymentSuccessfulFragmentModule.class)
    abstract StatusRepaymentSuccessfulFragment StatusRepaymentSuccessfulFragmentInject();


    @ContributesAndroidInjector(modules = StatusRepaymentConfirmFragmentModule.class)
    abstract StatusRepaymentConfirmFragment StatusRepaymentConfirmFragmentInject();

    @ContributesAndroidInjector(modules = AreaFragmentModule.class)
    abstract AreaFragment AreaFragmentInject();



    @ContributesAndroidInjector(modules = BorrowingStatusFragmentModule.class)
    abstract BorrowingStatusFragment BorrowingStatusFragmentInject();

}
