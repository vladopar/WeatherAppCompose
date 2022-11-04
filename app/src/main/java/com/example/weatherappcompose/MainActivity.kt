package com.example.weatherappcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.weatherappcompose.presentation.ViewModel
import com.example.weatherappcompose.presentation.ui.locationSearchScreen.LocationSearchScreen
import com.example.weatherappcompose.presentation.ui.mainScreen.CurrentWeatherScreen
import com.example.weatherappcompose.presentation.ui.theme.WeatherAppComposeTheme
import dagger.hilt.android.AndroidEntryPoint

enum class WeatherAppScreens() {
    CurrentWeatherScreen,
    LocationSearchScreen
}

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: ViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherAppComposeTheme {
                val navController: NavHostController = rememberNavController()
                val backStackEntry by navController.currentBackStackEntryAsState()
                val currentScreen = WeatherAppScreens.valueOf(
                    backStackEntry?.destination?.route
                        ?: WeatherAppScreens.CurrentWeatherScreen.name
                )
                NavHost(
                    navController = navController,
                    startDestination = WeatherAppScreens.CurrentWeatherScreen.name,
                ) {
                    composable(route = WeatherAppScreens.CurrentWeatherScreen.name) {
                        CurrentWeatherScreen(
                            viewModel = viewModel,
                            onSearchIconClick = { navController.navigate(WeatherAppScreens.LocationSearchScreen.name) }
                        )
                    }
                    composable(route = WeatherAppScreens.LocationSearchScreen.name) {
                        LocationSearchScreen(
                            viewModel = viewModel,
                            navigateUp = {navController.navigateUp()}
                        )
                    }
                }
            }
        }
    }
}