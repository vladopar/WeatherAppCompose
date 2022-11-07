package com.example.weatherappcompose.presentation.ui.mainScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherappcompose.R
import com.example.weatherappcompose.domain.weather.WeatherData
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun WeatherDataForDayOrHour(
    index: Int,
    weatherData: WeatherData,
    textColor: Color = MaterialTheme.colorScheme.onBackground,
    modifier: Modifier = Modifier
) {
    var dateTimeString = ""
    var temperatureString = ""
    val hour = LocalDateTime.now().hour

    when (weatherData.temperature) {
        null -> {
            dateTimeString = if (index == 0) {
                "TODAY"
            } else {
                weatherData.dayOfWeek.toString()
                    .lowercase().replaceFirstChar { it.uppercase() }
            }
            temperatureString = "${weatherData.temperatureMin} / ${weatherData.temperatureMax} °C"
        }
        else -> {
            dateTimeString = weatherData.time.format(DateTimeFormatter.ofPattern("HH:mm"))
            temperatureString = "${weatherData.temperature} °C"
        }
    }
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = modifier
            .border(
                color = Color.Black,
                width = if (hour == weatherData.time.hour) 4.dp else 0.dp,
                shape = RoundedCornerShape(8.dp)
            )
            .shadow(4.dp, RoundedCornerShape(8.dp))
            .width(180.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(
                text = dateTimeString,
                color = textColor
            )
            Image(
                painter = painterResource(id = weatherData.weatherType.iconRes),
                contentDescription = null,
                modifier = Modifier.size(40.dp)
            )
            Text(
                text = temperatureString,
                fontWeight = FontWeight.Bold,
                color = textColor,
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            WeatherDataWithIcon(
                value = weatherData.windSpeed.toString(),
                unit = " km/h, ${weatherData.windDirection}",
                icon = R.drawable.ic_wind
            )
            WeatherDataWithIcon(
                value = weatherData.precipitation.toString(),
                icon = R.drawable.ic_drop,
                unit = " mm"
            )
        }
    }
}