package com.example.weatherappcompose.presentation

import com.example.weatherappcompose.domain.location.Location

data class LocationState(
    val locationList: List<Location>? = null,
    var name: String = ""
)