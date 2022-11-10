package com.example.weatherappcompose.presentation.ui.favoriteLocationScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherappcompose.R
import com.example.weatherappcompose.domain.weather.WeatherData
import com.example.weatherappcompose.domain.weather.WeatherType
import com.example.weatherappcompose.presentation.ui.commonComposables.WeatherDataWithIcon
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@Composable
fun HourlyForecastItem(
    weatherData: WeatherData
) {
    var isExpanded by remember { mutableStateOf(false) }

    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .padding(vertical = 8.dp)
            .fillMaxWidth()
            .shadow(8.dp, RoundedCornerShape(10.dp))
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .padding(16.dp)
            ) {
                Column {
                    Image(
                        painter = painterResource(id = weatherData.weatherType.iconRes),
                        contentDescription = weatherData.weatherType.weatherDesc,
                        modifier = Modifier
                            .width(68.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column() {
                    Text(
                        text = "${weatherData.temperature} °C",
                        fontWeight = FontWeight.Bold,
                        fontSize = 36.sp,
                        modifier = Modifier
                    )
                    Text(text = weatherData.weatherType.weatherDesc)
                }
                Column(
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.End,
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Text(
                        text = weatherData.time.format(DateTimeFormatter.ofPattern("HH:mm")),
                    )
                    IconButton(onClick = { isExpanded = !isExpanded }) {
                        Icon(
                            imageVector = if (isExpanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                            contentDescription = "More details"
                        )
                    }
                }

            }
            if (isExpanded) {
                Row(
                    horizontalArrangement = Arrangement.SpaceAround,
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                        .fillMaxWidth()
                ) {
                    Column(
                        horizontalAlignment = Alignment.Start,
                        modifier = Modifier
                        .fillMaxWidth(0.5f)
                    ) {
                        WeatherDataWithIcon(
                            value = weatherData.precipitation.toString(),
                            icon = R.drawable.ic_drop,
                            unit = "mm"
                        )
                        WeatherDataWithIcon(
                            value = weatherData.windSpeed.toString(),
                            icon = R.drawable.ic_wind,
                            unit = " km/h, ${weatherData.windDirection}"
                        )
                    }
                    Column(
                        horizontalAlignment = Alignment.Start,
                        modifier = Modifier
                        .fillMaxWidth()
                    ){
                        WeatherDataWithIcon(
                            value = weatherData.humidity.toString(),
                            icon = R.drawable.ic_humidity,
                            unit = "%"
                        )
                        WeatherDataWithIcon(
                            value = weatherData.pressure.toString(),
                            icon = R.drawable.ic_pressure,
                            unit = "hPa"
                        )
                    }
                }
            }
        }
    }
}

val weatherType = WeatherType(
    weatherDesc = "Sunny",
    iconRes = R.drawable.ic_sunny
)

val weatherData = WeatherData(
    time = LocalDateTime.parse("2022-11-10T12:00", DateTimeFormatter.ISO_LOCAL_DATE_TIME),
    weatherType = weatherType,
    temperature = 25.00,
    precipitation = 0.00,
    windSpeed = 10.0,
    windDirection = "NW",
    humidity = 40,
    pressure = 1021.1
)

@Preview
@Composable
fun HourlyForecastItemPreview() {
    HourlyForecastItem(weatherData = weatherData)
}