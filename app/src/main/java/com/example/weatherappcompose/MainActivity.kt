package com.example.weatherappcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.weatherappcompose.data.util.internetConnectionToast
import com.example.weatherappcompose.data.util.isConnected
import com.example.weatherappcompose.presentation.ViewModel
import com.example.weatherappcompose.presentation.ui.favoriteLocationScreen.FavoriteLocationScreen
import com.example.weatherappcompose.presentation.ui.hourlyForecastScreen.HourlyForecastScreen
import com.example.weatherappcompose.presentation.ui.locationSearchScreen.LocationSearchScreen
import com.example.weatherappcompose.presentation.ui.mainScreen.CurrentWeatherScreen
import com.example.weatherappcompose.presentation.ui.theme.WeatherAppComposeTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

sealed class Screen(val route: String) {
    object CurrentWeatherScreen : Screen("current_weather_screen")
    object LocationSearchScreen : Screen("location_search_screen")
    object FavoriteLocationScreen : Screen("favorite_location_screen")
    object HourlyForecastScreen : Screen("hourly_forecast_screen")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach {
                append("/$it")
            }
        }
    }
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

                NavHost(
                    navController = navController,
                    startDestination = Screen.CurrentWeatherScreen.route
                ) {
                    composable(route = Screen.CurrentWeatherScreen.route) {
                        CurrentWeatherScreen(
                            viewModel = viewModel,
                            onSearchIconClick = {
                                if (isConnected(application.applicationContext)) {
                                    navController.navigate(Screen.LocationSearchScreen.route)
                                } else {
                                    internetConnectionToast(applicationContext)
                                }
                            },
                            onFavoriteIconClick = {
                                if (isConnected(application.applicationContext)) {
                                    navController.navigate(Screen.FavoriteLocationScreen.route)
                                } else {
                                    internetConnectionToast(applicationContext)
                                }
                            },
                            onItemClick = { navController.navigate(Screen.HourlyForecastScreen.withArgs(it.toString())) }
                        )
                    }
                    composable(route = Screen.LocationSearchScreen.route) {
                        LocationSearchScreen(
                            viewModel = viewModel,
                            backStack = backStack,
                            navigateUp = { navController.navigateUp() },
                        )
                    }
                    composable(route = Screen.FavoriteLocationScreen.route) {
                        FavoriteLocationScreen(
                            viewModel = viewModel,
                            backStack = backStack,
                            navigateUp = { navController.navigateUp() }
                        )
                    }
                    composable(route = Screen.HourlyForecastScreen.route + "/{index}",
                        arguments = listOf(
                            navArgument("index") {
                                type = NavType.IntType
                            }
                        )
                    ) {

                        HourlyForecastScreen(
                            viewModel = viewModel,
                            backStack = backStack,
                            index = it.arguments?.getInt("index"),
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