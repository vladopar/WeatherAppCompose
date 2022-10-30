package com.example.weatherappcompose.domain.weather

data class WeatherInfo(
    val currentWeatherData: CurrentWeatherData,
    val dailyWeatherData: List<DailyWeatherData>
)