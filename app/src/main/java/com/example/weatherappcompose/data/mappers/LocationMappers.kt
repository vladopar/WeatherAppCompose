package com.example.weatherappcompose.data.mappers

import com.example.weatherappcompose.data.remote.LocationDto
import com.example.weatherappcompose.domain.location.Location

fun LocationDto.toLocationList(): List<Location> {
    val list = mutableListOf<Location>()
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
    return list
}
