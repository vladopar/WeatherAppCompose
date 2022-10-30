package com.example.weatherappcompose.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

private const val END_POINT = "v1/forecast?hourly=relativehumidity_2m,temperature_2m,precipitation,weathercode,windspeed_10m,winddirection_10m&daily=weathercode,temperature_2m_max,temperature_2m_min,sunrise,sunset,precipitation_sum,windspeed_10m_max,winddirection_10m_dominant&current_weather=true&timezone=Europe%2FBerlin"

interface WeatherApi {

    @GET(END_POINT)
    suspend fun getWeatherData(
        @Query("latitude") lat: Double,
        @Query("longitude") long: Double
    ): WeatherDto
}