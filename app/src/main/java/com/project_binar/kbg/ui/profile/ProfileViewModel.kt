package com.project_binar.kbg.ui.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project_binar.kbg.model.profile.DataProfile
import com.project_binar.kbg.repository.RemoteRepository
import kotlinx.coroutines.launch

class ProfileViewModel(private val repository: RemoteRepository) : ViewModel() {
    private val _dataProfile = MutableLiveData<DataProfile>()
    val getDataProfile: LiveData<DataProfile> = _dataProfile
    private val _error = MutableLiveData<String>()
    val getError: LiveData<String> = _error
    fun getProfile(token: String) = viewModelScope.launch {repository.getProfile(token,{
        _dataProfile.value=it?.data
    },{
        Log.e("ERROR",it)
        _error.value=it
    })
    }
}