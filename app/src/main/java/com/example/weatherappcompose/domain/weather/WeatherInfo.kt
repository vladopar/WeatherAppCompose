package com.example.weatherappcompose.domain.weather

import java.time.DayOfWeek
import java.time.LocalDateTime

data class WeatherInfo(
    val currentWeatherData: WeatherData?,
    val dailyWeatherData: List<WeatherData>,
    val hourlyWeatherData: Map<Int, List<WeatherData>>
)

data class WeatherData(
    val time: LocalDateTime,
    val weatherType: WeatherType,
    val temperature: Double?,
    val temperatureMax: Double = 0.0,
    val temperatureMin: Double = 0.0,
    val precipitation: Double,
    val windSpeed: Double,
    val windDirection: String,
    val humidity: Int = 0,
    val pressure: Double = 0.0,
    val dayOfWeek: DayOfWeek = time.dayOfWeek,
    val sunrise: String = "",
    val sunset: String = ""
)