package com.example.weatherappcompose.domain.location

data class Location(
    val id: Int,
    val name: String,
    val lat: Double,
    val long: Double,
    val countryCode: String,
    val region: String?
)