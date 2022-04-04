package com.example.locatorapp.model

data class RequestBean(
    val address: String,
    val coordinate_mobile: String,
    val coordinate_phone_number: String,
    val first_name: String,
    val gender: String,
    val last_name: String,
    val lat: Double,
    val lng: Double,
    val region: Int
)