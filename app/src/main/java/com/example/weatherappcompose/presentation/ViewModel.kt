package com.example.weatherappcompose.presentation

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherappcompose.domain.location.Location
import com.example.weatherappcompose.domain.repositories.LocationRepository
import com.example.weatherappcompose.domain.repositories.WeatherRepository
import com.example.weatherappcompose.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val locationRepository: LocationRepository,
    application: Application
) : AndroidViewModel(application) {

    private val location = Location(
        id = null,
        name = "Trencin",
        lat = 48.90,
        lon = 18.06,
        countryCode = "SK",
        region = null
    )

    var state by mutableStateOf(UiState(currentSelectedLocation = location))
        private set

    fun loadWeatherInfo(location: Location) {
        viewModelScope.launch {
            state = state.copy()
            when (val result = weatherRepository.getWeatherData(location.lat, location.lon)) {
                is Resource.Success -> {
                    state = state.copy(
                        weatherInfo = result.data
                    )
                }
                is Resource.Error -> {
                    Log.d("weather", "${result.message}")
                    Toast.makeText(getApplication(), "${result.message}", Toast.LENGTH_SHORT).show()
                    state = state.copy(
                        weatherInfo = null
                    )
                }
            }
        }
    }

    fun loadLocationData(name: String) {
        viewModelScope.launch {
            state = state.copy()
            when (val result = locationRepository.getLocationData(name = name)) {
                is Resource.Success -> {
                    state = state.copy(
                        locationList = result.data
                    )
                }
                is Resource.Error -> {
                    Log.d("location", "${result.message}")
                    Toast.makeText(getApplication(), "${result.message}", Toast.LENGTH_SHORT).show()
                    state = state.copy(
                        locationList = null
                    )
                }
            }
        }
    }

    fun updateLocationState(location: Location) {
        state = state.copy(
            currentSelectedLocation = location
        )
    }

    fun loadFavoriteLocationData() {
        viewModelScope.launch {
            state = state.copy(
                favoriteLocationList = locationRepository.getFavoriteLocations()
            )
        }
    }

    fun insertFavoriteLocation(location: Location) {
        viewModelScope.launch {
            locationRepository.insertFavoriteLocation(location)
        }
    }

    fun deleteFavoriteLocation(location: Location) {
        viewModelScope.launch {
            locationRepository.deleteFavoriteLocation(location)
            state = state.copy(
                favoriteLocationList = locationRepository.getFavoriteLocations()
            )
        }
    }

}