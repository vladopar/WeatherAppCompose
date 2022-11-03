package com.example.weatherappcompose.domain.location

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

data class Location(
    val id: Int,
    val name: String,
    val lat: Double,
    val long: Double,
    val countryCode: String,
    val region: String
)