package com.example.weatherappcompose.presentation

import android.app.Application
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherappcompose.domain.repositories.LocationRepository
import com.example.weatherappcompose.domain.repositories.WeatherRepository
import com.example.weatherappcompose.domain.util.Resource
import dagger.hilt.android.internal.Contexts.getApplication
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val locationRepository: LocationRepository,
    application: Application
) : AndroidViewModel(application) {

    private val lat = 48.90
    private val long = 18.06

    var state by mutableStateOf(UiState())
        private set

    fun loadWeatherInfo() {
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

}