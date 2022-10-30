package com.example.weatherappcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import com.example.weatherappcompose.presentation.WeatherViewModel
import com.example.weatherappcompose.presentation.ui.WeatherCard
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
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.onPrimary)
                ) {
                    WeatherCard(state = viewModel.state)
                }
            }
        }
    }
}