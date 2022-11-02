package com.example.weatherappcompose.data.repositories

import com.example.weatherappcompose.data.remote.LocationApi
import com.example.weatherappcompose.data.remote.LocationDto
import com.example.weatherappcompose.domain.repositories.LocationRepository
import com.example.weatherappcompose.domain.util.Resource
import com.example.weatherappcompose.domain.weather.WeatherInfo
import retrofit2.HttpException
import javax.inject.Inject


class LocationRepositoryImpl @Inject constructor(
    private val api: LocationApi
) : LocationRepository {

    override suspend fun getLocationData(name: String): Resource<LocationDto> {
        return try {
            Resource.Success(
                data = api.getLocationData(name = name)
            )
        } catch (e: HttpException) {
            Resource.Error(message = "${e.code()}, ${e.message()}")
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(message = e.message ?: "An unknown error occured")
        }
    }
}