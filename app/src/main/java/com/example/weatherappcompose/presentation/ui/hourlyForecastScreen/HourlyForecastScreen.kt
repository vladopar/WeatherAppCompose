package com.example.weatherappcompose.presentation.ui.hourlyForecastScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import com.example.weatherappcompose.domain.weather.WeatherData
import com.example.weatherappcompose.presentation.ViewModel
import com.example.weatherappcompose.presentation.ui.commonComposables.TopBarWithNavigateUp
import com.example.weatherappcompose.presentation.ui.favoriteLocationScreen.HourlyForecastItem
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HourlyForecastScreen(
    viewModel: ViewModel,
    backStack: ArrayDeque<NavBackStackEntry>,
    index: Int?,
    navigateUp: () -> Unit
) {
    val weatherDataList = viewModel.state.weatherInfo?.hourlyWeatherData?.get(index) ?: emptyList()

    val title = when (index) {
        0 -> "TODAY"
        else -> weatherDataList.first().dayOfWeek.toString()
            .lowercase().replaceFirstChar { it.uppercase() }
    }


    Scaffold(
        topBar = {
            TopBarWithNavigateUp(
                title = title,
                backStack = backStack,
                navigateUp = navigateUp
            )
        }
    ) {
        Box(modifier = Modifier.padding(it)) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                LazyColumn() {
                    items(weatherDataList) { item ->
                        HourlyForecastItem(weatherData = item)
                    }
                }
            }
        }
    }

}