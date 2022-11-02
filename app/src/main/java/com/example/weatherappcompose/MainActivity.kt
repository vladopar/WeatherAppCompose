package com.example.weatherappcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.weatherappcompose.presentation.WeatherViewModel
import com.example.weatherappcompose.presentation.ui.mainScreen.WeatherCard
import com.example.weatherappcompose.presentation.ui.mainScreen.WeatherForecastForToday
import com.example.weatherappcompose.presentation.ui.mainScreen.CurrentWeatherScreen
import com.example.weatherappcompose.presentation.ui.theme.WeatherAppComposeTheme
import dagger.hilt.android.AndroidEntryPoint

enum class WeatherAppScreens() {
    CurrentWeatherScreen,
}

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: WeatherViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.loadWeatherInfo()
        setContent {
            WeatherAppComposeTheme {
                val navController: NavHostController = rememberNavController()
                val backStackEntry by navController.currentBackStackEntryAsState()
                val currentScreen = WeatherAppScreens.valueOf(
                    backStackEntry?.destination?.route ?: WeatherAppScreens.CurrentWeatherScreen.name
                )

                NavHost(
                    navController = navController,
                    startDestination = WeatherAppScreens.CurrentWeatherScreen.name,
                ) {
                    composable(route = WeatherAppScreens.CurrentWeatherScreen.name) {
                        CurrentWeatherScreen(viewModel = viewModel)
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadWeatherInfo()
    }
}