package com.example.weatherappcompose.domain.repositories

import com.example.weatherappcompose.domain.location.Location
import com.example.weatherappcompose.domain.util.Resource

interface LocationRepository {
    suspend fun getLocationData(name: String) : Resource<List<Location>>

    suspend fun insertFavoriteLocation(location: Location)

    suspend fun deleteFavoriteLocation(location: Location)

    suspend fun getFavoriteLocations(): List<Location>

}