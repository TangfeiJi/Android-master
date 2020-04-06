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

package com.project.movice.core.http;

import com.project.movice.core.http.api.ApiService;


import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Part;


public class HttpHelperImpl implements HttpHelper {

    private ApiService mApiService;

    @Inject
    HttpHelperImpl(ApiService apiService) {
        mApiService = apiService;
    }



    @Override
    public Observable<BaseResponse> getVersion(Map<String, String> options) {
        return mApiService.getVersion(options);
    }

    @Override
    public Observable<BaseResponse> getHome(Map<String, String> options) {
        return mApiService.getHome(options);
    }

    @Override
    public Observable<BaseResponse> get026(Map<String, String> options) {
        return mApiService.get026(options);
    }

    @Override
    public Observable<BaseResponse> get012(Map<String, String> options) {
        return mApiService.get012(options);
    }

    @Override
    public Observable<BaseResponse> get018(Map<String, String> options) {
        return mApiService.get018(options);
    }
    @Override
    public Observable<BaseResponse> get020(Map<String, String> options) {
        return mApiService.get020(options);
    }

    @Override
    public Observable<BaseResponse> get019(Map<String, String> options) {
        return mApiService.get019(options);
    }

    @Override
    public Observable<BaseResponse> get006(Map<String, String> options) {
        return mApiService.get006(options);
    }

    @Override
    public Observable<BaseResponse> get054(Map<String, String> options) {
        return mApiService.get054(options);
    }
    @Override
    public Observable<BaseResponse> get055(Map<String, String> options) {
        return mApiService.get055(options);
    }



    @Override
    public Observable<BaseResponse> get002(Map<String, String> options) {
        return mApiService.get002(options);
    }

    @Override
    public Observable<BaseResponse> get008(Map<String, String> options) {
        return mApiService.get008(options);
    }
    @Override
    public Observable<BaseResponse> get014(Map<String, String> options) {
        return mApiService.get014(options);
    }


    @Override
    public Observable<BaseResponse> get007(Map<String, String> options) {
        return mApiService.get007(options);
    }
    @Override
    public Observable<BaseResponse> get013(Map<String, String> options) {
        return mApiService.get013(options);
    }

    @Override
    public Observable<BaseResponse> get030(Map<String, String> options) {
        return mApiService.get030(options);
    }
    @Override
    public Observable<BaseResponse> get024(Map<String, String> options) {
        return mApiService.get024(options);
    }
    @Override
    public Observable<BaseResponse> get025(Map<String, String> options) {
        return mApiService.get025(options);
    }

    @Override
    public Observable<BaseResponse> get021(Map<String, String> options) {
        return mApiService.get021(options);
    }

    @Override
    public Observable<BaseResponse> get010(Map<String, String> options) {
        return mApiService.get010(options);
    }
    @Override
    public Observable<BaseResponse> get016(Map<String, String> options) {
        return mApiService.get016(options);
    }
    @Override
    public Observable<BaseResponse> get049(Map<String, String> options) {
        return mApiService.get049(options);
    }
    @Override
    public Observable<BaseResponse> get034(Map<String, String> options) {
        return mApiService.get034(options);
    }
    @Override
    public Observable<BaseResponse> get0122(Map<String, String> options) {
        return mApiService.get0122(options);
    }


    @Override
    public Observable<BaseResponse> get0277(@Body RequestBody body) {
        return mApiService.get0277(body);
    }

    @Override
    public Observable<BaseResponse> get031(Map<String, String> options) {
        return mApiService.get031(options);
    }
    @Override
    public Observable<BaseResponse> get001(Map<String, String> options) {
        return mApiService.get001(options);
    }

    @Override
    public Observable<BaseResponse> get053(Map<String, String> options) {
        return mApiService.get053(options);
    }
}
