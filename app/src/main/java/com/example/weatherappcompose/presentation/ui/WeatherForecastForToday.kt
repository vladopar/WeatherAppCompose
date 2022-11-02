package com.example.weatherappcompose.presentation.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
    val listState = rememberLazyListState()

    state.weatherInfo?.dailyWeatherData?.let { data ->
        Column(
            modifier = modifier
                .fillMaxWidth()
                //.padding(horizontal = 16.dp)
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
                        WeatherDataForDayOrHour(
                            index = index,
                            weatherData = weatherData,
                            modifier = Modifier
                                .height(160.dp)
                        )
                    }
                })
            LaunchedEffect(listState) {
                val index = LocalDateTime.now().hour
                listState.scrollToItem(index, 0)
            }
        }
    }
}