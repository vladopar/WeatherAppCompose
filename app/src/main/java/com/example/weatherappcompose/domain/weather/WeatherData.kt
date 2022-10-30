package com.example.weatherappcompose.domain.weather

import java.time.DayOfWeek
import java.time.LocalDateTime

data class WeatherData(
    val date: LocalDateTime,
    val weatherType: WeatherType,
    val temperatureMax: Double,
    val temperatureMin: Double = 0.0,
    val precipitation: Double,
    val windSpeed: Double,
    val windDirection: String,
    val dayOfWeek: DayOfWeek = date.dayOfWeek
)