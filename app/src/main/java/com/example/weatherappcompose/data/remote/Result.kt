package com.example.weatherappcompose.data.remote


import com.google.gson.annotations.SerializedName

data class Result(
    val admin1: String,
    @SerializedName("admin1_id")
    val admin1Id: Int,
    val admin2: String,
    @SerializedName("admin2_id")
    val admin2Id: Int,
    val admin3: String,
    @SerializedName("admin3_id")
    val admin3Id: Int,
    val admin4: String,
    @SerializedName("admin4_id")
    val admin4Id: Int,
    val country: String,
    @SerializedName("country_code")
    val countryCode: String,
    @SerializedName("country_id")
    val countryId: Int,
    val elevation: Int,
    @SerializedName("feature_code")
    val featureCode: String,
    val id: Int,
    val latitude: Double,
    val longitude: Double,
    val name: String,
    val population: Int,
    val postcodes: List<String>,
    val timezone: String
)