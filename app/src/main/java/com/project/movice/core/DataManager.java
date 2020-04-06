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


import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Part;

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
    public Observable<BaseResponse> getVersion(Map<String, String> options) {
        return mHttpHelper.getVersion(options);
    }

    @Override
    public Observable<BaseResponse> getHome(Map<String, String> options) {
        return mHttpHelper.getHome(options);
    }

    @Override
    public Observable<BaseResponse> get026(Map<String, String> options) {
        return mHttpHelper.get026(options);
    }

    @Override
    public Observable<BaseResponse> get012(Map<String, String> options) {
        return mHttpHelper.get012(options);
    }

    @Override
    public Observable<BaseResponse> get018(Map<String, String> options) {
        return mHttpHelper.get018(options);
    }
    @Override
    public Observable<BaseResponse> get020(Map<String, String> options) {
        return mHttpHelper.get020(options);
    }
    @Override
    public Observable<BaseResponse> get019(Map<String, String> options) {
        return mHttpHelper.get019(options);
    }

    @Override
    public Observable<BaseResponse> get006(Map<String, String> options) {
        return mHttpHelper.get006(options);
    }

    @Override
    public Observable<BaseResponse> get054(Map<String, String> options) {
        return mHttpHelper.get054(options);
    }
    @Override
    public Observable<BaseResponse> get055(Map<String, String> options) {
        return mHttpHelper.get055(options);
    }



    @Override
    public Observable<BaseResponse> get002(Map<String, String> options) {
        return mHttpHelper.get002(options);
    }
    @Override
    public Observable<BaseResponse> get008(Map<String, String> options) {
        return mHttpHelper.get008(options);
    }
    @Override
    public Observable<BaseResponse> get014(Map<String, String> options) {
        return mHttpHelper.get014(options);
    }

    @Override
    public Observable<BaseResponse> get007(Map<String, String> options) {
        return mHttpHelper.get007(options);
    }

    @Override
    public Observable<BaseResponse> get013(Map<String, String> options) {
        return mHttpHelper.get013(options);
    }

    @Override
    public Observable<BaseResponse> get030(Map<String, String> options) {
        return mHttpHelper.get030(options);
    }

    @Override
    public Observable<BaseResponse> get024(Map<String, String> options) {
        return mHttpHelper.get024(options);
    }
    @Override
    public Observable<BaseResponse> get025(Map<String, String> options) {
        return mHttpHelper.get025(options);
    }

    @Override
    public Observable<BaseResponse> get021(Map<String, String> options) {
        return mHttpHelper.get021(options);
    }
    @Override
    public Observable<BaseResponse> get010(Map<String, String> options) {
        return mHttpHelper.get010(options);
    }

    @Override
    public Observable<BaseResponse> get016(Map<String, String> options) {
        return mHttpHelper.get016(options);
    }
    @Override
    public Observable<BaseResponse> get049(Map<String, String> options) {
        return mHttpHelper.get049(options);
    }

    @Override
    public Observable<BaseResponse> get034(Map<String, String> options) {
        return mHttpHelper.get034(options);
    }
    @Override
    public Observable<BaseResponse> get0122(Map<String, String> options) {
        return mHttpHelper.get0122(options);
    }


    @Override
    public Observable<BaseResponse> get0277(@Body RequestBody body) {
        return mHttpHelper.get0277(body);
    }
    @Override
    public Observable<BaseResponse> get031(Map<String, String> options) {
        return mHttpHelper.get031(options);
    }
    @Override
    public Observable<BaseResponse> get001(Map<String, String> options) {
        return mHttpHelper.get001(options);
    }
    @Override
    public Observable<BaseResponse> get053(Map<String, String> options) {
        return mHttpHelper.get053(options);
    }

    //    ===============================================================
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
