package com.example.weatherappcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.ExperimentalMaterial3Api
import com.example.weatherappcompose.presentation.WeatherViewModel
import com.example.weatherappcompose.presentation.ui.mainScreen.MainScreen
import com.example.weatherappcompose.presentation.ui.theme.WeatherAppComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: WeatherViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.loadWeatherInfo()
        setContent {
            WeatherAppComposeTheme {
                MainScreen(viewModel = viewModel)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadWeatherInfo()
    }
}