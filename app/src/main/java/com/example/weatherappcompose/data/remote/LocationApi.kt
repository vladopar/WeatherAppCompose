package com.example.weatherappcompose.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

private const val END_POINT = "v1/search?"

interface LocationApi {

    @GET(END_POINT)
    suspend fun getLocationData(
        @Query("name") name: String
    ): LocationDto
}