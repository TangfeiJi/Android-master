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

package com.project.movice.core.http.api;

import com.project.movice.core.http.BaseResponse;
import com.project.movice.modules.loan.bean.JokeBean;

import com.project.movice.modules.login.bean.LoginData;
import com.project.movice.modules.mine.bean.ProjectTreeData;


import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {



    String BASE_MURL = "http://www.duans.top";



    /**
     * 获取段子列表
     * http://www.duans.top/freeApi/api.php?act=getJoke&page=1&count=10
     */
    @POST("/freeApi/api.php?act=getJoke")
    @FormUrlEncoded
    Observable<BaseResponse<List<JokeBean>>> getJoke(@Field("page") String pager,@Field("count") String num);


    /**
     * 获取段子列表
     * http://www.duans.top/freeApi/api.php?act=getJoke&page=1&count=10
     */
    @POST("/freeApi/api.php?act=getJoke")
    Observable<BaseResponse<List<JokeBean>>> requestTicketsData(@Body Object event);










}
