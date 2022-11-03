package com.example.weatherappcompose.data.remote


import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json

data class Result(
    val admin1: String?,
    @Json(name = "country_code") val countryCode: String,
    val id: Int,
    val latitude: Double,
    val longitude: Double,
    val name: String,
)