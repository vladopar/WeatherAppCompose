package com.example.weatherappcompose.presentation

import com.example.weatherappcompose.domain.location.Location
import com.example.weatherappcompose.domain.weather.WeatherInfo

data class UiState(
    val weatherInfo: WeatherInfo? = null,
    val locationList: List<Location>? = null,
    val favoriteLocationList: List<Location>? = null,
    val currentSelectedLocation: Location,
    var name: String = ""
)