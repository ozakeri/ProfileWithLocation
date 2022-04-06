package com.example.locatorapp.api

import com.example.locatorapp.model.RequestBean
import com.example.locatorapp.model.ResponseBean
import com.example.locatorapp.model.ResponseList
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface LocatorApi {

    @POST("api/karfarmas/address")
    suspend fun saveAddress(

        @Header("Content-Type") content_type: String = "application/json",
        @Body requestBean: RequestBean
    ): Response<ResponseBean>

    @GET("api/karfarmas/address")
    suspend fun getAddress(): Response<ResponseList>
}