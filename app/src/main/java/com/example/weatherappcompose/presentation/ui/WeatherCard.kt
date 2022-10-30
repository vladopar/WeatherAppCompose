package com.example.weatherappcompose.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherappcompose.presentation.WeatherState
import java.time.format.DateTimeFormatter
import com.example.weatherappcompose.R

@Composable
fun WeatherCard(
    state: WeatherState,
    modifier: Modifier = Modifier
) {
    state.weatherInfo?.currentWeatherData?.let { data ->
        Card(
            shape = RoundedCornerShape(10.dp),
            modifier = modifier.padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "${data.time.format(DateTimeFormatter.ISO_DATE)}," +
                            " ${data.time.format(DateTimeFormatter.ofPattern("HH:mm"))}",
                    modifier = Modifier
                        .align(Alignment.End)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Location placeholder",
                    modifier = Modifier
                        .align(Alignment.End)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Image(
                    painter = painterResource(id = data.weatherType.iconRes),
                    contentDescription = null,
                    modifier = Modifier
                        .size(200.dp)
                )
                Text(
                    text = "${data.temperature} °C",
                    fontSize = 50.sp
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = data.weatherType.weatherDesc,
                    fontSize = 20.sp,
                )
                Spacer(modifier = Modifier.height(32.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    WeatherDataWithIcon(
                        value = data.sunrise,
                        icon = R.drawable.ic_sunrise
                    )
                    WeatherDataWithIcon(
                        value = data.sunset,
                        icon = R.drawable.ic_sunset
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    WeatherDataWithIcon(
                        value = data.windSpeed.toString(),
                        unit = "km/h ${data.windDirection}",
                        icon = R.drawable.ic_wind
                    )
                    WeatherDataWithIcon(
                        value = data.humidity.toString(),
                        unit = "%",
                        icon = R.drawable.ic_drop
                    )
                    WeatherDataWithIcon(
                        value = data.pressure.toString(),
                        unit = "hPa",
                        icon = R.drawable.ic_pressure
                    )
                }
            }
        }
    }
}