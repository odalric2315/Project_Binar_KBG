package com.project_binar.kbg.ui.history

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project_binar.kbg.model.history.GetHistoryData
import com.project_binar.kbg.repository.RemoteRepository
import kotlinx.coroutines.launch

class HistoryViewModel(private val repository: RemoteRepository) : ViewModel() {
    private var _dataHistory = MutableLiveData<List<GetHistoryData>>()
    var getDataHistory: LiveData<List<GetHistoryData>> = _dataHistory
    fun getHistory(token: String)= viewModelScope.launch { repository.getHistory(token,{
        _dataHistory.value=it?.historyData
    },{
        Log.e("Error",it)
    })

    }
}