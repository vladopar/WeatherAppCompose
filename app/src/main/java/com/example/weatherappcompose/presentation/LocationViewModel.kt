package com.example.weatherappcompose.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherappcompose.domain.repositories.LocationRepository
import com.example.weatherappcompose.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(
    private val repository: LocationRepository
) : ViewModel() {

    var state by mutableStateOf(LocationState())
        private set

    fun loadLocationData(name: String) {
        viewModelScope.launch {
            state = state.copy()
            when (val result = repository.getLocationData(name = name)) {
                is Resource.Success -> {
                    state = state.copy(
                        locationList = result.data
                    )
                }
                is Resource.Error -> {
                    state = state.copy(
                        locationList = null
                    )
                }
            }
        }
    }
}