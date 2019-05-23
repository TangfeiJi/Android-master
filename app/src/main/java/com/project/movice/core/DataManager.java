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

package com.project.movice.core;

import com.project.movice.core.db.DbHelper;
import com.project.movice.core.greendao.HistoryData;
import com.project.movice.core.http.BaseResponse;
import com.project.movice.core.http.HttpHelper;
import com.project.movice.core.preference.PreferenceHelper;
import com.project.movice.modules.loan.bean.JokeBean;
import com.project.movice.modules.login.bean.LoginData;
import com.project.movice.modules.mine.bean.ProjectTreeData;


import java.util.List;

import io.reactivex.Observable;

public class DataManager implements HttpHelper, DbHelper,PreferenceHelper {
    private HttpHelper mHttpHelper;
    private DbHelper mDbHelper;
    private PreferenceHelper mPreferenceHelper;

    public DataManager(HttpHelper httpHelper, DbHelper dbHelper, PreferenceHelper preferenceHelper) {
        mHttpHelper = httpHelper;
        mDbHelper = dbHelper;
        mPreferenceHelper = preferenceHelper;
    }

    @Override
    public Observable<BaseResponse<List<JokeBean>>> getData(String pager, String num) {
        return mHttpHelper.getData(pager,num);
    }

    @Override
    public Observable<BaseResponse<List<JokeBean>>> requestTicketsData(Object data) {

        return mHttpHelper.requestTicketsData(data);
    }













    @Override
    public List<HistoryData> addHistoryData(String data) {
        return mDbHelper.addHistoryData(data);
    }

    @Override
    public void clearAllHistoryData() {
        mDbHelper.clearAllHistoryData();
    }

    @Override
    public void deleteHistoryDataById(Long id) {
        mDbHelper.deleteHistoryDataById(id);
    }

    @Override
    public List<HistoryData> loadAllHistoryData() {
        return mDbHelper.loadAllHistoryData();
    }

    @Override
    public void setLoginStatus(boolean isLogin) {
        mPreferenceHelper.setLoginStatus(isLogin);
    }

    @Override
    public boolean getLoginStatus() {
        return mPreferenceHelper.getLoginStatus();
    }

    @Override
    public void setLoginAccount(String account) {
        mPreferenceHelper.setLoginAccount(account);
    }

    @Override
    public String getLoginAccount() {
        return mPreferenceHelper.getLoginAccount();
    }

}
