package com.example.weatherappcompose.data.remote

import com.squareup.moshi.Json

data class WeatherDto(
    val daily: DailyWeatherDataDto,
    val hourly: HourlyWeatherDataDto
)

data class HourlyWeatherDataDto(
    @Json(name = "time") val times: List<String>,
    @Json(name = "temperature_2m") val temperatures: List<Double>,
    @Json(name = "precipitation") val precipitations: List<Double>,
    @Json(name = "weathercode") val weatherCodes: List<Int>,
    @Json(name = "windspeed_10m") val windSpeeds: List<Double>,
    @Json(name = "winddirection_10m") val windDirections: List<Int>,
    @Json(name = "relativehumidity_2m") val humidities: List<Int>,
    @Json(name = "pressure_msl") val pressures: List<Double>
)

data class DailyWeatherDataDto(
    @Json(name = "time") val date: List<String>,
    @Json(name = "weathercode") val weatherCodes: List<Int>,
    @Json(name = "temperature_2m_max") val temperaturesMax: List<Double>,
    @Json(name = "temperature_2m_min") val temperaturesMin: List<Double>,
    @Json(name = "precipitation_sum") val precipitations: List<Double>,
    @Json(name = "windspeed_10m_max") val windSpeeds: List<Double>,
    @Json(name = "winddirection_10m_dominant") val windDirections: List<Int>,
    @Json(name = "sunrise") val sunrises: List<String>,
    @Json(name = "sunset") val sunsets: List<String>
)