package com.example.weatherappcompose.domain.weather

data class WeatherInfo(
    val currentWeatherData: CurrentWeatherData,
    val dailyWeatherData: List<WeatherData>,
    val hourlyWeatherData: Map<Int, List<WeatherData>>
)