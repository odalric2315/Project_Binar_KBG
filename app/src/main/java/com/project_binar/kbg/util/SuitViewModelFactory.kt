package com.project_binar.kbg.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.project_binar.kbg.repository.RemoteRepository
import com.project_binar.kbg.ui.home.HomeViewModel
import com.project_binar.kbg.ui.login.LoginViewModel
import com.project_binar.kbg.ui.profile.ProfileViewModel
import com.project_binar.kbg.ui.register.RegisterViewModel
import java.lang.IllegalArgumentException

class SuitViewModelFactory (private val repository: RemoteRepository) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)){
            return LoginViewModel(repository) as T
        }
        else if(modelClass.isAssignableFrom(ProfileViewModel::class.java)){
            return ProfileViewModel(repository) as T
        }
        else if(modelClass.isAssignableFrom(HomeViewModel::class.java)){
            return HomeViewModel(repository) as T
        }
        else if(modelClass.isAssignableFrom(RegisterViewModel::class.java)){
            return RegisterViewModel(repository) as T
        }
        throw IllegalArgumentException("ERROR UNKNOWN VIEWMODEL CLASS")
    }

}