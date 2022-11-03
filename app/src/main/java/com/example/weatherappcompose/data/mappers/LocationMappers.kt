package com.example.weatherappcompose.data.mappers

import android.util.Log
import com.example.weatherappcompose.data.remote.LocationDto
import com.example.weatherappcompose.domain.location.Location

fun LocationDto.toLocationList(): MutableList<Location> {
    val list = mutableListOf<Location>()
    Log.d("XXX","mapper called")
    for (location in results) {
        list.add(
            Location(
                id = location.id,
                name = location.name,
                lat = location.latitude,
                long = location.longitude,
                countryCode = location.countryCode,
                region = location.admin1
            )
        )
    }
    Log.d("XXX","$list")
    return list
}