package com.project_binar.kbg.api

import com.example.mvvm2application.model.login.LoginBody
import com.example.mvvm2application.model.login.LoginResponse
import com.example.mvvm2application.model.profile.ProfileResponse
import com.example.mvvm2application.model.register.RegisterBody
import com.example.mvvm2application.model.register.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {
    @POST("auth/login")
    suspend fun login(@Body loginBody: LoginBody) : Response<LoginResponse>

    @POST("auth/register")
    suspend fun register(@Body registerBody: RegisterBody) : Response<RegisterResponse>

    @GET("users")
    suspend fun getProfile(@Header("Authorization")token: String): Response<ProfileResponse>
}