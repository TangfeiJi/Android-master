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


import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Part;


public interface HttpHelper {


    Observable<BaseResponse> getVersion(Map<String, String> options);

    Observable<BaseResponse> getHome(Map<String, String> options);


    Observable<BaseResponse> get026(Map<String, String> options);

    Observable<BaseResponse> get012(Map<String, String> options);


    Observable<BaseResponse> get018(Map<String, String> options);


    Observable<BaseResponse> get020(Map<String, String> options);

    Observable<BaseResponse> get019(Map<String, String> options);


    Observable<BaseResponse> get006(Map<String, String> options);

    Observable<BaseResponse> get054(Map<String, String> options);
    Observable<BaseResponse> get055(Map<String, String> options);

    Observable<BaseResponse> get002(Map<String, String> options);


    Observable<BaseResponse> get008(Map<String, String> options);
    Observable<BaseResponse> get014(Map<String, String> options);


    Observable<BaseResponse> get007(Map<String, String> options);

    Observable<BaseResponse> get013(Map<String, String> options);
    Observable<BaseResponse> get030(Map<String, String> options);

    Observable<BaseResponse> get024(Map<String, String> options);
    Observable<BaseResponse> get025(Map<String, String> options);
    Observable<BaseResponse> get021(Map<String, String> options);
    Observable<BaseResponse> get010(Map<String, String> options);

    Observable<BaseResponse> get016(Map<String, String> options);

    Observable<BaseResponse> get049(Map<String, String> options);

    Observable<BaseResponse> get034(Map<String, String> options);
    Observable<BaseResponse> get0122(Map<String, String> options);

    Observable<BaseResponse> get0277(@Body RequestBody body);
    Observable<BaseResponse> get031(Map<String, String> options);
    Observable<BaseResponse> get001(Map<String, String> options);

    Observable<BaseResponse> get053(Map<String, String> options);
}
