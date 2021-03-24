package com.project_binar.kbg.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project_binar.kbg.model.profile.DataProfile
import com.project_binar.kbg.repository.RemoteRepository
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: RemoteRepository) : ViewModel() {
    private val _dataProfile = MutableLiveData<DataProfile>()
    val getDataProfile: LiveData<DataProfile> = _dataProfile

    fun getProfile(token: String) = viewModelScope.launch {
        repository.getProfile(token, {
            _dataProfile.value = it?.data
        }, {
            Log.e("ERROR", it)
        })
    }
}