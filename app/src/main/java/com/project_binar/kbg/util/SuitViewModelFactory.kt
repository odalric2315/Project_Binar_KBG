package com.project_binar.kbg.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.project_binar.kbg.repository.RemoteRepository
import com.project_binar.kbg.ui.history.HistoryViewModel
import com.project_binar.kbg.ui.home.HomeViewModel
import com.project_binar.kbg.ui.login.LoginViewModel
import com.project_binar.kbg.ui.profile.ProfileViewModel
import com.project_binar.kbg.ui.register.RegisterViewModel

class SuitViewModelFactory (private val repository: RemoteRepository) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                return LoginViewModel(repository) as T
            }
            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> {
                return ProfileViewModel(repository) as T
            }
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                return HomeViewModel(repository) as T
            }
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                return RegisterViewModel(repository) as T
            }
            modelClass.isAssignableFrom(HistoryViewModel::class.java) -> {
                return HistoryViewModel(repository) as T
            }
            modelClass.isAssignableFrom(PlayViewModel::class.java) -> {
                return PlayViewModel(repository) as T
            }
            else -> throw IllegalArgumentException("ERROR UNKNOWN VIEWMODEL CLASS")
        }
    }

}