package com.example.weatherappcompose.domain.locationTracker

import android.location.Location

interface LocationTracker {
    suspend fun getCurrentLocation(): Location?
}