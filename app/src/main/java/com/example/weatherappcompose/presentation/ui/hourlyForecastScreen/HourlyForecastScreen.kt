package com.example.weatherappcompose.presentation.ui.hourlyForecastScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import com.example.weatherappcompose.presentation.ViewModel
import com.example.weatherappcompose.presentation.ui.commonComposables.TopBarWithNavigateUp
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.ArrayDeque

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

    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

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
                    .padding(start = 16.dp, end = 16.dp)
            ) {
                LazyColumn(
                    state = listState,
                    modifier = Modifier
                ) {
                    items(weatherDataList) { item ->
                        HourlyForecastItem(weatherData = item)

                    }
                    if (LocalDateTime.now().dayOfWeek == weatherDataList.first().dayOfWeek) {
                        coroutineScope.launch {
                            listState.scrollToItem(LocalDateTime.now().hour)
                        }
                    }
                }
            }
        }
    }

}