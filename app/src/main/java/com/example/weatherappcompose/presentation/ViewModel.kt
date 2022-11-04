package com.example.weatherappcompose.presentation

import android.app.Application
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

    private var _lat = 48.90
    val lat = _lat

    private var _long = 18.06
    val long = _long

    private val location = Location(
        id = null,
        name = "Trencin",
        lat = 48.90,
        long = 18.06,
        countryCode = "SK",
        region = null
    )

    var state by mutableStateOf(UiState(currentSelectedLocation = location))
        private set

    fun loadWeatherInfo(lat: Double, long: Double) {
        viewModelScope.launch {
            state = state.copy()
            when (val result = weatherRepository.getWeatherData(lat, long)) {
                is Resource.Success -> {
                    state = state.copy(
                        weatherInfo = result.data
                    )
                }
                is Resource.Error -> {
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

}