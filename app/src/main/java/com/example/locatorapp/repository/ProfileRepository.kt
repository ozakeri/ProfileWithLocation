package com.example.locatorapp.repository

import com.example.locatorapp.api.RetrofitInstance
import com.example.locatorapp.model.RequestBean

class ProfileRepository {
    suspend fun saveContent(requestBean: RequestBean) =  RetrofitInstance.api.saveAddress(requestBean)
}