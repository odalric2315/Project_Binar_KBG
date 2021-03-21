package com.project_binar.kbg.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvm2application.model.login.DataLogin
import com.example.mvvm2application.model.login.LoginBody
import com.project_binar.kbg.repository.RemoteRepository
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: RemoteRepository) : ViewModel() {
    private val _dataLogin = MutableLiveData<DataLogin>()
    val getDataLogin: LiveData<DataLogin> = _dataLogin
    private val _error = MutableLiveData<String>()
    val getError: LiveData<String> = _error
    fun login(loginBody: LoginBody) = viewModelScope.launch {
        repository.login(loginBody, {
            _dataLogin.value = it?.data
        }, {
            _error.value = it
        })
    }

}