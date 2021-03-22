package com.project_binar.kbg.ui.register

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvm2application.model.register.DataRegister
import com.example.mvvm2application.model.register.RegisterBody
import com.example.mvvm2application.model.register.RegisterResponse
import com.project_binar.kbg.repository.RemoteRepository
import kotlinx.coroutines.launch

class RegisterViewModel (private val repository: RemoteRepository) : ViewModel(){
    val _dataRegister = MutableLiveData<RegisterResponse>()
    private val _error = MutableLiveData<String>()
    fun register(registerBody: RegisterBody) = viewModelScope.launch {
            repository.register(registerBody, {
                _dataRegister.value=it
            },{
                _error.value=it
                Log.e("error","$_dataRegister")

            })
    }
}