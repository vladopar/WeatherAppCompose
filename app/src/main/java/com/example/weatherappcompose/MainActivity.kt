package com.example.weatherappcompose

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.weatherappcompose.data.util.InternetConnectionToast
import com.example.weatherappcompose.data.util.isConnected
import com.example.weatherappcompose.presentation.ViewModel
import com.example.weatherappcompose.presentation.ui.favoriteLocationScreen.FavoriteLocationScreen
import com.example.weatherappcompose.presentation.ui.hourlyForecastScreen.HourlyForecastScreen
import com.example.weatherappcompose.presentation.ui.locationSearchScreen.LocationSearchScreen
import com.example.weatherappcompose.presentation.ui.mainScreen.CurrentWeatherScreen
import com.example.weatherappcompose.presentation.ui.theme.WeatherAppComposeTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

enum class WeatherAppScreens() {
    CurrentWeatherScreen,
    LocationSearchScreen,
    FavoriteLocationScreen,
    HourlyForecastScreen
}

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: ViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            viewModel.loadLocationFromDataStore()
        }

        setContent {
            WeatherAppComposeTheme {
                val navController: NavHostController = rememberNavController()
                val backStack = navController.backQueue
                val backStackEntry by navController.currentBackStackEntryAsState()
                val currentScreen = WeatherAppScreens.valueOf(
                    backStackEntry?.destination?.route
                        ?: WeatherAppScreens.CurrentWeatherScreen.name
                )

                NavHost(
                    navController = navController,
                    startDestination = WeatherAppScreens.CurrentWeatherScreen.name
                ) {
                    composable(route = WeatherAppScreens.CurrentWeatherScreen.name) {
                        CurrentWeatherScreen(
                            viewModel = viewModel,
                            onSearchIconClick = {
                                if (isConnected(application.applicationContext)) {
                                    navController.navigate(WeatherAppScreens.LocationSearchScreen.name)
                                } else {
                                    InternetConnectionToast(applicationContext)
                                }
                            },
                            onFavoriteIconClick = {
                                if (isConnected(application.applicationContext)) {
                                    navController.navigate(WeatherAppScreens.FavoriteLocationScreen.name)
                                } else {
                                    InternetConnectionToast(applicationContext)
                                }
                            },
                            onDayClick = { navController.navigate(WeatherAppScreens.HourlyForecastScreen.name) }
                        )
                    }
                    composable(route = WeatherAppScreens.LocationSearchScreen.name) {
                        LocationSearchScreen(
                            viewModel = viewModel,
                            backStack = backStack,
                            navigateUp = { navController.navigateUp() },
                        )
                    }
                    composable(route = WeatherAppScreens.FavoriteLocationScreen.name) {
                        FavoriteLocationScreen(
                            viewModel = viewModel,
                            backStack = backStack,
                            navigateUp = { navController.navigateUp() }
                        )
                    }
                    composable(route = WeatherAppScreens.HourlyForecastScreen.name,
                    ) {

                        HourlyForecastScreen(
                            viewModel = viewModel,
                            backStack = backStack,
// TODO update with actual value
                            index = 0,
                            navigateUp = { navController.navigateUp() }
                        )
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.state.currentSelectedLocation?.let { viewModel.loadWeatherInfo(it) }
    }
}