package com.example.weatherappcompose.domain.repositories

import com.example.weatherappcompose.domain.location.Location
import com.example.weatherappcompose.domain.util.Resource

interface LocationRepository {
    suspend fun getLocationData(name: String) : Resource<List<Location>>
}