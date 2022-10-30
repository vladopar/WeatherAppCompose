package com.example.weatherappcompose.data.mappers

import com.example.weatherappcompose.data.remote.CurrentWeatherDataDto
import com.example.weatherappcompose.data.remote.DailyWeatherDataDto
import com.example.weatherappcompose.data.remote.WeatherDto
import com.example.weatherappcompose.domain.weather.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun WeatherDto.toWeatherInfo(): WeatherInfo {
    return WeatherInfo(
        currentWeatherData = currentWeather.toCurrentWeatherData(
            daily.sunrise.first(),
            daily.sunset.first()
        ),
        dailyWeatherData = daily.toDailyWeatherDataList()
    )
}

fun CurrentWeatherDataDto.toCurrentWeatherData(
    sunrise: String,
    sunset: String
): CurrentWeatherData {
    return CurrentWeatherData(
        time = LocalDateTime.parse(time, DateTimeFormatter.ISO_LOCAL_DATE_TIME),
        weatherType = WeatherType.fromWMO(weatherCode),
        temperature = temperature,
        windSpeed = windSpeed,
        windDirection = WindDirectionMapper.map(windDirection),
        sunrise = sunrise.substringAfter("T"),
        sunset = sunset.substringAfter("T")
    )
}

fun DailyWeatherDataDto.toDailyWeatherDataList(): List<DailyWeatherData> {
    return date.mapIndexed { index, date ->
        val weatherType = WeatherType.fromWMO(weatherCodes[index])
        val temperatureMax = temperaturesMax[index]
        val temperatureMin = temperaturesMin[index]
        val precipitation = precipitations[index]
        val windSpeed = windSpeeds[index]
        val windDirection = WindDirectionMapper.map(windDirections[index])
        DailyWeatherData(
            date = LocalDate.parse(date),
            weatherType = weatherType,
            temperatureMax = temperatureMax,
            temperatureMin = temperatureMin,
            precipitation = precipitation,
            windSpeed = windSpeed,
            windDirection = windDirection,
        )
    }
}