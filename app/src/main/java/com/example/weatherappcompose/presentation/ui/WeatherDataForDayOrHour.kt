package com.example.weatherappcompose.presentation.ui

import android.widget.Space
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherappcompose.R
import com.example.weatherappcompose.domain.weather.WeatherData
import java.time.format.DateTimeFormatter

@Composable
fun WeatherDataForDayOrHour(
    weatherData: WeatherData,
    textColor: Color = MaterialTheme.colorScheme.onBackground,
    modifier: Modifier = Modifier
) {
    var dateTimeString = ""
    var temperatureString = ""

    when (weatherData.temperature) {
        null -> {
            dateTimeString = weatherData.time.format(DateTimeFormatter.ofPattern("yyyy--MM-dd"))
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
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
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