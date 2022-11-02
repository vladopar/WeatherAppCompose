package com.example.weatherappcompose.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherappcompose.domain.repositories.WeatherRepository
import com.example.weatherappcompose.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepository
) : ViewModel() {

    private val lat = 48.90
    private val long = 18.06

    var state by mutableStateOf(WeatherState())
        private set

    fun loadWeatherInfo() {
        viewModelScope.launch {
            state = state.copy()
            when (val result = repository.getWeatherData(lat, long)) {
                is Resource.Success -> {
                    state = state.copy(
                        weatherInfo = result.data
                    )
                }
                is Resource.Error -> {
                    state = state.copy(
                        weatherInfo = null
                    )
                }
            }
        }
    }
}