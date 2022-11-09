package com.example.weatherappcompose.presentation.ui.favoriteLocationScreen

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import com.example.weatherappcompose.domain.weather.WeatherData


@Composable
fun HourlyForecastItem(
    weatherData: WeatherData
) {
    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .padding(vertical = 8.dp)
            .fillMaxWidth()
            .shadow(8.dp, RoundedCornerShape(10.dp))
    ) {
        Text(text = weatherData.time.toString())
    }
}