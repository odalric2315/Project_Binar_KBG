package com.com.dagger.projecbinar_kbr.ui.history

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mygameapplication.model.gethistory.GetHistoryData
import com.example.mygameapplication.repository.RemoteRepository
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