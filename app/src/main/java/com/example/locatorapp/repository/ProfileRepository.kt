package com.example.locatorapp.repository

import com.example.locatorapp.api.RetrofitInstance
import com.example.locatorapp.model.RequestBean
import com.example.locatorapp.util.Constant.Companion.CONTENT_PROVIDER

class ProfileRepository {
    suspend fun saveContent(requestBean: RequestBean) =  RetrofitInstance.api.saveAddress(CONTENT_PROVIDER,requestBean)
}