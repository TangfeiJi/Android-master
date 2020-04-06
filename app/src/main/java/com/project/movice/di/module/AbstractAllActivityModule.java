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

import com.project.movice.di.component.BaseActivityComponent;
import com.project.movice.modules.SplashActivity;
import com.project.movice.modules.area.AreaSelectActivity;
import com.project.movice.modules.drawname.DrawNameActivity;
import com.project.movice.modules.loan.ui.BankCardActivity;
import com.project.movice.modules.loan.ui.CardInforActivity;
import com.project.movice.modules.loan.ui.ContactInforActivity;
import com.project.movice.modules.loan.ui.ContractConfirmActivity;
import com.project.movice.modules.loan.ui.FaceCameraActivity;
import com.project.movice.modules.loan.ui.JobInforActivity;
import com.project.movice.modules.loan.ui.PersonalInforActivity;
import com.project.movice.modules.login.ui.LoginActivity;
import com.project.movice.modules.login.ui.FaceBookLoginActivity;
import com.project.movice.modules.main.ui.activity.MainActivity;
import com.project.movice.modules.main.ui.activity.SwichActivity;
import com.project.movice.modules.main.ui.activity.WebViewActivity;
import com.project.movice.modules.mine.ui.HelperDetailsActivity;
import com.project.movice.modules.mine.ui.LoanDetailsActivity;
import com.project.movice.modules.mine.ui.MyLoanActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module(subcomponents = {BaseActivityComponent.class})
public abstract class AbstractAllActivityModule {

    @ContributesAndroidInjector(modules = MainActivityModule.class)
    abstract MainActivity contributesMainActivityInjector();



    @ContributesAndroidInjector(modules = LoginActivityModule.class)
    abstract LoginActivity contributesLoginActivityInject();

    @ContributesAndroidInjector(modules = FaceBookLoginActivityModule.class)
    abstract FaceBookLoginActivity FaceBookLoginActivityInject();



    @ContributesAndroidInjector(modules = FaceCameraActivityModule.class)
    abstract FaceCameraActivity FaceCameractivityInject();


    @ContributesAndroidInjector(modules = CardActivityModule.class)
    abstract CardInforActivity CardActivityInject();

    @ContributesAndroidInjector(modules = PersonalInformationActivityModule.class)
    abstract PersonalInforActivity PersonalInformationActivityInject();

    @ContributesAndroidInjector(modules = ContactInformationActivityModule.class)
    abstract ContactInforActivity ContactInformationActivityInject();



    @ContributesAndroidInjector(modules = JobInformationActivityModule.class)
    abstract JobInforActivity JobInformationActivityInject();


    @ContributesAndroidInjector(modules = MyLoanActivityModule.class)
    abstract MyLoanActivity MyLoanActivityInject();


    @ContributesAndroidInjector(modules = HelperDetailsActivityModule.class)
    abstract HelperDetailsActivity HelperDetailsActivityInject();


    @ContributesAndroidInjector(modules = BankCardActivityModule.class)
    abstract BankCardActivity BankCardActivityInject();

    @ContributesAndroidInjector(modules = DrawNameActivityModule.class)
    abstract DrawNameActivity DrawNameActivityInject();


    @ContributesAndroidInjector(modules = ContractConfirmActivityModule.class)
    abstract ContractConfirmActivity ContractConfirmActivityInject();


    @ContributesAndroidInjector(modules = LoanDetailsActivityModule.class)
    abstract LoanDetailsActivity LoanDetailsActivityInject();

    @ContributesAndroidInjector(modules = WebViewActivityModule.class)
    abstract WebViewActivity WebViewActivityInject();
    @ContributesAndroidInjector(modules = SwichActivityModule.class)
    abstract SwichActivity SwichActivityInject();
    @ContributesAndroidInjector(modules = AreaSelectActivityModule.class)
    abstract AreaSelectActivity AreaSelectActivityInject();


    @ContributesAndroidInjector(modules = SplashActivityModule.class)
    abstract SplashActivity SplashActivityInject();

}


