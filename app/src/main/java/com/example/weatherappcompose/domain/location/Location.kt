package com.example.weatherappcompose.domain.location

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "favoritelocations")
data class Location(
    @PrimaryKey(autoGenerate = false)
    var id: Int?,
    var name: String,
    var lat: Double,
    var long: Double,
    @ColumnInfo(name = "countrycode")
    var countryCode: String?,
    var region: String?
) {
    constructor() : this(
        0,
        "",
        0.0,
        0.0,
        "",
        ""
    )
}