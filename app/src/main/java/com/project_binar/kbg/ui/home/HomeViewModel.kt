package com.project_binar.kbg.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project_binar.kbg.model.login.DataLogin
import com.project_binar.kbg.model.login.LoginBody
import com.project_binar.kbg.model.profile.DataProfile
import com.project_binar.kbg.repository.RemoteRepository
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: RemoteRepository) : ViewModel() {
    private val _dataProfile = MutableLiveData<DataProfile>()
    val getDataProfile: LiveData<DataProfile> = _dataProfile
    private val _dataLogin = MutableLiveData<DataLogin>()
    val getDataLogin: LiveData<DataLogin> = _dataLogin
    private val _error = MutableLiveData<String>()
    val getErrorLogin: LiveData<String> = _error
    val getError: LiveData<String> = _error
    fun getProfile(token: String) = viewModelScope.launch {
        repository.getProfile(token, {
            _dataProfile.value = it?.data
        }, {
            Log.e("ERROR", it)
            _error.value=it
        })
    }
    fun login(loginBody: LoginBody) = viewModelScope.launch {
        repository.login(loginBody, {
            _dataLogin.value = it?.data
        }, {
            _error.value = it
        })
    }
}