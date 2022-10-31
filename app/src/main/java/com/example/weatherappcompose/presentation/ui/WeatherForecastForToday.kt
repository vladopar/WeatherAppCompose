package com.example.weatherappcompose.presentation.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherappcompose.presentation.WeatherState
import kotlinx.coroutines.launch
import java.time.LocalDateTime

@Composable
fun WeatherForecastForToday(
    state: WeatherState,
    modifier: Modifier = Modifier
) {
    state.weatherInfo?.hourlyWeatherData?.get(0)?.let { data ->
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = "TODAY",
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
            )
            Spacer(modifier = Modifier.height(16.dp))
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                content = {
                    items(data) { weatherData ->
                        WeatherDataForDayOrHour(
                            weatherData = weatherData,
                            modifier = Modifier
                                .height(160.dp)
                        )
                    }
                })
        }
    }

}