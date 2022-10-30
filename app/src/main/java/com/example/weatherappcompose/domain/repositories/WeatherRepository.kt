package com.example.weatherappcompose.domain.repositories

import com.example.weatherappcompose.domain.weather.WeatherInfo
import com.example.weatherappcompose.domain.util.Resource

interface WeatherRepository {
    suspend fun getWeatherData(lat: Double, long: Double) : Resource<WeatherInfo>
}