package com.example.weatherappcompose.domain.weather

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime

data class WeatherData(
    val time: LocalDateTime,
    val weatherType: WeatherType,
    val temperature: Double = 0.0,
    val temperatureMax: Double = 0.0,
    val temperatureMin: Double = 0.0,
    val precipitation: Double,
    val windSpeed: Double,
    val windDirection: String,
    val dayOfWeek: DayOfWeek? = time.dayOfWeek
)