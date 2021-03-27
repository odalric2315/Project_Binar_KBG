package com.project_binar.kbg.api

import com.project_binar.kbg.model.history.AddHistoryBody
import com.project_binar.kbg.model.history.AddHistoryResponse
import com.project_binar.kbg.model.history.GetHistoryResponse
import com.project_binar.kbg.model.login.LoginBody
import com.project_binar.kbg.model.login.LoginResponse
import com.project_binar.kbg.model.profile.ProfileResponse
import com.project_binar.kbg.model.register.RegisterBody
import com.project_binar.kbg.model.register.RegisterResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @POST("auth/login")
    suspend fun login(@Body loginBody: LoginBody) : Response<LoginResponse>

    @POST("auth/register")
    suspend fun register(@Body registerBody: RegisterBody) : Response<RegisterResponse>

    @GET("users")
    suspend fun getProfile(@Header("Authorization")token: String): Response<ProfileResponse>

    @POST("battle")
    suspend fun addHistory(@Header("Authorization")token: String,@Body addHistoryBody: AddHistoryBody) : Response<AddHistoryResponse>

    @GET("battle")
    suspend fun getHistory(@Header("Authorization")token: String): Response<GetHistoryResponse>

    @Multipart
    @PUT("users")
    suspend fun updProfile(
        @Header("Authorization")token: String,
        @PartMap map: HashMap<String, RequestBody>,
        @Part photo: MultipartBody.Part?
    ):Response<ProfileResponse>
}