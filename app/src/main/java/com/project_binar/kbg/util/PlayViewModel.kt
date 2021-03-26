package com.project_binar.kbg.util

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project_binar.kbg.model.history.AddHistoryBody
import com.project_binar.kbg.model.history.AddHistoryData
import com.project_binar.kbg.repository.RemoteRepository
import kotlinx.coroutines.launch

class PlayViewModel (private val repository: RemoteRepository) : ViewModel(){
    private val _dataAddHistory = MutableLiveData<AddHistoryData>()
    val addHistoryData: LiveData<AddHistoryData> = _dataAddHistory
    private val _error = MutableLiveData<String>()
    val getError: LiveData<String> = _error
    fun addHistory(token: String,addHistoryBody: AddHistoryBody) = viewModelScope.launch { repository.addHistory(token,addHistoryBody,{
        _dataAddHistory.value=it?.addHistoryData
    },{
        Log.e("ERROR",it)
        _error.value=it
    }) }
}