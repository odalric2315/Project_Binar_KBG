package com.project_binar.kbg.repository

import android.util.Log
import com.example.mvvm2application.model.login.LoginBody
import com.example.mvvm2application.model.login.LoginResponse
import com.example.mvvm2application.model.profile.ProfileResponse
import com.example.mvvm2application.model.register.RegisterBody
import com.example.mvvm2application.model.register.RegisterResponse
import com.project_binar.kbg.api.ApiService
import com.project_binar.kbg.model.history.AddHistoryBody
import com.project_binar.kbg.model.history.AddHistoryResponse
import com.project_binar.kbg.model.history.GetHistoryResponse

class RemoteRepository(private val apiService: ApiService) {
    suspend fun login(
        loginBody: LoginBody,
        onResult: (LoginResponse?) -> Unit,
        onError: (String) -> Unit
    ) {
        val response = apiService.login(loginBody)
        if (response.isSuccessful) {
            onResult(response.body())
        } else {
            onError(response.message())
        }
    }

    suspend fun register(
        registerBody: RegisterBody,
        onResult: (RegisterResponse?) -> Unit,
        onError: (String) -> Unit
    ) {
        val response = apiService.register(registerBody)
        if(response.isSuccessful) {
            onResult(response.body())
        } else {
            onError(response.message())
        }
    }

    suspend fun getProfile(
        token: String,
        onResult: (ProfileResponse?) -> Unit,
        onError: (String) -> Unit
    ) {
        val response = apiService.getProfile(token)
        if (response.isSuccessful) {
            onResult(response.body())
        } else {
            onError(response.message())
        }
    }
    suspend fun getHistory(
        token: String,
        onResult:(GetHistoryResponse?) -> Unit,
        onError: (String) -> Unit
    ) {
        val response = apiService.getHistory(token)
        if (response.isSuccessful) {
            onResult(response.body())
        } else {
            onError(response.message())
        }
    }
    suspend fun addHistory(
        token: String,
        addHistoryBody: AddHistoryBody,
        onResult: (AddHistoryResponse?)-> Unit,
        onError: (String) -> Unit
    ) {
        val response = apiService.addHistory(token,addHistoryBody)
        if (response.isSuccessful){
            onResult(response.body())
        }else {
            onError(response.message())
        }
    }
}