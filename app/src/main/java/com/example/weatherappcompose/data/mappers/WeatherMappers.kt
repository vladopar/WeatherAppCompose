package com.example.weatherappcompose.data.mappers

import com.example.weatherappcompose.data.remote.DailyWeatherDataDto
import com.example.weatherappcompose.data.remote.HourlyWeatherDataDto
import com.example.weatherappcompose.data.remote.WeatherDto
import com.example.weatherappcompose.domain.weather.WeatherData
import com.example.weatherappcompose.domain.weather.WeatherInfo
import com.example.weatherappcompose.domain.weather.WeatherType
import com.example.weatherappcompose.domain.weather.WindDirectionMapper
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private data class IndexedWeatherData(
    val index: Int,
    val data: WeatherData
)

fun WeatherDto.toWeatherInfo(): WeatherInfo {

    val hourlyWeatherDataMap = hourly?.toHourlyWeatherDataList()
    val dailyWeatherDataList = daily?.toDailyWeatherDataList()
    val now = LocalDateTime.now()

    var tempCurrentWeatherData = hourlyWeatherDataMap?.get(0)?.find {
        it.time.hour == now.hour
    }

    return WeatherInfo(
        currentWeatherData = WeatherData(
            time = tempCurrentWeatherData!!.time,
            weatherType = tempCurrentWeatherData.weatherType,
            temperature = tempCurrentWeatherData.temperature,
            precipitation = tempCurrentWeatherData.precipitation,
            windSpeed = tempCurrentWeatherData.windSpeed,
            windDirection = tempCurrentWeatherData.windDirection,
            humidity = tempCurrentWeatherData.humidity,
            pressure = tempCurrentWeatherData.pressure,
            sunrise = dailyWeatherDataList?.get(0)?.sunrise?.substringAfter("T") ?: "",
            sunset = dailyWeatherDataList?.get(0)?.sunset?.substringAfter("T") ?: ""
        ),
        dailyWeatherData = dailyWeatherDataList ?: emptyList(),
        hourlyWeatherData = hourlyWeatherDataMap ?: emptyMap()
    )
}

fun HourlyWeatherDataDto.toHourlyWeatherDataList(): Map<Int, List<WeatherData>> {
    return times?.mapIndexed { index, time ->
        val weatherType = WeatherType.fromWMO(weatherCodes?.get(index) ?: 0)
        val temperature = temperatures?.get(index)
        val precipitation = precipitations?.get(index) ?: 0.0
        val windSpeed = windSpeeds?.get(index) ?: 0.0
        val windDirection = WindDirectionMapper.map(windDirections?.get(index) ?: 0)
        val humidity = humidities?.get(index) ?: 0
        val pressure = pressures?.get(index) ?: 0.0
        IndexedWeatherData(
            index = index,
            data = WeatherData(
                time = LocalDateTime.parse(time, DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                weatherType = weatherType,
                temperature = temperature,
                precipitation = precipitation,
                windSpeed = windSpeed,
                windDirection = windDirection,
                humidity = humidity,
                pressure = pressure
            )
        )
    }?.groupBy {
        it.index / 24
    }?.mapValues { it ->
        it.value.map { it.data }
    } ?: emptyMap()

}

fun DailyWeatherDataDto.toDailyWeatherDataList(): List<WeatherData> {
    return date?.mapIndexed { index, date ->
        val weatherType = WeatherType.fromWMO(weatherCodes?.get(index) ?: 0)
        val temperatureMax = temperaturesMax?.get(index) ?: 0.0
        val temperatureMin = temperaturesMin?.get(index) ?: 0.0
        val precipitation = precipitations?.get(index) ?: 0.0
        val windSpeed = windSpeeds?.get(index) ?: 0.0
        val windDirection = WindDirectionMapper.map(windDirections?.get(index) ?: 0)
        val sunrise = sunrises?.get(index) ?: ""
        val sunset = sunsets?.get(index) ?: ""
        WeatherData(
            time = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd")).atStartOfDay(),
            weatherType = weatherType,
            temperature = null,
            temperatureMax = temperatureMax,
            temperatureMin = temperatureMin,
            precipitation = precipitation,
            windSpeed = windSpeed,
            windDirection = windDirection,
            sunrise = sunrise,
            sunset = sunset
        )
    } ?: emptyList()
}