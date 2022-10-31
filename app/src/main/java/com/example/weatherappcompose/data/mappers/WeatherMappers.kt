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

    val hourlyWeatherDataMap = hourly.toHourlyWeatherDataList()
    val dailyWeatherDataList = daily.toDailyWeatherDataList()
    val now = LocalDateTime.now()

    var tempCurrentWeatherData = hourlyWeatherDataMap[0]?.find {
        val hour = if (now.minute < 30 || now.hour == 23) {
            now.hour
        } else {
            now.hour + 1
        }
        it.time.hour == hour
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
            sunrise = dailyWeatherDataList[0].sunrise.substringAfter("T"),
            sunset = dailyWeatherDataList[0].sunset.substringAfter("T")
        ),
        dailyWeatherData = dailyWeatherDataList,
        hourlyWeatherData = hourlyWeatherDataMap
    )
}

fun HourlyWeatherDataDto.toHourlyWeatherDataList(): Map<Int, List<WeatherData>> {
    return times.mapIndexed { index, time ->
        val weatherType = WeatherType.fromWMO(weatherCodes[index])
        val temperature = temperatures[index]
        val precipitation = precipitations[index]
        val windSpeed = windSpeeds[index]
        val windDirection = WindDirectionMapper.map(windDirections[index])
        val humidity = humidities[index]
        val pressure = pressures[index]
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
    }.groupBy {
        it.index / 24
    }.mapValues { it ->
        it.value.map { it.data }
    }

}

fun DailyWeatherDataDto.toDailyWeatherDataList(): List<WeatherData> {
    return date.mapIndexed { index, date ->
        val weatherType = WeatherType.fromWMO(weatherCodes[index])
        val temperatureMax = temperaturesMax[index]
        val temperatureMin = temperaturesMin[index]
        val precipitation = precipitations[index]
        val windSpeed = windSpeeds[index]
        val windDirection = WindDirectionMapper.map(windDirections[index])
        val sunrise = sunrises[index]
        val sunset = sunsets[index]
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
    }
}