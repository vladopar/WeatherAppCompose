package com.example.weatherappcompose

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.weatherappcompose.presentation.ViewModel
import com.example.weatherappcompose.presentation.ui.favoriteLocationScreen.FavoriteLocationScreen
import com.example.weatherappcompose.presentation.ui.locationSearchScreen.LocationSearchScreen
import com.example.weatherappcompose.presentation.ui.mainScreen.CurrentWeatherScreen
import com.example.weatherappcompose.presentation.ui.theme.WeatherAppComposeTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

enum class WeatherAppScreens() {
    CurrentWeatherScreen,
    LocationSearchScreen,
    FavoriteLocationScreen
}

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: ViewModel by viewModels()

    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>

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
                            onSearchIconClick = { navController.navigate(WeatherAppScreens.LocationSearchScreen.name) },
                            onFavoriteIconClick = { navController.navigate(WeatherAppScreens.FavoriteLocationScreen.name) }
                        )
                    }
                    composable(route = WeatherAppScreens.LocationSearchScreen.name) {
                        LocationSearchScreen(
                            viewModel = viewModel,
                            backStack = backStack,
                            navigateUp = { navController.navigateUp() }
                        )
                    }
                    composable(route = WeatherAppScreens.FavoriteLocationScreen.name) {
                        FavoriteLocationScreen(
                            viewModel = viewModel,
                            backStack = backStack,
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

    fun getPermissions() {
        permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) {

        }
        permissionLauncher.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )

    }
}