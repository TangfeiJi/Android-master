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

import com.project.movice.modules.loan.bean.JokeBean;

import com.project.movice.modules.login.bean.LoginData;
import com.project.movice.modules.mine.bean.ProjectTreeData;


import java.util.List;

import io.reactivex.Observable;


public interface HttpHelper {


    Observable<BaseResponse<List<JokeBean>>> getData(String pager, String num);
    Observable<BaseResponse<List<JokeBean>>> requestTicketsData(Object data);















}
