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


import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;

public interface ApiService {

//    http://149.129.251.98

//    String BASE_MURL = "http://149.129.251.98";



//    测试的
      String BASE_MURL ="https://api.makmurabadiin.com";

    //    图片上传
    String BASE_IMAGE="http://cdn.makmurabadiin.com";



    /**协议地址***/
    public final static String DES_KEY = "UANGgYAXiQUU0gkGGmkjaeTlAdzL3ugsm9LtSGPDlM0im1Jvvjv9NBBWBoGOT2mW";//加密key0


    public final static String ZHICHI_KEY = "873376b262d74b388957d47f614917f4";//智齿客服



    public  static String getRATE =BASE_MURL+"/rate/rate" ;


    public  static String PROTOCAL =BASE_MURL+"/protocal/protocal" ;


    /**
     * 获取版本
     */
    @POST("/032.ks?")
    Observable<BaseResponse> getVersion(@QueryMap Map<String, String> options);




    /**
     * 首页获取贷款信息
     */
    @POST("/v1.5/004.ks?")
    Observable<BaseResponse> getHome(@QueryMap Map<String, String> options);


    /**
     * 查询用户是否可以借款
     */
    @POST("/026.ks?")
    Observable<BaseResponse> get026(@QueryMap Map<String, String> options);



    /**
     * 获取个人完善状态
     */
    @POST("/v1.2/012.ks?")
    Observable<BaseResponse> get012(@QueryMap Map<String, String> options);


    /**
     * 取消订单
     */
    @POST("/018.ks?")
    Observable<BaseResponse> get018(@QueryMap Map<String, String> options);

    /**
     /*确认审核失败／放款失败／还款成功　订单
     */
    @POST("/020.ks?")
    Observable<BaseResponse> get020(@QueryMap Map<String, String> options);

    /**
     * 确认还款银行
     */
    @POST("/019.ks?")
    Observable<BaseResponse> get019(@QueryMap Map<String, String> options);


    /**
     * 获取个人基本信息
     */
    @POST("/v1.0/006.ks?")
    Observable<BaseResponse> get006(@QueryMap Map<String, String> options);


    /**
     * 提交个人基本信息
     */
    @POST("/v1.0/054.ks?")
    Observable<BaseResponse> get054(@QueryMap Map<String, String> options);

    /**
     * 提交个人基本信息card
     */
    @POST("/v1.0/055.ks?")
    Observable<BaseResponse> get055(@QueryMap Map<String, String> options);







    /**
     * 上传图片
     */
    @POST(BASE_IMAGE+"/027.api?")
    Observable<BaseResponse> get0277(@Body RequestBody body);




    /**
     * 登录
     */
    @POST("/002.ks?")
    Observable<BaseResponse> get002(@QueryMap Map<String, String> options);




    /**
     * 获取联系人信息接口
     */
    @POST("v1.0/008.ks?")
    Observable<BaseResponse> get008(@QueryMap Map<String, String> options);



    /**
     * 提交联系人信息接口
     */
    @POST("v1.0/014.ks?")
    Observable<BaseResponse> get014(@QueryMap Map<String, String> options);


    /**
     * 获取工作信息接口
     */
    @POST("v1.1/007.ks?")
    Observable<BaseResponse> get007(@QueryMap Map<String, String> options);


    /**
     * 提交工作信息
     */
    @POST("v1.1/013.ks?")
    Observable<BaseResponse> get013(@QueryMap Map<String, String> options);




    /**
     * 查询订单/借款记录
     */
    @POST("v1.0/030.ks?")
    Observable<BaseResponse> get030(@QueryMap Map<String, String> options);



    /**
     * 获取Mini助手二级列表
     */
    @POST("024.ks?")
    Observable<BaseResponse> get024(@QueryMap Map<String, String> options);

    /**
     * 点赞，点踩
     */
    @POST("025.ks?")
    Observable<BaseResponse> get025(@QueryMap Map<String, String> options);

    /**
     * 获取收款银行列表
     */
    @POST("021.ks?")
    Observable<BaseResponse> get021(@QueryMap Map<String, String> options);


    /**
     * 获取银行信息
     */
    @POST("010.ks?")
    Observable<BaseResponse> get010(@QueryMap Map<String, String> options);

    /**
     * 提交银行信息
     */
    @POST("016.ks?")
    Observable<BaseResponse> get016(@QueryMap Map<String, String> options);

    /**
     * 计算借款金额
     */
    @POST("v1.5/049.ks?")
    Observable<BaseResponse> get049(@QueryMap Map<String, String> options);

    /**
     * 提交隐私信息
     */
    @FormUrlEncoded
    @POST("034.ks?")
    Observable<BaseResponse> get034(@FieldMap Map<String, String> options);
    /**
     * 提交借款
     */
    @POST("v1.5/012.ks")
    Observable<BaseResponse> get0122(@QueryMap Map<String, String> options);



    /**
     * 查询订单详情
     */
    @POST("031.ks")
    Observable<BaseResponse> get031(@QueryMap Map<String, String> options);


    /**
     * 发送验证码
     */
    @POST("001.ks")
    Observable<BaseResponse> get001(@QueryMap Map<String, String> options);



    /**
     * 获取四级联动表
     */
    @POST("053.ks")
    Observable<BaseResponse> get053(@QueryMap Map<String, String> options);



}
