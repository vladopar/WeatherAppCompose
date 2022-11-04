package com.example.weatherappcompose.data.repositories

import com.example.weatherappcompose.data.local.FavoriteLocationDao
import com.example.weatherappcompose.data.mappers.toLocationList
import com.example.weatherappcompose.data.remote.LocationApi
import com.example.weatherappcompose.domain.location.Location
import com.example.weatherappcompose.domain.repositories.LocationRepository
import com.example.weatherappcompose.domain.util.Resource
import retrofit2.HttpException
import javax.inject.Inject


class LocationRepositoryImpl @Inject constructor(
    private val api: LocationApi,
    private val dao: FavoriteLocationDao
) : LocationRepository {

    override suspend fun getLocationData(name: String): Resource<List<Location>> {
        return try {
            Resource.Success(
                data = api.getLocationData(name = name).toLocationList()
            )
        } catch (e: HttpException) {
            Resource.Error(message = "${e.code()}, ${e.message()}")
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(message = e.message ?: "An unknown error occured")
        }
    }

    override suspend fun insertFavoriteLocation(location: Location) {
        dao.insertFavoriteLocation(location)
    }

    override suspend fun deleteFavoriteLocation(location: Location) {
        dao.deleteFavoriteLocation(location)
    }

    override suspend fun getFavoriteLocations(): List<Location> {
        return dao.getFavoriteLocations()
    }
}