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

import com.project.movice.application.MoviceApp;
import com.project.movice.core.DataManager;
import com.project.movice.core.db.DbHelper;
import com.project.movice.core.db.DbHelperImpl;
import com.project.movice.core.http.HttpHelper;
import com.project.movice.core.http.HttpHelperImpl;
import com.project.movice.core.preference.PreferenceHelper;
import com.project.movice.core.preference.PreferenceHelperImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    private final MoviceApp application;

    public AppModule(MoviceApp application) {
        this.application = application;
    }

    @Provides
    @Singleton
    MoviceApp provideApplicationContext() {
        return application;
    }

    @Provides
    @Singleton
    HttpHelper provideHttpHelper(HttpHelperImpl httpHelperImpl) {
        return httpHelperImpl;
    }

    @Provides
    @Singleton
    DbHelper provideDbHelper(DbHelperImpl dbHelperImpl) {
        return dbHelperImpl;
    }

    @Provides
    @Singleton
    PreferenceHelper providePreferenceHelper(PreferenceHelperImpl preferenceHelper) {
        return preferenceHelper;
    }

    @Provides
    @Singleton
    DataManager provideDataManager(HttpHelper httpHelper, DbHelper dbHelper, PreferenceHelper preferenceHelper) {
        return new DataManager(httpHelper, dbHelper, preferenceHelper);
    }

}
