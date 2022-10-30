package com.example.weatherappcompose.domain.weather

import java.time.DayOfWeek
import java.time.LocalDate

data class DailyWeatherData(
    val date: LocalDate,
    val weatherType: WeatherType,
    val temperatureMax: Double,
    val temperatureMin: Double,
    val precipitation: Double,
    val windSpeed: Double,
    val windDirection: String,
    val dayOfWeek: DayOfWeek = date.dayOfWeek
)