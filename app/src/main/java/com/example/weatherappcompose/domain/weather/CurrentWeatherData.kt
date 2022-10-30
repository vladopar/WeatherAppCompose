package com.example.weatherappcompose.domain.weather

import java.time.LocalDateTime

data class CurrentWeatherData(
    val time: LocalDateTime,
    val weatherType: WeatherType,
    val temperature: Double,
    val windSpeed: Double,
    val windDirection: String,
    val sunrise: String,
    val sunset: String
    )