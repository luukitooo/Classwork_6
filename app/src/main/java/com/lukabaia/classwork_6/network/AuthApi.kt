package com.lukabaia.classwork_6.network

import com.lukabaia.classwork_6.model.LoginResult
import com.lukabaia.classwork_6.model.RegisterResult
import com.lukabaia.classwork_6.model.UserInfo
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("login")
    suspend fun getLoginResult(
        @Body userInfo: UserInfo
    ): Response<LoginResult>

    @POST("register")
    suspend fun getRegisterResult(
        @Body userInfo: UserInfo
    ): Response<RegisterResult>

}