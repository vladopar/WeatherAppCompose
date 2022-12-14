package com.example.weatherappcompose.presentation.ui.mainScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherappcompose.presentation.UiState

@Composable
fun WeatherForecastForToday(
    state: UiState,
    modifier: Modifier = Modifier,
    onItemClick: (Int) -> Unit
) {
    val listState = rememberLazyListState()

    state.weatherInfo?.dailyWeatherData?.let { data ->
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            Text(
                text = "NEXT WEEK",
                modifier = Modifier
                    .padding(start = 16.dp),
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
            )
            Spacer(modifier = Modifier.height(16.dp))
            LazyRow(
                state = listState,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                content = {
                    itemsIndexed(data) { index, weatherData ->
                        DailyForecastItem(
                            index = index,
                            weatherData = weatherData,
                            modifier = Modifier
                                .height(160.dp)
                                .clickable { onItemClick(index) }
                        )
                    }
                }
            )
        }
    }
}