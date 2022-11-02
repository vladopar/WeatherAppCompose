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
import com.example.weatherappcompose.presentation.WeatherViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrentWeatherScreen(
    viewModel: WeatherViewModel
) {
    Scaffold(
        topBar = { TopBar() }
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
fun TopBar() {
    TopAppBar(
        title = { Text(text = "Current Weather") },
        actions = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Location Search"
                )
            }
            IconButton(onClick = { /*TODO*/ }) {
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