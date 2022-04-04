package com.example.locatorapp.api

import com.example.locatorapp.model.RequestBean
import com.example.locatorapp.model.ResponseBean
import com.example.locatorapp.util.Constant.Companion.CONTENT_PROVIDER
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface LocatorApi {

    @POST("api/karfarmas/address")
    suspend fun saveAddress(
        @Body requestBean: RequestBean,
        @Header("Content-Type") content_type: String = CONTENT_PROVIDER

    ): Response<ResponseBean>
}