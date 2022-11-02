package com.example.weatherappcompose.domain.repositories

import com.example.weatherappcompose.data.remote.LocationDto
import com.example.weatherappcompose.domain.weather.WeatherInfo
import com.example.weatherappcompose.domain.util.Resource

interface LocationRepository {
    suspend fun getLocationData(name: String) : Resource<LocationDto>
}