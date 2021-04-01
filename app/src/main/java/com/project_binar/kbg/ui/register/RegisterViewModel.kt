package com.project_binar.kbg.ui.register

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project_binar.kbg.model.register.RegisterBody
import com.project_binar.kbg.model.register.RegisterResponse
import com.project_binar.kbg.repository.RemoteRepository
import kotlinx.coroutines.launch

class RegisterViewModel (private val repository: RemoteRepository) : ViewModel(){
    val _dataRegister = MutableLiveData<RegisterResponse>()
    private val _error = MutableLiveData<String>()
    val getError: LiveData<String> = _error
    fun register(registerBody: RegisterBody) = viewModelScope.launch {
            repository.register(registerBody, {
                _dataRegister.value=it
            },{
                _error.value=it
                Log.e("error",it)

            })
    }
}