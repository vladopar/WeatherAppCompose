package com.example.weatherappcompose.presentation.ui.mainScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.weatherappcompose.presentation.ViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrentWeatherScreen(
    viewModel: ViewModel,
    onSearchIconClick: () -> Unit,
    onFavoriteIconClick: () -> Unit,
    onClick: (Int) -> Unit
) {

    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = Unit) {
        scope.launch {
            viewModel.state.currentSelectedLocation?.let { viewModel.loadWeatherInfo(it) }
                ?: onSearchIconClick()
        }
    }

    val scrollState = rememberScrollState()

    Scaffold(
        topBar = { CurrentWeatherTopBar(onSearchIconClick, onFavoriteIconClick) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.onPrimary)
                .padding(it)
                .verticalScroll(
                    scrollState,
                    true
                )
        ) {
            WeatherCard(state = viewModel.state)
            Spacer(modifier = Modifier.height(0.dp))
            WeatherForecastForToday(state = viewModel.state, onClick = onClick )
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