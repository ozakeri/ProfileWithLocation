package com.example.locatorapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.locatorapp.ProfileViewModelProviderFactory
import com.example.locatorapp.R
import com.example.locatorapp.repository.ProfileRepository
import com.example.locatorapp.viewmodel.ProfileViewModel

class MainActivity : AppCompatActivity() {

    lateinit var profileViewModel: ProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val repository = ProfileRepository()
        val viewModelProviderFactory = ProfileViewModelProviderFactory(application, repository)
        profileViewModel = ViewModelProvider(this, viewModelProviderFactory).get(ProfileViewModel::class.java)
  }
}