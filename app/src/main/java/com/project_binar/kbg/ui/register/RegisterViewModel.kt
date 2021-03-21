package com.project_binar.kbg.ui.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvm2application.model.register.DataRegister
import com.example.mvvm2application.model.register.RegisterBody
import com.project_binar.kbg.repository.RemoteRepository
import kotlinx.coroutines.launch

class RegisterViewModel (private val repository: RemoteRepository) : ViewModel(){
    private val _dataRegister = MutableLiveData<DataRegister>()
    private val _error = MutableLiveData<String>()
    fun register(registerBody: RegisterBody) = viewModelScope.launch {
            repository.register(registerBody, {
                _dataRegister.value=it?.dataRegister
            },{
                _error.value=it
            })
    }
}