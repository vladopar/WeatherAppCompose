package com.example.weatherappcompose.data.repositories

import android.app.Application
import com.example.weatherappcompose.data.local.DataStore
import com.example.weatherappcompose.data.mappers.toWeatherInfo
import com.example.weatherappcompose.data.remote.WeatherApi
import com.example.weatherappcompose.domain.repositories.WeatherRepository
import com.example.weatherappcompose.domain.util.Resource
import com.example.weatherappcompose.domain.weather.WeatherInfo
import retrofit2.HttpException
import javax.inject.Inject


class WeatherRepositoryImpl @Inject constructor(
    private val api: WeatherApi,
    application: Application
) : WeatherRepository {

    private val dataStore = DataStore(application.applicationContext)


    override suspend fun getWeatherData(lat: Double, long: Double): Resource<WeatherInfo> {
        try {
            val result = Resource.Success(
                data = api.getWeatherData(
                    lat = lat,
                    long = long
                ).toWeatherInfo()
            )
            result.data?.let { dataStore.saveWeatherInfoString(it) }
            return result
        } catch (e: HttpException) {
            return Resource.Error(
                message = "${e.code()}, ${e.message()}",
                data = dataStore.getWeatherInfoString()
            )
        } catch (e: Exception) {
            e.printStackTrace()
            return Resource.Error(
                message = e.message ?: "An unknown error occured",
                data = dataStore.getWeatherInfoString()
            )
        }
    }
}