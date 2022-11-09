package com.example.weatherappcompose.presentation

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherappcompose.WeatherApp
import com.example.weatherappcompose.data.local.DataStore
import com.example.weatherappcompose.data.util.isConnected
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

    var state by mutableStateOf(UiState())
        private set

    private val dataStore = DataStore(application.applicationContext)

    val app = application

    fun loadWeatherInfo(location: Location) {
        Log.d("json", "loading")
        viewModelScope.launch {
            state = state.copy()
            when (val result = weatherRepository.getWeatherData(location.lat, location.lon)) {
                is Resource.Success -> {
                    dataStore.saveWeatherInfoString(result.data!!)
                    state = state.copy(
                        weatherInfo = result.data
                    )
                }
                is Resource.Error -> {
                    if (!isConnected(context = app.applicationContext)) {
                        Toast.makeText(
                            getApplication(),
                            "No Internet Connection",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Log.d("json", "${result.message}")
                        Toast.makeText(getApplication(), "${result.message}", Toast.LENGTH_SHORT)
                            .show()
                    }
                    state = state.copy(
                        weatherInfo = dataStore.getWeatherInfoString()
                    )
                }
            }
        }
    }

    fun loadListOfLocationData(name: String) {
        viewModelScope.launch {
            state = state.copy()
            when (val result = locationRepository.getLocationData(name = name.trim())) {
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

    suspend fun loadLocationFromDataStore() {
        state = state.copy(
            currentSelectedLocation = dataStore.getCurrentLocationString()
        )
        Log.d("json", "currLocFromVM: ${state.currentSelectedLocation}")
    }

    fun updateLocationState(location: Location) {
        state = state.copy(
            currentSelectedLocation = location
        )
        Log.d("json", "location in state: ${state.currentSelectedLocation?.name.toString()}")
        viewModelScope.launch {
            dataStore.saveCurrentLocationString(location)
        }
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