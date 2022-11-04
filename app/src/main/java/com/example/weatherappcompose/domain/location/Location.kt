package com.example.weatherappcompose.domain.location

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "favoritelocations")
data class Location(
    @PrimaryKey(autoGenerate = false)
    val id: Int?,
    val name: String,
    val lat: Double,
    val lon: Double,
    @ColumnInfo(name = "countrycode")
    val countryCode: String?,
    val region: String?
)