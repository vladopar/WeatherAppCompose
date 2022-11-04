package com.example.weatherappcompose.presentation.ui.mainScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.weatherappcompose.presentation.ViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrentWeatherScreen(
    viewModel: ViewModel,
    onSearchIconClick: () -> Unit,
    onFavoriteIconClick: () -> Unit
) {
    viewModel.loadWeatherInfo(viewModel.state.currentSelectedLocation.lat, viewModel.state.currentSelectedLocation.long)

    Scaffold(
        topBar = { CurrentWeatherTopBar(onSearchIconClick, onFavoriteIconClick) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.onPrimary)
                .padding(it)
        ) {
            WeatherCard(state = viewModel.state)
            Spacer(modifier = Modifier.height(0.dp))
            WeatherForecastForToday(state = viewModel.state)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrentWeatherTopBar(
    onSearchIconClick: () -> Unit,
    onFavoriteIconClick: () -> Unit
) {
    TopAppBar(
        title = { Text(text = "Current Weather") },
        actions = {
            IconButton(onClick = onSearchIconClick) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Location Search"
                )
            }
            IconButton(onClick = onFavoriteIconClick) {
                Icon(
                    imageVector = Icons.Filled.Favorite,
                    contentDescription = "Favorite Locations"
                )
            }
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.onPrimary
        )
    )
}