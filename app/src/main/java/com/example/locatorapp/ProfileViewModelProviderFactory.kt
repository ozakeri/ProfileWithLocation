package com.example.locatorapp

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.locatorapp.repository.ProfileRepository
import com.example.locatorapp.viewmodel.ProfileViewModel

class ProfileViewModelProviderFactory(
    val application: Application,
    val repository: ProfileRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProfileViewModel(application, repository) as T
    }
}