package com.example.locatorapp.model

data class RequestBean(
    var region: Int,
    var address: String,
    var coordinate_mobile: String,
    var coordinate_phone_number: String,
    var first_name: String,
    var gender: String,
    var last_name: String,
    var lat: Double,
    var lng: Double
)