package com.example.weatherappcompose.data.remote

import com.squareup.moshi.Json


data class LocationDto(
    val results: List<Result>?,
    @Json(name = "generationtime_ms") val generationTime: Double
)