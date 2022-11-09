package com.example.weatherappcompose.presentation.ui.hourlyForecastScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import com.example.weatherappcompose.presentation.ViewModel
import com.example.weatherappcompose.presentation.ui.commonComposables.TopBarWithNavigateUp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HourlyForecastScreen(
    viewModel: ViewModel,
    backStack: ArrayDeque<NavBackStackEntry>,
    navigateUp: () -> Unit
) {
    Scaffold(
// TODO show selected date in topbar title
        topBar = {
            TopBarWithNavigateUp(
                title = "Hourly Forecast",
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

            }
        }
    }

}